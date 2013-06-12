/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Used by many-to-many and one-to-many mappings.
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
public interface SpecifiedJoinTable
	extends SpecifiedReferenceTable, JoinTable
{
	SpecifiedJoinTableRelationshipStrategy getParent();

	/**
	 * @see SpecifiedJoinTableRelationshipStrategy#initializeFrom(VirtualJoinTableRelationshipStrategy)
	 */
	void initializeFrom(VirtualJoinTable virtualTable);


	// ********** inverse join columns **********

	/**
	 * Convert the join table's default inverse join column to a specified
	 * inverse join column.
	 */
	void convertDefaultInverseJoinColumnToSpecified();

	ListIterable<? extends SpecifiedJoinColumn> getInverseJoinColumns();

	ListIterable<? extends SpecifiedJoinColumn> getSpecifiedInverseJoinColumns();

	SpecifiedJoinColumn getSpecifiedInverseJoinColumn(int index);

	/**
	 * Add a specified inverse join column to the join table.
	 * Return the newly-created join column.
	 */
	SpecifiedJoinColumn addSpecifiedInverseJoinColumn();

	/**
	 * Add a specified inverse join column to the join table.
	 * Return the newly-created join column.
	 */
	SpecifiedJoinColumn addSpecifiedInverseJoinColumn(int index);

	/**
	 * Remove the inverse join column at the specified index from the join table.
	 */
	void removeSpecifiedInverseJoinColumn(int index);

	/**
	 * Remove the specified inverse join column from the join table.
	 */
	void removeSpecifiedInverseJoinColumn(SpecifiedJoinColumn joinColumn);

	/**
	 * Move an inverse join column from the specified source index to the
	 * specified target index.
	 */
	void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Remove all the join table's inverse join columns.
	 */
	void clearSpecifiedInverseJoinColumns();

	SpecifiedJoinColumn getDefaultInverseJoinColumn();
}
