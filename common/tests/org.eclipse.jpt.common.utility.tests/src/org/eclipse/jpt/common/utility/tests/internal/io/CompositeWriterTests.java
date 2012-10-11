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

import java.io.CharArrayWriter;
import java.io.Writer;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.CompositeWriter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CompositeWriterTests
	extends TestCase
{
	private Writer writer1;
	private Writer writer2;
	private Writer tee;


	public CompositeWriterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.writer1 = new CharArrayWriter();
		this.writer2 = new CharArrayWriter();
		this.tee = new CompositeWriter(this.writer1, this.writer2);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWrite() throws Exception {
		String string = "The quick brown fox jumps over the lazy dog.";
		this.tee.write(string);
		assertEquals(string, this.writer1.toString());
		assertNotSame(string, this.writer1.toString());
		assertEquals(string, this.writer2.toString());
		assertNotSame(string, this.writer2.toString());
	}
}
