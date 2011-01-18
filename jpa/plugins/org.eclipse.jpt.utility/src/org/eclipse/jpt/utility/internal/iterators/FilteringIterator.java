/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>FilteringIterator</code> wraps another {@link Iterator}
 * and uses a {@link Filter} to determine which elements in the
 * nested iterator are to be returned by calls to {@link #next()}.
 * <p>
 * As an alternative to building a {@link Filter}, a subclass
 * of <code>FilteringIterator</code> can override the
 * {@link #accept(Object)} method.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that
 * the nested iterator's <code>next()</code> method will be invoked
 * <em>before</em> the filtered iterator's {@link #next()}
 * method is invoked. This is because the "next" element must be
 * checked for whether it is to be accepted before the filtered iterator
 * can determine whether it has a "next" element (i.e. that the
 * {@link #hasNext()} method should return <code>true</code>).
 * This also prevents a filtered iterator from supporting the optional
 * <code>remove()</code> method.
 * 
 * @param <E> the type of elements to be filtered
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.FilteringIterable
 */
public class FilteringIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;
	private final Filter<E> filter;
	private E next;
	private boolean done;


	/**
	 * Construct an iterator with the specified
	 * iterable and a disabled filter.
	 * Use this constructor if you want to override the
	 * {@link #accept(Object)} method instead of building
	 * a {@link Filter}.
	 */
	public FilteringIterator(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	/**
	 * Construct an iterator with the specified nested
	 * iterator and a disabled filter.
	 * Use this constructor if you want to override the
	 * {@link #accept(Object)} method instead of building
	 * a {@link Filter}.
	 */
	public FilteringIterator(Iterator<? extends E> iterator) {
		this(iterator, Filter.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified
	 * iterable and filter.
	 */
	public FilteringIterator(Iterable<? extends E> iterable, Filter<E> filter) {
		this(iterable.iterator(), filter);
	}

	/**
	 * Construct an iterator with the specified nested
	 * iterator and filter.
	 */
	public FilteringIterator(Iterator<? extends E> iterator, Filter<E> filter) {
		super();
		this.iterator = iterator;
		this.filter = filter;
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
	 * Because we need to pre-load the next element
	 * to be returned, we cannot support the <code>remove()</code>
	 * method.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load next with the next valid entry from the nested
	 * iterator. If there are none, next is set to <code>END</code>.
	 */
	private void loadNext() {
		this.done = true;
		while (this.iterator.hasNext() && (this.done)) {
			E temp = this.iterator.next();
			if (this.accept(temp)) {
				// assume that if the object was accepted it is of type E
				this.next = temp;
				this.done = false;
			} else {
				this.next = null;
				this.done = true;
			}
		}
	}

	/**
	 * Return whether the {@link FilteringIterator}
	 * should return the specified next element from a call to the
	 * {@link #next()} method.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link Filter}.
	 */
	protected boolean accept(E o) {
		return this.filter.accept(o);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
