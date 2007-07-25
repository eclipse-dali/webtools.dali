/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jdtutility;

import org.eclipse.jpt.core.internal.jdtutility.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.utility.internal.ClassTools;

public class DefaultAnnotationEditFormatterTests extends AnnotationTestCase {


	// ********** TestCase behavior **********

	public DefaultAnnotationEditFormatterTests(String name) {
		super(name);
	}


	// ********** tests **********

	public void testCommaLength() throws Exception {
		assertEquals(1, this.commaLength(","));
		assertEquals(1, this.commaLength(", "));
		assertEquals(1, this.commaLength(",   "));

		assertEquals(2, this.commaLength(" ,"));
		assertEquals(2, this.commaLength(" , "));
		assertEquals(2, this.commaLength(" ,   "));

		assertEquals(3, this.commaLength("  ,"));
		assertEquals(3, this.commaLength("  , "));
		assertEquals(3, this.commaLength("  ,   "));

		assertEquals(0, this.commaLength("  ,,,"));
		assertEquals(0, this.commaLength("  ,,, "));
		assertEquals(0, this.commaLength("  ,   ,"));

		assertEquals(0, this.commaLength("  ,x"));
		assertEquals(0, this.commaLength("  ,x "));
		assertEquals(0, this.commaLength("  ,   x"));

		assertEquals(0, this.commaLength("x  ,"));
		assertEquals(0, this.commaLength("x  , "));
		assertEquals(0, this.commaLength("x  ,   "));
	}

	private int commaLength(String s) {
		Integer len = (Integer) ClassTools.executeMethod(DefaultAnnotationEditFormatter.instance(), "commaLength", String.class, s);
		return len.intValue();
	}

	public void testStringIsAnnotation() throws Exception {
		assertTrue(this.stringIsAnnotation("@F"));
		assertTrue(this.stringIsAnnotation("@Foo"));
		assertTrue(this.stringIsAnnotation("@org.bar.Foo"));

		assertFalse(this.stringIsAnnotation(""));
		assertFalse(this.stringIsAnnotation("@"));
		assertFalse(this.stringIsAnnotation("Foo"));
		assertFalse(this.stringIsAnnotation("Foo@"));
	}

	private boolean stringIsAnnotation(String s) {
		Boolean b = (Boolean) ClassTools.executeMethod(DefaultAnnotationEditFormatter.instance(), "stringIsAnnotation", String.class, s);
		return b.booleanValue();
	}

}
