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
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.InvalidReader;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class InvalidReaderTests
	extends TestCase
{
	private Reader invalidReader;


	public InvalidReaderTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.invalidReader = InvalidReader.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testClose() throws IOException {
		this.invalidReader.close();
	}

	public void testMark() {
		boolean exCaught = false;
		try {
			this.invalidReader.mark(100);
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMarkSupported() {
		assertFalse(this.invalidReader.markSupported());
	}

	public void testRead() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidReader.read();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReadCharArray() throws IOException {
		char[] cbuf = new char[10];
		boolean exCaught = false;
		try {
			this.invalidReader.read(cbuf);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReadCharArrayIntInt() throws IOException {
		char[] cbuf = new char[10];
		boolean exCaught = false;
		try {
			this.invalidReader.read(cbuf, 3, 2);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReady() throws IOException {
		assertFalse(this.invalidReader.ready());
	}

	public void testReset() {
		boolean exCaught = false;
		try {
			this.invalidReader.reset();
		} catch (IOException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSkip() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidReader.skip(44);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
