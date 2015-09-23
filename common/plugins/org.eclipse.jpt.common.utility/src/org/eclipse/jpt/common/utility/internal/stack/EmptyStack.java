/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.io.Serializable;
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Empty implementation of the {@link Stack} interface.
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public final class EmptyStack<E>
	implements Stack<E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Stack INSTANCE = new EmptyStack();
	@SuppressWarnings("unchecked")
	public static <E> Stack<E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private EmptyStack() {
		super();
	}

	public void push(E element) {
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

	@Override
	public String toString() {
		return "[]"; //$NON-NLS-1$
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
