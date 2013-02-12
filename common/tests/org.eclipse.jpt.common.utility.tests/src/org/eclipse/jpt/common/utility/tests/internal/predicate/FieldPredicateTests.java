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
import org.eclipse.jpt.common.utility.internal.predicate.FieldPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FieldPredicateTests
	extends TestCase
{
	private FieldPredicate<BooleanReference> fieldPredicate;


	public FieldPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.fieldPredicate = PredicateTools.fieldPredicate("value");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testEvaluate() {
		SimpleBooleanReference ref = new SimpleBooleanReference(false);
		assertFalse(this.fieldPredicate.evaluate(ref));
		ref.setValue(true);
		assertTrue(this.fieldPredicate.evaluate(ref));
	}

	public void testClone() {
		FieldPredicate<BooleanReference> fieldPredicate2 = this.fieldPredicate.clone();
		assertEquals(this.fieldPredicate, fieldPredicate2);
		assertNotSame(this.fieldPredicate, fieldPredicate2);
	}

	public void testEquals() {
		FieldPredicate<BooleanReference> fieldPredicate2 = PredicateTools.fieldPredicate("value");
		assertEquals(this.fieldPredicate, fieldPredicate2);
		assertEquals(this.fieldPredicate.hashCode(), fieldPredicate2.hashCode());
		assertFalse(this.fieldPredicate.equals(Predicate.NotNull.instance()));
	}

	public void testSerialization() throws Exception {
		FieldPredicate<BooleanReference> fieldPredicate2 = TestTools.serialize(this.fieldPredicate);
		assertEquals(this.fieldPredicate, fieldPredicate2);
		assertNotSame(this.fieldPredicate, fieldPredicate2);
	}
}
