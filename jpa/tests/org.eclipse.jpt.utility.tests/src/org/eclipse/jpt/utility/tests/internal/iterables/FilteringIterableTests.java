/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;

@SuppressWarnings("nls")
public class FilteringIterableTests extends TestCase {
	private static final String PREFIX = "prefix";

	public FilteringIterableTests(String name) {
		super(name);
	}

	public void testAccept() {
		int i = 0;
		for (String s : this.buildIterable()) {
			assertTrue(s.contains(PREFIX));
			i++;
		}
		assertEquals(6, i);
	}

	private Iterable<String> buildIterable() {
		return this.buildFilteringIterable(this.buildNestedIterable());
	}

	private Iterable<String> buildFilteringIterable(Iterable<String> nestedIterable) {
		return new FilteringIterable<String, String>(nestedIterable) {
			@Override
			protected boolean accept(String s) {
				return s.startsWith(PREFIX);
			}
		};
	}

	private Iterable<String> buildNestedIterable() {
		Collection<String> c = new ArrayList<String>();
		c.add(PREFIX + "1");
		c.add(PREFIX + "2");
		c.add(PREFIX + "3");
		c.add("4");
		c.add(PREFIX + "5");
		c.add(PREFIX + "6");
		c.add(PREFIX + "7");
		c.add("8");
		return c;
	}

}
