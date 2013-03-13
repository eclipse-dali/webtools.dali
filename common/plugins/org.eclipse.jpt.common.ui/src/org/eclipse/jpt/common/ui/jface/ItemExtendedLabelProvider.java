/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

/**
 * Implementations of this interface can be used to maintain the label (image
 * and text) and description of a specific item. The implementation will monitor
 * the item for any changes that affect the description and forward them
 * appropriately to the {@link Manager}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.ILabelProvider
 * @see org.eclipse.ui.navigator.IDescriptionProvider
 */
public interface ItemExtendedLabelProvider
	extends ItemLabelProvider
{
	/**
	 * Return the description for the provider's item.
	 * 
	 * @see org.eclipse.ui.navigator.IDescriptionProvider#getDescription(Object)
	 */
	String getDescription();

	/**
	 * An item extended label provider's manager is notified whenever the item's
	 * description has changed.
	 */
	interface Manager
		extends ItemLabelProvider.Manager
	{
		/**
		 * The description for the specified item has changed. Update appropriately.
		 */
		void updateDescription(Object item);
	}

	/**
	 * Factory interface for constructing item extended label providers.
	 */
	interface Factory {
		/**
		 * Build an extended label provider for the specified item.
		 */
		ItemExtendedLabelProvider buildProvider(Object item, Manager manager);
	}
}
