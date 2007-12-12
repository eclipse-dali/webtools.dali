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

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * An adapter that allows us to make a ListValueModel behave like
 * a read-only CollectionValueModel, sorta.
 * 
 * We keep an internal collection somewhat in synch with the wrapped list.
 * 
 * NB: Since we only listen to the wrapped list when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * list while we are listening to it, results to various methods
 * (e.g. #size(), value()) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class ListCollectionValueModelAdapter
	extends AbstractModel
	implements CollectionValueModel
{
	/** The wrapped list value model. */
	protected final ListValueModel listHolder;

	/** A listener that forwards any events fired by the list holder. */
	protected final ListChangeListener listChangeListener;

	/**
	 * Our internal collection, which holds the same elements as
	 * the wrapped list.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList collection;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified ListValueModel.
	 */
	public ListCollectionValueModelAdapter(ListValueModel listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
		this.collection = new ArrayList();
		// postpone building the collection and listening to the underlying list
		// until we have listeners ourselves...
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}

	/**
	 * The wrapped list has changed, forward an equivalent
	 * collection change event to our listeners.
	 */
	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.itemsAdded(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.itemsRemoved(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.itemsReplaced(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.itemsMoved(e);
			}
			public void listCleared(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.listCleared(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListCollectionValueModelAdapter.this.listChanged(e);
			}
			@Override
			public String toString() {
				return "list change listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator iterator() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyIterator(this.collection);
	}

	public int size() {
		return this.collection.size();
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(listener);
	}

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Override to stop listening to the list holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		super.removeCollectionChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the list holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		super.removeCollectionChangeListener(collectionName, listener);
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * Return the index of the specified item, using object
	 * identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o) {
		return this.lastIdentityIndexOf(o, this.collection.size());
	}
	
	/**
	 * Return the last index of the specified item, starting just before the
	 * the specified endpoint, and using object identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o, int end) {
		for (int i = end; i-- > 0; ) {
			if (this.collection.get(i) == o) {
				return i;
			}
		}
		return -1;
	}
	

	// ********** behavior **********

	protected void buildCollection() {
		Iterator stream = this.listHolder.iterator();
		// if the new list is empty, do nothing
		if (stream.hasNext()) {
			this.collection.ensureCapacity(this.listHolder.size());
			while (stream.hasNext()) {
				this.collection.add(stream.next());
			}
		}
	}

	protected void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// synch our collection *after* we start listening to the list holder,
		// since its value might change when a listener is added
		this.buildCollection();
	}

	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// clear out the collection when we are not listening to the list holder
		this.collection.clear();
	}

	protected void itemsAdded(ListChangeEvent e) {
		this.addItemsToCollection(e.items(), this.collection, VALUES);
	}

	protected void removeInternalItems(Iterator items) {
		// we have to remove the items individually,
		// since they are probably not in sequence
		while (items.hasNext()) {
			Object removedItem = items.next();
			int index = this.lastIdentityIndexOf(removedItem);
			this.collection.remove(index);
			this.fireItemRemoved(VALUES, removedItem);
		}
	}

	protected void itemsRemoved(ListChangeEvent e) {
		this.removeInternalItems(e.items());
	}

	protected void itemsReplaced(ListChangeEvent e) {
		this.removeInternalItems(e.replacedItems());
		this.addItemsToCollection(e.items(), this.collection, VALUES);
	}

	protected void itemsMoved(ListChangeEvent e) {
		// do nothing? moving items in a list has no net effect on a collection...
	}

	protected void listCleared(ListChangeEvent e) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			this.collection.clear();
			this.fireCollectionCleared(VALUES);
		}
	}

	/**
	 * synchronize our internal collection with the wrapped list
	 * and fire the appropriate events
	 */
	protected void listChanged(ListChangeEvent e) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			ArrayList removedItems = (ArrayList) this.collection.clone();
			this.collection.clear();
			this.fireItemsRemoved(VALUES, removedItems);
		}

		this.buildCollection();
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			this.fireItemsAdded(VALUES, this.collection);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listHolder);
	}

}
