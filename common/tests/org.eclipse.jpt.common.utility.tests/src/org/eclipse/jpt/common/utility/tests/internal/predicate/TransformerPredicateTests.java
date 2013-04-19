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

import java.io.Serializable;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TransformerPredicateTests
	extends TestCase
{
	private Transformer<Integer, Boolean> transformer;
	private Predicate<Integer> transformerPredicate;


	public TransformerPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.transformer = new LessThan42Transformer();
		this.transformerPredicate = PredicateTools.adapt(this.transformer);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	@SuppressWarnings("boxing")
	public void testEvaluate() {
		assertTrue(this.transformerPredicate.evaluate(new Integer(-42)));
		assertFalse(this.transformerPredicate.evaluate(new Integer(42)));
		assertFalse(this.transformerPredicate.evaluate(new Integer(4242)));

		assertTrue(this.transformerPredicate.evaluate(-42));
		assertFalse(this.transformerPredicate.evaluate(42));
		assertFalse(this.transformerPredicate.evaluate(4242));

		assertTrue(this.transformerPredicate.evaluate(Integer.valueOf("-42")));
		assertFalse(this.transformerPredicate.evaluate(Integer.valueOf("42")));
		assertFalse(this.transformerPredicate.evaluate(Integer.valueOf("4242")));

		assertFalse(this.transformerPredicate.evaluate(null));
	}

	public void testEquals() {
		Predicate<Integer> transformerPredicate2 = PredicateTools.adapt(this.transformer);
		assertEquals(this.transformerPredicate, transformerPredicate2);
		assertEquals(this.transformerPredicate.hashCode(), transformerPredicate2.hashCode());
		assertFalse(this.transformerPredicate.equals(IsNotNull.instance()));
	}

	static class LessThan42Transformer
		extends AbstractTransformer<Integer, Boolean>
		implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Override
		protected Boolean transform_(Integer integer) {
			return Boolean.valueOf(integer.intValue() < 42);
		}
		@Override
		public boolean equals(Object obj) {
			return obj.getClass() == this.getClass();
		}
		@Override
		public int hashCode() {
			return 42;
		}
	}
}
