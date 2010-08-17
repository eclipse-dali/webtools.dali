/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
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
	 * ref will dispose its current mapping file and build a new one.
	 */
	protected final JpaXmlResource xmlResource;
	
	/**
	 * The resouce type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JpaResourceType resourceType;

	protected EntityMappings entityMappings;


	public GenericOrmXml(MappingFileRef parent, JpaXmlResource xmlResource) {
		super(parent);
		this.checkXmlResource(xmlResource);
		this.xmlResource = xmlResource;
		this.resourceType = xmlResource.getResourceType();

		XmlEntityMappings xmlEntityMappings = (XmlEntityMappings) xmlResource.getRootObject();
		if (xmlEntityMappings != null) {
			this.entityMappings = this.buildEntityMappings(xmlEntityMappings);
		}
	}

	protected void checkXmlResource(JpaXmlResource resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		if ( ! resource.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
			throw new IllegalArgumentException("Content type is not 'mapping file': " + resource); //$NON-NLS-1$
		}
	}

	// ********** overrides **********

	@Override
	public MappingFileRef getParent() {
		return (MappingFileRef) super.getParent();
	}

	@Override
	public IResource getResource() {
		return this.xmlResource.getFile();
	}

	@Override
	public JpaResourceType getResourceType() {
		return this.resourceType;
	}

	// ********** JpaStructureNode implementation **********

	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		if ((this.entityMappings != null) && this.entityMappings.containsOffset(textOffset)) {
			return this.entityMappings.getStructureNode(textOffset);
		}
		return this;
	}

	// never actually selected
	public TextRange getSelectionTextRange() {
		return TextRange.Empty.instance();
	}

	public void dispose() {
		if (this.entityMappings != null) {
			this.entityMappings.dispose();
		}
		JpaFile jpaFile = getJpaFile();
		if (jpaFile != null) {
			jpaFile.removeRootStructureNode(this.xmlResource);
		}
	}

	// ********** MappingFile implementation **********

	public JpaXmlResource getXmlResource() {
		return this.xmlResource;
	}

	public MappingFileRoot getRoot() {
		return this.entityMappings;
	}

	public OrmPersistentType getPersistentType(String name) {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistentType(name);
	}

	// ********** PersistentTypeContainer implementation **********

	/**
	 * All orm.xml mapping files must be able to generate a static metamodel
	 * because 1.0 orm.xml files can be referenced from 2.0 persistence.xml
	 * files.
	 */
	public Iterable<OrmPersistentType> getPersistentTypes() {
		return (this.entityMappings != null) ? this.entityMappings.getPersistentTypes() : EmptyIterable.<OrmPersistentType> instance();
	}

	// ********** entity mappings **********

	public EntityMappings getEntityMappings() {
		return this.entityMappings;
	}

	protected void setEntityMappings(EntityMappings entityMappings) {
		EntityMappings old = this.entityMappings;
		this.entityMappings = entityMappings;
		this.firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, old, entityMappings);
	}

	protected EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings) {
		return this.getXmlContextNodeFactory().buildEntityMappings(this, xmlEntityMappings);
	}

	// ********** updating **********

	public void update() {
		XmlEntityMappings oldXmlEntityMappings = (this.entityMappings == null) ? null : this.entityMappings.getXmlEntityMappings();
		XmlEntityMappings newXmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();
		JpaResourceType newResourceType = this.xmlResource.getResourceType();
		
		// If the old and new xml entity mappings are different instances,
		// we scrap the old context entity mappings and rebuild. This can
		// happen when the resource model drastically changes, such as
		// a cvs checkout or an edit reversion.
		if ((oldXmlEntityMappings != newXmlEntityMappings)
				|| (newXmlEntityMappings == null)
				|| this.valuesAreDifferent(this.resourceType, newResourceType)) {
			
			if (this.entityMappings != null) {
				getJpaFile().removeRootStructureNode(this.xmlResource);
				this.entityMappings.dispose();
				setEntityMappings(null);
			}
		}
		
		this.resourceType = newResourceType;
		
		if (newXmlEntityMappings != null) {
			if (this.entityMappings != null) {
				this.entityMappings.update();
			}
			else {
				setEntityMappings(buildEntityMappings(newXmlEntityMappings));
			}
			
			this.getJpaFile().addRootStructureNode(this.xmlResource, this.entityMappings);
		}
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.xmlResource.getFile());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.entityMappings != null) {
			this.entityMappings.postUpdate();
		}
	}


	// ********** misc **********

	public boolean isIn(IFolder folder) {
		IResource member = folder.findMember(this.getXmlResource().getFile().getName());
		IFile file = this.getXmlResource().getFile();
		return member != null && file != null && member.equals(file);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.entityMappings != null) {
			this.entityMappings.validate(messages, reporter);
		}
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		if (this.entityMappings != null) {
			return this.entityMappings.createDeleteTypeEdits(type);
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		if (this.entityMappings != null) {
			return this.entityMappings.createRenameTypeEdits(originalType, newName);
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.entityMappings != null) {
			return this.entityMappings.createMoveTypeEdits(originalType, newPackage);
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.entityMappings != null) {
			return this.entityMappings.createRenamePackageEdits(originalPackage, newName);
		}
		return EmptyIterable.instance();
	}
}
