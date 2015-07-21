/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Fixed-sized array FIFO implementation of the {@link Queue} interface.
 * This implementation will throw an exception if its capacity is exceeded.
 * @param <E> the type of elements maintained by the queue
 * @see ArrayQueue
 * @see QueueTools
 */
public class FixedSizeArrayQueue<E>
	implements Queue<E>, Cloneable, Serializable
{
	private final E[] elements;

	/** Index of next element to be "dequeued". */
	private int head = 0;

	/** Index of next element to be "enqueued". */
	private int tail = 0;

	private int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue with the specified capacity.
	 */
	@SuppressWarnings("unchecked")
	public FixedSizeArrayQueue(int capacity) {
		super();
		if (capacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + capacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[capacity];
	}


	// ********** Queue implementation **********

	/**
	 * @exception IllegalStateException if the queue is full
	 */
	public void enqueue(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Queue is full."); //$NON-NLS-1$
		}
		this.elements[this.tail] = element;
		if (++this.tail == this.elements.length) {
			this.tail = 0;
		}
		this.size++;
	}

	public E dequeue() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		E element = this.elements[this.head];
		this.elements[this.head] = null; // allow GC to work
		if (++this.head == this.elements.length) {
			this.head = 0;
		}
		this.size--;
		return element;
	}

	public E peek() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.elements[this.head];
	}

	public boolean isEmpty() {
		return this.size == 0;
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
	public FixedSizeArrayQueue<E> clone() {
		int len = this.elements.length;
		FixedSizeArrayQueue<E> clone = new FixedSizeArrayQueue<E>(len);
		System.arraycopy(this.elements, 0, clone.elements, 0, len);
		clone.head = this.head;
		clone.tail = this.tail;
		clone.size = this.size;
		return clone;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.copyElements());
	}

	private Object[] copyElements() {
		if (this.size == 0) {
			return ObjectTools.EMPTY_OBJECT_ARRAY;
		}
		Object[] result = new Object[this.size];
		if ((this.head == 0) || (this.head < this.tail) || (this.tail == 0)) {
			// elements are contiguous
			System.arraycopy(this.elements, this.head, result, 0, this.size);
		} else {
			// elements wrap past end of array
			int fragmentSize = this.elements.length - this.head;
			System.arraycopy(this.elements, this.head, result, 0, fragmentSize);
			System.arraycopy(this.elements, 0, result, fragmentSize, (this.size - fragmentSize));
		}
		return result;
	}
}
