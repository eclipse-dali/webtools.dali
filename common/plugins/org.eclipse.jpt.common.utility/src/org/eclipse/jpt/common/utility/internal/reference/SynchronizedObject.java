/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;

/**
 * This class provides synchronized access to an object of type <code>V</code>.
 * It also provides protocol for suspending a thread until the
 * value is set to <code>null</code> or a non-<code>null</code> value,
 * with optional time-outs.
 * 
 * @param <V> the type of the synchronized object's value
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


	// ********** ObjectReference **********

	public V getValue() {
		synchronized (this.mutex) {
			return this.value;
		}
	}

	public boolean valueEquals(Object object) {
		synchronized (this.mutex) {
			return ObjectTools.equals(this.value, object);
		}
	}

	public boolean valueNotEqual(Object object) {
		synchronized (this.mutex) {
			return ObjectTools.notEquals(this.value, object);
		}
	}

	public boolean is(Object object) {
		synchronized (this.mutex) {
			return this.value == object;
		}
	}

	public boolean isNot(Object object) {
		synchronized (this.mutex) {
			return this.value != object;
		}
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

	public boolean isMemberOf(Predicate<? super V> predicate) {
		synchronized (this.mutex) {
			return predicate.evaluate(this.value);
		}
	}

	public boolean isNotMemberOf(Predicate<? super V> predicate) {
		synchronized (this.mutex) {
			return ! predicate.evaluate(this.value);
		}
	}


	// ********** ModifiableObjectReference **********

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
	 * Return the previous value.
	 * Pre-condition: synchronized
	 */
	private V setValue_(V v) {
		V old = this.value;
		return ObjectTools.equals(old, v) ? old : this.setValue_(v, old);
	}

	/**
	 * Return the previous value.
	 * Pre-condition: synchronized and new value is different
	 */
	private V setChangedValue_(V v) {
		return this.setValue_(v, this.value);
	}

	/**
	 * Return the previous value.
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
	 * If the current value is {@link Object#equals(Object) equal} to
	 * the specified expected value, set it to the
	 * specified new value. Return the previous value.
	 */
	public boolean commit(V newValue, V expectedValue) {
		synchronized (this.mutex) {
			return this.commit_(newValue, expectedValue);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean commit_(V newValue, V expectedValue) {
		if (ObjectTools.equals(this.value, expectedValue)) {
			this.setValue_(newValue);
			return true;
		}
		return false;
	}

	public V swap(ModifiableObjectReference<V> other) {
		if (other == this) {
			return this.getValue();
		}
		if (other instanceof SynchronizedObject) {
			return this.swap_((SynchronizedObject<V>) other);
		}

		V thisValue = null;
		V otherValue = other.getValue();
		synchronized (this.mutex) {
		    thisValue = this.value;
		    if (ObjectTools.notEquals(thisValue, otherValue)) {
		        this.setChangedValue_(otherValue);
		    }
		}
        other.setValue(thisValue);
	    return otherValue;
	}

	/**
	 * Atomically swap the value of this synchronized object with the value of
	 * the specified synchronized object. Make assumptions about the value of
	 * <em>identity hash code</em> to avoid deadlock when two synchronized
	 * objects swap values with each other simultaneously.
	 * If either value changes, the corresponding waiting threads are notified.
	 * Return the new value.
	 */
	public V swap(SynchronizedObject<V> other) {
		return (other == this) ? this.getValue() : this.swap_(other);
	}

	/**
	 * Pre-condition: not same object
	 */
	private V swap_(SynchronizedObject<V> other) {
		boolean thisFirst = System.identityHashCode(this) < System.identityHashCode(other);
		SynchronizedObject<V> first = thisFirst ? this : other;
		SynchronizedObject<V> second = thisFirst ? other : this;
		synchronized (first.mutex) {
			synchronized (second.mutex) {
				V thisValue = this.value;
				V otherValue = other.value;
				if (ObjectTools.equals(thisValue, otherValue)) {
					return thisValue;  // nothing changes
				}
				other.setChangedValue_(thisValue);
				this.setChangedValue_(otherValue);
				return otherValue;
			}
		}
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
	 * to belong to the set specified by the specified predicate.
	 * If the value already belongs to the set, return immediately.
	 */
	public void waitUntil(Predicate<? super V> predicate) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(predicate);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void waitUntil_(Predicate<? super V> predicate) throws InterruptedException {
		while ( ! predicate.evaluate(this.value)) {
			this.mutex.wait();
		}
	}

	/**
	 * Suspend the current thread until the value changes
	 * to no longer belong to the set specified by the specified predicate.
	 * If the value is already outside the set, return immediately.
	 */
	public void waitUntilNot(Predicate<? super V> predicate) throws InterruptedException {
		this.waitUntil(PredicateTools.not(predicate));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be {@link Object#equals(Object) equal} to the specified object.
	 * If the value is already equal to the specified object, return immediately.
	 */
	public void waitUntilValueEquals(V object) throws InterruptedException {
		this.waitUntil(PredicateTools.isEqual(object));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be <em>not</em> {@link Object#equals(Object) equal} to the specified object.
	 * If the value is already unequal to the specified object, return immediately.
	 */
	public void waitUntilValueNotEqual(V object) throws InterruptedException {
		this.waitUntil(PredicateTools.isNotEqual(object));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to the specified object. If the value is already the
	 * specified object, return immediately.
	 */
	public void waitUntilValueIs(V object) throws InterruptedException {
		this.waitUntil(PredicateTools.isIdentical(object));
	}

	/**
	 * Suspend the current thread until the value changes
	 * to something other than the specified object. If the value is already
	 * something other than the specified object, return immediately.
	 */
	public void waitUntilValueIsNot(V object) throws InterruptedException {
		this.waitUntil(PredicateTools.isNotIdentical(object));
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
	 * something not {@link Object#equals(Object) equal} to
	 * the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value is already unequal to the specified value, set
	 * the value immediately.
	 * Return the previous value.
	 */
	public V waitToSetValue(V v) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(PredicateTools.isNotEqual(v));
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
	 * be {@link Object#equals(Object) equal} to the specified
	 * expected value, then change it
	 * to the specified new value and continue executing.
	 * If the value is already equal to the specified expected value,
	 * set the value to the specified new value immediately.
	 * Return the previous value.
	 */
	public V waitToCommit(V newValue, V expectedValue) throws InterruptedException {
		synchronized (this.mutex) {
			this.waitUntil_(PredicateTools.isEqual(expectedValue));
			return this.setValue_(newValue);
		}
	}


	// ********** timed waits **********

	/**
	 * Suspend the current thread until the value changes
	 * to belong to the set specified by the specified predicate
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value became a member of the set; return <code>false</code>
	 * if a time-out occurred.
	 * If the value already belongs to the set, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntil(Predicate<? super V> predicate, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			return this.waitUntil_(predicate, timeout);
		}
	}

	/**
	 * Pre-condition: synchronized
	 */
	private boolean waitUntil_(Predicate<? super V> predicate, long timeout) throws InterruptedException {
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
	 * to no longer belong to the set specified by the specified predicate
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value moved outside of the set; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already outside the set, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNot(Predicate<? super V> predicate, long timeout) throws InterruptedException {
		return this.waitUntil(PredicateTools.not(predicate), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be {@link Object#equals(Object) equal} to the specified object
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code> if a
	 * time-out occurred. If the value is already equal to the specified
	 * object, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueEquals(V object, long timeout) throws InterruptedException {
		return this.waitUntil(PredicateTools.isEqual(object), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be <em>not</em> {@link Object#equals(Object) equal} to the specified object
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value changed to be unequal; return <code>false</code> if a
	 * time-out occurred. If the value is already unequal to the specified
	 * object, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueNotEqual(V object, long timeout) throws InterruptedException {
		return this.waitUntil(PredicateTools.isNotEqual(object), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be the specified object or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the specified value was achieved; return <code>false</code> if a
	 * time-out occurred. If the value is already the specified
	 * object, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIs(V object, long timeout) throws InterruptedException {
		return this.waitUntil(PredicateTools.isIdentical(object), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to be <em>not</em> {@link Object#equals(Object) equal} to the specified object
	 * or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value changed to be unequal; return <code>false</code> if a
	 * time-out occurred. If the value is already unequal to the specified
	 * value, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilValueIsNot(V object, long timeout) throws InterruptedException {
		return this.waitUntil(PredicateTools.isNotIdentical(object), timeout);
	}

	/**
	 * Suspend the current thread until the value changes
	 * to <code>null</code> or the specified time-out occurs.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value changes to <code>null</code>; return <code>false</code>
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
	 * if the value changes to something other than <code>null</code>; return <code>false</code>
	 * if a time-out occurred. If the value is already <em>not</em>
	 * <code>null</code>, return <code>true</code> immediately.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitUntilNotNull(long timeout) throws InterruptedException {
		return this.waitUntilValueIsNot(null, timeout);
	}

	/**
	 * Suspend the current thread until the value changes to
	 * something unequal to the specified value, then change
	 * it back to the specified value and continue executing.
	 * If the value does not change to something unequal to the
	 * specified value before the time-out, simply continue executing
	 * without changing the value.
	 * The time-out is specified in milliseconds. Return <code>true</code>
	 * if the value was set to the specified value; return <code>false</code>
	 * if a time-out occurred.
	 * If the value is already something unequal to the specified value, set
	 * the value immediately and return <code>true</code>.
	 * If the time-out is zero, wait indefinitely.
	 */
	public boolean waitToSetValue(V v, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntil_(PredicateTools.isNotEqual(v), timeout);
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
	 * be {@link Object#equals(Object) equal} to the specified
	 * expected value, then change it
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
	public boolean waitToCommit(V newValue, V expectedValue, long timeout) throws InterruptedException {
		synchronized (this.mutex) {
			boolean success = this.waitUntil_(PredicateTools.isEqual(expectedValue), timeout);
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
