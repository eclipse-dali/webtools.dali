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

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>ReadOnlyIterator</code> wraps another <code>Iterator</code>
 * and removes support for #remove().
 */
public class ReadOnlyIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;

	/**
	 * Construct an iterator on the specified collection that
	 * disallows removes.
	 */
	public ReadOnlyIterator(Iterable<? extends E> c) {
		this(c.iterator());
	}

	/**
	 * Construct an iterator with the specified nested iterator
	 * and disallow removes.
	 */
	public ReadOnlyIterator(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.iterator.hasNext();
	}

	public E next() {
		// delegate to the nested iterator
		return this.iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}
	
}
