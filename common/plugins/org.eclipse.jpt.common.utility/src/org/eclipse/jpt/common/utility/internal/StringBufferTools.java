/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * {@link StringBuffer} utility methods.
 * <p>
 * As of JDK 1.5, it's tempting to convert all of these methods to use
 * {@link Appendable};
 * but all the {@link Appendable} methods throw {@link java.io.IOException} (yech) and
 * we [might?] get a bit better performance invoking methods on classes than
 * we get on interfaces. :-)
 * 
 * @see StringTools
 */
public final class StringBufferTools {

	// ********** char[] **********

	/**
	 * Convert the specified string buffer to a charactor array
	 * (<code>char[]</code>).
	 */
	public static char[] convertToCharArray(StringBuffer sb) {
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}


	// ********** padding/truncating/centering **********

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * each end.
	 */
	public static void center(StringBuffer sb, String string, int length) {
		center(sb, string, length, ' ');
	}

	/**
	 * @see #center(StringBuffer, String, int)
	 */
	public static void center(StringBuffer sb, char[] string, int length) {
		center(sb, string, length, ' ');
	}

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at each end.
	 */
	public static void center(StringBuffer sb, String string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			int begin = (stringLength - length) >> 1; // take fewer characters off the front
			sb.append(string, begin, begin + length);  // NB: end index is exclusive
		} else {
			int begin = (length - stringLength) >> 1; // add fewer characters to the front
			fill(sb, begin, c);
			sb.append(string);
			fill(sb, length - (begin + stringLength), c);
		}
	}

	/**
	 * @see #center(StringBuffer, String, int, char)
	 */
	public static void center(StringBuffer sb, char[] string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			int begin = (stringLength - length) >> 1; // take fewer characters off the front
			sb.append(string, begin, length);
		} else {
			int begin = (length - stringLength) >> 1; // add fewer characters to the front
			fill(sb, begin, c);
			sb.append(string);
			fill(sb, length - (begin + stringLength), c);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with spaces
	 * at the end.
	 */
	public static void pad(StringBuffer sb, String string, int length) {
		pad(sb, string, length, ' ');
	}

	/**
	 * @see #pad(StringBuffer, String, int)
	 */
	public static void pad(StringBuffer sb, char[] string, int length) {
		pad(sb, string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static void pad(StringBuffer sb, String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			pad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * @see #pad(StringBuffer, String, int, char)
	 */
	public static void pad(StringBuffer sb, char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			pad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * the end.
	 */
	public static void fit(StringBuffer sb, String string, int length) {
		fit(sb, string, length, ' ');
	}

	/**
	 * @see #fit(StringBuffer, String, int)
	 */
	public static void fit(StringBuffer sb, char[] string, int length) {
		fit(sb, string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static void fit(StringBuffer sb, String string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);  // NB: end index is exclusive
		} else {
			pad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * @see #fit(StringBuffer, String, int, char)
	 */
	public static void fit(StringBuffer sb, char[] string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);
		} else {
			pad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void pad_(StringBuffer sb, String string, int stringLength, int length, char c) {
		sb.append(string);
		fill(sb, stringLength, length, c);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void pad_(StringBuffer sb, char[] string, int stringLength, int length, char c) {
		sb.append(string);
		fill(sb, stringLength, length, c);
	}

	/**
	 * Add enough characters to the specified stream to compensate for
	 * the difference between the specified string length and specified length.
	 */
	private static void fill(StringBuffer sb, int stringLength, int length, char c) {
		fill(sb, length - stringLength, c);
	}

	/**
	 * Add the specified length of characters to the specified stream.
	 */
	private static void fill(StringBuffer sb, int length, char c) {
		sb.append(ArrayTools.fill(new char[length], c));
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with zeros
	 * at the front.
	 */
	public static void zeroPad(StringBuffer sb, String string, int length) {
		frontPad(sb, string, length, '0');
	}

	/**
	 * @see #zeroPad(StringBuffer, String, int)
	 */
	public static void zeroPad(StringBuffer sb, char[] string, int length) {
		frontPad(sb, string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static void frontPad(StringBuffer sb, String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * @see #frontPad(StringBuffer, String, int, char)
	 */
	public static void frontPad(StringBuffer sb, char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, append only the last
	 * part of the string.
	 * If the string is shorter than the specified length, pad it with zeros
	 * at the front.
	 */
	public static void zeroFit(StringBuffer sb, String string, int length) {
		frontFit(sb, string, length, '0');
	}

	/**
	 * @see #zeroFit(StringBuffer, String, int)
	 */
	public static void zeroFit(StringBuffer sb, char[] string, int length) {
		frontFit(sb, string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, append only the last
	 * part of the string.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static void frontFit(StringBuffer sb, String string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, stringLength - length, stringLength);  // NB: end index is exclusive
		} else {
			frontPad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * @see #frontFit(StringBuffer, String, int, char)
	 */
	public static void frontFit(StringBuffer sb, char[] string, int length, char c) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, stringLength - length, length);
		} else {
			frontPad_(sb, string, stringLength, length, c);
		}
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void frontPad_(StringBuffer sb, String string, int stringLength, int length, char c) {
		fill(sb, stringLength, length, c);
		sb.append(string);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void frontPad_(StringBuffer sb, char[] string, int stringLength, int length, char c) {
		fill(sb, stringLength, length, c);
		sb.append(string);
	}


	// ********** separating **********

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<p>
	 * <code>
	 * separate("012345", '-', 2) => "01-23-45"
	 * </code>
	 */
	public static void separate(StringBuffer sb, String string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length();
		if (stringLength <= segmentSize) {
			sb.append(string);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / segmentSize));
			separate(sb, string, separator, segmentSize, stringLength);
		}
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separate(StringBuffer sb, String string, char separator, int segmentSize, int stringLength) {
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			if (segCount == segmentSize) {
				sb.append(separator);
				segCount = 0;
			}
			segCount++;
			sb.append(c);
		}
	}

	/**
	 * @see #separate(StringBuffer, String, char, int)
	 */
	public static void separate(StringBuffer sb, char[] string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length;
		if (stringLength <= segmentSize) {
			sb.append(string);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / segmentSize));
			separate(sb, string, separator, segmentSize, stringLength);
		}
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separate(StringBuffer sb, char[] string, char separator, int segmentSize, int stringLength) {
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			if (segCount == segmentSize) {
				sb.append(separator);
				segCount = 0;
			}
			segCount++;
			sb.append(c);
		}
	}


	// ********** delimiting/quoting **********

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quote(StringBuffer sb, String string) {
		delimit(sb, string, CharacterTools.QUOTE);
	}

	/**
	 * @see #quote(StringBuffer, String)
	 */
	public static void quote(StringBuffer sb, char[] string) {
		delimit(sb, string, CharacterTools.QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimit(StringBuffer sb, String string, char delimiter) {
		int stringLength = string.length();
		sb.ensureCapacity(sb.length() + stringLength + 2);
		delimit(sb, string, delimiter, stringLength);
	}

	private static void delimit(StringBuffer sb, String string, char delimiter, int stringLength) {
		sb.append(delimiter);
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			if (c == delimiter) {
				sb.append(c);
			}
			sb.append(c);
		}
		sb.append(delimiter);
	}

	/**
	 * @see #delimit(StringBuffer, String, char)
	 */
	public static void delimit(StringBuffer sb, char[] string, char delimiter) {
		int stringLength = string.length;
		sb.ensureCapacity(sb.length() + stringLength + 2);
		delimit(sb, string, delimiter, stringLength);
	}

	private static void delimit(StringBuffer sb, char[] string, char delimiter, int stringLength) {
		sb.append(delimiter);
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			if (c == delimiter) {
				sb.append(c);
			}
			sb.append(c);
		}
		sb.append(delimiter);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimit(StringBuffer sb, String string, String delimiter) {
		int delimiterLength = delimiter.length();
		switch (delimiterLength) {
			case 0:
				sb.append(string);
				break;
			case 1:
				delimit(sb, string, delimiter.charAt(0));
				break;
			default:
				delimit(sb, string, delimiter, delimiterLength);
				break;
		}
	}

	/**
	 * No parm check
	 */
	private static void delimit(StringBuffer sb, String string, String delimiter, int delimiterLength) {
		sb.append(delimiter, 0, delimiterLength);
		sb.append(string);
		sb.append(delimiter, 0, delimiterLength);
	}

	/**
	 * @see #delimit(StringBuffer, String, String)
	 */
	public static void delimit(StringBuffer sb, char[] string, char[] delimiter) {
		int delimiterLength = delimiter.length;
		switch (delimiterLength) {
			case 0:
				sb.append(string);
				break;
			case 1:
				delimit(sb, string, delimiter[0]);
				break;
			default:
				delimit(sb, string, delimiter, delimiterLength);
				break;
		}
	}

	/**
	 * No parm check
	 */
	private static void delimit(StringBuffer sb, char[] string, char[] delimiter, int delimiterLength) {
		sb.append(delimiter, 0, delimiterLength);
		sb.append(string);
		sb.append(delimiter, 0, delimiterLength);
	}


	// ********** undelimiting **********

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an {@link IllegalArgumentException} if the string is
	 * too short to undelimit (i.e. length < 2).
	 */
	public static void undelimit(StringBuffer sb, String string) {
		int stringLength = string.length();
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		undelimit_(sb, string, stringLength);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimit_(StringBuffer sb, String string, int stringLength) {
		char delimiter = string.charAt(0);  // the first char is the delimiter
		char c = delimiter;
		char next = string.charAt(1);
		int i = 1;
		int last = stringLength - 1;
		do {
			c = next;
			sb.append(c);
			i++;
			next = string.charAt(i);
			if (c == delimiter) {
				if ((next != delimiter) || (i == last)) {
					// an embedded delimiter must be followed by another delimiter
					return;
				}
				i++;
				next = string.charAt(i);
			}
		} while (i != last);
	}

	/**
	 * @see #undelimit(StringBuffer, String)
	 */
	public static void undelimit(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		undelimit_(sb, string, stringLength);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimit_(StringBuffer sb, char[] string, int stringLength) {
		char delimiter = string[0];  // the first char is the delimiter
		char c = delimiter;
		char next = string[1];
		int i = 1;
		int last = stringLength - 1;
		do {
			c = next;
			sb.append(c);
			i++;
			next = string[i];
			if (c == delimiter) {
				if ((next != delimiter) || (i == last)) {
					// an embedded delimiter must be followed by another delimiter
					return;
				}
				i++;
				next = string[i];
			}
		} while (i != last);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * {@link IllegalArgumentException}.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimit(StringBuffer sb, String string, int count) {
		if (count == 0) {
			sb.append(string);
			return;
		}
		int resultLength = string.length() - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		sb.append(string, count, count + resultLength);  // NB: end index is exclusive
	}

	/**
	 * @see #undelimit(StringBuffer, String, int)
	 */
	public static void undelimit(StringBuffer sb, char[] string, int count) {
		if (count == 0) {
			sb.append(string);
			return;
		}
		int resultLength = string.length - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		sb.append(string, count, resultLength);
	}


	// ********** removing characters **********

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 */
	public static void removeFirstOccurrence(StringBuffer sb, String string, char c) {
		int index = string.indexOf(c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndex(sb, string, index);
		}
	}

	private static void removeCharAtIndex(StringBuffer sb, String string, int index) {
		int stringLength = string.length();
		if (index == 0) {
			// character found at the front of string
			sb.append(string, 1, stringLength);  // NB: end index is exclusive
		} else {
			sb.append(string, 0, index);  // NB: end index is exclusive
			if (index != (stringLength - 1)) {
				// character is not at the end of the string
				sb.append(string, index + 1, stringLength);  // NB: end index is exclusive
			}
		}
	}

	/**
	 * @see #removeFirstOccurrence(StringBuffer, String, char)
	 */
	public static void removeFirstOccurrence(StringBuffer sb, char[] string, char c) {
		int index = ArrayTools.indexOf(string, c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndex(sb, string, index);
		}
	}

	private static void removeCharAtIndex(StringBuffer sb, char[] string, int index) {
		int last = string.length - 1;
		if (index == 0) {
			// character found at the front of string
			sb.append(string, 1, last);
		} else if (index == last) {
			// character found at the end of string
			sb.append(string, 0, last);
		} else {
			// character found somewhere in the middle of the string
			sb.append(string, 0, index);
			sb.append(string, index + 1, last - index);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 */
	public static void removeAllOccurrences(StringBuffer sb, String string, char c) {
		int first = string.indexOf(c);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length();
			sb.ensureCapacity(sb.length() + stringLength);
			removeAllOccurrences(sb, string, c, first, stringLength);
		}
	}

	/**
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrences(StringBuffer sb, String string, char c, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		for (int i = first; i < stringLength; i++) {
			char d = string.charAt(i);
			if (d != c) {
				sb.append(d);
			}
		}
	}

	/**
	 * @see #removeAllOccurrences(StringBuffer, String, char)
	 */
	public static void removeAllOccurrences(StringBuffer sb, char[] string, char c) {
		int first = ArrayTools.indexOf(string, c);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length;
			sb.ensureCapacity(sb.length() + stringLength);
			removeAllOccurrences(sb, string, c, first, stringLength);
		}
	}

	/**
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrences(StringBuffer sb, char[] string, char c, int first, int stringLength) {
		sb.append(string, 0, first);
		for (int i = first; i < stringLength; i++) {
			char d = string[i];
			if (d != c) {
				sb.append(d);
			}
		}
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified stream.
	 */
	public static void removeAllSpaces(StringBuffer sb, String string) {
		removeAllOccurrences(sb, string, ' ');
	}

	/**
	 * @see #removeAllSpaces(StringBuffer, String)
	 */
	public static void removeAllSpaces(StringBuffer sb, char[] string) {
		removeAllOccurrences(sb, string, ' ');
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified stream.
	 */
	public static void removeAllWhitespace(StringBuffer sb, String string) {
		int first = StringTools.indexOfWhitespace(string);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length();
			sb.ensureCapacity(sb.length() + stringLength);
			removeAllWhitespace(sb, string, first, stringLength);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void removeAllWhitespace(StringBuffer sb, String string, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		for (int i = first; i < stringLength; i++) {
			char c = string.charAt(i);
			if ( ! Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
	}

	/**
	 * @see #removeAllWhitespace(StringBuffer, String)
	 */
	public static void removeAllWhitespace(StringBuffer sb, char[] string) {
		int first = CharArrayTools.indexOfWhitespace(string);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length;
			sb.ensureCapacity(sb.length() + stringLength);
			removeAllWhitespace(sb, string, first, stringLength);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void removeAllWhitespace(StringBuffer sb, char[] string, int first, int stringLength) {
		sb.append(string, 0, first);
		for (int i = first; i < stringLength; i++) {
			char c = string[i];
			if ( ! Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified stream.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 */
	public static void compressWhitespace(StringBuffer sb, String string) {
		int first = StringTools.indexOfWhitespace(string);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length();
			sb.ensureCapacity(sb.length() + stringLength);
			compressWhitespace(sb, string, first, stringLength);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespace(StringBuffer sb, String string, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		int i = first;
		char c = string.charAt(i);
		main: while (true) {
			// replace first whitespace character with a space...
			sb.append(' ');
			// ...and skip subsequent whitespace characters
			while (true) {
				i++;
				if (i >= stringLength) {
					break main;
				}
				c = string.charAt(i);
				if ( ! Character.isWhitespace(c)) {
					break;
				}
			}
			while (true) {
				sb.append(c);
				i++;
				if (i >= stringLength) {
					break main;
				}
				c = string.charAt(i);
				if (Character.isWhitespace(c)) {
					break;
				}
			}
		}
	}

	/**
	 * @see #compressWhitespace(StringBuffer, String)
	 */
	public static void compressWhitespace(StringBuffer sb, char[] string) {
		int first = CharArrayTools.indexOfWhitespace(string);
		if (first == -1) {
			sb.append(string);
		} else {
			int stringLength = string.length;
			sb.ensureCapacity(sb.length() + stringLength);
			compressWhitespace(sb, string, first, stringLength);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespace(StringBuffer sb, char[] string, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		int i = first;
		char c = string[i];
		main: while (true) {
			// replace first whitespace character with a space...
			sb.append(' ');
			// ...and skip subsequent whitespace characters
			while (true) {
				i++;
				if (i >= stringLength) {
					break main;
				}
				c = string[i];
				if ( ! Character.isWhitespace(c)) {
					break;
				}
			}
			while (true) {
				sb.append(c);
				i++;
				if (i >= stringLength) {
					break main;
				}
				c = string[i];
				if (Character.isWhitespace(c)) {
					break;
				}
			}
		}
	}


	// ********** capitalization **********

	/**
	 * Append the specified string to the specified stream
	 * with its first letter capitalized.
	 */
	public static void capitalize(StringBuffer sb, String string) {
		if (string.length() == 0) {
			return;
		}
		if (Character.isUpperCase(string.charAt(0))) {
			sb.append(string);
		} else {
			capitalize_(sb, string);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalize_(StringBuffer sb, String string) {
		sb.append(Character.toUpperCase(string.charAt(0)));
		sb.append(string, 1, string.length());  // NB: end index is exclusive
	}

	/**
	 * @see #capitalize(StringBuffer, String)
	 */
	public static void capitalize(StringBuffer sb, char[] string) {
		if (string.length == 0) {
			return;
		}
		if (Character.isUpperCase(string[0])) {
			sb.append(string);
		} else {
			capitalize_(sb, string);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalize_(StringBuffer sb, char[] string) {
		sb.append(Character.toUpperCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified stream
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalize(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (StringTools.needNotBeUncapitalized(string, stringLength)) {
			sb.append(string);
		} else {
			uncapitalize(sb, string, stringLength);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void uncapitalize(StringBuffer sb, String string, int stringLength) {
		sb.append(Character.toLowerCase(string.charAt(0)));
		sb.append(string, 1, stringLength);  // NB: end index is exclusive
	}

	/**
	 * @see #uncapitalize(StringBuffer, String)
	 */
	public static void uncapitalize(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (CharArrayTools.needNotBeUncapitalized(string, stringLength)) {
			sb.append(string);
		} else {
			uncapitalize(sb, string, stringLength);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void uncapitalize(StringBuffer sb, char[] string, int stringLength) {
		sb.append(Character.toLowerCase(string[0]));
		sb.append(string, 1, stringLength - 1);
	}


	// ********** convert byte array to hex string **********

	/**
	 * Convert the specified byte array to the corresponding string of
	 * hexadecimal characters.
	 * @see ByteArrayTools#convertToHexString(byte[])
	 */
	public static void convertToHexString(StringBuffer sb, byte[] bytes) {
		int bytesLength = bytes.length;
		if (bytesLength != 0) {
			sb.ensureCapacity(sb.length() + (bytesLength << 1));
			convertToHexString(sb, bytes, bytesLength);
		}
	}

	/**
	 * Pre-condition: the byte array is not empty
	 */
	private static void convertToHexString(StringBuffer sb, byte[] bytes, int bytesLength) {
		char[] digits = CharacterTools.DIGITS;
		for (int i = 0; i < bytesLength; i++) {
			int b = bytes[i] & 0xFF; // clear any sign bits
			sb.append(digits[b >> 4]); // first nibble
			sb.append(digits[b & 0xF]); // second nibble
		}
	}


	// ********** convert camel case to all caps **********

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * <p>
	 * <code>
	 * "largeProject" -> "LARGE_PROJECT"
	 * </code>
	 */
	public static void convertCamelCaseToAllCaps(StringBuffer sb, String camelCaseString) {
		int stringLength = camelCaseString.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / 4));
			convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	private static void convertCamelCaseToAllCaps_(StringBuffer sb, String camelCaseString, int stringLength) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString.charAt(0);
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString.charAt(i));
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(c));
			prev = c;
		}
	}

	/**
	 * @see #convertCamelCaseToAllCaps(StringBuffer, String)
	 */
	public static void convertCamelCaseToAllCaps(StringBuffer sb, char[] camelCaseString) {
		int stringLength = camelCaseString.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / 4));
			convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	private static void convertCamelCaseToAllCaps_(StringBuffer sb, char[] camelCaseString, int stringLength) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString[i]);
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(c));
			prev = c;
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * <p>
	 * <code>
	 * "largeProject" -> "LARGE_PROJECT"
	 * </code>
	 * <p>
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCaps(StringBuffer sb, String camelCaseString, int maxLength) {
		if (maxLength != 0) {
			int stringLength = camelCaseString.length();
			if (stringLength != 0) {
				sb.ensureCapacity(sb.length() + maxLength);
				convertCamelCaseToAllCaps(sb, camelCaseString, maxLength, stringLength);
			}
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertCamelCaseToAllCaps(StringBuffer sb, String camelCaseString, int maxLength, int stringLength) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString.charAt(0);
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString.charAt(i));
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				sb.append('_');
				if (sb.length() == maxLength) {
					return;
				}
			}
			sb.append(Character.toUpperCase(c));
			if (sb.length() == maxLength) {
				return;
			}
			prev = c;
		}
	}

	/**
	 * @see #convertCamelCaseToAllCaps(StringBuffer, String, int)
	 */
	public static void convertCamelCaseToAllCaps(StringBuffer sb, char[] camelCaseString, int maxLength) {
		if (maxLength != 0) {
			int stringLength = camelCaseString.length;
			if (stringLength != 0) {
				sb.ensureCapacity(sb.length() + maxLength);
				convertCamelCaseToAllCaps(sb, camelCaseString, maxLength, stringLength);
			}
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertCamelCaseToAllCaps(StringBuffer sb, char[] camelCaseString, int maxLength, int stringLength) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString[i]);
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				sb.append('_');
				if (sb.length() == maxLength) {
					return;
				}
			}
			sb.append(Character.toUpperCase(c));
			if (sb.length() == maxLength) {
				return;
			}
			prev = c;
		}
	}


	// ********** convert all caps to camel case **********

	/**
	 * Convert the specified "all caps" string to a "camel case" string:
	 * <p>
	 * <code>
	 * "LARGE_PROJECT" -> "LargeProject"
	 * </code>
	 * <p>
	 * Capitalize the first letter.
	 */
	public static void convertAllCapsToCamelCase(StringBuffer sb, String allCapsString) {
		convertAllCapsToCamelCase(sb, allCapsString, true);
	}

	/**
	 * @see #convertAllCapsToCamelCase(StringBuffer, String)
	 */
	public static void convertAllCapsToCamelCase(StringBuffer sb, char[] allCapsString) {
		convertAllCapsToCamelCase(sb, allCapsString, true);
	}

	/**
	 * Convert the specified "all caps" string to a "camel case" string:
	 * <p>
	 * <code>
	 * "LARGE_PROJECT" -> "largeProject"
	 * </code>
	 * <p>
	 * Optionally capitalize the first letter.
	 */
	public static void convertAllCapsToCamelCase(StringBuffer sb, String allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength);
			convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	private static void convertAllCapsToCamelCase(StringBuffer sb, String allCapsString, boolean capitalizeFirstLetter, int stringLength) {
		char prev = 0;
		char c = 0;
		boolean first = true;
		for (int i = 0; i < stringLength; i++) {
			prev = c;
			c = allCapsString.charAt(i);
			if (c == '_') {
				continue;
			}
			if (first) {
				first = false;
				sb.append(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				sb.append((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
			}
		}
	}

	/**
	 * @see #convertAllCapsToCamelCase(StringBuffer, String, boolean)
	 */
	public static void convertAllCapsToCamelCase(StringBuffer sb, char[] allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength);
			convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	private static void convertAllCapsToCamelCase(StringBuffer sb, char[] allCapsString, boolean capitalizeFirstLetter, int stringLength) {
		char prev = 0;
		char c = 0;
		boolean first = true;
		for (int i = 0; i < stringLength; i++) {
			prev = c;
			c = allCapsString[i];
			if (c == '_') {
				continue;
			}
			if (first) {
				first = false;
				sb.append(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				sb.append((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
			}
		}
	}


	// ********** convert to Java string literal **********

	/**
	 * Convert the specified string to a Java string literal, with the
	 * appropriate escaped characters.
	 */
	public static void convertToJavaStringLiteral(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_JAVA_STRING_LITERAL);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteral(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteral(StringBuffer sb, String string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToJavaStringLiteralContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * Convert the specified string to the contents of a Java string literal,
	 * with the appropriate escaped characters.
	 */
	public static void convertToJavaStringLiteralContent(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteralContent(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteralContent(StringBuffer sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToJavaStringLiteral(StringBuffer, String)
	 */
	public static void convertToJavaStringLiteral(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_JAVA_STRING_LITERAL);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteral(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteral(StringBuffer sb, char[] string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToJavaStringLiteralContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * @see #convertToJavaStringLiteralContent(StringBuffer, String)
	 */
	public static void convertToJavaStringLiteralContent(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteralContent(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteralContent(StringBuffer sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(sb, string[i]);
		}
	}

	private static void convertToJavaStringLiteral(StringBuffer sb, char c) {
		switch (c) {
			case '\b':  // backspace
				sb.append("\\b");  //$NON-NLS-1$
				break;
			case '\t':  // horizontal tab
				sb.append("\\t");  //$NON-NLS-1$
				break;
			case '\n':  // line-feed LF
				sb.append("\\n");  //$NON-NLS-1$
				break;
			case '\f':  // form-feed FF
				sb.append("\\f");  //$NON-NLS-1$
				break;
			case '\r':  // carriage-return CR
				sb.append("\\r");  //$NON-NLS-1$
				break;
			case '"':  // double-quote
				sb.append("\\\"");  //$NON-NLS-1$
				break;
//			case '\'':  // single-quote
//				sb.append("\\'");  //$NON-NLS-1$
//				break;
			case '\\':  // backslash
				sb.append("\\\\");  //$NON-NLS-1$
				break;
			default:
				sb.append(c);
				break;
		}
	}


	// ********** convert to XML **********

	/**
	 * Convert the specified string to an XML attribute value, escaping the
	 * appropriate characters. Delimit with quotes (<code>"</code>) unless
	 * there are <em>embedded</em> quotes (<em>and</em> no embedded
	 * apostrophes); in which case, delimit with apostrophes (<code>'</code>).
	 */
	public static void convertToXmlAttributeValue(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_XML_ATTRIBUTE_VALUE);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 12);
			convertToXmlAttributeValue(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlAttributeValue(StringBuffer sb, String string, int stringLength) {
		int index = string.indexOf(CharacterTools.QUOTE);
		if (index == -1) {
			// no embedded quotes - use quote delimiters
			convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
		} else {
			index = string.indexOf(CharacterTools.APOSTROPHE);
			if (index == -1) {
				// embedded quotes but no embedded apostrophes - use apostrophe delimiters
				convertToSingleQuotedXmlAttributeValue(sb, string, stringLength);
			} else {
				// embedded quotes *and* embedded apostrophes - use quote delimiters
				convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
			}
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValue(StringBuffer sb, String string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	private static void convertToSingleQuotedXmlAttributeValue(StringBuffer sb, String string, int stringLength) {
		sb.append(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.APOSTROPHE);
	}

	/**
	 * Convert the specified string to a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(StringBuffer, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(StringBuffer sb, String string) {
		convertToDoubleQuotedXmlAttributeValue(sb, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(StringBuffer, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToDoubleQuotedXmlAttributeValueContent(StringBuffer sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValueContent(sb, string.charAt(i));
		}
	}

	/**
	 * Convert the specified string to a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(StringBuffer, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValue(StringBuffer sb, String string) {
		convertToSingleQuotedXmlAttributeValue(sb, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(StringBuffer, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToSingleQuotedXmlAttributeValueContent(StringBuffer sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlAttributeValue(StringBuffer, String)
	 */
	public static void convertToXmlAttributeValue(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_XML_ATTRIBUTE_VALUE);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 12);
			convertToXmlAttributeValue(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlAttributeValue(StringBuffer sb, char[] string, int stringLength) {
		int index = ArrayTools.indexOf(string, CharacterTools.QUOTE);
		if (index == -1) {
			// no embedded quotes - use quote delimiters
			convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
		} else {
			index = ArrayTools.indexOf(string, CharacterTools.APOSTROPHE);
			if (index == -1) {
				// embedded quotes but no embedded apostrophes - use apostrophe delimiters
				convertToSingleQuotedXmlAttributeValue(sb, string, stringLength);
			} else {
				// embedded quotes *and* embedded apostrophes - use quote delimiters
				convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
			}
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValue(StringBuffer sb, char[] string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	private static void convertToSingleQuotedXmlAttributeValue(StringBuffer sb, char[] string, int stringLength) {
		sb.append(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.APOSTROPHE);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValue(StringBuffer, String)
	 * @see #convertToXmlAttributeValue(StringBuffer, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(StringBuffer sb, char[] string) {
		convertToDoubleQuotedXmlAttributeValue(sb, string, string.length);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValueContent(StringBuffer, String)
	 * @see #convertToXmlAttributeValue(StringBuffer, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValueContent(StringBuffer sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValueContent(sb, string[i]);
		}
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValue(StringBuffer, String)
	 * @see #convertToXmlAttributeValue(StringBuffer, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValue(StringBuffer sb, char[] string) {
		convertToSingleQuotedXmlAttributeValue(sb, string, string.length);
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValueContent(StringBuffer, String)
	 * @see #convertToXmlAttributeValue(StringBuffer, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	private static void convertToSingleQuotedXmlAttributeValueContent(StringBuffer sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(sb, string[i]);
		}
	}

	/**
	 * String is delimited with quotes - escape embedded quotes.
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToDoubleQuotedXmlAttributeValueContent(StringBuffer sb, char c) {
		switch (c) {
			case '"':  // double-quote
				sb.append(StringTools.XML_QUOTE);
				break;					
			case '&':  // ampersand
				sb.append(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				sb.append(StringTools.XML_LT);
				break;					
			default:
				sb.append(c);
				break;
		}
	}

	/**
	 * String is delimited with apostrophes - escape embedded apostrophes.
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToSingleQuotedXmlAttributeValueContent(StringBuffer sb, char c) {
		switch (c) {
			case '\'':  // apostrophe
				sb.append(StringTools.XML_APOS);
				break;					
			case '&':  // ampersand
				sb.append(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				sb.append(StringTools.XML_LT);
				break;					
			default:
				sb.append(c);
				break;
		}
	}

	/**
	 * Convert the specified string to an XML element text, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementCDATA(StringBuffer, String)
	 */
	public static void convertToXmlElementText(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 8);
			convertToXmlElementText(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementText(StringBuffer sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlElementText(StringBuffer, String)
	 */
	public static void convertToXmlElementText(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 8);
			convertToXmlElementText(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementText(StringBuffer sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(sb, string[i]);
		}
	}

	/**
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToXmlElementText(StringBuffer sb, char c) {
		switch (c) {
			case '&':  // ampersand
				sb.append(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				sb.append(StringTools.XML_LT);
				break;					
			default:
				sb.append(c);
				break;
		}
	}

	/**
	 * Convert the specified string to an XML element CDATA, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementText(StringBuffer, String)
	 */
	public static void convertToXmlElementCDATA(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_XML_ELEMENT_CDATA);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + StringTools.EMPTY_XML_ELEMENT_CDATA.length() + 6);
			convertToXmlElementCDATA(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementCDATA(StringBuffer sb, String string, int stringLength) {
		sb.append(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(sb, string, stringLength);
		sb.append(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * Convert the specified string to the contents of an XML element CDATA,
	 * escaping the appropriate characters.
	 * @see #convertToXmlElementCDATA(StringBuffer, String)
	 * @see #convertToXmlElementText(StringBuffer, String)
	 */
	public static void convertToXmlElementCDATAContent(StringBuffer sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToXmlElementCDATAContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 * @see <a href="http://www.w3.org/TR/xml/#sec-cdata-sect">XML Spec</a>
	 */
	private static void convertToXmlElementCDATAContent(StringBuffer sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			sb.append(c);
			// "]]>" -> "]]&gt;"
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string.charAt(i);
			sb.append(c);
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string.charAt(i);
			if (c == '>') {
				sb.append(StringTools.XML_GT);
			} else {
				sb.append(c);
			}
		}
	}

	/**
	 * @see #convertToXmlElementCDATA(StringBuffer, String)
	 */
	public static void convertToXmlElementCDATA(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			sb.append(StringTools.EMPTY_XML_ELEMENT_CDATA);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + StringTools.EMPTY_XML_ELEMENT_CDATA.length() + 6);
			convertToXmlElementCDATA(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementCDATA(StringBuffer sb, char[] string, int stringLength) {
		sb.append(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(sb, string, stringLength);
		sb.append(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * @see #convertToXmlElementCDATAContent(StringBuffer, String)
	 * @see #convertToXmlElementCDATA(StringBuffer, char[])
	 */
	public static void convertToXmlElementCDATAContent(StringBuffer sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToXmlElementCDATAContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 * @see <a href="http://www.w3.org/TR/xml/#sec-cdata-sect">XML Spec</a>
	 */
	private static void convertToXmlElementCDATAContent(StringBuffer sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			sb.append(c);
			// "]]>" -> "]]&gt;"
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string[i];
			sb.append(c);
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string[i];
			if (c == '>') {
				sb.append(StringTools.XML_GT);
			} else {
				sb.append(c);
			}
		}
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private StringBufferTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
