/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * A <code>JoinColumnEnabledRelationshipReference</code> is a type of 
 * {@link RelationshipReference} that may utilize a 
 * {@link JoinColumnJoiningStrategy}
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see RelationshipReference
 * @see JoinColumn
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JoinColumnEnabledRelationshipReference
	extends RelationshipReference
{
	/**
	 * Return the aggregate (never null) object used to configure the join column 
	 * joining strategy
	 */
	JoinColumnJoiningStrategy getJoinColumnJoiningStrategy();
	
	/**
	 * Return whether the join column joining strategy is currently the 
	 * predominant joining strategy
	 */
	boolean usesJoinColumnJoiningStrategy();
	
	/**
	 * Set the join column joining strategy as the predominant joining strategy
	 */
	void setJoinColumnJoiningStrategy();
	
	/**
	 * Unset the join column joining strategy as the predominant joining strategy.
	 * This will not set any other joining strategy as the predominant one, so
	 * whichever one is also configured (is specified) or the default strategy
	 * will apply at that point.
	 */
	// TODO remove this API (bug 311945)- wanted to remove it when I fixed bug 311248.
	// API users should just call set on another strategy, this will unset all other strategies
	void unsetJoinColumnJoiningStrategy();
	
	/**
	 * Return whether this reference may potentially have a default join column.
	 * (For example, a 1-1 mapping may have one if it does not specify a mappedBy
	 * or a join table, but a 1-M mapping may not.)
	 */
	boolean mayHaveDefaultJoinColumn();
}
