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
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.PeekableIterator;

@SuppressWarnings("nls")
public class PeekableIteratorTests extends TestCase {

	public PeekableIteratorTests(String name) {
		super(name);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildPeekableIterator(); stream.hasNext();) {
			String string = stream.next();
			if (string.equals("three")) {
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
		Iterator<String> stream = this.buildPeekableIterator();
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

	public void testHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildPeekableIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testHasNextUpcast() {
		int i = 0;
		for (Iterator<Object> stream = this.buildPeekableIteratorUpcast(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(6, i);
	}

	public void testNext() {
		Iterator<String> stream = this.buildPeekableIterator();
		assertEquals("zero", stream.next());
		assertEquals("one", stream.next());
		assertEquals("two", stream.next());
		assertEquals("three", stream.next());
		assertEquals("four", stream.next());
		assertEquals("five", stream.next());
	}

	public void testNextUpcast() {
		Iterator<Object> stream = this.buildPeekableIteratorUpcast();
		assertEquals("zero", stream.next());
		assertEquals("one", stream.next());
		assertEquals("two", stream.next());
		assertEquals("three", stream.next());
		assertEquals("four", stream.next());
		assertEquals("five", stream.next());
	}

	public void testPeek() {
		Object next = null;
		for (PeekableIterator<String> stream = this.buildPeekableIterator(); stream.hasNext();) {
			Object peek = stream.peek();
			assertTrue("peek and next are prematurely identical", peek != next);
			next = stream.next();
			assertTrue("peek and next are not identical", peek == next);
		}
	}

	public void testPeekUpcast() {
		Object next = null;
		for (PeekableIterator<Object> stream = this.buildPeekableIteratorUpcast(); stream.hasNext();) {
			Object peek = stream.peek();
			assertTrue("peek and next are prematurely identical", peek != next);
			next = stream.next();
			assertTrue("peek and next are not identical", peek == next);
		}
	}

	private PeekableIterator<String> buildPeekableIterator() {
		return this.buildPeekableIterator(this.buildNestedIterator());
	}

	private PeekableIterator<Object> buildPeekableIteratorUpcast() {
		return this.buildPeekableIteratorUpcast(this.buildNestedIterator());
	}

	private PeekableIterator<String> buildPeekableIterator(Iterator<String> nestedIterator) {
		return new PeekableIterator<String>(nestedIterator);
	}

	private PeekableIterator<Object> buildPeekableIteratorUpcast(Iterator<String> nestedIterator) {
		return new PeekableIterator<Object>(nestedIterator);
	}

	private Iterator<String> buildNestedIterator() {
		Collection<String> c = new ArrayList<String>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		c.add("four");
		c.add("five");
		return c.iterator();
	}

}
