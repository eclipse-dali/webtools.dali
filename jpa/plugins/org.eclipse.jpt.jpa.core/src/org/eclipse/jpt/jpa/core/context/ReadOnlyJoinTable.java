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
 * Used by association overrides.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyJoinTable
	extends ReadOnlyReferenceTable
{
	ReadOnlyJoinTableRelationshipStrategy getParent();

	RelationshipMapping getRelationshipMapping();


	// ********** inverse join columns **********

	/**
	 * Return the join table's inverse join columns, whether specified or default.
	 */
	ListIterator<? extends ReadOnlyJoinColumn> inverseJoinColumns();

	/**
	 * Return the number of inverse join columns, whether specified or default.
	 */
	int inverseJoinColumnsSize();

	/**
	 * Return the specified inverse join columns.
	 */
	ListIterator<? extends ReadOnlyJoinColumn> specifiedInverseJoinColumns();
		String SPECIFIED_INVERSE_JOIN_COLUMNS_LIST = "specifiedInverseJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified inverse join columns.
	 */
	int specifiedInverseJoinColumnsSize();

	/**
	 * Return whether the join table has specified inverse join columns.
	 */
	boolean hasSpecifiedInverseJoinColumns();

	/**
	 * Return the specified inverse join column at the specified index.
	 */
	ReadOnlyJoinColumn getSpecifiedInverseJoinColumn(int index);

	/**
	 * Return the default inverse join column or null. A default inverse join column
	 * only exists if there are no specified inverse join columns.
	 */
	ReadOnlyJoinColumn getDefaultInverseJoinColumn();
		String DEFAULT_INVERSE_JOIN_COLUMN = "defaultInverseJoinColumn"; //$NON-NLS-1$
}
