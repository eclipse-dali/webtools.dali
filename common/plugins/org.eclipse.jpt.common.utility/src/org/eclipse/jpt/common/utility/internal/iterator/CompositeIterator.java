/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
 * A <code>CompositeIterator</code> wraps a collection
 * of {@link Iterator}s and makes them appear to be a single
 * {@link Iterator}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable
 */
public class CompositeIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends Iterator<? extends E>> iterators;
	private Iterator<? extends E> currentIterator;
	private Iterator<? extends E> lastIteratorToReturnNext;


	// ********** constructors **********

	/**
	 * Construct an iterator with the specified collection of iterators.
	 */
	public CompositeIterator(Iterator<? extends Iterator<? extends E>> iterators) {
		super();
		if (iterators == null) {
			throw new NullPointerException();
		}
		this.iterators = iterators;
	}


	// ********** Iterator implementation **********

	public boolean hasNext() {
		try {
			this.loadCurrentIterator();
		} catch (NoSuchElementException ex) {
			// this occurs if there are no iterators at all
			return false;
		}
		return this.currentIterator.hasNext();
	}

	public E next() {
		this.loadCurrentIterator();
		E result = this.currentIterator.next();

		// the statement above will throw a NoSuchElementException
		// if the current iterator is at the end of the line;
		// so if we get here, we can set 'lastIteratorToReturnNext'
		this.lastIteratorToReturnNext = this.currentIterator;

		return result;
	}

	public void remove() {
		if (this.lastIteratorToReturnNext == null) {
			// CompositeIterator#next() has never been called
			throw new IllegalStateException();
		}
		this.lastIteratorToReturnNext.remove();
	}

	/**
	 * Load {@link #currentIterator} with the first iterator that {@link Iterator#hasNext()}
	 * or the final iterator if all the elements have already been retrieved.
	 */
	private void loadCurrentIterator() {
		if (this.currentIterator == null) {
			this.currentIterator = this.iterators.next();
		}
		while (( ! this.currentIterator.hasNext()) && this.iterators.hasNext()) {
			this.currentIterator = this.iterators.next();
		}
	}


	// ********** overrides **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterators);
	}
}
