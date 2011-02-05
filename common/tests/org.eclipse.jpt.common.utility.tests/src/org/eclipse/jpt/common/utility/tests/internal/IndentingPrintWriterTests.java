/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.StringWriter;
import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.IndentingPrintWriter;

@SuppressWarnings("nls")
public class IndentingPrintWriterTests extends TestCase {
	StringWriter sw1;
	StringWriter sw2;
	IndentingPrintWriter ipw1;
	IndentingPrintWriter ipw2;

	static final String CR = System.getProperty("line.separator");

	public IndentingPrintWriterTests(String name) {
		super(name);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.sw1 = new StringWriter();
		this.ipw1 = new IndentingPrintWriter(this.sw1);
		this.sw2 = new StringWriter();
		this.ipw2 = new IndentingPrintWriter(this.sw2, "    "); // indent with 4 spaces instead of a tab
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIndent() {
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
		this.ipw1.indent();
		assertEquals("wrong indent level", 1, this.ipw1.getIndentLevel());
	}

	public void testUndent() {
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
		this.ipw1.indent();
		assertEquals("wrong indent level", 1, this.ipw1.getIndentLevel());
		this.ipw1.undent();
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
	}

	public void testIncrementIndentLevel() {
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
		this.ipw1.incrementIndentLevel();
		assertEquals("wrong indent level", 1, this.ipw1.getIndentLevel());
	}

	public void testDecrementIndentLevel() {
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
		this.ipw1.incrementIndentLevel();
		assertEquals("wrong indent level", 1, this.ipw1.getIndentLevel());
		this.ipw1.decrementIndentLevel();
		assertEquals("wrong indent level", 0, this.ipw1.getIndentLevel());
	}

	public void testPrintTab() {
		String expected = "foo0" + CR + "\tfoo1" + CR + "\tfoo1" + CR + "\t\tfoo2" + CR + "\tfoo1" + CR + "\tfoo1" + CR + "foo0" + CR;

		this.ipw1.println("foo0");
		this.ipw1.indent();
		this.ipw1.println("foo1");
		this.ipw1.println("foo1");
		this.ipw1.indent();
		this.ipw1.println("foo2");
		this.ipw1.undent();
		this.ipw1.println("foo1");
		this.ipw1.println("foo1");
		this.ipw1.undent();
		this.ipw1.println("foo0");

		assertEquals("bogus output", expected, this.sw1.toString());
	}

	public void testPrintSpaces() {
		String expected = "foo0" + CR + "    foo1" + CR + "    foo1" + CR + "        foo2" + CR + "    foo1" + CR + "    foo1" + CR + "foo0" + CR;

		this.ipw2.println("foo0");
		this.ipw2.indent();
		this.ipw2.println("foo1");
		this.ipw2.println("foo1");
		this.ipw2.indent();
		this.ipw2.println("foo2");
		this.ipw2.undent();
		this.ipw2.println("foo1");
		this.ipw2.println("foo1");
		this.ipw2.undent();
		this.ipw2.println("foo0");

		assertEquals("bogus output", expected, this.sw2.toString());
	}

}
