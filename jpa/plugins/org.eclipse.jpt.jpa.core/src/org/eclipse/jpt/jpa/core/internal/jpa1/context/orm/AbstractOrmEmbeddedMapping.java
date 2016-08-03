/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideInverseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideJoinTableValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> embedded mapping
 */
public abstract class AbstractOrmEmbeddedMapping<X extends XmlEmbedded>
	extends AbstractOrmBaseEmbeddedMapping<X>
	implements OrmEmbeddedMapping2_0
{
	protected final OrmAssociationOverrideContainer associationOverrideContainer;


	protected AbstractOrmEmbeddedMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.associationOverrideContainer = this.buildAssociationOverrideContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.associationOverrideContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.associationOverrideContainer.update(monitor);
	}


	// ********** association override container **********

	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	protected OrmAssociationOverrideContainer buildAssociationOverrideContainer() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmAssociationOverrideContainer(this.buildAssociationOverrideContainerParentAdapter()) :
				new GenericOrmAssociationOverrideContainer(this);
	}

	protected OrmAssociationOverrideContainer2_0.ParentAdapter buildAssociationOverrideContainerParentAdapter() {
		return new AssociationOverrideContainerParentAdapter();
	}


	// ********** embedded mappings **********

	/**
	 * This is only to build the choices for a "mapped by" setting in a
	 * relationship mapping. JPA 2.0 does not support relationship mappings
	 * in an embedded ID class; so we only put this logic here.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Iterable<String> getAllMappingNames() {
		return this.isJpa2_0Compatible() ?
				IterableTools.concatenate(super.getAllMappingNames(), this.getAllEmbeddableAttributeMappingNames()) :
				super.getAllMappingNames();
	}

	protected Iterable<String> getAllEmbeddableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<RelationshipMapping> allOverridableAssociations() {
		return (this.targetEmbeddable != null) ?
				IterableTools.<AttributeMapping, RelationshipMapping>downcast(this.getAllOverridableAssociations_()) :
				IterableTools.<RelationshipMapping>emptyIterable();
	}

	protected Iterable<AttributeMapping> getAllOverridableAssociations_() {
		return IterableTools.filter(this.targetEmbeddable.getAttributeMappings(), AttributeMapping.OVERRIDABLE_ASSOCIATION_MAPPING_PREDICATE);
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String attributeName) {
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(attributeName);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		return this.isJpa2_0Compatible() ? this.resolveAttributeMapping_(attributeName) : null;
	}

	protected AttributeMapping resolveAttributeMapping_(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		// recurse into the embeddable mappings
		for (AttributeMapping mapping : this.getEmbeddableAttributeMappings()) {
			AttributeMapping resolvedMapping = mapping.resolveAttributeMapping(attributeName);
			if (resolvedMapping != null) {
				return resolvedMapping;
			}
		}
		return null;
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 80;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes xmlAttributes) {
		xmlAttributes.getEmbeddeds().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes xmlAttributes) {
		xmlAttributes.getEmbeddeds().remove(this.xmlAttributeMapping);
	}

	@Override
	public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
		return this.isJpa2_0Compatible() ? this.resolveOverriddenRelationship_(attributeName) : null;
	}

	protected SpecifiedRelationship resolveOverriddenRelationship_(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		SpecifiedAssociationOverride override = this.associationOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getRelationship() : this.resolveOverriddenRelationshipInTargetEmbeddable(attributeName);
	}

	protected SpecifiedRelationship resolveOverriddenRelationshipInTargetEmbeddable(String attributeName) {
		return (this.targetEmbeddable == null) ? null : this.targetEmbeddable.resolveOverriddenRelationship(attributeName);
	}

	@Override
	protected OrmAttributeOverrideContainer.ParentAdapter buildAttributeOverrideContainerParentAdapter() {
		return new AttributeOverrideContainerParentAdapter();
	}


	// ********** validation **********

	@Override
	protected void validateOverrides(List<IMessage> messages, IReporter reporter) {
		super.validateOverrides(messages, reporter);
		this.associationOverrideContainer.validate(messages, reporter);
	}

	// ********** completion proposals **********
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.associationOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** attribute override container parent adapter *********

	public class AttributeOverrideContainerParentAdapter
		extends AbstractOrmBaseEmbeddedMapping<XmlEmbedded>.AttributeOverrideContainerParentAdapter
	{
		// nothing yet
	}


	// ********** association override container parent adapter **********

	public class AssociationOverrideContainerParentAdapter
		implements OrmAssociationOverrideContainer2_0.ParentAdapter
	{
		public JpaContextModel getOverrideContainerParent() {
			return AbstractOrmEmbeddedMapping.this;
		}

		protected String getMappingName() {
			return AbstractOrmEmbeddedMapping.this.getName();
		}

		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmEmbeddedMapping.this.getTypeMapping();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmEmbeddedMapping.this.getTargetEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.getAllOverridableAssociationNames() : EmptyIterable.<String>instance();
		}

		public Iterable<String> getJavaOverrideNames() {
			return null;
		}

		public EList<XmlAssociationOverride> getXmlOverrides() {
			return AbstractOrmEmbeddedMapping.this.getXmlAttributeMapping().getAssociationOverrides();
		}

		public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
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

		public TextRange getValidationTextRange() {
			return AbstractOrmEmbeddedMapping.this.getValidationTextRange();
		}

		public JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new AssociationOverrideValidator(this.getPersistentAttribute(), (AssociationOverride) override, (AssociationOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter parentAdapter) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), (AssociationOverride) override, (JoinColumn) column, (JoinColumn.ParentAdapter) parentAdapter, new EntityTableDescriptionProvider());
		}

		public JpaValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), override, column, parentAdapter, new JoinTableTableDescriptionProvider());
		}

		public JpaValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
			return new AssociationOverrideInverseJoinColumnValidator(this.getPersistentAttribute(), override, column, parentAdapter, new JoinTableTableDescriptionProvider());
		}

		public JpaValidator buildJoinTableValidator(AssociationOverride override, Table table) {
			return new AssociationOverrideJoinTableValidator(this.getPersistentAttribute(), override, (JoinTable) table);
		}

		protected OrmSpecifiedPersistentAttribute getPersistentAttribute() {
			return AbstractOrmEmbeddedMapping.this.getPersistentAttribute();
		}
	}
}
