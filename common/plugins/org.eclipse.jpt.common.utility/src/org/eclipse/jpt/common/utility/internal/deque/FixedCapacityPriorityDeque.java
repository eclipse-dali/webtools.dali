/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.deque.InputRestrictedDeque;

/**
 * Fixed capacity priority implementation of the {@link InputRestrictedDeque} interface.
 * Elements will dequeue from the deque's head in the order determined by a comparator
 * (i.e. {@link #dequeueHead} will return the element sorted first
 * while {@link #dequeueTail} will return the element sorted last).
 * @param <E> the type of elements maintained by the deque
 * @see PriorityDeque
 * @see DequeTools
 */
public class FixedCapacityPriorityDeque<E>
	extends AbstractPriorityDeque<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty, fixed-capacity priority deque with the specified comparator
	 * and capacity.
	 */
	public FixedCapacityPriorityDeque(Comparator<? super E> comparator, int capacity) {
		super(comparator, capacity);
	}


	// ********** Deque implementation **********

	/**
	 * @exception IllegalStateException if the deque is full
	 */
	@Override
	public void enqueue(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Deque is full."); //$NON-NLS-1$
		}
		super.enqueue(element);
	}

	/**
	 * Return whether the deque is full,
	 * as its capacity is fixed.
	 */
	public boolean isFull() {
		return this.size == this.elements.length - 1;
	}


	// ********** standard methods **********

	@Override
	public FixedCapacityPriorityDeque<E> clone() {
		return (FixedCapacityPriorityDeque<E>) super.clone();
	}
}
