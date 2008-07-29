/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
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
 * 
 * As of jdk 1.5, it's tempting to convert all of these methods to use
 * java.lang.Appendable (instead of StringBuffer, StringBuilder, and Writer);
 * but all the Appendable methods throw java.io.IOException (yech) and we
 * [might?] get a bit better performance invoking methods on classes than
 * we get on interfaces. :-)
 */
public final class StringTools {

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	/** double quote */
	public static final char QUOTE = '"';

	/** parenthesis */
	public static final char OPEN_PARENTHESIS = '(';
	public static final char CLOSE_PARENTHESIS = ')';

	/** brackets */
	public static final char OPEN_BRACKET = '[';
	public static final char CLOSE_BRACKET = ']';

	/** brackets */
	public static final char OPEN_BRACE = '{';
	public static final char CLOSE_BRACE = '}';

	/** brackets */
	public static final char OPEN_CHEVRON = '<';
	public static final char CLOSE_CHEVRON = '>';

	/** empty string */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/** empty char array */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];



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
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, StringBuilder)
	 */
	public static void padOn(String string, int length, StringBuilder sb) {
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return pad_(string, length, c);
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
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			padOn_(string, length, c, writer);
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
	public static void padOn(String string, int length, char c, StringBuffer sb) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			padOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, StringBuilder)
	 */
	public static void padOn(String string, int length, char c, StringBuilder sb) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			padOn_(string, length, c, sb);
		}
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
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOn(int, StringBuilder)
	 */
	public static void padOn(char[] string, int length, StringBuilder sb) {
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return pad_(string, length, c);
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			padOn_(string, length, c, writer);
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			padOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOn(int, char, StringBuilder)
	 */
	public static void padOn(char[] string, int length, char c, StringBuilder sb) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			padOn_(string, length, c, sb);
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
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, StringBuilder)
	 */
	public static void padOrTruncateOn(String string, int length, StringBuilder sb) {
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
		return pad_(string, length, c);
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
		int stringLength = string.length();
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else if (stringLength > length) {
			writeStringOn(string.substring(0, length), writer);
		} else {
			padOn_(string, length, c, writer);
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
	public static void padOrTruncateOn(String string, int length, char c, StringBuffer sb) {
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string.substring(0, length));
		} else {
			padOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, StringBuilder)
	 */
	public static void padOrTruncateOn(String string, int length, char c, StringBuilder sb) {
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string.substring(0, length));
		} else {
			padOn_(string, length, c, sb);
		}
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
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * String#padOrTruncateOn(int, StringBuilder)
	 */
	public static void padOrTruncate(char[] string, int length, StringBuilder sb) {
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
		return pad_(string, length, c);
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
			padOn_(string, length, c, writer);
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
			padOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the end.
	 * String#padOrTruncateOn(int, char, StringBuilder)
	 */
	public static void padOrTruncateOn(char[] string, int length, char c, StringBuilder sb) {
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, 0, length);
		} else {
			padOn_(string, length, c, sb);
		}
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static String pad_(String string, int length, char c) {
		return new String(pad_(string.toCharArray(), length, c));
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(String string, int length, char c, Writer writer) {
		writeStringOn(string, writer);
		fill_(string, length, c, writer);
	}

	/*
	 * Add enough characters to the specified writer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(String string, int length, char c, Writer writer) {
		fill_(string.length(), length, c, writer);
	}

	/*
	 * Add enough characters to the specified writer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(char[] string, int length, char c, Writer writer) {
		fill_(string.length, length, c, writer);
	}

	/*
	 * Add enough characters to the specified writer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(int stringLength, int length, char c, Writer writer) {
		writeStringOn(CollectionTools.fill(new char[length - stringLength], c), writer);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(String string, int length, char c, StringBuffer sb) {
		sb.append(string);
		fill_(string, length, c, sb);
	}

	/*
	 * Add enough characters to the specified string buffer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(String string, int length, char c, StringBuffer sb) {
		fill_(string.length(), length, c, sb);
	}

	/*
	 * Add enough characters to the specified string buffer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(char[] string, int length, char c, StringBuffer sb) {
		fill_(string.length, length, c, sb);
	}

	/*
	 * Add enough characters to the specified string buffer to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(int stringLength, int length, char c, StringBuffer sb) {
		sb.append(CollectionTools.fill(new char[length - stringLength], c));
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(String string, int length, char c, StringBuilder sb) {
		sb.append(string);
		fill_(string, length, c, sb);
	}

	/*
	 * Add enough characters to the specified string builder to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(String string, int length, char c, StringBuilder sb) {
		fill_(string.length(), length, c, sb);
	}

	/*
	 * Add enough characters to the specified string builder to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(char[] string, int length, char c, StringBuilder sb) {
		fill_(string.length, length, c, sb);
	}

	/*
	 * Add enough characters to the specified string builder to compensate for
	 * the difference between the specified string and specified length.
	 */
	private static void fill_(int stringLength, int length, char c, StringBuilder sb) {
		sb.append(CollectionTools.fill(new char[length - stringLength], c));
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static char[] pad_(char[] string, int length, char c) {
		char[] result = new char[length];
		int stringLength = string.length;
		System.arraycopy(string, 0, result, 0, stringLength);
		Arrays.fill(result, stringLength, length, c);
		return result;
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(char[] string, int length, char c, Writer writer) {
		writeStringOn(string, writer);
		fill_(string, length, c, writer);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(char[] string, int length, char c, StringBuffer sb) {
		sb.append(string);
		fill_(string, length, c, sb);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void padOn_(char[] string, int length, char c, StringBuilder sb) {
		sb.append(string);
		fill_(string, length, c, sb);
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
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, StringBuilder)
	 */
	public static void zeroPadOn(String string, int length, StringBuilder sb) {
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return frontPad_(string, length, c);
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
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			frontPadOn_(string, length, c, writer);
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
	public static void frontPadOn(String string, int length, char c, StringBuffer sb) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPadOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, StringBuilder)
	 */
	public static void frontPadOn(String string, int length, char c, StringBuilder sb) {
		int stringLength = string.length();
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPadOn_(string, length, c, sb);
		}
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
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOn(int, StringBuilder)
	 */
	public static void zeroPadOn(char[] string, int length, StringBuilder sb) {
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			return string;
		}
		return frontPad_(string, length, c);
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else {
			frontPadOn_(string, length, c, writer);
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
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPadOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOn(int, char, StringBuilder)
	 */
	public static void frontPadOn(char[] string, int length, char c, StringBuilder sb) {
		int stringLength = string.length;
		if (stringLength > length) {
			throw new IllegalArgumentException("String is too long: " + stringLength + " > " + length);  //$NON-NLS-1$  //$NON-NLS-2$
		}
		if (stringLength == length) {
			sb.append(string);
		} else {
			frontPadOn_(string, length, c, sb);
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
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, StringBuilder)
	 */
	public static void zeroPadOrTruncateOn(String string, int length, StringBuilder sb) {
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
		return frontPad_(string, length, c);
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
		int stringLength = string.length();
		if (stringLength == length) {
			writeStringOn(string, writer);
		} else if (stringLength > length) {
			writeStringOn(string.substring(stringLength - length), writer);
		} else {
			frontPadOn_(string, length, c, writer);
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
	public static void frontPadOrTruncateOn(String string, int length, char c, StringBuffer sb) {
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string.substring(stringLength - length));
		} else {
			frontPadOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, StringBuilder)
	 */
	public static void frontPadOrTruncateOn(String string, int length, char c, StringBuilder sb) {
		int stringLength = string.length();
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string.substring(stringLength - length));
		} else {
			frontPadOn_(string, length, c, sb);
		}
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
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * String#zeroPadOrTruncateOn(int, StringBuilder)
	 */
	public static void zeroPadOrTruncateOn(char[] string, int length, StringBuilder sb) {
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
		return frontPad_(string, length, c);
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
			frontPadOn_(string, length, c, writer);
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
			frontPadOn_(string, length, c, sb);
		}
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with the 
	 * specified character at the front.
	 * String#frontPadOrTruncateOn(int, char, StringBuilder)
	 */
	public static void frontPadOrTruncateOn(char[] string, int length, char c, StringBuilder sb) {
		int stringLength = string.length;
		if (stringLength == length) {
			sb.append(string);
		} else if (stringLength > length) {
			sb.append(string, stringLength - length, length);
		} else {
			frontPadOn_(string, length, c, sb);
		}
	}

	/*
	 * Front-pad the specified string without validating the parms.
	 */
	private static String frontPad_(String string, int length, char c) {
		return new String(frontPad_(string.toCharArray(), length, c));
	}

	/*
	 * Zero-pad the specified string without validating the parms.
	 */
	private static char[] frontPad_(char[] string, int length, char c) {
		char[] result = new char[length];
		int stringLength = string.length;
		int padLength = length - stringLength;
		System.arraycopy(string, 0, result, padLength, stringLength);
		Arrays.fill(result, 0, padLength, c);
		return result;
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(String string, int length, char c, Writer writer) {
		fill_(string, length, c, writer);
		writeStringOn(string, writer);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(char[] string, int length, char c, Writer writer) {
		fill_(string, length, c, writer);
		writeStringOn(string, writer);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(String string, int length, char c, StringBuffer sb) {
		fill_(string, length, c, sb);
		sb.append(string);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(char[] string, int length, char c, StringBuffer sb) {
		fill_(string, length, c, sb);
		sb.append(string);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(String string, int length, char c, StringBuilder sb) {
		fill_(string, length, c, sb);
		sb.append(string);
	}

	/*
	 * Pad the specified string without validating the parms.
	 */
	private static void frontPadOn_(char[] string, int length, char c, StringBuilder sb) {
		fill_(string, length, c, sb);
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
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(String string, StringBuilder sb) {
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
		writeCharOn(wrap, writer);
		writeStringOn(string, writer);
		writeCharOn(wrap, writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, char wrap, StringBuffer sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, char wrap, StringBuilder sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
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
		writeStringOn(wrap, writer);
		writeStringOn(string, writer);
		writeStringOn(wrap, writer);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, String wrap, StringBuffer sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
	}

	/**
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(String string, String wrap, StringBuilder sb) {
		sb.append(wrap);
		sb.append(string);
		sb.append(wrap);
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
	 * Wrap the specified string with double quotes.
	 */
	public static void quoteOn(char[] string, StringBuilder sb) {
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
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char wrap, StringBuilder sb) {
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
	 * Wrap the specified string with the specified wrap; i.e. put a copy of
	 * the wrap at the front and back of the resulting string.
	 */
	public static void wrapOn(char[] string, char[] wrap, StringBuilder sb) {
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


	// ********** delimiting **********

	/**
	 * Return whether the specified string is quoted: "\"foo\"".
	 */
	public static boolean stringIsQuoted(String string) {
		return stringIsDelimited(string, QUOTE);
	}

	/**
	 * Return whether the specified string is parenthetical: "(foo)".
	 */
	public static boolean stringIsParenthetical(String string) {
		return stringIsDelimited(string, OPEN_PARENTHESIS, CLOSE_PARENTHESIS);
	}

	/**
	 * Return whether the specified string is bracketed: "[foo]".
	 */
	public static boolean stringIsBracketed(String string) {
		return stringIsDelimited(string, OPEN_BRACKET, CLOSE_BRACKET);
	}

	/**
	 * Return whether the specified string is braced: "{foo}".
	 */
	public static boolean stringIsBraced(String string) {
		return stringIsDelimited(string, OPEN_BRACE, CLOSE_BRACE);
	}

	/**
	 * Return whether the specified string is chevroned: "<foo>".
	 */
	public static boolean stringIsChevroned(String string) {
		return stringIsDelimited(string, OPEN_CHEVRON, CLOSE_CHEVRON);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * character.
	 */
	public static boolean stringIsDelimited(String string, char c) {
		return stringIsDelimited(string, c, c);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * characters.
	 */
	public static boolean stringIsDelimited(String string, char start, char end) {
		int len = string.length();
		if (len < 2) {
			return false;
		}
		return stringIsDelimited(string.toCharArray(), start, end, len);
	}

	/**
	 * Return whether the specified string is quoted: "\"foo\"".
	 */
	public static boolean stringIsQuoted(char[] string) {
		return stringIsDelimited(string, QUOTE);
	}

	/**
	 * Return whether the specified string is parenthetical: "(foo)".
	 */
	public static boolean stringIsParenthetical(char[] string) {
		return stringIsDelimited(string, OPEN_PARENTHESIS, CLOSE_PARENTHESIS);
	}

	/**
	 * Return whether the specified string is bracketed: "[foo]".
	 */
	public static boolean stringIsBracketed(char[] string) {
		return stringIsDelimited(string, OPEN_BRACKET, CLOSE_BRACKET);
	}

	/**
	 * Return whether the specified string is braced: "{foo}".
	 */
	public static boolean stringIsBraced(char[] string) {
		return stringIsDelimited(string, OPEN_BRACE, CLOSE_BRACE);
	}

	/**
	 * Return whether the specified string is chevroned: "<foo>".
	 */
	public static boolean stringIsChevroned(char[] string) {
		return stringIsDelimited(string, OPEN_CHEVRON, CLOSE_CHEVRON);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * character.
	 */
	public static boolean stringIsDelimited(char[] string, char c) {
		return stringIsDelimited(string, c, c);
	}

	/**
	 * Return whether the specified string is delimited by the specified
	 * characters.
	 */
	public static boolean stringIsDelimited(char[] string, char start, char end) {
		int len = string.length;
		if (len < 2) {
			return false;
		}
		return stringIsDelimited(string, start, end, len);
	}

	private static boolean stringIsDelimited(char[] s, char start, char end, int len) {
		return (s[0] == start) && (s[len - 1] == end);
	}


	// ********** unwrapping **********

	/**
	 * Remove the first and last characters from the specified string.
	 * If the string is too short to be unwrapped, throw an
	 * IllegalArgumentException.
	 */
	public static String unwrap(String string) {
		return unwrap(string, 1);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be unwrapped, throw an
	 * IllegalArgumentException.
	 */
	public static String unwrap(String string, int count) {
		int len = string.length() - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_STRING;
		}
		return new String(unwrap(string.toCharArray(), len, count));
	}

	/**
	 * Remove the first and last characters from the specified string.
	 * If the string is too short to be unwrapped, throw an
	 * IllegalArgumentException.
	 */
	public static char[] unwrap(char[] string) {
		return unwrap(string, 1);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be unwrapped, throw an
	 * IllegalArgumentException.
	 */
	public static char[] unwrap(char[] string, int count) {
		int len = string.length - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		return unwrap(string, len, count);
	}

	private static char[] unwrap(char[] string, int len, int count) {
		char[] result = new char[len];
		System.arraycopy(string, count, result, 0, len);
		return result;
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(String string, Writer writer) {
		unwrapOn(string, 1, writer);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(String string, int count, Writer writer) {
		int len = string.length() - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		writeStringOn(string, count, len, writer);
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(String string, StringBuffer sb) {
		unwrapOn(string, 1, sb);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(String string, int count, StringBuffer sb) {
		int len = string.length() - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		sb.append(string, count, count + len);
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(String string, StringBuilder sb) {
		unwrapOn(string, 1, sb);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(String string, int count, StringBuilder sb) {
		int len = string.length() - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		sb.append(string, count, count + len);
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(char[] string, Writer writer) {
		unwrapOn(string, 1, writer);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(char[] string, int count, Writer writer) {
		int len = string.length - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		writeStringOn(string, count, len, writer);
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(char[] string, StringBuffer sb) {
		unwrapOn(string, 1, sb);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(char[] string, int count, StringBuffer sb) {
		int len = string.length - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		sb.append(string, count, len);
	}

	/**
	 * Unwrap the specified string.
	 */
	public static void unwrapOn(char[] string, StringBuilder sb) {
		unwrapOn(string, 1, sb);
	}

	/**
	 * Unwrap the specified string of count characters.
	 */
	public static void unwrapOn(char[] string, int count, StringBuilder sb) {
		int len = string.length - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		sb.append(string, count, len);
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
		int index = string.indexOf(c);
		if (index == -1) {
			writeStringOn(string, writer);
		} else {
			removeCharAtIndexOn(string.toCharArray(), index, writer);
		}
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, StringBuffer)
	 */
	public static void removeFirstOccurrenceOn(String string, char c, StringBuffer sb) {
		int index = string.indexOf(c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndexOn(string.toCharArray(), index, sb);
		}
	}

	/**
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, StringBuilder)
	 */
	public static void removeFirstOccurrenceOn(String string, char c, StringBuilder sb) {
		int index = string.indexOf(c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndexOn(string.toCharArray(), index, sb);
		}
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
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, Writer)
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, Writer writer) {
		int index = CollectionTools.indexOf(string, c);
		if (index == -1) {
			writeStringOn(string, writer);
		} else {
			removeCharAtIndexOn(string, index, writer);
		}
	}

	private static void removeCharAtIndexOn(char[] string, int index, Writer writer) {
		int last = string.length - 1;
		if (index == 0) {
			// character found at the front of string
			writeStringOn(string, 1, last, writer);
		} else if (index == last) {
			// character found at the end of string
			writeStringOn(string, 0, last, writer);
		} else {
			// character found somewhere in the middle of the string
			writeStringOn(string, 0, index, writer);
			writeStringOn(string, index + 1, last - index, writer);
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
			sb.append(string);
		} else {
			removeCharAtIndexOn(string, index, sb);
		}
	}

	private static void removeCharAtIndexOn(char[] string, int index, StringBuffer sb) {
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
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * String#removeFirstOccurrenceOn(char, StringBuilder)
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, StringBuilder sb) {
		int index = CollectionTools.indexOf(string, c);
		if (index == -1) {
			sb.append(string);
		} else {
			removeCharAtIndexOn(string, index, sb);
		}
	}

	private static void removeCharAtIndexOn(char[] string, int index, StringBuilder sb) {
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
	 * from the specified string and return the result.
	 * String#removeAllOccurrences(char)
	 */
	public static String removeAllOccurrences(String string, char c) {
		int first = string.indexOf(c);
		return (first == -1) ? string : new String(removeAllOccurrences_(string.toCharArray(), c, first));
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * String#removeAllOccurrencesOn(char, Writer)
	 */
	public static void removeAllOccurrencesOn(String string, char c, Writer writer) {
		int first = string.indexOf(c);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			removeAllOccurrencesOn_(string.toCharArray(), c, first, writer);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * String#removeAllOccurrencesOn(char, StringBuffer)
	 */
	public static void removeAllOccurrencesOn(String string, char c, StringBuffer sb) {
		int first = string.indexOf(c);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllOccurrencesOn_(string.toCharArray(), c, first, sb);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * String#removeAllOccurrencesOn(char, StringBuilder)
	 */
	public static void removeAllOccurrencesOn(String string, char c, StringBuilder sb) {
		int first = string.indexOf(c);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllOccurrencesOn_(string.toCharArray(), c, first, sb);
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and return the result.
	 * String#removeAllOccurrences(char)
	 */
	public static char[] removeAllOccurrences(char[] string, char c) {
		int first = CollectionTools.indexOf(string, c);
		return (first == -1) ? string : removeAllOccurrences_(string, c, first);
	}

	/*
	 * The index of the first matching character is passed in.
	 */
	private static char[] removeAllOccurrences_(char[] string, char c, int first) {
		StringBuilder sb = new StringBuilder(string.length);
		removeAllOccurrencesOn_(string, c, first, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the
	 * specified writer.
	 * String#removeAllOccurrencesOn(char, Writer)
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, Writer writer) {
		int first = CollectionTools.indexOf(string, c);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			removeAllOccurrencesOn_(string, c, first, writer);
		}
	}

	/*
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrencesOn_(char[] string, char c, int first, Writer writer) {
		writeStringOn(string, 0, first, writer);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char d = string[i];
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
		int first = CollectionTools.indexOf(string, c);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllOccurrencesOn_(string, c, first, sb);
		}
	}

	/*
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrencesOn_(char[] string, char c, int first, StringBuffer sb) {
		sb.append(string, 0, first);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char d = string[i];
			if (d != c) {
				sb.append(d);
			}
		}
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and append the result to the
	 * specified string builder.
	 * String#removeAllOccurrencesOn(char, StringBuilder)
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, StringBuilder sb) {
		int first = CollectionTools.indexOf(string, c);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllOccurrencesOn_(string, c, first, sb);
		}
	}

	/*
	 * The index of the first matching character is passed in.
	 */
	private static void removeAllOccurrencesOn_(char[] string, char c, int first, StringBuilder sb) {
		sb.append(string, 0, first);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char d = string[i];
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

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified writer.
	 * String#removeAllSpacesOn(Writer)
	 */
	public static void removeAllSpacesOn(String string, Writer writer) {
		removeAllOccurrencesOn(string, ' ', writer);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified
	 * string buffer.
	 * String#removeAllSpacesOn(StringBuffer)
	 */
	public static void removeAllSpacesOn(String string, StringBuffer sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified
	 * string builder.
	 * String#removeAllSpacesOn(StringBuilder)
	 */
	public static void removeAllSpacesOn(String string, StringBuilder sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces from the specified string and return the result.
	 * String#removeAllSpaces()
	 */
	public static char[] removeAllSpaces(char[] string) {
		return removeAllOccurrences(string, ' ');
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the
	 * specified writer.
	 * String#removeAllSpacesOn(Writer)
	 */
	public static void removeAllSpacesOn(char[] string, Writer writer) {
		removeAllOccurrencesOn(string, ' ', writer);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and append the result to the
	 * specified string buffer.
	 * String#removeAllSpacesOn(StringBuffer)
	 */
	public static void removeAllSpacesOn(char[] string, StringBuffer sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and append the result to the
	 * specified string builder.
	 * String#removeAllSpacesOn(StringBuilder)
	 */
	public static void removeAllSpacesOn(char[] string, StringBuilder sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the whitespace from the specified string and return the result.
	 * String#removeAllWhitespace()
	 */
	public static String removeAllWhitespace(String string) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		return (first == -1) ? string : new String(removeAllWhitespace_(string2, first));
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified writer.
	 * String#removeAllWhitespaceOn(Writer)
	 */
	public static void removeAllWhitespaceOn(String string, Writer writer) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			removeAllWhitespaceOn_(string2, first, writer);
		}
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified string buffer.
	 * String#removeAllWhitespaceOn(StringBuffer)
	 */
	public static void removeAllWhitespaceOn(String string, StringBuffer sb) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllWhitespaceOn_(string2, first, sb);
		}
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified string builder.
	 * String#removeAllWhitespaceOn(StringBuilder)
	 */
	public static void removeAllWhitespaceOn(String string, StringBuilder sb) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllWhitespaceOn_(string2, first, sb);
		}
	}

	/**
	 * Remove all the whitespace from the specified string and return the result.
	 * String#removeAllWhitespace()
	 */
	public static char[] removeAllWhitespace(char[] string) {
		int first = indexOfWhitespace_(string);
		return (first == -1) ? string : removeAllWhitespace_(string, first);
	}

	private static int indexOfWhitespace_(char[] string) {
		int len = string.length;
		for (int i = 0; i < len; i++) {
			if (Character.isWhitespace(string[i])) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * The index of the first non-whitespace character is passed in.
	 */
	private static char[] removeAllWhitespace_(char[] string, int first) {
		StringBuilder sb = new StringBuilder(string.length);
		removeAllWhitespaceOn_(string, first, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified writer.
	 * String#removeAllWhitespaceOn(Writer)
	 */
	public static void removeAllWhitespaceOn(char[] string, Writer writer) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			removeAllWhitespaceOn_(string, first, writer);
		}
	}

	/*
	 * The index of the first non-whitespace character is passed in.
	 */
	private static void removeAllWhitespaceOn_(char[] string, int first, Writer writer) {
		writeStringOn(string, 0, first, writer);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if ( ! Character.isWhitespace(c)) {
				writeCharOn(c, writer);
			}
		}
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified string buffer.
	 * String#removeAllWhitespaceOn(StringBuffer)
	 */
	public static void removeAllWhitespaceOn(char[] string, StringBuffer sb) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllWhitespaceOn_(string, first, sb);
		}
	}

	/*
	 * The index of the first non-whitespace character is passed in.
	 */
	private static void removeAllWhitespaceOn_(char[] string, int first, StringBuffer sb) {
		sb.append(string, 0, first);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if ( ! Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
	}

	/**
	 * Remove all the whitespace
	 * from the specified string and append the result to the
	 * specified string builder.
	 * String#removeAllWhitespaceOn(StringBuilder)
	 */
	public static void removeAllWhitespaceOn(char[] string, StringBuilder sb) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			sb.append(string);
		} else {
			removeAllWhitespaceOn_(string, first, sb);
		}
	}

	/*
	 * The index of the first non-whitespace character is passed in.
	 */
	private static void removeAllWhitespaceOn_(char[] string, int first, StringBuilder sb) {
		sb.append(string, 0, first);
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if ( ! Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
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
		return commonPrefixLength_(s1, s2, Math.min(s1.length, s2.length));
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
		return commonPrefixLength_(s1, s2, Math.min(max, Math.min(s1.length, s2.length)));
	}

	/*
	 * Return the length of the common prefix shared by the specified strings;
	 * but limit the length to the specified maximum. Assume the specified
	 * maximum is less than the lengths of the specified strings.
	 */
	private static int commonPrefixLength_(char[] s1, char[] s2, int max) {
		for (int i = 0; i < max; i++) {
			if (s1[i] != s2[i]) {
				return i;
			}
		}
		return max;	// all the characters up to 'max' are the same
	}


	// ********** capitalization **********

	/*
	 * no zero-length check or lower case check
	 */
	private static char[] capitalize_(char[] string) {
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
		return capitalize_(string);
	}

	/**
	 * Return the specified string with its first letter capitalized.
	 * String#capitalize()
	 */
	public static String capitalize(String string) {
		if ((string.length() == 0) || Character.isUpperCase(string.charAt(0))) {
			return string;
		}
		return new String(capitalize_(string.toCharArray()));
	}

	/*
	 * no zero-length check or upper case check
	 */
	private static void capitalizeOn_(char[] string, StringBuffer sb) {
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
			capitalizeOn_(string, sb);
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
			capitalizeOn_(string.toCharArray(), sb);
		}
	}

	/*
	 * no zero-length check or upper case check
	 */
	private static void capitalizeOn_(char[] string, StringBuilder sb) {
		sb.append(Character.toUpperCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified string builder
	 * with its first letter capitalized.
	 */
	public static void capitalizeOn(char[] string, StringBuilder sb) {
		if (string.length == 0) {
			return;
		}
		if (Character.isUpperCase(string[0])) {
			sb.append(string);
		} else {
			capitalizeOn_(string, sb);
		}
	}

	/**
	 * Append the specified string to the specified string builder
	 * with its first letter capitalized.
	 * String#capitalizeOn(StringBuffer)
	 */
	public static void capitalizeOn(String string, StringBuilder sb) {
		if (string.length() == 0) {
			return;
		}
		if (Character.isUpperCase(string.charAt(0))) {
			sb.append(string);
		} else {
			capitalizeOn_(string.toCharArray(), sb);
		}
	}

	/*
	 * no zero-length check or upper case check
	 */
	private static void capitalizeOn_(char[] string, Writer writer) {
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
			capitalizeOn_(string, writer);
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
			capitalizeOn_(string.toCharArray(), writer);
		}
	}

	/*
	 * no zero-length check or lower case check
	 */
	private static char[] uncapitalize_(char[] string) {
		string[0] = Character.toLowerCase(string[0]);
		return string;
	}

	private static boolean stringNeedNotBeUncapitalized_(char[] string) {
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
		if (stringNeedNotBeUncapitalized_(string)) {
			return string;
		}
		return uncapitalize_(string);
	}

	private static boolean stringNeedNotBeUncapitalized_(String string) {
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
		if (stringNeedNotBeUncapitalized_(string)) {
			return string;
		}
		return new String(uncapitalize_(string.toCharArray()));
	}

	/*
	 * no zero-length check or lower case check
	 */
	private static void uncapitalizeOn_(char[] string, StringBuffer sb) {
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
		if (stringNeedNotBeUncapitalized_(string)) {
			sb.append(string);
		} else {
			uncapitalizeOn_(string, sb);
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
		if (stringNeedNotBeUncapitalized_(string)) {
			sb.append(string);
		} else {
			uncapitalizeOn_(string.toCharArray(), sb);
		}
	}

	/*
	 * no zero-length check or lower case check
	 */
	private static void uncapitalizeOn_(char[] string, StringBuilder sb) {
		sb.append(Character.toLowerCase(string[0]));
		sb.append(string, 1, string.length - 1);
	}

	/**
	 * Append the specified string to the specified string builder
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 */
	public static void uncapitalizeOn(char[] string, StringBuilder sb) {
		if (stringNeedNotBeUncapitalized_(string)) {
			sb.append(string);
		} else {
			uncapitalizeOn_(string, sb);
		}
	}

	/**
	 * Append the specified string to the specified string builder
	 * with its first letter converted to lower case.
	 * (Unless both the first and second letters are upper case,
	 * in which case the string is returned unchanged.)
	 * String#uncapitalizeOn(StringBuffer)
	 */
	public static void uncapitalizeOn(String string, StringBuilder sb) {
		if (stringNeedNotBeUncapitalized_(string)) {
			sb.append(string);
		} else {
			uncapitalizeOn_(string.toCharArray(), sb);
		}
	}

	/*
	 * no zero-length check or upper case check
	 */
	private static void uncapitalizeOn_(char[] string, Writer writer) {
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
		if (stringNeedNotBeUncapitalized_(string)) {
			writeStringOn(string, writer);
		} else {
			uncapitalizeOn_(string, writer);
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
		if (stringNeedNotBeUncapitalized_(string)) {
			writeStringOn(string, writer);
		} else {
			uncapitalizeOn_(string.toCharArray(), writer);
		}
	}


	// ********** #toString() helper methods **********

	/**
	 * Build a "standard" #toString() result for the specified object
	 * and additional information:
	 * 	ClassName[00F3EE42] (add'l info)
	 */
	public static String buildToStringFor(Object o, Object additionalInfo) {
		StringBuilder sb = new StringBuilder();
		buildSimpleToStringOn(o, sb);
		sb.append(" (");  //$NON-NLS-1$
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Build a "standard" simple #toString() result for the specified object:
	 * 	ClassName[00F3EE42]
	 */
	public static String buildToStringFor(Object o) {
		StringBuilder sb = new StringBuilder();
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

	/**
	 * Append a "standard" simple #toString() for the specified object to
	 * the specified string builder:
	 * 	ClassName[00F3EE42]
	 */
	public static void buildSimpleToStringOn(Object o, StringBuilder sb) {
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
		if (string == null) {
			return true;
		}
		int len = string.length();
		if (len == 0) {
			return true;
		}
		return stringIsEmpty_(string.toCharArray(), len);
	}

	/**
	 * Return whether the specified string is null, empty, or contains
	 * only whitespace characters.
	 */
	public static boolean stringIsEmpty(char[] string) {
		if (string == null) {
			return true;
		}
		int len = string.length;
		if (len == 0) {
			return true;
		}
		return stringIsEmpty_(string, len);
	}

	private static boolean stringIsEmpty_(char[] s, int len) {
		for (int i = len; i-- > 0; ) {
			if ( ! Character.isWhitespace(s[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified string is non-null, non-empty, and does
	 * not contain only whitespace characters.
	 */
	public static boolean stringIsNotEmpty(String string) {
		return ! stringIsEmpty(string);
	}

	/**
	 * Return whether the specified string is non-null, non-empty, and does
	 * not contain only whitespace characters.
	 */
	public static boolean stringIsNotEmpty(char[] string) {
		return ! stringIsEmpty(string);
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
		int len = s1.length;
		if (len != s2.length) {
			return false;
		}
		for (int i = len; i-- > 0; ) {
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
		int prefixLength = prefix.length;
		if (string.length < prefixLength) {
			return false;
		}
		for (int i = prefixLength; i-- > 0; ) {
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

	/**
	 * Return whether the specified string is uppercase.
	 */
	public static boolean stringIsUppercase(String string) {
		return (string.length() == 0) ? false : stringIsUppercase_(string);
	}

	/**
	 * Return whether the specified string is uppercase.
	 */
	public static boolean stringIsUppercase(char[] string) {
		return (string.length == 0) ? false : stringIsUppercase_(new String(string));
	}

	private static boolean stringIsUppercase_(String string) {
		return string.equals(string.toUpperCase());
	}

	/**
	 * Return whether the specified string is lowercase.
	 */
	public static boolean stringIsLowercase(String string) {
		return (string.length() == 0) ? false : stringIsLowercase_(string);
	}

	/**
	 * Return whether the specified string is lowercase.
	 */
	public static boolean stringIsLowercase(char[] string) {
		return (string.length == 0) ? false : stringIsLowercase_(new String(string));
	}

	private static boolean stringIsLowercase_(String string) {
		return string.equals(string.toLowerCase());
	}


	// ********** convert camel case to all caps **********

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static String convertCamelCaseToAllCaps(String camelCaseString) {
		int len = camelCaseString.length();
		if (len == 0) {
			return camelCaseString;
		}
		return new String(convertCamelCaseToAllCaps_(camelCaseString.toCharArray(), len));
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
		return convertCamelCaseToAllCaps_(camelCaseString, len);
	}

	private static char[] convertCamelCaseToAllCaps_(char[] camelCaseString, int len) {
		StringBuilder sb = new StringBuilder(len * 2);
		convertCamelCaseToAllCapsOn_(camelCaseString, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, StringBuffer sb) {
		int len = camelCaseString.length();
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), len, sb);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, StringBuffer sb) {
		int len = camelCaseString.length;
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int len, StringBuffer sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, StringBuilder sb) {
		int len = camelCaseString.length();
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), len, sb);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, StringBuilder sb) {
		int len = camelCaseString.length;
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int len, StringBuilder sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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
		int len = camelCaseString.length();
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), len, writer);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, Writer writer) {
		int len = camelCaseString.length;
		if (len != 0) {
			convertCamelCaseToAllCapsOn_(camelCaseString, len, writer);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int len, Writer writer) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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
		int len = camelCaseString.length();
		if ((len == 0) || (maxLength == 0)) {
			return camelCaseString;
		}
		return new String(convertCamelCaseToAllCaps_(camelCaseString.toCharArray(), maxLength, len));
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
		return convertCamelCaseToAllCaps_(camelCaseString, maxLength, len);
	}

	private static char[] convertCamelCaseToAllCaps_(char[] camelCaseString, int maxLength, int len) {
		StringBuilder sb = new StringBuilder(maxLength);
		convertCamelCaseToAllCapsOn_(camelCaseString, maxLength, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, int maxLength, StringBuffer sb) {
		int len = camelCaseString.length();
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), maxLength, len, sb);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, int maxLength, StringBuffer sb) {
		int len = camelCaseString.length;
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString, maxLength, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int maxLength, int len, StringBuffer sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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
	public static void convertCamelCaseToAllCapsOn(String camelCaseString, int maxLength, StringBuilder sb) {
		int len = camelCaseString.length();
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), maxLength, len, sb);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, int maxLength, StringBuilder sb) {
		int len = camelCaseString.length;
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString, maxLength, len, sb);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int maxLength, int len, StringBuilder sb) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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
		int len = camelCaseString.length();
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString.toCharArray(), maxLength, len, writer);
		}
	}

	/**
	 * Convert the specified "camel case" string to an "all caps" string:
	 * "largeProject" -> "LARGE_PROJECT"
	 * Limit the resulting string to the specified maximum length.
	 */
	public static void convertCamelCaseToAllCapsOn(char[] camelCaseString, int maxLength, Writer writer) {
		int len = camelCaseString.length;
		if ((len != 0) && (maxLength != 0)) {
			convertCamelCaseToAllCapsOn_(camelCaseString, maxLength, len, writer);
		}
	}

	private static void convertCamelCaseToAllCapsOn_(char[] camelCaseString, int maxLength, int len, Writer writer) {
		char prev = 0;	// assume 0 is not a valid char
		char c = 0;
		char next = camelCaseString[0];
		int writerLength = 0;
		for (int i = 1; i <= len; i++) {	// NB: start at 1 and end at len!
			c = next;
			next = ((i == len) ? 0 : camelCaseString[i]);
			if (camelCaseWordBreak_(prev, c, next)) {
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

	/*
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
	private static boolean camelCaseWordBreak_(char prev, char c, char next) {
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


	// ********** convert underscores to camel case **********

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "LargeProject"
	 * Capitalize the first letter.
	 */
	public static String convertUnderscoresToCamelCase(String underscoreString) {
		return convertUnderscoresToCamelCase(underscoreString, true);
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
		int len = underscoreString.length();
		if (len == 0) {
			return underscoreString;
		}
		return new String(convertUnderscoresToCamelCase_(underscoreString.toCharArray(), capitalizeFirstLetter, len));
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
		return convertUnderscoresToCamelCase_(underscoreString, capitalizeFirstLetter, len);
	}

	private static char[] convertUnderscoresToCamelCase_(char[] underscoreString, boolean capitalizeFirstLetter, int len) {
		StringBuilder sb = new StringBuilder(len);
		convertUnderscoresToCamelCaseOn_(underscoreString, capitalizeFirstLetter, len, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(String underscoreString, boolean capitalizeFirstLetter, StringBuffer sb) {
		int len = underscoreString.length();
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString.toCharArray(), capitalizeFirstLetter, len, sb);
		}
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(char[] underscoreString, boolean capitalizeFirstLetter, StringBuffer sb) {
		int len = underscoreString.length;
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString, capitalizeFirstLetter, len, sb);
		}
	}

	private static void convertUnderscoresToCamelCaseOn_(char[] underscoreString, boolean capitalizeFirstLetter, int len, StringBuffer sb) {
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
	public static void convertUnderscoresToCamelCaseOn(String underscoreString, boolean capitalizeFirstLetter, StringBuilder sb) {
		int len = underscoreString.length();
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString.toCharArray(), capitalizeFirstLetter, len, sb);
		}
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(char[] underscoreString, boolean capitalizeFirstLetter, StringBuilder sb) {
		int len = underscoreString.length;
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString, capitalizeFirstLetter, len, sb);
		}
	}

	private static void convertUnderscoresToCamelCaseOn_(char[] underscoreString, boolean capitalizeFirstLetter, int len, StringBuilder sb) {
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
		int len = underscoreString.length();
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString.toCharArray(), capitalizeFirstLetter, len, writer);
		}
	}

	/**
	 * Convert the specified "underscore" string to a "camel case" string:
	 * "LARGE_PROJECT" -> "largeProject"
	 * Optionally capitalize the first letter.
	 */
	public static void convertUnderscoresToCamelCaseOn(char[] underscoreString, boolean capitalizeFirstLetter, Writer writer) {
		int len = underscoreString.length;
		if (len != 0) {
			convertUnderscoresToCamelCaseOn_(underscoreString, capitalizeFirstLetter, len, writer);
		}
	}

	private static void convertUnderscoresToCamelCaseOn_(char[] underscoreString, boolean capitalizeFirstLetter, int len, Writer writer) {
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


	// ********** convert to Java string literal **********

	public static final String EMPTY_JAVA_STRING_LITERAL = "\"\"";  //$NON-NLS-1$
	public static final char[] EMPTY_JAVA_STRING_LITERAL_CHAR_ARRAY = EMPTY_JAVA_STRING_LITERAL.toCharArray();

	public static String convertToJavaStringLiteral(String string) {
		int len = string.length();
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToJavaStringLiteralOn_(string.toCharArray(), sb, len);
		return sb.toString();
	}

	public static char[] convertToJavaStringLiteral(char[] string) {
		int len = string.length;
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToJavaStringLiteralOn_(string, sb, len);
		len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	public static void convertToJavaStringLiteralOn(String string, StringBuffer sb) {
		int len = string.length();
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralOn_(string.toCharArray(), sb, len);
		}
	}

	public static void convertToJavaStringLiteralOn(char[] string, StringBuffer sb) {
		int len = string.length;
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralOn_(string, sb, len);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, StringBuffer sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
		sb.append(QUOTE);
		for (char c : string) {
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
//				case '\'':  // single-quote
//					sb.append("\\'");  //$NON-NLS-1$
//					break;
				case '\\':  // backslash
					sb.append("\\\\");  //$NON-NLS-1$
					break;
				default:
					sb.append(c);
					break;
			}
		}
		sb.append(QUOTE);
	}

	public static void convertToJavaStringLiteralOn(String string, StringBuilder sb) {
		int len = string.length();
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralOn_(string.toCharArray(), sb, len);
		}
	}

	public static void convertToJavaStringLiteralOn(char[] string, StringBuilder sb) {
		int len = string.length;
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralOn_(string, sb, len);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, StringBuilder sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
		sb.append(QUOTE);
		for (char c : string) {
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
//				case '\'':  // single-quote
//					sb.append("\\'");  //$NON-NLS-1$
//					break;
				case '\\':  // backslash
					sb.append("\\\\");  //$NON-NLS-1$
					break;
				default:
					sb.append(c);
					break;
			}
		}
		sb.append(QUOTE);
	}

	public static void convertToJavaStringLiteralOn(String string, Writer writer) {
		if (string.length() == 0) {
			writeStringOn(EMPTY_JAVA_STRING_LITERAL, writer);
		} else {
			convertToJavaStringLiteralOn_(string.toCharArray(), writer);
		}
	}

	public static void convertToJavaStringLiteralOn(char[] string, Writer writer) {
		if (string.length == 0) {
			writeStringOn(EMPTY_JAVA_STRING_LITERAL, writer);
		} else {
			convertToJavaStringLiteralOn_(string, writer);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, Writer writer) {
		writeCharOn(QUOTE, writer);
		for (char c : string) {
			switch (c) {
				case '\b':  // backspace
					writeStringOn("\\b", writer);  //$NON-NLS-1$
					break;
				case '\t':  // horizontal tab
					writeStringOn("\\t", writer);  //$NON-NLS-1$
					break;
				case '\n':  // line-feed LF
					writeStringOn("\\n", writer);  //$NON-NLS-1$
					break;
				case '\f':  // form-feed FF
					writeStringOn("\\f", writer);  //$NON-NLS-1$
					break;
				case '\r':  // carriage-return CR
					writeStringOn("\\r", writer);  //$NON-NLS-1$
					break;
				case '"':  // double-quote
					writeStringOn("\\\"", writer);  //$NON-NLS-1$
					break;
//				case '\'':  // single-quote
//					writeStringOn("\\'", writer);  //$NON-NLS-1$
//					break;
				case '\\':  // backslash
					writeStringOn("\\\\", writer);  //$NON-NLS-1$
					break;
				default:
					writeCharOn(c, writer);
					break;
			}
		}
		writeCharOn(QUOTE, writer);
	}


	// ********** convenience **********

	public static char[] convertToCharArray(StringBuffer sb) {
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	public static char[] convertToCharArray(StringBuilder sb) {
		int len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	private static void writeStringOn(char[] string, Writer writer) {
		try {
			writer.write(string);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void writeStringOn(char[] string, int off, int len, Writer writer) {
		try {
			writer.write(string, off, len);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void writeStringOn(String string, int off, int len, Writer writer) {
		try {
			writer.write(string, off, len);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void writeStringOn(String string, Writer writer) {
		try {
			writer.write(string);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void writeCharOn(char c, Writer writer) {
		try {
			writer.write(c);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
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
