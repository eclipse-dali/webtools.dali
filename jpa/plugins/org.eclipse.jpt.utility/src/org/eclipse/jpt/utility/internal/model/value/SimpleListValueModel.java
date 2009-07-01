/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Implementation of ListValueModel and List that simply holds a
 * collection and notifies listeners of any changes.
 */
public class SimpleListValueModel<E>
	extends AbstractModel
	implements ListValueModel<E>, List<E>
{
	/** The list. */
	protected List<E> list;


	// ********** constructors **********

	/**
	 * Construct a ListValueModel for the specified list.
	 */
	public SimpleListValueModel(List<E> list) {
		super();
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
	}

	/**
	 * Construct a ListValueModel with an empty initial list.
	 */
	public SimpleListValueModel() {
		this(new ArrayList<E>());
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, ListChangeListener.class, LIST_VALUES);
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return new LocalIterator<E>(this.list.iterator());
	}

	public ListIterator<E> listIterator() {
		return new LocalListIterator<E>(this.list.listIterator());
	}

	public int size() {
		return this.list.size();
	}

	public E get(int index) {
		return this.list.get(index);
	}


	// ********** List implementation **********

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T extends Object> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}

	public boolean add(E o) {
		return this.addItemToList(o, this.list, LIST_VALUES);
	}

	public boolean remove(Object o) {
		return this.removeItemFromList(o, this.list, LIST_VALUES);
	}

	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.addItemsToList(c, this.list, LIST_VALUES);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return this.addItemsToList(index, c, this.list, LIST_VALUES);
	}

	public boolean removeAll(Collection<?> c) {
		return this.removeItemsFromList(c, this.list, LIST_VALUES);
	}

	public boolean retainAll(Collection<?> c) {
		return this.retainItemsInList(c, this.list, LIST_VALUES);
	}

	public void clear() {
		this.clearList(this.list, LIST_VALUES);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ((o instanceof List<?>) && (o instanceof ListValueModel<?>)) {
			List<E> l1 = CollectionTools.list(this.list);
			@SuppressWarnings("unchecked")
			List<E> l2 = CollectionTools.list(((List<E>) o).iterator());
			return l1.equals(l2);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

	public E set(int index, E element) {
		return this.setItemInList(index, element, this.list, LIST_VALUES);
	}

	public void add(int index, E element) {
		this.addItemToList(index, element, this.list, LIST_VALUES);
	}

	public E remove(int index) {
		return this.removeItemFromList(index, this.list, LIST_VALUES);
	}

	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator(int index) {
		return new LocalListIterator<E>(this.list.listIterator(index));
	}

	public List<E> subList(int fromIndex, int toIndex) {
		// TODO hmmm  ~bjv
		throw new UnsupportedOperationException();
	}


	// ********** additional behavior **********

	/**
	 * Allow the list to be replaced.
	 */
	public void setList(List<E> list) {
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
		this.fireListChanged(LIST_VALUES, this.list);
	}

	/**
	 * Move a single element.
	 */
	public void move(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.list, LIST_VALUES);
	}

	/**
	 * Move a sub-list of elements.
	 */
	public void move(int targetIndex, int sourceIndex, int length) {
		this.moveItemsInList(targetIndex, sourceIndex, length, this.list, LIST_VALUES);
	}

	/**
	 * Remove a range of elements.
	 */
	public void remove(int index, int length) {
		this.removeItemsFromList(index, length, this.list, LIST_VALUES);
	}

	/**
	 * Set a range of elements.
	 */
	public void set(int index, List<E> elements) {
		this.setItemsInList(index, elements, this.list, LIST_VALUES);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.list);
	}


	// ********** iterators **********

	private class LocalIterator<T> implements Iterator<T> {
		private final Iterator<T> iterator;
		private int index = -1;
		private T next;

		LocalIterator(Iterator<T> iterator) {
			super();
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public T next() {
			this.next = this.iterator.next();
			this.index++;
			return this.next;
		}

		@SuppressWarnings("synthetic-access")
		public void remove() {
			this.iterator.remove();
			SimpleListValueModel.this.fireItemRemoved(LIST_VALUES, this.index, this.next);
		}

	}

	private class LocalListIterator<T> implements ListIterator<T> {
		private final ListIterator<T> iterator;
		private int last = -1;
		private int next = 0;
		private T current;

		LocalListIterator(ListIterator<T> iterator) {
			super();
			this.iterator = iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public T next() {
			this.current = this.iterator.next();
			this.last = this.next++;
			return this.current;
		}

		public int nextIndex() {
			return this.iterator.nextIndex();
		}

		public boolean hasPrevious() {
			return this.iterator.hasPrevious();
		}

		public T previous() {
			this.current = this.iterator.previous();
			this.last = --this.next;
			return this.current;
		}

		public int previousIndex() {
			return this.iterator.previousIndex();
		}

		@SuppressWarnings("synthetic-access")
		public void set(T o) {
			this.iterator.set(o);
			SimpleListValueModel.this.fireItemReplaced(LIST_VALUES, this.last, o, this.current);
		}

		@SuppressWarnings("synthetic-access")
		public void add(T o) {
			this.iterator.add(o);
			SimpleListValueModel.this.fireItemAdded(LIST_VALUES, this.next, o);
		}

		@SuppressWarnings("synthetic-access")
		public void remove() {
			this.iterator.remove();
			SimpleListValueModel.this.fireItemRemoved(LIST_VALUES, this.last, this.current);
		}

	}

}
