/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>ReadOnlyCompositeListIterator</code> wraps a list
 * of {@link ListIterator}s and makes them appear to be a single
 * read-only {@link ListIterator}. A read-only composite list
 * iterator is more flexible than a normal composite list iterator when it
 * comes to the element types of the nested iterators.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class ReadOnlyCompositeListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends ListIterator<? extends E>> iterators;
	private ListIterator<? extends E> nextIterator;
	private int nextIndex;


	/**
	 * Construct a read-only list iterator with the specified list of lists.
	 */
	public ReadOnlyCompositeListIterator(List<? extends List<? extends E>> lists) {
		this(
			new TransformationListIterator<List<? extends E>, ListIterator<? extends E>>(lists.listIterator()) {
				@Override
				protected ListIterator<? extends E> transform(List<? extends E> list) {
					return list.listIterator();
				}
			}
		);
	}

	/**
	 * Construct a read-only list iterator with the specified list of
	 * list iterators.
	 */
	public ReadOnlyCompositeListIterator(ListIterator<? extends ListIterator<? extends E>> iterators) {
		super();
		this.iterators = iterators;
		this.nextIndex = 0;
	}

	/**
	 * Construct a read-only list iterator with the specified object prepended
	 * to the specified list.
	 */
	public ReadOnlyCompositeListIterator(E object, List<? extends E> list) {
		this(object, list.listIterator());
	}

	/**
	 * Construct a read-only list iterator with the specified object prepended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public ReadOnlyCompositeListIterator(E object, ListIterator<? extends E> iterator) {
		this(new SingleElementListIterator<E>(object), iterator);
	}

	/**
	 * Construct a read-only list iterator with the specified object appended
	 * to the specified list.
	 */
	public ReadOnlyCompositeListIterator(List<? extends E> list, E object) {
		this(list.listIterator(), object);
	}

	/**
	 * Construct a read-only list iterator with the specified object appended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public ReadOnlyCompositeListIterator(ListIterator<? extends E> iterator, E object) {
		this(iterator, new SingleElementListIterator<E>(object));
	}

	/**
	 * Construct a read-only list iterator with the specified lists.
	 */
	public ReadOnlyCompositeListIterator(List<? extends E>... lists) {
		this(Arrays.asList(lists));
	}

	/**
	 * Construct a read-only list iterator with the specified list iterators.
	 */
	public ReadOnlyCompositeListIterator(ListIterator<? extends E>... iterators) {
		this(new ArrayListIterator<ListIterator<? extends E>>(iterators));
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
		return StringTools.buildToStringFor(this, this.iterators);
	}

}
