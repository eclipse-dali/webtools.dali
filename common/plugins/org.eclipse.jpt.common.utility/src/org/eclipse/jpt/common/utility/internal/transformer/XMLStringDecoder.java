/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This transformer will convert a string with any XML <em>character references</em>
 * by replacing the <em>character references</em> with the characters
 * themselves: <code>"&amp;#x2f;" => '/'</code>
 * @see XMLStringEncoder
 */
public final class XMLStringDecoder
	extends AbstractTransformer<String, String>
	implements Serializable
{
	public static final Transformer<String, String> INSTANCE = new XMLStringDecoder();

	public static Transformer<String, String> instance() {
		return INSTANCE;
	}
	/**
	 * Construct a decoder that converts XML character references
	 * into the corresponding characters.
	 */
	private XMLStringDecoder() {
		super();
	}

	/**
	 * Return the specified string with any XML character references
	 * replaced by the characters themselves.
	 */
	@Override
	protected String transform_(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		StringBuilder temp = new StringBuilder();	// performance tweak
		this.decode(sb, new StringReader(s), temp);
		return sb.toString();
	}

	private void decode(StringBuilder sb, Reader reader, StringBuilder temp) {
		try {
			this.decode_(sb, reader, temp);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void decode_(StringBuilder sb, Reader reader, StringBuilder temp) throws IOException {
		int c = reader.read();
		while (c != -1) {
			if (c == '&') {
				this.decodeCharacterReference(sb, reader, temp);
			} else {
				sb.append((char) c);
			}
			c = reader.read();
		}
		reader.close();
	}

	private void decodeCharacterReference(StringBuilder sb, Reader reader, StringBuilder temp) throws IOException {
		int c = reader.read();
		this.checkChar(c, '#');
		c = reader.read();
		this.checkChar(c, 'x');

		temp.setLength(0);  // re-use temp
		c = reader.read();
		while (c != ';') {
			this.checkEndOfStream(c);
			temp.append((char) c);
			c = reader.read();
		}
		String charValue = temp.toString();
		if (charValue.length() == 0) {
			throw new IllegalStateException("missing numeric string"); //$NON-NLS-1$
		}
		sb.append((char) Integer.parseInt(charValue, 16));
	}

	private void checkChar(int c, int expected) {
		this.checkEndOfStream(c);
		if (c != expected) {
			throw new IllegalStateException("expected '" + (char) expected + "', but encountered '" + (char) c + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	private void checkEndOfStream(int c) {
		if (c == -1) {
			throw new IllegalStateException("unexpected end of string"); //$NON-NLS-1$
		}
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
