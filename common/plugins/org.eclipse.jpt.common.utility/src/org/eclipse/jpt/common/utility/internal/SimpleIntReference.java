/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

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
public final class SimpleIntReference
	implements IntReference, Cloneable, Serializable
{
	/** Backing <code>int</code>. */
	private volatile int value = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a <code>int</code> reference with the specified initial value.
	 */
	public SimpleIntReference(int count) {
		super();
		this.value = count;
	}

	/**
	 * Construct a <code>int</code> reference with an initial value of zero.
	 */
	public SimpleIntReference() {
		this(0);
	}


	// ********** methods **********

	public int getValue() {
		return this.value;
	}

	public boolean equals(int v) {
		return this.value == v;
	}

	public boolean notEqual(int v) {
		return this.value != v;
	}

	public boolean isZero() {
		return this.value == 0;
	}

	public boolean isNotZero() {
		return this.value != 0;
	}

	public boolean isGreaterThan(int v) {
		return this.value > v;
	}

	public boolean isGreaterThanOrEqual(int v) {
		return this.value >= v;
	}

	public boolean isLessThan(int v) {
		return this.value < v;
	}

	public boolean isLessThanOrEqual(int v) {
		return this.value <= v;
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
		return Math.abs(this.value);
	}

	public int neg() {
		return -this.value;
	}

	public int add(int v) {
		return this.value + v;
	}

	public int subtract(int v) {
		return this.value - v;
	}

	public int multiply(int v) {
		return this.value * v;
	}

	public int divide(int v) {
		return this.value / v;
	}

	public int remainder(int v) {
		return this.value % v;
	}

	public int min(int v) {
		return Math.min(this.value, v);
	}

	public int max(int v) {
		return Math.max(this.value, v);
	}

	public double pow(int v) {
		return Math.pow(this.value, v);
	}

	public int setValue(int value) {
		int old = this.value;
		this.value = value;
		return old;
	}

	public int setZero() {
		return this.setValue(0);
	}

	public int increment() {
		return ++this.value;
	}

	public int decrement() {
		return --this.value;
	}


	// ********** Comparable implementation **********

	public int compareTo(ReadOnlyIntReference ref) {
		int v = ref.getValue();
		return (this.value < v) ? -1 : ((this.value == v) ? 0 : 1);
	}


	// ********** standard methods **********

	@Override
	public SimpleIntReference clone() {
		try {
			return (SimpleIntReference) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}
