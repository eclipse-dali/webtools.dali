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
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmBaseEmbeddedMapping<T extends AbstractXmlEmbedded> extends AbstractOrmAttributeMapping<T> implements OrmBaseEmbeddedMapping
{
	protected OrmAttributeOverrideContainer attributeOverrideContainer;

	private Embeddable targetEmbeddable;//TODO hmm, why no property change notification for setting this??
	
	protected AbstractOrmBaseEmbeddedMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.targetEmbeddable = embeddableFor(this.getJavaPersistentAttribute());
		this.attributeOverrideContainer =
			getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(
						this,
						new AttributeOverrideContainerOwner());
	}

	@Override
	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		this.attributeOverrideContainer.initializeFromAttributeOverrideContainer(oldMapping.getAttributeOverrideContainer());
	}

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected JavaAttributeOverride getJavaAttributeOverrideNamed(String attributeName) {
		if (getJavaEmbeddedMapping() != null) {
			return getJavaEmbeddedMapping().getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}	
	
	public Embeddable getTargetEmbeddable() {
		return this.targetEmbeddable;
	}
	
	protected void setTargetEmbeddable_(Embeddable newTargetEmbeddable) {
		Embeddable oldTargetEmbeddable = this.targetEmbeddable;
		this.targetEmbeddable = newTargetEmbeddable;
		firePropertyChanged(TARGET_EMBEDDABLE_PROPERTY, oldTargetEmbeddable, newTargetEmbeddable);
	}
	
	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		if (this.getTargetEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return this.getTargetEmbeddable().attributeMappings();
	}
	
	@Override
	public Iterator<String> allOverrideableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAttributeMappingNames() :
				super.allOverrideableAttributeMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAttributeMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAttributeMappingNames();
				}
			}
		);
	}

	@Override
	public Iterator<String> allOverrideableAssociationMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAssociationMappingNames() :
				super.allOverrideableAssociationMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAssociationMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAssociationMappingNames();
				}
			}
		);
	}
	
	protected Iterator<String> embeddableOverrideableMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings(), transformer))) 
		{
			@Override
			protected String transform(String next) {
				return getName() + '.' + next;
			}
		};
	}
	
	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		if (getName() == null) {
			return null;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AttributeOverride override = getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getColumn();
					}
					if (this.getTargetEmbeddable() == null) {
						return null;
					}
					return this.getTargetEmbeddable().resolveOverriddenColumn(attributeName);
				}
			}
		}
		return null;
	}

	public JavaBaseEmbeddedMapping getJavaEmbeddedMapping() {
		JavaPersistentAttribute jpa = this.getJavaPersistentAttribute();
		if ((jpa != null) && this.valuesAreEqual(jpa.getMappingKey(), this.getKey())) {
			return (JavaBaseEmbeddedMapping) jpa.getMapping();
		}
		return null;
	}

	
	@Override
	public void update() {
		super.update();
		setTargetEmbeddable_(embeddableFor(this.getJavaPersistentAttribute()));
		getAttributeOverrideContainer().update();
	}
	
	
	//******** Validation ******************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateTargetEmbeddable(messages, reporter);
		getAttributeOverrideContainer().validate(messages, reporter);
	}

	protected void validateTargetEmbeddable(List<IMessage> messages, IReporter reporter) {
		if (getTargetEmbeddable() == null) {
			String targetEmbeddableTypeName = getPersistentAttribute().getTypeName();
			// if the type isn't resolveable, there'll already be a java error
			if (targetEmbeddableTypeName != null) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TARGET_NOT_AN_EMBEDDABLE,
							new String[] {targetEmbeddableTypeName}, 
							this, 
							this.getValidationTextRange()));
			}
		}
	}

	//************ static methods ************
	
	public static Embeddable embeddableFor(JavaPersistentAttribute javaPersistentAttribute) {
		return (javaPersistentAttribute == null) ? null : javaPersistentAttribute.getEmbeddable();
	}
	
	
	//********** AttributeOverrideContainer.Owner implementation *********	
	
	class AttributeOverrideContainerOwner implements OrmAttributeOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTargetEmbeddable();
		}

		public EList<XmlAttributeOverride> getResourceAttributeOverrides() {
			return AbstractOrmBaseEmbeddedMapping.this.resourceAttributeMapping.getAttributeOverrides();
		}

		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTypeMapping();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaAttributeOverride = getJavaAttributeOverrideNamed(attributeOverrideName);
				if (javaAttributeOverride != null && !javaAttributeOverride.isVirtual()) {
					return javaAttributeOverride.getColumn();
				}
			}
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			return new VirtualXmlAttributeOverrideColumn(overridableColumn);
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

		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeUnresolvedColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideUnresolvedColumnNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualAttributeUnresolvedColumnTableNotValidMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					AbstractOrmBaseEmbeddedMapping.this.getName(), 
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideUnresolvedColumnNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
				);
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
					JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
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
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] {
					AbstractOrmBaseEmbeddedMapping.this.getName(), 
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
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column,
				textRange
			);
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmBaseEmbeddedMapping.this.getValidationTextRange();
		}
	}
}
