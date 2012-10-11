/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

public final class ByteArrayTools {

	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];


	/**
	 * Convert the specified byte array to the corresponding string of
	 * hexadecimal characters.
	 * @see StringTools#convertHexStringToByteArray(String)
	 */
	public static String convertToHexString(byte[] bytes) {
		int bytesLength = bytes.length;
		return (bytesLength == 0) ? StringTools.EMPTY_STRING : convertToHexString(bytes, bytesLength);
	}

	/**
	 * Pre-condition: the byte array is not empty
	 */
	private static String convertToHexString(byte[] bytes, int bytesLength) {
		return new String(convertToHexCharArray(bytes, bytesLength));
	}

	/**
	 * Convert the specified byte array to the corresponding string of
	 * hexadecimal characters.
	 * @see CharArrayTools#convertHexStringToByteArray(char[])
	 */
	public static char[] convertToHexCharArray(byte[] bytes) {
		int bytesLength = bytes.length;
		return (bytesLength == 0) ? CharArrayTools.EMPTY_CHAR_ARRAY : convertToHexCharArray(bytes, bytesLength);
	}

	/**
	 * Pre-condition: the byte array is not empty
	 */
	private static char[] convertToHexCharArray(byte[] bytes, int bytesLength) {
		int stringLength = bytesLength << 1;
		char[] digits = CharacterTools.DIGITS;
		char[] string = new char[stringLength];
		for (int bi = bytesLength - 1, si = stringLength - 2; bi >= 0; bi--, si -= 2) {
			int b = bytes[bi] & 0xFF; // clear any sign bits
			string[si] = digits[b >> 4]; // first nibble
			string[si + 1] = digits[b & 0xF]; // second nibble
		}
		return string;
	}


	// ********** constructor **********

	/*
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ByteArrayTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
