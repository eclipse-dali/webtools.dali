/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * A wrapper that keeps the contents of a {@link ListValueModel} sorted
 * and notifies listeners appropriately.
 * <p>
 * The {@link Comparator} can be changed at any time; allowing the same
 * adapter to be used with different sort criteria (e.g. when the user
 * wants to sort a list of files first by name, then by date, then by size).
 * <p>
 * <b>NB:</b> Since we only listen to the wrapped collection when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * collection while we are listening to it, results to various methods
 * (e.g. {@link #size()}, {@link #get(int)}) will be unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 * 
 * @see SortedListValueModelAdapter
 */
public class SortedListValueModelWrapper<E>
	extends ListValueModelWrapper<E>
	implements ListValueModel<E>
{
	/**
	 * A comparator used for sorting the elements;
	 * if it is null, we use "natural ordering".
	 */
	protected Comparator<E> comparator;

	/**
	 * Our internal list, which holds the same elements as
	 * the wrapped list, but keeps them sorted.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList<E> sortedList;


	// ********** constructors **********

	/**
	 * Wrap the specified list value model and sort its contents
	 * using the specified comparator.
	 */
	public SortedListValueModelWrapper(ListValueModel<? extends E> listHolder, Comparator<E> comparator) {
		super(listHolder);
		this.comparator = comparator;
		this.sortedList = new ArrayList<E>(listHolder.size());
		// postpone building the sorted list and listening to the wrapped list
		// until we have listeners ourselves...
	}

	/**
	 * Wrap the specified list value model and sort its contents
	 * based on the elements' "natural ordering".
	 */
	public SortedListValueModelWrapper(ListValueModel<? extends E> listHolder) {
		this(listHolder, null);
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.sortedList);
	}

	public E get(int index) {
		return this.sortedList.get(index);
	}

	public int size() {
		return this.sortedList.size();
	}

	public Object[] toArray() {
		return this.sortedList.toArray();
	}


	// ********** accessors **********

	public void setComparator(Comparator<E> comparator) {
		this.comparator = comparator;
		this.sortList();
	}


	// ********** behavior **********

    @Override
	protected void engageModel() {
		super.engageModel();
		// synch the sorted list *after* we start listening to the wrapped list holder,
		// since its value might change when a listener is added
		this.buildSortedList();
	}

    @Override
	protected void disengageModel() {
		super.disengageModel();
		// clear out the sorted list when we are not listening to the wrapped list holder
		this.sortedList.clear();
	}

	protected void buildSortedList() {
		// if the new list is empty, do nothing
		int size = this.listHolder.size();
		if (size != 0) {
			this.buildSortedList(size);
		}
	}

	protected void buildSortedList(int size) {
		this.sortedList.ensureCapacity(size);
		for (E each : this.listHolder) {
			this.sortedList.add(each);
		}
		Collections.sort(this.sortedList, this.comparator);
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list.
	 */
    @Override
	protected void itemsAdded(ListAddEvent event) {
		// first add the items and notify our listeners...
		this.addItemsToList(this.getItems(event), this.sortedList, LIST_VALUES);
		// ...then sort the list and notify our listeners
		this.sortList();
	}

	/**
	 * Items were removed from the wrapped list.
	 */
    @Override
	protected void itemsRemoved(ListRemoveEvent event) {
		this.removeItemsFromList(this.getItems(event), this.sortedList, LIST_VALUES);
		// no sorting needed
	}

	/**
	 * Items were replaced in the wrapped list.
	 */
    @Override
	protected void itemsReplaced(ListReplaceEvent event) {
		// first remove the old items and notify our listeners...
		this.removeItemsFromList(this.getOldItems(event), this.sortedList, LIST_VALUES);
		// then add the new items and notify our listeners...
		this.addItemsToList(this.getNewItems(event), this.sortedList, LIST_VALUES);
		// ...then sort the list and notify our listeners
		this.sortList();
	}

	/**
	 * Items were moved in the wrapped list.
	 */
    @Override
	protected void itemsMoved(ListMoveEvent event) {
    	// do nothing - sort order should remain unchanged
	}

	/**
	 * The wrapped list was cleared.
	 */
    @Override
	protected void listCleared(ListClearEvent event) {
    	this.clearList(this.sortedList, LIST_VALUES);
	}

	/**
	 * The wrapped list has changed in some dramatic fashion.
	 * Rebuild our sorted list and notify our listeners.
	 */
    @Override
	protected void listChanged(ListChangeEvent event) {
		int size = this.listHolder.size();
		if (size == 0) {
			if (this.sortedList.isEmpty()) {
				// no change
			} else {
				this.clearList(this.sortedList, LIST_VALUES);
			}
		} else {
			if (this.sortedList.isEmpty()) {
				this.buildSortedList(size);
				this.fireItemsAdded(LIST_VALUES, 0, this.sortedList);
			} else {
				this.sortedList.clear();
				this.buildSortedList(size);
				this.fireListChanged(LIST_VALUES, this.sortedList);
			}
		}
	}

	/**
	 * sort the sorted list and notify our listeners, if necessary;
	 */
	protected void sortList() {
		// save the unsorted state of the sorted list so we can minimize the number of "replaced" items
		@SuppressWarnings("unchecked")
		ArrayList<E> unsortedList = (ArrayList<E>) this.sortedList.clone();
		Collections.sort(this.sortedList, this.comparator);
		Range diffRange = CollectionTools.identityDiffRange(unsortedList, this.sortedList);
		if (diffRange.size > 0) {
			List<E> unsortedItems = unsortedList.subList(diffRange.start, diffRange.end + 1);
			List<E> sortedItems = this.sortedList.subList(diffRange.start, diffRange.end + 1);
			this.fireItemsReplaced(LIST_VALUES, diffRange.start, sortedItems, unsortedItems);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.sortedList);
	}

}
