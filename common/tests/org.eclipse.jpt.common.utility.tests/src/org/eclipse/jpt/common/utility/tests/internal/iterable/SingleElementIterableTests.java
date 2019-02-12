/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;

@SuppressWarnings("nls")
public class SingleElementIterableTests extends TestCase {

	public SingleElementIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		for (String s : this.buildSingleElementIterable()) {
			assertEquals(this.singleElement(), s);
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildSingleElementIterable().iterator();
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
		for (Iterator<String> stream = this.buildSingleElementIterable().iterator(); stream.hasNext(); ) {
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

	protected Iterable<String> buildSingleElementIterable() {
		return new SingleElementIterable<String>(this.singleElement());
	}

	protected String singleElement() {
		return "single element";
	}
}
