/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.predicate;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class PredicateTests
	extends TestCase
{
	public PredicateTests(String name) {
		super(name);
	}

	public void testPredicateAdapter() {
		Predicate<String> filter = new Predicate.Adapter<String>();
		assertTrue(filter.evaluate(""));
		assertTrue(filter.evaluate(null));
		assertTrue(filter.evaluate("foo"));
		assertTrue(filter.evaluate("bar"));
	}

	public void testPredicateAdapter_toString() {
		Predicate<String> filter = new Predicate.Adapter<String>();
		assertNotNull(filter.toString());
	}

	public void testTruePredicate() {
		Predicate<String> predicate = Predicate.True.instance();
		assertTrue(predicate.evaluate(""));
		assertTrue(predicate.evaluate("foo"));
		assertTrue(predicate.evaluate("bar"));
	}

	public void testTruePredicate_toString() {
		Predicate<String> predicate = Predicate.True.instance();
		assertNotNull(predicate.toString());
	}

	public void testTruePredicate_serialization() throws Exception {
		Predicate<String> predicate = Predicate.True.instance();
		assertSame(predicate, TestTools.serialize(predicate));
	}

	public void testFalsePredicate() {
		Predicate<String> predicate = Predicate.False.instance();
		assertFalse(predicate.evaluate(""));
		assertFalse(predicate.evaluate("foo"));
		assertFalse(predicate.evaluate("bar"));
	}

	public void testFalsePredicate_toString() {
		Predicate<String> predicate = Predicate.False.instance();
		assertNotNull(predicate.toString());
	}

	public void testFalsePredicate_serialization() throws Exception {
		Predicate<String> predicate = Predicate.False.instance();
		assertSame(predicate, TestTools.serialize(predicate));
	}

	public void testNotNullPredicate() {
		Predicate<String> filter = PredicateTools.notNullPredicate();
		assertTrue(filter.evaluate(""));
		assertFalse(filter.evaluate(null));
		assertTrue(filter.evaluate("foo"));
		assertTrue(filter.evaluate("bar"));
	}

	public void testNotNullPredicate_toString() {
		Predicate<String> filter = PredicateTools.notNullPredicate();
		assertNotNull(filter.toString());
	}

	public void testNullPredicate() {
		Predicate<String> filter = PredicateTools.nullPredicate();
		assertFalse(filter.evaluate(""));
		assertTrue(filter.evaluate(null));
		assertFalse(filter.evaluate("foo"));
		assertFalse(filter.evaluate("bar"));
	}

	public void testNullPredicate_toString() {
		Predicate<String> filter = PredicateTools.nullPredicate();
		assertNotNull(filter.toString());
	}

	public void testDisabledPredicate() {
		Predicate<String> predicate = Predicate.Disabled.instance();
		boolean exCaught = false;
		try {
			assertFalse(predicate.evaluate("foo"));
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledPredicate_toString() {
		Predicate<String> predicate = Predicate.Disabled.instance();
		assertNotNull(predicate.toString());
	}

	public void testDisabledPredicate_serialization() throws Exception {
		Predicate<String> predicate = Predicate.Disabled.instance();
		assertSame(predicate, TestTools.serialize(predicate));
	}
}
