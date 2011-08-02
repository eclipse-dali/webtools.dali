/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.jpa.db.Schema;
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

	public JavaResourceType getJavaResourceType() {
		return this.getPersistentType().getJavaResourceType();
	}

	public String getName() {
		return this.getPersistentType().getName();
	}

	public A getMappingAnnotation() {
		return this.mappingAnnotation;
	}

	public boolean attributeIsDerivedId(String attributeName) {
		return TypeMappingTools.attributeIsDerivedId(this, attributeName);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}


	// ********** tables **********

	public String getPrimaryTableName() {
		return null;
	}

	public org.eclipse.jpt.jpa.db.Table getPrimaryDbTable() {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}

	public Iterable<ReadOnlyTable> getAssociatedTables() {
		return EmptyIterable.instance();
	}

	public Iterable<ReadOnlyTable> getAllAssociatedTables() {
		return EmptyIterable.instance();
	}

	public Iterable<String> getAllAssociatedTableNames() {
		return EmptyIterable.instance();
	}

	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		return null;
	}


	// ********** inheritance **********

	public TypeMapping getSuperTypeMapping() {
		PersistentType superPersistentType = this.getPersistentType().getSuperPersistentType();
		return (superPersistentType == null) ? null : superPersistentType.getMapping();
	}

	public Iterable<TypeMapping> getInheritanceHierarchy() {
		return this.convertToMappings(this.getPersistentType().getInheritanceHierarchy());
	}

	/**
	 * Return the type mapping's "persistence" ancestors,
	 * <em>excluding</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	protected Iterable<TypeMapping> getAncestors() {
		return this.convertToMappings(this.getPersistentType().getAncestors());
	}

	protected Iterable<TypeMapping> convertToMappings(Iterable<PersistentType> types) {
		return new TransformationIterable<PersistentType, TypeMapping>(types) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}


	// ********** attribute mappings **********

	public Iterable<JavaAttributeMapping> getAttributeMappings() {
		return new TransformationIterable<JavaPersistentAttribute, JavaAttributeMapping>(this.getPersistentType().getAttributes()) {
			@Override
			protected JavaAttributeMapping transform(JavaPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}

	public Iterable<JavaAttributeMapping> getAttributeMappings(final String mappingKey) {
		return new FilteringIterable<JavaAttributeMapping>(this.getAttributeMappings()) {
			@Override
			protected boolean accept(JavaAttributeMapping o) {
				return Tools.valuesAreEqual(o.getKey(), mappingKey);
			}
		};
	}

	public Iterable<AttributeMapping> getAllAttributeMappings() {
		return new CompositeIterable<AttributeMapping>(this.getAllAttributeMappingsLists());
	}

	protected Iterable<Iterable<AttributeMapping>> getAllAttributeMappingsLists() {
		return new TransformationIterable<TypeMapping, Iterable<AttributeMapping>>(this.getNonNullInheritanceHierarchy(), TypeMappingTools.ATTRIBUTE_MAPPINGS_TRANSFORMER);
	}

	protected Iterable<TypeMapping> getNonNullInheritanceHierarchy() {
		return new FilteringIterable<TypeMapping>(this.getInheritanceHierarchy(), NotNullFilter.<TypeMapping>instance());
	}

	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return new FilteringIterable<AttributeMapping>(CollectionTools.collection(this.getAllAttributeMappings())) {
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

	public Iterable<String> getOverridableAttributeNames() {
		return new CompositeIterable<String>(this.getOverridableAttributeNamesLists());
	}

	protected Iterable<Iterable<String>> getOverridableAttributeNamesLists() {
		return new TransformationIterable<AttributeMapping, Iterable<String>>(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAttributeNames() {
		return new CompositeIterable<String>(this.allOverridableAttributeNamesLists());
	}

	protected Iterable<Iterable<String>> allOverridableAttributeNamesLists() {
		return new TransformationIterable<TypeMapping, Iterable<String>>(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
	}

	public Column resolveOverriddenColumn(String attributeName) {
		for (AttributeMapping attributeMapping : this.getAttributeMappings()) {
			Column column = attributeMapping.resolveOverriddenColumn(attributeName);
			if (column != null) {
				return column;
			}
		}
		return null;
	}


	// ********** association overrides **********

	public Iterable<String> getOverridableAssociationNames() {
		return new CompositeIterable<String>(this.getOverridableAssociationNamesLists());
	}

	protected Iterable<Iterable<String>> getOverridableAssociationNamesLists() {
		return new TransformationIterable<AttributeMapping, Iterable<String>>(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAssociationNames() {
		return new CompositeIterable<String>(this.getAllOverridableAssociationNamesLists());
	}

	protected Iterable<Iterable<String>> getAllOverridableAssociationNamesLists() {
		return new TransformationIterable<TypeMapping, Iterable<String>>(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
	}

	public Relationship resolveOverriddenRelationship(String attributeName) {
		for (AttributeMapping attributeMapping : this.getAttributeMappings()) {
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
		return new GenericTypeMappingValidator(this, this.getJavaResourceType(), buildTextRangeResolver(astRoot));
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
