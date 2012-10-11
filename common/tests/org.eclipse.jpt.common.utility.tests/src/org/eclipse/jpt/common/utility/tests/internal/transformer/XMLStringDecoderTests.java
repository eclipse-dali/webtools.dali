/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.transformer;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.FileTools;
import org.eclipse.jpt.common.utility.internal.transformer.XMLStringDecoder;
import org.eclipse.jpt.common.utility.internal.transformer.XMLStringEncoder;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class XMLStringDecoderTests
	extends TestCase
{
	public XMLStringDecoderTests(String name) {
		super(name);
	}

	public void testDenormalizeValidFileName() {
		Transformer<String, String> decoder = XMLStringDecoder.instance();

		String s = "foo";
		assertEquals(s, decoder.transform(s));

		s = "123foo123";
		assertEquals(s, decoder.transform(s));
	}

	public void testDenormalizeInvalidFileName() {
		Transformer<String, String> decoder = XMLStringDecoder.instance();

		String s = "&#x3f;foo&#x3f;";
		String expected = "?foo?";
		assertEquals(expected, decoder.transform(s));

		s = "&#x3f;foo&#x26;123";
		expected = "?foo&123";
		assertEquals(expected, decoder.transform(s));
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
		String actual = encoder.transform(s);
		Transformer<String, String> decoder = XMLStringDecoder.instance();
		assertEquals(s, decoder.transform(actual));
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
		Transformer<String, String> decoder = XMLStringDecoder.instance();
		boolean exCaught = false;
		try {
			s = decoder.transform(s);
			fail(s);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidCharacterSequence8() {
		String s = "foo&#xZZZZ;";
		Transformer<String, String> decoder = XMLStringDecoder.instance();
		boolean exCaught = false;
		try {
			s = decoder.transform(s);
			fail(s);
		} catch (NumberFormatException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

}
