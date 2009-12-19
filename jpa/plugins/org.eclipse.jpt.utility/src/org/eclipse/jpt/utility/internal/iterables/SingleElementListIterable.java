/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * A <code>SingleElementListIterable</code> returns a {@link ListIterator}
 * that holds a single element
 * and returns it with the first call to {@link ListIterator#next()}, at
 * which point it will return <code>false</code> to any subsequent
 * call to {@link ListIterator#hasNext()}. Likewise, it will return <code>false</code>
 * to a call to {@link ListIterator#hasPrevious()} until a call to {@link ListIterator#next()},
 * at which point a call to {@link ListIterator#previous()} will return the
 * single element.
 * <p>
 * A <code>SingleElementListIterable</code> is equivalent to the
 * {@link Iterable} returned by:
 * 	{@link java.util.Collections#singletonList(Object)}.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see SingleElementListIterator
 * @see SingleElementIterable
 */
public class SingleElementListIterable<E>
	implements ListIterable<E>
{
	private final E element;

	/**
	 * Construct a list iterable that contains only the specified element.
	 */
	public SingleElementListIterable(E element) {
		super();
		this.element = element;
	}

	public ListIterator<E> iterator() {
		return new SingleElementListIterator<E>(this.element);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.element);
	}

}
