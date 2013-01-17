/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Manager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Factory interface for constructing item extended label providers.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ItemExtendedLabelProviderFactory {
	/**
	 * Build an extended label provider for the specified item.
	 */
	ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager);


	/**
	 * A <em>null</em> item extended label provider factory that returns a
	 * <em>null</em> provider.
	 * @see ItemExtendedLabelProvider.Null
	 */
	final class Null
		implements ItemExtendedLabelProviderFactory, Serializable
	{
		public static final ItemExtendedLabelProviderFactory INSTANCE = new Null();
		public static ItemExtendedLabelProviderFactory instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public ItemExtendedLabelProvider buildProvider(Object item, Manager manager) {
			return ItemExtendedLabelProvider.Null.instance();
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
