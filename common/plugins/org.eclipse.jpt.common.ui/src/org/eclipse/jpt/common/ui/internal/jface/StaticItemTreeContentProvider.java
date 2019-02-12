/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Item tree content provider that contains unchanging parent and children.
 * 
 * @see NullItemTreeContentProvider
 */
public class StaticItemTreeContentProvider
	extends StaticItemContentProvider<ItemTreeContentProvider.Manager>
	implements ItemTreeContentProvider
{
	private final Object parent;


	/**
	 * Construct an item tree content provider for a <em>leaf</em> item with the
	 * specified parent (but no children).
	 */
	public StaticItemTreeContentProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		this(item, parent, ObjectTools.EMPTY_OBJECT_ARRAY, manager);
	}

	/**
	 * Construct an item tree content provider for a <em>branch</em> item with
	 * the specified parent and children.
	 */
	public StaticItemTreeContentProvider(Object item, Object parent, Object[] children, ItemTreeContentProvider.Manager manager) {
		super(item, children, manager);
		if (parent == null) {
			throw new NullPointerException();
		}
		this.parent = parent;
	}

	public Object getParent() {
		return this.parent;
	}

	public boolean hasChildren() {
		return this.children.length > 0;
	}

	@Override
	public Object[] getChildren() {
		return this.children;
	}
}
