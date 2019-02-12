/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;

public abstract class ModifiableIntReferenceTests
	extends IntReferenceTests
{
	public ModifiableIntReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableIntReference buildIntReference() {
		return this.buildIntReference(0);
	}

	@Override
	protected abstract ModifiableIntReference buildIntReference(int value);

	public void testSetValueInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(-3);
		assertEquals(-3, ref.getValue());
		assertEquals(-3, ref.setValue(4));
		assertEquals(4, ref.getValue());
	}

	public void testAbs() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(-3);
		assertEquals(-3, ref.getValue());
		assertEquals(3, ref.abs());
		assertEquals(3, ref.getValue());

		ref.setValue(3);
		assertEquals(3, ref.getValue());
		assertEquals(3, ref.abs());
		assertEquals(3, ref.getValue());
	}

	public void testNegate() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(-3);
		assertEquals(-3, ref.getValue());
		assertEquals(3, ref.negate());
		assertEquals(3, ref.getValue());

		ref.setValue(3);
		assertEquals(3, ref.getValue());
		assertEquals(-3, ref.negate());
		assertEquals(-3, ref.getValue());
	}

	public void testNegateExact() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(-3);
		assertEquals(-3, ref.getValue());
		assertEquals(3, ref.negateExact());
		assertEquals(3, ref.getValue());

		ref.setValue(3);
		assertEquals(3, ref.getValue());
		assertEquals(-3, ref.negateExact());
		assertEquals(-3, ref.getValue());
	}

	public void testSetZero() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(-3);
		assertEquals(-3, ref.getValue());
		assertEquals(-3, ref.setZero());
		assertEquals(0, ref.getValue());
	}

	public void testAddInt() {
		ModifiableIntReference ref;
		int value;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		value = ref.add(3);
		assertEquals(3, value);
		assertEquals(3, ref.getValue());

		value = ref.add(-7);
		assertEquals(-4, value);
		assertEquals(-4, ref.getValue());
	}

	public void testAddExactInt() {
		ModifiableIntReference ref;
		int value;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		value = ref.addExact(3);
		assertEquals(3, value);
		assertEquals(3, ref.getValue());

		value = ref.addExact(-7);
		assertEquals(-4, value);
		assertEquals(-4, ref.getValue());
	}

	public void testIncrement() {
		ModifiableIntReference ref;
		int value;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		value = ref.increment();
		assertEquals(1, value);
		assertEquals(1, ref.getValue());
	}

	public void testIncrementExact() {
		ModifiableIntReference ref;
		int value;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		value = ref.incrementExact();
		assertEquals(1, value);
		assertEquals(1, ref.getValue());
	}

	public void testSubtractInt() {
		ModifiableIntReference ref;
		int count;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		count = ref.subtract(3);
		assertEquals(-3, count);
		assertEquals(-3, ref.getValue());

		count = ref.subtract(-7);
		assertEquals(4, count);
		assertEquals(4, ref.getValue());
	}

	public void testSubtractExactInt() {
		ModifiableIntReference ref;
		int count;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		count = ref.subtractExact(3);
		assertEquals(-3, count);
		assertEquals(-3, ref.getValue());

		count = ref.subtractExact(-7);
		assertEquals(4, count);
		assertEquals(4, ref.getValue());
	}

	public void testDecrement() {
		ModifiableIntReference ref;
		int count;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		count = ref.decrement();
		assertEquals(-1, count);
		assertEquals(-1, ref.getValue());
	}

	public void testDecrementExact() {
		ModifiableIntReference ref;
		int count;
		ref = this.buildIntReference();
		assertEquals(0, ref.getValue());

		count = ref.decrementExact();
		assertEquals(-1, count);
		assertEquals(-1, ref.getValue());
	}

	public void testHalve() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(44);
		assertEquals(44, ref.getValue());
		assertEquals(22, ref.halve());
		assertEquals(22, ref.getValue());
	}

	public void testTwice() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(44);
		assertEquals(44, ref.getValue());
		assertEquals(88, ref.twice());
		assertEquals(88, ref.getValue());
	}

	public void testTwiceExact() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(44);
		assertEquals(44, ref.getValue());
		assertEquals(88, ref.twiceExact());
		assertEquals(88, ref.getValue());
	}

	public void testMultiplyInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(3);
		assertEquals(3, ref.getValue());
		assertEquals(9, ref.multiply(3));
		assertEquals(9, ref.getValue());
	}

	public void testMultiplyExactInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(3);
		assertEquals(3, ref.getValue());
		assertEquals(9, ref.multiplyExact(3));
		assertEquals(9, ref.getValue());
	}

	public void testDivideInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(24);
		assertEquals(24, ref.getValue());
		assertEquals(8, ref.divide(3));
		assertEquals(8, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(1, ref.divide(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(-1, ref.divide(3));
		assertEquals(-1, ref.getValue());
	}

	public void testFloorDivideInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(24);
		assertEquals(24, ref.getValue());
		assertEquals(8, ref.floorDivide(3));
		assertEquals(8, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(1, ref.floorDivide(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(-2, ref.floorDivide(3));
		assertEquals(-2, ref.getValue());
	}

	public void testRemainderInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(25);
		assertEquals(25, ref.getValue());
		assertEquals(1, ref.remainder(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(1, ref.remainder(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(1, ref.remainder(-3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(-1, ref.remainder(3));
		assertEquals(-1, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(-1, ref.remainder(-3));
		assertEquals(-1, ref.getValue());
	}

	public void testFloorRemainderInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(25);
		assertEquals(25, ref.getValue());
		assertEquals(1, ref.floorRemainder(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(1, ref.floorRemainder(3));
		assertEquals(1, ref.getValue());

		ref = this.buildIntReference(4);
		assertEquals(4, ref.getValue());
		assertEquals(-2, ref.floorRemainder(-3));
		assertEquals(-2, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(2, ref.floorRemainder(3));
		assertEquals(2, ref.getValue());

		ref = this.buildIntReference(-4);
		assertEquals(-4, ref.getValue());
		assertEquals(-1, ref.floorRemainder(-3));
		assertEquals(-1, ref.getValue());
	}

	public void testMinInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(25);
		assertEquals(25, ref.getValue());
		assertEquals(3, ref.min(3));
		assertEquals(3, ref.getValue());
		assertEquals(3, ref.min(33));
		assertEquals(3, ref.getValue());
	}

	public void testMaxInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(25);
		assertEquals(25, ref.getValue());
		assertEquals(25, ref.max(3));
		assertEquals(25, ref.getValue());
		assertEquals(30, ref.max(30));
		assertEquals(30, ref.getValue());
		assertEquals(30, ref.max(3));
		assertEquals(30, ref.getValue());
	}

	public void testCommitIntInt() {
		ModifiableIntReference ref;
		ref = this.buildIntReference(25);
		assertTrue(ref.commit(-3, 25));
		assertEquals(-3, ref.getValue());
		assertFalse(ref.commit(22, 25));
		assertEquals(-3, ref.getValue());
	}

	public void testSwapRef() throws Exception {
		ModifiableIntReference ref1 = this.buildIntReference(33);
		ModifiableIntReference ref2 = ref1;
		assertEquals(33, ref1.swap(ref2));

		ref2 = this.buildIntReference(-7);
		assertEquals(-7, ref1.swap(ref2));
		assertEquals(-7, ref1.getValue());
		assertEquals(33, ref2.getValue());

		ref1.setValue(42);
		ref2.setValue(42);
		assertEquals(42, ref1.swap(ref2));
		assertEquals(42, ref1.getValue());
		assertEquals(42, ref2.getValue());
	}
}
