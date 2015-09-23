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

import org.eclipse.jpt.common.utility.deque.Deque;

/**
 * Fixed-capacity array implementation of the {@link Deque} interface.
 * This implementation will throw an exception if its capacity is exceeded.
 * @param <E> the type of elements maintained by the queue
 * @see ArrayDeque
 * @see DequeTools
 */
public class FixedCapacityArrayDeque<E>
	extends AbstractArrayDeque<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty deque with the specified capacity.
	 */
	public FixedCapacityArrayDeque(int capacity) {
		super(capacity);
	}


	// ********** Deque implementation **********

	/**
	 * @exception IllegalStateException if the deque is full
	 */
	@Override
	public void enqueueTail(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Deque is full."); //$NON-NLS-1$
		}
		super.enqueueTail(element);
	}

	/**
	 * @exception IllegalStateException if the deque is full
	 */
	@Override
	public void enqueueHead(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Deque is full."); //$NON-NLS-1$
		}
		super.enqueueHead(element);
	}

	/**
	 * Return whether the deque is full,
	 * as its capacity is fixed.
	 */
	public boolean isFull() {
		return this.size == this.elements.length;
	}


	// ********** standard methods **********

	@Override
	public FixedCapacityArrayDeque<E> clone() {
		return (FixedCapacityArrayDeque<E>) super.clone();
	}
}
