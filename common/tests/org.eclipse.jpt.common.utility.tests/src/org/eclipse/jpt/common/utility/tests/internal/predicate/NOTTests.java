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
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.NOT;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NOTTests
	extends TestCase
{
	private NOT<Number> notPredicate;


	public NOTTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.notPredicate = new NOT<Number>(this.buildIsPositive());
	}

	private Predicate<Number> buildIsPositive() {
		return new IsPositive();
	}

	static class IsPositive
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

	public void testEquals() {
		Predicate<Number> notPredicate2 = PredicateTools.not(this.buildIsPositive());
		assertEquals(this.notPredicate, notPredicate2);
		assertEquals(this.notPredicate.hashCode(), notPredicate2.hashCode());
		assertFalse(this.notPredicate.equals(IsNotNull.instance()));
	}
}
