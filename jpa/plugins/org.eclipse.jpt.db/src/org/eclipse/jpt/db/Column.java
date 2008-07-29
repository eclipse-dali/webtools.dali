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

import org.eclipse.jpt.utility.JavaType;

/**
 * Database column
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Column
	extends DatabaseObject, Comparable<Column>
{
	/**
	 * Return the column's table.
	 */
	Table getTable();

	/**
	 * Return whether the column is part of it's table's primary key.
	 */
	boolean isPrimaryKeyColumn();

	/**
	 * Return whether the column is part of one of it's table's foreign keys.
	 */
	boolean isForeignKeyColumn();

	/**
	 * Return the name of the column's datatype.
	 */
	String getDataTypeName();

	/**
	 * Return whether the column's datatype is a LOB type
	 * (i.e. BLOB, CLOB, or NCLOB).
	 */
	boolean dataTypeIsLOB();


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
