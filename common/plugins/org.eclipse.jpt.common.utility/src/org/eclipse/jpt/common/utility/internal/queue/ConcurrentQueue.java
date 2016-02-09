/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.queue.Queue;

/**
 * Linked FIFO implementation of the {@link Queue} interface
 * that provides lock-free concurrent access
 * (using Michael-Scott algorithm).
 * <p>
 * Unlike most of the other queue implementations, this queue
 * does not accept <code>null</code> elements and returns <code>null</code>
 * when the queue is empty (rather than throwing a
 * {@link java.util.NoSuchElementException}).
 * 
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public class ConcurrentQueue<E>
	implements Queue<E>
{
	private final AtomicReference<Node<E>> headRef;
	private final AtomicReference<Node<E>> tailRef;


	/**
	 * Construct an empty queue.
	 */
	public ConcurrentQueue() {
		super();
		final Node<E> dummy = new Node<>(null);
		this.headRef = new AtomicReference<>(dummy);
		this.tailRef = new AtomicReference<>(dummy);
	}

	/**
	 * @exception NullPointerException when specified element is <code>null</code>
	 */
	public void enqueue(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		final Node<E> newNode = new Node<>(element);
		while (true) {
			Node<E> tail = this.tailRef.get();
			Node<E> next = tail.next;
			if (tail == this.tailRef.get()) {
				// 'tail' and 'next' are consistent
				if (next == null) {
					// 'tail' is last node (i.e. the queue is quiescent),
					// so try to insert new node
					if (Node.NEXT_UPDATER.compareAndSet(tail, null, newNode)) {
						// euqueue succeeded, now try to advance the queue's tail...
						this.tailRef.compareAndSet(tail, newNode);
						return; // ...but failure is OK (because another thread advanced it)
					}
					// else: insertion failed (i.e. another thread intervened);
					// loop and try again
				} else {
					// 'tail' is the penultimate node (i.e. the queue is in "intermediate" state),
					// so advance the queue's tail before looping and trying again
					this.tailRef.compareAndSet(tail, next);
				}
			}
		}
	}

	/**
	 * @return <code>null</code> if the queue is empty
	 */
	public E dequeue() {
		while (true) {
			Node<E> head = this.headRef.get();
			Node<E> tail = this.tailRef.get();
			Node<E> next = head.next;
			if (head == this.headRef.get()) {
				// 'head', 'tail'(?), and 'next' are consistent
				if (head == tail) {
					if (next == null) {
						// the queue is empty
						return null;
					}
					// else: the queue's tail is falling behind (i.e. the queue is in "intermediate" state),
					// so advance the queue's tail before looping and trying again
					this.tailRef.compareAndSet(tail, next);
				} else {
					// no need to deal with the queue's tail (because we have 2 or more nodes in the queue)
					E result = next.element;
					if (this.headRef.compareAndSet(head, next)) {
						return result;
					}
					// else: unable to move the queue's head to the next node;
					// loop and try again
				}
			}
		}
	}

	/**
	 * @return <code>null</code> if the queue is empty
	 */
	public E peek() {
		while (true) {
			Node<E> head = this.headRef.get();
			Node<E> tail = this.tailRef.get();
			Node<E> next = head.next;
			if (head == this.headRef.get()) {
				// 'head', 'tail'(?), and 'next' are consistent
				return (head == tail) ? ((next != null) ? next.element : null) : next.element;
			}
		}
	}

	public boolean isEmpty() {
		return this.peek() == null;
	}

	// may be inaccurate
	@Override
	public String toString() {
		ArrayList<E> nodes = new ArrayList<>();
		Node<E> head = this.headRef.get();
		Node<E> node = head.next;
		while (node != null) {
			nodes.add(node.element);
			node = node.next;
		}
		return nodes.toString();
	}


	// ********** Node **********

	private static final class Node<E> {
		final E element;
		volatile Node<E> next; // set reflectively by NEXT_UPDATER

		@SuppressWarnings({ "rawtypes", "nls" })
		static final AtomicReferenceFieldUpdater<Node, Node> NEXT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");

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
