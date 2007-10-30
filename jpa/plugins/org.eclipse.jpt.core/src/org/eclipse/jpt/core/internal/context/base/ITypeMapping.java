/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.Iterator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

public interface ITypeMapping extends IJpaContextNode
{
	/**
	 * Return a unique key for the ITypeMapping.  If this is defined in
	 * an extension they should be equal.
	 * @return
	 */
	String getKey();

	IPersistentType getPersistentType();

	boolean isMapped();
	
	String getName();

	String getTableName();

	/**
	 * Return the type mapping's "associated" tables, which includes the
	 * primary table and the collection of secondary tables.
	 */
//	Iterator<ITable> associatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the
	 * primary table and the collection of secondary tables, as well as all
	 * inherited "associated" tables.
	 */
//	Iterator<ITable> associatedTablesIncludingInherited();

	/**
	 * Return the names of the type mapping's "associated" tables,
	 * which includes the primary table and the collection of secondary
	 * tables, as well as the names of all the inherited "associated" tables.
	 */
	Iterator<String> associatedTableNamesIncludingInherited();

	/**
	 * return the resolved primary db table
	 */
	Table primaryDbTable();

	Schema dbSchema();

	/**
	 * return the resolved associated db table with the passed in name
	 */
	Table dbTable(String tableName);

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
