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
import java.util.Collection;
import java.util.EventObject;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.IntReference;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Abstract list value model that provides behavior for wrapping a {@link ListValueModel}
 * (or {@link CollectionValueModel}) and listening for changes to aspects of the
 * <em>items</em> held by the list (or collection). Changes to the actual list
 * (or collection) are also monitored.
 * 
 * This is useful if you have a collection of items that can be modified by adding
 * or removing items or the items themselves might change in a fashion that
 * might change the list or collection's external appearance.
 * 
 * Subclasses need to override two methods:<ul>
 * <li>{@link #engageItem_(Model)}<p>
 *     begin listening to the appropriate aspect of the specified item and call
 *     {@link #itemAspectChanged(EventObject)} whenever the aspect changes
 * <li>{@link #disengageItem_(Model)}<p>
 *     stop listening to the appropriate aspect of the specified item
 * </ul>
 */
public abstract class ItemAspectListValueModelAdapter<E>
	extends ListValueModelWrapper<E>
	implements ListValueModel<E>
{

	/**
	 * Maintain a counter for each of the items in the
	 * wrapped list holder we are listening to.
	 */
	protected final IdentityHashMap<E, IntReference> counters;


	// ********** constructors **********

	/**
	 * Constructor - the list holder is required.
	 */
	protected ItemAspectListValueModelAdapter(ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.counters = new IdentityHashMap<E, IntReference>();
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
		this.engageItems(this.listHolder);
	}

	protected void engageItems(Iterable<? extends E> items) {
		for (E item : items) {
			this.engageItem(item);
		}
	}

	protected void engageItem(E item) {
		// listen to each item only once
		IntReference counter = this.counters.get(item);
		if (counter == null) {
			counter = new IntReference();
			this.counters.put(item, counter);
			this.engageItem_((Model) item);
		}
		counter.increment();
	}

	/**
	 * Start listening to the specified item.
	 */
	protected abstract void engageItem_(Model item);

	/**
	 * Stop listening to the list holder and the items in the list.
	 */
	@Override
	protected void disengageModel() {
		this.disengageAllItems();
		super.disengageModel();
	}

	protected void disengageAllItems() {
		this.disengageItems(this.listHolder);
	}

	protected void disengageItems(Iterable<? extends E> items) {
		for (E item : items) {
			this.disengageItem(item);
		}
	}

	protected void disengageItem(E item) {
		// stop listening to each item only once
		IntReference counter = this.counters.get(item);
		if (counter == null) {
			// something is wrong if this happens...  ~bjv
			throw new IllegalStateException("missing counter: " + item); //$NON-NLS-1$
		}
		if (counter.decrement() == 0) {
			this.counters.remove(item);
			this.disengageItem_((Model) item);
		}
	}

	/**
	 * Stop listening to the specified item.
	 */
	protected abstract void disengageItem_(Model item);

	@Override
	public void toString(StringBuilder sb) {
		StringTools.append(sb, this);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder.
	 * Forward the event and begin listening to the added items.
	 */
	@Override
	protected void itemsAdded(ListAddEvent event) {
		// re-fire event with the wrapper as the source
		this.fireItemsAdded(event.clone(this, LIST_VALUES));
		this.engageItems(this.getItems(event));
	}

	/**
	 * Items were removed from the wrapped list holder.
	 * Stop listening to the removed items and forward the event.
	 */
	@Override
	protected void itemsRemoved(ListRemoveEvent event) {
		this.disengageItems(this.getItems(event));
		// re-fire event with the wrapper as the source
		this.fireItemsRemoved(event.clone(this, LIST_VALUES));
	}

	/**
	 * Items were replaced in the wrapped list holder.
	 * Stop listening to the removed items, forward the event,
	 * and begin listening to the added items.
	 */
	@Override
	protected void itemsReplaced(ListReplaceEvent event) {
		this.disengageItems(this.getOldItems(event));
		// re-fire event with the wrapper as the source
		this.fireItemsReplaced(event.clone(this, LIST_VALUES));
		this.engageItems(this.getNewItems(event));
	}

	/**
	 * Items were moved in the wrapped list holder.
	 * No need to change any listeners; just forward the event.
	 */
	@Override
	protected void itemsMoved(ListMoveEvent event) {
		// re-fire event with the wrapper as the source
		this.fireItemsMoved(event.clone(this, LIST_VALUES));
	}

	/**
	 * The wrapped list holder was cleared.
	 * Stop listening to the removed items and forward the event.
	 */
	@Override
	protected void listCleared(ListClearEvent event) {
		// we should only need to disengage each item once...
		// make a copy to prevent a ConcurrentModificationException
		Collection<E> keys = new ArrayList<E>(this.counters.keySet());
		this.disengageItems(keys);
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
		this.disengageItems(keys);
		this.counters.clear();
		// re-fire event with the wrapper as the source
		this.fireListChanged(event.clone(this));
		this.engageAllItems();
	}


	// ********** item change support **********

	/**
	 * The specified item has a bound property that has changed.
	 * Notify listeners of the change. The listeners will have to determine
	 * whether the item aspect change is significant.
	 */
	protected void itemAspectChanged(@SuppressWarnings("unused") EventObject event) {
		this.fireListChanged(LIST_VALUES, CollectionTools.list(this.listHolder, this.listHolder.size()));
	}

}
