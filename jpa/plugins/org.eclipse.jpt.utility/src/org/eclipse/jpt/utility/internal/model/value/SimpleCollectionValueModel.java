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
import java.util.Collection;

import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * Implementation of CollectionValueModel that simply holds on to a
 * collection and uses it as the value.
 */
public class SimpleCollectionValueModel
	extends AbstractModel
	implements CollectionValueModel
{
	/** The value. */
	protected Collection value;


	/**
	 * Construct a CollectionValueModel for the specified value.
	 */
	public SimpleCollectionValueModel(Collection value) {
		super();
		this.setValue(value);
	}

	/**
	 * Construct a CollectionValueModel with an initial
	 * value of an empty collection.
	 */
	public SimpleCollectionValueModel() {
		this(new HashBag());
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new ValueModelChangeSupport(this);
	}


	// ********** ValueModel implementation **********

	public Object getValue() {
		// try to prevent backdoor modification of the collection
		return new ReadOnlyIterator(this.value);
	}


	// ********** CollectionValueModel implementation **********

	public void addItem(Object item) {
		this.addItemToCollection(item, this.value, VALUE);
	}

	public void addItems(Collection items) {
		this.addItemsToCollection(items, this.value, VALUE);
	}

	public void removeItem(Object item) {
		this.removeItemFromCollection(item, this.value, VALUE);
	}

	public void removeItems(Collection items) {
		this.removeItemsFromCollection(items, this.value, VALUE);
	}

	public int size() {
		return this.value.size();
	}


	// ********** behavior **********

	/**
	 * Allow the value to be replaced.
	 */
	public void setValue(Collection value) {
		this.value = ((value == null) ? new HashBag() : value);
		this.fireCollectionChanged(VALUE);
	}

	/**
	 * Allow the value to be cleared.
	 */
	public void clear() {
		if (this.value.isEmpty()) {
			return;
		}
		Collection items = new ArrayList(this.value);
		this.value.clear();
		this.fireItemsRemoved(VALUE, items);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
