/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Assorted bit tools
 */
public class BitTools {

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean allFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) == flagsToCheck;
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean anyFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) != 0;
	}

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean allFlagsAreSet(int flags, int[] flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if ( ! allFlagsAreSet(flags, flagsToCheck[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean anyFlagsAreSet(int flags, int[] flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if (anyFlagsAreSet(flags, flagsToCheck[i])) {
				return true;
			}
		}
		return false;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private BitTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
