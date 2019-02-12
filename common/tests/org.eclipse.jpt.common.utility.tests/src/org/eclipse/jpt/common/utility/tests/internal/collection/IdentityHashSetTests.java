/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
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
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.IdentityHashSet;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class IdentityHashSetTests
	extends TestCase
{
	private IdentityHashSet<String> set;
	private String one = "one";
	private String two = "two";
	private String three = "three";
	private String four = "four";
	private String foo = "foo";
	private String bar = "bar";

	public IdentityHashSetTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.set = this.buildSet();
	}

	protected IdentityHashSet<String> buildSet() {
		IdentityHashSet<String> result = new IdentityHashSet<String>();
		result.add(null);
		result.add(this.one);
		result.add(this.two);
		result.add(this.two);
		result.add(this.three);
		result.add(this.three);
		result.add(this.three);
		result.add(this.four);
		result.add(this.four);
		result.add(this.four);
		result.add(this.four);
		return result;
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
		IdentityHashSet<String> localBag = new IdentityHashSet<String>(c);
		for (String s : c) {
			assertTrue(localBag.contains(s));
		}
	}

	public void testCtorInt() {
		this.set = new IdentityHashSet<String>(20);
		assertNotNull(this.set);

		boolean exCaught;
		exCaught = false;
		try {
			this.set = new IdentityHashSet<String>(-20);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdd() {
		// the other adds took place in setUp
		String five = "five";
		assertTrue(this.set.add(five));

		assertTrue(this.set.contains(this.one));
		assertTrue(this.set.contains(this.two));
		assertTrue(this.set.contains(this.three));
		assertTrue(this.set.contains(this.four));
		assertTrue(this.set.contains(five));
	}

	public void testAddAll() {
		Collection<String> c = this.buildCollection();
		assertTrue(this.set.addAll(c));
		for (String s : c) {
			assertTrue(this.set.contains(s));
		}
	}

	public void testClear() {
		assertTrue(this.set.contains(this.one));
		assertTrue(this.set.contains(this.two));
		assertTrue(this.set.contains(this.three));
		assertTrue(this.set.contains(this.four));
		assertTrue(this.set.contains(null));
		assertEquals(5, this.set.size());
		this.set.clear();
		assertFalse(this.set.contains(this.one));
		assertFalse(this.set.contains(this.two));
		assertFalse(this.set.contains(this.three));
		assertFalse(this.set.contains(this.four));
		assertFalse(this.set.contains(null));
		assertEquals(0, this.set.size());
	}

	public void testClone() {
		IdentityHashSet<String> set2 = this.set.clone();
		assertTrue("bad clone", this.set != set2);
		assertEquals("bad clone", this.set, set2);
		assertTrue("bad clone", this.set.hashCode() == set2.hashCode());
	}

	public void testContains() {
		assertTrue(this.set.contains(null));
		assertTrue(this.set.contains(this.one));
		assertTrue(this.set.contains(this.two));
		assertTrue(this.set.contains(this.three));
		assertTrue(this.set.contains(this.four));

		assertFalse(this.set.contains(new String("four")));
		assertFalse(this.set.contains("five"));
	}

	public void testContainsAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(null);
		c.add(this.one);
		c.add(this.two);
		c.add(this.three);
		c.add(this.four);
		assertTrue(this.set.containsAll(c));
		c.add(new String(this.four));
		assertFalse(this.set.containsAll(c));
	}

	public void testEquals() {
		assertEquals(this.set, this.set);
		IdentityHashSet<String> set2 = this.buildSet();
		assertEquals(this.set, set2);
		set2.add("five");
		assertFalse(this.set.equals(set2));
		Collection<String> c = new ArrayList<String>(this.set);
		assertFalse(this.set.equals(c));
	}

	public void testEquals_set() {
		HashSet<String> set2 = new HashSet<String>(this.set);
		assertEquals(this.set, set2);
		set2.add("five");
		assertFalse(this.set.equals(set2));
	}

	public void testHashCode() {
		IdentityHashSet<String> set2 = this.buildSet();
		assertEquals(this.set.hashCode(), set2.hashCode());
	}

	public void testIsEmpty() {
		assertFalse(this.set.isEmpty());
		this.set.clear();
		assertTrue(this.set.isEmpty());
		this.set.add("foo");
		assertFalse(this.set.isEmpty());
	}

	public void testEmptyIterator() {
		this.set.clear();
		Iterator<String> iterator = this.set.iterator();
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
		Iterator<String> iterator = this.set.iterator();
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
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + element, exCaught);

		iterator.remove();
		assertEquals(4, this.set.size());

		exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);

		// start over
		iterator = this.set.iterator();
		this.set.add("five");
		exCaught = false;
		try {
			iterator.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue("ConcurrentModificationException not thrown", exCaught);
	}

	public void testRemove() {
		assertTrue(this.set.remove(this.one));
		assertFalse(this.set.contains(this.one));
		assertFalse(this.set.remove(this.one));

		assertTrue(this.set.remove(this.two));
		assertFalse(this.set.remove(this.two));
		assertFalse(this.set.contains(this.two));
		assertFalse(this.set.remove(this.two));

		assertFalse(this.set.remove(new String(this.three)));
	}

	public void testRemoveAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(this.one);
		c.add(new String(this.two));
		c.add(this.three);
		assertTrue(this.set.removeAll(c));
		assertFalse(this.set.contains(this.one));
		assertTrue(this.set.contains(this.two));
		assertFalse(this.set.contains(this.three));
		assertFalse(this.set.remove(this.one));
		assertTrue(this.set.remove(this.two));
		assertFalse(this.set.remove(this.three));
		assertFalse(this.set.removeAll(c));
	}

	public void testRetainAll() {
		Collection<String> c = new ArrayList<String>();
		c.add(this.one);
		c.add(new String(this.two));
		c.add(this.three);
		assertTrue(this.set.retainAll(c));
		assertTrue(this.set.contains(this.one));
		assertFalse(this.set.contains(this.two));
		assertTrue(this.set.contains(this.three));
		assertFalse(this.set.contains(this.four));
		assertFalse(this.set.remove(this.two));
		assertFalse(this.set.remove(this.four));
		assertFalse(this.set.retainAll(c));
	}

	public void testRetainAll_IHS() {
		Collection<String> c = new IdentityHashSet<String>();
		c.add(this.one);
		c.add(new String(this.two));
		c.add(this.three);
		assertTrue(this.set.retainAll(c));
		assertTrue(this.set.contains(this.one));
		assertFalse(this.set.contains(this.two));
		assertTrue(this.set.contains(this.three));
		assertFalse(this.set.contains(this.four));
		assertFalse(this.set.remove(this.two));
		assertFalse(this.set.remove(this.four));
		assertFalse(this.set.retainAll(c));
	}

	public void testSize() {
		assertEquals(5, this.set.size());
		String five = "five";
		this.set.add(five);
		this.set.add(five);
		this.set.add(five);
		this.set.add(five);
		this.set.add(new String(five));
		assertEquals(7, this.set.size());
	}

	public void testSerialization() throws Exception {
		IdentityHashSet<String> set2 = TestTools.serialize(this.set);

		assertTrue("same object?", this.set != set2);
		assertEquals(5, set2.size());
		assertEquals(CollectionTools.hashSet(this.set.iterator()), CollectionTools.hashSet(set2.iterator()));
		// look for similar elements
		assertTrue(CollectionTools.hashSet(set2.iterator()).contains(null));
		assertTrue(CollectionTools.hashSet(set2.iterator()).contains("one"));
		assertTrue(CollectionTools.hashSet(set2.iterator()).contains("two"));
		assertTrue(CollectionTools.hashSet(set2.iterator()).contains("three"));
		assertTrue(CollectionTools.hashSet(set2.iterator()).contains("four"));

		int nullCount = 0, oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0;
		for (String next : set2) {
			if (next == null) {
				nullCount++;
			} else if (next.equals("one")) {
				oneCount++;
			} else if (next.equals("two")) {
				twoCount++;
			} else if (next.equals("three")) {
				threeCount++;
			} else if (next.equals("four")) {
				fourCount++;
			}
		}
		assertEquals(1, nullCount);
		assertEquals(1, oneCount);
		assertEquals(1, twoCount);
		assertEquals(1, threeCount);
		assertEquals(1, fourCount);
	}

	public void testToArray() {
		Object[] a = this.set.toArray();
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, null));
		assertTrue(ArrayTools.contains(a, this.one));
		assertTrue(ArrayTools.contains(a, this.two));
		assertTrue(ArrayTools.contains(a, this.three));
		assertTrue(ArrayTools.contains(a, this.four));
	}

	public void testToArrayObjectArray() {
		String[] a = new String[12];
		a[5] = "not null";
		String[] b = this.set.toArray(a);
		assertEquals(a, b);
		assertEquals(12, a.length);
		assertTrue(ArrayTools.contains(a, null));
		assertTrue(ArrayTools.contains(a, this.one));
		assertTrue(ArrayTools.contains(a, this.two));
		assertTrue(ArrayTools.contains(a, this.three));
		assertTrue(ArrayTools.contains(a, this.four));
		assertTrue(a[5] == null);
	}

	public void testToString() {
		String s = this.set.toString();
		assertTrue(s.startsWith("["));
		assertTrue(s.endsWith("]"));
		int commaCount = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				commaCount++;
			}
		}
		assertEquals("invalid number of commas", 4, commaCount);
		assertTrue(s.indexOf("one") != -1);
		assertTrue(s.indexOf("two") != -1);
		assertTrue(s.indexOf("three") != -1);
		assertTrue(s.indexOf("four") != -1);
		assertTrue(s.indexOf("null") != -1);
	}
}
