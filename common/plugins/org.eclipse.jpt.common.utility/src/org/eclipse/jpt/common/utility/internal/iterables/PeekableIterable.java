/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.PeekableIterator;

/**
 * A <code>PeekableIterable</code> wraps another {@link Iterable}
 * and returns an {@link PeekableIterator} that allows a
 * {@link PeekableIterator#peek() peek} at the next element to be 
 * returned by {@link Iterator#next()}.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that
 * the nested iterator's <code>next()</code> method will be invoked
 * <em>before</em> the peekable iterator's {@link Iterator#next()}
 * method is invoked. This is because the "next" element must be
 * pre-loaded for the {@link PeekableIterator#peek()} method.
 * This also prevents a peekable iterator from supporting the optional
 * {@link Iterator#remove()} method.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see PeekableIterator
 */
public class PeekableIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> iterable;

	/**
	 * Construct a peekable iterable that wraps the specified
	 * iterable.
	 */
	public PeekableIterable(Iterable<? extends E> iterable) {
		super();
		this.iterable = iterable;
	}

	public PeekableIterator<E> iterator() {
		return new PeekableIterator<E>(this.iterable);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}

}
