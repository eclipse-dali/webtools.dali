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

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Attribute override container.
 * <p>
 * Clients:<ul>
 * <li>entity - override mapped superclass attribute mappings
 * <li>embedded mapping - override embeddable attribute mappings
 * <li>embedded ID mapping - override embeddable attribute mappings
 * <li>element collection mapping - override (value/map value and map key)
 *     embeddable attribute mappings
 * <li>one-to-many mapping - override (map key) embeddable attribute mappings
 * <li>many-to-many mapping - override (map key) embeddable attribute mappings
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface AttributeOverrideContainer
	extends OverrideContainer
{
	/**
	 * Return the column of the mapping or attribute override for the specified
	 * attribute. Return <code>null</code> if it does not exist. This
	 * column mapping/attribute override will be found in the mapped superclass
	 * (or embeddable), not in the owning entity.
	 */
	Column resolveOverriddenColumn(String attributeName);

	// covariant overrides
	ListIterable<? extends AttributeOverride> getOverrides();
	AttributeOverride getOverrideNamed(String name);
	ListIterable<? extends SpecifiedAttributeOverride> getSpecifiedOverrides();
	SpecifiedAttributeOverride getSpecifiedOverride(int index);
	SpecifiedAttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends VirtualAttributeOverride> getVirtualOverrides();
	VirtualAttributeOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	SpecifiedAttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends OverrideContainer.ParentAdapter
	{
		/**
		 * @see AttributeOverrideContainer#resolveOverriddenColumn(String)
		 */
		Column resolveOverriddenColumn(String attributeName);
	}
}
