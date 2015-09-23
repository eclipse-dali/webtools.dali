/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

//subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class BagTests
	extends MultiThreadedTestCase
{
	private Bag<String> bag;

	protected BagTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.bag = this.buildBag();
	}

	protected Bag<String> buildBag() {
		Bag<String> b = this.buildBag_();
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

	protected abstract Bag<String> buildBag_();

	protected abstract Bag<String> buildBag(Collection<String> c);

	protected abstract Bag<String> buildBag(int initialCapacity, float loadFactor);

	@Override
	protected void tearDown() throws Exception {
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
		Bag<String> b = this.buildBag(c);
		for (String s : c) {
			assertTrue(b.contains(s));
		}
	}

	public void testCtorIntFloat() {
		boolean exCaught;

		exCaught = false;
		try {
			this.bag = this.buildBag(-20, 0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.bag = this.buildBag(20, -0.66f);
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
		@SuppressWarnings("unchecked")
		Bag<String> bag2 = (Bag<String>) ObjectTools.execute(this.bag, "clone");
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

	public void testEqualsObject() {
		Bag<String> bag2 = this.buildBag();
		assertEquals(this.bag, this.bag);
		assertEquals(this.bag, bag2);

		bag2.add("four");
		assertFalse(this.bag.equals(bag2)); // same unique counts; different sizes
		bag2.remove("four");

		bag2.add("five");
		bag2.remove("four");
		assertFalse(this.bag.equals(bag2)); // same sizes; different unique counts
		bag2.remove("five");
		bag2.add("four");

		bag2.remove("two");
		bag2.add("four");
		assertFalse(this.bag.equals(bag2)); // same sizes; same unique counts

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

	public void testIterator_remove_all() {
		assertEquals(4, this.bag.count("four"));
		Iterator<String> iterator = this.bag.iterator();
		String next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if ((next != null) && next.equals("four")) {
				iterator.remove();
			}
		}
		assertEquals(0, this.bag.count("four"));
	}

	public void testIterator_remove_CME() {
		Iterator<String> iterator = this.bag.iterator();
		assertTrue(iterator.hasNext());
		boolean exCaught = false;
		try {
			String next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next.equals("four")) {
					this.bag.remove("two");
					iterator.remove();
				}
			}
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyUniqueIterator() {
		this.bag.clear();
		Iterator<String> iterator = this.bag.uniqueIterator();
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

	public void testEmptyEntries() {
		this.bag.clear();
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
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

	public void testEntries() {
		int i = 0;
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
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
		iterator = this.bag.entries();
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				iterator.remove();
				break;
			}
		}
		assertEquals(7, this.bag.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		// start over
		iterator = this.bag.entries();
		this.bag.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntries_remove_CME() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertTrue(iterator.hasNext());
		boolean exCaught = false;
		try {
			Bag.Entry<String> next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next.getElement().equals("four")) {
					this.bag.remove("two");
					iterator.remove();
				}
			}
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntry_setCount_increase() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count("four"));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				assertEquals(4, next.setCount(42));
				break;
			}
		}
		assertEquals(42, this.bag.count("four"));
		assertEquals(49, this.bag.size());
	}

	public void testEntry_setCount_same() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count("four"));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				assertEquals(4, next.setCount(4));
				break;
			}
		}
		assertEquals(4, this.bag.count("four"));
		assertEquals(11, this.bag.size());
	}

	public void testEntry_setCount_derease() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count("four"));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				assertEquals(4, next.setCount(2));
				break;
			}
		}
		assertEquals(2, this.bag.count("four"));
		assertEquals(9, this.bag.size());
	}

	public void testEntry_setCount_IAE1() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		boolean exCaught = false;
		try {
			Bag.Entry<String> next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next.getElement().equals("four")) {
					next.setCount(0);
					fail(next.toString());
				}
			}
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntry_setCount_IAE2() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		boolean exCaught = false;
		try {
			Bag.Entry<String> next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next.getElement().equals("four")) {
					next.setCount(-33);
					fail(next.toString());
				}
			}
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	@SuppressWarnings("null")
	public void testEntry_equalsObject() {
		Iterator<Bag.Entry<String>> iterator1 = this.bag.entries();
		Bag<String> bag2 = this.buildBag();
		Bag.Entry<String> next1 = null;
		while (iterator1.hasNext()) {
			next1 = iterator1.next();
			if (next1.getElement().equals("four")) {
				break;
			}
		}
		assertFalse(next1.equals("four"));
		Iterator<Bag.Entry<String>> iterator2 = bag2.entries();
		Bag.Entry<String> next2 = null;
		while (iterator2.hasNext()) {
			next2 = iterator2.next();
			if (next2.getElement().equals("four")) {
				break;
			}
		}
		assertEquals(next1, next2);

		bag2.remove("four");
		iterator1 = this.bag.entries();
		while (iterator1.hasNext()) {
			next1 = iterator1.next();
			if (next1.getElement().equals("four")) {
				break;
			}
		}
		iterator2 = bag2.entries();
		while (iterator2.hasNext()) {
			next2 = iterator2.next();
			if (next2.getElement().equals("four")) {
				break;
			}
		}
		assertEquals(next1.getElement(), next2.getElement());
		assertFalse(next1.equals(next2));

		iterator1 = this.bag.entries();
		while (iterator1.hasNext()) {
			next1 = iterator1.next();
			if (next1.getElement().equals("three")) {
				break;
			}
		}
		assertEquals(next1.getCount(), next2.getCount());
		assertFalse(next1.equals(next2));
	}

	@SuppressWarnings("null")
	public void testEntry_hashCode() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				break;
			}
		}
		assertEquals(4 * "four".hashCode(), next.hashCode());

		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement() == null) {
				break;
			}
		}
		assertEquals(0, next.hashCode());
	}

	@SuppressWarnings("null")
	public void testEntry_toString() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals("four")) {
				break;
			}
		}
		assertEquals("four=>4", next.toString());
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

	public void testSerialization_empty() throws Exception {
		this.bag.clear();
		Bag<String> bag2 = TestTools.serialize(this.bag);

		assertTrue("same object?", this.bag != bag2);
		assertEquals(0, bag2.size());
		assertEquals(this.bag, bag2);
	}

	public void testToArray() {
		Object[] a = this.bag.toArray();
		assertEquals(11, a.length);
		assertTrue(ArrayTools.contains(a, null));
		assertTrue(ArrayTools.contains(a, "one"));
		assertTrue(ArrayTools.contains(a, "two"));
		assertTrue(ArrayTools.contains(a, "three"));
		assertTrue(ArrayTools.contains(a, "four"));
	}

	public void testToArrayObjectArray() {
		String[] a = new String[12];
		a[11] = "not null";
		String[] b = this.bag.toArray(a);
		assertEquals(a, b);
		assertEquals(12, a.length);
		assertTrue(ArrayTools.contains(a, null));
		assertTrue(ArrayTools.contains(a, "one"));
		assertTrue(ArrayTools.contains(a, "two"));
		assertTrue(ArrayTools.contains(a, "three"));
		assertTrue(ArrayTools.contains(a, "four"));
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
