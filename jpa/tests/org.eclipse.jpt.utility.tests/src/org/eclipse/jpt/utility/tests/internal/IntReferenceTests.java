/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.IntReference;

@SuppressWarnings("nls")
public class IntReferenceTests extends TestCase {

	public IntReferenceTests(String name) {
		super(name);
	}

	public void testCtors() {
		IntReference ir;
		ir = new IntReference();
		assertEquals(0, ir.getValue());
		ir = new IntReference(7);
		assertEquals(7, ir.getValue());
		ir = new IntReference(-7);
		assertEquals(-7, ir.getValue());
	}

	public void testEqualsInt() {
		IntReference ir;
		ir = new IntReference();
		assertTrue(ir.equals(0));
		assertFalse(ir.equals(7));

		ir = new IntReference(7);
		assertTrue(ir.equals(7));
		assertFalse(ir.equals(0));
	}

	public void testNotEqualsInt() {
		IntReference ir;
		ir = new IntReference();
		assertFalse(ir.notEquals(0));
		assertTrue(ir.notEquals(7));

		ir = new IntReference(7);
		assertFalse(ir.notEquals(7));
		assertTrue(ir.notEquals(0));
	}

	public void testIsZero() {
		IntReference ir;
		ir = new IntReference();
		assertTrue(ir.isZero());

		ir = new IntReference(7);
		assertFalse(ir.isZero());
	}

	public void testIsNotZero() {
		IntReference ir;
		ir = new IntReference();
		assertFalse(ir.isNotZero());

		ir = new IntReference(7);
		assertTrue(ir.isNotZero());
	}

	public void testIsGreaterThanInt() {
		IntReference ir;
		ir = new IntReference();
		assertTrue(ir.isGreaterThan(-1));
		assertFalse(ir.isGreaterThan(0));
		assertFalse(ir.isGreaterThan(7));
	}

	public void testIsGreaterThanOrEqualInt() {
		IntReference ir;
		ir = new IntReference();
		assertTrue(ir.isGreaterThanOrEqual(-1));
		assertTrue(ir.isGreaterThanOrEqual(0));
		assertFalse(ir.isGreaterThanOrEqual(7));
	}

	public void testIsLessThanInt() {
		IntReference ir;
		ir = new IntReference();
		assertFalse(ir.isLessThan(-1));
		assertFalse(ir.isLessThan(0));
		assertTrue(ir.isLessThan(7));
	}

	public void testIsLessThanOrEqualInt() {
		IntReference ir;
		ir = new IntReference();
		assertFalse(ir.isLessThanOrEqual(-1));
		assertTrue(ir.isLessThanOrEqual(0));
		assertTrue(ir.isLessThanOrEqual(7));
	}

	public void testIsPositive() {
		IntReference ir;
		ir = new IntReference(-3);
		assertFalse(ir.isPositive());

		ir = new IntReference();
		assertFalse(ir.isPositive());

		ir = new IntReference(7);
		assertTrue(ir.isPositive());
	}

	public void testIsNotPositive() {
		IntReference ir;
		ir = new IntReference(-3);
		assertTrue(ir.isNotPositive());

		ir = new IntReference();
		assertTrue(ir.isNotPositive());

		ir = new IntReference(7);
		assertFalse(ir.isNotPositive());
	}

	public void testIsNegative() {
		IntReference ir;
		ir = new IntReference(-3);
		assertTrue(ir.isNegative());

		ir = new IntReference();
		assertFalse(ir.isNegative());

		ir = new IntReference(7);
		assertFalse(ir.isNegative());
	}

	public void testIsNotNegative() {
		IntReference ir;
		ir = new IntReference(-3);
		assertFalse(ir.isNotNegative());

		ir = new IntReference();
		assertTrue(ir.isNotNegative());

		ir = new IntReference(7);
		assertTrue(ir.isNotNegative());
	}

	public void testSetValueInt() {
		IntReference ir;
		ir = new IntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(-3, ir.setValue(4));
		assertEquals(4, ir.getValue());
	}

	public void testAbs() {
		IntReference ir;
		ir = new IntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(3, ir.abs());
		assertEquals(3, ir.getValue());

		ir.setValue(3);
		assertEquals(3, ir.getValue());
		assertEquals(3, ir.abs());
		assertEquals(3, ir.getValue());
	}

	public void testNeg() {
		IntReference ir;
		ir = new IntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(3, ir.neg());
		assertEquals(3, ir.getValue());

		ir.setValue(3);
		assertEquals(3, ir.getValue());
		assertEquals(-3, ir.neg());
		assertEquals(-3, ir.getValue());
	}

	public void testSetZero() {
		IntReference ir;
		ir = new IntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(-3, ir.setZero());
		assertEquals(0, ir.getValue());
	}

	public void testAddInt() {
		IntReference ir;
		int value;
		ir = new IntReference();
		assertEquals(0, ir.getValue());

		value = ir.add(3);
		assertEquals(3, value);
		assertEquals(3, ir.getValue());

		value = ir.add(-7);
		assertEquals(-4, value);
		assertEquals(-4, ir.getValue());
	}

	public void testIncrement() {
		IntReference ir;
		int value;
		ir = new IntReference();
		assertEquals(0, ir.getValue());

		value = ir.increment();
		assertEquals(1, value);
		assertEquals(1, ir.getValue());
	}

	public void testSubtractInt() {
		IntReference ir;
		int count;
		ir = new IntReference();
		assertEquals(0, ir.getValue());

		count = ir.subtract(3);
		assertEquals(-3, count);
		assertEquals(-3, ir.getValue());

		count = ir.subtract(-7);
		assertEquals(4, count);
		assertEquals(4, ir.getValue());
	}

	public void testDecrement() {
		IntReference ir;
		int count;
		ir = new IntReference();
		assertEquals(0, ir.getValue());

		count = ir.decrement();
		assertEquals(-1, count);
		assertEquals(-1, ir.getValue());
	}

	public void testMultiplyInt() {
		IntReference ir;
		ir = new IntReference(3);
		assertEquals(3, ir.getValue());
		assertEquals(9, ir.multiply(3));
		assertEquals(9, ir.getValue());
	}

	public void testDivideInt() {
		IntReference ir;
		ir = new IntReference(24);
		assertEquals(24, ir.getValue());
		assertEquals(8, ir.divide(3));
		assertEquals(8, ir.getValue());
	}

	public void testRemainderInt() {
		IntReference ir;
		ir = new IntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(1, ir.remainder(3));
		assertEquals(1, ir.getValue());
	}

	public void testMinInt() {
		IntReference ir;
		ir = new IntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(3, ir.min(3));
		assertEquals(3, ir.getValue());
		assertEquals(3, ir.min(25));
		assertEquals(3, ir.getValue());
	}

	public void testMaxInt() {
		IntReference ir;
		ir = new IntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(25, ir.max(3));
		assertEquals(25, ir.getValue());
		assertEquals(30, ir.max(30));
		assertEquals(30, ir.getValue());
	}

	public void testPowInt() {
		IntReference ir;
		ir = new IntReference(5);
		assertEquals(5, ir.getValue());
		assertEquals(25, ir.pow(2));
		assertEquals(25, ir.getValue());
		assertEquals(625, ir.pow(2));
		assertEquals(625, ir.getValue());
	}

	public void testCompareToIntReference() {
		IntReference ir1 = new IntReference(44);
		IntReference ir2 = new IntReference(44);
		assertTrue(ir1.compareTo(ir2) == 0);
		ir2 = new IntReference(55);
		assertTrue(ir1.compareTo(ir2) < 0);
		ir2 = new IntReference(33);
		assertTrue(ir1.compareTo(ir2) > 0);
	}

	public void testClone() {
		IntReference ir1 = new IntReference(44);
		IntReference ir2 = (IntReference) ir1.clone();
		assertEquals(44, ir2.getValue());
		assertEquals(ir1, ir2);
		assertNotSame(ir1, ir2);
	}

	public void testEquals() {
		IntReference ir1 = new IntReference(44);
		IntReference ir2 = new IntReference(44);
		assertEquals(ir1, ir2);
		assertEquals(ir1.hashCode(), ir2.hashCode());
		assertFalse(ir1.equals(null));
	}

	public void testSerialization() throws Exception {
		IntReference ir1 = new IntReference(44);
		IntReference ir2 = TestTools.serialize(ir1);
		assertEquals(44, ir2.getValue());
		assertEquals(ir1, ir2);
		assertNotSame(ir1, ir2);
	}

	public void testToString() {
		IntReference ir;
		ir = new IntReference(5);
		assertEquals("[5]", ir.toString());
	}

}
