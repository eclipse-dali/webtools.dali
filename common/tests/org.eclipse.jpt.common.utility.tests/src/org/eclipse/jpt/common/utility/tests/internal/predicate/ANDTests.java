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
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.CompoundPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class ANDTests
	extends TestCase
{
	private CompoundPredicate<Number> andPredicate;


	public ANDTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.andPredicate = PredicateTools.and(this.buildMin(1), this.buildMax(10));
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
			return number.doubleValue() >= this.criterion.doubleValue();
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
			return number.doubleValue() <= this.criterion.doubleValue();
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
		assertTrue(this.andPredicate.evaluate(new Integer(7)));
		assertTrue(this.andPredicate.evaluate(new Integer(2)));
		assertTrue(this.andPredicate.evaluate(new Double(6.666)));
		assertFalse(this.andPredicate.evaluate(new Double(-99)));
		assertFalse(this.andPredicate.evaluate(new Double(-1)));
		assertFalse(this.andPredicate.evaluate(new Double(11)));
		assertFalse(this.andPredicate.evaluate(new Double(111)));
	}

	public void testEvaluate3() {
		@SuppressWarnings("unchecked")
		Predicate<Number> andPredicate2 = PredicateTools.and(this.andPredicate, this.buildIsEven());
		assertFalse(andPredicate2.evaluate(new Integer(7)));
		assertTrue(andPredicate2.evaluate(new Integer(2)));
		assertTrue(andPredicate2.evaluate(new Double(6.1)));
		assertFalse(andPredicate2.evaluate(new Double(-99)));
		assertFalse(andPredicate2.evaluate(new Double(-1)));
		assertFalse(andPredicate2.evaluate(new Double(11)));
		assertFalse(andPredicate2.evaluate(new Double(111)));
	}

	public void testComposite() {
		@SuppressWarnings("unchecked")
		Predicate<Number> andPredicate2 = PredicateTools.and(this.buildMin(1), this.buildMax(10), this.buildIsEven());
		assertFalse(andPredicate2.evaluate(new Integer(7)));
		assertTrue(andPredicate2.evaluate(new Integer(2)));
		assertTrue(andPredicate2.evaluate(new Double(6.1)));
		assertFalse(andPredicate2.evaluate(new Double(-99)));
		assertFalse(andPredicate2.evaluate(new Double(-1)));
		assertFalse(andPredicate2.evaluate(new Double(11)));
		assertFalse(andPredicate2.evaluate(new Double(111)));
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		Predicate<Number> andPredicate2 = PredicateTools.and(this.buildMin(1), this.buildMax(10));
		assertEquals(this.andPredicate, andPredicate2);
		assertEquals(this.andPredicate.hashCode(), andPredicate2.hashCode());
		assertFalse(this.andPredicate.equals(IsNotNull.instance()));
	}
}
