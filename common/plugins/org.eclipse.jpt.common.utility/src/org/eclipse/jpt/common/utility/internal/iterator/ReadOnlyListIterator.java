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
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>ReadOnlyListIterator</code> wraps another
 * {@link ListIterator} and removes support for:<ul>
 * <li>{@link #remove()}
 * 	<li>{@link #set(Object)}
 * 	<li>{@link #add(Object)}
 * </ul>
 * 
 * @param <E> the type of elements returned by the list iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.ReadOnlyListIterable
 */
public class ReadOnlyListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends E> listIterator;


	/**
	 * Construct a list iterator on the specified list iterator that
	 * disallows removes, sets, and adds.
	 */
	public ReadOnlyListIterator(ListIterator<? extends E> listIterator) {
		super();
		if (listIterator == null) {
			throw new NullPointerException();
		}
		this.listIterator = listIterator;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.listIterator.hasNext();
	}

	public E next() {
		// delegate to the nested iterator
		return this.listIterator.next();
	}

	public boolean hasPrevious() {
		// delegate to the nested iterator
		return this.listIterator.hasPrevious();
	}

	public E previous() {
		// delegate to the nested iterator
		return this.listIterator.previous();
	}

	public int nextIndex() {
		// delegate to the nested iterator
		return this.listIterator.nextIndex();
	}

	public int previousIndex() {
		// delegate to the nested iterator
		return this.listIterator.previousIndex();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public void set(E o) {
		throw new UnsupportedOperationException();
	}

	public void add(E o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.listIterator);
	}
}
