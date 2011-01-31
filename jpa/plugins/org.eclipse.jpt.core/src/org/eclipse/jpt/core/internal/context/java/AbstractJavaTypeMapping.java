/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NotNullFilter;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java type mapping
 */
public abstract class AbstractJavaTypeMapping<A extends Annotation>
	extends AbstractJavaJpaContextNode
	implements JavaTypeMapping
{
	// this can be null for a "null" type mapping
	protected final A mappingAnnotation;


	protected AbstractJavaTypeMapping(JavaPersistentType parent, A mappingAnnotation) {
		super(parent);
		this.mappingAnnotation = mappingAnnotation;
	}


	// ********** misc **********

	@Override
	public JavaPersistentType getParent() {
		return (JavaPersistentType) super.getParent();
	}

	public JavaPersistentType getPersistentType() {
		return this.getParent();
	}

	public JavaResourcePersistentType getResourcePersistentType() {
		return this.getPersistentType().getResourcePersistentType();
	}

	public String getName() {
		return this.getPersistentType().getName();
	}

	public A getMappingAnnotation() {
		return this.mappingAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}


	// ********** tables **********

	public String getPrimaryTableName() {
		return null;
	}

	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}

	public Iterator<ReadOnlyTable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ReadOnlyTable> allAssociatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allAssociatedTableNames() {
		return EmptyIterator.instance();
	}

	public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
		return null;
	}


	// ********** inheritance **********

	public TypeMapping getSuperTypeMapping() {
		PersistentType superPersistentType = this.getPersistentType().getSuperPersistentType();
		return (superPersistentType == null) ? null : superPersistentType.getMapping();
	}

	public Iterator<TypeMapping> inheritanceHierarchy() {
		return this.convertToMappings(this.getPersistentType().inheritanceHierarchy());
	}

	protected Iterable<TypeMapping> getInheritanceHierarchy() {
		return CollectionTools.iterable(this.inheritanceHierarchy());
	}

	/**
	 * Return the type mapping's "persistence" ancestors,
	 * <em>excluding</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	protected Iterator<TypeMapping> ancestors() {
		return this.convertToMappings(this.getPersistentType().ancestors());
	}

	protected Iterable<TypeMapping> getAncestors() {
		return CollectionTools.iterable(this.ancestors());
	}

	protected Iterator<TypeMapping> convertToMappings(Iterator<PersistentType> types) {
		return new TransformationIterator<PersistentType, TypeMapping>(types) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}


	// ********** attribute mappings **********

	public Iterator<JavaAttributeMapping> attributeMappings() {
		return new TransformationIterator<JavaPersistentAttribute, JavaAttributeMapping>(this.getPersistentType().attributes()) {
			@Override
			protected JavaAttributeMapping transform(JavaPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}

	public Iterable<JavaAttributeMapping> getAttributeMappings(final String mappingKey) {
		return new FilteringIterable<JavaAttributeMapping>(CollectionTools.collection(this.attributeMappings())) {
			@Override
			protected boolean accept(JavaAttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Iterator<AttributeMapping> allAttributeMappings() {
		return new CompositeIterator<AttributeMapping>(this.allAttributeMappingsLists());
	}

	protected Iterator<Iterator<AttributeMapping>> allAttributeMappingsLists() {
		return new TransformationIterator<TypeMapping, Iterator<AttributeMapping>>(this.nonNullInheritanceHierarchy(), TypeMappingTools.ATTRIBUTE_MAPPINGS_TRANSFORMER);
	}

	protected Iterator<TypeMapping> nonNullInheritanceHierarchy() {
		return new FilteringIterator<TypeMapping>(this.inheritanceHierarchy(), NotNullFilter.<TypeMapping>instance());
	}

	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(this.allAttributeMappings())) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}


	// ********** attribute overrides **********

	public Iterator<String> overridableAttributeNames() {
		return new CompositeIterator<String>(this.overridableAttributeNamesLists());
	}

	protected Iterator<Iterator<String>> overridableAttributeNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(this.allOverridableAttributeNamesLists());
	}

	protected Iterator<Iterator<String>> allOverridableAttributeNamesLists() {
		return new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
	}

	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(this.attributeMappings())) {
			Column column = attributeMapping.resolveOverriddenColumn(attributeName);
			if (column != null) {
				return column;
			}
		}
		return null;
	}


	// ********** association overrides **********

	public Iterator<String> overridableAssociationNames() {
		return new CompositeIterator<String>(this.overridableAssociationNamesLists());
	}

	protected Iterator<Iterator<String>> overridableAssociationNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.attributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(this.allOverridableAssociationNamesLists());
	}

	protected Iterator<Iterator<String>> allOverridableAssociationNamesLists() {
		return new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
	}

	public Relationship resolveOverriddenRelationship(String attributeName) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(this.attributeMappings())) {
			Relationship relationship = attributeMapping.resolveOverriddenRelationship(attributeName);
			if (relationship != null) {
				return relationship;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateType(messages, reporter, astRoot);
	}

	protected void validateType(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.buildTypeMappingValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildTypeMappingValidator(CompilationUnit astRoot) {
		return new GenericTypeMappingValidator(this, this.getResourcePersistentType(), buildTextRangeResolver(astRoot));
	}

	protected TypeMappingTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaTypeMappingTextRangeResolver(this, astRoot);
	}

	public boolean validatesAgainstDatabase() {
		return this.getPersistenceUnit().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.mappingAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getPersistentType().getValidationTextRange(astRoot);
	}
}
