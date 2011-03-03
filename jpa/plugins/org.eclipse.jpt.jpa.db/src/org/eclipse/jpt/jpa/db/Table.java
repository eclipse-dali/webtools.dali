/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

/**
 * Database table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Table
	extends DatabaseObject
{
	/**
	 * Return the table's schema.
	 */
	Schema getSchema();


	// ********** columns **********

	/**
	 * Return the table's columns.
	 */
	Iterable<Column> getColumns();

	/**
	 * Return the number of columns the table contains.
	 */
	int getColumnsSize();

	/**
	 * Return the column with the specified name. The name must be an exact match
	 * of the column's name.
	 * @see #getColumnForIdentifier(String)
	 */
	Column getColumnNamed(String name);

	/**
	 * Return the table's column identifers, sorted by name.
	 * @see #getColumnForIdentifier(String)
	 */
	Iterable<String> getSortedColumnIdentifiers();

	/**
	 * Return the column for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #getColumnNamed(String)
	 * @see #getSortedColumnIdentifiers()
	 */
	Column getColumnForIdentifier(String identifier);


	// ********** primary key columns **********

	/**
	 * Return the table's primary key columns.
	 */
	Iterable<Column> getPrimaryKeyColumns();

	/**
	 * Return the number of primary key columns the table contains.
	 */
	int getPrimaryKeyColumnsSize();

	/**
	 * Return the table's single primary key column. Throw an
	 * {@link IllegalStateException} if the table has more than one primary key column.
	 */
	Column getPrimaryKeyColumn();


	// ********** foreign keys **********

	/**
	 * Return the table's foreign keys.
	 */
	Iterable<ForeignKey> getForeignKeys();

	/**
	 * Return the number of foreign keys the table contains.
	 */
	int getForeignKeysSize();


	// ********** join table support **********

	/**
	 * Return whether the table is possibly a <em>join</em> table
	 * (i.e. it contains only 2 foreign keys). Whether the table <em>actually</em> is
	 * a <em>join</em> table is determined by the semantics of the database design.
	 */
	boolean isPossibleJoinTable();

	/**
	 * Assuming the table is a <em>join</em> table, return the foreign key to the
	 * <em>owning</em> table.
	 * @see #isPossibleJoinTable()
	 */
	ForeignKey getJoinTableOwningForeignKey();

	/**
	 * Assuming the table is a <em>join</em> table, return the foreign key to the
	 * <em>non-owning</em> table.
	 * @see #isPossibleJoinTable()
	 */
	ForeignKey getJoinTableNonOwningForeignKey();

	/**
	 * Assuming the table is a <em>join</em> table, return whether its name matches
	 * the JPA default (i.e. <code>"OWNINGTABLE_NONOWNINGTABLE"</code>).
	 * @see #isPossibleJoinTable()
	 */
	boolean joinTableNameIsDefault();
}
