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
import java.io.Reader;
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.NullReader;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NullReaderTests
	extends TestCase
{
	private Reader nullReader;


	public NullReaderTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nullReader = NullReader.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testClose() throws IOException {
		this.nullReader.close();
	}

	public void testRead() throws IOException {
		assertEquals(-1, this.nullReader.read());
	}

	public void testReadCharArray() throws IOException {
		char[] expected = new char[10];
		Arrays.fill(expected, 'a');
		char[] actual = new char[10];
		Arrays.fill(actual, 'a');
		assertEquals(-1, this.nullReader.read(actual));
		assertTrue(Arrays.equals(actual, expected));
	}

	public void testReadCharArrayIntInt() throws IOException {
		char[] expected = new char[10];
		Arrays.fill(expected, 'a');
		char[] actual = new char[10];
		Arrays.fill(actual, 'a');
		assertEquals(-1, this.nullReader.read(actual, 2, 5));
		assertTrue(Arrays.equals(actual, expected));
	}

	public void testSkip() throws IOException {
		assertEquals(0, this.nullReader.skip(5));
	}

	public void testMark() {
		boolean exCaught = false;
		try {
			this.nullReader.mark(5);
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMarkSupported() {
		assertFalse(this.nullReader.markSupported());
	}

	public void testReady() {
		assertFalse(this.nullReader.markSupported());
	}

	public void testReset() {
		boolean exCaught = false;
		try {
			this.nullReader.reset();
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
