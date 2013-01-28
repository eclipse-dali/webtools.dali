/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;

/**
 * A <code>ReverseIterator</code> wraps another {@link Iterator} and returns
 * its elements in the reverse order in which the wrapped {@link Iterator}
 * returns the elements.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class ReverseIterator<E>
	implements Iterator<E>
{
	/**
	 * The elements in this iterator are already reversed.
	 */
	private final Iterator<E> iterator;


	/**
	 * Construct a reverse iterator for the specified iterator.
	 */
	public ReverseIterator(Iterator<? extends E> iterator) {
		this(ListTools.reverse(ListTools.list(iterator)));
	}

	/**
	 * Construct a reverse iterator for the specified iterator.
	 */
	public ReverseIterator(Iterator<? extends E> iterator, int size) {
		this(ListTools.reverse(ListTools.list(iterator, size)));
	}

	private ReverseIterator(ArrayList<E> reverseList) {
		super();
		this.iterator = reverseList.iterator();
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public E next() {
		return this.iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
