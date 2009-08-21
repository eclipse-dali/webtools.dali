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

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>SingleElementIterator</code> holds a single element
 * and returns it with the first call to {@link next()}, at
 * which point it will return <code>false</code> to any subsequent
 * call to {@link #hasNext()}.
 * <p>
 * A <code>SingleElementIterator</code> is equivalent to the
 * {@link Iterator} returned by:
 * 	{@link java.util.Collections#singleton(element)}<code>.iterator()</code>
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class SingleElementIterator<E>
	implements Iterator<E>
{
	private final E element;
	private boolean done;


	/**
	 * Construct an iterator that returns only the specified element.
	 */
	public SingleElementIterator(E element) {
		super();
		this.element = element;
		this.done = false;
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		this.done = true;
		return this.element;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.element);
	}

}
