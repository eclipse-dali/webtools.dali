/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jpt.utility.model.Model;

/**
 * A "collection add" event gets delivered whenever a model adds items to a
 * "bound" or "constrained" collection. A CollectionAddEvent is sent as an
 * argument to the CollectionChangeListener.
 * 
 * Design options:
 * - create a collection to wrap a single added or removed item
 * 	(this is the option we implemented below and in collaborating code)
 * 	since there is no way to optimize downstream code for
 * 	single items, we take another performance hit by building
 * 	a collection each time  (@see Collections#singleton(Object))
 * 	and forcing downstream code to use an iterator every time
 * 
 * - fire a separate event for each item added or removed
 * 	eliminates any potential for optimizations to downstream code
 * 
 * - add protocol to support both single items and collections
 * 	adds conditional logic to downstream code
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CollectionAddEvent extends CollectionChangeEvent {

	/** The items added to the collection. */
	private final Collection<?> addedItems;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new collection add event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 * @param items The items added to the collection.
	 */
	public CollectionAddEvent(Model source, String collectionName, Collection<?> addedItems) {
		super(source, collectionName);
		if (addedItems == null) {
			throw new NullPointerException();
		}
		this.addedItems = Collections.unmodifiableCollection(addedItems);
	}


	// ********** standard state **********

	/**
	 * Return the items added to the collection.
	 */
	public Iterable<?> getAddedItems() {
		return this.addedItems;
	}

	/**
	 * Return the number of items added to the collection.
	 */
	public int getAddedItemsSize() {
		return this.addedItems.size();
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	@Override
	public CollectionAddEvent cloneWithSource(Model newSource) {
		return this.cloneWithSource(newSource, this.collectionName);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	@Override
	public CollectionAddEvent cloneWithSource(Model newSource, String newCollectionName) {
		return new CollectionAddEvent(newSource, newCollectionName, this.addedItems);
	}

}
