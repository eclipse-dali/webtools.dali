/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericTypeMappingValidator;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java type mapping
 */
public abstract class AbstractJavaTypeMapping<A extends Annotation>
	extends AbstractJavaContextModel<JavaPersistentType>
	implements JavaTypeMapping
{
	// this can be null for a "null" type mapping
	protected final A mappingAnnotation;


	protected AbstractJavaTypeMapping(JavaPersistentType parent, A mappingAnnotation) {
		super(parent);
		this.mappingAnnotation = mappingAnnotation;
	}


	// ********** misc **********

	public JavaPersistentType getPersistentType() {
		return this.parent;
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
		return IterableTools.transform(this.getPersistentType().getInheritanceHierarchy(), PersistentType.MAPPING_TRANSFORMER);
	}

	/**
	 * Return the type mapping's "persistence" ancestors,
	 * <em>excluding</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	protected Iterable<TypeMapping> getAncestors() {
		return IterableTools.transform(this.getPersistentType().getAncestors(), PersistentType.MAPPING_TRANSFORMER);
	}

	public InheritanceType getInheritanceStrategy() {
		return null;
	}

	public boolean isRootEntity() {
		return false;
	}

	public Entity getRootEntity() {
		return null;
	}


	// ********** attribute mappings **********

	public Iterable<JavaAttributeMapping> getAttributeMappings() {
		return IterableTools.downCast(IterableTools.transform(this.getPersistentType().getAttributes(), PersistentAttribute.MAPPING_TRANSFORMER));
	}

	public Iterable<JavaAttributeMapping> getAttributeMappings(String mappingKey) {
		return IterableTools.filter(this.getAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}

	public Iterable<AttributeMapping> getAllAttributeMappings() {
		return IterableTools.children(this.getNonNullInheritanceHierarchy(), TypeMappingTools.ATTRIBUTE_MAPPINGS_TRANSFORMER);
	}

	protected Iterable<TypeMapping> getNonNullInheritanceHierarchy() {
		return IterableTools.removeNulls(this.getInheritanceHierarchy());
	}

	public Iterable<AttributeMapping> getAllAttributeMappings(final String mappingKey) {
		return IterableTools.filter(this.getAllAttributeMappings(), new AttributeMapping.KeyEquals(mappingKey));
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterable<AttributeMapping> getNonTransientAttributeMappings() {
		return new FilteringIterable<AttributeMapping>(this.getAllAttributeMappings(), AttributeMapping.IS_NOT_TRANSIENT);
	}


	// ********** attribute overrides **********

	public Iterable<String> getOverridableAttributeNames() {
		return IterableTools.children(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAttributeNames() {
		return IterableTools.children(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER);
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
		return IterableTools.children(this.getAttributeMappings(), AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	public Iterable<String> getAllOverridableAssociationNames() {
		return IterableTools.children(this.getInheritanceHierarchy(), TypeMappingTools.OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER);
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


	// ********** generators **********

	public Iterable<Generator> getGenerators() {
		return IterableTools.children(this.getAttributeMappings(), AttributeMapping.GENERATORS_TRANSFORMER);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateType(messages, reporter);
	}

	protected void validateType(List<IMessage> messages, IReporter reporter) {
		if (this.getJavaResourceType() != null) {
			this.buildTypeMappingValidator().validate(messages, reporter);
		}
	}

	/**
	 * Pre-condition: the mapping's {@link #getJavaResourceType() Java resource
	 * type} is not <code>null</code>.
	 */
	protected JptValidator buildTypeMappingValidator() {
		return new GenericTypeMappingValidator(this);
	}

	public boolean validatesAgainstDatabase() {
		return this.getPersistenceUnit().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.mappingAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getPersistentType().getValidationTextRange();
	}
}
