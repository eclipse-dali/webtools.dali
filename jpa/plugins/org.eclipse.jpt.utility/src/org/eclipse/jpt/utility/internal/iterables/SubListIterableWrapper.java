/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.SubListIteratorWrapper;

/**
 * Wrap a list iterable of elements of type <code>E1</code>, converting it into
 * a list iterable of elements of type <code>E2</code>. Assume the wrapped
 * iterable contains only elements of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements contained by the wrapped list iterable
 * @param <E2> output: the type of elements returned by the iterable's list iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterators.SubListIteratorWrapper
 */
public class SubListIterableWrapper<E1, E2>
	implements ListIterable<E2>
{
	private final ListIterable<E1> iterable;


	public SubListIterableWrapper(List<E1> list) {
		this(new ListListIterable<E1>(list));
	}

	public SubListIterableWrapper(ListIterable<E1> iterable) {
		super();
		this.iterable = iterable;
	}

	public ListIterator<E2> iterator() {
		return new SubListIteratorWrapper<E1, E2>(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}

}
