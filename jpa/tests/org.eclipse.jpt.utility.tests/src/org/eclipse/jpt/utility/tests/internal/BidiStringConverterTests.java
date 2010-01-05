/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.BidiStringConverter;

@SuppressWarnings("nls")
public class BidiStringConverterTests extends TestCase {

	public BidiStringConverterTests(String name) {
		super(name);
	}

	public void testDefaultBidiStringConverter_convertToString() throws Exception {
		assertEquals("foo", BidiStringConverter.Default.instance().convertToString("foo"));
		assertNull(BidiStringConverter.Default.instance().convertToString(null));
	}

	public void testDefaultBidiStringConverter_convertToObject() throws Exception {
		assertEquals("foo", BidiStringConverter.Default.instance().convertToObject("foo"));
		assertNull(BidiStringConverter.Default.instance().convertToString(null));
	}

	public void testDefaultBidiStringConverter_toString() throws Exception {
		assertNotNull(BidiStringConverter.Default.instance().toString());
	}

	public void testDefaultBidiStringConverter_serialization() throws Exception {
		BidiStringConverter<?> xxx = TestTools.serialize(BidiStringConverter.Default.instance());
		assertSame(BidiStringConverter.Default.instance(), xxx);
	}

	public void testDisabledBidiStringConverter_convertToString() throws Exception {
		boolean exCaught = false;
		try {
			BidiStringConverter.Disabled.instance().convertToString("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiStringConverter_convertToObject() throws Exception {
		boolean exCaught = false;
		try {
			BidiStringConverter.Disabled.instance().convertToObject("foo");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBidiStringConverter_toString() throws Exception {
		assertNotNull(BidiStringConverter.Disabled.instance().toString());
	}

	public void testDisabledBidiStringConverter_serialization() throws Exception {
		BidiStringConverter<?> xxx = TestTools.serialize(BidiStringConverter.Disabled.instance());
		assertSame(BidiStringConverter.Disabled.instance(), xxx);
	}

	public void testBooleanBidiStringConverter_convertToString() throws Exception {
		assertEquals("true", BidiStringConverter.BooleanConverter.instance().convertToString(Boolean.TRUE));
		assertEquals("false", BidiStringConverter.BooleanConverter.instance().convertToString(Boolean.FALSE));
		assertNull(BidiStringConverter.BooleanConverter.instance().convertToString(null));
	}

	public void testBooleanBidiStringConverter_convertToObject() throws Exception {
		assertEquals(Boolean.TRUE, BidiStringConverter.BooleanConverter.instance().convertToObject("true"));
		assertEquals(Boolean.TRUE, BidiStringConverter.BooleanConverter.instance().convertToObject("TRUE"));
		assertEquals(Boolean.FALSE, BidiStringConverter.BooleanConverter.instance().convertToObject("false"));
		assertEquals(Boolean.FALSE, BidiStringConverter.BooleanConverter.instance().convertToObject("xxxx"));
		assertNull(BidiStringConverter.BooleanConverter.instance().convertToObject(null));
	}

	public void testBooleanBidiStringConverter_toString() throws Exception {
		assertNotNull(BidiStringConverter.BooleanConverter.instance().toString());
	}

	public void testBooleanBidiStringConverter_serialization() throws Exception {
		BidiStringConverter<?> xxx = TestTools.serialize(BidiStringConverter.BooleanConverter.instance());
		assertSame(BidiStringConverter.BooleanConverter.instance(), xxx);
	}

	public void testIntegerBidiStringConverter_convertToString() throws Exception {
		assertEquals("7", BidiStringConverter.IntegerConverter.instance().convertToString(Integer.valueOf(7)));
		assertNull(BidiStringConverter.IntegerConverter.instance().convertToString(null));
	}

	public void testIntegerBidiStringConverter_convertToObject() throws Exception {
		assertEquals(Integer.valueOf(7), BidiStringConverter.IntegerConverter.instance().convertToObject("7"));
		assertNull(BidiStringConverter.IntegerConverter.instance().convertToObject(null));
	}

	public void testIntegerBidiStringConverter_toString() throws Exception {
		assertNotNull(BidiStringConverter.IntegerConverter.instance().toString());
	}

	public void testIntegerBidiStringConverter_serialization() throws Exception {
		BidiStringConverter<?> xxx = TestTools.serialize(BidiStringConverter.IntegerConverter.instance());
		assertSame(BidiStringConverter.IntegerConverter.instance(), xxx);
	}

}
