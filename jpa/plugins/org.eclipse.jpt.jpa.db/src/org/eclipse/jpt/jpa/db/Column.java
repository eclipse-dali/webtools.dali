/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;

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
	Predicate<Column> IS_PART_OF_PRIMARY_KEY = new IsPartOfPrimaryKey();
	public static class IsPartOfPrimaryKey
		extends PredicateAdapter<Column>
	{
		@Override
		public boolean evaluate(Column column) {
			return column.isPartOfPrimaryKey();
		}
	}

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
	 * Return whether the column's type is a date type.
	 * (i.e. DATE, CALENDAR)
	 */
	boolean isDateDataType();
	
	/**
	 * Return whether the column's type is time type.
	 * (i.e. TIME, TIMESTAMP)
	 */
	boolean isTimeDataType();
	

	/**
	 * Return the column's precision if it is a NumericalDataType or a TimeDataType;
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
