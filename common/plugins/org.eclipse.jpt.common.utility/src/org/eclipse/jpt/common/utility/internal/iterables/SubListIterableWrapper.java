/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.SubListIteratorWrapper;

/**
 * Wrap a list iterable of elements of type <code>E1</code>, converting it into
 * a list iterable of elements of type <code>E2</code>. <em>Assume</em> the
 * wrapped iterable contains only elements of type <code>E2</code>.
 * The result is a {@link ClassCastException} if this assumption is false.
 * <p>
 * This is like a {@link LateralListIterableWrapper} but with more restrictive type
 * parameters.
 * 
 * @param <E1> input: the type of elements contained by the wrapped list iterable
 * @param <E2> output: the type of elements returned by the iterable's list iterators
 * 
 * @see SubListIteratorWrapper
 */
public class SubListIterableWrapper<E1, E2 extends E1>
	implements ListIterable<E2>
{
	private final ListIterable<E1> iterable;


	public SubListIterableWrapper(List<E1> list) {
		this(new ListListIterable<E1>(list));
	}

	public SubListIterableWrapper(ListIterable<E1> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public ListIterator<E2> iterator() {
		return new SubListIteratorWrapper<E1, E2>(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return this.iterable.toString();
	}
}
