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
	private final NodeFactory<E> nodeFactory;
	private transient Node<E> head; // next element to dequeue head
	private transient Node<E> tail; // next element to dequeue tail

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty deque with no node cache.
	 */
	public LinkedDeque() {
		this(0);
	}

	/**
	 * Construct an empty deque with a node cache with the specified size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public LinkedDeque(int cacheSize) {
		this(LinkedDeque.<E>buildNodeFactory(cacheSize));
		this.head = null;
	}

	private static <E> NodeFactory<E> buildNodeFactory(int cacheSize) {
		if (cacheSize < -1) {
			throw new IllegalArgumentException("Cache size must be greater than or equal to -1: " + cacheSize); //$NON-NLS-1$
		}
		return (cacheSize == 0) ? SimpleNodeFactory.<E>instance() : new CachingNodeFactory<>(cacheSize);
	}

	private LinkedDeque(NodeFactory<E> nodeFactory) {
		super();
		this.nodeFactory = nodeFactory;
		this.head = null;
		this.tail = null;
	}


	// ********** Deque implementation **********

	public void enqueueTail(E element) {
		Node<E> newNode = this.nodeFactory.buildNode(element, null, this.tail);
		if (this.tail == null) {
			this.head = newNode; // first node
		} else {
			this.tail.next = newNode;
		}
		this.tail = newNode;
	}

	public void enqueueHead(E element) {
		Node<E> newNode = this.nodeFactory.buildNode(element, this.head, null);
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
		E element = node.element;
		this.nodeFactory.release(node);
		return element;
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
		E element = node.element;
		this.nodeFactory.release(node);
		return element;
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
		LinkedDeque<E> clone = new LinkedDeque<>(this.nodeFactory.copy());
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


	// ********** Node classes **********

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

	private abstract static class NodeFactory<E> {
		NodeFactory() {
			super();
		}

		Node<E> buildNode(E element, Node<E> next, Node<E> prev) {
			return new Node<>(element, next, prev);
		}

		abstract void release(Node<E> node);

		abstract NodeFactory<E> copy();
	}

	private static class SimpleNodeFactory<E>
		extends NodeFactory<E>
		implements Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final NodeFactory INSTANCE = new SimpleNodeFactory();
		@SuppressWarnings("unchecked")
		public static <E> NodeFactory<E> instance() {
			return INSTANCE;
		}

		private SimpleNodeFactory() {
			super();
		}

		@Override
		void release(Node<E> node) {
			// NOP
		}

		@Override
		NodeFactory<E> copy() {
			return this;
		}

		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}

		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	private static final class CachingNodeFactory<E>
		extends NodeFactory<E>
		implements Serializable
	{
		private final int maxCacheSize;
		private transient int cacheSize = 0;
		private transient Node<E> cacheHead;
		private static final long serialVersionUID = 1L;

		CachingNodeFactory(int maxCacheSize) {
			super();
			this.maxCacheSize = maxCacheSize;
		}

		@Override
		Node<E> buildNode(E element, Node<E> next, Node<E> prev) {
			if (this.cacheHead == null) {
				return super.buildNode(element, next, prev);
			}
			Node<E> node = this.cacheHead;
			this.cacheHead = node.next;
			this.cacheSize--;
			node.element = element;
			node.next = next;
			return node;
		}

		@Override
		void release(Node<E> node) {
			if ((this.maxCacheSize == -1) || (this.cacheSize < this.maxCacheSize)) {
				node.element = null; // allow GC to work
				node.next = this.cacheHead;
				node.prev = null;
				this.cacheHead = node;
				this.cacheSize++;
			}
		}

		@Override
		NodeFactory<E> copy() {
			return new CachingNodeFactory<>(this.maxCacheSize);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.cacheSize);
		}
	}
}
