/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
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


	protected AbstractJavaBaseEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
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

	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this.buildAttributeOverrideContainerOwner());
	}

	protected abstract JavaAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner();


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
		return this.getPersistentAttribute().getEmbeddable();
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

	protected Iterable<String> getEmbeddableAttributeMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new CompositeIterable<String>(this.getEmbeddableAttributeMappingNamesLists(transformer));
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<Iterable<String>> getEmbeddableAttributeMappingNamesLists(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new TransformationIterable<AttributeMapping, Iterable<String>>(this.getEmbeddableAttributeMappings(), transformer);
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
	public Column resolveOverriddenColumn(String attributeName) {
		return this.isJpa2_0Compatible() ? this.resolveOverriddenColumn_(attributeName) : null;
	}

	protected Column resolveOverriddenColumn_(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		AttributeOverride override = this.attributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getColumn() : this.resolveOverriddenColumnInTargetEmbeddable(attributeName);
	}

	protected Column resolveOverriddenColumnInTargetEmbeddable(String attributeName) {
		return (this.targetEmbeddable == null) ? null : this.targetEmbeddable.resolveOverriddenColumn(attributeName);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.attributeOverrideContainer.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		if (this.validateTargetEmbeddable(messages, astRoot)) {
			this.validateOverrides(messages, reporter, astRoot);
		}
	}

	protected boolean validateTargetEmbeddable(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.targetEmbeddable != null) {
			return true;
		}
		String targetEmbeddableTypeName = this.getPersistentAttribute().getTypeName();
		// if the type isn't resolvable, there will already be a java compile error
		if (targetEmbeddableTypeName != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TARGET_NOT_AN_EMBEDDABLE,
					new String[] {targetEmbeddableTypeName},
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
		return false;
	}

	protected void validateOverrides(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.attributeOverrideContainer.validate(messages, reporter, astRoot);
	}


	// ********** attribute override container owner *********

	protected abstract class AttributeOverrideContainerOwner
		implements JavaAttributeOverrideContainer2_0.Owner
	{
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
		 * NB: Overridden in {@link GenericJavaEmbeddedIdMapping.AttributeOverrideContainerOwner}
		 */
		protected Iterable<String> getAllOverridableAttributeNames_(TypeMapping overriddenTypeMapping) {
			return overriddenTypeMapping.getAllOverridableAttributeNames();
		}

		public Column resolveOverriddenColumn(String attributeName) {
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

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaBaseEmbeddedMapping.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, textRangeResolver, new EntityTableDescriptionProvider());
		}

		protected JavaPersistentAttribute getPersistentAttribute() {
			return AbstractJavaBaseEmbeddedMapping.this.getPersistentAttribute();
		}
	}
}
