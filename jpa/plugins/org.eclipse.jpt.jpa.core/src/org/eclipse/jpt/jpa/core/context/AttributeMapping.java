/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.MappingKeys;

/**
 * JPA attribute mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.0
 */
public interface AttributeMapping
	extends JpaContextNode
{
	/**
	 * Return the mapping's attribute (typically its parent node in the
	 * containment hierarchy).
	 */
	PersistentAttribute getPersistentAttribute();

	/**
	 * Return the mapping's name, which corresponds to the name of the
	 * associated attribute.
	 */
	String getName();
	Transformer<AttributeMapping, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<AttributeMapping, String>
	{
		@Override
		public String transform(AttributeMapping mapping) {
			return mapping.getName();
		}
	}

	/**
	 * Return whether the mapping is its attribute's <em>default</em> mapping
	 * (as opposed to its <em>specified</em> mapping).
	 */
	boolean isDefault();
		String DEFAULT_PROPERTY = "default"; //$NON-NLS-1$

	/**
	 * Return a unique key for the attribute mapping. If this is defined in
	 * an extension they should be equal.
	 */
	String getKey();

	class KeyEquals
		extends FilterAdapter<AttributeMapping>
	{
		private final String mappingKey;
		public KeyEquals(String mappingKey) {
			super();
			this.mappingKey = mappingKey;
		}
		@Override
		public boolean accept(AttributeMapping mapping) {
			return ObjectTools.equals(mapping.getKey(), this.mappingKey);
		}
	}

	Filter<AttributeMapping> IS_NOT_TRANSIENT = new IsNotTransient();
	public static class IsNotTransient
		extends FilterAdapter<AttributeMapping>
	{
		@Override
		public boolean accept(AttributeMapping mapping) {
			return mapping.getKey() != MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
		}
	}

	/**
	 * Return whether the "attribute" mapping can be overridden.
	 * The mapping must be a {@link ColumnMapping}.
	 */
	boolean isOverridableAttributeMapping();

	/**
	 * Return whether the "association" mapping can be overridden.
	 * The mapping must be a {@link RelationshipMapping}.
	 */
	boolean isOverridableAssociationMapping();

	Filter<AttributeMapping> IS_OVERRIDABLE_ASSOCIATION_MAPPING = new IsOverridableAssociationMapping();
	public static class IsOverridableAssociationMapping
		extends FilterAdapter<AttributeMapping>
	{
		@Override
		public boolean accept(AttributeMapping mapping) {
			return mapping.isOverridableAssociationMapping();
		}
	}

	/**
	 * Return the mapping for the type that contains the mapping's attribute.
	 */
	TypeMapping getTypeMapping();

	/**
	 * If the mapping is for a primary key column, return the column's name,
	 * otherwise return <code>null</code>.
	 */
	String getPrimaryKeyColumnName();

	/**
	 * Return whether this mapping is the owning side of a relationship.
	 * Either this is a unidirectional mapping or it is the owning side of a
	 * bidirectional relationship. If bidirectional, the owning side is the
	 * side that does <em>not</em> specify a "mapped by" attribute name.
	 * The owning side is the side where the join table is to be specified.
	 * If this returns <code>true</code> the mapping will be a
	 * {@link RelationshipMapping}.
	 */
	boolean isRelationshipOwner();

	/**
	 * Return whether the specified mapping manages a relationship with the
	 * mapping.
	 */
	boolean isOwnedBy(AttributeMapping mapping);

	/**
	 * Return whether any database metadata specific validation should occur.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean validatesAgainstDatabase();

	/**
	 * Return the relationship for the specified attribute.
	 */
	Relationship resolveOverriddenRelationship(String attributeName);

	/**
	 * Typically only ID mappings have generators.
	 */
	Iterable<Generator> getGenerators();
	Transformer<AttributeMapping, Iterable<Generator>> GENERATORS_TRANSFORMER = new GeneratorsTransformer();
	class GeneratorsTransformer
		extends TransformerAdapter<AttributeMapping, Iterable<Generator>>
	{
		@Override
		public Iterable<Generator> transform(AttributeMapping mapping) {
			return mapping.getGenerators();
		}
	}


	// ********** embedded mappings **********

	/**
	 * This is used for "mapped by" choices in a relationship mapping.
	 * Typically this will just be a single element iterator with the mapping's
	 * name.
	 * <p>
	 * In a JPA 2.0 project, an embedded mapping should return its own name as
	 * well as the name of its target embeddable's mappings with the embedded
	 * mapping name prepended, e.g.:<ul>
	 * <li><code>"embedded"</code>
	 * <li><code>"embedded.foo"</code>
	 * <li><code>"embedded.bar"</code>
	 * </ul>
	 */
	Iterable<String> getAllMappingNames();

	/**
	 * This is used to determine the virtual attribute overrides for an
	 * embedded mapping or entity. Return the names of all the attributes
	 * that can be overridden.
	 * <p>
	 * In a JPA 2.0 project this will include overridable nested attributes.
	 * @see #isOverridableAttributeMapping()
	 */
	Iterable<String> getAllOverridableAttributeMappingNames();

	/**
	 * This is used to determine the virtual association overrides for an
	 * embedded mapping or entity. Return the names of all the associations
	 * that can be overridden.
	 * <p>
	 * In a JPA 2.0 project this will include overridable nested associations.
	 * @see #isOverridableAssociationMapping()
	 */
	Iterable<String> getAllOverridableAssociationMappingNames();

	/**
	 * Return the mapping itself if its name matches the specified name.
	 * <p>
	 * In JPA 2.0 this name could use dot-notation for nested mappings.
	 * JPA 2.0 embedded mappings will have to parse this name and return the
	 * appropriate nested mapping.
	 */
	AttributeMapping resolveAttributeMapping(String name);

	/**
	 * Return the column of the overridable attribute mapping (or attribute
	 * override) with the specified attribute name.
	 * <p>
	 * In JPA 2.0 this name can use dot-notation to designate nested attributes
	 * in embedded attribute mapping's embeddable type mapping.
	 */
	Column resolveOverriddenColumn(String attributeName);
}
