/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.OverrideContainer;
import org.eclipse.jpt.core.context.Override_;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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


	protected AbstractOrmBaseEmbeddedMapping(OrmPersistentAttribute parent, X xmlMapping) {
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
		return this.getContextNodeFactory().buildOrmAttributeOverrideContainer(this, this.buildAttributeOverrideContainerOwner());
	}

	protected OrmAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}


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
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getEmbeddable();
	}


	// ********** embedded mappings **********

	@Override
	public Iterator<String> allOverridableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverridableAttributeMappingNames() :
				super.allOverridableAttributeMappingNames();
	}

	protected Iterator<String> embeddableOverridableAttributeMappingNames() {
		return this.qualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterator<String> allOverridableAssociationMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverridableAssociationMappingNames() :
				super.allOverridableAssociationMappingNames();
	}

	protected Iterator<String> embeddableOverridableAssociationMappingNames() {
		return this.qualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterator<String> qualifiedEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<String, String>(this.embeddableAttributeMappingNames(transformer), this.buildQualifierTransformer());
	}

	protected Iterator<String> embeddableAttributeMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new CompositeIterator<String>(this.embeddableAttributeMappingNamesLists(transformer));
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterator<Iterator<String>> embeddableAttributeMappingNamesLists(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings(), transformer);
	}

	/**
	 * Return the target embeddable's attribute mappings.
	 */
	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		return ((this.targetEmbeddable != null) && (this.targetEmbeddable != this.getTypeMapping())) ?
				this.targetEmbeddable.attributeMappings() :
				EmptyIterator.<AttributeMapping>instance();
	}


	// ********** misc **********

	@Override
	protected void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		this.attributeOverrideContainer.initializeFrom(oldMapping.getAttributeOverrideContainer());
	}

	protected JavaAttributeOverride getSpecifiedJavaAttributeOverrideNamed(String attributeName) {
		JavaBaseEmbeddedMapping javaMapping = this.getJavaEmbeddedMapping();
		return (javaMapping == null) ? null : javaMapping.getAttributeOverrideContainer().getSpecifiedOverrideNamed(attributeName);
	}

	protected JavaBaseEmbeddedMapping getJavaEmbeddedMapping() {
		JavaAttributeMapping javaMapping = this.getJavaAttributeMapping();
		return ((javaMapping != null) && this.valuesAreEqual(javaMapping.getKey(), this.getKey())) ?
				(JavaBaseEmbeddedMapping) javaMapping : null;
	}

	protected JavaAttributeMapping getJavaAttributeMapping() {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getMapping();
	}

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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.validateTargetEmbeddable(messages)) {
			this.validateOverrides(messages, reporter);
		}
	}

	protected boolean validateTargetEmbeddable(List<IMessage> messages) {
		if (this.targetEmbeddable == null) {
			String targetEmbeddableTypeName = this.getPersistentAttribute().getTypeName();
			// if the type isn't resolvable, there'll already be a java error
			if (targetEmbeddableTypeName != null) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TARGET_NOT_AN_EMBEDDABLE,
						new String[] {targetEmbeddableTypeName},
						this,
						this.getValidationTextRange()
					)
				);
			}
			return false;
		}
		return true;
	}

	protected void validateOverrides(List<IMessage> messages, IReporter reporter) {
		this.attributeOverrideContainer.validate(messages, reporter);
	}


	// ********** attribute override container owner *********

	protected class AttributeOverrideContainerOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTypeMapping();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTargetEmbeddable();
		}

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? this.allOverridableAttributeNames_(typeMapping) : EmptyIterator.<String>instance();
		}

		/**
		 * pre-condition: type mapping is not <code>null</code>
		 * <p>
		 * NB: Overridden in {@link GenericOrmEmbeddedIdMapping.AttributeOverrideContainerOwner}
		 */
		protected Iterator<String> allOverridableAttributeNames_(TypeMapping typeMapping) {
			return typeMapping.allOverridableAttributeNames();
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return AbstractOrmBaseEmbeddedMapping.this.getXmlAttributeMapping().getAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			if (this.mappingIsVirtual() && ! this.getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaOverride = AbstractOrmBaseEmbeddedMapping.this.getSpecifiedJavaAttributeOverrideNamed(attributeName);
				if (javaOverride != null) {
					return javaOverride.getColumn();
				}
			}
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return this.getTypeMapping().allAssociatedTableNames();
		}

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public String getDefaultTableName() {
			return this.getTypeMapping().getPrimaryTableName();
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, textRangeResolver, new EntityTableDescriptionProvider());
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmBaseEmbeddedMapping.this.getValidationTextRange();
		}

		protected OrmPersistentAttribute getPersistentAttribute() {
			return AbstractOrmBaseEmbeddedMapping.this.getPersistentAttribute();
		}

		protected boolean mappingIsVirtual() {
			return AbstractOrmBaseEmbeddedMapping.this.isVirtual();
		}
	}
}
