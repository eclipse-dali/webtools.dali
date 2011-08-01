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

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Association override container.
 * Used by entities, embedded mappings, and element collection mappings.
 * <p>
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
	ReadOnlyRelationship resolveOverriddenRelationship(String attributeName);

	// covariant overrides
	ListIterable<? extends ReadOnlyAssociationOverride> getOverrides();
	ReadOnlyAssociationOverride getOverrideNamed(String name);
	ListIterable<? extends AssociationOverride> getSpecifiedOverrides();
	AssociationOverride getSpecifiedOverride(int index);
	AssociationOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends VirtualAssociationOverride> getVirtualOverrides();
	VirtualAssociationOverride convertOverrideToVirtual(Override_ specifiedOverride);
	AssociationOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** owner **********

	interface Owner
		extends OverrideContainer.Owner
	{
		/**
		 * @see AssociationOverrideContainer#resolveOverriddenRelationship(String)
		 */
		ReadOnlyRelationship resolveOverriddenRelationship(String attributeName);
	}
}
