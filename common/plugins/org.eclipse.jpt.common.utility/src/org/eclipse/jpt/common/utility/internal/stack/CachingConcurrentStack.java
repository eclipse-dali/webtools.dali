/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Linked LIFO implementation of the {@link Stack} interface
 * that provides non-blocking, lock-free concurrent access.
 * The stack's internal nodes are cached and re-used.
 * <p>
 * Unlike most of the other stack implementations, this stack
 * does not accept <code>null</code> elements and returns <code>null</code>
 * when the stack is empty (rather than throwing a
 * {@link EmptyStackException}).
 * 
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class CachingConcurrentStack<E>
	implements Stack<E>
{
	private final AtomicStampedReference<Node<E>> headRef = new AtomicStampedReference<>(null, 0);

	private final AtomicReference<Node<E>> cacheHeadRef = new AtomicReference<>(null);

	/**
	 * Construct an empty stack.
	 */
	public CachingConcurrentStack() {
		super();
	}

	/**
	 * @exception NullPointerException when specified element is <code>null</code>
	 */
	public void push(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		Node<E> newHead = this.buildNode(element);
		Node<E> oldHead = null;
		int[] stampHolder = new int[1];
		int stamp = -1;
		do {
			oldHead = this.headRef.get(stampHolder);
			newHead.next = oldHead;
			stamp = stampHolder[0];
		} while ( ! this.headRef.compareAndSet(oldHead, newHead, stampHolder[0], ++stamp));
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E pop() {
		Node<E> oldHead = null;
		Node<E> newHead = null;
		int[] stampHolder = new int[1];
		int stamp = -1;
		do {
			oldHead = this.headRef.get(stampHolder);
			if (oldHead == null) {
				return null;
			}
			newHead = oldHead.next;
			stamp = stampHolder[0];
		} while ( ! this.headRef.compareAndSet(oldHead, newHead, stampHolder[0], ++stamp));
		E element = oldHead.element;
		this.release(oldHead);
		return element;
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E peek() {
		Node<E> head = this.headRef.get(DUMMY_STAMP_HOLDER);
		return (head == null) ? null : head.element;
	}

	public boolean isEmpty() {
		return this.headRef.get(DUMMY_STAMP_HOLDER) == null;
	}

	// may be inaccurate
	@Override
	public String toString() {
		ArrayList<E> nodes = new ArrayList<>();
		for (Node<E> node = this.headRef.get(DUMMY_STAMP_HOLDER); node != null; node = node.next) {
			nodes.add(node.element);
		}
		return nodes.toString();
	}

	private static final int[] DUMMY_STAMP_HOLDER = new int[1];

	private Node<E> buildNode(E element) {
		Node<E> oldHead = null;
		Node<E> newHead = null;
		do {
			oldHead = this.cacheHeadRef.get();
			if (oldHead == null) {
				return new Node<>(element);
			}
			newHead = oldHead.next;
		} while ( ! this.cacheHeadRef.compareAndSet(oldHead, newHead));
		oldHead.element = element;
		return oldHead;
	}

	private void release(Node<E> node) {
		node.element = null; // allow GC to work
		Node<E> oldHead = null;
		do {
			oldHead = this.cacheHeadRef.get();
			node.next = oldHead;
		} while ( ! this.cacheHeadRef.compareAndSet(oldHead, node));
	}


	// ********** Node **********

	private static final class Node<E> {
		E element;
		Node<E> next = null;

		Node(E element) {
			super();
			this.element = element;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.element);
		}
	}
}
