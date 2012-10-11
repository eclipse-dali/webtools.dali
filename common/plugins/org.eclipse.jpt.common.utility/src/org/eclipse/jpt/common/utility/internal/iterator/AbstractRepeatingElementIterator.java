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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * An <code>AbstractRepeatingElementIterator</code> provides an {@link Iterator}
 * that returns a single element a specific number of times.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.collection.AbstractRepeatingElementList
 */
public abstract class AbstractRepeatingElementIterator<E>
	implements Iterator<E>
{
	private final int size;
	/* private-protected */ int cursor;

	/**
	 * Construct an iterator for the specified number of elements.
	 */
	protected AbstractRepeatingElementIterator(int size) {
		super();
		if (size < 0) {
			throw new IllegalArgumentException("size: " + size); //$NON-NLS-1$
		}
		this.size = size;
		this.cursor = 0;
	}

	public boolean hasNext() {
		return this.cursor != this.size;
	}

	public E next() {
		if (this.hasNext()) {
			this.cursor++;
			return this.getElement();
		}
		throw new NoSuchElementException();
	}

	protected abstract E getElement();

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.size);
	}
}
