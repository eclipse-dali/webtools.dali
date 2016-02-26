/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Stack} utility methods.
 */
public class StackTools {

	// ********** push all **********

	/**
	 * Push all the elements returned by the specified iterable
	 * on the specified stack.
	 * Return whether the stack changed as a result.
	 */
	public static <E> boolean pushAll(Stack<? super E> stack, Iterable<? extends E> iterable) {
		return pushAll(stack, iterable.iterator());
	}

	/**
	 * Push all the elements returned by the specified iterator
	 * on the specified stack.
	 * Return whether the stack changed as a result.
	 */
	public static <E> boolean pushAll(Stack<? super E> stack, Iterator<? extends E> iterator) {
		return iterator.hasNext() && pushAll_(stack, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean pushAll_(Stack<? super E> stack, Iterator<? extends E> iterator) {
		do {
			stack.push(iterator.next());
		} while (iterator.hasNext());
		return true;
	}

	/**
	 * Push all the elements in the specified array
	 * on the specified stack.
	 * Return whether the stack changed as a result.
	 */
	@SafeVarargs
	public static <E> boolean pushAll(Stack<? super E> stack, E... array) {
		int len = array.length;
		return (len != 0) && pushAll_(stack, array, len);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean pushAll_(Stack<? super E> stack, E[] array, int arrayLength) {
		int i = 0;
		do {
			stack.push(array[i++]);
		} while (i < arrayLength);
		return true;
	}


	// ********** pop all **********

	/**
	 * Pop all the elements from the specified stack and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> popAll(Stack<? extends E> stack) {
		ArrayList<E> result = new ArrayList<>();
		popAllTo(stack, result);
		return result;
	}

	/**
	 * Pop all the elements from the specified stack and add them to the
	 * specified collection.
	 * Return whether the stack changed as a result.
	 */
	public static <E> boolean popAllTo(Stack<? extends E> stack, Collection<? super E> collection) {
		return ( ! stack.isEmpty()) && popAllTo_(stack, collection);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <E> boolean popAllTo_(Stack<? extends E> stack, Collection<? super E> collection) {
		do {
			collection.add(stack.pop());
		} while ( ! stack.isEmpty());
		return true;
	}

	/**
	 * Pop all the elements from the specified stack
	 * to the specified list at the specified index.
	 * Return whether the stack changed as a result.
	 */
	public static <E> boolean popAllTo(Stack<? extends E> stack, List<? super E> list, int index) {
		return ( ! stack.isEmpty()) && popAllTo_(stack, list, index);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <E> boolean popAllTo_(Stack<? extends E> stack, List<? super E> list, int index) {
		return (index == list.size()) ? popAllTo_(stack, list) : list.addAll(index, popAll(stack));
	}

	/**
	 * Pop all the elements from the specified stack and enqueue them on the
	 * specified queue.
	 * Return whether the stack changed as a result.
	 */
	public static <E> boolean popAllTo(Stack<? extends E> stack, Queue<? super E> queue) {
		return ( ! stack.isEmpty()) && popAllTo_(stack, queue);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <E> boolean popAllTo_(Stack<? extends E> stack, Queue<? super E> queue) {
		do {
			queue.enqueue(stack.pop());
		} while ( ! stack.isEmpty());
		return true;
	}

	/**
	 * Pop all the elements from the first specified stack and push them
	 * on the second specified stack.
	 * Return whether the first stack changed as a result.
	 */
	public static <E> boolean popAllTo(Stack<? extends E> stack1, Stack<? super E> stack2) {
		return ( ! stack1.isEmpty()) && popAllTo_(stack1, stack2);
	}

	/**
	 * assume stack 1 is not empty
	 */
	private static <E> boolean popAllTo_(Stack<? extends E> stack1, Stack<? super E> stack2) {
		do {
			stack2.push(stack1.pop());
		} while ( ! stack1.isEmpty());
		return true;
	}

	/**
	 * Pop all the elements from the specified stack, passing each element to the
	 * specified key transformer. Map the generated key to its element.
	 * Return whether the stack changed as a result.
	 */
//	public static <K, V, E extends V> boolean popAllTo(Stack<E> stack, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
	public static <K, V> boolean popAllTo(Stack<? extends V> stack, Map<K, V> map, Transformer<? super V, ? extends K> keyTransformer) {
		return ( ! stack.isEmpty()) && popAllTo_(stack, map, keyTransformer);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <K, V, E extends V> boolean popAllTo_(Stack<E> stack, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer) {
		do {
			MapTools.add(map, stack.pop(), keyTransformer);
		} while ( ! stack.isEmpty());
		return true;
	}

	/**
	 * Pop all the elements from the specified stack, passing each element to the
	 * specified key and value transformers. Add the generated key/value pairs
	 * to the specified map.
	 * Return whether the stack changed as a result.
	 */
	public static <K, V, E> boolean popAllTo(Stack<E> stack, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		return ( ! stack.isEmpty()) && popAllTo_(stack, map, keyTransformer, valueTransformer);
	}

	/**
	 * assume the stack is not empty
	 */
	private static <K, V, E> boolean popAllTo_(Stack<E> stack, Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		do {
			MapTools.add(map, stack.pop(), keyTransformer, valueTransformer);
		} while ( ! stack.isEmpty());
		return true;
	}


	// ********** array stack factory methods **********

	/**
	 * Return an array-based LIFO stack corresponding.
	 */
	public static <E> ArrayStack<E> arrayStack() {
		return arrayStack(10);
	}

	/**
	 * Return an array-based LIFO stack corresponding with the specified initial capacity.
	 */
	public static <E> ArrayStack<E> arrayStack(int initialCapacity) {
		return new ArrayStack<>(initialCapacity);
	}

	/**
	 * Return an array-based LIFO stack corresponding to the specified iterable.
	 * The stack will pop its elements in reverse of the
	 * order they are returned by the iterable's iterator (i.e. the
	 * last element returned by the iterable's iterator will be the
	 * first element returned by {@link Stack#pop()}; the first, last.).
	 */
	public static <E> ArrayStack<E> arrayStack(Iterable<? extends E> iterable) {
		return arrayStack(iterable.iterator());
	}

	/**
	 * Return an array-based LIFO stack corresponding to the specified iterable.
	 * The stack will pop its elements in reverse of the
	 * order they are returned by the iterable's iterator (i.e. the
	 * last element returned by the iterable's iterator will be the
	 * first element returned by {@link Stack#pop()}; the first, last.).
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterable<? extends E> iterable, int iterableSize) {
		return arrayStack(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array-based LIFO stack corresponding to the specified iterator.
	 * The stack will pop its elements in reverse of the
	 * order they are returned by the iterator (i.e. the
	 * last element returned by the iterator will be the
	 * first element returned by {@link Stack#pop()}; the first, last.).
	 */
	public static <E> ArrayStack<E> arrayStack(Iterator<? extends E> iterator) {
		ArrayStack<E> result = arrayStack();
		pushAll(result, iterator);
		return result;
	}

	/**
	 * Return an array-based LIFO stack corresponding to the specified iterator.
	 * The stack will pop its elements in reverse of the
	 * order they are returned by the iterator (i.e. the
	 * last element returned by the iterator will be the
	 * first element returned by {@link Stack#pop()}; the first, last.).
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterator<? extends E> iterator, int iteratorSize) {
		ArrayStack<E> result = arrayStack(iteratorSize);
		pushAll(result, iterator);
		return result;
	}

	/**
	 * Return an array-based LIFO stack corresponding to the specified array.
	 */
	@SafeVarargs
	public static <E> ArrayStack<E> arrayStack(E... array) {
		ArrayStack<E> result = arrayStack(array.length);
		pushAll(result, array);
		return result;
	}


	// ********** linked stack factory methods **********

	/**
	 * Return an empty link-based LIFO stack.
	 */
	public static <E> LinkedStack<E> linkedStack() {
		return new LinkedStack<>();
	}

	/**
	 * Return a link-based LIFO stack corresponding to the specified iterable.
	 */
	public static <E> LinkedStack<E> linkedStack(Iterable<? extends E> iterable) {
		return linkedStack(iterable.iterator());
	}

	/**
	 * Return a link-based LIFO stack corresponding to the specified iterator.
	 */
	public static <E> LinkedStack<E> linkedStack(Iterator<? extends E> iterator) {
		LinkedStack<E> result = linkedStack();
		pushAll(result, iterator);
		return result;
	}

	/**
	 * Return a link-based LIFO stack corresponding to the specified array.
	 */
	@SafeVarargs
	public static <E> LinkedStack<E> linkedStack(E... array) {
		LinkedStack<E> result = linkedStack();
		pushAll(result, array);
		return result;
	}


	// ********** concurrent stack factory methods **********

	/**
	 * Return an empty concurrent LIFO stack.
	 */
	public static <E> ConcurrentStack<E> concurrentStack() {
		return new ConcurrentStack<>();
	}

	/**
	 * Return a concurrent LIFO stack corresponding to the specified iterable.
	 */
	public static <E> ConcurrentStack<E> concurrentStack(Iterable<? extends E> iterable) {
		return concurrentStack(iterable.iterator());
	}

	/**
	 * Return a concurrent LIFO stack corresponding to the specified iterator.
	 */
	public static <E> ConcurrentStack<E> concurrentStack(Iterator<? extends E> iterator) {
		ConcurrentStack<E> result = concurrentStack();
		pushAll(result, iterator);
		return result;
	}

	/**
	 * Return a concurrent LIFO stack corresponding to the specified array.
	 */
	@SafeVarargs
	public static <E> ConcurrentStack<E> concurrentStack(E... array) {
		ConcurrentStack<E> result = concurrentStack();
		pushAll(result, array);
		return result;
	}


	// ********** fixed capacity stack factory methods **********

	/**
	 * Return a fixed-capacity stack with the specified capacity.
	 */
	public static <E> FixedCapacityArrayStack<E> fixedCapacityArrayStack(int capacity) {
		return new FixedCapacityArrayStack<>(capacity);
	}

	/**
	 * Return a fized-capacity stack containing the elements of the specified
	 * collection. The stack will pop its elements in reverse of the
	 * order they are returned by the collection's iterator (i.e. the
	 * last element returned by the collection's iterator will be the
	 * first element returned by {@link Stack#pop()}; the first, last.).
	 */
	public static <E> FixedCapacityArrayStack<E> fixedCapacityArrayStack(Collection<? extends E> collection) {
		FixedCapacityArrayStack<E> result = fixedCapacityArrayStack(collection.size());
		pushAll(result, collection);
		return result;
	}


	// ********** synchronized stack factory methods **********

	/**
	 * Return a synchronized stack.
	 */
	public static <E> SynchronizedStack<E> synchronizedStack() {
		ArrayStack<E> stack = arrayStack();
		return synchronizedStack(stack);
	}

	/**
	 * Return a stack that synchronizes with specified mutex.
	 */
	public static <E> SynchronizedStack<E> synchronizedStack(Object mutex) {
		LinkedStack<E> stack = linkedStack();
		return synchronizedStack(stack, mutex);
	}

	/**
	 * Return a stack that synchronizes the specified stack.
	 */
	public static <E> SynchronizedStack<E> synchronizedStack(Stack<E> stack) {
		return new SynchronizedStack<>(stack);
	}

	/**
	 * Return a stack that synchronizes the specified stack
	 * with specified mutex.
	 */
	public static <E> SynchronizedStack<E> synchronizedStack(Stack<E> stack, Object mutex) {
		return new SynchronizedStack<>(stack, mutex);
	}


	// ********** misc stack factory methods **********

	/**
	 * Adapt the specified list to the {@link Stack} interface.
	 */
	public static <E> ListStack<E> adapt(List<E> list) {
		return new ListStack<>(list);
	}

	/**
	 * Adapt the specified deque to the {@link Stack} interface.
	 */
	public static <E> DequeStack<E> adapt(Deque<E> deque) {
		return new DequeStack<>(deque);
	}

	/**
	 * Return an unmodifiable empty LIFO stack.
	 */
	public static <E> Stack<E> emptyStack() {
		return EmptyStack.instance();
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private StackTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
