/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;

/**
 * Read-only entity secondary table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlySecondaryTable
	extends ReadOnlyTable
{
	public Entity getParent();

	/**
	 * Return whether the secondary table is part of an <code>orm.xml</code>
	 * entity but is specified only in the entity's Java annotations (as
	 * opposed to explicitly in the <code>orm.xml</code>).
	 */
	boolean isVirtual();


	// ********** primary key join columns **********

	/**
	 * Return the secondary table's primary key join columns,
	 * whether specified or default.
	 */
	ListIterator<? extends ReadOnlyPrimaryKeyJoinColumn> primaryKeyJoinColumns();

	/**
	 * Return the number of primary key join columns,
	 * whether specified or default.
	 */
	int primaryKeyJoinColumnsSize();


	// ********** specified primary key join columns **********

	/**
	 * Return the specified primary key join columns.
	 */
	ListIterator<? extends ReadOnlyPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified primary key join columns.
	 */
	int specifiedPrimaryKeyJoinColumnsSize();


	// ********** default primary key join columns **********

	/**
	 * Return the default primary key join column or null.  A default primary
	 * key join column only exists if there are no specified primary key join
	 * columns.
	 */
	ReadOnlyPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN = "defaultPrimaryKeyJoinColumn"; //$NON-NLS-1$
}
