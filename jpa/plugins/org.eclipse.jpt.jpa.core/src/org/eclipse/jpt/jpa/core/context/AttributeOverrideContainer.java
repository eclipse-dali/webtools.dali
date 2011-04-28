/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;

/**
 * Attribute override container.
 * Used by entities, embedded mappings, and element collection mappings.
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
public interface AttributeOverrideContainer
	extends OverrideContainer
{
	ListIterator<? extends ReadOnlyAttributeOverride> overrides();
	ReadOnlyAttributeOverride getOverrideNamed(String name);
	ListIterator<? extends AttributeOverride> specifiedOverrides();
	AttributeOverride getSpecifiedOverride(int index);
	AttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterator<? extends VirtualAttributeOverride> virtualOverrides();
	VirtualAttributeOverride convertOverrideToVirtual(Override_ specifiedOverride);
	AttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);

	/**
	 * Return the column of the mapping or attribute override with the specified
	 * attribute name. Return <code>null</code> if it does not exist. This
	 * column mapping/attribute override will be found in the mapped superclass
	 * (or embeddable), not in the owning entity.
	 */
	Column resolveOverriddenColumn(String attributeName);


	// ********** owner **********

	interface Owner
		extends OverrideContainer.Owner
	{
		/**
		 * @see AttributeOverrideContainer#resolveOverriddenColumn(String)
		 */
		Column resolveOverriddenColumn(String attributeName);
	}
}
