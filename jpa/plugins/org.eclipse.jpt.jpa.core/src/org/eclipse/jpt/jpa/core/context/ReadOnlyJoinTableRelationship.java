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

/**
 * Join table relationship<ul>
 * <li>1:1 (JPA 2.0)
 * <li>1:m
 * <li>m:1 (JPA 2.0)
 * <li>m:m
 * <li>association override (JPA 2.0)
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyJoinTableRelationship
	extends ReadOnlyRelationship
{
	/**
	 * Return the (never <code>null</code>) strategy used to configure
	 * the relationship's join table strategy.
	 */
	ReadOnlyJoinTableRelationshipStrategy getJoinTableStrategy();

	/**
	 * Return whether the join table strategy is the
	 * relationship's current strategy.
	 */
	boolean strategyIsJoinTable();

	/**
	 * Return whether this relationship may potentially have a default join
	 * table. For example, a M-M mapping may have a default join table
	 * if it does not specify a "mapped by" attribute or a join table;
	 * but a M-1 mapping does not support a default join table in any
	 * situation.
	 */
	boolean mayHaveDefaultJoinTable();
}
