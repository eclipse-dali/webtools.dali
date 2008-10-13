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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * 
 */
public abstract class AbstractJavaRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaAttributeMapping<T>
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
		this.getResourceMapping().setTargetEntity(targetEntity);
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

	protected void setResolvedTargetEntity(Entity entity) {
		Entity old = this.resolvedTargetEntity;
		this.resolvedTargetEntity = entity;
		this.firePropertyChanged(RESOLVED_TARGET_ENTITY_PROPERTY, old, entity);
	}


	// ********** cascade **********

	public JavaCascade getCascade() {
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
		this.getResourceMapping().setFetch(FetchType.toJavaResourceModel(fetch));
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}
	
	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}
	

	// ********** resource => context **********

	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.defaultTargetEntity = this.buildDefaultTargetEntity(jrpa);
		super.initialize(jrpa);
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity(jrpa));
		super.update(jrpa);
	}
	
	@Override
	protected void initialize(T relationshipMappingAnnotation) {
		this.specifiedFetch = this.buildFetch(relationshipMappingAnnotation);
		this.cascade.initialize(relationshipMappingAnnotation);
		this.specifiedTargetEntity = this.buildSpecifiedTargetEntity(relationshipMappingAnnotation);
		this.resolvedTargetEntity = this.buildResolvedTargetEntity(relationshipMappingAnnotation);
	}

	@Override
	protected void update(T relationshipMappingAnnotation) {
		super.update(relationshipMappingAnnotation);
		this.setSpecifiedFetch_(this.buildFetch(relationshipMappingAnnotation));
		this.cascade.update(relationshipMappingAnnotation);
		this.setSpecifiedTargetEntity_(this.buildSpecifiedTargetEntity(relationshipMappingAnnotation));
		this.setResolvedTargetEntity(this.buildResolvedTargetEntity(relationshipMappingAnnotation));
	}
	
	protected FetchType buildFetch(T relationshipMappingAnnotation) {
		return FetchType.fromJavaResourceModel(relationshipMappingAnnotation.getFetch());
	}
	
	protected String buildSpecifiedTargetEntity(T relationshipMappingAnnotation) {
		return relationshipMappingAnnotation.getTargetEntity();
	}
	
	protected abstract String buildDefaultTargetEntity(JavaResourcePersistentAttribute jrpa);
	
	protected Entity buildResolvedTargetEntity(T relationshipMappingAnnotation) {
		String qualifiedTargetEntity = getDefaultTargetEntity();
		if (getSpecifiedTargetEntity() != null) {
			qualifiedTargetEntity = relationshipMappingAnnotation.getFullyQualifiedTargetEntity();
		}
		if (qualifiedTargetEntity == null) {
			return null;
		}
		return getPersistenceUnit().getEntity(qualifiedTargetEntity);
	}


	// ********** RelationshipMapping implementation **********

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this);
	}


	// ********** convenience methods **********

	protected Iterator<String> allTargetEntityAttributeNames() {
		Entity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMappedByAttributeNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateMappedByAttributeNames(), filter);
	}

	protected Iterator<String> javaCandidateMappedByAttributeNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMappedByAttributeNames(filter));
	}

}
