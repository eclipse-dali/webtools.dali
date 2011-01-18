/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

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
	ListIterator<? extends ReadOnlyJoinColumn> joinColumns();

	/**
	 * Return the number of join columns, whether specified or default.
	 */
	int joinColumnsSize();

	/**
	 * Return the reference table's specified join columns.
	 */
	ListIterator<? extends ReadOnlyJoinColumn> specifiedJoinColumns();
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified join columns.
	 */
	int specifiedJoinColumnsSize();

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
