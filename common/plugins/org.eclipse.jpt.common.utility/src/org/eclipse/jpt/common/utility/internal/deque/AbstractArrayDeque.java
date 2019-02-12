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

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;

/**
 * Abstract array implementation of the {@link Deque} interface.
 * @param <E> the type of elements maintained by the deque
 */
public abstract class AbstractArrayDeque<E>
	implements Deque<E>, Cloneable, Serializable
{
	protected transient E[] elements;

	/** Index of head element */
	protected transient int head = 0;

	/** Index of the <em>next</em> tail element. */
	protected transient int tail = 0;

	protected int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty deque with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractArrayDeque(int initialCapacity) {
		super();
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[initialCapacity];
	}


	// ********** Deque implementation **********

	public void enqueueTail(E element) {
		this.elements[this.tail] = element;
		if (++this.tail == this.elements.length) {
			this.tail = 0;
		}
		this.size++;
	}

	public void enqueueHead(E element) {
		if (this.head == 0) {
			this.head = this.elements.length;
		}
		this.elements[--this.head] = element;
		this.size++;
	}

	public E dequeueHead() {
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

	public E dequeueTail() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		if (this.tail == 0) {
			this.tail = this.elements.length;
		}
		E element = this.elements[--this.tail];
		this.elements[this.tail] = null; // allow GC to work
		this.size--;
		return element;
	}

	public E peekHead() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.elements[this.head];
	}

	public E peekTail() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		int index = (this.tail == 0) ? this.elements.length : this.tail;
		return this.elements[--index];
	}

	public boolean isEmpty() {
		return this.size == 0;
	}


	// ********** standard methods **********

	@Override
	public AbstractArrayDeque<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			AbstractArrayDeque<E> clone = (AbstractArrayDeque<E>) super.clone();
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
			Object oldElements[] = this.elements;
			int t = this.tail;
			if (t == 0) {
				t = oldElements.length;
			}
			if (this.head < t) {
				// elements are contiguous
				System.arraycopy(oldElements, this.head, newElements, 0, this.size);
			} else {
				// elements wrap past end of array
				int fragmentSize = oldElements.length - this.head;
				System.arraycopy(oldElements, this.head, newElements, 0, fragmentSize);
				System.arraycopy(oldElements, 0, newElements, fragmentSize, (this.size - fragmentSize));
			}
		}
		return newElements;
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write size (and any hidden stuff)
		stream.defaultWriteObject();
		Object[] array = this.elements;
		int elementsLength = array.length;
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
				stream.writeObject(array[i]);
			}
		} else { // (this.head >= t) - elements wrap past end of array
			for (int i = this.head; i < elementsLength; i++) {
				stream.writeObject(array[i]);
			}
			for (int i = 0; i < this.tail; i++) {
				stream.writeObject(array[i]);
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
