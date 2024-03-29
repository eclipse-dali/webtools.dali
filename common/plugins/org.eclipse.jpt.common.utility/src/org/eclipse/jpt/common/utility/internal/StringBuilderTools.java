/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * {@link StringBuilder} utility methods.
 * <p>
 * As of JDK 1.5, it's tempting to convert all of these methods to use
 * {@link Appendable};
 * but all the {@link Appendable} methods throw {@link java.io.IOException} (yech) and
 * we [might?] get a bit better performance invoking methods on classes than
 * we get on interfaces. :-)
 * 
 * @see StringTools
 */
public final class StringBuilderTools {

	// ********** char[] **********

	/**
	 * Convert the specified string builder to a charactor array
	 * (<code>char[]</code>).
	 */
	public static char[] convertToCharArray(StringBuilder sb) {
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}


	// ********** reverse **********

	/**
	 * Reverse the specified string.
	 */
	public static void reverse(StringBuilder sb, String string) {
		for (int i = string.length(); i-- > 0; ) {
			sb.append(string.charAt(i));
		}
	}

	/**
	 * Reverse the specified string.
	 */
	public static void reverse(StringBuilder sb, char[] string) {
		for (int i = string.length; i-- > 0; ) {
			sb.append(string[i]);
		}
	}


	// ********** padding/truncating/centering/repeating **********

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * each end.
	 */
	public static void center(StringBuilder sb, String string, int length) {
		center(sb, string, length, ' ');
	}

	/**
	 * @see #center(StringBuilder, String, int)
	 */
	public static void center(StringBuilder sb, char[] string, int length) {
		center(sb, string, length, ' ');
	}

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at each end.
	 */
	public static void center(StringBuilder sb, String string, int length, char c) {
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
	 * @see #center(StringBuilder, String, int, char)
	 */
	public static void center(StringBuilder sb, char[] string, int length, char c) {
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
	public static void pad(StringBuilder sb, String string, int length) {
		pad(sb, string, length, ' ');
	}

	/**
	 * @see #pad(StringBuilder, String, int)
	 */
	public static void pad(StringBuilder sb, char[] string, int length) {
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
	public static void pad(StringBuilder sb, String string, int length, char c) {
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
	 * @see #pad(StringBuilder, String, int, char)
	 */
	public static void pad(StringBuilder sb, char[] string, int length, char c) {
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
	public static void fit(StringBuilder sb, String string, int length) {
		fit(sb, string, length, ' ');
	}

	/**
	 * @see #fit(StringBuilder, String, int)
	 */
	public static void fit(StringBuilder sb, char[] string, int length) {
		fit(sb, string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static void fit(StringBuilder sb, String string, int length, char c) {
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
	 * @see #fit(StringBuilder, String, int, char)
	 */
	public static void fit(StringBuilder sb, char[] string, int length, char c) {
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
	private static void pad_(StringBuilder sb, String string, int stringLength, int length, char c) {
		sb.append(string);
		fill(sb, stringLength, length, c);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void pad_(StringBuilder sb, char[] string, int stringLength, int length, char c) {
		sb.append(string);
		fill(sb, stringLength, length, c);
	}

	/**
	 * Add enough characters to the specified stream to compensate for
	 * the difference between the specified string length and specified length.
	 */
	private static void fill(StringBuilder sb, int stringLength, int length, char c) {
		fill(sb, length - stringLength, c);
	}

	/**
	 * Add the specified length of characters to the specified stream.
	 */
	private static void fill(StringBuilder sb, int length, char c) {
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
	public static void zeroPad(StringBuilder sb, String string, int length) {
		frontPad(sb, string, length, '0');
	}

	/**
	 * @see #zeroPad(StringBuilder, String, int)
	 */
	public static void zeroPad(StringBuilder sb, char[] string, int length) {
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
	public static void frontPad(StringBuilder sb, String string, int length, char c) {
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
	 * @see #frontPad(StringBuilder, String, int, char)
	 */
	public static void frontPad(StringBuilder sb, char[] string, int length, char c) {
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
	public static void zeroFit(StringBuilder sb, String string, int length) {
		frontFit(sb, string, length, '0');
	}

	/**
	 * @see #zeroFit(StringBuilder, String, int)
	 */
	public static void zeroFit(StringBuilder sb, char[] string, int length) {
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
	public static void frontFit(StringBuilder sb, String string, int length, char c) {
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
	 * @see #frontFit(StringBuilder, String, int, char)
	 */
	public static void frontFit(StringBuilder sb, char[] string, int length, char c) {
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
	private static void frontPad_(StringBuilder sb, String string, int stringLength, int length, char c) {
		fill(sb, stringLength, length, c);
		sb.append(string);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void frontPad_(StringBuilder sb, char[] string, int stringLength, int length, char c) {
		fill(sb, stringLength, length, c);
		sb.append(string);
	}

	/**
	 * Repeat the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, repeat it to the
	 * specified length, truncating the last iteration if necessary.
	 */
	public static void repeat(StringBuilder sb, String string, int length) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);  // NB: end index is exclusive
		} else {
			repeat(sb, string, length, stringLength);
		}
	}

	/**
	 * assume the string must, indeed, be repeated
	 */
	static void repeat(StringBuilder sb, String string, int length, int stringLength) {
		do {
			sb.append(string);
			length = length - stringLength;
		} while (stringLength <= length);
		if (length > 0) {
			sb.append(string, 0, length);  // NB: end index is exclusive
		}
	}

	/**
	 * @see #repeat(StringBuilder, String, int)
	 */
	public static void repeat(StringBuilder sb, char[] string, int length) {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);  // NB: end index is exclusive
		} else {
			repeat(sb, string, length, stringLength);
		}
	}

	/**
	 * assume the string must, indeed, be repeated
	 */
	static void repeat(StringBuilder sb, char[] string, int length, int stringLength) {
		do {
			sb.append(string);
			length = length - stringLength;
		} while (stringLength <= length);
		if (length > 0) {
			sb.append(string, 0, length);  // NB: end index is exclusive
		}
	}


	// ********** separating **********

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<p>
	 * <code>
	 * separate("012345", '-', 2) => "01-23-45"
	 * </code>
	 */
	public static void separate(StringBuilder sb, String string, char separator, int segmentSize) {
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
	private static void separate(StringBuilder sb, String string, char separator, int segmentSize, int stringLength) {
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
	 * @see #separate(StringBuilder, String, char, int)
	 */
	public static void separate(StringBuilder sb, char[] string, char separator, int segmentSize) {
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
	private static void separate(StringBuilder sb, char[] string, char separator, int segmentSize, int stringLength) {
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
	public static void quote(StringBuilder sb, String string) {
		delimit(sb, string, CharacterTools.QUOTE);
	}

	/**
	 * @see #quote(StringBuilder, String)
	 */
	public static void quote(StringBuilder sb, char[] string) {
		delimit(sb, string, CharacterTools.QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimit(StringBuilder sb, String string, char delimiter) {
		int stringLength = string.length();
		sb.ensureCapacity(sb.length() + stringLength + 2);
		delimit(sb, string, delimiter, stringLength);
	}

	static void delimit(StringBuilder sb, String string, char delimiter, int stringLength) {
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
	 * @see #delimit(StringBuilder, String, char)
	 */
	public static void delimit(StringBuilder sb, char[] string, char delimiter) {
		int stringLength = string.length;
		sb.ensureCapacity(sb.length() + stringLength + 2);
		delimit(sb, string, delimiter, stringLength);
	}

	static void delimit(StringBuilder sb, char[] string, char delimiter, int stringLength) {
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
	public static void delimit(StringBuilder sb, String string, String delimiter) {
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
	private static void delimit(StringBuilder sb, String string, String delimiter, int delimiterLength) {
		sb.append(delimiter, 0, delimiterLength);
		sb.append(string);
		sb.append(delimiter, 0, delimiterLength);
	}

	/**
	 * @see #delimit(StringBuilder, String, String)
	 */
	public static void delimit(StringBuilder sb, char[] string, char[] delimiter) {
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
	private static void delimit(StringBuilder sb, char[] string, char[] delimiter, int delimiterLength) {
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
	public static void undelimit(StringBuilder sb, String string) {
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
	static void undelimit_(StringBuilder sb, String string, int stringLength) {
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
	 * @see #undelimit(StringBuilder, String)
	 */
	public static void undelimit(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + String.valueOf(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		undelimit_(sb, string, stringLength);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	static void undelimit_(StringBuilder sb, char[] string, int stringLength) {
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
	public static void undelimit(StringBuilder sb, String string, int count) {
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
	 * @see #undelimit(StringBuilder, String, int)
	 */
	public static void undelimit(StringBuilder sb, char[] string, int count) {
		if (count == 0) {
			sb.append(string);
			return;
		}
		int resultLength = string.length - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + String.valueOf(string) + '"'); //$NON-NLS-1$
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
	public static void removeFirstOccurrence(StringBuilder sb, String string, char c) {
		int index = string.indexOf(c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndex(sb, string, index);
		}
	}

	private static void removeCharAtIndex(StringBuilder sb, String string, int index) {
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
	 * @see #removeFirstOccurrence(StringBuilder, String, char)
	 */
	public static void removeFirstOccurrence(StringBuilder sb, char[] string, char c) {
		int index = ArrayTools.indexOf(string, c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndex(sb, string, index);
		}
	}

	private static void removeCharAtIndex(StringBuilder sb, char[] string, int index) {
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
	public static void removeAllOccurrences(StringBuilder sb, String string, char c) {
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
	static void removeAllOccurrences(StringBuilder sb, String string, char c, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		for (int i = first; i < stringLength; i++) {
			char d = string.charAt(i);
			if (d != c) {
				sb.append(d);
			}
		}
	}

	/**
	 * @see #removeAllOccurrences(StringBuilder, String, char)
	 */
	public static void removeAllOccurrences(StringBuilder sb, char[] string, char c) {
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
	static void removeAllOccurrences(StringBuilder sb, char[] string, char c, int first, int stringLength) {
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
	public static void removeAllSpaces(StringBuilder sb, String string) {
		removeAllOccurrences(sb, string, ' ');
	}

	/**
	 * @see #removeAllSpaces(StringBuilder, String)
	 */
	public static void removeAllSpaces(StringBuilder sb, char[] string) {
		removeAllOccurrences(sb, string, ' ');
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified stream.
	 */
	public static void removeAllWhitespace(StringBuilder sb, String string) {
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
	static void removeAllWhitespace(StringBuilder sb, String string, int first, int stringLength) {
		sb.append(string, 0, first);  // NB: end index is exclusive
		for (int i = first; i < stringLength; i++) {
			char c = string.charAt(i);
			if ( ! Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
	}

	/**
	 * @see #removeAllWhitespace(StringBuilder, String)
	 */
	public static void removeAllWhitespace(StringBuilder sb, char[] string) {
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
	static void removeAllWhitespace(StringBuilder sb, char[] string, int first, int stringLength) {
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
	public static void compressWhitespace(StringBuilder sb, String string) {
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
	static void compressWhitespace(StringBuilder sb, String string, int first, int stringLength) {
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
	 * @see #compressWhitespace(StringBuilder, String)
	 */
	public static void compressWhitespace(StringBuilder sb, char[] string) {
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
	static void compressWhitespace(StringBuilder sb, char[] string, int first, int stringLength) {
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
	public static void capitalize(StringBuilder sb, String string) {
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
	private static void capitalize_(StringBuilder sb, String string) {
		sb.append(Character.toUpperCase(string.charAt(0)));
		sb.append(string, 1, string.length());  // NB: end index is exclusive
	}

	/**
	 * @see #capitalize(StringBuilder, String)
	 */
	public static void capitalize(StringBuilder sb, char[] string) {
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
	private static void capitalize_(StringBuilder sb, char[] string) {
		sb.append(Character.toUpperCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified stream
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalize(StringBuilder sb, String string) {
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
	private static void uncapitalize(StringBuilder sb, String string, int stringLength) {
		sb.append(Character.toLowerCase(string.charAt(0)));
		sb.append(string, 1, stringLength);  // NB: end index is exclusive
	}

	/**
	 * @see #uncapitalize(StringBuilder, String)
	 */
	public static void uncapitalize(StringBuilder sb, char[] string) {
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
	private static void uncapitalize(StringBuilder sb, char[] string, int stringLength) {
		sb.append(Character.toLowerCase(string[0]));
		sb.append(string, 1, stringLength - 1);
	}


	// ********** convert byte array to hex string **********

	/**
	 * Convert the specified byte array to the corresponding string of
	 * hexadecimal characters.
	 * @see ByteArrayTools#convertToHexString(byte[])
	 */
	public static void convertToHexString(StringBuilder sb, byte[] bytes) {
		int bytesLength = bytes.length;
		if (bytesLength != 0) {
			sb.ensureCapacity(sb.length() + (bytesLength << 1));
			convertToHexString(sb, bytes, bytesLength);
		}
	}

	/**
	 * Pre-condition: the byte array is not empty
	 */
	static void convertToHexString(StringBuilder sb, byte[] bytes, int bytesLength) {
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
	public static void convertCamelCaseToAllCaps(StringBuilder sb, String camelCaseString) {
		int stringLength = camelCaseString.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / 4));
			convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	static void convertCamelCaseToAllCaps_(StringBuilder sb, String camelCaseString, int stringLength) {
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
	 * @see #convertCamelCaseToAllCaps(StringBuilder, String)
	 */
	public static void convertCamelCaseToAllCaps(StringBuilder sb, char[] camelCaseString) {
		int stringLength = camelCaseString.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + (stringLength / 4));
			convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	static void convertCamelCaseToAllCaps_(StringBuilder sb, char[] camelCaseString, int stringLength) {
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
	public static void convertCamelCaseToAllCaps(StringBuilder sb, String camelCaseString, int maxLength) {
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
	static void convertCamelCaseToAllCaps(StringBuilder sb, String camelCaseString, int maxLength, int stringLength) {
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
	 * @see #convertCamelCaseToAllCaps(StringBuilder, String, int)
	 */
	public static void convertCamelCaseToAllCaps(StringBuilder sb, char[] camelCaseString, int maxLength) {
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
	static void convertCamelCaseToAllCaps(StringBuilder sb, char[] camelCaseString, int maxLength, int stringLength) {
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
	public static void convertAllCapsToCamelCase(StringBuilder sb, String allCapsString) {
		convertAllCapsToCamelCase(sb, allCapsString, true);
	}

	/**
	 * @see #convertAllCapsToCamelCase(StringBuilder, String)
	 */
	public static void convertAllCapsToCamelCase(StringBuilder sb, char[] allCapsString) {
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
	public static void convertAllCapsToCamelCase(StringBuilder sb, String allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength);
			convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	static void convertAllCapsToCamelCase(StringBuilder sb, String allCapsString, boolean capitalizeFirstLetter, int stringLength) {
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
	 * @see #convertAllCapsToCamelCase(StringBuilder, String, boolean)
	 */
	public static void convertAllCapsToCamelCase(StringBuilder sb, char[] allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength);
			convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	static void convertAllCapsToCamelCase(StringBuilder sb, char[] allCapsString, boolean capitalizeFirstLetter, int stringLength) {
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
	public static void convertToJavaStringLiteral(StringBuilder sb, String string) {
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
	static void convertToJavaStringLiteral(StringBuilder sb, String string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToJavaStringLiteralContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * Convert the specified string to the contents of a Java string literal,
	 * with the appropriate escaped characters.
	 */
	public static void convertToJavaStringLiteralContent(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteralContent(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	static void convertToJavaStringLiteralContent(StringBuilder sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToJavaStringLiteral(StringBuilder, String)
	 */
	public static void convertToJavaStringLiteral(StringBuilder sb, char[] string) {
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
	static void convertToJavaStringLiteral(StringBuilder sb, char[] string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToJavaStringLiteralContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * @see #convertToJavaStringLiteralContent(StringBuilder, String)
	 */
	public static void convertToJavaStringLiteralContent(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			convertToJavaStringLiteralContent(sb, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	static void convertToJavaStringLiteralContent(StringBuilder sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(sb, string[i]);
		}
	}

	private static void convertToJavaStringLiteral(StringBuilder sb, char c) {
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
	public static void convertToXmlAttributeValue(StringBuilder sb, String string) {
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
	static void convertToXmlAttributeValue(StringBuilder sb, String string, int stringLength) {
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

	static void convertToDoubleQuotedXmlAttributeValue(StringBuilder sb, String string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	static void convertToSingleQuotedXmlAttributeValue(StringBuilder sb, String string, int stringLength) {
		sb.append(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.APOSTROPHE);
	}

	/**
	 * Convert the specified string to a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(StringBuilder, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(StringBuilder sb, String string) {
		convertToDoubleQuotedXmlAttributeValue(sb, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(StringBuilder, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	static void convertToDoubleQuotedXmlAttributeValueContent(StringBuilder sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValueContent(sb, string.charAt(i));
		}
	}

	/**
	 * Convert the specified string to a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(StringBuilder, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValue(StringBuilder sb, String string) {
		convertToSingleQuotedXmlAttributeValue(sb, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(StringBuilder, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	static void convertToSingleQuotedXmlAttributeValueContent(StringBuilder sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlAttributeValue(StringBuilder, String)
	 */
	public static void convertToXmlAttributeValue(StringBuilder sb, char[] string) {
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
	static void convertToXmlAttributeValue(StringBuilder sb, char[] string, int stringLength) {
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

	static void convertToDoubleQuotedXmlAttributeValue(StringBuilder sb, char[] string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	static void convertToSingleQuotedXmlAttributeValue(StringBuilder sb, char[] string, int stringLength) {
		sb.append(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		sb.append(CharacterTools.APOSTROPHE);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValue(StringBuilder, String)
	 * @see #convertToXmlAttributeValue(StringBuilder, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(StringBuilder sb, char[] string) {
		convertToDoubleQuotedXmlAttributeValue(sb, string, string.length);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValueContent(StringBuilder, String)
	 * @see #convertToXmlAttributeValue(StringBuilder, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	static void convertToDoubleQuotedXmlAttributeValueContent(StringBuilder sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValueContent(sb, string[i]);
		}
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValue(StringBuilder, String)
	 * @see #convertToXmlAttributeValue(StringBuilder, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValue(StringBuilder sb, char[] string) {
		convertToSingleQuotedXmlAttributeValue(sb, string, string.length);
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValueContent(StringBuilder, String)
	 * @see #convertToXmlAttributeValue(StringBuilder, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 10);
			convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		}
	}

	static void convertToSingleQuotedXmlAttributeValueContent(StringBuilder sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(sb, string[i]);
		}
	}

	/**
	 * String is delimited with quotes - escape embedded quotes.
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToDoubleQuotedXmlAttributeValueContent(StringBuilder sb, char c) {
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
	private static void convertToSingleQuotedXmlAttributeValueContent(StringBuilder sb, char c) {
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
	 * @see #convertToXmlElementCDATA(StringBuilder, String)
	 */
	public static void convertToXmlElementText(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 8);
			convertToXmlElementText(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	static void convertToXmlElementText(StringBuilder sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(sb, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlElementText(StringBuilder, String)
	 */
	public static void convertToXmlElementText(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 8);
			convertToXmlElementText(sb, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	static void convertToXmlElementText(StringBuilder sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(sb, string[i]);
		}
	}

	/**
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToXmlElementText(StringBuilder sb, char c) {
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
	 * @see #convertToXmlElementText(StringBuilder, String)
	 */
	public static void convertToXmlElementCDATA(StringBuilder sb, String string) {
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
	static void convertToXmlElementCDATA(StringBuilder sb, String string, int stringLength) {
		sb.append(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(sb, string, stringLength);
		sb.append(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * Convert the specified string to the contents of an XML element CDATA,
	 * escaping the appropriate characters.
	 * @see #convertToXmlElementCDATA(StringBuilder, String)
	 * @see #convertToXmlElementText(StringBuilder, String)
	 */
	public static void convertToXmlElementCDATAContent(StringBuilder sb, String string) {
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
	static void convertToXmlElementCDATAContent(StringBuilder sb, String string, int stringLength) {
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
	 * @see #convertToXmlElementCDATA(StringBuilder, String)
	 */
	public static void convertToXmlElementCDATA(StringBuilder sb, char[] string) {
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
	static void convertToXmlElementCDATA(StringBuilder sb, char[] string, int stringLength) {
		sb.append(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(sb, string, stringLength);
		sb.append(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * @see #convertToXmlElementCDATAContent(StringBuilder, String)
	 * @see #convertToXmlElementCDATA(StringBuilder, char[])
	 */
	public static void convertToXmlElementCDATAContent(StringBuilder sb, char[] string) {
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
	static void convertToXmlElementCDATAContent(StringBuilder sb, char[] string, int stringLength) {
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


	// ********** JSON **********

	/**
	 * Append the JSON representation of the specified object.
	 * <ul>
	 * <li>Nulls are appended as JSON nulls.
	 * <li>Strings are delimited with double quotes and escaped appropriately.
	 * <li>Character arrays treated like strings.
	 * <li>Boolean primitives and wrappers are appended as JSON booleans.
	 * <li>Maps with string keys are appended as JSON objects.
	 * <li>Iterables, object arrrays, and primitive arrays are appended as JSON arrays.
	 * <li>Number primitives and wrappers are appended as JSON numbers.
	 * <li>All other types of objects are appended as JSON objects,
	 *     with the attribute values derived using Java reflection.
	 * </ul>
	 * Circular object references are not supported and will result in a
	 * {@link StackOverflowError}.
	 */
	public static void appendJSON(StringBuilder sb, Object object) {
		if (object == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, object);
		}
	}

	/**
	 * Assume non-<code>null</code> object.
	 */
	private static void appendJSON_(StringBuilder sb, Object object) {
		if (object instanceof String) {
			appendJSON_(sb, (String) object);
		}
		else if (object instanceof char[]) {
			appendJSON_(sb, (char[]) object);
		}
		else if (object instanceof Boolean) {
			appendJSON_(sb, (Boolean) object);
		}
		else if (object instanceof Map) {
			appendJSON_(sb, (Map<?, ?>) object);
		}
		else if (object instanceof Iterable) {
			appendJSON_(sb, (Iterable<?>) object);
		}
		else if (object instanceof Object[]) {
			appendJSON_(sb, (Object[]) object);
		}
		else if (object instanceof Number) {
			appendJSON_(sb, (Number) object);
		}
		else if (object instanceof int[]) {
			appendJSON_(sb, (int[]) object);
		}
		else if (object instanceof double[]) {
			appendJSON_(sb, (double[]) object);
		}
		else if (object instanceof byte[]) {
			appendJSON_(sb, (byte[]) object);
		}
		else if (object instanceof short[]) {
			appendJSON_(sb, (short[]) object);
		}
		else if (object instanceof long[]) {
			appendJSON_(sb, (long[]) object);
		}
		else if (object instanceof float[]) {
			appendJSON_(sb, (float[]) object);
		}
		else if (object instanceof boolean[]) {
			appendJSON_(sb, (boolean[]) object);
		}
		else {
			appendJSON__(sb, object);
		}
	}

	/**
	 * Append the JSON representation of the specified map as a JSON object
	 * if all the map's keys are strings; otherwise append a reflectively-derived
	 * representation of the map.
	 * Circular references are not supported and will result in a
	 * {@link StackOverflowError}.
	 */
	public static void appendJSON(StringBuilder sb, Map<?, ?> map) {
		if (map == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, map);
		}
	}

	/**
	 * Assume non-<code>null</code> map.
	 * Sort output by key.
	 */
	private static void appendJSON_(StringBuilder sb, Map<?, ?> map) {
		Set<?> keys = map.keySet();
		for (Object key : keys) {
			if ( ! (key instanceof String)) {
				appendJSON__(sb, map); // object reflection
				return;
			}
		}
		sb.append('{');
		if ( ! keys.isEmpty()) {
			@SuppressWarnings("unchecked")
			TreeSet<String> sortedKeys = (TreeSet<String>) new TreeSet<>(map.keySet());
			for (String key : sortedKeys) {
				appendJSON(sb, key);
				sb.append(':');
				appendJSON(sb, map.get(key));
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append('}');
	}

	/**
	 * Assume non-<code>null</code> non-standard object.
	 */
	private static void appendJSON__(StringBuilder sb, Object object) {
		sb.append('{');
		Iterator<Field> stream = IterableTools.sort(ClassTools.allInstanceFields(object.getClass()), FIELD_COMPARATOR).iterator();
		if (stream.hasNext()) {
			do {
				Field field = stream.next();
				appendJSON(sb, field.getName());
				sb.append(':');
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(object);
				} catch (IllegalAccessException ex) {
					throw new RuntimeException(ex);
				}
				appendJSON(sb, value);
				sb.append(',');
			} while (stream.hasNext());
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append('}');
	}

	/**
	 * Compare fields' names.
	 */
	private static final ComparatorAdapter<Field> FIELD_COMPARATOR = new FieldComparator();
	static class FieldComparator
		extends ComparatorAdapter<Field>
	{
		@Override
		public int compare(Field field1, Field field2) {
			return field1.getName().compareTo(field2.getName());
		}
	}

	/**
	 * Append a JSON null.
	 */
	public static void appendJSONNull(StringBuilder sb) {
		sb.append(String.valueOf((Object) null)); // "null"
	}

	/**
	 * Append the JSON representation of the specified string,
	 * delimitint it with double quotes and escaping the appropriate characters.
	 */
	public static void appendJSON(StringBuilder sb, String string) {
		if (string == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, string);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSON_(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			sb.append(EMPTY_JSON_STRING_LITERAL);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			appendJSON_(sb, string, stringLength);
		}
	}

	/**
	 * Assume non-<code>null</code> non-empty string.
	 */
	private static void appendJSON_(StringBuilder sb, String string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		appendJSONContent_(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * Append the JSON string literal content for the specified string,
	 * escaping the appropriate characters.
	 */
	public static void appendJSONContent(StringBuilder sb, String string) {
		if (string == null) {
			appendJSONNull(sb);
		} else {
			appendJSONContent_(sb, string);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSONContent_(StringBuilder sb, String string) {
		int stringLength = string.length();
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			appendJSONContent_(sb, string, stringLength);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSONContent_(StringBuilder sb, String string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			appendJSON(sb, string.charAt(i));
		}
	}

	/**
	 * Append the JSON representation of the specified string,
	 * delimitint it with double quotes and escaping the appropriate characters.
	 */
	public static void appendJSON(StringBuilder sb, char[] string) {
		if (string == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, string);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSON_(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			sb.append(EMPTY_JSON_STRING_LITERAL);
		} else {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			appendJSON_(sb, string, stringLength);
		}
	}

	/**
	 * Assume non-<code>null</code> non-empty string.
	 */
	private static void appendJSON_(StringBuilder sb, char[] string, int stringLength) {
		sb.append(CharacterTools.QUOTE);
		appendJSONContent_(sb, string, stringLength);
		sb.append(CharacterTools.QUOTE);
	}

	/**
	 * Append the JSON string literal content for the specified string,
	 * escaping the appropriate characters.
	 */
	public static void appendJSONContent(StringBuilder sb, char[] string) {
		if (string == null) {
			appendJSONNull(sb);
		} else {
			appendJSONContent_(sb, string);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSONContent_(StringBuilder sb, char[] string) {
		int stringLength = string.length;
		if (stringLength != 0) {
			sb.ensureCapacity(sb.length() + stringLength + 6);
			appendJSONContent_(sb, string, stringLength);
		}
	}

	/**
	 * Assume non-<code>null</code> string.
	 */
	private static void appendJSONContent_(StringBuilder sb, char[] string, int stringLength) {
		for (int i = 0; i < stringLength; i++) {
			appendJSON(sb, string[i]);
		}
	}

	/**
	 * Escape the character if necessary.
	 */
	private static void appendJSON(StringBuilder sb, char c) {
		switch (c) {
			case '"':  // double-quote
				sb.append("\\\"");  //$NON-NLS-1$
				break;
			case '\\':  // backslash
				sb.append("\\\\");  //$NON-NLS-1$
				break;
//			case '/':  // slash
//				sb.append("\\/");  //$NON-NLS-1$
//				break;
			case '\b':  // backspace
				sb.append("\\b");  //$NON-NLS-1$
				break;
			case '\f':  // form-feed FF
				sb.append("\\f");  //$NON-NLS-1$
				break;
			case '\n':  // line-feed LF
				sb.append("\\n");  //$NON-NLS-1$
				break;
			case '\r':  // carriage-return CR
				sb.append("\\r");  //$NON-NLS-1$
				break;
			case '\t':  // horizontal tab
				sb.append("\\t");  //$NON-NLS-1$
				break;
			default:
				if (c < 32) {
					if (c < 16) {
						sb.append("\\u000"); //$NON-NLS-1$
						sb.append(Integer.toHexString(c));
					} else {
						sb.append("\\u00"); //$NON-NLS-1$
						sb.append(Integer.toHexString(c));
					}
				} else {
					sb.append(c);
				}
				break;
		}
	}

	/**
	 * Append a JSON array representing the specified iterable.
	 */
	public static void appendJSON(StringBuilder sb, Iterable<?> objects) {
		if (objects == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, objects);
		}
	}

	/**
	 * Assume non-<code>null</code> iterable.
	 */
	private static void appendJSON_(StringBuilder sb, Iterable<?> objects) {
		sb.append('[');
		Iterator<?> stream = objects.iterator();
		if (stream.hasNext()) {
			do {
				appendJSON(sb, stream.next());
				sb.append(',');
			} while (stream.hasNext());
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Object[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Object[] array) {
		if (array instanceof Boolean[]) {
			appendJSON_(sb, (Boolean[]) array);
		}
		else if (array instanceof Number[]) {
			appendJSON_(sb, (Number[]) array);
		}
		else {
			appendJSON__(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> non-standard array.
	 */
	private static void appendJSON__(StringBuilder sb, Object[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Object each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Boolean[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Boolean[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Boolean each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	public static void appendJSON(StringBuilder sb, Boolean b) {
		if (b == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, b);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Boolean b) {
		appendJSON(sb, b.booleanValue());
	}

	public static void appendJSON(StringBuilder sb, boolean[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, boolean[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (boolean each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	public static void appendJSON(StringBuilder sb, boolean b) {
		sb.append(b);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Number[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Number[] array) {
		if (array instanceof Integer[]) {
			appendJSON_(sb, (Integer[]) array);
		}
		else if (array instanceof Double[]) {
			appendJSON_(sb, (Double[]) array);
		}
		else if (array instanceof Byte[]) {
			appendJSON_(sb, (Byte[]) array);
		}
		else if (array instanceof Float[]) {
			appendJSON_(sb, (Float[]) array);
		}
		else if (array instanceof Long[]) {
			appendJSON_(sb, (Long[]) array);
		}
		else if (array instanceof Short[]) {
			appendJSON_(sb, (Short[]) array);
		}
		else if (array instanceof java.math.BigDecimal[]) {
			appendJSON_(sb, (java.math.BigDecimal[]) array);
		}
		else if (array instanceof java.math.BigInteger[]) {
			appendJSON_(sb, (java.math.BigInteger[]) array);
		}
		else {
			appendJSON__(sb, array);
		}
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Number n) {
		if (n == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, n);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Number n) {
		if (n instanceof Integer) {
			appendJSON_(sb, (Integer) n);
		}
		else if (n instanceof Double) {
			appendJSON_(sb, (Double) n);
		}
		else if (n instanceof Byte) {
			appendJSON_(sb, (Byte) n);
		}
		else if (n instanceof Float) {
			appendJSON_(sb, (Float) n);
		}
		else if (n instanceof Long) {
			appendJSON_(sb, (Long) n);
		}
		else if (n instanceof Short) {
			appendJSON_(sb, (Short) n);
		}
		else if (n instanceof java.math.BigDecimal) {
			appendJSON_(sb, (java.math.BigDecimal) n);
		}
		else if (n instanceof java.math.BigInteger) {
			appendJSON_(sb, (java.math.BigInteger) n);
		}
		else {
			appendJSON__(sb, n);
		}
	}

	/**
	 * Assume non-<code>null</code> non-standard number.
	 */
	private static void appendJSON__(StringBuilder sb, Number n) {
		String s = n.toString();
		try {
			@SuppressWarnings("unused")
			java.math.BigDecimal bd = new java.math.BigDecimal(s);
			sb.append(s); // toString() produces valid "number"
		} catch (NumberFormatException ex) {
			appendJSON__(sb, (Object) n); // object reflection
		}
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Integer[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Integer[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Integer each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Integer i) {
		if (i == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, i);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Integer i) {
		appendJSON(sb, i.intValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, int[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, int[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (int each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, int i) {
		sb.append(i);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Double[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Double[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Double each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Double d) {
		if (d == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, d);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Double d) {
		appendJSON(sb, d.doubleValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, double[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, double[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (double each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, double d) {
		sb.append(d);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Byte[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Byte[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Byte each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Byte b) {
		if (b == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, b);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Byte b) {
		appendJSON(sb, b.byteValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, byte[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, byte[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (byte each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, byte b) {
		sb.append(b);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Float[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Float[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Float each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Float f) {
		if (f == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, f);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Float f) {
		appendJSON(sb, f.floatValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, float[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, float[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (float each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, float f) {
		sb.append(f);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Long[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Long[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Long each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Long l) {
		if (l == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, l);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Long l) {
		appendJSON(sb, l.longValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, long[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, long[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (long each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, long l) {
		sb.append(l);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, Short[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, Short[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Short each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, Short s) {
		if (s == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, s);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, Short s) {
		appendJSON(sb, s.shortValue());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, short[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, short[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (short each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, short s) {
		sb.append(s);
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, java.math.BigDecimal[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, java.math.BigDecimal[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (java.math.BigDecimal each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, java.math.BigDecimal bd) {
		if (bd == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, bd);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, java.math.BigDecimal bd) {
		sb.append(bd.toString());
	}

	/**
	 * Append a JSON array representing the specified array.
	 */
	public static void appendJSON(StringBuilder sb, java.math.BigInteger[] array) {
		if (array == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, array);
		}
	}

	/**
	 * Assume non-<code>null</code> array.
	 */
	private static void appendJSON_(StringBuilder sb, java.math.BigInteger[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (java.math.BigInteger each : array) {
				appendJSON(sb, each);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append a JSON number literal representing the specified number.
	 */
	public static void appendJSON(StringBuilder sb, java.math.BigInteger bi) {
		if (bi == null) {
			appendJSONNull(sb);
		} else {
			appendJSON_(sb, bi);
		}
	}

	/**
	 * Assume non-<code>null</code> number.
	 */
	private static void appendJSON_(StringBuilder sb, java.math.BigInteger bi) {
		sb.append(bi.toString());
	}

	/**
	 * Assume non-<code>null</code> non-standard <code>Number</code> array.
	 */
	private static void appendJSON__(StringBuilder sb, Number[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Number number : array) {
				appendJSON(sb, number);
				sb.append(',');
			}
			sb.setLength(sb.length() - 1);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Value: {@value}
	 */
	public static final String EMPTY_JSON_STRING_LITERAL = "\"\"";  //$NON-NLS-1$


	// ********** toString() helper methods **********

	/**
	 * Append the string representations of the objects in the specified array
	 * to the specified string builder:<p>
	 * <code>
	 * ["foo", "bar", "baz"]
	 * </code>
	 */
	public static void append(StringBuilder sb, Object[] array) {
		if (array == null) {
			sb.append(array);
		} else {
			append_(sb, array);
		}
	}

	private static void append_(StringBuilder sb, Object[] array) {
		sb.append('[');
		if (array.length > 0) {
			for (Object element : array) {
				sb.append(element);
				sb.append(", "); //$NON-NLS-1$
			}
			sb.setLength(sb.length() - 2);  // strip off extra comma
		}
		sb.append(']');
	}

	/**
	 * Append the string representations of the objects in the specified iterable
	 * to the specified string builder:<p>
	 * <code>
	 * ["foo", "bar", "baz"]
	 * </code>
	 */
	public static <T> void append(StringBuilder sb, Iterable<T> iterable) {
		append(sb, iterable.iterator());
	}

	/**
	 * Append the string representations of the objects in the specified iterator
	 * to the specified string builder:<p>
	 * <code>
	 * ["foo", "bar", "baz"]
	 * </code>
	 */
	public static <T> void append(StringBuilder sb, Iterator<T> iterator) {
		sb.append('[');
		while (iterator.hasNext()) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}
		sb.append(']');
	}

	/**
	 * Append a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object to the specified string builder:<pre>
	 *     ClassName[00-F3-EE-42]
	 * </pre>
	 * @see Object#toString()
	 */
	public static void appendHashCodeToString(StringBuilder sb, Object object) {
		appendToStringName(sb, object.getClass());
		sb.append('[');
		separate(sb, buildHashCode(object), '-', 2);
		sb.append(']');
	}

	/**
	 * Use {@link System#identityHashCode(Object)},
	 * since {@link Object#hashCode()} may be overridden.
	 */
	private static String buildHashCode(Object object) {
		return StringTools.zeroPad(Integer.toHexString(System.identityHashCode(object)).toUpperCase(), 8);
	}

	/**
	 * Append a class name suitable for a "Dali standard"
	 * {@link Object#toString() toString()} implementation to the specified
	 * string builder.
	 * 
	 * @see ClassTools#toStringName(Class)
	 * @see Object#toString()
	 */
	public static void appendToStringName(StringBuilder sb, Class<?> javaClass) {
		ClassTools.appendToStringNameTo(javaClass, sb);
	}

	/**
	 * Append a "Java standard" {@link Object#toString() toString()} result for
	 * the specified object to the specified string builder:<pre>
	 *     package.ClassName@F3EE42
	 * </pre>
	 * @see Object#toString()
	 */
	public static void appendIdentityToString(StringBuilder sb, Object object) {
		sb.append(object.getClass().getName());
		sb.append('@');
		sb.append(Integer.toHexString(object.hashCode()));
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private StringBuilderTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
