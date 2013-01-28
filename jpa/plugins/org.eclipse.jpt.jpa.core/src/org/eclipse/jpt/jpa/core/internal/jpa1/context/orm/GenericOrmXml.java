/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.Collection;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA <code>orm.xml</code> file.
 */
public class GenericOrmXml
	extends AbstractOrmXmlContextNode
	implements OrmXml
{
	/**
	 * If the XML resource's content type changes, the mapping file
	 * ref will throw out its current mapping file.
	 */
	protected final JptXmlResource xmlResource;  // never null

	/**
	 * The resource type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JptResourceType resourceType;

	/**
	 * Cache the <code>orm.xml</code> definition alongside the resource type.
	 * (The definition is determined by the resource type.)
	 */
	protected OrmXmlDefinition definition;

	/**
	 * The root element of the <code>orm.xml</code> file.
	 */
	protected EntityMappings root;


	public GenericOrmXml(MappingFileRef parent, JptXmlResource xmlResource) {
		super(parent);
		this.checkXmlResource(xmlResource);
		this.xmlResource = xmlResource;
		this.resourceType = xmlResource.getResourceType();
		this.definition = this.buildDefinition();

		XmlEntityMappings xmlEntityMappings = (XmlEntityMappings) xmlResource.getRootObject();
		if (xmlEntityMappings != null) {
			this.root = this.buildRoot(xmlEntityMappings);
		}
	}


	// ********** synchronize/update **********

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#synchronizeWithResourceModel()
	 */
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncEntityMappings();
	}

	protected void syncEntityMappings() {
		this.syncEntityMappings(true);
	}

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#update()
	 */
	@Override
	public void update() {
		super.update();
		this.updateEntityMappings();
	}

	protected void updateEntityMappings() {
		this.syncEntityMappings(false);
	}

	/**
	 * We call this method from both {@link #syncEntityMappings()} and
	 * {@link #updateEntityMappings()} because<ul>
	 * <li>a <em>sync</em> will occur when the file is edited directly;
	 *     the user could modify something (e.g. the version number) that 
	 *     causes the XML entity mappings to be rebuilt. 
	 * 
	 * <li>an <em>update</em> will occur whenever the JPA file is added or removed: 
	 *     when resource contents replaced from history EMF unloads the resource.
	 */
	protected void syncEntityMappings(boolean sync) {
		XmlEntityMappings oldXmlEntityMappings = (this.root == null) ? null : this.root.getXmlEntityMappings();
		XmlEntityMappings newXmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();
		JptResourceType newResourceType = this.xmlResource.getResourceType();

		// If the old and new XML entity mappings are different instances,
		// we scrap the old context entity mappings and rebuild.
		// (This can happen when the resource model changes drastically,
		// such as a CVS checkout or an edit reversion.)
		if ((oldXmlEntityMappings != newXmlEntityMappings) ||
				(newXmlEntityMappings == null) ||
				this.valuesAreDifferent(this.resourceType, newResourceType)
		) {
			if (this.root != null) {
				this.root.dispose();
				this.setRoot(null);
			}
		}

		this.resourceType = newResourceType;
		this.definition = this.buildDefinition();

		if (newXmlEntityMappings != null) {
			if (this.root == null) {
				this.setRoot(this.buildRoot(newXmlEntityMappings));
			} else {
				// the context entity mappings already holds the XML entity mappings
				if (sync) {
					this.root.synchronizeWithResourceModel();
				}
				else {
					this.root.update();
				}
			}
		}
	}


	// ********** resource type/definition **********

	@Override
	public JptResourceType getResourceType() {
		return this.resourceType;
	}

	public OrmXmlDefinition getDefinition() {
		return this.definition;
	}

	protected OrmXmlDefinition buildDefinition() {
		return (OrmXmlDefinition) this.getJpaPlatform().getResourceDefinition(this.resourceType);
	}


	// ********** root **********

	public EntityMappings getRoot() {
		return this.root;
	}

	protected void setRoot(EntityMappings root) {
		EntityMappings old = this.root;
		this.root = root;
		// dual-purpose state!
		this.firePropertyChanged(XmlFile.ROOT_PROPERTY, old, root);
		this.firePropertyChanged(MappingFile.ROOT_PROPERTY, old, root);
	}

	protected EntityMappings buildRoot(XmlEntityMappings xmlEntityMappings) {
		return this.getContextNodeFactory().buildEntityMappings(this, xmlEntityMappings);
	}


	// ********** queries/generators **********

	public Iterable<Query> getMappingFileQueries() {
		return (this.root != null) ? this.root.getMappingFileQueries() : EmptyIterable.<Query>instance();
	}

	public Iterable<Generator> getMappingFileGenerators() {
		return (this.root != null) ? this.root.getMappingFileGenerators() : EmptyIterable.<Generator>instance();
	}


	// ********** misc **********

	protected void checkXmlResource(JptXmlResource resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		if ( ! resource.getContentType().isKindOf(ResourceMappingFile.Root.CONTENT_TYPE)) {
			throw new IllegalArgumentException("Content type is not 'mapping file': " + resource); //$NON-NLS-1$
		}
	}

	@Override
	public MappingFileRef getParent() {
		return (MappingFileRef) super.getParent();
	}

	@Override
	public IResource getResource() {
		return this.xmlResource.getFile();
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.xmlResource.getFile());
	}

	/**
	 * All the the <code>orm.xml</code> objects go up through the containment
	 * hierarchy to the mapping file root (entity mappings);
	 * but we are "above" the entity mappings here, so simply return itself.
	 */
	@Override
	protected OrmXml getOrmXml() {
		return this;
	}

	public JptXmlResource getXmlResource() {
		return this.xmlResource;
	}

	public boolean isLatestSupportedVersion() {
		return XmlFile_.isLatestSupportedVersion(this);
	}

	public boolean isIn(IFolder folder) {
		IResource member = folder.findMember(this.xmlResource.getFile().getName());
		IFile file = this.xmlResource.getFile();
		return ObjectTools.equals(member, file);
	}

	public boolean isGenericMappingFile() {
		return XmlFile_.isGenericMappingFile(this);
	}

	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<OrmXml> getType() {
		return OrmXml.class;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		if ((this.root != null) && this.root.containsOffset(textOffset)) {
			return this.root.getStructureNode(textOffset);
		}
		return this;
	}

	// never actually selected
	public TextRange getSelectionTextRange() {
		return TextRange.Empty.instance();
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.root == null) {
			return;
		}
		if (this.getResource().equals(jpaFile.getFile())) {
			rootStructureNodes.add(this.root); 
			return;
		}
		this.root.gatherRootStructureNodes(jpaFile, rootStructureNodes);
	}

	public void dispose() {
		if (this.root != null) {
			this.root.dispose();
		}
	}

	// ********** MappingFile implementation **********

	public Object getResourceMappingFile() {
		return this.xmlResource;
	}

	public OrmPersistentType getPersistentType(String name) {
		return (this.root == null) ? null : this.root.getPersistentType(name);
	}


	// ********** PersistentTypeContainer implementation **********

	/**
	 * All <code>orm.xml</code> mapping files must be able to generate a static metamodel
	 * because 1.0 <code>orm.xml</code> files can be referenced from 2.0
	 * <code>persistence.xml</code>
	 * files.
	 */
	public Iterable<OrmPersistentType> getPersistentTypes() {
		return (this.root != null) ? this.root.getPersistentTypes() : EmptyIterable.<OrmPersistentType>instance();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return (this.root != null) ?
				this.root.createDeleteTypeEdits(type) :
				IterableTools.<DeleteEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.root != null) ?
				this.root.createRenameTypeEdits(originalType, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.root != null) ?
				this.root.createMoveTypeEdits(originalType, newPackage) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.root != null) ?
				this.root.createRenamePackageEdits(originalPackage, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.root != null) {
			this.root.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		// since this is the entire file, point to the top of the file
		return TextRange.Empty.instance();
	}
}
