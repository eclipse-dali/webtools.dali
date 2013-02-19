/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.io;

import java.io.IOException;
import java.io.Writer;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CharArrayTools;
import org.eclipse.jpt.common.utility.internal.CharacterTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * {@link Writer} utility methods.
 * 
 * @see StringTools
 */
public final class WriterTools {

	// ********** padding/truncating/centering **********

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * each end.
	 */
	public static void center(Writer writer, String string, int length) throws IOException {
		center(writer, string, length, ' ');
	}

	/**
	 * @see #center(Writer, String, int)
	 */
	public static void center(Writer writer, char[] string, int length) throws IOException {
		center(writer, string, length, ' ');
	}

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at each end.
	 */
	public static void center(Writer writer, String string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			int begin = (stringLength - length) >> 1; // take fewer characters off the front
			writer.write(string, begin, length);
		} else {
			int begin = (length - stringLength) >> 1; // add fewer characters to the front
			fill(writer, begin, c);
			writer.append(string);
			fill(writer, length - (begin + stringLength), c);
		}
	}

	/**
	 * @see #center(Writer, String, int, char)
	 */
	public static void center(Writer writer, char[] string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			int begin = (stringLength - length) >> 1; // take fewer characters off the front
			writer.write(string, begin, length);
		} else {
			int begin = (length - stringLength) >> 1; // add fewer characters to the front
			fill(writer, begin, c);
			writer.write(string);
			fill(writer, length - (begin + stringLength), c);
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
	public static void pad(Writer writer, String string, int length) throws IOException {
		pad(writer, string, length, ' ');
	}

	/**
	 * @see #pad(Writer, String, int)
	 */
	public static void pad(Writer writer, char[] string, int length) throws IOException {
		pad(writer, string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static void pad(Writer writer, String string, int length, char c) throws IOException {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writer.write(string);
		} else {
			pad_(writer, string, length, c);
		}
	}

	/**
	 * @see #pad(Writer, String, int, char)
	 */
	public static void pad(Writer writer, char[] string, int length, char c) throws IOException {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writer.write(string);
		} else {
			pad_(writer, string, stringLength, length, c);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * the end.
	 */
	public static void fit(Writer writer, String string, int length) throws IOException {
		fit(writer, string, length, ' ');
	}

	/**
	 * @see #fit(Writer, String, int)
	 */
	public static void fit(Writer writer, char[] string, int length) throws IOException {
		fit(writer, string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static void fit(Writer writer, String string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			writer.write(string, 0, length);
		} else {
			pad_(writer, string, length, c);
		}
	}

	/**
	 * @see #fit(Writer, String, int, char)
	 */
	public static void fit(Writer writer, char[] string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			writer.write(string, 0, length);
		} else {
			pad_(writer, string, stringLength, length, c);
		}
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void pad_(Writer writer, String string, int length, char c) throws IOException {
		writer.write(string);
		fill(writer, string, length, c);
	}

	/**
	 * Add enough characters to the specified writer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill(Writer writer, String string, int length, char c) throws IOException {
		fill(writer, string.length(), length, c);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void pad_(Writer writer, char[] string, int stringLength, int length, char c) throws IOException {
		writer.write(string);
		fill(writer, stringLength, length, c);
	}

	/**
	 * Add enough characters to the specified writer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill(Writer writer, int stringLength, int length, char c) throws IOException {
		fill(writer, length - stringLength, c);
	}

	/**
	 * Add the specified length of characters to the specified writer.
	 */
	private static void fill(Writer writer, int length, char c) throws IOException {
		writer.write(ArrayTools.fill(new char[length], c));
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with zeros
	 * at the front.
	 */
	public static void zeroPad(Writer writer, String string, int length) throws IOException {
		frontPad(writer, string, length, '0');
	}

	/**
	 * @see #zeroPad(Writer, String, int)
	 */
	public static void zeroPad(Writer writer, char[] string, int length) throws IOException {
		frontPad(writer, string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static void frontPad(Writer writer, String string, int length, char c) throws IOException {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writer.write(string);
		} else {
			frontPad_(writer, string, length, c);
		}
	}

	/**
	 * @see #frontPad(Writer, String, int, char)
	 */
	public static void frontPad(Writer writer, char[] string, int length, char c) throws IOException {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writer.write(string);
		} else {
			frontPad_(writer, string, stringLength, length, c);
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
	public static void zeroFit(Writer writer, String string, int length) throws IOException {
		frontFit(writer, string, length, '0');
	}

	/**
	 * @see #zeroFit(Writer, String, int)
	 */
	public static void zeroFit(Writer writer, char[] string, int length) throws IOException {
		frontFit(writer, string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, append it unchanged.
	 * If the string is longer than the specified length, append only the last
	 * part of the string.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static void frontFit(Writer writer, String string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			writer.write(string, stringLength - length, length);
		} else {
			frontPad_(writer, string, length, c);
		}
	}

	/**
	 * @see #frontFit(Writer, String, int, char)
	 */
	public static void frontFit(Writer writer, char[] string, int length, char c) throws IOException {
		if (length == 0) {
			return;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			writer.write(string);
		} else if (stringLength > length) {
			writer.write(string, stringLength - length, length);
		} else {
			frontPad_(writer, string, stringLength, length, c);
		}
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void frontPad_(Writer writer, String string, int length, char c) throws IOException {
		fill(writer, string, length, c);
		writer.write(string);
	}

	/**
	 * Pad the specified string without validating the args.
	 */
	private static void frontPad_(Writer writer, char[] string, int stringLength, int length, char c) throws IOException {
		fill(writer, stringLength, length, c);
		writer.write(string);
	}


	// ********** separating **********

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<p>
	 * <code>
	 * separate("012345", '-', 2) => "01-23-45"
	 * </code>
	 */
	public static void separate(Writer writer, String string, char separator, int segmentSize) throws IOException {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length();
		if (stringLength <= segmentSize) {
			writer.write(string);
		} else {
			separate(writer, string, separator, segmentSize, stringLength);
		}
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separate(Writer writer, String string, char separator, int segmentSize, int stringLength) throws IOException {
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			if (segCount == segmentSize) {
				writer.write(separator);
				segCount = 0;
			}
			segCount++;
			writer.write(c);
		}
	}

	/**
	 * @see #separate(Writer, String, char, int)
	 */
	public static void separate(Writer writer, char[] string, char separator, int segmentSize) throws IOException {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length;
		if (stringLength <= segmentSize) {
			writer.write(string);
		} else {
			separate(writer, string, separator, segmentSize, stringLength);
		}
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separate(Writer writer, char[] string, char separator, int segmentSize, int stringLength) throws IOException {
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			if (segCount == segmentSize) {
				writer.write(separator);
				segCount = 0;
			}
			segCount++;
			writer.write(c);
		}
	}


	// ********** delimiting/quoting **********

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quote(Writer writer, String string) throws IOException {
		delimit(writer, string, CharacterTools.QUOTE);
	}

	/**
	 * @see #quote(Writer, String)
	 */
	public static void quote(Writer writer, char[] string) throws IOException {
		delimit(writer, string, CharacterTools.QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimit(Writer writer, String string, char delimiter) throws IOException {
		writer.write(delimiter);
		int stringLength = string.length();
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			if (c == delimiter) {
				writer.write(c);
			}
			writer.write(c);
		}
		writer.write(delimiter);
	}

	/**
	 * @see #delimit(Writer, String, char)
	 */
	public static void delimit(Writer writer, char[] string, char delimiter) throws IOException {
		writer.write(delimiter);
		for (char c : string) {
			if (c == delimiter) {
				writer.write(c);
			}
			writer.write(c);
		}
		writer.write(delimiter);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimit(Writer writer, String string, String delimiter) throws IOException {
		int delimiterLength = delimiter.length();
		switch (delimiterLength) {
			case 0:
				writer.write(string);
				break;
			case 1:
				delimit(writer, string, delimiter.charAt(0));
				break;
			default:
				delimit(writer, string, delimiter, delimiterLength);
				break;
		}
	}

	/**
	 * No parm check
	 */
	private static void delimit(Writer writer, String string, String delimiter, int delimiterLength) throws IOException {
		writer.write(delimiter, 0, delimiterLength);
		writer.write(string);
		writer.write(delimiter, 0, delimiterLength);
	}

	/**
	 * @see #delimit(Writer, String, String)
	 */
	public static void delimit(Writer writer, char[] string, char[] delimiter) throws IOException {
		int delimiterLength = delimiter.length;
		switch (delimiterLength) {
			case 0:
				writer.write(string);
				break;
			case 1:
				delimit(writer, string, delimiter[0]);
				break;
			default:
				delimit(writer, string, delimiter, delimiterLength);
				break;
		}
	}

	/**
	 * No parm check
	 */
	private static void delimit(Writer writer, char[] string, char[] delimiter, int delimiterLength) throws IOException {
		writer.write(delimiter, 0, delimiterLength);
		writer.write(string);
		writer.write(delimiter, 0, delimiterLength);
	}


	// ********** undelimiting **********

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an {@link IllegalArgumentException} if the string is
	 * too short to undelimit (i.e. length < 2).
	 */
	public static void undelimit(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		undelimit_(writer, string, stringLength);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimit_(Writer writer, String string, int stringLength) throws IOException {
		char delimiter = string.charAt(0);  // the first char is the delimiter
		char c = delimiter;
		char next = string.charAt(1);
		int i = 1;
		int last = stringLength - 1;
		do {
			c = next;
			writer.write(c);
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
	 * @see #undelimit(Writer, String)
	 */
	public static void undelimit(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		undelimit_(writer, string, stringLength);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimit_(Writer writer, char[] string, int stringLength) throws IOException {
		char delimiter = string[0];  // the first char is the delimiter
		char c = delimiter;
		char next = string[1];
		int i = 1;
		int last = stringLength - 1;
		do {
			c = next;
			writer.write(c);
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
	public static void undelimit(Writer writer, String string, int count) throws IOException {
		if (count == 0) {
			writer.write(string);
			return;
		}
		int resultLength = string.length() - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		writer.write(string, count, resultLength);
	}

	/**
	 * @see #undelimit(Writer, String, int)
	 */
	public static void undelimit(Writer writer, char[] string, int count) throws IOException {
		if (count == 0) {
			writer.write(string);
			return;
		}
		int resultLength = string.length - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return;
		}
		writer.write(string, count, resultLength);
	}


	// ********** removing characters **********

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 */
	public static void removeFirstOccurrence(Writer writer, String string, char c) throws IOException {
		int index = string.indexOf(c);
		if (index == -1) {
			writer.write(string);
		} else {
			removeCharAtIndex(writer, string, index);
		}
	}

	private static void removeCharAtIndex(Writer writer, String string, int index) throws IOException {
		int last = string.length() - 1;
		if (index == 0) {
			// character found at the front of string
			writer.write(string, 1, last);
		} else {
			writer.write(string, 0, index);
			if (index != last) {
				// character is not the end of the string
				writer.write(string, index + 1, last - index);
			}
		}
	}

	/**
	 * @see #removeFirstOccurrence(Writer, String, char)
	 */
	public static void removeFirstOccurrence(Writer writer, char[] string, char c) throws IOException {
		int index = ArrayTools.indexOf(string, c);
		if (index == -1) {
			writer.write(string);
		} else {
			removeCharAtIndex(writer, string, index);
		}
	}

	private static void removeCharAtIndex(Writer writer, char[] string, int index) throws IOException {
		int last = string.length - 1;
		if (index == 0) {
			// character found at the front of string
			writer.write(string, 1, last);
		} else if (index == last) {
			// character found at the end of string
			writer.write(string, 0, last);
		} else {
			// character found somewhere in the middle of the string
			writer.write(string, 0, index);
			writer.write(string, index + 1, last - index);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 */
	public static void removeAllOccurrences(Writer writer, String string, char c) throws IOException {
		int first = string.indexOf(c);
		if (first == -1) {
			writer.write(string);
		} else {
			removeAllOccurrences(writer, string, c, first);
		}
	}

	/**
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrences(Writer writer, String string, char c, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length();
		for (int i = first; i < stringLength; i++) {
			char d = string.charAt(i);
			if (d != c) {
				writer.write(d);
			}
		}
	}

	/**
	 * @see #removeAllOccurrences(Writer, String, char)
	 */
	public static void removeAllOccurrences(Writer writer, char[] string, char c) throws IOException {
		int first = ArrayTools.indexOf(string, c);
		if (first == -1) {
			writer.write(string);
		} else {
			removeAllOccurrences(writer, string, c, first);
		}
	}

	/**
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrences(Writer writer, char[] string, char c, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length;
		for (int i = first; i < stringLength; i++) {
			char d = string[i];
			if (d != c) {
				writer.write(d);
			}
		}
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified stream.
	 */
	public static void removeAllSpaces(Writer writer, String string) throws IOException {
		removeAllOccurrences(writer, string, ' ');
	}

	/**
	 * @see #removeAllSpaces(Writer, String)
	 */
	public static void removeAllSpaces(Writer writer, char[] string) throws IOException {
		removeAllOccurrences(writer, string, ' ');
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified stream.
	 */
	public static void removeAllWhitespace(Writer writer, String string) throws IOException {
		int first = StringTools.indexOfWhitespace(string);
		if (first == -1) {
			writer.write(string);
		} else {
			removeAllWhitespace(writer, string, first);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void removeAllWhitespace(Writer writer, String string, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length();
		for (int i = first; i < stringLength; i++) {
			char c = string.charAt(i);
			if ( ! Character.isWhitespace(c)) {
				writer.write(c);
			}
		}
	}

	/**
	 * @see #removeAllWhitespace(Writer, String)
	 */
	public static void removeAllWhitespace(Writer writer, char[] string) throws IOException {
		int first = CharArrayTools.indexOfWhitespace(string);
		if (first == -1) {
			writer.write(string);
		} else {
			removeAllWhitespace(writer, string, first);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void removeAllWhitespace(Writer writer, char[] string, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length;
		for (int i = first; i < stringLength; i++) {
			char c = string[i];
			if ( ! Character.isWhitespace(c)) {
				writer.write(c);
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
	public static void compressWhitespace(Writer writer, String string) throws IOException {
		int first = StringTools.indexOfWhitespace(string);
		if (first == -1) {
			writer.write(string);
		} else {
			compressWhitespace(writer, string, first);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespace(Writer writer, String string, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length();
		int i = first;
		char c = string.charAt(i);
		main: while (true) {
			// replace first whitespace character with a space...
			writer.write(' ');
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
				writer.write(c);
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
	 * @see #compressWhitespace(Writer, String)
	 */
	public static void compressWhitespace(Writer writer, char[] string) throws IOException {
		int first = CharArrayTools.indexOfWhitespace(string);
		if (first == -1) {
			writer.write(string);
		} else {
			compressWhitespace(writer, string, first);
		}
	}

	/**
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespace(Writer writer, char[] string, int first) throws IOException {
		writer.write(string, 0, first);
		int stringLength = string.length;
		int i = first;
		char c = string[i];
		main: while (true) {
			// replace first whitespace character with a space...
			writer.write(' ');
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
				writer.write(c);
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
	public static void capitalize(Writer writer, String string) throws IOException {
		if (string.length() == 0) {
			return;
		}
		if (Character.isUpperCase(string.charAt(0))) {
			writer.write(string);
		} else {
			capitalize_(writer, string);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalize_(Writer writer, String string) throws IOException {
		writer.write(Character.toUpperCase(string.charAt(0)));
		writer.write(string, 1, string.length() - 1);
	}

	/**
	 * @see #capitalize(Writer, String)
	 */
	public static void capitalize(Writer writer, char[] string) throws IOException {
		if (string.length == 0) {
			return;
		}
		if (Character.isUpperCase(string[0])) {
			writer.write(string);
		} else {
			capitalize_(writer, string);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalize_(Writer writer, char[] string) throws IOException {
		writer.write(Character.toUpperCase(string[0]));
		writer.write(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified stream
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalize(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (StringTools.needNotBeUncapitalized(string, stringLength)) {
			writer.write(string);
		} else {
			uncapitalize(writer, string, stringLength);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void uncapitalize(Writer writer, String string, int stringLength) throws IOException {
		writer.write(Character.toLowerCase(string.charAt(0)));
		writer.write(string, 1, stringLength - 1);
	}

	/**
	 * @see #uncapitalize(Writer, String)
	 */
	public static void uncapitalize(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (CharArrayTools.needNotBeUncapitalized(string, stringLength)) {
			writer.write(string);
		} else {
			uncapitalize(writer, string, stringLength);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void uncapitalize(Writer writer, char[] string, int stringLength) throws IOException {
		writer.write(Character.toLowerCase(string[0]));
		writer.write(string, 1, stringLength - 1);
	}


	// ********** convert byte array to hex string **********

	/**
	 * Convert the specified byte array to the corresponding string of
	 * hexadecimal characters.
	 * @see org.eclipse.jpt.common.utility.internal.ByteArrayTools#convertToHexString(byte[])
	 */
	public static void convertToHexString(Writer writer, byte[] bytes) throws IOException {
		int bytesLength = bytes.length;
		if (bytesLength != 0) {
			convertToHexString(writer, bytes, bytesLength);
		}
	}

	/**
	 * Pre-condition: the byte array is not empty
	 */
	private static void convertToHexString(Writer writer, byte[] bytes, int bytesLength) throws IOException {
		char[] digits = CharacterTools.DIGITS;
		for (int i = 0; i < bytesLength; i++) {
			int b = bytes[i] & 0xFF; // clear any sign bits
			writer.write(digits[b >> 4]); // first nibble
			writer.write(digits[b & 0xF]); // second nibble
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
	public static void convertCamelCaseToAllCaps(Writer writer, String camelCaseString) throws IOException {
		int stringLength = camelCaseString.length();
		if (stringLength != 0) {
			convertCamelCaseToAllCaps_(writer, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	private static void convertCamelCaseToAllCaps_(Writer writer, String camelCaseString, int stringLength) throws IOException {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString.charAt(0);
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString.charAt(i));
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				writer.write('_');
			}
			writer.write(Character.toUpperCase(c));
			prev = c;
		}
	}

	/**
	 * @see #convertCamelCaseToAllCaps(Writer, String)
	 */
	public static void convertCamelCaseToAllCaps(Writer writer, char[] camelCaseString) throws IOException {
		int stringLength = camelCaseString.length;
		if (stringLength != 0) {
			convertCamelCaseToAllCaps_(writer, camelCaseString, stringLength);
		}
	}

	/**
	 * Pre-condition: the string is not empty
	 */
	private static void convertCamelCaseToAllCaps_(Writer writer, char[] camelCaseString, int stringLength) throws IOException {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString[i]);
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				writer.write('_');
			}
			writer.write(Character.toUpperCase(c));
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
	public static void convertCamelCaseToAllCaps(Writer writer, String camelCaseString, int maxLength) throws IOException {
		if (maxLength != 0) {
			int stringLength = camelCaseString.length();
			if (stringLength != 0) {
				convertCamelCaseToAllCaps(writer, camelCaseString, maxLength, stringLength);
			}
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertCamelCaseToAllCaps(Writer writer, String camelCaseString, int maxLength, int stringLength) throws IOException {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString.charAt(0);
		int writerLength = 0;
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString.charAt(i));
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				writer.write('_');
				if (++writerLength == maxLength) {
					return;
				}
			}
			writer.write(Character.toUpperCase(c));
			if (++writerLength == maxLength) {
				return;
			}
			prev = c;
		}
	}

	/**
	 * @see #convertCamelCaseToAllCaps(Writer, String, int)
	 */
	public static void convertCamelCaseToAllCaps(Writer writer, char[] camelCaseString, int maxLength) throws IOException {
		if (maxLength != 0) {
			int stringLength = camelCaseString.length;
			if (stringLength != 0) {
				convertCamelCaseToAllCaps(writer, camelCaseString, maxLength, stringLength);
			}
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertCamelCaseToAllCaps(Writer writer, char[] camelCaseString, int maxLength, int stringLength) throws IOException {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		int writerLength = 0;
		for (int i = 1; i <= stringLength; i++) {	// NB: start at 1 and end at stringLength!
			c = next;
			next = ((i == stringLength) ? 0 : camelCaseString[i]);
			if (StringTools.camelCaseWordBreak(prev, c, next)) {
				writer.write('_');
				if (++writerLength == maxLength) {
					return;
				}
			}
			writer.write(Character.toUpperCase(c));
			if (++writerLength == maxLength) {
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
	public static void convertAllCapsToCamelCase(Writer writer, String allCapsString) throws IOException {
		convertAllCapsToCamelCase(writer, allCapsString, true);
	}

	/**
	 * @see #convertAllCapsToCamelCase(Writer, String)
	 */
	public static void convertAllCapsToCamelCase(Writer writer, char[] allCapsString) throws IOException {
		convertAllCapsToCamelCase(writer, allCapsString, true);
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
	public static void convertAllCapsToCamelCase(Writer writer, String allCapsString, boolean capitalizeFirstLetter) throws IOException {
		int stringLength = allCapsString.length();
		if (stringLength != 0) {
			convertAllCapsToCamelCase(writer, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	private static void convertAllCapsToCamelCase(Writer writer, String allCapsString, boolean capitalizeFirstLetter, int stringLength) throws IOException {
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
				writer.write(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				writer.write((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
			}
		}
	}

	/**
	 * @see #convertAllCapsToCamelCase(Writer, String, boolean)
	 */
	public static void convertAllCapsToCamelCase(Writer writer, char[] allCapsString, boolean capitalizeFirstLetter) throws IOException {
		int stringLength = allCapsString.length;
		if (stringLength != 0) {
			convertAllCapsToCamelCase(writer, allCapsString, capitalizeFirstLetter, stringLength);
		}
	}

	private static void convertAllCapsToCamelCase(Writer writer, char[] allCapsString, boolean capitalizeFirstLetter, int stringLength) throws IOException {
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
				writer.write(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				writer.write((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
			}
		}
	}


	// ********** convert to Java string literal **********

	/**
	 * Convert the specified string to a Java string literal, with the
	 * appropriate escaped characters.
	 */
	public static void convertToJavaStringLiteral(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteral(writer, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteral(Writer writer, String string, int stringLength) throws IOException {
		writer.write(CharacterTools.QUOTE);
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(writer, string.charAt(i));
		}
		writer.write(CharacterTools.QUOTE);
	}

	/**
	 * Convert the specified string to the contents of a Java string literal,
	 * with the appropriate escaped characters.
	 */
	public static void convertToJavaStringLiteralContent(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength != 0) {
			convertToJavaStringLiteralContent(writer, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteralContent(Writer writer, String string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(writer, string.charAt(i));
		}
	}

	/**
	 * @see #convertToJavaStringLiteral(Writer, String)
	 */
	public static void convertToJavaStringLiteral(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteral(writer, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteral(Writer writer, char[] string, int stringLength) throws IOException {
		writer.write(CharacterTools.QUOTE);
		convertToJavaStringLiteralContent(writer, string, stringLength);
		writer.write(CharacterTools.QUOTE);
	}

	/**
	 * @see #convertToJavaStringLiteralContent(Writer, String)
	 */
	public static void convertToJavaStringLiteralContent(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength != 0) {
			convertToJavaStringLiteralContent(writer, string, stringLength);
		}
	}

	/**
	 * The length of the string is passed in.
	 */
	private static void convertToJavaStringLiteralContent(Writer writer, char[] string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToJavaStringLiteral(writer, string[i]);
		}
	}

	private static void convertToJavaStringLiteral(Writer writer, char c) throws IOException {
		switch (c) {
			case '\b':  // backspace
				writer.write("\\b");  //$NON-NLS-1$
				break;
			case '\t':  // horizontal tab
				writer.write("\\t");  //$NON-NLS-1$
				break;
			case '\n':  // line-feed LF
				writer.write("\\n");  //$NON-NLS-1$
				break;
			case '\f':  // form-feed FF
				writer.write("\\f");  //$NON-NLS-1$
				break;
			case '\r':  // carriage-return CR
				writer.write("\\r");  //$NON-NLS-1$
				break;
			case '"':  // double-quote
				writer.write("\\\"");  //$NON-NLS-1$
				break;
//			case '\'':  // single-quote
//				writer.write("\\'");  //$NON-NLS-1$
//				break;
			case '\\':  // backslash
				writer.write("\\\\");  //$NON-NLS-1$
				break;
			default:
				writer.write(c);
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
	public static void convertToXmlAttributeValue(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_XML_ATTRIBUTE_VALUE);
		} else {
			convertToXmlAttributeValue(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlAttributeValue(Writer writer, String string, int stringLength) throws IOException {
		int index = string.indexOf(CharacterTools.QUOTE);
		if (index == -1) {
			// no embedded quotes - use quote delimiters
			convertToDoubleQuotedXmlAttributeValue(writer, string, stringLength);
		} else {
			index = string.indexOf(CharacterTools.APOSTROPHE);
			if (index == -1) {
				// embedded quotes but no embedded apostrophes - use apostrophe delimiters
				convertToSingleQuotedXmlAttributeValue(writer, string, stringLength);
			} else {
				// embedded quotes *and* embedded apostrophes - use quote delimiters
				convertToDoubleQuotedXmlAttributeValue(writer, string, stringLength);
			}
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValue(Writer writer, String string, int stringLength) throws IOException {
		writer.write(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(writer, string, stringLength);
		writer.write(CharacterTools.QUOTE);
	}

	private static void convertToSingleQuotedXmlAttributeValue(Writer writer, String string, int stringLength) throws IOException {
		writer.write(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(writer, string, stringLength);
		writer.write(CharacterTools.APOSTROPHE);
	}

	/**
	 * Convert the specified string to a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(Writer, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(Writer writer, String string) throws IOException {
		convertToDoubleQuotedXmlAttributeValue(writer, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(Writer, String)
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength != 0) {
			convertToDoubleQuotedXmlAttributeValueContent(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToDoubleQuotedXmlAttributeValueContent(Writer writer, String string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValue(writer, string.charAt(i));
		}
	}

	/**
	 * Convert the specified string to a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(Writer, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValue(Writer writer, String string) throws IOException {
		convertToSingleQuotedXmlAttributeValue(writer, string, string.length());
	}

	/**
	 * Convert the specified string to the contents of a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(Writer, String)
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength != 0) {
			convertToSingleQuotedXmlAttributeValueContent(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToSingleQuotedXmlAttributeValueContent(Writer writer, String string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(writer, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlAttributeValue(Writer, String)
	 */
	public static void convertToXmlAttributeValue(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_XML_ATTRIBUTE_VALUE);
		} else {
			convertToXmlAttributeValue(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlAttributeValue(Writer writer, char[] string, int stringLength) throws IOException {
		int index = ArrayTools.indexOf(string, CharacterTools.QUOTE);
		if (index == -1) {
			// no embedded quotes - use quote delimiters
			convertToDoubleQuotedXmlAttributeValue(writer, string, stringLength);
		} else {
			index = ArrayTools.indexOf(string, CharacterTools.APOSTROPHE);
			if (index == -1) {
				// embedded quotes but no embedded apostrophes - use apostrophe delimiters
				convertToSingleQuotedXmlAttributeValue(writer, string, stringLength);
			} else {
				// embedded quotes *and* embedded apostrophes - use quote delimiters
				convertToDoubleQuotedXmlAttributeValue(writer, string, stringLength);
			}
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValue(Writer writer, char[] string, int stringLength) throws IOException {
		writer.write(CharacterTools.QUOTE);
		convertToDoubleQuotedXmlAttributeValueContent(writer, string, stringLength);
		writer.write(CharacterTools.QUOTE);
	}

	private static void convertToSingleQuotedXmlAttributeValue(Writer writer, char[] string, int stringLength) throws IOException {
		writer.write(CharacterTools.APOSTROPHE);
		convertToSingleQuotedXmlAttributeValueContent(writer, string, stringLength);
		writer.write(CharacterTools.APOSTROPHE);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValue(Writer, String)
	 * @see #convertToXmlAttributeValue(Writer, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValue(Writer writer, char[] string) throws IOException {
		convertToDoubleQuotedXmlAttributeValue(writer, string, string.length);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValueContent(Writer, String)
	 * @see #convertToXmlAttributeValue(Writer, char[])
	 */
	public static void convertToDoubleQuotedXmlAttributeValueContent(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength != 0) {
			convertToDoubleQuotedXmlAttributeValueContent(writer, string, stringLength);
		}
	}

	private static void convertToDoubleQuotedXmlAttributeValueContent(Writer writer, char[] string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToDoubleQuotedXmlAttributeValue(writer, string[i]);
		}
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValue(Writer, String)
	 * @see #convertToXmlAttributeValue(Writer, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValue(Writer writer, char[] string) throws IOException {
		convertToSingleQuotedXmlAttributeValue(writer, string, string.length);
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValueContent(Writer, String)
	 * @see #convertToXmlAttributeValue(Writer, char[])
	 */
	public static void convertToSingleQuotedXmlAttributeValueContent(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength != 0) {
			convertToSingleQuotedXmlAttributeValueContent(writer, string, stringLength);
		}
	}

	private static void convertToSingleQuotedXmlAttributeValueContent(Writer writer, char[] string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToSingleQuotedXmlAttributeValueContent(writer, string[i]);
		}
	}

	/**
	 * String is delimited with quotes - escape embedded quotes.
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToDoubleQuotedXmlAttributeValue(Writer writer, char c) throws IOException {
		switch (c) {
			case '"':  // double-quote
				writer.write(StringTools.XML_QUOTE);
				break;					
			case '&':  // ampersand
				writer.write(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				writer.write(StringTools.XML_LT);
				break;					
			default:
				writer.write(c);
				break;
		}
	}

	/**
	 * String is delimited with apostrophes - escape embedded apostrophes.
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToSingleQuotedXmlAttributeValueContent(Writer writer, char c) throws IOException {
		switch (c) {
			case '\'':  // apostrophe
				writer.write(StringTools.XML_APOS);
				break;					
			case '&':  // ampersand
				writer.write(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				writer.write(StringTools.XML_LT);
				break;					
			default:
				writer.write(c);
				break;
		}
	}

	/**
	 * Convert the specified string to an XML element text, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementCDATA(Writer, String)
	 */
	public static void convertToXmlElementText(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength != 0) {
			convertToXmlElementText(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementText(Writer writer, String string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(writer, string.charAt(i));
		}
	}

	/**
	 * @see #convertToXmlElementText(Writer, String)
	 */
	public static void convertToXmlElementText(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength != 0) {
			convertToXmlElementText(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementText(Writer writer, char[] string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			convertToXmlElementText(writer, string[i]);
		}
	}

	/**
	 * @see <a href="http://www.w3.org/TR/xml/#syntax">XML Spec</a>
	 */
	private static void convertToXmlElementText(Writer writer, char c) throws IOException {
		switch (c) {
			case '&':  // ampersand
				writer.write(StringTools.XML_AMP);
				break;					
			case '<':  // less than
				writer.write(StringTools.XML_LT);
				break;					
			default:
				writer.write(c);
				break;
		}
	}

	/**
	 * Convert the specified string to an XML element CDATA, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementText(Writer, String)
	 */
	public static void convertToXmlElementCDATA(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_XML_ELEMENT_CDATA);
		} else {
			convertToXmlElementCDATA(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementCDATA(Writer writer, String string, int stringLength) throws IOException {
		writer.write(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(writer, string, stringLength);
		writer.write(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * Convert the specified string to the contents of an XML element CDATA,
	 * escaping the appropriate characters.
	 * @see #convertToXmlElementCDATA(Writer, String)
	 * @see #convertToXmlElementText(Writer, String)
	 */
	public static void convertToXmlElementCDATAContent(Writer writer, String string) throws IOException {
		int stringLength = string.length();
		if (stringLength != 0) {
			convertToXmlElementCDATAContent(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 * @see <a href="http://www.w3.org/TR/xml/#sec-cdata-sect">XML Spec</a>
	 */
	private static void convertToXmlElementCDATAContent(Writer writer, String string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			writer.write(c);
			// "]]>" -> "]]&gt;"
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string.charAt(i);
			writer.write(c);
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string.charAt(i);
			if (c == '>') {
				writer.write(StringTools.XML_GT);
			} else {
				writer.write(c);
			}
		}
	}

	/**
	 * @see #convertToXmlElementCDATA(Writer, String)
	 */
	public static void convertToXmlElementCDATA(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength == 0) {
			writer.write(StringTools.EMPTY_XML_ELEMENT_CDATA);
		} else {
			convertToXmlElementCDATA(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 */
	private static void convertToXmlElementCDATA(Writer writer, char[] string, int stringLength) throws IOException {
		writer.write(StringTools.XML_ELEMENT_CDATA_START);
		convertToXmlElementCDATAContent(writer, string, stringLength);
		writer.write(StringTools.XML_ELEMENT_CDATA_END);
	}

	/**
	 * @see #convertToXmlElementCDATAContent(Writer, String)
	 * @see #convertToXmlElementCDATA(Writer, char[])
	 */
	public static void convertToXmlElementCDATAContent(Writer writer, char[] string) throws IOException {
		int stringLength = string.length;
		if (stringLength != 0) {
			convertToXmlElementCDATAContent(writer, string, stringLength);
		}
	}

	/**
	 * The (non-zero) length of the string is passed in.
	 * @see <a href="http://www.w3.org/TR/xml/#sec-cdata-sect">XML Spec</a>
	 */
	private static void convertToXmlElementCDATAContent(Writer writer, char[] string, int stringLength) throws IOException {
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			writer.write(c);
			// "]]>" -> "]]&gt;"
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string[i];
			writer.write(c);
			if (c != ']') {
				continue;
			}
			if (++i >= stringLength) {
				break;
			}
			c = string[i];
			if (c == '>') {
				writer.write(StringTools.XML_GT);
			} else {
				writer.write(c);
			}
		}
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private WriterTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
