/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Read-only reference table (i.e. a table that joins with one other table,
 * as opposed to a "join table" that joins with two other tables)<ul>
 * <li>join table
 * <li>collection table
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyReferenceTable
	extends ReadOnlyTable
{
	// TODO is this method necessary?
	PersistentAttribute getPersistentAttribute();
	

	// ********** join columns **********

	/**
	 * Return the reference table's join columns, whether specified or default.
	 */
	ListIterable<? extends ReadOnlyJoinColumn> getJoinColumns();

	/**
	 * Return the number of join columns, whether specified or default.
	 */
	int getJoinColumnsSize();

	/**
	 * Return the reference table's specified join columns.
	 */
	ListIterable<? extends ReadOnlyJoinColumn> getSpecifiedJoinColumns();
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified join columns.
	 */
	int getSpecifiedJoinColumnsSize();

	/**
	 * Return whether the reference table has specified join columns.
	 */
	boolean hasSpecifiedJoinColumns();

	/**
	 * Return the specified join column at the specified index.
	 */
	ReadOnlyJoinColumn getSpecifiedJoinColumn(int index);

	/**
	 * Return the default join column or <code>null</code>.
	 * A default join column only exists if there are no specified join columns.
	 */
	ReadOnlyJoinColumn getDefaultJoinColumn();
		String DEFAULT_JOIN_COLUMN_PROPERTY = "defaultJoinColumn"; //$NON-NLS-1$
}
