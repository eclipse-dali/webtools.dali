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
import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * An adapter that allows us to make a CollectionValueModel behave like
 * a read-only ListValueModel, sorta.
 * 
 * To maintain a reasonably consistent appearance to client code, we
 * keep an internal list somewhat in synch with the wrapped collection.
 * 
 * NB: Since we only listen to the wrapped collection when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * collection while we are listening to it, results to various methods
 * (e.g. #size(), getItem(int)) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class CollectionListValueModelAdapter
	extends AbstractModel
	implements ListValueModel
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel collectionHolder;

	/** A listener that forwards any events fired by the collection holder. */
	protected final CollectionChangeListener collectionChangeListener;

	/**
	 * Our internal list, which holds the same elements as
	 * the wrapped collection, but keeps them in order.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList list;


	// ********** constructors **********

	/**
	 * Wrap the specified CollectionValueModel.
	 */
	public CollectionListValueModelAdapter(CollectionValueModel collectionHolder) {
		super();
		if (collectionHolder == null) {
			throw new NullPointerException();
		}
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
		this.list = new ArrayList();
		// postpone building the list and listening to the underlying collection
		// until we have listeners ourselves...
	}


	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, VALUE);
	}

	/**
	 * The wrapped collection has changed, forward an equivalent
	 * list change event to our listeners.
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				CollectionListValueModelAdapter.this.itemsAdded(e);
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				CollectionListValueModelAdapter.this.itemsRemoved(e);
			}
			public void collectionCleared(CollectionChangeEvent e) {
				CollectionListValueModelAdapter.this.collectionCleared(e);
			}
			public void collectionChanged(CollectionChangeEvent e) {
				CollectionListValueModelAdapter.this.collectionChanged(e);
			}
			@Override
			public String toString() {
				return "collection change listener";
			}
		};
	}


	// ********** ValueModel implementation **********

	public Object value() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyListIterator(this.list);
	}


	// ********** ListValueModel implementation **********

	public void addItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public void addItems(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object removeItem(int index) {
		throw new UnsupportedOperationException();
	}

	public List removeItems(int index, int length) {
		throw new UnsupportedOperationException();
	}

	public Object replaceItem(int index, Object item) {
		throw new UnsupportedOperationException();
	}

	public List replaceItems(int index, List items) {
		throw new UnsupportedOperationException();
	}

	public Object getItem(int index) {
		return this.list.get(index);
	}

	public int size() {
		return this.list.size();
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the collection holder if necessary.
	 */
	@Override
	public void addListChangeListener(ListChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listener);
	}

	/**
	 * Override to start listening to the collection holder if necessary.
	 */
	@Override
	public void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName == VALUE && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}

	/**
	 * Override to stop listening to the collection holder if appropriate.
	 */
	@Override
	public void removeListChangeListener(ListChangeListener listener) {
		super.removeListChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the collection holder if appropriate.
	 */
	@Override
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		super.removeListChangeListener(listName, listener);
		if (listName == VALUE && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(VALUE);
	}

	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * Return the index of the specified item, using object
	 * identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o) {
		return this.lastIdentityIndexOf(o, this.list.size());
	}
	
	/**
	 * Return the last index of the specified item, starting just before the
	 * the specified endpoint, and using object identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o, int end) {
		for (int i = end; i-- > 0; ) {
			if (this.list.get(i) == o) {
				return i;
			}
		}
		return -1;
	}
	

	// ********** behavior **********

	protected void buildList() {
		Iterator stream = (Iterator) this.collectionHolder.values();
		// if the new collection is empty, do nothing
		if (stream.hasNext()) {
			this.list.ensureCapacity(this.collectionHolder.size());
			while (stream.hasNext()) {
				this.list.add(stream.next());
			}
			this.postBuildList();
		}
	}

	/**
	 * Allow subclasses to manipulate the internal list before
	 * sending out change notification.
	 */
	protected void postBuildList() {
		// the default is to do nothing...
	}

	protected void engageModel() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// synch our list *after* we start listening to the collection holder,
		// since its value might change when a listener is added
		this.buildList();
	}

	protected void disengageModel() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// clear out the list when we are not listening to the collection holder
		this.list.clear();
	}

	protected void itemsAdded(CollectionChangeEvent e) {
		this.addItemsToList(this.indexToAddItems(), CollectionTools.list(e.items()), this.list, VALUE);
	}
	
    protected int indexToAddItems() {
        return this.list.size();
    }
    
	protected void itemsRemoved(CollectionChangeEvent e) {
		// we have to remove the items individually,
		// since they are probably not in sequence
		for (Iterator stream = e.items(); stream.hasNext(); ) {
			this.removeItemFromList(this.lastIdentityIndexOf(stream.next()), this.list, VALUE);
		}
	}

	protected void collectionCleared(CollectionChangeEvent e) {
		this.clearList(this.list, VALUE);
	}
	
	/**
	 * synchronize our internal list with the wrapped collection
	 * and fire the appropriate events
	 */
	protected void collectionChanged(CollectionChangeEvent e) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.list.isEmpty()) {
			ArrayList removedItems = (ArrayList) this.list.clone();
			this.list.clear();
			this.fireItemsRemoved(VALUE, 0, removedItems);
		}

		this.buildList();
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.list.isEmpty()) {
			this.fireItemsAdded(VALUE, 0, this.list);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collectionHolder);
	}

}
