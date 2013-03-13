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
import org.eclipse.jpt.common.utility.internal.predicate.NotNullPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.ORPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class ORPredicateTests
	extends TestCase
{
	private ORPredicate<Number> orPredicate;


	public ORPredicateTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.orPredicate = PredicateTools.or(this.buildMinPredicate(1), this.buildMaxPredicate(10));
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
		extends PredicateAdapter<Number>
	{
		EvenPredicate() {
			super();
		}
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
		ORPredicate<Number> orPredicate2 = PredicateTools.or(this.orPredicate, this.buildEvenPredicate());
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
		Predicate<Number> orPredicate2 = PredicateTools.or(this.buildMinPredicate(1), this.buildMaxPredicate(10), this.buildEvenPredicate());
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

	public void testClone() {
		ORPredicate<Number> orPredicate2 = this.orPredicate.clone();
		assertEquals(this.orPredicate.getPredicates()[0], orPredicate2.getPredicates()[0]);
		assertEquals(this.orPredicate.getPredicates()[1], orPredicate2.getPredicates()[1]);
		assertNotSame(this.orPredicate, orPredicate2);
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		ORPredicate<Number> orPredicate2 = PredicateTools.or(this.buildMinPredicate(1), this.buildMaxPredicate(10));
		assertEquals(this.orPredicate, orPredicate2);
		assertEquals(this.orPredicate.hashCode(), orPredicate2.hashCode());
		assertFalse(this.orPredicate.equals(NotNullPredicate.instance()));
	}

	public void testSerialization() throws Exception {
		ORPredicate<Number> orPredicate2 = TestTools.serialize(this.orPredicate);
		assertEquals(this.orPredicate.getPredicates()[0], orPredicate2.getPredicates()[0]);
		assertEquals(this.orPredicate.getPredicates()[1], orPredicate2.getPredicates()[1]);
		assertNotSame(this.orPredicate, orPredicate2);
	}
}
