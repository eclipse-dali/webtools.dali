/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Iterator;

/**
 * database unique constraint
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface UniqueConstraint
	extends ReadOnlyUniqueConstraint
{
	void initializeFrom(ReadOnlyUniqueConstraint oldUniqueConstraint);


	// ********** column names **********

	/**
	 * Add the specified column name to the end of the
	 * unique constraint's list of column names.
	 */
	void addColumnName(String columnName);

	/**
	 * Add the specified column name to the
	 * unique constraint's list of column names
	 * at the specified index.
	 */
	void addColumnName(int index, String columnName);

	/**
	 * Remove the specified column name from the
	 * unique constraint's list of column names.
	 */
	void removeColumnName(String columnName);

	/**
	 * Remove the column name at the specified index from the
	 * unique constraint's list of column names.
	 */
	void removeColumnName(int index);

	/**
	 * Move the column name at the specified source index
	 * to the specified target index in the
	 * unique constraint's list of column names.
	 */
	void moveColumnName(int targetIndex, int sourceIndex);


	// ********** owner **********

	/**
	 * All containers must implement this interface.
	 */
	interface Owner {
		Iterator<String> candidateUniqueConstraintColumnNames();
	}
}
