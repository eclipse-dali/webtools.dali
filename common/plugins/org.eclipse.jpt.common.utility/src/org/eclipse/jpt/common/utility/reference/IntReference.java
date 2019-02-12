/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * Interface for a container for holding an <code>int</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see ModifiableIntReference
 */
public interface IntReference
	extends Comparable<IntReference>
{
	/**
	 * Return the current <code>int</code> value.
	 */
	int getValue();

	/**
	 * Return whether the current <code>int</code> value is equal to the
	 * specified value.
	 */
	boolean equals(int i);

	/**
	 * Return whether the current <code>int</code> value is not equal to
	 * the specified value.
	 */
	boolean notEqual(int i);

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
	boolean isGreaterThan(int i);

	/**
	 * Return whether the current <code>int</code> value is greater than
	 * or equal to the specified value.
	 */
	boolean isGreaterThanOrEqual(int i);

	/**
	 * Return whether the current <code>int</code> value is less than
	 * the specified value.
	 */
	boolean isLessThan(int i);

	/**
	 * Return whether the current <code>int</code> value is less than
	 * or equal to the specified value.
	 */
	boolean isLessThanOrEqual(int i);

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
	 * Return whether the current <code>int</code> value is a
	 * member of the set specified by the specified predicate.
	 */
	boolean isMemberOf(IntPredicate predicate);

	/**
	 * Return whether the current <code>int</code> value is not a
	 * member of the set specified by the specified predicate.
	 */
	boolean isNotMemberOf(IntPredicate predicate);
}
