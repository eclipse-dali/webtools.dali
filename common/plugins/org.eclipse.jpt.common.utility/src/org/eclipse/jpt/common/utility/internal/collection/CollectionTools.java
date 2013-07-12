/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Collection} utility methods.
 * @see org.eclipse.jpt.common.utility.internal.ArrayTools
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools
 * @see org.eclipse.jpt.common.utility.internal.iterator.IteratorTools
 * @see ListTools
 */
public final class CollectionTools {

	// ********** add all **********

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable) {
		return addAll(collection, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable, int iterableSize) {
		return addAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator) {
		return iterator.hasNext() && addAll_(collection, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, Iterator<? extends E> iterator) {
		boolean modified = false;
		while (iterator.hasNext()) {
			modified |= collection.add(iterator.next());
		}
		return modified;
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() && collection.addAll(ListTools.list(iterator, iteratorSize));
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, E... array) {
		return (array.length != 0) && addAll_(collection, array);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, E... array) {
		boolean modified = false;
		for (E element : array) {
			modified |= collection.add(element);
		}
		return modified;
	}

	/**
	 * Dequeue all the elements from the specified queue and add them
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Queue<? extends E> queue) {
		return ( ! queue.isEmpty()) && addAll_(collection, queue);
	}

	/**
	 * assume the queue is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, Queue<? extends E> queue) {
		boolean modified = false;
		while ( ! queue.isEmpty()) {
			modified |= collection.add(queue.dequeue());
		}
		return modified;
	}

	/**
	 * Dequeue all the elements from the specified queue and add them
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified queue size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Queue<? extends E> queue, int queueSize) {
		return ( ! queue.isEmpty()) && collection.addAll(ListTools.list(queue, queueSize));
	}

	/**
	 * Pop all the elements from the specified stack and add them
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Stack<? extends E> stack) {
		return ( ! stack.isEmpty()) && addAll_(collection, stack);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, Stack<? extends E> stack) {
		boolean modified = false;
		while ( ! stack.isEmpty()) {
			modified |= collection.add(stack.pop());
		}
		return modified;
	}

	/**
	 * Pop all the elements from the specified stack and add them
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified stack size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Stack<? extends E> stack, int stackSize) {
		return ( ! stack.isEmpty()) && collection.addAll(ListTools.list(stack, stackSize));
	}


	// ********** contains all **********

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterable.
	 */
	public static boolean containsAll(Collection<?> collection, Iterable<?> iterable) {
		return containsAll(collection, iterable.iterator());
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterator, retrieving elements from the iterator
	 * until one is not found in the collection.
	 */
	public static boolean containsAll(Collection<?> collection, Iterator<?> iterator) {
		while (iterator.hasNext()) {
			if ( ! collection.contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified array.
	 */
	public static boolean containsAll(Collection<?> collection, Object... array) {
		for (int i = array.length; i-- > 0; ) {
			if ( ! collection.contains(array[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified queue, dequeueing elements from the queue
	 * until one is not found in the collection.
	 */
	public static boolean containsAll(Collection<?> collection, Queue<?> queue) {
		while ( ! queue.isEmpty()) {
			if ( ! collection.contains(queue.dequeue())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified stack, popping elements from the stack
	 * until one is not found in the collection.
	 */
	public static boolean containsAll(Collection<?> collection, Stack<?> stack) {
		while ( ! stack.isEmpty()) {
			if ( ! collection.contains(stack.pop())) {
				return false;
			}
		}
		return true;
	}


	// ********** filter **********

	/**
	 * Return a new collection with the filtered
	 * elements of the specified collection.
	 */
	public static <E> HashBag<E> filter(Collection<? extends E> collection, Predicate<E> filter) {
		HashBag<E> result = new HashBag<E>(collection.size());
		for (E e : collection) {
			if (filter.evaluate(e)) {
				result.add(e);
			}
		}
		return result;
	}


	// ********** remove all **********

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable) {
		return removeAll(collection, iterable.iterator());
	}

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return removeAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator) {
		return iterator.hasNext() && collection.removeAll(set(iterator));
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		return iterator.hasNext() && collection.removeAll(set(iterator, iteratorSize));
	}

	/**
	 * Remove all the elements in the specified array
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Object... array) {
		return (array.length != 0) && collection.removeAll(set(array));
	}

	/**
	 * Remove all the elements dequeued from the specified queue
	 * from the specified collection, draining the queue in the process.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Queue<?> queue) {
		return ( ! queue.isEmpty()) && collection.removeAll(set(queue));
	}

	/**
	 * Remove all the elements dequeued from the specified queue
	 * from the specified collection, draining the queue in the process.
	 * Return whether the collection changed as a result.
	 * The specified queue size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Queue<?> queue, int queueSize) {
		return ( ! queue.isEmpty()) && collection.removeAll(set(queue, queueSize));
	}

	/**
	 * Remove all the elements from the specified stack
	 * from the specified collection, draining the stack in the process.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Stack<?> stack) {
		return ( ! stack.isEmpty()) && collection.removeAll(set(stack));
	}

	/**
	 * Remove all the elements from the specified stack
	 * from the specified collection, draining the stack in the process.
	 * Return whether the collection changed as a result.
	 * The specified stack size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Stack<?> stack, int stackSize) {
		return ( ! stack.isEmpty()) && collection.removeAll(set(stack, stackSize));
	}


	// ********** remove all occurrences **********

	/**
	 * Remove all occurrences of the specified element
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAllOccurrences(Collection<?> collection, Object value) {
		boolean modified = false;
		Iterator<?> stream = collection.iterator();
		if (value == null) {
			while (stream.hasNext()) {
				if (stream.next() == null) {
					stream.remove();
					modified = true;
				}
			}
		} else {
			while (stream.hasNext()) {
				if (value.equals(stream.next())) {
					stream.remove();
					modified = true;
				}
			}
		}
		return modified;
	}


	// ********** retain all **********

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable) {
		return retainAll(collection, iterable.iterator());
	}

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return retainAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Retain only the elements in the specified iterator
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified iterator
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator, iteratorSize));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified array
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Object... array) {
		if (array.length > 0) {
			return collection.retainAll(set(array));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified queue
	 * in the specified collection, draining the queue in the process.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Queue<?> queue) {
		if ( ! queue.isEmpty()) {
			return collection.retainAll(set(queue));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified queue
	 * in the specified collection, draining the queue in the process.
	 * Return whether the collection changed as a result.
	 * The specified queue size is a performance hint.
	 */
	public static boolean retainAll(Collection<?> collection, Queue<?> queue, int queueSize) {
		if ( ! queue.isEmpty()) {
			return collection.retainAll(set(queue, queueSize));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified stack
	 * in the specified collection, draining the stack in the process.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Stack<?> stack) {
		if ( ! stack.isEmpty()) {
			return collection.retainAll(set(stack));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified stack
	 * in the specified collection, draining the stack in the process.
	 * Return whether the collection changed as a result.
	 * The specified stack size is a performance hint.
	 */
	public static boolean retainAll(Collection<?> collection, Stack<?> stack, int stackSize) {
		if ( ! stack.isEmpty()) {
			return collection.retainAll(set(stack, stackSize));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}


	// ********** transform **********

	/**
	 * Return a new collection with transformations of the
	 * elements in the specified collection.
	 */
	public static <E1, E2> HashBag<E2> transform(Collection<E1> collection, Transformer<E1, ? extends E2> transformer) {
		HashBag<E2> result = new HashBag<E2>(collection.size());
		for (E1 e : collection) {
			result.add(transformer.transform(e));
		}
		return result;
	}


	// ********** bag factory methods **********

	/**
	 * Return a bag corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable) {
		return bag(iterable.iterator());
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable, int iterableSize) {
		return bag(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator) {
		return bag(iterator, new HashBag<E>());
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator, int iteratorSize) {
		return bag(iterator, new HashBag<E>(iteratorSize));
	}

	private static <E> HashBag<E> bag(Iterator<? extends E> iterator, HashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified array.
	 */
	public static <E> HashBag<E> bag(E... array) {
		int len = array.length;
		HashBag<E> bag = new HashBag<E>(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified queue, draining the queue
	 * in the process.
	 */
	public static <E> HashBag<E> bag(Queue<? extends E> queue) {
		return bag(queue, new HashBag<E>());
	}

	/**
	 * Return a bag corresponding to the specified queue, draining the queue
	 * in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Queue<? extends E> queue, int queueSize) {
		return bag(queue, new HashBag<E>(queueSize));
	}

	private static <E> HashBag<E> bag(Queue<? extends E> queue, HashBag<E> set) {
		while ( ! queue.isEmpty()) {
			set.add(queue.dequeue());
		}
		return set;
	}

	/**
	 * Return a bag corresponding to the specified stack, draining the stack
	 * in the process.
	 */
	public static <E> HashBag<E> bag(Stack<? extends E> stack) {
		return bag(stack, new HashBag<E>());
	}

	/**
	 * Return a bag corresponding to the specified stack, draining the stack
	 * in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Stack<? extends E> stack, int stackSize) {
		return bag(stack, new HashBag<E>(stackSize));
	}

	private static <E> HashBag<E> bag(Stack<? extends E> stack, HashBag<E> set) {
		while ( ! stack.isEmpty()) {
			set.add(stack.pop());
		}
		return set;
	}


	// ********** identity bag factory methods **********

	/**
	 * Return an identity bag corresponding to the specified iterable.
	 */
	public static <E> IdentityHashBag<E> identityBag(Iterable<? extends E> iterable) {
		return identityBag(iterable.iterator());
	}

	/**
	 * Return an identity bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> IdentityHashBag<E> identityBag(Iterable<? extends E> iterable, int iterableSize) {
		return identityBag(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an identity bag corresponding to the specified iterator.
	 */
	public static <E> IdentityHashBag<E> identityBag(Iterator<? extends E> iterator) {
		return identityBag(iterator, new IdentityHashBag<E>());
	}

	/**
	 * Return an identity bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> IdentityHashBag<E> identityBag(Iterator<? extends E> iterator, int iteratorSize) {
		return identityBag(iterator, new IdentityHashBag<E>(iteratorSize));
	}

	private static <E> IdentityHashBag<E> identityBag(Iterator<? extends E> iterator, IdentityHashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return an identity bag corresponding to the specified array.
	 */
	public static <E> IdentityHashBag<E> identityBag(E... array) {
		int len = array.length;
		IdentityHashBag<E> bag = new IdentityHashBag<E>(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}


	// ********** collection factory methods **********

	/**
	 * Return a collection corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable) {
		return collection(iterable.iterator());
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable, int iterableSize) {
		return collection(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator) {
		return bag(iterator);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator, int iteratorSize) {
		return bag(iterator, iteratorSize);
	}

	/**
	 * Return a collection corresponding to the specified array.
	 */
	public static <E> HashBag<E> collection(E... array) {
		return bag(array);
	}

	/**
	 * Return a collection corresponding to the specified queue, draining the queue
	 * in the process.
	 */
	public static <E> HashBag<E> collection(Queue<? extends E> queue) {
		return bag(queue);
	}

	/**
	 * Return a collection corresponding to the specified queue, draining the queue
	 * in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Queue<? extends E> queue, int queueSize) {
		return bag(queue, queueSize);
	}

	/**
	 * Return a collection corresponding to the specified stack, draining the stack
	 * in the process.
	 */
	public static <E> HashBag<E> collection(Stack<? extends E> stack) {
		return bag(stack);
	}

	/**
	 * Return a collection corresponding to the specified stack, draining the stack
	 * in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Stack<? extends E> stack, int stackSize) {
		return bag(stack, stackSize);
	}


	// ********** set factory methods **********

	/**
	 * Return a set corresponding to the specified iterable.
	 */
	public static <E> HashSet<E> set(Iterable<? extends E> iterable) {
		return set(iterable.iterator());
	}

	/**
	 * Return a set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashSet<E> set(Iterable<? extends E> iterable, int iterableSize) {
		return set(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 */
	public static <E> HashSet<E> set(Iterator<? extends E> iterator) {
		return set(iterator, new HashSet<E>());
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashSet<E> set(Iterator<? extends E> iterator, int iteratorSize) {
		return set(iterator, new HashSet<E>(iteratorSize));
	}

	private static <E> HashSet<E> set(Iterator<? extends E> iterator, HashSet<E> set) {
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Return a set corresponding to the specified array.
	 */
	public static <E> HashSet<E> set(E... array) {
		HashSet<E> set = new HashSet<E>(array.length);
		for (int i = array.length; i-- > 0;) {
			set.add(array[i]);
		}
		return set;
	}

	/**
	 * Return a set corresponding to the specified queue, draining the queue
	 * in the process.
	 */
	public static <E> HashSet<E> set(Queue<? extends E> queue) {
		return set(queue, new HashSet<E>());
	}

	/**
	 * Return a set corresponding to the specified queue, draining the queue
	 * in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> HashSet<E> set(Queue<? extends E> queue, int queueSize) {
		return set(queue, new HashSet<E>(queueSize));
	}

	private static <E> HashSet<E> set(Queue<? extends E> queue, HashSet<E> set) {
		while ( ! queue.isEmpty()) {
			set.add(queue.dequeue());
		}
		return set;
	}

	/**
	 * Return a set corresponding to the specified stack, draining the stack
	 * in the process.
	 */
	public static <E> HashSet<E> set(Stack<? extends E> stack) {
		return set(stack, new HashSet<E>());
	}

	/**
	 * Return a set corresponding to the specified stack, draining the stack
	 * in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> HashSet<E> set(Stack<? extends E> stack, int stackSize) {
		return set(stack, new HashSet<E>(stackSize));
	}

	private static <E> HashSet<E> set(Stack<? extends E> stack, HashSet<E> set) {
		while ( ! stack.isEmpty()) {
			set.add(stack.pop());
		}
		return set;
	}


	// ********** sorted set factory methods **********

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterable<? extends E> iterable) {
		return sortedSet(iterable.iterator());
	}

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterable<? extends E> iterable, int iterableSize) {
		return sortedSet(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable
	 * and comparator.
	 */
	public static <E> TreeSet<E> sortedSet(Iterable<? extends E> iterable, Comparator<? super E> comparator) {
		return sortedSet(iterable.iterator(), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable
	 * and comparator.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Iterable<? extends E> iterable, Comparator<? super E> comparator, int iterableSize) {
		return sortedSet(iterable.iterator(), comparator, iterableSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterator<? extends E> iterator) {
		return sortedSet(iterator, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterator<? extends E> iterator, int iteratorSize) {
		return sortedSet(iterator, null, iteratorSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator
	 * and comparator.
	 */
	public static <E> TreeSet<E> sortedSet(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return sortedSet(ListTools.list(iterator), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator
	 * and comparator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Iterator<? extends E> iterator, Comparator<? super E> comparator, int iteratorSize) {
		return sortedSet(ListTools.list(iterator, iteratorSize), comparator);
	}

	private static <E> TreeSet<E> sortedSet(List<E> list, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(list);
		return sortedSet;
	}

	/**
	 * Return a sorted set corresponding to the specified array.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(E... array) {
		return sortedSet(array, null);
	}

	/**
	 * Return a sorted set corresponding to the specified array
	 * and comparator.
	 */
	public static <E> TreeSet<E> sortedSet(E[] array, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(Arrays.asList(array));
		return sortedSet;
	}

	/**
	 * Return a sorted set corresponding to the specified queue,
	 * draining the queue in the process.
	 */
	public static <E> TreeSet<E> sortedSet(Queue<? extends E> queue) {
		return sortedSet(queue, null);
	}

	/**
	 * Return a sorted set corresponding to the specified queue,
	 * draining the queue in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Queue<? extends E> queue, int queueSize) {
		return sortedSet(queue, null, queueSize);
	}

	/**
	 * Return a sorted set corresponding to the specified queue and comparator,
	 * draining the queue in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Queue<? extends E> queue, Comparator<? super E> comparator) {
		return sortedSet(ListTools.list(queue), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified queue and comparator,
	 * draining the queue in the process.
	 * The specified queue size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Queue<? extends E> queue, Comparator<? super E> comparator, int queueSize) {
		return sortedSet(ListTools.list(queue, queueSize), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified stack,
	 * draining the stack in the process.
	 */
	public static <E> TreeSet<E> sortedSet(Stack<? extends E> stack) {
		return sortedSet(stack, null);
	}

	/**
	 * Return a sorted set corresponding to the specified stack,
	 * draining the stack in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Stack<? extends E> stack, int stackSize) {
		return sortedSet(stack, null, stackSize);
	}

	/**
	 * Return a sorted set corresponding to the specified stack and comparator,
	 * draining the stack in the process.
	 */
	public static <E> TreeSet<E> sortedSet(Stack<? extends E> stack, Comparator<? super E> comparator) {
		return sortedSet(ListTools.list(stack), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified stack and comparator,
	 * draining the stack in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> TreeSet<E> sortedSet(Stack<? extends E> stack, Comparator<? super E> comparator, int stackSize) {
		return sortedSet(ListTools.list(stack, stackSize), comparator);
	}


	// ********** Old School Vector factory methods **********

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable) {
		return vector(iterable.iterator());
	}

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable, int size) {
		return vector(iterable.iterator(), size);
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator) {
		return vector(iterator, new Vector<E>());
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator, int size) {
		return vector(iterator, new Vector<E>(size));
	}

	private static <E> Vector<E> vector(Iterator<? extends E> iterator, Vector<E> v) {
		while (iterator.hasNext()) {
			v.addElement(iterator.next());
		}
		return v;
	}

	/**
	 * Return a vector corresponding to the specified array.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(E... array) {
		Vector<E> v = new Vector<E>(array.length);
		for (E item : array) {
			v.addElement(item);
		}
		return v;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CollectionTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
