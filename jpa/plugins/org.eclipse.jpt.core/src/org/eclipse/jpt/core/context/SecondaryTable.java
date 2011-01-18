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
 * entity secondary table
 * <p>
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
	extends Table, ReadOnlySecondaryTable
{
	ListIterator<? extends PrimaryKeyJoinColumn> primaryKeyJoinColumns();
	ListIterator<? extends PrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	PrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();

	/**
	 * Add a specified primary key join column to the secondary table.
	 * Return the newly-created primary key join column.
	 */
	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn();

	/**
	 * Add a specified primary key join column to the secondary table.
	 * Return the newly-created primary key join column.
	 */
	PrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column at the specified index from
	 * the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(int index);

	/**
	 * Remove the specified primary key join column from the secondary table.
	 */
	void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn);

	/**
	 * Move the specified primary key join column from the specified source
	 * index to the specified target index.
	 */
	void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex);
}
