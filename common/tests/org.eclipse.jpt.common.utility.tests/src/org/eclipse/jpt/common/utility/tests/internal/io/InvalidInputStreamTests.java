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

import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.InvalidInputStream;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class InvalidInputStreamTests
	extends TestCase
{
	private InputStream invalidInputStream;


	public InvalidInputStreamTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.invalidInputStream = InvalidInputStream.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAvailable() throws IOException {
		assertEquals(0, this.invalidInputStream.available());
	}

	public void testClose() throws IOException {
		this.invalidInputStream.close();
	}

	public void testMark() {
		this.invalidInputStream.mark(100);
	}

	public void testMarkSupported() {
		assertFalse(this.invalidInputStream.markSupported());
	}

	public void testRead() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidInputStream.read();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReadByteArray() throws IOException {
		byte[] b = new byte[10];
		boolean exCaught = false;
		try {
			this.invalidInputStream.read(b);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReadByteArrayIntInt() throws IOException {
		byte[] b = new byte[10];
		boolean exCaught = false;
		try {
			this.invalidInputStream.read(b, 3, 2);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReset() {
		boolean exCaught = false;
		try {
			this.invalidInputStream.reset();
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSkip() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidInputStream.skip(44);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
