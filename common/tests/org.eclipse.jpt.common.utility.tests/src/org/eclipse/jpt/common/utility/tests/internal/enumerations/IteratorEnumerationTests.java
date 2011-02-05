/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.enumerations;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.enumerations.IteratorEnumeration;

@SuppressWarnings("nls")
public class IteratorEnumerationTests extends TestCase {

	public IteratorEnumerationTests(String name) {
		super(name);
	}

	public void testHasMoreElements() {
		int i = 0;
		for (Enumeration<String> stream = this.buildEnumeration(); stream.hasMoreElements();) {
			stream.nextElement();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testHasMoreElementsUpcast() {
		int i = 0;
		for (Enumeration<Object> stream = this.buildEnumerationUpcast(); stream.hasMoreElements();) {
			stream.nextElement();
			i++;
		}
		assertEquals(this.buildVector().size(), i);
	}

	public void testNextElement() {
		Iterator<String> iterator = this.buildIterator();
		for (Enumeration<String> stream = this.buildEnumeration(); stream.hasMoreElements();) {
			assertEquals("bogus element", iterator.next(), stream.nextElement());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Enumeration<String> stream = this.buildEnumeration();
		String string = null;
		while (stream.hasMoreElements()) {
			string = stream.nextElement();
		}
		try {
			string = stream.nextElement();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	private Enumeration<String> buildEnumeration() {
		return this.buildEnumeration(this.buildIterator());
	}

	private Enumeration<Object> buildEnumerationUpcast() {
		return this.buildEnumerationUpcast(this.buildIterator());
	}

	private Enumeration<String> buildEnumeration(Iterator<String> iterator) {
		return new IteratorEnumeration<String>(iterator);
	}

	private Enumeration<Object> buildEnumerationUpcast(Iterator<String> iterator) {
		return new IteratorEnumeration<Object>(iterator);
	}

	private Iterator<String> buildIterator() {
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
