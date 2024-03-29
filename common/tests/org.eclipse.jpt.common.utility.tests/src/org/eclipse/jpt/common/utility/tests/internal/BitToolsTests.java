/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;

public class BitToolsTests
	extends TestCase
{
	public BitToolsTests(String name) {
		super(name);
	}

	public void testFlagIsSetIntInt() {
		assertTrue(BitTools.flagIsSet(0x0003, 0x0001));
		assertTrue(BitTools.flagIsSet(0x0303, 0x0001));
		assertTrue(BitTools.flagIsSet(0x0303, 0x0101));
		assertTrue(BitTools.flagIsSet(0x0303, 0x0103));

		assertFalse(BitTools.flagIsSet(0x0303, 0x1103));
		assertFalse(BitTools.flagIsSet(0x0000, 0x1103));
	}

	public void testSetFlagIntInt() {
		assertEquals(0x0003, BitTools.setFlag(0x0003, 0x0001));

		assertEquals(0x1303, BitTools.setFlag(0x0303, 0x1103));
		assertEquals(0x1103, BitTools.setFlag(0x0000, 0x1103));
	}

	public void testFlagIsOffIntInt() {
		assertFalse(BitTools.flagIsOff(0x0003, 0x0001));
		assertFalse(BitTools.flagIsOff(0x0303, 0x0001));
		assertFalse(BitTools.flagIsOff(0x0303, 0x0101));
		assertFalse(BitTools.flagIsOff(0x0303, 0x0103));

		assertTrue(BitTools.flagIsOff(0x2204, 0x1103));
		assertTrue(BitTools.flagIsOff(0x0000, 0x1103));
	}

	public void testClearFlagIntInt() {
		assertEquals(0x0002, BitTools.clearFlag(0x0003, 0x0001));

		assertEquals(0x0200, BitTools.clearFlag(0x0303, 0x1103));
		assertEquals(0x0000, BitTools.clearFlag(0x0000, 0x1103));
	}

	public void testOnlyFlagIsSetIntInt() {
		assertFalse(BitTools.onlyFlagIsSet(0x0003, 0x0001));
		assertTrue(BitTools.onlyFlagIsSet(0x0001, 0x0001));

		assertFalse(BitTools.onlyFlagIsSet(0x0303, 0x0001));
		assertTrue(BitTools.onlyFlagIsSet(0x0001, 0x0001));

		assertFalse(BitTools.onlyFlagIsSet(0x0303, 0x0101));
		assertTrue(BitTools.onlyFlagIsSet(0x0101, 0x0101));

		assertFalse(BitTools.onlyFlagIsSet(0x0303, 0x0103));
		assertTrue(BitTools.onlyFlagIsSet(0x0103, 0x0103));

		assertFalse(BitTools.onlyFlagIsSet(0x0303, 0x1103));
		assertTrue(BitTools.onlyFlagIsSet(0x1103, 0x1103));

		assertFalse(BitTools.onlyFlagIsSet(0x0000, 0x1103));
		assertTrue(BitTools.onlyFlagIsSet(0x0103, 0x0103));
	}

	public void testOnlyFlagIsOffIntInt() {
		assertFalse(BitTools.onlyFlagIsOff(0x0003, 0x0001));
		assertFalse(BitTools.onlyFlagIsOff(0x0303, 0x0001));
		assertTrue(BitTools.onlyFlagIsOff(0xFFFFFFFE, 0x0001));

		assertFalse(BitTools.onlyFlagIsOff(0x0303, 0x0101));
		assertTrue(BitTools.onlyFlagIsOff(0xFFFFFEFE, 0x0101));

		assertFalse(BitTools.onlyFlagIsOff(0x0303, 0x0103));
		assertTrue(BitTools.onlyFlagIsOff(0xFFFFFEFC, 0x0103));

		assertFalse(BitTools.onlyFlagIsOff(0x0303, 0x1103));
		assertTrue(BitTools.onlyFlagIsOff(0xFFFFEEFC, 0x1103));
	}

	public void testAllFlagsAreSetIntInt() {
		assertTrue(BitTools.allFlagsAreSet(0x0003, 0x0001));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0001));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0101));
		assertTrue(BitTools.allFlagsAreSet(0x0303, 0x0103));

		assertFalse(BitTools.allFlagsAreSet(0x0303, 0x1103));
		assertFalse(BitTools.allFlagsAreSet(0x0000, 0x1103));
	}

	public void testSetAllFlagsIntInt() {
		assertEquals(0x0003, BitTools.setAllFlags(0x0003, 0x0001));

		assertEquals(0x1303, BitTools.setAllFlags(0x0303, 0x1103));
		assertEquals(0x1103, BitTools.setAllFlags(0x0000, 0x1103));
	}

	public void testAllFlagsAreOffIntInt() {
		assertFalse(BitTools.allFlagsAreOff(0x0003, 0x0001));
		assertFalse(BitTools.allFlagsAreOff(0x0303, 0x0001));
		assertFalse(BitTools.allFlagsAreOff(0x0303, 0x0101));
		assertFalse(BitTools.allFlagsAreOff(0x0303, 0x0103));

		assertTrue(BitTools.allFlagsAreOff(0x2204, 0x1103));
		assertTrue(BitTools.allFlagsAreOff(0x0000, 0x1103));
	}

	public void testClearAllFlagsIntInt() {
		assertEquals(0x0002, BitTools.clearAllFlags(0x0003, 0x0001));

		assertEquals(0x0200, BitTools.clearAllFlags(0x0303, 0x1103));
		assertEquals(0x0000, BitTools.clearAllFlags(0x0000, 0x1103));
	}

	public void testOnlyFlagsAreSetIntInt() {
		assertFalse(BitTools.onlyFlagsAreSet(0x0003, 0x0001));
		assertTrue(BitTools.onlyFlagsAreSet(0x0001, 0x0001));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, 0x0001));
		assertTrue(BitTools.onlyFlagsAreSet(0x0001, 0x0001));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, 0x0101));
		assertTrue(BitTools.onlyFlagsAreSet(0x0101, 0x0101));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, 0x0103));
		assertTrue(BitTools.onlyFlagsAreSet(0x0103, 0x0103));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, 0x1103));
		assertTrue(BitTools.onlyFlagsAreSet(0x1103, 0x1103));

		assertFalse(BitTools.onlyFlagsAreSet(0x0000, 0x1103));
		assertTrue(BitTools.onlyFlagsAreSet(0x0103, 0x0103));
	}

	public void testOnlyFlagsAreOffIntInt() {
		assertFalse(BitTools.onlyFlagsAreOff(0x0003, 0x0001));
		assertFalse(BitTools.onlyFlagsAreOff(0x0303, 0x0001));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFFFE, 0x0001));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, 0x0101));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFEFE, 0x0101));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, 0x0103));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFEFC, 0x0103));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, 0x1103));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFEEFC, 0x1103));
	}

	public void testAnyFlagsAreSetIntInt() {
		assertTrue(BitTools.anyFlagsAreSet(0x0003, 0x0001));
		assertTrue(BitTools.anyFlagsAreSet(0xFFFF, 0x0001));
		assertTrue(BitTools.anyFlagsAreSet(0x0003, 0xFFFF));

		assertFalse(BitTools.anyFlagsAreSet(0x0303, 0x1010));
		assertFalse(BitTools.anyFlagsAreSet(0x0000, 0xFFFF));
	}

	public void testAnyFlagsAreOffIntInt() {
		assertTrue(BitTools.anyFlagsAreOff(0x333E, 0x0001));
		assertTrue(BitTools.anyFlagsAreOff(0xFFFE, 0x0001));
		assertTrue(BitTools.anyFlagsAreOff(0x0003, 0xFFFF));

		assertFalse(BitTools.anyFlagsAreOff(0x7373, 0x1010));
		assertFalse(BitTools.anyFlagsAreOff(0xFFFF, 0xFFFF));
	}

	public void testAllFlagsAreSetIntIntArray() {
		assertTrue(BitTools.allFlagsAreSet(0x0003, new int[] { 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0100, 0x0001 }));
		assertTrue(BitTools.allFlagsAreSet(0x0303, new int[] { 0x0100, 0x0002, 0x0001 }));

		assertFalse(BitTools.allFlagsAreSet(0x0303, new int[] { 0x1000, 0x0100, 0x0002, 0x0001 }));
		assertFalse(BitTools.allFlagsAreSet(0x0000, new int[] { 0x1000, 0x0100, 0x0002, 0x0001 }));
	}

	public void testSetAllFlagsIntIntArray() {
		assertEquals(0x0003, BitTools.setAllFlags(0x0003, new int[] { 0x0001 }));

		assertEquals(0x1303, BitTools.setAllFlags(0x0303, new int[] { 0x0003, 0x1100 }));
		assertEquals(0x1103, BitTools.setAllFlags(0x0000, new int[] { 0x0003, 0x1100 }));
	}

	public void testAllFlagsAreOffIntIntArray() {
		assertFalse(BitTools.allFlagsAreOff(0x0003, new int[] { 0x0001 }));
		assertFalse(BitTools.allFlagsAreOff(0x0303, new int[] { 0x0001 }));
		assertFalse(BitTools.allFlagsAreOff(0x0303, new int[] { 0x0100, 0x0001 }));
		assertFalse(BitTools.allFlagsAreOff(0x0303, new int[] { 0x0100, 0x0002, 0x0001 }));

		assertTrue(BitTools.allFlagsAreOff(0x0303, new int[] { 0x1000, 0x0400, 0x0020, 0x0000 }));
		assertTrue(BitTools.allFlagsAreOff(0x0000, new int[] { 0x1000, 0x0100, 0x0002, 0x0001 }));
	}

	public void testClearAllFlagsIntIntArray() {
		assertEquals(0x0002, BitTools.clearAllFlags(0x0003, new int[] { 0x0001 }));

		assertEquals(0x0200, BitTools.clearAllFlags(0x0303, new int[] { 0x0003, 0x1100 }));
		assertEquals(0x0000, BitTools.clearAllFlags(0x0000, new int[] { 0x0003, 0x1100 }));
	}

	public void testOnlyFlagsAreSetIntIntArray() {
		assertFalse(BitTools.onlyFlagsAreSet(0x0003, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x0001, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x0001, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, new int[] { 0x001, 0x0100, 0x0000, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x0101, new int[] { 0x001, 0x0100, 0x0000, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, new int[] { 0x001, 0x0100, 0x0002, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x0103, new int[] { 0x001, 0x0100, 0x0002, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreSet(0x0303, new int[] { 0x011, 0x0100, 0x0002, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x1103, new int[] { 0x1100, 0x0100, 0x0002, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreSet(0x0000, new int[] { 0x011, 0x0100, 0x0002, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreSet(0x0103, new int[] { 0x0101, 0x0100, 0x0002, 0x0001 }));
	}

	public void testOnlyFlagsAreOffIntIntArray() {
		assertFalse(BitTools.onlyFlagsAreOff(0x0003, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));
		assertFalse(BitTools.onlyFlagsAreOff(0x0303, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFFFE, new int[] { 0x001, 0x0000, 0x0000, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, new int[] { 0x001, 0x0100, 0x0000, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFEFE, new int[] { 0x001, 0x0100, 0x0000, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, new int[] { 0x001, 0x0100, 0x0002, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFFEFC, new int[] { 0x001, 0x0100, 0x0002, 0x0001 }));

		assertFalse(BitTools.onlyFlagsAreOff(0x0303, new int[] { 0x1100, 0x0100, 0x0002, 0x0001 }));
		assertTrue(BitTools.onlyFlagsAreOff(0xFFFFEEFC, new int[] { 0x1100, 0x0100, 0x0002, 0x0001 }));
	}

	public void testAnyFlagsAreSetIntIntArray() {
		assertTrue(BitTools.anyFlagsAreSet(0x0003, new int[] { 0x0001 }));
		assertTrue(BitTools.anyFlagsAreSet(0xFFFF, new int[] { 0x0001 }));
		assertTrue(BitTools.anyFlagsAreSet(0x0303, new int[] { 0xF000, 0x0F00, 0x00F0, 0x000F }));

		assertFalse(BitTools.anyFlagsAreSet(0x0303, new int[] { 0x1000, 0x0010 }));
		assertFalse(BitTools.anyFlagsAreSet(0x0000, new int[] { 0xF000, 0x0F00, 0x00F0, 0x000F }));
	}

	public void testAnyFlagsAreOffIntIntArray() {
		assertFalse(BitTools.anyFlagsAreOff(0x0003, new int[] { 0x0001 }));
		assertFalse(BitTools.anyFlagsAreOff(0xFFFF, new int[] { 0x0001 }));
		assertFalse(BitTools.anyFlagsAreOff(0x0303, new int[] { 0x0100, 0x0200, 0x0003, 0x0002 }));

		assertTrue(BitTools.anyFlagsAreOff(0x0303, new int[] { 0x0100, 0x0010 }));
		assertTrue(BitTools.anyFlagsAreOff(0x0000, new int[] { 0xF000, 0x0F00, 0x00F0, 0x000F }));
	}

	public void testOrFlags() {
		assertEquals(0x0001, BitTools.orFlags(new int[] { 0x0001, 0x0000 }));
		assertEquals(0x0011, BitTools.orFlags(new int[] { 0x0001, 0x0011 }));
		assertEquals(0xF011, BitTools.orFlags(new int[] { 0x0001, 0x0011, 0xF000 }));
	}

	public void testAndFlags() {
		assertEquals(0x0001, BitTools.andFlags(new int[] { 0x0001, 0x0001 }));
		assertEquals(0x0001, BitTools.andFlags(new int[] { 0x0001, 0x0011 }));
		assertEquals(0x0000, BitTools.andFlags(new int[] { 0x0001, 0x0011, 0xF000 }));
		assertEquals(0x0001, BitTools.andFlags(new int[] { 0x0001, 0x0011, 0xF001 }));
	}

	public void testXorFlags() {
		assertEquals(0x0001, BitTools.xorFlags(new int[] { 0x0001, 0x0000 }));
		assertEquals(0x0010, BitTools.xorFlags(new int[] { 0x0001, 0x0011 }));
		assertEquals(0xF010, BitTools.xorFlags(new int[] { 0x0001, 0x0011, 0xF000 }));
		assertEquals(0xFF11, BitTools.xorFlags(new int[] { 0x0001, 0x0011, 0xF000, 0x0F01 }));
		assertEquals(0xF010, BitTools.xorFlags(new int[] { 0x0001, 0x0011, 0xF000, 0x0F01, 0x0F01 }));
	}

	public void testIsEven() {
		assertTrue(BitTools.isEven(-22));
		assertTrue(BitTools.isEven(0));
		assertTrue(BitTools.isEven(22));

		assertFalse(BitTools.isEven(-21));
		assertFalse(BitTools.isEven(21));
	}

	public void testIsOdd() {
		assertFalse(BitTools.isOdd(-22));
		assertFalse(BitTools.isOdd(0));
		assertFalse(BitTools.isOdd(22));

		assertTrue(BitTools.isOdd(-21));
		assertTrue(BitTools.isOdd(21));
	}

	public void testHalf() {
		assertEquals(-11, BitTools.half(-22));
		assertEquals(0, BitTools.half(0));
		assertEquals(11, BitTools.half(22));

		assertEquals(-11, BitTools.half(-21));
		assertEquals(10, BitTools.half(21));
	}

	public void testTwice() {
		assertEquals(-22, BitTools.twice(-11));
		assertEquals(0, BitTools.twice(0));
		assertEquals(22, BitTools.twice(11));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(BitTools.class);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

}
