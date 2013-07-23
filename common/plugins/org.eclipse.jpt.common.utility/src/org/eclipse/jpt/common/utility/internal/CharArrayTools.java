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
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * <code>char[]</code> string-related utility methods.
 * 
 * @see StringTools
 * @see org.eclipse.jpt.common.utility.internal.io.WriterTools
 * @see StringBuilderTools
 * @see StringBufferTools
 * @see ArrayTools
 */
public final class CharArrayTools {

	/** carriage return */
	public static final char[] CR = StringTools.CR.toCharArray();

	/** empty char array */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];

	/** empty char array array */
	public static final char[][] EMPTY_CHAR_ARRAY_ARRAY = new char[0][0];


	// ********** last **********

	/**
	 * Return the last character of the specified string.
	 */
	public static char last(char[] string) {
		return string[string.length - 1];
	}


	// ********** concatenation **********

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static char[] concatenate(char[]... strings) {
		int stringLength = 0;
		for (char[] string : strings) {
			stringLength += string.length;
		}
		if (stringLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		char[] buffer = new char[stringLength];
		int i = 0;
		for (char[] string : strings) {
			int len = string.length;
			if (len > 0) {
				System.arraycopy(string, 0, buffer, i, len);
				i += len;
			}
		}
		return buffer;
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static char[] concatenate(Iterable<char[]> strings) {
		return concatenate(strings.iterator());
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static char[] concatenate(Iterator<char[]> strings) {
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next());
		}
		return sb.toString().toCharArray();
	}


	// ********** padding/truncating/centering **********

	/**
	 * @see StringTools#center(String, int)
	 */
	public static char[] center(char[] string, int length) {
		return center(string, length, ' ');
	}

	/**
	 * @see StringTools#center(String, int, char)
	 */
	public static char[] center(char[] string, int length, char c) {
		if (length == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return ArrayTools.subArrayLength(string, ((stringLength - length) >> 1), length); // take fewer characters off the front
		}
		// stringLength < length
		char[] result = new char[length];
		int begin = (length - stringLength) >> 1; // add fewer characters to the front
		Arrays.fill(result, 0, begin, c);
		System.arraycopy(string, 0, result, begin, stringLength);
		Arrays.fill(result, begin + stringLength, length, c);
		return result;
	}

	/**
	 * @see StringTools#pad(String, int)
	 */
	public static char[] pad(char[] string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * @see StringTools#pad(String, int, char)
	 */
	public static char[] pad(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return pad(string, length, c, stringLength);
	}

	/**
	 * @see StringTools#fit(String, int)
	 */
	public static char[] fit(char[] string, int length) {
		return fit(string, length, ' ');
	}

	/**
	 * @see StringTools#fit(String, int, char)
	 */
	public static char[] fit(char[] string, int length, char c) {
		if (length == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return ArrayTools.subArrayLength(string, 0, length);
		}
		return pad(string, length, c, stringLength);
	}

	/**
	 * no length checks
	 */
	private static char[] pad(char[] string, int length, char c, int stringLength) {
		char[] result = new char[length];
		System.arraycopy(string, 0, result, 0, stringLength);
		Arrays.fill(result, stringLength, length, c);
		return result;
	}

	/**
	 * @see StringTools#zeroPad(String, int)
	 */
	public static char[] zeroPad(char[] string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * @see StringTools#frontPad(String, int, char)
	 */
	public static char[] frontPad(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return frontPad(string, length, c, stringLength);
	}

	/**
	 * @see StringTools#zeroFit(String, int)
	 */
	public static char[] zeroFit(char[] string, int length) {
		return frontFit(string, length, '0');
	}

	/**
	 * @see StringTools#frontFit(String, int, char)
	 */
	public static char[] frontFit(char[] string, int length, char c) {
		if (length == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		int stringLength = string.length;
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return ArrayTools.subArrayLength(string, stringLength - length, length);
		}
		return frontPad(string, length, c, stringLength);
	}

	/**
	 * no length checks
	 */
	private static char[] frontPad(char[] string, int length, char c, int stringLength) {
		char[] result = new char[length];
		int padLength = length - stringLength;
		System.arraycopy(string, 0, result, padLength, stringLength);
		Arrays.fill(result, 0, padLength, c);
		return result;
	}


	// ********** separating **********

	/**
	 * @see StringTools#separate(String, char, int)
	 */
	public static char[] separate(char[] string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int stringLength = string.length;
		return (stringLength <= segmentSize) ? string : separate(string, separator, segmentSize, stringLength);
	}

	/**
	 * Pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static char[] separate(char[] string, char separator, int segmentSize, int stringLength) {
		int resultLen = stringLength + (stringLength / segmentSize);
		if ((stringLength % segmentSize) == 0) {
			resultLen--;  // no separator after the final segment if nothing following it
		}
		char[] result = new char[resultLen];
		int j = 0;
		int segCount = 0;
		for (int i = 0; i < stringLength; i++) {
			char c = string[i];
			if (segCount == segmentSize) {
				result[j++] = separator;
				segCount = 0;
			}
			segCount++;
			result[j++] = c;
		}
		return result;
	}


	// ********** delimiting/quoting **********

	/**
	 * @see StringTools#quote(String)
	 */
	public static char[] quote(char[] string) {
		return delimit(string, CharacterTools.QUOTE);
	}

	/**
	 * @see StringTools#delimit(String, char)
	 */
	public static char[] delimit(char[] string, char delimiter) {
		int stringLength = string.length;
		StringBuilder sb = new StringBuilder(stringLength + 2);
		StringBuilderTools.delimit(sb, string, delimiter, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #delimit(char[], char)
	 */
	public static class CharDelimiter
		implements Transformer<char[], char[]>
	{
		private final char delimiter;
		public CharDelimiter(char delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public char[] transform(char[] string) {
			return delimit(string, this.delimiter);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.delimiter);
		}
	}

	/**
	 * @see StringTools#delimit(String, String)
	 */
	public static char[] delimit(char[] string, char[] delimiter) {
		return delimit(string, delimiter, delimiter.length);
	}

	/* CU private */ static char[] delimit(char[] string, char[] delimiter, int delimiterLength) {
		switch (delimiterLength) {
			case 0:
				return string;
			case 1:
				return delimit(string, delimiter[0]);
			default:
				return delimit_(string, delimiter, delimiterLength);
		}
	}

	/**
	 * No parm check
	 */
	private static char[] delimit_(char[] string, char[] delimiter, int delimiterLength) {
		int stringLength = string.length;
		char[] result = new char[stringLength+(2*delimiterLength)];
		System.arraycopy(delimiter, 0, result, 0, delimiterLength);
		System.arraycopy(string, 0, result, delimiterLength, stringLength);
		System.arraycopy(delimiter, 0, result, stringLength+delimiterLength, delimiterLength);
		return result;
	}

	/**
	 * @see #delimit(char[], char[])
	 */
	public static class CharArrayDelimiter
		implements Transformer<char[], char[]>
	{
		private final char[] delimiter;
		private final int delimiterLength;
		public CharArrayDelimiter(char[] delimiter) {
			super();
			this.delimiter = delimiter;
			this.delimiterLength = delimiter.length;
		}
		public char[] transform(char[] string) {
			return delimit(string, this.delimiter, this.delimiterLength);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.delimiter);
		}
	}


	// ********** delimiting queries **********

	/**
	 * @see StringTools#isQuoted(String)
	 */
	public static boolean isQuoted(char[] string) {
		return isDelimited(string, CharacterTools.QUOTE);
	}

	/**
	 * @see StringTools#isParenthetical(String)
	 */
	public static boolean isParenthetical(char[] string) {
		return isDelimited(string, CharacterTools.OPEN_PARENTHESIS, CharacterTools.CLOSE_PARENTHESIS);
	}

	/**
	 * @see StringTools#isBracketed(String)
	 */
	public static boolean isBracketed(char[] string) {
		return isDelimited(string, CharacterTools.OPEN_BRACKET, CharacterTools.CLOSE_BRACKET);
	}

	/**
	 * @see StringTools#isBraced(String)
	 */
	public static boolean isBraced(char[] string) {
		return isDelimited(string, CharacterTools.OPEN_BRACE, CharacterTools.CLOSE_BRACE);
	}

	/**
	 * @see StringTools#isChevroned(String)
	 */
	public static boolean isChevroned(char[] string) {
		return isDelimited(string, CharacterTools.OPEN_CHEVRON, CharacterTools.CLOSE_CHEVRON);
	}

	/**
	 * @see StringTools#isDelimited(String, char)
	 */
	public static boolean isDelimited(char[] string, char c) {
		return isDelimited(string, c, c);
	}

	/**
	 * @see StringTools#isDelimited(String, char, char)
	 */
	public static boolean isDelimited(char[] string, char start, char end) {
		int stringLength = string.length;
		return (stringLength < 2) ?
				false :
				isDelimited(string, start, end, stringLength);
	}

	/**
	 * no length check
	 */
	private static boolean isDelimited(char[] string, char start, char end, int stringLength) {
		return (string[0] == start) && (string[stringLength - 1] == end);
	}


	// ********** undelimiting **********

	/**
	 * @see StringTools#undelimit(String)
	 */
	public static char[] undelimit(char[] string) {
		int stringLength = string.length;
		int resultLength = stringLength - 2;
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		// delegate to StringBuilderTools to take care of embedded delimiters
		StringBuilder sb = new StringBuilder(resultLength);
		StringBuilderTools.undelimit_(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see StringTools#undelimit(String, int)
	 */
	public static char[] undelimit(char[] string, int count) {
		if (count == 0) {
			return string;
		}
		int resultLength = string.length - (2 * count);
		if (resultLength < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (resultLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		return undelimit(string, count, resultLength);
	}

	/**
	 * No parm checks
	 */
	private static char[] undelimit(char[] string, int count, int resultLength) {
		return ArrayTools.subArrayLength(string, count, resultLength);
	}


	// ********** removing characters **********

	/**
	 * @see StringTools#removeFirstOccurrence(String, char)
	 */
	public static char[] removeFirstOccurrence(char[] string, char c) {
		int index = ArrayTools.indexOf(string, c);
		if (index == -1) {
			// character not found
			return string;
		}
		int last = string.length - 1;
		char[] result = new char[last];
		if (index == 0) {
			// character found at the front of string
			System.arraycopy(string, 1, result, 0, last);
		} else if (index == last) {
			// character found at the end of string
			System.arraycopy(string, 0, result, 0, last);
		} else {
			// character found somewhere in the middle of the string
			System.arraycopy(string, 0, result, 0, index);
			System.arraycopy(string, index + 1, result, index, last - index);
		}
		return result;
	}

	/**
	 * @see StringTools#removeAllOccurrences(String, char)
	 */
	public static char[] removeAllOccurrences(char[] string, char c) {
		int first = ArrayTools.indexOf(string, c);
		return (first == -1) ? string : removeAllOccurrences(string, c, first);
	}

	/**
	 * no occurrence check
	 */
	private static char[] removeAllOccurrences(char[] string, char c, int first) {
		int stringLength = string.length;
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.removeAllOccurrences(sb, string, c, first, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see StringTools#removeAllSpaces(String)
	 */
	public static char[] removeAllSpaces(char[] string) {
		return removeAllOccurrences(string, ' ');
	}

	/**
	 * @see StringTools#removeAllWhitespace(String)
	 */
	public static char[] removeAllWhitespace(char[] string) {
		int first = indexOfWhitespace(string);
		return (first == -1) ? string : removeAllWhitespace(string, first);
	}

	/**
	 * @see StringTools#indexOfWhitespace(String)
	 */
	public static int indexOfWhitespace(char[] string) {
		int stringLength = string.length;
		for (int i = 0; i < stringLength; i++) {
			if (Character.isWhitespace(string[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * no whitespace check
	 */
	private static char[] removeAllWhitespace(char[] string, int first) {
		int stringLength = string.length;
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.removeAllWhitespace(sb, string, first, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see StringTools#compressWhitespace(String)
	 */
	public static char[] compressWhitespace(char[] string) {
		int first = indexOfWhitespace(string);
		return (first == -1) ? string : compressWhitespace(string, first);
	}

	/**
	 * no whitespace check
	 */
	private static char[] compressWhitespace(char[] string, int first) {
		int stringLength = string.length;
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.compressWhitespace(sb, string, first, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}


	// ********** common prefix **********

	/**
	 * @see StringTools#commonPrefixLength(String, String)
	 */
	public static int commonPrefixLength(char[] s1, char[] s2) {
		return commonPrefixLength(s1, s2, Math.min(s1.length, s2.length));
	}

	/**
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum.
	 */
	public static int commonPrefixLength(char[] s1, char[] s2, int max) {
		for (int i = 0; i < max; i++) {
			if (s1[i] != s2[i]) {
				return i;
			}
		}
		return max;	// all the characters up to 'max' are the same
	}


	// ********** capitalization **********

	/**
	 * @see StringTools#capitalize(String)
	 * Return a <em>new</em> array if the first letter must be changed.
	 */
	public static char[] capitalize(char[] string) {
		int stringLength = string.length;
		return ((stringLength == 0) || Character.isUpperCase(string[0])) ?
				string :
				capitalize(string, stringLength);
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static char[] capitalize(char[] string, int stringLength) {
		char[] result = new char[stringLength];
		result[0] = Character.toUpperCase(string[0]);
		System.arraycopy(string, 1, result, 1, stringLength-1);
		return result;
	}

	/**
	 * @see #capitalize(char[])
	 */
	public static final Transformer<char[], char[]> CAPITALIZER = new Capitalizer();

	/* CU private */ static class Capitalizer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#uncapitalize(String)
	 * Return a <em>new</em> array if the first letter must be changed.
	 */
	public static char[] uncapitalize(char[] string) {
		int stringLength = string.length;
		return needNotBeUncapitalized(string, stringLength) ?
				string :
				uncapitalize(string, stringLength);
	}

	/**
	 * @see StringTools#needNotBeUncapitalized(String, int)
	 */
	public static boolean needNotBeUncapitalized(char[] string, int stringLength) {
		if (stringLength == 0) {
			return true;
		}
		if (Character.isLowerCase(string[0])) {
			return true;
		}
		// if both the first and second characters are capitalized,
		// return the string unchanged
		if ((stringLength > 1)
				&& Character.isUpperCase(string[1])
				&& Character.isUpperCase(string[0])){
			return true;
		}
		return false;
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static char[] uncapitalize(char[] string, int stringLength) {
		char[] result = new char[stringLength];
		result[0] = Character.toLowerCase(string[0]);
		System.arraycopy(string, 1, result, 1, stringLength-1);
		return result;
	}

	/**
	 * @see #uncapitalize(char[])
	 */
	public static final Transformer<char[], char[]> UNCAPITALIZER = new Uncapitalizer();

	/* CU private */ static class Uncapitalizer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#isBlank(String)
	 */
	public static boolean isBlank(char[] string) {
		if (string == null) {
			return true;
		}
		int stringLength = string.length;
		if (stringLength == 0) {
			return true;
		}
		return isBlank(string, stringLength);
	}

	/**
	 * no <code>null</code> or length checks
	 */
	private static boolean isBlank(char[] string, int stringLength) {
		for (int i = stringLength; i-- > 0; ) {
			if ( ! Character.isWhitespace(string[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see StringTools#isNotBlank(String)
	 */
	public static boolean isNotBlank(char[] string) {
		return ! isBlank(string);
	}

	/**
	 * @see #isNotBlank(char[])
	 */
	public static final Predicate<char[]> IS_NOT_BLANK = new IsNotBlank();

	/* CU private */ static class IsNotBlank
		implements Predicate<char[]>, Serializable
	{
		public boolean evaluate(char[] string) {
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
	 * @see StringTools#equalsIgnoreCase(String, String)
	 */
	public static boolean equalsIgnoreCase(char[] s1, char[] s2) {
		return (s1 == null) ? (s2 == null) : ((s2 != null) && equalsIgnoreCase_(s1, s2));
	}

	/**
	 * no <code>null</code> checks
	 */
	private static boolean equalsIgnoreCase_(char[] s1, char[] s2) {
		int stringLength = s1.length;
		if (stringLength != s2.length) {
			return false;
		}
		for (int i = stringLength; i-- > 0; ) {
			if ( ! CharacterTools.equalsIgnoreCase(s1[i], s2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see StringTools#startsWithIgnoreCase(String, String)
	 */
	public static boolean startsWithIgnoreCase(char[] string, char[] prefix) {
		int prefixLength = prefix.length;
		if (string.length < prefixLength) {
			return false;
		}
		for (int i = prefixLength; i-- > 0; ) {
			if ( ! CharacterTools.equalsIgnoreCase(string[i], prefix[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see StringTools#isUppercase(String)
	 */
	public static boolean isUppercase(char[] string) {
		return (string.length != 0) && StringTools.isUppercase_(new String(string));
	}

	/**
	 * @see StringTools#isLowercase(String)
	 */
	public static boolean isLowercase(char[] string) {
		return (string.length != 0) && StringTools.isLowercase_(new String(string));
	}


	// ********** byte arrays **********

	/**
	 * @see StringTools#convertHexStringToByteArray(String)
	 */
	public static byte[] convertHexStringToByteArray(char[] hexString) {
		int hexStringLength = hexString.length;
		if (hexStringLength == 0) {
			return ByteArrayTools.EMPTY_BYTE_ARRAY;
		}
		if (BitTools.isOdd(hexStringLength)) {
			throw new IllegalArgumentException("Odd-sized hexadecimal string: " + new String(hexString) + " (" + hexStringLength + " characters)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return convertHexStringToByteArray(hexString, hexStringLength);
	}

	/**
	 * Pre-condition: the string is neither empty nor odd-sized
	 */
	private static byte[] convertHexStringToByteArray(char[] hexString, int hexStringLength) {
		byte[] bytes = new byte[BitTools.half(hexStringLength)];
		for (int bi = bytes.length - 1, si = hexStringLength - 2; bi >= 0; bi--, si -= 2) {
			byte digit1 = (byte) Character.digit(hexString[si], 16);
			if (digit1 == -1) {
				throw new IllegalArgumentException(buildIllegalHexCharMessage(hexString, si));
			}
			byte digit2 = (byte) Character.digit(hexString[si + 1], 16);
			if (digit2 == -1) {
				throw new IllegalArgumentException(buildIllegalHexCharMessage(hexString, si + 1));
			}
			bytes[bi] = (byte) ((digit1 << 4) + digit2);
		}
		return bytes;
	}

	private static String buildIllegalHexCharMessage(char[] hexString, int index) {
		return StringTools.buildIllegalHexCharMessage(new String(hexString), index);
	}


	// ********** convert camel case to all caps **********

	/**
	 * @see StringTools#convertCamelCaseToAllCaps(String)
	 */
	public static char[] convertCamelCaseToAllCaps(char[] camelCaseString) {
		int stringLength = camelCaseString.length;
		return (stringLength == 0) ?
				camelCaseString :
				convertCamelCaseToAllCaps_(camelCaseString, stringLength);
	}

	/**
	 * no length check
	 */
	private static char[] convertCamelCaseToAllCaps_(char[] camelCaseString, int stringLength) {
		StringBuilder sb = new StringBuilder(stringLength + (stringLength / 4));
		StringBuilderTools.convertCamelCaseToAllCaps_(sb, camelCaseString, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see StringTools#convertCamelCaseToAllCaps(String, int)
	 */
	public static char[] convertCamelCaseToAllCaps(char[] camelCaseString, int maxLength) {
		if (maxLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		int stringLength = camelCaseString.length;
		return (stringLength == 0) ?
				camelCaseString :
				convertCamelCaseToAllCaps(camelCaseString, maxLength, stringLength);
	}

	/**
	 * no check for empty string or zero max length
	 */
	private static char[] convertCamelCaseToAllCaps(char[] camelCaseString, int maxLength, int stringLength) {
		StringBuilder sb = new StringBuilder(maxLength);
		StringBuilderTools.convertCamelCaseToAllCaps(sb, camelCaseString, maxLength, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}


	// ********** convert all caps to camel case **********

	/**
	 * @see StringTools#convertAllCapsToCamelCase(String)
	 */
	public static char[] convertAllCapsToCamelCase(char[] allCapsString) {
		return convertAllCapsToCamelCase(allCapsString, true);  // true => capitalize first letter
	}

	/**
	 * @see StringTools#convertAllCapsToCamelCase(String, boolean)
	 */
	public static char[] convertAllCapsToCamelCase(char[] allCapsString, boolean capitalizeFirstLetter) {
		int stringLength = allCapsString.length;
		return (stringLength == 0) ?
			allCapsString :
			convertAllCapsToCamelCase(allCapsString, capitalizeFirstLetter, stringLength);
	}

	/**
	 * no length check
	 */
	private static char[] convertAllCapsToCamelCase(char[] allCapsString, boolean capitalizeFirstLetter, int stringLength) {
		StringBuilder sb = new StringBuilder(stringLength);
		StringBuilderTools.convertAllCapsToCamelCase(sb, allCapsString, capitalizeFirstLetter, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}


	// ********** convert to Java string literal **********

	public static final char[] EMPTY_JAVA_STRING_LITERAL = StringTools.EMPTY_JAVA_STRING_LITERAL.toCharArray();

	/**
	 * @see StringTools#convertToJavaStringLiteral(String)
	 */
	public static char[] convertToJavaStringLiteral(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToJavaStringLiteral(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToJavaStringLiteral(char[])
	 */
	public static final Transformer<char[], char[]> JAVA_STRING_LITERAL_TRANSFORMER = new JavaStringLiteralTransformer();

	/* CU private */ static class JavaStringLiteralTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToJavaStringLiteralContent(String)
	 */
	public static char[] convertToJavaStringLiteralContent(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToJavaStringLiteralContent(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToJavaStringLiteralContent(char[])
	 */
	public static final Transformer<char[], char[]> JAVA_STRING_LITERAL_CONTENT_TRANSFORMER = new JavaStringLiteralContentTransformer();

	/* CU private */ static class JavaStringLiteralContentTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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

	public static final char[] EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE = StringTools.EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE.toCharArray();
	public static final char[] EMPTY_SINGLE_QUOTED_XML_ATTRIBUTE_VALUE = StringTools.EMPTY_SINGLE_QUOTED_XML_ATTRIBUTE_VALUE.toCharArray();
	public static final char[] EMPTY_XML_ATTRIBUTE_VALUE = StringTools.EMPTY_XML_ATTRIBUTE_VALUE.toCharArray();
	public static final char[] XML_ELEMENT_CDATA_START = StringTools.XML_ELEMENT_CDATA_START.toCharArray();
	public static final char[] XML_ELEMENT_CDATA_END = StringTools.XML_ELEMENT_CDATA_END.toCharArray();
	public static final char[] EMPTY_XML_ELEMENT_CDATA = StringTools.EMPTY_XML_ELEMENT_CDATA.toCharArray();

	// XML predefined entities
	public static final char[] XML_QUOTE = StringTools.XML_QUOTE.toCharArray();
	public static final char[] XML_AMP   = StringTools.XML_AMP.toCharArray();
	public static final char[] XML_APOS  = StringTools.XML_APOS.toCharArray();
	public static final char[] XML_LT    = StringTools.XML_LT.toCharArray();
	public static final char[] XML_GT    = StringTools.XML_GT.toCharArray();

	/**
	 * @see StringTools#convertToXmlAttributeValue(String)
	 */
	public static char[] convertToXmlAttributeValue(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToXmlAttributeValue(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToXmlAttributeValue(char[])
	 */
	public static final Transformer<char[], char[]> XML_ATTRIBUTE_VALUE_TRANSFORMER = new XmlAttributeValueTransformer();

	/* CU private */ static class XmlAttributeValueTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToDoubleQuotedXmlAttributeValue(String)
	 * @see #convertToXmlAttributeValue(char[])
	 */
	public static char[] convertToDoubleQuotedXmlAttributeValue(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValue(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValue(char[])
	 */
	public static final Transformer<char[], char[]> DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER = new DoubleQuotedXmlAttributeValueTransformer();

	/* CU private */ static class DoubleQuotedXmlAttributeValueTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToDoubleQuotedXmlAttributeValueContent(String)
	 * @see #convertToXmlAttributeValue(char[])
	 */
	public static char[] convertToDoubleQuotedXmlAttributeValueContent(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(stringLength + 10);
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToDoubleQuotedXmlAttributeValueContent(char[])
	 */
	public static final Transformer<char[], char[]> XML_DOUBLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER = new XmlDoubleQuotedAttributeValueContentTransformer();

	/* CU private */ static class XmlDoubleQuotedAttributeValueContentTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToSingleQuotedXmlAttributeValue(String)
	 * @see #convertToXmlAttributeValue(char[])
	 */
	public static char[] convertToSingleQuotedXmlAttributeValue(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_SINGLE_QUOTED_XML_ATTRIBUTE_VALUE;
		}
		StringBuilder sb = new StringBuilder(stringLength + 12);
		StringBuilderTools.convertToSingleQuotedXmlAttributeValue(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValue(char[])
	 */
	public static final Transformer<char[], char[]> SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER = new SingleQuotedXmlAttributeValueTransformer();

	/* CU private */ static class SingleQuotedXmlAttributeValueTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToSingleQuotedXmlAttributeValueContent(String)
	 * @see #convertToXmlAttributeValue(char[])
	 */
	public static char[] convertToSingleQuotedXmlAttributeValueContent(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(stringLength + 10);
		StringBuilderTools.convertToSingleQuotedXmlAttributeValueContent(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToSingleQuotedXmlAttributeValueContent(char[])
	 */
	public static final Transformer<char[], char[]> XML_SINGLE_QUOTED_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER = new XmlSingleQuotedAttributeValueContentTransformer();

	/* CU private */ static class XmlSingleQuotedAttributeValueContentTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToXmlElementText(String)
	 */
	public static char[] convertToXmlElementText(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return string;
		}
		StringBuilder sb = new StringBuilder(stringLength + 8);
		StringBuilderTools.convertToXmlElementText(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToXmlElementText(char[])
	 */
	public static final Transformer<char[], char[]> XML_ELEMENT_TEXT_TRANSFORMER = new XmlElementTextTransformer();

	/* CU private */ static class XmlElementTextTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToXmlElementCDATA(String)
	 */
	public static char[] convertToXmlElementCDATA(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_XML_ELEMENT_CDATA;
		}
		StringBuilder sb = new StringBuilder(stringLength + EMPTY_XML_ELEMENT_CDATA.length + 6);
		StringBuilderTools.convertToXmlElementCDATA(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToXmlElementCDATA(char[])
	 */
	public static final Transformer<char[], char[]> XML_ELEMENT_CDATA_TRANSFORMER = new XmlElementCDATATransformer();

	/* CU private */ static class XmlElementCDATATransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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
	 * @see StringTools#convertToXmlElementCDATAContent(String)
	 */
	public static char[] convertToXmlElementCDATAContent(char[] string) {
		int stringLength = string.length;
		if (stringLength == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(stringLength + 6);
		StringBuilderTools.convertToXmlElementCDATAContent(sb, string, stringLength);
		return StringBuilderTools.convertToCharArray(sb);
	}

	/**
	 * @see #convertToXmlElementCDATAContent(char[])
	 */
	public static final Transformer<char[], char[]> XML_ELEMENT_CDATA_CONTENT_TRANSFORMER = new XmlElementCDATAContentTransformer();

	/* CU private */ static class XmlElementCDATAContentTransformer
		implements Transformer<char[], char[]>, Serializable
	{
		public char[] transform(char[] string) {
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


	// ********** String methods **********

	/**
	 * @see String#codePointAt(int)
	 */
	public static int codePointAt(char[] string, int index) {
		return Character.codePointAt(string, index);
	}

	/**
	 * @see String#codePointBefore(int)
	 */
	public static int codePointBefore(char[] string, int index) {
		return Character.codePointBefore(string, index);
	}

	/**
	 * @see String#codePointCount(int, int)
	 */
	public static int codePointCount(char[] string, int beginIndex, int endIndex) {
		return Character.codePointCount(string, beginIndex, endIndex - beginIndex);
	}

	/**
	 * @see String#compareTo(String)
	 */
	public static int compareTo(char[] s1, char[] s2) {
		int len1 = s1.length;
		int len2 = s2.length;
		int lim = Math.min(len1, len2);
		for (int i = 0; i < lim; i++) {
			char c1 = s1[i];
			char c2 = s2[i];
			if (c1 != c2) {
				return c1 - c2;
			}
		}
		return len1 - len2;
	}

	/**
	 * @see String#compareToIgnoreCase(String)
	 * @see #CASE_INSENSITIVE_ORDER
	 */
	public static int compareToIgnoreCase(char[] s1, char[] s2) {
		return CASE_INSENSITIVE_ORDER.compare(s1, s2);
	}

	/**
	 * @see String#CASE_INSENSITIVE_ORDER
	 */
	public static final Comparator<char[]> CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();
	private static class CaseInsensitiveComparator
		implements Comparator<char[]>, java.io.Serializable
	{
		private static final long serialVersionUID = 1;

		CaseInsensitiveComparator() {
			super();
		}

		public int compare(char[] s1, char[] s2) {
			int len1 = s1.length;
			int len2 = s2.length;
			for (int i1 = 0, i2 = 0; (i1 < len1) && (i2 < len2); i1++, i2++) {
				char c1 = s1[i1];
				char c2 = s2[i2];
				if (c1 != c2) {
					c1 = Character.toUpperCase(c1);
					c2 = Character.toUpperCase(c2);
					if (c1 != c2) {
						c1 = Character.toLowerCase(c1);
						c2 = Character.toLowerCase(c2);
						if (c1 != c2) {
							return c1 - c2;
						}
					}
				}
			}
			return len1 - len2;
		}
	}

	/**
	 * @see String#concat(String)
	 * @see #concatenate(char[][])
	 */
	public static char[] concat(char[] s1, char[] s2) {
		int len1 = s1.length;
		if (len1 == 0) {
			return s2;
		}
		int len2 = s2.length;
		if (len2 == 0) {
			return s1;
		}

		char[] buffer = new char[len1 + len2];
		System.arraycopy(s1, 0, buffer, 0, len1);
		System.arraycopy(s2, 0, buffer, len1, len2);
		return buffer;
	}

	/**
	 * @see String#contains(CharSequence)
	 */
	public static boolean contains(char[] string, CharSequence cs) {
		return indexOf(string, cs) > -1;
	}

	/**
	 * @see String#contains(CharSequence)
	 */
	public static boolean contains(char[] s1, char[] s2) {
		return indexOf(s1, s2) > -1;
	}

	/**
	 * @see String#contentEquals(CharSequence)
	 */
	public static boolean contentEquals(char[] string, CharSequence cs) {
		int len = string.length;
		if (len != cs.length()) {
			return false;
		}
		for (int i = len; i-- > 0; ) {
			if (string[i] != cs.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see String#contentEquals(StringBuffer)
	 */
	public static boolean contentEquals(char[] string, StringBuffer sb) {
		synchronized(sb) {
			return contentEquals(string, (CharSequence) sb);
		}
	}

	/**
	 * @see String#endsWith(String)
	 */
	public static boolean endsWith(char[] string, char[] suffix) {
		return startsWith(string, suffix, string.length - suffix.length);
	}

	/**
	 * @see String#getBytes()
	 */
	public static byte[] getBytes(char[] string) {
		return String.valueOf(string).getBytes(); // delegate 'bytes' stuff...
	}

	/**
	 * @see String#getBytes(String)
	 */
	public static byte[] getBytes(char[] string, String charsetName) throws UnsupportedEncodingException {
		return String.valueOf(string).getBytes(charsetName); // delegate 'bytes' stuff...
	}

	/**
	 * @see String#getChars(int, int, char[], int)
	 */
	public static void getChars(char[] string, int srcBegin, int srcEnd, char[] dest, int destBegin) {
		System.arraycopy(string, srcBegin, dest, destBegin, srcEnd - srcBegin);
	}

	/**
	 * @see String#indexOf(int)
	 */
	public static int indexOf(char[] string, int c) {
		return indexOf(string, c, 0);
	}

	/**
	 * @see String#indexOf(int, int)
	 */
	public static int indexOf(char[] string, int c, int fromIndex) {
		return String.valueOf(string).indexOf(c, fromIndex); // delegate 'int' stuff...
	}

	/**
	 * @see String#indexOf(String)
	 */
	public static int indexOf(char[] string, CharSequence cs) {
		return indexOf(string, cs, 0);
	}

	/**
	 * @see String#indexOf(String)
	 */
	public static int indexOf(char[] s1, char[] s2) {
		return indexOf(s1, s2, 0);
	}

	/**
	 * @see String#indexOf(String, int)
	 */
	public static int indexOf(char[] string, CharSequence cs, int fromIndex) {
		int len1 = string.length;
		int len2 = cs.length();
		if (fromIndex >= len1) {
			return (len2 == 0) ? len1 : -1;
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (len2 == 0) {
			return fromIndex;
		}

		char first = cs.charAt(0);
		int max = len1 - len2;

		for (int i = fromIndex; i <= max; i++) {
			// look for first character
			if (string[i] != first) {
				while ((++i <= max) && (string[i] != first)) {/* NOP */}
			}
			// found first character - now look at the rest of cs
			if (i <= max) {
				int j = i + 1;
				int end = j + len2 - 1;
				for (int k = 1; j < end && string[j] == cs.charAt(k); j++, k++) {/* NOP */}
				if (j == end) {
					return i;  // found the entire string
				}
			}
		}
		return -1;
	}

	/**
	 * @see String#indexOf(String, int)
	 */
	public static int indexOf(char[] s1, char[] s2, int fromIndex) {
		int len1 = s1.length;
		int len2 = s2.length;
		if (fromIndex >= len1) {
			return (len2 == 0) ? len1 : -1;
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (len2 == 0) {
			return fromIndex;
		}

		char first = s2[0];
		int max = len1 - len2;

		for (int i = fromIndex; i <= max; i++) {
			// look for first character
			if (s1[i] != first) {
				while ((++i <= max) && (s1[i] != first)) {/* NOP */}
			}
			// found first character - now look at the rest of s2
			if (i <= max) {
				int j = i + 1;
				int end = j + len2 - 1;
				for (int k = 1; j < end && s1[j] == s2[k]; j++, k++) {/* NOP */}
				if (j == end) {
					return i;  // found the entire string
				}
			}
		}
		return -1;
	}

	/**
	 * @see String#lastIndexOf(int)
	 */
	public static int lastIndexOf(char[] string, int c) {
		return indexOf(string, c, string.length - 1);
	}

	/**
	 * @see String#lastIndexOf(int, int)
	 */
	public static int lastIndexOf(char[] string, int c, int fromIndex) {
		return String.valueOf(string).lastIndexOf(c, fromIndex); // delegate 'int' stuff...
	}

	/**
	 * @see String#lastIndexOf(String)
	 */
	public static int lastIndexOf(char[] s1, char[] s2) {
		return lastIndexOf(s1, s2, s1.length);
	}

	/**
	 * @see String#lastIndexOf(String, int)
	 */
	public static int lastIndexOf(char[] s1, char[] s2, int fromIndex) {
		if (fromIndex < 0) {
			return -1;
		}
		int len1 = s1.length;
		int len2 = s2.length;
		int rightIndex = len1 - len2;
		if (fromIndex > rightIndex) {
			fromIndex = rightIndex;
		}
		// empty string always matches
		if (len2 == 0) {
			return fromIndex;
		}

		int strLastIndex = len2 - 1;
		char strLastChar = s2[strLastIndex];
		int min = len2 - 1;
		int i = min + fromIndex;

		startSearchForLastChar:
		while (true) {
			while ((i >= min) && (s1[i] != strLastChar)) {
				i--;
			}
			if (i < min) {
				return -1;
			}
			int j = i - 1;
			int start = j - (len2 - 1);
			int k = strLastIndex - 1;

			while (j > start) {
				if (s1[j--] != s2[k--]) {
					i--;
					continue startSearchForLastChar;
				}
			}
			return start + 1;
		}
	}

	/**
	 * @see String#matches(String)
	 */
	public static boolean matches(char[] string, char[] regex) {
		return matches(string, String.valueOf(regex)); // delegate regex stuff...
	}

	/**
	 * @see String#matches(String)
	 */
	public static boolean matches(char[] string, String regex) {
		return String.valueOf(string).matches(regex); // delegate regex stuff...
	}

	/**
	 * @see String#offsetByCodePoints(int, int)
	 */
	public static int offsetByCodePoints(char[] string, int index, int codePointOffset) {
		return Character.offsetByCodePoints(string, 0, string.length, index, codePointOffset);
	}

	/**
	 * @see String#regionMatches(boolean, int, String, int, int)
	 */
	public static boolean regionMatches(char[] s1, boolean ignoreCase, int offset1, char[] s2, int offset2, int len) {
		// NB: offset1, offset2, or len might be near -1 >>> 1
		if ((offset1 < 0) || (offset2 < 0) || (offset1 > (long) s1.length - len) || (offset2 > (long) s2.length - len)) {
			return false;
		}
		while (len-- > 0) {
			char c1 = s1[offset1++];
			char c2 = s2[offset2++];
			if (c1 == c2) {
				continue;
			}
			if (ignoreCase) {
				char u1 = Character.toUpperCase(c1);
				char u2 = Character.toUpperCase(c2);
				if (u1 == u2) {
					continue;
				}
				if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
					continue;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * @see String#regionMatches(int, String, int, int)
	 */
	public static boolean regionMatches(char[] s1, int offset1, char[] s2, int offset2, int len) {
		// NB: offset1, offset2, or len might be near -1 >>> 1
		if ((offset2 < 0) || (offset1 < 0) || (offset1 > (long) s1.length - len) || (offset2 > (long) s2.length - len)) {
			return false;
		}
		while (len-- > 0) {
			if (s1[offset1++] != s2[offset2++]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see String#replace(char, char)
	 */
	public static char[] replace(char[] string, char oldChar, char newChar) {
		if (oldChar == newChar) {
			return string;
		}
		int len = string.length;
		int i = -1;
		while (++i < len) {
			if (string[i] == oldChar) {
				break;
			}
		}
		if (i == len) {
			return string;
		}
		char[] result = new char[len];
		for (int j = 0; j < i ; j++) {
			result[j] = string[j];
		}
		for ( ; i < len; i++) {
			char c = string[i];
			result[i] = (c == oldChar) ? newChar : c;
		}
		return result;
	}

	/**
	 * @see String#replace(CharSequence, CharSequence)
	 */
	public static char[] replace(char[] string, CharSequence target, CharSequence replacement) {
		return String.valueOf(string).replace(target, replacement).toCharArray(); // delegate regex stuff...
	}

	/**
	 * @see String#replace(CharSequence, CharSequence)
	 */
	public static char[] replace(char[] string, char[] target, char[] replacement) {
		return replace(string, String.valueOf(target), String.valueOf(replacement)); // delegate regex stuff...
	}

	/**
	 * @see String#replaceAll(String, String)
	 */
	public static char[] replaceAll(char[] string, char[] regex, char[] replacement) {
		return replaceAll(string, String.valueOf(regex), replacement); // delegate regex stuff...
	}

	/**
	 * @see String#replaceAll(String, String)
	 */
	public static char[] replaceAll(char[] string, String regex, char[] replacement) {
		return String.valueOf(string).replaceAll(regex, String.valueOf(replacement)).toCharArray(); // delegate regex stuff...
	}

	/**
	 * @see String#replaceFirst(String, String)
	 */
	public static char[] replaceFirst(char[] string, char[] regex, char[] replacement) {
		return replaceFirst(string, String.valueOf(regex), replacement); // delegate regex stuff...
	}

	/**
	 * @see String#replaceFirst(String, String)
	 */
	public static char[] replaceFirst(char[] string, String regex, char[] replacement) {
		return String.valueOf(string).replaceFirst(regex, String.valueOf(replacement)).toCharArray(); // delegate regex stuff...
	}

	/**
	 * @see String#split(String)
	 */
	public static char[][] split(char[] string, char[] regex) {
		return split(string, regex, 0);
	}

	/**
	 * @see String#split(String)
	 */
	public static char[][] split(char[] string, String regex) {
		return split(string, regex, 0);
	}

	/**
	 * @see String#split(String, int)
	 */
	public static char[][] split(char[] string, char[] regex, int limit) {
		return split(string, String.valueOf(regex), limit); // delegate regex stuff...
	}

	/**
	 * @see String#split(String, int)
	 */
	public static char[][] split(char[] string, String regex, int limit) {
		String[] strings = String.valueOf(string).split(regex, limit); // delegate regex stuff...
		char[][] result = new char[strings.length][];
		for (int i = result.length; i-- > 0;) {
			result[i] = strings[i].toCharArray();
		}
		return result;
	}

	/**
	 * @see String#startsWith(String)
	 */
	public static boolean startsWith(char[] string, char[] prefix) {
		return startsWith(string, prefix, 0);
	}

	/**
	 * @see String#startsWith(String, int)
	 */
	public static boolean startsWith(char[] string, char[] prefix, int offset) {
		int len = prefix.length;
		// NB: offset might be near -1 >>> 1
		if ((offset < 0) || (offset > string.length - len)) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			if (prefix[i] != string[offset++]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see String#subSequence(int, int)
	 */
	public static char[] subSequence(char[] string, int beginIndex, int endIndex) {
		return substring(string, beginIndex, endIndex);
	}

	/**
	 * @see String#substring(int)
	 */
	public static char[] substring(char[] string, int beginIndex) {
		return substring(string, beginIndex, string.length);
	}

	/**
	 * @see String#substring(int, int)
	 */
	public static char[] substring(char[] string, int beginIndex, int endIndex) {
		return ArrayTools.subArray(string, beginIndex, endIndex);
	}

	/**
	 * @see String#toLowerCase()
	 */
	public static char[] toLowerCase(char[] string) {
		return toLowerCase(string, Locale.getDefault());
	}

	/**
	 * @see String#toLowerCase(Locale)
	 */
	public static char[] toLowerCase(char[] string, Locale locale) {
		return String.valueOf(string).toLowerCase(locale).toCharArray(); // delegate case stuff...
	}

	/**
	 * @see String#toUpperCase()
	 */
	public static char[] toUpperCase(char[] string) {
		return toLowerCase(string, Locale.getDefault());
	}

	/**
	 * @see String#toUpperCase(Locale)
	 */
	public static char[] toUpperCase(char[] string, Locale locale) {
		return String.valueOf(string).toUpperCase(locale).toCharArray(); // delegate case stuff...
	}

	/**
	 * @see String#trim()
	 */
	public static char[] trim(char[] string) {
		int end = string.length;
		int start = 0;
		while ((start < end) && (string[start] <= ' ')) {
			start++;
		}
		while ((start < end) && (string[end - 1] <= ' ')) {
			end--;
		}
		return ((start > 0) || (end < string.length)) ? substring(string, start, end) : string;
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CharArrayTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
