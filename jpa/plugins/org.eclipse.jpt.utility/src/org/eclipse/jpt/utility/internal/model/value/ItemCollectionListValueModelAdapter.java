/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more collection
 * aspects of each item in the wrapped list model.
 */
public class ItemCollectionListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the items' collections that we listen to. */
	protected final String[] collectionNames;

	/** Listener that listens to all the items in the list. */
	protected final CollectionChangeListener itemCollectionListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item Collections.
	 */
	public ItemCollectionListValueModelAdapter(ListValueModel listHolder, String... collectionNames) {
		super(listHolder);
		this.collectionNames = collectionNames;
		this.itemCollectionListener = this.buildItemCollectionListener();
	}

	/**
	 * Construct an adapter for the specified item Collections.
	 */
	public ItemCollectionListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... collectionNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), collectionNames);
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a Collection aspect has 
	 * changed.  Do the same thing no matter which event occurs.
	 */
	protected CollectionChangeListener buildItemCollectionListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void collectionCleared(CollectionChangeEvent e) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(e);
			}
			public void collectionChanged(CollectionChangeEvent e) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(e);
			}
			@Override
			public String toString() {
				return "item collection listener: " + Arrays.asList(ItemCollectionListValueModelAdapter.this.collectionNames);
			}
		};
	}


	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (String collectionName : this.collectionNames) {
			item.addCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (String collectionName : this.collectionNames) {
			item.removeCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

}
