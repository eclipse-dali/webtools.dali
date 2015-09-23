/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
	private final NodeFactory<E> nodeFactory;
	private transient Node<E> head;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty stack with no node cache.
	 */
	public LinkedStack() {
		this(0);
	}

	/**
	 * Construct an empty stack with a node cache with the specified size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public LinkedStack(int cacheSize) {
		this(LinkedStack.<E>buildNodeFactory(cacheSize));
		this.head = null;
	}

	private static <E> NodeFactory<E> buildNodeFactory(int cacheSize) {
		if (cacheSize < -1) {
			throw new IllegalArgumentException("Cache size must be greater than or equal to -1: " + cacheSize); //$NON-NLS-1$
		}
		return (cacheSize == 0) ? SimpleNodeFactory.<E>instance() : new CachingNodeFactory<E>(cacheSize);
	}

	private LinkedStack(NodeFactory<E> nodeFactory) {
		super();
		this.nodeFactory = nodeFactory;
		this.head = null;
	}


	// ********** Stack implementation **********

	public void push(E element) {
		this.head = this.nodeFactory.buildNode(element, this.head);
	}

	public E pop() {
		if (this.head == null) {
			throw new EmptyStackException();
		}
		Node<E> node = this.head;
		this.head = node.next;
		E element = node.element;
		this.nodeFactory.release(node);
		return element;
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
		LinkedStack<E> clone = new LinkedStack<E>(this.nodeFactory.copy());
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

	private abstract static class NodeFactory<E> {
		NodeFactory() {
			super();
		}

		Node<E> buildNode(E element, Node<E> next) {
			return new Node<E>(element, next);
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
		Node<E> buildNode(E element, Node<E> next) {
			if (this.cacheHead == null) {
				return super.buildNode(element, next);
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
				this.cacheHead = node;
				this.cacheSize++;
			}
		}

		@Override
		NodeFactory<E> copy() {
			return new CachingNodeFactory<E>(this.maxCacheSize);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.cacheSize);
		}
	}
}
