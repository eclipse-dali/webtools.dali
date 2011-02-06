/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
 * Read-only join column relationship strategy.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see ReadOnlyAssociationOverride
 * @see JoinColumnRelationship
 */
public interface ReadOnlyJoinColumnRelationshipStrategy
	extends ReadOnlyRelationshipStrategy
{
	/**
	 * The source of the relationship is usually the owning type mapping.
	 * In the case of a target foreign key relationship the source and target
	 * are swapped.
	 * @see #isTargetForeignKey()
	 */
	TypeMapping getRelationshipSource();

	/**
	 * The target of the relationship usually is the target entity.
	 * In the case of a target foreign key relationship the source and target
	 * are swapped.
	 * @see #isTargetForeignKey()
	 */
	TypeMapping getRelationshipTarget();

	/**
	 * Return whether this relationship is a target foreign key relationship.
	 * A one-to-many mapping with a join column will have the foreign key
	 * in the target table.
	 */
	boolean isTargetForeignKey();


	// ********** join columns **********

	/**
	 * Return the join columns whether specified or default.
	 */
	ListIterator<? extends ReadOnlyJoinColumn> joinColumns();

	/**
	 * Return the number of join columns, whether specified and default.
	 */
	int joinColumnsSize();


	// ********** specified join columns **********

	/**
	 * Change notification identifier for "specifiedJoinColumns" list
	 */
	String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the specified join columns.
	 */
	ListIterator<? extends ReadOnlyJoinColumn> specifiedJoinColumns();

	/**
	 * Return the number of specified join columns.
	 */
	int specifiedJoinColumnsSize();

	/**
	 * Return whether the strategy has any specified join columns.
	 * (Equivalent to {@link #specifiedJoinColumnsSize()} != 0.)
	 */
	boolean hasSpecifiedJoinColumns();

	/**
	 * Return the specified join column at the specified index.
	 */
	ReadOnlyJoinColumn getSpecifiedJoinColumn(int index);


	// ********** default join column **********

	/**
	 * Change notification identifier for "defaultJoinColumn" property
	 */
	String DEFAULT_JOIN_COLUMN_PROPERTY = "defaultJoinColumn"; //$NON-NLS-1$

	/**
	 * Return the default join column. If there are specified join
	 * columns, there is no default join column. There are also
	 * times that there may be no default join column even if there are no
	 * specified join columns.
	 */
	ReadOnlyJoinColumn getDefaultJoinColumn();
}
