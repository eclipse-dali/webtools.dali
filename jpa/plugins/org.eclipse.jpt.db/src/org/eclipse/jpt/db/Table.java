/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;

import java.util.Iterator;

/**
 * Database table
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Table extends Comparable<Table> {

	/**
	 * Return the table's name.
	 */
	String getName();

	/**
	 * Return the table's columns.
	 */
	Iterator<Column> columns();

	/**
	 * Return the number of columns the table contains.
	 */
	int columnsSize();

	/**
	 * Return the names of the table's columns.
	 */
	Iterator<String> columnNames();

	/**
	 * Return whether the table contains a column with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	boolean containsColumnNamed(String name);

	/**
	 * Return the column in the table with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	Column columnNamed(String name);

	/**
	 * Return the table's primary key columns.
	 */
	Iterator<Column> primaryKeyColumns();

	/**
	 * Return the table's single primary key column. Throw an
	 * IllegalStateException if the table has more than one primary key column.
	 */
	Column primaryKeyColumn();

	/**
	 * Return the number of primary key columns the table contains.
	 */
	int primaryKeyColumnsSize();

	/**
	 * Return whether the specified column is one of the table's primary key
	 * columns.
	 */
	boolean primaryKeyColumnsContains(Column column);

	/**
	 * Return the table's foreign keys.
	 */
	Iterator<ForeignKey> foreignKeys();

	/**
	 * Return the number of foreign keys the table contains.
	 */
	int foreignKeysSize();

	/**
	 * Return whether the specified column is a "base" column
	 * for any of the table's foreign keys.
	 */
	boolean foreignKeyBaseColumnsContains(Column column);

	/**
	 * Return the table's name, converted to a Java-appropriate class
	 * identifier, respecting the database's case-sensitivity.
	 */
	String getShortJavaClassName();

	/**
	 * Return whether the table's name matches the specified Java-appropriate
	 * identifier, respecting the database's case-sensitivity.
	 */
	boolean matchesShortJavaClassName(String shortJavaClassName);

	/**
	 * Return the table's name, converted to a Java-appropriate field
	 * identifier, respecting the database's case-sensitivity.
	 */
	public String getJavaFieldName();

}
