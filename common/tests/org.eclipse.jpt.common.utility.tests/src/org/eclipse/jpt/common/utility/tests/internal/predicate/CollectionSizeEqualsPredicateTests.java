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
import org.eclipse.jpt.common.utility.internal.predicate.CollectionSizeEqualsPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CollectionSizeEqualsPredicateTests
	extends TestCase
{
	public CollectionSizeEqualsPredicateTests(String name) {
		super(name);
	}

	public void testEvaluate() {
		Collection<String> list = new ArrayList<>();
		Predicate<Collection<String>> predicate = PredicateTools.collectionSizeEqualsPredicate(2);
		assertFalse(predicate.evaluate(list));
		list.add("foo");
		assertFalse(predicate.evaluate(list));
		list.add("bar");
		assertTrue(predicate.evaluate(list));
		list.add("baz");
		assertFalse(predicate.evaluate(list));
		list.remove("bar");
		assertTrue(predicate.evaluate(list));
		list.remove("foo");
		assertFalse(predicate.evaluate(list));
		list.remove("baz");
		assertFalse(predicate.evaluate(list));
	}

	public void testGetSize() throws Exception {
		CollectionSizeEqualsPredicate<Collection<String>> predicate = (CollectionSizeEqualsPredicate<Collection<String>>) PredicateTools.<Collection<String>>collectionSizeEqualsPredicate(2);
		assertEquals(2, predicate.getSize());
	}

	public void testEquals() throws Exception {
		Predicate<Collection<String>> predicate1 = PredicateTools.collectionSizeEqualsPredicate(2);
		assertFalse(predicate1.equals(null));
		Predicate<Collection<String>> predicate2 = PredicateTools.collectionSizeEqualsPredicate(2);
		assertTrue(predicate1.equals(predicate2));
		predicate2 = PredicateTools.collectionSizeEqualsPredicate(3);
		assertFalse(predicate1.equals(predicate2));
		predicate2 = PredicateTools.collectionIsEmptyPredicate();
		assertFalse(predicate1.equals(predicate2));
	}

	public void testHashCode() throws Exception {
		Predicate<Collection<String>> predicate = PredicateTools.collectionSizeEqualsPredicate(2);
		assertEquals(2, predicate.hashCode());
	}

	public void testToString() {
		Predicate<Collection<String>> predicate = PredicateTools.collectionSizeEqualsPredicate(2);
		assertTrue(predicate.toString().indexOf("(2)") != -1);
	}

	public void testSerialization() throws Exception {
		Predicate<Collection<String>> predicate = PredicateTools.collectionSizeEqualsPredicate(2);
		assertEquals(predicate, TestTools.serialize(predicate));
	}
}
