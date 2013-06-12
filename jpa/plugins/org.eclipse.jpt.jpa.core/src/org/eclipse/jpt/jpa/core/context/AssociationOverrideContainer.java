/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Association override container.
 * <p>
 * Clients:<ul>
 * <li>entity - override mapped superclass association mappings
 * <li>embedded mapping - override embeddable association mappings
 * <li>element collection mapping - override (value/map value)
 *     embeddable association mappings
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.3
 */
public interface AssociationOverrideContainer
	extends OverrideContainer
{
	/**
	 * Return the relationship mapping for the specified attribute.
	 * Return <code>null</code> if it does not exist. This relationship mapping
	 * will be found in the mapped superclass or embeddable type whose mapping
	 * is being overridden, not in the owning entity
	 */
	RelationshipMapping getRelationshipMapping(String attributeName);

	/**
	 * Return the relationship with the specified attribute name.
	 */
	Relationship resolveOverriddenRelationship(String attributeName);

	// covariant overrides
	ListIterable<? extends AssociationOverride> getOverrides();
	AssociationOverride getOverrideNamed(String name);
	ListIterable<? extends SpecifiedAssociationOverride> getSpecifiedOverrides();
	SpecifiedAssociationOverride getSpecifiedOverride(int index);
	SpecifiedAssociationOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends VirtualAssociationOverride> getVirtualOverrides();
	VirtualAssociationOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	SpecifiedAssociationOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends OverrideContainer.ParentAdapter
	{
		/**
		 * @see AssociationOverrideContainer#resolveOverriddenRelationship(String)
		 */
		Relationship resolveOverriddenRelationship(String attributeName);
	}
}
