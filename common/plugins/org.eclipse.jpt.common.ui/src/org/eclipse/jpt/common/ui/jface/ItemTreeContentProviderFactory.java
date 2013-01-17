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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Factory interface for constructing item tree content providers.
 * Typically used by {@link ItemTreeContentProvider.Manager item tree content
 * provider managers}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ItemTreeContentProviderFactory {
	/**
	 * Build a tree content provider for the specified item.
	 * Return <code>null</code> if there is no provider for the specified item.
	 */
	ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager);


	/**
	 * A <em>null</em> item tree content provider factory that returns a
	 * <em>null</em> provider.
	 * @see ItemTreeContentProvider.Null
	 */
	final class Null
		implements ItemTreeContentProviderFactory, Serializable
	{
		public static final ItemTreeContentProviderFactory INSTANCE = new Null();
		public static ItemTreeContentProviderFactory instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public ItemTreeContentProvider buildProvider(Object item, org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager manager) {
			return ItemTreeContentProvider.Null.instance();
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
