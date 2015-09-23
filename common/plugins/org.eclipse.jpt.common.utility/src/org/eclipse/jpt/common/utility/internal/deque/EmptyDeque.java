/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.io.Serializable;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;

/**
 * Empty implementation of the {@link Deque} interface.
 * @param <E> the type of elements maintained by the deque
 * @see DequeTools
 */
public final class EmptyDeque<E>
	implements Deque<E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Deque INSTANCE = new EmptyDeque();
	@SuppressWarnings("unchecked")
	public static <E> Deque<E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private EmptyDeque() {
		super();
	}

	public void enqueueTail(E o) {
		throw new UnsupportedOperationException();
	}

	public void enqueueHead(E o) {
		throw new UnsupportedOperationException();
	}

	public E dequeueHead() {
		throw new NoSuchElementException();
	}

	public E dequeueTail() {
		throw new NoSuchElementException();
	}

	public E peekHead() {
		throw new NoSuchElementException();
	}

	public E peekTail() {
		throw new NoSuchElementException();
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
