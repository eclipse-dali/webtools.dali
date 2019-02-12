/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An <code>ArrayListIterator</code> provides a {@link ListIterator}
 * for an array of objects.
 * <p>
 * The name might be a bit confusing:
 * This is a {@link ListIterator} for an <code>Array</code>;
 * <em>not</em> an {@link java.util.Iterator Iterator} for an
 * {@link java.util.ArrayList ArrayList}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.ArrayListIterable
 */
public class ArrayListIterator<E>
	extends ArrayIterator<E>
	implements ListIterator<E>
{
	private final int min;


	/**
	 * Construct a list iterator for the specified array,
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public ArrayListIterator(E[] array, int start, int end) {
		super(array, start, end);
		this.min = start;
	}

	public int nextIndex() {
		return this.cursor;
	}

	public int previousIndex() {
		return this.cursor - 1;
	}

	public boolean hasPrevious() {
		return this.cursor != this.min;
	}

	public E previous() {
		if (this.hasPrevious()) {
			return this.array[--this.cursor];
		}
		throw new NoSuchElementException();
	}

	public void add(E e) {
		throw new UnsupportedOperationException();
	}

	public void set(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
