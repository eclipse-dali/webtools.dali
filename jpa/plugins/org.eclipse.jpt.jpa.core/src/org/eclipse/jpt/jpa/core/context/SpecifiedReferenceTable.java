/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Reference table (i.e. a table that joins with one other table,
 * as opposed to a "join table" that joins with two other tables)<ul>
 * <li>join table
 * <li>collection table
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface SpecifiedReferenceTable
	extends SpecifiedTable, ReferenceTable
{
	// ********** join columns **********

	/**
	 * Convert the reference table's default join column to a specified join column. 
	 * As a side-effect in the update, the default join columns will be recalculated.
	 */
	void convertDefaultJoinColumnToSpecified();

	/**
	 * Remove all the specified join columns. As a side-effect in the
	 * update, the default join columns will be recalculated.
	 */
	void clearSpecifiedJoinColumns();

	ListIterable<? extends SpecifiedJoinColumn> getJoinColumns();

	ListIterable<? extends SpecifiedJoinColumn> getSpecifiedJoinColumns();

	SpecifiedJoinColumn getSpecifiedJoinColumn(int index);

	/**
	 * Add and return a specified join column to the reference table.
	 */
	SpecifiedJoinColumn addSpecifiedJoinColumn();

	/**
	 * Add and return a specified join column to the reference table.
	 */
	SpecifiedJoinColumn addSpecifiedJoinColumn(int index);

	/**
	 * Remove the join column at the specified index from the reference table.
	 */
	void removeSpecifiedJoinColumn(int index);

	/**
	 * Remove the specified join column from the reference table.
	 */
	void removeSpecifiedJoinColumn(SpecifiedJoinColumn joinColumn);

	/**
	 * Move the join column at the specified source index to the
	 * specified target index.
	 */
	void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex);

	SpecifiedJoinColumn getDefaultJoinColumn();
}
