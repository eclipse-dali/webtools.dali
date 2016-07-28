/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Linked LIFO implementation of the {@link Stack} interface.
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class LinkedStack<E>
	implements Stack<E>, Cloneable, Serializable
{
	private transient Node<E> head;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty linked stack.
	 */
	public LinkedStack() {
		super();
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.head = new Node<>(element, this.head);
	}

	public E pop() {
		if (this.head == null) {
			throw new EmptyStackException();
		}
		Node<E> node = this.head;
		this.head = node.next;
		return node.element;
	}

	public E peek() {
		if (this.head == null) {
			throw new EmptyStackException();
		}
		return this.head.element;
	}

	public boolean isEmpty() {
		return this.head == null;
	}


	// ********** standard methods **********

	@Override
	public LinkedStack<E> clone() {
		LinkedStack<E> clone = new LinkedStack<>();
		E[] elements = this.buildElements();
		for (int i = elements.length; i-- > 0; ) {
			clone.push(elements[i]);
		}
		return clone;
	}

	@SuppressWarnings("unchecked")
	private E[] buildElements() {
		int size = this.size();
		if (size == 0) {
			return (E[]) ObjectTools.EMPTY_OBJECT_ARRAY;
		}
		E[] elements = (E[]) new Object[size];
		int i = 0;
		for (Node<E> node = this.head; node != null; node = node.next) {
			elements[i++] = node.element;
		}
		return elements;
	}

	private int size() {
		int size = 0;
		for (Node<E> node = this.head; node != null; node = node.next) {
			size++;
		}
		return size;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.buildElements());
	}


	// ********** Serializable "implementation" **********

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// write nodeFactory (and any hidden stuff)
		stream.defaultWriteObject();
		Object[] elements = this.buildElements();
		int len = elements.length;
		stream.writeInt(len);
		// write elements in reverse order so they can be pushed as read
		for (int i = len; i-- > 0; ) {
			stream.writeObject(elements[i]);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		// read nodeFactory (and any hidden stuff)
		stream.defaultReadObject();
		int len = stream.readInt();
		for (int i = len; i-- > 0; ) {
			this.push((E) stream.readObject());
		}
	}


	// ********** Node classes **********

	private static final class Node<E> {
		E element;
		Node<E> next;

		Node(E element, Node<E> next) {
			super();
			this.element = element;
			this.next = next;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.element);
		}
	}
}