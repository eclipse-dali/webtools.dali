/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class AbstractJavaRelationshipMapping<T extends RelationshipMappingAnnotation> extends AbstractJavaAttributeMapping<T>
	implements JavaRelationshipMapping
{

	protected String specifiedTargetEntity;

	protected String defaultTargetEntity;

	protected Entity resolvedTargetEntity;

	protected final JavaCascade cascade;
	
	protected FetchType specifiedFetch;
	
	protected AbstractJavaRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.cascade = new JavaCascade(this);
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
		this.getMappingResource().setTargetEntity(newSpecifiedTargetEntity);
		firePropertyChanged(RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY, oldSpecifiedTargetEntity, newSpecifiedTargetEntity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		firePropertyChanged(RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY, oldDefaultTargetEntity, newDefaultTargetEntity);
	}

	public Entity getResolvedTargetEntity() {
		return this.resolvedTargetEntity;
	}

	protected void setResolvedTargetEntity(Entity newResolvedTargetEntity) {
		Entity oldResolvedTargetEntity = this.resolvedTargetEntity;
		this.resolvedTargetEntity = newResolvedTargetEntity;
		firePropertyChanged(RelationshipMapping.RESOLVED_TARGET_ENTITY_PROPERTY, oldResolvedTargetEntity, newResolvedTargetEntity);
	}

	public JavaCascade getCascade() {
		return this.cascade;
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
		this.getMappingResource().setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		this.defaultTargetEntity = this.defaultTargetEntity(resourcePersistentAttribute);
		super.initializeFromResource(resourcePersistentAttribute);
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		this.setDefaultTargetEntity(this.defaultTargetEntity(resourcePersistentAttribute));
		super.update(resourcePersistentAttribute);
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		this.specifiedFetch = this.fetch(relationshipMapping);
		this.cascade.initialize(relationshipMapping);
		this.specifiedTargetEntity = this.specifiedTargetEntity(relationshipMapping);
		this.resolvedTargetEntity = this.resolveTargetEntity(relationshipMapping);
	}

	@Override
	protected void update(T relationshipMapping) {
		this.setSpecifiedFetch(this.fetch(relationshipMapping));
		this.cascade.update(relationshipMapping);
		this.setSpecifiedTargetEntity(this.specifiedTargetEntity(relationshipMapping));
		this.setResolvedTargetEntity(this.resolveTargetEntity(relationshipMapping));
	}
	
	protected FetchType fetch(T relationshipMapping) {
		return FetchType.fromJavaResourceModel(relationshipMapping.getFetch());
	}
	
	protected String specifiedTargetEntity(T relationshipMapping) {
		return relationshipMapping.getTargetEntity();
	}
	
	protected abstract String defaultTargetEntity(JavaResourcePersistentAttribute resourcePersistentAttribute);
	
	protected Entity resolveTargetEntity(T relationshipMapping) {
		String qualifiedTargetEntity = getDefaultTargetEntity();
		if (getSpecifiedTargetEntity() != null) {
			qualifiedTargetEntity = relationshipMapping.getFullyQualifiedTargetEntity();
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

	public Entity getEntity() {
		if (getTypeMapping() instanceof Entity) {
			return (Entity) getTypeMapping();
		}
		return null;
	}

	public Iterator<String> allTargetEntityAttributeNames() {
		Entity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMappedByAttributeNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateMappedByAttributeNames(), filter);
	}

	protected Iterator<String> quotedCandidateMappedByAttributeNames(Filter<String> filter) {
		return StringTools.quote(this.candidateMappedByAttributeNames(filter));
	}


	// ********** static methods **********

	public boolean targetEntityIsValid(String targetEntity) {
		return MappingTools.targetEntityIsValid(targetEntity);
	}
}
