/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.JpaContextNode;

/**
 * Derived identity
 * <p>
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
// TODO bjv rename methods
	/**
	 * Return the associated single relationship mapping.
	 */
	SingleRelationshipMapping2_0 getMapping();

	// ********** predominant strategy **********

	/**
	 * String associated with changes to the predominant derived identity
	 * strategy property.
	 */
	String PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY = "predominantDerivedIdentityStrategy";  //$NON-NLS-1$

	/**
	 * Return the mapping's predominant strategy.
	 */
	DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy();


	// ********** null strategy **********

	/**
	 * Return whether the mapping has no strategy.
	 */
	boolean usesNullDerivedIdentityStrategy();

	/**
	 * Configure the mapping so it has no strategy.
	 * This will remove all other strategies.
	 */
	void setNullDerivedIdentityStrategy();


	// ********** ID strategy **********

	/**
	 * Return the mapping's ID strategy.
	 */
	IdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy();

	/**
	 * Return whether the mapping uses an ID strategy.
	 */
	boolean usesIdDerivedIdentityStrategy();

	/**
	 * Configure the mapping so it has an ID strategy.
	 * This will remove all other strategies.
	 */
	void setIdDerivedIdentityStrategy();


	// ********** maps ID strategy **********

	/**
	 * Return the mapping's "maps ID" strategy.
	 */
	MapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy();

	/**
	 * Return whether the mapping uses a "maps ID" strategy.
	 */
	boolean usesMapsIdDerivedIdentityStrategy();

	/**
	 * Configure the mapping so it has a "maps ID" strategy.
	 * This will remove all other strategies.
	 */
	void setMapsIdDerivedIdentityStrategy();

}
