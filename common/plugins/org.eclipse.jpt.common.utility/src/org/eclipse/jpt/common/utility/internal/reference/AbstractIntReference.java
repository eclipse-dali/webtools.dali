/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.predicate.IntPredicate;
import org.eclipse.jpt.common.utility.reference.IntReference;

/**
 * Implement some of the methods in {@link IntReference} that can
 * be defined in terms of the other methods.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * </ul>
 */
public abstract class AbstractIntReference
	implements IntReference
{
	protected AbstractIntReference() {
		super();
	}

	public boolean equals(int i) {
		return this.getValue() == i;
	}

	public boolean notEqual(int i) {
		return this.getValue() != i;
	}

	public boolean isZero() {
		return this.getValue() == 0;
	}

	public boolean isNotZero() {
		return this.getValue() != 0;
	}

	public boolean isGreaterThan(int i) {
		return this.getValue() > i;
	}

	public boolean isGreaterThanOrEqual(int i) {
		return this.getValue() >= i;
	}

	public boolean isLessThan(int i) {
		return this.getValue() < i;
	}

	public boolean isLessThanOrEqual(int i) {
		return this.getValue() <= i;
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
		return predicate.evaluate(this.getValue());
	}

	public boolean isNotMemberOf(IntPredicate predicate) {
		return ! this.isMemberOf(predicate);
	}


	// ********** Comparable **********

	public int compareTo(IntReference other) {
		int thisValue = this.getValue();
		int otherValue = other.getValue();
		return (thisValue < otherValue) ? -1 : ((thisValue == otherValue) ? 0 : 1);
	}


	// ********** standard methods **********

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
}
