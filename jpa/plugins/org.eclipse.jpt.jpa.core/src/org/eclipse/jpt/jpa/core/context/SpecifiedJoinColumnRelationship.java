/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

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
 * @see SpecifiedRelationship
 * @see SpecifiedJoinColumn
 * 
 * @version 2.2
 * @since 2.2
 */
public interface SpecifiedJoinColumnRelationship
	extends JoinColumnRelationship, SpecifiedRelationship
{
	SpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy();
	
	/**
	 * Set the relationship's strategy to the join column strategy.
	 */
	void setStrategyToJoinColumn();
}
