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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.predicate.IsNotNull;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class TransformationPredicateTests
	extends TestCase
{
	private Predicate<Person> predicate;


	public TransformationPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.predicate = PredicateTools.transformVariable(IsNotNull.instance(), Person.NAME_TRANSFORMER);
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

	public void testEquals() {
		Predicate<Person> predicate2 = PredicateTools.transformVariable(IsNotNull.instance(), Person.NAME_TRANSFORMER);
		assertEquals(this.predicate, predicate2);
		assertEquals(this.predicate.hashCode(), predicate2.hashCode());
		assertFalse(this.predicate.equals(IsNotNull.instance()));
	}

	static class Person {
		final String name;
		Person(String name) {
			super();
			this.name = name;
		}
		static final Transformer<Person, String> NAME_TRANSFORMER = new NameTransformer();
		static class NameTransformer
			extends TransformerAdapter<Person, String>
			implements Serializable
		{
			private static final long serialVersionUID = 1L;
			@Override
			public String transform(Person person) {
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
