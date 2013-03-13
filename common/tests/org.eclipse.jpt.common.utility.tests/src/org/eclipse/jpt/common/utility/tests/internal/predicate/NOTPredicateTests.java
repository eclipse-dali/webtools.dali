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

import java.io.Serializable;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.NOTPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.NotNullPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NOTPredicateTests
	extends TestCase
{
	private NOTPredicate<Number> notPredicate;


	public NOTPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.notPredicate = PredicateTools.not(this.buildPositivePredicate());
	}

	private Predicate<Number> buildPositivePredicate() {
		return new PositivePredicate();
	}

	static class PositivePredicate
		extends PredicateAdapter<Number>
		implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Override
		public boolean evaluate(Number number) {
			return number.doubleValue() > 0;
		}
		@Override
		public boolean equals(Object obj) {
			return this.getClass() == obj.getClass();
		}
		@Override
		public int hashCode() {
			return 789;
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		assertTrue(this.notPredicate.evaluate(new Integer(0)));
		assertTrue(this.notPredicate.evaluate(new Integer(-1)));
		assertTrue(this.notPredicate.evaluate(new Double(-0.001)));
		assertFalse(this.notPredicate.evaluate(new Double(1)));
		assertFalse(this.notPredicate.evaluate(new Double(11)));
		assertFalse(this.notPredicate.evaluate(new Double(111)));
	}

	public void testClone() {
		NOTPredicate<Number> notPredicate2 = this.notPredicate.clone();
		assertEquals(this.notPredicate.getPredicate(), notPredicate2.getPredicate());
		assertNotSame(this.notPredicate, notPredicate2);
	}

	public void testEquals() {
		NOTPredicate<Number> notPredicate2 = PredicateTools.not(this.buildPositivePredicate());
		assertEquals(this.notPredicate, notPredicate2);
		assertEquals(this.notPredicate.hashCode(), notPredicate2.hashCode());
		assertFalse(this.notPredicate.equals(NotNullPredicate.instance()));
	}

	public void testSerialization() throws Exception {
		NOTPredicate<Number> notPredicate2 = TestTools.serialize(this.notPredicate);
		assertEquals(this.notPredicate.getPredicate(), notPredicate2.getPredicate());
		assertNotSame(this.notPredicate, notPredicate2);
	}
}
