/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaBaseEmbeddedMapping<T extends Annotation>
	extends AbstractJavaAttributeMapping<T>
	implements JavaBaseEmbeddedMapping
{
	protected final JavaAttributeOverrideContainer attributeOverrideContainer;
	
	private Embeddable targetEmbeddable;
	
	
	protected AbstractJavaBaseEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.attributeOverrideContainer = 
				this.getJpaFactory().buildJavaAttributeOverrideContainer(
					this, 
					buildAttributeOverrideContainerOwner());
	}
	
	
	protected JavaAttributeOverrideContainer.Owner buildAttributeOverrideContainerOwner() {
		return new AttributeOverrideContainerOwner();
	}
	
	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	public TypeMapping getOverridableTypeMapping() {
		return this.targetEmbeddable;
	}
	
	
	//****************** JavaAttributeMapping implementation *******************

	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.ATTRIBUTE_OVERRIDE);
		names.add(JPA.ATTRIBUTE_OVERRIDES);
	}

	public Embeddable getTargetEmbeddable() {
		return this.targetEmbeddable;
	}
	
	protected void setTargetEmbeddable_(Embeddable newTargetEmbeddable) {
		Embeddable oldTargetEmbeddable = this.targetEmbeddable;
		this.targetEmbeddable = newTargetEmbeddable;
		firePropertyChanged(TARGET_EMBEDDABLE_PROPERTY, oldTargetEmbeddable, newTargetEmbeddable);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.attributeOverrideContainer.initialize(this.getResourcePersistentAttribute());
		this.targetEmbeddable = this.getPersistentAttribute().getEmbeddable();
	}
	
	@Override
	protected void update() {
		super.update();
		setTargetEmbeddable_(this.getPersistentAttribute().getEmbeddable());
		this.attributeOverrideContainer.update(this.getResourcePersistentAttribute());
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
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		
		result = getAttributeOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	//******** Validation ******************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (validateTargetEmbeddable(messages, reporter, astRoot)) {
			validateOverrides(messages, reporter, astRoot);
		}
	}

	protected boolean validateTargetEmbeddable(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
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
							this.getValidationTextRange(astRoot)));
			}
			return false;
		}
		return true;
	}

	protected void validateOverrides(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		getAttributeOverrideContainer().validate(messages, reporter, astRoot);
	}

	//********** AttributeOverrideContainer.Owner implementation *********	
	
	protected class AttributeOverrideContainerOwner
		implements JavaAttributeOverrideContainer.Owner
	{
		public TypeMapping getTypeMapping() {
			return AbstractJavaBaseEmbeddedMapping.this.getTypeMapping();
		}
		
		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaBaseEmbeddedMapping.this.getOverridableTypeMapping();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ?
				EmptyIterator.<String>instance()
				: allOverridableAttributeNames_(typeMapping);
		}
		
		/* assumes the type mapping is not null */
		protected Iterator<String> allOverridableAttributeNames_(TypeMapping typeMapping) {
			return typeMapping.allOverridableAttributeNames();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}
		
		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public String getPossiblePrefix() {
			return null;
		}
		
		public String getWritePrefix() {
			return null;
		}
		
		public boolean isRelevant(String overrideName) {
			//no prefix, so always true
			return true;
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaBaseEmbeddedMapping.this.getValidationTextRange(astRoot);
		}
		
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnTableNotValidMessage(override.getName(), column, textRange);
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
		
		protected IMessage buildVirtualColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
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
		
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}
	}
}
