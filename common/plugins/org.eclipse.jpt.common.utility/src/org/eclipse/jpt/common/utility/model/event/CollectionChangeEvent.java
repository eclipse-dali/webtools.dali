/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.event;

import java.util.Collection;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * A "collection change" event gets delivered whenever a model changes a "bound"
 * or "constrained" collection in a manner that is not easily characterized by
 * the other collection events.
 * A <code>CollectionChangeEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener}.
 * A <code>CollectionChangeEvent</code> is accompanied by the collection name and
 * the current state of the collection.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class CollectionChangeEvent extends CollectionEvent {

	/**
	 * The the collection in its current state.
	 * Clients will need to calculate the necessary changes to synchronize
	 * with the collection.
	 */
	private final Object[] collection;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new collection change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param collectionName The programmatic name of the collection that was changed.
	 */
	public CollectionChangeEvent(Model source, String collectionName, Collection<?> collection) {
		this(source, collectionName, collection.toArray());  // NPE if 'collection' is null
	}

	private CollectionChangeEvent(Model source, String collectionName, Object[] collection) {
		super(source, collectionName);
		this.collection = collection;
	}


	// ********** standard state **********

	/**
	 * Return the current state of the collection.
	 */
	public Iterable<?> getCollection() {
		return new ArrayIterable<Object>(this.collection);
	}

	/**
	 * Return the number of items in the current state of the collection.
	 */
	public int getCollectionSize() {
		return this.collection.length;
	}

	@Override
	protected void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(": "); //$NON-NLS-1$
		StringTools.append(sb, this.collection);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public CollectionChangeEvent clone(Model newSource) {
		return this.clone(newSource, this.collectionName);
	}

	/**
	 * Return a copy of the event with the specified source and collection name
	 * replacing the current source and collection name.
	 */
	public CollectionChangeEvent clone(Model newSource, String newCollectionName) {
		return new CollectionChangeEvent(newSource, newCollectionName, this.collection);
	}

}
