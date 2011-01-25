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
 * Join column relationship (1:1, 1:m, m:1, and association override)
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
	 * Return the aggregate (never <code>null</code>) object used to configure
	 * the join column strategy
	 */
	ReadOnlyJoinColumnRelationshipStrategy getJoinColumnJoiningStrategy();
	
	/**
	 * Return whether the join column strategy is currently the strategy
	 */
	boolean usesJoinColumnJoiningStrategy();
	
	/**
	 * Return whether this reference may potentially have a default join column.
	 * (For example, a 1-1 mapping may have one if it does not specify a mappedBy
	 * or a join table, but a 1-M mapping may not.)
	 */
	boolean mayHaveDefaultJoinColumn();
}
