/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
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
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> embedded or embedded ID mapping
 */
public abstract class AbstractJavaBaseEmbeddedMapping<A extends Annotation>
	extends AbstractJavaAttributeMapping<A>
	implements JavaBaseEmbeddedMapping
{
	protected final JavaAttributeOverrideContainer attributeOverrideContainer;

	protected Embeddable targetEmbeddable;


	protected AbstractJavaBaseEmbeddedMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.attributeOverrideContainer = this.buildAttributeOverrideContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.attributeOverrideContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.attributeOverrideContainer.update(monitor);
		this.setTargetEmbeddable(this.buildTargetEmbeddable());
	}


	// ********** attribute override container **********

	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this.buildAttributeOverrideContainerParentAdapter());
	}

	protected abstract JavaAttributeOverrideContainer.ParentAdapter buildAttributeOverrideContainerParentAdapter();


	// ***** target embeddable *****
	
	public String getTargetEmbeddableName() {
		return getPersistentAttribute().getSingleReferenceTargetTypeName();
	}
	
	public Embeddable getTargetEmbeddable() {
		return this.targetEmbeddable;
	}
	
	protected void setTargetEmbeddable(Embeddable embeddable) {
		Embeddable old = this.targetEmbeddable;
		this.targetEmbeddable = embeddable;
		this.firePropertyChanged(TARGET_EMBEDDABLE_PROPERTY, old, embeddable);
	}
	
	protected Embeddable buildTargetEmbeddable() {
		String typeName = getTargetEmbeddableName();
		return (typeName == null) ? null : getPersistenceUnit().getEmbeddable(typeName);
	}
	
	
	// ********** embedded mappings **********

	@Override
	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.getEmbeddableOverridableAttributeMappingNames() :
				super.getAllOverridableAttributeMappingNames();
	}

	protected Iterable<String> getEmbeddableOverridableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.getEmbeddableOverridableAssociationMappingNames() :
				super.getAllOverridableAssociationMappingNames();
	}

	protected Iterable<String> getEmbeddableOverridableAssociationMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
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


	// ********** Java completion proposals **********

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
		String targetEmbeddableTypeName = this.getPersistentAttribute().getTypeName();
		if (this.getPersistentAttribute().isVirtual()) {
			messages.add(
				this.buildValidationMessage(
					this.getVirtualPersistentAttributeTextRange(),
					JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_NOT_AN_EMBEDDABLE,
					this.getName(),
					targetEmbeddableTypeName
				)
			);
		}
		else {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.TARGET_NOT_AN_EMBEDDABLE,
					targetEmbeddableTypeName
				)
			);
		}
		return false;
	}

	protected void validateOverrides(List<IMessage> messages, IReporter reporter) {
		this.attributeOverrideContainer.validate(messages, reporter);
	}


	// ********** attribute override container parent adapter *********

	public abstract class AttributeOverrideContainerParentAdapter
		implements JavaAttributeOverrideContainer2_0.ParentAdapter
	{
		public JpaContextModel getOverrideContainerParent() {
			return AbstractJavaBaseEmbeddedMapping.this;
		}

		public JavaResourceMember getResourceMember() {
			return AbstractJavaBaseEmbeddedMapping.this.getResourceAttribute();
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaBaseEmbeddedMapping.this.getTypeMapping();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaBaseEmbeddedMapping.this.getTargetEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? this.getAllOverridableAttributeNames_(overriddenTypeMapping) : EmptyIterable.<String>instance();
		}

		/**
		 * pre-condition: type mapping is not <code>null</code>
		 * <p>
		 * NB: Overridden in {@link AbstractJavaEmbeddedIdMapping.AttributeOverrideContainerParentAdapter}
		 */
		protected Iterable<String> getAllOverridableAttributeNames_(TypeMapping overriddenTypeMapping) {
			return overriddenTypeMapping.getAllOverridableAttributeNames();
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

		public Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public String getDefaultTableName() {
			return this.getTypeMapping().getPrimaryTableName();
		}

		public String getPossiblePrefix() {
			return null;
		}

		public String getWritePrefix() {
			return null;
		}

		// no maps, so all overrides are relevant
		public boolean isRelevant(String overrideName) {
			return true;
		}

		public TextRange getValidationTextRange() {
			return AbstractJavaBaseEmbeddedMapping.this.getValidationTextRange();
		}

		public JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}
		
		public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter parentAdapter) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, new EntityTableDescriptionProvider());
		}

		protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
			return AbstractJavaBaseEmbeddedMapping.this.getPersistentAttribute();
		}
	}
}
