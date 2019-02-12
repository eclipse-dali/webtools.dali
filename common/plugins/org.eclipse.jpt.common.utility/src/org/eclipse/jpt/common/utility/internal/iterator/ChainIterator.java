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
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>ChainIterator</code> provides a pluggable {@link Iterator}
 * that loops over a chain of arbitrarily associated objects. The chain
 * should be null-terminated (i.e. a call to {@link Transformer#transform(Object)}
 * should return <code>null</code> when it is passed the last
 * link of the chain).
 * To use, supply a starting link and a {@link Transformer}.
 * The starting link will be the first object returned by the iterator.
 * If the starting link is <code>null</code>, the iterator will be empty.
 * <p>
 * <strong>NB:</strong> this iterator does not support <code>null</code> elements.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#chainIterable(Object, Transformer)
 */
public class ChainIterator<E>
	implements Iterator<E>
{
	private E next;
	private final Transformer<? super E, ? extends E> transformer;


	/**
	 * Construct an iterator with the specified starting link
	 * and transformer.
	 */
	public ChainIterator(E first, Transformer<? super E, ? extends E> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.next = first;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		return this.next != null;
	}

	public E next() {
		if (this.next == null) {
			throw new NoSuchElementException();
		}
		E result = this.next;
		this.next = this.transformer.transform(this.next);
		return result;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.next);
	}
}
