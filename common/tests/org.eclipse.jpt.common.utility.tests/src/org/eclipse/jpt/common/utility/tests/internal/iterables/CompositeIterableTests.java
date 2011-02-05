/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterables;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;

@SuppressWarnings("nls")
public class CompositeIterableTests extends TestCase {

	public CompositeIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");

		Collection<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");

		@SuppressWarnings("unchecked")
		Iterable<String> composite = new CompositeIterable<String>(c1, c2);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testExtraElement1() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");

		Iterable<String> composite = new CompositeIterable<String>(c1, "4");
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testExtraElement2() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("1");
		c1.add("2");
		c1.add("3");

		Iterable<String> composite = new CompositeIterable<String>("0", c1);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testCollectionOfIterables() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");

		Collection<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");

		Collection<Iterable<String>> collection = new ArrayList<Iterable<String>>();
		collection.add(c1);
		collection.add(c2);
		Iterable<String> composite = new CompositeIterable<String>(collection);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testToString() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");

		Collection<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");

		@SuppressWarnings("unchecked")
		Iterable<String> composite = new CompositeIterable<String>(c1, c2);
		assertNotNull(composite.toString());
	}

}
