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
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An <code>EmptyIterator</code> is just that.
 * 
 * @param <E> the type of elements (not) returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable
 */
public final class EmptyIterator<E>
	implements Iterator<E>, Serializable
{
	// singleton
	@SuppressWarnings("rawtypes")
	private static final EmptyIterator INSTANCE = new EmptyIterator();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyIterator() {
		super();
	}

	public boolean hasNext() {
		return false;
	}

	public E next() {
		throw new NoSuchElementException();
	}

	public void remove() {
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
