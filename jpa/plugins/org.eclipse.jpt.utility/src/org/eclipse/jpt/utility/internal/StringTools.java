/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Convenience methods related to the java.lang.String class.
 */
public final class StringTools {

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");

	/** double quote */
	public static final char QUOTE = '"';



	// ********** padding/truncating **********

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#pad(int)
	 */
	public static String pad(String string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, Writer)
	 */
	public static void padOn(String string, int length, Writer writer) {
		padOn(string, length, ' ', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, StringBuffer)
	 */
	public static void padOn(String string, int length, StringBuffer sb) {
		padOn(string, length, ' ', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#pad(int, char)
	 */
	public static String pad(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			return string;
		}
		return padInternal(string, length, c);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, Writer)
	 */
	public static void padOn(String string, int length, char c, Writer writer) {
		padOn(string.toCharArray(), length, c, writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, StringBuffer)
	 */
	public static void padOn(String string, int length, char c, StringBuffer sb) {
		padOn(string.toCharArray(), length, c, sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#pad(int)
	 */
	public static char[] pad(char[] string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, writer)
	 */
	public static void padOn(char[] string, int length, Writer writer) {
		padOn(string, length, ' ', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, StringBuffer)
	 */
	public static void padOn(char[] string, int length, StringBuffer sb) {
		padOn(string, length, ' ', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#pad(int, char)
	 */
	public static char[] pad(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			return string;
		}
		return padInternal(string, length, c);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, Writer)
	 */
	public static void padOn(char[] string, int length, char c, Writer writer) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			padOnInternal(string, length, c, writer);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, StringBuffer)
	 */
	public static void padOn(char[] string, int length, char c, StringBuffer sb) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			padOnInternal(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncate(int)
	 */
	public static String padOrTruncate(String string, int length) {
		return padOrTruncate(string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, Writer)
	 */
	public static void padOrTruncateOn(String string, int length, Writer writer) {
		padOrTruncateOn(string, length, ' ', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, StringBuffer)
	 */
	public static void padOrTruncateOn(String string, int length, StringBuffer sb) {
		padOrTruncateOn(string, length, ' ', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncate(int, char)
	 */
	public static String padOrTruncate(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return string.substring(0, length);
		}
		return padInternal(string, length, c);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, Writer)
	 */
	public static void padOrTruncateOn(String string, int length, char c, Writer writer) {
		padOrTruncateOn(string.toCharArray(), length, c, writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, StringBuffer)
	 */
	public static void padOrTruncateOn(String string, int length, char c, StringBuffer sb) {
		padOrTruncateOn(string.toCharArray(), length, c, sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncate(int)
	 */
	public static char[] padOrTruncate(char[] string, int length) {
		return padOrTruncate(string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, Writer)
	 */
	public static void padOrTruncateOn(char[] string, int length, Writer writer) {
		padOrTruncateOn(string, length, ' ', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, StringBuffer)
	 */
	public static void padOrTruncate(char[] string, int length, StringBuffer sb) {
		padOrTruncateOn(string, length, ' ', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncate(int, char)
	 */
	public static char[] padOrTruncate(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			char[] result = new char[length];
			System.arraycopy(string, 0, result, 0, length);
			return result;
		}
		return padInternal(string, length, c);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, Writer)
	 */
	public static void padOrTruncateOn(char[] string, int length, char c, Writer writer) {
		int stringLength = string.length;
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else if (stringLength > length) {
			writeStringOn(string, 0, length, writer);
		} else {
			padOnInternal(string, length, c, writer);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, StringBuffer)
	 */
	public static void padOrTruncateOn(char[] string, int length, char c, StringBuffer sb) {
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);
		} else {
			padOnInternal(string, length, c, sb);
		}
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static String padInternal(String string, int length, char c) {
		return new String(padInternal(string.toCharArray(), length, c));
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static char[] padInternal(char[] string, int length, char c) {
		char[] result = new char[length];
		int stringLength = string.length;
		System.arraycopy(string, 0, result, 0, stringLength);
		Arrays.fill(result, stringLength, length, c);
		return result;
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static void padOnInternal(char[] string, int length, char c, Writer writer) {
		writeStringOn(string, writer);
		writeStringOn(CollectionTools.fill(new char[length - string.length], c), writer);
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static void padOnInternal(char[] string, int length, char c, StringBuffer sb) {
		sb.append(string);
		sb.append(CollectionTools.fill(new char[length - string.length], c));
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPad(int)
	 */
	public static String zeroPad(String string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, Writer)
	 */
	public static void zeroPadOn(String string, int length, Writer writer) {
		frontPadOn(string, length, '0', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, StringBuffer)
	 */
	public static void zeroPadOn(String string, int length, StringBuffer sb) {
		frontPadOn(string, length, '0', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPad(int, char)
	 */
	public static String frontPad(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			return string;
		}
		return frontPadInternal(string, length, c);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, Writer)
	 */
	public static void frontPadOn(String string, int length, char c, Writer writer) {
		frontPadOn(string.toCharArray(), length, c, writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, StringBuffer)
	 */
	public static void frontPadOn(String string, int length, char c, StringBuffer sb) {
		frontPadOn(string.toCharArray(), length, c, sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPad(int)
	 */
	public static char[] zeroPad(char[] string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, Writer)
	 */
	public static void zeroPadOn(char[] string, int length, Writer writer) {
		frontPadOn(string, length, '0', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, StringBuffer)
	 */
	public static void zeroPadOn(char[] string, int length, StringBuffer sb) {
		frontPadOn(string, length, '0', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPad(int, char)
	 */
	public static char[] frontPad(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			return string;
		}
		return frontPadInternal(string, length, c);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, Writer)
	 */
	public static void frontPadOn(char[] string, int length, char c, Writer writer) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			frontPadOnInternal(string, length, c, writer);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, StringBuffer)
	 */
	public static void frontPadOn(char[] string, int length, char c, StringBuffer sb) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPadOnInternal(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncate(int)
	 */
	public static String zeroPadOrTruncate(String string, int length) {
		return frontPadOrTruncate(string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, Writer)
	 */
	public static void zeroPadOrTruncateOn(String string, int length, Writer writer) {
		frontPadOrTruncateOn(string, length, '0', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, StringBuffer)
	 */
	public static void zeroPadOrTruncateOn(String string, int length, StringBuffer sb) {
		frontPadOrTruncateOn(string, length, '0', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOrTruncate(int, char)
	 */
	public static String frontPadOrTruncate(String string, int length, char c) {
		int stringLength = string.length();
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			return string.substring(stringLength - length);
		}
		return frontPadInternal(string, length, c);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, Writer)
	 */
	public static void frontPadOrTruncateOn(String string, int length, char c, Writer writer) {
		frontPadOrTruncateOn(string.toCharArray(), length, c, writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, StringBuffer)
	 */
	public static void frontPadOrTruncateOn(String string, int length, char c, StringBuffer sb) {
		frontPadOrTruncateOn(string.toCharArray(), length, c, sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncate(int)
	 */
	public static char[] zeroPadOrTruncate(char[] string, int length) {
		return frontPadOrTruncate(string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, Writer)
	 */
	public static void zeroPadOrTruncateOn(char[] string, int length, Writer writer) {
		frontPadOrTruncateOn(string, length, '0', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, StringBuffer)
	 */
	public static void zeroPadOrTruncateOn(char[] string, int length, StringBuffer sb) {
		frontPadOrTruncateOn(string, length, '0', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the 
	 * specified character at the front.
	 * String#frontPadOrTruncate(int, char)
	 */
	public static char[] frontPadOrTruncate(char[] string, int length, char c) {
		int stringLength = string.length;
		if (stringLength == length) {
			return string;
		}
		if (stringLength > length) {
			char[] result = new char[length];
			System.arraycopy(string, stringLength - length, result, 0, length);
			return result;
		}
		return frontPadInternal(string, length, c);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the 
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, Writer)
	 */
	public static void frontPadOrTruncateOn(char[] string, int length, char c, Writer writer) {
		int stringLength = string.length;
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else if (stringLength > length) {
			writeStringOn(string, stringLength - length, length, writer);
		} else {
			frontPadOnInternal(string, length, c, writer);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the 
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, StringBuffer)
	 */
	public static void frontPadOrTruncateOn(char[] string, int length, char c, StringBuffer sb) {
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, stringLength - length, length);
		} else {
			frontPadOnInternal(string, length, c, sb);
		}
	}

	/**
	 * Front-pad the specified string without validating the parms.
	 */
	private static String frontPadInternal(String string, int length, char c) {
		return new String(frontPadInternal(string.toCharArray(), length, c));
	}

	/**
	 * Zero-pad the specified string without validating the parms.
	 */
	private static char[] frontPadInternal(char[] string, int length, char c) {
		char[] result = new char[length];
		int stringLength = string.length;
		int padLength = length - stringLength;
		System.arraycopy(string, 0, result, padLength, stringLength);
		Arrays.fill(result, 0, padLength, c);
		return result;
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOnInternal(char[] string, int length, char c, Writer writer) {
		writeStringOn(CollectionTools.fill(new char[length - string.length], c), writer);
		writeStringOn(string, writer);
	}

	/**
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOnInternal(char[] string, int length, char c, StringBuffer sb) {
		sb.append(CollectionTools.fill(new char[length - string.length], c));
		sb.append(string);
	}


	// ********** wrapping/quoting **********

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static String quote(String string) {
		return wrap(string, QUOTE);
	}

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(String string, Writer writer) {
		wrapOn(string, QUOTE, writer);
	}

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(String string, StringBuffer sb) {
		wrapOn(string, QUOTE, sb);
	}

	/**
	 * Wrap each of the specified strings with double quotes.
	 */
	public static Iterator<String> quote(Iterator<String> strings) {
		return new TransformationIterator<String, String>(strings) {
			@Override
			protected String transform(String string) {
				return StringTools.quote(string);
			}
		};
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static String wrap(String string, char wrap) {
		return new String(wrap(string.toCharArray(), wrap));
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, char wrap, Writer writer) {
		wrapOn(string.toCharArray(), wrap, writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, char wrap, StringBuffer sb) {
		wrapOn(string.toCharArray(), wrap, sb);
	}

	/**
	 * Wrap each of the specified strings with the specified wrap; i.e. put a
	 * copy of the wrap at the front and back of the resulting string.
	 */
	public static Iterator<String> wrap(Iterator<String> strings, final char wrap) {
		return new TransformationIterator<String, String>(strings) {
			@Override
			protected String transform(String string) {
				return StringTools.wrap(string, wrap);
			}
		};
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static String wrap(String string, String wrap) {
		return new String(wrap(string.toCharArray(), wrap.toCharArray()));
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, String wrap, Writer writer) {
		wrapOn(string.toCharArray(), wrap.toCharArray(), writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, String wrap, StringBuffer sb) {
		wrapOn(string.toCharArray(), wrap.toCharArray(), sb);
	}

	/**
	 * Wrap each of the specified strings with the specified wrap; i.e. put a
	 * copy of the wrap at the front and back of the resulting string.
	 */
	public static Iterator<String> wrap(Iterator<String> strings, final String wrap) {
		return new TransformationIterator<String, String>(strings) {
			@Override
			protected String transform(String string) {
				return StringTools.wrap(string, wrap);
			}
		};
	}

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static char[] quote(char[] string) {
		return wrap(string, QUOTE);
	}

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(char[] string, Writer writer) {
		wrapOn(string, QUOTE, writer);
	}

	/**
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(char[] string, StringBuffer sb) {
		wrapOn(string, QUOTE, sb);
	}

	/**
	 * Wrap each of the specified strings with double quotes.
	 */
	public static Iterator<char[]> quoteCharArrays(Iterator<char[]> strings) {
		return new TransformationIterator<char[], char[]>(strings) {
			@Override
			protected char[] transform(char[] string) {
				return StringTools.quote(string);
			}
		};
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static char[] wrap(char[] string, char wrap) {
		int len = string.length;
		char[] result = new char[len+2];
		result[0] = wrap;
		System.arraycopy(string, 0, result, 1, len);
		result[len+1] = wrap;
		return result;
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char wrap, Writer writer) {
		writeCharOn(wrap, writer);
		writeStringOn(string, writer);
		writeCharOn(wrap, writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char wrap, StringBuffer sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
	}

	/**
	 * Wrap each of the specified strings with the specified wrap; i.e. put a
	 * copy of the wrap at the front and back of the resulting string.
	 */
	public static Iterator<char[]> wrapCharArrays(Iterator<char[]> strings, final char wrap) {
		return new TransformationIterator<char[], char[]>(strings) {
			@Override
			protected char[] transform(char[] string) {
				return StringTools.wrap(string, wrap);
			}
		};
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static char[] wrap(char[] string, char[] wrap) {
		int stringLength = string.length;
		int wrapLength = wrap.length;
		char[] result = new char[stringLength+(2*wrapLength)];
		System.arraycopy(wrap, 0, result, 0, wrapLength);
		System.arraycopy(string, 0, result, wrapLength, stringLength);
		System.arraycopy(wrap, 0, result, stringLength+wrapLength, wrapLength);
		return result;
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char[] wrap, Writer writer) {
		writeStringOn(wrap, writer);
		writeStringOn(string, writer);
		writeStringOn(wrap, writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char[] wrap, StringBuffer sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
	}

	/**
	 * Wrap each of the specified strings with the specified wrap; i.e. put a
	 * copy of the wrap at the front and back of the resulting string.
	 */
	public static Iterator<char[]> wrapCharArrays(Iterator<char[]> strings, final char[] wrap) {
		return new TransformationIterator<char[], char[]>(strings) {
			@Override
			protected char[] transform(char[] string) {
				return StringTools.wrap(string, wrap);
			}
		};
	}


	// ********** removing characters **********

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and return the result.
	 * String#removeFirstOccurrence(char)
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
		int last = string.length() - 1;
		if (index == last) {
			// character found at the end of string
			return string.substring(0, last);
		}
		// character found somewhere in the middle of the string
		return string.substring(0, index).concat(string.substring(index + 1));
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, Writer)
	 */
	public static void removeFirstOccurrenceOn(String string, char c, Writer writer) {
		removeFirstOccurrenceOn(string.toCharArray(), c, writer);
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, StringBuffer)
	 */
	public static void removeFirstOccurrenceOn(String string, char c, StringBuffer sb) {
		removeFirstOccurrenceOn(string.toCharArray(), c, sb);
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and return the result.
	 * String#removeFirstOccurrence(char)
	 */
	public static char[] removeFirstOccurrence(char[] string, char c) {
		int index = CollectionTools.indexOf(string, c);
		if (index == -1) {
			// character not found
			return string;
		}

		int len = string.length - 1;
		char[] result = new char[len];
		if (index == 0) {
			// character found at the front of string
			System.arraycopy(string, 1, result, 0, len);
		} else if (index == len) {
			// character found at the end of string
			System.arraycopy(string, 0, result, 0, len);
		} else {
			// character found somewhere in the middle of the string
			System.arraycopy(string, 0, result, 0, index);
			System.arraycopy(string, index + 1, result, index, len - index);
		}
		return result;
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, Writer)
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, Writer writer) {
		int index = CollectionTools.indexOf(string, c);
		if (index == -1) {
			// character not found
			writeStringOn(string, writer);
			return;
		}

		int len = string.length - 1;
		if (index == 0) {
			// character found at the front of string
			writeStringOn(string, 1, len, writer);
		} else if (index == len) {
			// character found at the end of string
			writeStringOn(string, 0, len, writer);
		} else {
			// character found somewhere in the middle of the string
			writeStringOn(string, 0, index, writer);
			writeStringOn(string, index + 1, len - index, writer);
		}
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, StringBuffer)
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, StringBuffer sb) {
		int index = CollectionTools.indexOf(string, c);
		if (index == -1) {
			// character not found
			sb.append(string);
			return;
		}

		int len = string.length - 1;
		if (index == 0) {
			// character found at the front of string
			sb.append(string, 1, len);
		} else if (index == len) {
			// character found at the end of string
			sb.append(string, 0, len);
		} else {
			// character found somewhere in the middle of the string
			sb.append(string, 0, index);
			sb.append(string, index + 1, len - index);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and return the result.
	 * String#removeAllOccurrences(char)
	 */
	public static String removeAllOccurrences(String string, char c) {
		return new String(removeAllOccurrences(string.toCharArray(), c));
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * String#removeAllOccurrencesOn(char, Writer)
	 */
	public static void removeAllOccurrencesOn(String string, char c, Writer writer) {
		removeAllOccurrencesOn(string.toCharArray(), c, writer);
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * String#removeAllOccurrencesOn(char, StringBuffer)
	 */
	public static void removeAllOccurrencesOn(String string, char c, StringBuffer sb) {
		removeAllOccurrencesOn(string.toCharArray(), c, sb);
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and return the result.
	 * String#removeAllOccurrences(char)
	 */
	public static char[] removeAllOccurrences(char[] string, char c) {
		StringBuffer sb = new StringBuffer(string.length);
		removeAllOccurrencesOn(string, c, sb);
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the
	 * specified writer.
	 * String#removeAllOccurrencesOn(char, Writer)
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, Writer writer) {
		removeAllOccurrencesOnInternal(string, c, writer);
	}

	private static void removeAllOccurrencesOnInternal(char[] string, char c, Writer writer) {
		for (char d : string) {
			if (d != c) {
				writeCharOn(d, writer);
			}
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and append the result to the
	 * specified string buffer.
	 * String#removeAllOccurrencesOn(char, StringBuffer)
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, StringBuffer sb) {
		for (char d : string) {
			if (d != c) {
				sb.append(d);
			}
		}
	}

	/**
	 * Remove all the spaces from the specified string and return the result.
	 * String#removeAllSpaces()
	 */
	public static String removeAllSpaces(String string) {
		return removeAllOccurrences(string, ' ');
	}


	// ********** common prefix **********

	/**
	 * Return the length of the common prefix shared by the specified strings.
	 * String#commonPrefixLength(String)
	 */
	public static int commonPrefixLength(String s1, String s2) {
		return commonPrefixLength(s1.toCharArray(), s2.toCharArray());
	}

	/**
	 * Return the length of the common prefix shared by the specified strings.
	 */
	public static int commonPrefixLength(char[] s1, char[] s2) {
		return commonPrefixLengthInternal(s1, s2, Math.min(s1.length, s2.length));
	}

	/**
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum.
	 * String#commonPrefixLength(String, int)
	 */
	public static int commonPrefixLength(String s1, String s2, int max) {
		return commonPrefixLength(s1.toCharArray(), s2.toCharArray(), max);
	}

	/**
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum.
	 */
	public static int commonPrefixLength(char[] s1, char[] s2, int max) {
		return commonPrefixLengthInternal(s1, s2, Math.min(max, Math.min(s1.length, s2.length)));
	}

	/**
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum. Assume the specified
	 * maximum is less than the lengths of the specified strings.
	 */
	private static int commonPrefixLengthInternal(char[] s1, char[] s2, int max) {
		for (int i = 0; i < max; i++) {
			if (s1[i] != s2[i]) {
				return i;
			}
		}
		return max;	// all the characters up to 'max' are the same
	}


	// ********** capitalization **********

	/**
	 * no zero-length check or lower case check
	 */
	private static char[] capitalizeInternal(char[] string) {
		string[0] = Character.toUpperCase(string[0]);
		return string;
	}

	/**
	 * Modify and return the specified string with
	 * its first letter capitalized.
	 */
	public static char[] capitalize(char[] string) {
		if ((string.length == 0) || Character.isUpperCase(string[0])) {
			return string;
		}
		return capitalizeInternal(string);
	}

	/**
	 * Return the specified string with its first letter capitalized.
	 * String#capitalize()
	 */
	public static String capitalize(String string) {
		if ((string.length() == 0) || Character.isUpperCase(string.charAt(0))) {
			return string;
		}
		return new String(capitalizeInternal(string.toCharArray()));
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalizeOnInternal(char[] string, StringBuffer sb) {
		sb.append(Character.toUpperCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter capitalized.
	 */
	public static void capitalizeOn(char[] string, StringBuffer sb) {
		if (string.length == 0) {
			return;
		}
		if (Character.isUpperCase(string[0])) {
			sb.append(string);
		} else {
			capitalizeOnInternal(string, sb);
		}
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter capitalized.
	 * String#capitalizeOn(StringBuffer)
	 */
	public static void capitalizeOn(String string, StringBuffer sb) {
		if (string.length() == 0) {
			return;
		}
		if (Character.isUpperCase(string.charAt(0))) {
			sb.append(string);
		} else {
			capitalizeOnInternal(string.toCharArray(), sb);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void capitalizeOnInternal(char[] string, Writer writer) {
		writeCharOn(Character.toUpperCase(string[0]), writer);
		writeStringOn(string, 1, string.length - 1, writer);
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter capitalized.
	 */
	public static void capitalizeOn(char[] string, Writer writer) {
		if (string.length == 0) {
			return;
		}
		if (Character.isUpperCase(string[0])) {
			writeStringOn(string, writer);
		} else {
			capitalizeOnInternal(string, writer);
		}
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter capitalized.
	 * String#capitalizeOn(Writer)
	 */
	public static void capitalizeOn(String string, Writer writer) {
		if (string.length() == 0) {
			return;
		}
		if (Character.isUpperCase(string.charAt(0))) {
			writeStringOn(string, writer);
		} else {
			capitalizeOnInternal(string.toCharArray(), writer);
		}
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static char[] uncapitalizeInternal(char[] string) {
		string[0] = Character.toLowerCase(string[0]);
		return string;
	}

	private static boolean stringNeedNotBeUncapitalized(char[] string) {
		if (string.length == 0) {
			return true;
		}
		if (Character.isLowerCase(string[0])) {
			return true;
		}
		// if both the first and second characters are capitalized,
		// return the string unchanged
		if ((string.length > 1)
				&& Character.isUpperCase(string[1])
				&& Character.isUpperCase(string[0])){
			return true;
		}
		return false;
	}

	/**
	 * Modify and return the specified string with its
	 * first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static char[] uncapitalize(char[] string) {
		if (stringNeedNotBeUncapitalized(string)) {
			return string;
		}
		return uncapitalizeInternal(string);
	}

	private static boolean stringNeedNotBeUncapitalized(String string) {
		if (string.length() == 0) {
			return true;
		}
		if (Character.isLowerCase(string.charAt(0))) {
			return true;
		}
		// if both the first and second characters are capitalized,
		// return the string unchanged
		if ((string.length() > 1)
				&& Character.isUpperCase(string.charAt(1))
				&& Character.isUpperCase(string.charAt(0))){
			return true;
		}
		return false;
	}

	/**
	 * Return the specified string with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 * String#uncapitalize()
	 */
	public static String uncapitalize(String string) {
		if (stringNeedNotBeUncapitalized(string)) {
			return string;
		}
		return new String(uncapitalizeInternal(string.toCharArray()));
	}

	/**
	 * no zero-length check or lower case check
	 */
	private static void uncapitalizeOnInternal(char[] string, StringBuffer sb) {
		sb.append(Character.toLowerCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalizeOn(char[] string, StringBuffer sb) {
		if (stringNeedNotBeUncapitalized(string)) {
			sb.append(string);
		} else {
			uncapitalizeOnInternal(string, sb);
		}
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 * String#uncapitalizeOn(StringBuffer)
	 */
	public static void uncapitalizeOn(String string, StringBuffer sb) {
		if (stringNeedNotBeUncapitalized(string)) {
			sb.append(string);
		} else {
			uncapitalizeOnInternal(string.toCharArray(), sb);
		}
	}

	/**
	 * no zero-length check or upper case check
	 */
	private static void uncapitalizeOnInternal(char[] string, Writer writer) {
		writeCharOn(Character.toLowerCase(string[0]), writer);
		writeStringOn(string, 1, string.length - 1, writer);
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalizeOn(char[] string, Writer writer) {
		if (stringNeedNotBeUncapitalized(string)) {
			writeStringOn(string, writer);
		} else {
			uncapitalizeOnInternal(string, writer);
		}
	}

	/**
	 * Append the specified string to the specified string buffer
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 * String#uncapitalizeOn(Writer)
	 */
	public static void uncapitalizeOn(String string, Writer writer) {
		if (stringNeedNotBeUncapitalized(string)) {
			writeStringOn(string, writer);
		} else {
			uncapitalizeOnInternal(string.toCharArray(), writer);
		}
	}


	// ********** #toString() helper methods **********

	/**
	 * Build a "standard" #toString() result for the specified object
	 * and additional information:
	 * 	ClassName[00F3EE42] (add'l info)
	 */
	public static String buildToStringFor(Object o, Object additionalInfo) {
		StringBuffer sb = new StringBuffer();
		buildSimpleToStringOn(o, sb);
		sb.append(" (");
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Build a "standard" simple #toString() result for the specified object:
	 * 	ClassName[00F3EE42]
	 */
	public static String buildToStringFor(Object o) {
		StringBuffer sb = new StringBuffer();
		buildSimpleToStringOn(o, sb);
		return sb.toString();
	}

	/**
	 * Append a "standard" simple #toString() for the specified object to
	 * the specified string buffer:
	 * 	ClassName[00F3EE42]
	 */
	public static void buildSimpleToStringOn(Object o, StringBuffer sb) {
		sb.append(ClassTools.toStringClassNameForObject(o));
		sb.append('[');
		// use System#identityHashCode(Object), since Object#hashCode() may be overridden
		sb.append(zeroPad(Integer.toHexString(System.identityHashCode(o)).toUpperCase(), 8));
		sb.append(']');
	}


	// ********** queries **********

	/**
	 * Return whether the specified string is null, empty, or contains
	 * only whitespace characters.
	 */
	public static boolean stringIsEmpty(String string) {
		if ((string == null) || (string.length() == 0)) {
			return true;
		}
		return stringIsEmptyInternal(string.toCharArray());
	}

	/**
	 * Return whether the specified string is null, empty, or contains
	 * only whitespace characters.
	 */
	public static boolean stringIsEmpty(char[] string) {
		if ((string == null) || (string.length == 0)) {
			return true;
		}
		return stringIsEmptyInternal(string);
	}

	private static boolean stringIsEmptyInternal(char[] s) {
		for (int i = s.length; i-- > 0; ) {
			if ( ! Character.isWhitespace(s[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified strings are equal, ignoring case.
	 * Check for nulls.
	 */
	public static boolean stringsAreEqualIgnoreCase(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) {
			return true;  // both are null
		}
		if ((s1 == null) || (s2 == null)) {
			return false;  // one is null but the other is not
		}
		return s1.equalsIgnoreCase(s2);
	}
	
	/**
	 * Return whether the specified strings are equal, ignoring case.
	 * Check for nulls.
	 */
	public static boolean stringsAreEqualIgnoreCase(char[] s1, char[] s2) {
		if ((s1 == null) && (s2 == null)) {
			return true;  // both are null
		}
		if ((s1 == null) || (s2 == null)) {
			return false;  // one is null but the other is not
		}
		if (s1.length != s2.length) {
			return false;
		}
		for (int i = s1.length; i-- > 0; ) {
			if ( ! charactersAreEqualIgnoreCase(s1[i], s2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified string starts with the specified prefix,
	 * ignoring case.
	 */
	public static boolean stringStartsWithIgnoreCase(char[] string, char[] prefix) {
		if (string.length < prefix.length) {
			return false;
		}
		for (int i = prefix.length; i-- > 0; ) {
			if ( ! charactersAreEqualIgnoreCase(string[i], prefix[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Return whether the specified string starts with the specified prefix,
	 * ignoring case.
	 */
	public static boolean stringStartsWithIgnoreCase(String string, String prefix) {
		return string.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	/**
	 * Return whether the specified characters are are equal, ignoring case.
	 * @see java.lang.String#regionMatches(boolean, int, String, int, int)
	 */
	public static boolean charactersAreEqualIgnoreCase(char c1, char c2) {
		//  something about the Georgian alphabet requires us to check lower case also
		return (c1 == c2)
				|| (Character.toUpperCase(c1) == Character.toUpperCase(c2))
				|| (Character.toLowerCase(c1) == Character.toLowerCase(c2));
	}

	// ********** conversions **********

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static String convertCamelCaseToAllCaps(String camelCaseString) {
		return new String(convertCamelCaseToAllCaps(camelCaseString.toCharArray()));
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static char[] convertCamelCaseToAllCaps(char[] camelCaseString) {
		int len = camelCaseString.length;
		if (len == 0) {
			return camelCaseString;
		}
		StringBuffer sb = new StringBuffer(len * 2);
		convertCamelCaseToAllCapsOnInternal(camelCaseString, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, StringBuffer sb) {
		convertCamelCaseToAllCapsOn(camelCaseString.toCharArray(), sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, StringBuffer sb) {
		int len = camelCaseString.length;
		if (len != 0) {
			convertCamelCaseToAllCapsOnInternal(camelCaseString, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOnInternal(char[] camelCaseString, int len, StringBuffer sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak(prev, c, next)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(c));
			prev = c;
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, Writer writer) {
		convertCamelCaseToAllCapsOn(camelCaseString.toCharArray(), writer);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, Writer writer) {
		int len = camelCaseString.length;
		if (len != 0) {
			convertCamelCaseToAllCapsOnInternal(camelCaseString, len, writer);
		}
	}

	private static void convertCamelCaseToAllCapsOnInternal(char[] camelCaseString, int len, Writer writer) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak(prev, c, next)) {
				writeCharOn('_', writer);
			}
			writeCharOn(Character.toUpperCase(c), writer);
			prev = c;
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static String convertCamelCaseToAllCaps(String camelCaseString, int maxLength) {
		return new String(convertCamelCaseToAllCaps(camelCaseString.toCharArray(), maxLength));
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static char[] convertCamelCaseToAllCaps(char[] camelCaseString, int maxLength) {
		int len = camelCaseString.length;
		if ((len == 0) || (maxLength == 0)) {
			return camelCaseString;
		}
		StringBuffer sb = new StringBuffer(maxLength);
		convertCamelCaseToAllCapsOnInternal(camelCaseString, maxLength, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, int maxLength, StringBuffer sb) {
		convertCamelCaseToAllCapsOn(camelCaseString.toCharArray(), maxLength, sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, int maxLength, StringBuffer sb) {
		int len = camelCaseString.length;
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOnInternal(camelCaseString, maxLength, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOnInternal(char[] camelCaseString, int maxLength, int len, StringBuffer sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak(prev, c, next)) {
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
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, int maxLength, Writer writer) {
		convertCamelCaseToAllCapsOn(camelCaseString.toCharArray(), maxLength, writer);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, int maxLength, Writer writer) {
		int len = camelCaseString.length;
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOnInternal(camelCaseString, maxLength, len, writer);
		}
	}

	private static void convertCamelCaseToAllCapsOnInternal(char[] camelCaseString, int maxLength, int len, Writer writer) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		int writerLength = 0;
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak(prev, c, next)) {
				writeCharOn('_', writer);
				if (++writerLength == maxLength) {
					return;
				}
			}
			writeCharOn(Character.toUpperCase(c), writer);
			if (++writerLength == maxLength) {
				return;
			}
			prev = c;
		}
	}

	/**
	 * Return whether the specified series of characters occur at
	 * a "camel case" work break:
	 *     "*aa" -> false
	 *     "*AA" -> false
	 *     "*Aa" -> false
	 *     "AaA" -> false
	 *     "AAA" -> false
	 *     "aa*" -> false
	 *     "AaA" -> false
	 *     "aAa" -> true
	 *     "AA*" -> false
	 *     "AAa" -> true
	 * where '*' == any char
	 */
	private static boolean camelCaseWordBreak(char prev, char c, char next) {
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

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "LargeProject"
	 * Capitalize the first letter.
	 */
	public static String convertUnderscoresToCamelCase(String underscoreString) {
		return new String(convertUnderscoresToCamelCase(underscoreString.toCharArray()));
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "LargeProject"
	 * Capitalize the first letter.
	 */
	public static char[] convertUnderscoresToCamelCase(char[] underscoreString) {
		return convertUnderscoresToCamelCase(underscoreString, true);
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static String convertUnderscoresToCamelCase(String underscoreString, boolean capitalizeFirstLetter) {
		return new String(convertUnderscoresToCamelCase(underscoreString.toCharArray(), capitalizeFirstLetter));
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static char[] convertUnderscoresToCamelCase(char[] underscoreString, boolean capitalizeFirstLetter) {
		int len = underscoreString.length;
		if (len == 0) {
			return underscoreString;
		}
		StringBuffer sb = new StringBuffer(len);
		convertUnderscoresToCamelCaseOnInternal(underscoreString, capitalizeFirstLetter, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(String underscoreString, boolean capitalizeFirstLetter, StringBuffer sb) {
		convertUnderscoresToCamelCaseOn(underscoreString.toCharArray(), capitalizeFirstLetter, sb);
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(char[] underscoreString, boolean capitalizeFirstLetter, StringBuffer sb) {
		int len = underscoreString.length;
		if (len != 0) {
			convertUnderscoresToCamelCaseOnInternal(underscoreString, capitalizeFirstLetter, len, sb);
		}
	}

	private static void convertUnderscoresToCamelCaseOnInternal(char[] underscoreString, boolean capitalizeFirstLetter, int len, StringBuffer sb) {
		char prev = 0;
		char c = 0;
		boolean first = true;
		for (int i = 0; i < len; i++) {
			prev = c;
			c = underscoreString[i];
			if (c == '_') {
				continue;
			}
			if (first) {
				first = false;
				if (capitalizeFirstLetter) {
					sb.append(Character.toUpperCase(c));
				} else {
					sb.append(Character.toLowerCase(c));
				}
			} else {
				if (prev == '_') {
					sb.append(Character.toUpperCase(c));
				} else {
					sb.append(Character.toLowerCase(c));
				}
			}
		}
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(String underscoreString, boolean capitalizeFirstLetter, Writer writer) {
		convertUnderscoresToCamelCaseOn(underscoreString.toCharArray(), capitalizeFirstLetter, writer);
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(char[] underscoreString, boolean capitalizeFirstLetter, Writer writer) {
		int len = underscoreString.length;
		if (len != 0) {
			convertUnderscoresToCamelCaseOnInternal(underscoreString, capitalizeFirstLetter, len, writer);
		}
	}

	private static void convertUnderscoresToCamelCaseOnInternal(char[] underscoreString, boolean capitalizeFirstLetter, int len, Writer writer) {
		char prev = 0;
		char c = 0;
		boolean first = true;
		for (int i = 0; i < len; i++) {
			prev = c;
			c = underscoreString[i];
			if (c == '_') {
				continue;
			}
			if (first) {
				first = false;
				if (capitalizeFirstLetter) {
					writeCharOn(Character.toUpperCase(c), writer);
				} else {
					writeCharOn(Character.toLowerCase(c), writer);
				}
			} else {
				if (prev == '_') {
					writeCharOn(Character.toUpperCase(c), writer);
				} else {
					writeCharOn(Character.toLowerCase(c), writer);
				}
			}
		}
	}


	// ********** convenience **********

	public static char[] convertToCharArray(StringBuffer sb) {
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	/**
	 * checked exceptions suck
	 */
	private static void writeStringOn(char[] string, Writer writer) {
		try {
			writer.write(string);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * checked exceptions suck
	 */
	private static void writeStringOn(char[] string, int off, int len, Writer writer) {
		try {
			writer.write(string, off, len);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * checked exceptions suck
	 */
	private static void writeStringOn(String string, Writer writer) {
		try {
			writer.write(string);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * checked exceptions suck
	 */
	private static void writeCharOn(char c, Writer writer) {
		try {
			writer.write(c);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private StringTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
