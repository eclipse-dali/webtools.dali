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

import org.eclipse.jpt.utility.Command;

/**
 * This class provides synchronized access to an <code>int</code>.
 * It also provides protocol for suspending a thread until the
 * value is set to a specified value, with optional time-outs.
 */
public class SynchronizedInt
	implements Cloneable, Serializable
{
	/**
	 * Backing value.
	 */
	private int value;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a synchronized integer with the specified initial value
	 * and mutex.
	 */
	public SynchronizedInt(int value, Object mutex) {
		super();
		this.value = value;
		this.mutex = mutex;
	}

	/**
	 * Create a synchronized integer with the specified initial value.
	 * The synchronized object itself will be the mutex.
	 */
	public SynchronizedInt(int value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized object with an initial value of zero.
	 * The synchronized object itself will be the mutex.
	 */
	public SynchronizedInt() {
		this(0);
	}


	// ********** accessors **********

	/**
	 * Return the current value.
	 */
	public int getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current value is zero.
	 */
	public boolean isZero() {
		synchronized (this.mutex) {
			return this.value == 0;
		}
	}

	/**
	 * Return whether the current value is not zero.
	 */
	public boolean isNotZero() {
		synchronized (this.mutex) {
			return this.value != 0;
		}
	}

	/**
	 * Set the value. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public int setValue(int value) {
		synchronized (this.mutex) {
			return this.setValue_(value);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int setValue_(int v) {
		int old = this.value;
		if (this.value != v) {
			this.value = v;
			this.mutex.notifyAll();
		}
		return old;
	}

	/**
	 * Set the value to zero. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public int setZero() {
		return this.setValue(0);
	}

	/**
	 * If the current value is the specified expected value, set it to the
	 * specified new value. Return the previous value.
	 */
	public int compareAndSwap(int expectedValue, int newValue) {
		synchronized (this.mutex) {
			return this.compareAndSwap_(expectedValue, newValue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int compareAndSwap_(int expectedValue, int newValue) {
		return (this.value == expectedValue) ? this.setValue_(newValue) : this.value;
	}

	/**
	 * Return the object the synchronized integer locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value. If the value is already the
	 * specified value, return immediately.
	 */
	public void waitUntilValueIs(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIs_(int v) throws InterruptedException {
		while (this.value != v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than the specified value. If the
	 * value is already <em>not</em> the specified value,
	 * return immediately.
	 */
	public void waitUntilValueIsNot(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIsNot_(int v) throws InterruptedException {
		while (this.value == v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes to zero.
	 * If the value is already zero, return immediately.
	 */
	public void waitUntilZero() throws InterruptedException {
		this.waitUntilValueIs(0);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than zero.
	 * If the value is already <em>not</em> zero,
	 * return immediately.
	 */
	public void waitUntilNotZero() throws InterruptedException {
		this.waitUntilValueIsNot(0);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value is already <em>not</em> the specified value, set
	 * the value immediately.
	 * Return the previous value.
	 */
	public int waitToSetValue(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot_(v);
			return this.setValue_(v);
		}
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than zero, then change it
	 * back to zero and continue executing.
	 * If the value is already <em>not</em> zero,
	 * set the value to zero immediately.
	 * Return the previous value.
	 */
	public int waitToSetZero() throws InterruptedException {
		return this.waitToSetValue(0);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * the specified expected value, then change it
	 * to the specified new value and continue executing.
	 * If the value is already the specified expected value,
	 * set the value to the specified new value immediately.
	 * Return the previous value.
	 */
	public int waitToSwap(int expectedValue, int newValue) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(expectedValue);
			return this.setValue_(newValue);
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already the specified value, return true immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIs(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIs_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIs_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value != v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value == v);
	}

	/**
	 * Suspend the current thread until the value changes to something
	 * other than the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was removed; return <code>false</code> if a
	 * time-out occurred. If the value is already <em>not</em> the specified
	 * value, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIsNot(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIsNot_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIsNot_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIsNot_(v);	// wait indefinitely until notified
			return true;	// if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value == v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value != v);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to zero or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred. If the value is already zero,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilZero(long timeout) throws InterruptedException {
		return this.waitUntilValueIs(0, timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than zero or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred. If the value is already <em>not</em>
	 * zero, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotZero(long timeout) throws InterruptedException {
		return this.waitUntilValueIsNot(0, timeout);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value does not change to something other than the
	 * specified value before the time-out, simply continue executing
	 * without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to the specified value; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already something other than the specified value, set
	 * the value immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetValue(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIsNot_(v, timeout);
			if (success) {
				this.setValue_(v);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the value changes to something
	 * other than zero, then change it back to zero
	 * and continue executing. If the value does not change to something
	 * other than zero before the time-out, simply continue
	 * executing without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to zero; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already something other than zero, set
	 * the value to zero immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetZero(long timeout) throws InterruptedException {
		return this.waitToSetValue(0, timeout);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * the specified expected value, then change it
	 * to the specified new value and continue executing.
	 * If the value does not change to the specified expected value
	 * before the time-out, simply continue executing without changing
	 * the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to the specified new value; return
	 * <code>false</code> if a time-out occurred.
	 * If the value is already the specified expected value,
	 * set the value to the specified new value immediately.
	 * Return the previous value.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSwap(int expectedValue, int newValue, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIs_(expectedValue, timeout);
			if (success) {
				this.setValue_(newValue);
			}
			return success;
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the value from another
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
	public SynchronizedInt clone() {
		try {
			synchronized (this.mutex) {
				return (SynchronizedInt) super.clone();
			}
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof SynchronizedInt &&
				this.getValue() == ((SynchronizedInt) obj).getValue();
	}

	@Override
	public int hashCode() {
		return this.getValue();
	}

	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}

}
