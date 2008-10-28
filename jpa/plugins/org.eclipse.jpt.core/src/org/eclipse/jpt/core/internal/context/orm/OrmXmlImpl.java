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
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
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

public class OrmXmlImpl
	extends AbstractXmlContextNode
	implements OrmXml
{
	protected OrmResource ormResource;
	
	protected EntityMappings entityMappings;
	
	
	public OrmXmlImpl(MappingFileRef parent, OrmResource ormResource) {
		super(parent);
		this.initialize(ormResource);
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public MappingFileRef getParent() {
		return (MappingFileRef) super.getParent();
	}
	
	@Override
	public IResource getResource() {
		return this.ormResource.getFile();
	}
	
	
	// **************** JpaContextNode impl ************************************
	
	@Override
	public MappingFileRoot getMappingFileRoot() {
		// TODO Auto-generated method stub
		return getEntityMappings();
	}
	
	
	// **************** XmlContextNode impl ************************************
	
	@Override
	public JpaXmlResource getEResource() {
		return this.ormResource;
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}
	
	
	// **************** MappingFile impl ***************************************
	
	public JpaXmlResource getXmlResource() {
		return ormResource;
	}
	
	public MappingFileRoot getRoot() {
		return getEntityMappings();
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistentType(fullyQualifiedTypeName);
	}
	
	
	
	// **************** persistence ********************************************
	
	public EntityMappings getEntityMappings() {
		return this.entityMappings;
	}
	
	protected void setEntityMappings(EntityMappings newEntityMappings) {
		EntityMappings oldEntityMappings = this.entityMappings;
		this.entityMappings = newEntityMappings;
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, oldEntityMappings, newEntityMappings);
	}

	public EntityMappings addEntityMappings() {
		if (this.entityMappings != null) {
			throw new IllegalStateException();
		}
		
		XmlEntityMappings xmlEntityMappings = buildEntityMappingsResource();
		this.entityMappings = buildEntityMappings(xmlEntityMappings);
		this.ormResource.getContents().add(xmlEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, null, this.entityMappings);
		return this.entityMappings;
	}
	
	protected XmlEntityMappings buildEntityMappingsResource() {
		return OrmFactory.eINSTANCE.createXmlEntityMappings();
	}
	
	public void removeEntityMappings() {
		if (this.entityMappings == null) {
			throw new IllegalStateException();
		}
		getJpaFile(this.ormResource.getFile()).removeRootStructureNode(this.ormResource);
		this.entityMappings.dispose();
		EntityMappings oldEntityMappings = this.entityMappings;
		
		this.entityMappings = null;
		XmlEntityMappings xmlEntityMappings = this.ormResource.getEntityMappings(); //TODO helper removeEntityMappings method on ormResource??
		this.ormResource.getContents().remove(xmlEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, oldEntityMappings, null);
	}
	
	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return (this.entityMappings == null) ? null : this.entityMappings.getPersistenceUnitDefaults();
	}
	
	
	// **************** updating ***********************************************
	
	protected void initialize(OrmResource resource) {
		this.ormResource = resource;
		XmlEntityMappings xmlEntityMappings = resource.getEntityMappings();
		if (xmlEntityMappings != null) {
			this.entityMappings = buildEntityMappings(xmlEntityMappings);
		}
	}

	public void update(JpaXmlResource resource) {
		try {
			this.ormResource = (OrmResource) resource;
		} catch (ClassCastException cce) {
			throw new IllegalArgumentException(resource.toString());
		}
		XmlEntityMappings xmlEntityMappings = this.ormResource.getEntityMappings();
		if (xmlEntityMappings != null) {
			if (this.entityMappings != null) {
				this.getJpaFile(this.ormResource.getFile()).addRootStructureNode(this.ormResource, this.entityMappings);
				this.entityMappings.update();
			} else {
				this.setEntityMappings(this.buildEntityMappings(xmlEntityMappings));
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
	
	
	// *************************************************************************
	
	public JpaStructureNode getStructureNode(int textOffset) {
		if (entityMappings.containsOffset(textOffset)) {
			return entityMappings.getStructureNode(textOffset);
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
	
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (this.entityMappings != null) {
			this.entityMappings.validate(messages);
		}
	}
	
	public void dispose() {
		if (this.entityMappings != null) {
			this.entityMappings.dispose();
		}
	}

}
