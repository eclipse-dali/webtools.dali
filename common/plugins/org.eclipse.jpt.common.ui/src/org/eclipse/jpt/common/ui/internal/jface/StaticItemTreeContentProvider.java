/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;

/**
 * Item tree content provider that contains unchanging parent and children.
 * 
 * @see org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Null
 */
public class StaticItemTreeContentProvider
	implements ItemTreeContentProvider
{
	protected final Object parent;
	protected final Object[] children;

	/**
	 * Construct an item tree content provider for an item with neither
	 * a parent nor children.
	 */
	public StaticItemTreeContentProvider() {
		this(null);
	}

	/**
	 * Construct an item tree content provider for an item with the specified
	 * parent but no children.
	 */
	public StaticItemTreeContentProvider(Object parent) {
		this(parent, Tools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Construct an item tree content provider for an item with the specified
	 * parent and children.
	 */
	public StaticItemTreeContentProvider(Object parent, Object[] children) {
		super();
		if (children == null) {
			throw new NullPointerException();
		}
		this.parent = parent;
		this.children = children;
	}

	public Object[] getElements() {
		return this.children;
	}

	public Object getParent() {
		return this.parent;
	}

	public Object[] getChildren() {
		return this.children;
	}

	public boolean hasChildren() {
		return this.children.length > 0;
	}

	public void dispose() {
		// NOP
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
