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
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * An adapter that allows us to make a {@link CollectionValueModel} behave like
 * a read-only {@link ListValueModel}, sorta.
 * <p>
 * To maintain a reasonably consistent appearance to client code, we
 * keep an internal list somewhat in synch with the wrapped collection.
 * <p>
 * <strong>NB:</strong> Since we only listen to the wrapped collection when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * collection while we are listening to it, results to various methods
 * (e.g. {@link #size()}, {@link #get(int)}) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, clients will also be listeners.
 */
public class CollectionListValueModelAdapter<E>
	extends AbstractListValueModel
	implements ListValueModel<E>
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<? extends E> collectionHolder;

	/** A listener that forwards any events fired by the collection holder. */
	protected final CollectionChangeListener collectionChangeListener;

	/**
	 * Our internal list, which holds the same elements as
	 * the wrapped collection, but keeps them in order.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList<E> list;


	// ********** constructors **********

	/**
	 * Wrap the specified collection value model.
	 */
	public CollectionListValueModelAdapter(CollectionValueModel<? extends E> collectionHolder) {
		super();
		if (collectionHolder == null) {
			throw new NullPointerException();
		}
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
		this.list = new ArrayList<E>(collectionHolder.size());
		// postpone building the list and listening to the underlying collection
		// until we have listeners ourselves...
	}


	// ********** initialization **********

	/**
	 * The wrapped collection has changed, forward an equivalent
	 * list change event to our listeners.
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				CollectionListValueModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				CollectionListValueModelAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				CollectionListValueModelAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionListValueModelAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.list);
	}

	public E get(int index) {
		return this.list.get(index);
	}

	public int size() {
		return this.list.size();
	}

	public Object[] toArray() {
		return this.list.toArray();
	}


	// ********** behavior **********

	@Override
	protected void engageModel() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// synch our list *after* we start listening to the collection holder,
		// since its value might change when a listener is added
		this.buildList();
	}

	@Override
	protected void disengageModel() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// clear out the list when we are not listening to the collection holder
		this.list.clear();
	}

	protected void buildList() {
		// if the new collection is empty, do nothing
		int size = this.collectionHolder.size();
		if (size != 0) {
			this.buildList(size);
		}
	}

	protected void buildList(int size) {
		this.list.ensureCapacity(size);
		for (E each : this.collectionHolder) {
			this.list.add(each);
		}
	}

	protected void itemsAdded(CollectionAddEvent event) {
		this.addItemsToList(this.indexToAddItems(), this.getItems(event), this.list, LIST_VALUES);
	}
	
	protected int indexToAddItems() {
		return this.list.size();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	protected void itemsRemoved(CollectionRemoveEvent event) {
		this.removeItemsFromList(this.getItems(event), this.list, LIST_VALUES);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}

	protected void collectionCleared(@SuppressWarnings("unused") CollectionClearEvent event) {
		this.clearList(this.list, LIST_VALUES);
	}
	
	/**
	 * synchronize our internal list with the wrapped collection
	 * and fire the appropriate events
	 */
	protected void collectionChanged(@SuppressWarnings("unused") CollectionChangeEvent event) {
		int size = this.collectionHolder.size();
		if (size == 0) {
			if (this.list.isEmpty()) {
				// no change
			} else {
				this.clearList(this.list, LIST_VALUES);
			}
		} else {
			if (this.list.isEmpty()) {
				this.buildList(size);
				this.fireItemsAdded(LIST_VALUES, 0, this.list);
			} else {
				this.list.clear();
				this.buildList(size);
				this.fireListChanged(LIST_VALUES, this.list);
			}
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.list);
	}

}
