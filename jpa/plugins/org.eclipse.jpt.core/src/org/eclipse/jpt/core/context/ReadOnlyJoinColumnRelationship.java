/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Join column relationship<ul>
 * <li>1:1
 * <li>1:m
 * <li>m:1
 * <li>association override
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see Relationship
 * @see JoinColumn
 */
public interface ReadOnlyJoinColumnRelationship
	extends ReadOnlyRelationship
{
	/**
	 * Return the (never <code>null</code>) strategy used to configure
	 * the relationship's join column strategy.
	 */
	ReadOnlyJoinColumnRelationshipStrategy getJoinColumnStrategy();

	/**
	 * Return whether the join column strategy is the
	 * relationship's current strategy.
	 */
	boolean strategyIsJoinColumn();

	/**
	 * Return whether this relationship may potentially have a default join
	 * column. For example, a 1-1 mapping may have a default join column
	 * if it does not specify a "mapped by" attribute or a join table;
	 * but a 1-M mapping does not support a default join column in any
	 * situation.
	 */
	boolean mayHaveDefaultJoinColumn();
}
