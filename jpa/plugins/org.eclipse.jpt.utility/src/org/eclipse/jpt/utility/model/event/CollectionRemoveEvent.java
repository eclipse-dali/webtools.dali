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

import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.model.Model;

/**
 * A "collection remove" event gets delivered whenever a model removes items
 * from a "bound" or "constrained" collection. A CollectionRemoveEvent is sent
 * as an argument to the CollectionChangeListener.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
/*
 * See design discussion in CollectionAddEvent
 */
public class CollectionRemoveEvent extends CollectionEvent {

	/** The items removed from the collection. */
	private final Object[] items;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new collection remove event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 * @param items The items removed from the collection.
	 */
	public CollectionRemoveEvent(Model source, String collectionName, Collection<?> items) {
		this(source, collectionName, items.toArray());  // NPE if 'items' is null
	}

	private CollectionRemoveEvent(Model source, String collectionName, Object[] items) {
		super(source, collectionName);
		this.items = items;
	}


	// ********** standard state **********

	/**
	 * Return the items removed from the collection.
	 */
	public Iterable<?> getItems() {
		return new ArrayIterable<Object>(this.items);
	}

	/**
	 * Return the number of items removed from the collection.
	 */
	public int getItemsSize() {
		return this.items.length;
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public CollectionRemoveEvent clone(Model newSource) {
		return this.clone(newSource, this.collectionName);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	public CollectionRemoveEvent clone(Model newSource, String newCollectionName) {
		return new CollectionRemoveEvent(newSource, newCollectionName, this.items);
	}

}
