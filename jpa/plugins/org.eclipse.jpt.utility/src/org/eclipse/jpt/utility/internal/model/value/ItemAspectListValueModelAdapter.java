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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.Counter;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;

/**
 * Abstract list value model that provides behavior for wrapping a list value
 * model (or collection value model) and listening for changes to aspects of the
 * *items* held by the list (or collection). Changes to the actual list
 * (or collection) are also monitored.
 * 
 * This is useful if you have a collection of items that can be modified by adding
 * or removing items or the items themselves might change in a fashion that
 * might change the collection's external appearance.
 * 
 * Subclasses need to override two methods:
 * 
 * #listenToItem(Model)
 *     begin listening to the appropriate aspect of the specified item and call
 *     #itemAspectChanged(Object) whenever the aspect changes
 * 
 * #stopListeningToItem(Model)
 *     stop listening to the appropriate aspect of the specified item
 */
public abstract class ItemAspectListValueModelAdapter<E>
	extends ListValueModelWrapper<E>
	implements ListValueModel<E>
{

	/**
	 * Maintain a counter for each of the items in the
	 * wrapped list holder we are listening to.
	 */
	protected final IdentityHashMap<E, Counter> counters;


	// ********** constructors **********

	/**
	 * Constructor - the list holder is required.
	 */
	protected ItemAspectListValueModelAdapter(ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.counters = new IdentityHashMap<E, Counter>();
	}

	/**
	 * Constructor - the collection holder is required.
	 */
	protected ItemAspectListValueModelAdapter(CollectionValueModel<? extends E> collectionHolder) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder));
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.listHolder.listIterator());
	}

	public E get(int index) {
		return this.listHolder.get(index);
	}

	public int size() {
		return this.listHolder.size();
	}

	public Object[] toArray() {
		return this.listHolder.toArray();
	}


	// ********** behavior **********

	/**
	 * Start listening to the list holder and the items in the list.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.engageAllItems();
	}

	protected void engageAllItems() {
		this.engageItems(this.listHolder.iterator());
	}

	protected void engageItems(Iterator<? extends E> stream) {
		while (stream.hasNext()) {
			this.engageItem(stream.next());
		}
	}

	protected void engageItem(E item) {
		// listen to an item only once
		Counter counter = this.counters.get(item);
		if (counter == null) {
			counter = new Counter();
			this.counters.put(item, counter);
			this.startListeningToItem((Model) item);
		}
		counter.increment();
	}

	/**
	 * Start listening to the specified item.
	 */
	protected abstract void startListeningToItem(Model item);

	/**
	 * Stop listening to the list holder and the items in the list.
	 */
	@Override
	protected void disengageModel() {
		this.disengageAllItems();
		super.disengageModel();
	}

	protected void disengageAllItems() {
		this.disengageItems(this.listHolder.iterator());
	}

	protected void disengageItems(Iterator<? extends E> stream) {
		while (stream.hasNext()) {
			this.disengageItem(stream.next());
		}
	}

	protected void disengageItem(E item) {
		// stop listening to an item only once
		Counter counter = this.counters.get(item);
		if (counter == null) {
			// something is wrong if this happens...  ~bjv
			throw new IllegalStateException("missing counter: " + item);
		}
		if (counter.decrement() == 0) {
			this.counters.remove(item);
			this.stopListeningToItem((Model) item);
		}
	}

	/**
	 * Stop listening to the specified item.
	 */
	protected abstract void stopListeningToItem(Model item);


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder.
	 * Forward the event and begin listening to the added items.
	 */
	@Override
	protected void itemsAdded(ListChangeEvent event) {
		// re-fire event with the wrapper as the source
		this.fireItemsAdded(event.cloneWithSource(this, LIST_VALUES));
		this.engageItems(this.items(event));
	}

	/**
	 * Items were removed from the wrapped list holder.
	 * Stop listening to the removed items and forward the event.
	 */
	@Override
	protected void itemsRemoved(ListChangeEvent event) {
		this.disengageItems(this.items(event));
		// re-fire event with the wrapper as the source
		this.fireItemsRemoved(event.cloneWithSource(this, LIST_VALUES));
	}

	/**
	 * Items were replaced in the wrapped list holder.
	 * Stop listening to the removed items, forward the event,
	 * and begin listening to the added items.
	 */
	@Override
	protected void itemsReplaced(ListChangeEvent event) {
		this.disengageItems(this.replacedItems(event));
		// re-fire event with the wrapper as the source
		this.fireItemsReplaced(event.cloneWithSource(this, LIST_VALUES));
		this.engageItems(this.items(event));
	}

	/**
	 * Items were moved in the wrapped list holder.
	 * No need to change any listeners; just forward the event.
	 */
	@Override
	protected void itemsMoved(ListChangeEvent event) {
		// re-fire event with the wrapper as the source
		this.fireItemsMoved(event.cloneWithSource(this, LIST_VALUES));
	}

	/**
	 * The wrapped list holder was cleared.
	 * Stop listening to the removed items and forward the event.
	 */
	@Override
	protected void listCleared(ListChangeEvent event) {
		// we should only need to disengage each item once...
		// make a copy to prevent a ConcurrentModificationException
		Collection<E> keys = new ArrayList<E>(this.counters.keySet());
		this.disengageItems(keys.iterator());
		this.counters.clear();
		// re-fire event with the wrapper as the source
		this.fireListCleared(LIST_VALUES);
	}

	/**
	 * The wrapped list holder has changed in some dramatic fashion.
	 * Reconfigure our listeners and forward the event.
	 */
	@Override
	protected void listChanged(ListChangeEvent event) {
		// we should only need to disengage each item once...
		// make a copy to prevent a ConcurrentModificationException
		Collection<E> keys = new ArrayList<E>(this.counters.keySet());
		this.disengageItems(keys.iterator());
		this.counters.clear();
		// re-fire event with the wrapper as the source
		this.fireListChanged(LIST_VALUES);
		this.engageAllItems();
	}


	// ********** item change support **********

	/**
	 * The specified item has a bound property that has changed.
	 * Notify listeners of the change.
	 */
	protected void itemAspectChanged(EventObject event) {
		Object item = event.getSource();
		int index = this.lastIdentityIndexOf(item);
		while (index != -1) {
			this.itemAspectChanged(index, item);
			index = this.lastIdentityIndexOf(item, index);
		}
	}

	/**
	 * The specified item has a bound property that has changed.
	 * Notify listeners of the change.
	 */
	protected void itemAspectChanged(int index, Object item) {
		this.fireItemReplaced(LIST_VALUES, index, item, item);		// hmmm...
	}

	/**
	 * Return the last index of the specified item, using object
	 * identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o) {
		return this.lastIdentityIndexOf(o, this.listHolder.size());
	}

	/**
	 * Return the last index of the specified item, starting just before the
	 * the specified endpoint, and using object identity instead of equality.
	 */
	protected int lastIdentityIndexOf(Object o, int end) {
		for (int i = end; i-- > 0; ) {
			if (this.listHolder.get(i) == o) {
				return i;
			}
		}
		return -1;
	}

}
