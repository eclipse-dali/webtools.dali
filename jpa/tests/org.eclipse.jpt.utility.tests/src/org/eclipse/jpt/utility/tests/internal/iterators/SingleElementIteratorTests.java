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
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

public class SingleElementIteratorTests extends TestCase {

	public SingleElementIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<String> stream = this.buildSingleElementIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(1, i);
	}

	public void testNext() {
		for (Iterator<String> stream = this.buildSingleElementIterator(); stream.hasNext();) {
			assertEquals("bogus element", this.singleElement(), stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildSingleElementIterator();
		String string = stream.next();
		try {
			string = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	public void testRemove() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildSingleElementIterator(); stream.hasNext();) {
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

	protected Iterator<String> buildSingleElementIterator() {
		return new SingleElementIterator<String>(this.singleElement());
	}

	protected String singleElement() {
		return "single element";
	}
}
