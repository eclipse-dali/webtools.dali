/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.InterruptibleCommand;
import org.eclipse.jpt.common.utility.InterruptibleCommandExecutor;

/**
 * This class provides synchronized access to a <code>boolean</code> value.
 * It also provides protocol for suspending a thread until the
 * <code>boolean</code> value is set to <code>true</code> or <code>false</code>,
 * with optional time-outs.
 * 
 * @see SimpleBooleanReference
 */
public class SynchronizedBoolean
	implements InterruptibleCommandExecutor, BooleanReference, Cloneable, Serializable
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

	public boolean getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean is(boolean b) {
		synchronized (this.mutex) {
			return this.value == b;
		}
	}

	public boolean isNot(boolean b) {
		synchronized (this.mutex) {
			return this.value != b;
		}
	}

	public boolean isTrue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean isFalse() {
		synchronized (this.mutex) {
			return ! this.value;
		}
	}

	/**
	 * If the value changes, all waiting threads are notified.
	 * Return the <em>old</em> value.
	 */
	public boolean setValue(boolean value) {
		synchronized (this.mutex) {
			return (value == this.value) ? value : ! this.setChangedValue_(value);
		}
	}

	/**
	 * Pre-condition: synchronized; new value is different
	 * <br>
	 * Return the <em>new</em> value.
	 */
	private boolean setChangedValue_(boolean v) {
		this.value = v;
		this.mutex.notifyAll();
		return v;
	}

	/**
	 * If the value changes, all waiting threads are notified.
	 * Return the new value.
	 */
	public boolean flip() {
		synchronized (this.mutex) {
			return this.setChangedValue_( ! this.value);
		}
	}

	/**
	 * Set the value to <code>value & b</code> and return the new value.
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean and(boolean b) {
		synchronized (this.mutex) {
			return this.setValue_(this.value & b);
		}
	}

	/**
	 * Set the value to <code>value | b</code> and return the new value.
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean or(boolean b) {
		synchronized (this.mutex) {
			return this.setValue_(this.value | b);
		}
	}

	/**
	 * Set the value to <code>value ^ b</code> and return the new value.
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean xor(boolean b) {
		synchronized (this.mutex) {
			return this.setValue_(this.value ^ b);
		}
	}

	/**
	 * Pre-condition: synchronized
	 * <br>
	 * Return the <em>new</em> value.
	 */
	private boolean setValue_(boolean v) {
		return (v == this.value) ? v : this.setChangedValue_(v);
	}

	/**
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean setNot(boolean b) {
		return this.setValue( ! b);
	}

	/**
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean setTrue() {
		return this.setValue(true);
	}

	/**
	 * If the value changes, all waiting threads are notified.
	 */
	public boolean setFalse() {
		return this.setValue(false);
	}

	/**
	 * Set the value to the specified new value if it is currently the specified
	 * expected value. If the value changes, all waiting threads are notified.
	 * Return whether the commit was successful.
	 */
	public boolean commit(boolean expectedValue, boolean newValue) {
		synchronized (this.mutex) {
			boolean success = (this.value == expectedValue);
			if (success) {
				this.setValue_(newValue);
			}
			return success;
		}
	}

	/**
	 * Atomically swap the value of this synchronized boolean with the value of
	 * the specified synchronized boolean. Make assumptions about the value of
	 * <em>identity hash code</em> to avoid deadlock when two synchronized
	 * booleans swap values with each other simultaneously.
	 * If either value changes, the corresponding waiting threads are notified.
	 * Return the new value.
	 */
	public boolean swap(SynchronizedBoolean other) {
		if (other == this) {
			return this.getValue();
		}
		boolean thisFirst = System.identityHashCode(this) < System.identityHashCode(other);
		SynchronizedBoolean first = thisFirst ? this : other;
		SynchronizedBoolean second = thisFirst ? other : this;
		synchronized (first.mutex) {
			synchronized (second.mutex) {
				boolean thisValue = this.value;
				boolean otherValue = other.value;
				if (thisValue == otherValue) {
					return thisValue;  // nothing changes
				}
				other.setChangedValue_(thisValue);
				return this.setChangedValue_(otherValue);
			}
		}
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
	public void waitUntilValueIs(boolean b) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(b);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIs_(boolean b) throws InterruptedException {
		while (this.value != b) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the NOT of the specified value.
	 * If the <code>boolean</code> value is already the NOT of the specified
	 * value, return immediately.
	 */
	public void waitUntilValueIsNot(boolean b) throws InterruptedException {
		this.waitUntilValueIs( ! b);
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
	 * the NOT of the specified value, then change it back to the specified
	 * value and continue executing. If the <code>boolean</code> value is already
	 * the NOT of the specified value, set the value to the specified value
	 * immediately.
	 */
	public void waitToSetValue(boolean b) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_( ! b);
			this.setChangedValue_(b);
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

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the specified value,
	 * then execute the specified command.
	 * If the <code>boolean</code> value is already equal to the specified
	 * value, execute the specified command immediately.
	 */
	public void whenEqual(boolean b, InterruptibleCommand command) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(b);
			command.execute();
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the NOT of the specified value,
	 * then execute the specified command.
	 * If the <code>boolean</code> value is already the NOT of the specified
	 * value, execute the specified command immediately.
	 */
	public void whenNotEqual(boolean b, InterruptibleCommand command) throws InterruptedException {
		this.whenEqual( ! b, command);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>true</code>,
	 * then execute the specified command.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * execute the specified command immediately.
	 */
	public void whenTrue(InterruptibleCommand command) throws InterruptedException {
		this.whenEqual(true, command);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to <code>false</code>,
	 * then execute the specified command.
	 * If the <code>boolean</code> value is already <code>false</code>,
	 * execute the specified command immediately.
	 */
	public void whenFalse(InterruptibleCommand command) throws InterruptedException {
		this.whenEqual(false, command);
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
	public boolean waitUntilValueIs(boolean b, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs_(b, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIs_(boolean b, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIs_(b);	// wait indefinitely until notified
			return true;	// if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value != b) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value == b);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value
	 * changes to the NOT of the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the NOT of the specified value was achieved;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already the NOT of the specified
	 * value, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIsNot(boolean b, long timeout) throws InterruptedException {
		return this.waitUntilValueIs( ! b, timeout);
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
	 * If the <code>boolean</code> value is already <code>false</code>,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilFalse(long timeout) throws InterruptedException {
		return this.waitUntilValueIs(false, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to the NOT of the specified value, then change it back to the specified
	 * value and continue executing. If the <code>boolean</code> value does not
	 * change to <code>false</code> before the time-out, simply continue
	 * executing without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to the specified value; return <code>false</code>
	 * if a time-out occurred.
	 * If the <code>boolean</code> value is already
	 * the NOT of the specified value, set the value to the specified value
	 * immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetValue(boolean b, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIs_( ! b, timeout);
			if (success) {
				this.setChangedValue_(b);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>false</code>, then change it back to <code>true</code> and
	 * continue executing. If the <code>boolean</code> value does not change to
	 * <code>false</code> before the time-out, simply continue executing without
	 * changing the value. 
	 * The time-out is specified in milliseconds. Return
	 * <code>true</code> if the value was set to <code>true</code>;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>false</code>, set the
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
	 * changing the value.
	 * The time-out is specified in milliseconds. Return
	 * <code>true</code> if the value was set to <code>false</code>;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>true</code>, set the
	 * value to <code>false</code> immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetFalse(long timeout) throws InterruptedException {
		return this.waitToSetValue(false, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to the specified value or the specified time-out occurs;
	 * then, if a time-out did not occur, execute the specified command.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already the specified value,
	 * execute the specified command immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean whenEqual(boolean b, InterruptibleCommand command, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIs_(b, timeout);
			if (success) {
				command.execute();
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to the NOT of the specified value or the specified time-out occurs;
	 * then, if a time-out did not occur, execute the specified command.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already the NOT of the specified value,
	 * execute the specified command immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean whenNotEqual(boolean b, InterruptibleCommand command, long timeout) throws InterruptedException {
		return this.whenEqual( ! b, command, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>true</code> or the specified time-out occurs;
	 * then, if a time-out did not occur, execute the specified command.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>true</code>,
	 * execute the specified command immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean whenTrue(InterruptibleCommand command, long timeout) throws InterruptedException {
		return this.whenEqual(true, command, timeout);
	}

	/**
	 * Suspend the current thread until the <code>boolean</code> value changes
	 * to <code>false</code> or the specified time-out occurs;
	 * then, if a time-out did not occur, execute the specified command.
	 * The time-out is specified in milliseconds. Return <code>true</code> if
	 * the command was executed;
	 * return <code>false</code> if a time-out occurred.
	 * If the <code>boolean</code> value is already <code>false</code>,
	 * execute the specified command immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean whenFalse(InterruptibleCommand command, long timeout) throws InterruptedException {
		return this.whenEqual(false, command, timeout);
	}


	// ********** synchronized behavior **********

	/**
	 * If the current thread is not interrupted, execute the specified command 
	 * with the mutex locked. This is useful for initializing the value from another
	 * thread.
	 */
	public void execute(InterruptibleCommand command) throws InterruptedException {
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

	/**
	 * Object identity is critical to synchronized booleans.
	 * There is no reason for two different synchronized booleans to be
	 * <em>equal</em>.
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
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
