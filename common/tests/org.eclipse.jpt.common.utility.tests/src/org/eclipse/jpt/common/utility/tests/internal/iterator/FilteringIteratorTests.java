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
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.DisabledPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

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

	private Iterator<String> buildFilteredIterator(Iterator<String> nestedIterator, Predicate<String> filter) {
		return IteratorTools.filter(nestedIterator, filter);
	}

	private Iterator<String> buildSuperFilteredIterator(Iterator<String> nestedIterator, Predicate<Object> filter) {
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
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildStringStartsWith(PREFIX));
	}

	private Iterator<String> buildSuperAcceptIterator() {
		return this.buildSuperFilteredIterator(this.buildNestedIterator(), this.buildObjectStartsWith(PREFIX));
	}

	private Predicate<String> buildStringStartsWith(String prefix) {
		return new StringStartsWith(prefix);
	}

	class StringStartsWith
		extends CriterionPredicate<String, String>
	{
		StringStartsWith(String criterion) {
			super(criterion);
		}
		public boolean evaluate(String s) {
			return s.startsWith(this.criterion);
		}
	}

	private Predicate<Object> buildObjectStartsWith(String prefix) {
		return new ObjectToStringStartsWith(prefix);
	}

	class ObjectToStringStartsWith
		extends CriterionPredicate<Object, String>
	{
		ObjectToStringStartsWith(String criterion) {
			super(criterion);
		}
		public boolean evaluate(Object o) {
			return o.toString().startsWith(this.criterion);
		}
	}

	private Iterator<String> buildRejectIterator() {
		return this.buildFilteredIterator(this.buildNestedIterator(), this.buildRejectFilter(PREFIX));
	}

	private Predicate<String> buildRejectFilter(String prefix) {
		return PredicateTools.not(this.buildStringStartsWith(prefix));
	}

	public void testInvalidFilteringIterator() {
		boolean exCaught = false;
		try {
			// missing method override
			Iterator<String> iterator = IteratorTools.filter(this.buildNestedIterator(), DisabledPredicate.<String>instance());
			String s = iterator.next();
			fail("invalid string: " + s);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

}
