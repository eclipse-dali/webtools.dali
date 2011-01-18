/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
 * 
 * @see SimpleIntReference
 */
public class SynchronizedInt
	implements IntReference, Cloneable, Serializable
{
	/** Backing <code>int</code>. */
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
	 * The synchronized integer itself will be the mutex.
	 */
	public SynchronizedInt(int value) {
		super();
		this.value = value;
		this.mutex = this;
	}

	/**
	 * Create a synchronized integer with an initial value of zero
	 * and the specified mutex.
	 */
	public SynchronizedInt(Object mutex) {
		this(0, mutex);
	}

	/**
	 * Create a synchronized object with an initial value of zero.
	 * The synchronized integer itself will be the mutex.
	 */
	public SynchronizedInt() {
		this(0);
	}


	// ********** methods **********

	public int getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean equals(int v) {
		synchronized (this.mutex) {
			return this.value == v;
		}
	}

	public boolean notEqual(int v) {
		synchronized (this.mutex) {
			return this.value != v;
		}
	}

	public boolean isZero() {
		synchronized (this.mutex) {
			return this.value == 0;
		}
	}

	public boolean isNotZero() {
		synchronized (this.mutex) {
			return this.value != 0;
		}
	}

	public boolean isGreaterThan(int v) {
		synchronized (this.mutex) {
			return this.value > v;
		}
	}

	public boolean isGreaterThanOrEqual(int v) {
		synchronized (this.mutex) {
			return this.value >= v;
		}
	}

	public boolean isLessThan(int v) {
		synchronized (this.mutex) {
			return this.value < v;
		}
	}

	public boolean isLessThanOrEqual(int v) {
		synchronized (this.mutex) {
			return this.value <= v;
		}
	}

	public boolean isPositive() {
		return this.isGreaterThan(0);
	}

	public boolean isNotPositive() {
		return this.isLessThanOrEqual(0);
	}

	public boolean isNegative() {
		return this.isLessThan(0);
	}

	public boolean isNotNegative() {
		return this.isGreaterThanOrEqual(0);
	}

	public int abs() {
		synchronized (this.mutex) {
			return Math.abs(this.value);
		}
	}

	public int neg() {
		synchronized (this.mutex) {
			return -this.value;
		}
	}

	public int add(int v) {
		synchronized (this.mutex) {
			return this.value + v;
		}
	}

	public int subtract(int v) {
		synchronized (this.mutex) {
			return this.value - v;
		}
	}

	public int multiply(int v) {
		synchronized (this.mutex) {
			return this.value * v;
		}
	}

	public int divide(int v) {
		synchronized (this.mutex) {
			return this.value / v;
		}
	}

	public int remainder(int v) {
		synchronized (this.mutex) {
			return this.value % v;
		}
	}

	public int min(int v) {
		synchronized (this.mutex) {
			return Math.min(this.value, v);
		}
	}

	public int max(int v) {
		synchronized (this.mutex) {
			return Math.max(this.value, v);
		}
	}

	public double pow(int v) {
		synchronized (this.mutex) {
			return Math.pow(this.value, v);
		}
	}

	/**
	 * If the value changes, all waiting threads are notified.
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
		return (old == v) ? old : this.setValue_(v, old);
	}

	/**
	 * Pre-condition: synchronized and new value is different
	 */
	private int setChangedValue_(int v) {
		return this.setValue_(v, this.value);
	}

	/**
	 * Pre-condition: synchronized and new value is different
	 */
	private int setValue_(int v, int old) {
		this.value = v;
		this.mutex.notifyAll();
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
	 * Increment the value by one.
	 * Return the new value.
	 */
	public int increment() {
		synchronized (this.mutex) {
			this.value++;
			this.mutex.notifyAll();
			return this.value;
		}
	}

	/**
	 * Decrement the value by one.
	 * Return the new value.
	 */
	public int decrement() {
		synchronized (this.mutex) {
			this.value--;
			this.mutex.notifyAll();
			return this.value;
		}
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
	public void waitUntilEqual(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilEqual_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilEqual_(int v) throws InterruptedException {
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
	public void waitUntilNotEqual(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilNotEqual_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilNotEqual_(int v) throws InterruptedException {
		while (this.value == v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes to zero.
	 * If the value is already zero, return immediately.
	 */
	public void waitUntilZero() throws InterruptedException {
		this.waitUntilEqual(0);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than zero.
	 * If the value is already <em>not</em> zero,
	 * return immediately.
	 */
	public void waitUntilNotZero() throws InterruptedException {
		this.waitUntilNotEqual(0);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value greater than the specified value. If the value is already
	 * greater than the specified value, return immediately.
	 */
	public void waitUntilGreaterThan(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilGreaterThan_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilGreaterThan_(int v) throws InterruptedException {
		while (this.value <= v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value greater than or equal to the specified value. If the value is already
	 * greater than or equal the specified value, return immediately.
	 */
	public void waitUntilGreaterThanOrEqual(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilGreaterThanOrEqual_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilGreaterThanOrEqual_(int v) throws InterruptedException {
		while (this.value < v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value less than the specified value. If the value is already
	 * less than the specified value, return immediately.
	 */
	public void waitUntilLessThan(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilLessThan_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilLessThan_(int v) throws InterruptedException {
		while (this.value >= v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value less than or equal to the specified value. If the value is already
	 * less than or equal the specified value, return immediately.
	 */
	public void waitUntilLessThanOrEqual(int v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntilLessThanOrEqual_(v);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntilLessThanOrEqual_(int v) throws InterruptedException {
		while (this.value > v) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value is positive.
	 * If the value is already positive, return immediately.
	 */
	public void waitUntilPositive() throws InterruptedException {
		this.waitUntilGreaterThan(0);
	}

	/**
	 * Suspend the current thread until the value is not positive
	 * (i.e. negative or zero).
	 * If the value is already <em>not</em> positive,
	 * return immediately.
	 */
	public void waitUntilNotPositive() throws InterruptedException {
		this.waitUntilLessThanOrEqual(0);
	}

	/**
	 * Suspend the current thread until the value is negative.
	 * If the value is already negative, return immediately.
	 */
	public void waitUntilNegative() throws InterruptedException {
		this.waitUntilLessThan(0);
	}

	/**
	 * Suspend the current thread until the value is not negative
	 * (i.e. zero or positive).
	 * If the value is already <em>not</em> negative,
	 * return immediately.
	 */
	public void waitUntilNotNegative() throws InterruptedException {
		this.waitUntilGreaterThanOrEqual(0);
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
			this.waitUntilNotEqual_(v);
			return this.setChangedValue_(v);
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
			this.waitUntilEqual_(expectedValue);
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
	public boolean waitUntilEqual(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilEqual_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilEqual_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilEqual_(v);  // wait indefinitely until notified
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
	public boolean waitUntilNotEqual(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilNotEqual_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilNotEqual_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilNotEqual_(v);	// wait indefinitely until notified
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
	 * if the value is now zero; return <code>false</code>
	 * if a time-out occurred. If the value is already zero,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilZero(long timeout) throws InterruptedException {
		return this.waitUntilEqual(0, timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than zero or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value is now not zero; return <code>false</code>
	 * if a time-out occurred. If the value is already <em>not</em>
	 * zero, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotZero(long timeout) throws InterruptedException {
		return this.waitUntilNotEqual(0, timeout);
	}

	/**
	 * Suspend the current thread until the value changes to a value greater
	 * than the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already greater than the specified value, return immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilGreaterThan(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilGreaterThan_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilGreaterThan_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilGreaterThan_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value <= v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value > v);
	}

	/**
	 * Suspend the current thread until the value changes to a value greater
	 * than or equal to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already greater than or equal to the specified value,
	 * return immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilGreaterThanOrEqual(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilGreaterThanOrEqual_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilGreaterThanOrEqual_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilGreaterThanOrEqual_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value < v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value >= v);
	}

	/**
	 * Suspend the current thread until the value changes to a value less
	 * than the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already less than the specified value, return immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilLessThan(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilLessThan_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilLessThan_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilLessThan_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value >= v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value < v);
	}

	/**
	 * Suspend the current thread until the value changes to a value less
	 * than or equal to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already less than or equal to the specified value,
	 * return immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilLessThanOrEqual(int v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntilLessThanOrEqual_(v, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntilLessThanOrEqual_(int v, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntilLessThanOrEqual_(v);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while ((this.value > v) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return (this.value <= v);
	}

	/**
	 * Suspend the current thread until the value is positive
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value is now positive; return <code>false</code>
	 * if a time-out occurred. If the value is already positive,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilPositive(long timeout) throws InterruptedException {
		return this.waitUntilGreaterThan(0, timeout);
	}

	/**
	 * Suspend the current thread until the value is not positive
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value is now not positive; return <code>false</code>
	 * if a time-out occurred. If the value is already not positive,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotPositive(long timeout) throws InterruptedException {
		return this.waitUntilLessThanOrEqual(0, timeout);
	}

	/**
	 * Suspend the current thread until the value is negative
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value is now negative; return <code>false</code>
	 * if a time-out occurred. If the value is already negative,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNegative(long timeout) throws InterruptedException {
		return this.waitUntilLessThan(0, timeout);
	}

	/**
	 * Suspend the current thread until the value is not negative
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value is now not negative; return <code>false</code>
	 * if a time-out occurred. If the value is already not negative,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotNegative(long timeout) throws InterruptedException {
		return this.waitUntilGreaterThanOrEqual(0, timeout);
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
			boolean success = this.waitUntilNotEqual_(v, timeout);
			if (success) {
				this.setChangedValue_(v);
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
			boolean success = this.waitUntilEqual_(expectedValue, timeout);
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


	// ********** Comparable implementation **********

	public int compareTo(ReadOnlyIntReference ref) {
		int thisValue = this.getValue();
		int otherValue = ref.getValue();
		return (thisValue < otherValue) ? -1 : ((thisValue == otherValue) ? 0 : 1);
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
	public String toString() {
		return '[' + String.valueOf(this.getValue()) + ']';
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}

}
