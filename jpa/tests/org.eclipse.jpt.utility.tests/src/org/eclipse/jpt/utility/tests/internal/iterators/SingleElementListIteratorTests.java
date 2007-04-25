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
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class SingleElementListIteratorTests extends SingleElementIteratorTests {

	public SingleElementListIteratorTests(String name) {
		super(name);
	}

	public void testNextIndex() {
		ListIterator<String> stream = this.buildSingleElementListIterator();
		while (stream.hasNext()) {
			assertEquals("bogus index", 0, stream.nextIndex());
			stream.next();
		}
		assertEquals("bogus index", 1, stream.nextIndex());
	}

	public void testHasPrevious() {
		int i = 0;
		ListIterator<String> stream = this.buildSingleElementListIterator();
		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(1, i);

		while (stream.hasPrevious()) {
			stream.previous();
			i++;
		}
		assertEquals(2, i);
	}

	public void testPrevious() {
		ListIterator<String> stream = this.buildSingleElementListIterator();

		while (stream.hasNext()) {
			assertEquals("bogus element", this.singleElement(), stream.next());
		}

		while (stream.hasPrevious()) {
			assertEquals("bogus element", this.singleElement(), stream.previous());
		}
	}

	public void testPreviousIndex() {
		ListIterator<String> stream = this.buildSingleElementListIterator();

		while (stream.hasNext()) {
			assertEquals("bogus index", 0, stream.nextIndex());
			stream.next();
		}

		while (stream.hasPrevious()) {
			assertEquals("bogus index", 0, stream.previousIndex());
			stream.previous();
		}

		assertEquals("bogus index", -1, stream.previousIndex());
	}

	public void testAdd() {
		boolean exCaught = false;
		ListIterator<String> stream = this.buildSingleElementListIterator();

		try {
			stream.add("foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}

		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testSet() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildSingleElementListIterator(); stream.hasNext();) {
			if (stream.next().equals(this.singleElement())) {
				try {
					stream.set("foo");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	@Override
	protected Iterator<String> buildSingleElementIterator() {
		return new SingleElementListIterator<String>(this.singleElement());
	}

	protected ListIterator<String> buildSingleElementListIterator() {
		return (ListIterator<String>) this.buildSingleElementIterator();
	}

}
