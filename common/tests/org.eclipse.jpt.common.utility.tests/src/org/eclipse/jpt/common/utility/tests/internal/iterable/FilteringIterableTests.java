/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.DisabledPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;

@SuppressWarnings("nls")
public class FilteringIterableTests
	extends TestCase
{
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

	public void testFilter() {
		Predicate<String> filter = this.buildFilter();
		int i = 0;
		for (String s : IterableTools.filter(this.buildNestedIterable(), filter)) {
			assertTrue(s.contains(PREFIX));
			i++;
		}
		assertEquals(6, i);
	}

	public void testSuperFilter() {
		Predicate<Object> filter = this.buildSuperFilter();
		int i = 0;
		for (String s : IterableTools.filter(this.buildNestedIterable(), filter)) {
			assertTrue(s.contains(PREFIX));
			i++;
		}
		assertEquals(6, i);
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	public void testMissingFilter() {
		boolean exCaught = false;
		Iterable<String> iterable = IterableTools.filter(this.buildNestedIterable(), DisabledPredicate.<String>instance());
		try {
			Iterator<String> iterator = iterable.iterator();
			fail("bogus iterator: " + iterator);
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private Iterable<String> buildIterable() {
		return IterableTools.filter(this.buildNestedIterable(), this.buildFilter());
	}

	private Predicate<String> buildFilter() {
		return new StringStartsWith();
	}

	/* CU private */ static class StringStartsWith
		extends PredicateAdapter<String>
	{
		@Override
		public boolean evaluate(String s) {
			return s.startsWith(PREFIX);
		}
	}

	private Predicate<Object> buildSuperFilter() {
		return new ObjectToStringStartsWith();
	}

	/* CU private */ static class ObjectToStringStartsWith
		extends PredicateAdapter<Object>
	{
		@Override
		public boolean evaluate(Object o) {
			return o.toString().startsWith(PREFIX);
		}
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
