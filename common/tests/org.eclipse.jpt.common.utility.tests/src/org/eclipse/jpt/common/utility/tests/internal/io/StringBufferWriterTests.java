/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.io;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.StringBufferWriter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class StringBufferWriterTests
	extends TestCase
{
	private StringBufferWriter writer;


	public StringBufferWriterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.writer = new StringBufferWriter();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWriteInt() throws Exception {
		this.writer.write('a');
		this.writer.write('b');
		this.writer.write('c');
		assertEquals(3, this.writer.getBuffer().length());
		assertEquals("abc", this.writer.toString());
	}

	public void testWriteCharArray() throws Exception {
		this.writer.write(new char[] {'a', 'b', 'c'});
		this.writer.write(new char[] {'a', 'b', 'c'});
		assertEquals(6, this.writer.getBuffer().length());
		assertEquals("abcabc", this.writer.toString());
	}

	public void testWriteString() throws Exception {
		this.writer.write("abc");
		this.writer.write("abc");
		assertEquals(6, this.writer.getBuffer().length());
		assertEquals("abcabc", this.writer.toString());
	}

	public void testWriteStringIntInt() throws Exception {
		this.writer.write("abc", 1, 2);
		this.writer.write("abc", 2, 1);
		assertEquals(3, this.writer.getBuffer().length());
		assertEquals("bcc", this.writer.toString());
	}
}
