/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import java.util.Iterator;

import org.eclipse.jpt.utility.model.Model;

/**
 * A "collection change" event gets delivered whenever a model changes a "bound"
 * or "constrained" collection. A CollectionChangeEvent is sent as an
 * argument to the CollectionChangeListener.
 * 
 * Normally a CollectionChangeEvent is accompanied by the collection name and
 * the items that were added to or removed from the changed collection.
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
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CollectionChangeEvent extends ChangeEvent {

	/** Name of the collection that changed. */
	private final String collectionName;

	/** The items that were added to or removed from the collection. May be empty, if not known. */
	private final Collection<?> items;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new collection change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 * @param items The items that were added to or removed from the collection.
	 */
	public CollectionChangeEvent(Model source, String collectionName, Collection<?> items) {
		super(source);
		if ((collectionName == null) || (items == null)) {
			throw new NullPointerException();
		}
		this.collectionName = collectionName;
		this.items = Collections.unmodifiableCollection(items);
	}

	/**
	 * Construct a new collection change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 */
	public CollectionChangeEvent(Model source, String collectionName) {
		this(source, collectionName, Collections.emptySet());
	}


	// ********** standard state **********

	/**
	 * Return the programmatic name of the collection that was changed.
	 */
	public String collectionName() {
		return this.collectionName;
	}

	@Override
	public String aspectName() {
		return this.collectionName;
	}

	/**
	 * Return an iterator on the items that were added to or
	 * removed from the collection.
	 * May be empty if inappropriate or unknown.
	 */
	public Iterator<?> items() {
		return this.items.iterator();
	}

	/**
	 * Return the number of items that were added to or
	 * removed from the collection.
	 * May be 0 if inappropriate or unknown.
	 */
	public int itemsSize() {
		return this.items.size();
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	@Override
	public CollectionChangeEvent cloneWithSource(Model newSource) {
		return new CollectionChangeEvent(newSource, this.collectionName, this.items);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	public CollectionChangeEvent cloneWithSource(Model newSource, String newCollectionName) {
		return new CollectionChangeEvent(newSource, newCollectionName, this.items);
	}

}
