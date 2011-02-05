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

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationListIterator;

/**
 * A <code>ReadOnlyCompositeListIterable</code> wraps a {@link ListIterable}
 * of {@link ListIterable}s and makes them appear to be a single
 * read-only {@link ListIterable}. A read-only composite list
 * iterable is more flexible than a normal composite list iterable when it
 * comes to the element types of the nested list iterables.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see ReadOnlyCompositeListIterator
 * @see CompositeListIterable
 */
public class ReadOnlyCompositeListIterable<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends ListIterable<? extends E>> iterables;


	/**
	 * Construct a list iterable with the specified list of list iterables.
	 */
	public ReadOnlyCompositeListIterable(ListIterable<? extends ListIterable<? extends E>> iterables) {
		super();
		this.iterables = iterables;
	}

	/**
	 * Construct a list iterable with the specified object prepended
	 * to the specified list iterable.
	 */
	@SuppressWarnings("unchecked")
	public ReadOnlyCompositeListIterable(E object, ListIterable<? extends E> iterable) {
		this(new SingleElementListIterable<E>(object), iterable);
	}

	/**
	 * Construct a list iterable with the specified object appended
	 * to the specified list iterable.
	 */
	@SuppressWarnings("unchecked")
	public ReadOnlyCompositeListIterable(ListIterable<? extends E> iterable, E object) {
		this(iterable, new SingleElementListIterable<E>(object));
	}

	/**
	 * Construct a list iterable with the specified list iterables.
	 */
	public ReadOnlyCompositeListIterable(ListIterable<? extends E>... iterables) {
		this(new ArrayListIterable<ListIterable<? extends E>>(iterables));
	}

	/**
	 * combined list iterators
	 */
	public ListIterator<E> iterator() {
		return new ReadOnlyCompositeListIterator<E>(this.iterators());
	}

	/**
	 * list iterator of list iterators
	 */
	protected ListIterator<? extends ListIterator<? extends E>> iterators() {
		return new TransformationListIterator<ListIterable<? extends E>, ListIterator<? extends E>>(this.iterables()) {
			@Override
			protected ListIterator<? extends E> transform(ListIterable<? extends E> next) {
				return next.iterator();
			}
		};
	}

	/**
	 * list iterator of list iterables
	 */
	protected ListIterator<? extends ListIterable<? extends E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterables);
	}

}
