/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more list
 * aspects of each item in the wrapped list model.
 */
public class ItemListListValueModelAdapter extends ItemAspectListValueModelAdapter {

	/** The names of the subject's lists that we listen to. */
	protected final String[] listNames;

	/** Listener that listens to all the items in the list. */
	protected ListChangeListener itemListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item List aspect.
	 */
	public ItemListListValueModelAdapter(ListValueModel listHolder, String listName) {
		this(listHolder, new String[] {listName});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(ListValueModel listHolder, String listName1, String listName2) {
		this(listHolder, new String[] {listName1, listName2});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(ListValueModel listHolder, String listName1, String listName2, String listName3) {
		this(listHolder, new String[] {listName1, listName2, listName3});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(ListValueModel listHolder, String[] listNames) {
		super(listHolder);
		this.listNames = listNames;
	}

	/**
	 * Construct an adapter for the specified item List aspect.
	 */
	public ItemListListValueModelAdapter(CollectionValueModel collectionHolder, String listName) {
		this(collectionHolder, new String[] {listName});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(CollectionValueModel collectionHolder, String listName1, String listName2) {
		this(collectionHolder, new String[] {listName1, listName2});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(CollectionValueModel collectionHolder, String listName1, String listName2, String listName3) {
		this(collectionHolder, new String[] {listName1, listName2, listName3});
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 */
	public ItemListListValueModelAdapter(CollectionValueModel collectionHolder, String[] listNames) {
		super(collectionHolder);
		this.listNames = listNames;
	}


	// ********** initialization **********

	@Override
	protected void initialize() {
		super.initialize();
		this.itemListListener = this.buildItemListListener();
	}

	/**
	 * All we really care about is the fact that the List aspect has 
	 * changed.  Do the same thing no matter which event occurs.
	 */
	protected ListChangeListener buildItemListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void listCleared(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void listChanged(ListChangeEvent e) {
				ItemListListValueModelAdapter.this.itemAspectChanged(e);
			}
			@Override
			public String toString() {
				return "item list listener: " + Arrays.asList(ItemListListValueModelAdapter.this.listNames);
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (int i = this.listNames.length; i-- > 0; ) {
			item.addListChangeListener(this.listNames[i], this.itemListListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (int i = this.listNames.length; i-- > 0; ) {
			item.removeListChangeListener(this.listNames[i], this.itemListListener);
		}
	}

}
