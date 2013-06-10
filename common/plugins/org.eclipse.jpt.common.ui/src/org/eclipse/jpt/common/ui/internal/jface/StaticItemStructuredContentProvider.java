/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;

/**
 * Item structured content provider that contains unchanging elements.
 * 
 * @see NullItemStructuredContentProvider
 */
public class StaticItemStructuredContentProvider
	extends StaticItemContentProvider<ItemStructuredContentProvider.Manager>
	implements ItemStructuredContentProvider
{
	/**
	 * Construct an item structured content provider for an item with the
	 * specified elements.
	 * @see NullItemStructuredContentProvider
	 */
	public StaticItemStructuredContentProvider(Object item, Object[] elements, ItemStructuredContentProvider.Manager manager) {
		super(item, elements, manager);
	}

	public Object[] getElements() {
		return this.children;
	}
}
