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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.EnumerationIterator;

@SuppressWarnings("nls")
public class EnumerationIteratorTests extends TestCase {

	public EnumerationIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testHasNextUpcast() {
		int i = 0;
		for (Iterator<Object> stream = this.buildIteratorUpcast(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testNext() {
		Enumeration<String> enumeration = this.buildEnumeration();
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext();) {
			assertEquals("bogus element", enumeration.nextElement(), stream.next());
		}
	}

	public void testNextUpcast() {
		Enumeration<String> enumeration = this.buildEnumeration();
		for (Iterator<Object> stream = this.buildIteratorUpcast(); stream.hasNext();) {
			assertEquals("bogus element", enumeration.nextElement(), stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildIterator();
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
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext();) {
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

	private Iterator<String> buildIterator() {
		return this.buildIterator(this.buildEnumeration());
	}

	private Iterator<String> buildIterator(Enumeration<String> enumeration) {
		return new EnumerationIterator<String>(enumeration);
	}

	private Enumeration<String> buildEnumeration() {
		return this.buildVector().elements();
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

	private Iterator<Object> buildIteratorUpcast() {
		return this.buildIteratorUpcast(this.buildEnumeration());
	}

	private Iterator<Object> buildIteratorUpcast(Enumeration<String> enumeration) {
		return new EnumerationIterator<Object>(enumeration);
	}

}
