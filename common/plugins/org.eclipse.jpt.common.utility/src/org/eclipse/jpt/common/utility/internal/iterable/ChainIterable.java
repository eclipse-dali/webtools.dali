/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>ChainIterable</code> provides a pluggable {@link Iterable}
 * that loops over a chain of arbitrarily linked objects. The chain
 * should be null-terminated (i.e. a call to the {@link Transformer#transform(Object)}
 * method should return <code>null</code> when it is passed the last
 * link of the chain).
 * To use, supply a starting link and a {@link Transformer}.
 * The starting link will be the first object returned by the iterable's iterator.
 * If the starting link is <code>null</code>, the iterable will be empty.
 * <p>
 * <strong>NB:</strong> this iterable does not support <code>null</code> elements.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see IteratorTools#chainIterator(Object, Transformer)
 */
public class ChainIterable<E>
	implements Iterable<E>
{
	private final E first;
	private final Transformer<? super E, ? extends E> transformer;


	/**
	 * Construct an iterable with the specified starting link
	 * and linker.
	 */
	public ChainIterable(E first, Transformer<? super E, ? extends E> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.first = first;
		this.transformer = transformer;
	}

	public Iterator<E> iterator() {
		return IteratorTools.chainIterator(this.first, this.transformer);
	}

	@Override
	public String toString() {
		return ListTools.arrayList(this).toString();
	}
}
