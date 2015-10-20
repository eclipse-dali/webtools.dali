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
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;
import org.eclipse.jpt.common.utility.reference.IntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;

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
	implements ModifiableIntReference, Cloneable, Serializable
{
	/** Backing <code>int</code>. */
	private volatile int value = 0;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an <code>int</code> reference with the specified initial value.
	 */
	public SimpleIntReference(int count) {
		super();
		this.value = count;
	}

	/**
	 * Construct an <code>int</code> reference with an initial value of zero.
	 */
	public SimpleIntReference() {
		this(0);
	}


	// ********** IntReference **********

	public int getValue() {
		return this.value;
	}

	public boolean equals(int i) {
		return this.value == i;
	}

	public boolean notEqual(int i) {
		return this.value != i;
	}

	public boolean isZero() {
		return this.value == 0;
	}

	public boolean isNotZero() {
		return this.value != 0;
	}

	public boolean isGreaterThan(int i) {
		return this.value > i;
	}

	public boolean isGreaterThanOrEqual(int i) {
		return this.value >= i;
	}

	public boolean isLessThan(int i) {
		return this.value < i;
	}

	public boolean isLessThanOrEqual(int i) {
		return this.value <= i;
	}

	public boolean isPositive() {
		return this.value > 0;
	}

	public boolean isNotPositive() {
		return this.value <= 0;
	}

	public boolean isNegative() {
		return this.value < 0;
	}

	public boolean isNotNegative() {
		return this.value >= 0;
	}

	public boolean isMemberOf(IntPredicate predicate) {
		return predicate.evaluate(this.value);
	}

	public boolean isNotMemberOf(IntPredicate predicate) {
		return ! predicate.evaluate(this.value);
	}


	// ********** ModifiableIntReference **********

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

	public int incrementExact() {
		this.value = Math.incrementExact(this.value);
		return this.value;
	}

	public int decrement() {
		return --this.value;
	}

	public int decrementExact() {
		this.value = Math.decrementExact(this.value);
		return this.value;
	}

	public int halve() {
		this.value = BitTools.half(this.value);
		return this.value;
	}

	public int twice() {
		this.value = BitTools.twice(this.value);
		return this.value;
	}

	public int twiceExact() {
		this.value = Math.multiplyExact(this.value, 2);
		return this.value;
	}

	public int abs() {
		this.value = Math.abs(this.value);
		return this.value;
	}

	public int negate() {
		this.value = -this.value;
		return this.value;
	}

	public int negateExact() {
		this.value = Math.negateExact(this.value);
		return this.value;
	}

	public int add(int i) {
		this.value += i;
		return this.value;
	}

	public int addExact(int i) {
		this.value = Math.addExact(this.value, i);
		return this.value;
	}

	public int subtract(int i) {
		this.value -= i;
		return this.value;
	}

	public int subtractExact(int i) {
		this.value = Math.subtractExact(this.value, i);
		return this.value;
	}

	public int multiply(int i) {
		this.value = this.value * i;
		return this.value;
	}

	public int multiplyExact(int i) {
		this.value = Math.multiplyExact(this.value, i);
		return this.value;
	}

	public int divide(int i) {
		this.value = this.value / i;
		return this.value;
	}

	public int floorDivide(int i) {
		this.value = Math.floorDiv(this.value, i);
		return this.value;
	}

	public int remainder(int i) {
		this.value = this.value % i;
		return this.value;
	}

	public int floorRemainder(int i) {
		this.value = Math.floorMod(this.value, i);
		return this.value;
	}

	public int min(int i) {
		this.value = Math.min(this.value, i);
		return this.value;
	}

	public int max(int i) {
		this.value = Math.max(this.value, i);
		return this.value;
	}

	public boolean commit(int newValue, int expectedValue) {
		if (this.value == expectedValue) {
			this.value = newValue;
			return true;
		}
		return false;
	}

	public int swap(ModifiableIntReference other) {
		if (other == this) {
			return this.value;
		}
		int otherValue = other.getValue();
		if (otherValue == this.value) {
			return this.value;
		}
		other.setValue(this.value);
		this.value = otherValue;
		return otherValue;
	}


	// ********** Comparable **********

	public int compareTo(IntReference other) {
		int otherValue = other.getValue();
		return (this.value < otherValue) ? -1 : ((this.value == otherValue) ? 0 : 1);
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

	/**
	 * Object identity is critical to <code>int</code> references.
	 * There is no reason for two different <code>int</code> references to be
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
		return '[' + String.valueOf(this.value) + ']';
	}
}
