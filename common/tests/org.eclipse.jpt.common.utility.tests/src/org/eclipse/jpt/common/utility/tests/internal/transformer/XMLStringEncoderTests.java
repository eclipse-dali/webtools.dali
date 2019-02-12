/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.transformer;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.FileTools;
import org.eclipse.jpt.common.utility.internal.transformer.XMLStringEncoder;

@SuppressWarnings("nls")
public class XMLStringEncoderTests
	extends TestCase
{
	public XMLStringEncoderTests(String name) {
		super(name);
	}

	public void testEncodeNoCharacterSequences() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "foo";
		assertEquals(s, encoder.transform(s));

		s = "123foo123";
		assertEquals(s, encoder.transform(s));
	}

	public void testEncodeCharacterSequences() {
		XMLStringEncoder encoder = new XMLStringEncoder(FileTools.INVALID_FILENAME_CHARACTERS);

		String s = "?foo?";
		String expected = "&#x3f;foo&#x3f;";
		assertEquals(expected, encoder.transform(s));

		s = "?foo&123";
		expected = "&#x3f;foo&#x26;123";
		assertEquals(expected, encoder.transform(s));
	}
}
