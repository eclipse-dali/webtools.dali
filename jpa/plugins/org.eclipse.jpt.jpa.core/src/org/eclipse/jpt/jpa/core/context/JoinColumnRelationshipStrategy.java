/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
 * Join column relationship strategy.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see AssociationOverride
 * @see SpecifiedJoinColumnRelationship
 */
public interface JoinColumnRelationshipStrategy
	extends RelationshipStrategy
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
	ListIterable<? extends JoinColumn> getJoinColumns();

	/**
	 * Return the number of join columns, whether specified and default.
	 */
	int getJoinColumnsSize();


	// ********** specified join columns **********

	/**
	 * Change notification identifier for "specifiedJoinColumns" list
	 */
	String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the specified join columns.
	 */
	ListIterable<? extends JoinColumn> getSpecifiedJoinColumns();

	/**
	 * Return the number of specified join columns.
	 */
	int getSpecifiedJoinColumnsSize();

	/**
	 * Return whether the strategy has any specified join columns.
	 * (Equivalent to {@link #getSpecifiedJoinColumnsSize()} != 0.)
	 */
	boolean hasSpecifiedJoinColumns();

	/**
	 * Return the specified join column at the specified index.
	 */
	JoinColumn getSpecifiedJoinColumn(int index);


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
	JoinColumn getDefaultJoinColumn();
}
