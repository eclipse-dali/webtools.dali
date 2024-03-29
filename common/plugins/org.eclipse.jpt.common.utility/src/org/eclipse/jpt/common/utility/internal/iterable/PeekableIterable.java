/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.PeekableIterator;

/**
 * A <code>PeekableIterable</code> wraps another {@link Iterable}
 * and returns an {@link PeekableIterator} that allows a
 * {@link PeekableIterator#peek() peek} at the next element to be 
 * returned by {@link java.util.Iterator#next()}.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that
 * the nested iterator's <code>next()</code> method will be invoked
 * <em>before</em> the peekable iterator's {@link java.util.Iterator#next()}
 * method is invoked. This is because the "next" element must be
 * pre-loaded for the {@link PeekableIterator#peek()} method.
 * This also prevents a peekable iterator from supporting the optional
 * {@link java.util.Iterator#remove()} method.
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
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public PeekableIterator<E> iterator() {
		return IteratorTools.peekable(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return this.iterable.toString();
	}
}
