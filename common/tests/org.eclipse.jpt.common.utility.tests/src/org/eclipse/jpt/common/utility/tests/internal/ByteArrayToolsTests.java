/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.ByteArrayTools;

@SuppressWarnings("nls")
public class ByteArrayToolsTests
	extends TestCase
{
	public ByteArrayToolsTests(String name) {
		super(name);
	}

	// ********** convert to hex string **********

	public void testConvertToHexString() throws Exception {
		String s = "test";
		assertEquals("74657374", ByteArrayTools.convertToHexString(s.getBytes())); // UTF-8 values
	}

	public void testConvertToHexString_negative() throws Exception {
		String s = "caf\u00E9"; // cafe'
		assertEquals(this.getHexCafe(), ByteArrayTools.convertToHexString(s.getBytes()));
	}

	public void testConvertToHexCharArray() throws Exception {
		String s = "test";
		TestTools.assertEquals("74657374", ByteArrayTools.convertToHexCharArray(s.getBytes()));
	}

	public void testConvertToHexCharArray_negative() throws Exception {
		String s = "caf\u00E9"; // cafe'
		TestTools.assertEquals(this.getHexCafe(), ByteArrayTools.convertToHexCharArray(s.getBytes()));
	}

	private String getHexCafe() {
		return StringToolsTests.getHexCafe();
	}
}
