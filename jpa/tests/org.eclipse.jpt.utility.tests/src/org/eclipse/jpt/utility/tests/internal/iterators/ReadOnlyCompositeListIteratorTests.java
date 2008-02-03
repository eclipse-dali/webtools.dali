/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;

public class ReadOnlyCompositeListIteratorTests extends CompositeIteratorTests {

	public ReadOnlyCompositeListIteratorTests(String name) {
		super(name);
	}

	@Override
	void verifyHasAnother(Iterator<String> stream) {
		super.verifyHasAnother(stream);
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		int i = 0;
		while (stream2.hasPrevious()) {
			stream2.previous();
			i++;
		}
		assertEquals(8, i);
	}

	@Override
	void verifyAnother(Iterator<String> stream) {
		super.verifyAnother(stream);
		int i = 8;
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		while (stream2.hasPrevious()) {
			assertEquals("bogus element", String.valueOf(i--), stream2.previous().substring(0, 1));
		}
	}

	public void testNextIndexPreviousIndex() {
		int i = 0;
		ListIterator<String> stream = (ListIterator<String>) this.buildCompositeIterator();
		assertEquals(i, stream.nextIndex());
		assertEquals(i - 1, stream.previousIndex());
		while (stream.hasNext()) {
			stream.next();
			i++;
			assertEquals(i, stream.nextIndex());
			assertEquals(i - 1, stream.previousIndex());
		}
		assertEquals("index is corrupt", 8, i);

		assertEquals(i, stream.nextIndex());
		assertEquals(i - 1, stream.previousIndex());
		while (stream.hasPrevious()) {
			stream.previous();
			i--;
			assertEquals(i, stream.nextIndex());
			assertEquals(i - 1, stream.previousIndex());
		}
		assertEquals("index is corrupt", 0, i);
	}

	public void testPreviousIndex() {
		// TODO
	}

	@Override
	public void testRemove() {
		// #remove() is not supported
	}

	@Override
	public void testIllegalStateException() {
		// #remove() is not supported
	}

	@Override
	public void testEmptyIllegalStateException1() {
		// #remove() is not supported
	}

	@Override
	public void testEmptyIllegalStateException2() {
		// #remove() is not supported
	}

	@Override
	void verifyNoSuchElementException(Iterator<String> stream) {
		super.verifyNoSuchElementException(stream);
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		boolean exCaught = false;
		String string = null;
		while (stream2.hasPrevious()) {
			string = stream2.previous();
		}
		try {
			string = stream2.previous();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	@Override
	void verifyUnsupportedOperationException(Iterator<String> stream) {
		super.verifyUnsupportedOperationException(stream);
		boolean exCaught = false;
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		while (stream2.hasPrevious()) {
			Object string = stream2.previous();
			if (string.equals("333")) {
				try {
					stream2.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	@Override
	void verifyIllegalStateException(Iterator<String> stream) {
		super.verifyIllegalStateException(stream);
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		boolean exCaught = false;
		try {
			stream2.set("junk");
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);
	}

	@Override
	void verifyEmptyHasAnother(Iterator<String> stream) {
		super.verifyEmptyHasAnother(stream);
		ListIterator<String> stream2 = (ListIterator<String>) stream;
		int i = 0;
		while (stream2.hasPrevious()) {
			stream2.previous();
			i++;
		}
		assertEquals(0, i);
	}

	// unchecked so we can override the unchecked method in superclass
	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator(Iterator iterators) {
		return new ReadOnlyCompositeListIterator<String>((ListIterator<ListIterator<String>>) iterators);
	}

	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator2() {
		return new ReadOnlyCompositeListIterator<String>(this.buildIterator1(), this.buildIterator2(), this.buildIterator3());
	}

	@Override
	@SuppressWarnings("unchecked")
	Iterator<String> buildCompositeIterator3() {
		return new ReadOnlyCompositeListIterator<String>(new ListIterator[] { this.buildIterator1(), this.buildIterator2(), this.buildIterator3() });
	}

	Iterator<String> buildCompositeIterator(String string, ListIterator<String> iterator) {
		return this.buildCompositeListIterator(string, iterator);
	}

	ListIterator<String> buildCompositeListIterator(String string, ListIterator<String> iterator) {
		return new ReadOnlyCompositeListIterator<String>(string, iterator);
	}

	public void testVariedNestedIterators() {
		List<Integer> integerList = new ArrayList<Integer>();
		integerList.add(42);
		integerList.add(22);
		integerList.add(111);
		integerList.add(77);

		List<Float> floatList = new ArrayList<Float>();
		floatList.add(42.42f);
		floatList.add(22.22f);
		floatList.add(111.111f);
		floatList.add(77.77f);

		List<ListIterator<? extends Number>> list = new ArrayList<ListIterator<? extends Number>>();
		list.add(integerList.listIterator());
		list.add(floatList.listIterator());
		ListIterator<Number> li = new ReadOnlyCompositeListIterator<Number>(list);
		while (li.hasNext()) {
			assertTrue(li.next().intValue() > 0);
		}
	}

}
