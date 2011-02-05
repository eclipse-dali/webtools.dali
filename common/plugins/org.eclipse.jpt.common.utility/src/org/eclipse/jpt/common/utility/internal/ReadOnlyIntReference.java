/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Interface for a container for holding an <code>int</code> that cannot be
 * changed by clients.
 */
public interface ReadOnlyIntReference
	extends Comparable<ReadOnlyIntReference>
{
	/**
	 * Return the current <code>int</code> value.
	 */
	int getValue();

	/**
	 * Return whether the current <code>int</code> value is equal to the
	 * specified value.
	 */
	boolean equals(int v);

	/**
	 * Return whether the current <code>int</code> value is not equal to
	 * the specified value.
	 */
	boolean notEqual(int v);

	/**
	 * Return whether the current <code>int</code> value is zero.
	 */
	boolean isZero();

	/**
	 * Return whether the current <code>int</code> value is not zero.
	 */
	boolean isNotZero();

	/**
	 * Return whether the current <code>int</code> value is greater than
	 * the specified value.
	 */
	boolean isGreaterThan(int v);

	/**
	 * Return whether the current <code>int</code> value is greater than
	 * or equal to the specified value.
	 */
	boolean isGreaterThanOrEqual(int v);

	/**
	 * Return whether the current <code>int</code> value is less than
	 * the specified value.
	 */
	boolean isLessThan(int v);

	/**
	 * Return whether the current <code>int</code> value is less than
	 * or equal to the specified value.
	 */
	boolean isLessThanOrEqual(int v);

	/**
	 * Return whether the current <code>int</code> value is positive.
	 */
	boolean isPositive();

	/**
	 * Return whether the current <code>int</code> value is not positive
	 * (i.e. negative or zero).
	 */
	boolean isNotPositive();

	/**
	 * Return whether the current <code>int</code> value is negative.
	 */
	boolean isNegative();

	/**
	 * Return whether the current <code>int</code> value is not negative
	 * (i.e. zero or positive).
	 */
	boolean isNotNegative();

	/**
	 * Return the absolute value of the current <code>int</code> value.
	 */
	int abs();

	/**
	 * Return the negative value of the current <code>int</code> value.
	 */
	int neg();

	/**
	 * Return the current <code>int</code> value plus the specified value.
	 */
	int add(int v);

	/**
	 * Return current <code>int</code> value minus the specified value.
	 */
	int subtract(int v);

	/**
	 * Return current <code>int</code> value multiplied by the specified value.
	 */
	int multiply(int v);

	/**
	 * Return current <code>int</code> value divided by the specified value.
	 */
	int divide(int v);

	/**
	 * Return the remainder of the current <code>int</code> value divided by
	 * the specified value.
	 */
	int remainder(int v);

	/**
	 * Return the minimum of the current <code>int</code> value and
	 * the specified value.
	 */
	int min(int v);

	/**
	 * Return the maximum of the current <code>int</code> value and
	 * the specified value.
	 */
	int max(int v);

	/**
	 * Return the current <code>int</code> value raised to the power
	 * of the specified value.
	 */
	double pow(int v);
}
