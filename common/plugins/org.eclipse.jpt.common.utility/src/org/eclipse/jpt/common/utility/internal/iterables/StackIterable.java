/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.Stack;
import org.eclipse.jpt.common.utility.internal.iterators.StackIterator;

/**
 * A <code>StackIterable</code> provides an {@link Iterable}
 * for a {@link Stack} of objects of type <code>E</code>. The stack's elements
 * are {@link Stack#pop() "popped"} as the iterable's iterator returns
 * them with calls to {@link Iterator#next()}.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see Stack
 * @see StackIterator
 */
public class StackIterable<E>
	implements Iterable<E>
{
	private final Stack<E> stack;

	/**
	 * Construct an iterable for the specified stack.
	 */
	public StackIterable(Stack<E> stack) {
		super();
		if (stack == null) {
			throw new NullPointerException();
		}
		this.stack = stack;
	}

	public Iterator<E> iterator() {
		return new StackIterator<E>(this.stack);
	}

	@Override
	public String toString() {
		return this.stack.toString();
	}
}
