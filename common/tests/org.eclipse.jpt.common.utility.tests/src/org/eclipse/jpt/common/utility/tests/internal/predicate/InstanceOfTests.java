/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class InstanceOfTests
	extends TestCase
{
	private Predicate<Number> instanceOfPredicate;


	public InstanceOfTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.instanceOfPredicate = PredicateTools.instanceOf(Number.class);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		Predicate<Object> instanceOfPredicate2 = PredicateTools.<Object>instanceOf(Integer.class);

		assertTrue(this.instanceOfPredicate.evaluate(new Integer(42)));
		assertTrue(instanceOfPredicate2.evaluate(new Integer(42)));

		assertTrue(this.instanceOfPredicate.evaluate(42));
		assertTrue(instanceOfPredicate2.evaluate(42));

		assertTrue(this.instanceOfPredicate.evaluate(Integer.valueOf("42")));
		assertTrue(instanceOfPredicate2.evaluate(Integer.valueOf("42")));

		assertFalse(this.instanceOfPredicate.evaluate(null));
		assertFalse(instanceOfPredicate2.evaluate(null));

		assertTrue(this.instanceOfPredicate.evaluate(24));
		assertTrue(instanceOfPredicate2.evaluate(24));

		assertTrue(this.instanceOfPredicate.evaluate(24f));
		assertFalse(instanceOfPredicate2.evaluate(24f));
	}

	public void testEquals() {
		Predicate<Number> instanceOfPredicate2 = PredicateTools.instanceOf(Number.class);
		assertEquals(this.instanceOfPredicate, instanceOfPredicate2);
		assertEquals(this.instanceOfPredicate.hashCode(), instanceOfPredicate2.hashCode());
		assertFalse(this.instanceOfPredicate.equals(IsNotNull.instance()));
	}
}
