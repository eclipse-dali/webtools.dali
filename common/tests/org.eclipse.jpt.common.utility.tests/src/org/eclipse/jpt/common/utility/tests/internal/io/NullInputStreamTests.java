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
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.NullInputStream;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NullInputStreamTests
	extends TestCase
{
	private InputStream nullInputStream;


	public NullInputStreamTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nullInputStream = NullInputStream.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAvailable() throws IOException {
		assertEquals(0, this.nullInputStream.available());
	}

	public void testClose() throws IOException {
		this.nullInputStream.close();
	}

	public void testMark() {
		this.nullInputStream.mark(5);
	}

	public void testMarkSupported() {
		assertFalse(this.nullInputStream.markSupported());
	}

	public void testRead() throws IOException {
		assertEquals(-1, this.nullInputStream.read());
	}

	public void testReadByteArray() throws IOException {
		byte[] expected = new byte[10];
		Arrays.fill(expected, (byte) 7);
		byte[] actual = new byte[10];
		Arrays.fill(actual, (byte) 7);
		assertEquals(-1, this.nullInputStream.read(actual));
		assertTrue(Arrays.equals(actual, expected));
	}

	public void testReadByteArrayIntInt() throws IOException {
		byte[] expected = new byte[10];
		Arrays.fill(expected, (byte) 7);
		byte[] actual = new byte[10];
		Arrays.fill(actual, (byte) 7);
		assertEquals(-1, this.nullInputStream.read(actual, 2, 5));
		assertTrue(Arrays.equals(actual, expected));
	}

	public void testReset() {
		boolean exCaught = false;
		try {
			this.nullInputStream.reset();
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSkip() throws IOException {
		assertEquals(0, this.nullInputStream.skip(5));
	}
}
