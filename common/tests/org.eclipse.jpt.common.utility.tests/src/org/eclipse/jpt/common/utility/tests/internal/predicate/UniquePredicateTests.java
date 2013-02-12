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

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.UniquePredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class UniquePredicateTests
	extends TestCase
{
	private UniquePredicate<String> uniquePredicate;


	public UniquePredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.uniquePredicate = PredicateTools.uniquePredicate();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		assertTrue(this.uniquePredicate.evaluate("Fred"));
		assertTrue(this.uniquePredicate.evaluate("Wilma"));
		assertTrue(this.uniquePredicate.evaluate("Barney"));
		assertTrue(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate("Fred"));
		assertFalse(this.uniquePredicate.evaluate("Barney"));
	}

	public void testAdd() {
		assertTrue(this.uniquePredicate.evaluate("Fred"));
		assertTrue(this.uniquePredicate.add("Wilma"));
		assertTrue(this.uniquePredicate.add("Barney"));
		assertTrue(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate("Fred"));
		assertFalse(this.uniquePredicate.evaluate("Barney"));
	}

	public void testAddAll() {
		assertTrue(this.uniquePredicate.evaluate("Fred"));
		assertTrue(this.uniquePredicate.addAll(Arrays.asList("Wilma", "Barney")));
		assertTrue(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate(null));
		assertFalse(this.uniquePredicate.evaluate("Fred"));
		assertFalse(this.uniquePredicate.evaluate("Barney"));
	}

	public void testClone() {
		UniquePredicate<String> uniquePredicate2 = this.uniquePredicate.clone();
		assertEquals(this.uniquePredicate, uniquePredicate2);
		assertNotSame(this.uniquePredicate, uniquePredicate2);
	}

	public void testEquals() {
		UniquePredicate<String> uniquePredicate2 = PredicateTools.uniquePredicate();
		assertEquals(this.uniquePredicate, uniquePredicate2);
		assertEquals(this.uniquePredicate.hashCode(), uniquePredicate2.hashCode());
		assertFalse(this.uniquePredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		UniquePredicate<String> uniquePredicate2 = TestTools.serialize(this.uniquePredicate);
		assertEquals(this.uniquePredicate, uniquePredicate2);
		assertNotSame(this.uniquePredicate, uniquePredicate2);
	}
}
