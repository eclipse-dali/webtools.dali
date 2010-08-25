/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaTypeMapping extends AbstractJavaJpaContextNode
	implements JavaTypeMapping
{
	protected JavaResourcePersistentType javaResourcePersistentType;


	protected AbstractJavaTypeMapping(JavaPersistentType parent) {
		super(parent);
	}
	
	@Override
	public JavaPersistentType getParent() {
		return (JavaPersistentType) super.getParent();
	}
	
	protected JavaResourcePersistentType getResourcePersistentType() {
		return this.javaResourcePersistentType;
	}
	
	protected Annotation getResourceMappingAnnotation() {
		return this.javaResourcePersistentType.getAnnotation(getAnnotationName());
	}

	//***************** TypeMapping impl ***************************************
	
	public String getName() {
		return getPersistentType().getName();
	}

	public JavaPersistentType getPersistentType() {
		return getParent();
	}
	
	/* default implementation */
	public JavaPersistentType getIdClass() {
		return null;
	}
	
	public String getPrimaryTableName() {
		return null;
	}

	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return null;
	}

	public org.eclipse.jpt.db.Table getDbTable(String tableName) {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<Table> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}
	
	public TypeMapping getSuperTypeMapping() {
		return (getPersistentType().getSuperPersistentType() == null) ?
				null 
				: getPersistentType().getSuperPersistentType().getMapping();
	}
	
	/**
	 * Return an iterator of TypeMappings, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	public Iterator<TypeMapping> inheritanceHierarchy() {
		return this.getInheritanceHierarchy().iterator();
	}

	public Iterable<TypeMapping> getInheritanceHierarchy() {
		return new TransformationIterable<PersistentType, TypeMapping>(CollectionTools.iterable(getPersistentType().inheritanceHierarchy())) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}
	
	public Iterator<JavaAttributeMapping> attributeMappings() {
		return new TransformationIterator<JavaPersistentAttribute, JavaAttributeMapping>(getPersistentType().attributes()) {
			@Override
			protected JavaAttributeMapping transform(JavaPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}
	
	public Iterable<JavaAttributeMapping> getAttributeMappings(final String mappingKey) {
		return new FilteringIterable<JavaAttributeMapping>(CollectionTools.collection(attributeMappings())) {
			@Override
			protected boolean accept(JavaAttributeMapping o) {
				return StringTools.stringsAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Iterator<AttributeMapping> allAttributeMappings() {
		return new CompositeIterator<AttributeMapping>(
			new TransformationIterator<TypeMapping, Iterator<AttributeMapping>>(this.inheritanceHierarchy()) {
				@Override
				protected Iterator<AttributeMapping> transform(TypeMapping typeMapping) {
					return typeMapping == null ? EmptyIterator.<AttributeMapping> instance() : typeMapping.attributeMappings();
				}
		});
	}
	
	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(allAttributeMappings())) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return StringTools.stringsAreEqual(o.getKey(), mappingKey);
			}
		};
	}
	
	public Iterator<String> overridableAttributeNames() {
		return new CompositeIterator<String>(
			new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings()) {
				@Override
				protected Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAttributeMappingNames();
				}
			});
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAttributeNames();
			}
		});
	}
	
	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(attributeMappings())) {
			Column resolvedColumn = attributeMapping.resolveOverriddenColumn(attributeName);
			if (resolvedColumn != null) {
				return resolvedColumn;
			}
		}
		return null;
	}
	
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(attributeMappings())) {
			RelationshipReference resolvedRelationshipReference = attributeMapping.resolveRelationshipReference(attributeName);
			if (resolvedRelationshipReference != null) {
				return resolvedRelationshipReference;
			}
		}
		return null;
	}
	
	public Iterator<String> overridableAssociationNames() {
		return new CompositeIterator<String>(
			new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings()) {
				@Override
				protected Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAssociationMappingNames();
				}
			});
	}
	
	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}
	
	
	//******************** updating *********************
	public void initialize(JavaResourcePersistentType jrpt) {
		this.javaResourcePersistentType = jrpt;
	}

	public void update(JavaResourcePersistentType jrpt) {
		this.javaResourcePersistentType = jrpt;
	}
	
	//******************** validation *********************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		validateType(messages, reporter, astRoot);
	}

	protected void validateType(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.buildTypeMappingValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildTypeMappingValidator(CompilationUnit astRoot) {
		return new GenericTypeMappingValidator(this, this.javaResourcePersistentType, buildTextRangeResolver(astRoot));
	}

	protected TypeMappingTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaTypeMappingTextRangeResolver(this, astRoot);
	}
	
	public boolean shouldValidateAgainstDatabase() {
		return getPersistenceUnit().shouldValidateAgainstDatabase();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getResourceMappingAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getPersistentType().getValidationTextRange(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}
}
