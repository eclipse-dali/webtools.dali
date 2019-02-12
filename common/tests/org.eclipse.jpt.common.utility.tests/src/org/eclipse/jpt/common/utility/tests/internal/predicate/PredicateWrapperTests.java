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

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateWrapper;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class PredicateWrapperTests
	extends TestCase
{
	private Predicate<Integer> wrappedPredicate;
	private PredicateWrapper<Integer> predicateWrapper;


	public PredicateWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedPredicate = PredicateTools.isEqual(Integer.valueOf(42));
		this.predicateWrapper = PredicateTools.wrap(this.wrappedPredicate);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		assertTrue(this.predicateWrapper.evaluate(new Integer(42)));
		assertTrue(this.predicateWrapper.evaluate(42));
		assertTrue(this.predicateWrapper.evaluate(Integer.valueOf("42")));
		assertFalse(this.predicateWrapper.evaluate(null));
		assertFalse(this.predicateWrapper.evaluate(24));

		this.predicateWrapper.setPredicate(PredicateTools.isEqual(Integer.valueOf(13)));
		assertTrue(this.predicateWrapper.evaluate(new Integer(13)));
		assertFalse(this.predicateWrapper.evaluate(new Integer(42)));
		assertFalse(this.predicateWrapper.evaluate(42));
		assertFalse(this.predicateWrapper.evaluate(Integer.valueOf("42")));
		assertFalse(this.predicateWrapper.evaluate(null));
		assertFalse(this.predicateWrapper.evaluate(24));
	}

	public void testEquals() {
		PredicateWrapper<Integer> predicateWrapper2 = PredicateTools.wrap(this.wrappedPredicate);
		assertEquals(this.predicateWrapper, predicateWrapper2);
		assertEquals(this.predicateWrapper.hashCode(), predicateWrapper2.hashCode());
		assertFalse(this.predicateWrapper.equals(IsNotNull.instance()));
	}
}
