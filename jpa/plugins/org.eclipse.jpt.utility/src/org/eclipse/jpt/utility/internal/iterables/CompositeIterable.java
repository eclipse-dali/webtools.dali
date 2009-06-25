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

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * A <code>CompositeIterable</code> wraps an <code>Iterable</code>
 * of <code>Iterable</code>s and makes them appear to be a single
 * <code>Iterable</code>.
 * 
 * @see CompositeIterator
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
		this.iterables = iterables;
	}

	/**
	 * Construct an iterable with the specified object prepended
	 * to the specified iterable.
	 */
	@SuppressWarnings("unchecked")
	public CompositeIterable(E object, Iterable<? extends E> iterable) {
		this(new SingleElementIterable<E>(object), iterable);
	}

	/**
	 * Construct an iterable with the specified object appended
	 * to the specified iterable.
	 */
	@SuppressWarnings("unchecked")
	public CompositeIterable(Iterable<? extends E> iterable, E object) {
		this(iterable, new SingleElementIterable<E>(object));
	}

	/**
	 * Construct an iterable with the specified iterables.
	 */
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
		return new TransformationIterator<Iterable<? extends E>, Iterator<? extends E>>(this.iterables()) {
			@Override
			protected Iterator<? extends E> transform(Iterable<? extends E> next) {
				return next.iterator();
			}
		};
	}

	/**
	 * iterator of iterables
	 */
	protected Iterator<? extends Iterable<? extends E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterables);
	}

}
