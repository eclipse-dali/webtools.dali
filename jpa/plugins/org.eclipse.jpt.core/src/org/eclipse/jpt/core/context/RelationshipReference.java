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
 * A <code>RelationshipReference</code> represents the meta-information required 
 * to populate the entities involved in a <code>RelationshipMapping</code>. This 
 * might includes join table information, join column information, and/or 
 * "mappedBy" information, to name a few.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 */
public interface RelationshipReference extends JpaContextNode
{
	RelationshipMapping getRelationshipMapping();

	/**
	 * Return whether this is the owning side of the relationship.
	 * @see {@link RelationshipMapping#isRelationshipOwner()}
	 */
	boolean isRelationshipOwner();
	
	/**
	 * Returns whether the given mapping is the owning side of a relationship
	 * with this relationship reference
	 */
	boolean isOwnedBy(RelationshipMapping mapping);
	
	/**
	 * String associated with changes to the predominant strategy property
	 */
	final static String PREDOMINANT_JOINING_STRATEGY_PROPERTY = 
		"predominantStrategy";  //$NON-NLS-1$
	
	JoiningStrategy getPredominantJoiningStrategy();
}
