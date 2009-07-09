/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

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
public class ListCollectionValueModelAdapter<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The wrapped list value model. */
	protected final ListValueModel<? extends E> listHolder;

	/** A listener that forwards any events fired by the list holder. */
	protected final ListChangeListener listChangeListener;

	/**
	 * Our internal collection, which holds the same elements as
	 * the wrapped list.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList<E> collection;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified ListValueModel.
	 */
	public ListCollectionValueModelAdapter(ListValueModel<? extends E> listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
		this.collection = new ArrayList<E>();
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
			public void itemsAdded(ListAddEvent event) {
				ListCollectionValueModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ListCollectionValueModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListCollectionValueModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListCollectionValueModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ListCollectionValueModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyIterator<E>(this.collection);
	}

	public int size() {
		return this.collection.size();
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addChangeListener(ChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addChangeListener(listener);
	}

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName.equals(VALUES) && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Override to stop listening to the list holder if appropriate.
	 */
	@Override
	public void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
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
		if (collectionName.equals(VALUES) && this.hasNoListeners()) {
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


	// ********** behavior **********

	protected void buildCollection() {
		Iterator<? extends E> stream = this.listHolder.iterator();
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

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getNewItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getNewItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getOldItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getOldItems();
	}

	protected void itemsAdded(ListAddEvent event) {
		this.addItemsToCollection(this.getItems(event), this.collection, VALUES);
	}

	protected void removeInternalItems(Iterable<E> removedItems) {
		// we have to remove the items individually,
		// since they are probably not in sequence
		for (E removedItem : removedItems) {
			this.removeItemFromCollection(removedItem, this.collection, VALUES);
		}
	}

	protected void itemsRemoved(ListRemoveEvent event) {
		this.removeInternalItems(this.getItems(event));
	}

	protected void itemsReplaced(ListReplaceEvent event) {
		this.removeInternalItems(this.getOldItems(event));
		this.addItemsToCollection(this.getNewItems(event), this.collection, VALUES);
	}

	protected void itemsMoved(@SuppressWarnings("unused") ListMoveEvent event) {
		// do nothing? moving items in a list has no net effect on a collection...
	}

	protected void listCleared(@SuppressWarnings("unused") ListClearEvent event) {
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
	protected void listChanged(@SuppressWarnings("unused") ListChangeEvent event) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			@SuppressWarnings("unchecked")
			ArrayList<E> removedItems = (ArrayList<E>) this.collection.clone();
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
