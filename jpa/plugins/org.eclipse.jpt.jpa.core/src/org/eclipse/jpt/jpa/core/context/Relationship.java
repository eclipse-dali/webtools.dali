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
 * A relationship contains the settings describing how entities are related
 * in a {@link RelationshipMapping} or {@link SpecifiedAssociationOverride}:<ul>
 * <li>join column
 * <li>join table
 * <li>"mapped by"
 * <li>primary key join column
 * </ul>
 * Supported mappings:<ul>
 * <li>1:1
 * <li>1:m
 * <li>m:1
 * <li>m:m
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see RelationshipMapping
 * @see SpecifiedAssociationOverride
 */
public interface Relationship
	extends JpaContextModel
{
	/**
	 * Return the relationship's mapping; which for a mapping relationship is
	 * the relationship's parent, but in the case of an override is the
	 * overridden mapping (from a superclass or embeddable type).
	 */
	RelationshipMapping getMapping();

	/**
	 * Return the type mapping that contains the relationship's mapping or
	 * override.
	 */
	TypeMapping getTypeMapping();

	/**
	 * Return the entity that contains the relationship's mapping or override.
	 * This is just a convenience method that calls {@link #getTypeMapping()}
	 * and returns <code>null</code> if the result is not an {@link Entity}.
	 */
	Entity getEntity();

	/**
	 * String associated with changes to the predominant strategy property
	 */
	final static String STRATEGY_PROPERTY = "strategy";  //$NON-NLS-1$

	/**
	 * Return the current strategy, this is never <code>null</code>.
	 */
	RelationshipStrategy getStrategy();

	/**
	 * Return whether the the relationship is virtual.
	 */
	boolean isVirtual();
}
