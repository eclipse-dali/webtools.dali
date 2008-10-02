/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;

public class CompositeListIteratorTests extends ReadOnlyCompositeListIteratorTests {

	public CompositeListIteratorTests(String name) {
		super(name);
	}

	@Override
	public void testRemove() {
		super.testRemove();
		List<String> list1 = this.buildList1();
		List<String> list2 = this.buildList2();
		List<String> list3 = this.buildList3();
		Object firstElement3 = list3.get(0);

		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(list1.listIterator());
		list.add(list2.listIterator());
		list.add(list3.listIterator());

		ListIterator<String> stream = (ListIterator<String>) this.buildCompositeIterator(list.listIterator());
		// position to end of stream
		while (stream.hasNext()) {
			stream.next();
		}
		while (stream.hasPrevious()) {
			Object previous = stream.previous();
			if (previous.equals("333")) {
				stream.remove();
			}
			// test special case - where we are between iterators
			if (previous.equals(firstElement3)) {
				// this will trigger the next iterator to be loaded
				stream.hasPrevious();
				// now try to remove from the previous iterator
				stream.remove();
			}
		}
		stream.remove();

		assertEquals("nothing removed from collection 1", this.buildList1().size() - 2, list1.size());
		assertFalse("element still in collection 1", list1.contains("1"));
		assertFalse("element still in collection 1", list1.contains("333"));

		assertEquals("nothing removed from collection 3", this.buildList3().size() - 1, list3.size());
		assertFalse("first element still in collection 3", list3.contains(firstElement3));
		assertTrue("wrong element removed from collection 3", list3.contains("666666"));
	}

	public void testAdd() {
		List<String> list1 = this.buildList1();
		Object lastElement1 = list1.get(list1.size() - 1);
		List<String> list2 = this.buildList2();
		List<String> list3 = this.buildList3();
		Object firstElement3 = list3.get(0);

		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(list1.listIterator());
		list.add(list2.listIterator());
		list.add(list3.listIterator());

		ListIterator<String> stream = (ListIterator<String>) this.buildCompositeIterator(list.listIterator());
		while (stream.hasNext()) {
			Object next = stream.next();
			if (next.equals("333")) {
				stream.add("3.5");
			}
			// test special case - where we are between iterators
			if (next.equals(lastElement1)) {
				// this will trigger the next iterator to be loaded
				stream.hasNext();
				// now try to add to the iterator
				stream.add("something in 3");
			}
		}
		stream.add("finale");
		boolean checkForFinale = true;
		while (stream.hasPrevious()) {
			Object previous = stream.previous();
			if (checkForFinale) {
				checkForFinale = false;
				assertEquals("added element dropped", "finale", previous);
			}
			if (previous.equals("333")) {
				stream.add("2.5");
			}
			// test special case - where we are between iterators
			if (previous.equals(firstElement3)) {
				// this will trigger the next iterator to be loaded
				stream.hasPrevious();
				// now try to remove from the previous iterator
				stream.add("old start of 3");
			}
		}
		stream.add("prelude");
		assertEquals("added element dropped", "prelude", stream.previous());

		assertEquals("elements not added to collection 1", this.buildList1().size() + 3, list1.size());
		assertEquals("element not added to collection 1", "prelude", list1.get(0));
		assertEquals("element not added to collection 1", "2.5", list1.get(3));
		assertEquals("element not added to collection 1", "3.5", list1.get(5));

		assertEquals("elements not added to collection 3", this.buildList3().size() + 3, list3.size());
		assertEquals("element not added to collection 3", "something in 3", list3.get(0));
		assertEquals("element not added to collection 3", "old start of 3", list3.get(1));
		assertEquals("element not added to collection 3", "finale", list3.get(list3.size() - 1));

		// add to the front
		stream = (ListIterator<String>) this.buildCompositeIterator();
		stream.add("blah");
		assertFalse("added element should be placed BEFORE the \"cursor\"", stream.next().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		stream.add("blah");
		assertTrue("added element should be placed BEFORE the \"cursor\"", stream.previous().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		while (stream.hasPrevious()) {
			stream.previous();
		}
		stream.add("blah");
		assertFalse("added element should be placed BEFORE the \"cursor\"", stream.next().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		while (stream.hasPrevious()) {
			stream.previous();
		}
		stream.add("blah");
		assertTrue("added element should be placed BEFORE the \"cursor\"", stream.previous().equals("blah"));

		// add to the middle
		stream = (ListIterator<String>) this.buildCompositeIterator();
		stream.next();
		stream.add("blah");
		assertFalse("added element should be placed BEFORE the \"cursor\"", stream.next().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		stream.next();
		stream.add("blah");
		assertTrue("added element should be placed BEFORE the \"cursor\"", stream.previous().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		stream.previous();
		stream.add("blah");
		assertFalse("added element should be placed BEFORE the \"cursor\"", stream.next().equals("blah"));

		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		stream.previous();
		stream.add("blah");
		assertTrue("added element should be placed BEFORE the \"cursor\"", stream.previous().equals("blah"));

		// add to the end
		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		stream.add("blah");
		assertFalse("added element should be placed BEFORE the \"cursor\"", stream.hasNext());

		stream = (ListIterator<String>) this.buildCompositeIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		stream.add("blah");
		assertTrue("added element should be placed BEFORE the \"cursor\"", stream.previous().equals("blah"));
	}

	public void testSet() {
		List<String> list1 = this.buildList1();
		Object lastElement1 = list1.get(list1.size() - 1);
		List<String> list2 = this.buildList2();
		List<String> list3 = this.buildList3();
		Object firstElement3 = list3.get(0);

		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(list1.listIterator());
		list.add(list2.listIterator());
		list.add(list3.listIterator());

		ListIterator<String> stream = (ListIterator<String>) this.buildCompositeIterator(list.listIterator());
		// position to end of stream
		while (stream.hasNext()) {
			Object next = stream.next();
			if (next.equals("333")) {
				stream.set("333a");
			}
			// test special case - where we are between iterators
			if (next.equals(lastElement1)) {
				// this will trigger the next iterator to be loaded
				stream.hasNext();
				// now try to remove from the previous iterator
				stream.set("end of 1");
			}
		}
		while (stream.hasPrevious()) {
			Object previous = stream.previous();
			if (previous.equals("22")) {
				stream.set("22a");
			}
			// test special case - where we are between iterators
			if (previous.equals(firstElement3)) {
				// this will trigger the next iterator to be loaded
				stream.hasPrevious();
				// now try to remove from the previous iterator
				stream.set("start of 3");
			}
		}

		assertEquals("element(s) added to collection 1", this.buildList1().size(), list1.size());
		assertEquals("element not set in collection 1", "22a", list1.get(1));
		assertFalse("element not set in collection 1", list1.contains("22"));
		assertEquals("element not set in collection 1", "333a", list1.get(2));
		assertFalse("element not set in collection 1", list1.contains("333"));
		assertEquals("element not set in collection 1", "end of 1", list1.get(list1.size() - 1));
		assertFalse("element not set in collection 1", list1.contains(lastElement1));

		assertEquals("element(s) added to collection 3", this.buildList3().size(), list3.size());
		assertEquals("element not set in collection 3", "start of 3", list3.get(0));
		assertFalse("element not set in collection 3", list3.contains(firstElement3));
	}

	@Override
	public void testNextIndexPreviousIndex() {
		int i = 0;
		ListIterator<String> stream = (ListIterator<String>) this.buildCompositeIterator();
		assertEquals(i, stream.nextIndex());
		assertEquals(i - 1, stream.previousIndex());
		while (stream.hasNext()) {
			Object next = stream.next();
			i++;
			if (next.equals("333")) {
				stream.remove();
				i--;
			}
			if (next.equals("7777777")) {
				stream.add("7.5");
				i++;
			}
			assertEquals(i, stream.nextIndex());
			assertEquals(i - 1, stream.previousIndex());
		}
		assertEquals("index is corrupt", 8, i);

		assertEquals(i, stream.nextIndex());
		assertEquals(i - 1, stream.previousIndex());
		while (stream.hasPrevious()) {
			Object previous = stream.previous();
			i--;
			if (previous.equals("666666")) {
				stream.remove();
				// removing a previous element, does not change the cursor
			}
			if (previous.equals("22")) {
				stream.add("1.5");
				i++;
			}
			assertEquals(i, stream.nextIndex());
			assertEquals(i - 1, stream.previousIndex());
		}
		assertEquals("index is corrupt", 0, i);
	}

	@Override
	public void testIllegalStateException() {
		this.verifyIllegalStateException();
	}

	@Override
	public void testEmptyIllegalStateException1() {
		this.verifyEmptyIllegalStateException1();
	}

	@Override
	public void testEmptyIllegalStateException2() {
		this.verifyEmptyIllegalStateException2();
	}

	// unchecked so we can override the unchecked method in superclass
	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator(Iterator iterators) {
		return new CompositeListIterator<String>((ListIterator<ListIterator<String>>) iterators);
	}

	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator2() {
		return new CompositeListIterator<String>(this.buildIterator1(), this.buildIterator2(), this.buildIterator3());
	}

	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator3() {
		return new CompositeListIterator<String>(new ListIterator[] { this.buildIterator1(), this.buildIterator2(), this.buildIterator3() });
	}

	@Override
	ListIterator<String> buildCompositeListIterator(String string, ListIterator<String> iterator) {
		return new CompositeListIterator<String>(string, iterator);
	}

}
