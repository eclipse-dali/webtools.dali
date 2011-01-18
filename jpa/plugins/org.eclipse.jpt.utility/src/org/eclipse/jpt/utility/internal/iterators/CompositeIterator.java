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
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

/**
 * A <code>CompositeIterator</code> wraps a collection
 * of {@link Iterator}s and makes them appear to be a single
 * {@link Iterator}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.CompositeIterable
 */
public class CompositeIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends Iterator<? extends E>> iterators;
	private Iterator<? extends E> currentIterator;
	private Iterator<? extends E> lastIteratorToReturnNext;


	// ********** constructors **********

	/**
	 * Construct an iterator that returns all the elements held by the
	 * specified iterables.
	 */
	public CompositeIterator(Iterable<? extends Iterable<? extends E>> iterables) {
		this(
			new TransformationIterator<Iterable<? extends E>, Iterator<? extends E>>(iterables.iterator()) {
				@Override
				protected Iterator<? extends E> transform(Iterable<? extends E> iterable) {
					return iterable.iterator();
				}
			}
		);
	}

	/**
	 * Construct an iterator with the specified collection of iterators.
	 */
	public CompositeIterator(Iterator<? extends Iterator<? extends E>> iterators) {
		super();
		this.iterators = iterators;
	}

	/**
	 * Construct an iterator with the specified object prepended
	 * to the specified iterable.
	 */
	public CompositeIterator(E object, Iterable<? extends E> iterable) {
		this(object, iterable.iterator());
	}

	/**
	 * Construct an iterator with the specified object prepended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public CompositeIterator(E object, Iterator<? extends E> iterator) {
		this(new SingleElementIterator<E>(object), iterator);
	}

	/**
	 * Construct an iterator with the specified object appended
	 * to the specified iterable.
	 */
	public CompositeIterator(Iterable<? extends E> iterable, E object) {
		this(iterable.iterator(), object);
	}

	/**
	 * Construct an iterator with the specified object appended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public CompositeIterator(Iterator<? extends E> iterator, E object) {
		this(iterator, new SingleElementIterator<E>(object));
	}

	/**
	 * Construct an iterator with the specified iterables.
	 */
	public CompositeIterator(Iterable<? extends E>... iterables) {
		this(new ArrayIterable<Iterable<? extends E>>(iterables));
	}

	/**
	 * Construct an iterator with the specified iterators.
	 */
	public CompositeIterator(Iterator<? extends E>... iterators) {
		this(new ArrayIterator<Iterator<? extends E>>(iterators));
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
		return StringTools.buildToStringFor(this, this.iterators);
	}

}
