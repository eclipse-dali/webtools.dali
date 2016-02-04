/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.internal.stack.ArrayStack;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Queue} utility methods.
 */
public final class QueueTools {

	// ********** enqueue all **********

	/**
	 * Enqueue all the elements returned by the specified iterable
	 * on the specified queue.
	 * Return whether the queue changed as a result.
	 */
	public static <E> boolean enqueueAll(Queue<? super E> queue, Iterable<? extends E> iterable) {
		return enqueueAll(queue, iterable.iterator());
	}

	/**
	 * Enqueue all the elements returned by the specified iterator
	 * on the specified queue.
	 * Return whether the queue changed as a result.
	 */
	public static <E> boolean enqueueAll(Queue<? super E> queue, Iterator<? extends E> iterator) {
		return iterator.hasNext() && enqueueAll_(queue, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean enqueueAll_(Queue<? super E> queue, Iterator<? extends E> iterator) {
		do {
			queue.enqueue(iterator.next());
		} while (iterator.hasNext());
		return true;
	}

	/**
	 * Enqueue all the elements in the specified array
	 * on the specified queue.
	 * Return whether the queue changed as a result.
	 */
	@SafeVarargs
	public static <E> boolean enqueueAll(Queue<? super E> queue, E... array) {
		int len = array.length;
		return (len != 0) && enqueueAll_(queue, array, len);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean enqueueAll_(Queue<? super E> queue, E[] array, int arrayLength) {
		int i = 0;
		do {
			queue.enqueue(array[i++]);
		} while (i < arrayLength);
		return true;
	}


	// ********** drain **********

	/**
	 * Drain all the elements from the specified queue and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> drain(Queue<? extends E> queue) {
		ArrayList<E> result = new ArrayList<>();
		drainTo(queue, result);
		return result;
	}

	/**
	 * Drain all the elements from the specified queue and add them to the
	 * specified collection.
	 * Return whether the queue changed as a result.
	 */
	public static <E> boolean drainTo(Queue<? extends E> queue, Collection<? super E> collection) {
		return ( ! queue.isEmpty()) && drainTo_(queue, collection);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <E> boolean drainTo_(Queue<? extends E> queue, Collection<? super E> collection) {
		do {
			collection.add(queue.dequeue());
		} while ( ! queue.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified queue
	 * to the specified list at the specified index.
	 * Return whether the queue changed as a result.
	 */
	public static <E> boolean drainTo(Queue<? extends E> queue, List<? super E> list, int index) {
		return ( ! queue.isEmpty()) && drainTo_(queue, list, index);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <E> boolean drainTo_(Queue<? extends E> queue, List<? super E> list, int index) {
		return (index == list.size()) ? drainTo_(queue, list) : list.addAll(index, drain(queue));
	}

	/**
	 * Drain all the elements from the specified queue and push them on the
	 * specified stack.
	 * Return whether the queue changed as a result.
	 */
	public static <E> boolean drainTo(Queue<? extends E> queue, Stack<? super E> stack) {
		return ( ! queue.isEmpty()) && drainTo_(queue, stack);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <E> boolean drainTo_(Queue<? extends E> queue, Stack<? super E> stack) {
		do {
			stack.push(queue.dequeue());
		} while ( ! queue.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the first specified queue and enqueue them
	 * on the second specified queue.
	 * Return whether the first queue changed as a result.
	 */
	public static <E> boolean drainTo(Queue<? extends E> queue1, Queue<? super E> queue2) {
		return ( ! queue1.isEmpty()) && drainTo_(queue1, queue2);
	}

	/**
	 * assume queue 1 is not empty
	 */
	private static <E> boolean drainTo_(Queue<? extends E> queue1, Queue<? super E> queue2) {
		do {
			queue2.enqueue(queue1.dequeue());
		} while ( ! queue1.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified queue, passing each element to the
	 * specified key transformer. Map the generated key to its element.
	 * Return whether the queue changed as a result.
	 */
	public static <K, V, E extends V> boolean drainTo(Queue<E> queue, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		return ( ! queue.isEmpty()) && drainTo_(queue, map, keyTransformer);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <K, V, E extends V> boolean drainTo_(Queue<E> queue, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		do {
			MapTools.add(map, queue.dequeue(), keyTransformer);
		} while ( ! queue.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified queue, passing each element to the
	 * specified key and value transformers. Add the generated key/value pairs
	 * to the specified map.
	 * Return whether the queue changed as a result.
	 */
	public static <K, V, E> boolean drainTo(Queue<E> queue, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		return ( ! queue.isEmpty()) && drainTo_(queue, map, keyTransformer, valueTransformer);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <K, V, E> boolean drainTo_(Queue<E> queue, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		do {
			MapTools.add(map, queue.dequeue(), keyTransformer, valueTransformer);
		} while ( ! queue.isEmpty());
		return true;
	}


	// ********** array queue factory methods **********

	/**
	 * Return an empty array-based FIFO queue.
	 */
	public static <E> ArrayQueue<E> arrayQueue() {
		return arrayQueue(10);
	}

	/**
	 * Return an empty array-based FIFO queue with specified initial capacity.
	 */
	public static <E> ArrayQueue<E> arrayQueue(int initialCapacity) {
		return new ArrayQueue<>(initialCapacity);
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
		ArrayQueue<E> result = QueueTools.arrayQueue();
		enqueueAll(result, iterator);
		return result;
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayQueue<E> arrayQueue(Iterator<? extends E> iterator, int iteratorSize) {
		ArrayQueue<E> result = QueueTools.arrayQueue(iteratorSize);
		enqueueAll(result, iterator);
		return result;
	}

	/**
	 * Return an array-based FIFO queue corresponding to the specified array.
	 */
	@SafeVarargs
	public static <E> ArrayQueue<E> arrayQueue(E... array) {
		ArrayQueue<E> result = QueueTools.arrayQueue(array.length);
		enqueueAll(result, array);
		return result;
	}


	// ********** linked queue factory methods **********

	/**
	 * Return an empty link-based FIFO queue with no node cache.
	 */
	public static <E> LinkedQueue<E> linkedQueue() {
		return linkedQueue(0);
	}

	/**
	 * Return an empty link-based FIFO queue
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedQueue<E> linkedQueue(int cacheSize) {
		return new LinkedQueue<>(cacheSize);
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified iterable.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterable<? extends E> iterable) {
		return linkedQueue(iterable, 0);
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified iterable
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterable<? extends E> iterable, int cacheSize) {
		return linkedQueue(iterable.iterator(), cacheSize);
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified iterator.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterator<? extends E> iterator) {
		return linkedQueue(iterator, 0);
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified iterator
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedQueue<E> linkedQueue(Iterator<? extends E> iterator, int cacheSize) {
		LinkedQueue<E> result = QueueTools.linkedQueue(cacheSize);
		enqueueAll(result, iterator);
		return result;
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified array.
	 */
	@SafeVarargs
	public static <E> LinkedQueue<E> linkedQueue(E... array) {
		return linkedQueue(array, 0);
	}

	/**
	 * Return a link-based FIFO queue corresponding to the specified array
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedQueue<E> linkedQueue(E[] array, int cacheSize) {
		LinkedQueue<E> result = QueueTools.linkedQueue(cacheSize);
		enqueueAll(result, array);
		return result;
	}


	// ********** fixed size queue factory methods **********

	/**
	 * Return a fixed-capacity array queue with the specified capacity.
	 */
	public static <E> FixedCapacityArrayQueue<E> fixedCapacityArrayQueue(int capacity) {
		return new FixedCapacityArrayQueue<>(capacity);
	}

	/**
	 * Return a fized-capacity array queue containing the elements of the specified
	 * collection. The queue will dequeue its elements in the same
	 * order they are returned by the collection's iterator (i.e. the
	 * first element returned by the collection's iterator will be the
	 * first element returned by {@link Queue#dequeue()}).
	 * The queue's capacity will be match the collection's size.
	 */
	public static <E> FixedCapacityArrayQueue<E> fixedCapacityArrayQueue(Collection<? extends E> collection) {
		FixedCapacityArrayQueue<E> result = QueueTools.fixedCapacityArrayQueue(collection.size());
		enqueueAll(result, collection);
		return result;
	}


	// ********** stack queue factory methods **********

	/**
	 * Return a LIFO queue.
	 */
	public static <E> StackQueue<E> stackQueue() {
		return adapt(new ArrayStack<E>());
	}

	/**
	 * Adapt the specified stack to the {@link Queue} interface,
	 * implementing a LIFO queue.
	 */
	public static <E> StackQueue<E> adapt(Stack<E> stack) {
		return new StackQueue<>(stack);
	}


	// ********** priority queue factory methods **********

	/**
	 * Return a priority queue that returns its elements in
	 * {@linkplain Comparable natural order}.
	 */
	public static <E extends Comparable<E>> PriorityQueue<E> priorityQueue() {
		return priorityQueue(10);
	}

	/**
	 * Return a priority queue that returns its elements in
	 * {@linkplain Comparable natural order} and has the specified initial capacity.
	 */
	public static <E extends Comparable<E>> PriorityQueue<E> priorityQueue(int initialCapacity) {
		return priorityQueue(ComparatorTools.<E>naturalComparator(), initialCapacity);
	}

	/**
	 * Return a priority queue whose elements are returned in
	 * the order determined by the specified comparator.
	 */
	public static <E> PriorityQueue<E> priorityQueue(Comparator<? super E> comparator) {
		return priorityQueue(comparator, 10);
	}

	/**
	 * Return a priority queue whose elements are returned in
	 * the order determined by the specified comparator
	 * and has the specified initial capacity.
	 */
	public static <E> PriorityQueue<E> priorityQueue(Comparator<? super E> comparator, int initialCapacity) {
		return new PriorityQueue<>(comparator, initialCapacity);
	}

	/**
	 * Construct a priority queue with the specified initial full array of elements.
	 * The priority queue will return its elements in {@linkplain Comparable natural order}.
	 * The array of elements should hold elements at
	 * every index except 0. The queue will <em>not</em> copy the
	 * elements from the supplied array; i.e. the queue will directly use and manipulate
	 * the supplied array.
	 */
	public static <E extends Comparable<E>> PriorityQueue<E> priorityQueue(E[] initialElements) {
		return priorityQueue(initialElements, initialElements.length - 1);
	}

	/**
	 * Construct a priority queue with the specified initial array of elements and size.
	 * The priority queue will return its elements in {@linkplain Comparable natural order}.
	 * The array of elements should hold elements at contiguous indices from
	 * 1 to the the specified size, inclusive. The array should <em>not</em>
	 * hold an element at index 0. The queue will <em>not</em> copy the
	 * elements from the supplied array; i.e. the queue will directly use and manipulate
	 * the supplied array.
	 */
	public static <E extends Comparable<E>> PriorityQueue<E> priorityQueue(E[] initialElements, int size) {
		return priorityQueue(ComparatorTools.<E>naturalComparator(), initialElements, size);
	}

	/**
	 * Construct a priority queue with the specified comparator and
	 * initial full array of elements.
	 * The array of elements should hold elements at
	 * every index except 0. The queue will <em>not</em> copy the
	 * elements from the supplied array; i.e. the queue will directly use and manipulate
	 * the supplied array.
	 */
	public static <E> PriorityQueue<E> priorityQueue(Comparator<? super E> comparator, E[] initialElements) {
		return priorityQueue(comparator, initialElements, initialElements.length - 1);
	}

	/**
	 * Construct a priority queue with the specified comparator,
	 * initial array of elements, and size. The array of elements
	 * should hold elements at contiguous indices from
	 * 1 to the the specified size, inclusive. The array should <em>not</em>
	 * hold an element at index 0. The queue will <em>not</em> copy the
	 * elements from the supplied array; i.e. the queue will directly use and manipulate
	 * the supplied array.
	 */
	public static <E> PriorityQueue<E> priorityQueue(Comparator<? super E> comparator, E[] initialElements, int size) {
		return new PriorityQueue<>(comparator, initialElements, size);
	}


	// ********** synchronized queue factory methods **********

	/**
	 * Return a synchronized queue.
	 */
	public static <E> SynchronizedQueue<E> synchronizedQueue() {
		ArrayQueue<E> queue = arrayQueue();
		return synchronizedQueue(queue);
	}

	/**
	 * Return a queue that synchronizes with specified mutex.
	 */
	public static <E> SynchronizedQueue<E> synchronizedQueue(Object mutex) {
		ArrayQueue<E> queue = arrayQueue();
		return synchronizedQueue(queue, mutex);
	}

	/**
	 * Return a queue that synchronizes the specified queue.
	 */
	public static <E> SynchronizedQueue<E> synchronizedQueue(Queue<E> queue) {
		return new SynchronizedQueue<>(queue);
	}

	/**
	 * Return a queue that synchronizes the specified queue
	 * with specified mutex.
	 */
	public static <E> SynchronizedQueue<E> synchronizedQueue(Queue<E> queue, Object mutex) {
		return new SynchronizedQueue<>(queue, mutex);
	}


	// ********** misc queue factory methods **********

	/**
	 * Adapt the specified deque to the {@link Queue} interface.
	 */
	public static <E> DequeQueue<E> adapt(Deque<E> deque) {
		return new DequeQueue<>(deque);
	}

	/**
	 * Adapt the specified list to the {@link Queue} interface.
	 */
	public static <E> ListQueue<E> adapt(List<E> list) {
		return new ListQueue<>(list);
	}

	/**
	 * Return an unmodifiable empty queue.
	 */
	public static <E> Queue<E> emptyQueue() {
		return EmptyQueue.instance();
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
