/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterator.CompositeIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>CompositeIterable</code> wraps an {@link Iterable}
 * of {@link Iterable}s and makes them appear to be a single
 * {@link Iterable}.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see CompositeIterator
 * @see CompositeListIterable
 */
public class CompositeIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends Iterable<? extends E>> iterables;


	/**
	 * Construct an iterable with the specified collection of iterables.
	 */
	public CompositeIterable(Iterable<? extends Iterable<? extends E>> iterables) {
		super();
		if (iterables == null) {
			throw new NullPointerException();
		}
		this.iterables = iterables;
	}

	/**
	 * Construct an iterable with the specified iterables.
	 */
	// TODO remove
	public CompositeIterable(Iterable<? extends E>... iterables) {
		this(new ArrayIterable<Iterable<? extends E>>(iterables));
	}

	/**
	 * combined iterators
	 */
	public Iterator<E> iterator() {
		return new CompositeIterator<E>(this.iterators());
	}

	/**
	 * iterator of iterators
	 */
	protected Iterator<? extends Iterator<? extends E>> iterators() {
		Transformer<Iterable<? extends E>, Iterator<E>> transformer = IterableTools.iteratorTransformer();
		return IteratorTools.transform(this.iterables(), transformer);
	}

	/**
	 * iterator of iterables
	 */
	protected Iterator<? extends Iterable<? extends E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return ListTools.list(this).toString();
	}
}
