/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.event;

import java.util.Collection;

import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * A "list remove" event gets delivered whenever a model removes items
 * from a "bound" or "constrained" list. A <code>ListRemoveEvent</code> is sent
 * as an argument to the {@link org.eclipse.jpt.common.utility.model.listener.ListChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
/*
 * See design discussion in CollectionAddEvent
 */
public final class ListRemoveEvent extends ListEvent {

	/** The index at which the items were removed. */
	private final int index;

	/** The items removed from the list. */
	private final Object[] items;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new list remove event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the items were removed.
	 * @param item The item removed from the list.
	 */
	public ListRemoveEvent(Model source, String listName, int index, Object item) {
		this(source, listName, index, new Object[] {item});
	}

	/**
	 * Construct a new list remove event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the items were removed.
	 * @param items The items removed from the list.
	 */
	public ListRemoveEvent(Model source, String listName, int index, Collection<?> items) {
		this(source, listName, index, items.toArray());  // NPE if 'items' is null
	}

	private ListRemoveEvent(Model source, String listName, int index, Object[] items) {
		super(source, listName);
		this.index = index;
		this.items = items;
	}


	// ********** standard state **********

	/**
	 * Return the index at which the items were removed from the list.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Return the items removed from the list.
	 */
	public Iterable<?> getItems() {
		return new ArrayIterable<Object>(this.items);
	}

	/**
	 * Return the number of items removed from the list.
	 */
	public int getItemsSize() {
		return this.items.length;
	}

	@Override
	protected void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(": "); //$NON-NLS-1$
		StringBuilderTools.append(sb, this.items);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public ListRemoveEvent clone(Model newSource) {
		return this.clone(newSource, this.listName);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListRemoveEvent clone(Model newSource, String newListName) {
		return this.clone(newSource, newListName, 0);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name and displacing
	 * the index by the specified amount.
	 */
	public ListRemoveEvent clone(Model newSource, String newListName, int offset) {
		return new ListRemoveEvent(newSource, newListName, this.index + offset, this.items);
	}

}
