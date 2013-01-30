/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.SimultaneousIterator;

@SuppressWarnings("nls")
public class SimultaneousIteratorTests
	extends TestCase
{
	public SimultaneousIteratorTests(String name) {
		super(name);
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Iterator<List<String>> iterator = this.buildIterator((Iterable<ListIterator<String>>) null);
			fail("bogus iterator: " + iterator);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<List<String>> stream = this.buildIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(this.buildList2().size(), i);
	}

	public void testHasNextEmpty() {
		Iterator<List<String>> iterator = this.buildIterator(Collections.<ListIterator<String>>emptyList());
		assertFalse(iterator.hasNext());
	}

	public void testNext() {
		Iterator<List<String>> stream = this.buildIterator();
		List<String> next = stream.next();
		assertEquals("1", next.get(0));
		assertEquals("one", next.get(1));

		next = stream.next();
		assertEquals("2", next.get(0));
		assertEquals("two", next.get(1));

		next = stream.next();
		assertEquals("3", next.get(0));
		assertEquals("three", next.get(1));

		next = stream.next();
		assertEquals("4", next.get(0));
		assertEquals("four", next.get(1));

		next = stream.next();
		assertEquals("5", next.get(0));
		assertEquals("five", next.get(1));

		next = stream.next();
		assertEquals("6", next.get(0));
		assertEquals("six", next.get(1));

		next = stream.next();
		assertEquals("7", next.get(0));
		assertEquals("seven", next.get(1));
	}

	public void testNextEmpty() {
		Iterator<List<String>> iterator = this.buildIterator(Collections.<ListIterator<String>>emptyList());
		boolean exCaught = false;
		try {
			List<String> next = iterator.next();
			fail("bogus element: " + next);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove() {
		List<String> list1 = this.buildList1();
		List<String> list2 = this.buildList2();
		assertTrue(list1.contains("2"));
		assertTrue(list2.contains("two"));

		Iterator<List<String>> stream = this.buildIterator(list1, list2);
		List<String> next = stream.next();
		assertEquals("1", next.get(0));
		assertEquals("one", next.get(1));

		next = stream.next();
		assertEquals("2", next.get(0));
		assertEquals("two", next.get(1));

		stream.remove();
		assertFalse(list1.contains("2"));
		assertFalse(list1.contains("two"));
	}

	public void testRemoveEmpty() {
		Iterator<List<String>> iterator = this.buildIterator(Collections.<ListIterator<String>>emptyList());
		boolean exCaught = false;
		try {
			iterator.remove();
			fail("bogus iterator: " + iterator);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private List<String> buildList1() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		return list;
	}

	private List<String> buildList2() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		list.add("five");
		list.add("six");
		list.add("seven");
		return list;
	}

	protected Iterator<List<String>> buildIterator() {
		return this.buildIterator(this.buildList1(), this.buildList2());
	}

	@SuppressWarnings("unchecked")
	protected Iterator<List<String>> buildIterator(List<String> list1, List<String> list2) {
		return this.buildIterator(list1.listIterator(), list2.listIterator());
	}

	protected Iterator<List<String>> buildIterator(ListIterator<String>... iterators) {
		return IteratorTools.align(iterators);
	}

	protected Iterator<List<String>> buildIterator(Iterable<ListIterator<String>> iterators) {
		return new SimultaneousIterator<String>(iterators);
	}
}
