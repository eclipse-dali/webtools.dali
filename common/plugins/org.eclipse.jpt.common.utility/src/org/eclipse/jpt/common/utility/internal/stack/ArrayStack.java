/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Resizable-array LIFO implementation of the {@link Stack} interface.
 * @param <E> the type of elements maintained by the stack
 * @see FixedCapacityArrayStack
 * @see StackTools
 */
public class ArrayStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private transient E[] elements;

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


	// ********** Stack implementation **********

	public void push(E element) {
		this.ensureCapacity(this.size + 1);
		this.elements[this.size++] = element;
	}

	/**
	 * Increase the stack's capacity, if necessary, to ensure it has at least
	 * the specified minimum capacity.
	 */
	public void ensureCapacity(int minCapacity) {
		if (this.elements.length < minCapacity) {
			int newCapacity = ((this.elements.length * 3) >> 1) + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elements = this.copyElements(newCapacity);
		}
	}

	/**
	 * Decrease the stack's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		if (this.elements.length > this.size) {
			this.elements = this.copyElements(this.size);
		}
	}

	private E[] copyElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] newElements = (E[]) new Object[newCapacity];
		if (this.size != 0) {
			System.arraycopy(this.elements, 0, newElements, 0, this.size);
		}
		return newElements;
	}

	public E pop() {
		if (this.size == 0) {
			throw new EmptyStackException();
		}
		int index = this.size - 1;
		E element = this.elements[index];
		this.elements[index] = null; // allow GC to work
		this.size--;
		return element;
	}

	public E peek() {
		if (this.size == 0) {
			throw new EmptyStackException();
		}
		return this.elements[this.size - 1];
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

	@Override
	public String toString() {
		return Arrays.toString(ArrayTools.reverse(this.copyElements(this.size)));
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write size (and any hidden stuff)
		stream.defaultWriteObject();
		Object[] array = this.elements;
		int elementsLength = array.length;
		stream.writeInt(elementsLength);
		for (int i = 0; i < this.size; i++) {
			stream.writeObject(array[i]);
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
	}
}
