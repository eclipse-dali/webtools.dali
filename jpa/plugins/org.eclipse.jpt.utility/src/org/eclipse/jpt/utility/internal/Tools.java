/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Various utility methods.
 */
public class Tools {

	/**
	 * Return whether the specified values are equal, with the appropriate null checks.
	 */
	public static boolean valuesAreEqual(Object value1, Object value2) {
		return (value1 == null) ? (value2 == null) : value1.equals(value2);
	}

	/**
	 * Return whether the specified values are different, with the appropriate null checks.
	 */
	public static boolean valuesAreDifferent(Object value1, Object value2) {
		return ! valuesAreEqual(value1, value2);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private Tools() {
		super();
		throw new UnsupportedOperationException();
	}

}
