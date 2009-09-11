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
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * An adapter that allows us to make a {@link ListValueModel} behave like
 * a read-only {@link CollectionValueModel}, sorta.
 * <p>
 * We keep an internal collection somewhat in synch with the wrapped list.
 * <p>
 * <strong>NB:</strong> Since we only listen to the wrapped list when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * list while we are listening to it, results to various methods
 * (e.g. {@link #size()}, {@link iterator()}) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class ListCollectionValueModelAdapter<E>
	extends AbstractCollectionValueModel
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


	// ********** constructors **********

	/**
	 * Wrap the specified list value model.
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


	// ********** initialization **********

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


	// ********** AbstractCollectionValueModel implementation **********

	@Override
	protected void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// synch our collection *after* we start listening to the list holder,
		// since its value might change when a listener is added
		this.buildCollection();
	}

	@Override
	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// clear out the collection when we are not listening to the list holder
		this.collection.clear();
	}


	// ********** behavior **********

	protected void itemsAdded(ListAddEvent event) {
		this.addItemsToCollection(this.getItems(event), this.collection, VALUES);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	protected void itemsRemoved(ListRemoveEvent event) {
		this.removeItemsFromCollection(this.getItems(event), this.collection, VALUES);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}

	protected void itemsReplaced(ListReplaceEvent event) {
		this.removeItemsFromCollection(this.getOldItems(event), this.collection, VALUES);
		this.addItemsToCollection(this.getNewItems(event), this.collection, VALUES);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getOldItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getOldItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getNewItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getNewItems();
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
		if (this.listHolder.size() == 0) {
			if (this.collection.isEmpty()) {
				// no change
			} else {
				this.clearCollection(this.collection, VALUES);
			}
		} else {
			if (this.collection.isEmpty()) {
				this.buildCollection();
				this.fireItemsAdded(VALUES, this.collection);
			} else {
				this.collection.clear();
				this.buildCollection();
				this.fireCollectionChanged(VALUES, this.collection);
			}
		}
	}

	protected void buildCollection() {
		// if the new list is empty, do nothing
		int size = this.listHolder.size();
		if (size != 0) {
			this.buildCollection(size);
		}
	}

	protected void buildCollection(int size) {
		this.collection.ensureCapacity(size);
		for (E each : this.listHolder) {
			this.collection.add(each);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collection);
	}

}
