/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.predicate.XORPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class XORPredicateTests
	extends TestCase
{
	private XORPredicate<Number> xorPredicate;


	public XORPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.xorPredicate = PredicateTools.xor(this.buildMinPredicate(1), this.buildMaxPredicate(10));
	}

	private Predicate<Number> buildMinPredicate(double min) {
		return new MinPredicate(min);
	}

	static class MinPredicate
		extends CriterionPredicate<Number, Number>
	{
		private static final long serialVersionUID = 1L;
		MinPredicate(double min) {
			super(new Double(min));
		}
		public boolean evaluate(Number number) {
			return number.doubleValue() <= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildMaxPredicate(double max) {
		return new MaxPredicate(max);
	}

	static class MaxPredicate
		extends CriterionPredicate<Number, Number>
	{
		private static final long serialVersionUID = 1L;
		MaxPredicate(double min) {
			super(new Double(min));
		}
		public boolean evaluate(Number number) {
			return number.doubleValue() >= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildEvenPredicate() {
		return new EvenPredicate();
	}

	static class EvenPredicate
		extends Predicate.Adapter<Number>
	{
		@Override
		public boolean evaluate(Number number) {
			return BitTools.isEven(number.intValue());
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testPredicateing2() {
		assertFalse(this.xorPredicate.evaluate(new Integer(7)));
		assertFalse(this.xorPredicate.evaluate(new Integer(2)));
		assertFalse(this.xorPredicate.evaluate(new Double(6.666)));
		assertTrue(this.xorPredicate.evaluate(new Double(-99)));
		assertTrue(this.xorPredicate.evaluate(new Double(-1)));
		assertTrue(this.xorPredicate.evaluate(new Double(11)));
		assertTrue(this.xorPredicate.evaluate(new Double(111)));
	}

	public void testPredicateing3() {
		XORPredicate<Number> xorPredicate2 = PredicateTools.xor(this.xorPredicate, this.buildEvenPredicate());
		assertFalse(xorPredicate2.evaluate(new Integer(7)));
		assertFalse(xorPredicate2.evaluate(new Integer(3)));
		assertFalse(xorPredicate2.evaluate(new Integer(9)));
		assertTrue(xorPredicate2.evaluate(new Integer(2)));
		assertTrue(xorPredicate2.evaluate(new Double(6.1)));
		assertTrue(xorPredicate2.evaluate(new Double(-99)));
		assertTrue(xorPredicate2.evaluate(new Double(-1)));
		assertTrue(xorPredicate2.evaluate(new Double(11)));
		assertTrue(xorPredicate2.evaluate(new Double(111)));
		assertFalse(xorPredicate2.evaluate(new Double(-98)));
		assertFalse(xorPredicate2.evaluate(new Double(0)));
		assertFalse(xorPredicate2.evaluate(new Double(-2)));
		assertFalse(xorPredicate2.evaluate(new Double(12)));
		assertFalse(xorPredicate2.evaluate(new Double(222)));
	}

	public void testClone() {
		XORPredicate<Number> xorPredicate2 = this.xorPredicate.clone();
		assertEquals(this.xorPredicate.getPredicates()[0], xorPredicate2.getPredicates()[0]);
		assertEquals(this.xorPredicate.getPredicates()[1], xorPredicate2.getPredicates()[1]);
		assertNotSame(this.xorPredicate, xorPredicate2);
	}

	public void testEquals() {
		XORPredicate<Number> xorPredicate2 = PredicateTools.xor(this.buildMinPredicate(1), this.buildMaxPredicate(10));
		assertEquals(this.xorPredicate, xorPredicate2);
		assertEquals(this.xorPredicate.hashCode(), xorPredicate2.hashCode());
		assertFalse(this.xorPredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		XORPredicate<Number> xorPredicate2 = TestTools.serialize(this.xorPredicate);
		assertEquals(this.xorPredicate.getPredicates()[0], xorPredicate2.getPredicates()[0]);
		assertEquals(this.xorPredicate.getPredicates()[1], xorPredicate2.getPredicates()[1]);
		assertNotSame(this.xorPredicate, xorPredicate2);
	}
}
