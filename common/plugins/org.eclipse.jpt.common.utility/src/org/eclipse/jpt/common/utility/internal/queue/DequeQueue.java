/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Adapt a {@link Deque} to the {@link Queue} interface.
 * Elements are dequeued from the head of the deque and
 * enqueued to the tail of the deque.
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public class DequeQueue<E>
	implements Queue<E>, Serializable
{
	private Deque<E> deque;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a queue, adapting the specified deque.
	 * Elements are dequeued from the head of the deque and
	 * enqueued to the tail of the deque.
	 */
	public DequeQueue(Deque<E> deque) {
		super();
		this.deque = deque;
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		this.deque.enqueueTail(element);
	}

	public E dequeue() {
		return this.deque.dequeueHead();
	}

	public E peek() {
		return this.deque.peekHead();
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
