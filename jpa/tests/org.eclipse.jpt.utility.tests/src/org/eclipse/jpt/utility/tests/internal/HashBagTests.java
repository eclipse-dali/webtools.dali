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
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;

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
			assertTrue("missing element", b.contains(s));
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
		assertTrue("IllegalArgumentException not thrown", exCaught);

		exCaught = false;
		try {
			this.bag = new HashBag<String>(20, -0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue("IllegalArgumentException not thrown", exCaught);
	}

	public void testAdd() {
		// the other adds took place in setUp
		assertTrue("incorrect return value", this.bag.add("five"));

		assertTrue("missing element", this.bag.contains("one"));
		assertTrue("missing element", this.bag.contains("two"));
		assertTrue("missing element", this.bag.contains("three"));
		assertTrue("missing element", this.bag.contains("four"));
		assertTrue("missing element", this.bag.contains("five"));
	}

	public void testAddAll() {
		Collection<String> c = this.buildCollection();
		assertTrue("incorrect return value", this.bag.addAll(c));
		for (String s : c) {
			assertTrue("missing element", this.bag.contains(s));
		}
	}

	public void testClear() {
		assertTrue("missing element", this.bag.contains("one"));
		assertTrue("missing element", this.bag.contains("two"));
		assertTrue("missing element", this.bag.contains("three"));
		assertTrue("missing element", this.bag.contains("four"));
		assertTrue("missing element", this.bag.contains(null));
		assertEquals("invalid size", 11, this.bag.size());
		this.bag.clear();
		assertFalse("malingering element", this.bag.contains("one"));
		assertFalse("malingering element", this.bag.contains("two"));
		assertFalse("malingering element", this.bag.contains("three"));
		assertFalse("malingering element", this.bag.contains("four"));
		assertFalse("malingering element", this.bag.contains(null));
		assertEquals("invalid size", 0, this.bag.size());
	}

	public void testClone() {
		Bag<String> bag2 = this.bag.clone();
		assertTrue("bad clone", this.bag != bag2);
		assertEquals("bad clone", this.bag, bag2);
		assertTrue("bad clone", this.bag.hashCode() == bag2.hashCode());
	}

	public void testContains() {
		assertTrue("missing element", this.bag.contains(null));
		assertTrue("missing element", this.bag.contains("one"));
		assertTrue("missing element", this.bag.contains("two"));
		assertTrue("missing element", this.bag.contains("three"));
		assertTrue("missing element", this.bag.contains("four"));
		assertTrue("missing element", this.bag.contains(new String("four")));
		assertTrue("missing element", this.bag.contains("fo" + "ur"));
		assertFalse("element found", this.bag.contains("five"));
	}

	public void testContainsAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(null);
		c.add(new String("one"));
		c.add(new String("two"));
		c.add(new String("three"));
		c.add(new String("four"));
		assertTrue("missing element(s)", this.bag.containsAll(c));
	}

	public void testCount() {
		assertEquals("bad count", 0, this.bag.count("zero"));
		assertEquals("bad count", 1, this.bag.count("one"));
		assertEquals("bad count", 2, this.bag.count("two"));
		assertEquals("bad count", 3, this.bag.count("three"));
		assertEquals("bad count", 4, this.bag.count("four"));
		assertEquals("bad count", 0, this.bag.count("five"));
	}

	public void testEquals() {
		Bag<String> bag2 = this.buildBag();
		assertEquals("bags are not equal", this.bag, bag2);
		bag2.add("five");
		assertFalse("bags are equal", this.bag.equals(bag2));
		Collection<String> c = new ArrayList<String>(this.bag);
		assertFalse("bags are not equal to collections", this.bag.equals(c));
	}

	public void testHashCode() {
		Bag<String> bag2 = this.buildBag();
		assertEquals("bad hash code", this.bag.hashCode(), bag2.hashCode());
	}

	public void testIsEmpty() {
		assertFalse("bag is empty", this.bag.isEmpty());
		this.bag.clear();
		assertTrue("bag is not empty", this.bag.isEmpty());
		this.bag.add("foo");
		assertFalse("bag is empty", this.bag.isEmpty());
	}

	public void testEmptyIterator() {
		this.bag.clear();
		Iterator<String> iterator = this.bag.iterator();
		assertFalse("iterator is not empty", iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);
	}

	public void testIterator() {
		int i = 0;
		Iterator<String> iterator = this.bag.iterator();
		assertTrue("iterator is empty", iterator.hasNext());
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals("invalid hasNext() loop", 11, i);
		assertFalse("iterator should be empty now", iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);

		iterator.remove();
		assertEquals("iterator did not remove element", 10, this.bag.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);

		// start over
		iterator = this.bag.iterator();
		this.bag.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue("ConcurrentModificationException not thrown", exCaught);
	}

	public void testUniqueIterator() {
		int i = 0;
		Iterator<String> iterator = this.bag.uniqueIterator();
		assertTrue("iterator is empty", iterator.hasNext());
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals("invalid hasNext() loop", 5, i);
		assertFalse("iterator should be empty now", iterator.hasNext());

		boolean exCaught = false;
		Object element = null;
		try {
			element = iterator.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);

		// start over
		iterator = this.bag.uniqueIterator();
		Object next = null;
		while (iterator.hasNext() && !"four".equals(next)) {
			next = iterator.next();
		}
		iterator.remove();
		assertEquals("iterator did not remove all copies of element", 7, this.bag.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);

		// start over
		iterator = this.bag.uniqueIterator();
		this.bag.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue("ConcurrentModificationException not thrown", exCaught);
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
		assertTrue("incorrect return value", this.bag.remove("one"));
		assertFalse("element still present", this.bag.contains("one"));
		assertFalse("incorrect return value", this.bag.remove("one"));

		assertTrue("incorrect return value", this.bag.remove("two"));
		assertTrue("incorrect return value", this.bag.remove("two"));
		assertFalse("element still present", this.bag.contains("one"));
		assertFalse("incorrect return value", this.bag.remove("one"));
	}

	public void testRemoveAll() {
		Collection<String> c = new ArrayList<String>();
		c.add("one");
		c.add("three");
		assertTrue("incorrect return value", this.bag.removeAll(c));
		assertFalse("element still present", this.bag.contains("one"));
		assertFalse("element still present", this.bag.contains("three"));
		assertFalse("incorrect return value", this.bag.remove("one"));
		assertFalse("incorrect return value", this.bag.remove("three"));
		assertFalse("incorrect return value", this.bag.removeAll(c));
	}

	public void testRetainAll() {
		Collection<String> c = new ArrayList<String>();
		c.add("one");
		c.add("three");
		assertTrue("incorrect return value", this.bag.retainAll(c));
		assertTrue("element removed", this.bag.contains("one"));
		assertTrue("element removed", this.bag.contains("three"));
		assertFalse("element still present", this.bag.contains("two"));
		assertFalse("element still present", this.bag.contains("four"));
		assertFalse("incorrect return value", this.bag.remove("two"));
		assertFalse("incorrect return value", this.bag.remove("four"));
		assertFalse("incorrect return value", this.bag.retainAll(c));
	}

	public void testSize() {
		assertTrue("incorrect size", this.bag.size() == 11);
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		this.bag.add("five");
		assertEquals("incorrect size", 16, this.bag.size());
	}

	public void testSerialization() throws Exception {
		Bag<String> bag2 = TestTools.serialize(this.bag);

		assertTrue("same object?", this.bag != bag2);
		assertEquals("incorrect size", 11, bag2.size());
		assertEquals("unequal bag", this.bag, bag2);
		// look for similar elements
		assertTrue("missing element", bag2.contains(null));
		assertTrue("missing element", bag2.contains("one"));
		assertTrue("missing element", bag2.contains("two"));
		assertTrue("missing element", bag2.contains("three"));
		assertTrue("missing element", bag2.contains("four"));

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
		assertEquals("bad element count", 1, nullCount);
		assertEquals("bad element count", 1, oneCount);
		assertEquals("bad element count", 2, twoCount);
		assertEquals("bad element count", 3, threeCount);
		assertEquals("bad element count", 4, fourCount);
	}

	public void testToArray() {
		Object[] a = this.bag.toArray();
		assertEquals("incorrect length", 11, a.length);
		assertTrue("missing element", CollectionTools.contains(a, null));
		assertTrue("missing element", CollectionTools.contains(a, "one"));
		assertTrue("missing element", CollectionTools.contains(a, "two"));
		assertTrue("missing element", CollectionTools.contains(a, "three"));
		assertTrue("missing element", CollectionTools.contains(a, "four"));
	}

	public void testToArrayObjectArray() {
		String[] a = new String[12];
		a[11] = "not null";
		String[] b = this.bag.toArray(a);
		assertEquals("different array", a, b);
		assertEquals("incorrect length", 12, a.length);
		assertTrue("missing element", CollectionTools.contains(a, null));
		assertTrue("missing element", CollectionTools.contains(a, "one"));
		assertTrue("missing element", CollectionTools.contains(a, "two"));
		assertTrue("missing element", CollectionTools.contains(a, "three"));
		assertTrue("missing element", CollectionTools.contains(a, "four"));
		assertTrue("missing null element", a[11] == null);
	}

	public void testToString() {
		String s = this.bag.toString();
		assertTrue("invalid string prefix", s.startsWith("["));
		assertTrue("invalid string suffix", s.endsWith("]"));
		int commaCount = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				commaCount++;
			}
		}
		assertEquals("invalid number of commas", 10, commaCount);
		assertTrue("missing element toString()", s.indexOf("one") != -1);
		assertTrue("missing element toString()", s.indexOf("two") != -1);
		assertTrue("missing element toString()", s.indexOf("three") != -1);
		assertTrue("missing element toString()", s.indexOf("four") != -1);
		assertTrue("missing element toString()", s.indexOf("null") != -1);
	}

}
