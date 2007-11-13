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
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

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
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}


	// ********** CollectionValueModel implementation **********

	public Iterator values() {
		// try to prevent backdoor modification of the collection
		return new ReadOnlyIterator(this.value);
	}

	public void add(Object item) {
		this.addItemToCollection(item, this.value, VALUES);
	}

	public void addAll(Collection items) {
		this.addItemsToCollection(items, this.value, VALUES);
	}

	public void remove(Object item) {
		this.removeItemFromCollection(item, this.value, VALUES);
	}

	public void removeAll(Collection items) {
		this.removeItemsFromCollection(items, this.value, VALUES);
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
		this.fireCollectionChanged(VALUES);
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
		this.fireItemsRemoved(VALUES, items);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
