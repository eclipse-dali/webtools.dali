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
import org.eclipse.jpt.common.utility.internal.predicate.UniqueIdentityPredicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class UniqueIdentityPredicateTests
	extends TestCase
{
	private UniqueIdentityPredicate<String> uniqueIdentityPredicate;


	public UniqueIdentityPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.uniqueIdentityPredicate = new UniqueIdentityPredicate<String>();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		assertTrue(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertTrue(this.uniqueIdentityPredicate.evaluate("Wilma"));
		assertTrue(this.uniqueIdentityPredicate.evaluate("Barney"));
		assertTrue(this.uniqueIdentityPredicate.evaluate(null));
		assertFalse(this.uniqueIdentityPredicate.evaluate(null));
		assertTrue(this.uniqueIdentityPredicate.evaluate(new String("Fred")));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Barney"));
	}

	public void testAdd() {
		assertTrue(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertTrue(this.uniqueIdentityPredicate.add("Wilma"));
		assertTrue(this.uniqueIdentityPredicate.add("Barney"));
		assertTrue(this.uniqueIdentityPredicate.evaluate(null));
		assertFalse(this.uniqueIdentityPredicate.evaluate(null));
		assertTrue(this.uniqueIdentityPredicate.evaluate(new String("Fred")));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Barney"));
	}

	public void testAddAll() {
		assertTrue(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertTrue(this.uniqueIdentityPredicate.addAll(Arrays.asList("Wilma", "Barney")));
		assertTrue(this.uniqueIdentityPredicate.evaluate(null));
		assertFalse(this.uniqueIdentityPredicate.evaluate(null));
		assertTrue(this.uniqueIdentityPredicate.evaluate(new String("Fred")));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Fred"));
		assertFalse(this.uniqueIdentityPredicate.evaluate("Barney"));
	}
}
