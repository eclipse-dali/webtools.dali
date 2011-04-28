/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
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
	protected final JpaXmlResource xmlResource;  // never null

	/**
	 * The resouce type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JptResourceType resourceType;

	/**
	 * The root element of the <code>orm.xml</code> file.
	 */
	protected EntityMappings root;


	public GenericOrmXml(MappingFileRef parent, JpaXmlResource xmlResource) {
		super(parent);
		this.checkXmlResource(xmlResource);
		this.xmlResource = xmlResource;
		this.resourceType = xmlResource.getResourceType();

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
				this.unregisterRootStructureNode();
				this.root.dispose();
				this.setRoot(null);
			}
		}

		this.resourceType = newResourceType;

		if (newXmlEntityMappings != null) {
			if (this.root == null) {
				this.setRoot(this.buildRoot(newXmlEntityMappings));
			} else {
				// the context entity mappings already holds the XML entity mappings
				this.root.synchronizeWithResourceModel();
			}
		}
	}

	@Override
	public void update() {
		super.update();
		if (this.root != null) {
			this.root.update();
			// this will happen redundantly - need to hold JpaFile?
			this.registerRootStructureNode();
		}
	}


	// ********** root **********

	public EntityMappings getRoot() {
		return this.root;
	}

	protected void setRoot(EntityMappings root) {
		EntityMappings old = this.root;
		this.root = root;
		this.firePropertyChanged(ROOT_PROPERTY, old, root);
	}

	protected EntityMappings buildRoot(XmlEntityMappings xmlEntityMappings) {
		return this.getContextNodeFactory().buildEntityMappings(this, xmlEntityMappings);
	}


	// ********** misc **********

	protected void checkXmlResource(JpaXmlResource resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		if ( ! resource.getContentType().isKindOf(JptJpaCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
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

	@Override
	public JptResourceType getResourceType() {
		return this.resourceType;
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.xmlResource.getFile());
	}

	public boolean isIn(IFolder folder) {
		IResource member = folder.findMember(this.xmlResource.getFile().getName());
		IFile file = this.xmlResource.getFile();
		return Tools.valuesAreEqual(member, file);
	}

	// ********** JpaStructureNode implementation **********

	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
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

	public void dispose() {
		if (this.root != null) {
			JpaFile jpaFile = this.getJpaFile();
			if (jpaFile != null) {
				this.unregisterRootStructureNode();
			}
			this.root.dispose();
		}
	}

	// TODO hold the JpaFile?
	protected void registerRootStructureNode() {
		this.getJpaFile().addRootStructureNode(this.xmlResource, this.root);
	}

	protected void unregisterRootStructureNode() {
		this.getJpaFile().removeRootStructureNode(this.xmlResource, this.root);
	}


	// ********** MappingFile implementation **********

	public JpaXmlResource getXmlResource() {
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
				EmptyIterable.<DeleteEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.root != null) ?
				this.root.createRenameTypeEdits(originalType, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.root != null) ?
				this.root.createMoveTypeEdits(originalType, newPackage) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.root != null) ?
				this.root.createRenamePackageEdits(originalPackage, newName) :
				EmptyIterable.<ReplaceEdit>instance();
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
