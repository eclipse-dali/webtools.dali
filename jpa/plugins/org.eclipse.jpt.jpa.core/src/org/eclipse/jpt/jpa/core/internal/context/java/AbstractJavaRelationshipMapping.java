/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaCascade;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.RelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java relationship mapping (1:1, 1:m, m:1, m:m)
 */
public abstract class AbstractJavaRelationshipMapping<A extends RelationshipMappingAnnotation>
	extends AbstractJavaAttributeMapping<A>
	implements JavaRelationshipMapping, RelationshipMapping2_0
{
	protected String specifiedTargetEntity;
	protected String defaultTargetEntity;
	protected String targetEntity;
	protected String fullyQualifiedTargetEntity;

	protected final JavaMappingRelationship relationship;

	protected final Cascade2_0 cascade;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch;
	protected FetchType fetch;


	protected AbstractJavaRelationshipMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.specifiedTargetEntity = this.buildSpecifiedTargetEntity();
		this.relationship = this.buildRelationship();
		this.cascade = this.buildCascade();
		this.specifiedFetch = this.buildSpecifiedFetch();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedTargetEntity_(this.buildSpecifiedTargetEntity());
		this.relationship.synchronizeWithResourceModel(monitor);
		this.cascade.synchronizeWithResourceModel(monitor);
		this.setSpecifiedFetch_(this.buildSpecifiedFetch());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity());
		this.setTargetEntity(this.buildTargetEntity());
		this.setFullyQualifiedTargetEntity(this.buildFullyQualifiedTargetEntity());
		this.relationship.update(monitor);
		this.cascade.update(monitor);
		this.setDefaultFetch(this.buildDefaultFetch());
		this.setFetch(this.buildFetch());
	}


	// ********** target entity **********

	public String getTargetEntity() {
		return this.targetEntity;
	}

	protected void setTargetEntity(String entity) {
		String old = this.targetEntity;
		this.firePropertyChanged(TARGET_ENTITY_PROPERTY, old, this.targetEntity = entity);
	}

	protected String buildTargetEntity() {
		return (this.specifiedTargetEntity != null) ? this.specifiedTargetEntity : this.defaultTargetEntity;
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String entity) {
		if (ObjectTools.notEquals(entity, this.specifiedTargetEntity)) {
			this.getAnnotationForUpdate().setTargetEntity(entity);
			this.setSpecifiedTargetEntity_(entity);
		}
	}

	protected void setSpecifiedTargetEntity_(String entity) {
		String old = this.specifiedTargetEntity;
		this.firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, old, this.specifiedTargetEntity = entity);
	}

	protected String buildSpecifiedTargetEntity() {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetEntity();
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String entity) {
		String old = this.defaultTargetEntity;
		this.firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, old, this.defaultTargetEntity = entity);
	}

	protected abstract String buildDefaultTargetEntity();

	public String getFullyQualifiedTargetEntity() {
		return this.fullyQualifiedTargetEntity;
	}

	protected void setFullyQualifiedTargetEntity(String entity) {
		String old = this.fullyQualifiedTargetEntity;
		this.fullyQualifiedTargetEntity = entity;
		this.firePropertyChanged(FULLY_QUALIFIED_TARGET_ENTITY_PROPERTY, old, entity);
	}

	protected String buildFullyQualifiedTargetEntity() {
		return (this.specifiedTargetEntity == null) ?
				this.defaultTargetEntity :
				this.getMappingAnnotation().getFullyQualifiedTargetEntityClassName();
	}

	public Entity getResolvedTargetEntity() {
		return this.getPersistenceUnit().getEntity(this.fullyQualifiedTargetEntity);
	}

	// sub-classes like this to be public
	public PersistentType getResolvedTargetType() {
		return this.getPersistenceUnit().getPersistentType(this.fullyQualifiedTargetEntity);
	}

	public char getTargetEntityEnclosingTypeSeparator() {
		return '.';
	}


	// ********** relationship reference **********

	public JavaMappingRelationship getRelationship() {
		return this.relationship;
	}

	protected abstract JavaMappingRelationship buildRelationship();


	// ********** cascade **********

	public Cascade2_0 getCascade() {
		return this.cascade;
	}

	protected Cascade2_0 buildCascade() {
		// NB: we don't use the platform
		return new GenericJavaCascade(this);
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return this.fetch;
	}

	protected void setFetch(FetchType fetch) {
		FetchType old = this.fetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, this.fetch = fetch);
	}

	protected FetchType buildFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		if (ObjectTools.notEquals(fetch, this.specifiedFetch)) {
			this.getAnnotationForUpdate().setFetch(FetchType.toJavaResourceModel(fetch));
			this.setSpecifiedFetch_(fetch);
		}
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, this.specifiedFetch = fetch);
	}

	protected FetchType buildSpecifiedFetch() {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : FetchType.fromJavaResourceModel(annotation.getFetch());
	}

	public FetchType getDefaultFetch() {
		return this.defaultFetch;
	}

	protected void setDefaultFetch(FetchType fetch) {
		FetchType old = this.defaultFetch;
		this.firePropertyChanged(DEFAULT_FETCH_PROPERTY, old, this.defaultFetch = fetch);
	}

	protected abstract FetchType buildDefaultFetch();


	// ********** misc **********

	@Override
	public boolean isRelationshipOwner() {
		return this.relationship.isOwner();
	}

	@Override
	public boolean isOwnedBy(AttributeMapping mapping) {
		return mapping.isRelationshipOwner() &&
			this.relationship.isOwnedBy((RelationshipMapping) mapping);
	}

	public RelationshipMapping getRelationshipOwner() {
		Entity entity = this.getResolvedTargetEntity();
		if (entity == null) {
			return null;
		}
		for (PersistentAttribute attribute : entity.getPersistentType().getAllAttributes()) {
			AttributeMapping mapping = attribute.getMapping();
			if (this.isOwnedBy(mapping)) {
				return (RelationshipMapping) mapping;
			}
		}
		return null;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return this.relationship.isOverridable();
	}

	public Iterable<String> getAllTargetEntityAttributeNames() {
		return IterableTools.children(this.getAllTargetEntityAttributeMappings(), ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getAllTargetEntityAttributeMappings() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity != null) ? entity.getAllAttributeMappings() : EmptyIterable.<AttributeMapping> instance();
	}

	// Get the names of non-transient attribute mappings
	public Iterable<String> getTargetEntityNonTransientAttributeNames() {
		return IterableTools.children(this.getTargetEntityNonTransientAttributeMappings(), ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getTargetEntityNonTransientAttributeMappings() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity != null) ? entity.getNonTransientAttributeMappings() : EmptyIterable.<AttributeMapping> instance();
	}
	
	protected String getTargetEntityIdAttributeName() {
		SpecifiedPersistentAttribute attribute = this.getTargetEntityIdAttribute();
		return (attribute == null) ? null : attribute.getName();
	}

	protected SpecifiedPersistentAttribute getTargetEntityIdAttribute() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity == null) ? null : entity.getIdAttribute();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.relationship.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		return null;
	}


	// ********** metamodel **********

	@Override
	public String getMetamodelTypeName() {
		return (this.fullyQualifiedTargetEntity != null) ? this.fullyQualifiedTargetEntity : MetamodelField2_0.DEFAULT_TYPE_NAME;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetEntity(messages);
		this.relationship.validate(messages, reporter);
	}

	protected void validateTargetEntity(List<IMessage> messages) {
		if (this.getTargetEntity() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						this.buildValidationMessage(
							this.getVirtualPersistentAttributeTextRange(),
							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED,
							this.getName()
						)
					);
			}
			else {
				messages.add(
						this.buildValidationMessage(
							this.getValidationTextRange(),
							JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_DEFINED,
							this.getName()
						)
					);
			}
			return;
		}

		IType targetEntityJdtType = JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedTargetEntity());
		if (targetEntityJdtType == null) {
			//java compiler will handle this case
			return;
		}
		if (this.getResolvedTargetEntity() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY,
						this.getName(),
						this.getFullyQualifiedTargetEntity()
					)
				);
			} else {
				messages.add(
					this.buildValidationMessage(
						this.getTargetEntityTextRange(),
						JptJpaCoreValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,
						this.getFullyQualifiedTargetEntity()
					)
				);
			}
		}
	}

	protected TextRange getTargetEntityTextRange() {
		return this.getValidationTextRange(this.getAnnotationTargetEntityTextRange());
	}

	protected TextRange getAnnotationTargetEntityTextRange() {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetEntityTextRange();
	}
}
