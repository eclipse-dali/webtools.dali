/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Thread-safe implementation of the {@link Deque} interface.
 * This also provides protocol for suspending a thread until the
 * deque is empty or not empty, with optional time-outs.
 * @param <E> the type of elements maintained by the deque
 * @see DequeTools
 */
public class SynchronizedDeque<E>
	implements Deque<E>, Serializable
{
	/** Backing deque. */
	private final Deque<E> deque;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a synchronized deque that wraps the
	 * specified deque and locks on the specified mutex.
	 */
	public SynchronizedDeque(Deque<E> deque, Object mutex) {
		super();
		if ((deque == null) || (mutex == null)) {
			throw new NullPointerException();
		}
		this.deque = deque;
		this.mutex = mutex;
	}

	/**
	 * Construct a synchronized deque that wraps the
	 * specified deque and locks on itself.
	 */
	public SynchronizedDeque(Deque<E> deque) {
		super();
		if (deque == null) {
			throw new NullPointerException();
		}
		this.deque = deque;
		this.mutex = this;
	}


	// ********** Deque implementation **********

	public void enqueueTail(E element) {
		synchronized (this.mutex) {
			this.enqueueTail_(element);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void enqueueTail_(E element) {
		this.deque.enqueueTail(element);
		this.mutex.notifyAll();
	}

	public void enqueueHead(E element) {
		synchronized (this.mutex) {
			this.enqueueHead_(element);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void enqueueHead_(E element) {
		this.deque.enqueueHead(element);
		this.mutex.notifyAll();
	}

	public E dequeueHead() {
		synchronized (this.mutex) {
			return this.dequeueHead_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private E dequeueHead_() {
		E element = this.deque.dequeueHead();
		this.mutex.notifyAll();
		return element;
	}

	public E dequeueTail() {
		synchronized (this.mutex) {
			return this.dequeueTail_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private E dequeueTail_() {
		E element = this.deque.dequeueTail();
		this.mutex.notifyAll();
		return element;
	}

	public E peekHead() {
		synchronized (this.mutex) {
			return this.deque.peekHead();
		}
	}

	public E peekTail() {
		synchronized (this.mutex) {
			return this.deque.peekTail();
		}
	}

	public boolean isEmpty() {
		synchronized (this.mutex) {
			return this.deque.isEmpty();
		}
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the deque's empty status changes
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
		while (this.deque.isEmpty() != empty) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the deque is empty.
	 */
	public void waitUntilEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(true);
	}

	/**
	 * Suspend the current thread until the deque has something on it.
	 */
	public void waitUntilNotEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(false);
	}

	/**
	 * Suspend the current thread until the deque is empty,
	 * then "enqueue" the specified item to the tail of the deque
	 * and continue executing.
	 */
	public void waitToEnqueueTail(E element) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(true);
			this.enqueueTail_(element);
		}
	}

	/**
	 * Suspend the current thread until the deque is empty,
	 * then "enqueue" the specified item to the head of the deque
	 * and continue executing.
	 */
	public void waitToEnqueueHead(E element) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(true);
			this.enqueueHead_(element);
		}
	}

	/**
	 * Suspend the current thread until the deque has something on it,
	 * then "dequeue" an item from the head of the deque and return it.
	 */
	public Object waitToDequeueHead() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(false);
			return this.dequeueHead_();
		}
	}

	/**
	 * Suspend the current thread until the deque has something on it,
	 * then "dequeue" an item from the tail of the deque and return it.
	 */
	public Object waitToDequeueTail() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(false);
			return this.dequeueTail_();
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the deque's empty status changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the specified
	 * empty status was achieved; return <code>false</code> if a time-out occurred.
	 * If the deque's empty status is already the specified value,
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
		while ((this.deque.isEmpty() != empty) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.deque.isEmpty() == empty);
	}

	/**
	 * Suspend the current thread until the deque is empty
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the deque is empty; return <code>false</code> if a time-out occurred.
	 * If the deque is already empty, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(true, timeout);
	}

	/**
	 * Suspend the current thread until the deque has something on it.
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the deque is not empty; return <code>false</code> if a time-out occurred.
	 * If the deque already has something on it, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(false, timeout);
	}

	/**
	 * Suspend the current thread until the deque is empty,
	 * then "enqueue" the specified item to the tail of the deque
	 * and continue executing. If the deque is not emptied out
	 * before the time-out, simply continue executing without
	 * "enqueueing" the item.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the
	 * item was enqueued; return <code>false</code> if a time-out occurred.
	 * If the deque is already empty, "enqueue" the specified item and
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToEnqueueTail(E element, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(true, timeout);
			if (success) {
				this.enqueueTail_(element);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the deque is empty,
	 * then "enqueue" the specified item to the head of the deque
	 * and continue executing. If the deque is not emptied out
	 * before the time-out, simply continue executing without
	 * "enqueueing" the item.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the
	 * item was enqueued; return <code>false</code> if a time-out occurred.
	 * If the deque is already empty, "enqueue" the specified item and
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToEnqueueHead(E element, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(true, timeout);
			if (success) {
				this.enqueueHead_(element);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the deque has something on it,
	 * then "dequeue" an item from the head of the deque and return it.
	 * If the deque is empty and nothing is "enqueued" on to it before the
	 * time-out, throw a no such element exception.
	 * The time-out is specified in milliseconds.
	 * If the deque is not empty, "dequeue" an item and
	 * return it immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public Object waitToDequeueHead(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(false, timeout);
			if (success) {
				return this.dequeueHead_();
			}
			throw new NoSuchElementException();
		}
	}

	/**
	 * Suspend the current thread until the deque has something on it,
	 * then "dequeue" an item from the tail of the deque and return it.
	 * If the deque is empty and nothing is "enqueued" on to it before the
	 * time-out, throw a no such element exception.
	 * The time-out is specified in milliseconds.
	 * If the deque is not empty, "dequeue" an item and
	 * return it immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public Object waitToDequeueTail(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(false, timeout);
			if (success) {
				return this.dequeueTail_();
			}
			throw new NoSuchElementException();
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the deque in another
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


	// ********** additional public protocol **********

	/**
	 * "Enqueue" all the elements returned by the specified iterable to the deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueTailAll(Iterable<? extends E> iterable) {
		return this.enqueueTailAll(iterable.iterator());
	}

	/**
	 * "Enqueue" all the elements returned by the specified iterable to the deque's head.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueHeadAll(Iterable<? extends E> iterable) {
		return this.enqueueHeadAll(iterable.iterator());
	}

	/**
	 * "Enqueue" all the elements returned by the specified iterator to the deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueTailAll(Iterator<? extends E> iterator) {
		if ( ! iterator.hasNext()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueTailAll_(iterator);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the iterator is not empty.
	 */
	private boolean enqueueTailAll_(Iterator<? extends E> iterator) {
		do {
			this.deque.enqueueTail(iterator.next());
		} while (iterator.hasNext());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Enqueue" all the elements returned by the specified iterator to the deque's head.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueHeadAll(Iterator<? extends E> iterator) {
		if ( ! iterator.hasNext()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueHeadAll_(iterator);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the iterator is not empty.
	 */
	private boolean enqueueHeadAll_(Iterator<? extends E> iterator) {
		do {
			this.deque.enqueueHead(iterator.next());
		} while (iterator.hasNext());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Enqueue" all the elements in the specified array to the deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueTailAll(E... array) {
		int len = array.length;
		if (len == 0) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueTailAll_(array, len);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the array is not empty.
	 */
	private boolean enqueueTailAll_(E[] array, int arrayLength) {
		int i = 0;
		do {
			this.deque.enqueueTail(array[i++]);
		} while (i < arrayLength);
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Enqueue" all the elements in the specified array to the deque's head.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueHeadAll(E... array) {
		int len = array.length;
		if (len == 0) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueHeadAll_(array, len);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the array is not empty.
	 */
	private boolean enqueueHeadAll_(E[] array, int arrayLength) {
		int i = 0;
		do {
			this.deque.enqueueHead(array[i++]);
		} while (i < arrayLength);
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Pop all the elements from the specified stack and "enqueue" them to the deque's tail.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueTailAll(Stack<? extends E> stack) {
		if (stack.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueTailAll_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the stack is not empty.
	 */
	private boolean enqueueTailAll_(Stack<? extends E> stack) {
		do {
			this.deque.enqueueTail(stack.pop());
		} while ( ! stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Pop all the elements from the specified stack and "enqueue" them to the deque's head.
	 * Return whether the deque changed as a result.
	 */
	public boolean enqueueHeadAll(Stack<? extends E> stack) {
		if (stack.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueHeadAll_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the stack is not empty.
	 */
	private boolean enqueueHeadAll_(Stack<? extends E> stack) {
		do {
			this.deque.enqueueHead(stack.pop());
		} while ( ! stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Dequeue" all the elements from the specified deque's head and
	 * "enqueue" them to the deque's tail.
	 * Return whether the deque changed as a result.
	 * @see #drainHeadTo(Deque)
	 */
	public boolean enqueueTailAll(Deque<? extends E> q) {
		if (q.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueTailAll_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the deque is not empty.
	 */
	private boolean enqueueTailAll_(Deque<? extends E> q) {
		do {
			this.deque.enqueueTail(q.dequeueHead());
		} while ( ! q.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Dequeue" all the elements from the specified deque's tail and
	 * "enqueue" them to the deque's head.
	 * Return whether the deque changed as a result.
	 * @see #drainTailTo(Deque)
	 */
	public boolean enqueueHeadAll(Deque<? extends E> q) {
		if (q.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueHeadAll_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the deque is not empty.
	 */
	private boolean enqueueHeadAll_(Deque<? extends E> q) {
		do {
			this.deque.enqueueHead(q.dequeueTail());
		} while ( ! q.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head and return them in a list.
	 */
	public ArrayList<E> drainHead() {
		ArrayList<E> result = new ArrayList<E>();
		this.drainHeadTo(result);
		return result;
	}

	/**
	 * "Drain" all the current items from the deque's tail and return them in a list.
	 */
	public ArrayList<E> drainTail() {
		ArrayList<E> result = new ArrayList<E>();
		this.drainTailTo(result);
		return result;
	}

	/**
	 * "Drain" all the current items from the deque's head into specified collection.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainHeadTo(Collection<? super E> collection) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(collection);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainHeadTo_(Collection<? super E> collection) {
		if (this.deque.isEmpty()) {
			return false;
		}
		return this.drainHeadTo__(collection);
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the deque is not empty.
	 */
	private boolean drainHeadTo__(Collection<? super E> collection) {
		do {
			collection.add(this.deque.dequeueHead());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail into specified collection.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainTailTo(Collection<? super E> collection) {
		synchronized (this.mutex) {
			return this.drainTailTo_(collection);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTailTo_(Collection<? super E> collection) {
		if (this.deque.isEmpty()) {
			return false;
		}
		return this.drainTailTo__(collection);
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the deque is not empty.
	 */
	private boolean drainTailTo__(Collection<? super E> collection) {
		do {
			collection.add(this.deque.dequeueTail());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head into specified list
	 * at the specified index.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainHeadTo(List<? super E> list, int index) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(list, index);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainHeadTo_(List<? super E> list, int index) {
		if (this.deque.isEmpty()) {
			return false;
		}
		if (index == list.size()) {
			return this.drainHeadTo__(list);
		}
		ArrayList<E> temp = new ArrayList<E>();
		this.drainHeadTo__(temp);
		list.addAll(index, temp);
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail into specified list
	 * at the specified index.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainTailTo(List<? super E> list, int index) {
		synchronized (this.mutex) {
			return this.drainTailTo_(list, index);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTailTo_(List<? super E> list, int index) {
		if (this.deque.isEmpty()) {
			return false;
		}
		if (index == list.size()) {
			return this.drainTailTo__(list);
		}
		ArrayList<E> temp = new ArrayList<E>();
		this.drainTailTo__(temp);
		list.addAll(index, temp);
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head into specified stack.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainHeadTo(Stack<? super E> stack) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainHeadTo_(Stack<? super E> stack) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			stack.push(this.deque.dequeueHead());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail into specified stack.
	 * Return whether the deque changed as a result.
	 */
	public boolean drainTailTo(Stack<? super E> stack) {
		synchronized (this.mutex) {
			return this.drainTailTo_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTailTo_(Stack<? super E> stack) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			stack.push(this.deque.dequeueTail());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head into specified deque's tail.
	 * Return whether the deque changed as a result.
	 * @see #enqueueTailAll(Deque)
	 */
	public boolean drainHeadTo(Deque<? super E> q) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainHeadTo_(Deque<? super E> q) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			q.enqueueTail(this.deque.dequeueHead());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail into specified deque's head.
	 * Return whether the deque changed as a result.
	 * @see #enqueueTailAll(Deque)
	 */
	public boolean drainTailTo(Deque<? super E> q) {
		synchronized (this.mutex) {
			return this.drainTailTo_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTailTo_(Deque<? super E> q) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			q.enqueueHead(this.deque.dequeueTail());
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each item.
	 * Return whether the deque changed as a result.
	 */
	public <K> boolean drainHeadTo(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(map, keyTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K> boolean drainHeadTo_(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.deque.dequeueHead(), keyTransformer);
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each item.
	 * Return whether the deque changed as a result.
	 */
	public <K> boolean drainTailTo(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		synchronized (this.mutex) {
			return this.drainTailTo_(map, keyTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K> boolean drainTailTo_(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.deque.dequeueTail(), keyTransformer);
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's head
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each dequeued item and the specified value transformer
	 * to generator the value for each dequeued item.
	 * Return whether the deque changed as a result.
	 */
	public <K, V> boolean drainHeadTo(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		synchronized (this.mutex) {
			return this.drainHeadTo_(map, keyTransformer, valueTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K, V> boolean drainHeadTo_(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.deque.dequeueHead(), keyTransformer, valueTransformer);
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the deque's tail
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each dequeued item and the specified value transformer
	 * to generator the value for each dequeued item.
	 * Return whether the deque changed as a result.
	 */
	public <K, V> boolean drainTailTo(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		synchronized (this.mutex) {
			return this.drainTailTo_(map, keyTransformer, valueTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K, V> boolean drainTailTo_(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		if (this.deque.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.deque.dequeueTail(), keyTransformer, valueTransformer);
		} while ( ! this.deque.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Return the object the deque locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return this.deque.toString();
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}
}
