/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.Iterator;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.ReadOnlyIterable;

@SuppressWarnings("nls")
public class ReadOnlyIterableTests extends TestCase {

	public ReadOnlyIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		Iterator<String> nestedIterator = this.buildVector().iterator();
		for (String s : this.buildReadOnlyIterable()) {
			assertEquals(nestedIterator.next(), s);
		}
	}

	public void testRemove() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildReadOnlyIterable().iterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	private Iterable<String> buildReadOnlyIterable() {
		return new ReadOnlyIterable<String>(this.buildVector());
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
