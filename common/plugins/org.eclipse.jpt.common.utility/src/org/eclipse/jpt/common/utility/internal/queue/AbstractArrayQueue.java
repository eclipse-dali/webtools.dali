/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Abstract array implementation of the {@link Queue} interface.
 * @param <E> the type of elements maintained by the queue
 */
public abstract class AbstractArrayQueue<E>
	implements Queue<E>, Cloneable, Serializable
{
	protected transient E[] elements;

	/** Index of next element to be "dequeued". */
	protected transient int head = 0;

	/** Index of next element to be "enqueued". */
	protected transient int tail = 0;

	protected int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractArrayQueue(int initialCapacity) {
		super();
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[initialCapacity];
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
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


	// ********** standard methods **********

	@Override
	public AbstractArrayQueue<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			AbstractArrayQueue<E> clone = (AbstractArrayQueue<E>) super.clone();
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

	protected E[] copyElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[]) new Object[newCapacity];
		if (this.size != 0) {
			int t = this.tail;
			if (t == 0) {
				t = this.elements.length;
			}
			if (this.head < t) {
				// elements are contiguous
				System.arraycopy(this.elements, this.head, newElements, 0, this.size);
			} else {
				// elements wrap past end of array
				int fragmentSize = this.elements.length - this.head;
				System.arraycopy(this.elements, this.head, newElements, 0, fragmentSize);
				System.arraycopy(this.elements, 0, newElements, fragmentSize, (this.size - fragmentSize));
			}
		}
		return newElements;
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write size (and any hidden stuff)
		stream.defaultWriteObject();
		int elementsLength = this.elements.length;
		stream.writeInt(elementsLength);
		if (this.size == 0) {
			return;
		}
		// save the elements in contiguous order
		int t = this.tail;
		if (t == 0) {
			t = elementsLength;
		}
		if (this.head < t) { // elements are contiguous
			for (int i = this.head; i < t; i++) {
				stream.writeObject(this.elements[i]);
			}
		} else { // (this.head >= t) - elements wrap past end of array
			for (int i = this.head; i < elementsLength; i++) {
				stream.writeObject(this.elements[i]);
			}
			for (int i = 0; i < this.tail; i++) {
				stream.writeObject(this.elements[i]);
			}
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
