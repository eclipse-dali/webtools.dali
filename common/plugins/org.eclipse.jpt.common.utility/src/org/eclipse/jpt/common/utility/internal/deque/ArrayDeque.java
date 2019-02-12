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

import org.eclipse.jpt.common.utility.deque.Deque;

/**
 * Resizable-array implementation of the {@link Deque} interface.
 * @param <E> the type of elements maintained by the deque
 * @see FixedCapacityArrayDeque
 * @see DequeTools
 */
public class ArrayDeque<E>
	extends AbstractArrayDeque<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty deque with the specified initial capacity.
	 */
	public ArrayDeque(int initialCapacity) {
		super(initialCapacity);
	}


	// ********** Deque implementation **********

	@Override
	public void enqueueTail(E element) {
		this.ensureCapacity(this.size + 1);
		super.enqueueTail(element);
	}

	@Override
	public void enqueueHead(E element) {
		this.ensureCapacity(this.size + 1);
		super.enqueueHead(element);
	}

	/**
	 * Increase the deque's capacity, if necessary, to ensure it has at least
	 * the specified minimum capacity.
	 */
	public void ensureCapacity(int minCapacity) {
		int oldCapacity = this.elements.length;
		if (oldCapacity < minCapacity) {
			int newCapacity = ((oldCapacity * 3) >> 1) + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elements = this.copyElements(newCapacity);
			this.head = 0;
			this.tail = this.size;
		}
	}

	/**
	 * Decrease the deque's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		if (this.elements.length > this.size) {
			this.elements = this.copyElements(this.size);
			this.head = 0;
			this.tail = this.size;
		}
	}


	// ********** standard methods **********

	@Override
	public ArrayDeque<E> clone() {
		return (ArrayDeque<E>) super.clone();
	}
}
