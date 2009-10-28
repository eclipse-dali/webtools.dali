/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.Command;

/**
 * Thread-safe implementation of the {@link Queue} interface.
 * This also provides protocol for suspending a thread until the
 * queue is empty or not empty, with optional time-outs.
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
		if (queue == null) {
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

	/**
	 * Construct an empty synchronized queue that locks on the specified mutex.
	 */
	public SynchronizedQueue(Object mutex) {
		this(new SimpleQueue<E>(), mutex);
	}

	/**
	 * Construct an empty synchronized queue that locks on itself.
	 */
	public SynchronizedQueue() {
		this(new SimpleQueue<E>());
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
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		synchronized (this.mutex) {
			command.execute();
		}
	}


	// ********** additional public protocol **********

	/**
	 * "Drain" all the current items from the queue into specified queue.
	 */
	public void drainTo(Queue<E> q) {
		synchronized (this.mutex) {
			this.drainTo_(q);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void drainTo_(Queue<E> q) {
		boolean changed = false;
		while ( ! this.queue.isEmpty()) {
			q.enqueue(this.queue.dequeue());
			changed = true;
		}
		if (changed) {
			this.mutex.notifyAll();
		}
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
			return '[' + this.queue.toString() + ']';
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}

}
