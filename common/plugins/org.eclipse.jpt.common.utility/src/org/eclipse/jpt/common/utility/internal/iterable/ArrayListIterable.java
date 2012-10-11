/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.ArrayListIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * An <code>ArrayListIterable</code> provides a {@link ListIterable}
 * for an array of objects of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see ArrayIterable
 * @see ArrayListIterator
 */
public class ArrayListIterable<E>
	extends ArrayIterable<E>
	implements ListIterable<E>
{
	/**
	 * Construct a list iterable for the specified array.
	 */
	public ArrayListIterable(E... array) {
		this(array, 0);
	}

	/**
	 * Construct a list iterable for the specified array,
	 * starting at the specified start index and continuing for
	 * the rest of the array.
	 */
	public ArrayListIterable(E[] array, int start) {
		this(array, start, array.length);
	}

	/**
	 * Construct a list iterable for the specified array,
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public ArrayListIterable(E[] array, int start, int end) {
		super(array, start, end);
	}

	@Override
	public ListIterator<E> iterator() {
		return new ArrayListIterator<E>(this.array, this.start, this.end);
	}
}
