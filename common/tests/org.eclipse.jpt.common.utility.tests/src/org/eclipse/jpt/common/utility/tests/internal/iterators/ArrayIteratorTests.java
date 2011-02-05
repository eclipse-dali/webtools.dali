/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class ArrayIteratorTests extends TestCase {

	public ArrayIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext(); ) {
			stream.next();
			i++;
		}
		assertEquals(this.buildArray().length, i);
	}

	public void testNext() {
		int i = 1;
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext(); ) {
			assertEquals("bogus element", i++, Integer.parseInt(stream.next()));
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
		for (Iterator<String> stream = this.buildIterator(); stream.hasNext(); ) {
			if (stream.next().equals("3")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testIllegalArgumentException() {
		this.triggerIllegalArgumentException(-1, 1);
		this.triggerIllegalArgumentException(8, 1);
		this.triggerIllegalArgumentException(0, -1);
		this.triggerIllegalArgumentException(0, 9);
	}

	public void testGenerics() {
		Integer[] integers = new Integer[3];
		integers[0] = new Integer(0);
		integers[1] = new Integer(1);
		integers[2] = new Integer(2);
		int i = 0;
		for (Iterator<Number> stream = this.buildGenericIterator(integers); stream.hasNext();) {
			assertEquals(i++, stream.next().intValue());
		}
		assertEquals(integers.length, i);
	}

	Iterator<Number> buildGenericIterator(Integer[] integers) {
		return new ArrayIterator<Number>(integers);
	}

	public void testVarargs() {
		int i = 0;
		for (Iterator<Number> stream = this.buildVarArgIterator(); stream.hasNext();) {
			assertEquals(i++, stream.next().intValue());
		}
		assertEquals(3, i);
	}

	Iterator<Number> buildVarArgIterator() {
		return new ArrayIterator<Number>(new Integer(0), new Integer(1), new Integer(2));
	}

	void triggerIllegalArgumentException(int start, int length) {
		boolean exCaught = false;
		Iterator<String> stream = null;
		try {
			stream = this.buildIterator(start, length);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue("IllegalArgumentException not thrown: " + stream, exCaught);
	}

	Iterator<String> buildIterator() {
		return this.buildIterator(this.buildArray());
	}

	Iterator<String> buildIterator(String[] array) {
		return new ArrayIterator<String>(array);
	}

	Iterator<String> buildIterator(int start, int length) {
		return this.buildIterator(this.buildArray(), start, length);
	}

	Iterator<String> buildIterator(String[] array, int start, int length) {
		return new ArrayIterator<String>(array, start, length);
	}

	String[] buildArray() {
		return new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
	}

}
