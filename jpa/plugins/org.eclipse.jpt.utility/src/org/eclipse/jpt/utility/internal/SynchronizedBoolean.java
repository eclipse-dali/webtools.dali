/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 * This class provides synchronized access to a boolean value.
 * It also provides protocol for suspending a thread until the
 * boolean value is set to true or false, with optional time-outs.
 * @see BooleanHolder
 */
public class SynchronizedBoolean
	implements Cloneable, Serializable
{
	/** Backing boolean. */
	private boolean value;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a synchronized boolean with the specified initial value
	 * and mutex.
	 */
	public SynchronizedBoolean(boolean value, Object mutex) {
		super();
		this.value = value;
		this.mutex = mutex;
	}

	/**
	 * Create a synchronized boolean with the specified initial value.
	 */
	public SynchronizedBoolean(boolean value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized boolean with an initial value of false
	 * and specified mutex.
	 */
	public SynchronizedBoolean(Object mutex) {
		this(false, mutex);
	}

	/**
	 * Create a synchronized boolean with an initial value of false.
	 */
	public SynchronizedBoolean() {
		this(false);
	}


	// ********** accessors **********

	/**
	 * Return the current boolean value.
	 */
	public boolean value() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current boolean value is true.
	 */
	public boolean isTrue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	/**
	 * Return whether the current boolean value is false.
	 */
	public boolean isFalse() {
		synchronized (this.mutex) {
			return ! this.value;
		}
	}

	/**
	 * Return whether the current boolean value is the specified value.
	 */
	public boolean is(boolean v) {
		synchronized (this.mutex) {
			return this.value == v;
		}
	}

	/**
	 * Set the boolean value. If the value changes, all waiting
	 * threads are notified.
	 */
	public void setValue(boolean value) {
		synchronized (this.mutex) {
			if (this.value != value) {
				this.value = value;
				this.mutex.notifyAll();
			}
		}
	}

	/**
	 * Set the boolean value to true. If the value changes, all waiting
	 * threads are notified.
	 */
	public void setTrue() {
		synchronized (this.mutex) {
			this.setValue(true);
		}
	}

	/**
	 * Set the boolean value to false. If the value changes, all waiting
	 * threads are notified.
	 */
	public void setFalse() {
		synchronized (this.mutex) {
			this.setValue(false);
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
	 * Suspend the current thread until the boolean value changes
	 * to the specified value. If the boolean value is already the
	 * specified value, return immediately.
	 */
	public void waitUntilValueIs(boolean v) throws InterruptedException {
		synchronized (this.mutex) {
			while (this.value != v) {
				this.mutex.wait();
			}
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to true.
	 * If the boolean value is already true, return immediately.
	 */
	public void waitUntilTrue() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs(true);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to false.
	 * If the boolean value is already false, return immediately.
	 */
	public void waitUntilFalse() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs(false);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to
	 * NOT the specified value, then change it back to the specified
	 * value and continue executing. If the boolean value is already
	 * NOT the specified value, set the value to the specified value
	 * immediately.
	 */
	public void waitToSetValue(boolean v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs( ! v);
			this.setValue(v);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to false,
	 * then change it back to true and continue executing. If the boolean
	 * value is already false, set the value to true immediately.
	 */
	public void waitToSetTrue() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitToSetValue(true);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to true,
	 * then change it back to false and continue executing. If the boolean
	 * value is already true, set the value to false immediately.
	 */
	public void waitToSetFalse() throws InterruptedException {
		synchronized (this.mutex) {
			this.waitToSetValue(false);
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the boolean value changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 * If the boolean value is already the specified value, return true
	 * immediately.
	 */
	public boolean waitUntilValueIs(boolean v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			if (timeout == 0L) {
				this.waitUntilValueIs(v);	// wait indefinitely until notified
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
	}

	/**
	 * Suspend the current thread until the boolean value changes
	 * to true or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 * If the boolean value is already true, return true immediately.
	 */
	public boolean waitUntilTrue(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs(true, timeout);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes
	 * to false or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return true if the specified
	 * value was achieved; return false if a time-out occurred.
	 * If the boolean value is already true, return true immediately.
	 */
	public boolean waitUntilFalse(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs(false, timeout);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to NOT the
	 * specified value, then change it back to the specified value and continue
	 * executing. If the boolean value does not change to false before the
	 * time-out, simply continue executing without changing the value.
	 * The time-out is specified in milliseconds. Return true if the value was
	 * set to the specified value; return false if a time-out occurred.
	 * If the boolean value is already NOT the specified value, set the value
	 * to the specified value immediately and return true.
	 */
	public boolean waitToSetValue(boolean v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIs( ! v, timeout);
			if (success) {
				this.setValue(v);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to false,
	 * then change it back to true and continue executing. If the boolean
	 * value does not change to false before the time-out, simply continue
	 * executing without changing the value. The time-out is specified in
	 * milliseconds. Return true if the value was set to true; return false
	 * if a time-out occurred. If the boolean value is already false, set the
	 * value to true immediately and return true.
	 */
	public boolean waitToSetTrue(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitToSetValue(true, timeout);
		}
	}

	/**
	 * Suspend the current thread until the boolean value changes to true,
	 * then change it back to false and continue executing. If the boolean
	 * value does not change to true before the time-out, simply continue
	 * executing without changing the value. The time-out is specified in
	 * milliseconds. Return true if the value was set to false; return false
	 * if a time-out occurred. If the boolean value is already true, set the
	 * value to false immediately and return true.
	 */
	public boolean waitToSetFalse(long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitToSetValue(false, timeout);
		}
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
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
	public Object clone() {
		try {
			synchronized (this.mutex) {
				return super.clone();
			}
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SynchronizedBoolean) {
			return this.value() == ((SynchronizedBoolean) o).value();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value() ? 1 : 0;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value());
	}

}
