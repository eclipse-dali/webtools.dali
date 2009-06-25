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
 * A "collection remove" event gets delivered whenever a model removes items
 * from a "bound" or "constrained" collection. A CollectionRemoveEvent is sent
 * as an argument to the CollectionChangeListener.
 * 
 * @see CollectionAddEvent
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CollectionRemoveEvent extends CollectionChangeEvent {

	/** The items removed from the collection. */
	private final Collection<?> removedItems;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new collection remove event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 * @param items The items removed from the collection.
	 */
	public CollectionRemoveEvent(Model source, String collectionName, Collection<?> removedItems) {
		super(source, collectionName);
		if (removedItems == null) {
			throw new NullPointerException();
		}
		this.removedItems = Collections.unmodifiableCollection(removedItems);
	}


	// ********** standard state **********

	/**
	 * Return the items removed from the collection.
	 */
	public Iterable<?> getRemovedItems() {
		return this.removedItems;
	}

	/**
	 * Return the number of items removed from the collection.
	 */
	public int getRemovedItemsSize() {
		return this.removedItems.size();
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	@Override
	public CollectionRemoveEvent cloneWithSource(Model newSource) {
		return this.cloneWithSource(newSource, this.collectionName);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	@Override
	public CollectionRemoveEvent cloneWithSource(Model newSource, String newCollectionName) {
		return new CollectionRemoveEvent(newSource, newCollectionName, this.removedItems);
	}

}
