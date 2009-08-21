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

import java.util.List;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.model.Model;

/**
 * A "list replace" event gets delivered whenever a model replaces items in a
 * "bound" or "constrained" list. A <code>ListReplaceEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.utility.model.listener.ListChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class ListReplaceEvent extends ListEvent {

	/** The index at which the items were replaced. */
	private final int index;

	/** The new items that replaced the old items in the list. */
	private final Object[] newItems;

	/** The old items that were replaced by the new items in the list. */
	private final Object[] oldItems;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new list replace event for a list of replaced items.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the item in the list was replaced.
	 * @param newItem The new item in the list.
	 * @param oldItem The old item in the list that were replaced.
	 */
	public ListReplaceEvent(Model source, String listName, int index, Object newItem, Object oldItem) {
		this(source, listName, index, new Object[] {newItem}, new Object[] {oldItem});
	}

	/**
	 * Construct a new list replace event for a list of replaced items.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the items in the list were replaced.
	 * @param newItems The new items in the list.
	 * @param oldItems The old items in the list that were replaced.
	 */
	public ListReplaceEvent(Model source, String listName, int index, List<?> newItems, List<?> oldItems) {
		this(source, listName, index, newItems.toArray(), oldItems.toArray());  // NPE if either 'newItems' or 'oldItems' is null
	}

	private ListReplaceEvent(Model source, String listName, int index, Object[] newItems, Object[] oldItems) {
		super(source, listName);
		if (newItems.length != oldItems.length) {
			throw new IllegalArgumentException("sizes must match - new items size: " + newItems.length //$NON-NLS-1$
					+ " old items size: " + oldItems.length); //$NON-NLS-1$
		}
		this.index = index;
		this.newItems = newItems;
		this.oldItems = oldItems;
	}


	// ********** standard state **********

	/**
	 * Return the index at which the items were replaced in the list.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Return the new items that replaced the old items in the list.
	 */
	public Iterable<?> getNewItems() {
		return new ArrayIterable<Object>(this.newItems);
	}

	/**
	 * Return the old items that were replaced by the new items in the list.
	 */
	public Iterable<?> getOldItems() {
		return new ArrayIterable<Object>(this.oldItems);
	}

	/**
	 * Return the number of items that were replaced.
	 */
	public int getItemsSize() {
		return this.newItems.length;
	}

	@Override
	protected void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(": "); //$NON-NLS-1$
		StringTools.append(sb, this.oldItems);
		sb.append(" => "); //$NON-NLS-1$
		StringTools.append(sb, this.newItems);
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public ListReplaceEvent clone(Model newSource) {
		return this.clone(newSource, this.listName);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListReplaceEvent clone(Model newSource, String newListName) {
		return this.clone(newSource, newListName, 0);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name and displacing
	 * the index by the specified amount.
	 */
	public ListReplaceEvent clone(Model newSource, String newListName, int offset) {
		return new ListReplaceEvent(newSource, newListName, this.index + offset, this.newItems, this.oldItems);
	}

}
