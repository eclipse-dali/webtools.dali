/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * A "null" list is a bit different from an "empty" list: it allows clients to
 * add/remove elements to/from it but never changes. This is useful
 * for passing to methods that require a "collecting parameter" but the
 * client will ignore the resulting "collection".
 * 
 * NB: We return 'null' from the following methods (as opposed to throwing
 * an exception):
 *     get(int) : E
 *     remove(int) : E
 *     set(int, E) : E
 */
public final class NullList<E> implements List<E> {

	// singleton
	@SuppressWarnings("unchecked")
	private static final NullList INSTANCE = new NullList();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullList() {
		super();
	}

	public boolean add(E o) {
		return false;  // the list did not change
	}

	public void add(int index, E element) {
		// ignore
	}

	public boolean addAll(Collection<? extends E> c) {
		return false;  // the list did not change
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return false;  // the list did not change
	}

	public void clear() {
		// ignore
	}

	public boolean contains(Object o) {
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		return c.isEmpty();
	}

	public E get(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int indexOf(Object o) {
		return -1;
	}

	public boolean isEmpty() {
		return true;
	}

	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	public int lastIndexOf(Object o) {
		return -1;
	}

	public ListIterator<E> listIterator() {
		return EmptyListIterator.instance();
	}

	public ListIterator<E> listIterator(int index) {
		return EmptyListIterator.instance();
	}

	public boolean remove(Object o) {
		return false;  // the list did not change
	}

	public E remove(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean removeAll(Collection<?> c) {
		return false;  // the list did not change
	}

	public boolean retainAll(Collection<?> c) {
		return false;  // the list did not change
	}

	public E set(int index, E element) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int size() {
		return 0;
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return this;
	}

	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public Object[] toArray() {
		return EMPTY_OBJECT_ARRAY;
	}

	public <T> T[] toArray(T[] a) {
		return a;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
