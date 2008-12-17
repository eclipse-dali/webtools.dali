/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmXml
	extends AbstractXmlContextNode
	implements OrmXml
{
	protected OrmResource ormResource;
	
	protected EntityMappings entityMappings;
	
	
	public GenericOrmXml(MappingFileRef parent, OrmResource ormResource) {
		super(parent);
		this.initialize(ormResource);
	}
	
	protected void initialize(OrmResource resource) {
		this.ormResource = resource;
		XmlEntityMappings xmlEntityMappings = resource.getEntityMappings();
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
		return this.ormResource.getFile();
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

	public String getType() {
		return this.ormResource.getType();
	}
	
	
	// ********** MappingFile implementation **********
	
	public JpaXmlResource getXmlResource() {
		return this.ormResource;
	}
	
	public MappingFileRoot getRoot() {
		return getEntityMappings();
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistentType(fullyQualifiedTypeName);
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
		this.ormResource.getContents().add(xmlEntityMappings);
		this.firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, null, this.entityMappings);
		return this.entityMappings;
	}
	
	protected XmlEntityMappings buildEntityMappingsResource() {
		return OrmFactory.eINSTANCE.createXmlEntityMappings();
	}
	
	public void removeEntityMappings() {
		if (this.entityMappings == null) {
			throw new IllegalStateException();
		}
		this.getJpaFile(this.ormResource.getFile()).removeRootStructureNode(this.ormResource);
		this.entityMappings.dispose();
		EntityMappings old = this.entityMappings;
		
		this.entityMappings = null;
		XmlEntityMappings xmlEntityMappings = this.ormResource.getEntityMappings(); //TODO helper removeEntityMappings method on ormResource??
		this.ormResource.getContents().remove(xmlEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, old, null);
	}
	
	
	// ********** updating **********
	
	public void update(JpaXmlResource resource) {
		OrmResource newOrmResource;
		try {
			newOrmResource = (OrmResource) resource;
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException(resource.toString(), ex);
		}
		
		XmlEntityMappings oldXmlEntityMappings = 
			(this.entityMappings == null) ? null : this.entityMappings.getXmlEntityMappings();
		XmlEntityMappings newXmlEntityMappings = newOrmResource.getEntityMappings();
		
		this.ormResource = newOrmResource;

		// if the old and new xml entity mappings are different instances,
		// we scrap the old and rebuild.  this can happen when the resource
		// model drastically changes, such as a cvs checkout or an edit reversion
		if (oldXmlEntityMappings != newXmlEntityMappings) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.ormResource.getFile()).removeRootStructureNode(this.ormResource);
				this.entityMappings.dispose();
				this.setEntityMappings(null);
			}
		}
		
		if (newXmlEntityMappings != null) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.ormResource.getFile()).addRootStructureNode(this.ormResource, this.entityMappings);
				this.entityMappings.update();
			} else {
				this.setEntityMappings(this.buildEntityMappings(newXmlEntityMappings));
			}
		} else {
			if (this.entityMappings != null) {
				this.getJpaFile(this.ormResource.getFile()).removeRootStructureNode(this.ormResource);
				this.entityMappings.dispose();
			}
			this.setEntityMappings(null);
		}
	}
	
	protected EntityMappings buildEntityMappings(XmlEntityMappings xmlEntityMappings) {
		return getJpaFactory().buildEntityMappings(this, xmlEntityMappings);
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
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (this.entityMappings != null) {
			this.entityMappings.validate(messages);
		}
	}
	
	
	// ********** dispose **********
	
	public void dispose() {
		if (this.entityMappings != null) {
			this.entityMappings.dispose();
		}
	}

}
