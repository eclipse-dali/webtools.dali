/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.collection.EmptyBag;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class BagTests extends TestCase {

	public BagTests(String name) {
		super(name);
	}

	public void testEmptyBag_iterator() throws Exception {
		assertFalse(EmptyBag.instance().iterator().hasNext());
	}

	public void testEmptyBag_size() throws Exception {
		assertEquals(0, EmptyBag.instance().size());
	}

	public void testEmptyBag_uniqueIterator() throws Exception {
		assertFalse(EmptyBag.instance().uniqueIterator().hasNext());
	}

	public void testEmptyBag_uniqueCount() throws Exception {
		assertEquals(0, EmptyBag.instance().uniqueCount());
	}

	public void testEmptyBag_count() throws Exception {
		assertEquals(0, EmptyBag.instance().count("foo"));
	}

	public void testEmptyBag_entries() throws Exception {
		assertFalse(EmptyBag.instance().entries().hasNext());
	}

	public void testEmptyBag_remove() throws Exception {
		assertFalse(EmptyBag.instance().remove("foo", 3));
	}

	public void testEmptyBag_add() throws Exception {
		boolean exCaught = false;
		try {
			EmptyBag.instance().add("foo", 3);
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyBag_equals() throws Exception {
		assertTrue(EmptyBag.instance().equals(EmptyBag.instance()));
		assertFalse(EmptyBag.instance().equals("foo"));

		Bag<Object> bag = new HashBag<Object>();
		assertTrue(EmptyBag.instance().equals(bag));
		bag.add("foo");
		assertFalse(EmptyBag.instance().equals(bag));
	}

	public void testEmptyBag_hashCode() throws Exception {
		assertEquals(0, EmptyBag.instance().hashCode());
	}

	public void testEmptyBag_serialization() throws Exception {
		Bag<?> xxx = TestTools.serialize(EmptyBag.instance());
		assertSame(EmptyBag.instance(), xxx);
	}

}
