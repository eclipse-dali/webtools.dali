/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.queue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Thread-safe implementation of the {@link Queue} interface.
 * This also provides protocol for suspending a thread until the
 * queue is empty or not empty, with optional time-outs.
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public class SynchronizedQueue<E>
	implements Queue<E>, Serializable
{
	/** Backing queue. */
	private final Queue<E> queue;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a synchronized queue that wraps the
	 * specified queue and locks on the specified mutex.
	 */
	public SynchronizedQueue(Queue<E> queue, Object mutex) {
		super();
		if ((queue == null) || (mutex == null)) {
			throw new NullPointerException();
		}
		this.queue = queue;
		this.mutex = mutex;
	}

	/**
	 * Construct a synchronized queue that wraps the
	 * specified queue and locks on itself.
	 */
	public SynchronizedQueue(Queue<E> queue) {
		super();
		if (queue == null) {
			throw new NullPointerException();
		}
		this.queue = queue;
		this.mutex = this;
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		synchronized (this.mutex) {
			this.enqueue_(element);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void enqueue_(E element) {
		this.queue.enqueue(element);
		this.mutex.notifyAll();
	}

	public E dequeue() {
		synchronized (this.mutex) {
			return this.dequeue_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private E dequeue_() {
		E element = this.queue.dequeue();
		this.mutex.notifyAll();
		return element;
	}

	public E peek() {
		synchronized (this.mutex) {
			return this.queue.peek();
		}
	}

	public boolean isEmpty() {
		synchronized (this.mutex) {
			return this.queue.isEmpty();
		}
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the queue's empty status changes
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
		while (this.queue.isEmpty() != empty) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the queue is empty.
	 */
	public void waitUntilEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(true);
	}

	/**
	 * Suspend the current thread until the queue has something on it.
	 */
	public void waitUntilNotEmpty() throws InterruptedException {
		this.waitUntilEmptyIs(false);
	}

	/**
	 * Suspend the current thread until the queue is empty,
	 * then "enqueue" the specified item to the tail of the queue
	 * and continue executing.
	 */
	public void waitToEnqueue(E element) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(true);
			this.enqueue_(element);
		}
	}

	/**
	 * Suspend the current thread until the queue has something on it,
	 * then "dequeue" an item from the head of the queue and return it.
	 */
	public Object waitToDequeue() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEmptyIs_(false);
			return this.dequeue_();
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the queue's empty status changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the specified
	 * empty status was achieved; return <code>false</code> if a time-out occurred.
	 * If the queue's empty status is already the specified value,
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
		while ((this.queue.isEmpty() != empty) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.queue.isEmpty() == empty);
	}

	/**
	 * Suspend the current thread until the queue is empty
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the queue is empty; return <code>false</code> if a time-out occurred.
	 * If the queue is already empty, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(true, timeout);
	}

	/**
	 * Suspend the current thread until the queue has something on it.
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the queue is not empty; return <code>false</code> if a time-out occurred.
	 * If the queue already has something on it, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotEmpty(long timeout) throws InterruptedException {
		return this.waitUntilEmptyIs(false, timeout);
	}

	/**
	 * Suspend the current thread until the queue is empty,
	 * then "enqueue" the specified item to the tail of the queue
	 * and continue executing. If the queue is not emptied out
	 * before the time-out, simply continue executing without
	 * "enqueueing" the item.
	 * The time-out is specified in milliseconds. Return <code>true</code> if the
	 * item was enqueued; return <code>false</code> if a time-out occurred.
	 * If the queue is already empty, "enqueue" the specified item and
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToEnqueue(E element, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(true, timeout);
			if (success) {
				this.enqueue_(element);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the queue has something on it,
	 * then "dequeue" an item from the head of the queue and return it.
	 * If the queue is empty and nothing is "enqueued" on to it before the
	 * time-out, throw a no such element exception.
	 * The time-out is specified in milliseconds.
	 * If the queue is not empty, "dequeue" an item and
	 * return it immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public Object waitToDequeue(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilEmptyIs_(false, timeout);
			if (success) {
				return this.dequeue_();
			}
			throw new NoSuchElementException();
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the queue in another
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
	 * "Enqueue" all the elements returned by the specified iterable.
	 * Return whether the queue changed as a result.
	 */
	public boolean enqueueAll(Iterable<? extends E> iterable) {
		return this.enqueueAll(iterable.iterator());
	}

	/**
	 * "Enqueue" all the elements returned by the specified iterator.
	 * Return whether the queue changed as a result.
	 */
	public boolean enqueueAll(Iterator<? extends E> iterator) {
		if ( ! iterator.hasNext()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueAll_(iterator);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the iterator is not empty.
	 */
	private boolean enqueueAll_(Iterator<? extends E> iterator) {
		do {
			this.queue.enqueue(iterator.next());
		} while (iterator.hasNext());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Enqueue" all the elements in the specified array.
	 * Return whether the queue changed as a result.
	 */
	public boolean enqueueAll(E... array) {
		int len = array.length;
		if (len == 0) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueAll_(array, len);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the array is not empty.
	 */
	private boolean enqueueAll_(E[] array, int arrayLength) {
		int i = 0;
		do {
			this.queue.enqueue(array[i++]);
		} while (i < arrayLength);
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Pop all the elements from the specified stack and "enqueue" them.
	 * Return whether the queue changed as a result.
	 */
	public boolean enqueueAll(Stack<? extends E> stack) {
		if (stack.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueAll_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the stack is not empty.
	 */
	private boolean enqueueAll_(Stack<? extends E> stack) {
		do {
			this.queue.enqueue(stack.pop());
		} while ( ! stack.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Dequeue" all the elements from the specified queue and
	 * "enqueue" them.
	 * Return whether the queue changed as a result.
	 * @see #drainTo(Queue)
	 */
	public boolean enqueueAll(Queue<? extends E> q) {
		if (q.isEmpty()) {
			return false;
		}
		synchronized (this.mutex) {
			return this.enqueueAll_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the queue is not empty.
	 */
	private boolean enqueueAll_(Queue<? extends E> q) {
		do {
			this.queue.enqueue(q.dequeue());
		} while ( ! q.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the queue and return them in a list.
	 */
	public ArrayList<E> drain() {
		ArrayList<E> result = new ArrayList<E>();
		this.drainTo(result);
		return result;
	}

	/**
	 * "Drain" all the current items from the queue into specified collection.
	 * Return whether the queue changed as a result.
	 */
	public boolean drainTo(Collection<? super E> collection) {
		synchronized (this.mutex) {
			return this.drainTo_(collection);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTo_(Collection<? super E> collection) {
		if (this.queue.isEmpty()) {
			return false;
		}
		return this.drainTo__(collection);
	}

	/**
	 * Pre-condition: synchronized
	 * Assume the queue is not empty.
	 */
	private boolean drainTo__(Collection<? super E> collection) {
		do {
			collection.add(this.queue.dequeue());
		} while ( ! this.queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the queue into specified list
	 * at the specified index.
	 * Return whether the queue changed as a result.
	 */
	public boolean drainTo(List<? super E> list, int index) {
		synchronized (this.mutex) {
			return this.drainTo_(list, index);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTo_(List<? super E> list, int index) {
		if (this.queue.isEmpty()) {
			return false;
		}
		if (index == list.size()) {
			return this.drainTo__(list);
		}
		ArrayList<E> temp = new ArrayList<E>();
		this.drainTo__(temp);
		list.addAll(index, temp);
		return true;
	}

	/**
	 * "Drain" all the current items from the queue into specified stack.
	 * Return whether the queue changed as a result.
	 */
	public boolean drainTo(Stack<? super E> stack) {
		synchronized (this.mutex) {
			return this.drainTo_(stack);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTo_(Stack<? super E> stack) {
		if (this.queue.isEmpty()) {
			return false;
		}
		do {
			stack.push(this.queue.dequeue());
		} while ( ! this.queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the queue into specified queue.
	 * Return whether the queue changed as a result.
	 * @see #enqueueAll(Queue)
	 */
	public boolean drainTo(Queue<? super E> q) {
		synchronized (this.mutex) {
			return this.drainTo_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean drainTo_(Queue<? super E> q) {
		if (this.queue.isEmpty()) {
			return false;
		}
		do {
			q.enqueue(this.queue.dequeue());
		} while ( ! this.queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the queue
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each item.
	 * Return whether the queue changed as a result.
	 */
	public <K> boolean drainTo(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		synchronized (this.mutex) {
			return this.drainTo_(map, keyTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K> boolean drainTo_(Map<K, ? super E> map, Transformer<? super E, ? extends K> keyTransformer) {
		if (this.queue.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.queue.dequeue(), keyTransformer);
		} while ( ! this.queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * "Drain" all the current items from the queue
	 * and add them on the specified map, using the specified key transformer
	 * to generate the key for each popped item and the specified value transformer
	 * to generator the value for each dequeued item.
	 * Return whether the queue changed as a result.
	 */
	public <K, V> boolean drainTo(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		synchronized (this.mutex) {
			return this.drainTo_(map, keyTransformer, valueTransformer);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private <K, V> boolean drainTo_(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		if (this.queue.isEmpty()) {
			return false;
		}
		do {
			MapTools.add(map, this.queue.dequeue(), keyTransformer, valueTransformer);
		} while ( ! this.queue.isEmpty());
		this.mutex.notifyAll();
		return true;
	}

	/**
	 * Return the object the queue locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return this.queue.toString();
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}
}
