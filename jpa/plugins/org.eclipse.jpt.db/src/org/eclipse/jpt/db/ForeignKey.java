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
public interface ForeignKey extends DatabaseObject {

	// ********** tables **********

	/**
	 * Return the foreign key's "base" table.
	 */
	Table getBaseTable();

	/**
	 * Return the foreign key's "referenced" table.
	 */
	Table getReferencedTable();


	// ********** column pairs **********

	/**
	 * Return the foreign key's column pairs.
	 */
	Iterator<ColumnPair> columnPairs();

	/**
	 * Return the size of the foreign key's column pairs.
	 */
	int columnPairsSize();

	/**
	 * Return the foreign key's single column pair. Throw an
	 * IllegalStateException if the foreign key has more than one column pair.
	 */
	ColumnPair getColumnPair();

	/**
	 * Return the foreign key's "base" columns.
	 */
	Iterator<Column> baseColumns();

	/**
	 * Return the foreign key's "base" columns that are not part of the base
	 * table's primary key. (The non-primary key base columns are not used to
	 * generate basic attributes during entity generation.)
	 */
	Iterator<Column> nonPrimaryKeyBaseColumns();

	/**
	 * Return the foreign key's "referenced" columns.
	 */
	Iterator<Column> referencedColumns();

	/**
	 * Return whether the foreign key references the primary key of the
	 * "referenced" table and that primary key has only a single column.
	 * This can be used when determining JPA defaults.
	 */
	boolean referencesSingleColumnPrimaryKey();


	// ********** JPA support **********

	/**
	 * Return an appropriate name for an attribute that holds the entity
	 * mapped to the foreign key's "referenced" table.
	 */
	String getAttributeName();

	/**
	 * If the name of the "base" column adheres to the JPA spec for a
	 * default mapping (i.e. it ends with an underscore followed by the name
	 * of the "referenced" column, and the "referenced" column is the single
	 * primary key column of the "referenced" table), return the corresponding
	 * default attribute name:
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.ID) => "CUBICLE"
	 * Return a null if it does not adhere to the JPA spec:
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.CUBICLE_ID) => null
	 *     ForeignKey(EMP.CUBICLE => CUBICLE.ID) => null
	 */
	String getDefaultAttributeName();

	/**
	 * Given the name of an attribute (field or property) that is mapped to the
	 * foreign key,
	 * build and return a string to be used as the value for the attribute's
	 * JoinColumn annotation's 'name' element. Return null if the attribute
	 * maps to the join column by default.
	 * Precondition: The foreign key consists of a single column pair whose
	 * referenced column is the single-column primary key of the foreign
	 * key's referenced table.
	 */
	String getJoinColumnAnnotationIdentifier(String attributeName);

	// ********** column pair interface **********

	/**
	 * Pair up the foreign key's column pairs, matching each "base" column with
	 * the appropriate "referenced" column.
	 * @see #columnPairs()
	 */
	interface ColumnPair {

		/**
		 * Return the column pair's "base" column.
		 */
		Column getBaseColumn();

		/**
		 * Return the column pair's "referenced" column.
		 */
		Column getReferencedColumn();

	}

}
