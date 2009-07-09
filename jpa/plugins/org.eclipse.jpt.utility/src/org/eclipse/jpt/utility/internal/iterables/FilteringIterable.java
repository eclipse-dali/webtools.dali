/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
 * A <code>FilteringIterable</code> wraps another <code>Iterable</code>
 * and uses a <code>Filter</code> to determine which elements in the
 * nested iterable are to be returned by the iterable's iterator.
 * <p>
 * As an alternative to building a <code>Filter</code>, a subclass
 * of <code>FilteringIterable</code> can override the
 * <code>accept(Object)</code> method.
 * 
 * @see FilteringIterator
 */
public class FilteringIterable<E1, E2>
	implements Iterable<E2>
{
	private final Iterable<? extends E1> iterable;
	private final Filter<E1> filter;


	/**
	 * Construct an iterable with the specified nested
	 * iterable and a default filter that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * <code>accept(Object)</code> method instead of building
	 * a <code>Filter</code>.
	 */
	public FilteringIterable(Iterable<? extends E1> iterable) {
		super();
		this.iterable = iterable;
		this.filter = this.buildDefaultFilter();
	}

	/**
	 * Construct an iterable with the specified nested
	 * iterable and filter.
	 */
	public FilteringIterable(Iterable<? extends E1> iterable, Filter<E1> filter) {
		super();
		this.iterable = iterable;
		this.filter = filter;
	}

	protected Filter<E1> buildDefaultFilter() {
		return new DefaultFilter();
	}

	public Iterator<E2> iterator() {
		return new FilteringIterator<E1, E2>(this.iterable.iterator(), this.filter);
	}

	/**
	 * Return whether the <code>FilteringIterator</code>
	 * should return the specified next element from a call to the
	 * <code>next()</code> method.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Filter</code>.
	 */
	protected boolean accept(@SuppressWarnings("unused") E1 o) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}


	//********** default filter **********

	protected class DefaultFilter implements Filter<E1> {
		public boolean accept(E1 o) {
			return FilteringIterable.this.accept(o);
		}
	}

}
