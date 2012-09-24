/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;

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
	
	/** XML double quote */
	public static final String XML_QUOTE = "&quot;"; //$NON-NLS-1$
	
	/** XML apostrophe */
	public static final String XML_APOSTROPHE = "&apos;";
	
	/** XML ampersand */
	public static final String XML_AMPERSAND = "&amp;";
	
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

	/** XML angle brackets */
	public static final String XML_OPEN_CHEVRON= "&lt;";
	public static final String XML_CLOSE_CHEVRON = "&gt;";

	/** empty string */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/** empty char array */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];

	/** empty string array */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];



	// ********** padding/truncating **********

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.pad(int)
	 * </code>
	 */
	public static String pad(String string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, Writer)
	 * </code>
	 */
	public static void padOn(String string, int length, Writer writer) {
		padOn(string, length, ' ', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, StringBuffer)
	 * </code>
	 */
	public static void padOn(String string, int length, StringBuffer sb) {
		padOn(string, length, ' ', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.pad(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.pad(int)
	 * </code>
	 */
	public static char[] pad(char[] string, int length) {
		return pad(string, length, ' ');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, writer)
	 * </code>
	 */
	public static void padOn(char[] string, int length, Writer writer) {
		padOn(string, length, ' ', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, StringBuffer)
	 * </code>
	 */
	public static void padOn(char[] string, int length, StringBuffer sb) {
		padOn(string, length, ' ', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.pad(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncate(int)
	 * </code>
	 */
	public static String padOrTruncate(String string, int length) {
		return padOrTruncate(string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, Writer)
	 * </code>
	 */
	public static void padOrTruncateOn(String string, int length, Writer writer) {
		padOrTruncateOn(string, length, ' ', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, StringBuffer)
	 * </code>
	 */
	public static void padOrTruncateOn(String string, int length, StringBuffer sb) {
		padOrTruncateOn(string, length, ' ', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncate(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncate(int)
	 * </code>
	 */
	public static char[] padOrTruncate(char[] string, int length) {
		return padOrTruncate(string, length, ' ');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, Writer)
	 * </code>
	 */
	public static void padOrTruncateOn(char[] string, int length, Writer writer) {
		padOrTruncateOn(string, length, ' ', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, StringBuffer)
	 * </code>
	 */
	public static void padOrTruncate(char[] string, int length, StringBuffer sb) {
		padOrTruncateOn(string, length, ' ', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, it is truncated.
	 * If it is shorter than the specified length, it is padded with spaces at the end.
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncate(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.padOrTruncateOn(int, char, StringBuilder)
	 * </code>
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
		writeStringOn(ArrayTools.fill(new char[length - stringLength], c), writer);
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
		sb.append(ArrayTools.fill(new char[length - stringLength], c));
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
		sb.append(ArrayTools.fill(new char[length - stringLength], c));
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
	 * <p>
	 * <code>
	 * String.zeroPad(int)
	 * </code>
	 */
	public static String zeroPad(String string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, Writer)
	 * </code>
	 */
	public static void zeroPadOn(String string, int length, Writer writer) {
		frontPadOn(string, length, '0', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, StringBuffer)
	 * </code>
	 */
	public static void zeroPadOn(String string, int length, StringBuffer sb) {
		frontPadOn(string, length, '0', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPad(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.zeroPad(int)
	 * </code>
	 */
	public static char[] zeroPad(char[] string, int length) {
		return frontPad(string, length, '0');
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, Writer)
	 * </code>
	 */
	public static void zeroPadOn(char[] string, int length, Writer writer) {
		frontPadOn(string, length, '0', writer);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, StringBuffer)
	 * </code>
	 */
	public static void zeroPadOn(char[] string, int length, StringBuffer sb) {
		frontPadOn(string, length, '0', sb);
	}

	/**
	 * Pad the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, an IllegalArgumentException is thrown.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPad(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncate(int)
	 * </code>
	 */
	public static String zeroPadOrTruncate(String string, int length) {
		return frontPadOrTruncate(string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, Writer)
	 * </code>
	 */
	public static void zeroPadOrTruncateOn(String string, int length, Writer writer) {
		frontPadOrTruncateOn(string, length, '0', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, StringBuffer)
	 * </code>
	 */
	public static void zeroPadOrTruncateOn(String string, int length, StringBuffer sb) {
		frontPadOrTruncateOn(string, length, '0', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncate(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncate(int)
	 * </code>
	 */
	public static char[] zeroPadOrTruncate(char[] string, int length) {
		return frontPadOrTruncate(string, length, '0');
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, Writer)
	 * </code>
	 */
	public static void zeroPadOrTruncateOn(char[] string, int length, Writer writer) {
		frontPadOrTruncateOn(string, length, '0', writer);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, StringBuffer)
	 * </code>
	 */
	public static void zeroPadOrTruncateOn(char[] string, int length, StringBuffer sb) {
		frontPadOrTruncateOn(string, length, '0', sb);
	}

	/**
	 * Pad or truncate the specified string to the specified length.
	 * If the string is already the specified length, it is returned unchanged.
	 * If it is longer than the specified length, only the last part of the string is returned.
	 * If it is shorter than the specified length, it is padded with zeros at the front.
	 * <p>
	 * <code>
	 * String.zeroPadOrTruncateOn(int, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncate(int, char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.frontPadOrTruncateOn(int, char, StringBuilder)
	 * </code>
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


	// ********** separating **********

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static String separate(String string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int len = string.length();
		return (len <= segmentSize) ? string : new String(separate(string.toCharArray(), separator, segmentSize, len));
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(String string, char separator, int segmentSize, Writer writer) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length() <= segmentSize) {
			writeStringOn(string, writer);
		} else {
			separateOn_(string.toCharArray(), separator, segmentSize, writer);
		}
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(String string, char separator, int segmentSize, StringBuffer sb) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length() <= segmentSize) {
			sb.append(string);
		} else {
			separateOn_(string.toCharArray(), separator, segmentSize, sb);
		}
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(String string, char separator, int segmentSize, StringBuilder sb) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length() <= segmentSize) {
			sb.append(string);
		} else {
			separateOn_(string.toCharArray(), separator, segmentSize, sb);
		}
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static char[] separate(char[] string, char separator, int segmentSize) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		int len = string.length;
		return (len <= segmentSize) ? string : separate(string, separator, segmentSize, len);
	}

	/**
	 * pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static char[] separate(char[] string, char separator, int segmentSize, int len) {
		int resultLen = len + (len / segmentSize);
		if ((len % segmentSize) == 0) {
			resultLen--;  // no separator after the final segment if nothing following it
		}
		char[] result = new char[resultLen];
		int j = 0;
		int segCount = 0;
		for (char c : string) {
			if (segCount == segmentSize) {
				result[j++] = separator;
				segCount = 0;
			}
			segCount++;
			result[j++] = c;
		}
		return result;
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(char[] string, char separator, int segmentSize, Writer writer) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length <= segmentSize) {
			writeStringOn(string, writer);
		} else {
			separateOn_(string, separator, segmentSize, writer);
		}
	}

	/**
	 * pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separateOn_(char[] string, char separator, int segmentSize, Writer writer) {
		int segCount = 0;
		for (char c : string) {
			if (segCount == segmentSize) {
				writeCharOn(separator, writer);
				segCount = 0;
			}
			segCount++;
			writeCharOn(c, writer);
		}
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(char[] string, char separator, int segmentSize, StringBuffer sb) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length <= segmentSize) {
			sb.append(string);
		} else {
			separateOn_(string, separator, segmentSize, sb);
		}
	}

	/**
	 * pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separateOn_(char[] string, char separator, int segmentSize, StringBuffer sb) {
		int segCount = 0;
		for (char c : string) {
			if (segCount == segmentSize) {
				sb.append(separator);
				segCount = 0;
			}
			segCount++;
			sb.append(c);
		}
	}

	/**
	 * Separate the segments of the specified string with the specified
	 * separator:<pre>
	 *     separate("012345", '-', 2) => "01-23-45"
	 * </pre>
	 */
	public static void separateOn(char[] string, char separator, int segmentSize, StringBuilder sb) {
		if (segmentSize <= 0) {
			throw new IllegalArgumentException("segment size must be positive: " + segmentSize); //$NON-NLS-1$
		}
		if (string.length <= segmentSize) {
			sb.append(string);
		} else {
			separateOn_(string, separator, segmentSize, sb);
		}
	}

	/**
	 * pre-conditions: string is longer than segment size; segment size is positive
	 */
	private static void separateOn_(char[] string, char separator, int segmentSize, StringBuilder sb) {
		int segCount = 0;
		for (char c : string) {
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
	public static String quote(String string) {
		return delimit(string, QUOTE);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(String string, Writer writer) {
		delimitOn(string, QUOTE, writer);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(String string, StringBuffer sb) {
		delimitOn(string, QUOTE, sb);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(String string, StringBuilder sb) {
		delimitOn(string, QUOTE, sb);
	}

	/**
	 * Delimit each of the specified strings with double quotes.
	 * Escape any occurrences of a double quote in a string with another
	 * double quote.
	 */
	public static Iterator<String> quote(Iterator<String> strings) {
		return delimit(strings, QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static String delimit(String string, char delimiter) {
		return new String(delimit(string.toCharArray(), delimiter));
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(String string, char delimiter, Writer writer) {
		delimitOn(string.toCharArray(), delimiter, writer);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(String string, char delimiter, StringBuffer sb) {
		delimitOn(string.toCharArray(), delimiter, sb);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(String string, char delimiter, StringBuilder sb) {
		delimitOn(string.toCharArray(), delimiter, sb);
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in a string with another delimiter.
	 */
	public static Iterable<String> delimit(Iterable<String> strings, char delimiter) {
		return new TransformationIterable<String, String>(strings, new CharStringDelimiter(delimiter));
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in a string with another delimiter.
	 */
	public static Iterator<String> delimit(Iterator<String> strings, char delimiter) {
		return new TransformationIterator<String, String>(strings, new CharStringDelimiter(delimiter));
	}

	private static class CharStringDelimiter implements Transformer<String, String> {
		private char delimiter;
		CharStringDelimiter(char delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public String transform(String string) {
			return StringTools.delimit(string, this.delimiter);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static String delimit(String string, String delimiter) {
		if (delimiter.length() == 1) {
			return delimit(string, delimiter.charAt(0));
		}
		return new String(delimit(string.toCharArray(), delimiter.toCharArray()));
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(String string, String delimiter, Writer writer) {
		if (delimiter.length() == 1) {
			delimitOn(string, delimiter.charAt(0), writer);
		} else {
			delimitOn(string.toCharArray(), delimiter.toCharArray(), writer);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(String string, String delimiter, StringBuffer sb) {
		if (delimiter.length() == 1) {
			delimitOn(string, delimiter.charAt(0), sb);
		} else {
			delimitOn(string.toCharArray(), delimiter.toCharArray(), sb);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(String string, String delimiter, StringBuilder sb) {
		if (delimiter.length() == 1) {
			delimitOn(string, delimiter.charAt(0), sb);
		} else {
			delimitOn(string.toCharArray(), delimiter.toCharArray(), sb);
		}
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in a string with
	 * another delimiter.
	 */
	public static Iterable<String> delimit(Iterable<String> strings, String delimiter) {
		return (delimiter.length() == 1) ?
				delimit(strings, delimiter.charAt(0)) :
				new TransformationIterable<String, String>(strings, new StringStringDelimiter(delimiter));
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in a string with
	 * another delimiter.
	 */
	public static Iterator<String> delimit(Iterator<String> strings, String delimiter) {
		return (delimiter.length() == 1) ?
				delimit(strings, delimiter.charAt(0)) :
				new TransformationIterator<String, String>(strings, new StringStringDelimiter(delimiter));
	}

	private static class StringStringDelimiter implements Transformer<String, String> {
		private String delimiter;
		StringStringDelimiter(String delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public String transform(String string) {
			return StringTools.delimit(string, this.delimiter);
		}
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static char[] quote(char[] string) {
		return delimit(string, QUOTE);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(char[] string, Writer writer) {
		delimitOn(string, QUOTE, writer);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(char[] string, StringBuffer sb) {
		delimitOn(string, QUOTE, sb);
	}

	/**
	 * Delimit the specified string with double quotes.
	 * Escape any occurrences of a double quote in the string with another
	 * double quote.
	 */
	public static void quoteOn(char[] string, StringBuilder sb) {
		delimitOn(string, QUOTE, sb);
	}

	/**
	 * Delimit each of the specified strings with double quotes.
	 * Escape any occurrences of a double quote in a string with another
	 * double quote.
	 */
	// cannot name method simply 'quote' because of type-erasure...
	public static Iterator<char[]> quoteCharArrays(Iterator<char[]> strings) {
		return delimitCharArrays(strings, QUOTE);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static char[] delimit(char[] string, char delimiter) {
		StringBuilder sb = new StringBuilder(string.length + 2);
		delimitOn(string, delimiter, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(char[] string, char delimiter, Writer writer) {
		writeCharOn(delimiter, writer);
		writeStringOn(string, delimiter, writer);
		writeCharOn(delimiter, writer);
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(char[] string, char delimiter, StringBuffer sb) {
		sb.append(delimiter);
		for (char c : string) {
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
	 * Escape any occurrences of the delimiter in the string with another delimiter.
	 */
	public static void delimitOn(char[] string, char delimiter, StringBuilder sb) {
		sb.append(delimiter);
		for (char c : string) {
			if (c == delimiter) {
				sb.append(c);
			}
			sb.append(c);
		}
		sb.append(delimiter);
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in a string with another delimiter.
	 */
	// cannot name method simply 'delimit' because of type-erasure...
	public static Iterable<char[]> delimitCharArrays(Iterable<char[]> strings, char delimiter) {
		return new TransformationIterable<char[], char[]>(strings, new CharCharArrayDelimiter(delimiter));
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of the delimiter in a string with another delimiter.
	 */
	// cannot name method simply 'delimit' because of type-erasure...
	public static Iterator<char[]> delimitCharArrays(Iterator<char[]> strings, char delimiter) {
		return new TransformationIterator<char[], char[]>(strings, new CharCharArrayDelimiter(delimiter));
	}

	private static class CharCharArrayDelimiter implements Transformer<char[], char[]> {
		private char delimiter;
		CharCharArrayDelimiter(char delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public char[] transform(char[] string) {
			return StringTools.delimit(string, this.delimiter);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static char[] delimit(char[] string, char[] delimiter) {
		int delimiterLength = delimiter.length;
		if (delimiterLength == 1) {
			return delimit(string, delimiter[0]);
		}
		int stringLength = string.length;
		char[] result = new char[stringLength+(2*delimiterLength)];
		System.arraycopy(delimiter, 0, result, 0, delimiterLength);
		System.arraycopy(string, 0, result, delimiterLength, stringLength);
		System.arraycopy(delimiter, 0, result, stringLength+delimiterLength, delimiterLength);
		return result;
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(char[] string, char[] delimiter, Writer writer) {
		if (delimiter.length == 1) {
			delimitOn(string, delimiter[0], writer);
		} else {
			writeStringOn(delimiter, writer);
			writeStringOn(string, writer);
			writeStringOn(delimiter, writer);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(char[] string, char[] delimiter, StringBuffer sb) {
		if (delimiter.length == 1) {
			delimitOn(string, delimiter[0], sb);
		} else {
			sb.append(delimiter);
			sb.append(string);
			sb.append(delimiter);
		}
	}

	/**
	 * Delimit the specified string with the specified delimiter; i.e. put a copy of
	 * the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in the string with
	 * another delimiter.
	 */
	public static void delimitOn(char[] string, char[] delimiter, StringBuilder sb) {
		if (delimiter.length == 1) {
			delimitOn(string, delimiter[0], sb);
		} else {
			sb.append(delimiter);
			sb.append(string);
			sb.append(delimiter);
		}
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in a string with
	 * another delimiter.
	 */
	// cannot name method simply 'delimit' because of type-erasure...
	public static Iterable<char[]> delimitCharArrays(Iterable<char[]> strings, char[] delimiter) {
		return new TransformationIterable<char[], char[]>(strings, new CharArrayCharArrayDelimiter(delimiter));
	}

	/**
	 * Delimit each of the specified strings with the specified delimiter; i.e. put a
	 * copy of the delimiter at the front and back of the resulting string.
	 * Escape any occurrences of a single-character delimiter in a string with
	 * another delimiter.
	 */
	// cannot name method simply 'delimit' because of type-erasure...
	public static Iterator<char[]> delimitCharArrays(Iterator<char[]> strings, char[] delimiter) {
		return new TransformationIterator<char[], char[]>(strings, new CharArrayCharArrayDelimiter(delimiter));
	}

	private static class CharArrayCharArrayDelimiter implements Transformer<char[], char[]> {
		private char[] delimiter;
		CharArrayCharArrayDelimiter(char[] delimiter) {
			super();
			this.delimiter = delimiter;
		}
		public char[] transform(char[] string) {
			return StringTools.delimit(string, this.delimiter);
		}
	}


	// ********** delimiting queries **********

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


	// ********** undelimiting **********

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static String undelimit(String string) {
		int len = string.length() - 2;
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_STRING;
		}
		return new String(undelimit_(string.toCharArray(), len));
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static String undelimit(String string, int count) {
		int len = string.length() - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + string + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_STRING;
		}
		return new String(undelimit(string.toCharArray(), len, count));
	}

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static char[] undelimit(char[] string) {
		int len = string.length - 2;
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		return undelimit_(string, len);
	}

	private static char[] undelimit_(char[] string, int length) {
		StringBuilder sb = new StringBuilder(length);
		undelimitOn_(string, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static char[] undelimit(char[] string, int count) {
		int len = string.length - (2 * count);
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		return undelimit(string, len, count);
	}

	private static char[] undelimit(char[] string, int len, int count) {
		char[] result = new char[len];
		System.arraycopy(string, count, result, 0, len);
		return result;
	}

	/**
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(String string, Writer writer) {
		undelimitOn(string.toCharArray(), writer);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(String string, int count, Writer writer) {
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
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(String string, StringBuffer sb) {
		undelimitOn(string.toCharArray(), sb);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(String string, int count, StringBuffer sb) {
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
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(String string, StringBuilder sb) {
		undelimitOn(string.toCharArray(), sb);
	}

	/**
	 * Remove the first and last count characters from the specified string.
	 * If the string is too short to be undelimited, throw an
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(String string, int count, StringBuilder sb) {
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
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(char[] string, Writer writer) {
		int len = string.length - 2;
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		undelimitOn_(string, writer);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimitOn_(char[] string, Writer writer) {
		char delimiter = string[0];  // the first char is the delimiter
		char c = string[0];
		char next = string[1];
		int i = 1;
		int last = string.length - 1;
		do {
			c = next;
			writeCharOn(c, writer);
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
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(char[] string, int count, Writer writer) {
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
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(char[] string, StringBuffer sb) {
		int len = string.length - 2;
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		undelimitOn_(string, sb);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimitOn_(char[] string, StringBuffer sb) {
		char delimiter = string[0];  // the first char is the delimiter
		char c = string[0];
		char next = string[1];
		int i = 1;
		int last = string.length - 1;
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
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(char[] string, int count, StringBuffer sb) {
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
	 * Remove the delimiters from the specified string, removing any escape
	 * characters. Throw an IllegalArgumentException if the string is too short
	 * to undelimit (i.e. length < 2).
	 */
	public static void undelimitOn(char[] string, StringBuilder sb) {
		int len = string.length - 2;
		if (len < 0) {
			throw new IllegalArgumentException("invalid string: \"" + new String(string) + '"'); //$NON-NLS-1$
		}
		if (len == 0) {
			return;
		}
		undelimitOn_(string, sb);
	}

	/**
	 * pre-condition: string is at least 3 characters long
	 */
	private static void undelimitOn_(char[] string, StringBuilder sb) {
		char delimiter = string[0];  // the first char is the delimiter
		char c = string[0];
		char next = string[1];
		int i = 1;
		int last = string.length - 1;
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
	 * IllegalArgumentException.
	 * Use this method to undelimit strings that do not escape embedded
	 * delimiters.
	 */
	public static void undelimitOn(char[] string, int count, StringBuilder sb) {
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrence(char)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrence(char)
	 * </code>
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
	 * Remove the first occurrence of the specified character
	 * from the specified string and print the result on the specified stream.
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, Writer)
	 * </code>
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, Writer writer) {
		int index = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, StringBuffer)
	 * </code>
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, StringBuffer sb) {
		int index = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeFirstOccurrenceOn(char, StringBuilder)
	 * </code>
	 */
	public static void removeFirstOccurrenceOn(char[] string, char c, StringBuilder sb) {
		int index = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrences(char)
	 * </code>
	 */
	public static String removeAllOccurrences(String string, char c) {
		int first = string.indexOf(c);
		return (first == -1) ? string : new String(removeAllOccurrences_(string.toCharArray(), c, first));
	}

	/**
	 * Remove all occurrences of the specified character
	 * from the specified string and write the result to the specified stream.
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrences(char)
	 * </code>
	 */
	public static char[] removeAllOccurrences(char[] string, char c) {
		int first = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, Writer)
	 * </code>
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, Writer writer) {
		int first = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, StringBuffer)
	 * </code>
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, StringBuffer sb) {
		int first = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeAllOccurrencesOn(char, StringBuilder)
	 * </code>
	 */
	public static void removeAllOccurrencesOn(char[] string, char c, StringBuilder sb) {
		int first = ArrayTools.indexOf(string, c);
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
	 * <p>
	 * <code>
	 * String.removeAllSpaces()
	 * </code>
	 */
	public static String removeAllSpaces(String string) {
		return removeAllOccurrences(string, ' ');
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified writer.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(Writer)
	 * </code>
	 */
	public static void removeAllSpacesOn(String string, Writer writer) {
		removeAllOccurrencesOn(string, ' ', writer);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified
	 * string buffer.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(StringBuffer)
	 * </code>
	 */
	public static void removeAllSpacesOn(String string, StringBuffer sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the specified
	 * string builder.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(StringBuilder)
	 * </code>
	 */
	public static void removeAllSpacesOn(String string, StringBuilder sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces from the specified string and return the result.
	 * <p>
	 * <code>
	 * String.removeAllSpaces()
	 * </code>
	 */
	public static char[] removeAllSpaces(char[] string) {
		return removeAllOccurrences(string, ' ');
	}

	/**
	 * Remove all the spaces
	 * from the specified string and write the result to the
	 * specified writer.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(Writer)
	 * </code>
	 */
	public static void removeAllSpacesOn(char[] string, Writer writer) {
		removeAllOccurrencesOn(string, ' ', writer);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and append the result to the
	 * specified string buffer.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(StringBuffer)
	 * </code>
	 */
	public static void removeAllSpacesOn(char[] string, StringBuffer sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the spaces
	 * from the specified string and append the result to the
	 * specified string builder.
	 * <p>
	 * <code>
	 * String.removeAllSpacesOn(StringBuilder)
	 * </code>
	 */
	public static void removeAllSpacesOn(char[] string, StringBuilder sb) {
		removeAllOccurrencesOn(string, ' ', sb);
	}

	/**
	 * Remove all the whitespace from the specified string and return the result.
	 * <p>
	 * <code>
	 * String.removeAllWhitespace()
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(StringBuilder)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespace()
	 * </code>
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(Writer)
	 * </code>
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
	 * The index of the first whitespace character is passed in.
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(StringBuffer)
	 * </code>
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
	 * The index of the first whitespace character is passed in.
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
	 * <p>
	 * <code>
	 * String.removeAllWhitespaceOn(StringBuilder)
	 * </code>
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
	 * The index of the first whitespace character is passed in.
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
//===============================
	/**
	 * Compress the whitespace in the specified string and return the result.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespace()
	 * </code>
	 */
	public static String compressWhitespace(String string) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		return (first == -1) ? string : new String(compressWhitespace_(string2, first));
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified writer.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(Writer)
	 * </code>
	 */
	public static void compressWhitespaceOn(String string, Writer writer) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			compressWhitespaceOn_(string2, first, writer);
		}
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified string buffer.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(StringBuffer)
	 * </code>
	 */
	public static void compressWhitespaceOn(String string, StringBuffer sb) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			sb.append(string);
		} else {
			compressWhitespaceOn_(string2, first, sb);
		}
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified string builder.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(StringBuilder)
	 * </code>
	 */
	public static void compressWhitespaceOn(String string, StringBuilder sb) {
		char[] string2 = string.toCharArray();
		int first = indexOfWhitespace_(string2);
		if (first == -1) {
			sb.append(string);
		} else {
			compressWhitespaceOn_(string2, first, sb);
		}
	}

	/**
	 * Compress the whitespace in the specified string and return the result.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespace()
	 * </code>
	 */
	public static char[] compressWhitespace(char[] string) {
		int first = indexOfWhitespace_(string);
		return (first == -1) ? string : compressWhitespace_(string, first);
	}

	/*
	 * The index of the first whitespace character is passed in.
	 */
	private static char[] compressWhitespace_(char[] string, int first) {
		StringBuilder sb = new StringBuilder(string.length);
		compressWhitespaceOn_(string, first, sb);
		return convertToCharArray(sb);
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified writer.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(Writer)
	 * </code>
	 */
	public static void compressWhitespaceOn(char[] string, Writer writer) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			writeStringOn(string, writer);
		} else {
			compressWhitespaceOn_(string, first, writer);
		}
	}

	/*
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespaceOn_(char[] string, int first, Writer writer) {
		writeStringOn(string, 0, first, writer);
		boolean spaceWritten = false;
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if (Character.isWhitespace(c)) {
				if (spaceWritten) {
					// skip subsequent whitespace characters
				} else {
					// replace first whitespace character with a space
					spaceWritten = true;
					writeCharOn(' ', writer);
				}
			} else {
				spaceWritten = false;
				writeCharOn(c, writer);
			}
		}
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified string buffer.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(StringBuffer)
	 * </code>
	 */
	public static void compressWhitespaceOn(char[] string, StringBuffer sb) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			sb.append(string);
		} else {
			compressWhitespaceOn_(string, first, sb);
		}
	}

	/*
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespaceOn_(char[] string, int first, StringBuffer sb) {
		sb.append(string, 0, first);
		boolean spaceWritten = false;
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if (Character.isWhitespace(c)) {
				if (spaceWritten) {
					// skip subsequent whitespace characters
				} else {
					// replace first whitespace character with a space
					spaceWritten = true;
					sb.append(' ');
				}
			} else {
				spaceWritten = false;
				sb.append(c);
			}
		}
	}

	/**
	 * Compress the whitespace
	 * in the specified string and append the result to the
	 * specified string builder.
	 * The whitespace is compressed by replacing any occurrence of one or more
	 * whitespace characters with a single space.
	 * <p>
	 * <code>
	 * String.compressWhitespaceOn(StringBuilder)
	 * </code>
	 */
	public static void compressWhitespaceOn(char[] string, StringBuilder sb) {
		int first = indexOfWhitespace_(string);
		if (first == -1) {
			sb.append(string);
		} else {
			compressWhitespaceOn_(string, first, sb);
		}
	}

	/*
	 * The index of the first whitespace character is passed in.
	 */
	private static void compressWhitespaceOn_(char[] string, int first, StringBuilder sb) {
		sb.append(string, 0, first);
		boolean spaceWritten = false;
		int len = string.length;
		for (int i = first; i < len; i++) {
			char c = string[i];
			if (Character.isWhitespace(c)) {
				if (spaceWritten) {
					// skip subsequent whitespace characters
				} else {
					// replace first whitespace character with a space
					spaceWritten = true;
					sb.append(' ');
				}
			} else {
				spaceWritten = false;
				sb.append(c);
			}
		}
	}


	// ********** common prefix **********

	/**
	 * Return the length of the common prefix shared by the specified strings.
	 * <p>
	 * <code>
	 * String.commonPrefixLength(String)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.commonPrefixLength(String, int)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.capitalize()
	 * </code>
	 */
	public static String capitalize(String string) {
		if ((string.length() == 0) || Character.isUpperCase(string.charAt(0))) {
			return string;
		}
		return new String(capitalize_(string.toCharArray()));
	}

	/**
	 * Modify each of the specified strings, capitalizing the first letter of
	 * each.
	 */
	public static Iterable<String> capitalize(Iterable<String> strings) {
		return new TransformationIterable<String, String>(strings, STRING_CAPITALIZER);
	}

	/**
	 * Modify each of the specified strings, capitalizing the first letter of
	 * each.
	 */
	public static Iterator<String> capitalize(Iterator<String> strings) {
		return new TransformationIterator<String, String>(strings, STRING_CAPITALIZER);
	}

	private static final Transformer<String, String> STRING_CAPITALIZER = new Transformer<String, String>() {
		public String transform(String string) {
			return StringTools.capitalize(string);
		}
	};

	/**
	 * Modify each of the specified strings, capitalizing the first letter of
	 * each.
	 */
	// cannot name method simply 'capitalize' because of type-erasure...
	public static Iterable<char[]> capitalizeCharArrays(Iterable<char[]> strings) {
		return new TransformationIterable<char[], char[]>(strings, CHAR_ARRAY_CAPITALIZER);
	}

	/**
	 * Modify each of the specified strings, capitalizing the first letter of
	 * each.
	 */
	// cannot name method simply 'capitalize' because of type-erasure...
	public static Iterator<char[]> capitalizeCharArrays(Iterator<char[]> strings) {
		return new TransformationIterator<char[], char[]>(strings, CHAR_ARRAY_CAPITALIZER);
	}

	private static final Transformer<char[], char[]> CHAR_ARRAY_CAPITALIZER = new Transformer<char[], char[]>() {
		public char[] transform(char[] string) {
			return StringTools.capitalize(string);
		}
	};

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
	 * <p>
	 * <code>
	 * String.capitalizeOn(StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.capitalizeOn(StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.capitalizeOn(Writer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.uncapitalize()
	 * </code>
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
	 * <p>
	 * <code>
	 * String.uncapitalizeOn(StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.uncapitalizeOn(StringBuffer)
	 * </code>
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
	 * <p>
	 * <code>
	 * String.uncapitalizeOn(Writer)
	 * </code>
	 */
	public static void uncapitalizeOn(String string, Writer writer) {
		if (stringNeedNotBeUncapitalized_(string)) {
			writeStringOn(string, writer);
		} else {
			uncapitalizeOn_(string.toCharArray(), writer);
		}
	}


	// ********** toString() helper methods **********

	/**
	 * Build a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object and additional information:<pre>
	 *     ClassName[00-F3-EE-42](add'l info)
	 * </pre>
	 */
	public static String buildToStringFor(Object o, Object additionalInfo) {
		StringBuilder sb = new StringBuilder();
		appendSimpleToString(sb, o);
		sb.append('(');
		sb.append(additionalInfo);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Build a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object:<pre>
	 *     ClassName[00-F3-EE-42]
	 * </pre>
	 */
	public static String buildToStringFor(Object o) {
		StringBuilder sb = new StringBuilder();
		appendSimpleToString(sb, o);
		return sb.toString();
	}

	/**
	 * Append a "Dali standard" {@link Object#toString() toString()} result for
	 * the specified object to the specified string builder:<pre>
	 *     ClassName[00-F3-EE-42]
	 */
	public static void appendSimpleToString(StringBuilder sb, Object o) {
		appendToStringClassName(sb, o.getClass());
		sb.append('[');
		separateOn(buildHashCode(o), '-', 2, sb);
		sb.append(']');
	}

	private static String buildHashCode(Object o) {
		// use System.identityHashCode(Object), since Object.hashCode() may be overridden
		return zeroPad(Integer.toHexString(System.identityHashCode(o)).toUpperCase(), 8);
	}

	/**
	 * Return a name suitable for a "Dali standard"
	 * {@link Object#toString() toString()} implementation.
	 * {@link Class#getSimpleName()} isn't quite useful enough:<ul>
	 * <li>An <em>anonymous</em> class's simple name is an empty string.
	 *     Return the "Dali standard" name of the anonymous class's super class
	 *     instead, which is a bit more helpful.
	 * <li>A <em>member</em> or <em>local</em> class's simple name does not
	 *     include its context. Prefix the class's simple name with its
	 *     enclosing class's "Dali standard" name.
	 * </ul>
	 */
	public static String buildToStringClassName(Class<?> javaClass) {
		StringBuilder sb = new StringBuilder();
		appendToStringClassName(sb, javaClass);
		return sb.toString();
	}

	/**
	 * Return a string suitable for a <em>singleton</em>; which is the simple
	 * name of the object's class, since there should only be one.
	 */
	public static String buildSingletonToString(Object o) {
		return buildToStringClassName(o.getClass());
	}

	/**
	 * Append a name suitable for a "Dali standard"
	 * {@link Object#toString() toString()} implementation to the specified
	 * string builder.
	 * @see #buildToStringClassName(Class)
	 */
	public static void appendToStringClassName(StringBuilder sb, Class<?> javaClass) {
		if (javaClass.isAnonymousClass()) {
			appendToStringClassName(sb, javaClass.getSuperclass());  // recurse
		} else {
			Class<?> enclosingClass = javaClass.getEnclosingClass();
			if (enclosingClass == null) {
				appendToStringClassName_(sb, javaClass);  // top-level class
			} else {
				appendToStringClassName(sb, enclosingClass);  // recurse
				sb.append('.');
				sb.append(javaClass.getSimpleName());
			}
		}
	}

	/**
	 * pre-condition: the specified class is a top-level class
	 */
	private static void appendToStringClassName_(StringBuilder sb, Class<?> javaClass) {
		String fullName = javaClass.getName();
		int dot = fullName.lastIndexOf('.');
		if (dot == -1) {
			sb.append(fullName);  // "default" package
		} else {
			sb.append(fullName, dot + 1, fullName.length());
		}
	}

	/**
	 * Append the string representations of the objects in the specified array
	 * to the specified string builder:<pre>
	 *     ["foo", "bar", "baz"]
	 * </pre>
	 * <p>
	 * <code>
	 * StringBuilder.append(Object[])
	 * </code>
	 */
	public static <T> String append(StringBuilder sb, T[] array) {
		return append(sb, new ArrayListIterator<T>(array));
	}

	/**
	 * Append the string representations of the objects in the specified iterable
	 * to the specified string builder:<pre>
	 *     ["foo", "bar", "baz"]
	 * </pre>
	 * <p>
	 * <code>
	 * StringBuilder.append(Iterable)
	 * </code>
	 */
	public static <T> String append(StringBuilder sb, Iterable<T> iterable) {
		return append(sb, iterable.iterator());
	}

	/**
	 * Append the string representations of the objects in the specified iterator
	 * to the specified string builder:<pre>
	 *     ["foo", "bar", "baz"]
	 * </pre>
	 * <p>
	 * <code>
	 * StringBuilder.append(Iterator)
	 * </code>
	 */
	public static <T> String append(StringBuilder sb, Iterator<T> iterator) {
		sb.append('[');
		while (iterator.hasNext()) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}
		sb.append(']');
		return sb.toString();
	}
	
	/**
	 * Return a concatenation of the given strings
	 * 
	 * @param strings - the strings to concatenate
	 * @return the resultant concatenated string
	 */
	public static String concatenate(String ... strings) {
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			sb.append(string);
		}
		return sb.toString();
	}


	// ********** queries **********

	/**
	 * Return whether the specified string is <code>null</code>, empty, or
	 * contains only whitespace characters.
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
	 * Return whether the specified string is <code>null</code>, empty, or
	 * contains only whitespace characters.
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
	 * Return whether the specified string is non-<code>null</code>, non-empty,
	 * and does not contain only whitespace characters.
	 */
	public static boolean stringIsNotEmpty(String string) {
		return ! stringIsEmpty(string);
	}

	/**
	 * Return whether the specified string is non-<code>null</code>, non-empty,
	 * and does not contain only whitespace characters.
	 */
	public static boolean stringIsNotEmpty(char[] string) {
		return ! stringIsEmpty(string);
	}

	/**
	 * Return whether the specified strings are equal.
	 * Check for <code>null</code>s.
	 */
	public static boolean stringsAreEqual(String s1, String s2) {
		return Tools.valuesAreEqual(s1, s2);
	}

	/**
	 * Return whether the specified strings are equal.
	 * Check for <code>null</code>s.
	 */
	public static boolean stringsAreEqual(char[] s1, char[] s2) {
		return (s1 == null) ?
				(s2 == null) :
				((s2 != null) && stringsAreEqual_(s1, s2));
	}

	/**
	 * no <code>null</code> checks
	 */
	private static boolean stringsAreEqual_(char[] s1, char[] s2) {
		int len = s1.length;
		if (len != s2.length) {
			return false;
		}
		for (int i = len; i-- > 0; ) {
			if (s1[i] != s2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified strings are equal, ignoring case.
	 * Check for <code>null</code>s.
	 */
	public static boolean stringsAreEqualIgnoreCase(String s1, String s2) {
		return (s1 == null) ?
				(s2 == null) :
				((s2 != null) && s1.equalsIgnoreCase(s2));
	}

	/**
	 * Return whether the specified strings are equal, ignoring case.
	 * Check for <code>null</code>s.
	 */
	public static boolean stringsAreEqualIgnoreCase(char[] s1, char[] s2) {
		return (s1 == null) ?
				(s2 == null) :
				((s2 != null) && stringsAreEqualIgnoreCase_(s1, s2));
	}

	/**
	 * no <code>null</code> checks
	 */
	private static boolean stringsAreEqualIgnoreCase_(char[] s1, char[] s2) {
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
	 * @see String#regionMatches(boolean, int, String, int, int)
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
				sb.append(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				sb.append((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
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
				sb.append(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c));
			} else {
				sb.append((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c));
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
				writeCharOn(capitalizeFirstLetter ? Character.toUpperCase(c) : Character.toLowerCase(c), writer);
			} else {
				writeCharOn((prev == '_') ? Character.toUpperCase(c) : Character.toLowerCase(c), writer);
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

	public static String convertToJavaStringLiteralContent(String string) {
		int len = string.length();
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToJavaStringLiteralContentOn_(string.toCharArray(), sb, len);
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

	public static char[] convertToJavaStringLiteralContent(char[] string) {
		int len = string.length;
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL_CHAR_ARRAY;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToJavaStringLiteralContentOn_(string, sb, len);
		len = sb.length();
		char[] result = new char[len];
		sb.getChars(0, len, result, 0);
		return result;
	}

	public static Iterable<String> convertToJavaStringLiterals(Iterable<String> strings) {
		return new TransformationIterable<String, String>(strings, STRING_TO_JAVA_STRING_LITERAL_TRANSFORMER);
	}

	public static Iterable<String> convertToJavaStringLiteralContents(Iterable<String> strings) {
		return new TransformationIterable<String, String>(strings, STRING_TO_JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	public static Iterator<String> convertToJavaStringLiterals(Iterator<String> strings) {
		return new TransformationIterator<String, String>(strings, STRING_TO_JAVA_STRING_LITERAL_TRANSFORMER);
	}

	private static final Transformer<String, String> STRING_TO_JAVA_STRING_LITERAL_TRANSFORMER = new Transformer<String, String>() {
		public String transform(String string) {
			return StringTools.convertToJavaStringLiteral(string);
		}
	};

	private static final Transformer<String, String> STRING_TO_JAVA_STRING_LITERAL_CONTENT_TRANSFORMER = new Transformer<String, String>() {
		public String transform(String string) {
			return StringTools.convertToJavaStringLiteralContent(string);
		}
	};

	// cannot name method simply 'convertToJavaStringLiterals' because of type-erasure...
	public static Iterable<char[]> convertToJavaCharArrayLiterals(Iterable<char[]> strings) {
		return new TransformationIterable<char[], char[]>(strings, CHAR_ARRAY_TO_JAVA_STRING_LITERAL_TRANSFORMER);
	}

	// cannot name method simply 'convertToJavaStringLiterals' because of type-erasure...
	public static Iterator<char[]> convertToJavaCharArrayLiterals(Iterator<char[]> strings) {
		return new TransformationIterator<char[], char[]>(strings, CHAR_ARRAY_TO_JAVA_STRING_LITERAL_TRANSFORMER);
	}

	private static final Transformer<char[], char[]> CHAR_ARRAY_TO_JAVA_STRING_LITERAL_TRANSFORMER = new Transformer<char[], char[]>() {
		public char[] transform(char[] string) {
			return StringTools.convertToJavaStringLiteral(string);
		}
	};

	public static void convertToJavaStringLiteralOn(String string, StringBuffer sb) {
		int len = string.length();
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralOn_(string.toCharArray(), sb, len);
		}
	}

	public static void convertToJavaStringLiteralContentOn(String string, StringBuffer sb) {
		int len = string.length();
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralContentOn_(string.toCharArray(), sb, len);
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

	public static void convertToJavaStringLiteralContentOn(char[] string, StringBuffer sb) {
		int len = string.length;
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralContentOn_(string, sb, len);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, StringBuffer sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
		sb.append(QUOTE);
		convertToJavaStringLiteralContentOn_(string, sb, len);
		sb.append(QUOTE);
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralContentOn_(char[] string, StringBuffer sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
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

	public static void convertToJavaStringLiteralContentOn(String string, StringBuilder sb) {
		int len = string.length();
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralContentOn_(string.toCharArray(), sb, len);
		}
	}

	public static void convertToJavaStringLiteralContentOn(char[] string, StringBuilder sb) {
		int len = string.length;
		if (len == 0) {
			sb.append(EMPTY_JAVA_STRING_LITERAL);
		} else {
			convertToJavaStringLiteralContentOn_(string, sb, len);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, StringBuilder sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
		sb.append(QUOTE);
		convertToJavaStringLiteralContentOn_(string, sb, len);
		sb.append(QUOTE);
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralContentOn_(char[] string, StringBuilder sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
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

	public static void convertToJavaStringLiteralContentOn(String string, Writer writer) {
		if (string.length() == 0) {
			writeStringOn(EMPTY_JAVA_STRING_LITERAL, writer);
		} else {
			convertToJavaStringLiteralContentOn_(string.toCharArray(), writer);
		}
	}

	public static void convertToJavaStringLiteralContentOn(char[] string, Writer writer) {
		if (string.length == 0) {
			writeStringOn(EMPTY_JAVA_STRING_LITERAL, writer);
		} else {
			convertToJavaStringLiteralContentOn_(string, writer);
		}
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralOn_(char[] string, Writer writer) {
		writeCharOn(QUOTE, writer);
		convertToJavaStringLiteralContentOn_(string, writer);
		writeCharOn(QUOTE, writer);
	}

	/*
	 * no length checks
	 */
	private static void convertToJavaStringLiteralContentOn_(char[] string, Writer writer) {
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
	}

	// ********** convert to XML string literal **********

	public static String convertToXmlStringLiteral(String string) {
		return convertToXmlStringLiteralQuote(string);
	}
	
	public static String convertToXmlStringLiteralQuote(String string) {
		return convertToXmlStringLiteral(string, '"');
	}
	
	public static String convertToXmlStringLiteralApostrophe(String string) {
		return convertToXmlStringLiteral(string, '\'');
	}
	
	public static String convertToXmlStringLiteral(String string, char delimiter) {
		int len = string.length();
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToXmlStringLiteralOn_(string.toCharArray(), sb, len, delimiter);
		return sb.toString();
	}

	//TODO need to add the rest of the predifende entities to this switch (amp, lt, and gt)
	private static void convertToXmlStringLiteralOn_(char[] string, StringBuilder sb, int len, char delimiter) {
		sb.ensureCapacity(sb.length() + len + 5);
		sb.append(delimiter);
		for (char c : string) {
			switch (c) {
				case '"':  // double-quote
					sb.append((delimiter == '"') ? XML_QUOTE : '"');
					break;
				case '\'':  // apostrophe
					sb.append((delimiter == '\'') ? XML_APOSTROPHE : '\'');
					break;
				default:
					sb.append(c);
					break;
			}
		}
		sb.append(delimiter);
	}
	
	public static String convertToXmlElementStringLiteral(String string) {
		int len = string.length();
		if (len == 0) {
			return EMPTY_JAVA_STRING_LITERAL;
		}
		StringBuilder sb = new StringBuilder(len + 5);
		convertToXmlElementStringLiteralOn_(string.toCharArray(), sb, len);
		return sb.toString();
		}
		
		private static void convertToXmlElementStringLiteralOn_(char[] string, StringBuilder sb, int len) {
		sb.ensureCapacity(sb.length() + len + 5);
		for (char c : string) {
		switch (c) {
					case '&':  // ampersand
						sb.append(XML_AMPERSAND);
						break;
					case '<':  // less-than sign
						sb.append(XML_OPEN_CHEVRON);
						break;
					case '>':  // greater-than sign
						sb.append(XML_CLOSE_CHEVRON);
						break;
					default:
						sb.append(c);
						break;
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

	private static void writeStringOn(char[] string, char escape, Writer writer) {
		try {
			for (char c : string) {
				if (c == escape) {
					writer.write(c);
				}
				writer.write(c);
			}
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
