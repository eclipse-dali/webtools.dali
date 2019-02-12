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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.CompositeOutputStream;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CompositeOutputStreamTests
	extends TestCase
{
	private OutputStream out1;
	private OutputStream out2;
	private OutputStream tee;


	public CompositeOutputStreamTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.out1 = new ByteArrayOutputStream();
		this.out2 = new ByteArrayOutputStream();
		this.tee = new CompositeOutputStream(this.out1, this.out2);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testWrite() throws Exception {
		String string = "The quick brown fox jumps over the lazy dog.";
		this.tee.write(string.getBytes());
		assertEquals(string, this.out1.toString());
		assertNotSame(string, this.out1.toString());
		assertEquals(string, this.out2.toString());
		assertNotSame(string, this.out2.toString());
	}
}
