/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.Arrays;
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
		assertEquals("74657374", ByteArrayTools.convertToHexString(s.getBytes())); // Unicode values
	}

	public void testConvertToHexString_negative() throws Exception {
		String s = "café";
		assertEquals("636166E9", ByteArrayTools.convertToHexString(s.getBytes())); // Unicode values
	}

	public void testConvertToHexCharArray() throws Exception {
		String s = "test";
		assertTrue(Arrays.equals("74657374".toCharArray(), ByteArrayTools.convertToHexCharArray(s.getBytes()))); // Unicode values
	}

	public void testConvertToHexCharArray_negative() throws Exception {
		String s = "café";
		assertTrue(Arrays.equals("636166E9".toCharArray(), ByteArrayTools.convertToHexCharArray(s.getBytes()))); // Unicode values
	}
}
