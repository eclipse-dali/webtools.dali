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
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

@SuppressWarnings("nls")
public class EmptyIteratorTests extends TestCase {

	public EmptyIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<Object> stream = EmptyIterator.instance(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(0, i);
	}

	public void testNext() {
		for (Iterator<String> stream = EmptyIterator.instance(); stream.hasNext();) {
			fail("bogus element: " + stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<Number> stream = EmptyIterator.instance();
		Object element = null;
		while (stream.hasNext()) {
			element = stream.next();
		}
		try {
			element = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		try {
			EmptyIterator.instance().remove();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

}
