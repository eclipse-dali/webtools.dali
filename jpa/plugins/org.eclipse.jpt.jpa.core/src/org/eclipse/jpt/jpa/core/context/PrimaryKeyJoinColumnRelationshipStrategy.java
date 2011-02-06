/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
 * Primary key join column relationship strategy.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see PrimaryKeyJoinColumnRelationship
 * 
 * @version 2.2
 * @since 2.2
 */
public interface PrimaryKeyJoinColumnRelationshipStrategy
	extends RelationshipStrategy
{
	/**
	 * Change notification identifier for "primaryKeyJoinColumns" list
	 */
	String PRIMARY_KEY_JOIN_COLUMNS_LIST = "primaryKeyJoinColumns"; //$NON-NLS-1$
	
	/**
	 * Return the strategy's primary key join columns.
	 */
	ListIterator<? extends PrimaryKeyJoinColumn> primaryKeyJoinColumns();
	
	/**
	 * Return the number of primary key join columns.
	 */
	int primaryKeyJoinColumnsSize();
	
	/**
	 * Return whether the relationship has any primary key join columns.
	 * (Equivalent to {@link #primaryKeyJoinColumnsSize()} == 0.)
	 */
	boolean hasPrimaryKeyJoinColumns();

	/**
	 * Return the primary key join column at the specified index.
	 */
	PrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index);
	
	/**
	 * Add a primary key join column.
	 */
	PrimaryKeyJoinColumn addPrimaryKeyJoinColumn();
	
	/**
	 * Add a primary key join column.
	 */
	PrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index);
	
	/**
	 * Remove the specified primary key join column.
	 */
	void removePrimaryKeyJoinColumn(int index);
	
	/**
	 * Remove the specified primary key join column.
	 */
	void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn);
	
	/**
	 * Remove the specified primary key join column.
	 */
	void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex);
}
