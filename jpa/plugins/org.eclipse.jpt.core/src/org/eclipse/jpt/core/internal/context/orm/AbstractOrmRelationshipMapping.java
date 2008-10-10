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
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class AbstractOrmRelationshipMapping<T extends XmlRelationshipMapping>
	extends AbstractOrmAttributeMapping<T>
	implements OrmRelationshipMapping
{
	
	protected String specifiedTargetEntity;
	protected String defaultTargetEntity;
	protected Entity resolvedTargetEntity;
	
	protected final OrmCascade cascade;

	protected FetchType specifiedFetch;
	
	
	protected AbstractOrmRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.cascade = new OrmCascade(this);
	}


	// ********** target entity **********

	public String getTargetEntity() {
		return (this.specifiedTargetEntity != null) ? this.specifiedTargetEntity : this.defaultTargetEntity;
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String targetEntity) {
		String old = this.specifiedTargetEntity;
		this.specifiedTargetEntity = targetEntity;
		this.getAttributeMapping().setTargetEntity(targetEntity);
		this.firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, old, targetEntity);
	}

	protected void setSpecifiedTargetEntity_(String targetEntity) {
		String old = this.specifiedTargetEntity;
		this.specifiedTargetEntity = targetEntity;
		this.firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, old, targetEntity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String targetEntity) {
		String old = this.defaultTargetEntity;
		this.defaultTargetEntity = targetEntity;
		this.firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, old, targetEntity);
	}

	public Entity getResolvedTargetEntity() {
		return this.resolvedTargetEntity;
	}

	protected void setResolvedTargetEntity(Entity targetEntity) {
		Entity old = this.resolvedTargetEntity;
		this.resolvedTargetEntity = targetEntity;
		this.firePropertyChanged(RESOLVED_TARGET_ENTITY_PROPERTY, old, targetEntity);
	}


	// ********** cascade **********

	public OrmCascade getCascade() {
		return this.cascade;
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.getDefaultFetch();
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.getAttributeMapping().setFetch(FetchType.toOrmResourceModel(fetch));
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}


	// ********** resource => context **********

	@Override
	public void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedTargetEntity = relationshipMapping.getTargetEntity();
		this.defaultTargetEntity = this.buildDefaultTargetEntity();
		this.resolvedTargetEntity = this.buildResolvedTargetEntity();
		this.specifiedFetch = this.buildSpecifiedFetch(relationshipMapping);
		this.cascade.initialize(relationshipMapping);
	}
	
	@Override
	public void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedTargetEntity_(relationshipMapping.getTargetEntity());
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity());
		this.setResolvedTargetEntity(this.buildResolvedTargetEntity());
		this.setSpecifiedFetch_(this.buildSpecifiedFetch(relationshipMapping));
		this.cascade.update(relationshipMapping);
	}
	
	protected FetchType buildSpecifiedFetch(XmlRelationshipMapping relationshipMapping) {
		return FetchType.fromOrmResourceModel(relationshipMapping.getFetch());
	}
	
	protected String buildDefaultTargetEntity() {
		RelationshipMapping javaMapping = getJavaRelationshipMapping();
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
	
	protected RelationshipMapping getJavaRelationshipMapping() {
		if (findJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = findJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping instanceof RelationshipMapping) {
			return ((RelationshipMapping) javaAttributeMapping);
		}
		return null;
	}
	
	protected abstract String defaultTargetEntity(JavaResourcePersistentAttribute persistentAttributeResource);

	protected Entity buildResolvedTargetEntity() {
		if (getTargetEntity() == null) {
			return null;
		}
		PersistentType persistentType = getTargetPersistentType();
		if (persistentType != null && persistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (Entity) persistentType.getMapping();
		}
		return null;
	}
	
	protected PersistentType getTargetPersistentType() {
		// try to resolve by only the locally specified name
		PersistentType persistentType = getPersistenceUnit().getPersistentType(getTargetEntity());
		if (persistentType == null) {
			// try to resolve by prepending the global package name
			String packageName = 
				getPersistentAttribute().getPersistentType().getContext().getDefaultPersistentTypePackage();
			persistentType = getPersistenceUnit().getPersistentType(packageName + '.' + getTargetEntity());
		}
		return persistentType;
	}


	// ********** RelationshipMapping implementation **********

	public Entity getEntity() {
		if (getTypeMapping() instanceof Entity) {
			return (Entity) getTypeMapping();
		}
		return null;
	}
	
	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this);
	}

	@Override
	public void initializeFromOrmRelationshipMapping(OrmRelationshipMapping oldMapping) {
		super.initializeFromOrmRelationshipMapping(oldMapping);
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
		
}
