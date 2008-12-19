/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.Filter;

@SuppressWarnings("nls")
public class FilterTests extends TestCase {

	public FilterTests(String name) {
		super(name);
	}

	public void testNullFilter() {
		Filter<String> filter = Filter.Null.instance();
		assertTrue(filter.accept(""));
		assertTrue(filter.accept("foo"));
		assertTrue(filter.accept("bar"));
	}

	public void testOpaqueFilter() {
		Filter<String> filter = Filter.Opaque.instance();
		assertFalse(filter.accept(""));
		assertFalse(filter.accept("foo"));
		assertFalse(filter.accept("bar"));
	}

	public void testDisabledFilter() {
		Filter<String> filter = Filter.Disabled.instance();
		boolean exCaught = false;
		try {
			assertFalse(filter.accept("foo"));
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

}
