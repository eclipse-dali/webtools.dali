/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
	extends AbstractSimultaneousIterable<E, Iterable<? extends E>>
	implements Iterable<List<E>>
{
	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 */
	public <I extends Iterable<? extends E>> SimultaneousIterable(Iterable<I> iterables) {
		super(iterables);
	}

	/**
	 * Construct a "multiple" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends Iterable<? extends E>> SimultaneousIterable(Iterable<I> iterables, int iterablesSize) {
		super(iterables, iterablesSize);
	}

	public Iterator<List<E>> iterator() {
		ArrayList<Iterator<? extends E>> iterators = this.buildList();
		for (Iterable<? extends E> iterable : this.iterables) {
			iterators.add(iterable.iterator());
		}
		return new SimultaneousIterator<E>(iterators, iterators.size());
	}
}
