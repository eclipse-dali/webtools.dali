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
 * "Mapped by" relationship<ul>
 * <li>1:1
 * <li>1:m
 * <li>m:m
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
public interface MappedByRelationship
	extends SpecifiedRelationship
{
	/**
	 * Return the (never <code>null</code>) strategy used to configure
	 * the relationship's "mapped by" strategy.
	 */
	SpecifiedMappedByRelationshipStrategy getMappedByStrategy();

	/**
	 * Return whether the "mapped by" strategy is the
	 * relationship's current strategy.
	 */
	boolean strategyIsMappedBy();

	/**
	 * Set the relationship's strategy to the "mapped by" strategy.
	 */
	void setStrategyToMappedBy();

	/**
	 * Return whether the specified mapping may own the relationship.
	 */
	boolean mayBeMappedBy(AttributeMapping mapping);
}
