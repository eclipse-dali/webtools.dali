/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.FileTools;
import org.eclipse.jpt.utility.internal.XMLStringEncoder;

@SuppressWarnings("nls")
public class XMLStringEncoderTests extends TestCase {

	public XMLStringEncoderTests(String name) {
		super(name);
	}

	public void testEncodeNoCharacterSequences() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "foo";
		assertEquals(s, encoder.encode(s));

		s = "123foo123";
		assertEquals(s, encoder.encode(s));
	}

	public void testEncodeCharacterSequences() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "?foo?";
		String expected = "&#x3f;foo&#x3f;";
		assertEquals(expected, encoder.encode(s));

		s = "?foo&123";
		expected = "&#x3f;foo&#x26;123";
		assertEquals(expected, encoder.encode(s));
	}

	public void testDenormalizeValidFileName() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "foo";
		assertEquals(s, encoder.decode(s));

		s = "123foo123";
		assertEquals(s, encoder.decode(s));
	}

	public void testDenormalizeInvalidFileName() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "&#x3f;foo&#x3f;";
		String expected = "?foo?";
		assertEquals(expected, encoder.decode(s));

		s = "&#x3f;foo&#x26;123";
		expected = "?foo&123";
		assertEquals(expected, encoder.decode(s));
	}

	public void testRoundTripNoCharacterSequences() {
		this.verifyRoundTrip("foo");
		this.verifyRoundTrip("123foo456");
	}

	public void testRoundTripCharacterSequences() {
		this.verifyRoundTrip("?foo?");
		this.verifyRoundTrip("?foo&123&&&&&&>>>>");
	}

	private void verifyRoundTrip(String s) {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);
		String actual = encoder.encode(s);
		assertEquals(s, encoder.decode(actual));
	}

	public void testInvalidCharacterSequence1() {
		this.verifyIllegalStateException("foo&");
	}

	public void testInvalidCharacterSequence2() {
		this.verifyIllegalStateException("foo&#");
	}

	public void testInvalidCharacterSequence3() {
		this.verifyIllegalStateException("foo&#x");
	}

	public void testInvalidCharacterSequence4() {
		this.verifyIllegalStateException("foo&#x3");
	}

	public void testInvalidCharacterSequence5() {
		this.verifyIllegalStateException("foo&#x;");
	}

	public void testInvalidCharacterSequence6() {
		this.verifyIllegalStateException("foo&A");
	}

	public void testInvalidCharacterSequence7() {
		this.verifyIllegalStateException("foo&#A");
	}

	private void verifyIllegalStateException(String s) {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);
		boolean exCaught = false;
		try {
			s = encoder.decode(s);
			fail(s);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidCharacterSequence8() {
		String s = "foo&#xZZZZ;";
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);
		boolean exCaught = false;
		try {
			s = encoder.decode(s);
			fail(s);
		} catch (NumberFormatException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

}
