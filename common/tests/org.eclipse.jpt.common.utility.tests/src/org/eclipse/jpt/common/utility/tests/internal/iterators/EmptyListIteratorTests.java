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

import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;

@SuppressWarnings("nls")
public class EmptyListIteratorTests extends TestCase {

	public EmptyListIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (ListIterator<Object> stream = EmptyListIterator.instance(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(0, i);
	}

	public void testNext() {
		for (ListIterator<Object> stream = EmptyListIterator.instance(); stream.hasNext();) {
			fail("bogus element: " + stream.next());
		}
	}

	public void testNextIndex() {
		ListIterator<Object> stream = EmptyListIterator.instance();
		assertEquals(0, stream.nextIndex());
	}

	public void testHasPrevious() {
		ListIterator<Object> stream = EmptyListIterator.instance();
		int i = 0;
		while (stream.hasPrevious()) {
			stream.previous();
			i++;
		}
		assertEquals(0, i);

		while (stream.hasNext()) {
			stream.next();
		}
		i = 0;
		while (stream.hasPrevious()) {
			stream.previous();
			i++;
		}
		assertEquals(0, i);
	}

	public void testPrevious() {
		ListIterator<Object> stream = EmptyListIterator.instance();
		while (stream.hasPrevious()) {
			fail("bogus element: " + stream.previous());
		}
		while (stream.hasNext()) {
			stream.next();
		}
		while (stream.hasPrevious()) {
			fail("bogus element: " + stream.previous());
		}
	}

	public void testPreviousIndex() {
		ListIterator<Object> stream = EmptyListIterator.instance();
		assertEquals(-1, stream.previousIndex());
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		ListIterator<Object> stream = EmptyListIterator.instance();
		Object element = null;
		while (stream.hasNext()) {
			element = stream.next();
		}
		try {
			element = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown (next): " + element, exCaught);
		while (stream.hasPrevious()) {
			element = stream.previous();
		}
		try {
			element = stream.previous();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown (previous): " + element, exCaught);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		try {
			EmptyListIterator.instance().remove();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("UnsupportedOperationException not thrown (remove)", exCaught);
		try {
			EmptyListIterator.instance().set(new Object());
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("UnsupportedOperationException not thrown (set)", exCaught);
		try {
			EmptyListIterator.instance().add(new Object());
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("UnsupportedOperationException not thrown (add)", exCaught);
	}

}
