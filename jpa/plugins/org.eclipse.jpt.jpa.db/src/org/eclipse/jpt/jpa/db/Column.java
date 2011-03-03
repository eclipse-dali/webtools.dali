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

import org.eclipse.jpt.common.utility.JavaType;

/**
 * Database column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Column
	extends DatabaseObject
{
	/**
	 * Return the column's table.
	 */
	Table getTable();


	// ********** constraints **********

	/**
	 * Return whether the column is part of its table's primary key.
	 */
	boolean isPartOfPrimaryKey();

	/**
	 * Return whether the column is part of one of its table's foreign keys.
	 */
	boolean isPartOfForeignKey();

	/**
	 * Return whether the column is part of a unique constraint defined for its
	 * table.
	 */
	boolean isPartOfUniqueConstraint();

	/**
	 * Return whether the column is nullable.
	 */
	boolean isNullable();


	// ********** data type **********

	/**
	 * Return the name of the column's datatype.
	 */
	String getDataTypeName();

	/**
	 * Return whether the column's type is numeric.
	 */
	boolean isNumeric();

	/**
	 * Return the column's precision if it is a NumericalDataType;
	 * otherwise, return -1.
	 */
	public int getPrecision();

	/**
	 * Return the column's scale if it is an ExactNumericDataType;
	 * otherwise, return -1.
	 */
	public int getScale();

	/**
	 * If the column is a CharacterStringDataType, return its length;
	 * otherwise, return -1.
	 */
	public int getLength();

	/**
	 * Return whether the column's datatype is a LOB type
	 * (i.e. BLOB, CLOB, or NCLOB).
	 */
	boolean isLOB();


	// ********** Java type **********

	/**
	 * Return a Java type declaration that is reasonably
	 * similar to the column's data type.
	 */
	String getJavaTypeDeclaration();

	/**
	 * Return a Java type that is reasonably
	 * similar to the column's data type.
	 */
	JavaType getJavaType();

	/**
	 * Return a Java type declaration that is reasonably
	 * similar to the column's data type and suitable for use as a
	 * primary key field.
	 */
	String getPrimaryKeyJavaTypeDeclaration();

	/**
	 * Return a Java type that is reasonably
	 * similar to the column's data type and suitable for use as a
	 * primary key field.
	 */
	JavaType getPrimaryKeyJavaType();
}
