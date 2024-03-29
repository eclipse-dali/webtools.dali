/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Used by association overrides.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JoinTable
	extends ReferenceTable
{
	JoinTableRelationshipStrategy getParent();

	RelationshipMapping getRelationshipMapping();


	// ********** inverse join columns **********

	/**
	 * Return the join table's inverse join columns, whether specified or default.
	 */
	ListIterable<? extends JoinColumn> getInverseJoinColumns();

	/**
	 * Return the number of inverse join columns, whether specified or default.
	 */
	int getInverseJoinColumnsSize();

	/**
	 * Return the specified inverse join columns.
	 */
	ListIterable<? extends JoinColumn> getSpecifiedInverseJoinColumns();
		String SPECIFIED_INVERSE_JOIN_COLUMNS_LIST = "specifiedInverseJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified inverse join columns.
	 */
	int getSpecifiedInverseJoinColumnsSize();

	/**
	 * Return whether the join table has specified inverse join columns.
	 */
	boolean hasSpecifiedInverseJoinColumns();

	/**
	 * Return the specified inverse join column at the specified index.
	 */
	JoinColumn getSpecifiedInverseJoinColumn(int index);

	/**
	 * Return the default inverse join column or null. A default inverse join column
	 * only exists if there are no specified inverse join columns.
	 */
	JoinColumn getDefaultInverseJoinColumn();
		String DEFAULT_INVERSE_JOIN_COLUMN = "defaultInverseJoinColumn"; //$NON-NLS-1$
}
