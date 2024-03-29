/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.SuperListIteratorWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Wrap a list iterable of elements of any sub-type of <code>E</code>,
 * converting it into a <em>non-writable</em> list iterable of elements
 * of type <code>E</code>.
 * This shouldn't be a problem since the resulting list iterable's list
 * iterator disables the methods that would put invalid elements in the list
 * iterator's backing list (i.e. {@link SuperListIteratorWrapper#set(Object)}
 * and {@link SuperListIteratorWrapper#add(Object)}).
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 * 
 * @see SuperListIteratorWrapper
 */
public class SuperListIterableWrapper<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends E> iterable;


	public SuperListIterableWrapper(ListIterable<? extends E> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public ListIterator<E> iterator() {
		return new SuperListIteratorWrapper<E>(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return this.iterable.toString();
	}
}
