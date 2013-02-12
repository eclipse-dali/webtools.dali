/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.filter;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.predicate.Predicate;

@SuppressWarnings("nls")
public class NotNullFilterTests extends TestCase {

	public NotNullFilterTests(String name) {
		super(name);
	}

	public void testNotNullFilter() {
		Predicate<String> filter = NotNullFilter.instance();
		assertTrue(filter.evaluate(""));
		assertFalse(filter.evaluate(null));
		assertTrue(filter.evaluate("foo"));
		assertTrue(filter.evaluate("bar"));
	}

	public void testToString() {
		Predicate<String> filter = NotNullFilter.instance();
		assertNotNull(filter.toString());
	}

}
