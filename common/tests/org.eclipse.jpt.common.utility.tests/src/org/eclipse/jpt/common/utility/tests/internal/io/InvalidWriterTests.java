/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.io;

import java.io.IOException;
import java.io.Writer;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.InvalidWriter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class InvalidWriterTests
	extends TestCase
{
	private Writer invalidWriter;


	public InvalidWriterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.invalidWriter = InvalidWriter.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testClose() throws IOException {
		this.invalidWriter.close();
	}

	public void testFlush() throws IOException {
		this.invalidWriter.flush();
	}

	public void testWriteCharArray() throws IOException {
		char[] cbuf = new char[10];
		boolean exCaught = false;
		try {
			this.invalidWriter.write(cbuf);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteCharArrayIntInt() throws IOException {
		char[] cbuf = new char[10];
		boolean exCaught = false;
		try {
			this.invalidWriter.write(cbuf, 3, 2);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteInt() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidWriter.write(77);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteString() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidWriter.write("cbuf");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteStringIntInt() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidWriter.write("cbuf", 1, 2);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
