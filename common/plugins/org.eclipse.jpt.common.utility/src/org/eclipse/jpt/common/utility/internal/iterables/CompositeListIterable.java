/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationListIterator;

/**
 * A <code>CompositeListIterable</code> wraps a {@link ListIterable}
 * of {@link ListIterable}s and makes them appear to be a single
 * {@link ListIterable}.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see CompositeListIterator
 * @see CompositeIterable
 * @see ReadOnlyCompositeListIterable
 */
public class CompositeListIterable<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends ListIterable<E>> iterables;


	/**
	 * Construct a list iterable with the specified list of list iterables.
	 */
	public CompositeListIterable(List<ListIterable<E>> iterables) {
		this(new ListListIterable<ListIterable<E>>(iterables));
	}

	/**
	 * Construct a list iterable with the specified list of list iterables.
	 */
	public CompositeListIterable(ListIterable<? extends ListIterable<E>> iterables) {
		super();
		this.iterables = iterables;
	}

	/**
	 * Construct a list iterable with the specified object prepended
	 * to the specified list.
	 */
	public CompositeListIterable(E object, List<E> list) {
		this(object, new ListListIterable<E>(list));
	}

	/**
	 * Construct a list iterable with the specified object prepended
	 * to the specified list iterable.
	 */
	@SuppressWarnings("unchecked")
	public CompositeListIterable(E object, ListIterable<E> iterable) {
		this(new SingleElementListIterable<E>(object), iterable);
	}

	/**
	 * Construct a list iterable with the specified object appended
	 * to the specified list.
	 */
	public CompositeListIterable(List<E> list, E object) {
		this(new ListListIterable<E>(list), object);
	}

	/**
	 * Construct a list iterable with the specified object appended
	 * to the specified list iterable.
	 */
	@SuppressWarnings("unchecked")
	public CompositeListIterable(ListIterable<E> iterable, E object) {
		this(iterable, new SingleElementListIterable<E>(object));
	}

	/**
	 * Construct a list iterable with the specified list iterables.
	 */
	public CompositeListIterable(ListIterable<E>... iterables) {
		this(new ArrayListIterable<ListIterable<E>>(iterables));
	}

	/**
	 * Construct a list iterable with the specified lists.
	 */
	public CompositeListIterable(List<E>... lists) {
		this(new TransformationListIterable<List<E>, ListIterable<E>>(new ArrayListIterable<List<E>>(lists)) {
			@Override
			protected ListIterable<E> transform(List<E> list) {
				return new ListListIterable<E>(list);
			}
		});
	}

	/**
	 * combined list iterators
	 */
	public ListIterator<E> iterator() {
		return new CompositeListIterator<E>(this.iterators());
	}

	/**
	 * list iterator of list iterators
	 */
	protected ListIterator<? extends ListIterator<E>> iterators() {
		return new TransformationListIterator<ListIterable<E>, ListIterator<E>>(this.iterables()) {
			@Override
			protected ListIterator<E> transform(ListIterable<E> next) {
				return next.iterator();
			}
		};
	}

	/**
	 * list iterator of list iterables
	 */
	protected ListIterator<? extends ListIterable<E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterables);
	}

}
