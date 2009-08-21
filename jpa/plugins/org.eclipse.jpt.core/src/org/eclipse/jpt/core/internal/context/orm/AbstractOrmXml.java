/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmXml
	extends AbstractXmlContextNode
	implements OrmXml
{
	protected JpaXmlResource xmlResource;
	
	protected EntityMappings entityMappings;
	
	
	public AbstractOrmXml(MappingFileRef parent, JpaXmlResource resource) {
		super(parent);
		if (!resource.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have mapping file content type"); //$NON-NLS-1$
		}
		this.xmlResource = resource;
		XmlEntityMappings xmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();
		if (xmlEntityMappings != null) {
			this.entityMappings = this.buildEntityMappings(xmlEntityMappings);
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
	public MappingFileRoot getMappingFileRoot() {
		return getEntityMappings();
	}
	
	
	// ********** JpaStructureNode implementation **********
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}

	public IContentType getContentType() {
		return this.xmlResource.getContentType();
	}
	
	
	// ********** MappingFile implementation **********
	
	public JpaXmlResource getXmlResource() {
		return this.xmlResource;
	}
	
	public MappingFileRoot getRoot() {
		return getEntityMappings();
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistentType(fullyQualifiedTypeName);
	}

	/**
	 * All orm.xml mapping files must be able to generate a static metamodel
	 * because 1.0 orm.xml files can be referenced from 2.0 persistence.xml files.
	 */
	public void synchronizeStaticMetaModel() {
		this.getEntityMappings().synchronizeStaticMetaModel();
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

	public EntityMappings addEntityMappings() {
		if (this.entityMappings != null) {
			throw new IllegalStateException();
		}
		
		XmlEntityMappings xmlEntityMappings = this.buildEntityMappingsResource();
		this.entityMappings = this.buildEntityMappings(xmlEntityMappings);
		this.xmlResource.getContents().add(xmlEntityMappings);
		this.firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, null, this.entityMappings);
		return this.entityMappings;
	}
	
	protected abstract XmlEntityMappings buildEntityMappingsResource();
	
	public void removeEntityMappings() {
		if (this.entityMappings == null) {
			throw new IllegalStateException();
		}
		this.getJpaFile(this.xmlResource.getFile()).removeRootStructureNode(this.xmlResource);
		this.entityMappings.dispose();
		EntityMappings old = this.entityMappings;
		
		this.entityMappings = null;
		EObject xmlEntityMappings = this.xmlResource.getRootObject(); //TODO helper removeEntityMappings method on ormResource??
		this.xmlResource.getContents().remove(xmlEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, old, null);
	}
	
	
	// ********** updating **********
	
	public void update(JpaXmlResource resource) {		
		if (!resource.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE)) {
			throw new IllegalArgumentException(resource + " does not have mapping file content type"); //$NON-NLS-1$
		}
		this.xmlResource = resource;
		this.update();
	}
	
	protected void update() {		
		XmlEntityMappings oldXmlEntityMappings = 
			(this.entityMappings == null) ? null : this.entityMappings.getXmlEntityMappings();
		XmlEntityMappings newXmlEntityMappings = (XmlEntityMappings) this.xmlResource.getRootObject();

		// if the old and new xml entity mappings are different instances,
		// we scrap the old and rebuild.  this can happen when the resource
		// model drastically changes, such as a cvs checkout or an edit reversion
		if (oldXmlEntityMappings != newXmlEntityMappings) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.xmlResource.getFile()).removeRootStructureNode(this.xmlResource);
				this.entityMappings.dispose();
				this.setEntityMappings(null);
			}
		}
		
		if (newXmlEntityMappings != null) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.xmlResource.getFile()).addRootStructureNode(this.xmlResource, this.entityMappings);
				this.entityMappings.update();
			} else {
				this.setEntityMappings(this.buildEntityMappings(newXmlEntityMappings));
			}
		}
	}
	
	protected abstract EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings);
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.entityMappings != null) {
			this.entityMappings.postUpdate();
		}
	}
	
	
	// ********** text **********
	
	public JpaStructureNode getStructureNode(int textOffset) {
		if (this.entityMappings.containsOffset(textOffset)) {
			return this.entityMappings.getStructureNode(textOffset);
		}
		return this;
	}
	
	// never actually selected
	public TextRange getSelectionTextRange() {
		return TextRange.Empty.instance();
	}
	
	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance();
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.entityMappings != null) {
			this.entityMappings.validate(messages, reporter);
		}
	}
	
	
	// ********** dispose **********
	
	public void dispose() {
		if (this.entityMappings != null) {
			this.entityMappings.dispose();
		}
	}

}
