/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicStampedReference;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Linked LIFO implementation of the {@link Stack} interface
 * that provides lock-free concurrent access.
 * The stack's internal nodes are cached and re-used.
 * <p>
 * Unlike most of the other stack implementations, this stack
 * does not accept <code>null</code> elements and returns <code>null</code>
 * when the stack is empty (rather than throwing a
 * {@link EmptyStackException}).
 * 
 *******************************************************************************
 * NB: This class is a nice demonstration of the poor performance of an object
 * cache for concurrency support!
 * @see StackTests#testConcurrentAccess()
 *******************************************************************************
 * 
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class CachingConcurrentStack<E>
	implements Stack<E>
{
	private final AtomicStampedReference<Node<E>> headRef = new AtomicStampedReference<>(null, 0);

	private final AtomicStampedReference<Node<E>> cacheHeadRef = new AtomicStampedReference<>(null, 0);

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
		int[] oldStampHolder = new int[1];
		int newStamp = -1;
		do {
			oldHead = this.headRef.get(oldStampHolder);
			newHead.next = oldHead;
			newStamp = oldStampHolder[0];
			newStamp++;
		} while ( ! this.headRef.compareAndSet(oldHead, newHead, oldStampHolder[0], newStamp));
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E pop() {
		Node<E> oldHead = null;
		Node<E> newHead = null;
		int[] oldStampHolder = new int[1];
		int newStamp = -1;
		do {
			oldHead = this.headRef.get(oldStampHolder);
			if (oldHead == null) {
				return null;
			}
			newHead = oldHead.next;
			newStamp = oldStampHolder[0];
			newStamp++;
		} while ( ! this.headRef.compareAndSet(oldHead, newHead, oldStampHolder[0], newStamp));
		E element = oldHead.element;
		this.release(oldHead);
		return element;
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E peek() {
		int[] stampHolder = new int[1];
		Node<E> head = this.headRef.get(stampHolder);
		return (head == null) ? null : head.element;
	}

	public boolean isEmpty() {
		int[] stampHolder = new int[1];
		return this.headRef.get(stampHolder) == null;
	}

	// may be inaccurate
	@Override
	public String toString() {
		int[] stampHolder = new int[1];
		ArrayList<E> nodes = new ArrayList<>();
		for (Node<E> node = this.headRef.get(stampHolder); node != null; node = node.next) {
			nodes.add(node.element);
		}
		return nodes.toString();
	}

	private Node<E> buildNode(E element) {
		Node<E> oldHead = null;
		Node<E> newHead = null;
		int[] oldStampHolder = new int[1];
		int newStamp = -1;
		do {
			oldHead = this.cacheHeadRef.get(oldStampHolder);
			if (oldHead == null) {
				return new Node<>(element);
			}
			newHead = oldHead.next;
			newStamp = oldStampHolder[0];
			newStamp++;
		} while ( ! this.cacheHeadRef.compareAndSet(oldHead, newHead, oldStampHolder[0], newStamp));
		oldHead.element = element;
		return oldHead;
	}

	private void release(Node<E> node) {
		node.element = null; // allow GC to work
		Node<E> oldHead = null;
		int[] oldStampHolder = new int[1];
		int newStamp = -1;
		do {
			oldHead = this.cacheHeadRef.get(oldStampHolder);
			node.next = oldHead;
			newStamp = oldStampHolder[0];
			newStamp++;
		} while ( ! this.cacheHeadRef.compareAndSet(oldHead, node, oldStampHolder[0], newStamp));
	}


	// ********** Node **********

	private static final class Node<E> {
		volatile E element;
		volatile Node<E> next = null;

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
