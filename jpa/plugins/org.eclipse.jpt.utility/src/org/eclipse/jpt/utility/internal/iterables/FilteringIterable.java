/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.Iterator;

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * A <code>FilteringIterable</code> wraps another {@link Iterable}
 * and uses a {@link Filter} to determine which elements in the
 * nested iterable are to be returned by the iterable's iterator.
 * <p>
 * As an alternative to building a {@link Filter}, a subclass
 * of <code>FilteringIterable</code> can override the
 * {@link #accept(Object)} method.
 * 
 * @param <E> the type of elements to be filtered
 * 
 * @see FilteringIterator
 */
public class FilteringIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> iterable;
	private final Filter<E> filter;


	/**
	 * Construct an iterable with the specified nested
	 * iterable and a default filter that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #accept(Object)} method instead of building
	 * a {@link Filter}.
	 */
	public FilteringIterable(Iterable<? extends E> iterable) {
		super();
		this.iterable = iterable;
		this.filter = this.buildDefaultFilter();
	}

	/**
	 * Construct an iterable with the specified nested
	 * iterable and filter.
	 */
	public FilteringIterable(Iterable<? extends E> iterable, Filter<E> filter) {
		super();
		this.iterable = iterable;
		this.filter = filter;
	}

	protected Filter<E> buildDefaultFilter() {
		return new DefaultFilter();
	}

	public Iterator<E> iterator() {
		return new FilteringIterator<E>(this.iterable.iterator(), this.filter);
	}

	/**
	 * Return whether the iterable's iterator
	 * should return the specified next element from a call to the
	 * {@link Iterator#next()} method.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link Filter}.
	 */
	protected boolean accept(@SuppressWarnings("unused") E o) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}


	//********** default filter **********

	protected class DefaultFilter implements Filter<E> {
		public boolean accept(E o) {
			return FilteringIterable.this.accept(o);
		}
	}

}
