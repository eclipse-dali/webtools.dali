/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;

@SuppressWarnings("nls")
public class NotNullFilterTests extends TestCase {

	public NotNullFilterTests(String name) {
		super(name);
	}

	public void testNotNullFilter() {
		Filter<String> filter = NotNullFilter.instance();
		assertTrue(filter.accept(""));
		assertFalse(filter.accept(null));
		assertTrue(filter.accept("foo"));
		assertTrue(filter.accept("bar"));
	}

	public void testToString() {
		Filter<String> filter = NotNullFilter.instance();
		assertNotNull(filter.toString());
	}

}
