/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

/**
 * This class provides synchronized access to an object of type E.
 * It also provides protocol for suspending a thread until the
 * value is set to null or a non-null value, with optional time-outs.
 */
public class SynchronizedObject<E>
	implements Cloneable, Serializable
{
	/** Backing value. */
	private E value;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a synchronized object with the specified initial value
	 * and mutex.
	 */
	public SynchronizedObject(E value, Object mutex) {
		super();
		this.value = value;
		this.mutex = mutex;
	}

	/**
	 * Create a synchronized object with the specified initial value.
	 */
	public SynchronizedObject(E value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized object with an initial value of null.
	 */
	public SynchronizedObject() {
		this(null);
	}


	// ********** accessors **********

	/**
	 * Return the current value.
	 */
	public E value() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current value is null.
	 */
	public boolean isNull() {
		synchronized (this.mutex) {
			return this.value == null;
		}
	}

	/**
	 * Return whether the current value is not null.
	 */
	public boolean isNotNull() {
		synchronized (this.mutex) {
			return this.value != null;
		}
	}

	/**
	 * Set the value. If the value changes, all waiting
	 * threads are notified.
	 */
	public void setValue(E value) {
		synchronized (this.mutex) {
			if (this.value != value) {
				this.value = value;
				this.mutex.notifyAll();
			}
		}
	}

	/**
	 * Set the value to null. If the value changes, all waiting
	 * threads are notified.
	 */
	public void setNull() {
		synchronized (this.mutex) {
			this.setValue(null);
		}
	}

	/**
	 * Return the object this object locks on while performing
	 * its operations.
	 */
	public Object mutex() {
		return this.mutex;
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value.
	 */
	public void waitUntilValueIs(E x) throws InterruptedException {
		synchronized (this.mutex) {
			while (this.value != x) {
				this.mutex.wait();
			}
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than the specified value.
	 */
	public void waitUntilValueIsNot(E x) throws InterruptedException {
		synchronized (this.mutex) {
			while (this.value == x) {
				this.mutex.wait();
			}
		}
	}

	/**
	 * Suspend the current thread until the value changes to null.
	 */
	public void waitUntilNull() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs(null);
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than null.
	 */
	public void waitUntilNotNull() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot(null);
		}
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than the specified value, then change
	 * it back to the specified value and continue executing.
	 */
	public void waitToSetValue(E x) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot(x);
			this.setValue(x);
		}
	}

	/**
	 * Suspend the current thread until the value changes to
	 * null, then change it back to null and continue executing.
	 */
	public void waitToSetNull() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilNotNull();
			this.setValue(null);
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 */
	public boolean waitUntilValueIs(E x, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			if (timeout == 0L) {
				this.waitUntilValueIs(x);	// wait indefinitely until notified
				return true;	// if it ever comes back, the condition was met
			}

			long stop = System.currentTimeMillis() + timeout;
			long remaining = timeout;
			while ((this.value != x) && (remaining > 0L)) {
				this.mutex.wait(remaining);
				remaining = stop - System.currentTimeMillis();
			}
			return (this.value == x);
		}
	}

	/**
	 * Suspend the current thread until the value changes to something
	 * other than the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was removed; return false if a time-out occurred.
	 */
	public boolean waitUntilValueIsNot(E x, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			if (timeout == 0L) {
				this.waitUntilValueIsNot(x);	// wait indefinitely until notified
				return true;	// if it ever comes back, the condition was met
			}

			long stop = System.currentTimeMillis() + timeout;
			long remaining = timeout;
			while ((this.value == x) && (remaining > 0L)) {
				this.mutex.wait(remaining);
				remaining = stop - System.currentTimeMillis();
			}
			return (this.value != x);
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to null or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 */
	public boolean waitUntilNull(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs(null, timeout);
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than null or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 */
	public boolean waitUntilNotNull(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIsNot(null, timeout);
		}
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value does not change to something other than the
	 * specified before the time-out, simply continue executing
	 * without changing the value.
	 * The time-out is specified in milliseconds. Return true if the value was
	 * set to true; return false if a time-out occurred.
	 */
	public boolean waitToSetValue(E x, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIsNot(x, timeout);
			if (success) {
				this.setValue(x);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the value changes to something
	 * other than null, then change it back to null and continue executing.
	 * If the value does not change to something other than null before
	 * the time-out, simply continue executing without changing the value.
	 * The time-out is specified in milliseconds. Return true if the value was
	 * set to false; return false if a time-out occurred.
	 */
	public boolean waitToSetNull(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilNotNull(timeout);
			if (success) {
				this.setValue(null);
			}
			return success;
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the value in another
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


	// ********** standard methods **********

	@Override
	public SynchronizedObject<E> clone() {
		try {
			synchronized (this.mutex) {
				@SuppressWarnings("unchecked")
				SynchronizedObject<E> clone = (SynchronizedObject<E>) super.clone();
				return clone;
			}
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if ( ! (obj instanceof SynchronizedObject)) {
			return false;
		}
		Object thisValue = this.value();
		Object otherValue = ((SynchronizedObject<?>) obj).value();
		return (thisValue == null) ?
			(otherValue == null) : thisValue.equals(otherValue);
	}

	@Override
	public int hashCode() {
		Object temp = this.value();
		return (temp == null) ? 0 : temp.hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(this.value());
	}

}
