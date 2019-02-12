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

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * An <code>ArrayIterator</code> provides an {@link Iterator}
 * for an array of objects of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable
 */
public class ArrayIterator<E>
	implements Iterator<E>
{
	/* private-protected */ final E[] array;
	/* private-protected */ int cursor;
	private final int max;


	/**
	 * Construct an iterator for the specified array,
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public ArrayIterator(E[] array, int start, int end) {
		super();
		if ((start < 0) || (start > array.length)) {
			throw new IllegalArgumentException("end: " + start); //$NON-NLS-1$
		}
		if ((end < start) || (end > array.length)) {
			throw new IllegalArgumentException("length: " + end); //$NON-NLS-1$
		}
		this.array = array;
		this.cursor = start;
		this.max = end;
	}

	public boolean hasNext() {
		return this.cursor != this.max;
	}

	public E next() {
		if (this.hasNext()) {
			return this.array[this.cursor++];
		}
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, Arrays.toString(this.array));
	}
}
