/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Assorted bit tools
 */
public final class BitTools {

	/**
	 * Return whether the specified 'flags' has the specified
	 * 'flagToCheck' set.
	 */
	public static boolean flagIsSet(int flags, int flagToCheck) {
		return allFlagsAreSet(flags, flagToCheck);
	}

	/**
	 * Return whether the specified 'flags' has the specified
	 * 'flagToCheck' turned off.
	 */
	public static boolean flagIsOff(int flags, int flagToCheck) {
		return allFlagsAreOff(flags, flagToCheck);
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagToCheck' set.
	 */
	public static boolean onlyFlagIsSet(int flags, int flagToCheck) {
		return onlyFlagsAreSet(flags, flagToCheck);
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagToCheck' turned off.
	 */
	public static boolean onlyFlagIsOff(int flags, int flagToCheck) {
		return onlyFlagsAreOff(flags, flagToCheck);
	}

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean allFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) == flagsToCheck;
	}

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean allFlagsAreOff(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) == 0;
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean onlyFlagsAreSet(int flags, int flagsToCheck) {
		return allFlagsAreSet(flags, flagsToCheck) && allFlagsAreOff(flags, ~flagsToCheck);
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean onlyFlagsAreOff(int flags, int flagsToCheck) {
		return allFlagsAreOff(flags, flagsToCheck) && allFlagsAreSet(flags, ~flagsToCheck);
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean anyFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) != 0;
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean anyFlagsAreOff(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) != flagsToCheck;
	}

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean allFlagsAreSet(int flags, int... flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if ( ! allFlagsAreSet(flags, flagsToCheck[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified 'flags' has all the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean allFlagsAreOff(int flags, int... flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if ( ! allFlagsAreOff(flags, flagsToCheck[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean onlyFlagsAreSet(int flags, int... flagsToCheck) {
		int combinedFlags = orFlags(flagsToCheck);
		return allFlagsAreSet(flags, combinedFlags) && allFlagsAreOff(flags, ~combinedFlags);
	}

	/**
	 * Return whether the specified 'flags' has ONLY the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean onlyFlagsAreOff(int flags, int... flagsToCheck) {
		int combinedFlags = orFlags(flagsToCheck);
		return allFlagsAreOff(flags, combinedFlags) && allFlagsAreSet(flags, ~combinedFlags);
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' set.
	 */
	public static boolean anyFlagsAreSet(int flags, int... flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if (anyFlagsAreSet(flags, flagsToCheck[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified 'flags' has any one of the specified
	 * 'flagsToCheck' turned off.
	 */
	public static boolean anyFlagsAreOff(int flags, int... flagsToCheck) {
		for (int i = flagsToCheck.length; i-- > 0; ) {
			if (anyFlagsAreOff(flags, flagsToCheck[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * OR all the specified 'flags' together and return the result.
	 */
	public static int orFlags(int... flags) {
		int last = flags.length - 1;
		int result = flags[last];
		for (int i = last; i-- > 0; ) {
			result |= flags[i];
		}
		return result;
	}

	/**
	 * AND all the specified 'flags' together and return the result.
	 */
	public static int andFlags(int... flags) {
		int last = flags.length - 1;
		int result = flags[last];
		for (int i = last; i-- > 0; ) {
			result &= flags[i];
		}
		return result;
	}

	/**
	 * XOR all the specified 'flags' together and return the result.
	 */
	public static int xorFlags(int... flags) {
		int last = flags.length - 1;
		int result = flags[last];
		for (int i = last; i-- > 0; ) {
			result ^= flags[i];
		}
		return result;
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
