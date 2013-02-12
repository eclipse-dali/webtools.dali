/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.filter;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FilterTests extends TestCase {

	public FilterTests(String name) {
		super(name);
	}

	public void testNullFilter() {
		Predicate<String> filter = Predicate.True.instance();
		assertTrue(filter.evaluate(""));
		assertTrue(filter.evaluate("foo"));
		assertTrue(filter.evaluate("bar"));
	}

	public void testNullFilter_toString() {
		Predicate<String> filter = Predicate.True.instance();
		assertNotNull(filter.toString());
	}

	public void testNullFilter_serialization() throws Exception {
		Predicate<String> filter = Predicate.True.instance();
		assertSame(filter, TestTools.serialize(filter));
	}

	public void testOpaqueFilter() {
		Predicate<String> filter = Predicate.False.instance();
		assertFalse(filter.evaluate(""));
		assertFalse(filter.evaluate("foo"));
		assertFalse(filter.evaluate("bar"));
	}

	public void testOpaqueFilter_toString() {
		Predicate<String> filter = Predicate.False.instance();
		assertNotNull(filter.toString());
	}

	public void testOpaqueFilter_serialization() throws Exception {
		Predicate<String> filter = Predicate.False.instance();
		assertSame(filter, TestTools.serialize(filter));
	}

	public void testDisabledFilter() {
		Predicate<String> filter = Predicate.Disabled.instance();
		boolean exCaught = false;
		try {
			assertFalse(filter.evaluate("foo"));
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledFilter_toString() {
		Predicate<String> filter = Predicate.Disabled.instance();
		assertNotNull(filter.toString());
	}

	public void testDisabledFilter_serialization() throws Exception {
		Predicate<String> filter = Predicate.Disabled.instance();
		assertSame(filter, TestTools.serialize(filter));
	}
}
