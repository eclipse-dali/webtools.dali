/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
	private final ListIterator<? extends E> nestedListIterator;


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
	public ReadOnlyListIterator(ListIterator<? extends E> nestedListIterator) {
		super();
		this.nestedListIterator = nestedListIterator;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.nestedListIterator.hasNext();
	}

	public E next() {
		// delegate to the nested iterator
		return this.nestedListIterator.next();
	}

	public boolean hasPrevious() {
		// delegate to the nested iterator
		return this.nestedListIterator.hasPrevious();
	}

	public E previous() {
		// delegate to the nested iterator
		return this.nestedListIterator.previous();
	}

	public int nextIndex() {
		// delegate to the nested iterator
		return this.nestedListIterator.nextIndex();
	}

	public int previousIndex() {
		// delegate to the nested iterator
		return this.nestedListIterator.previousIndex();
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
		return StringTools.buildToStringFor(this, this.nestedListIterator);
	}
	
}
