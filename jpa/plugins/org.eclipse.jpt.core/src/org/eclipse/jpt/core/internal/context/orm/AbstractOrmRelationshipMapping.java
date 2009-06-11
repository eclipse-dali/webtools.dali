/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmRelationshipMapping<T extends AbstractXmlRelationshipMapping>
	extends AbstractOrmAttributeMapping<T>
	implements OrmRelationshipMapping
{
	protected String specifiedTargetEntity;
	protected String defaultTargetEntity;
	protected Entity resolvedTargetEntity;
	
	protected final OrmRelationshipReference relationshipReference;
	
	protected final OrmCascade cascade;

	protected FetchType specifiedFetch;
	
	
	protected AbstractOrmRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.relationshipReference = buildRelationshipReference();
		this.cascade = new OrmCascade(this, this.resourceAttributeMapping);
		this.specifiedTargetEntity = getResourceTargetEntity();
		this.defaultTargetEntity = buildDefaultTargetEntity();
		this.resolvedTargetEntity = buildResolvedTargetEntity();
		this.specifiedFetch = getResourceFetch();
	}
	
	
	protected abstract OrmRelationshipReference buildRelationshipReference();

	@Override
	public OrmPersistentAttribute getParent() {
		return (OrmPersistentAttribute) super.getParent();
	}
	
	public boolean isRelationshipOwner() {
		return this.relationshipReference.isRelationshipOwner();
	}
	
	@Override
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.relationshipReference.isOwnedBy(mapping);
	}
	
	
	// **************** target entity ******************************************

	public char getTargetEntityEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getTargetEntity() {
		return (this.specifiedTargetEntity != null) ? this.specifiedTargetEntity : this.defaultTargetEntity;
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String targetEntity) {
		String old = this.specifiedTargetEntity;
		this.specifiedTargetEntity = targetEntity;
		this.resourceAttributeMapping.setTargetEntity(targetEntity);
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


	// **************** reference **********************************************
	
	public OrmRelationshipReference getRelationshipReference() {
		return this.relationshipReference;
	}
	
	
	// **************** cascade ************************************************
	
	public OrmCascade getCascade() {
		return this.cascade;
	}
	
	
	// **************** fetch **************************************************

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.getDefaultFetch();
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.resourceAttributeMapping.setFetch(FetchType.toOrmResourceModel(fetch));
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}


	// **************** resource => context ************************************
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedTargetEntity_(this.getResourceTargetEntity());
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity());
		this.setResolvedTargetEntity(this.buildResolvedTargetEntity());
		this.relationshipReference.update();
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.cascade.update();
	}
	
	protected String getResourceTargetEntity() {
		return this.resourceAttributeMapping.getTargetEntity();
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromOrmResourceModel(this.resourceAttributeMapping.getFetch());
	}
	
	protected String buildDefaultTargetEntity() {
		RelationshipMapping javaMapping = getJavaRelationshipMapping();
		if (javaMapping != null) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				return javaMapping.getTargetEntity();
			}
		}
		if (this.getJavaPersistentAttribute() != null) {
			return getResourceDefaultTargetEntity();
		}
		return null;
	}
	
	protected RelationshipMapping getJavaRelationshipMapping() {
		if (this.getJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = this.getJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping instanceof RelationshipMapping) {
			return ((RelationshipMapping) javaAttributeMapping);
		}
		return null;
	}
	
	protected abstract String getResourceDefaultTargetEntity();

	protected Entity buildResolvedTargetEntity() {
		String targetEntityName = this.getTargetEntity();
		if (targetEntityName == null) {
			return null;
		}

		// first try to resolve using only the locally specified name...
		Entity targetEntity = this.getEntity(targetEntityName);
		if (targetEntity != null) {
			return targetEntity;
		}

		// ...then try to resolve by prepending the global package name
		String defaultPackageName = this.getDefaultPackageName();
		if (defaultPackageName == null) {
			return null;
		}
		return this.getEntity(defaultPackageName + '.' + targetEntityName);
	}

	protected String getDefaultPackageName() {
		return this.getPersistentAttribute().getPersistentType().getDefaultPackage();
	}

	protected Entity getEntity(String typeName) {
		return this.getPersistenceUnit().getEntity(typeName);
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
		oldMapping.getRelationshipReference().initializeOn(this.relationshipReference);
		this.cascade.initializeFrom(oldMapping.getCascade());
		//TODO should we set the fetch type from a BasicMapping??
	}
	
	public Iterator<String> allTargetEntityAttributeNames() {
		Entity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateTargetEntity(messages);
		this.relationshipReference.validate(messages, reporter);
	}
	
	protected void validateTargetEntity(List<IMessage> messages) {
		if (getTargetEntity() == null) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED,
						new String[] {this.getName()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
			else { 
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TARGET_ENTITY_NOT_DEFINED,
						new String[] {this.getName()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
		}
		else if (getResolvedTargetEntity() == null) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY,
						new String[] {this.getName(), getTargetEntity()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,
						new String[] {getTargetEntity(), this.getName()}, 
						this, 
						this.getTargetEntityTextRange()
					)
				);
			}
		}
	}
	
	protected TextRange getTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	protected TextRange getTargetEntityTextRange() {
		return this.getTextRange(this.getResourceAttributeMapping().getTargetEntityTextRange());
	}	
}
