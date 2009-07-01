/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import java.util.List;

import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.model.Model;

/**
 * A "list change" event gets delivered whenever a model changes a "bound"
 * or "constrained" list. A ListChangeEvent is sent as an
 * argument to the ListChangeListener.
 * 
 * A ListChangeEvent is accompanied by the list name and
 * the current state of the list.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ListChangeEvent extends ListEvent {

	/**
	 * The the list in its current state.
	 * Clients will need to calculate the necessary changes to synchronize
	 * with the list.
	 */
	private final Object[] list;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new list change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 */
	public ListChangeEvent(Model source, String listName, List<?> list) {
		this(source, listName, list.toArray());  // NPE if 'list' is null
	}

	private ListChangeEvent(Model source, String listName, Object[] list) {
		super(source, listName);
		this.list = list;
	}


	// ********** standard state **********

	/**
	 * Return the current state of the list.
	 */
	public Iterable<?> getList() {
		return new ArrayIterable<Object>(this.list);
	}

	/**
	 * Return the number of items in the current state of the list.
	 */
	public int getListSize() {
		return this.list.length;
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public ListChangeEvent clone(Model newSource) {
		return this.clone(newSource, this.listName);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListChangeEvent clone(Model newSource, String newListName) {
		return new ListChangeEvent(newSource, newListName, this.list);
	}

}
