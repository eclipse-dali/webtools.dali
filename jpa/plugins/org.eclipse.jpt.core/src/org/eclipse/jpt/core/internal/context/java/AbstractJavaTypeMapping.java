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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


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
	
	protected Annotation getResourceMappingAnnotation() {
		return this.javaResourcePersistentType.getAnnotation(getAnnotationName());
	}

	//***************** TypeMapping impl ***************************************
	
	public JavaPersistentType getPersistentType() {
		return getParent();
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
	
	public boolean specifiesPrimaryKey() {
		// default implementation
		return false;
	}
	
	/**
	 * Return whether an ancestor class has defined a primary key
	 */
	protected boolean primaryKeyIsDefinedOnAncestor() {
		for (TypeMapping each : CollectionTools.iterable(inheritanceHierarchy())) {
			if (each != this && each.specifiesPrimaryKey()) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean hasNoPrimaryKeyAttribute() {
		return ! this.hasPrimaryKeyAttribute();
	}
	
	protected boolean hasPrimaryKeyAttribute() {
		for (Iterator<PersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isPrimaryKeyAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return all attributes defined on this type mapping that define the type's primary key
	 */
	protected Iterable<JavaPersistentAttribute> getPrimaryKeyAttributes() {
		return new FilteringIterable<JavaPersistentAttribute>(
				CollectionTools.collection(getPersistentType().attributes())) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return o.isPrimaryKeyAttribute();
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

	public Iterator<AttributeMapping> allAttributeMappings() {
		return new CompositeIterator<AttributeMapping>(
			new TransformationIterator<TypeMapping, Iterator<AttributeMapping>>(this.inheritanceHierarchy()) {
				@Override
				protected Iterator<AttributeMapping> transform(TypeMapping typeMapping) {
					return typeMapping == null ? EmptyIterator.<AttributeMapping> instance() : typeMapping.attributeMappings();
				}
		});
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
