/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * <code>char</code> utility methods.
 */
public final class CharacterTools {

	/** quotes */
	public static final char QUOTE = '"';
	public static final char DOUBLE_QUOTE = QUOTE;
	public static final char APOSTROPHE = '\'';
	public static final char SINGLE_QUOTE = APOSTROPHE;

	/** parentheses */
	public static final char OPEN_PARENTHESIS = '(';
	public static final char CLOSE_PARENTHESIS = ')';

	/** brackets */
	public static final char OPEN_BRACKET = '[';
	public static final char CLOSE_BRACKET = ']';

	/** braces */
	public static final char OPEN_BRACE = '{';
	public static final char CLOSE_BRACE = '}';

	/** chevrons */
	public static final char OPEN_CHEVRON = '<';
	public static final char CLOSE_CHEVRON = '>';

	/** Java String characters */
	public static final char BACKSPACE = '\b';
	public static final char TAB = '\t';
	public static final char LINE_FEED = '\n';
	public static final char FORM_FEED = '\f';
	public static final char CARRIAGE_RETURN = '\r';
	public static final char BACKSLASH = '\\';

	/**
	 * Character array containing the possible digits,
	 * indexed appropriately.
	 */
	public static final char[] DIGITS = {
		'0','1','2','3','4','5','6','7','8','9',
		'A','B','C','D','E','F','G','H','I','J',
		'K','L','M','N','O','P','Q','R','S','T',
		'U','V','W','X','Y','Z'};


	/**
	 * Return whether the specified characters are are equal, ignoring case.
	 * @see String#regionMatches(boolean, int, String, int, int)
	 */
	public static boolean equalsIgnoreCase(char c1, char c2) {
		// something about the Georgian alphabet requires us to check lower case also
		return (c1 == c2)
				|| (Character.toUpperCase(c1) == Character.toUpperCase(c2))
				|| (Character.toLowerCase(c1) == Character.toLowerCase(c2));
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CharacterTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
