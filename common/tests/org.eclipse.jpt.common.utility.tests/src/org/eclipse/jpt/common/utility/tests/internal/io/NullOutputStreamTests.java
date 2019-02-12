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
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.NullOutputStream;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NullOutputStreamTests
	extends TestCase
{
	private OutputStream nullOutputStream;


	public NullOutputStreamTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nullOutputStream = NullOutputStream.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testClose() throws IOException {
		this.nullOutputStream.close();
	}

	public void testFlush() throws IOException {
		this.nullOutputStream.flush();
	}

	public void testWriteByteArray() throws IOException {
		byte[] byteArray = new byte[10];
		Arrays.fill(byteArray, (byte) 7);
		this.nullOutputStream.write(byteArray);
	}

	public void testWriteByteArrayIntInt() throws IOException {
		byte[] byteArray = new byte[10];
		Arrays.fill(byteArray, (byte) 7);
		this.nullOutputStream.write(byteArray, 2, 5);
	}

	public void testWriteInt() throws IOException {
		this.nullOutputStream.write(10);
	}
}
