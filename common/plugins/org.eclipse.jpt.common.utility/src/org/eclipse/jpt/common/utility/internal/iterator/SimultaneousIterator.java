/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;

/**
 * A <code>SimultaneousIterator</code> provides an {@link Iterator}
 * for the simultaneous processing of a set of {@link Iterator}s
 * of objects of type <code>E</code>. Each call to {@link #next()}
 * returns a {@link java.util.List List} of elements of type <code>E</code>. The elements
 * in the list are in the same order as the order of the {@link Iterator}s
 * passed to the simultaneous iterator's constructor. The simultaneous iterator
 * will return as many lists as there are elements returned by <em>all</em>
 * the nested {@link Iterator}s. Any elements returned by {@link Iterator}s
 * that are longer than the shortest of the {@link Iterator}s will be ignored.
 * If an empty list of {@link Iterator}s is passed to the simultaneuous
 * iterator's constructor, the resulting iterator will be empty.
 * 
 * @param <E> the type of elements returned by the nested iterators
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.SimultaneousIterable
 */
public class SimultaneousIterator<E>
	extends AbstractSimultaneousIterator<E, Iterator<? extends E>>
{
	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	public <I extends Iterator<? extends E>> SimultaneousIterator(Iterable<I> iterators) {
		super(iterators);
	}

	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends Iterator<? extends E>> SimultaneousIterator(Iterable<I> iterators, int iteratorsSize) {
		super(iterators, iteratorsSize);
	}
}
