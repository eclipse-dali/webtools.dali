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
import org.eclipse.jpt.common.utility.internal.reference.AbstractIntReference;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class AbstractIntReferenceTests
	extends TestCase
{
	public AbstractIntReferenceTests(String name) {
		super(name);
	}

	public void testGetValue() {
		LocalIntReference br = new LocalIntReference(42);
		assertEquals(42, br.getValue());
	}

	public void testEqualsInt() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.equals(42));
		assertFalse(br.equals(0));
	}

	public void testNotEqual() {
		LocalIntReference br = new LocalIntReference(42);
		assertFalse(br.notEqual(42));
		assertTrue(br.notEqual(0));
	}

	public void testIsZero() {
		LocalIntReference br = new LocalIntReference(42);
		assertFalse(br.isZero());
		br = new LocalIntReference(0);
		assertTrue(br.isZero());
	}

	public void testIsNotZero() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isNotZero());
		br = new LocalIntReference(0);
		assertFalse(br.isNotZero());
	}

	public void testIsGreaterThan() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isGreaterThan(22));
		assertFalse(br.isGreaterThan(2222));
	}

	public void testIsGreaterThanOrEqual() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isGreaterThanOrEqual(22));
		assertTrue(br.isGreaterThanOrEqual(42));
		assertFalse(br.isGreaterThanOrEqual(2222));
	}

	public void testIsLessThan() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isLessThan(2222));
		assertFalse(br.isLessThan(22));
	}

	public void testIsLessThanOrEqual() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isLessThanOrEqual(2222));
		assertTrue(br.isLessThanOrEqual(42));
		assertFalse(br.isLessThanOrEqual(22));
	}

	public void testIsPositive() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isPositive());
		br = new LocalIntReference(0);
		assertFalse(br.isPositive());
		br = new LocalIntReference(-42);
		assertFalse(br.isPositive());
	}

	public void testIsNotPositive() {
		LocalIntReference br = new LocalIntReference(-42);
		assertTrue(br.isNotPositive());
		br = new LocalIntReference(0);
		assertTrue(br.isNotPositive());
		br = new LocalIntReference(42);
		assertFalse(br.isNotPositive());
	}

	public void testIsNegative() {
		LocalIntReference br = new LocalIntReference(-42);
		assertTrue(br.isNegative());
		br = new LocalIntReference(0);
		assertFalse(br.isNegative());
		br = new LocalIntReference(42);
		assertFalse(br.isNegative());
	}

	public void testIsNotNegative() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isNotNegative());
		br = new LocalIntReference(0);
		assertTrue(br.isNotNegative());
		br = new LocalIntReference(-42);
		assertFalse(br.isNotNegative());
	}

	public void testIsMemberOf() {
		LocalIntReference br = new LocalIntReference(42);
		assertTrue(br.isMemberOf(IntPredicateTools.isEven()));
		assertFalse(br.isMemberOf(IntPredicateTools.isOdd()));
	}

	public void testIsNotMemberOf() {
		LocalIntReference br = new LocalIntReference(42);
		assertFalse(br.isNotMemberOf(IntPredicateTools.isEven()));
		assertTrue(br.isNotMemberOf(IntPredicateTools.isOdd()));
	}

	public void testCompareToIntReference() {
		LocalIntReference ir1 = new LocalIntReference(44);
		SimpleIntReference ir2 = new SimpleIntReference(44);
		assertTrue(ir1.compareTo(ir2) == 0);
		ir2 = new SimpleIntReference(55);
		assertTrue(ir1.compareTo(ir2) < 0);
		ir2 = new SimpleIntReference(33);
		assertTrue(ir1.compareTo(ir2) > 0);
	}

	public void testEquals() {
		LocalIntReference br1 = new LocalIntReference(42);
		LocalIntReference br2 = new LocalIntReference(42);
		assertTrue(br1.equals(br1));
		assertFalse(br1.equals(br2));
	}

	public void testHashCode() {
		LocalIntReference br = new LocalIntReference(42);
		assertEquals(br.hashCode(), br.hashCode());
	}

	public void testToString() {
		LocalIntReference br = new LocalIntReference(42);
		assertEquals("[42]", br.toString());
	}

	private class LocalIntReference
		extends AbstractIntReference
	{
		private int value;
		LocalIntReference(int value) {
			super();
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
	}
}
