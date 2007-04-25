/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;

public class ReadOnlyIteratorTests extends TestCase {

	public ReadOnlyIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildReadOnlyIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testHasNextUpcast() {
		int i = 0;
		for (Iterator<Object> stream = this.buildReadOnlyIteratorUpcast(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testNext() {
		Iterator<String> nestedIterator = this.buildNestedIterator();
		for (Iterator<String> stream = this.buildReadOnlyIterator(); stream.hasNext();) {
			assertEquals("bogus element", nestedIterator.next(), stream.next());
		}
	}

	public void testNextUpcast() {
		Iterator<String> nestedIterator = this.buildNestedIterator();
		for (Iterator<Object> stream = this.buildReadOnlyIteratorUpcast(); stream.hasNext();) {
			assertEquals("bogus element", nestedIterator.next(), stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildReadOnlyIterator();
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
		for (Iterator<String> stream = this.buildReadOnlyIterator(); stream.hasNext();) {
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

	private Iterator<String> buildReadOnlyIterator() {
		return this.buildReadOnlyIterator(this.buildNestedIterator());
	}

	private Iterator<Object> buildReadOnlyIteratorUpcast() {
		return this.buildReadOnlyIteratorUpcast(this.buildNestedIterator());
	}

	private Iterator<String> buildReadOnlyIterator(Iterator<String> nestedIterator) {
		return new ReadOnlyIterator<String>(nestedIterator);
	}

	private Iterator<Object> buildReadOnlyIteratorUpcast(Iterator<String> nestedIterator) {
		return new ReadOnlyIterator<Object>(nestedIterator);
	}

	private Iterator<String> buildNestedIterator() {
		return this.buildVector().iterator();
	}

	private Vector<String> buildVector() {
		Vector<String> v = new Vector<String>();
		v.addElement("one");
		v.addElement("two");
		v.addElement("three");
		v.addElement("four");
		v.addElement("five");
		v.addElement("six");
		v.addElement("seven");
		v.addElement("eight");
		return v;
	}

}
