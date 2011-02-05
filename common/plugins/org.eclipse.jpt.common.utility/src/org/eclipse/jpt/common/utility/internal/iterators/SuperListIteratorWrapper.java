/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
 * Wrap a list iterator on elements of any sub-type of <code>E</code>, converting it into a
 * list iterator on elements of type <code>E</code>. This shouldn't be a problem since the
 * resulting list iterator disables the methods that would put invalid elements
 * in the iterator's backing list (i.e. {@link #set(Object)} and {@link #add(Object)}).
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper
 */
public class SuperListIteratorWrapper<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends E> listIterator;


	public SuperListIteratorWrapper(List<? extends E> list) {
		this(list.listIterator());
	}

	public SuperListIteratorWrapper(ListIterable<? extends E> listIterable) {
		this(listIterable.iterator());
	}

	public SuperListIteratorWrapper(ListIterator<? extends E> listIterator) {
		super();
		this.listIterator = listIterator;
	}

	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	public E next() {
		return this.listIterator.next();
	}

	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	public E previous() {
		return this.listIterator.previous();
	}

	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	public void remove() {
		this.listIterator.remove();
	}

	public void set(E e) {
		throw new UnsupportedOperationException();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listIterator);
	}

}
