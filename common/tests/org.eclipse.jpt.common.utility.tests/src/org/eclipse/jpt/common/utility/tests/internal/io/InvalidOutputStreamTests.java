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
import java.io.OutputStream;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.InvalidOutputStream;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class InvalidOutputStreamTests
	extends TestCase
{
	private OutputStream invalidOutputStream;


	public InvalidOutputStreamTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.invalidOutputStream = InvalidOutputStream.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testClose() throws IOException {
		this.invalidOutputStream.close();
	}

	public void testFlush() throws IOException {
		this.invalidOutputStream.flush();
	}

	public void testWriteByteArray() throws IOException {
		byte[] b = new byte[10];
		boolean exCaught = false;
		try {
			this.invalidOutputStream.write(b);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteByteArrayIntInt() throws IOException {
		byte[] b = new byte[10];
		boolean exCaught = false;
		try {
			this.invalidOutputStream.write(b, 3, 2);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWriteInt() throws IOException {
		boolean exCaught = false;
		try {
			this.invalidOutputStream.write(77);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
