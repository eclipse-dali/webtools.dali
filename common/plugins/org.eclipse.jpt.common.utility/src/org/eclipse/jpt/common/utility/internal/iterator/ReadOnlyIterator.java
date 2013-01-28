/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>ReadOnlyIterator</code> wraps another {@link Iterator}
 * and removes support for {@link #remove()}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.ReadOnlyIterable
 */
public class ReadOnlyIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;

	/**
	 * Construct an iterator with the specified nested iterator
	 * and disallow removes.
	 */
	public ReadOnlyIterator(Iterator<? extends E> iterator) {
		super();
		if (iterator == null) {
			throw new NullPointerException();
		}
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
		return ObjectTools.toString(this, this.iterator);
	}
}
