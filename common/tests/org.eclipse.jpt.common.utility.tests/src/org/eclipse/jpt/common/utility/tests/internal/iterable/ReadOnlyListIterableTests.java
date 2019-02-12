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
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.ListListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.ReadOnlyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

@SuppressWarnings("nls")
public class ReadOnlyListIterableTests extends TestCase {

	public ReadOnlyListIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		Iterator<String> nestedIterator = this.buildVector().iterator();
		for (String s : this.buildReadOnlyListIterable()) {
			assertEquals(nestedIterator.next(), s);
		}
	}

	public void testRemove() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildReadOnlyListIterable().iterator(); stream.hasNext();) {
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

	public void testToString() {
		assertNotNull(this.buildReadOnlyListIterable().toString());
	}

	private Iterable<String> buildReadOnlyListIterable() {
		return new ReadOnlyListIterable<String>(this.buildNestedListIterable());
	}

	private ListIterable<String> buildNestedListIterable() {
		return new ListListIterable<String>(this.buildVector());
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
