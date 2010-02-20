/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

/**
 * JPA attribute mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AttributeMapping
	extends JpaContextNode
{
	PersistentAttribute getPersistentAttribute();
	
	String getName();

	boolean isDefault();

	/**
	 * Return a unique key for the attribute mapping.  If this is defined in
	 * an extension they should be equal.
	 */
	String getKey();
	
	/**
	 * Return whether the "attribute" mapping can be overridden.
	 * Must be a {@link ColumnMapping}
	 */
	boolean isOverridableAttributeMapping();

	/**
	 * Return whether the "association" mapping can be overridden.
	 * Must be a {@link RelationshipMapping}
	 */
	boolean isOverridableAssociationMapping();

	/**
	 * Return the mapping for the attribute mapping's attribute's type.
	 */
	TypeMapping getTypeMapping();

	/**
	 * If the mapping is for a primary key column, return the column's name,
	 * otherwise return null.
	 */
	String getPrimaryKeyColumnName();

	/**
	 * Return whether this mapping is the owning side of the relationship.
	 * Either this is a unidirectional mapping or it is the owning side of a
	 * bidirectional relationship. If bidirectional, the owning side is the
	 * side that does not specify 'mappedBy'. The owning side is the side where
	 * the join table would be specified. If this returns true then the mapping
	 * will be a RelationshipMapping
	 */
	boolean isRelationshipOwner();

	/**
	 * Return whether the given mapping manages a relationship with this mapping
	 */
	boolean isOwnedBy(AttributeMapping mapping);
	
	/**
	 * Return whether any database metadata specific validation should occur.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean shouldValidateAgainstDatabase();

	/**
	 * This is used for mappedBy choices in a relationship mapping.
	 * Typically this will just be a single element iterator with the name of the mapping.
	 * In a 2.0 project, an embedded mapping should return its own name as well as 
	 * the name of its target Embeddable's mappings with the embedded mapping name 
	 * prepended : "embedded", "embedded.foo", "embedded.bar".
	 */
	Iterator<String> allMappingNames();
	
	/**
	 * This is used to determine the virtual attribute overrides for an embedded mapping
	 * or an entity. Return an Iterator of all attribute names that can be overridden.
	 * In a 2.0 project this will include nested attributes that are overridable
	 * @see isOverridableAttributeMapping()
	 */
	Iterator<String> allOverrideableAttributeMappingNames();
	
	/**
	 * This is used to determine the virtual association overrides for an embedded mapping
	 * or an entity. Return an Iterator of all association names that can be overridden.
	 * In a 2.0 project this will include nested associations that are overridable
	 * @see isOverridableAssociationMapping()
	 */
	Iterator<String> allOverrideableAssociationMappingNames();

	/**
	 * Returns this attribute mapping if its name matches the name.
	 * In 2.0 this name could use dot-notation for nested mappings.
	 * 2.0 Embedded mappings will have to parse this name and return the appropriate 
	 * nested attribute mapping.
	 */
	AttributeMapping resolveAttributeMapping(String name);
	
	/**
	 * Returns the Column of the overridable attribute mapping with the given 
	 * attribute name. In 2.0 this name could use dot-notation for nested mappings.
	 */
	Column resolveOverriddenColumn(String attributeName);

	RelationshipReference resolveRelationshipReference(String attributeName);

}
