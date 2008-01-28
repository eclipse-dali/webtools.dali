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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.RelationshipMappingTools;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.RelationshipMapping;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class JavaRelationshipMapping<T extends RelationshipMapping> extends JavaAttributeMapping
	implements IJavaRelationshipMapping
{

	protected String specifiedTargetEntity;

	protected String defaultTargetEntity;

	protected IEntity resolvedTargetEntity;

	protected final JavaCascade cascade;
	
	protected FetchType specifiedFetch;
	
	protected JavaRelationshipMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.cascade = new JavaCascade(this);
	}

	protected abstract T relationshipMapping();
	
	public String getTargetEntity() {
		return (this.getSpecifiedTargetEntity() == null) ? getDefaultTargetEntity() : this.getSpecifiedTargetEntity();
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String newSpecifiedTargetEntity) {
		String oldSpecifiedTargetEntity = this.specifiedTargetEntity;
		this.specifiedTargetEntity = newSpecifiedTargetEntity;
		this.relationshipMapping().setTargetEntity(newSpecifiedTargetEntity);
		firePropertyChanged(IRelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY, oldSpecifiedTargetEntity, newSpecifiedTargetEntity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		firePropertyChanged(IRelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY, oldDefaultTargetEntity, newDefaultTargetEntity);
	}

	public IEntity getResolvedTargetEntity() {
		return this.resolvedTargetEntity;
	}

	protected void setResolvedTargetEntity(IEntity newResolvedTargetEntity) {
		IEntity oldResolvedTargetEntity = this.resolvedTargetEntity;
		this.resolvedTargetEntity = newResolvedTargetEntity;
		firePropertyChanged(IRelationshipMapping.RESOLVED_TARGET_ENTITY_PROPERTY, oldResolvedTargetEntity, newResolvedTargetEntity);
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
		this.relationshipMapping().setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.defaultTargetEntity = this.defaultTargetEntity(persistentAttributeResource);
		initialize(this.relationshipMapping());
	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.setDefaultTargetEntity(this.defaultTargetEntity(persistentAttributeResource));
		this.update(this.relationshipMapping());
	}
	
	protected void initialize(T relationshipMapping) {
		this.specifiedFetch = this.fetch(relationshipMapping);
		this.cascade.initialize(relationshipMapping);
		this.specifiedTargetEntity = this.specifiedTargetEntity(relationshipMapping);
		this.resolvedTargetEntity = this.resolveTargetEntity(relationshipMapping);
	}

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
	
	protected abstract String defaultTargetEntity(JavaPersistentAttributeResource persistentAttributeResource);
	
	protected IEntity resolveTargetEntity(T relationshipMapping) {
		String qualifiedTargetEntity = getDefaultTargetEntity();
		if (getSpecifiedTargetEntity() != null) {
			qualifiedTargetEntity = relationshipMapping.getFullyQualifiedTargetEntity();
		}
		if (qualifiedTargetEntity == null) {
			return null;
		}
		IPersistentType persistentType = persistenceUnit().persistentType(qualifiedTargetEntity);
		if (persistentType != null && persistentType.mappingKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (IEntity) persistentType.getMapping();
		}
		return null;
	}

	public IEntity getEntity() {
		if (typeMapping() instanceof IEntity) {
			return (IEntity) typeMapping();
		}
		return null;
	}

	public Iterator<String> allTargetEntityAttributeNames() {
		IEntity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.persistentType().allAttributeNames();
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
		return RelationshipMappingTools.targetEntityIsValid(targetEntity);
	}
}
