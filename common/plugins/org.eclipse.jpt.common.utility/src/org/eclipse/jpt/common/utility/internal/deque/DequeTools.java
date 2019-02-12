/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Deque} utility methods.
 */
public final class DequeTools {

	// ********** enqueue all **********

	/**
	 * Enqueue all the elements returned by the specified iterable
	 * on the specified deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueTailAll(Deque<? super E> deque, Iterable<? extends E> iterable) {
		return enqueueTailAll(deque, iterable.iterator());
	}

	/**
	 * Enqueue all the elements returned by the specified iterable
	 * on the specified deque's head.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueHeadAll(Deque<? super E> deque, Iterable<? extends E> iterable) {
		return enqueueHeadAll(deque, iterable.iterator());
	}

	/**
	 * Enqueue all the elements returned by the specified iterator
	 * on the specified deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueTailAll(Deque<? super E> deque, Iterator<? extends E> iterator) {
		return iterator.hasNext() && enqueueTailAll_(deque, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean enqueueTailAll_(Deque<? super E> deque, Iterator<? extends E> iterator) {
		do {
			deque.enqueueTail(iterator.next());
		} while (iterator.hasNext());
		return true;
	}

	/**
	 * Enqueue all the elements returned by the specified iterator
	 * on the specified deque's head.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueHeadAll(Deque<? super E> deque, Iterator<? extends E> iterator) {
		return iterator.hasNext() && enqueueHeadAll_(deque, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean enqueueHeadAll_(Deque<? super E> deque, Iterator<? extends E> iterator) {
		do {
			deque.enqueueHead(iterator.next());
		} while (iterator.hasNext());
		return true;
	}

	/**
	 * Enqueue all the elements in the specified array
	 * on the specified deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueTailAll(Deque<? super E> deque, E... array) {
		int len = array.length;
		return (len != 0) && enqueueTailAll_(deque, array, len);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean enqueueTailAll_(Deque<? super E> deque, E[] array, int arrayLength) {
		int i = 0;
		do {
			deque.enqueueTail(array[i++]);
		} while (i < arrayLength);
		return true;
	}

	/**
	 * Enqueue all the elements in the specified array
	 * on the specified deque's head.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean enqueueHeadAll(Deque<? super E> deque, E... array) {
		int len = array.length;
		return (len != 0) && enqueueHeadAll_(deque, array, len);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean enqueueHeadAll_(Deque<? super E> deque, E[] array, int arrayLength) {
		int i = 0;
		do {
			deque.enqueueHead(array[i++]);
		} while (i < arrayLength);
		return true;
	}


	// ********** drain **********

	/**
	 * Drain all the elements from the specified deque's head and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> drainHead(Deque<? extends E> deque) {
		ArrayList<E> result = new ArrayList<E>();
		drainHeadTo(deque, result);
		return result;
	}

	/**
	 * Drain all the elements from the specified deque's tail and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> drainTail(Deque<? extends E> deque) {
		ArrayList<E> result = new ArrayList<E>();
		drainTailTo(deque, result);
		return result;
	}

	/**
	 * Drain all the elements from the specified deque's head and add them to the
	 * specified collection.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainHeadTo(Deque<? extends E> deque, Collection<? super E> collection) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, collection);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainHeadTo_(Deque<? extends E> deque, Collection<? super E> collection) {
		do {
			collection.add(deque.dequeueHead());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's tail and add them to the
	 * specified collection.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainTailTo(Deque<? extends E> deque, Collection<? super E> collection) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, collection);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainTailTo_(Deque<? extends E> deque, Collection<? super E> collection) {
		do {
			collection.add(deque.dequeueTail());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's head
	 * to the specified list at the specified index.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainHeadTo(Deque<? extends E> deque, List<? super E> list, int index) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, list, index);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainHeadTo_(Deque<? extends E> deque, List<? super E> list, int index) {
		return (index == list.size()) ? drainHeadTo_(deque, list) : list.addAll(index, drainHead(deque));
	}

	/**
	 * Drain all the elements from the specified deque's tail
	 * to the specified list at the specified index.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainTailTo(Deque<? extends E> deque, List<? super E> list, int index) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, list, index);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainTailTo_(Deque<? extends E> deque, List<? super E> list, int index) {
		return (index == list.size()) ? drainTailTo_(deque, list) : list.addAll(index, drainTail(deque));
	}

	/**
	 * Drain all the elements from the specified deque's head and push them on the
	 * specified stack.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainHeadTo(Deque<? extends E> deque, Stack<? super E> stack) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, stack);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainHeadTo_(Deque<? extends E> deque, Stack<? super E> stack) {
		do {
			stack.push(deque.dequeueHead());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's tail and push them on the
	 * specified stack.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainTailTo(Deque<? extends E> deque, Stack<? super E> stack) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, stack);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainTailTo_(Deque<? extends E> deque, Stack<? super E> stack) {
		do {
			stack.push(deque.dequeueTail());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's head and enqueue them
	 * on the specified queue.
	 * Return whether the deque changed as a result.
	 */
	public static <E> boolean drainHeadTo(Deque<? extends E> deque, Queue<? super E> queue) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, queue);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainHeadTo_(Deque<? extends E> deque, Queue<? super E> queue) {
		do {
			queue.enqueue(deque.dequeueHead());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's tail and enqueue them
	 * on the specified queue.
	 * Return whether the first deque changed as a result.
	 */
	public static <E> boolean drainTailTo(Deque<? extends E> deque, Queue<? super E> queue) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, queue);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <E> boolean drainTailTo_(Deque<? extends E> deque, Queue<? super E> queue) {
		do {
			queue.enqueue(deque.dequeueTail());
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the first specified deque's head and enqueue them
	 * on the second specified deque's tail.
	 * Return whether the first deque changed as a result.
	 */
	public static <E> boolean drainHeadTo(Deque<? extends E> deque1, Deque<? super E> deque2) {
		return ( ! deque1.isEmpty()) && drainHeadTo_(deque1, deque2);
	}

	/**
	 * assume deque 1 is not empty
	 */
	private static <E> boolean drainHeadTo_(Deque<? extends E> deque1, Deque<? super E> deque2) {
		do {
			deque2.enqueueTail(deque1.dequeueHead());
		} while ( ! deque1.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the first specified deque's tail and enqueue them
	 * on the second specified deque's head.
	 * Return whether the first deque changed as a result.
	 */
	public static <E> boolean drainTailTo(Deque<? extends E> deque1, Deque<? super E> deque2) {
		return ( ! deque1.isEmpty()) && drainTailTo_(deque1, deque2);
	}

	/**
	 * assume deque 1 is not empty
	 */
	private static <E> boolean drainTailTo_(Deque<? extends E> deque1, Deque<? super E> deque2) {
		do {
			deque2.enqueueHead(deque1.dequeueTail());
		} while ( ! deque1.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's head, passing each element to the
	 * specified key transformer. Map the generated key to its element.
	 */
	public static <K, V, E extends V> boolean drainHeadTo(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, map, keyTransformer);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <K, V, E extends V> boolean drainHeadTo_(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		do {
			MapTools.add(map, deque.dequeueHead(), keyTransformer);
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's tail, passing each element to the
	 * specified key transformer. Map the generated key to its element.
	 */
	public static <K, V, E extends V> boolean drainTailTo(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, map, keyTransformer);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <K, V, E extends V> boolean drainTailTo_(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		do {
			MapTools.add(map, deque.dequeueTail(), keyTransformer);
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's head, passing each element to the
	 * specified key and value transformers. Add the generated key/value pairs
	 * to the specified map.
	 */
	public static <K, V, E> boolean drainHeadTo(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		return ( ! deque.isEmpty()) && drainHeadTo_(deque, map, keyTransformer, valueTransformer);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <K, V, E> boolean drainHeadTo_(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		do {
			MapTools.add(map, deque.dequeueHead(), keyTransformer, valueTransformer);
		} while ( ! deque.isEmpty());
		return true;
	}

	/**
	 * Drain all the elements from the specified deque's tail, passing each element to the
	 * specified key and value transformers. Add the generated key/value pairs
	 * to the specified map.
	 */
	public static <K, V, E> boolean drainTailTo(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		return ( ! deque.isEmpty()) && drainTailTo_(deque, map, keyTransformer, valueTransformer);
	}

	/**
	 * assume the deque is not empty
	 */
	private static <K, V, E> boolean drainTailTo_(Deque<E> deque, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		do {
			MapTools.add(map, deque.dequeueTail(), keyTransformer, valueTransformer);
		} while ( ! deque.isEmpty());
		return true;
	}


	// ********** array deque factory methods **********

	/**
	 * Return an empty array-based deque.
	 */
	public static <E> ArrayDeque<E> arrayDeque() {
		return arrayDeque(10);
	}

	/**
	 * Return an empty array-based deque with specified initial capacity.
	 */
	public static <E> ArrayDeque<E> arrayDeque(int initialCapacity) {
		return new ArrayDeque<E>(initialCapacity);
	}

	/**
	 * Return an array-based deque corresponding to the specified iterable.
	 */
	public static <E> ArrayDeque<E> arrayDeque(Iterable<? extends E> iterable) {
		return arrayDeque(iterable.iterator());
	}

	/**
	 * Return an array-based deque corresponding to the reverse of the specified iterable.
	 */
	public static <E> ArrayDeque<E> reverseArrayDeque(Iterable<? extends E> iterable) {
		return reverseArrayDeque(iterable.iterator());
	}

	/**
	 * Return an array-based deque corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayDeque<E> arrayDeque(Iterable<? extends E> iterable, int iterableSize) {
		return arrayDeque(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array-based deque corresponding to the reverse of the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayDeque<E> reverseArrayDeque(Iterable<? extends E> iterable, int iterableSize) {
		return reverseArrayDeque(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array-based deque corresponding to the specified iterator.
	 */
	public static <E> ArrayDeque<E> arrayDeque(Iterator<? extends E> iterator) {
		ArrayDeque<E> deque = arrayDeque();
		enqueueTailAll(deque, iterator);
		return deque;
	}

	/**
	 * Return an array-based deque corresponding to the reverse of the specified iterator.
	 */
	public static <E> ArrayDeque<E> reverseArrayDeque(Iterator<? extends E> iterator) {
		ArrayDeque<E> deque = arrayDeque();
		enqueueHeadAll(deque, iterator);
		return deque;
	}

	/**
	 * Return an array-based deque corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayDeque<E> arrayDeque(Iterator<? extends E> iterator, int iteratorSize) {
		ArrayDeque<E> deque = arrayDeque(iteratorSize);
		enqueueTailAll(deque, iterator);
		return deque;
	}

	/**
	 * Return an array-based deque corresponding to the reverse of the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayDeque<E> reverseArrayDeque(Iterator<? extends E> iterator, int iteratorSize) {
		ArrayDeque<E> deque = arrayDeque(iteratorSize);
		enqueueHeadAll(deque, iterator);
		return deque;
	}

	/**
	 * Return an array-based deque corresponding to the specified array.
	 */
	public static <E> ArrayDeque<E> arrayDeque(E... array) {
		ArrayDeque<E> deque = arrayDeque(array.length);
		enqueueTailAll(deque, array);
		return deque;
	}

	/**
	 * Return an array-based deque corresponding to the reverse of the specified array.
	 */
	public static <E> ArrayDeque<E> reverseArrayDeque(E... array) {
		ArrayDeque<E> deque = arrayDeque(array.length);
		enqueueHeadAll(deque, array);
		return deque;
	}


	// ********** linked deque factory methods **********

	/**
	 * Return an empty link-based deque with no node cache.
	 */
	public static <E> LinkedDeque<E> linkedDeque() {
		return linkedDeque(0);
	}

	/**
	 * Return an empty link-based deque
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> linkedDeque(int cacheSize) {
		return new LinkedDeque<E>(cacheSize);
	}

	/**
	 * Return a link-based deque corresponding to the specified iterable.
	 */
	public static <E> LinkedDeque<E> linkedDeque(Iterable<? extends E> iterable) {
		return linkedDeque(iterable, 0);
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified iterable.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(Iterable<? extends E> iterable) {
		return reverseLinkedDeque(iterable, 0);
	}

	/**
	 * Return a link-based deque corresponding to the specified iterable
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> linkedDeque(Iterable<? extends E> iterable, int cacheSize) {
		return linkedDeque(iterable.iterator(), cacheSize);
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified iterable
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(Iterable<? extends E> iterable, int cacheSize) {
		return reverseLinkedDeque(iterable.iterator(), cacheSize);
	}

	/**
	 * Return a link-based deque corresponding to the specified iterator.
	 */
	public static <E> LinkedDeque<E> linkedDeque(Iterator<? extends E> iterator) {
		return linkedDeque(iterator, 0);
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified iterator.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(Iterator<? extends E> iterator) {
		return reverseLinkedDeque(iterator, 0);
	}

	/**
	 * Return a link-based deque corresponding to the specified iterator
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> linkedDeque(Iterator<? extends E> iterator, int cacheSize) {
		LinkedDeque<E> deque = linkedDeque(cacheSize);
		enqueueTailAll(deque, iterator);
		return deque;
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified iterator
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(Iterator<? extends E> iterator, int cacheSize) {
		LinkedDeque<E> deque = linkedDeque(cacheSize);
		enqueueHeadAll(deque, iterator);
		return deque;
	}

	/**
	 * Return a link-based deque corresponding to the specified array.
	 */
	public static <E> LinkedDeque<E> linkedDeque(E... array) {
		return linkedDeque(array, 0);
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified array.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(E... array) {
		return reverseLinkedDeque(array, 0);
	}

	/**
	 * Return a link-based deque corresponding to the specified array
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> linkedDeque(E[] array, int cacheSize) {
		LinkedDeque<E> deque = linkedDeque(cacheSize);
		enqueueTailAll(deque, array);
		return deque;
	}

	/**
	 * Return a link-based deque corresponding to the reverse of the specified array
	 * with the specified node cache size.
	 * Specify a cache size of -1 for an unlimited cache.
	 */
	public static <E> LinkedDeque<E> reverseLinkedDeque(E[] array, int cacheSize) {
		LinkedDeque<E> deque = linkedDeque(cacheSize);
		enqueueHeadAll(deque, array);
		return deque;
	}


	// ********** fixed size array deque factory methods **********

	/**
	 * Return a fixed-capacity array deque with the specified capacity.
	 */
	public static <E> FixedCapacityArrayDeque<E> fixedCapacityArrayDeque(int capacity) {
		return new FixedCapacityArrayDeque<E>(capacity);
	}

	/**
	 * Return a fized-capacity array deque containing the elements of the specified
	 * collection. The deque will dequeue its head elements in the same
	 * order they are returned by the collection's iterator (i.e. the
	 * first element returned by the collection's iterator will be the
	 * first element returned by {@link Deque#dequeueHead()}).
	 * The deque's capacity will be match the collection's size.
	 */
	public static <E> FixedCapacityArrayDeque<E> fixedCapacityArrayDeque(Collection<? extends E> collection) {
		FixedCapacityArrayDeque<E> deque = fixedCapacityArrayDeque(collection.size());
		enqueueTailAll(deque, collection);
		return deque;
	}

	/**
	 * Return a fized-capacity array deque containing the elements of the specified
	 * collection. The deque will dequeue its tail elements in the same
	 * order they are returned by the collection's iterator (i.e. the
	 * first element returned by the collection's iterator will be the
	 * first element returned by {@link Deque#dequeueTail()}).
	 * The deque's capacity will be match the collection's size.
	 */
	public static <E> FixedCapacityArrayDeque<E> reverseFixedCapacityArrayDeque(Collection<? extends E> collection) {
		FixedCapacityArrayDeque<E> deque = fixedCapacityArrayDeque(collection.size());
		enqueueHeadAll(deque, collection);
		return deque;
	}


	// ********** priority deque factory methods **********

	/**
	 * Return a priority deque that returns its elements in
	 * {@linkplain Comparable natural order}.
	 */
	public static <E extends Comparable<E>> PriorityDeque<E> priorityDeque() {
		return priorityDeque(10);
	}

	/**
	 * Return a priority deque that returns its elements in
	 * {@linkplain Comparable natural order} and has the specified initial capacity.
	 */
	public static <E extends Comparable<E>> PriorityDeque<E> priorityDeque(int initialCapacity) {
		return priorityDeque(ComparatorTools.<E>naturalComparator(), initialCapacity);
	}

	/**
	 * Return a priority deque whose elements are returned in
	 * the order determined by the specified comparator.
	 */
	public static <E> PriorityDeque<E> priorityDeque(Comparator<? super E> comparator) {
		return priorityDeque(comparator, 10);
	}

	/**
	 * Return a priority deque whose elements are returned in
	 * the order determined by the specified comparator
	 * and has the specified initial capacity.
	 */
	public static <E> PriorityDeque<E> priorityDeque(Comparator<? super E> comparator, int initialCapacity) {
		return new PriorityDeque<E>(comparator, initialCapacity);
	}


	// ********** fixed size priority deque factory methods **********

	/**
	 * Return a fixed-capacity priority deque that returns its elements in
	 * {@linkplain Comparable natural order} and has the specified capacity.
	 */
	public static <E extends Comparable<E>> FixedCapacityPriorityDeque<E> fixedCapacityPriorityDeque(int capacity) {
		return fixedCapacityPriorityDeque(ComparatorTools.<E>naturalComparator(), capacity);
	}

	/**
	 * Return a fixed-capacity priority deque whose elements are returned in
	 * the order determined by the specified comparator
	 * and has the specified capacity.
	 */
	public static <E> FixedCapacityPriorityDeque<E> fixedCapacityPriorityDeque(Comparator<? super E> comparator, int capacity) {
		return new FixedCapacityPriorityDeque<E>(comparator, capacity);
	}


	// ********** synchronized deque factory methods **********

	/**
	 * Return a synchronized deque.
	 */
	public static <E> SynchronizedDeque<E> synchronizedDeque() {
		ArrayDeque<E> deque = arrayDeque();
		return synchronizedDeque(deque);
	}

	/**
	 * Return a deque that synchronizes the specified deque.
	 */
	public static <E> SynchronizedDeque<E> synchronizedDeque(Object mutex) {
		ArrayDeque<E> deque = arrayDeque();
		return synchronizedDeque(deque, mutex);
	}

	/**
	 * Return a deque that synchronizes the specified deque.
	 */
	public static <E> SynchronizedDeque<E> synchronizedDeque(Deque<E> deque) {
		return new SynchronizedDeque<E>(deque);
	}

	/**
	 * Return a deque that synchronizes the specified deque
	 * with specified mutex.
	 */
	public static <E> SynchronizedDeque<E> synchronizedDeque(Deque<E> deque, Object mutex) {
		return new SynchronizedDeque<E>(deque, mutex);
	}


	// ********** misc deque factory methods **********

	/**
	 * Adapt the specified list to the {@link Deque} interface.
	 */
	public static <E> ListDeque<E> adapt(List<E> list) {
		return new ListDeque<E>(list);
	}

	/**
	 * Return a deque that reverses the specified deque.
	 */
	public static <E> Deque<E> reverse(Deque<E> deque) {
		return new ReverseDeque<E>(deque);
	}

	/**
	 * Return an unmodifiable empty deque.
	 */
	public static <E> Deque<E> emptyDeque() {
		return EmptyDeque.instance();
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private DequeTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
