/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.BitTools;

public class BitToolsTests extends TestCase {

	public BitToolsTests(String name) {
		super(name);
	}

	public void testAllFlagsAreSetIntInt() {
		assertTrue(BitTools.allFlagsAreSet(0x0003, 0x0001));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0001));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0101));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0103));

		assertFalse(BitTools.allFlagsAreSet(0x0303, 0x1103));
		assertFalse(BitTools.allFlagsAreSet(0x0000, 0x1103));
	}

	public void testAllFlagsAreSetIntIntArray() {
		assertTrue(BitTools.allFlagsAreSet(0x0003, new int[] { 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0100, 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0100, 0x0002, 0x0001 }));

		assertFalse(BitTools.allFlagsAreSet(0x0303, new int[] { 0x1000, 0x0100, 0x0002, 0x0001 }));
		assertFalse(BitTools.allFlagsAreSet(0x0000, new int[] { 0x1000, 0x0100, 0x0002, 0x0001 }));
	}

	public void testAnyFlagsAreSetIntInt() {
		assertTrue(BitTools.anyFlagsAreSet(0x0003, 0x0001));
		assertTrue(BitTools.anyFlagsAreSet(0xFFFF, 0x0001));
		assertTrue(BitTools.anyFlagsAreSet(0x0003, 0xFFFF));

		assertFalse(BitTools.anyFlagsAreSet(0x0303, 0x1010));
		assertFalse(BitTools.anyFlagsAreSet(0x0000, 0xFFFF));
	}

	public void testAnyFlagsAreSetIntIntArray() {
		assertTrue(BitTools.anyFlagsAreSet(0x0003, new int[] { 0x0001 }));
		assertTrue(BitTools.anyFlagsAreSet(0xFFFF, new int[] { 0x0001 }));
		assertTrue(BitTools.anyFlagsAreSet(0x0303, new int[] { 0xF000, 0x0F00, 0x00F0, 0x000F }));

		assertFalse(BitTools.anyFlagsAreSet(0x0303, new int[] { 0x1000, 0x0010 }));
		assertFalse(BitTools.anyFlagsAreSet(0x0000, new int[] { 0xF000, 0x0F00, 0x00F0, 0x000F }));
	}

}
