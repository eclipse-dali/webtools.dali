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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * An adapter that allows us to make a <code>CollectionValueModel</code>
 * (or <code>ListValueModel</code>) behave like a <code>ListValueModel</code>
 * that keeps its contents sorted and notifies listeners appropriately.
 * <p>
 * The comparator can be changed at any time; allowing the same
 * adapter to be used with different sort criteria (e.g. when the user
 * wants to sort a list of files first by name, then by date, then by size).
 * <p>
 * NB: Since we only listen to the wrapped collection when we have
 * listeners ourselves and we can only stay in synch with the wrapped
 * collection while we are listening to it, results to various methods
 * (e.g. <code>#size()</code>, <code>#getItem(int)</code>) will be
 * unpredictable whenever
 * we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 */
public class SortedListValueModelAdapter<E>
	extends CollectionListValueModelAdapter<E>
{
	/**
	 * A comparator used for sorting the elements;
	 * if it is null, we use "natural ordering".
	 */
	protected Comparator<E> comparator;


	// ********** constructors **********

	/**
	 * Wrap the specified collection value model and sort its contents
	 * using the specified comparator.
	 */
	public SortedListValueModelAdapter(CollectionValueModel<? extends E> collectionHolder, Comparator<E> comparator) {
		super(collectionHolder);
		this.comparator = comparator;
	}

	/**
	 * Wrap the specified collection value model and sort its contents
	 * based on the elements' "natural ordering".
	 */
	public SortedListValueModelAdapter(CollectionValueModel<? extends E> collectionHolder) {
		this(collectionHolder, null);
	}

	/**
	 * Wrap the specified list value model and sort its contents
	 * using the specified comparator.
	 */
	public SortedListValueModelAdapter(ListValueModel<? extends E> listHolder, Comparator<E> comparator) {
		this(new ListCollectionValueModelAdapter<E>(listHolder), comparator);
	}

	/**
	 * Wrap the specified list value model and sort its contents
	 * based on the elements' "natural ordering".
	 */
	public SortedListValueModelAdapter(ListValueModel<? extends E> listHolder) {
		this(listHolder, null);
	}


	// ********** accessors **********

	public void setComparator(Comparator<E> comparator) {
		this.comparator = comparator;
		this.sortList();
	}


	// ********** behavior **********

	/**
	 * Sort the internal list before
	 * sending out change notification.
	 */
	@Override
	protected void postBuildList() {
		super.postBuildList();
		Collections.sort(this.list, this.comparator);
	}

	/**
	 * the list will need to be sorted after the item is added
	 */
	@Override
	protected void itemsAdded(CollectionChangeEvent event) {
		// first add the items and notify our listeners...
		super.itemsAdded(event);
		// ...then sort the list
		this.sortList();
	}

	/**
	 * sort the list and notify our listeners, if necessary;
	 */
	protected void sortList() {
		// save the unsorted state of the sorted list so we can minimize the number of "replaced" items
		@SuppressWarnings("unchecked")
		ArrayList<E> unsortedList = (ArrayList<E>) this.list.clone();
		Collections.sort(this.list, this.comparator);
		Range diffRange = CollectionTools.identityDiffRange(unsortedList, this.list);
		if (diffRange.size > 0) {
			List<E> unsortedItems = unsortedList.subList(diffRange.start, diffRange.end + 1);
			List<E> sortedItems = this.list.subList(diffRange.start, diffRange.end + 1);
			this.fireItemsReplaced(LIST_VALUES, diffRange.start, sortedItems, unsortedItems);
		}
	}

}
