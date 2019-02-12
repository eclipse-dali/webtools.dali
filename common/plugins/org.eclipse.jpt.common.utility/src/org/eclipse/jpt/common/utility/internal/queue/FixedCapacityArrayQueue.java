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

import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Fixed-capacity array FIFO implementation of the {@link Queue} interface.
 * This implementation will throw an exception if its capacity is exceeded.
 * @param <E> the type of elements maintained by the queue
 * @see ArrayQueue
 * @see QueueTools
 */
public class FixedCapacityArrayQueue<E>
	extends AbstractArrayQueue<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue with the specified capacity.
	 */
	public FixedCapacityArrayQueue(int capacity) {
		super(capacity);
	}


	// ********** Queue implementation **********

	/**
	 * @exception IllegalStateException if the queue is full
	 */
	@Override
	public void enqueue(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Queue is full."); //$NON-NLS-1$
		}
		super.enqueue(element);
	}

	/**
	 * Return whether the queue is full,
	 * as its capacity is fixed.
	 */
	public boolean isFull() {
		return this.size == this.elements.length;
	}


	// ********** standard methods **********

	@Override
	public FixedCapacityArrayQueue<E> clone() {
		return (FixedCapacityArrayQueue<E>) super.clone();
	}
}
