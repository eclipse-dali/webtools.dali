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
 * An <code>OwnableRelationshipReference</code> is a type of 
 * {@link RelationshipReference} that may be the owned side of the relationship.
 * (i.e. It may use a "mappedBy" joining strategy.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see RelationshipReference
 * 
 * @version 2.2
 * @since 2.2
 */
public interface OwnableRelationshipReference
	extends RelationshipReference
{
	/**
	 * Return the aggregate (never null) object used to configure the mapped by
	 * joining strategy
	 */
	MappedByJoiningStrategy getMappedByJoiningStrategy();
	
	/**
	 * Return whether the mapped by joining strategy is currently the predominant
	 * joining strategy
	 */
	boolean usesMappedByJoiningStrategy();
	
	/**
	 * Set the mapped by joining strategy as the predominant joining strategy
	 */
	void setMappedByJoiningStrategy();
	
	/**
	 * Unset the mapped by joining strategy as the predominant joining strategy.
	 * This will not set any other joining strategy as the predominant one, so
	 * whichever one is also being used (is specified) or the default strategy
	 * will apply at that point.
	 */
	// TODO remove this API (bug 311945)- wanted to remove it when I fixed bug 311248.
	// API users should just call set on another strategy, this will unset all other strategies
	void unsetMappedByJoiningStrategy();
	
	/**
	 * Validates whether the given mapping may own the relationship
	 */
	boolean mayBeMappedBy(AttributeMapping mapping);
}
