/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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
	
	@Override
	public JavaPersistentAttribute getParent() {
		return (JavaPersistentAttribute) super.getParent();
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
		this.resourceMapping.setTargetEntity(targetEntity);
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
		this.resourceMapping.setFetch(FetchType.toJavaResourceModel(fetch));
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}
	
	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}
	

	// ********** resource => context **********

	@Override
	protected void initialize() {
		super.initialize();
		this.defaultTargetEntity = this.buildDefaultTargetEntity();
		this.specifiedFetch = this.getResourceFetch();
		this.cascade.initialize(this.resourceMapping);
		this.specifiedTargetEntity = this.getResourceTargetEntity();
		this.resolvedTargetEntity = this.buildResolvedTargetEntity();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity());
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.cascade.update(this.resourceMapping);
		this.setSpecifiedTargetEntity_(this.getResourceTargetEntity());
		this.setResolvedTargetEntity(this.buildResolvedTargetEntity());
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromJavaResourceModel(this.resourceMapping.getFetch());
	}
	
	protected String getResourceTargetEntity() {
		return this.resourceMapping.getTargetEntity();
	}
	
	protected abstract String buildDefaultTargetEntity();
	
	protected Entity buildResolvedTargetEntity() {
		String targetEntityName = (this.specifiedTargetEntity == null) ?
						this.defaultTargetEntity :
						this.resourceMapping.getFullyQualifiedTargetEntity();
		return (targetEntityName == null) ? null : this.getPersistenceUnit().getEntity(targetEntityName);
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
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		validateTargetEntity(messages, astRoot);
	}
	
	protected void validateTargetEntity(List<IMessage> messages, CompilationUnit astRoot) {
		if (getTargetEntity() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TARGET_ENTITY_NOT_DEFINED,
					new String[] {this.getAttributeName()}, 
					this, 
					this.getValidationTextRange(astRoot)
				)
			);
		}
		else if (getResolvedTargetEntity() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,
					new String[] {getTargetEntity(), this.getAttributeName()}, 
					this, 
					this.getTargetEntityTextRange(astRoot)
				)
			);			
		}
	}
	
	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	protected TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.resourceMapping.getTargetEntityTextRange(astRoot), astRoot);
	}
}
