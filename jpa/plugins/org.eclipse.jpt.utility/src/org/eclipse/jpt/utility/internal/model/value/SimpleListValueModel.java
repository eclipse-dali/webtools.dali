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
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

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
		return new SingleAspectChangeSupport(this, ListChangeListener.class, LIST_VALUES);
	}


	// ********** ListValueModel implementation **********

	public ListIterator values() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyListIterator(this.value);
	}

	public void add(int index, Object item) {
		this.addItemToList(index, item, this.value, LIST_VALUES);
	}

	public void addAll(int index, List items) {
		this.addItemsToList(index, items, this.value, LIST_VALUES);
	}

	public Object remove(int index) {
		return this.removeItemFromList(index, this.value, LIST_VALUES);
	}

	public List remove(int index, int length) {
		return this.removeItemsFromList(index, length, this.value, LIST_VALUES);
	}

	public Object replace(int index, Object item) {
		return this.setItemInList(index, item, this.value, LIST_VALUES);
	}

	public List replaceAll(int index, List items) {
		return this.setItemsInList(index, items, this.value, LIST_VALUES);
	}

	public Object get(int index) {
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
		this.fireListChanged(LIST_VALUES);
	}

	/**
	 * Add the specified item to the end of the list.
	 */
	public void addItem(Object item) {
		this.add(this.size(), item);
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
		this.remove(this.indexOfItem(item));
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
		this.fireItemsRemoved(LIST_VALUES, 0, items);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
