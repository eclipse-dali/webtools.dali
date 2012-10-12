/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * Extend {@link ItemAspectListValueModelAdapter} to listen to one or more collection
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
	public ItemCollectionListValueModelAdapter(ListValueModel<E> listHolder, String... collectionNames) {
		super(listHolder);
		if (collectionNames == null) {
			throw new NullPointerException();
		}
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
			public void itemsAdded(CollectionAddEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item collection listener: " + Arrays.asList(ItemCollectionListValueModelAdapter.this.collectionNames); //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	@Override
	protected void engageItem_(Model item) {
		for (String collectionName : this.collectionNames) {
			item.addCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

	@Override
	protected void disengageItem_(Model item) {
		for (String collectionName : this.collectionNames) {
			item.removeCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

}
