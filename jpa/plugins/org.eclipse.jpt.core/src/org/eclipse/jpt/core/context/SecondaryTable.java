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
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface SecondaryTable
	extends Table
{

	public Entity getParent();
	
	/**
	 * Return a list iterator of the primary key join columns whether specified or default.
	 * This will not be null.
	 */
	<T extends PrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of primary key join columns, both specified and default.
	 */
	int primaryKeyJoinColumnsSize();

	/**
	 * Return a list iterator of the specified primary key join columns.
	 * This will not be null.
	 */
	<T extends PrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns();

	/**
	 * Return the number of specified primary key join columns.
	 */
	int specifiedPrimaryKeyJoinColumnsSize();

	/**
	 * Return the default primary key join column or null.  A default primary key join column
	 * only exists if there are no specified primary key join columns.
	 */
	PrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN = "defaultPrimaryKeyJoinColumn"; //$NON-NLS-1$

	/**
	 * Add a specified primary key join column to the secondary table return the object 
	 * representing it.
	 */
	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column from the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column at the index from the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn);

	/**
	 * Move the specified primary key join column from the source index to the target index.
	 */
	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Return true if the secondary table exists as specified on the owning object, 
	 * or false if the secondary table is a result of defaults calculation
	 */
	boolean isVirtual();
}
