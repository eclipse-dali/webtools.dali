/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * A <code>SimultaneousListIterator</code> provides a {@link ListIterator}
 * for the simultaneous processing of a set of {@link ListIterator}s
 * of objects of type <code>E</code>. Each call to {@link #next()}
 * returns a {@link List} of elements of type <code>E</code>. The elements
 * in the list are in the same order as the order of the {@link ListIterator}s
 * passed to the simultaneous iterator's constructor. The simultaneous list iterator
 * will return as many lists as there are elements returned by <em>all</em>
 * the nested {@link ListIterator}s. Any elements returned by {@link ListIterator}s
 * that are longer than the shortest of the {@link ListIterator}s will be ignored.
 * If an empty list of {@link ListIterator}s is passed to the simultaneuous
 * list iterator's constructor, the resulting list iterator will be empty.
 * 
 * @param <E> the type of elements returned by the nested iterators
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.SimultaneousListIterable
 */
public class SimultaneousListIterator<E>
	extends AbstractSimultaneousIterator<E, ListIterator<E>>
	implements ListIterator<List<E>>
{
	/**
	 * Construct a "simultaneous" list iterator for the specified list iterators.
	 */
	public <I extends ListIterator<E>> SimultaneousListIterator(I... iterators) {
		super(iterators);
	}

	/**
	 * Construct a "simultaneous" list iterator for the specified list iterators.
	 */
	public <I extends ListIterator<E>> SimultaneousListIterator(Iterable<I> iterators) {
		super(iterators);
	}

	/**
	 * Construct a "simultaneous" list iterator for the specified list iterators.
	 * Use the specified size as a performance hint.
	 */
	public <I extends ListIterator<E>> SimultaneousListIterator(Iterable<I> iterators, int iteratorsSize) {
		super(iterators, iteratorsSize);
	}

	public int nextIndex() {
		// get the index from the first iterator
		return this.iteratorsIsEmpty() ? 0 : this.iterators.iterator().next().nextIndex();
	}

	public boolean hasPrevious() {
		if (this.iteratorsIsEmpty()) {
			return false;
		}
		for (ListIterator<E> iterator : this.iterators) {
			if ( ! iterator.hasPrevious()) {
				return false;
			}
		}
		return true;
	}

	public List<E> previous() {
		if (this.iteratorsIsEmpty()) {
			throw new NoSuchElementException();
		}
		ArrayList<E> result = this.buildList();
		for (ListIterator<E> iterator : this.iterators) {
			result.add(iterator.next());
		}
		return result;
	}

	public int previousIndex() {
		// get the index from the first iterator
		return this.iteratorsIsEmpty() ? -1 : this.iterators.iterator().next().previousIndex();
	}

	public void add(List<E> elements) {
		this.checkElements(elements);
		Iterator<E> addElements = elements.iterator();
		for (ListIterator<E> iterator : this.iterators) {
			iterator.add(addElements.next());
		}
	}

	public void set(List<E> elements) {
		this.checkElements(elements);
		Iterator<E> setElements = elements.iterator();
		for (ListIterator<E> iterator : this.iterators) {
			iterator.set(setElements.next());
		}
	}

	private void checkElements(List<E> elements) {
		if (elements.size() != this.iteratorsSize()) {
			throw new IllegalArgumentException("invalid elements: " + elements); //$NON-NLS-1$
		}
	}

	private int iteratorsSize() {
		return IterableTools.size(this.iterators);
	}
}
