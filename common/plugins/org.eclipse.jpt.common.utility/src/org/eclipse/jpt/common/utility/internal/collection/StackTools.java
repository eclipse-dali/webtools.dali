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
import java.util.Iterator;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;

/**
 * {@link Stack} utility methods.
 */
public class StackTools {

	// ********** push all **********

	/**
	 * Push all the elements returned by the specified iterable
	 * on the specified stack.
	 * Return the stack.
	 */
	public static <S extends Stack<? super E>, E> S pushAll(S stack, Iterable<? extends E> iterable) {
		return pushAll(stack, iterable.iterator());
	}

	/**
	 * Push all the elements returned by the specified iterator
	 * on the specified stack.
	 * Return the stack.
	 */
	public static <S extends Stack<? super E>, E> S pushAll(S stack, Iterator<? extends E> iterator) {
		while (iterator.hasNext()) {
			stack.push(iterator.next());
		}
		return stack;
	}

	/**
	 * Push all the elements in the specified array
	 * on the specified stack.
	 * Return the stack.
	 */
	public static <S extends Stack<? super E>, E> S pushAll(S stack, E... array) {
		for (E element : array) {
			stack.push(element);
		}
		return stack;
	}

	/**
	 * Dequeue all the elements from the specified queue and push them
	 * on the specified stack.
	 * Return the stack.
	 */
	public static <S extends Stack<? super E>, E> S pushAll(S stack, Queue<? extends E> queue) {
		while ( ! queue.isEmpty()) {
			stack.push(queue.dequeue());
		}
		return stack;
	}

	/**
	 * Pop all the elements from the second specified stack and push them
	 * on the first specified stack.
	 * Return the first stack.
	 * @see #popAllTo(Stack, Stack)
	 */
	public static <S extends Stack<? super E>, E> S pushAll(S stack1, Stack<? extends E> stack2) {
		while ( ! stack2.isEmpty()) {
			stack1.push(stack2.pop());
		}
		return stack1;
	}


	// ********** drain **********

	/**
	 * Pop all the elements from the specified stack and return them in a
	 * list.
	 */
	public static <E> ArrayList<E> popAll(Stack<? extends E> stack) {
		return popAllTo(stack, new ArrayList<E>());
	}

	/**
	 * Pop all the elements from the specified stack and add them to the
	 * specified collection.
	 * Return the collection.
	 */
	public static <C extends Collection<? super E>, E> C popAllTo(Stack<? extends E> stack, C collection) {
		while ( ! stack.isEmpty()) {
			collection.add(stack.pop());
		}
		return collection;
	}

	/**
	 * Pop all the elements from the specified stack and enqueue them on the
	 * specified queue.
	 * Return the queue.
	 */
	public static <Q extends Queue<? super E>, E> Q popAllTo(Stack<? extends E> stack, Q queue) {
		while ( ! stack.isEmpty()) {
			queue.enqueue(stack.pop());
		}
		return queue;
	}

	/**
	 * Pop all the elements from the first specified stack and push them
	 * on the second specified stack.
	 * Return the second stack.
	 * @see #pushAll(Stack, Stack)
	 */
	public static <S extends Stack<? super E>, E> S popAllTo(Stack<? extends E> stack1, S stack2) {
		while ( ! stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
		return stack2;
	}


	// ********** factory methods **********

	/**
	 * Return a stack corresponding to the specified iterable.
	 */
	public static <E> ArrayStack<E> stack(Iterable<? extends E> iterable) {
		return arrayStack(iterable);
	}

	/**
	 * Return a stack corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayStack<E> stack(Iterable<? extends E> iterable, int iterableSize) {
		return arrayStack(iterable, iterableSize);
	}

	/**
	 * Return a stack corresponding to the specified iterator.
	 */
	public static <E> ArrayStack<E> stack(Iterator<? extends E> iterator) {
		return arrayStack(iterator);
	}

	/**
	 * Return a stack corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayStack<E> stack(Iterator<? extends E> iterator, int iteratorSize) {
		return arrayStack(iterator, iteratorSize);
	}

	/**
	 * Return a stack corresponding to the specified array.
	 */
	public static <E> ArrayStack<E> stack(E... array) {
		return arrayStack(array);
	}

	/**
	 * Return an array-based stack corresponding to the specified iterable.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterable<? extends E> iterable) {
		return arrayStack(iterable.iterator());
	}

	/**
	 * Return an array-based stack corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterable<? extends E> iterable, int iterableSize) {
		return arrayStack(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array-based stack corresponding to the specified iterator.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterator<? extends E> iterator) {
		return pushAll(new ArrayStack<E>(), iterator);
	}

	/**
	 * Return an array-based stack corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayStack<E> arrayStack(Iterator<? extends E> iterator, int iteratorSize) {
		return pushAll(new ArrayStack<E>(iteratorSize), iterator);
	}

	/**
	 * Return an array-based stack corresponding to the specified array.
	 */
	public static <E> ArrayStack<E> arrayStack(E... array) {
		return pushAll(new ArrayStack<E>(array.length), array);
	}

	/**
	 * Return an linked list-based stack corresponding to the specified iterable.
	 */
	public static <E> LinkedStack<E> linkedStack(Iterable<? extends E> iterable) {
		return linkedStack(iterable.iterator());
	}

	/**
	 * Return an linked list-based stack corresponding to the specified iterator.
	 */
	public static <E> LinkedStack<E> linkedStack(Iterator<? extends E> iterator) {
		return pushAll(new LinkedStack<E>(), iterator);
	}

	/**
	 * Return an linked list-based stack corresponding to the specified array.
	 */
	public static <E> LinkedStack<E> linkedStack(E... array) {
		return pushAll(new LinkedStack<E>(), array);
	}

	/**
	 * Return an unmodifiable empty stack.
	 */
	public static <E> Stack<E> emptyStack() {
		return EmptyStack.<E>instance();
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
