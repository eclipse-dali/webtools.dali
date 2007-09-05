/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>FilteringIterator</code> wraps another <code>Iterator</code>
 * and uses a <code>Filter</code> to determine which elements in the
 * nested iterator are to be returned by calls to <code>next()</code>.
 * <p>
 * As an alternative to building a <code>Filter</code>, a subclass
 * of <code>FilteringIterator</code> can override the
 * <code>accept(Object)</code> method.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that
 * the nested iterator's <code>next()</code> method will be invoked
 * <em>before</em> the filtered iterator's <code>next()</code>
 * method is invoked. This is because the "next" element must be
 * checked for whether it is to be accepted before the filtered iterator
 * can determine whether it has a "next" element (i.e. that the
 * <code>hasNext()</code> method should return <code>true</code>).
 * This also prevents a filtered iterator from supporting the optional
 * <code>remove()</code> method.
 */
public class FilteringIterator<E>
	implements Iterator<E>
{
	private final Iterator<?> nestedIterator;
	// trust that the filter is correct - i.e. it will only accept elements of type E
	@SuppressWarnings("unchecked")
	private final Filter filter;
	private E next;
	private boolean done;


	/**
	 * Construct an iterator with the specified nested
	 * iterator and a filter that simply accepts every object.
	 * Use this constructor if you want to override the
	 * <code>accept(Object)</code> method instead of building
	 * a <code>Filter</code>.
	 */
	public FilteringIterator(Iterator<?> nestedIterator) {
		this(nestedIterator, Filter.Null.instance());
	}

	/**
	 * Construct an iterator with the specified nested
	 * iterator and filter.
	 */
	public FilteringIterator(Iterator<?> nestedIterator, @SuppressWarnings("unchecked") Filter filter) {
		super();
		this.nestedIterator = nestedIterator;
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
		while (this.nestedIterator.hasNext() && (this.done)) {
			Object o = this.nestedIterator.next();
			if (this.accept(o)) {
				// assume that if the object was accepted it is of type E
				this.next = this.downcast(o);
				this.done = false;
			} else {
				this.next = null;
				this.done = true;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private E downcast(Object o) {
		return (E) o;
	}

	/**
	 * Return whether the <code>FilteringIterator</code>
	 * should return the specified next element from a call to the
	 * <code>next()</code> method.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Filter</code>.
	 */
	@SuppressWarnings("unchecked")
	protected boolean accept(Object o) {
		return this.filter.accept(o);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nestedIterator);
	}

}
