/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.SimultaneousListIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * A <code>SimultaneousListIterable</code> returns a {@link ListIterator} that supports
 * the simultaneous processing of a set of {@link ListIterable}s
 * of objects of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the nested list iterators
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterator.SimultaneousListIterator
 */
public class SimultaneousListIterable<E>
	extends AbstractSimultaneousIterable<E, ListIterable<E>>
	implements ListIterable<List<E>>
{
	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	public <I extends ListIterable<E>> SimultaneousListIterable(I... iterables) {
		super(iterables);
	}

	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 */
	public <I extends ListIterable<E>> SimultaneousListIterable(Iterable<I> iterables) {
		super(iterables);
	}

	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends ListIterable<E>> SimultaneousListIterable(Iterable<I> iterables, int iterablesSize) {
		super(iterables, iterablesSize);
	}

	public ListIterator<List<E>> iterator() {
		ArrayList<ListIterator<E>> iterators = this.buildList();
		for (ListIterable<E> iterable : this.iterables) {
			iterators.add(iterable.iterator());
		}
		return new SimultaneousListIterator<E>(iterators, iterators.size());
	}
}
