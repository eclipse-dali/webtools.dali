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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.common.utility.internal.iterator.SimultaneousIterator;

/**
 * A <code>SimultaneousIterable</code> returns an {@link Iterator} that supports
 * the simultaneous processing of a set of {@link Iterable}s
 * of objects of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the nested iterators
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterator.SimultaneousIterator
 */
public class SimultaneousIterable<E>
	extends AbstractSimultaneousIterable<E, Iterable<E>>
	implements Iterable<List<E>>
{
	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	public <I extends Iterable<E>> SimultaneousIterable(I... iterables) {
		super(iterables);
	}

	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 */
	public <I extends Iterable<E>> SimultaneousIterable(Iterable<I> iterables) {
		super(iterables);
	}

	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends Iterable<E>> SimultaneousIterable(Iterable<I> iterables, int iterablesSize) {
		super(iterables, iterablesSize);
	}

	public Iterator<List<E>> iterator() {
		ArrayList<Iterator<E>> iterators = this.buildList();
		for (Iterable<E> iterable : this.iterables) {
			iterators.add(iterable.iterator());
		}
		return new SimultaneousIterator<E>(iterators, iterators.size());
	}
}
