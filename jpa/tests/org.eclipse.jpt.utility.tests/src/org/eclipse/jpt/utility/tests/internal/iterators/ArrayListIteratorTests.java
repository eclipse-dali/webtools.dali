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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

@SuppressWarnings("nls")
public class ArrayListIteratorTests extends ArrayIteratorTests {

	public ArrayListIteratorTests(String name) {
		super(name);
	}

	public void testHasPrevious() {
		ListIterator<String> stream = this.buildListIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		int i = 0;
		while (stream.hasPrevious()) {
			stream.previous();
			i++;
		}
		assertEquals(this.buildArray().length, i);
	}

	public void testPrevious() {
		ListIterator<String> stream = this.buildListIterator();
		while (stream.hasNext()) {
			stream.next();
		}
		int i = this.buildArray().length;
		while (stream.hasPrevious()) {
			assertEquals("bogus element", i--, Integer.parseInt(stream.previous()));
		}
	}

	public void testNextIndex() {
		int i = 0;
		ListIterator<String> stream = this.buildListIterator();
		while (stream.hasNext()) {
			assertEquals(i, stream.nextIndex());
			stream.next();
			i++;
		}
		assertEquals(i, stream.nextIndex());
	}

	public void testPreviousIndex() {
		int i = 0;
		ListIterator<String> stream = this.buildListIterator();
		while (stream.hasNext()) {
			assertEquals(i - 1, stream.previousIndex());
			stream.next();
			i++;
		}
		assertEquals(i - 1, stream.previousIndex());
	}

	@Override
	public void testNoSuchElementException() {
		boolean exCaught = false;
		ListIterator<String> stream = this.buildListIterator();
		String string = null;
		try {
			string = stream.previous();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	public void testUnsupportedOperationExceptionAdd() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildListIterator(); stream.hasNext();) {
			if (stream.next().equals("3")) {
				try {
					stream.add("3.5");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testUnsupportedOperationExceptionSet() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildListIterator(); stream.hasNext();) {
			if (stream.next().equals("3")) {
				try {
					stream.set("three");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	@Override
	Iterator<Number> buildGenericIterator(Integer[] integers) {
		return new ArrayListIterator<Number>(integers);
	}

	@Override
	Iterator<Number> buildVarArgIterator() {
		return new ArrayListIterator<Number>(new Integer(0), new Integer(1), new Integer(2));
	}

	private ListIterator<String> buildListIterator() {
		return this.buildListIterator(this.buildArray());
	}

	private ListIterator<String> buildListIterator(String[] array) {
		return new ArrayListIterator<String>(array);
	}

	@Override
	Iterator<String> buildIterator(String[] array) {
		return new ArrayListIterator<String>(array);
	}

	@Override
	Iterator<String> buildIterator(String[] array, int start, int length) {
		return new ArrayListIterator<String>(array, start, length);
	}

}
