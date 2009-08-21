/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * Wrap an iterator on elements of any sub-type of <code>E</code>, converting it into an
 * iterator on elements of type <code>E</code>. This shouldn't be a problem since there
 * is no way to add invalid elements to the iterator's backing collection.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class GenericIteratorWrapper<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;


	public GenericIteratorWrapper(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	public GenericIteratorWrapper(Iterator<? extends E> iterator) {
		super();
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
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
