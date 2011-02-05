/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * Extend {@link ItemAspectListValueModelAdapter} to listen to one or more list
 * aspects of each item in the wrapped list model.
 */
public class ItemListListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the subject's lists that we listen to. */
	protected final String[] listNames;

	/** Listener that listens to all the items in the list. */
	protected final ListChangeListener itemListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(ListValueModel<E> listHolder, String... listNames) {
		super(listHolder);
		this.listNames = listNames;
		this.itemListListener = this.buildItemListListener();
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... listNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), listNames);
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that the List aspect has 
	 * changed.  Do the same thing no matter which event occurs.
	 */
	protected ListChangeListener buildItemListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void listCleared(ListClearEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void listChanged(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item list listener: " + Arrays.asList(ItemListListValueModelAdapter.this.listNames); //$NON-NLS-1$
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void engageItem_(Model item) {
		for (String listName : this.listNames) {
			item.addListChangeListener(listName, this.itemListListener);
		}
	}

	@Override
	protected void disengageItem_(Model item) {
		for (String listName : this.listNames) {
			item.removeListChangeListener(listName, this.itemListListener);
		}
	}

}
