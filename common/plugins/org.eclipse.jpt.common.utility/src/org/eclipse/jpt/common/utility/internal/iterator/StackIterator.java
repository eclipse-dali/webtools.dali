/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * A <code>StackIterator</code> provides an {@link Iterator}
 * for a {@link Stack} of objects of type <code>E</code>. The stack's elements
 * are {@link Stack#pop() pop}ped" as the iterator returns them with
 * calls to {@link #next()}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see Stack
 * @see org.eclipse.jpt.common.utility.internal.iterable.StackIterable
 */
public class StackIterator<E>
	implements Iterator<E>
{
	private final Stack<? extends E> stack;


	/**
	 * Construct an iterator for the specified stack.
	 */
	public StackIterator(Stack<? extends E> stack) {
		super();
		this.stack = stack;
	}

	public boolean hasNext() {
		return ! this.stack.isEmpty();
	}

	public E next() {
		return this.stack.pop();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.stack);
	}
}
