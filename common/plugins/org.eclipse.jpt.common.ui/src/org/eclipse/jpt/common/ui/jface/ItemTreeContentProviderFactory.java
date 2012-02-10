/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

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
}
