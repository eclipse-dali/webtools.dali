/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * A <code>SimultaneousIterator</code> provides an {@link Iterator}
 * for the simultaneous processing of a set of {@link Iterator}s
 * of objects of type <code>E</code>. Each call to {@link #next()}
 * returns a {@link List} of elements of type <code>E</code>. The elements
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
	extends AbstractSimultaneousIterator<E, Iterator<E>>
{
	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	public <I extends Iterator<E>> SimultaneousIterator(I... iterators) {
		super(iterators);
	}

	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	public <I extends Iterator<E>> SimultaneousIterator(Iterable<I> iterators) {
		super(iterators);
	}

	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends Iterator<E>> SimultaneousIterator(Iterable<I> iterators, int iteratorsSize) {
		super(iterators, iteratorsSize);
	}
}
