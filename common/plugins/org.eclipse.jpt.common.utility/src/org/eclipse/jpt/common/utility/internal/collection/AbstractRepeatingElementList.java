/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <em>read-only</em> repeating element list (i.e. the list contains only
 * one or more references to the same element).
 * 
 * @param <E> the type of elements maintained by the list
 */
public abstract class AbstractRepeatingElementList<E>
	implements List<E>, Serializable
{
	// never negative
	private final int size;

	/**
	 * Construct a <em>read-only</em> list with the specified number of
	 * objects in it.
	 */
	protected AbstractRepeatingElementList(int size) {
		super();
		if (size < 0) {
			throw new IllegalArgumentException("Invalid size: " + size); //$NON-NLS-1$
		}
		this.size = size;
	}

	public boolean add(E o) {
		throw new UnsupportedOperationException();
	}

	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#addAll(Collection)}.
	 */
	public boolean addAll(Collection<? extends E> c) {
		if (c.isEmpty()) {
			return false;
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractList#addAll(int, Collection)}.
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		return this.addAll(c);  // ignore the index
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#clear()}.
	 */
	public void clear() {
		if (this.size != 0) {
			throw new UnsupportedOperationException();
		}
	}

	public boolean contains(Object o) {
		return (this.elementIs(o) && (this.size > 0));
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#containsAll(Collection)}.
	 */
	public boolean containsAll(Collection<?> c) {
		if (c.isEmpty()) {
			return true;
		}
		if (this.size == 0) {
			return false;
		}
		for (Iterator<?> iterator = c.iterator(); iterator.hasNext(); ) {
			if (this.elementIsNot(iterator.next())) {
				return false;
			}
		}
		return true;
	}

	public E get(int index) {
		this.checkIndex(index);
		return this.getElement();
	}

	private void checkIndex(int index) {
		if ((index < 0) || (index >= this.size)) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public int indexOf(Object o) {
		return (this.elementIs(o) && (this.size > 0)) ? 0 : -1;
	}

	public boolean isEmpty() {
		return (this.size == 0);
	}

	public Iterator<E> iterator() {
		return this.iterator(this.size);
	}

	protected abstract Iterator<E> iterator(int iteratorSize);

	public int lastIndexOf(Object o) {
		return (this.elementIs(o) && (this.size > 0)) ? (this.size - 1) : -1;
	}

	public ListIterator<E> listIterator() {
		return this.listIterator_(this.size);
	}

	public ListIterator<E> listIterator(int index) {
		return this.listIterator_(this.size - index);
	}

	protected abstract ListIterator<E> listIterator_(int iteratorSize);

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#remove(Object)}.
	 */
	public boolean remove(Object o) {
		if ((this.size > 0) && this.elementIs(o)) {
			throw new UnsupportedOperationException();
		}
		return false;
	}

	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#removeAll(Collection)}.
	 */
	public boolean removeAll(Collection<?> c) {
		if ((this.size > 0) && c.contains(this.getElement())) {
			throw new UnsupportedOperationException();
		}
		return false;
	}

	/**
	 * Replicate behavior of
	 * {@link java.util.AbstractCollection#retainAll(Collection)}.
	 */
	public boolean retainAll(Collection<?> c) {
		if ((this.size > 0) && ! c.contains(this.getElement())) {
			throw new UnsupportedOperationException();
		}
		return false;
	}

	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return this.size;
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return this.subList(toIndex - fromIndex);
	}

	protected abstract List<E> subList(int subListSize);

	public Object[] toArray() {
		return ArrayTools.fill(new Object[this.size], this.getElement());
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		int sz = this.size;
		return (T[]) ArrayTools.fill(((a.length >= sz) ? a : ArrayTools.newInstance(a, sz)), 0, sz, this.getElement());
	}

	private boolean elementIsNot(Object o) {
		return ! this.elementIs(o);
	}

	private boolean elementIs(Object o) {
		return ObjectTools.equals(o, this.getElement());
	}

	/**
	 * Return the list's repeating element.
	 */
	protected abstract E getElement();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = this.size; i-- > 0; ) {
			sb.append(this.getElement());
			sb.append(", "); //$NON-NLS-1$
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - 2);
		}
		sb.append(']');
		return sb.toString();
	}

	private static final long serialVersionUID = 1L;
}
