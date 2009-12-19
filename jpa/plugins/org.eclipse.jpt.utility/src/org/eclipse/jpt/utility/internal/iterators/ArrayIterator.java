/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * An <code>ArrayIterator</code> provides an {@link Iterator}
 * for an array of objects of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.ArrayIterable
 */
public class ArrayIterator<E>
	implements Iterator<E>
{
	final E[] array;	// private-protected
	int cursor;		// private-protected
	private final int max;


	/**
	 * Construct an iterator for the specified array.
	 */
	public ArrayIterator(E... array) {
		this(array, 0, array.length);
	}

	/**
	 * Construct an iterator for the specified array,
	 * starting at the specified start index and continuing for
	 * the rest of the array.
	 */
	public ArrayIterator(E[] array, int start) {
		this(array, start, array.length - start);
	}

	/**
	 * Construct an iterator for the specified array,
	 * starting at the specified start index and continuing for
	 * the specified length.
	 */
	public ArrayIterator(E[] array, int start, int length) {
		super();
		if ((start < 0) || (start > array.length)) {
			throw new IllegalArgumentException("start: " + start); //$NON-NLS-1$
		}
		if ((length < 0) || (length > array.length - start)) {
			throw new IllegalArgumentException("length: " + length); //$NON-NLS-1$
		}
		this.array = array;
		this.cursor = start;
		this.max = start + length;
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
		return StringTools.buildToStringFor(this, Arrays.toString(this.array));
	}

}
