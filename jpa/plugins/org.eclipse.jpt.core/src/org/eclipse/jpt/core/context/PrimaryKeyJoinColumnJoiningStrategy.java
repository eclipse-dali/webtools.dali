/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;


/**
 * Joining strategy that uses primary key join columns
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * @see {@link RelationshipMapping}
 * @see {@link PrimaryKeyJoinColumnEnabledRelationshipReference}
 */
public interface PrimaryKeyJoinColumnJoiningStrategy
	extends JoiningStrategy
{
	/**
	 * Change notification identifier for "primaryKeyJoinColumns" list
	 */
	String PRIMARY_KEY_JOIN_COLUMNS_LIST = "primaryKeyJoinColumns"; //$NON-NLS-1$
	
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
	 * Return whether this has any primary key join columns.
	 * (Equivalent to {@link #primaryKeyJoinColumnsSize()} == 0)
	 */
	boolean hasPrimaryKeyJoinColumns();
	
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
}
