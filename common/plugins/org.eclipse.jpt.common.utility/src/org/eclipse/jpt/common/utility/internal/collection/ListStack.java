/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.List;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * Adapt a {@link List} to the {@link Stack} interface.
 * Elements are popped from the end of the list (i.e. index size - 1).
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class ListStack<E>
	implements Stack<E>, Serializable
{
	private List<E> list;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a stack, adapting the specified list.
	 * The stack will pop its elements in the reverse
	 * order they are returned by the list's iterator (i.e. the
	 * last element returned by the list's iterator will be the
	 * first element returned by {@link #pop()}).
	 */
	public ListStack(List<E> list) {
		super();
		this.list = list;
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.list.add(element);
	}

	public E pop() {
		int size = this.list.size();
		if (size == 0) {
			throw new EmptyStackException();
		}
		return this.list.remove(size - 1);
	}

	public E peek() {
		int size = this.list.size();
		if (size == 0) {
			throw new EmptyStackException();
		}
		return this.list.get(size - 1);
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return this.list.toString();
	}
}
