/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlEmbedded;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> embedded or embedded ID mapping
 */
public abstract class AbstractOrmBaseEmbeddedMapping<X extends AbstractXmlEmbedded>
	extends AbstractOrmAttributeMapping<X>
	implements OrmBaseEmbeddedMapping
{
	protected final OrmAttributeOverrideContainer attributeOverrideContainer;

	protected Embeddable targetEmbeddable;


	protected AbstractOrmBaseEmbeddedMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.attributeOverrideContainer = this.buildAttributeOverrideContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.attributeOverrideContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.attributeOverrideContainer.update();
		this.setTargetEmbeddable(this.buildTargetEmbeddable());
	}


	// ********** attribute override container **********

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildAttributeOverrideContainer() {
		return this.getContextModelFactory().buildOrmAttributeOverrideContainer(this.buildAttributeOverrideContainerParentAdapter());
	}

	protected abstract OrmAttributeOverrideContainer.ParentAdapter buildAttributeOverrideContainerParentAdapter();


	// ********** target embeddable **********

	public Embeddable getTargetEmbeddable() {
		return this.targetEmbeddable;
	}

	protected void setTargetEmbeddable(Embeddable embeddable) {
		Embeddable old = this.targetEmbeddable;
		this.targetEmbeddable = embeddable;
		this.firePropertyChanged(TARGET_EMBEDDABLE_PROPERTY, old, embeddable);
	}

	protected Embeddable buildTargetEmbeddable() {
		TypeMapping typeMapping = this.getResolvedTargetTypeMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected TypeMapping getResolvedTargetTypeMapping() {
		PersistentType resolvedTargetType = this.getResolvedAttributeType();
		return (resolvedTargetType == null) ? null : resolvedTargetType.getMapping();
	}

	// ********** embedded mappings **********

	@Override
	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.getEmbeddableOverridableAttributeMappingNames() :
				super.getAllOverridableAttributeMappingNames();
	}

	protected Iterable<String> getEmbeddableOverridableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.getEmbeddableOverridableAssociationMappingNames() :
				super.getAllOverridableAssociationMappingNames();
	}

	protected Iterable<String> getEmbeddableOverridableAssociationMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<String> getQualifiedEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new TransformationIterable<String, String>(this.getEmbeddableAttributeMappingNames(transformer), this.buildQualifierTransformer());
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<String> getEmbeddableAttributeMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return IterableTools.children(this.getEmbeddableAttributeMappings(), transformer);
	}

	/**
	 * Return the target embeddable's attribute mappings.
	 */
	protected Iterable<AttributeMapping> getEmbeddableAttributeMappings() {
		return ((this.targetEmbeddable != null) && (this.targetEmbeddable != this.getTypeMapping())) ?
				this.targetEmbeddable.getAttributeMappings() :
				EmptyIterable.<AttributeMapping>instance();
	}


	// ********** misc **********

	@Override
	protected void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		this.attributeOverrideContainer.initializeFrom(oldMapping.getAttributeOverrideContainer());
	}

	@Override
	public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
		return this.isJpa2_0Compatible() ? this.resolveOverriddenColumn_(attributeName) : null;
	}

	protected SpecifiedColumn resolveOverriddenColumn_(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		SpecifiedAttributeOverride override = this.attributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getColumn() : this.resolveOverriddenColumnInTargetEmbeddable(attributeName);
	}

	protected SpecifiedColumn resolveOverriddenColumnInTargetEmbeddable(String attributeName) {
		return (this.targetEmbeddable == null) ? null : this.targetEmbeddable.resolveOverriddenColumn(attributeName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.validateTargetEmbeddable(messages)) {
			this.validateOverrides(messages, reporter);
		}
	}

	protected boolean validateTargetEmbeddable(List<IMessage> messages) {
		if (this.targetEmbeddable != null) {
			return true;
		}
		messages.add(
			this.buildValidationMessage(
				this.getAttributeTypeTextRange(),
				JptJpaCoreValidationMessages.TARGET_NOT_AN_EMBEDDABLE,
				this.getFullyQualifiedAttributeType()
			)
		);
		return false;
	}

	protected TextRange getAttributeTypeTextRange() {
		return this.getValidationTextRange();
	}

	protected void validateOverrides(List<IMessage> messages, IReporter reporter) {
		this.attributeOverrideContainer.validate(messages, reporter);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.attributeOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** attribute override container parent adapter *********

	public abstract class AttributeOverrideContainerParentAdapter
		implements OrmAttributeOverrideContainer.ParentAdapter
	{
		public JpaContextModel getOverrideContainerParent() {
			return AbstractOrmBaseEmbeddedMapping.this;
		}

		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTypeMapping();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTargetEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? this.getAllOverridableAttributeNames_(overriddenTypeMapping) : EmptyIterable.<String>instance();
		}

		/**
		 * pre-condition: type mapping is not <code>null</code>
		 * <p>
		 * NB: Overridden in {@link org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmEmbeddedIdMapping.AttributeOverrideContainerParentAdapter}
		 */
		protected Iterable<String> getAllOverridableAttributeNames_(TypeMapping overriddenTypeMapping) {
			return overriddenTypeMapping.getAllOverridableAttributeNames();
		}

		public Iterable<String> getJavaOverrideNames() {
			return null;
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return AbstractOrmBaseEmbeddedMapping.this.getXmlAttributeMapping().getAttributeOverrides();
		}

		public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return this.getTypeMapping().getAllAssociatedTableNames();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public String getDefaultTableName() {
			return this.getTypeMapping().getPrimaryTableName();
		}

		public JptValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, new EntityTableDescriptionProvider());
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmBaseEmbeddedMapping.this.getValidationTextRange();
		}

		protected OrmSpecifiedPersistentAttribute getPersistentAttribute() {
			return AbstractOrmBaseEmbeddedMapping.this.getPersistentAttribute();
		}
	}
}
