/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.io.Serializable;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An <code>EmptyListIterator</code> is just that.
 * 
 * @param <E> the type of elements (not) returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable
 */
public final class EmptyListIterator<E>
	implements ListIterator<E>, Serializable
{
	// singleton
	@SuppressWarnings("rawtypes")
	private static final EmptyListIterator INSTANCE = new EmptyListIterator();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ListIterator<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyListIterator() {
		super();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return false;
	}

	public boolean hasPrevious() {
		return false;
	}

	public E next() {
		throw new NoSuchElementException();
	}

	public int nextIndex() {
		return 0;
	}

	public E previous() {
		throw new NoSuchElementException();
	}

	public int previousIndex() {
		return -1;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public void set(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
