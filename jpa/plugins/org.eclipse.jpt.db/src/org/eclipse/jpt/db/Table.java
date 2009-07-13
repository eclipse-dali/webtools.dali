/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
public interface Table extends DatabaseObject {

	/**
	 * Return the table's schema.
	 */
	Schema getSchema();


	// ********** columns **********

	/**
	 * Return the table's columns.
	 */
	Iterator<Column> columns();

	/**
	 * Return the number of columns the table contains.
	 */
	int columnsSize();

	/**
	 * Return the column with specified name. The name must be an exact match
	 * of the column's name.
	 * @see #getColumnForIdentifier(String)
	 */
	Column getColumnNamed(String name);

	/**
	 * Return the table's column identifers, sorted by name.
	 */
	Iterator<String> sortedColumnIdentifiers();

	/**
	 * Return the column for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #getColumnNamed(String)
	 */
	Column getColumnForIdentifier(String identifier);


	// ********** primary key columns **********

	/**
	 * Return the table's primary key columns.
	 */
	Iterator<Column> primaryKeyColumns();

	/**
	 * Return the number of primary key columns the table contains.
	 */
	int primaryKeyColumnsSize();

	/**
	 * Return the table's single primary key column. Throw an
	 * IllegalStateException if the table has more than one primary key column.
	 */
	Column getPrimaryKeyColumn();


	// ********** foreign keys **********

	/**
	 * Return the table's foreign keys.
	 */
	Iterator<ForeignKey> foreignKeys();

	/**
	 * Return the number of foreign keys the table contains.
	 */
	int foreignKeysSize();


	// ********** join table support **********

	/**
	 * Return whether the table is possibly a "join" table
	 * (i.e. it contains only 2 foreign keys). Whether the table *actually* is
	 * a "join" table is determined by the semantics of the database design.
	 */
	boolean isPossibleJoinTable();

	/**
	 * Assuming the table is a "join" table, return the foreign key to the
	 * "owning" table.
	 * @see #isPossibleJoinTable()
	 */
	ForeignKey getJoinTableOwningForeignKey();

	/**
	 * Assuming the table is a "join" table, return the foreign key to the
	 * "non-owning" table.
	 * @see #isPossibleJoinTable()
	 */
	ForeignKey getJoinTableNonOwningForeignKey();

	/**
	 * Assuming the table is a "join" table, return whether its name matches
	 * the JPA default (i.e. "OWNINGTABLE_NONOWNINGTABLE").
	 * @see #isPossibleJoinTable()
	 */
	boolean joinTableNameIsDefault();

}
