/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
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
import java.util.Collection;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.collection.Queue;

/**
 * Resizable-array FIFO implementation of the {@link Queue} interface.
 * @param <E> the type of elements maintained by the queue
 * @see FixedSizeArrayQueue
 */
public class ArrayQueue<E>
	implements Queue<E>, Cloneable, Serializable
{
	private transient E[] elements;

	/** Index of next element to be "dequeued". */
	private transient int head = 0;

	/** Index of next element to be "enqueued". */
	private transient int tail = 0;

	private int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue.
	 */
	public ArrayQueue() {
		this(10);
	}

	/**
	 * Construct an empty queue with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public ArrayQueue(int initialCapacity) {
		super();
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[initialCapacity];
	}

	/**
	 * Construct a queue containing the elements of the specified
	 * collection. The queue will dequeue its elements in the same
	 * order they are returned by the collection's iterator (i.e. the
	 * first element returned by the collection's iterator will be the
	 * first element returned by {@link #dequeue()}).
	 */
	@SuppressWarnings("unchecked")
	public ArrayQueue(Collection<? extends E> c) {
		super();
		this.size = c.size();
		// add 10% for growth
		int capacity = (int) Math.min((this.size * 110L) / 100, Integer.MAX_VALUE);
		this.elements = (E[]) c.toArray(new Object[capacity]);
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		this.ensureCapacity(this.size + 1);
		this.elements[this.tail] = element;
		if (++this.tail == this.elements.length) {
			this.tail = 0;
		}
		this.size++;
	}

	/**
	 * Increase the queue's capacity, if necessary, to ensure it has at least
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
	 * Decrease the queue's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		if (this.elements.length > this.size) {
			this.elements = this.copyElements(this.size);
			this.head = 0;
			this.tail = this.size;
		}
	}

	private E[] copyElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[]) new Object[newCapacity];
		if (this.size != 0) {
			Object oldElements[] = this.elements;
			if ((this.head == 0) || (this.head < this.tail) || (this.tail == 0)) {
				// elements are contiguous
				System.arraycopy(oldElements, this.head, newElements, 0, this.size);
			} else {
				// elements wrap past end of array
				int fragmentSize = oldElements.length - this.head;
				System.arraycopy(oldElements, this.head, newElements, 0, fragmentSize);
				System.arraycopy(oldElements, 0, newElements, fragmentSize, this.size - fragmentSize);
			}
		}
		return newElements;
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


	// ********** standard methods **********

	@Override
	public ArrayQueue<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			ArrayQueue<E> clone = (ArrayQueue<E>) super.clone();
			@SuppressWarnings("cast")
			E[] array = (E[]) this.elements.clone();
			clone.elements = array;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(this.copyElements(this.size));
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write size (and any hidden stuff)
		stream.defaultWriteObject();
		Object[] array = this.elements;
		int elementsLength = array.length;
		stream.writeInt(elementsLength);
		if (this.head < this.tail) { // elements are contiguous
			for (int i = this.head; i < this.tail; i++) {
				stream.writeObject(array[i]);
			}
		} else if (this.head > this.tail) { // elements wrap past end of array
			for (int i = this.head; i < elementsLength; i++) {
				stream.writeObject(array[i]);
			}
			for (int i = 0; i < this.tail; i++) {
				stream.writeObject(array[i]);
			}
		} else { // (this.head == this.tail)
			// nothing to write
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		// read size (and any hidden stuff)
		stream.defaultReadObject();
		int elementsLength = stream.readInt();
		Object[] array = new Object[elementsLength];
		for (int i = 0; i < this.size; i++) {
			array[i] = stream.readObject();
		}
		this.elements = (E[]) array;
		this.head = 0;
		this.tail = this.size;
	}
}
