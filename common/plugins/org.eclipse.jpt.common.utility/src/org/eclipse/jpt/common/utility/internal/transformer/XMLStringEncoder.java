/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This transformer will replace any of a specified set of characters with an XML
 * <em>character reference</em>: <code>'/' => "&amp;#x2f;"</code>
 * @see XMLStringDecoder
 */
public class XMLStringEncoder
	implements Transformer<String, String>
{
	/** The set of characters to be converted into XML character references. */
	private final char[] chars;

	/** Cache the value of the highest character in the set above. */
	private final char maxChar;


	/**
	 * Construct an encoder that converts the specified set of characters
	 * into XML character references.
	 */
	public XMLStringEncoder(char[] chars) {
		super();
		if (chars == null) {
			throw new NullPointerException();
		}
		// the ampersand must be included since it is the escape character
		if (ArrayTools.contains(chars, '&')) {
			this.chars = chars;
		} else {
			this.chars = ArrayTools.add(chars, '&');
		}
		this.maxChar = this.calculateMaxInvalidFileNameChar();
	}

	/**
	 * Calculate the maximum value of the set of characters to be converted
	 * into XML character references. This will be used to short-circuit the
	 * search for a character in the set.
	 * @see #charIsToBeEncoded(char)
	 */
	private char calculateMaxInvalidFileNameChar() {
		char[] localChars = this.chars;
		char max = 0;
		for (int i = localChars.length; i-- > 0; ) {
			char c = localChars[i];
			if (max < c) {
				max = c;
			}
		}
		return max;
	}

	/**
	 * Return the specified string with any characters in the set
	 * replaced with XML character references.
	 */
	@Override
	public String transform(String s) {
		int len = s.length();
		// allow for a few encoded characters
		StringBuilder sb = new StringBuilder(len + 20);
		for (int i = 0; i < len; i++) {
			this.append(sb, s.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * Append the specified character to the string buffer,
	 * converting it to an XML character reference if necessary.
	 */
	private void append(StringBuilder sb, char c) {
		if (this.charIsToBeEncoded(c)) {
			this.appendCharacterReference(sb, c);
		} else {
			sb.append(c);
		}
	}

	/**
	 * Return whether the specified character is one of the characters
	 * to be converted to XML character references.
	 */
	private boolean charIsToBeEncoded(char c) {
		return (c <= this.maxChar) && ArrayTools.contains(this.chars, c);
	}

	/**
	 * Append the specified character's XML character reference to the
	 * specified string buffer (e.g. <code>'/' => "&amp;#x2f;"</code>).
	 */
	private void appendCharacterReference(StringBuilder sb, char c) {
		sb.append("&#x"); //$NON-NLS-1$
		sb.append(Integer.toString(c, 16));
		sb.append(';');
	}

	public char[] getChars() {
		return this.chars;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, Arrays.toString(this.chars));
	}
}
