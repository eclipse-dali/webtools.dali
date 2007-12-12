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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class CollectionToolsTests extends TestCase {

	public CollectionToolsTests(String name) {
		super(name);
	}

	public void testAddObjectArrayObject1() {
		Object[] a = CollectionTools.add(this.buildObjectArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayObject2() {
		String[] a = CollectionTools.add(this.buildStringArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayIntObject1() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject2() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObjectException() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		boolean exCaught = false;
		try {
			a = CollectionTools.add(a, 33, "X");
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddCharArrayChar() {
		char[] a = CollectionTools.add(this.buildCharArray(), 'd');
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, 'd'));
	}

	public void testAddCharArrayIntChar() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.add(a, 2, 'X');
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'X', 'c', 'd' }, a));
	}

	public void testAddIntArrayInt() {
		int[] a = CollectionTools.add(this.buildIntArray(), 30);
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, 30));
	}

	public void testAddIntArrayIntInt() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.add(a, 2, 99);
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 99, 3, 4 }, a));
	}

	public void testAddAllCollectionIterable() {
		List<String> l1 = this.buildStringList1();
		Iterable<String> i2 = this.buildStringList2();
		Set<String> s1 = this.buildStringSet1();
		Iterable<String> i3 = this.buildStringList1(); // same elements as s1

		assertTrue(CollectionTools.addAll(l1, i2.iterator()));
		assertEquals(6, l1.size());
		assertTrue(l1.containsAll(this.buildStringList2()));

		assertFalse(CollectionTools.addAll(s1, i3.iterator()));
		assertEquals(3, s1.size());
		assertTrue(s1.containsAll(this.buildStringList1()));
	}

	public void testAddAllCollectionIterator1() {
		List<String> l1 = this.buildStringList1();
		List<String> l2 = this.buildStringList2();
		Set<String> s1 = this.buildStringSet1();
		List<String> l3 = this.buildStringList1(); // same elements as s1

		assertTrue(CollectionTools.addAll(l1, l2.iterator()));
		assertEquals(6, l1.size());
		assertTrue(l1.containsAll(l2));

		assertFalse(CollectionTools.addAll(s1, l3.iterator()));
		assertEquals(3, s1.size());
		assertTrue(s1.containsAll(l3));
	}

	public void testAddAllCollectionIterator2() {
		List<Object> l1 = this.buildObjectList1();
		List<String> l2 = this.buildStringList2();
		Set<Object> s1 = this.buildObjectSet1();
		List<String> l3 = this.buildStringList1(); // same elements as s1

		assertTrue(CollectionTools.addAll(l1, l2.iterator()));
		assertEquals(6, l1.size());
		assertTrue(l1.containsAll(l2));

		assertFalse(CollectionTools.addAll(s1, l3.iterator()));
		assertEquals(3, s1.size());
		assertTrue(s1.containsAll(l3));
	}

	public void testAddAllCollectionObjectArray1() {
		List<String> l = this.buildStringList1();
		String[] a = this.buildStringArray1();
		Set<String> s = this.buildStringSet1();

		assertTrue(CollectionTools.addAll(l, a));
		assertEquals(6, l.size());
		assertTrue(l.containsAll(CollectionTools.collection(a)));

		assertFalse(CollectionTools.addAll(s, a));
		assertEquals(3, s.size());
		assertTrue(s.containsAll(CollectionTools.collection(a)));
	}

	public void testAddAllCollectionObjectArray2() {
		List<Object> l = this.buildObjectList1();
		String[] a = this.buildStringArray1();
		Set<Object> s = this.buildObjectSet1();

		assertTrue(CollectionTools.addAll(l, a));
		assertEquals(6, l.size());
		assertTrue(l.containsAll(CollectionTools.collection(a)));

		assertFalse(CollectionTools.addAll(s, a));
		assertEquals(3, s.size());
		assertTrue(s.containsAll(CollectionTools.collection(a)));
	}

	public void testAddAllObjectArrayCollection1() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection2() {
		Object[] a = this.buildObjectArray1();
		Collection<String> c = this.buildStringList2();
		Object[] newArray = CollectionTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> i = this.buildStringList1();
		String[] newArray = CollectionTools.addAll(a, i);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator1() {
		String[] a = this.buildStringArray1();
		Iterator<String> i = this.buildStringList1().iterator();
		String[] newArray = CollectionTools.addAll(a, i);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator2() {
		String[] a = this.buildStringArray1();
		Iterator<Object> i = this.buildObjectList1().iterator();
		Object[] newArray = CollectionTools.addAll(a, i);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildObjectList1()));
	}

	public void testAddAllObjectArrayObjectArray1() {
		Object[] a1 = this.buildObjectArray1();
		Object[] a2 = this.buildObjectArray2();
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray2() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = this.buildStringArray2();
		String[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray3() {
		Object[] a1 = this.buildObjectArray1();
		String[] a2 = this.buildStringArray2();
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayIntObjectArray1() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new Object[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray2() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray3() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllCharArrayCharArray() {
		char[] a = CollectionTools.addAll(this.buildCharArray(), new char[] { 'd', 'e' });
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'd'));
		assertTrue(CollectionTools.contains(a, 'e'));
	}

	public void testAddAllCharArrayIntCharArray() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.addAll(a, 2, new char[] { 'X', 'X', 'X' });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'X', 'X', 'X', 'c', 'd' }, a));
	}

	public void testAddAllIntArrayIntArray() {
		int[] a = CollectionTools.addAll(this.buildIntArray(), new int[] { 30, 40 });
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 30));
		assertTrue(CollectionTools.contains(a, 40));
	}

	public void testAddAllIntArrayIntIntArray() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.addAll(a, 2, new int[] { 99, 99, 99 });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 99, 99, 99, 3, 4 }, a));
	}

	public void testArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = CollectionTools.array(iterable);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray1() {
		Iterable<String> iterable = this.buildStringList1();
		String[] a = CollectionTools.array(iterable, new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray2() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = CollectionTools.array(iterable, new Object[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterator() {
		Object[] a = CollectionTools.array(this.buildStringList1().iterator());
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorObjectArray1() {
		String[] a = CollectionTools.array(this.buildStringList1().iterator(), new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorObjectArray2() {
		Object[] a = CollectionTools.array(this.buildStringList1().iterator(), new Object[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testBagEnumeration1() {
		Bag<String> b = CollectionTools.bag(this.buildStringVector1().elements());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringVector1()));
	}

	public void testBagEnumeration2() {
		Bag<Object> b = CollectionTools.<Object>bag(this.buildStringVector1().elements());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringVector1()));
	}

	public void testBagIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Bag<String> b = CollectionTools.bag(iterable);
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIterator1() {
		Bag<String> b = CollectionTools.bag(this.buildStringList1().iterator());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIterator2() {
		Collection<String> c = new ArrayList<String>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		Bag<Object> b = CollectionTools.<Object>bag(c.iterator());
		assertEquals(4, b.size());
		assertTrue(b.containsAll(c));
	}

	public void testBagObjectArray1() {
		Bag<String> b = CollectionTools.bag(this.buildStringArray1());
		assertEquals(3, b.size());
		assertTrue(CollectionTools.containsAll(b, this.buildStringArray1()));
	}

	public void testBagObjectArray2() {
		Bag<String> b = CollectionTools.bag("foo", "bar", "baz");
		assertEquals(3, b.size());
		assertTrue(CollectionTools.containsAll(b, new Object[]{"foo", "bar", "baz"}));
	}

	public void testCollectionEnumeration1() {
		Collection<String> c = CollectionTools.collection(this.buildStringVector1().elements());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringVector1()));
	}

	public void testCollectionEnumeration2() {
		Collection<Object> c = CollectionTools.<Object>collection(this.buildStringVector1().elements());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringVector1()));
	}

	public void testCollectionIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Collection<String> c = CollectionTools.collection(iterable);
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIterator1() {
		Collection<String> c = CollectionTools.collection(this.buildStringList1().iterator());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIterator2() {
		Collection<Object> c = CollectionTools.<Object>collection(this.buildStringList1().iterator());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionObjectArray() {
		Collection<String> c = CollectionTools.collection(this.buildStringArray1());
		assertEquals(3, c.size());
		assertTrue(CollectionTools.containsAll(c, this.buildStringArray1()));
	}

	public void testContainsEnumerationObject1() {
		Vector<String> v = this.buildStringVector1();
		assertTrue(CollectionTools.contains(v.elements(), "one"));
		assertFalse(CollectionTools.contains(v.elements(), null));
		v.add(null);
		assertTrue(CollectionTools.contains(v.elements(), null));
	}

	public void testContainsEnumerationObject2() {
		Vector<Object> c = new Vector<Object>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		String one = "one";
		assertTrue(CollectionTools.contains(c.elements(), one));
		assertFalse(CollectionTools.contains(c.elements(), null));
		c.add(null);
		assertTrue(CollectionTools.contains(c.elements(), null));
	}

	public void testContainsIterableObject() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = c;
		assertTrue(CollectionTools.contains(iterable, "one"));
		assertFalse(CollectionTools.contains(iterable, null));
		c.add(null);
		assertTrue(CollectionTools.contains(iterable, null));
	}

	public void testContainsIteratorObject1() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.contains(c.iterator(), "one"));
		assertFalse(CollectionTools.contains(c.iterator(), null));
		c.add(null);
		assertTrue(CollectionTools.contains(c.iterator(), null));
	}

	public void testContainsIteratorObject2() {
		Collection<Object> c = new HashBag<Object>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		String one = "one";
		assertTrue(CollectionTools.contains(c.iterator(), one));
		assertFalse(CollectionTools.contains(c.iterator(), null));
		c.add(null);
		assertTrue(CollectionTools.contains(c.iterator(), null));
	}

	public void testContainsObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertTrue(CollectionTools.contains(a, "one"));
		assertFalse(CollectionTools.contains(a, null));
		Object[] a2 = CollectionTools.add(a, null);
		assertTrue(CollectionTools.contains(a2, null));
	}

	public void testContainsCharArrayChar() {
		char[] a = this.buildCharArray();
		assertTrue(CollectionTools.contains(a, 'a'));
		assertFalse(CollectionTools.contains(a, 'z'));
		char[] a2 = CollectionTools.add(a, 'z');
		assertTrue(CollectionTools.contains(a2, 'z'));
	}

	public void testContainsIntArrayInt() {
		int[] a = this.buildIntArray();
		assertTrue(CollectionTools.contains(a, 10));
		assertFalse(CollectionTools.contains(a, 55));
		int[] a2 = CollectionTools.add(a, 55);
		assertTrue(CollectionTools.contains(a2, 55));
	}

	public void testContainsAllCollectionIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), iterable));
	}

	public void testContainsAllCollectionIterator1() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), this.buildStringList1().iterator()));
	}

	public void testContainsAllCollectionIterator2() {
		Collection<Object> c1 = new ArrayList<Object>();
		c1.add("zero");
		c1.add("one");
		c1.add("two");
		Collection<String> c2 = new ArrayList<String>();
		c2.add("two");
		c2.add("zero");
		c2.add("one");
		assertTrue(CollectionTools.containsAll(c1, c2.iterator()));
	}

	public void testContainsAllCollectionObjectArray1() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), this.buildObjectArray1()));
	}

	public void testContainsAllCollectionObjectArray2() {
		Object[] a = new Object[] { "zero", "one", "two" };
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), a));
	}

	public void testContainsAllIterableCollection() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildStringList1()));
	}

	public void testContainsAllIterableIterable() {
		Iterable<String> iterable1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable1, iterable2));
	}

	public void testContainsAllIterableIterator() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildStringList1().iterator()));
	}

	public void testContainsAllIterableObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildObjectArray1()));
	}

	public void testContainsAllIteratorCollection1() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList1()));
	}

	public void testContainsAllIteratorCollection2() {
		Collection<Object> c1 = new ArrayList<Object>();
		c1.add("zero");
		c1.add("one");
		c1.add("two");
		Collection<String> c2 = new ArrayList<String>();
		c2.add("zero");
		c2.add("one");
		c2.add("two");
		assertTrue(CollectionTools.containsAll(c1.iterator(), c2));
	}

	public void testContainsAllIteratorIterator1() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList1().iterator()));
	}

	public void testContainsAllIteratorIterator2() {
		Collection<Object> c1 = new ArrayList<Object>();
		c1.add("zero");
		c1.add("one");
		c1.add("two");
		Collection<String> c2 = new ArrayList<String>();
		c2.add("zero");
		c2.add("one");
		c2.add("two");
		assertTrue(CollectionTools.containsAll(c1.iterator(), c2.iterator()));
	}

	public void testContainsAllIteratorObjectArray() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildObjectArray1()));
	}

	public void testContainsAllObjectArrayCollection() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList1()));
	}

	public void testContainsAllObjectArrayIterator() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList1().iterator()));
	}

	public void testContainsAllObjectArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), iterable));
	}

	public void testContainsAllObjectArrayObjectArray() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildObjectArray1()));
	}

	public void testContainsAllCharArrayCharArray() {
		assertTrue(CollectionTools.containsAll(this.buildCharArray(), this.buildCharArray()));
	}

	public void testContainsAllIntArrayIntArray() {
		assertTrue(CollectionTools.containsAll(this.buildIntArray(), this.buildIntArray()));
	}

	public void testDiffEnd() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = new String("a");
		String b_ = new String("b");
		String c_ = new String("c");
		String d_ = new String("d");
		String e_ = new String("e");
		assertTrue((a != a_) && a.equals(a_));
		assertTrue((b != b_) && b.equals(b_));
		assertTrue((c != c_) && c.equals(c_));
		assertTrue((d != d_) && d.equals(d_));
		assertTrue((e != e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(-1, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(-1, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.diffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(4, CollectionTools.diffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(-1, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(2, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, CollectionTools.diffEnd(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(-1, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, CollectionTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(-1, CollectionTools.diffEnd(array1, array2));
	}

	public void testDiffRange() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = a;
		String b_ = b;
		String c_ = c;
		String d_ = d;
		String e_ = e;
		assertTrue((a == a_) && a.equals(a_));
		assertTrue((b == b_) && b.equals(b_));
		assertTrue((c == c_) && c.equals(c_));
		assertTrue((d == d_) && d.equals(d_));
		assertTrue((e == e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(5, -1), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(new Range(1, -1), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.diffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(new Range(0, 4), CollectionTools.diffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(new Range(0, -1), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(new Range(0, 2), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(new Range(0, 0), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(new Range(3, 3), CollectionTools.diffRange(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(new Range(5, -1), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(2, 2), CollectionTools.diffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(new Range(5, -1), CollectionTools.diffRange(array1, array2));
	}

	public void testDiffStart() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = new String("a");
		String b_ = new String("b");
		String c_ = new String("c");
		String d_ = new String("d");
		String e_ = new String("e");
		assertTrue((a != a_) && a.equals(a_));
		assertTrue((b != b_) && b.equals(b_));
		assertTrue((c != c_) && c.equals(c_));
		assertTrue((d != d_) && d.equals(d_));
		assertTrue((e != e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(5, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(1, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(4, CollectionTools.diffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(0, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(0, CollectionTools.diffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(0, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, e_, c_, d_ };
		assertEquals(2, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, CollectionTools.diffStart(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c__));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(5, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, CollectionTools.diffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(5, CollectionTools.diffStart(array1, array2));
	}

	public void testEqualsListIteratorListIterator() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < list1.size(); i++) {
			list2.add(String.valueOf((i + 1) * 1000));
		}
		assertFalse(CollectionTools.identical(list1.listIterator(), list2.listIterator()));
		assertTrue(CollectionTools.equals(list1.listIterator(), list2.listIterator()));
	}

	public void testGetListIteratorInt() {
		List<String> list = this.buildStringList1();
		String o = CollectionTools.get(list.listIterator(), 1);
		assertEquals("one", o);
		list.add(null);
		o = CollectionTools.get(list.listIterator(), list.size() - 1);
		assertNull(o);

		boolean exCaught = false;
		try {
			CollectionTools.get(list.listIterator(), list.size());
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIdenticalObjectArrayObjectArray() {
		Object[] a1 = new Object[4];
		for (int i = 0; i < a1.length; i++) {
			a1[i] = String.valueOf(i * 1000);
		}

		Object[] a2 = new Object[a1.length];
		for (int i = 0; i < a2.length; i++) {
			a2[i] = a1[i];
		}

		assertTrue(CollectionTools.identical(a1, a2));
		a2[2] = "2000";
		assertFalse(CollectionTools.identical(a1, a2));
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testIdenticalListIteratorListIterator() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<String> list2 = new ArrayList<String>();
		for (String s : list1) {
			list2.add(s);
		}
		assertTrue(CollectionTools.identical(list1.listIterator(), list2.listIterator()));
		assertTrue(CollectionTools.equals(list1.listIterator(), list2.listIterator()));
	}

	public void testIdentityDiffEnd() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = a;
		String b_ = b;
		String c_ = c;
		String d_ = d;
		String e_ = e;
		assertTrue((a == a_) && a.equals(a_));
		assertTrue((b == b_) && b.equals(b_));
		assertTrue((c == c_) && c.equals(c_));
		assertTrue((d == d_) && d.equals(d_));
		assertTrue((e == e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(-1, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(-1, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(4, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(-1, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(2, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, CollectionTools.identityDiffEnd(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(2, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, CollectionTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(-1, CollectionTools.identityDiffEnd(array1, array2));
	}

	public void testIdentityDiffRange() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = a;
		String b_ = b;
		String c_ = c;
		String d_ = d;
		String e_ = e;
		assertTrue((a == a_) && a.equals(a_));
		assertTrue((b == b_) && b.equals(b_));
		assertTrue((c == c_) && c.equals(c_));
		assertTrue((d == d_) && d.equals(d_));
		assertTrue((e == e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(5, -1), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(new Range(1, -1), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(new Range(0, 4), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(new Range(0, -1), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(new Range(0, 2), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(new Range(0, 0), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(new Range(3, 3), CollectionTools.identityDiffRange(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(new Range(2, 2), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(2, 2), CollectionTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(new Range(5, -1), CollectionTools.identityDiffRange(array1, array2));
	}

	public void testIdentityDiffStart() {
		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String a_ = a;
		String b_ = b;
		String c_ = c;
		String d_ = d;
		String e_ = e;
		assertTrue((a == a_) && a.equals(a_));
		assertTrue((b == b_) && b.equals(b_));
		assertTrue((c == c_) && c.equals(c_));
		assertTrue((d == d_) && d.equals(d_));
		assertTrue((e == e_) && e.equals(e_));
		String[] array1;
		String[] array2;

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(5, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(1, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(4, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(0, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(0, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(0, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, e_, c_, d_ };
		assertEquals(2, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, CollectionTools.identityDiffStart(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(2, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, CollectionTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(5, CollectionTools.identityDiffStart(array1, array2));
	}

	public void testIndexOfListIteratorObject1() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.indexOf(list.listIterator(), "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.indexOf(list.listIterator(), null));
	}

	public void testIndexOfListIteratorObject2() {
		List<Object> list = new ArrayList<Object>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");

		String one = "1";
		assertEquals(1, CollectionTools.indexOf(list.listIterator(), one));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.indexOf(list.listIterator(), null));
	}

	public void testIndexOfObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertEquals(1, CollectionTools.indexOf(a, "one"));
		a = CollectionTools.add(a, null);
		assertEquals(a.length - 1, CollectionTools.indexOf(a, null));
	}

	public void testIndexOfCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(1, CollectionTools.indexOf(a, 'b'));
		a = CollectionTools.add(a, 'd');
		assertEquals(a.length - 1, CollectionTools.indexOf(a, 'd'));
	}

	public void testIndexOfIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(1, CollectionTools.indexOf(a, 10));
		a = CollectionTools.add(a, 30);
		assertEquals(a.length - 1, CollectionTools.indexOf(a, 30));
	}

	public void testInsertionIndexOfListComparable() {
		List<String> list = Arrays.asList(new String[] { "A", "C", "D" });
		assertEquals(1, CollectionTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "C", "D" });
		assertEquals(2, CollectionTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" });
		assertEquals(4, CollectionTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" });
		assertEquals(6, CollectionTools.insertionIndexOf(list, "E"));

		list = Arrays.asList(new String[] { "B", "B", "B", "C", "D" });
		assertEquals(0, CollectionTools.insertionIndexOf(list, "A"));

		list = Arrays.asList(new String[] { "A", "A", "B", "B", "C", "D" });
		assertEquals(2, CollectionTools.insertionIndexOf(list, "A"));
	}

	public void testInsertionIndexOfListObjectComparator() {
		Comparator<String> c = new ReverseComparator<String>();
		List<String> list = Arrays.asList(new String[] { "D", "C", "A" });
		assertEquals(2, CollectionTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "A" });
		assertEquals(3, CollectionTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" });
		assertEquals(5, CollectionTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" });
		assertEquals(0, CollectionTools.insertionIndexOf(list, "E", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B" });
		assertEquals(5, CollectionTools.insertionIndexOf(list, "A", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "A", "A" });
		assertEquals(6, CollectionTools.insertionIndexOf(list, "A", c));
	}

	public void testInsertionIndexOfObjectArrayComparable() {
		String[] a = new String[] { "A", "C", "D" };
		assertEquals(1, CollectionTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "C", "D" };
		assertEquals(2, CollectionTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "B", "B", "C", "D" };
		assertEquals(4, CollectionTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "B", "B", "C", "D" };
		assertEquals(6, CollectionTools.insertionIndexOf(a, "E"));

		a = new String[] { "B", "B", "B", "C", "D" };
		assertEquals(0, CollectionTools.insertionIndexOf(a, "A"));

		a = new String[] { "A", "A", "B", "B", "C", "D" };
		assertEquals(2, CollectionTools.insertionIndexOf(a, "A"));
	}

	public void testInsertionIndexOfObjectArrayObjectComparator() {
		Comparator<String> c = new ReverseComparator<String>();
		String[] a = new String[] { "D", "C", "A" };
		assertEquals(2, CollectionTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "A" };
		assertEquals(3, CollectionTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "B", "B", "A" };
		assertEquals(5, CollectionTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "B", "B", "A" };
		assertEquals(0, CollectionTools.insertionIndexOf(a, "E", c));

		a = new String[] { "D", "C", "B", "B", "B" };
		assertEquals(5, CollectionTools.insertionIndexOf(a, "A", c));

		a = new String[] { "D", "C", "B", "B", "A", "A" };
		assertEquals(6, CollectionTools.insertionIndexOf(a, "A", c));
	}

	public void testIterableIterator() {
		Iterator<Object> emptyIterator = EmptyIterator.instance();
		Iterable<Object> emptyIterable = CollectionTools.iterable(emptyIterator);
		
		assertEquals(emptyIterator, emptyIterable.iterator());
		
		boolean exceptionThrown = false;
		try {
			emptyIterator = emptyIterable.iterator();
			fail("invalid iterator: " + emptyIterator);
		} catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		assertTrue("IllegalStateException not thrown.", exceptionThrown);
		
	}

	public void testIterableObjectArray() {
		String[] strings = this.buildStringArray1();
		int i = 0;
		for (String string : CollectionTools.iterable(strings)) {
			assertEquals(strings[i++], string);
		}
	}

	public void testIteratorObjectArray() {
		String[] a = this.buildStringArray1();
		int i = 0;
		for (Iterator<String> stream = CollectionTools.iterator(a); stream.hasNext(); i++) {
			assertEquals(a[i], stream.next());
		}
	}

	public void testLastIndexOfListIteratorObject() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.lastIndexOf(list.listIterator(), "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(list.listIterator(), null));
	}

	public void testLastIndexOfObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertEquals(1, CollectionTools.lastIndexOf(a, "one"));
		a = CollectionTools.add(a, null);
		assertEquals(a.length - 1, CollectionTools.lastIndexOf(a, null));
	}

	public void testLastIndexOfCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(1, CollectionTools.lastIndexOf(a, 'b'));
		a = CollectionTools.add(a, 'd');
		assertEquals(a.length - 1, CollectionTools.lastIndexOf(a, 'd'));
	}

	public void testLastIndexOfIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(1, CollectionTools.lastIndexOf(a, 10));
		a = CollectionTools.add(a, 30);
		assertEquals(a.length - 1, CollectionTools.lastIndexOf(a, 30));
	}

	public void testListIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(this.buildStringList1(), CollectionTools.list(iterable));
	}

	public void testListIterator1() {
		List<String> list = CollectionTools.list(this.buildStringList1().iterator());
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIterator2() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<Object> list2 = CollectionTools.<Object>list(list1.iterator());
		assertEquals(list1, list2);
	}

	public void testListObjectArray() {
		List<String> list = CollectionTools.list(this.buildStringArray1());
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIteratorObjectArray() {
		String[] a = this.buildStringArray1();
		int i = 0;
		for (ListIterator<String> stream = CollectionTools.listIterator(a); stream.hasNext(); i++) {
			assertEquals(a[i], stream.next());
		}
	}

	public void testListIteratorObjectArrayInt() {
		String[] a = this.buildStringArray1();
		int i = 1;
		for (ListIterator<String> stream = CollectionTools.listIterator(a, 1); stream.hasNext(); i++) {
			assertEquals(a[i], stream.next());
		}
	}

	public void testMaxCharArray() {
		assertEquals('c', CollectionTools.max(this.buildCharArray()));
	}

	public void testMaxIntArray() {
		assertEquals(20, CollectionTools.max(this.buildIntArray()));
	}

	public void testMinCharArray() {
		assertEquals('a', CollectionTools.min(this.buildCharArray()));
	}

	public void testMinIntArray() {
		assertEquals(0, CollectionTools.min(this.buildIntArray()));
	}

	public void testMoveObjectArrayIntInt() {
		String[] array = new String[] { "0", "1", "2", "3", "4", "5" };

		String[] result = CollectionTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result));

		result = CollectionTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result));

		result = CollectionTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result));
	}

	public void testMoveObjectArrayIntIntInt() {
		String[] array = new String[] { "0", "1", "2", "3", "4", "5" };

		String[] result = CollectionTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result));

		result = CollectionTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result));

		result = CollectionTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result));

		result = CollectionTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));

		result = CollectionTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result));

		result = CollectionTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));
	}

	public void testMoveIntArrayIntInt() {
		int[] array = new int[] { 0, 1, 2, 3, 4, 5 };

		int[] result = CollectionTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 1, 3, 4, 2, 5 }, result));

		result = CollectionTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 1, 3, 4, 2 }, result));

		result = CollectionTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));
	}

	public void testMoveInttArrayIntIntInt() {
		int[] array = new int[] { 0, 1, 2, 3, 4, 5 };

		int[] result = CollectionTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 1, 3, 4, 2, 5 }, result));

		result = CollectionTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 1, 3, 4, 2 }, result));

		result = CollectionTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));

		result = CollectionTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));

		result = CollectionTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 3, 2, 4, 5, 1 }, result));

		result = CollectionTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));
	}

	public void testMoveCharArrayIntInt() {
		char[] array = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };

		char[] result = CollectionTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'd', 'e', 'c', 'f' }, result));

		result = CollectionTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'f', 'a', 'b', 'd', 'e', 'c' }, result));

		result = CollectionTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'f', 'a', 'e', 'b', 'd', 'c' }, result));
	}

	public void testMoveCharArrayIntIntInt() {
		char[] array = new char[] { 'a', 'b', 'b', 'c', 'd', 'e' };

		char[] result = CollectionTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'b', 'e' }, result));

		result = CollectionTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'b', 'c', 'd', 'b' }, result));

		result = CollectionTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'd', 'b', 'c', 'b' }, result));

		result = CollectionTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));

		result = CollectionTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'c', 'b', 'd', 'e', 'b' }, result));

		result = CollectionTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));
	}

	public void testMoveListIntIntRandomAccess() {
		List<String> list = new ArrayList<String>();
		CollectionTools.addAll(list, new String[] { "0", "1", "2", "3", "4", "5" });

		List<String> result = CollectionTools.move(list, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = CollectionTools.move(list, 0, 5);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));
	}

	public void testMoveListIntIntSequentialAccess() {
		List<String> list = new LinkedList<String>();
		CollectionTools.addAll(list, new String[] { "0", "1", "2", "3", "4", "5" });

		List<String> result = CollectionTools.move(list, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = CollectionTools.move(list, 0, 5);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));
	}

	public void testMoveListIntIntIntRandomAccess() {
		List<String> list = new ArrayList<String>(Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5" }));

		List<String> result = CollectionTools.move(list, 4, 2, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = CollectionTools.move(list, 0, 5, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = CollectionTools.move(list, 0, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result.toArray()));

		result = CollectionTools.move(list, 1, 0, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));
	}

	public void testMoveListIntIntIntSequentialAccess() {
		List<String> list = new LinkedList<String>(Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5" }));

		List<String> result = CollectionTools.move(list, 4, 2, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = CollectionTools.move(list, 0, 5, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = CollectionTools.move(list, 2, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = CollectionTools.move(list, 0, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result.toArray()));

		result = CollectionTools.move(list, 1, 0, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));
	}

	public void testRemoveAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[] { "E", "B" };
		String[] a3 = CollectionTools.removeAll(a1, a2);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'A', 'A', 'C', 'C', 'D', 'D', 'F', 'F' }, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 1, 1, 3, 3, 4, 4, 6, 6 }, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveObjectArrayObject1() {
		Object[] a = this.buildObjectArray1();
		a = CollectionTools.add(a, "three");
		a = CollectionTools.add(a, "four");
		a = CollectionTools.add(a, "five");

		assertEquals(6, a.length);
		assertTrue(CollectionTools.contains(a, "three"));
		a = CollectionTools.remove(a, "three");
		assertEquals(5, a.length);
		assertFalse(CollectionTools.contains(a, "three"));
		assertTrue(CollectionTools.contains(a, "four"));
		assertTrue(CollectionTools.contains(a, "five"));
	}

	public void testRemoveObjectArrayObject2() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.add(a, "three");
		a = CollectionTools.add(a, "four");
		a = CollectionTools.add(a, "five");

		assertEquals(6, a.length);
		assertTrue(CollectionTools.contains(a, "three"));
		a = CollectionTools.remove(a, "three");
		assertEquals(5, a.length);
		assertFalse(CollectionTools.contains(a, "three"));
		assertTrue(CollectionTools.contains(a, "four"));
		assertTrue(CollectionTools.contains(a, "five"));
	}

	public void testRemoveCharArrayChar() {
		char[] a = this.buildCharArray();
		a = CollectionTools.add(a, 'd');
		a = CollectionTools.add(a, 'e');
		a = CollectionTools.add(a, 'f');

		assertEquals(6, a.length);
		assertTrue(CollectionTools.contains(a, 'd'));
		a = CollectionTools.remove(a, 'd');
		assertEquals(5, a.length);
		assertFalse(CollectionTools.contains(a, 'd'));
		assertTrue(CollectionTools.contains(a, 'e'));
		assertTrue(CollectionTools.contains(a, 'f'));
	}

	public void testRemoveIntArrayInt() {
		int[] a = this.buildIntArray();
		a = CollectionTools.add(a, 30);
		a = CollectionTools.add(a, 40);
		a = CollectionTools.add(a, 50);

		assertEquals(6, a.length);
		assertTrue(CollectionTools.contains(a, 30));
		a = CollectionTools.remove(a, 30);
		assertEquals(5, a.length);
		assertFalse(CollectionTools.contains(a, 30));
		assertTrue(CollectionTools.contains(a, 40));
		assertTrue(CollectionTools.contains(a, 50));
	}

	public void testRemoveAllCollectionIterable() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, iterable));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.removeAll(c, iterable));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionIterator1() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, this.buildStringList1().iterator()));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, this.buildStringList2().iterator()));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionIterator2() {
		Collection<String> c = new ArrayList<String>();
		c.add("a");
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("d");
		String[] a = new String[] { "a", "d" };
		Iterator<String> iterator = new ArrayIterator<String>(a);
		assertTrue(CollectionTools.removeAll(c, iterator));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		iterator = new ArrayIterator<String>(a);
		assertFalse(CollectionTools.removeAll(c, iterator));
	}

	public void testRemoveAllCollectionIterator3() {
		Collection<Object> c = new ArrayList<Object>();
		c.add("a");
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("d");
		String[] a = new String[] { "a", "d" };
		Iterator<String> iterator = new ArrayIterator<String>(a);
		assertTrue(CollectionTools.removeAll(c, iterator));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		iterator = new ArrayIterator<String>(a);
		assertFalse(CollectionTools.removeAll(c, iterator));
	}

	public void testRemoveAllCollectionObjectArray1() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, this.buildObjectArray1()));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, this.buildObjectArray2()));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionObjectArray2() {
		Collection<String> c = new ArrayList<String>();
		c.add("a");
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("d");
		String[] a = new String[] { "a", "d" };
		assertTrue(CollectionTools.removeAll(c, a));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		assertFalse(CollectionTools.removeAll(c, a));
	}

	public void testRemoveAllCollectionObjectArray3() {
		Collection<String> c = new ArrayList<String>();
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("a");
		c.add("d");
		c.add("d");
		c.add("a");
		c.add("c");
		String[] a = new String[] { "a", "d" };
		assertTrue(CollectionTools.removeAll(c, a));
		assertEquals(3, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		assertFalse(CollectionTools.removeAll(c, a));
	}

	public void testRemoveAllOccurrencesCollectionObject() {
		Collection<String> c = this.buildStringList1();
		assertEquals(3, c.size());
		assertFalse(CollectionTools.removeAllOccurrences(c, "three"));
		assertTrue(CollectionTools.removeAllOccurrences(c, "two"));
		assertFalse(CollectionTools.removeAllOccurrences(c, "two"));
		assertEquals(2, c.size());

		c.add("five");
		c.add("five");
		c.add("five");
		assertEquals(5, c.size());
		assertTrue(CollectionTools.removeAllOccurrences(c, "five"));
		assertFalse(CollectionTools.removeAllOccurrences(c, "five"));
		assertEquals(2, c.size());

		c.add(null);
		c.add(null);
		c.add(null);
		assertEquals(5, c.size());
		assertTrue(CollectionTools.removeAllOccurrences(c, null));
		assertFalse(CollectionTools.removeAllOccurrences(c, null));
		assertEquals(2, c.size());
	}

	public void testRemoveAllOccurrencesObjectArrayObject() {
		String[] a = this.buildStringArray1();
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, "three");
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, "two");
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, "two");
		assertEquals(2, a.length);

		a = CollectionTools.add(a, "five");
		a = CollectionTools.add(a, "five");
		a = CollectionTools.add(a, "five");
		assertEquals(5, a.length);
		a = CollectionTools.removeAllOccurrences(a, "five");
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, "five");
		assertEquals(2, a.length);

		a = CollectionTools.add(a, null);
		a = CollectionTools.add(a, null);
		a = CollectionTools.add(a, null);
		assertEquals(5, a.length);
		a = CollectionTools.removeAllOccurrences(a, null);
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, null);
		assertEquals(2, a.length);
	}

	public void testRemoveAllOccurrencesCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, 'd');
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, 'b');
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, 'b');
		assertEquals(2, a.length);

		a = CollectionTools.add(a, 'g');
		a = CollectionTools.add(a, 'g');
		a = CollectionTools.add(a, 'g');
		assertEquals(5, a.length);
		a = CollectionTools.removeAllOccurrences(a, 'g');
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, 'g');
		assertEquals(2, a.length);
	}

	public void testRemoveAllOccurrencesIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, 55);
		assertEquals(3, a.length);
		a = CollectionTools.removeAllOccurrences(a, 10);
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, 10);
		assertEquals(2, a.length);

		a = CollectionTools.add(a, 77);
		a = CollectionTools.add(a, 77);
		a = CollectionTools.add(a, 77);
		assertEquals(5, a.length);
		a = CollectionTools.removeAllOccurrences(a, 77);
		assertEquals(2, a.length);
		a = CollectionTools.removeAllOccurrences(a, 77);
		assertEquals(2, a.length);
	}

	public void testRemoveElementAtIndexObjectArrayInt() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.removeElementAtIndex(a, 3);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "A", "D" }, a));
	}

	public void testRemoveElementAtIndexCharArrayInt() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.removeElementAtIndex(a, 3);
		assertTrue(Arrays.equals(new char[] { 'A', 'B', 'A', 'A', 'D' }, a));
	}

	public void testRemoveElementAtIndexIntArrayInt() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = CollectionTools.removeElementsAtIndex(a, 3, 3);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7 }, a));
	}

	public void testRemoveElementsAtIndexListIntInt() {
		List<String> list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "A", "C", "A", "D" }));
		List<String> removed = CollectionTools.removeElementsAtIndex(list, 3, 2);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "D" }, list.toArray()));
		assertTrue(Arrays.equals(new String[] { "C", "A" }, removed.toArray()));

		list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" }));
		removed = CollectionTools.removeElementsAtIndex(list, 3, 3);
		assertTrue(Arrays.equals(new String[] { "A", "B", "C" }, list.toArray()));
		assertTrue(Arrays.equals(new String[] { "D", "E", "F" }, removed.toArray()));

		list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" }));
		removed = CollectionTools.removeElementsAtIndex(list, 0, 3);
		assertTrue(Arrays.equals(new String[] { "D", "E", "F" }, list.toArray()));
		assertTrue(Arrays.equals(new String[] { "A", "B", "C" }, removed.toArray()));
	}

	public void testRemoveElementsAtIndexObjectArrayIntInt() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.removeElementsAtIndex(a, 3, 2);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "D" }, a));
	}

	public void testRemoveElementsAtIndexCharArrayIntInt() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.removeElementsAtIndex(a, 0, 5);
		assertTrue(Arrays.equals(new char[] { 'D' }, a));
	}

	public void testRemoveElementsAtIndexIntArrayIntInt() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = CollectionTools.removeElementAtIndex(a, 3);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7, 2, 11 }, a));
	}

	public void testReplaceAllObjectArray1() {
		Object[] a = new Object[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new Object[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllObjectArray2() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new String[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllCharArray() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.replaceAll(a, 'A', 'Z');
		assertTrue(Arrays.equals(new char[] { 'Z', 'B', 'Z', 'C', 'Z', 'D' }, a));
	}

	public void testReplaceAllIntArray() {
		int[] a = new int[] { 0, 1, 0, 7, 0, 99 };
		a = CollectionTools.replaceAll(a, 0, 13);
		assertTrue(Arrays.equals(new int[] { 13, 1, 13, 7, 13, 99 }, a));
	}

	public void testRetainAllCollectionIterable() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = this.buildStringList1();
		assertFalse(CollectionTools.retainAll(c, iterable));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		iterable = this.buildStringList2();
		assertTrue(CollectionTools.retainAll(c, iterable));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));
	}

	public void testRetainAllCollectionIterator1() {
		Collection<String> c = this.buildStringList1();
		assertFalse(CollectionTools.retainAll(c, this.buildStringList1().iterator()));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		assertTrue(CollectionTools.retainAll(c, this.buildStringList2().iterator()));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));
	}

	public void testRetainAllCollectionIterator2() {
		Collection<Object> c1 = new ArrayList<Object>();
		c1.add("zero");
		c1.add("one");
		c1.add("two");
		
		Collection<String> c2 = new ArrayList<String>();
		c2.add("zero");
		c2.add("one");
		c2.add("two");
		
		assertFalse(CollectionTools.retainAll(c1, c2.iterator()));
		assertEquals(c2.size(), c1.size());
		assertEquals(c2, c1);

		Collection<String> c3 = new ArrayList<String>();
		c3.add("three");
		c3.add("four");
		c3.add("five");
		
		assertTrue(CollectionTools.retainAll(c1, c3.iterator()));
		assertEquals(0, c1.size());
		assertFalse(c1.contains("one"));
		assertFalse(c1.contains("two"));
		assertFalse(c1.contains("three"));
	}

	public void testRetainAllCollectionObjectArray() {
		Collection<String> c = this.buildStringList1();
		assertFalse(CollectionTools.retainAll(c, this.buildObjectArray1()));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		assertTrue(CollectionTools.retainAll(c, this.buildObjectArray2()));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));
	}

	public void testRetainAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Object[] a2 = new Object[] { "E", "B", new Integer(7) };
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, a2)));
	}

	public void testRetainAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'B', 'B', 'E', 'E' }, CollectionTools.retainAll(a1, a2)));
	}

	public void testRetainAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 2, 2, 5, 5 }, CollectionTools.retainAll(a1, a2)));
	}

	public void testReverseObjectArray1() {
		Object[] a = this.buildObjectArray1();
		a = CollectionTools.reverse(a);
		assertEquals("two", a[0]);
		assertEquals("one", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testReverseObjectArray2() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.reverse(a);
		assertEquals("two", a[0]);
		assertEquals("one", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testReverseCharArray() {
		char[] a = this.buildCharArray();
		a = CollectionTools.reverse(a);
		assertEquals('c', a[0]);
		assertEquals('b', a[1]);
		assertEquals('a', a[2]);
	}

	public void testReverseIntArray() {
		int[] a = this.buildIntArray();
		a = CollectionTools.reverse(a);
		assertEquals(20, a[0]);
		assertEquals(10, a[1]);
		assertEquals(0, a[2]);
	}

	public void testReverseListIterable() {
		Iterable<String> iterable = this.buildStringList1();
		List<String> actual = CollectionTools.reverseList(iterable);
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIterator1() {
		List<String> actual = CollectionTools.reverseList(this.buildStringList1().iterator());
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIterator2() {
		List<Object> actual = CollectionTools.<Object>reverseList(this.buildStringList1().iterator());
		List<Object> expected = this.buildObjectList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testRotateObjectArray() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.rotate(a);
		assertEquals("two", a[0]);
		assertEquals("zero", a[1]);
		assertEquals("one", a[2]);
	}

	public void testRotateObjectArrayInt() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.rotate(a, 2);
		assertEquals("one", a[0]);
		assertEquals("two", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testRotateCharArray() {
		char[] a = this.buildCharArray();
		a = CollectionTools.rotate(a);
		assertEquals('c', a[0]);
		assertEquals('a', a[1]);
		assertEquals('b', a[2]);
	}

	public void testRotateCharArrayInt() {
		char[] a = this.buildCharArray();
		a = CollectionTools.rotate(a, 2);
		assertEquals('b', a[0]);
		assertEquals('c', a[1]);
		assertEquals('a', a[2]);
	}

	public void testRotateIntArray() {
		int[] a = this.buildIntArray();
		a = CollectionTools.rotate(a);
		assertEquals(20, a[0]);
		assertEquals(0, a[1]);
		assertEquals(10, a[2]);
	}

	public void testRotateIntArrayInt() {
		int[] a = this.buildIntArray();
		a = CollectionTools.rotate(a, 2);
		assertEquals(10, a[0]);
		assertEquals(20, a[1]);
		assertEquals(0, a[2]);
	}

	public void testSetIterable() {
		Iterable<String> iterable = this.buildStringSet1();
		assertEquals(this.buildStringSet1(), CollectionTools.set(iterable));
	}

	public void testSetIterator1() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().iterator()));
	}

	public void testSetIterator2() {
		Set<String> set1 = new HashSet<String>();
		set1.add("0");
		set1.add("1");
		set1.add("2");
		set1.add("3");

		Set<Object> set2 = CollectionTools.<Object>set(set1.iterator());
		assertEquals(set1, set2);
	}

	public void testSetObjectArray() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().toArray()));
	}

	public void testShuffleObjectArray() {
		String[] array1 = this.buildStringArray1();
		String[] array2 = CollectionTools.shuffle(this.buildStringArray1());
		assertEquals(array1.length, array2.length);
		assertTrue(CollectionTools.containsAll(array1, array2));
	}

	public void testShuffleCharArray() {
		char[] array1 = this.buildCharArray();
		char[] array2 = CollectionTools.shuffle(this.buildCharArray());
		assertEquals(array1.length, array2.length);
		assertTrue(CollectionTools.containsAll(array1, array2));
	}

	public void testShuffleIntArray() {
		int[] array1 = this.buildIntArray();
		int[] array2 = CollectionTools.shuffle(this.buildIntArray());
		assertEquals(array1.length, array2.length);
		assertTrue(CollectionTools.containsAll(array1, array2));
	}

	public void testSingletonIterator1() {
		Iterator<String> stream = CollectionTools.singletonIterator("foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSingletonIterator2() {
		Iterator<Object> stream = CollectionTools.<Object>singletonIterator("foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSingletonIterator3() {
		Iterator<Object> stream = CollectionTools.singletonIterator((Object) "foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSizeIterable() {
		Iterable<Object> iterable = this.buildObjectList1();
		assertEquals(3, CollectionTools.size(iterable));
	}

	public void testSizeIterator() {
		assertEquals(3, CollectionTools.size(this.buildObjectList1().iterator()));
	}

	public void testSortedSetIterable() {
		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.add("0");
		ss1.add("2");
		ss1.add("3");
		ss1.add("1");

		Iterable<String> iterable = ss1;
		SortedSet<String> set2 = CollectionTools.<String>sortedSet(iterable);
		assertEquals(ss1, set2);
	}

	public void testSortedSetIterator1() {
		assertEquals(this.buildSortedStringSet1(), CollectionTools.sortedSet(this.buildSortedStringSet1().iterator()));
	}

	public void testSortedSetIterator2() {
		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.add("0");
		ss1.add("2");
		ss1.add("3");
		ss1.add("1");

		SortedSet<String> set2 = CollectionTools.<String>sortedSet(ss1.iterator());
		assertEquals(ss1, set2);
	}

	public void testSortedSetObjectArray() {
		assertEquals(this.buildSortedStringSet1(), CollectionTools.set(this.buildSortedStringSet1().toArray()));
	}

	public void testSubArrayObjectArrayIntInt() {
		String[] array = new String[] {"foo", "bar", "baz", "joo", "jar", "jaz"};
		String[] result = new String[] {"foo", "bar", "baz", "joo"};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 0, 4)));

		result = new String[] {"jar"};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 4, 1)));

		result = new String[0];
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 5, 0)));

		result = new String[] {"joo", "jar", "jaz"};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 3, 3)));
	}

	public void testSubArrayIntArrayIntInt() {
		int[] array = new int[] {77, 99, 333, 4, 9090, 42};
		int[] result = new int[] {77, 99, 333, 4};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 0, 4)));

		result = new int[] {9090};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 4, 1)));

		result = new int[0];
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 5, 0)));

		result = new int[] {4, 9090, 42};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 3, 3)));
	}

	public void testSubArrayCharArrayIntInt() {
		char[] array = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
		char[] result = new char[] {'a', 'b', 'c', 'd'};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 0, 4)));

		result = new char[] {'e'};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 4, 1)));

		result = new char[0];
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 5, 0)));

		result = new char[] {'d', 'e', 'f'};
		assertTrue(Arrays.equals(result, CollectionTools.subArray(array, 3, 3)));
	}

	public void testSwapObjectArray() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.swap(a, 1, 2);
		assertEquals("zero", a[0]);
		assertEquals("two", a[1]);
		assertEquals("one", a[2]);
	}

	public void testSwapCharArray() {
		char[] a = this.buildCharArray();
		a = CollectionTools.swap(a, 1, 2);
		assertEquals('a', a[0]);
		assertEquals('c', a[1]);
		assertEquals('b', a[2]);
	}

	public void testSwapIntArray() {
		int[] a = this.buildIntArray();
		a = CollectionTools.swap(a, 1, 2);
		assertEquals(0, a[0]);
		assertEquals(20, a[1]);
		assertEquals(10, a[2]);
	}

	public void testRemoveDuplicateElementsList() {
		List<String> list = this.buildStringVector1();
		list.add("zero");
		list.add("zero");
		list.add("two");
		list.add("zero");
		list = CollectionTools.removeDuplicateElements(list);
		int i = 0;
		assertEquals("zero", list.get(i++));
		assertEquals("one", list.get(i++));
		assertEquals("two", list.get(i++));
		assertEquals(i, list.size());
	}

	public void testRemoveDuplicateElementsObjectArray1() {
		List<String> list = this.buildStringVector1();
		list.add("zero");
		list.add("zero");
		list.add("two");
		list.add("zero");
		String[] array = CollectionTools.removeDuplicateElements(list.toArray(new String[list.size()]));
		int i = 0;
		assertEquals("zero", array[i++]);
		assertEquals("one", array[i++]);
		assertEquals("two", array[i++]);
		assertEquals(i, array.length);
	}

	public void testRemoveDuplicateElementsObjectArray2() {
		List<String> list = this.buildStringVector1();
		list.add("zero");
		list.add("zero");
		list.add("two");
		list.add("zero");
		String[] array = CollectionTools.removeDuplicateElements(list.toArray(new String[list.size()]));
		int i = 0;
		assertEquals("zero", array[i++]);
		assertEquals("one", array[i++]);
		assertEquals("two", array[i++]);
		assertEquals(i, array.length);
	}

	public void testVectorIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Vector<String> v = CollectionTools.vector(iterable);
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIterator1() {
		Vector<String> v = CollectionTools.vector(this.buildStringList1().iterator());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIterator2() {
		Vector<Object> v = CollectionTools.<Object>vector(this.buildStringList1().iterator());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorObjectArray() {
		Vector<String> v = CollectionTools.vector(this.buildStringArray1());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	private Object[] buildObjectArray1() {
		return new Object[] { "zero", "one", "two" };
	}

	private String[] buildStringArray1() {
		return new String[] { "zero", "one", "two" };
	}

	private char[] buildCharArray() {
		return new char[] { 'a', 'b', 'c' };
	}

	private int[] buildIntArray() {
		return new int[] { 0, 10, 20 };
	}

	private Object[] buildObjectArray2() {
		return new Object[] { "three", "four", "five" };
	}

	private String[] buildStringArray2() {
		return new String[] { "three", "four", "five" };
	}

	private Vector<String> buildStringVector1() {
		Vector<String> v = new Vector<String>();
		this.addToCollection1(v);
		return v;
	}

	private List<String> buildStringList1() {
		List<String> l = new ArrayList<String>();
		this.addToCollection1(l);
		return l;
	}

	private List<Object> buildObjectList1() {
		List<Object> l = new ArrayList<Object>();
		this.addToCollection1(l);
		return l;
	}

	private void addToCollection1(Collection<? super String> c) {
		c.add("zero");
		c.add("one");
		c.add("two");
	}

	private List<String> buildStringList2() {
		List<String> l = new ArrayList<String>();
		this.addToCollection2(l);
		return l;
	}

	private void addToCollection2(Collection<? super String> c) {
		c.add("three");
		c.add("four");
		c.add("five");
	}

	private Set<String> buildStringSet1() {
		Set<String> s = new HashSet<String>();
		this.addToCollection1(s);
		return s;
	}

	private Set<Object> buildObjectSet1() {
		Set<Object> s = new HashSet<Object>();
		this.addToCollection1(s);
		return s;
	}

	private SortedSet<String> buildSortedStringSet1() {
		SortedSet<String> s = new TreeSet<String>();
		this.addToCollection1(s);
		return s;
	}

}
