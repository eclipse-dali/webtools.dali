/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jpt.db.internal.Schema;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TypeMapping extends JpaContextNode
{
	/**
	 * Return a unique key for the ITypeMapping.  If this is defined in
	 * an extension they should be equal.
	 * @return
	 */
	String getKey();

	PersistentType persistentType();

	boolean isMapped();
	
	/**
	 * Return the type mapping's primary table name, null if a primary table does not apply
	 */
	String tableName();

	/**
	 * Return the type mapping's "associated" tables, which includes the
	 * primary table and the collection of secondary tables.
	 */
	Iterator<Table> associatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the
	 * primary table and the collection of secondary tables, as well as all
	 * inherited "associated" tables.
	 */
	Iterator<Table> associatedTablesIncludingInherited();

	/**
	 * Return the names of the type mapping's "associated" tables,
	 * which includes the primary table and the collection of secondary
	 * tables, as well as the names of all the inherited "associated" tables.
	 */
	Iterator<String> associatedTableNamesIncludingInherited();

	/**
	 * return the resolved primary db table
	 */
	org.eclipse.jpt.db.internal.Table primaryDbTable();

	Schema dbSchema();

	/**
	 * return the resolved associated db table with the passed in name
	 */
	org.eclipse.jpt.db.internal.Table dbTable(String tableName);

	/**
	 * Return whether the specified table is invalid for any annotations
	 * associated with the type mapping.
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return an Iterator of attribute names.  The attributes must be BasicMappings or IdMappings
	 * found in any MappedSuperclass in the inheritance hierarchy
	 */
	Iterator<String> overridableAttributeNames();

	/**
	 * Return an Iterator of attribute names.  The attributes must be OneToOneMappings or ManyToOneMappings
	 * found in any MappedSuperclass in the inheritance hierarchy
	 */
	Iterator<String> overridableAssociationNames();

	Iterator<String> allOverridableAttributeNames();

	Iterator<String> allOverridableAssociationNames();

	/**
	 * Return whether the given attribute mapping key is valid for this particular
	 * type mapping
	 * (for example, id's are not valid for an embeddable type mapping)
	 */
	boolean attributeMappingKeyAllowed(String attributeMappingKey);
}
