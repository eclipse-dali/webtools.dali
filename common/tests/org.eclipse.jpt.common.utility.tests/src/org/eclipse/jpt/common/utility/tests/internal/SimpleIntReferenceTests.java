/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.SimpleIntReference;

@SuppressWarnings("nls")
public class SimpleIntReferenceTests extends TestCase {

	public SimpleIntReferenceTests(String name) {
		super(name);
	}

	public void testCtors() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());
		ir = new SimpleIntReference(7);
		assertEquals(7, ir.getValue());
		ir = new SimpleIntReference(-7);
		assertEquals(-7, ir.getValue());
	}

	public void testEqualsInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertTrue(ir.equals(0));
		assertFalse(ir.equals(7));

		ir = new SimpleIntReference(7);
		assertTrue(ir.equals(7));
		assertFalse(ir.equals(0));
	}

	public void testNotEqualInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertFalse(ir.notEqual(0));
		assertTrue(ir.notEqual(7));

		ir = new SimpleIntReference(7);
		assertFalse(ir.notEqual(7));
		assertTrue(ir.notEqual(0));
	}

	public void testIsZero() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertTrue(ir.isZero());

		ir = new SimpleIntReference(7);
		assertFalse(ir.isZero());
	}

	public void testIsNotZero() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertFalse(ir.isNotZero());

		ir = new SimpleIntReference(7);
		assertTrue(ir.isNotZero());
	}

	public void testIsGreaterThanInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertTrue(ir.isGreaterThan(-1));
		assertFalse(ir.isGreaterThan(0));
		assertFalse(ir.isGreaterThan(7));
	}

	public void testIsGreaterThanOrEqualInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertTrue(ir.isGreaterThanOrEqual(-1));
		assertTrue(ir.isGreaterThanOrEqual(0));
		assertFalse(ir.isGreaterThanOrEqual(7));
	}

	public void testIsLessThanInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertFalse(ir.isLessThan(-1));
		assertFalse(ir.isLessThan(0));
		assertTrue(ir.isLessThan(7));
	}

	public void testIsLessThanOrEqualInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference();
		assertFalse(ir.isLessThanOrEqual(-1));
		assertTrue(ir.isLessThanOrEqual(0));
		assertTrue(ir.isLessThanOrEqual(7));
	}

	public void testIsPositive() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertFalse(ir.isPositive());

		ir = new SimpleIntReference();
		assertFalse(ir.isPositive());

		ir = new SimpleIntReference(7);
		assertTrue(ir.isPositive());
	}

	public void testIsNotPositive() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertTrue(ir.isNotPositive());

		ir = new SimpleIntReference();
		assertTrue(ir.isNotPositive());

		ir = new SimpleIntReference(7);
		assertFalse(ir.isNotPositive());
	}

	public void testIsNegative() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertTrue(ir.isNegative());

		ir = new SimpleIntReference();
		assertFalse(ir.isNegative());

		ir = new SimpleIntReference(7);
		assertFalse(ir.isNegative());
	}

	public void testIsNotNegative() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertFalse(ir.isNotNegative());

		ir = new SimpleIntReference();
		assertTrue(ir.isNotNegative());

		ir = new SimpleIntReference(7);
		assertTrue(ir.isNotNegative());
	}

	public void testSetValueInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(-3, ir.setValue(4));
		assertEquals(4, ir.getValue());
	}

	public void testAbs() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(3, ir.abs());

		ir.setValue(3);
		assertEquals(3, ir.getValue());
		assertEquals(3, ir.abs());
	}

	public void testNeg() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(3, ir.neg());

		ir.setValue(3);
		assertEquals(3, ir.getValue());
		assertEquals(-3, ir.neg());
	}

	public void testSetZero() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(-3);
		assertEquals(-3, ir.getValue());
		assertEquals(-3, ir.setZero());
		assertEquals(0, ir.getValue());
	}

	public void testAddInt() {
		SimpleIntReference ir;
		int value;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());

		value = ir.add(3);
		assertEquals(3, value);

		ir.setValue(3);
		value = ir.add(-7);
		assertEquals(-4, value);
	}

	public void testIncrement() {
		SimpleIntReference ir;
		int value;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());

		value = ir.increment();
		assertEquals(1, value);
		assertEquals(1, ir.getValue());
	}

	public void testSubtractInt() {
		SimpleIntReference ir;
		int count;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());

		count = ir.subtract(3);
		assertEquals(-3, count);

		ir.setValue(-3);
		count = ir.subtract(-7);
		assertEquals(4, count);
	}

	public void testDecrement() {
		SimpleIntReference ir;
		int count;
		ir = new SimpleIntReference();
		assertEquals(0, ir.getValue());

		count = ir.decrement();
		assertEquals(-1, count);
		assertEquals(-1, ir.getValue());
	}

	public void testMultiplyInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(3);
		assertEquals(3, ir.getValue());
		assertEquals(9, ir.multiply(3));
	}

	public void testDivideInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(24);
		assertEquals(24, ir.getValue());
		assertEquals(8, ir.divide(3));
	}

	public void testRemainderInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(1, ir.remainder(3));
	}

	public void testMinInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(3, ir.min(3));
		assertEquals(25, ir.min(33));
	}

	public void testMaxInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(25);
		assertEquals(25, ir.getValue());
		assertEquals(25, ir.max(3));
		assertEquals(30, ir.max(30));
	}

	public void testPowInt() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(5);
		assertEquals(5, ir.getValue());
		assertTrue(ir.pow(2) == 25L);
	}

	public void testCompareToIntReference() {
		SimpleIntReference ir1 = new SimpleIntReference(44);
		SimpleIntReference ir2 = new SimpleIntReference(44);
		assertTrue(ir1.compareTo(ir2) == 0);
		ir2 = new SimpleIntReference(55);
		assertTrue(ir1.compareTo(ir2) < 0);
		ir2 = new SimpleIntReference(33);
		assertTrue(ir1.compareTo(ir2) > 0);
	}

	public void testClone() {
		SimpleIntReference ir1 = new SimpleIntReference(44);
		SimpleIntReference ir2 = ir1.clone();
		assertEquals(44, ir2.getValue());
		assertNotSame(ir1, ir2);
	}

	public void testSerialization() throws Exception {
		SimpleIntReference ir1 = new SimpleIntReference(44);
		SimpleIntReference ir2 = TestTools.serialize(ir1);
		assertEquals(44, ir2.getValue());
		assertNotSame(ir1, ir2);
	}

	public void testToString() {
		SimpleIntReference ir;
		ir = new SimpleIntReference(5);
		assertEquals("[5]", ir.toString());
	}

}
