/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.filter.SimpleFilter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

@SuppressWarnings("nls")
public class FilteringIteratorTests
	extends TestCase
{
	private static final String PREFIX = "prefix";

	public FilteringIteratorTests(String name) {
		super(name);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildAcceptIterator(); stream.hasNext();) {
			String string = stream.next();
			if (string.equals(PREFIX + "3")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildAcceptIterator();
		String string = null;
		while (stream.hasNext()) {
			string = stream.next();
		}
		try {
			string = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	public void testAcceptHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildAcceptIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testAcceptNext() {
		for (Iterator<String> stream = this.buildAcceptIterator(); stream.hasNext();) {
			assertTrue("bogus accept", stream.next().startsWith(PREFIX));
		}
	}

	public void testAcceptNext_super() {
		for (Iterator<String> stream = this.buildSuperAcceptIterator(); stream.hasNext();) {
			assertTrue("bogus accept", stream.next().startsWith(PREFIX));
		}
	}

	public void testRejectHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildRejectIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(2, i);
	}

	public void testRejectNext() {
		for (Iterator<String> stream = this.buildRejectIterator(); stream.hasNext();) {
			assertFalse("bogus reject", stream.next().startsWith(PREFIX));
		}
	}

	public void testBothHasNext() {
		// if both accept() and reject() are overridden, accept() is used
		int i = 0;
		for (Iterator<String> stream = this.buildBothIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testFilterHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildFilterIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testFilterNext() {
		for (Iterator<String> stream = this.buildFilterIterator(); stream.hasNext();) {
			assertTrue("bogus accept", stream.next().startsWith(PREFIX));
		}
	}

	private Iterator<String> buildFilteredIterator(Iterator<String> nestedIterator, Filter<String> filter) {
		return IteratorTools.filter(nestedIterator, filter);
	}

	private Iterator<String> buildSuperFilteredIterator(Iterator<String> nestedIterator, Filter<Object> filter) {
		return IteratorTools.<String>filter(nestedIterator, filter);
	}

	String getPrefix() {
		return PREFIX;
	}

	private Iterator<String> buildNestedIterator() {
		Collection<String> c = new ArrayList<String>();
		c.add(PREFIX + "1");
		c.add(PREFIX + "2");
		c.add(PREFIX + "3");
		c.add("4");
		c.add(PREFIX + "5");
		c.add(PREFIX + "6");
		c.add(PREFIX + "7");
		c.add("8");
		return c.iterator();
	}

	private Iterator<String> buildAcceptIterator() {
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildAcceptFilter(PREFIX));
	}

	private Iterator<String> buildSuperAcceptIterator() {
		return this.buildSuperFilteredIterator(this.buildNestedIterator(), this.buildSuperAcceptFilter(PREFIX));
	}

	private Iterator<String> buildFilterIterator() {
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildFilterFilter(PREFIX));
	}

	private Filter<String> buildAcceptFilter(String prefix) {
		return new SimpleFilter<String, String>(prefix) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean accept(String s) {
				return s.startsWith(this.criterion);
			}
		};
	}

	private Filter<Object> buildSuperAcceptFilter(String prefix) {
		return new SimpleFilter<Object, String>(prefix) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean accept(Object o) {
				return o.toString().startsWith(this.criterion);
			}
		};
	}

	private Iterator<String> buildRejectIterator() {
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildRejectFilter(PREFIX));
	}

	private Filter<String> buildRejectFilter(String prefix) {
		return new SimpleFilter<String, String>(prefix) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean reject(String s) {
				return s.startsWith(this.criterion);
			}
		};
	}

	// use anonymous inner Filter
	private Filter<String> buildFilterFilter(final String prefix) {
		return new Filter<String>() {
			public boolean accept(String s) {
				return s.startsWith(prefix);
			}
		};
	}

	private Iterator<String> buildBothIterator() {
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildBothFilter(PREFIX));
	}

	private Filter<String> buildBothFilter(String prefix) {
		return new SimpleFilter<String, String>(prefix) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean reject(String s) {
				return s.startsWith(this.criterion);
			}

			@Override
			public boolean accept(String s) {
				return s.startsWith(this.criterion);
			}
		};
	}

	public void testInvalidFilteringIterator() {
		boolean exCaught = false;
		try {
			// missing method override
			Iterator<String> iterator = IteratorTools.filter(this.buildNestedIterator(), Filter.Disabled.<String>instance());
			String s = iterator.next();
			fail("invalid string: " + s);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

}
