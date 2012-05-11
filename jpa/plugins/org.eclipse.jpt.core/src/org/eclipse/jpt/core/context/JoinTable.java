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
 * Used by ManyToMany and OneToMany mappings.
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
public interface JoinTable
	extends ReferenceTable
{
	RelationshipMapping getRelationshipMapping();

	JoinTableJoiningStrategy getParent();


	// ********** inverse join columns **********

	/**
	 * Return the join table's inverse join columns, whether specified or default.
	 */
	<T extends JoinColumn> ListIterator<T> inverseJoinColumns();

	/**
	 * Return the number of inverse join columns, whether specified or default.
	 */
	int inverseJoinColumnsSize();

	/**
	 * Convert the join table's default inverse join column to a specified
	 * inverse join column.
	 */
	void convertDefaultToSpecifiedInverseJoinColumn();

	/**
	 * Return the specified inverse join columns.
	 */
	<T extends JoinColumn> ListIterator<T> specifiedInverseJoinColumns();
		String SPECIFIED_INVERSE_JOIN_COLUMNS_LIST = "specifiedInverseJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified inverse join columns.
	 */
	int specifiedInverseJoinColumnsSize();

	/**
	 * Return the default inverse join column or null. A default inverse join column
	 * only exists if there are no specified inverse join columns.
	 */
	JoinColumn getDefaultInverseJoinColumn();
		String DEFAULT_INVERSE_JOIN_COLUMN = "defaultInverseJoinColumn"; //$NON-NLS-1$

	/**
	 * Add a specified join column to the join table.
	 * Return the newly-created join column.
	 */
	JoinColumn addSpecifiedInverseJoinColumn(int index);

	/**
	 * Remove the inverse join column at the specified index from the join table.
	 */
	void removeSpecifiedInverseJoinColumn(int index);

	/**
	 * Remove the specified inverse join column from the join table.
	 */
	void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn);

	/**
	 * Move an inverse join column from the specified source index to the
	 * specified target index.
	 */
	void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Return whether the join table has specified inverse join columns.
	 */
	boolean hasSpecifiedInverseJoinColumns();

	/**
	 * Remove all the join table's inverse join columns.
	 */
	void clearSpecifiedInverseJoinColumns();

}
