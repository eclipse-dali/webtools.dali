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

/**
 * This class can be used wherever a mutable integer object is needed.
 * It is a cross between an <code>int</code> and an {@link Integer}.
 * It can be stored in a standard container (e.g. {@link java.util.Collection})
 * but can be modified. It is also useful passing a value that can be changed
 * by the recipient.
 * 
 * @see SynchronizedInt
 */
public final class IntReference
	implements Comparable<IntReference>, Cloneable, Serializable
{
	/** Backing <code>int</code>. */
	private volatile int value = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a <code>int</code> reference with the specified initial value.
	 */
	public IntReference(int count) {
		super();
		this.value = count;
	}

	/**
	 * Construct a <code>int</code> reference with an initial value of zero.
	 */
	public IntReference() {
		this(0);
	}


	// ********** methods **********

	/**
	 * Return the current value.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Return whether the current value is equal to the specified value.
	 */
	public boolean equals(int v) {
		return this.value == v;
	}

	/**
	 * Return whether the current value is not equal to the specified value.
	 */
	public boolean notEquals(int v) {
		return this.value != v;
	}

	/**
	 * Return whether the current value is zero.
	 */
	public boolean isZero() {
		return this.value == 0;
	}

	/**
	 * Return whether the current value is not zero.
	 */
	public boolean isNotZero() {
		return this.value != 0;
	}

	/**
	 * Return whether the current value is greater than the specified value.
	 */
	public boolean isGreaterThan(int v) {
		return this.value > v;
	}

	/**
	 * Return whether the current value is greater than or equal to the
	 * specified value.
	 */
	public boolean isGreaterThanOrEqual(int v) {
		return this.value >= v;
	}

	/**
	 * Return whether the current value is less than the specified value.
	 */
	public boolean isLessThan(int v) {
		return this.value < v;
	}

	/**
	 * Return whether the current value is less than or equal to the
	 * specified value.
	 */
	public boolean isLessThanOrEqual(int v) {
		return this.value <= v;
	}

	/**
	 * Return whether the current value is positive.
	 */
	public boolean isPositive() {
		return this.isGreaterThan(0);
	}

	/**
	 * Return whether the current value is not positive
	 * (i.e. negative or zero).
	 */
	public boolean isNotPositive() {
		return this.isLessThanOrEqual(0);
	}

	/**
	 * Return whether the current value is negative.
	 */
	public boolean isNegative() {
		return this.isLessThan(0);
	}

	/**
	 * Return whether the current value is not negative
	 * (i.e. zero or positive).
	 */
	public boolean isNotNegative() {
		return this.isGreaterThanOrEqual(0);
	}

	/**
	 * Set the value. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public int setValue(int value) {
		int old = this.value;
		this.value = value;
		return old;
	}

	/**
	 * Set the value to the absolute value of the current value.
	 * Return the new value.
	 */
	public int abs() {
		return this.value = Math.abs(this.value);
	}

	/**
	 * Set the value to the negative value of the current value.
	 * Return the new value.
	 */
	public int neg() {
		return this.value = -this.value;
	}

	/**
	 * Set the value to zero. If the value changes, all waiting
	 * threads are notified. Return the previous value.
	 */
	public int setZero() {
		return this.setValue(0);
	}

	/**
	 * Set the value to the current value plus the specified value.
	 * Return the new value.
	 */
	public int add(int v) {
		return this.value += v;
	}

	/**
	 * Increment and return the value.
	 */
	public int increment() {
		return ++this.value;
	}

	/**
	 * Set the value to the current value minus the specified value.
	 * Return the new value.
	 */
	public int subtract(int v) {
		return this.value -= v;
	}

	/**
	 * Decrement and return the value.
	 */
	public int decrement() {
		return --this.value;
	}

	/**
	 * Set the value to the current value times the specified value.
	 * Return the new value.
	 */
	public int multiply(int v) {
		return this.value *= v;
	}

	/**
	 * Set the value to the current value divided by the specified value.
	 * Return the new value.
	 */
	public int divide(int v) {
		return this.value /= v;
	}

	/**
	 * Set the value to the remainder of the current value divided by the
	 * specified value.
	 * Return the new value.
	 */
	public int remainder(int v) {
		return this.value %= v;
	}

	/**
	 * Set the value to the minimum of the current value and the specified value.
	 * Return the new value.
	 */
	public int min(int v) {
		return this.value = Math.min(this.value, v);
	}

	/**
	 * Set the value to the maximum of the current value and the specified value.
	 * Return the new value.
	 */
	public int max(int v) {
		return this.value = Math.max(this.value, v);
	}

	/**
	 * Set the value to the current value raised to the power of the'
	 * specified value.
	 * Return the new value.
	 */
	public int pow(int v) {
		return this.value = (int) Math.pow(this.value, v);
	}


	// ********** Comparable implementation **********

	public int compareTo(IntReference ir) {
		int v = ir.getValue();
		return this.value < v ? -1 : (this.value == v ? 0 : 1);
	}


	// ********** standard methods **********

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof IntReference) &&
			(this.value == ((IntReference) o).value);
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}

}
