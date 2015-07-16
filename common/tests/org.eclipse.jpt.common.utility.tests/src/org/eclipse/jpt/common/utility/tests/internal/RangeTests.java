/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.Range;

public class RangeTests
	extends TestCase
{
	public RangeTests(String name) {
		super(name);
	}

	public void testIncludes() {
		Range range = new Range(5, 17);
		assertFalse(range.includes(-55));
		assertFalse(range.includes(0));
		assertFalse(range.includes(4));
		assertTrue(range.includes(5));
		assertTrue(range.includes(6));
		assertTrue(range.includes(16));
		assertTrue(range.includes(17));
		assertFalse(range.includes(18));
		assertFalse(range.includes(200));
	}

	public void testIncludes2() {
		Range range = new Range(17, 5);
		assertFalse(range.includes(-55));
		assertFalse(range.includes(0));
		assertFalse(range.includes(4));
		assertTrue(range.includes(5));
		assertTrue(range.includes(6));
		assertTrue(range.includes(16));
		assertTrue(range.includes(17));
		assertFalse(range.includes(18));
		assertFalse(range.includes(200));
	}

	public void testEquals() {
		Range range1 = new Range(5, 17);
		Range range2 = new Range(5, 17);
		assertNotSame(range1, range2);
		assertEquals(range1, range1);
		assertEquals(range1, range2);
		assertEquals(range2, range1);
		assertEquals(range1.hashCode(), range2.hashCode());

		range2 = new Range(17, 5);
		assertEquals(range1, range2);
		assertEquals(range2, range1);
		assertEquals(range1.hashCode(), range2.hashCode());

		range2 = new Range(5, 15);
		assertFalse(range1.equals(range2));
		assertFalse(range2.equals(range1));
	}

	public void testClone() {
		Range range1 = new Range(5, 17);
		Range range2 = range1.clone();
		assertNotSame(range1, range2);
		assertEquals(range1, range1);
		assertEquals(range1, range2);
		assertEquals(range2, range1);
		assertEquals(range1.hashCode(), range2.hashCode());
	}

	public void testSerialization() throws Exception {
		Range range1 = new Range(5, 17);
		Range range2 = TestTools.serialize(range1);
		assertNotSame(range1, range2);
		assertEquals(range1, range1);
		assertEquals(range1, range2);
		assertEquals(range2, range1);
		assertEquals(range1.hashCode(), range2.hashCode());
	}
}
