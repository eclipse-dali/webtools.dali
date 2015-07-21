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
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Fixed-size array LIFO implementation of the {@link Stack} interface.
 * This implementation will throw an exception if its capacity is exceeded.
 * @param <E> the type of elements maintained by the stack
 * @see ArrayStack
 * @see StackTools
 */
public class FixedSizeArrayStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private E[] elements;

	/** The index of where the next "pushed" element will go. */
	private int next = 0;

	private int size = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty stack with the specified capacity.
	 */
	@SuppressWarnings("unchecked")
	public FixedSizeArrayStack(int capacity) {
		super();
		if (capacity < 0) {
			throw new IllegalArgumentException("Illegal capacity: " + capacity); //$NON-NLS-1$
		}
		this.elements = (E[]) new Object[capacity];
	}


	// ********** Stack implementation **********

	public void push(E element) {
		if (this.isFull()) {
			throw new IllegalStateException("Stack is full."); //$NON-NLS-1$
		}
		this.elements[this.next] = element;
		if (++this.next == this.elements.length) {
			this.next = 0;
		}
		this.size++;
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

	/**
	 * Return whether the stack is full,
	 * as its capacity is fixed.
	 */
	public boolean isFull() {
		return this.size == this.elements.length;
	}


	// ********** standard methods **********

	@Override
	public FixedSizeArrayStack<E> clone() {
		int len = this.elements.length;
		FixedSizeArrayStack<E> clone = new FixedSizeArrayStack<E>(len);
		System.arraycopy(this.elements, 0, clone.elements, 0, len);
		clone.next = this.next;
		clone.size = this.size;
		return clone;
	}

	/**
	 * Print the elements in the order in which they are "pushed" on to
	 * the stack (as opposed to the order in which they will be "popped"
	 * off of the stack).
	 */
	@Override
	public String toString() {
		return Arrays.toString(this.copyElements());
	}

	private Object[] copyElements() {
		if (this.size == 0) {
			return ObjectTools.EMPTY_OBJECT_ARRAY;
		}
		Object[] result = new Object[this.size];
		if (this.next >= this.size) {
			// elements are contiguous, but not to end of array
			System.arraycopy(this.elements, (this.next - this.size), result, 0, this.size);
		} else if (this.next == 0) {
			// elements are contiguous to end of array
			System.arraycopy(this.elements, (this.elements.length - this.size), result, 0, this.size);
		} else {
			// elements wrap past end of array
			int fragmentSize = this.size - this.next;
			System.arraycopy(this.elements, (this.elements.length - fragmentSize), result, 0, fragmentSize);
			System.arraycopy(this.elements, 0, result, fragmentSize, (this.size - fragmentSize));
		}
		return result;
	}
}
