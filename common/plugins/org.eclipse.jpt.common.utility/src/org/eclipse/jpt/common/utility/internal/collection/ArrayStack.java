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
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * Resizable-array LIFO implementation of the {@link Stack} interface.
 * @param <E> the type of elements maintained by the stack
 * @see FixedSizeArrayStack
 */
public class ArrayStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private transient E[] elements;

	/** The index of where the next "pushed" element will go. */
	private transient int next = 0;

	private int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty stack.
	 */
	public ArrayStack() {
		this(10);
	}

	/**
	 * Construct an empty stack with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(int initialCapacity) {
		super();
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + initialCapacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[initialCapacity];
	}

	/**
	 * Construct a stack containing the elements of the specified
	 * collection. The stack will pop its elements in reverse of the
	 * order they are returned by the collection's iterator (i.e. the
	 * last element returned by the collection's iterator will be the
	 * first element returned by {@link #pop()}; the first, last.).
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(Collection<? extends E> c) {
		super();
		int len = c.size();
		// add 10% for growth
		int capacity = (int) Math.min((len * 110L) / 100, Integer.MAX_VALUE);
		this.elements = (E[]) c.toArray(new Object[capacity]);
		this.next = len;
		this.size = len;
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.ensureCapacity(this.size + 1);
		this.elements[this.next] = element;
		if (++this.next == this.elements.length) {
			this.next = 0;
		}
		this.size++;
	}

	/**
	 * Increase the stack's capacity, if necessary, to ensure it has at least
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
			this.next = this.size;
		}
	}

	/**
	 * Decrease the stack's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		if (this.elements.length > this.size) {
			this.elements = this.copyElements(this.size);
			this.next = this.size;
		}
	}

	private E[] copyElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[]) new Object[newCapacity];
		int len = this.size;
		if (len != 0) {
			System.arraycopy(this.elements, 0, newElements, 0, len);
		}
		return newElements;
	}

	public E pop() {
		if (this.size == 0) {
			throw new EmptyStackException();
		}
		int index = this.next;
		if (index == 0) {
			index = this.elements.length;
		}
		index--;
		E element = this.elements[index];
		this.elements[index] = null; // allow GC to work
		this.next = index;
		this.size--;
		return element;
	}

	public E peek() {
		if (this.size == 0) {
			throw new EmptyStackException();
		}
		int index = this.next;
		if (index == 0) {
			index = this.elements.length;
		}
		index--;
		return this.elements[index];
	}

	public boolean isEmpty() {
		return this.size == 0;
	}


	// ********** standard methods **********

	@Override
	public ArrayStack<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			ArrayStack<E> clone = (ArrayStack<E>) super.clone();
			@SuppressWarnings("cast")
			E[] array = (E[]) this.elements.clone();
			clone.elements = array;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	/**
	 * Print the elements in the order in which they are "pushed" on to
	 * the stack (as opposed to the order in which they will be "popped"
	 * off of the stack).
	 */
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
		if (this.size == 0) {
			return;
		}
		// save the elements in contiguous order
		if (this.next >= this.size) { // elements are contiguous
			for (int i = (this.next - this.size); i < this.next; i++) {
				stream.writeObject(array[i]);
			}
		} else { // (this.next < this.size) - elements wrap past end of array
			for (int i = (elementsLength - (this.size - this.next)); i < elementsLength; i++) {
				stream.writeObject(array[i]);
			}
			for (int i = 0; i < this.next; i++) {
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
		this.next = this.size;
	}
}
