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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;

@SuppressWarnings("nls")
public class CompositeIteratorTests extends TestCase {

	public CompositeIteratorTests(String name) {
		super(name);
	}

	public void testHasAnother() {
		this.verifyHasAnother(this.buildCompositeIterator());
	}

	public void testHasAnother2() {
		this.verifyHasAnother(this.buildCompositeIterator2());
	}

	public void testHasAnother3() {
		this.verifyHasAnother(this.buildCompositeIterator3());
	}

	void verifyHasAnother(Iterator<String> stream) {
		this.verifyHasAnother(8, stream);
	}

	void verifyHasAnother(int expected, Iterator<String> stream) {
		int i = 0;
		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(expected, i);
	}

	public void testAnother() {
		this.verifyAnother(this.buildCompositeIterator());
	}

	public void testAnother2() {
		this.verifyAnother(this.buildCompositeIterator2());
	}

	public void testAnother3() {
		this.verifyAnother(this.buildCompositeIterator3());
	}

	void verifyAnother(Iterator<String> stream) {
		this.verifyAnother(1, stream);
	}

	void verifyAnother(int start, Iterator<String> stream) {
		int index = start;
		while (stream.hasNext()) {
			assertEquals("bogus element", String.valueOf(index++), stream.next().substring(0, 1));
		}
	}

	public void testRemove() {
		this.verifyRemove();
	}

	protected void verifyRemove() {
		List<String> list1 = this.buildList1();
		Object lastElement1 = list1.get(list1.size() - 1);
		List<String> list2 = this.buildList2();
		List<String> list3 = this.buildList3();

		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(list1.listIterator());
		list.add(list2.listIterator());
		list.add(list3.listIterator());

		Iterator<String> stream = this.buildCompositeIterator(list.listIterator());
		while (stream.hasNext()) {
			Object next = stream.next();
			if (next.equals("333")) {
				stream.remove();
			}
			// test special case - where we are between iterators
			if (next.equals(lastElement1)) {
				// this will trigger the next iterator to be loaded
				stream.hasNext();
				// now try to remove from the previous iterator
				stream.remove();
			}
		}
		stream.remove();

		assertEquals("nothing removed from collection 1", this.buildList1().size() - 2, list1.size());
		assertFalse("element still in collection 1", list1.contains("333"));
		assertFalse("last element still in collection 1", list1.contains(lastElement1));
		assertTrue("wrong element removed from collection 1", list1.contains("22"));

		assertEquals("nothing removed from collection 3", this.buildList3().size() - 1, list3.size());
		assertFalse("element still in collection 3", list3.contains("88888888"));
		assertTrue("wrong element removed from collection 3", list3.contains("666666"));
	}

	public void testSingleElement() {
		String item = "0";
		this.verifyHasAnother(9, this.buildCompositeIterator(item, this.buildCompositeIterator()));
		this.verifyAnother(0, this.buildCompositeIterator(item, this.buildCompositeIterator()));
	}

	public void testNoSuchElementException() {
		this.verifyNoSuchElementException(this.buildCompositeIterator());
	}

	void verifyNoSuchElementException(Iterator<String> stream) {
		boolean exCaught = false;
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

	public void testUnsupportedOperationException() {
		this.verifyUnsupportedOperationException(this.buildUnmodifiableCompositeIterator());
	}

	void verifyUnsupportedOperationException(Iterator<String> stream) {
		boolean exCaught = false;
		while (stream.hasNext()) {
			Object string = stream.next();
			if (string.equals("333")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testIllegalStateException() {
		this.verifyIllegalStateException();
	}

	void verifyIllegalStateException() {
		this.verifyIllegalStateException(this.buildCompositeIterator());
	}

	void verifyIllegalStateException(Iterator<String> stream) {
		boolean exCaught = false;
		try {
			stream.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);
	}

	public void testEmptyHasAnother1() {
		this.verifyEmptyHasAnother(this.buildEmptyCompositeIterator1());
	}

	void verifyEmptyHasAnother(Iterator<String> stream) {
		int i = 0;
		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(0, i);
	}

	public void testEmptyNoSuchElementException1() {
		this.verifyNoSuchElementException(this.buildEmptyCompositeIterator1());
	}

	public void testEmptyIllegalStateException1() {
		this.verifyEmptyIllegalStateException1();
	}

	void verifyEmptyIllegalStateException1() {
		this.verifyIllegalStateException(this.buildEmptyCompositeIterator1());
	}

	public void testEmptyHasAnother2() {
		this.verifyEmptyHasAnother(this.buildEmptyCompositeIterator2());
	}

	public void testEmptyNoSuchElementException2() {
		this.verifyNoSuchElementException(this.buildEmptyCompositeIterator2());
	}

	public void testEmptyIllegalStateException2() {
		this.verifyEmptyIllegalStateException2();
	}

	void verifyEmptyIllegalStateException2() {
		this.verifyIllegalStateException(this.buildEmptyCompositeIterator2());
	}

	Iterator<String> buildCompositeIterator() {
		return this.buildCompositeIterator(this.buildIterators());
	}

	Iterator<String> buildEmptyCompositeIterator1() {
		return this.buildCompositeIterator(this.buildEmptyIterators1());
	}

	Iterator<String> buildEmptyCompositeIterator2() {
		return this.buildCompositeIterator(this.buildEmptyIterators2());
	}

	Iterator<String> buildUnmodifiableCompositeIterator() {
		return this.buildCompositeIterator(this.buildUnmodifiableIterators());
	}

	// leave unchecked so we can override in subclass
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator(Iterator iterators) {
		return new CompositeIterator<String>(iterators);
	}

	// use vararg constructor
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator2() {
		return new CompositeIterator<String>(this.buildIterator1(), this.buildIterator2(), this.buildIterator3());
	}

	// use vararg constructor
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator3() {
		return new CompositeIterator<String>(new Iterator[] { this.buildIterator1(), this.buildIterator2(), this.buildIterator3() });
	}

	Iterator<String> buildCompositeIterator(String string, Iterator<String> iterator) {
		return new CompositeIterator<String>(string, iterator);
	}

	ListIterator<Iterator<String>> buildIterators() {
		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(this.buildIterator1());
		list.add(this.buildIterator2());
		list.add(this.buildIterator3());
		return list.listIterator();
	}

	ListIterator<Iterator<String>> buildEmptyIterators1() {
		return this.buildEmptyIteratorIterator();
	}

	ListIterator<Iterator<String>> buildEmptyIterators2() {
		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(this.buildEmptyStringIterator());
		list.add(this.buildEmptyStringIterator());
		list.add(this.buildEmptyStringIterator());
		return list.listIterator();
	}

	ListIterator<Iterator<String>> buildUnmodifiableIterators() {
		List<Iterator<String>> list = new ArrayList<Iterator<String>>();
		list.add(this.buildUnmodifiableIterator1());
		list.add(this.buildUnmodifiableIterator2());
		list.add(this.buildUnmodifiableIterator3());
		return list.listIterator();
	}

	ListIterator<String> buildIterator1() {
		return this.buildList1().listIterator();
	}

	ListIterator<String> buildIterator2() {
		return this.buildList2().listIterator();
	}

	ListIterator<String> buildIterator3() {
		return this.buildList3().listIterator();
	}

	ListIterator<String> buildUnmodifiableIterator1() {
		return this.buildUnmodifiableList1().listIterator();
	}

	ListIterator<String> buildUnmodifiableIterator2() {
		return this.buildUnmodifiableList2().listIterator();
	}

	ListIterator<String> buildUnmodifiableIterator3() {
		return this.buildUnmodifiableList3().listIterator();
	}

	ListIterator<Iterator<String>> buildEmptyIteratorIterator() {
		return (new ArrayList<Iterator<String>>()).listIterator();
	}

	ListIterator<String> buildEmptyStringIterator() {
		return (new ArrayList<String>()).listIterator();
	}

	List<String> buildList1() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("22");
		list.add("333");
		list.add("4444");
		return list;
	}

	List<String> buildList2() {
		return new ArrayList<String>();
	}

	List<String> buildList3() {
		List<String> list = new ArrayList<String>();
		list.add("55555");
		list.add("666666");
		list.add("7777777");
		list.add("88888888");
		return list;
	}

	List<String> buildUnmodifiableList1() {
		return Collections.unmodifiableList(this.buildList1());
	}

	List<String> buildUnmodifiableList2() {
		return Collections.unmodifiableList(this.buildList2());
	}

	List<String> buildUnmodifiableList3() {
		return Collections.unmodifiableList(this.buildList3());
	}

}
