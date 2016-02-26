/*******************************************************************************
 * Copyright (c) 2015, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Linked implementation of the {@link Deque} interface.
 * @param <E> the type of elements maintained by the deque
 * @see DequeTools
 */
public class LinkedDeque<E>
	implements Deque<E>, Cloneable, Serializable
{
	private transient Node<E> head; // next element to dequeue head
	private transient Node<E> tail; // next element to dequeue tail

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty linked deque.
	 */
	public LinkedDeque() {
		super();
		this.head = null;
		this.tail = null;
	}


	// ********** Deque implementation **********

	public void enqueueTail(E element) {
		Node<E> newNode = new Node<>(element, null, this.tail);
		if (this.tail == null) {
			this.head = newNode; // first node
		} else {
			this.tail.next = newNode;
		}
		this.tail = newNode;
	}

	public void enqueueHead(E element) {
		Node<E> newNode = new Node<>(element, this.head, null);
		if (this.head == null) {
			this.tail = newNode; // first node
		} else {
			this.head.prev = newNode;
		}
		this.head = newNode;
	}

	public E dequeueHead() {
		if (this.head == null) {
			throw new NoSuchElementException();
		}
		Node<E> node = this.head;
		this.head = node.next;
		if (this.head == null) {
			this.tail = null; // last node
		} else {
			this.head.prev = null;
		}
		return node.element;
	}

	public E dequeueTail() {
		if (this.tail == null) {
			throw new NoSuchElementException();
		}
		Node<E> node = this.tail;
		this.tail = node.prev;
		if (this.tail == null) {
			this.head = null; // last node
		} else {
			this.tail.next = null;
		}
		return node.element;
	}

	public E peekHead() {
		if (this.head == null) {
			throw new NoSuchElementException();
		}
		return this.head.element;
	}

	public E peekTail() {
		if (this.tail == null) {
			throw new NoSuchElementException();
		}
		return this.tail.element;
	}

	public boolean isEmpty() {
		return this.head == null;
	}


	// ********** standard methods **********

	@Override
	public LinkedDeque<E> clone() {
		LinkedDeque<E> clone = new LinkedDeque<>();
		E[] elements = this.buildElements();
		for (E element : elements) {
			clone.enqueueTail(element);
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
		stream.writeInt(elements.length);
		for (Object element : elements) {
			stream.writeObject(element);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		// read nodeFactory (and any hidden stuff)
		stream.defaultReadObject();
		int len = stream.readInt();
		for (int i = len; i-- > 0; ) {
			this.enqueueTail((E) stream.readObject());
		}
	}


	// ********** Node **********

	private static final class Node<E> {
		E element;
		Node<E> next;
		Node<E> prev;

		Node(E element, Node<E> next, Node<E> prev) {
			super();
			this.element = element;
			this.next = next;
			this.prev = prev;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.element);
		}
	}
}
