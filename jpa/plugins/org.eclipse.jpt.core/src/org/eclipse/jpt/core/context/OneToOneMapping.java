/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 */
public interface OneToOneMapping
	extends SingleRelationshipMapping, NonOwningMapping
{
	// **************** primary key join columns **************************************

	/**
	 * Return a list iterator of the primary key join columns.
	 * This will not be null.
	 */
	<T extends PrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns();

	/**
	 * Return the number of join columns, both specified and default.
	 */
	int primaryKeyJoinColumnsSize();
	/**
	 * Add a specified join column to the join table return the object 
	 * representing it.
	 */
	PrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index);
	
	/**
	 * Remove the specified join column from the join table.
	 */
	void removePrimaryKeyJoinColumn(int index);
	
	/**
	 * Remove the specified join column at the index from the join table.
	 */
	void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn);
	
	/**
	 * Move the specified join column from the source index to the target index.
	 */
	void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex);

	boolean containsPrimaryKeyJoinColumns();
		String PRIMAY_KEY_JOIN_COLUMNS_LIST = "primaryKeyJoinColumnsList";

}
