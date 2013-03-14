/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmCascade;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.RelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCascade2_0;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlRelationshipMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> relationship mapping (1:1, 1:m, m:1, m:m)
 */
public abstract class AbstractOrmRelationshipMapping<X extends AbstractXmlRelationshipMapping>
	extends AbstractOrmAttributeMapping<X>
	implements OrmRelationshipMapping, RelationshipMapping2_0
{
	protected String specifiedTargetEntity;
	protected String defaultTargetEntity;
	protected String fullyQualifiedTargetEntity;

	protected final OrmMappingRelationship relationship;

	protected final OrmCascade2_0 cascade;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch;


	protected AbstractOrmRelationshipMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.specifiedTargetEntity = xmlMapping.getTargetEntity();
		this.relationship = this.buildRelationship();
		this.cascade = this.buildCascade();
		this.specifiedFetch = this.buildSpecifiedFetch();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTargetEntity_(this.xmlAttributeMapping.getTargetEntity());
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


	// ********** fully-qualified target entity **********

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
				this.getEntityMappings().qualify(this.specifiedTargetEntity);
	}

	// ********** target entity **********

	public String getTargetEntity() {
		return (this.specifiedTargetEntity != null) ? this.specifiedTargetEntity : this.defaultTargetEntity;
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String entity) {
		this.setSpecifiedTargetEntity_(entity);
		this.xmlAttributeMapping.setTargetEntity(entity);
	}

	protected void setSpecifiedTargetEntity_(String entity) {
		String old = this.specifiedTargetEntity;
		this.specifiedTargetEntity = entity;
		this.firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, old, entity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String entity) {
		String old = this.defaultTargetEntity;
		this.defaultTargetEntity = entity;
		this.firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, old, entity);
	}

	protected String buildDefaultTargetEntity() {
		return (this.getJavaPersistentAttribute() == null) ? null : this.getJavaTargetType();
	}

	/**
	 * pre-condition: the mapping's Java persistent attribute is not
	 * <code>null</code>.
	 */
	protected abstract String getJavaTargetType();

	public Entity getResolvedTargetEntity() {
		TypeMapping typeMapping = this.getResolvedTargetTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	protected TypeMapping getResolvedTargetTypeMapping() {
		PersistentType resolvedTargetType = this.getResolvedTargetType();
		return (resolvedTargetType == null) ? null : resolvedTargetType.getMapping();
	}

	// sub-classes like this to be public
	public PersistentType getResolvedTargetType() {
		if (this.fullyQualifiedTargetEntity == null) {
			return null;
		}
		return getPersistenceUnit().getPersistentType(this.fullyQualifiedTargetEntity);
	}

	public char getTargetEntityEnclosingTypeSeparator() {
		return '$';
	}


	// ********** relationship reference **********

	public OrmMappingRelationship getRelationship() {
		return this.relationship;
	}

	protected abstract OrmMappingRelationship buildRelationship();


	// ********** cascade **********

	public OrmCascade2_0 getCascade() {
		return this.cascade;
	}

	protected OrmCascade2_0 buildCascade() {
		// NB: we don't use the platform
		return new GenericOrmCascade(this);
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		this.setSpecifiedFetch_(fetch);
		this.xmlAttributeMapping.setFetch(FetchType.toOrmResourceModel(fetch));
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildSpecifiedFetch() {
		return FetchType.fromOrmResourceModel(this.xmlAttributeMapping.getFetch());
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

	@Override
	protected void initializeFromOrmRelationshipMapping(OrmRelationshipMapping oldMapping) {
		super.initializeFromOrmRelationshipMapping(oldMapping);
		this.setSpecifiedTargetEntity(oldMapping.getSpecifiedTargetEntity());
		this.setSpecifiedFetch(oldMapping.getSpecifiedFetch());
		oldMapping.getRelationship().initializeOn(this.relationship);
		this.cascade.initializeFrom(oldMapping.getCascade());
		//TODO should we set the fetch type from a BasicMapping??
	}

	public Iterable<String> getAllTargetEntityAttributeNames() {
		return IterableTools.children(this.getAllTargetEntityAttributeMappings(), ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getAllTargetEntityAttributeMappings() {
		Entity entity = this.getResolvedTargetEntity();
		return (entity != null) ? entity.getAllAttributeMappings() : EmptyIterable.<AttributeMapping>instance();
	}

	// Get the name of non-transient attribute mappings
	public Iterable<String> getTargetEntityNonTransientAttributeNames() {
		return IterableTools.children(this.getNonTransientTargetEntityAttributeMappings(), ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getNonTransientTargetEntityAttributeMappings() {
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


	//************ refactoring ************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.createTargetEntityRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createTargetEntityRenameTypeEdits(IType originalType, String newName) {
		if (this.specifiedTargetEntity != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createTargetEntityRenameTypeEdit(originalType, newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createTargetEntityRenameTypeEdit(IType originalType, String newName) {
		return this.xmlAttributeMapping.createRenameTargetEntityEdit(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createTargetEntityMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createTargetEntityMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.specifiedTargetEntity != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createTargetEntityRenamePackageEdit(newPackage.getElementName()));
			}
		}
		return EmptyIterable.instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createTargetEntityRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createTargetEntityRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.specifiedTargetEntity != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isIn(originalPackage)) {
				return new SingleElementIterable<ReplaceEdit>(this.createTargetEntityRenamePackageEdit(newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createTargetEntityRenamePackageEdit(String newName) {
		return this.xmlAttributeMapping.createRenameTargetEntityPackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetEntity(messages);
		this.relationship.validate(messages, reporter);
	}

	protected void validateTargetEntity(List<IMessage> messages) {
		if (StringTools.isBlank(this.getTargetEntity())) {
			messages.add(
				this.buildValidationMessage(
					this.getTargetEntityTextRange(),
					JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_DEFINED
				)
			);
			return;
		}
		if (this.getResolvedTargetType() == null) {
			IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedTargetEntity());
			if (jdtType == null) {
				messages.add(
					this.buildValidationMessage(
						this.getTargetEntityTextRange(),
						JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_EXIST,
						this.getFullyQualifiedTargetEntity()
					)
				);
			}
			return;
		}
		if (this.getResolvedTargetEntity() == null) {
			messages.add(
				this.buildValidationMessage(
					this.getTargetEntityTextRange(),
					JptJpaCoreValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,
					this.getFullyQualifiedTargetEntity()
				)
			);
		}
	}

	protected TextRange getTargetEntityTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getTargetEntityTextRange());
	}


	// ********** metamodel **********

	@Override
	public String getMetamodelTypeName() {
		PersistentType resolvedTargetType = this.getResolvedTargetType();
		if(((PersistentType2_0)resolvedTargetType).getMetamodelType() == null) { // dynamic type
			return null;
		}
		return resolvedTargetType.getName();
	}
	
	
	// ********** completion proposals **********

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
		if (this.targetEntityTouches(pos)) {
			return this.getCandidateTargetEntityClassNames();
		}
		return null;
	}

	protected boolean targetEntityTouches(int pos) {
		return this.xmlAttributeMapping.targetEntityTouches(pos);
	}
	
	protected Iterable<String> getCandidateTargetEntityClassNames() {
		return MappingTools.getSortedJavaClassNames(getJavaProject());
	}
}
