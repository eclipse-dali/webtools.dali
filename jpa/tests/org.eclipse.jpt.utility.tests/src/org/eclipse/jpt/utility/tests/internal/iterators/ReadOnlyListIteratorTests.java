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
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;

@SuppressWarnings("nls")
public class ReadOnlyListIteratorTests extends TestCase {

	public ReadOnlyListIteratorTests(String name) {
		super(name);
	}

	public void testHasNextAndHasPrevious() {
		int i = 0;
		ListIterator<String> stream = this.buildReadOnlyListIterator();
		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(this.buildList().size(), i);

		while (stream.hasPrevious()) {
			stream.previous();
			i--;
		}
		assertEquals(0, i);
	}

	public void testHasNextAndHasPreviousUpcast() {
		int i = 0;
		ListIterator<Object> stream = this.buildReadOnlyListIteratorUpcast();
		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(this.buildList().size(), i);

		while (stream.hasPrevious()) {
			stream.previous();
			i--;
		}
		assertEquals(0, i);
	}

	public void testNextAndPrevious() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		ListIterator<String> stream = this.buildReadOnlyListIterator();
		while (stream.hasNext()) {
			assertEquals("bogus element", nestedListIterator.next(), stream.next());
		}
		while (stream.hasPrevious()) {
			assertEquals("bogus element", nestedListIterator.previous(), stream.previous());
		}
	}

	public void testNextAndPreviousUpcast() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		ListIterator<Object> stream = this.buildReadOnlyListIteratorUpcast();
		while (stream.hasNext()) {
			assertEquals("bogus element", nestedListIterator.next(), stream.next());
		}
		while (stream.hasPrevious()) {
			assertEquals("bogus element", nestedListIterator.previous(), stream.previous());
		}
	}

	public void testNextIndexAndPreviousIndex() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		ListIterator<String> stream = this.buildReadOnlyListIterator();
		while (stream.hasNext()) {
			assertEquals("bogus index", nestedListIterator.nextIndex(), stream.nextIndex());
			nestedListIterator.next();
			stream.next();
		}
		assertEquals("bogus index", this.buildList().size(), stream.nextIndex());
		while (stream.hasPrevious()) {
			assertEquals("bogus element", nestedListIterator.previousIndex(), stream.previousIndex());
			nestedListIterator.previous();
			stream.previous();
		}
		assertEquals("bogus index", -1, stream.previousIndex());
	}

	public void testNextIndexAndPreviousIndexUpcast() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		ListIterator<Object> stream = this.buildReadOnlyListIteratorUpcast();
		while (stream.hasNext()) {
			assertEquals("bogus index", nestedListIterator.nextIndex(), stream.nextIndex());
			nestedListIterator.next();
			stream.next();
		}
		assertEquals("bogus index", this.buildList().size(), stream.nextIndex());
		while (stream.hasPrevious()) {
			assertEquals("bogus element", nestedListIterator.previousIndex(), stream.previousIndex());
			nestedListIterator.previous();
			stream.previous();
		}
		assertEquals("bogus index", -1, stream.previousIndex());
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		ListIterator<String> stream = this.buildReadOnlyListIterator();
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

	public void testRemove() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildReadOnlyListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testSet() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildReadOnlyListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.set("bogus");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testAdd() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildReadOnlyListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.add("bogus");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	private ListIterator<String> buildReadOnlyListIterator() {
		return this.buildReadOnlyListIterator(this.buildNestedListIterator());
	}

	private ListIterator<Object> buildReadOnlyListIteratorUpcast() {
		return this.buildReadOnlyListIteratorUpcast(this.buildNestedListIterator());
	}

	private ListIterator<String> buildReadOnlyListIterator(ListIterator<String> nestedListIterator) {
		return new ReadOnlyListIterator<String>(nestedListIterator);
	}

	private ListIterator<Object> buildReadOnlyListIteratorUpcast(ListIterator<String> nestedListIterator) {
		return new ReadOnlyListIterator<Object>(nestedListIterator);
	}

	private ListIterator<String> buildNestedListIterator() {
		return this.buildList().listIterator();
	}

	private List<String> buildList() {
		List<String> l = new ArrayList<String>();
		l.add("one");
		l.add("two");
		l.add("three");
		l.add("four");
		l.add("five");
		l.add("six");
		l.add("seven");
		l.add("eight");
		return l;
	}

}
