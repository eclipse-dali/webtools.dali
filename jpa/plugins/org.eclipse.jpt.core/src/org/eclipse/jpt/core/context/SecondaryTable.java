/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;


public interface SecondaryTable extends Table
{

	public Entity parent();
	
	/**
	 * Return a list iterator of the primary key join columns whether specified or default.
	 * This will not be null.
	 */
	<T extends PrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();
		String SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST = "specifiedPrimaryKeyJoinColumnsList";

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
		String DEFAULT_PRIMARY_KEY_JOIN_COLUMN = "defaultPrimaryKeyJoinColumn";

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
	

//	boolean containsSpecifiedPrimaryKeyJoinColumns();
//
//	boolean isVirtual();
}
