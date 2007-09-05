/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Straightforward implementation of the Stack interface.
 */
public class SimpleStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private LinkedList<E> elements;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty stack.
	 */
	public SimpleStack() {
		super();
		this.elements = new LinkedList<E>();
	}


	// ********** Stack implementation **********

	public void push(E o) {
		this.elements.addLast(o);
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


	// ********** Cloneable implementation **********

	@Override
	public SimpleStack<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			SimpleStack<E> clone = (SimpleStack<E>) super.clone();
			@SuppressWarnings("unchecked")
			LinkedList<E> ll = (LinkedList<E>) this.elements.clone();
			clone.elements = ll;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

}
