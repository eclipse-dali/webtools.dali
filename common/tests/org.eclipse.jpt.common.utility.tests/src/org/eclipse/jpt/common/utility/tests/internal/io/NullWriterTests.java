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
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.NullWriter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class NullWriterTests
	extends TestCase
{
	private Writer nullWriter;


	public NullWriterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nullWriter = NullWriter.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWriteCharArrayIntInt() throws IOException {
		this.nullWriter.write(new char[10], 2, 5);
	}

	public void testFlush() throws IOException {
		this.nullWriter.flush();
	}

	public void testClose() throws IOException {
		this.nullWriter.close();
	}

	public void testWriteCharArray() throws IOException {
		char[] charArray = new char[10];
		Arrays.fill(charArray, 'a');
		this.nullWriter.write(charArray);
	}

	public void testWriteInt() throws IOException {
		this.nullWriter.write(10);
	}

	public void testWriteStringIntInt() throws IOException {
		this.nullWriter.write("0123456789", 2, 5);
	}

	public void testWriteString() throws IOException {
		this.nullWriter.write("0123456789");
	}
}
