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
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

public abstract class AbstractSimultaneousIterator<E, I extends Iterator<E>>
	implements Iterator<List<E>>
{
	/* private- */ protected final Iterable<? extends I> iterators;
	/* private- */ protected final int iteratorsSize;  // hint


	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	protected <T extends I> AbstractSimultaneousIterator(T... iterators) {
		this(new ArrayIterable<I>(iterators), iterators.length);
	}

	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 */
	protected <T extends I> AbstractSimultaneousIterator(Iterable<T> iterators) {
		this(iterators, -1);
	}

	/**
	 * Construct a "simultaneous" iterator for the specified iterators.
	 * Use the specified size as a performance hint.
	 */
	protected <T extends I> AbstractSimultaneousIterator(Iterable<T> iterators, int iteratorsSize) {
		super();
		if (iterators == null) {
			throw new NullPointerException();
		}
		this.iterators = iterators;
		this.iteratorsSize = iteratorsSize;
	}

	public boolean hasNext() {
		if (this.iteratorsIsEmpty()) {
			return false;
		}
		for (I iterator : this.iterators) {
			if ( ! iterator.hasNext()) {
				return false;
			}
		}
		return true;
	}

	public List<E> next() {
		if (this.iteratorsIsEmpty()) {
			throw new NoSuchElementException();
		}
		ArrayList<E> result = this.buildList();
		for (I iterator : this.iterators) {
			result.add(iterator.next());
		}
		return result;
	}

	/* private- */ protected ArrayList<E> buildList() {
		return (this.iteratorsSize < 0) ? new ArrayList<E>() : new ArrayList<E>(this.iteratorsSize);
	}

	public void remove() {
		if (this.iteratorsIsEmpty()) {
			throw new IllegalStateException();
		}
		for (I iterator : this.iterators) {
			iterator.remove();
		}
	}

	/* private- */ protected boolean iteratorsIsEmpty() {
		return IterableTools.isEmpty(this.iterators);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterators);
	}
}
