/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.predicate.int_.IntPredicateTools;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;
import org.eclipse.jpt.common.utility.reference.IntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;

/**
 * This class provides synchronized access to an <code>int</code>.
 * It also provides protocol for suspending a thread until the
 * value is set to a specified value, with optional time-outs.
 * 
 * @see SimpleIntReference
 */
public class SynchronizedInt
	implements ModifiableIntReference, Cloneable, Serializable
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


	// ********** IntReference **********

	public int getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean equals(int i) {
		synchronized (this.mutex) {
			return this.value == i;
		}
	}

	public boolean notEqual(int i) {
		synchronized (this.mutex) {
			return this.value != i;
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

	public boolean isGreaterThan(int i) {
		synchronized (this.mutex) {
			return this.value > i;
		}
	}

	public boolean isGreaterThanOrEqual(int i) {
		synchronized (this.mutex) {
			return this.value >= i;
		}
	}

	public boolean isLessThan(int i) {
		synchronized (this.mutex) {
			return this.value < i;
		}
	}

	public boolean isLessThanOrEqual(int i) {
		synchronized (this.mutex) {
			return this.value <= i;
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

	public boolean isMemberOf(IntPredicate predicate) {
		synchronized (this.mutex) {
			return predicate.evaluate(this.value);
		}
	}

	public boolean isNotMemberOf(IntPredicate predicate) {
		synchronized (this.mutex) {
			return ! predicate.evaluate(this.value);
		}
	}


	// ********** ModifiableIntReference **********

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

	public int setZero() {
		return this.setValue(0);
	}

	public int increment() {
		synchronized (this.mutex) {
			this.value++;
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int incrementExact() {
		synchronized (this.mutex) {
			this.value = Math.incrementExact(this.value);
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int decrement() {
		synchronized (this.mutex) {
			this.value--;
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int decrementExact() {
		synchronized (this.mutex) {
			this.value = Math.decrementExact(this.value);
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int halve() {
		synchronized (this.mutex) {
			return this.halve_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int halve_() {
		int old = this.value;
		this.value = BitTools.half(old);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int twice() {
		synchronized (this.mutex) {
			return this.twice_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int twice_() {
		int old = this.value;
		this.value = BitTools.twice(old);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int twiceExact() {
		synchronized (this.mutex) {
			return this.twiceExact_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int twiceExact_() {
		int old = this.value;
		this.value = Math.multiplyExact(old, 2);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int abs() {
		synchronized (this.mutex) {
			return this.abs_();
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int abs_() {
		int old = this.value;
		this.value = Math.abs(old);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int negate() {
		synchronized (this.mutex) {
			this.value = -this.value;
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int negateExact() {
		synchronized (this.mutex) {
			this.value = Math.negateExact(this.value);
			this.mutex.notifyAll();
			return this.value;
		}
	}

	public int add(int i) {
		synchronized (this.mutex) {
			return this.add_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int add_(int i) {
		int old = this.value;
		this.value += i;
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int addExact(int i) {
		synchronized (this.mutex) {
			return this.addExact_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int addExact_(int i) {
		int old = this.value;
		this.value = Math.addExact(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int subtract(int i) {
		synchronized (this.mutex) {
			return this.subtract_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int subtract_(int i) {
		int old = this.value;
		this.value -= i;
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int subtractExact(int i) {
		synchronized (this.mutex) {
			return this.subtractExact_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int subtractExact_(int i) {
		int old = this.value;
		this.value = Math.subtractExact(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int multiply(int i) {
		synchronized (this.mutex) {
			return this.multiply_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int multiply_(int i) {
		int old = this.value;
		this.value = this.value * i;
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int multiplyExact(int i) {
		synchronized (this.mutex) {
			return this.multiplyExact_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int multiplyExact_(int i) {
		int old = this.value;
		this.value = Math.multiplyExact(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int divide(int i) {
		synchronized (this.mutex) {
			return this.divide_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int divide_(int i) {
		int old = this.value;
		this.value = this.value / i;
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int floorDivide(int i) {
		synchronized (this.mutex) {
			return this.floorDivide_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int floorDivide_(int i) {
		int old = this.value;
		this.value = Math.floorDiv(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int remainder(int i) {
		synchronized (this.mutex) {
			return this.remainder_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int remainder_(int i) {
		int old = this.value;
		this.value = this.value % i;
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int floorRemainder(int i) {
		synchronized (this.mutex) {
			return this.floorRemainder_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int floorRemainder_(int i) {
		int old = this.value;
		this.value = Math.floorMod(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int min(int i) {
		synchronized (this.mutex) {
			return this.min_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int min_(int i) {
		int old = this.value;
		this.value = Math.min(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public int max(int i) {
		synchronized (this.mutex) {
			return this.max_(i);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private int max_(int i) {
		int old = this.value;
		this.value = Math.max(this.value, i);
		if (this.value != old) {
			this.mutex.notifyAll();
		}
		return this.value;
	}

	public boolean commit(int newValue, int expectedValue) {
		synchronized (this.mutex) {
			return this.commit_(newValue, expectedValue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean commit_(int newValue, int expectedValue) {
		if (this.value == expectedValue) {
			this.setValue_(newValue);
			return true;
		}
		return false;
	}

	public int swap(ModifiableIntReference other) {
		if (other == this) {
			return this.getValue();
		}
		if (other instanceof SynchronizedInt) {
			return this.swap_((SynchronizedInt) other);
		}

		int thisValue = 0;
		int otherValue = other.getValue();
		synchronized (this.mutex) {
		    thisValue = this.value;
		    if (thisValue != otherValue) {
		        this.setChangedValue_(otherValue);
		    }
		}
        other.setValue(thisValue);
	    return otherValue;
	}

	/**
	 * Atomically swap the value of this synchronized int with the value of
	 * the specified synchronized int. Make assumptions about the value of
	 * <em>identity hash code</em> to avoid deadlock when two synchronized
	 * ints swap values with each other simultaneously.
	 * If either value changes, the corresponding waiting threads are notified.
	 * Return the new value.
	 */
	public int swap(SynchronizedInt other) {
		return (other == this) ? this.getValue() : this.swap_(other);
	}

	/**
	 * Pre-condition: not same object
	 */
	private int swap_(SynchronizedInt other) {
		boolean thisFirst = System.identityHashCode(this) < System.identityHashCode(other);
		SynchronizedInt first = thisFirst ? this : other;
		SynchronizedInt second = thisFirst ? other : this;
		synchronized (first.mutex) {
			synchronized (second.mutex) {
				int thisValue = this.value;
				int otherValue = other.value;
				if (thisValue == otherValue) {
					return thisValue;  // nothing changes
				}
				other.setChangedValue_(thisValue);
				return this.setChangedValue_(otherValue);
			}
		}
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
	 * to belong to the set specified by the specified predicate.
	 * If the value already belongs to the set, return immediately.
	 */
	public void waitUntil(IntPredicate predicate) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(predicate);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntil_(IntPredicate predicate) throws InterruptedException {
		while ( ! predicate.evaluate(this.value)) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to no longer belong to the set specified by the specified predicate.
	 * If the value already does not belong to the set, return immediately.
	 */
	public void waitUntilNot(IntPredicate predicate) throws InterruptedException {
		this.waitUntil(IntPredicateTools.not(predicate));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value. If the value is already the
	 * specified value, return immediately.
	 */
	public void waitUntilEqual(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.isEqual(i));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than the specified value. If the
	 * value is already <em>not</em> the specified value,
	 * return immediately.
	 */
	public void waitUntilNotEqual(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.notEqual(i));
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
	public void waitUntilGreaterThan(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.isGreaterThan(i));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value greater than or equal to the specified value. If the value is already
	 * greater than or equal the specified value, return immediately.
	 */
	public void waitUntilGreaterThanOrEqual(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.isGreaterThanOrEqual(i));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value less than the specified value. If the value is already
	 * less than the specified value, return immediately.
	 */
	public void waitUntilLessThan(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.isLessThan(i));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to a value less than or equal to the specified value. If the value is already
	 * less than or equal the specified value, return immediately.
	 */
	public void waitUntilLessThanOrEqual(int i) throws InterruptedException {
		this.waitUntil(IntPredicateTools.isLessThanOrEqual(i));
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
	public int waitToSetValue(int i) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(IntPredicateTools.notEqual(i));
			return this.setChangedValue_(i);
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
	public int waitToCommit(int newValue, int expectedValue) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(IntPredicateTools.isEqual(expectedValue));
			return this.setValue_(newValue);
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to belong to the set specified by the specified predicate
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value now beloongs to the set; return <code>false</code>
	 * if a time-out occurred. If the value already belongs to the set,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntil(IntPredicate predicate, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntil_(predicate, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntil_(IntPredicate predicate, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitUntil_(predicate);  // wait indefinitely until notified
			return true;  // if it ever comes back, the condition was met
		}

		long stop = System.currentTimeMillis() + timeout;
		long remaining = timeout;
		while (( ! predicate.evaluate(this.value)) && (remaining > 0L)) {
			this.mutex.wait(remaining);
			remaining = stop - System.currentTimeMillis();
		}
		return predicate.evaluate(this.value);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to belong to the set specified by the specified predicate
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value now beloongs to the set; return <code>false</code>
	 * if a time-out occurred. If the value already belongs to the set,
	 * return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNot(IntPredicate predicate, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.not(predicate), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to the specified value or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already the specified value, return true immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilEqual(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.isEqual(i), timeout);
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
	public boolean waitUntilNotEqual(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.notEqual(i), timeout);
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
	public boolean waitUntilGreaterThan(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.isGreaterThan(i), timeout);
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
	public boolean waitUntilGreaterThanOrEqual(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.isGreaterThanOrEqual(i), timeout);
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
	public boolean waitUntilLessThan(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.isLessThan(i), timeout);
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
	public boolean waitUntilLessThanOrEqual(int i, long timeout) throws InterruptedException {
		return this.waitUntil(IntPredicateTools.isLessThanOrEqual(i), timeout);
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
	public boolean waitToSetValue(int i, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntil_(IntPredicateTools.notEqual(i), timeout);
			if (success) {
				this.setChangedValue_(i);
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
	public boolean waitToCommit(int newValue, int expectedValue, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntil_(IntPredicateTools.isEqual(expectedValue), timeout);
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


	// ********** Comparable implementation **********

	public int compareTo(IntReference other) {
		int thisValue = this.getValue();
		int otherValue = other.getValue();
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

	/**
	 * Object identity is critical to int references.
	 * There is no reason for two different int references to be
	 * <em>equal</em>.
	 * 
	 * @see #equals(int)
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
