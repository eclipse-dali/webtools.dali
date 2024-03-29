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
 * Entity secondary table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
 * @since 2.0
 */
public interface SpecifiedSecondaryTable
	extends SpecifiedTable, SecondaryTable
{
	ListIterable<? extends SpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
	ListIterable<? extends SpecifiedPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
	SpecifiedPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	SpecifiedPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Add a specified primary key join column to the secondary table.
	 * Return the newly-created primary key join column.
	 */
	SpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn();

	/**
	 * Add a specified primary key join column to the secondary table.
	 * Return the newly-created primary key join column.
	 */
	SpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column at the specified index from
	 * the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column from the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn pkJoinColumn);

	/**
	 * Move the specified primary key join column from the specified source
	 * index to the specified target index.
	 */
	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Add specified primary key join column for each default join column
	 * with the same name and referenced column name. As a side-effect in the
	 * update, the default primary key join columns will be recalculated.
	 */
	void convertDefaultPrimaryKeyJoinColumnsToSpecified();

	/**
	 * Remove all the specified primary key join columns. As a side-effect in the
	 * update, the default primary key join columns will be recalculated.
	 */
	void clearSpecifiedPrimaryKeyJoinColumns();

}
