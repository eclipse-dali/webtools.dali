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
import org.eclipse.jpt.common.utility.internal.predicate.MethodPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;
import org.eclipse.jpt.common.utility.internal.transformer.BooleanStringTransformer;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class MethodPredicateTests
	extends TestCase
{
	private MethodPredicate<BooleanReference> methodPredicate;
	private MethodPredicate<Transformer<String, Boolean>> parmMethodPredicate;


	public MethodPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.methodPredicate = PredicateTools.methodPredicate("getValue");
		this.parmMethodPredicate = PredicateTools.methodPredicate("transform", Object.class, "true");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		SimpleBooleanReference ref = new SimpleBooleanReference(false);
		assertFalse(this.methodPredicate.evaluate(ref));
		ref.setValue(true);
		assertTrue(this.methodPredicate.evaluate(ref));

		// this will always evaluate to 'true'
		assertTrue(this.parmMethodPredicate.evaluate(BooleanStringTransformer.instance()));
	}

	public void testClone() {
		MethodPredicate<BooleanReference> methodPredicate2 = this.methodPredicate.clone();
		assertEquals(this.methodPredicate, methodPredicate2);
		assertNotSame(this.methodPredicate, methodPredicate2);

		MethodPredicate<Transformer<String, Boolean>> parmMethodPredicate2 = this.parmMethodPredicate.clone();
		assertEquals(this.parmMethodPredicate, parmMethodPredicate2);
		assertNotSame(this.parmMethodPredicate, parmMethodPredicate2);
	}

	public void testEquals() {
		MethodPredicate<BooleanReference> methodPredicate2 = PredicateTools.methodPredicate("getValue");
		assertEquals(this.methodPredicate, methodPredicate2);
		assertEquals(this.methodPredicate.hashCode(), methodPredicate2.hashCode());
		assertFalse(this.methodPredicate.equals(Predicate.NotNull.instance()));

		MethodPredicate<Transformer<String, Boolean>> parmMethodPredicate2 = PredicateTools.methodPredicate("transform", Object.class, "true");
		assertEquals(this.parmMethodPredicate, parmMethodPredicate2);
		assertEquals(this.parmMethodPredicate.hashCode(), parmMethodPredicate2.hashCode());
		assertFalse(this.parmMethodPredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		MethodPredicate<BooleanReference> methodPredicate2 = TestTools.serialize(this.methodPredicate);
		assertEquals(this.methodPredicate, methodPredicate2);
		assertNotSame(this.methodPredicate, methodPredicate2);

		MethodPredicate<Transformer<String, Boolean>> parmMethodPredicate2 = TestTools.serialize(this.parmMethodPredicate);
		assertEquals(this.parmMethodPredicate, parmMethodPredicate2);
		assertNotSame(this.parmMethodPredicate, parmMethodPredicate2);
	}
}
