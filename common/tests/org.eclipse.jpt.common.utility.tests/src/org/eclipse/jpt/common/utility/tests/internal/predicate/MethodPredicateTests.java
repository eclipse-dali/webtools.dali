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
import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class MethodPredicateTests
	extends TestCase
{
	private Predicate<BooleanReference> methodPredicate;
	private Predicate<Transformer<String, Boolean>> parmMethodPredicate;


	public MethodPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.methodPredicate = PredicateTools.execute("getValue");
		this.parmMethodPredicate = PredicateTools.execute("transform", Object.class, "true");
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
		assertTrue(this.parmMethodPredicate.evaluate(TransformerTools.stringToBooleanTransformer()));
	}

	public void testEquals() {
		Predicate<BooleanReference> methodPredicate2 = PredicateTools.execute("getValue");
		assertEquals(this.methodPredicate, methodPredicate2);
		assertEquals(this.methodPredicate.hashCode(), methodPredicate2.hashCode());
		assertFalse(this.methodPredicate.equals(IsNotNull.instance()));

		Predicate<Transformer<String, Boolean>> parmMethodPredicate2 = PredicateTools.execute("transform", Object.class, "true");
		assertEquals(this.parmMethodPredicate, parmMethodPredicate2);
		assertEquals(this.parmMethodPredicate.hashCode(), parmMethodPredicate2.hashCode());
		assertFalse(this.parmMethodPredicate.equals(IsNotNull.instance()));
	}
}
