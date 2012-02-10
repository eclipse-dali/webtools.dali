/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.ModifiableObjectReference;
import org.eclipse.jpt.common.utility.command.Command;

/**
 * This class provides synchronized access to an object of type <code>V</code>.
 * It also provides protocol for suspending a thread until the
 * value is set to <code>null</code> or a non-<code>null</code> value,
 * with optional time-outs.
 * 
 * @parm V the type of the synchronized object's value
 * @see SimpleObjectReference
 */
public class SynchronizedObject<V>
	implements ModifiableObjectReference<V>, Cloneable, Serializable
{
	/** Backing value. */
	private V value;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a synchronized object with the specified initial value
	 * and mutex.
	 */
	public SynchronizedObject(V value, Object mutex) {
		super();
		this.value = value;
		this.mutex = mutex;
	}

	/**
	 * Create a synchronized object with the specified initial value.
	 * The synchronized object itself will be the mutex.
	 */
	public SynchronizedObject(V value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized object with an initial value of <code>null</code>.
	 * The synchronized object itself will be the mutex.
	 */
	public SynchronizedObject() {
		this(null);
	}


	// ********** accessors **********

	public V getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean valueEquals(Object object) {
		return Tools.valuesAreEqual(this.getValue(), object);
	}

	public boolean valueNotEqual(Object object) {
		return Tools.valuesAreDifferent(this.getValue(), object);
	}

	public boolean isNull() {
		synchronized (this.mutex) {
			return this.value == null;
		}
	}

	public boolean isNotNull() {
		synchronized (this.mutex) {
			return this.value != null;
		}
	}

	/**
	 * Set the value. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public V setValue(V value) {
		synchronized (this.mutex) {
			return this.setValue_(value);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private V setValue_(V v) {
		V old = this.value;
		return Tools.valuesAreEqual(old, v) ? old : this.setValue_(v, old);
	}

	/**
	 * Pre-condition: synchronized and new value is different
	 */
	private V setChangedValue_(V v) {
		return this.setValue_(v, this.value);
	}

	/**
	 * Pre-condition: synchronized and new value is different
	 */
	private V setValue_(V v, V old) {
		this.value = v;
		this.mutex.notifyAll();
		return old;
	}

	/**
	 * Set the value to <code>null</code>. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public V setNull() {
		return this.setValue(null);
	}

	/**
	 * If the current value is the specified expected value, set it to the
	 * specified new value. Return the previous value.
	 */
	public V compareAndSwap(V expectedValue, V newValue) {
		synchronized (this.mutex) {
			return this.compareAndSwap_(expectedValue, newValue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private V compareAndSwap_(V expectedValue, V newValue) {
		return Tools.valuesAreEqual(this.value, expectedValue) ? this.setValue_(newValue) : this.value;
	}

	/**
	 * Return the object the synchronized object locks on while performing
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
	public void waitUntilValueIs(V v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIs_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIs_(V v) throws InterruptedException {
		while (Tools.valuesAreDifferent(this.value, v)) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than the specified value. If the
	 * value is already <em>not</em> the specified value,
	 * return immediately.
	 */
	public void waitUntilValueIsNot(V v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilValueIsNot_(V v) throws InterruptedException {
		while (Tools.valuesAreEqual(this.value, v)) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes to <code>null</code>.
	 * If the value is already <code>null</code>, return immediately.
	 */
	public void waitUntilNull() throws InterruptedException {
		this.waitUntilValueIs(null);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than <code>null</code>.
	 * If the value is already <em>not</em> <code>null</code>,
	 * return immediately.
	 */
	public void waitUntilNotNull() throws InterruptedException {
		this.waitUntilValueIsNot(null);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value is already <em>not</em> the specified value, set
	 * the value immediately.
	 * Return the previous value.
	 */
	public V waitToSetValue(V v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilValueIsNot_(v);
			return this.setChangedValue_(v);
		}
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something other than <code>null</code>, then change it
	 * back to <code>null</code> and continue executing.
	 * If the value is already <em>not</em> <code>null</code>,
	 * set the value to <code>null</code> immediately.
	 * Return the previous value.
	 */
	public V waitToSetNull() throws InterruptedException {
		return this.waitToSetValue(null);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * the specified expected value, then change it
	 * to the specified new value and continue executing.
	 * If the value is already the specified expected value,
	 * set the value to the specified new value immediately.
	 * Return the previous value.
	 */
	public V waitToSwap(V expectedValue, V newValue) throws InterruptedException {
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
	public boolean waitUntilValueIs(V v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIs_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIs_(V v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIs_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while (Tools.valuesAreDifferent(this.value, v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return Tools.valuesAreEqual(this.value, v);
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
	public boolean waitUntilValueIsNot(V v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilValueIsNot_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilValueIsNot_(V v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilValueIsNot_(v);	// wait indefinitely until notified
			return true;	// if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while (Tools.valuesAreEqual(this.value, v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return Tools.valuesAreDifferent(this.value, v);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to <code>null</code> or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred. If the value is already <code>null</code>,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNull(long timeout) throws InterruptedException {
		return this.waitUntilValueIs(null, timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than <code>null</code> or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred. If the value is already <em>not</em>
	 * <code>null</code>, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotNull(long timeout) throws InterruptedException {
		return this.waitUntilValueIsNot(null, timeout);
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
	public boolean waitToSetValue(V v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntilValueIsNot_(v, timeout);
			if (success) {
				this.setChangedValue_(v);
			}
			return success;
		}
	}

	/**
	 * Suspend the current thread until the value changes to something
	 * other than <code>null</code>, then change it back to <code>null</code>
	 * and continue executing. If the value does not change to something
	 * other than <code>null</code> before the time-out, simply continue
	 * executing without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to <code>null</code>; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already something other than <code>null</code>, set
	 * the value to <code>null</code> immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetNull(long timeout) throws InterruptedException {
		return this.waitToSetValue(null, timeout);
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
	public boolean waitToSwap(V expectedValue, V newValue, long timeout) throws InterruptedException {
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
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
		synchronized (this.mutex) {
			command.execute();
		}
	}


	// ********** standard methods **********

	@Override
	public SynchronizedObject<V> clone() {
		try {
			synchronized (this.mutex) {
				@SuppressWarnings("unchecked")
				SynchronizedObject<V> clone = (SynchronizedObject<V>) super.clone();
				return clone;
			}
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
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
