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
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface DerivedIdentity2_0
	extends JpaContextNode
{
	SingleRelationshipMapping2_0 getMapping();
	
	/**
	 * String associated with changes to the predominant strategy property
	 */
	final static String PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY = 
		"predominantDerivedIdentityStrategy";  //$NON-NLS-1$
	
	/**
	 * Return the predominant joining strategy, this should not be null
	 */
	DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy();
	
	/**
	 * Return whether there is no derived identity strategy
	 */
	boolean usesNullDerivedIdentityStrategy();
	
	/**
	 * Sets it so that there is no derived identity strategy.  This will unset all other strategies.
	 */
	void setNullDerivedIdentityStrategy();
	
	/**
	 * Return the aggregate (never null) id strategy
	 */
	IdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy();
	
	/**
	 * Return whether id is the strategy currently used by this object
	 */
	boolean usesIdDerivedIdentityStrategy();
	
	/**
	 * Sets the id derived identity strategy.  This will unset all other strategies.
	 */
	void setIdDerivedIdentityStrategy();
	
	/**
	 * Unsets the id derived identity strategy.
	 * This will not set any other strategy, so whichever other strategy is present (or the null strategy) 
	 * will apply at that point.
	 */
	void unsetIdDerivedIdentityStrategy();
	
	/**
	 * Return the aggregate (never null) maps id derived identity strategy
	 */
	MapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy();
	
	/**
	 * Return whether maps id is the strategy currently used by this object
	 */
	boolean usesMapsIdDerivedIdentityStrategy();
	
	/**
	 * Sets the maps id derived identity strategy.  This will unset all other strategies.
	 */
	void setMapsIdDerivedIdentityStrategy();
	
	/**
	 * Unsets the maps id derived identity strategy.
	 * This will not set any other strategy, so whichever other strategy is present (or the null strategy) 
	 * will apply at that point.
	 */
	void unsetMapsIdDerivedIdentityStrategy();
}
