/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Resizable priority implementation of the {@link Queue} interface.
 * Elements will dequeue in the order determined by a comparator
 * (i.e. {@link #dequeue} will return the element sorted first).
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public class PriorityQueue<E>
	implements Queue<E>, Cloneable, Serializable
{
	private final Comparator<? super E> comparator;

	/**
	 * Standard heap implementation.
	 * To simplify our math, we leave the first slot [0] empty.
	 */
	private transient E[] elements;

	private int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty priority queue with the specified comparator
	 * and initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue(Comparator<? super E> comparator, int initialCapacity) {
		super();
		if (comparator == null) {
			throw new NullPointerException();
		}
		this.comparator = comparator;
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		
		this.elements = (E[]) new Object[initialCapacity + 1];
	}

	/**
	 * Construct a priority queue with the specified comparator,
	 * initial array of elements, and size. The array of elements
	 * should hold elements at contiguous indices from
	 * 1 to the the specified size, inclusive. The array should <em>not</em>
	 * hold an element at index 0. The queue will <em>not</em> copy the
	 * elements from the supplied array; i.e. the queue will directly use
	 * and manipulate the supplied array.
	 */
	public PriorityQueue(Comparator<? super E> comparator, E[] initialElements, int size) {
		super();
		if (comparator == null) {
			throw new NullPointerException();
		}
		this.comparator = comparator;

		if (initialElements == null) {
			throw new NullPointerException();
		}
		if (initialElements.length == 0) {
			throw new IllegalArgumentException("Initial elements array must have a length of at least 1."); //$NON-NLS-1$
		}
		this.elements = initialElements;

		if (size < 0) {
			throw new IllegalArgumentException("Illegal size: " + size); //$NON-NLS-1$
		}
		this.size = size;

		if (this.size > 1) {
			// this is similar to what we do when an element is dequeued
			int nonLeafMax = this.size >> 1;
			for (int i = nonLeafMax; i > 0; i--) { // we need move only non-leaf nodes
				int current = i;
				do {
					int child = current << 1; // left child
					if ((child < this.size) && (this.comparator.compare(this.elements[child + 1], this.elements[child]) < 0)) {
						// right child exists and is less than left (i.e. dequeues first)
						child++;
					}
					if (this.comparator.compare(this.elements[current], this.elements[child]) < 0) {
						break;
					}
					ArrayTools.swap(this.elements, current, child);
					current = child;
				} while (current <= nonLeafMax); // i.e. not a leaf (i.e. has at least one child)
			}
		}
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		this.ensureCapacity(this.size + 1);
		this.size++;
		int current = this.size;
		this.elements[current] = element;
		int parent = current >> 1;
		while ((parent != 0) && this.comparator.compare(this.elements[current], this.elements[parent]) < 0) {
			ArrayTools.swap(this.elements, current, parent);
			current = parent;
			parent = current >> 1;
		}
	}

	/**
	 * Increase the queue's capacity, if necessary, to ensure it has at least
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
	 * Decrease the queue's capacity, if necessary, to match its current size.
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

	public E dequeue() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		E element = this.elements[1];
		if (this.size != 1) {
			// replace root with last node and move it to its new position
			ArrayTools.swap(this.elements, 1, this.size);
			int newSize = this.size - 1;
			int nonLeafMax = newSize >> 1;
			int current = 1;
			while (current <= nonLeafMax) { // i.e. not a leaf (i.e. has at least one child)
				int child = current << 1; // left child
				if ((child < newSize) && (this.comparator.compare(this.elements[child + 1], this.elements[child]) < 0)) {
					// right child exists and is less than left (i.e. dequeues first)
					child++;
				}
				if (this.comparator.compare(this.elements[current], this.elements[child]) < 0) {
					break;
				}
				ArrayTools.swap(this.elements, current, child);
				current = child;
			}
		}
		this.elements[this.size] = null; // allow GC to work
		this.size--;
		return element;
	}

	public E peek() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		return this.elements[1];
	}

	public boolean isEmpty() {
		return this.size == 0;
	}


	// ********** standard methods **********

	@Override
	public PriorityQueue<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			PriorityQueue<E> clone = (PriorityQueue<E>) super.clone();
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
		return Arrays.toString(ArrayTools.subArray(this.elements, 1, this.size + 1));
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write comparator and size (and any hidden stuff)
		stream.defaultWriteObject();
		Object[] array = this.elements;
		int elementsLength = array.length;
		stream.writeInt(elementsLength);
		if (this.size == 0) {
			return;
		}
		for (int i = 1; i <= this.size; i++) { // skip 0
			stream.writeObject(array[i]);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		// read comparator and size (and any hidden stuff)
		stream.defaultReadObject();
		int elementsLength = stream.readInt();
		Object[] array = new Object[elementsLength];
		for (int i = 1; i <= this.size; i++) { // skip 0
			array[i] = stream.readObject();
		}
		this.elements = (E[]) array;
	}
}
