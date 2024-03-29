/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
