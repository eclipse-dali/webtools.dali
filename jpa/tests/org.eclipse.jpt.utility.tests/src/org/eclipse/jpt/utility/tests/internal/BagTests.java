/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.HashBag;

@SuppressWarnings("nls")
public class BagTests extends TestCase {

	public BagTests(String name) {
		super(name);
	}

	public void testEmptyBag_iterator() throws Exception {
		assertFalse(Bag.Empty.instance().iterator().hasNext());
	}

	public void testEmptyBag_size() throws Exception {
		assertEquals(0, Bag.Empty.instance().size());
	}

	public void testEmptyBag_uniqueIterator() throws Exception {
		assertFalse(Bag.Empty.instance().uniqueIterator().hasNext());
	}

	public void testEmptyBag_uniqueCount() throws Exception {
		assertEquals(0, Bag.Empty.instance().uniqueCount());
	}

	public void testEmptyBag_count() throws Exception {
		assertEquals(0, Bag.Empty.instance().count("foo"));
	}

	public void testEmptyBag_entries() throws Exception {
		assertFalse(Bag.Empty.instance().entries().hasNext());
	}

	public void testEmptyBag_remove() throws Exception {
		assertFalse(Bag.Empty.instance().remove("foo", 3));
	}

	public void testEmptyBag_add() throws Exception {
		boolean exCaught = false;
		try {
			Bag.Empty.instance().add("foo", 3);
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyBag_equals() throws Exception {
		assertTrue(Bag.Empty.instance().equals(Bag.Empty.instance()));
		assertFalse(Bag.Empty.instance().equals("foo"));

		Bag<Object> bag = new HashBag<Object>();
		assertTrue(Bag.Empty.instance().equals(bag));
		bag.add("foo");
		assertFalse(Bag.Empty.instance().equals(bag));
	}

	public void testEmptyBag_hashCode() throws Exception {
		assertEquals(0, Bag.Empty.instance().hashCode());
	}

	public void testEmptyBag_serialization() throws Exception {
		Bag<?> xxx = TestTools.serialize(Bag.Empty.instance());
		assertSame(Bag.Empty.instance(), xxx);
	}

}
