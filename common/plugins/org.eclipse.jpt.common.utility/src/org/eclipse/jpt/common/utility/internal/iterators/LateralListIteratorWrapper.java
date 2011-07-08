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

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Wrap a list iterator on elements of type <code>E1</code>, converting it into
 * a list iterator on elements of type <code>E2</code>. <em>Assume</em> the
 * wrapped list iterator returns only elements of type <code>E2</code>.
 * The result is a {@link ClassCastException} if this assumption is false.
 * 
 * @param <E1> input: the type of elements returned by the wrapped list iterator
 * @param <E2> output: the type of elements returned by the list iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.LateralIterableWrapper
 * @see SubListIteratorWrapper
 */
public class LateralListIteratorWrapper<E1, E2>
	implements ListIterator<E2>
{
	final ListIterator<E1> listIterator;


	public LateralListIteratorWrapper(List<E1> list) {
		this(list.listIterator());
	}

	public LateralListIteratorWrapper(ListIterable<E1> listIterable) {
		this(listIterable.iterator());
	}

	public LateralListIteratorWrapper(ListIterator<E1> iterator) {
		super();
		this.listIterator = iterator;
	}

	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public E2 next() {
		return (E2) this.listIterator.next();
	}

	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	@SuppressWarnings("unchecked")
	public E2 previous() {
		return (E2) this.listIterator.previous();
	}

	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	public void remove() {
		this.listIterator.remove();
	}

	@SuppressWarnings("unchecked")
	public void set(E2 e) {
		this.listIterator.set((E1) e);
	}

	@SuppressWarnings("unchecked")
	public void add(E2 e) {
		this.listIterator.add((E1) e);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listIterator);
	}
}
