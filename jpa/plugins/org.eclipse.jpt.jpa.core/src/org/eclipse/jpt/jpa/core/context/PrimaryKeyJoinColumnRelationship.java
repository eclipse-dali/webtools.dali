/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Primary key join column relationship<ul>
 * <li>1:1
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface PrimaryKeyJoinColumnRelationship
	extends SpecifiedRelationship
{
	/**
	 * Return the (never <code>null</code>) strategy used to configure
	 * the relationship's primary key join column strategy.
	 */
	SpecifiedPrimaryKeyJoinColumnRelationshipStrategy getPrimaryKeyJoinColumnStrategy();

	/**
	 * Return whether the primary key join column strategy is the
	 * relationship's current strategy.
	 */
	boolean strategyIsPrimaryKeyJoinColumn();
		String STRATEGY_IS_PRIMARY_KEY_JOIN_COLUMN_PROPERTY = "strategyIsPrimaryKeyJoinColumn"; //$NON-NLS-1$

	/**
	 * Set the relationship's strategy to the primary key join column strategy.
	 */
	void setStrategyToPrimaryKeyJoinColumn();
}
