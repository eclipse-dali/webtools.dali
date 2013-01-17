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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

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
	 * A <em>null</em> item extended label provider that returns a
	 * <code>null</code> image, a <code>null</code> text string, and
	 * a <code>null</code> description string.
	 */
	final class Null
		implements ItemExtendedLabelProvider, Serializable
	{
		public static final ItemExtendedLabelProvider INSTANCE = new Null();
		public static ItemExtendedLabelProvider instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public Image getImage() {
			return null;
		}
		public String getText() {
			return null;
		}
		public String getDescription() {
			return null;
		}
		public void dispose() {
			// NOP
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
