/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.SuperListIteratorWrapper;

/**
 * Wrap a list iterable of elements of any sub-type of <code>E</code>,
 * converting it into a list iterable of elements of type <code>E</code>.
 * This shouldn't be a problem since the resulting list iterable's list
 * iterator disables the methods that would put invalid elements in the list
 * iterator's backing list (i.e. {@link SuperListIteratorWrapper#set(Object)}
 * and {@link SuperListIteratorWrapper#add(Object)}).
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 * 
 * @see org.eclipse.jpt.utility.internal.iterators.SuperListIteratorWrapper
 */
public class SuperListIterableWrapper<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends E> iterable;


	public <T extends E> SuperListIterableWrapper(List<T> list) {
		this(new ListListIterable<T>(list));
	}

	public SuperListIterableWrapper(ListIterable<? extends E> iterable) {
		super();
		this.iterable = iterable;
	}

	public ListIterator<E> iterator() {
		return new SuperListIteratorWrapper<E>(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}

}
