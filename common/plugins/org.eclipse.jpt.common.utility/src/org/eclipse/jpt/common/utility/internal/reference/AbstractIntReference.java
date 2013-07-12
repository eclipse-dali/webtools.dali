/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

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

	public boolean equals(int v) {
		return this.getValue() == v;
	}

	public boolean notEqual(int v) {
		return this.getValue() != v;
	}

	public boolean isZero() {
		return this.getValue() == 0;
	}

	public boolean isNotZero() {
		return this.getValue() != 0;
	}

	public boolean isGreaterThan(int v) {
		return this.getValue() > v;
	}

	public boolean isGreaterThanOrEqual(int v) {
		return this.getValue() >= v;
	}

	public boolean isLessThan(int v) {
		return this.getValue() < v;
	}

	public boolean isLessThanOrEqual(int v) {
		return this.getValue() <= v;
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
		return Math.abs(this.getValue());
	}

	public int neg() {
		return -this.getValue();
	}

	public int add(int v) {
		return this.getValue() + v;
	}

	public int subtract(int v) {
		return this.getValue() - v;
	}

	public int multiply(int v) {
		return this.getValue() * v;
	}

	public int divide(int v) {
		return this.getValue() / v;
	}

	public int remainder(int v) {
		return this.getValue() % v;
	}

	public int min(int v) {
		return Math.min(this.getValue(), v);
	}

	public int max(int v) {
		return Math.max(this.getValue(), v);
	}

	public double pow(int v) {
		return Math.pow(this.getValue(), v);
	}


	// ********** Comparable implementation **********

	public int compareTo(IntReference ref) {
		int v = ref.getValue();
		return (this.getValue() < v) ? -1 : ((this.getValue() == v) ? 0 : 1);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return '[' + String.valueOf(this.getValue()) + ']';
	}
}
