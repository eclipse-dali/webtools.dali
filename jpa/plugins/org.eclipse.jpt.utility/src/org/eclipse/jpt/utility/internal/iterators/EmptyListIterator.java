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

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>NullListIterator</code> is just that.
 */
public final class EmptyListIterator<E>
	implements ListIterator<E>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static EmptyListIterator INSTANCE = new EmptyListIterator();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ListIterator<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyListIterator() {
		super();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return false;
	}

	public boolean hasPrevious() {
		return false;
	}

	public E next() {
		throw new NoSuchElementException();
	}

	public int nextIndex() {
		return 0;
	}

	public E previous() {
		throw new NoSuchElementException();
	}

	public int previousIndex() {
		return -1;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public void set(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
	
}
