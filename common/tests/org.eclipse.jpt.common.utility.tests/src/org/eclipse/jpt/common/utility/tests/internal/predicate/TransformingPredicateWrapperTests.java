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
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.predicate.TransformingPredicateWrapper;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TransformingPredicateWrapperTests
	extends TestCase
{
	private TransformingPredicateWrapper<Person, String> predicate;


	public TransformingPredicateWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.predicate = PredicateTools.wrap(Predicate.NotNull.<String>instance(), Person.NAME_TRANSFORMER);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		assertTrue(this.predicate.evaluate(new Person("")));
		assertTrue(this.predicate.evaluate(new Person("Fred")));
		assertFalse(this.predicate.evaluate(new Person(null)));
	}

	public void testClone() {
		TransformingPredicateWrapper<Person, String> predicate2 = this.predicate.clone();
		assertEquals(this.predicate, predicate2);
		assertNotSame(this.predicate, predicate2);
	}

	public void testEquals() {
		TransformingPredicateWrapper<Person, String> predicate2 = PredicateTools.wrap(Predicate.NotNull.<String>instance(), Person.NAME_TRANSFORMER);
		assertEquals(this.predicate, predicate2);
		assertEquals(this.predicate.hashCode(), predicate2.hashCode());
		assertFalse(this.predicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		TransformingPredicateWrapper<Person, String> predicate2 = TestTools.serialize(this.predicate);
		assertEquals(this.predicate, predicate2);
		assertNotSame(this.predicate, predicate2);
	}

	static class Person {
		final String name;
		Person(String name) {
			super();
			this.name = name;
		}
		static final Transformer<Person, String> NAME_TRANSFORMER = new NameTransformer();
		static class NameTransformer
			extends AbstractTransformer<Person, String>
			implements Serializable
		{
			private static final long serialVersionUID = 1L;
			@Override
			protected String transform_(Person person) {
				return person.name;
			}
			@Override
			public boolean equals(Object obj) {
				return obj.getClass() == this.getClass();
			}
			@Override
			public int hashCode() {
				return this.getClass().hashCode();
			}
		}
	}
}
