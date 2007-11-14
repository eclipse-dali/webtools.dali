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

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * Implementation of CollectionValueModel and Collection that simply holds a
 * collection and notifies listeners of any changes.
 */
public class SimpleCollectionValueModel<E>
	extends AbstractModel
	implements CollectionValueModel, Collection<E>
{
	/** The value. */
	protected Collection<E> collection;


	// ********** constructors **********

	/**
	 * Construct a CollectionValueModel for the specified collection.
	 */
	public SimpleCollectionValueModel(Collection<E> collection) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
	}

	/**
	 * Construct a CollectionValueModel with an initial
	 * value of an empty collection.
	 */
	public SimpleCollectionValueModel() {
		this(new HashBag<E>());
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return new LocalIterator<E>(this.collection.iterator());
	}

	private class LocalIterator<T> implements Iterator<T> {
		private final Iterator<T> iterator;
		private T next;
		LocalIterator(Iterator<T> iterator) {
			super();
			this.iterator = iterator;
		}
		public boolean hasNext() {
			return this.iterator.hasNext();
		}
		public T next() {
			return this.next = this.iterator.next();
		}
		@SuppressWarnings("synthetic-access")
		public void remove() {
			this.iterator.remove();
			SimpleCollectionValueModel.this.fireItemRemoved(VALUES, this.next);
		}
	}

	public int size() {
		return this.collection.size();
	}


	// ********** Collection implementation **********

	public boolean isEmpty() {
		return this.collection.isEmpty();
	}

	public boolean contains(Object o) {
		return this.collection.contains(o);
	}

	public Object[] toArray() {
		return this.collection.toArray();
	}

	public <T extends Object> T[] toArray(T[] a) {
		return this.collection.toArray(a);
	}

	public boolean add(E o) {
		return this.addItemToCollection(o, this.collection, VALUES);
	}

	public boolean remove(Object o) {
		return this.removeItemFromCollection(o, this.collection, VALUES);
	}

	public boolean containsAll(Collection<?> c) {
		return this.collection.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.addItemsToCollection(c, this.collection, VALUES);
	}

	public boolean removeAll(Collection<?> c) {
		return this.removeItemsFromCollection(c, this.collection, VALUES);
	}

	public boolean retainAll(Collection<?> c) {
		return this.retainItemsInCollection(c, this.collection, VALUES);
	}

	public void clear() {
		if (this.collection.isEmpty()) {
			return;
		}
		Collection<E> items = new ArrayList<E>(this.collection);
		this.collection.clear();
		this.fireItemsRemoved(VALUES, items);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ((o instanceof Collection) && (o instanceof CollectionValueModel)) {
			Collection<E> c1 = CollectionTools.collection(this.collection);
			@SuppressWarnings("unchecked")
			Collection<E> c2 = CollectionTools.collection(((Collection<E>) o).iterator());
			return c1.equals(c2);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return CollectionTools.collection(this.collection).hashCode();
	}


	// ********** additional behavior **********

	/**
	 * Allow the value to be replaced.
	 */
	public void setCollection(Collection<E> collection) {
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
		this.fireCollectionChanged(VALUES);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collection);
	}

}
