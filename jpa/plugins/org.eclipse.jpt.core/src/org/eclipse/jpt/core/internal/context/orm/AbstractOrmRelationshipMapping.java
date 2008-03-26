/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class AbstractOrmRelationshipMapping<T extends XmlRelationshipMapping> extends AbstractOrmAttributeMapping<T>
	implements OrmRelationshipMapping
{
	
	protected String specifiedTargetEntity;

	protected String defaultTargetEntity;

	protected Entity resolvedTargetEntity;
	
	protected FetchType specifiedFetch;
	
	protected final OrmCascade cascade;

	
	protected AbstractOrmRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.cascade = new OrmCascade(this);
	}

	public String getTargetEntity() {
		return (this.getSpecifiedTargetEntity() == null) ? getDefaultTargetEntity() : this.getSpecifiedTargetEntity();
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String newSpecifiedTargetEntity) {
		String oldSpecifiedTargetEntity = this.specifiedTargetEntity;
		this.specifiedTargetEntity = newSpecifiedTargetEntity;
		getAttributeMapping().setTargetEntity(newSpecifiedTargetEntity);
		firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, oldSpecifiedTargetEntity, newSpecifiedTargetEntity);
	}

	protected void setSpecifiedTargetEntity_(String newSpecifiedTargetEntity) {
		String oldSpecifiedTargetEntity = this.specifiedTargetEntity;
		this.specifiedTargetEntity = newSpecifiedTargetEntity;
		firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, oldSpecifiedTargetEntity, newSpecifiedTargetEntity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, oldDefaultTargetEntity, newDefaultTargetEntity);
	}

	public Entity getResolvedTargetEntity() {
		return this.resolvedTargetEntity;
	}

	protected void setResolvedTargetEntity(Entity newResolvedTargetEntity) {
		Entity oldResolvedTargetEntity = this.resolvedTargetEntity;
		this.resolvedTargetEntity = newResolvedTargetEntity;
		firePropertyChanged(RESOLVED_TARGET_ENTITY_PROPERTY, oldResolvedTargetEntity, newResolvedTargetEntity);
	}

	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.getAttributeMapping().setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public OrmCascade getCascade() {
		return this.cascade;
	}


	@Override
	public void initializeFromXmlRelationshipMapping(OrmRelationshipMapping oldMapping) {
		super.initializeFromXmlRelationshipMapping(oldMapping);
		setSpecifiedTargetEntity(oldMapping.getSpecifiedTargetEntity());
		setSpecifiedFetch(oldMapping.getSpecifiedFetch());
		getCascade().initializeFrom(oldMapping.getCascade());
		//TODO should we set the fetch type from a BasicMapping??
	}

	public Iterator<String> allTargetEntityAttributeNames() {
		Entity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}
		
	public Entity getEntity() {
		if (getTypeMapping() instanceof Entity) {
			return (Entity) getTypeMapping();
		}
		return null;
	}
	
	public boolean targetEntityIsValid(String targetEntity) {
		return MappingTools.targetEntityIsValid(targetEntity);
	}
	
	@Override
	public void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedTargetEntity = relationshipMapping.getTargetEntity();
		this.defaultTargetEntity = this.defaultTargetEntity();
		this.resolvedTargetEntity = this.resolveTargetEntity();
		this.specifiedFetch = this.specifiedFetch(relationshipMapping);
		this.cascade.initialize(relationshipMapping);
	}
	
	@Override
	public void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedTargetEntity_(relationshipMapping.getTargetEntity());
		this.setDefaultTargetEntity(this.defaultTargetEntity());
		this.setResolvedTargetEntity(this.resolveTargetEntity());
		this.setSpecifiedFetch_(this.specifiedFetch(relationshipMapping));
		this.cascade.update(relationshipMapping);
	}
	
	protected FetchType specifiedFetch(XmlRelationshipMapping relationshipMapping) {
		return FetchType.fromOrmResourceModel(relationshipMapping.getFetch());
	}
	
	protected RelationshipMapping javaRelationshipMapping() {
		if (findJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = findJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping instanceof RelationshipMapping) {
			return ((RelationshipMapping) javaAttributeMapping);
		}
		return null;
	}
	
	protected String defaultTargetEntity() {
		RelationshipMapping javaMapping = javaRelationshipMapping();
		if (javaMapping != null) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				return javaMapping.getTargetEntity();
			}
		}
		if (findJavaPersistentAttribute() != null) {
			return defaultTargetEntity(findJavaPersistentAttribute().getResourcePersistentAttribute());
		}
		return null;
	}
	
	protected abstract String defaultTargetEntity(JavaResourcePersistentAttribute persistentAttributeResource);

	protected Entity resolveTargetEntity() {
		String qualifiedTargetEntity = getDefaultTargetEntity();
		if (getSpecifiedTargetEntity() != null) {
			qualifiedTargetEntity = fullyQualifiedTargetEntity();
		}
		if (qualifiedTargetEntity == null) {
			return null;
		}
		PersistentType persistentType = getPersistenceUnit().getPersistentType(qualifiedTargetEntity);
		if (persistentType != null && persistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (Entity) persistentType.getMapping();
		}
		return null;
	}
	
	protected String fullyQualifiedTargetEntity() {
		if (getTargetEntity() == null) {
			return null;
		}
		if (targetEntityIncludesPackage()) {
			return getTargetEntity();
		}
		String package_ = getEntityMappings().getPackage();
		if (package_ != null) {
			return package_ + '.' + getTargetEntity();
		}
		return getTargetEntity();
	}

	private boolean targetEntityIncludesPackage() {
		return getTargetEntity().lastIndexOf('.') != -1;
	}
}
