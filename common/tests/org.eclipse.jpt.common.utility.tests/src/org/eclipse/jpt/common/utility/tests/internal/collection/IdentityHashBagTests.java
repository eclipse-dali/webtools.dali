/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.SystemTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.IdentityHashBag;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class IdentityHashBagTests
	extends TestCase
{
	private IdentityHashBag<String> bag;
	private String one = "one";
	private String two = "two";
	private String three = "three";
	private String four = "four";
	private String foo = "foo";
	private String bar = "bar";

	public IdentityHashBagTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.bag = this.buildBag();
	}
	
	protected IdentityHashBag<String> buildBag() {
		return CollectionTools.identityHashBag(this.buildBagContents());
	}
	
	protected Collection<String> buildBagContents() {
		ArrayList<String> c = new ArrayList<String>();
		c.add(null);
		c.add(this.one);
		c.add(this.two);
		c.add(this.two);
		c.add(this.three);
		c.add(this.three);
		c.add(this.three);
		c.add(this.four);
		c.add(this.four);
		c.add(this.four);
		c.add(this.four);
		return c;
	}
	
	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private Collection<String> buildCollection() {
		Collection<String> c = new ArrayList<String>();
		c.add(this.foo);
		c.add(this.foo);
		c.add(this.bar);
		c.add(this.bar);
		c.add(this.bar);
		return c;
	}
	
	public void testCtorCollection() {
		Collection<String> c = this.buildCollection();
		IdentityHashBag<String> localBag = new IdentityHashBag<String>(c);
		for (String s : c) {
			assertTrue(localBag.contains(s));
		}
	}
	
	public void testCtorIntFloat() {
		boolean exCaught;
	
		exCaught = false;
		try {
			this.bag = new IdentityHashBag<String>(-20, 0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue("IllegalArgumentException not thrown", exCaught);
	
		exCaught = false;
		try {
			this.bag = new IdentityHashBag<String>(20, -0.66f);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue("IllegalArgumentException not thrown", exCaught);
	}
	
	public void testAdd() {
		// the other adds took place in setUp
		String five = "five";
		assertTrue(this.bag.add(five));
	
		assertTrue(this.bag.contains(this.one));
		assertTrue(this.bag.contains(this.two));
		assertTrue(this.bag.contains(this.three));
		assertTrue(this.bag.contains(this.four));
		assertTrue(this.bag.contains(five));
	}
	
	public void testAddCount() {
		String minus3 = "minus3";
		String zero = "zero";
		String five = "five";
		// the other adds took place in setUp
		this.bag.add(minus3, -3);
		this.bag.add(zero, 0);
		this.bag.add(five, 5);

		assertFalse(this.bag.contains(minus3));
		assertFalse(this.bag.contains(zero));
		assertEquals(1, this.bag.count(this.one));
		assertEquals(2, this.bag.count(this.two));
		assertEquals(3, this.bag.count(this.three));
		assertEquals(4, this.bag.count(this.four));
		assertEquals(5, this.bag.count(five));

		this.bag.add(this.three, 2);
		assertEquals(5, this.bag.count(this.three));
	}

	public void testAddAll() {
		Collection<String> c = this.buildCollection();
		assertTrue(this.bag.addAll(c));
		for (String s : c) {
			assertTrue(this.bag.contains(s));
		}
	}
	
	public void testClear() {
		assertTrue(this.bag.contains(this.one));
		assertTrue(this.bag.contains(this.two));
		assertTrue(this.bag.contains(this.three));
		assertTrue(this.bag.contains(this.four));
		assertTrue(this.bag.contains(null));
		assertEquals(11, this.bag.size());
		this.bag.clear();
		assertFalse(this.bag.contains(this.one));
		assertFalse(this.bag.contains(this.two));
		assertFalse(this.bag.contains(this.three));
		assertFalse(this.bag.contains(this.four));
		assertFalse(this.bag.contains(null));
		assertEquals(0, this.bag.size());
	}
	
	public void testClone() {
		IdentityHashBag<String> bag2 = this.bag.clone();
		assertTrue("bad clone", this.bag != bag2);
		assertEquals("bad clone", this.bag, bag2);
		assertTrue("bad clone", this.bag.hashCode() == bag2.hashCode());
	}
	
	public void testContains() {
		assertTrue(this.bag.contains(null));
		assertTrue(this.bag.contains(this.one));
		assertTrue(this.bag.contains(this.two));
		assertTrue(this.bag.contains(this.three));
		assertTrue(this.bag.contains(this.four));

		assertFalse(this.bag.contains(new String("four")));
		assertFalse(this.bag.contains("five"));
	}
	
	public void testContainsAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(null);
		c.add(this.one);
		c.add(this.two);
		c.add(this.three);
		c.add(this.four);
		assertTrue(this.bag.containsAll(c));
		c.add(new String(this.four));
		assertFalse(this.bag.containsAll(c));
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
		assertEquals(this.bag, this.bag);
		IdentityHashBag<String> bag2 = CollectionTools.identityHashBag(this.buildBagContents(), 20);
		assertEquals(this.bag, bag2);

		bag2.add(this.four);
		assertFalse(this.bag.equals(bag2)); // same unique counts; different sizes
		bag2.remove(this.four);

		String five = "five";
		bag2.add(five);
		bag2.remove(this.four);
		assertFalse(this.bag.equals(bag2)); // same sizes; different unique counts
		bag2.remove(five);
		bag2.add(this.four);

		bag2.remove(this.two);
		bag2.add(this.four);
		assertFalse(this.bag.equals(bag2)); // same sizes; same unique counts

		Collection<String> c = new ArrayList<String>(this.bag);
		assertFalse(this.bag.equals(c));
	}
	
	public void testHashCode() {
		IdentityHashBag<String> bag2 = CollectionTools.identityHashBag(this.buildBagContents().toArray(StringTools.EMPTY_STRING_ARRAY));
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
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);
	
		iterator.remove();
		assertEquals(10, this.bag.size());
	
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
		while (iterator.hasNext() && !this.four.equals(next)) {
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
		String five = "five";
		this.bag.add(five);
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
		Iterator<org.eclipse.jpt.common.utility.collection.Bag.Entry<String>> iterator = this.bag.entries();
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
		org.eclipse.jpt.common.utility.collection.Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals(this.four)) {
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
		String five = "five";
		this.bag.add(five);
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
				if (next.getElement().equals(this.four)) {
					this.bag.remove(this.two);
					iterator.remove();
				}
			}
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testHashingDistribution() throws Exception {
		IdentityHashBag<String> bigBag = new IdentityHashBag<String>();
		for (int i = 0; i < 10000; i++) {
			bigBag.add("object" + i);
		}
	
		java.lang.reflect.Field field = bigBag.getClass().getDeclaredField("table");
		field.setAccessible(true);
		Object[] table = (Object[]) field.get(bigBag);
		int bucketCount = table.length;
		int filledBucketCount = 0;
		for (int i = 0; i < bucketCount; i++) {
			if (table[i] != null) {
				filledBucketCount++;
			}
		}
		float loadFactor = ((float) filledBucketCount) / ((float) bucketCount);
		if ((loadFactor < 0.20) || (loadFactor > 0.80)) {
			String msg = "poor load factor: " + loadFactor;
			if (SystemTools.jvmIsSun()) {
				fail(msg);
			} else {
				// poor load factor is seen in the Eclipse build environment for some reason...
				System.out.println(this.getClass().getName() + '.' + this.getName() + " - " + msg);
				TestTools.printSystemProperties();
			}
		}
	}
	
	public void testRemove() {
		assertTrue(this.bag.remove(this.one));
		assertFalse(this.bag.contains(this.one));
		assertFalse(this.bag.remove(this.one));
	
		assertTrue(this.bag.remove(this.two));
		assertTrue(this.bag.remove(this.two));
		assertFalse(this.bag.contains(this.two));
		assertFalse(this.bag.remove(this.two));

		assertFalse(this.bag.remove(new String(this.three)));
	}
	
	public void testRemoveCount() {
		assertFalse(this.bag.remove(this.one, 0));
		assertTrue(this.bag.contains(this.one));

		assertTrue(this.bag.remove(this.one, 1));
		assertFalse(this.bag.contains(this.one));
		assertFalse(this.bag.remove(this.one));

		assertFalse(this.bag.remove(this.two, -3));
		assertTrue(this.bag.remove(this.two, 1));
		assertTrue(this.bag.contains(this.two));

		assertTrue(this.bag.remove(this.two, 1));
		assertFalse(this.bag.contains(this.two));
		assertFalse(this.bag.remove(this.two));

		assertTrue(this.bag.remove(this.three, 3));
		assertFalse(this.bag.contains(this.three));
		assertFalse(this.bag.remove(this.three));
	}

	public void testRemoveAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(this.one);
		c.add(new String(this.two));
		c.add(this.three);
		assertTrue(this.bag.removeAll(c));
		assertFalse(this.bag.contains(this.one));
		assertTrue(this.bag.contains(this.two));
		assertFalse(this.bag.contains(this.three));
		assertFalse(this.bag.remove(this.one));
		assertTrue(this.bag.remove(this.two));
		assertFalse(this.bag.remove(this.three));
		assertFalse(this.bag.removeAll(c));
	}
	
	public void testRetainAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(this.one);
		c.add(new String(this.two));
		c.add(this.three);
		assertTrue(this.bag.retainAll(c));
		assertTrue(this.bag.contains(this.one));
		assertFalse(this.bag.contains(this.two));
		assertTrue(this.bag.contains(this.three));
		assertFalse(this.bag.contains(this.four));
		assertFalse(this.bag.remove(this.two));
		assertFalse(this.bag.remove(this.four));
		assertFalse(this.bag.retainAll(c));
	}
	
	public void testSize() {
		assertTrue(this.bag.size() == 11);
		String five = "five";
		this.bag.add(five);
		this.bag.add(five);
		this.bag.add(five);
		this.bag.add(five);
		this.bag.add(new String(five));
		assertEquals(16, this.bag.size());
	}
	
	public void testSerialization() throws Exception {
		IdentityHashBag<String> bag2 = TestTools.serialize(this.bag);
	
		assertTrue("same object?", this.bag != bag2);
		assertEquals(11, bag2.size());
		assertEquals(CollectionTools.hashBag(this.bag.iterator()), CollectionTools.hashBag(bag2.iterator()));
		// look for similar elements
		assertTrue(CollectionTools.hashBag(bag2.iterator()).contains(null));
		assertTrue(CollectionTools.hashBag(bag2.iterator()).contains("one"));
		assertTrue(CollectionTools.hashBag(bag2.iterator()).contains("two"));
		assertTrue(CollectionTools.hashBag(bag2.iterator()).contains("three"));
		assertTrue(CollectionTools.hashBag(bag2.iterator()).contains("four"));
	
		int nullCount = 0, oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0;
		for (String next : bag2) {
			if (next == null)
				nullCount++;
			else if (next.equals("one"))
				oneCount++;
			else if (next.equals("two"))
				twoCount++;
			else if (next.equals("three"))
				threeCount++;
			else if (next.equals("four"))
				fourCount++;
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
		assertTrue(ArrayTools.contains(a, this.one));
		assertTrue(ArrayTools.contains(a, this.two));
		assertTrue(ArrayTools.contains(a, this.three));
		assertTrue(ArrayTools.contains(a, this.four));
	}
	
	public void testToArrayObjectArray() {
		String[] a = new String[12];
		a[11] = "not null";
		String[] b = this.bag.toArray(a);
		assertEquals(a, b);
		assertEquals(12, a.length);
		assertTrue(ArrayTools.contains(a, null));
		assertTrue(ArrayTools.contains(a, this.one));
		assertTrue(ArrayTools.contains(a, this.two));
		assertTrue(ArrayTools.contains(a, this.three));
		assertTrue(ArrayTools.contains(a, this.four));
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
		assertEquals("invalid number of commas", 10, commaCount);
		assertTrue(s.indexOf("one") != -1);
		assertTrue(s.indexOf("two") != -1);
		assertTrue(s.indexOf("three") != -1);
		assertTrue(s.indexOf("four") != -1);
		assertTrue(s.indexOf("null") != -1);
	}

	public void testEntry_setCount_increase() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count(this.four));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals(this.four)) {
				assertEquals(4, next.setCount(42));
				break;
			}
		}
		assertEquals(42, this.bag.count(this.four));
		assertEquals(49, this.bag.size());
	}

	public void testEntry_setCount_same() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count(this.four));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals(this.four)) {
				assertEquals(4, next.setCount(4));
				break;
			}
		}
		assertEquals(4, this.bag.count(this.four));
		assertEquals(11, this.bag.size());
	}

	public void testEntry_setCount_derease() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		assertEquals(4, this.bag.count(this.four));
		Bag.Entry<String> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.getElement().equals(this.four)) {
				assertEquals(4, next.setCount(2));
				break;
			}
		}
		assertEquals(2, this.bag.count(this.four));
		assertEquals(9, this.bag.size());
	}

	public void testEntry_setCount_IAE1() {
		Iterator<Bag.Entry<String>> iterator = this.bag.entries();
		boolean exCaught = false;
		try {
			Bag.Entry<String> next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
				if (next.getElement().equals(this.four)) {
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
				if (next.getElement().equals(this.four)) {
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
			if (next1.getElement().equals(this.four)) {
				break;
			}
		}
		assertFalse(next1.equals(this.four));
		Iterator<Bag.Entry<String>> iterator2 = bag2.entries();
		Bag.Entry<String> next2 = null;
		while (iterator2.hasNext()) {
			next2 = iterator2.next();
			if (next2.getElement().equals(this.four)) {
				break;
			}
		}
		assertEquals(next1, next2);

		bag2.remove(this.four);
		iterator1 = this.bag.entries();
		while (iterator1.hasNext()) {
			next1 = iterator1.next();
			if (next1.getElement().equals(this.four)) {
				break;
			}
		}
		iterator2 = bag2.entries();
		while (iterator2.hasNext()) {
			next2 = iterator2.next();
			if (next2.getElement().equals(this.four)) {
				break;
			}
		}
		assertEquals(next1.getElement(), next2.getElement());
		assertFalse(next1.equals(next2));

		iterator1 = this.bag.entries();
		while (iterator1.hasNext()) {
			next1 = iterator1.next();
			if (next1.getElement().equals(this.three)) {
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
			if (next.getElement().equals(this.four)) {
				break;
			}
		}
		assertEquals(4 * this.four.hashCode(), next.hashCode());

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
			if (next.getElement().equals(this.four)) {
				break;
			}
		}
		assertEquals("four=>4", next.toString());
	}
}
