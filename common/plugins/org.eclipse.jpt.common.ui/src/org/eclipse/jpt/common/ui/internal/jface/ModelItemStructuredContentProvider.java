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

import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * Model {@link ItemStructuredContentProvider item content provider} that
 * provides support for listening to an {@link #item input} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider.Manager manager}
 * whenever the input's elements change in a significant way. Clients must
 * supply an {@link CollectionValueModel elements model}.
 * 
 * @see StaticItemStructuredContentProvider
 */
public class ModelItemStructuredContentProvider
	extends ModelItemContentProvider<ItemStructuredContentProvider.Manager>
	implements ItemStructuredContentProvider
{
	public ModelItemStructuredContentProvider(Object input, CollectionValueModel<?> elementsModel, ItemStructuredContentProvider.Manager manager) {
		super(input, elementsModel, manager);
	}

	/**
	 * Use the item's children as its "elements".
	 */
	public Object[] getElements() {
		return this.children;
	}

	@Override
	/* private-protected */ void notifyManager(Iterable<?> addedChildren, Iterable<?> removedChildren) {
		this.manager.elementsChanged(this.item, addedChildren, removedChildren);
	}
}
