/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Adapt a {@link Deque} to the {@link Stack} interface.
 * Elements are pushed to and popped from the head of the deque.
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class DequeStack<E>
	implements Stack<E>, Serializable
{
	private Deque<E> deque;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a stack, adapting the specified deque.
	 * Elements are pushed to and popped from the head of the deque.
	 */
	public DequeStack(Deque<E> deque) {
		super();
		this.deque = deque;
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.deque.enqueueHead(element);
	}

	public E pop() {
		try {
			return this.deque.dequeueHead();
		} catch (NoSuchElementException ex) {
			throw new EmptyStackException();
		}
	}

	public E peek() {
		try {
			return this.deque.peekHead();
		} catch (NoSuchElementException ex) {
			throw new EmptyStackException();
		}
	}

	public boolean isEmpty() {
		return this.deque.isEmpty();
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return this.deque.toString();
	}
}
