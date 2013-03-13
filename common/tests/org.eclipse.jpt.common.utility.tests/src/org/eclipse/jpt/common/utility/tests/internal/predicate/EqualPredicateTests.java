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
import org.eclipse.jpt.common.utility.internal.predicate.EqualPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.NotNullPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class EqualPredicateTests
	extends TestCase
{
	private EqualPredicate<Integer> equalPredicate;


	public EqualPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.equalPredicate = PredicateTools.equalPredicate(Integer.valueOf(42));
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		assertTrue(this.equalPredicate.evaluate(new Integer(42)));
		assertTrue(this.equalPredicate.evaluate(42));
		assertTrue(this.equalPredicate.evaluate(Integer.valueOf("42")));
		assertFalse(this.equalPredicate.evaluate(null));
		assertFalse(this.equalPredicate.evaluate(24));
	}

	public void testClone() {
		EqualPredicate<Integer> equalPredicate2 = this.equalPredicate.clone();
		assertEquals(this.equalPredicate, equalPredicate2);
		assertNotSame(this.equalPredicate, equalPredicate2);
	}

	public void testEquals() {
		EqualPredicate<Integer> equalPredicate2 = PredicateTools.equalPredicate(new Integer(42));
		assertEquals(this.equalPredicate, equalPredicate2);
		assertEquals(this.equalPredicate.hashCode(), equalPredicate2.hashCode());
		assertFalse(this.equalPredicate.equals(NotNullPredicate.instance()));
	}

	public void testSerialization() throws Exception {
		EqualPredicate<Integer> equalPredicate2 = TestTools.serialize(this.equalPredicate);
		assertEquals(this.equalPredicate, equalPredicate2);
		assertNotSame(this.equalPredicate, equalPredicate2);
	}
}
