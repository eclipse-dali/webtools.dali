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
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.ReadOnlyCompositeListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListListIterable;

@SuppressWarnings("nls")
public class ReadOnlyCompositeListIterableTests extends TestCase {

	public ReadOnlyCompositeListIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		List<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");
		ListIterable<String> li1 = new ListListIterable<String>(c1);

		List<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");
		ListIterable<String> li2 = new ListListIterable<String>(c2);

		@SuppressWarnings("unchecked")
		Iterable<String> composite = new ReadOnlyCompositeListIterable<String>(li1, li2);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testExtraElement1() {
		List<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");
		ListIterable<String> li1 = new ListListIterable<String>(c1);

		Iterable<String> composite = new ReadOnlyCompositeListIterable<String>(li1, "4");
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testExtraElement2() {
		List<String> c1 = new ArrayList<String>();
		c1.add("1");
		c1.add("2");
		c1.add("3");
		ListIterable<String> li1 = new ListListIterable<String>(c1);

		Iterable<String> composite = new ReadOnlyCompositeListIterable<String>("0", li1);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testCollectionOfIterables() {
		List<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");
		ListIterable<String> li1 = new ListListIterable<String>(c1);

		List<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");
		ListIterable<String> li2 = new ListListIterable<String>(c2);

		List<ListIterable<String>> collection = new ArrayList<ListIterable<String>>();
		collection.add(li1);
		collection.add(li2);
		ListIterable<ListIterable<String>> li = new ListListIterable<ListIterable<String>>(collection);
		
		Iterable<String> composite = new ReadOnlyCompositeListIterable<String>(li);
		int i = 0;
		for (String s : composite) {
			assertEquals(String.valueOf(i++), s);
		}
	}

	public void testToString() {
		List<String> c1 = new ArrayList<String>();
		c1.add("0");
		c1.add("1");
		c1.add("2");
		c1.add("3");
		ListIterable<String> li1 = new ListListIterable<String>(c1);

		List<String> c2 = new ArrayList<String>();
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");
		ListIterable<String> li2 = new ListListIterable<String>(c2);

		@SuppressWarnings("unchecked")
		Iterable<String> composite = new ReadOnlyCompositeListIterable<String>(li1, li2);
		assertNotNull(composite.toString());
	}

}
