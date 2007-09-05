/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;

import junit.framework.TestCase;

public class HashBagTests extends TestCase {
	private HashBag<String> bag;

	public HashBagTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.bag = this.buildBag();
	}

	private HashBag<String> buildBag() {
		HashBag<String> b = new HashBag<String>();
		b.add(null);
		b.add(new String("one"));
		b.add(new String("two"));
		b.add(new String("two"));
		b.add(new String("three"));
		b.add(new String("three"));
		b.add(new String("three"));
		b.add(new String("four"));
		b.add(new String("four"));
		b.add(new String("four"));
		b.add(new String("four"));
		return b;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private Collection<String> buildCollection() {
		Collection<String> c = new ArrayList<String>();
		c.add(new String("foo"));
		c.add(new String("foo"));
		c.add(new String("bar"));
		c.add(new String("bar"));
		c.add(new String("bar"));
		return c;
	}

	public void testCtorCollection() {
		Collection<String> c = this.buildCollection();
		Bag<String> b = new HashBag<String>(c);
		for (String s : c) {
			assertTrue(b.contains(s));
		}
	}

	public void testCtorIntFloat() {
		boolean exCaught;

		exCaught = false;
		try {
			this.bag = new HashBag<String>(-20, 0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.bag = new HashBag<String>(20, -0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdd() {
		// the other adds took place in setUp
		assertTrue(this.bag.add("five"));

		assertTrue(this.bag.contains("one"));
		assertTrue(this.bag.contains("two"));
		assertTrue(this.bag.contains("three"));
		assertTrue(this.bag.contains("four"));
		assertTrue(this.bag.contains("five"));
	}

	public void testAddCount() {
		// the other adds took place in setUp
		this.bag.add("minus3", -3);
		this.bag.add("zero", 0);
		this.bag.add("five", 5);

		assertFalse(this.bag.contains("minus3"));
		assertFalse(this.bag.contains("zero"));
		assertEquals(1, this.bag.count("one"));
		assertEquals(2, this.bag.count("two"));
		assertEquals(3, this.bag.count("three"));
		assertEquals(4, this.bag.count("four"));
		assertEquals(5, this.bag.count("five"));

		this.bag.add("three", 2);
		assertEquals(5, this.bag.count("three"));
	}

	public void testAddAll() {
		Collection<String> c = this.buildCollection();
		assertTrue(this.bag.addAll(c));
		for (String s : c) {
			assertTrue(this.bag.contains(s));
		}
	}

	public void testClear() {
		assertTrue(this.bag.contains("one"));
		assertTrue(this.bag.contains("two"));
		assertTrue(this.bag.contains("three"));
		assertTrue(this.bag.contains("four"));
		assertTrue(this.bag.contains(null));
		assertEquals(11, this.bag.size());
		this.bag.clear();
		assertFalse(this.bag.contains("one"));
		assertFalse(this.bag.contains("two"));
		assertFalse(this.bag.contains("three"));
		assertFalse(this.bag.contains("four"));
		assertFalse(this.bag.contains(null));
		assertEquals(0, this.bag.size());
	}

	public void testClone() {
		Bag<String> bag2 = this.bag.clone();
		assertTrue(this.bag != bag2);
		assertEquals(this.bag, bag2);
		assertTrue(this.bag.hashCode() == bag2.hashCode());
	}

	public void testContains() {
		assertTrue(this.bag.contains(null));
		assertTrue(this.bag.contains("one"));
		assertTrue(this.bag.contains("two"));
		assertTrue(this.bag.contains("three"));
		assertTrue(this.bag.contains("four"));
		assertTrue(this.bag.contains(new String("four")));
		assertTrue(this.bag.contains("fo" + "ur"));
		assertFalse(this.bag.contains("five"));
	}

	public void testContainsAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(null);
		c.add(new String("one"));
		c.add(new String("two"));
		c.add(new String("three"));
		c.add(new String("four"));
		assertTrue(this.bag.containsAll(c));
	}

	public void testCount() {
		assertEquals(0, this.bag.count("zero"));
		assertEquals(1, this.bag.count("one"));
		assertEquals(2, this.bag.count("two"));
		assertEquals(3, this.bag.count("three"));
		assertEquals(4, this.bag.count("four"));
		assertEquals(0, this.bag.count("five"));
	}

	public void testEquals() {
		Bag<String> bag2 = this.buildBag();
		assertEquals(this.bag, bag2);
		bag2.add("five");
		assertFalse(this.bag.equals(bag2));
		Collection<String> c = new ArrayList<String>(this.bag);
		assertFalse(this.bag.equals(c));
	}

	public void testHashCode() {
		Bag<String> bag2 = this.buildBag();
		assertEquals(this.bag.hashCode(), bag2.hashCode());
	}

	public void testIsEmpty() {
		assertFalse(this.bag.isEmpty());
		this.bag.clear();
		assertTrue(this.bag.isEmpty());
		this.bag.add("foo");
		assertFalse(this.bag.isEmpty());
	}

	public void testEmptyIterator() {
		this.bag.clear();
		Iterator<String> iterator = this.bag.iterator();
		assertFalse(iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
			fail(element.toString());
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIterator() {
		int i = 0;
		Iterator<String> iterator = this.bag.iterator();
		assertTrue(iterator.hasNext());
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(11, i);
		assertFalse(iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
			fail(element.toString());
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		iterator.remove();
		assertEquals(10, this.bag.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		// start over
		iterator = this.bag.iterator();
		this.bag.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUniqueIterator() {
		int i = 0;
		Iterator<String> iterator = this.bag.uniqueIterator();
		assertTrue(iterator.hasNext());
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(5, i);
		assertFalse(iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
			fail(element.toString());
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		// start over
		iterator = this.bag.uniqueIterator();
		Object next = null;
		while (iterator.hasNext() && !"four".equals(next)) {
			next = iterator.next();
		}
		iterator.remove();
		assertEquals(7, this.bag.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		// start over
		iterator = this.bag.uniqueIterator();
		this.bag.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testHashingDistribution() throws Exception {
		Bag<String> bigBag = new HashBag<String>();
		for (int i = 0; i < 10000; i++) {
			bigBag.add("object" + i);
		}

		java.lang.reflect.Field field = bigBag.getClass().getDeclaredField("table");
		field.setAccessible(true);
		Object[] table = (Object[]) field.get(bigBag);
		int bucketCount = table.length;
		int filledBucketCount = 0;
		for (Object o : table) {
			if (o != null) {
				filledBucketCount++;
			}
		}
		float loadFactor = ((float) filledBucketCount) / ((float) bucketCount);
		assertTrue("WARNING - poor load factor: " + loadFactor, loadFactor > 0.20);
		assertTrue("WARNING - poor load factor: " + loadFactor, loadFactor < 0.75);
	}

	public void testRemove() {
		assertTrue(this.bag.remove("one"));
		assertFalse(this.bag.contains("one"));
		assertFalse(this.bag.remove("one"));

		assertTrue(this.bag.remove("two"));
		assertTrue(this.bag.remove("two"));
		assertFalse(this.bag.contains("two"));
		assertFalse(this.bag.remove("two"));
	}

	public void testRemoveCount() {
		assertFalse(this.bag.remove("one", 0));
		assertTrue(this.bag.contains("one"));

		assertTrue(this.bag.remove("one", 1));
		assertFalse(this.bag.contains("one"));
		assertFalse(this.bag.remove("one"));

		assertFalse(this.bag.remove("two", -3));
		assertTrue(this.bag.remove("two", 1));
		assertTrue(this.bag.contains("two"));

		assertTrue(this.bag.remove("two", 1));
		assertFalse(this.bag.contains("two"));
		assertFalse(this.bag.remove("two"));

		assertTrue(this.bag.remove("three", 3));
		assertFalse(this.bag.contains("three"));
		assertFalse(this.bag.remove("three"));
	}

	public void testRemoveAll() {
		Collection<String> c = new ArrayList<String>();
		c.add("one");
		c.add("three");
		assertTrue(this.bag.removeAll(c));
		assertFalse(this.bag.contains("one"));
		assertFalse(this.bag.contains("three"));
		assertFalse(this.bag.remove("one"));
		assertFalse(this.bag.remove("three"));
		assertFalse(this.bag.removeAll(c));
	}

	public void testRetainAll() {
		Collection<String> c = new ArrayList<String>();
		c.add("one");
		c.add("three");
		assertTrue(this.bag.retainAll(c));
		assertTrue(this.bag.contains("one"));
		assertTrue(this.bag.contains("three"));
		assertFalse(this.bag.contains("two"));
		assertFalse(this.bag.contains("four"));
		assertFalse(this.bag.remove("two"));
		assertFalse(this.bag.remove("four"));
		assertFalse(this.bag.retainAll(c));
	}

	public void testSize() {
		assertTrue(this.bag.size() == 11);
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		assertEquals(16, this.bag.size());
	}

	public void testSerialization() throws Exception {
		Bag<String> bag2 = TestTools.serialize(this.bag);

		assertTrue("same object?", this.bag != bag2);
		assertEquals(11, bag2.size());
		assertEquals(this.bag, bag2);
		// look for similar elements
		assertTrue(bag2.contains(null));
		assertTrue(bag2.contains("one"));
		assertTrue(bag2.contains("two"));
		assertTrue(bag2.contains("three"));
		assertTrue(bag2.contains("four"));

		int nullCount = 0, oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0;
		for (String s : bag2) {
			if (s == null) {
				nullCount++;
			} else if (s.equals("one")) {
				oneCount++;
			} else if (s.equals("two")) {
				twoCount++;
			} else if (s.equals("three")) {
				threeCount++;
			} else if (s.equals("four")) {
				fourCount++;
			}
		}
		assertEquals(1, nullCount);
		assertEquals(1, oneCount);
		assertEquals(2, twoCount);
		assertEquals(3, threeCount);
		assertEquals(4, fourCount);
	}

	public void testToArray() {
		Object[] a = this.bag.toArray();
		assertEquals(11, a.length);
		assertTrue(CollectionTools.contains(a, null));
		assertTrue(CollectionTools.contains(a, "one"));
		assertTrue(CollectionTools.contains(a, "two"));
		assertTrue(CollectionTools.contains(a, "three"));
		assertTrue(CollectionTools.contains(a, "four"));
	}

	public void testToArrayObjectArray() {
		String[] a = new String[12];
		a[11] = "not null";
		String[] b = this.bag.toArray(a);
		assertEquals(a, b);
		assertEquals(12, a.length);
		assertTrue(CollectionTools.contains(a, null));
		assertTrue(CollectionTools.contains(a, "one"));
		assertTrue(CollectionTools.contains(a, "two"));
		assertTrue(CollectionTools.contains(a, "three"));
		assertTrue(CollectionTools.contains(a, "four"));
		assertTrue(a[11] == null);
	}

	public void testToString() {
		String s = this.bag.toString();
		assertTrue(s.startsWith("["));
		assertTrue(s.endsWith("]"));
		int commaCount = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				commaCount++;
			}
		}
		assertEquals(10, commaCount);
		assertTrue(s.indexOf("one") != -1);
		assertTrue(s.indexOf("two") != -1);
		assertTrue(s.indexOf("three") != -1);
		assertTrue(s.indexOf("four") != -1);
		assertTrue(s.indexOf("null") != -1);
	}

}
