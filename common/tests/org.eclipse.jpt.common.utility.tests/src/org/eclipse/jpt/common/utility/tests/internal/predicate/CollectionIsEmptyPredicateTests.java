/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CollectionIsEmptyPredicateTests
	extends TestCase
{
	public CollectionIsEmptyPredicateTests(String name) {
		super(name);
	}

	public void testEvaluate() {
		Collection<String> list = new ArrayList<>();
		Predicate<Collection<String>> predicate = PredicateTools.collectionIsEmptyPredicate();
		assertTrue(predicate.evaluate(list));
		list.add("foo");
		assertFalse(predicate.evaluate(list));
		list.remove("foo");
		assertTrue(predicate.evaluate(list));
	}

	public void testToString() {
		Predicate<Collection<String>> predicate = PredicateTools.collectionIsEmptyPredicate();
		assertEquals("CollectionIsEmptyPredicate", predicate.toString());
	}

	public void testSerialization() throws Exception {
		Predicate<Collection<String>> predicate = PredicateTools.collectionIsEmptyPredicate();
		assertSame(predicate, TestTools.serialize(predicate));
	}
}
