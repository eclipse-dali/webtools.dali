/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import java.util.Iterator;

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * A <code>FilteringCollectionValueModel</code> wraps another
 * {@link CollectionValueModel} and uses a {@link Filter}
 * to determine which items in the collection are returned by calls
 * to {@link #iterator()}.
 * <p>
 * The filter can be changed at any time; allowing the same
 * adapter to be used with different filter criteria (e.g. when the user
 * wants to view a list of <code>.java</code> files).
 * <p>
 * <strong>NB:</strong> If the objects in the "filtered" collection can change in such a way
 * that they should be removed from the "filtered" collection, you will
 * need to wrap the original collection in an {@link ItemAspectListValueModelAdapter}.
 * For example, if the filter only "accepts" items whose names begin
 * with "X" and the names of the items can change, you will need to
 * wrap the original list of unfiltered items with an
 * {@link ItemPropertyListValueModelAdapter} that listens for changes to each
 * item's name and fires the appropriate event whenever an item's name
 * changes. The event will cause this wrapper to re-filter the changed
 * item and add or remove it from the "filtered" collection as appropriate.
 */
public class FilteringCollectionValueModel<E>
	extends CollectionValueModelWrapper<E>
	implements CollectionValueModel<E>
{
	/** This filters the items in the nested collection. */
	private Filter<E> filter;

	/** Cache the items that were accepted by the filter */
	private final Collection<E> filteredItems = new ArrayList<E>();


	// ********** constructors **********

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model and a filter that simply accepts every object.
	 */
	public FilteringCollectionValueModel(CollectionValueModel<? extends E> collectionHolder) {
		this(collectionHolder, Filter.Null.<E>instance());
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model and filter.
	 */
	public FilteringCollectionValueModel(CollectionValueModel<? extends E> collectionHolder, Filter<E> filter) {
		super(collectionHolder);
		this.filter = filter;
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * list value model and a filter that simply accepts every object.
	 */
	public FilteringCollectionValueModel(ListValueModel<E> listHolder) {
		this(new ListCollectionValueModelAdapter<E>(listHolder));
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * list value model and filter.
	 */
	public FilteringCollectionValueModel(ListValueModel<E> listHolder, Filter<E> filter) {
		this(new ListCollectionValueModelAdapter<E>(listHolder), filter);
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.filteredItems);
	}

	public int size() {
		return this.filteredItems.size();
	}


	// ********** CollectionValueModelWrapper overrides/implementation **********

	@Override
	protected void engageModel() {
		super.engageModel();
		// synch our cache *after* we start listening to the nested collection,
		// since its value might change when a listener is added
		CollectionTools.addAll(this.filteredItems, this.filter(this.collectionHolder));
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// clear out the cache when we are not listening to the nested collection
		this.filteredItems.clear();
	}

	@Override
	protected void itemsAdded(CollectionAddEvent event) {
		// filter the values before propagating the change event
		this.addItemsToCollection(this.filter(this.getItems(event)), this.filteredItems, VALUES);
	}

	@Override
	protected void itemsRemoved(CollectionRemoveEvent event) {
		// do *not* filter the values, because they may no longer be
		// "accepted" and that might be why they were removed in the first place;
		// anyway, any extraneous items are harmless
		this.removeItemsFromCollection(event.getItems(), this.filteredItems, VALUES);
	}

	@Override
	protected void collectionCleared(CollectionClearEvent event) {
		this.clearCollection(this.filteredItems, VALUES);
	}

	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.rebuildFilteredItems();
	}


	// ********** miscellaneous **********

	/**
	 * Change the filter and rebuild the collection.
	 */
	public void setFilter(Filter<E> filter) {
		this.filter = filter;
		this.rebuildFilteredItems();
	}

	/**
	 * Return an iterable that filters the specified iterable.
	 */
	protected Iterable<E> filter(Iterable<? extends E> items) {
		return new FilteringIterable<E>(items, this.filter);
	}

	/**
	 * Synchronize our cache with the wrapped collection.
	 */
	protected void rebuildFilteredItems() {
		this.filteredItems.clear();
		CollectionTools.addAll(this.filteredItems, this.filter(this.collectionHolder));
		this.fireCollectionChanged(VALUES, this.filteredItems);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.filteredItems);
	}

}
