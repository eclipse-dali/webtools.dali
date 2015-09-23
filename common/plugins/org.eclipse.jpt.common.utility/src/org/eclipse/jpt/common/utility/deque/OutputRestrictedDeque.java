/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.deque;

/**
 * Interface defining a output-restricted double-ended queue.
 * Elements can be added to either end of the queue,
 * but removed from only the head of the queue.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <E> the type of elements contained by the deque
 * @see Deque
 */
public interface OutputRestrictedDeque<E> {

	/**
	 * "Enqueue" the specified item to the head of the queue.
	 */
	void enqueueHead(E element);

	/**
	 * "Enqueue" the specified item to the tail of the deque.
	 */
	void enqueueTail(E element);

	/**
	 * "Dequeue" an item from the head of the deque.
	 */
	E dequeueHead();

	/**
	 * Return the item on the head of the deque
	 * without removing it from the deque.
	 */
	E peekHead();

	/**
	 * Return whether the deque is empty.
	 */
	boolean isEmpty();
}
