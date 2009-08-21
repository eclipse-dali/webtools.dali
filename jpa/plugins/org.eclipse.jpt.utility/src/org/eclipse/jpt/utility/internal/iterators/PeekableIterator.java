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

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>PeekableIterator</code> wraps another {@link Iterator}
 * and allows a {@link peek()} at the next element to be 
 * returned by {@link #next()}.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that
 * the nested iterator's <code>next()</code> method will be invoked
 * <em>before</em> the peekable iterator's {@link #next()}
 * method is invoked. This is because the "next" element must be
 * pre-loaded for the {@link #peek()} method.
 * This also prevents a peekable iterator from supporting the optional
 * {@link #remove()} method.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class PeekableIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;
	private E next;
	private boolean done;


	/**
	 * Construct a peekable iterator that wraps the specified
	 * iterable.
	 */
	public PeekableIterator(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	/**
	 * Construct a peekable iterator that wraps the specified nested
	 * iterator.
	 */
	public PeekableIterator(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
		this.done = false;
		this.loadNext();
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		E result = this.next;
		this.loadNext();
		return result;
	}

	/**
	 * Return the element that will be returned by the next call to the
	 * {@link #next()} method, without advancing past it.
	 */
	public E peek() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		return this.next;
	}

	/**
	 * Because we need to pre-load the next element
	 * to be returned, we cannot support the {@link #remove()}
	 * method.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load next with the next entry from the nested
	 * iterator. If there are none, {@link #next} is set to <code>null</code>
	 * and {@link #done} is set to <code>true</code>.
	 */
	private void loadNext() {
		if (this.iterator.hasNext()) {
			this.next = this.iterator.next();
		} else {
			this.next = null;
			this.done = true;
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
