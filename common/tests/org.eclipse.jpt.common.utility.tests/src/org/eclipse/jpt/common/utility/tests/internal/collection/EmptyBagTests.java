/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class EmptyBagTests
	extends TestCase
{
	public EmptyBagTests(String name) {
		super(name);
	}

	public void testEmptyBag_iterator() throws Exception {
		assertFalse(CollectionTools.emptyBag().iterator().hasNext());
	}

	public void testEmptyBag_size() throws Exception {
		assertEquals(0, CollectionTools.emptyBag().size());
	}

	public void testEmptyBag_uniqueIterator() throws Exception {
		assertFalse(CollectionTools.emptyBag().uniqueIterator().hasNext());
	}

	public void testEmptyBag_uniqueCount() throws Exception {
		assertEquals(0, CollectionTools.emptyBag().uniqueCount());
	}

	public void testEmptyBag_count() throws Exception {
		assertEquals(0, CollectionTools.emptyBag().count("foo"));
	}

	public void testEmptyBag_entries() throws Exception {
		assertFalse(CollectionTools.emptyBag().entries().hasNext());
	}

	public void testEmptyBag_remove() throws Exception {
		assertFalse(CollectionTools.emptyBag().remove("foo", 3));
	}

	public void testEmptyBag_add() throws Exception {
		boolean exCaught = false;
		try {
			CollectionTools.emptyBag().add("foo", 3);
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyBag_equals() throws Exception {
		assertTrue(CollectionTools.emptyBag().equals(CollectionTools.emptyBag()));
		assertFalse(CollectionTools.emptyBag().equals("foo"));

		Bag<Object> bag = new HashBag<Object>();
		assertTrue(CollectionTools.emptyBag().equals(bag));
		bag.add("foo");
		assertFalse(CollectionTools.emptyBag().equals(bag));
	}

	public void testEmptyBag_hashCode() throws Exception {
		assertEquals(0, CollectionTools.emptyBag().hashCode());
	}

	public void testEmptyBag_serialization() throws Exception {
		Bag<?> xxx = TestTools.serialize(CollectionTools.emptyBag());
		assertSame(CollectionTools.emptyBag(), xxx);
	}

}
