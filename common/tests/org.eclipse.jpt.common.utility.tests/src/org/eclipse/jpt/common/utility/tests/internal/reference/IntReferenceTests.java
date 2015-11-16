/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.predicate.int_.IntPredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.reference.IntReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class IntReferenceTests
	extends TestCase
{
	public IntReferenceTests(String name) {
		super(name);
	}

	protected IntReference buildIntReference() {
		return this.buildIntReference(0);
	}

	protected abstract IntReference buildIntReference(int value);

	public void testGetValue() {
		IntReference ref = this.buildIntReference(42);
		assertEquals(42, ref.getValue());
	}

	public void testEqualsInt() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.equals(42));
		assertFalse(ref.equals(0));
	}

	public void testNotEqualInt() {
		IntReference ref = this.buildIntReference(42);
		assertFalse(ref.notEqual(42));
		assertTrue(ref.notEqual(0));
	}

	public void testIsZero() {
		IntReference ref = this.buildIntReference(42);
		assertFalse(ref.isZero());
		ref = this.buildIntReference(0);
		assertTrue(ref.isZero());
	}

	public void testIsNotZero() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isNotZero());
		ref = this.buildIntReference(0);
		assertFalse(ref.isNotZero());
	}

	public void testIsGreaterThan() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isGreaterThan(22));
		assertFalse(ref.isGreaterThan(2222));
	}

	public void testIsGreaterThanOrEqual() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isGreaterThanOrEqual(22));
		assertTrue(ref.isGreaterThanOrEqual(42));
		assertFalse(ref.isGreaterThanOrEqual(2222));
	}

	public void testIsLessThan() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isLessThan(2222));
		assertFalse(ref.isLessThan(22));
	}

	public void testIsLessThanOrEqual() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isLessThanOrEqual(2222));
		assertTrue(ref.isLessThanOrEqual(42));
		assertFalse(ref.isLessThanOrEqual(22));
	}

	public void testIsPositive() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isPositive());
		ref = this.buildIntReference(0);
		assertFalse(ref.isPositive());
		ref = this.buildIntReference(-42);
		assertFalse(ref.isPositive());
	}

	public void testIsNotPositive() {
		IntReference ref = this.buildIntReference(-42);
		assertTrue(ref.isNotPositive());
		ref = this.buildIntReference(0);
		assertTrue(ref.isNotPositive());
		ref = this.buildIntReference(42);
		assertFalse(ref.isNotPositive());
	}

	public void testIsNegative() {
		IntReference ref = this.buildIntReference(-42);
		assertTrue(ref.isNegative());
		ref = this.buildIntReference(0);
		assertFalse(ref.isNegative());
		ref = this.buildIntReference(42);
		assertFalse(ref.isNegative());
	}

	public void testIsNotNegative() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isNotNegative());
		ref = this.buildIntReference(0);
		assertTrue(ref.isNotNegative());
		ref = this.buildIntReference(-42);
		assertFalse(ref.isNotNegative());
	}

	public void testIsMemberOf() {
		IntReference ref = this.buildIntReference(42);
		assertTrue(ref.isMemberOf(IntPredicateTools.isEven()));
		assertFalse(ref.isMemberOf(IntPredicateTools.isOdd()));
	}

	public void testIsNotMemberOf() {
		IntReference ref = this.buildIntReference(42);
		assertFalse(ref.isNotMemberOf(IntPredicateTools.isEven()));
		assertTrue(ref.isNotMemberOf(IntPredicateTools.isOdd()));
	}

	public void testCompareToIntReference() {
		IntReference ref1 = this.buildIntReference(44);
		SimpleIntReference ref2 = new SimpleIntReference(44);
		assertTrue(ref1.compareTo(ref2) == 0);
		ref2 = new SimpleIntReference(55);
		assertTrue(ref1.compareTo(ref2) < 0);
		ref2 = new SimpleIntReference(33);
		assertTrue(ref1.compareTo(ref2) > 0);
	}

	public void testEquals() {
		IntReference ref1 = this.buildIntReference(42);
		IntReference ref2 = this.buildIntReference(42);
		assertTrue(ref1.equals(ref1));
		assertFalse(ref1.equals(ref2));
	}

	public void testHashCode() {
		IntReference ref = this.buildIntReference(42);
		assertEquals(ref.hashCode(), ref.hashCode());
	}

	public void testToString() {
		IntReference ref = this.buildIntReference(42);
		assertEquals("[42]", ref.toString());
	}
}
