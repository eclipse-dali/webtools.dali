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

public class ORTests
	extends TestCase
{
	private CompoundPredicate<Number> orPredicate;


	public ORTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.orPredicate = PredicateTools.or(this.buildMin(1), this.buildMax(10));
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

	public void testEvaluate2() {
		assertFalse(this.orPredicate.evaluate(new Integer(7)));
		assertFalse(this.orPredicate.evaluate(new Integer(2)));
		assertFalse(this.orPredicate.evaluate(new Double(6.666)));
		assertTrue(this.orPredicate.evaluate(new Double(-99)));
		assertTrue(this.orPredicate.evaluate(new Double(-1)));
		assertTrue(this.orPredicate.evaluate(new Double(11)));
		assertTrue(this.orPredicate.evaluate(new Double(111)));
	}

	public void testEvaluate3() {
		@SuppressWarnings("unchecked")
		CompoundPredicate<Number> orPredicate2 = PredicateTools.or(this.orPredicate, this.buildIsEven());
		assertFalse(orPredicate2.evaluate(new Integer(7)));
		assertFalse(orPredicate2.evaluate(new Integer(3)));
		assertFalse(orPredicate2.evaluate(new Integer(9)));
		assertTrue(orPredicate2.evaluate(new Integer(2)));
		assertTrue(orPredicate2.evaluate(new Double(6.1)));
		assertTrue(orPredicate2.evaluate(new Double(-99)));
		assertTrue(orPredicate2.evaluate(new Double(-1)));
		assertTrue(orPredicate2.evaluate(new Double(11)));
		assertTrue(orPredicate2.evaluate(new Double(111)));
		assertTrue(orPredicate2.evaluate(new Double(-98)));
		assertTrue(orPredicate2.evaluate(new Double(0)));
		assertTrue(orPredicate2.evaluate(new Double(-2)));
		assertTrue(orPredicate2.evaluate(new Double(12)));
		assertTrue(orPredicate2.evaluate(new Double(222)));
	}

	public void testComposite() {
		@SuppressWarnings("unchecked")
		Predicate<Number> orPredicate2 = PredicateTools.or(this.buildMin(1), this.buildMax(10), this.buildIsEven());
		assertFalse(orPredicate2.evaluate(new Integer(7)));
		assertFalse(orPredicate2.evaluate(new Integer(3)));
		assertFalse(orPredicate2.evaluate(new Integer(9)));
		assertTrue(orPredicate2.evaluate(new Integer(2)));
		assertTrue(orPredicate2.evaluate(new Double(6.1)));
		assertTrue(orPredicate2.evaluate(new Double(-99)));
		assertTrue(orPredicate2.evaluate(new Double(-1)));
		assertTrue(orPredicate2.evaluate(new Double(11)));
		assertTrue(orPredicate2.evaluate(new Double(111)));
		assertTrue(orPredicate2.evaluate(new Double(-98)));
		assertTrue(orPredicate2.evaluate(new Double(0)));
		assertTrue(orPredicate2.evaluate(new Double(-2)));
		assertTrue(orPredicate2.evaluate(new Double(12)));
		assertTrue(orPredicate2.evaluate(new Double(222)));
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		CompoundPredicate<Number> orPredicate2 = PredicateTools.or(this.buildMin(1), this.buildMax(10));
		assertEquals(this.orPredicate, orPredicate2);
		assertEquals(this.orPredicate.hashCode(), orPredicate2.hashCode());
		assertFalse(this.orPredicate.equals(IsNotNull.instance()));
	}
}
