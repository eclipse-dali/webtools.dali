/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * Resizable-array implementation of the {@link Stack} interface.
 * @see ArrayList
 */
public class ArrayStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private ArrayList<E> elements;

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
	public ArrayStack(int initialCapacity) {
		super();
		this.elements = new ArrayList<E>(initialCapacity);
	}

	/**
	 * Construct a stack containing the elements of the specified
	 * collection. The stack will pop its elements in reverse of the
	 * order they are returned by the collection's iterator (i.e. the
	 * last element returned by the collection's iterator will be the
	 * first element returned by {@link #pop()}).
	 */
	public ArrayStack(Collection<? extends E> collection) {
		super();
		this.elements = new ArrayList<E>(collection);
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.elements.add(element);
	}

	public E pop() {
		try {
			return this.elements.remove(this.elements.size() - 1);
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException();
		}
	}

	public E peek() {
		try {
			return this.elements.get(this.elements.size() - 1);
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException();
		}
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	/**
	 * Increase the stack's capacity, if necessary, to ensure it has at least
	 * the specified minimum capacity.
	 */
	public void ensureCapacity(int minCapacity) {
		this.elements.ensureCapacity(minCapacity);
	}

	/**
	 * Decrease the stack's capacity, if necessary, to match its current size.
	 */
	public void trimToSize() {
		this.elements.trimToSize();
	}


	// ********** standard methods **********

	@Override
	public ArrayStack<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			ArrayStack<E> clone = (ArrayStack<E>) super.clone();
			@SuppressWarnings("unchecked")
			ArrayList<E> list = (ArrayList<E>) this.elements.clone();
			clone.elements = list;
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
		return this.elements.toString();
	}
}
