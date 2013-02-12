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
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
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
	public static String concatenate(char[]... strings) {
		int stringLength = 0;
		for (char[] string : strings) {
			stringLength += string.length;
		}
		StringBuilder sb = new StringBuilder(stringLength);
		for (char[] string : strings) {
			sb.append(string);
		}
		return sb.toString();
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static String concatenate(Iterable<char[]> strings) {
		return concatenate(strings.iterator());
	}

	/**
	 * Return a concatenation of the specified strings.
	 */
	public static String concatenate(Iterator<char[]> strings) {
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next());
		}
		return sb.toString();
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
		extends TransformerAdapter<char[], char[]>
	{
		private final char delimiter;
		public CharDelimiter(char delimiter) {
			super();
			this.delimiter = delimiter;
		}
		@Override
		public char[] transform(char[] string) {
			return delimit(string, this.delimiter);
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
		extends TransformerAdapter<char[], char[]>
	{
		private final char[] delimiter;
		private final int delimiterLength;
		public CharArrayDelimiter(char[] delimiter) {
			super();
			this.delimiter = delimiter;
			this.delimiterLength = delimiter.length;
		}
		@Override
		public char[] transform(char[] string) {
			return delimit(string, this.delimiter, this.delimiterLength);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return capitalize(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return uncapitalize(string);
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
	public static final Predicate<char[]> NON_BLANK_FILTER = new NonBlankFilter();

	/* CU private */ static class NonBlankFilter
		extends Predicate.Adapter<char[]>
		implements Serializable
	{
		@Override
		public boolean evaluate(char[] string) {
			return isNotBlank(string);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return NON_BLANK_FILTER;
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToJavaStringLiteral(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToJavaStringLiteralContent(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToXmlAttributeValue(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToDoubleQuotedXmlAttributeValue(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToDoubleQuotedXmlAttributeValueContent(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToSingleQuotedXmlAttributeValue(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToSingleQuotedXmlAttributeValueContent(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToXmlElementText(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToXmlElementCDATA(string);
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
		extends TransformerAdapter<char[], char[]>
		implements Serializable
	{
		@Override
		public char[] transform(char[] string) {
			return convertToXmlElementCDATAContent(string);
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
	private CharArrayTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
