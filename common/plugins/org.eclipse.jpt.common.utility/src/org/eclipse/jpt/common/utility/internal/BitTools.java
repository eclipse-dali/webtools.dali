/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Bit utility methods.
 */
public final class BitTools {

	/**
	 * Return whether the specified flags have the specified
	 * flag to check set.
	 * @see #setFlag(int, int)
	 */
	public static boolean flagIsSet(int flags, int flagToCheck) {
		return allFlagsAreSet(flags, flagToCheck);
	}

	/**
	 * Set the specified flag in the specified flags.
	 * Return the new flags.
	 * @see #flagIsSet(int, int)
	 */
	public static int setFlag(int flags, int flagToSet) {
		return setAllFlags(flags, flagToSet);
	}

	/**
	 * Return whether the specified flags have the specified
	 * flag to check turned off.
	 * @see #clearFlag(int, int)
	 */
	public static boolean flagIsOff(int flags, int flagToCheck) {
		return allFlagsAreOff(flags, flagToCheck);
	}

	/**
	 * Clear the specified flag in the specified flags.
	 * Return the new flags.
	 * @see #flagIsOff(int, int)
	 */
	public static int clearFlag(int flags, int flagToClear) {
		return clearAllFlags(flags, flagToClear);
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flag to check set.
	 * <p>
	 * No setter method necessary; simply use the flag itself.
	 */
	public static boolean onlyFlagIsSet(int flags, int flagToCheck) {
		return onlyFlagsAreSet(flags, flagToCheck);
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flag to check turned off.
	 * <p>
	 * No setter method necessary; simply use the flag itself, flipped.
	 */
	public static boolean onlyFlagIsOff(int flags, int flagToCheck) {
		return onlyFlagsAreOff(flags, flagToCheck);
	}

	/**
	 * Return whether the specified flags have all the specified
	 * flags to check set.
	 * @see #setAllFlags(int, int)
	 */
	public static boolean allFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) == flagsToCheck;
	}

	/**
	 * Set the specified flags in the specified flags.
	 * Return the new flags.
	 * @see #allFlagsAreSet(int, int)
	 */
	public static int setAllFlags(int flags, int flagsToSet) {
		return flags | flagsToSet;
	}

	/**
	 * Return whether the specified flags have all the specified
	 * flags to check turned off.
	 * @see #clearAllFlags(int, int)
	 */
	public static boolean allFlagsAreOff(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) == 0;
	}

	/**
	 * Clear the specified flags in the specified flags.
	 * Return the new flags.
	 * @see #allFlagsAreOff(int, int)
	 */
	public static int clearAllFlags(int flags, int flagsToClear) {
		return flags & ~flagsToClear;
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flags to check set.
	 * <p>
	 * No setter method necessary; simply use the flags themselves.
	 */
	public static boolean onlyFlagsAreSet(int flags, int flagsToCheck) {
		return allFlagsAreSet(flags, flagsToCheck) && allFlagsAreOff(flags, ~flagsToCheck);
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flags to check turned off.
	 * <p>
	 * No setter method necessary; simply use the flags themselves, flippped.
	 */
	public static boolean onlyFlagsAreOff(int flags, int flagsToCheck) {
		return allFlagsAreOff(flags, flagsToCheck) && allFlagsAreSet(flags, ~flagsToCheck);
	}

	/**
	 * Return whether the specified flags have any one of the specified
	 * flags to check set.
	 */
	public static boolean anyFlagsAreSet(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) != 0;
	}

	/**
	 * Return whether the specified flags have any one of the specified
	 * flags to check turned off.
	 */
	public static boolean anyFlagsAreOff(int flags, int flagsToCheck) {
		return (flags & flagsToCheck) != flagsToCheck;
	}

	/**
	 * Return whether the specified flags have all the specified
	 * flags to check set.
	 * @see #setAllFlags(int, int...)
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
	 * Set the specified flags in the specified flags.
	 * Return the new flags.
	 * @see #allFlagsAreSet(int, int...)
	 */
	public static int setAllFlags(int flags, int... flagsToSet) {
		for (int i = flagsToSet.length; i-- > 0; ) {
			flags |= flagsToSet[i];
		}
		return flags;
	}

	/**
	 * Return whether the specified flags have all the specified
	 * flags to check turned off.
	 * @see #clearAllFlags(int, int...)
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
	 * Clear the specified flags in the specified flags.
	 * Return the new flags.
	 * @see #allFlagsAreOff(int, int...)
	 */
	public static int clearAllFlags(int flags, int... flagsToClear) {
		int combinedFlags = 0;
		for (int i = flagsToClear.length; i-- > 0; ) {
			combinedFlags |= flagsToClear[i];
		}
		return clearAllFlags(flags, combinedFlags);
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flags to check set.
	 * <p>
	 * No setter method necessary; simply use the ORed flags themselves.
	 */
	public static boolean onlyFlagsAreSet(int flags, int... flagsToCheck) {
		int combinedFlags = orFlags(flagsToCheck);
		return allFlagsAreSet(flags, combinedFlags) && allFlagsAreOff(flags, ~combinedFlags);
	}

	/**
	 * Return whether the specified flags have <em>only</em> the specified
	 * flags to check turned off.
	 * <p>
	 * No setter method necessary; simply use the ORed flags themselves, flipped.
	 */
	public static boolean onlyFlagsAreOff(int flags, int... flagsToCheck) {
		int combinedFlags = orFlags(flagsToCheck);
		return allFlagsAreOff(flags, combinedFlags) && allFlagsAreSet(flags, ~combinedFlags);
	}

	/**
	 * Return whether the specified flags have any one of the specified
	 * flags to check set.
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
	 * Return whether the specified flags have any one of the specified
	 * flags to check turned off.
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
	 * OR all the specified flags together and return the result.
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
	 * AND all the specified flags together and return the result.
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
	 * XOR all the specified flags together and return the result.
	 * <p>
	 * <strong>NB:</strong> The order of the flags is significant.
	 */
	public static int xorFlags(int... flags) {
		int last = flags.length - 1;
		int result = flags[last];
		for (int i = last; i-- > 0; ) {
			result ^= flags[i];
		}
		return result;
	}

	/**
	 * Return whether the specified integer is even.
	 * @see #isOdd(int)
	 */
	public static boolean isEven(int i) {
		return (i & 1) == 0;
	}

	/**
	 * Return whether the specified integer is odd.
	 * @see #isEven(int)
	 */
	public static boolean isOdd(int i) {
		return (i & 1) == 1;
	}

	/**
	 * Return half the specified integer, rounding to negative infinity
	 * (i.e. <code>half(7) ==> 3</code> and <code>half(-7) ==> -4</code>).
	 * @see #twice(int)
	 */
	public static int half(int i) {
		return i >> 1;
	}

	/**
	 * Return twice the specified integer
	 * (i.e. <code>twice(7) ==> 14</code> and <code>twice(-7) ==> -14</code>).
	 * @see #half(int)
	 */
	public static int twice(int i) {
		return i << 1;
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
