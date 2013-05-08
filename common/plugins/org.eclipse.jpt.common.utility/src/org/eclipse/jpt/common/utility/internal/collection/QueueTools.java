/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * {@link Queue} utility methods.
 */
public final class QueueTools {

	// ********** enqueue all **********

	/**
	 * Enqueue all the elements returned by the specified iterable
	 * on the specified queue.
	 * Return the queue.
	 */
	public static <Q extends Queue<? super E>, E> Q enqueueAll(Q queue, Iterable<? extends E> iterable) {
		return enqueueAll(queue, iterable.iterator());
	}

	/**
	 * Enqueue all the elements returned by the specified iterator
	 * on the specified queue.
	 * Return the queue.
	 */
	public static <Q extends Queue<? super E>, E> Q enqueueAll(Q queue, Iterator<? extends E> iterator) {
		while (iterator.hasNext()) {
			queue.enqueue(iterator.next());
		}
		return queue;
	}

	/**
	 * Enqueue all the elements in the specified array
	 * on the specified queue.
	 * Return the queue.
	 */
	public static <Q extends Queue<? super E>, E> Q enqueueAll(Q queue, E... array) {
		for (E element : array) {
			queue.enqueue(element);
		}
		return queue;
	}

	/**
	 * Pop all the elements from the specified stack and enqueue them
	 * on the specified queue.
	 * Return the queue.
	 */
	public static <Q extends Queue<? super E>, E> Q enqueueAll(Q queue, Stack<? extends E> stack) {
		while ( ! stack.isEmpty()) {
			queue.enqueue(stack.pop());
		}
		return queue;
	}

	/**
	 * Dequeue all the elements from the second specified queue and enqueue them
	 * on the first specified queue.
	 * Return the first queue.
	 * @see #drainTo(Queue, Queue)
	 */
	public static <Q extends Queue<? super E>, E> Q enqueueAll(Q queue1, Queue<? extends E> queue2) {
		while ( ! queue2.isEmpty()) {
			queue1.enqueue(queue2.dequeue());
		}
		return queue1;
	}


	// ********** drain **********

	/**
	 * Drain all the elements from the specified queue and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> drain(Queue<? extends E> queue) {
		return drainTo(queue, new ArrayList<E>());
	}

	/**
	 * Drain all the elements from the specified queue and add them to the
	 * specified collection.
	 * Return the collection.
	 */
	public static <C extends Collection<? super E>, E> C drainTo(Queue<? extends E> queue, C collection) {
		while ( ! queue.isEmpty()) {
			collection.add(queue.dequeue());
		}
		return collection;
	}

	/**
	 * Drain all the elements from the specified queue and push them on the
	 * specified stack.
	 * Return the stack.
	 */
	public static <S extends Stack<? super E>, E> S drainTo(Queue<? extends E> queue, S stack) {
		while ( ! queue.isEmpty()) {
			stack.push(queue.dequeue());
		}
		return stack;
	}

	/**
	 * Drain all the elements from the first specified queue and enqueue them
	 * on the second specified queue.
	 * Return the second queue.
	 * @see #enqueueAll(Queue, Queue)
	 */
	public static <Q extends Queue<? super E>, E> Q drainTo(Queue<? extends E> queue1, Q queue2) {
		while ( ! queue1.isEmpty()) {
			queue2.enqueue(queue1.dequeue());
		}
		return queue2;
	}


	// ********** factory methods **********

	/**
	 * Return a FIFO queue corresponding to the specified iterable.
	 */
	public static <E> ArrayQueue<E> queue(Iterable<? extends E> iterable) {
		return arrayQueue(iterable);
	}

	/**
	 * Return a FIFO queue corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayQueue<E> queue(Iterable<? extends E> iterable, int iterableSize) {
		return arrayQueue(iterable, iterableSize);
	}

	/**
	 * Return a FIFO queue corresponding to the specified iterator.
	 */
	public static <E> ArrayQueue<E> queue(Iterator<? extends E> iterator) {
		return arrayQueue(iterator);
	}

	/**
	 * Return a FIFO queue corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayQueue<E> queue(Iterator<? extends E> iterator, int iteratorSize) {
		return arrayQueue(iterator, iteratorSize);
	}

	/**
	 * Return a FIFO queue corresponding to the specified array.
	 */
	public static <E> ArrayQueue<E> queue(E... array) {
		return arrayQueue(array);
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified iterable.
	 */
	public static <E> ArrayQueue<E> arrayQueue(Iterable<? extends E> iterable) {
		return arrayQueue(iterable.iterator());
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayQueue<E> arrayQueue(Iterable<? extends E> iterable, int iterableSize) {
		return arrayQueue(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified iterator.
	 */
	public static <E> ArrayQueue<E> arrayQueue(Iterator<? extends E> iterator) {
		return enqueueAll(new ArrayQueue<E>(), iterator);
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayQueue<E> arrayQueue(Iterator<? extends E> iterator, int iteratorSize) {
		return enqueueAll(new ArrayQueue<E>(iteratorSize), iterator);
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified array.
	 */
	public static <E> ArrayQueue<E> arrayQueue(E... array) {
		return enqueueAll(new ArrayQueue<E>(array.length), array);
	}

	/**
	 * Return an linked list-based FIFO queue corresponding to the specified iterable.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterable<? extends E> iterable) {
		return linkedQueue(iterable.iterator());
	}

	/**
	 * Return an linked list-based FIFO queue corresponding to the specified iterator.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterator<? extends E> iterator) {
		return enqueueAll(new LinkedQueue<E>(), iterator);
	}

	/**
	 * Return an linked list-based FIFO queue corresponding to the specified array.
	 */
	public static <E> LinkedQueue<E> linkedQueue(E... array) {
		return enqueueAll(new LinkedQueue<E>(), array);
	}

	/**
	 * Return a LIFO queue.
	 */
	public static <E> StackQueue<E> stackQueue() {
		return queue(new ArrayStack<E>());
	}

	/**
	 * Adapt the specified stack to the {@link Queue} interface,
	 * implementing a LIFO queue.
	 */
	public static <E> StackQueue<E> queue(Stack<E> stack) {
		return new StackQueue<E>(stack);
	}

	/**
	 * Return a priority queue that returns its elements in
	 * {@linkplain Comparable natural order}.
	 */
	public static <E> PriorityQueue<E> priorityQueue() {
		return queue((Comparator<? super E>) null);
	}

	/**
	 * Return a priority queue whose elements are returned in
	 * the order determined by the specified comparator.
	 * If the specified comparator is <code>null</code>, the elements will be
	 * returned in {@linkplain Comparable natural order}.
	 */
	public static <E> PriorityQueue<E> queue(Comparator<? super E> comparator) {
		return queue(new TreeSet<E>(comparator));
	}

	/**
	 * Adapt the specified sorted set to the {@link Queue} interface,
	 * implementing a priority queue.
	 */
	public static <E> PriorityQueue<E> queue(SortedSet<E> elements) {
		return new PriorityQueue<E>(elements);
	}

	/**
	 * Return an unmodifiable empty queue.
	 */
	public static <E> Queue<E> emptyQueue() {
		return EmptyQueue.<E>instance();
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private QueueTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
