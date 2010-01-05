/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap an iterator on elements of type <code>E1</code>, converting it into an
 * iterator on elements of type <code>E2</code>. Assume the wrapped iterator
 * returns only elements of type <code>E2</code>.
 * 
 * @param <E1> input: the type of elements returned by the wrapped iterator
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper
 */
public class SubIteratorWrapper<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<E1> iterator;


	public SubIteratorWrapper(Iterable<E1> iterable) {
		this(iterable.iterator());
	}

	public SubIteratorWrapper(Iterator<E1> iterator) {
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
