/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SubIteratorWrapper;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmEmbeddedMapping
	extends AbstractOrmBaseEmbeddedMapping<XmlEmbedded> 
	implements OrmEmbeddedMapping2_0
{
	protected OrmAssociationOverrideContainer associationOverrideContainer;

	public GenericOrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		super(parent, resourceMapping);
		this.associationOverrideContainer = 
			getXmlContextNodeFactory().
				buildOrmAssociationOverrideContainer(
					this,
					new AssociationOverrideContainerOwner());
	}
	
	@Override
	public void update() {
		super.update();
		getAssociationOverrideContainer().update();
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		getAssociationOverrideContainer().postUpdate();
	}

	@Override
	public JavaEmbeddedMapping2_0 getJavaEmbeddedMapping() {
		return (JavaEmbeddedMapping2_0) super.getJavaEmbeddedMapping();
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedMapping(this);
	}

	public int getXmlSequence() {
		return 80;
	}

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddeds().add(this.resourceAttributeMapping);
	}

	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddeds().remove(this.resourceAttributeMapping);
	}
	
	//only putting this in EmbeddedMapping since relationship mappings
	//defined within an embedded id class are not supported  by the 2.0 spec.
	//i.e. the mappedBy choices will not include attributes nested in an embedded mapping
	@Override
	public Iterator<String> allMappingNames() {
		return this.isJpa2_0Compatible() ?
				new CompositeIterator<String>(this.getName(),this.allEmbeddableAttributeMappingNames()) :
				super.allMappingNames();
	}

	protected Iterator<String> allEmbeddableAttributeMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allMappingNames();
				}
			}
		);
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String attributeName) {
		if (getName() == null) {
			return null;
		}
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(attributeName);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
						resolvedMapping = attributeMapping.resolveAttributeMapping(attributeName.substring(dotIndex + 1));
						if (resolvedMapping != null) {
							return resolvedMapping;
						}
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		if (getName() == null) {
			return null;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AssociationOverride override = getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getRelationshipReference();
					}
					if (this.getTargetEmbeddable() == null) {
						return null;
					}
					return this.getTargetEmbeddable().resolveRelationshipReference(attributeName);
				}
			}
		}
		return null;
	}
	
	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	protected JavaAssociationOverride getJavaAssociationOverrideNamed(String attributeName) {
		if (getJavaEmbeddedMapping() != null) {
			return getJavaEmbeddedMapping().getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		}
		return null;
	}
	
	
	//************* AttributeOverrideContainer.Owner implementation ********************
	
	public Iterator<String> allOverridableAssociationNames() {
		return new TransformationIterator<RelationshipMapping, String>(this.allOverridableAssociations()) {
			@Override
			protected String transform(RelationshipMapping overridableAssociation) {
				return overridableAssociation.getName();
			}
		};
	}

	public Iterator<RelationshipMapping> allOverridableAssociations() {
		return (this.getTargetEmbeddable() == null) ?
				EmptyIterator.<RelationshipMapping>instance() :
				new SubIteratorWrapper<AttributeMapping, RelationshipMapping>(this.allOverridableAssociations_());
	}

	protected Iterator<AttributeMapping> allOverridableAssociations_() {
		return new FilteringIterator<AttributeMapping>(this.getTargetEmbeddable().attributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return o.isOverridableAssociationMapping();
			}
		};
	}


	// ********** validation **********
	@Override
	protected void validateOverrides(List<IMessage> messages, IReporter reporter) {
		super.validateOverrides(messages, reporter);
		this.getAssociationOverrideContainer().validate(messages, reporter);
	}

	
	//********** OrmAssociationOverrideContainer.Owner implementation *********	
	
	class AssociationOverrideContainerOwner
		implements OrmAssociationOverrideContainer.Owner
	{
		public OrmTypeMapping getTypeMapping() {
			return GenericOrmEmbeddedMapping.this.getTypeMapping();
		}
		
		public TypeMapping getOverridableTypeMapping() {
			return GenericOrmEmbeddedMapping.this.getTargetEmbeddable();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAssociationNames();
		}
		
		public EList<XmlAssociationOverride> getResourceAssociationOverrides() {
			return GenericOrmEmbeddedMapping.this.resourceAttributeMapping.getAssociationOverrides();
		}
		
		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAssociationOverride javaAssociationOverride = getJavaAssociationOverrideNamed(associationOverrideName);
				if (javaAssociationOverride != null && !javaAssociationOverride.isVirtual()) {
					return javaAssociationOverride.getRelationshipReference();
				}
			}
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}
		
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmEmbeddedMapping.this.getValidationTextRange();
		}
		
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,
					new String[] {
						column.getTable(),
						column.getName(),
						JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
					column, 
					textRange
				);
		}
		
		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					GenericOrmEmbeddedMapping.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column,
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column,
				textRange
			);
		}
		
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					GenericOrmEmbeddedMapping.this.getName(), 
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
				);
		}
		
		public IMessage buildColumnUnresolvedReferencedColumnNameMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
				new String[] {
					GenericOrmEmbeddedMapping.this.getName(),
					overrideName,
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {
					overrideName,
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {GenericOrmEmbeddedMapping.this.getName(), overrideName},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {GenericOrmEmbeddedMapping.this.getName(), overrideName},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
	}
}
