/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.stack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Thread-safe implementation of the {@link Stack} interface.
 * This also provides protocol for suspending a thread until the
 * stack is empty or not empty, with optional time-outs.
 * @param <E> the type of elements maintained by the stack
 * @see StackTools
 */
public class SynchronizedStack<E>
	implements Stack<E>, Serializable
{
	/** Backing stack. */
	private final Stack<E> stack;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a synchronized stack that wraps the
	 * specified stack and locks on the specified mutex.
	 */
	public SynchronizedStack(Stack<E> stack, Object mutex) {
		super();
		if ((stack == null) || (mutex == null)) {
			throw new NullPointerException();
		}
		this.stack = stack;
		this.mutex = mutex;
	}

	/**
	 * Construct a synchronized stack that wraps the
	 * specified stack and locks on itself.
	 */
	public SynchronizedStack(Stack<E> stack) {
		super();
		if (stack == null) {
			throw new NullPointerException();
		}
		this.stack = stack;
		this.mutex = this;
	}


	// ********** Stack implementation **********

	public void push(E element) {
		synchronized (this.mutex) {
			this.push_(element);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void push_(E element) {
		this.stack.push(element);
		this.mutex.notifyAll();
	}

	public E pop() {
		synchronized (this.mutex) {
			return this.pop_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private E pop_() {
		E o = this.stack.pop();
		this.mutex.notifyAll();
		return o;
	}

	public E peek() {
		synchronized (this.mutex) {
			return this.stack.peek();
		}
	}

	public boolean isEmpty() {
		synchronized (this.mutex) {
			return this.stack.isEmpty();
		}
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the stack's empty status changes
	 * to the specified value.
	 */
	public void waitUntilEmptyIs(boolean empty) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(empty);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilEmptyIs_(boolean empty) throws InterruptedException {
		while (this.stack.isEmpty() != empty) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the stack is empty.
	 */
	public void waitUntilEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(true);
	}

	/**
	 * Suspend the current thread until the stack has something on it.
	 */
	public void waitUntilNotEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(false);
	}

	/**
	 * Suspend the current thread until the stack is empty,
	 * then "push" the specified item on to the top of the stack
	 * and continue executing.
	 */
	public void waitToPush(E element) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(true);
			this.push_(element);
		}
	}

	/**
	 * Suspend the current thread until the stack has something on it,
	 * then "pop" an item from the top of the stack and return it.
	 */
	public Object waitToPop() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(false);
			return this.pop_();
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the stack's empty status changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the specified
	 * empty status was achieved; return <code>false</code> if a time-out occurred.
	 * If the stack's empty status is already the specified value,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilEmptyIs(boolean empty, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilEmptyIs_(empty, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilEmptyIs_(boolean empty, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilEmptyIs_(empty);	// wait indefinitely until notified
			return true;	// if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.stack.isEmpty() != empty) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.stack.isEmpty() == empty);
	}

	/**
	 * Suspend the current thread until the stack is empty
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the stack is empty; return <code>false</code> if a time-out occurred.
	 * If the stack is already empty, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(true, timeout);
	}

	/**
	 * Suspend the current thread until the stack has something on it.
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the stack is not empty; return <code>false</code> if a time-out occurred.
	 * If the stack already has something on it, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(false, timeout);
	}

	/**
	 * Suspend the current thread until the stack is empty,
	 * then "push" the specified item on to the top of the stack
	 * and continue executing. If the stack is not emptied out
	 * before the time-out, simply continue executing without
	 * "pushing" the item.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the
	 * item was pushed; return <code>false</code> if a time-out occurred.
	 * If the stack is already empty, "push" the specified item and
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToPush(E element, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(true, timeout);
			if (success) {
				this.push_(element);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the stack has something on it,
	 * then "pop" an item from the top of the stack and return it.
	 * If the stack is empty and nothing is "pushed" on to it before the
	 * time-out, throw an empty stack exception.
	 * The time-out is specified in milliseconds.
	 * If the stack is not empty, "pop" an item and
	 * return it immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public Object waitToPop(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(false, timeout);
			if (success) {
				return this.pop_();
			}
			throw new EmptyStackException();
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the stack in another
	 * thread.
	 */
	public void execute(Command command) throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
		synchronized (this.mutex) {
			command.execute();
		}
	}


	// ********** additional (synchronized) public protocol **********

	/**
	 * "Push" all the elements returned by the specified iterable.
	 * Return whether the stack changed as a result.
	 */
	public boolean pushAll(Iterable<? extends E> iterable) {
		return this.pushAll(iterable.iterator());
	}

	/**
	 * "Push" all the elements returned by the specified iterator.
	 * Return whether the stack changed as a result.
	 */
	public boolean pushAll(Iterator<? extends E> iterator) {
		if ( ! iterator.hasNext()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.pushAll_(iterator);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the iterator is not empty.
	 */
	private boolean pushAll_(Iterator<? extends E> iterator) {
		do {
			this.stack.push(iterator.next());
		} while (iterator.hasNext());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Push" all the elements in the specified array.
	 * Return whether the stack changed as a result.
	 */
	public boolean pushAll(@SuppressWarnings("unchecked") E... array) {
		int len = array.length;
		if (len == 0) {
			return false;
		}
		synchronized (this.mutex) {
			return this.pushAll_(array, len);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the array is not empty.
	 */
	private boolean pushAll_(E[] array, int arrayLength) {
		int i = 0;
		do {
			this.stack.push(array[i++]);
		} while (i < arrayLength);
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the elements from the specified stack and "push" them.
	 * Return whether the stack changed as a result.
	 */
	public boolean pushAll(Stack<? extends E> s) {
		if (s.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.pushAll_(s);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the stack is not empty.
	 */
	private boolean pushAll_(Stack<? extends E> s) {
		do {
			this.stack.push(s.pop());
		} while ( ! s.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Dequeue" all the elements from the specified queue and
	 * "push" them.
	 * Return whether the stack changed as a result.
	 * @see #popAllTo(Queue)
	 */
	public boolean pushAll(Queue<? extends E> queue) {
		if (queue.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.pushAll_(queue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the queue is not empty.
	 */
	private boolean pushAll_(Queue<? extends E> queue) {
		do {
			this.stack.push(queue.dequeue());
		} while ( ! queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the current items from the stack and return them in a list.
	 */
	public ArrayList<E> popAll() {
		ArrayList<E> result = new ArrayList<>();
		this.popAllTo(result);
		return result;
	}

	/**
	 * "Pop" all the current items from the stack into specified collection.
	 * Return whether the stack changed as a result.
	 */
	public boolean popAllTo(Collection<? super E> collection) {
		synchronized (this.mutex) {
			return this.popAllTo_(collection);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean popAllTo_(Collection<? super E> collection) {
		if (this.stack.isEmpty()) {
			return false;
		}
		return this.popAllTo__(collection);
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the stack is not empty.
	 */
	private boolean popAllTo__(Collection<? super E> collection) {
		do {
			collection.add(this.stack.pop());
		} while ( ! this.stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the current items from the stack into specified list
	 * at the specified index.
	 * Return whether the stack changed as a result.
	 */
	public boolean popAllTo(List<? super E> list, int index) {
		synchronized (this.mutex) {
			return this.popAllTo_(list, index);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean popAllTo_(List<? super E> list, int index) {
		if (this.stack.isEmpty()) {
			return false;
		}
		if (index == list.size()) {
			return this.popAllTo__(list);
		}
		ArrayList<E> temp = new ArrayList<>();
		this.popAllTo__(temp);
		list.addAll(index, temp);
		return true;
	}

	/**
	 * "Pop" all the current items from the stack
	 * and "push" them onto the specified stack.
	 * Return whether the stack changed as a result.
	 */
	public boolean popAllTo(Stack<? super E> stack2) {
		synchronized (this.mutex) {
			return this.popAllTo_(stack2);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	public boolean popAllTo_(Stack<? super E> stack2) {
		if (this.stack.isEmpty()) {
			return false;
		}
		do {
			stack2.push(this.stack.pop());
		} while ( ! this.stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the current items from the stack
	 * and "enqueue" them on the specified queue.
	 * Return whether the stack changed as a result.
	 * @see #pushAll(Queue)
	 */
	public boolean popAllTo(Queue<? super E> queue) {
		synchronized (this.mutex) {
			return this.popAllTo_(queue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	public boolean popAllTo_(Queue<? super E> queue) {
		if (this.stack.isEmpty()) {
			return false;
		}
		do {
			queue.enqueue(this.stack.pop());
		} while ( ! this.stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the current items from the stack
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each item.
	 * Return whether the stack changed as a result.
	 */
	public <K> boolean popAllTo(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		synchronized (this.mutex) {
			return this.popAllTo_(map, keyTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K> boolean popAllTo_(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		if (this.stack.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.stack.pop(), keyTransformer);
		} while ( ! this.stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Pop" all the current items from the stack
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each popped item and the specified value transformer
	 * to generator the value for each popped item.
	 * Return whether the stack changed as a result.
	 */
	public <K, V> boolean popAllTo(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		synchronized (this.mutex) {
			return this.popAllTo_(map, keyTransformer, valueTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K, V> boolean popAllTo_(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		if (this.stack.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.stack.pop(), keyTransformer, valueTransformer);
		} while ( ! this.stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Return the object the stack locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return this.stack.toString();
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}
}
