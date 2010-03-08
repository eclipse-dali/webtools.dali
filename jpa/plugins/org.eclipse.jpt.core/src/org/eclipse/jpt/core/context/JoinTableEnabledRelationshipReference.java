/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
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
 * A <code>JoinTableEnabledRelationshipReference</code> is a type of 
 * {@link RelationshipReference} that may utilize a 
 * {@link JoinTableJoiningStrategy}
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see RelationshipReference
 * @see JoinTableJoiningStrategy
 * @see JoinTable
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JoinTableEnabledRelationshipReference
	extends RelationshipReference
{
	/**
	 * Return the aggregate (never null) object used to configure the join table 
	 * joining strategy
	 */
	JoinTableJoiningStrategy getJoinTableJoiningStrategy();
	
	/**
	 * Return whether the join table joining strategy is currently the 
	 * predominant joining strategy
	 */
	boolean usesJoinTableJoiningStrategy();
	
	/**
	 * Set the join table joining strategy as the predominant joining strategy
	 */
	void setJoinTableJoiningStrategy();
	
	/**
	 * Unset the join table joining strategy as the predominant joining strategy.
	 * This will not set any other joining strategy as the predominant one, so
	 * whichever one is also configured (is specified) or the default strategy
	 * will apply at that point.
	 */
	void unsetJoinTableJoiningStrategy();
	
	/**
	 * Return whether this reference may potentially have a default join table.
	 * (For example, a M-M mapping may have one if it does not specify a 
	 * mappedBy)
	 */
	boolean mayHaveDefaultJoinTable();
}
