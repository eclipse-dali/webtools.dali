/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>ReadOnlyCompositeListIterator</code> wraps a list
 * of {@link ListIterator}s and makes them appear to be a single
 * read-only {@link ListIterator}. A read-only composite list
 * iterator is more flexible than a normal composite list iterator when it
 * comes to the element types of the nested iterators.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.ReadOnlyCompositeListIterable
 */
public class ReadOnlyCompositeListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends ListIterator<? extends E>> iterators;
	private ListIterator<? extends E> nextIterator;
	private int nextIndex;


	/**
	 * Construct a read-only list iterator with the specified list of
	 * list iterators.
	 */
	public ReadOnlyCompositeListIterator(ListIterator<? extends ListIterator<? extends E>> iterators) {
		super();
		if (iterators == null) {
			throw new NullPointerException();
		}
		this.iterators = iterators;
		this.nextIndex = 0;
	}

	public boolean hasNext() {
		try {
			this.loadNextIterator();
		} catch (NoSuchElementException ex) {
			// this occurs if there are no iterators at all
			return false;
		}
		return this.nextIterator.hasNext();
	}

	public boolean hasPrevious() {
		try {
			this.loadPreviousIterator();
		} catch (NoSuchElementException ex) {
			// this occurs if there are no iterators at all
			return false;
		}
		return this.nextIterator.hasPrevious();
	}

	public E next() {
		this.loadNextIterator();
		E result = this.nextIterator.next();
	
		// the statement above will throw a NoSuchElementException
		// if the current iterator is at the end of the line;
		// so if we get here, we can increment 'nextIndex'
		this.nextIndex++;
	
		return result;
	}

	public int nextIndex() {
		return this.nextIndex;
	}

	public E previous() {
		this.loadPreviousIterator();
		E result = this.nextIterator.previous();
	
		// the statement above will throw a NoSuchElementException
		// if the current iterator is at the end of the line;
		// so if we get here, we can decrement 'nextIndex'
		this.nextIndex--;
	
		return result;
	}

	public int previousIndex() {
		return this.nextIndex  - 1;
	}

	public void add(E o) {
		// the list iterator is read-only
		throw new UnsupportedOperationException();
	}

	public void remove() {
		// the list iterator is read-only
		throw new UnsupportedOperationException();
	}

	public void set(E e) {
		// the list iterator is read-only
		throw new UnsupportedOperationException();
	}

	/**
	 * Load {@link #nextIterator} with the first iterator that {@link #hasNext()}
	 * or the final iterator if all the elements have already been retrieved.
	 */
	private void loadNextIterator() {
		this.checkNextIterator();
		while (( ! this.nextIterator.hasNext()) && this.iterators.hasNext()) {
			this.nextIterator = this.iterators.next();
		}
	}

	/**
	 * Load {@link #nextIterator} with the first iterator that {@link #hasPrevious()}
	 * or the first iterator if all the elements have already been retrieved.
	 */
	private void loadPreviousIterator() {
		this.checkNextIterator();
		while (( ! this.nextIterator.hasPrevious()) && this.iterators.hasPrevious()) {
			this.nextIterator = this.iterators.previous();
		}
	}

	/**
	 * If {@link #nextIterator} is null, load it with the first iterator.
	 */
	private void checkNextIterator() {
		if (this.nextIterator == null) {
			this.nextIterator = this.iterators.next();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterators);
	}
}
