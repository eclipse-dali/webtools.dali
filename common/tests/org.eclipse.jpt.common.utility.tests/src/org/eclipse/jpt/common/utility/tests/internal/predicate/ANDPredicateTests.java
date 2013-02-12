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
import org.eclipse.jpt.common.utility.internal.predicate.ANDPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class ANDPredicateTests
	extends TestCase
{
	private ANDPredicate<Number> andPredicate;


	public ANDPredicateTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.andPredicate = PredicateTools.and(this.buildMinPredicate(1), this.buildMaxPredicate(10));
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
			return number.doubleValue() >= this.criterion.doubleValue();
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
			return number.doubleValue() <= this.criterion.doubleValue();
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
		ANDPredicate<Number> andPredicate2 = PredicateTools.and(this.andPredicate, this.buildEvenPredicate());
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
		Predicate<Number> andPredicate2 = PredicateTools.and(this.buildMinPredicate(1), this.buildMaxPredicate(10), this.buildEvenPredicate());
		assertFalse(andPredicate2.evaluate(new Integer(7)));
		assertTrue(andPredicate2.evaluate(new Integer(2)));
		assertTrue(andPredicate2.evaluate(new Double(6.1)));
		assertFalse(andPredicate2.evaluate(new Double(-99)));
		assertFalse(andPredicate2.evaluate(new Double(-1)));
		assertFalse(andPredicate2.evaluate(new Double(11)));
		assertFalse(andPredicate2.evaluate(new Double(111)));
	}

	public void testClone() {
		ANDPredicate<Number> andPredicate2 = this.andPredicate.clone();
		assertEquals(this.andPredicate.getPredicates()[0], andPredicate2.getPredicates()[0]);
		assertEquals(this.andPredicate.getPredicates()[1], andPredicate2.getPredicates()[1]);
		assertNotSame(this.andPredicate, andPredicate2);
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		ANDPredicate<Number> andPredicate2 = PredicateTools.and(this.buildMinPredicate(1), this.buildMaxPredicate(10));
		assertEquals(this.andPredicate, andPredicate2);
		assertEquals(this.andPredicate.hashCode(), andPredicate2.hashCode());
		assertFalse(this.andPredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		@SuppressWarnings("cast")
		ANDPredicate<Number> andPredicate2 = (ANDPredicate<Number>) TestTools.serialize(this.andPredicate);
		assertEquals(this.andPredicate.getPredicates()[0], andPredicate2.getPredicates()[0]);
		assertEquals(this.andPredicate.getPredicates()[1], andPredicate2.getPredicates()[1]);
		assertNotSame(this.andPredicate, andPredicate2);
	}
}
