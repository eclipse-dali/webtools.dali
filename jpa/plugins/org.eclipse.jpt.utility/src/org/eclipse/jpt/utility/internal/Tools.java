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

import java.util.Iterator;

/**
 * Various utility methods.
 */
public class Tools {

	/**
	 * Return whether the specified values are equal, with the appropriate null checks.
	 */
	public static boolean valuesAreEqual(Object value1, Object value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;	// both are null
		}
		if ((value1 == null) || (value2 == null)) {
			return false;	// one is null but the other is not
		}
		return value1.equals(value2);  // neither are null
	}

	/**
	 * Return whether the specified values are different, with the appropriate null checks.
	 */
	public static boolean valuesAreDifferent(Object value1, Object value2) {
		return ! valuesAreEqual(value1, value2);
	}

	/**
	 * Return whether the specified iterables return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
		Iterator<?> iterator1 = iterable1.iterator();
		Iterator<?> iterator2 = iterable2.iterator();
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (valuesAreDifferent(iterator1.next(), iterator2.next())) {
				return false;
			}
		}
		return ( ! iterator1.hasNext()) && ( ! iterator2.hasNext());
	}

	/**
	 * Return whether the specified iterables do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Iterable<?> iterable1, Iterable<?> iterable2) {
		return ! elementsAreEqual(iterable1, iterable2);
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
