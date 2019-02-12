/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * "Capital-B {@link Boolean}" utility methods.
 */
 // commented code is just playing around with building *everything* from NAND
public final class BooleanTools {

	/**
	 * Return the NOT of the specified boolean.
	 */
	public static Boolean not(Boolean b) {
		return Boolean.valueOf( ! b.booleanValue());
//		return nand(b, b);
	}

	/**
	 * Return the AND of the specified booleans.
	 */
	public static Boolean and(Boolean b1, Boolean b2) {
		return Boolean.valueOf(b1.booleanValue() && b2.booleanValue());
//		Boolean nand = nand(b1, b2);
//		return nand(nand, nand);
	}

	/**
	 * Return the OR of the specified booleans.
	 */
	public static Boolean or(Boolean b1, Boolean b2) {
		return Boolean.valueOf(b1.booleanValue() || b2.booleanValue());
//		Boolean nand = nand(b1, b2);
//		Boolean xor = nand(nand(b1, nand), nand(b2, nand));
//		Boolean and = nand(nand, nand);
//		Boolean nand2 = nand(xor, and);
//		return nand(nand(xor, nand2), nand(and, nand2));
	}

	/**
	 * Return the XOR of the specified booleans.
	 */
	public static Boolean xor(Boolean b1, Boolean b2) {
		return and(or(b1, b2), nand(b1, b2));
//		Boolean nand = nand(b1, b2);
//		return nand(nand(b1, nand), nand(b2, nand));
	}

	/**
	 * Return the NAND of the specified booleans.
	 */
	public static Boolean nand(Boolean b1, Boolean b2) {
		return not(and(b1, b2));
//		return Boolean.valueOf( ! (b1.booleanValue() && b2.booleanValue()));
	}

	/**
	 * Return the NOR of the specified booleans.
	 */
	public static Boolean nor(Boolean b1, Boolean b2) {
		return not(or(b1, b2));
//		Boolean nand = nand(b1, b2);
//		Boolean xor = nand(nand(b1, nand), nand(b2, nand));
//		Boolean and = nand(nand, nand);
//		Boolean nand2 = nand(xor, and);
//		Boolean nand3 = nand(nand(xor, nand2), nand(and, nand2));
//		return nand(nand3, nand3);
	}

	/**
	 * Return the XNOR of the specified booleans.
	 */
	public static Boolean xnor(Boolean b1, Boolean b2) {
		return not(xor(b1, b2));
//		Boolean nand = nand(b1, b2);
//		Boolean xor = nand(nand(b1, nand), nand(b2, nand));
//		return nand(xor, xor);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private BooleanTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
