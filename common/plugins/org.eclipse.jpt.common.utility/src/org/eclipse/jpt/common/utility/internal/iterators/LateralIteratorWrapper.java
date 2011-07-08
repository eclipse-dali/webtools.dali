/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterators;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Wrap an iterator on elements of type <code>E1</code>, converting it into an
 * iterator on elements of type <code>E2</code>. <em>Assume</em> the wrapped
 * iterator returns only elements of type <code>E2</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * 
 * @param <E1> input: the type of elements returned by the wrapped iterator
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.LateralIterableWrapper
 * @see SubIteratorWrapper
 */
public class LateralIteratorWrapper<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<E1> iterator;


	public LateralIteratorWrapper(Iterable<E1> iterable) {
		this(iterable.iterator());
	}

	public LateralIteratorWrapper(Iterator<E1> iterator) {
		super();
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public E2 next() {
		return (E2) this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}
}
