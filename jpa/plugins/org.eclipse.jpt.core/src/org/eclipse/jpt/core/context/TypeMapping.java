/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.db.Schema;

/**
 * type mapping:<ul>
 * <li>entity
 * <li>mapped superclass
 * <li>embeddable
 * </ul>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface TypeMapping
	extends JpaContextNode
{
	PersistentType getPersistentType();

	/**
	 * Return a unique key for the type mapping. If this is defined in an
	 * extension they should be equal.
	 */
	String getKey();

	/**
	 * Return the name, specified or default if not specified.
	 */
	String getName();

	boolean isMapped();
	
	/**
	 * Return the resolved id class specified on this type mapping, null otherwise
	 */
	JavaPersistentType getIdClass();
	

	// ********** inheritance **********

	/**
	 * Return the type mapping of this type mapping's super type.
	 * Return null if this is the root.
	 */
	TypeMapping getSuperTypeMapping();
	
	/**
	 * Return the type mapping's "persistence" inheritance hierarchy,
	 * <em>including</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterator<TypeMapping> inheritanceHierarchy();
	

	// ********** tables **********

	/**
	 * Return the type mapping's primary table name.
	 * Return null if a primary table is not applicable.
	 */
	String getPrimaryTableName();

	/**
	 * Return the type mapping's primary database table.
	 * Return null if a primary table is not applicable.
	 */
	org.eclipse.jpt.jpa.db.Table getPrimaryDbTable();

	Schema getDbSchema();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables.
	 */
	Iterator<ReadOnlyTable> associatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables, as well as all inherited
	 * "associated" tables.
	 */
	Iterator<ReadOnlyTable> allAssociatedTables();

	/**
	 * Return the names of the type mapping's "associated" tables, which
	 * includes the primary table and the collection of secondary tables, as
	 * well as all the inherited "associated" tables.
	 */
	Iterator<String> allAssociatedTableNames();

	/**
	 * Return the resolved associated db table with the specified name.
	 */
	org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName);

	/**
	 * Return whether the specified table is invalid for any annotations
	 * associated with the type mapping.
	 */
	boolean tableNameIsInvalid(String tableName);
	

	// ********** mappings **********

	/**
	 * A convenience method for getting the attribute mappings from PersistentType.attributes()
	 */
	<T extends AttributeMapping> Iterator<T> attributeMappings();
	
	/**
	 * Return attribute mappings of a particular mapping type that are declared on this type mapping
	 */
	<T extends AttributeMapping> Iterable<T> getAttributeMappings(String mappingKey);
	
	/**
	 * Return all the attribute mappings in the type mapping's
	 * inheritance hierarchy.
	 */
	Iterator<AttributeMapping> allAttributeMappings();

	/**
	 * Return attribute mappings of a particular mapping type that are declared anywhere on this 
	 * type mapping's hierarchy
	 */
	<T extends AttributeMapping> Iterable<T> getAllAttributeMappings(String mappingKey);
	
	/**
	 * Return whether the given attribute mapping key is valid for this
	 * particular type mapping (for example, id's are not valid for an
	 * embeddable type mapping)
	 */
	boolean attributeMappingKeyAllowed(String attributeMappingKey);


	// ********** attribute overrides **********

	/**
	 * Return an Iterator of attribute names that can be overridden by a 
	 * sub type mapping.
	 */
	Iterator<String> overridableAttributeNames();

	/**
	 * Return an Iterator of all attribute names that can be overridden in this
	 * type mapping.
	 */
	Iterator<String> allOverridableAttributeNames();
	
	/**
	 * Return the column of the overridable attribute mapping (or attribute
	 * override) with the specified attribute name.
	 * <p>
	 * In JPA 2.0 this name can use dot-notation to designate nested attributes
	 * in embedded attribute mapping's embeddable type mapping.
	 */
	Column resolveOverriddenColumn(String attributeName);


	// ********** association overrides **********

	/**
	 * Return an Iterator of associations names that can be overridden in this
	 * type mapping.
	 */
	Iterator<String> overridableAssociationNames();
	
	/**
	 * Return an Iterator of all associations names that can be overridden in this
	 * type mapping.
	 */
	Iterator<String> allOverridableAssociationNames();

	Relationship resolveOverriddenRelationship(String attributeName);


	// ********** validation **********

	/**
	 * Return whether any database metadata specific validation should occur.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean validatesAgainstDatabase();
}
