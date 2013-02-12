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
import org.eclipse.jpt.common.utility.internal.predicate.InstanceOfPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class InstanceOfPredicateTests
	extends TestCase
{
	private InstanceOfPredicate<Number> instanceOfPredicate;


	public InstanceOfPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.instanceOfPredicate = PredicateTools.instanceOfPredicate(Number.class);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		InstanceOfPredicate<Object> instanceOfPredicate2 = PredicateTools.<Object>instanceOfPredicate(Integer.class);

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

	public void testClone() {
		InstanceOfPredicate<Number> instanceOfPredicate2 = this.instanceOfPredicate.clone();
		assertEquals(this.instanceOfPredicate, instanceOfPredicate2);
		assertNotSame(this.instanceOfPredicate, instanceOfPredicate2);
	}

	public void testEquals() {
		InstanceOfPredicate<Number> instanceOfPredicate2 = PredicateTools.instanceOfPredicate(Number.class);
		assertEquals(this.instanceOfPredicate, instanceOfPredicate2);
		assertEquals(this.instanceOfPredicate.hashCode(), instanceOfPredicate2.hashCode());
		assertFalse(this.instanceOfPredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		InstanceOfPredicate<Number> instanceOfPredicate2 = TestTools.serialize(this.instanceOfPredicate);
		assertEquals(this.instanceOfPredicate, instanceOfPredicate2);
		assertNotSame(this.instanceOfPredicate, instanceOfPredicate2);
	}
}
