/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaCascade;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaCascade;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java relationship mapping (1:1, 1:m, m:1, m:m)
 */
public abstract class AbstractJavaRelationshipMapping<A extends RelationshipMappingAnnotation>
	extends AbstractJavaAttributeMapping<A>
	implements JavaRelationshipMapping
{
	protected String specifiedTargetEntity;
	protected String defaultTargetEntity;
	protected String fullyQualifiedTargetEntity;

	protected final JavaMappingRelationship relationship;

	protected final JavaCascade cascade;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch;


	protected AbstractJavaRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedTargetEntity = this.buildSpecifiedTargetEntity();
		this.relationship = this.buildRelationship();
		this.cascade = this.buildCascade();
		this.specifiedFetch = this.buildSpecifiedFetch();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTargetEntity_(this.buildSpecifiedTargetEntity());
		this.relationship.synchronizeWithResourceModel();
		this.cascade.synchronizeWithResourceModel();
		this.setSpecifiedFetch_(this.buildSpecifiedFetch());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTargetEntity(this.buildDefaultTargetEntity());
		this.setFullyQualifiedTargetEntity(this.buildFullyQualifiedTargetEntity());
		this.relationship.update();
		this.cascade.update();
		this.setDefaultFetch(this.buildDefaultFetch());
	}


	// ********** target entity **********

	public String getTargetEntity() {
		return (this.specifiedTargetEntity != null) ? this.specifiedTargetEntity : this.defaultTargetEntity;
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String entity) {
		if (this.valuesAreDifferent(entity, this.specifiedTargetEntity)) {
			this.getAnnotationForUpdate().setTargetEntity(entity);
			this.setSpecifiedTargetEntity_(entity);
		}
	}

	protected void setSpecifiedTargetEntity_(String entity) {
		String old = this.specifiedTargetEntity;
		this.specifiedTargetEntity = entity;
		this.firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, old, entity);
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
		this.defaultTargetEntity = entity;
		this.firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, old, entity);
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
		return (this.fullyQualifiedTargetEntity == null) ? null : this.getPersistenceUnit().getEntity(this.fullyQualifiedTargetEntity);
	}

	// sub-classes like this to be public
	public PersistentType getResolvedTargetType() {
		return (this.fullyQualifiedTargetEntity == null) ? null : this.getPersistenceUnit().getPersistentType(this.fullyQualifiedTargetEntity);
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

	public JavaCascade getCascade() {
		return this.cascade;
	}

	protected JavaCascade buildCascade() {
		// NB: we don't use the platform
		return new GenericJavaCascade(this);
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		if (this.valuesAreDifferent(fetch, this.specifiedFetch)) {
			this.getAnnotationForUpdate().setFetch(FetchType.toJavaResourceModel(fetch));
			this.setSpecifiedFetch_(fetch);
		}
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
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
		this.defaultFetch = fetch;
		this.firePropertyChanged(DEFAULT_FETCH_PROPERTY, old, fetch);
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
		for (ReadOnlyPersistentAttribute attribute : CollectionTools.iterable(entity.getPersistentType().allAttributes())) {
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

	public Iterator<String> allTargetEntityAttributeNames() {
		return new CompositeIterator<String>(this.allTargetEntityAttributeNamesLists());
	}

	protected Iterator<Iterator<String>> allTargetEntityAttributeNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.allTargetEntityAttributeMappings(), AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterator<AttributeMapping> allTargetEntityAttributeMappings() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity != null) ? entity.allAttributeMappings() : EmptyIterator.<AttributeMapping> instance();
	}

	protected String getTargetEntityIdAttributeName() {
		PersistentAttribute attribute = this.getTargetEntityIdAttribute();
		return (attribute == null) ? null : attribute.getName();
	}

	protected PersistentAttribute getTargetEntityIdAttribute() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity == null) ? null : entity.getIdAttribute();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.relationship.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		return null;
	}


	// ********** metamodel **********

	@Override
	public String getMetamodelTypeName() {
		return (this.fullyQualifiedTargetEntity != null) ? this.fullyQualifiedTargetEntity : MetamodelField.DEFAULT_TYPE_NAME;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateTargetEntity(messages, astRoot);
		this.relationship.validate(messages, reporter, astRoot);
	}

	protected void validateTargetEntity(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTargetEntity() == null) {
			String msg = this.getPersistentAttribute().isVirtual() ?
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED :
						JpaValidationMessages.TARGET_ENTITY_NOT_DEFINED;
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					msg,
					new String[] {this.getName()},
					this,
					this.getValidationTextRange(astRoot)
				)
			);
			return;
		}
		if (this.getResolvedTargetEntity() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY,
						new String[] {this.getName(), this.getTargetEntity()},
						this,
						this.getValidationTextRange(astRoot)
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,
						new String[] {this.getTargetEntity(), this.getName()},
						this,
						this.getTargetEntityTextRange(astRoot)
					)
				);
			}
		}
	}

	protected TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getAnnotationTargetEntityTextRange(astRoot), astRoot);
	}

	protected TextRange getAnnotationTargetEntityTextRange(CompilationUnit astRoot) {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetEntityTextRange(astRoot);
	}
}
