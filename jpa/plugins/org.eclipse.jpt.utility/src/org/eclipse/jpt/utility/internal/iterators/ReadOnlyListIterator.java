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

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>ReadOnlyListIterator</code> wraps another
 * <code>ListIterator</code> and removes support for:
 * 	#remove()
 * 	#set(Object)
 * 	#add(Object)
 */
public class ReadOnlyListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<? extends E> listIterator;


	/**
	 * Construct an iterator on the specified list that
	 * disallows removes, sets, and adds.
	 */
	public ReadOnlyListIterator(List<? extends E> list) {
		this(list.listIterator());
	}

	/**
	 * Construct an iterator on the specified list iterator that
	 * disallows removes, sets, and adds.
	 */
	public ReadOnlyListIterator(ListIterator<? extends E> listIterator) {
		super();
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
		return StringTools.buildToStringFor(this, this.listIterator);
	}
	
}
