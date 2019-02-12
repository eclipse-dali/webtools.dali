/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.NullList;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class NullListTests
	extends TestCase
{
	public NullListTests(String name) {
		super(name);
	}

	public void testAddObject() {
		List<String> list = NullList.<String>instance();
		assertFalse(list.add("foo"));
		assertTrue(list.isEmpty());
	}

	public void testAddIntObject() {
		List<String> list = NullList.<String>instance();
		list.add(0, "foo");
		assertTrue(list.isEmpty());
	}

	public void testAddAllCollection() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.addAll(collection));
		assertTrue(list.isEmpty());
	}

	public void testAddAllIntCollection() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.addAll(0, collection));
		assertTrue(list.isEmpty());
	}

	public void testClear() {
		List<String> list = NullList.<String>instance();
		list.clear();
		assertTrue(list.isEmpty());
	}

	public void testContainsObject() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.addAll(collection));
		assertFalse(list.contains("foo"));
		assertFalse(list.contains("bar"));
		assertFalse(list.contains("XXX"));
	}

	public void testContainsAllCollection() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.addAll(collection));
		assertFalse(list.containsAll(collection));
		collection.clear();
		assertTrue(list.containsAll(collection));
	}

	public void testGetInt() {
		List<String> list = NullList.<String>instance();
		boolean exCaught = false;
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIndexOfObject() {
		List<String> list = NullList.<String>instance();
		assertEquals(-1, list.indexOf("foo"));
	}

	public void testIsEmpty() {
		List<String> list = NullList.<String>instance();
		assertTrue(list.isEmpty());
	}

	public void testIterator() {
		List<String> list = NullList.<String>instance();
		assertTrue(IteratorTools.isEmpty(list.iterator()));
	}

	public void testLastIndexOfObject() {
		List<String> list = NullList.<String>instance();
		assertEquals(-1, list.lastIndexOf("foo"));
	}

	public void testListIterator() {
		List<String> list = NullList.<String>instance();
		assertTrue(IteratorTools.isEmpty(list.listIterator()));
	}

	public void testListIteratorInt() {
		List<String> list = NullList.<String>instance();
		ListIterator<String> iterator = list.listIterator(0);
		assertTrue(IteratorTools.isEmpty(iterator));
		boolean exCaught = false;
		try {
			iterator = list.listIterator(1);
			fail("bogus list iterator: " + iterator);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveObject() {
		List<String> list = NullList.<String>instance();
		assertFalse(list.remove("foo"));
		assertTrue(list.isEmpty());
	}

	public void testRemoveInt() {
		List<String> list = NullList.<String>instance();
		boolean exCaught = false;
		try {
			String object = list.remove(0);
			fail("bogus element: " + object);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertTrue(list.isEmpty());
	}

	public void testRemoveAllCollection() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.removeAll(collection));
		assertTrue(list.isEmpty());
	}

	public void testRetainAllCollection() {
		List<String> list = NullList.<String>instance();
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("bar");
		assertFalse(list.retainAll(collection));
		assertTrue(list.isEmpty());
	}

	public void testSetIntObject() {
		List<String> list = NullList.<String>instance();
		boolean exCaught = false;
		try {
			String element = list.set(0, "foo");
			fail("bogus set: " + element);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		List<String> list = NullList.<String>instance();
		assertEquals(0, list.size());
	}

	public void testSubList() {
		List<String> list = NullList.<String>instance();
		List<String> subList = list.subList(0, 0);
		assertTrue(subList.isEmpty());
		boolean exCaught = false;
		try {
			subList = list.subList(0, 3);
			fail("bogus sub list: " + subList);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			subList = list.subList(3, 0);
			fail("bogus sub list: " + subList);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			subList = list.subList(3, 3);
			fail("bogus sub list: " + subList);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		subList = list.subList(0, 0);
		assertTrue(subList.isEmpty());
	}

	public void testToArray() {
		List<String> list = NullList.<String>instance();
		assertEquals(0, list.toArray().length);
	}

	public void testToArrayObjectArray() {
		List<String> list = NullList.<String>instance();
		assertEquals(0, list.toArray(ObjectTools.EMPTY_OBJECT_ARRAY).length);
	}

	public void testSerialization() throws Exception {
		List<String> list = NullList.<String>instance();
		assertSame(list, TestTools.serialize(list));
	}

	public void testToString() {
		List<String> list = NullList.<String>instance();
		assertEquals("[]", list.toString());
	}
}
