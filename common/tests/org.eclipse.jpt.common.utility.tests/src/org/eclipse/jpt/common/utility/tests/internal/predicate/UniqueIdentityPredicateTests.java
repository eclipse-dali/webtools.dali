/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.predicate.UniquePredicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class UniqueIdentityPredicateTests
	extends TestCase
{
	private UniquePredicate<String> predicate;


	public UniqueIdentityPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.predicate = PredicateTools.uniqueIdentityPredicate();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		assertTrue(this.predicate.evaluate("Fred"));
		assertTrue(this.predicate.evaluate("Wilma"));
		assertTrue(this.predicate.evaluate("Barney"));
		assertTrue(this.predicate.evaluate(null));
		assertFalse(this.predicate.evaluate(null));
		assertTrue(this.predicate.evaluate(new String("Fred")));
		assertFalse(this.predicate.evaluate("Fred"));
		assertFalse(this.predicate.evaluate("Barney"));
	}

	public void testAdd() {
		assertTrue(this.predicate.evaluate("Fred"));
		assertTrue(this.predicate.add("Wilma"));
		assertTrue(this.predicate.add("Barney"));
		assertTrue(this.predicate.evaluate(null));
		assertFalse(this.predicate.evaluate(null));
		assertTrue(this.predicate.evaluate(new String("Fred")));
		assertFalse(this.predicate.evaluate("Fred"));
		assertFalse(this.predicate.evaluate("Barney"));
	}

	public void testAddAll() {
		assertTrue(this.predicate.evaluate("Fred"));
		assertTrue(this.predicate.addAll(Arrays.asList("Wilma", "Barney")));
		assertTrue(this.predicate.evaluate(null));
		assertFalse(this.predicate.evaluate(null));
		assertTrue(this.predicate.evaluate(new String("Fred")));
		assertFalse(this.predicate.evaluate("Fred"));
		assertFalse(this.predicate.evaluate("Barney"));
	}
}
