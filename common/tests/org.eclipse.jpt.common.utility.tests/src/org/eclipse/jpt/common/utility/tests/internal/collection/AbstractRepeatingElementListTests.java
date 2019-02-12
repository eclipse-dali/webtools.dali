/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;

@SuppressWarnings("nls")
public abstract class AbstractRepeatingElementListTests
	extends TestCase
{
	public AbstractRepeatingElementListTests(String name) {
		super(name);
	}

	public void testBogusSize() {
		boolean exCaught = false;
		try {
			List<String> list = this.buildList(-3);
			fail("bogus list: " + list);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddObject() {
		List<String> list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.add("foo");
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddIntObject() {
		List<String> list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.add(2, "foo");
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddAllCollection() {
		List<String> list = this.buildList(3);
		list.addAll(Collections.<String>emptySet());

		boolean exCaught = false;
		try {
			list.addAll(CollectionTools.hashBag("foo", "bar"));
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddAllIntCollection() {
		List<String> list = this.buildList(3);
		list.addAll(1, Collections.<String>emptySet());

		boolean exCaught = false;
		try {
			list.addAll(1, CollectionTools.hashBag("foo", "bar"));
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClear() {
		List<String> list = this.buildList(0);
		list.clear();

		list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.clear();
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testContainsObject() {
		List<String> list = this.buildList(0);
		assertFalse(list.contains(this.getElement()));
		assertFalse(list.contains(new Object()));

		list = this.buildList(3);
		assertTrue(list.contains(this.getElement()));
		assertFalse(list.contains(new Object()));
	}

	public void testContainsAllCollection() {
		Collection<String> emptyCollection = Collections.emptySet();

		Collection<String> goodCollection = new ArrayList<String>();
		goodCollection.add(this.getElement());
		goodCollection.add(this.getElement());
		goodCollection.add(this.getElement());

		Collection<String> badCollection = new ArrayList<String>();
		badCollection.add(this.getElement());
		badCollection.add("bad");
		badCollection.add(this.getElement());

		List<String> list = this.buildList(0);
		assertTrue(list.containsAll(emptyCollection));
		assertFalse(list.containsAll(goodCollection));
		assertFalse(list.containsAll(badCollection));

		list = this.buildList(3);
		assertTrue(list.containsAll(emptyCollection));
		assertTrue(list.containsAll(goodCollection));
		assertFalse(list.containsAll(badCollection));
	}

	public void testGetInt() {
		List<String> list = this.buildList(3);
		assertEquals(this.getElement(), list.get(0));
		assertEquals(this.getElement(), list.get(1));
		assertEquals(this.getElement(), list.get(2));

		boolean exCaught = false;
		try {
			fail("bogus element: " + list.get(-1));
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			fail("bogus element: " + list.get(3));
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIndexOfObject() {
		List<String> list = this.buildList(3);
		assertEquals(0, list.indexOf(this.getElement()));
		assertEquals(-1, list.indexOf(new Object()));

		list = this.buildList(0);
		assertEquals(-1, list.indexOf(this.getElement()));
	}

	public void testIsEmpty() {
		List<String> list = this.buildList(3);
		assertFalse(list.isEmpty());

		list = this.buildList(0);
		assertTrue(list.isEmpty());
	}

	public void testIterator() {
		List<String> list = this.buildList(3);
		Iterator<String> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(this.getElement(), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(this.getElement(), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(this.getElement(), iterator.next());
		assertFalse(iterator.hasNext());

		boolean exCaught = false;
		try {
			iterator.remove();
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		list = this.buildList(0);
		iterator = list.iterator();
		assertFalse(iterator.hasNext());
	}

	public void testLastIndexOfObject() {
		List<String> list = this.buildList(3);
		assertEquals(2, list.lastIndexOf(this.getElement()));
		assertEquals(-1, list.lastIndexOf(new Object()));

		list = this.buildList(0);
		assertEquals(-1, list.indexOf(this.getElement()));
		assertEquals(-1, list.lastIndexOf(new Object()));
	}

	public void testListIterator() {
		List<String> list = this.buildList(3);
		ListIterator<String> iterator = list.listIterator();
		this.verifyListIterator(list, iterator);

		list = this.buildList(0);
		iterator = list.listIterator();
		assertFalse(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
	}

	public void testListIteratorInt() {
		List<String> list = this.buildList(7);
		ListIterator<String> iterator = list.listIterator(4);
		this.verifyListIterator(list, iterator);

		list = this.buildList(0);
		iterator = list.listIterator();
		assertFalse(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
	}

	public void verifyListIterator(List<String> list, ListIterator<String> iterator) {
		assertTrue(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
		assertEquals(0, iterator.nextIndex());
		assertEquals(-1, iterator.previousIndex());
		assertEquals(this.getElement(), iterator.next());

		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals(1, iterator.nextIndex());
		assertEquals(0, iterator.previousIndex());
		assertEquals(this.getElement(), iterator.next());

		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals(2, iterator.nextIndex());
		assertEquals(1, iterator.previousIndex());
		assertEquals(this.getElement(), iterator.next());

		assertFalse(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals(3, iterator.nextIndex());
		assertEquals(2, iterator.previousIndex());
		boolean exCaught = false;
		try {
			fail("bogus element: " + iterator.next());
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertEquals(this.getElement(), iterator.previous());

		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals(2, iterator.nextIndex());
		assertEquals(1, iterator.previousIndex());
		assertEquals(this.getElement(), iterator.previous());

		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals(1, iterator.nextIndex());
		assertEquals(0, iterator.previousIndex());
		assertEquals(this.getElement(), iterator.previous());

		assertTrue(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
		assertEquals(0, iterator.nextIndex());
		assertEquals(-1, iterator.previousIndex());
		exCaught = false;
		try {
			fail("bogus element: " + iterator.previous());
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			iterator.remove();
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);


		exCaught = false;
		try {
			iterator.add("foo");
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);


		exCaught = false;
		try {
			iterator.set("foo");
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		list = this.buildList(0);
		iterator = list.listIterator();
		assertFalse(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
	}

	public void testRemoveObject() {
		List<String> list = this.buildList(0);
		assertFalse(list.remove(this.getElement()));
		assertFalse(list.remove("foo"));

		list = this.buildList(3);
		assertFalse(list.remove("foo"));
		boolean exCaught = false;
		try {
			list.remove(this.getElement());
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveInt() {
		List<String> list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.remove(0);
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveAllCollection() {
		Collection<String> emptyCollection = Collections.emptySet();

		Collection<String> goodCollection = new ArrayList<String>();
		goodCollection.add("good");
		goodCollection.add("good");
		goodCollection.add("good");

		Collection<String> badCollection = new ArrayList<String>();
		badCollection.add("bad");
		badCollection.add(this.getElement());
		badCollection.add("bad");

		List<String> list = this.buildList(0);
		assertFalse(list.removeAll(emptyCollection));
		assertFalse(list.removeAll(goodCollection));
		assertFalse(list.removeAll(badCollection));

		list = this.buildList(3);
		assertFalse(list.removeAll(emptyCollection));
		assertFalse(list.removeAll(goodCollection));
		boolean exCaught = false;
		try {
			list.removeAll(badCollection);
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRetainAllCollection() {
		Collection<String> emptyCollection = Collections.emptySet();

		Collection<String> goodCollection = new ArrayList<String>();
		goodCollection.add("good");
		goodCollection.add(this.getElement());
		goodCollection.add("good");

		Collection<String> badCollection = new ArrayList<String>();
		badCollection.add("bad");
		badCollection.add("bad");
		badCollection.add("bad");

		List<String> list = this.buildList(0);
		assertFalse(list.retainAll(emptyCollection));
		assertFalse(list.retainAll(goodCollection));
		assertFalse(list.retainAll(badCollection));

		list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.retainAll(emptyCollection);
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertFalse(list.retainAll(goodCollection));
		exCaught = false;
		try {
			list.retainAll(badCollection);
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSetIntObject() {
		List<String> list = this.buildList(3);
		boolean exCaught = false;
		try {
			list.set(0, "foo");
			fail("bogus list: " + list);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		List<String> list = this.buildList(3);
		assertEquals(3, list.size());
	}

	public void testSubList() {
		List<String> list = this.buildList(7);
		List<String> subList = list.subList(2, 7);
		assertEquals(5, subList.size());
		assertEquals(this.getElement(), subList.get(0));
		assertEquals(this.getElement(), subList.get(3));
		assertEquals(this.getElement(), subList.get(4));
	}

	public void testToArray() {
		List<String> list = this.buildList(7);
		Object[] array = list.toArray();
		assertEquals(7, array.length);
		assertEquals(this.getElement(), array[0]);
		assertEquals(this.getElement(), array[3]);
		assertEquals(this.getElement(), array[6]);
	}

	public void testToArrayObjectArray() {
		List<String> list = this.buildList(7);
		String[] array = list.toArray(new String[0]);
		assertEquals(7, array.length);
		assertEquals(this.getElement(), array[0]);
		assertEquals(this.getElement(), array[3]);
		assertEquals(this.getElement(), array[6]);

		String[] template = new String[21];
		array = list.toArray(template);
		assertSame(template, array);
		assertEquals(21, array.length);
		assertEquals(this.getElement(), array[0]);
		assertEquals(this.getElement(), array[3]);
		assertEquals(this.getElement(), array[6]);
		assertNull(array[7]);
		assertNull(array[20]);
	}

	public void testToString() {
		List<String> list1 = this.buildList(3);
		List<String> list2 = new ArrayList<String>();
		list2.add(this.getElement());
		list2.add(this.getElement());
		list2.add(this.getElement());
		assertEquals(list2.toString(), list1.toString());

		list1 = this.buildList(0);
		list2 = new ArrayList<String>();
		assertEquals(list2.toString(), list1.toString());
	}

	public abstract List<String> buildList(int size);

	public abstract String getElement();
}
