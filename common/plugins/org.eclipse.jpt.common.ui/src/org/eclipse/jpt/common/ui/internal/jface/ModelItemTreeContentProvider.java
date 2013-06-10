/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * Model {@link ItemTreeContentProvider item content provider} that provides
 * support for listening to an {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager
 * manager} whenever the item's children change in a significant way. Clients
 * must supply a {@link CollectionValueModel children model}.
 * <p>
 * <strong>NB:</strong>
 * This content provider can be used for <em>only</em> the branch and leaf items
 * of a tree, not the root item
 * (i.e. the {@link Viewer#getInput() viewer's input}).
 * The root item's content provider could be a
 * {@link ModelItemStructuredContentProvider}
 * 
 * @see StaticItemTreeContentProvider
 * @see ModelItemStructuredContentProvider
 */
public class ModelItemTreeContentProvider
	extends ModelItemContentProvider<ItemTreeContentProvider.Manager>
	implements ItemTreeContentProvider
{
	private final Object parent;


	public ModelItemTreeContentProvider(Object item, Object parent, CollectionValueModel<?> childrenModel, ItemTreeContentProvider.Manager manager) {
		super(item, childrenModel, manager);
		if (parent == null) {
			throw new NullPointerException();
		}
		this.parent = parent;
	}

	public Object getParent() {
		return this.parent;
	}

	public boolean hasChildren() {
		return this.children.length != 0;
	}

	@Override
	public Object[] getChildren() {
		return super.getChildren();
	}

	@Override
	/* private-protected */ void notifyManager(Iterable<?> addedChildren, Iterable<?> removedChildren) {
		this.manager.childrenChanged(this.item, addedChildren, removedChildren);
	}
}
