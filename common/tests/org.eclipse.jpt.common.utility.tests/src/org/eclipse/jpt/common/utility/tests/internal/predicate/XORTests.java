/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.CompoundPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class XORTests
	extends TestCase
{
	private CompoundPredicate<Number> xorPredicate;


	public XORTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.xorPredicate = PredicateTools.xor(this.buildMin(1), this.buildMax(10));
	}

	private Predicate<Number> buildMin(double min) {
		return new Min(min);
	}

	static class Min
		extends CriterionPredicate<Number, Number>
	{
		Min(double min) {
			super(new Double(min));
		}
		public boolean evaluate(Number number) {
			return number.doubleValue() <= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildMax(double max) {
		return new Max(max);
	}

	static class Max
		extends CriterionPredicate<Number, Number>
	{
		Max(double max) {
			super(new Double(max));
		}
		public boolean evaluate(Number number) {
			return number.doubleValue() >= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildIsEven() {
		return new IsEven();
	}

	static class IsEven
		extends PredicateAdapter<Number>
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
		CompoundPredicate<Number> xorPredicate2 = PredicateTools.xor(this.xorPredicate, this.buildIsEven());
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

	public void testEquals() {
		CompoundPredicate<Number> xorPredicate2 = PredicateTools.xor(this.buildMin(1), this.buildMax(10));
		assertEquals(this.xorPredicate, xorPredicate2);
		assertEquals(this.xorPredicate.hashCode(), xorPredicate2.hashCode());
		assertFalse(this.xorPredicate.equals(IsNotNull.instance()));
	}
}
