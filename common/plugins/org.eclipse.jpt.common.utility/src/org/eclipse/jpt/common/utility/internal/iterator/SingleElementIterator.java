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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>SingleElementIterator</code> holds a single element
 * and returns it with the first call to {@link #next()}, at
 * which point it will return <code>false</code> to any subsequent
 * call to {@link #hasNext()}.
 * <p>
 * A <code>SingleElementIterator</code> is equivalent to the
 * {@link Iterator} returned by:
 * 	{@link java.util.Collections#singleton(Object element)}<code>.iterator()</code>
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable
 */
public class SingleElementIterator<E>
	implements Iterator<E>
{
	private final E element;
	private boolean done;


	/**
	 * Construct an iterator that returns only the specified element.
	 */
	public SingleElementIterator(E element) {
		super();
		this.element = element;
		this.done = false;
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		this.done = true;
		return this.element;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.element);
	}
}
