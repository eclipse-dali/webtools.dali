/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
 * Wrap an iterator on elements of any sub-type of <code>E</code>, converting
 * it into a iterator on elements of type <code>E</code>. This shouldn't be a
 * problem since there is no way to add invalid elements to the iterator's
 * backing collection. (Note the lack of compiler warnings, suppressed or
 * otherwise.)
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper
 */
public class SuperIteratorWrapper<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;


	public SuperIteratorWrapper(Iterator<? extends E> iterator) {
		super();
		if (iterator == null) {
			throw new NullPointerException();
		}
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public E next() {
		return this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterator);
	}
}
