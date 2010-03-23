/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>SingleElementListIterator</code> holds a single element
 * and returns it with the first call to {@link next()}, at
 * which point it will return <code>false</code> to any subsequent
 * call to {@link #hasNext()}. Likewise, it will return <code>false</code>
 * to a call to {@link #hasPrevious()} until a call to {@link #next()},
 * at which point a call to {@link #previous()} will return the
 * single element.
 * <p>
 * A <code>SingleElementListIterator</code> is equivalent to the
 * {@link Iterator} returned by:
 * 	{@link java.util.Collections#singletonList(element)}<code>.listIterator()</code>
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.SingleElementListIterable
 */
public class SingleElementListIterator<E>
	implements ListIterator<E>
{
	private final E element;
	private boolean hasNext;


	/**
	 * Construct a list iterator that returns only the specified element.
	 */
	public SingleElementListIterator(E element) {
		super();
		this.element = element;
		this.hasNext = true;
	}

	public boolean hasNext() {
		return this.hasNext;
	}

	public E next() {
		if (this.hasNext) {
			this.hasNext = false;
			return this.element;
		}
		throw new NoSuchElementException();
	}

	public int nextIndex() {
		return this.hasNext ? 0 : 1;
	}

	public boolean hasPrevious() {
		return ! this.hasNext;
	}

	public E previous() {
		if (this.hasNext) {
			throw new NoSuchElementException();
		}
		this.hasNext = true;
		return this.element;
	}

	public int previousIndex() {
		return this.hasNext ? -1 : 0;
	}

	public void add(E e) {
		throw new UnsupportedOperationException();
	}

	public void set(E e) {
		throw new UnsupportedOperationException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.element);
	}

}
