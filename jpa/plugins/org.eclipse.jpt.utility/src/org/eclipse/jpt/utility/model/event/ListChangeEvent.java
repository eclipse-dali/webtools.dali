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

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.model.Model;

/**
 * A "list change" event gets delivered whenever a model changes a "bound"
 * or "constrained" list. A ListChangeEvent is sent as an
 * argument to the ListChangeListener.
 * 
 * Normally a ListChangeEvent is accompanied by the list name,
 * the items that were added to or removed from the changed list,
 * and the index of where the items are or were in the list.
 * 
 * Design options:
 * - create a list to wrap a single added or removed item
 * 	(this is the option we implemented below and in collaborating code)
 * 	since there is no way to optimize downstream code for
 * 	single items, we take another performance hit by building
 * 	a list each time  (@see Collections#singletonList(Object))
 * 	and forcing downstream code to use a list iterator every time
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
public class ListChangeEvent extends ChangeEvent {

	/**
	 * Name of the list that changed.
	 */
	private final String listName;

	/**
	 * The index at which the items were added, removed, or replaced.
	 * In the case of "moved" items, this will be the "target" index.
	 * May be -1, if not known.
	 */
	private final int index;

	/**
	 * The items that were added to or removed from the list. In the case of
	 * "replaced" items, these are the new items in the list.
	 * In the case of "moved" items, this will be empty.
	 * May be empty, if not known.
	 */
	private final List<?> items;

	/**
	 * The items in the list that were replaced by the items listed above,
	 * in #items. May be empty, if not known.
	 */
	private final List<?> replacedItems;

	/**
	 * In the case of "moved" items, this will be the "source" index.
	 * May be -1, if not known.
	 */
	private final int sourceIndex;

	/**
	 * In the case of "moved" items, this will be the number of items moved.
	 * May be -1, if not known.
	 */
	private final int moveLength;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	protected ListChangeEvent(Model source, String listName, int index, List<?> items, List<?> replacedItems, int sourceIndex, int moveLength) {
		super(source);
		if ((listName == null) || (items == null) || (replacedItems == null)) {
			throw new NullPointerException();
		}
		this.listName = listName;
		this.index = index;
		this.items = Collections.unmodifiableList(items);
		this.replacedItems = Collections.unmodifiableList(replacedItems);
		this.sourceIndex = sourceIndex;
		this.moveLength = moveLength;
	}

	/**
	 * Construct a new list change event for a list of replaced items.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the items in the list were replaced.
	 * @param items The new items in the list.
	 * @param replacedItems The items in the list that were replaced.
	 */
	public ListChangeEvent(Model source, String listName, int index, List<?> items, List<?> replacedItems) {
		this(source, listName, index, items, replacedItems, -1, -1);
	}

	/**
	 * Construct a new list change event for a list of added or removed items.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param index The index at which the items were added to or removed from the list.
	 * @param items The items that were added to or removed from the list.
	 */
	public ListChangeEvent(Model source, String listName, int index, List<?> items) {
		this(source, listName, index, items, Collections.emptyList(), -1, -1);
	}

	/**
	 * Construct a new list change event for a list of moved items.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 * @param targetIndex The index to which the items were moved.
	 * @param sourceIndex The index from which the items were moved.
	 */
	public ListChangeEvent(Model source, String listName, int targetIndex, int sourceIndex, int length) {
		this(source, listName, targetIndex, Collections.emptyList(), Collections.emptyList(), sourceIndex, length);
	}

	/**
	 * Construct a new list change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param listName The programmatic name of the list that was changed.
	 */
	public ListChangeEvent(Model source, String listName) {
		this(source, listName, -1, Collections.emptyList(), Collections.emptyList(), -1, -1);
	}


	// ********** standard state **********

	/**
	 * Return the programmatic name of the list that was changed.
	 */
	public String getListName() {
		return this.listName;
	}

	@Override
	public String getAspectName() {
		return this.listName;
	}

	/**
	 * Return the index at which the items were added to, removed from,
	 * or replaced in the list.
	 * In the case of "moved" items, this will be the "target" index.
	 * May be -1 if inappropriate or unknown.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Return a list iterator on the items that were added to or
	 * removed from the list. In the case of "replaced" items, these
	 * are the new items in the list.
	 * May be empty if inappropriate or unknown.
	 */
	public ListIterator<?> items() {
		return this.items.listIterator();
	}

	/**
	 * Return the number of items that were added to,
	 * removed from, or replaced in the list.
	 * May be 0 if inappropriate or unknown.
	 */
	public int itemsSize() {
		return this.items.size();
	}


	// ********** replace **********

	/**
	 * Return a list iterator on the items in the list that were replaced.
	 * May be empty if inappropriate or unknown.
	 */
	public ListIterator<?> replacedItems() {
		return this.replacedItems.listIterator();
	}


	// ********** move **********

	/**
	 * In the case of "moved" items, this will be the "target" index.
	 * May be -1 if inappropriate or unknown.
	 */
	public int getTargetIndex() {
		return this.index;
	}

	/**
	 * In the case of "moved" items, this will be the "source" index.
	 * May be -1 if inappropriate or unknown.
	 */
	public int getSourceIndex() {
		return this.sourceIndex;
	}

	/**
	 * In the case of "moved" items, this will be the number of items moved.
	 * May be -1 if inappropriate or unknown.
	 */
	public int getMoveLength() {
		return this.moveLength;
	}


	// ********** cloning **********

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	@Override
	public ListChangeEvent cloneWithSource(Model newSource) {
		return new ListChangeEvent(newSource, this.listName, this.index, this.items, this.replacedItems, this.sourceIndex, this.moveLength);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name.
	 */
	public ListChangeEvent cloneWithSource(Model newSource, String newListName) {
		return new ListChangeEvent(newSource, newListName, this.index, this.items, this.replacedItems, this.sourceIndex, this.moveLength);
	}

	/**
	 * Return a copy of the event with the specified source and list name
	 * replacing the current source and list name and displacing
	 * the index by the specified amount.
	 */
	public ListChangeEvent cloneWithSource(Model newSource, String newListName, int offset) {
		return new ListChangeEvent(newSource, newListName, this.index + offset, this.items, this.replacedItems, this.sourceIndex + offset, this.moveLength);
	}

}
