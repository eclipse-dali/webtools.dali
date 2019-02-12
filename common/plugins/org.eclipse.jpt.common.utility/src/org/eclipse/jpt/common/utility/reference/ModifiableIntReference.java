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

/**
 * Interface for a container for passing an integer that can be changed by
 * the recipient.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ModifiableIntReference
	extends IntReference
{
	/**
	 * Set the <code>int</code> value.
	 * Return the previous value.
	 */
	int setValue(int value);

	/**
	 * Set the <code>int</code> value to zero.
	 * Return the previous value.
	 */
	int setZero();

	/**
	 * Increment the <code>int</code> value by one.
	 * Return the new value.
	 */
	int increment();

	/**
	 * Increment the <code>int</code> value by one.
	 * Return the new value.
	 * @see Math#incrementExact(int)
	 */
	int incrementExact();

	/**
	 * Decrement the <code>int</code> value by one.
	 * Return the new value.
	 */
	int decrement();

	/**
	 * Decrement the <code>int</code> value by one.
	 * Return the new value.
	 * @see Math#decrementExact(int)
	 */
	int decrementExact();

	/**
	 * Halve the <code>int</code> value.
	 * Return the new value.
	 */
	int halve();

	/**
	 * Double the <code>int</code> value.
	 * Return the new value.
	 */
	int twice();

	/**
	 * Double the <code>int</code> value.
	 * Return the new value.
	 */
	int twiceExact();

	/**
	 * Set <code>int</code> value to its absolute value.
	 * Return the new value.
	 */
	int abs();

	/**
	 * Set <code>int</code> value to its negative value.
	 * Return the new value.
	 */
	int negate();

	/**
	 * Set <code>int</code> value to its negative value.
	 * Return the new value.
	 * @see Math#negateExact(int)
	 */
	int negateExact();

	/**
	 * Add the specified value to the <code>int</code> value.
	 * Return the new value.
	 */
	int add(int i);

	/**
	 * Add the specified value to the <code>int</code> value.
	 * Return the new value.
	 * @see Math#addExact(int, int)
	 */
	int addExact(int i);

	/**
	 * Subtract the specified value from the <code>int</code> value.
	 * Return the new value.
	 */
	int subtract(int i);

	/**
	 * Subtract the specified value from the <code>int</code> value.
	 * Return the new value.
	 * @see Math#subtractExact(int, int)
	 */
	int subtractExact(int i);

	/**
	 * Multiply the <code>int</code> value by the specified value.
	 * Return the new value.
	 */
	int multiply(int i);

	/**
	 * Multiply the <code>int</code> value by the specified value.
	 * Return the new value.
	 * @see Math#multiplyExact(int, int)
	 */
	int multiplyExact(int i);

	/**
	 * Divide the <code>int</code> value by the specified value.
	 * Return the new value.
	 */
	int divide(int i);

	/**
	 * Divide the <code>int</code> value by the specified value.
	 * Return the new value.
	 * @see Math#floorDiv(int, int)
	 */
	int floorDivide(int i);

	/**
	 * Divide the <code>int</code> value by the specified value and set the
	 * <code>int</code> value to the remainder.
	 * Return the new value.
	 */
	int remainder(int i);

	/**
	 * Divide the <code>int</code> value by the specified value and set the
	 * <code>int</code> value to the remainder.
	 * Return the new value.
	 * @see Math#floorMod(int, int)
	 */
	int floorRemainder(int i);

	/**
	 * Set the <code>int</code> value to the minimum of itself and the
	 * specified value.
	 * Return the new value.
	 */
	int min(int i);

	/**
	 * Set the <code>int</code> value to the maximum of itself and the
	 * specified value.
	 * Return the new value.
	 */
	int max(int i);

	/**
	 * Set the <code>int</code> value to the specified new value if it is
	 * currently the specified expected value.
	 * Return whether the set was successful.
	 */
	boolean commit(int newValue, int expectedValue);

	/**
 	 * Swap the <code>int</code> value of the <code>int</code> reference with
 	 * the value of the specified <code>int</code> reference.
 	 * Return the new value.
	 */
	int swap(ModifiableIntReference other);
}
