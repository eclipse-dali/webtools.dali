/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * Linked list implementation of the {@link Stack} interface.
 * @param <E> the type of elements maintained by the stack
 * @see LinkedList
 */
public class LinkedStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private LinkedList<E> elements;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty stack.
	 */
	public LinkedStack() {
		super();
		this.elements = new LinkedList<E>();
	}

	/**
	 * Construct a stack containing the elements of the specified
	 * collection. The stack will pop its elements in reverse of the
	 * order they are returned by the collection's iterator (i.e. the
	 * last element returned by the collection's iterator will be the
	 * first element returned by {@link #pop()}).
	 */
	public LinkedStack(Collection<? extends E> collection) {
		super();
		this.elements = new LinkedList<E>(collection);
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.elements.addLast(element);
	}

	public E pop() {
		try {
			return this.elements.removeLast();
		} catch (NoSuchElementException ex) {
			throw new EmptyStackException();
		}
	}

	public E peek() {
		try {
			return this.elements.getLast();
		} catch (NoSuchElementException ex) {
			throw new EmptyStackException();
		}
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}


	// ********** standard methods **********

	@Override
	public LinkedStack<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			LinkedStack<E> clone = (LinkedStack<E>) super.clone();
			@SuppressWarnings("unchecked")
			LinkedList<E> list = (LinkedList<E>) this.elements.clone();
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
