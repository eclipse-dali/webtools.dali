/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.deque.InputRestrictedDeque;

/**
 * Resizable priority implementation of the {@link InputRestrictedDeque} interface.
 * Elements will dequeue from the deque's head in the order determined by a comparator
 * (i.e. {@link #dequeueHead} will return the element sorted first
 * while {@link #dequeueTail} will return the element sorted last).
 * @param <E> the type of elements maintained by the deque
 * @see FixedCapacityPriorityDeque
 * @see DequeTools
 */
public class PriorityDeque<E>
	extends AbstractPriorityDeque<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty priority deque with the specified comparator
	 * and initial capacity.
	 */
	public PriorityDeque(Comparator<? super E> comparator, int initialCapacity) {
		super(comparator, initialCapacity);
	}


	// ********** Deque implementation **********

	@Override
	public void enqueue(E element) {
		this.ensureCapacity(this.size + 1);
		super.enqueue(element);
	}

	/**
	 * Increase the deque's capacity, if necessary, to ensure it has at least
	 * the specified minimum capacity.
	 */
	public void ensureCapacity(int minCapacity) {
		int oldCapacity = this.elements.length - 1;
		if (oldCapacity < minCapacity) {
			int newCapacity = ((oldCapacity * 3) >> 1) + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elements = this.copyElements(newCapacity);
		}
	}

	/**
	 * Decrease the deque's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		if (this.elements.length > this.size + 1) {
			this.elements = this.copyElements(this.size);
		}
	}

	private E[] copyElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[]) new Object[newCapacity + 1];
		System.arraycopy(this.elements, 1, newElements, 1, this.size); // skip 0
		return newElements;
	}


	// ********** standard methods **********

	@Override
	public PriorityDeque<E> clone() {
		return (PriorityDeque<E>) super.clone();
	}
}
