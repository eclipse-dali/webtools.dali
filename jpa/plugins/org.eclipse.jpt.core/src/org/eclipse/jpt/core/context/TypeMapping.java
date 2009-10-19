/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.db.Schema;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 */
public interface TypeMapping extends JpaContextNode {
	/**
	 * Return a unique key for the ITypeMapping. If this is defined in an
	 * extension they should be equal.
	 */
	String getKey();

	PersistentType getPersistentType();

	boolean isMapped();

	/**
	 * Return the type mapping's primary table name, null if a primary table
	 * does not apply.
	 */
	String getPrimaryTableName();

	/**
	 * Return the type mapping's primary database table.
	 */
	org.eclipse.jpt.db.Table getPrimaryDbTable();

	Schema getDbSchema();

	/**
	 * Return the type mapping's "persistence" inheritance hierarchy,
	 * <em>including</em> the type mapping itself.
	 * The returned iterator will return elements infinitely if the hierarchy
	 * has a loop.
	 */
	Iterator<TypeMapping> inheritanceHierarchy();
	
	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables.
	 */
	Iterator<Table> associatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables, as well as all inherited
	 * "associated" tables.
	 */
	Iterator<Table> associatedTablesIncludingInherited();

	/**
	 * Return the identifiers of the type mapping's "associated" tables, which
	 * includes the primary table and the collection of secondary tables, as
	 * well as all the inherited "associated" tables.
	 */
	Iterator<String> associatedTableNamesIncludingInherited();

	/**
	 * return the resolved associated db table with the passed in name
	 */
	org.eclipse.jpt.db.Table getDbTable(String tableName);

	/**
	 * Return whether the specified table is invalid for any annotations
	 * associated with the type mapping.
	 */
	boolean tableNameIsInvalid(String tableName);
	

	/**
	 * A convenience methods for getting the attribute mappings from PersistentType.attributes()
	 */
	<T extends AttributeMapping> ListIterator<T> attributeMappings();
	
	/**
	 * Return all the attribute mappings in the type mapping's
	 * inheritance hierarchy.
	 */
	Iterator<AttributeMapping> allAttributeMappings();

	/**
	/**
	 * Return an Iterator of attribute names that can be overridden by a 
	 * sub type mapping.
	 * @see
	 * {@link TypeMapping#overridableAttributes()}
	 */
	Iterator<String> overridableAttributeNames();

	/**
	 * Return an Iterator of all attribute names that can be overridden in this
	 * type mapping.
	 * @see
	 * {@link TypeMapping#allOverridableAttributes()}
	 */
	Iterator<String> allOverridableAttributeNames();
	
	/**
	 * Returns the Column of the overridable attribute mapping with the given 
	 * attribute name. In 2.0 this name could use dot-notation for nested mappings.
	 */
	Column resolveOverridenColumn(String attributeName);

	/**
	 * Return an Iterator of associations that can be overridden by a 
	 * sub type mapping. The platform and joining strategy are used to determine
	 * if it is an overridable association.
	 */
	<T extends RelationshipMapping> Iterator<T> overridableAssociations();

	/**
	 * Return an Iterator of association names that can be overridden by a 
	 * sub type mapping.
	 * @see
	 * {@link TypeMapping#overridableAssociations()}
	 */
	Iterator<String> overridableAssociationNames();

	/**
	 * Return an Iterator of all associations that can be overridden in this
	 * type mapping.  The platform and joining strategy are used to determine
	 * if it is an overridable association.
	 * 
	 * See
	 * {@link TypeMapping#overridableAssociations()} and
	 * {@link PersistentType#inheritanceHierarchy()}
	 */
	Iterator<RelationshipMapping> allOverridableAssociations();
	
	/**
	 * Return an Iterator of all associations names that can be overridden in this
	 * type mapping.
	 * @see
	 * {@link TypeMapping#allOverridableAssociations()}
	 */
	Iterator<String> allOverridableAssociationNames();

	/**
	 * Return whether the given attribute mapping key is valid for this
	 * particular type mapping (for example, id's are not valid for an
	 * embeddable type mapping)
	 */
	boolean attributeMappingKeyAllowed(String attributeMappingKey);
	
	/**
	 * Return whether any database metadata specific validation should occur.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean shouldValidateAgainstDatabase();
}
