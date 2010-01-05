/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * A <code>CompositeListIterator</code> wraps a list
 * of {@link ListIterator}s and makes them appear to be a single
 * {@link ListIterator}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.utility.internal.iterables.CompositeListIterable
 */
public class CompositeListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends ListIterator<E>> iterators;
	private ListIterator<E> nextIterator;
	private int nextIndex;
 	/**
 	 * true if "next" was last returned; false if "previous" was last returned;
 	 * this determines the effect of {@link #remove(Object)} on {@link #nextIndex}
 	 */
	private boolean nextReturned;
	private ListIterator<E> lastIteratorToReturnElement;


	/**
	 * Construct a list iterator on the elements in the specified list of lists.
	 */
	public CompositeListIterator(List<? extends List<E>> lists) {
		this(
			new TransformationListIterator<List<E>, ListIterator<E>>(lists.listIterator()) {
				@Override
				protected ListIterator<E> transform(List<E> list) {
					return list.listIterator();
				}
			}
		);
	}

	/**
	 * Construct a list iterator on the elements in the specified list of lists.
	 */
	public CompositeListIterator(ListIterable<? extends ListIterable<E>> listIterables) {
		this(
			new TransformationListIterator<ListIterable<E>, ListIterator<E>>(listIterables.iterator()) {
				@Override
				protected ListIterator<E> transform(ListIterable<E> list) {
					return list.iterator();
				}
			}
		);
	}

	/**
	 * Construct a list iterator with the specified list of list iterators.
	 */
	public CompositeListIterator(ListIterator<? extends ListIterator<E>> iterators) {
		super();
		this.iterators = iterators;
		this.nextIndex = 0;
		this.nextReturned = false;
	}

	/**
	 * Construct a list iterator with the specified object prepended
	 * to the specified list.
	 */
	public CompositeListIterator(E object, List<E> list) {
		this(object, list.listIterator());
	}

	/**
	 * Construct a list iterator with the specified object prepended
	 * to the specified list.
	 */
	public CompositeListIterator(E object, ListIterable<E> listIterable) {
		this(object, listIterable.iterator());
	}

	/**
	 * Construct a list iterator with the specified object prepended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public CompositeListIterator(E object, ListIterator<E> iterator) {
		this(new SingleElementListIterator<E>(object), iterator);
	}

	/**
	 * Construct a list iterator with the specified object appended
	 * to the specified list.
	 */
	public CompositeListIterator(List<E> list, E object) {
		this(list.listIterator(), object);
	}

	/**
	 * Construct a list iterator with the specified object appended
	 * to the specified list.
	 */
	public CompositeListIterator(ListIterable<E> listIterable, E object) {
		this(listIterable.iterator(), object);
	}

	/**
	 * Construct a list iterator with the specified object appended
	 * to the specified iterator.
	 */
	@SuppressWarnings("unchecked")
	public CompositeListIterator(ListIterator<E> iterator, E object) {
		this(iterator, new SingleElementListIterator<E>(object));
	}

	/**
	 * Construct a list iterator with the specified lists.
	 */
	public CompositeListIterator(List<E>... lists) {
		this(Arrays.asList(lists));
	}

	/**
	 * Construct a list iterator with the specified lists.
	 */
	public CompositeListIterator(ListIterable<E>... listIterables) {
		this(new ArrayListIterable<ListIterable<E>>(listIterables));
	}

	/**
	 * Construct a list iterator with the specified list iterators.
	 */
	public CompositeListIterator(ListIterator<E>... iterators) {
		this(new ArrayListIterator<ListIterator<E>>(iterators));
	}

	public void add(E o) {
		this.checkNextIterator();
		this.nextIterator.add(o);
		this.nextIndex++;
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
		// so if we get here, we can set the 'lastIteratorToReturnElement'
		this.lastIteratorToReturnElement = this.nextIterator;
		this.nextIndex++;
		this.nextReturned = true;

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
		// so if we get here, we can set the 'lastIteratorToReturnElement'
		this.lastIteratorToReturnElement = this.nextIterator;
		this.nextIndex--;
		this.nextReturned = false;

		return result;
	}

	public int previousIndex() {
		return this.nextIndex  - 1;
	}

	public void remove() {
		if (this.lastIteratorToReturnElement == null) {
			throw new IllegalStateException();
		}
		this.lastIteratorToReturnElement.remove();
		if (this.nextReturned) {
			// decrement the index because the "next" element has moved forward in the list
			this.nextIndex--;
		}
	}

	public void set(E e) {
		if (this.lastIteratorToReturnElement == null) {
			throw new IllegalStateException();
		}
		this.lastIteratorToReturnElement.set(e);
	}

	/**
	 * Load 'nextIterator' with the first iterator that <code>hasNext()</code>
	 * or the final iterator if all the elements have already been retrieved.
	 */
	private void loadNextIterator() {
		this.checkNextIterator();
		while (( ! this.nextIterator.hasNext()) && this.iterators.hasNext()) {
			this.nextIterator = this.iterators.next();
		}
	}

	/**
	 * Load 'nextIterator' with the first iterator that <code>hasPrevious()</code>
	 * or the first iterator if all the elements have already been retrieved.
	 */
	private void loadPreviousIterator() {
		this.checkNextIterator();
		while (( ! this.nextIterator.hasPrevious()) && this.iterators.hasPrevious()) {
			this.nextIterator = this.iterators.previous();
		}
	}

	/**
	 * If 'nextIterator' is null, load it with the first iterator.
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
