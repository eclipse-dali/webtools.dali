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
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.stack.Stack;

/**
 * Linked LIFO implementation of the {@link Stack} interface
 * that provides lock-free concurrent access.
 * <p>
 * Unlike most of the other stack implementations, this stack
 * does not accept <code>null</code> elements and returns <code>null</code>
 * when the stack is empty (rather than throwing a
 * {@link java.util.EmptyStackException}).
 * 
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class ConcurrentStack<E>
	implements Stack<E>
{
	private final AtomicReference<Node<E>> headRef = new AtomicReference<>(null);


	/**
	 * Construct an empty stack.
	 */
	public ConcurrentStack() {
		super();
	}

	/**
	 * @exception NullPointerException when specified element is <code>null</code>
	 */
	public void push(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		Node<E> oldHead = null;
		Node<E> newHead = new Node<>(element);
		do {
			oldHead = this.headRef.get();
			newHead.next = oldHead;
		} while ( ! this.headRef.compareAndSet(oldHead, newHead));
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E pop() {
		Node<E> oldHead = null;
		Node<E> newHead = null;
		do {
			oldHead = this.headRef.get();
			if (oldHead == null) {
				return null;
			}
			newHead = oldHead.next;
		} while ( ! this.headRef.compareAndSet(oldHead, newHead));
		return oldHead.element;
	}

	/**
	 * @return <code>null</code> if the stack is empty
	 */
	public E peek() {
		Node<E> head = this.headRef.get();
		return (head == null) ? null : head.element;
	}

	public boolean isEmpty() {
		return this.headRef.get() == null;
	}

	// may be inaccurate
	@Override
	public String toString() {
		ArrayList<E> nodes = new ArrayList<>();
		for (Node<E> node = this.headRef.get(); node != null; node = node.next) {
			nodes.add(node.element);
		}
		return nodes.toString();
	}


	// ********** Node **********

	private static final class Node<E> {
		final E element;
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
