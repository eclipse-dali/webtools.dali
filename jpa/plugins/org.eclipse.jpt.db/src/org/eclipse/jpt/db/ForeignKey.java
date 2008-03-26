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
 * Database foreign key
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface ForeignKey extends Comparable<ForeignKey> {

	/**
	 * Return the foreign key's name.
	 */
	String getName();

	/**
	 * Return the foreign key's "base" table.
	 */
	Table getBaseTable();

	/**
	 * Return the foreign key's "referenced" table.
	 */
	Table getReferencedTable();

	/**
	 * Return the foreign key's column pairs.
	 * @see ColumnPair
	 */
	Iterator<ColumnPair> columnPairs();

	/**
	 * Return the foreign key's single column pair. Throw an
	 * IllegalStateException if the foreign key has more than one column pair.
	 */
	ColumnPair columnPair();

	/**
	 * Return the size of the foreign key's column pairs.
	 * @see ColumnPair
	 */
	int columnPairsSize();

	/**
	 * Return the foreign key's "base" columns.
	 * @see ColumnPair
	 */
	Iterator<Column> baseColumns();

	/**
	 * Return the foreign key's "base" columns that are not part of
	 * the base table's primary key.
	 * @see ColumnPair
	 */
	Iterator<Column> nonPrimaryKeyBaseColumns();

	/**
	 * Return the foreign key's "referenced" columns.
	 * @see ColumnPair
	 */
	Iterator<Column> referencedColumns();

	/**
	 * Return a Java-appropriate name for a field that holds the entity
	 * mapped to the foreign key's "referenced" table.
	 */
	String getJavaFieldName();

	/**
	 * Return whether the foreign key's default Java field name matches the
	 * specified Java identifier, respecting the database's case-sensitivity.
	 */
	boolean defaultMatchesJavaFieldName(String javaFieldName);

	/**
	 * Return whether the foreign key is the default for the specified Java
	 * field name, respecting the database's case-sensitivity.
	 */
	boolean isDefaultFor(String javaFieldName);

	/**
	 * Return whether the foreign key references the primary key of the
	 * "referenced" table and that primary key has only a single column.
	 */
	boolean referencesSingleColumnPrimaryKey();


	/**
	 * Pair up the foreign key's column pairs, matching each "base" column with
	 * the appropriate "referenced" column.
	 * @see #columnPairs()
	 */
	interface ColumnPair extends Comparable<ColumnPair> {

		/**
		 * Return the column pair's "base" column.
		 */
		Column baseColumn();

		/**
		 * Return the column pair's "referenced" column.
		 */
		Column referencedColumn();

	}

}
