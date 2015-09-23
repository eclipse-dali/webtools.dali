/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.io.Serializable;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Empty implementation of the {@link Queue} interface.
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public final class EmptyQueue<E>
	implements Queue<E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Queue INSTANCE = new EmptyQueue();
	@SuppressWarnings("unchecked")
	public static <E> Queue<E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private EmptyQueue() {
		super();
	}

	public void enqueue(E o) {
		throw new UnsupportedOperationException();
	}

	public E dequeue() {
		throw new NoSuchElementException();
	}

	public E peek() {
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
