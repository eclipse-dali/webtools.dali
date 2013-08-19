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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link String} utility methods
 * 
 * @see CharArrayTools
 * @see org.eclipse.jpt.common.utility.internal.io.WriterTools
 * @see StringBuilderTools
 * @see StringBufferTools
 */
public final class StringTools {

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	/** empty string */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/** empty string array */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];


	// ********** last **********

	/**
	 * Return the last character of the specified string.
	 */
	public static char last(String string) {
		return string.charAt(string.length() - 1);
	}


	// ********** concatenation **********

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static String concatenate(String... strings) {
		return (strings.length != 0) ? concatenate_(IteratorTools.iterator(strings)) : EMPTY_STRING;
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static String concatenate(Iterable<String> strings) {
		return concatenate(strings.iterator());
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static String concatenate(Iterator<String> strings) {
		return strings.hasNext() ? concatenate_(strings) : EMPTY_STRING;
	}

	/**
	 * Pre-condition: iterator is not empty
	 */
	private static String concatenate_(Iterator<String> strings) {
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next());
		}
		return sb.toString();
	}

	/**
	 * Return a concatenation of the specified strings
	 * inserting the specified separator between them.
	 */
	public static String concatenate(String[] strings, String separator) {
		return (strings.length != 0) ? concatenate_(IteratorTools.iterator(strings), separator) : EMPTY_STRING;
	}

	/**
	 * Return a concatenation of the specified strings
	 * inserting the specified separator between them.
	 */
	public static String concatenate(Iterable<String> strings, String separator) {
		return concatenate(strings.iterator(), separator);
	}

	/**
	 * Return a concatenation of the specified strings
	 * inserting the specified separator between them.
	 */
	public static String concatenate(Iterator<String> strings, String separator) {
		return strings.hasNext() ? concatenate_(strings, separator) : EMPTY_STRING;
	}

	/**
	 * Pre-condition: iterator is not empty
	 */
	private static String concatenate_(Iterator<String> strings, String separator) {
		int separatorLength = separator.length();
		if (separatorLength == 0) {
			return concatenate_(strings);
		}
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next());
			sb.append(separator);
		}
		sb.setLength(sb.length() - separatorLength);  // chop off trailing separator
		return sb.toString();
	}


	// ********** padding/truncating/centering/repeating **********

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * each end.
	 */
	public static String center(String string, int length) {
		return center(string, length, ' ');
	}

	/**
	 * Center the specified string in the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at each end.
	 */
	public static String center(String string, int length, char c) {
		if (length == 0) {
			return EMPTY_STRING;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			int begin = (stringLength - length) >> 1; // take fewer characters off the front
			return string.substring(begin, begin + length);  // NB: end index is exclusive
		}
		// stringLength < length
		char[] result = new char[length];
		int begin = (length - stringLength) >> 1; // add fewer characters to the front
		Arrays.fill(result, 0, begin, c);
		string.getChars(0, stringLength, result, begin);
		Arrays.fill(result, begin + stringLength, length, c);
		return new String(result);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with spaces
	 * at the end.
	 */
	public static String pad(String string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static String pad(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return pad(string, length, c, stringLength);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, returned it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with spaces at
	 * the end.
	 */
	public static String fit(String string, int length) {
		return fit(string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the end.
	 */
	public static String fit(String string, int length, char c) {
		if (length == 0) {
			return EMPTY_STRING;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return string.substring(0, length);  // NB: end index is exclusive
		}
		return pad(string, length, c, stringLength);
	}

	/**
	 * no length checks
	 */
	private static String pad(String string, int length, char c, int stringLength) {
		char[] result = new char[length];
		string.getChars(0, stringLength, result, 0);
		Arrays.fill(result, stringLength, length, c);
		return new String(result);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with zeros
	 * at the front.
	 */
	public static String zeroPad(String string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, throw an
	 * {@link IllegalArgumentException}.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static String frontPad(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return frontPad(string, length, c, stringLength);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, return only the last
	 * part of the string.
	 * If the string is shorter than the specified length, pad it with zeros
	 * at the front.
	 */
	public static String zeroFit(String string, int length) {
		return frontFit(string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, return only the last
	 * part of the string.
	 * If the string is shorter than the specified length, pad it with the
	 * specified character at the front.
	 */
	public static String frontFit(String string, int length, char c) {
		if (length == 0) {
			return EMPTY_STRING;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return string.substring(stringLength - length);
		}
		return frontPad(string, length, c, stringLength);
	}

	/**
	 * no length checks
	 */
	private static String frontPad(String string, int length, char c, int stringLength) {
		char[] result = new char[length];
		int padLength = length - stringLength;
		string.getChars(0, stringLength, result, padLength);
		Arrays.fill(result, 0, padLength, c);
		return new String(result);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, return it unchanged.
	 * If the string is longer than the specified length, truncate it.
	 * If the string is shorter than the specified length, repeat it to the
	 * specified length, truncating the last iteration if necessary.
	 */
	public static String repeat(String string, int length) {
		if (length == 0) {
			return EMPTY_STRING;
		}
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return string.substring(0, length);  // NB: end index is exclusive
		}
		StringBuilder sb = new StringBuilder(length);
		StringBuilderTools.repeat(sb, string, length, stringLength);
		return sb.toString();
	}


	// ********** separating **********

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<p>
	 * <code>
	 * separate("012345", '-', 2) => "01-23-45"
	 * </code>
	 */
	public static String separate(String string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length();
		return (stringLength <= segmentSize) ? string : separate(string, separator, segmentSize, stringLength);
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static String separate(String string, char separator, int segmentSize, int stringLength) {
		int resultLen = stringLength + (stringLength / segmentSize);
		if ((stringLength % segmentSize) == 0) {
			resultLen--;  // no separator after the final segment if nothing following it
		}
		char[] result = new char[resultLen];
		int j = 0;
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string.charAt(i);
			if (segCount == segmentSize) {
				result[j++] = separator;
				segCount = 0;
			}
			segCount++;
			result[j++] = c;
		}
		return new String(result);
	}


	// ********** delimiting/quoting **********

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static String quote(String string) {
		return delimit(string, CharacterTools.QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static String delimit(String string, char delimiter) {
		int stringLength = string.length();
		StringBuilder sb = new StringBuilder(stringLength + 2);
		StringBuilderTools.delimit(sb, string, delimiter, stringLength);
		return sb.toString();
	}

	/**
	 * @see #delimit(String, char)
	 */
	public static class CharDelimiter
		implements Transformer<String, String>
	{
		private final char delimiter;
		public CharDelimiter(char delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public String transform(String string) {
			return delimit(string, this.delimiter);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.delimiter);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static String delimit(String string, String delimiter) {
		return delimit(string, delimiter, delimiter.length());
	}

	/* CU private */ static String delimit(String string, String delimiter, int delimiterLength) {
		switch (delimiterLength) {
			case 0:
				return string;
			case 1:
				return delimit(string, delimiter.charAt(0));
			default:
				return delimit_(string, delimiter, delimiterLength);
		}
	}

	/**
	 * No parm check
	 */
	private static String delimit_(String string, String delimiter, int delimiterLength) {
		int stringLength = string.length();
		char[] result = new char[stringLength + (2 * delimiterLength)];
		delimiter.getChars(0, delimiterLength, result, 0);
		string.getChars(0, stringLength, result, delimiterLength);
		delimiter.getChars(0, delimiterLength, result, delimiterLength+stringLength);
		return new String(result);
	}

	/**
	 * @see #delimit(String, String)
	 */
	public static class StringDelimiter
		implements Transformer<String, String>
	{
		private final String delimiter;
		private final int delimiterLength;
		public StringDelimiter(String delimiter) {
			super();
			this.delimiter = delimiter;
			this.delimiterLength = delimiter.length();
		}
		public String transform(String string) {
			return delimit(string, this.delimiter, this.delimiterLength);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.delimiter);
		}
	}


	// ********** delimiting queries **********

	/**
	 * Return whether the specified string is quoted:
	 * <p>
	 * <code>
	 * "\"foo\""
	 * </code>
	 */
	public static boolean isQuoted(String string) {
		return isDelimited(string, CharacterTools.QUOTE);
	}

	/**
	 * Return whether the specified string is parenthetical:
	 * <p>
	 * <code>
	 * "(foo)"
	 * </code>
	 */
	public static boolean isParenthetical(String string) {
		return isDelimited(string, CharacterTools.OPEN_PARENTHESIS, CharacterTools.CLOSE_PARENTHESIS);
	}

	/**
	 * Return whether the specified string is bracketed:
	 * <p>
	 * <code>
	 * "[foo]"
	 * </code>
	 */
	public static boolean isBracketed(String string) {
		return isDelimited(string, CharacterTools.OPEN_BRACKET, CharacterTools.CLOSE_BRACKET);
	}

	/**
	 * Return whether the specified string is braced:
	 * <p>
	 * <code>
	 * "{foo}"
	 * </code>
	 */
	public static boolean isBraced(String string) {
		return isDelimited(string, CharacterTools.OPEN_BRACE, CharacterTools.CLOSE_BRACE);
	}

	/**
	 * Return whether the specified string is chevroned:
	 * <p>
	 * {@code
	 * "<foo>"
	 * }
	 */
	public static boolean isChevroned(String string) {
		return isDelimited(string, CharacterTools.OPEN_CHEVRON, CharacterTools.CLOSE_CHEVRON);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * character.
	 */
	public static boolean isDelimited(String string, char c) {
		return isDelimited(string, c, c);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * characters.
	 */
	public static boolean isDelimited(String string, char start, char end) {
		int stringLength = string.length();
		return (stringLength < 2) ?
				false :
				isDelimited(string, start, end, stringLength);
	}

	/**
	 * no length check
	 */
	private static boolean isDelimited(String string, char start, char end, int stringLength) {
		return (string.charAt(0) == start) && (string.charAt(stringLength - 1) == end);
	}


	// ********** undelimiting **********

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an {@link IllegalArgumentException} if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static String undelimit(String string) {
		int stringLength = string.length();
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return EMPTY_STRING;
		}
		// delegate to StringBuilderTools to take care of embedded delimiters
		StringBuilder sb = new StringBuilder(resultLength);
		StringBuilderTools.undelimit_(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * {@link IllegalArgumentException}.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static String undelimit(String string, int count) {
		if (count == 0) {
			return string;
		}
		int resultLength = string.length() - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return EMPTY_STRING;
		}
		return undelimit(string, count, resultLength);
	}

	/**
	 * No parm checks
	 */
	private static String undelimit(String string, int count, int resultLength) {
		char[] result = new char[resultLength];
		string.getChars(count, count+resultLength, result, 0);
		return new String(result);
	}


	// ********** removing characters **********

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and return the result.
	 */
	public static String removeFirstOccurrence(String string, char c) {
		int index = string.indexOf(c);
		if (index == -1) {
			// character not found
			return string;
		}
		if (index == 0) {
			// character found at the front of string
			return string.substring(1);
		}
		int stringLength = string.length();
		int last = stringLength - 1;
		if (index == last) {
			// character found at the end of string
			return string.substring(0, last);  // NB: end index is exclusive
		}
		// character found somewhere in the middle of the string
		StringBuilder sb = new StringBuilder(last);
		sb.append(string, 0, index);  // NB: end index is exclusive
		sb.append(string, index + 1, stringLength);  // NB: end index is exclusive
		return sb.toString();
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and return the result.
	 */
	public static String removeAllOccurrences(String string, char c) {
		int first = string.indexOf(c);
		return (first == -1) ? string : removeAllOccurrences(string, c, first);
	}

	/**
	 * no occurrence check
	 */
	private static String removeAllOccurrences(String string, char c, int first) {
		int stringLength = string.length();
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.removeAllOccurrences(sb, string, c, first, stringLength);
		return sb.toString();
	}

	/**
	 * Remove all the spaces from the specified string and return the result.
	 */
	public static String removeAllSpaces(String string) {
		return removeAllOccurrences(string, ' ');
	}

	/**
	 * Remove all the whitespace from the specified string and return the result.
	 * @see Character#isWhitespace(char)
	 */
	public static String removeAllWhitespace(String string) {
		int first = indexOfWhitespace(string);
		return (first == -1) ? string : removeAllWhitespace(string, first);
	}

	/**
	 * Return the index of the first whitespace character in the specified
	 * string. Return -1 if the specified string contains no whitespace.
	 * @see Character#isWhitespace(char)
	 */
	public static int indexOfWhitespace(String string) {
		int stringLength = string.length();
		for (int i = 0; i < stringLength; i++) {
			if (Character.isWhitespace(string.charAt(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * no whitespace check
	 */
	private static String removeAllWhitespace(String string, int first) {
		int stringLength = string.length();
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.removeAllWhitespace(sb, string, first, stringLength);
		return sb.toString();
	}

	/**
	 * Compress the whitespace in the specified string and return the result.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * @see Character#isWhitespace(char)
	 */
	public static String compressWhitespace(String string) {
		int first = indexOfWhitespace(string);
		return (first == -1) ? string : new String(compressWhitespace(string, first));
	}

	/**
	 * no whitespace check
	 */
	private static String compressWhitespace(String string, int first) {
		int stringLength = string.length();
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.compressWhitespace(sb, string, first, stringLength);
		return sb.toString();
	}


	// ********** common prefix **********

	/**
	 * Return the length of the common prefix shared by the specified strings.
	 */
	public static int commonPrefixLength(String s1, String s2) {
		return commonPrefixLength_(s1, s2, Math.min(s1.length(), s2.length()));
	}

	/**
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum.
	 */
	public static int commonPrefixLength(String s1, String s2, int max) {
		return commonPrefixLength_(s1, s2, Math.min(max, Math.min(s1.length(), s2.length())));
	}

	/**
	 * no max check
	 */
	private static int commonPrefixLength_(String s1, String s2, int max) {
		for (int i = 0; i < max; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				return i;
			}
		}
		return max;	// all the characters up to 'max' are the same
	}


	// ********** capitalization **********

	/**
	 * Return the specified string with its first letter capitalized.
	 */
	public static String capitalize(String string) {
		int stringLength = string.length();
		return ((stringLength == 0) || Character.isUpperCase(string.charAt(0))) ?
				string :
				capitalize(string, stringLength);
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static String capitalize(String string, int stringLength) {
		char[] result = new char[stringLength];
		result[0] = Character.toUpperCase(string.charAt(0));
		string.getChars(1, stringLength, result, 1);
		return new String(result);
	}

	/**
	 * @see #capitalize(String)
	 */
	public static final Transformer<String, String> CAPITALIZER = new Capitalizer();

	/* CU private */ static class Capitalizer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return capitalize(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return CAPITALIZER;
		}
	}

	/**
	 * Return the specified string with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static String uncapitalize(String string) {
		int stringLength = string.length();
		return needNotBeUncapitalized(string, stringLength) ?
				string :
				uncapitalize(string, stringLength);
	}

	/**
	 * Return whether the specified string need not have its first character
	 * capitalized when the specified string is to be capitalized.
	 */
	public static boolean needNotBeUncapitalized(String string, int stringLength) {
		if (stringLength == 0) {
			return true;
		}
		if (Character.isLowerCase(string.charAt(0))) {
			return true;
		}
		// if both the first and second characters are capitalized,
		// return the string unchanged
		if ((stringLength > 1)
				&& Character.isUpperCase(string.charAt(1))
				&& Character.isUpperCase(string.charAt(0))){
			return true;
		}
		return false;
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static String uncapitalize(String string, int stringLength) {
		char[] result = new char[stringLength];
		result[0] = Character.toLowerCase(string.charAt(0));
		string.getChars(1, stringLength, result, 1);
		return new String(result);
	}

	/**
	 * @see #uncapitalize(String)
	 */
	public static final Transformer<String, String> UNCAPITALIZER = new Uncapitalizer();

	/* CU private */ static class Uncapitalizer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return uncapitalize(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return UNCAPITALIZER;
		}
	}


	// ********** string queries **********

	/**
	 * Return whether the specified string is <code>null</code>, empty, or
	 * contains only whitespace characters.
	 */
	public static boolean isBlank(String string) {
		if (string == null) {
			return true;
		}
		int stringLength = string.length();
		if (stringLength == 0) {
			return true;
		}
		return isBlank(string, stringLength);
	}

	/**
	 * no <code>null</code> or length checks
	 */
	private static boolean isBlank(String string, int stringLength) {
		for (int i = stringLength; i-- > 0; ) {
			if ( ! Character.isWhitespace(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified string is non-<code>null</code>, non-empty,
	 * and does not contain only whitespace characters.
	 */
	public static boolean isNotBlank(String string) {
		return ! isBlank(string);
	}

	/**
	 * @see #isNotBlank(String)
	 */
	public static final Predicate<String> IS_NOT_BLANK = new IsNotBlank();

	/* CU private */ static class IsNotBlank
		implements Predicate<String>, Serializable
	{
		public boolean evaluate(String string) {
			return isNotBlank(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return IS_NOT_BLANK;
		}
	}

	/**
	 * Return whether the specified strings are equal, ignoring case.
	 * Check for <code>null</code>s.
	 */
	public static boolean equalsIgnoreCase(String s1, String s2) {
		return (s1 == null) ? (s2 == null) : s1.equalsIgnoreCase(s2);
	}

	/**
	 * Return whether the specified string starts with the specified prefix,
	 * ignoring case.
	 * @see String#regionMatches(boolean, int, String, int, int)
	 */
	public static boolean startsWithIgnoreCase(String string, String prefix) {
		return string.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	public static class StartsWithIgnoreCase
		extends CriterionPredicate<String, String>
	{
		public StartsWithIgnoreCase(String prefix) {
			super(prefix);
		}
		public boolean evaluate(String string) {
			return startsWithIgnoreCase(string, this.criterion);
		}
	}

	/**
	 * Return whether the specified string is uppercase.
	 */
	public static boolean isUppercase(String string) {
		return (string.length() != 0) && isUppercase_(string);
	}

	/**
	 * no length check
	 */
	static boolean isUppercase_(String string) {
		return string.equals(string.toUpperCase());
	}

	/**
	 * Return whether the specified string is lowercase.
	 */
	public static boolean isLowercase(String string) {
		return (string.length() != 0) && isLowercase_(string);
	}

	/**
	 * no length check
	 */
	static boolean isLowercase_(String string) {
		return string.equals(string.toLowerCase());
	}


	// ********** byte arrays **********

	/**
	 * Convert the specified string of hexadecimal characters to a byte array.
	 * @see ByteArrayTools#convertToHexString(byte[])
	 */
	public static byte[] convertHexStringToByteArray(String hexString) {
		int hexStringLength = hexString.length();
		if (hexStringLength == 0) {
			return ByteArrayTools.EMPTY_BYTE_ARRAY;
		}
		if (BitTools.isOdd(hexStringLength)) {
			throw new IllegalArgumentException("Odd-sized hexadecimal string: " + hexString + " (" + hexStringLength + " characters)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return convertHexStringToByteArray(hexString, hexStringLength);
	}

	/**
	 * Pre-condition: the string is neither empty nor odd-sized
	 */
	private static byte[] convertHexStringToByteArray(String hexString, int hexStringLength) {
		byte[] bytes = new byte[BitTools.half(hexStringLength)];
		for (int bi = bytes.length - 1, si = hexStringLength - 2; bi >= 0; bi--, si -= 2) {
			byte digit1 = (byte) Character.digit(hexString.charAt(si), 16);
			if (digit1 == -1) {
				throw new IllegalArgumentException(buildIllegalHexCharMessage(hexString, si));
			}
			byte digit2 = (byte) Character.digit(hexString.charAt(si + 1), 16);
			if (digit2 == -1) {
				throw new IllegalArgumentException(buildIllegalHexCharMessage(hexString, si + 1));
			}
			bytes[bi] = (byte) ((digit1 << 4) + digit2);
		}
		return bytes;
	}

	static String buildIllegalHexCharMessage(String hexString, int index) {
		StringBuilder sb = new StringBuilder(hexString.length() + 40);
		sb.append("Illegal hexadecimal character: "); //$NON-NLS-1$
		sb.append(hexString, 0, index);
		sb.append('[');
		sb.append(hexString.charAt(index));
		sb.append(']');
		sb.append(hexString, index + 1, hexString.length());
		return sb.toString();
	}


	// ********** convert camel case to all caps **********

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * <p>
	 * <code>
	 * "largeProject" -> "LARGE_PROJECT"
	 * </code>
	 */
	public static String convertCamelCaseToAllCaps(String camelCaseString) {
		int stringLength = camelCaseString.length();
		return (stringLength == 0) ?
				camelCaseString :
				convertCamelCaseToAllCaps_(camelCaseString, stringLength);
	}

	/**
	 * no length check
	 */
	private static String convertCamelCaseToAllCaps_(String camelCaseString, int stringLength) {
		StringBuilder sb = new StringBuilder(stringLength + (stringLength / 4));
		StringBuilderTools.convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		return sb.toString();
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
	public static String convertCamelCaseToAllCaps(String camelCaseString, int maxLength) {
		if (maxLength == 0) {
			return EMPTY_STRING;
		}
		int stringLength = camelCaseString.length();
		return (stringLength == 0) ?
				camelCaseString :
				convertCamelCaseToAllCaps(camelCaseString, maxLength, stringLength);
	}

	/**
	 * no check for empty string or zero max length
	 */
	private static String convertCamelCaseToAllCaps(String camelCaseString, int maxLength, int stringLength) {
		StringBuilder sb = new StringBuilder(maxLength);
		StringBuilderTools.convertCamelCaseToAllCaps(sb, camelCaseString, maxLength, stringLength);
		return sb.toString();
	}

	/**
	 * Return whether the specified series of characters occur at
	 * a "camel case" work break:<ul>
	 * <code>
	 * <li>"*aa" -> false
	 * <li>"*AA" -> false
	 * <li>"*Aa" -> false
	 * <li>"AaA" -> false
	 * <li>"AAA" -> false
	 * <li>"aa*" -> false
	 * <li>"AaA" -> false
	 * <li>"aAa" -> true
	 * <li>"AA*" -> false
	 * <li>"AAa" -> true
	 * </code>
	 * </ul>
	 * where <code>'*' == &lt;any char&gt;</code>
	 */
	public static boolean camelCaseWordBreak(char prev, char c, char next) {
		if (prev == 0) {	// start of string
			return false;
		}
		if (Character.isLowerCase(c)) {
			return false;
		}
		if (Character.isLowerCase(prev)) {
			return true;
		}
		if (next == 0) {	// end of string
			return false;
		}
		return Character.isLowerCase(next);
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
	public static String convertAllCapsToCamelCase(String allCapsString) {
		return convertAllCapsToCamelCase(allCapsString, true);  // true => capitalize first letter
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
	public static String convertAllCapsToCamelCase(String allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length();
		return (stringLength == 0) ?
			allCapsString :
			convertAllCapsToCamelCase(allCapsString, capitalizeFirstLetter, stringLength);
	}

	/**
	 * no length check
	 */
	private static String convertAllCapsToCamelCase(String allCapsString, boolean capitalizeFirstLetter, int stringLength) {
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		return sb.toString();
	}


	// ********** convert to Java string literal **********

	/**
	 * Value: {@value}
	 */
	public static final String EMPTY_JAVA_STRING_LITERAL = "\"\"";  //$NON-NLS-1$

	/**
	 * Convert the specified string to a Java string literal, with the
	 * appropriate escaped characters.
	 */
	public static String convertToJavaStringLiteral(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToJavaStringLiteral(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToJavaStringLiteral(String)
	 */
	public static final Transformer<String, String> JAVA_STRING_LITERAL_TRANSFORMER = new JavaStringLiteralTransformer();

	/* CU private */ static class JavaStringLiteralTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToJavaStringLiteral(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return JAVA_STRING_LITERAL_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to the contents of a Java string literal,
	 * with the appropriate escaped characters.
	 */
	public static String convertToJavaStringLiteralContent(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToJavaStringLiteralContent(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToJavaStringLiteralContent(String)
	 */
	public static final Transformer<String, String> JAVA_STRING_LITERAL_CONTENT_TRANSFORMER = new JavaStringLiteralContentTransformer();

	/* CU private */ static class JavaStringLiteralContentTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToJavaStringLiteralContent(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return JAVA_STRING_LITERAL_CONTENT_TRANSFORMER;
		}
	}


	// ********** convert to XML **********

	public static final String EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE = "\"\"";  //$NON-NLS-1$
	public static final String EMPTY_SINGLE_QUOTED_XML_ATTRIBUTE_VALUE = "''";  //$NON-NLS-1$
	public static final String EMPTY_XML_ATTRIBUTE_VALUE = EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE;
	public static final String XML_ELEMENT_CDATA_START = "<![CDATA[";  //$NON-NLS-1$
	public static final String XML_ELEMENT_CDATA_END = "]]>";  //$NON-NLS-1$
	public static final String EMPTY_XML_ELEMENT_CDATA = XML_ELEMENT_CDATA_START + XML_ELEMENT_CDATA_END;

	// XML predefined entities
	public static final String XML_QUOTE = "&quot;"; //$NON-NLS-1$
	public static final String XML_AMP   = "&amp;"; //$NON-NLS-1$
	public static final String XML_APOS  = "&apos;"; //$NON-NLS-1$
	public static final String XML_LT    = "&lt;"; //$NON-NLS-1$
	public static final String XML_GT    = "&gt;"; //$NON-NLS-1$

	/**
	 * Convert the specified string to an XML attribute value, escaping the
	 * appropriate characters. Delimit with quotes (<code>"</code>) unless
	 * there are <em>embedded</em> quotes (<em>and</em> no embedded
	 * apostrophes); in which case, delimit with apostrophes (<code>'</code>).
	 */
	public static String convertToXmlAttributeValue(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToXmlAttributeValue(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToXmlAttributeValue(String)
	 */
	public static final Transformer<String, String> XML_ATTRIBUTE_VALUE_TRANSFORMER = new XmlAttributeValueTransformer();

	/* CU private */ static class XmlAttributeValueTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToXmlAttributeValue(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_ATTRIBUTE_VALUE_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(String)
	 */
	public static String convertToDoubleQuotedXmlAttributeValue(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValue(String)
	 */
	public static final Transformer<String, String> DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER = new DoubleQuotedXmlAttributeValueTransformer();

	/* CU private */ static class DoubleQuotedXmlAttributeValueTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToDoubleQuotedXmlAttributeValue(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to the contents of a double-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * double quotes).
	 * @see #convertToXmlAttributeValue(String)
	 */
	public static String convertToDoubleQuotedXmlAttributeValueContent(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder(stringLength + 10);
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValueContent(String)
	 */
	public static final Transformer<String, String> XML_DOUBLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER = new XmlDoubleQuotedAttributeValueContentTransformer();

	/* CU private */ static class XmlDoubleQuotedAttributeValueContentTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToDoubleQuotedXmlAttributeValueContent(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_DOUBLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(String)
	 */
	public static String convertToSingleQuotedXmlAttributeValue(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_SINGLE_QUOTED_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToSingleQuotedXmlAttributeValue(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValue(String)
	 */
	public static final Transformer<String, String> SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER = new SingleQuotedXmlAttributeValueTransformer();

	/* CU private */ static class SingleQuotedXmlAttributeValueTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToSingleQuotedXmlAttributeValue(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to the contents of a single-quoted XML
	 * attribute value, escaping the appropriate characters (i.e. the embedded
	 * single quotes).
	 * @see #convertToXmlAttributeValue(String)
	 */
	public static String convertToSingleQuotedXmlAttributeValueContent(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder(stringLength + 10);
		StringBuilderTools.convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValueContent(String)
	 */
	public static final Transformer<String, String> XML_SINGLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER = new XmlSingleQuotedAttributeValueContentTransformer();

	/* CU private */ static class XmlSingleQuotedAttributeValueContentTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToSingleQuotedXmlAttributeValueContent(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_SINGLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to an XML element text, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementCDATA(String)
	 */
	public static String convertToXmlElementText(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return string;
		}
		StringBuilder sb = new StringBuilder(stringLength + 8);
		StringBuilderTools.convertToXmlElementText(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToXmlElementText(String)
	 */
	public static final Transformer<String, String> XML_ELEMENT_TEXT_TRANSFORMER = new XmlElementTextTransformer();

	/* CU private */ static class XmlElementTextTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToXmlElementText(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_ELEMENT_TEXT_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to an XML element CDATA, escaping the
	 * appropriate characters.
	 * @see #convertToXmlElementText(String)
	 */
	public static String convertToXmlElementCDATA(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_XML_ELEMENT_CDATA;
		}
		StringBuilder sb = new StringBuilder(stringLength + EMPTY_XML_ELEMENT_CDATA.length() + 6);
		StringBuilderTools.convertToXmlElementCDATA(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToXmlElementCDATA(String)
	 */
	public static final Transformer<String, String> XML_ELEMENT_CDATA_TRANSFORMER = new XmlElementCDATATransformer();

	/* CU private */ static class XmlElementCDATATransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToXmlElementCDATA(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_ELEMENT_CDATA_TRANSFORMER;
		}
	}

	/**
	 * Convert the specified string to the contents of an XML element CDATA,
	 * escaping the appropriate characters.
	 * @see #convertToXmlElementCDATA(String)
	 * @see #convertToXmlElementText(String)
	 */
	public static String convertToXmlElementCDATAContent(String string) {
		int stringLength = string.length();
		if (stringLength == 0) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToXmlElementCDATAContent(sb, string, stringLength);
		return sb.toString();
	}

	/**
	 * @see #convertToXmlElementCDATAContent(String)
	 */
	public static final Transformer<String, String> XML_ELEMENT_CDATA_CONTENT_TRANSFORMER = new XmlElementCDATAContentTransformer();

	/* CU private */ static class XmlElementCDATAContentTransformer
		implements Transformer<String, String>, Serializable
	{
		public String transform(String string) {
			return convertToXmlElementCDATAContent(string);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return XML_ELEMENT_CDATA_CONTENT_TRANSFORMER;
		}
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private StringTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
