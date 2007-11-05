/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * Implementation of ListValueModel that simply holds on to a
 * list and uses it as the value.
 */
public class SimpleListValueModel
	extends AbstractModel
	implements ListValueModel
{
	/** The value. */
	protected List value;


	/**
	 * Construct a ListValueModel for the specified value.
	 */
	public SimpleListValueModel(List value) {
		super();
		this.setValue(value);
	}

	/**
	 * Construct a ListValueModel with an initial value
	 * of an empty list
	 */
	public SimpleListValueModel() {
		this(new ArrayList());
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new ValueModelChangeSupport(this);
	}


	// ********** ValueModel implementation **********

	public Object value() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyListIterator(this.value);
	}

	// ********** ListValueModel implementation **********

	public void addItem(int index, Object item) {
		this.addItemToList(index, item, this.value, VALUE);
	}

	public void addItems(int index, List items) {
		this.addItemsToList(index, items, this.value, VALUE);
	}

	public Object removeItem(int index) {
		return this.removeItemFromList(index, this.value, VALUE);
	}

	public List removeItems(int index, int length) {
		return this.removeItemsFromList(index, length, this.value, VALUE);
	}

	public Object replaceItem(int index, Object item) {
		return this.setItemInList(index, item, this.value, VALUE);
	}

	public List replaceItems(int index, List items) {
		return this.setItemsInList(index, items, this.value, VALUE);
	}

	public Object getItem(int index) {
		return this.value.get(index);
	}

	public int size() {
		return this.value.size();
	}


	// ********** behavior **********

	/**
	 * Allow the value to be replaced.
	 */
	public void setValue(List value) {
		this.value = ((value == null) ? new ArrayList() : value);
		this.fireListChanged(VALUE);
	}

	/**
	 * Add the specified item to the end of the list.
	 */
	public void addItem(Object item) {
		this.addItem(this.size(), item);
	}

	/**
	 * Return the index of the first occurrence of the specified item.
	 */
	public int indexOfItem(Object item) {
		return this.value.indexOf(item);
	}

	/**
	 * Remove the first occurrence of the specified item.
	 */
	public void removeItem(Object item) {
		this.removeItem(this.indexOfItem(item));
	}

	/**
	 * Allow the value to be cleared.
	 */
	public void clear() {
		if (this.value.isEmpty()) {
			return;
		}
		List items = new ArrayList(this.value);
		this.value.clear();
		this.fireItemsRemoved(VALUE, 0, items);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
