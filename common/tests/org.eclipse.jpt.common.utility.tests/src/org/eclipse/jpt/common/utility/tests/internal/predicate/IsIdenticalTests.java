/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
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

public class IsIdenticalTests
	extends TestCase
{
	private Integer integer = Integer.valueOf(42);
	private Predicate<Object> identityPredicate;


	public IsIdenticalTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.identityPredicate = PredicateTools.isIdentical(this.integer);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		assertTrue(this.identityPredicate.evaluate(this.integer));
		assertFalse(this.identityPredicate.evaluate(new Integer(42)));
		assertTrue(this.identityPredicate.evaluate(42));
		assertFalse(this.identityPredicate.evaluate(null));
		assertFalse(this.identityPredicate.evaluate(24));
	}

	public void testEquals() {
		Predicate<Object> identityPredicate2 = PredicateTools.isIdentical(new Integer(42));
		assertEquals(this.identityPredicate, identityPredicate2);
		assertEquals(this.identityPredicate.hashCode(), identityPredicate2.hashCode());
		assertFalse(this.identityPredicate.equals(IsNotNull.instance()));
	}
}
