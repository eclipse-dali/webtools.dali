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
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FieldPredicateTests
	extends TestCase
{
	private Predicate<BooleanReference> fieldPredicate;


	public FieldPredicateTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.fieldPredicate = PredicateTools.get("value");
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

	public void testEquals() {
		Predicate<BooleanReference> fieldPredicate2 = PredicateTools.get("value");
		assertEquals(this.fieldPredicate, fieldPredicate2);
		assertEquals(this.fieldPredicate.hashCode(), fieldPredicate2.hashCode());
		assertFalse(this.fieldPredicate.equals(IsNotNull.instance()));
	}
}
