/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.SimpleFilter;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

@SuppressWarnings("nls")
public class FilteringIteratorTests extends TestCase {

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

	public void testInnerHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildInnerIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testInnerNext() {
		for (Iterator<String> stream = this.buildInnerIterator(); stream.hasNext();) {
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

	public void testLoadNext() {
		// loadNext() used to cause a NPE when executing during the
		// constructor because the "outer" class is not bound until completion
		// of the constructor
		for (Iterator<String> stream = this.buildInnerIterator2(); stream.hasNext();) {
			assertTrue("bogus accept", stream.next().startsWith(PREFIX));
		}
	}

	// test a filtered iterator with a nested iterator of a sub-type (Number vs. Integer)
	public void testGenerics1() {
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(new Integer(0));
		integers.add(new Integer(1));
		integers.add(new Integer(2));
		integers.add(new Integer(3));
		Iterator<Number> stream = new FilteringIterator<Integer, Number>(integers.iterator()) {
			@Override
			protected boolean accept(Integer o) {
				return o.intValue() > 1;
			}
		};
		assertEquals(2, CollectionTools.size(stream));
	}

	// test a filtered iterator with a nested iterator of a super-type (Integer vs. Number)
	// i.e. trust that Filter will only return the appropriate sub-type objects
	public void testGenerics2() {
		List<Number> numbers = new ArrayList<Number>();
		numbers.add(new Integer(0));
		numbers.add(new Long(1));
		numbers.add(new Integer(2));
		numbers.add(new Float(3));
		Iterator<Integer> stream = new FilteringIterator<Number, Integer>(numbers.iterator()) {
			@Override
			protected boolean accept(Number o) {
				return o instanceof Integer;
			}
		};
		assertEquals(2, CollectionTools.size(stream));
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
		return new FilteringIterator<String, String>(nestedIterator, filter);
	}

	private Iterator<String> buildInnerFilteredIterator(Iterator<String> nestedIterator) {
		return new FilteringIterator<String, String>(nestedIterator) {
			@Override
			protected boolean accept(String s) {
				return s.startsWith(PREFIX);
			}
		};
	}

	String getPrefix() {
		return PREFIX;
	}

	// this inner iterator will call the "outer" object
	private Iterator<String> buildInnerFilteredIterator2(Iterator<String> nestedIterator) {
		return new FilteringIterator<String, String>(nestedIterator) {
			@Override
			protected boolean accept(String s) {
				return s.startsWith(FilteringIteratorTests.this.getPrefix());
			}
		};
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

	private Iterator<String> buildInnerIterator() {
		return this.buildInnerFilteredIterator(this.buildNestedIterator());
	}

	// this inner iterator will call the "outer" object
	private Iterator<String> buildInnerIterator2() {
		return this.buildInnerFilteredIterator2(this.buildNestedIterator());
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
			Iterator<String> iterator = new FilteringIterator<String, String>(this.buildNestedIterator());
			String s = iterator.next();
			fail("invalid string: " + s);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

}
