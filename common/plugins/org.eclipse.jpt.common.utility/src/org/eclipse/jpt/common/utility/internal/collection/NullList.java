/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyListIterator;

/**
 * A "null" list is a bit different from an "empty" list: it allows clients to
 * add/remove elements to/from it but never changes. This is useful
 * for passing to methods that require a "collecting parameter" but the
 * client will ignore the resulting "collection".
 * @param <E> the type of elements maintained by the list
 */
public final class NullList<E>
	implements List<E>, Serializable
{
	// singleton
	@SuppressWarnings("rawtypes")
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
		if (index == 0) {
			return EmptyListIterator.instance();
		}
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
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
		if ((fromIndex == 0) && (toIndex == 0)) {
			return this;
		}
		throw new IndexOutOfBoundsException("Index: " + fromIndex + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public Object[] toArray() {
		return ObjectTools.EMPTY_OBJECT_ARRAY;
	}

	public <T> T[] toArray(T[] a) {
		return a;
	}

	@Override
	public String toString() {
		return "[]"; //$NON-NLS-1$
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
