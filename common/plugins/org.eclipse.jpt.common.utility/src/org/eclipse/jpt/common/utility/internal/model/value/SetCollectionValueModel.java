/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * A <code>SetCollectionValueModel</code> wraps another
 * {@link CollectionValueModel} and returns the items in the collection
 * only once.
 */
public class SetCollectionValueModel<E>
	extends CollectionValueModelWrapper<E>
	implements CollectionValueModel<E>
{
	private final HashBag<E> bag = new HashBag<E>();


	// ********** constructors **********

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model and a filter that simply accepts every object.
	 */
	public SetCollectionValueModel(CollectionValueModel<? extends E> collectionHolder) {
		super(collectionHolder);
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * list value model and a filter that simply accepts every object.
	 */
	public SetCollectionValueModel(ListValueModel<E> listHolder) {
		this(new ListCollectionValueModelAdapter<E>(listHolder));
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.bag.uniqueIterator());
	}

	public int size() {
		return this.bag.uniqueCount();
	}


	// ********** CollectionValueModelWrapper overrides/implementation **********

	@Override
	protected void engageModel() {
		super.engageModel();
		// synch our cache *after* we start listening to the nested collection,
		// since its value might change when a listener is added
		CollectionTools.addAll(this.bag, this.collectionHolder);
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// clear out the cache when we are not listening to the nested collection
		this.bag.clear();
	}

	@Override
	protected void itemsAdded(CollectionAddEvent event) {
		ArrayList<E> addedItems = new ArrayList<E>(event.getItemsSize());
		int uniqueCount = this.bag.uniqueCount();
		for (E item : this.getItems(event)) {
			this.bag.add(item);
			if (this.bag.uniqueCount() > uniqueCount) {
				uniqueCount = this.bag.uniqueCount();
				addedItems.add(item);
			}
		}
		this.fireItemsAdded(VALUES, addedItems);
	}

	@Override
	protected void itemsRemoved(CollectionRemoveEvent event) {
		ArrayList<E> removedItems = new ArrayList<E>(event.getItemsSize());
		int uniqueCount = this.bag.uniqueCount();
		for (E item : this.getItems(event)) {
			if (this.bag.remove(item)) {
				if (this.bag.uniqueCount() < uniqueCount) {
					uniqueCount = this.bag.uniqueCount();
					removedItems.add(item);
				}
			} else {
				throw new IllegalStateException("missing item: " + item); //$NON-NLS-1$
			}
		}
		this.fireItemsRemoved(VALUES, removedItems);
	}

	@Override
	protected void collectionCleared(CollectionClearEvent event) {
		this.clearCollection(this.bag, VALUES);
	}

	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.bag.clear();
		CollectionTools.addAll(this.bag, this.collectionHolder);
		this.fireCollectionChanged(VALUES, new HashSet<E>(this.bag));
	}

	@Override
	public void toString(StringBuilder sb) {
		StringTools.append(sb, this);
	}

}
