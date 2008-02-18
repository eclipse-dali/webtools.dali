/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.persistence.IMappingFileRef;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;

public class OrmXmlImpl extends JpaContextNode
	implements OrmXml
{
	protected OrmResource ormResource;
	
	protected EntityMappings entityMappings;
	
	
	public OrmXmlImpl(IMappingFileRef parent) {
		super(parent);
	}
	
	public String getId() {
		// isn't actually displayed, so needs no details page
		return null;
	}
	
	public XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName) {
		if (getEntityMappings() != null) {
			return getEntityMappings().persistentTypeFor(fullyQualifiedTypeName);
		}
		return null;
	}
	
	@Override
	public IResource resource() {
		return ormResource.getFile();
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
		
		org.eclipse.jpt.core.internal.resource.orm.EntityMappings ormEntityMappings = OrmFactory.eINSTANCE.createEntityMappings();
		this.entityMappings = createEntityMappings(ormEntityMappings);
		this.ormResource.getContents().add(ormEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, null, this.entityMappings);
		return this.entityMappings;
	}
	
	public void removeEntityMappings() {
		if (this.entityMappings == null) {
			throw new IllegalStateException();
		}
		
		EntityMappings oldEntityMappings = this.entityMappings;
		this.entityMappings = null;
		org.eclipse.jpt.core.internal.resource.orm.EntityMappings ormEntityMappings = this.ormResource.getEntityMappings(); //TODO helper removeEntityMappings method on ormResource??
		this.ormResource.getContents().remove(ormEntityMappings);
		firePropertyChanged(ENTITY_MAPPINGS_PROPERTY, oldEntityMappings, null);
	}
	
	public PersistenceUnitDefaults persistenceUnitDefaults() {
		if (getEntityMappings() != null) {
			return getEntityMappings().persistenceUnitDefaults();
		}
		return null;
	}
	// **************** updating ***********************************************
	
	public void initialize(OrmResource ormResource) {
		this.ormResource = ormResource;
		if (ormResource.getEntityMappings() != null) {
			this.entityMappings = createEntityMappings(ormResource.getEntityMappings());
		}
	}

	public void update(OrmResource ormResource) {
		this.ormResource = ormResource;
		if (ormResource.getEntityMappings() != null) {
			if (this.entityMappings != null) {
				this.entityMappings.update(ormResource.getEntityMappings());
			}
			else {
				setEntityMappings(createEntityMappings(ormResource.getEntityMappings()));
			}
			ormResource.resourceModel().addRootStructureNode(getEntityMappings());
		}
		else {
			ormResource.resourceModel().removeRootStructureNode(getEntityMappings());
			setEntityMappings(null);
		}
	}
	
	protected EntityMappings createEntityMappings(org.eclipse.jpt.core.internal.resource.orm.EntityMappings ormEntityMappings) {
		EntityMappings entityMappings = jpaFactory().createEntityMappings(this);
		entityMappings.initialize(ormEntityMappings);
		return entityMappings;
	}
	
	
	// *************************************************************************
	
	public IJpaStructureNode structureNode(int textOffset) {
		if (entityMappings.containsOffset(textOffset)) {
			return entityMappings.structureNode(textOffset);
		}
		return this;
	}
	
	// never actually selected
	public ITextRange selectionTextRange() {
		return ITextRange.Empty.instance();
	}
	
	public ITextRange validationTextRange() {
		return ITextRange.Empty.instance();
	}
}
