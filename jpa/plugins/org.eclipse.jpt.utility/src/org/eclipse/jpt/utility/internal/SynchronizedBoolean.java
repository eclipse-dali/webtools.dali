/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
 * This class provides synchronized access to a <code>boolean</code> value.
 * It also provides protocol for suspending a thread until the
 * <code>boolean</code> value is set to <code>true</code> or <code>false</code>,
 * with optional time-outs.
 * 
 * @see BooleanReference
 */
public class SynchronizedBoolean
	implements Cloneable, Serializable
{
	/** Backing <code>boolean</code>. */
	private boolean value;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a synchronized <code>boolean</code> with the specified
	 * initial value and mutex.
	 */
	public SynchronizedBoolean(boolean value, Object mutex) {
		super();
		this.value = value;
		this.mutex = mutex;
	}

	/**
	 * Create a synchronized <code>boolean</code> with the
	 * specified initial value.
	 * The synchronized <code>boolean</code> itself will be the mutex.
	 */
	public SynchronizedBoolean(boolean value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized <code>boolean</code>
	 * with an initial value of <code>false</code>
	 * and specified mutex.
	 */
	public SynchronizedBoolean(Object mutex) {
		this(false, mutex);
	}

	/**
	 * Create a synchronized <code>boolean</code>
	 * with an initial value of <code>false</code>.
	 * The synchronized <code>boolean</code> itself will be the mutex.
	 */
	public SynchronizedBoolean() {
		this(false);
	}


	// ********** accessors **********

	/**
	 * Return the current <code>boolean</code> value.
	 */
	public boolean getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current <code>boolean</code>
	 * value is the specified value.
	 */
	public boolean is(boolean v) {
		synchronized (this.mutex) {
			return this.value == v;
		}
	}

	/**
	 * Return whether the current <code>boolean</code>
	 * value is the specified value.
	 */
	public boolean isNot(boolean v) {
		synchronized (this.mutex) {
			return this.value != v;
		}
	}

	/**
	 * Return whether the current <code>boolean</code>
	 * value is <code>true</code>.
	 */
	public boolean isTrue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current <code>boolean</code>
	 * value is <code>false</code>.
	 */
	public boolean isFalse() {
		synchronized (this.mutex) {
			return ! this.value;
		}
	}

	/**
	 * Set the <code>boolean</code> value.
	 * If the value changes, all waiting threads are notified.
	 */
	public void setValue(boolean value) {
		synchronized (this.mutex) {
			this.setValue_(value);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void setValue_(boolean v) {
		if (this.value != v) {
			this.value = v;
			this.mutex.notifyAll();
		}
	}

	/**
	 * Set the <code>boolean</code> value to the NOT of its current value.
	 * If the value changes, all waiting threads are notified.
	 * Return the resulting value.
	 */
	public boolean flip() {
		synchronized (this.mutex) {
			boolean v = ! this.value;
			this.setValue_(v);
			return v;
		}
	}

	/**
	 * Set the <code>boolean</code> value to <code>true</code>.
	 * If the value changes, all waiting threads are notified.
	 */
	public void setNot(boolean v) {
		this.setValue( ! v);
	}

	/**
	 * Set the <code>boolean</code> value to <code>true</code>.
	 * If the value changes, all waiting threads are notified.
	 */
	public void setTrue() {
		this.setValue(true);
	}

	/**
	 * Set the <code>boolean</code> value to <code>false</code>.
	 * If the value changes, all waiting threads are notified.
	 */
	public void setFalse() {
		this.setValue(false);
	}

	/**
	 * Return the object this object locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** indefinite waits **********

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to the specified value. If the <code>boolean</code> value is already the
	 * specified value, return immediately.
	 */
	public void waitUntilValueIs(boolean v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIs_(boolean v) throws InterruptedException {
		while (this.value != v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the NOT of the specified value.
	 * If the <code>boolean</code> value is already the NOT of the specified
	 * value, return immediately.
	 */
	public void waitUntilValueIsNot(boolean v) throws InterruptedException {
		this.waitUntilValueIs( ! v);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>true</code>.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * return immediately.
	 */
	public void waitUntilTrue() throws InterruptedException {
		this.waitUntilValueIs(true);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>false</code>.
	 * If the <code>boolean</code> value is already <code>false</code>,
	 * return immediately.
	 */
	public void waitUntilFalse() throws InterruptedException {
		this.waitUntilValueIs(false);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes to
	 * <em>not</em> the specified value, then change it back to the specified
	 * value and continue executing. If the <code>boolean</code> value is already
	 * <em>not</em> the specified value, set the value to the specified value
	 * immediately.
	 */
	public void waitToSetValue(boolean v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_( ! v);
			this.setValue_(v);
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>false</code>,
	 * then change it back to <code>true</code> and continue executing.
	 * If the <code>boolean</code> value is already <code>false</code>,
	 * set the value to <code>true</code> immediately.
	 */
	public void waitToSetTrue() throws InterruptedException {
		this.waitToSetValue(true);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>true</code>,
	 * then change it back to <code>false</code> and continue executing.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * set the value to <code>false</code> immediately.
	 */
	public void waitToSetFalse() throws InterruptedException {
		this.waitToSetValue(false);
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the specified value was achieved;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already the specified value,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIs(boolean v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIs_(boolean v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIs_(v);	// wait indefinitely until notified
			return true;	// if it ever comes back, the condition was met
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
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the NOT of the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the NOT of the specified value was achieved;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already the NOT of the specified
	 * value, return immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public void waitUntilValueIsNot(boolean v, long timeout) throws InterruptedException {
		this.waitUntilValueIs( ! v, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>true</code> or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * <code>true</code> was achieved;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilTrue(long timeout) throws InterruptedException {
		return this.waitUntilValueIs(true, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>false</code> or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * <code>false</code> was achieved;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilFalse(long timeout) throws InterruptedException {
		return this.waitUntilValueIs(false, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <em>not</em> the specified value, then change it back to the specified
	 * value and continue executing. If the <code>boolean</code> value does not
	 * change to <code>false</code> before the time-out, simply continue
	 * executing without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to the specified value; return <code>false</code>
	 * if a time-out occurred. If the <code>boolean</code> value is already
	 * <em>not</em> the specified value, set the value to the specified value
	 * immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetValue(boolean v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIs_( ! v, timeout);
			if (success) {
				this.setValue_(v);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>false</code>, then change it back to <code>true</code> and
	 * continue executing. If the <code>boolean</code> value does not change to
	 * <code>false</code> before the time-out, simply continue executing without
	 * changing the value. The time-out is specified in milliseconds. Return
	 * <code>true</code> if the value was set to <code>true</code>;
	 * return <code>false</code> if a time-out occurred. If the
	 * <code>boolean</code> value is already <code>false</code>, set the
	 * value to <code>true</code> immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetTrue(long timeout) throws InterruptedException {
		return this.waitToSetValue(true, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>true</code>, then change it back to <code>false</code> and
	 * continue executing. If the <code>boolean</code> value does not change to
	 * <code>true</code> before the time-out, simply continue executing without
	 * changing the value. The time-out is specified in milliseconds. Return
	 * <code>true</code> if the value was set to <code>false</code>;
	 * return <code>false</code> if a time-out occurred. If the
	 * <code>boolean</code> value is already <code>true</code>, set the
	 * value to <code>false</code> immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetFalse(long timeout) throws InterruptedException {
		return this.waitToSetValue(false, timeout);
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
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
	public SynchronizedBoolean clone() {
		try {
			synchronized (this.mutex) {
				return (SynchronizedBoolean) super.clone();
			}
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof SynchronizedBoolean) &&
			(this.getValue() == ((SynchronizedBoolean) o).getValue());
	}

	@Override
	public int hashCode() {
		return this.getValue() ? 1 : 0;
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.getValue()) + ']';
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}

}
