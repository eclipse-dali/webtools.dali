/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.util.EmptyStackException;

/**
 * Interface defining the classic stack behavior,
 * without the backdoors allowed by java.util.Stack.
 * E is the type of elements contained by the Stack.
 */
public interface Stack<E> {

	/**
	 * "Push" the specified item on to the top of the stack.
	 */
	void push(E o);

	/**
	 * "Pop" an item from the top of the stack.
	 */
	E pop();

	/**
	 * Return the item on the top of the stack
	 * without removing it from the stack.
	 */
	E peek();

	/**
	 * Return whether the stack is empty.
	 */
	boolean isEmpty();


	final class Empty<E> implements Stack<E>, Serializable {
		@SuppressWarnings("unchecked")
		public static final Stack INSTANCE = new Empty();
		@SuppressWarnings("unchecked")
		public static <T> Stack<T> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		public void push(E o) {
			throw new UnsupportedOperationException();
		}
		public E pop() {
			throw new EmptyStackException();
		}
		public E peek() {
			throw new EmptyStackException();
		}
		public boolean isEmpty() {
			return true;
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
