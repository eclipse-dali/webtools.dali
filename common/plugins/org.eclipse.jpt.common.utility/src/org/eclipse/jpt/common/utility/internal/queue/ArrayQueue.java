/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Resizable-array FIFO implementation of the {@link Queue} interface.
 * @param <E> the type of elements maintained by the queue
 * @see FixedCapacityArrayQueue
 * @see QueueTools
 */
public class ArrayQueue<E>
	extends AbstractArrayQueue<E>
{
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue with the specified initial capacity.
	 */
	public ArrayQueue(int initialCapacity) {
		super(initialCapacity);
	}


	// ********** Queue implementation **********

	@Override
	public void enqueue(E element) {
		this.ensureCapacity(this.size + 1);
		super.enqueue(element);
	}

	/**
	 * Increase the queue's capacity, if necessary, to ensure it has at least
	 * the specified minimum capacity.
	 */
	public void ensureCapacity(int minCapacity) {
		if (this.elements.length < minCapacity) {
			int newCapacity = ((this.elements.length * 3) >> 1) + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elements = this.copyElements(newCapacity);
			this.head = 0;
			this.tail = this.size;
		}
	}

	/**
	 * Decrease the queue's capacity, if necessary, to match its current size.
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
	public ArrayQueue<E> clone() {
		return (ArrayQueue<E>) super.clone();
	}
}
