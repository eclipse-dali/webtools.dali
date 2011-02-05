/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementIterator;

/**
 * A <code>SingleElementIterable</code> returns an {@link Iterator}
 * that holds a single element
 * and returns it with the first call to {@link Iterator#next()}, at
 * which point it will return <code>false</code> to any subsequent
 * call to {@link Iterator#hasNext()}.
 * <p>
 * A <code>SingleElementIterable</code> is equivalent to the
 * {@link Iterable} returned by:
 * 	{@link java.util.Collections#singleton(Object)}.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see SingleElementIterator
 * @see SingleElementListIterable
 */
public class SingleElementIterable<E>
	implements Iterable<E>
{
	private final E element;

	/**
	 * Construct an iterable that contains only the specified element.
	 */
	public SingleElementIterable(E element) {
		super();
		this.element = element;
	}

	public Iterator<E> iterator() {
		return new SingleElementIterator<E>(this.element);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.element);
	}

}
