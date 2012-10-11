/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;

@SuppressWarnings("nls")
public class SingleElementListIterableTests extends TestCase {

	public SingleElementListIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		for (String s : this.buildSingleElementListIterable()) {
			assertEquals(this.singleElement(), s);
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildSingleElementListIterable().iterator();
		String string = stream.next();
		try {
			string = stream.next();
			fail("bogus element: " + string);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildSingleElementListIterable().iterator(); stream.hasNext(); ) {
			if (stream.next().equals(this.singleElement())) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testToString() {
		assertNotNull(this.buildSingleElementListIterable().toString());
	}

	protected Iterable<String> buildSingleElementListIterable() {
		return new SingleElementListIterable<String>(this.singleElement());
	}

	protected String singleElement() {
		return "single element";
	}
}
