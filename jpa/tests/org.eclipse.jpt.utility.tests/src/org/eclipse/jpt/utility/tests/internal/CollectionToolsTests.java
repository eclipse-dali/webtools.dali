/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.lang.reflect.InvocationTargetException;
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
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.enumerations.EmptyEnumeration;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

@SuppressWarnings("nls")
public class CollectionToolsTests extends TestCase {

	public CollectionToolsTests(String name) {
		super(name);
	}


	// ********** add all **********

	public void testAddAllCollectionIterable_StringModified() {
		List<String> list1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList2();
		assertTrue(CollectionTools.addAll(list1, iterable2.iterator()));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(this.buildStringList2()));
	}

	public void testAddAllCollectionIterable_StringUnmodified() {
		Set<String> set1 = this.buildStringSet1();
		Iterable<String> iterable3 = this.buildStringList1(); // same elements as set1
		assertFalse(CollectionTools.addAll(set1, iterable3.iterator()));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll(this.buildStringList1()));
	}

	public void testAddAllCollectionIterable_ObjectModified() {
		List<Object> list1 = this.buildObjectList1();
		Iterable<String> iterable2 = this.buildStringList2();
		assertTrue(CollectionTools.addAll(list1, iterable2));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll((List<String>) iterable2));
	}

	public void testAddAllCollectionIterable_ObjectUnmodified() {
		Set<Object> set1 = this.buildObjectSet1();
		Iterable<String> iterable3 = this.buildStringList1(); // same elements as set1
		assertFalse(CollectionTools.addAll(set1, iterable3));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll((List<String>) iterable3));
	}

	public void testAddAllCollectionIterable_EmptyIterable() {
		Set<Object> set1 = this.buildObjectSet1();
		assertFalse(CollectionTools.addAll(set1, EmptyIterable.instance()));
		assertEquals(3, set1.size());
	}

	public void testAddAllCollectionIterableInt_Modified() {
		List<String> list1 = this.buildStringList1();
		List<String> list2 = this.buildStringList2();
		Iterable<String> iterable2 = list2;
		assertTrue(CollectionTools.addAll(list1, iterable2, list2.size()));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(this.buildStringList2()));
	}

	public void testAddAllCollectionIterableInt_Unmodified() {
		Set<String> set1 = this.buildStringSet1();
		List<String> list1 = this.buildStringList1(); // same elements as set1
		Iterable<String> iterable3 = list1;
		assertFalse(CollectionTools.addAll(set1, iterable3, list1.size()));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll(this.buildStringList1()));
	}

	public void testAddAllCollectionIterator_StringModified() {
		List<String> list1 = this.buildStringList1();
		List<String> list2 = this.buildStringList2();
		assertTrue(CollectionTools.addAll(list1, list2.iterator()));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(list2));
	}

	public void testAddAllCollectionIterator_StringUnmodified() {
		Set<String> set1 = this.buildStringSet1();
		List<String> list3 = this.buildStringList1(); // same elements as s1
		assertFalse(CollectionTools.addAll(set1, list3.iterator()));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll(list3));
	}

	public void testAddAllCollectionIterator_ObjectModified() {
		List<Object> list1 = this.buildObjectList1();
		List<String> list2 = this.buildStringList2();
		assertTrue(CollectionTools.addAll(list1, list2.iterator()));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(list2));
	}

	public void testAddAllCollectionIterator_ObjectUnmodified() {
		Set<Object> set1 = this.buildObjectSet1();
		List<String> list3 = this.buildStringList1(); // same elements as s1
		assertFalse(CollectionTools.addAll(set1, list3.iterator()));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll(list3));
	}

	public void testAddAllCollectionIterator_EmptyIterator() {
		List<String> list1 = this.buildStringList1();
		assertFalse(CollectionTools.addAll(list1, EmptyIterator.<String>instance()));
		assertEquals(3, list1.size());
	}

	public void testAddAllCollectionIteratorInt_Modified() {
		List<String> list1 = this.buildStringList1();
		List<String> list2 = this.buildStringList2();
		assertTrue(CollectionTools.addAll(list1, list2.iterator(), 3));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(list2));
	}

	public void testAddAllCollectionIteratorInt_Unmodified() {
		Set<String> set1 = this.buildStringSet1();
		List<String> list3 = this.buildStringList1(); // same elements as s1
		assertFalse(CollectionTools.addAll(set1, list3.iterator(), 3));
		assertEquals(3, set1.size());
		assertTrue(set1.containsAll(list3));
	}

	public void testAddAllCollectionIteratorInt_EmptyIterator() {
		List<String> list1 = this.buildStringList1();
		assertFalse(CollectionTools.addAll(list1, EmptyIterator.<String>instance(), 0));
		assertEquals(3, list1.size());
	}

	public void testAddAllCollectionObjectArray_StringModified() {
		List<String> list = this.buildStringList1();
		String[] a = this.buildStringArray1();
		assertTrue(CollectionTools.addAll(list, a));
		assertEquals(6, list.size());
		assertTrue(list.containsAll(CollectionTools.collection(a)));
	}

	public void testAddAllCollectionObjectArray_StringListEmptyArray() {
		List<String> list = this.buildStringList1();
		assertFalse(CollectionTools.addAll(list, new String[0]));
	}

	public void testAddAllCollectionObjectArray_StringUnmodified() {
		Set<String> set = this.buildStringSet1();
		String[] a = this.buildStringArray1();
		assertFalse(CollectionTools.addAll(set, a));
		assertEquals(3, set.size());
		assertTrue(set.containsAll(CollectionTools.collection(a)));

		assertFalse(CollectionTools.addAll(set, new String[0]));
	}

	public void testAddAllCollectionObjectArray_StringSetEmptyArray() {
		Set<String> set = this.buildStringSet1();
		assertFalse(CollectionTools.addAll(set, new String[0]));
	}

	public void testAddAllCollectionObjectArray_ObjectModified() {
		List<Object> list = this.buildObjectList1();
		String[] a = this.buildStringArray1();

		assertTrue(CollectionTools.addAll(list, a));
		assertEquals(6, list.size());
		assertTrue(list.containsAll(CollectionTools.collection(a)));
	}

	public void testAddAllCollectionObjectArray_ObjectUnmodified() {
		String[] a = this.buildStringArray1();
		Set<Object> set = this.buildObjectSet1();
		assertFalse(CollectionTools.addAll(set, a));
		assertEquals(3, set.size());
		assertTrue(set.containsAll(CollectionTools.collection(a)));
	}

	public void testAddAllListIntObjectArray() {
		List<String> list = this.buildStringList1();
		CollectionTools.addAll(list, 2, new String[] { "X", "X", "X" });
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntObjectArray_Zero() {
		List<String> list = new ArrayList<String>();
		CollectionTools.addAll(list, 0, new String[] { "X", "X", "X" });
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntObjectArray_EmptyArray() {
		List<String> list = this.buildStringList1();
		CollectionTools.addAll(list, 2, new String[0]);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		CollectionTools.addAll(list, 2, iterable);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterable_Zero() {
		List<String> list = new ArrayList<String>();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		CollectionTools.addAll(list, 0, iterable);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterable_EmptyIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = EmptyIterable.instance();
		CollectionTools.addAll(list, 2, iterable);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		CollectionTools.addAll(list, 2, iterable, 3);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt_Zero() {
		List<String> list = new ArrayList<String>();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		CollectionTools.addAll(list, 0, iterable, 3);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt_EmptyIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = EmptyIterable.instance();
		CollectionTools.addAll(list, 2, iterable, 0);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		CollectionTools.addAll(list, 2, iterator);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterator_Zero() {
		List<String> list = new ArrayList<String>();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		CollectionTools.addAll(list, 0, iterator);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterator_EmptyIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = EmptyIterator.instance();
		CollectionTools.addAll(list, 2, iterator);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		CollectionTools.addAll(list, 2, iterator, 3);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt_Zero() {
		List<String> list = new ArrayList<String>();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		CollectionTools.addAll(list, 0, iterator, 3);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt_EmptyIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = EmptyIterator.instance();
		CollectionTools.addAll(list, 2, iterator, 0);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}


	// ********** bag **********

	public void testBagEnumeration_String() {
		Bag<String> b = CollectionTools.bag(this.buildStringVector1().elements());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringVector1()));
	}

	public void testBagEnumeration_Object() {
		Bag<Object> b = CollectionTools.<Object>bag(this.buildStringVector1().elements());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringVector1()));
	}

	public void testBagEnumeration_Empty() {
		Bag<Object> b = CollectionTools.<Object>bag(EmptyEnumeration.instance());
		assertEquals(0, b.size());
	}

	public void testBagEnumerationInt() {
		Bag<String> b = CollectionTools.bag(this.buildStringVector1().elements(), 3);
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringVector1()));
	}

	public void testBagEnumerationInt_Empty() {
		Bag<String> b = CollectionTools.bag(EmptyEnumeration.<String>instance(), 3);
		assertEquals(0, b.size());
	}

	public void testBagIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Bag<String> b = CollectionTools.bag(iterable);
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		Bag<String> b = CollectionTools.bag(iterable, 3);
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIterator_String() {
		Bag<String> b = CollectionTools.bag(this.buildStringList1().iterator());
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIterator_StringObject() {
		Collection<String> c = new ArrayList<String>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		Bag<Object> b = CollectionTools.<Object>bag(c.iterator());
		assertEquals(4, b.size());
		assertTrue(b.containsAll(c));
	}

	public void testBagIterator_Empty() {
		Bag<String> b = CollectionTools.bag(EmptyIterator.<String>instance());
		assertEquals(0, b.size());
	}

	public void testBagIteratorInt() {
		Bag<String> b = CollectionTools.bag(this.buildStringList1().iterator(), 3);
		assertEquals(3, b.size());
		assertTrue(b.containsAll(this.buildStringList1()));
	}

	public void testBagIteratorInt_Empty() {
		Bag<String> b = CollectionTools.bag(EmptyIterator.<String>instance(), 3);
		assertEquals(0, b.size());
	}

	public void testBagObjectArray() {
		Bag<String> b = CollectionTools.bag(this.buildStringArray1());
		assertEquals(3, b.size());
		assertTrue(CollectionTools.containsAll(b, (Object[]) this.buildStringArray1()));
	}

	public void testBagObjectArray_Vararg() {
		Bag<String> b = CollectionTools.bag("foo", "bar", "baz");
		assertEquals(3, b.size());
		assertTrue(CollectionTools.containsAll(b, new Object[]{"foo", "bar", "baz"}));
	}

	public void testBagObjectArray_Empty() {
		Bag<String> b = CollectionTools.bag(Bag.Empty.<String>instance());
		assertEquals(0, b.size());
	}


	// ********** collection **********

	public void testCollectionEnumeration() {
		Collection<String> c = CollectionTools.collection(this.buildStringVector1().elements());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringVector1()));
	}

	public void testCollectionEnumeration_ObjectString() {
		Collection<Object> c = CollectionTools.<Object>collection(this.buildStringVector1().elements());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringVector1()));
	}

	public void testCollectionEnumerationInt() {
		Collection<String> c = CollectionTools.collection(this.buildStringVector1().elements(), 3);
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringVector1()));
	}

	public void testCollectionIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Collection<String> c = CollectionTools.collection(iterable);
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		Collection<String> c = CollectionTools.collection(iterable, 3);
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIterator() {
		Collection<String> c = CollectionTools.collection(this.buildStringList1().iterator());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIterator_ObjectString() {
		Collection<Object> c = CollectionTools.<Object>collection(this.buildStringList1().iterator());
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionIteratorInt() {
		Collection<String> c = CollectionTools.collection(this.buildStringList1().iterator(), 3);
		assertEquals(3, c.size());
		assertTrue(c.containsAll(this.buildStringList1()));
	}

	public void testCollectionObjectArray() {
		Collection<String> c = CollectionTools.collection(this.buildStringArray1());
		assertEquals(3, c.size());
		assertTrue(CollectionTools.containsAll(c, (Object[]) this.buildStringArray1()));
	}


	// ********** contains **********

	public void testContainsEnumerationObject_String() {
		Vector<String> v = this.buildStringVector1();
		assertTrue(CollectionTools.contains(v.elements(), "one"));
		assertFalse(CollectionTools.contains(v.elements(), null));
		v.add(null);
		assertTrue(CollectionTools.contains(v.elements(), null));
	}

	public void testContainsEnumerationObject_Object() {
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

	public void testContainsIteratorObject_String() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.contains(c.iterator(), "one"));
		assertFalse(CollectionTools.contains(c.iterator(), null));
		c.add(null);
		assertTrue(CollectionTools.contains(c.iterator(), null));
	}

	public void testContainsIteratorObject_Object() {
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


	// ********** contains all **********

	public void testContainsAllCollectionIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), iterable));
	}

	public void testContainsAllCollectionIterator_String() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), this.buildStringList1().iterator()));
	}

	public void testContainsAllCollectionIterator_Object() {
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

	public void testContainsAllCollectionObjectArray_StringObject() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), this.buildObjectArray1()));
	}

	public void testContainsAllCollectionObjectArray() {
		Object[] a = new Object[] { "zero", "one", "two" };
		assertTrue(CollectionTools.containsAll(this.buildStringList1(), a));
	}

	public void testContainsAllIterableCollection() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildStringList1()));
	}

	public void testContainsAllIterableIntCollection() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, 3, this.buildStringList1()));
	}

	public void testContainsAllIterableIterable() {
		Iterable<String> iterable1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable1, iterable2));
	}

	public void testContainsAllIterableIntIterable() {
		Iterable<String> iterable1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable1, 3, iterable2));
	}

	public void testContainsAllIterableIterator() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildStringList1().iterator()));
	}

	public void testContainsAllIterableIntIterator() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, 3, this.buildStringList1().iterator()));
	}

	public void testContainsAllIterableObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, this.buildObjectArray1()));
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.containsAll(iterable, this.buildObjectArray1()));
	}

	public void testContainsAllIterableIntObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(iterable, 3, this.buildObjectArray1()));
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.containsAll(iterable, 3, this.buildObjectArray1()));
	}

	public void testContainsAllIteratorCollection_StringString() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList1()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList2()));
	}

	public void testContainsAllIteratorCollection_ObjectString() {
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

	public void testContainsAllIteratorIntCollection() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), 5, this.buildStringList1()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), 5, this.buildStringList2()));
	}

	public void testContainsAllIteratorIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), iterable));
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), iterable));
	}

	public void testContainsAllIteratorIntIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, iterable));
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, iterable));
	}

	public void testContainsAllIteratorIterator_StringString() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList1().iterator()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildStringList2().iterator()));
	}

	public void testContainsAllIteratorIterator_ObjectString() {
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

	public void testContainsAllIteratorIntIterator() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, this.buildStringList1().iterator()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, this.buildStringList2().iterator()));
	}

	public void testContainsAllIteratorObjectArray() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildObjectArray1()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), this.buildObjectArray2()));
	}

	public void testContainsAllIteratorIntObjectArray() {
		assertTrue(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, this.buildObjectArray1()));
		assertFalse(CollectionTools.containsAll(this.buildStringList1().iterator(), 3, this.buildObjectArray2()));
	}


	// ********** diff **********

	public void testDiffEndListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(-1, CollectionTools.diffEnd(list1, list2));
	}

	public void testDiffRangeListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(new Range(3, -1), CollectionTools.diffRange(list1, list2));
	}

	public void testDiffStartListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(3, CollectionTools.diffStart(list1, list2));
	}


	// ********** identity diff **********

	public void testIdentityDiffEndListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(-1, CollectionTools.identityDiffEnd(list1, list2));
	}

	public void testIdentityDiffRangeListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(new Range(3, -1), CollectionTools.identityDiffRange(list1, list2));
	}

	public void testIdentityDiffStartListList() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(3, CollectionTools.identityDiffStart(list1, list2));
	}


	// ********** elements are equal **********

	public void testElementsAreDifferentIterableIterable() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();

		assertTrue(CollectionTools.elementsAreDifferent(list1, list2));
		assertFalse(CollectionTools.elementsAreEqual(list1, list2));
	}

	public void testElementsAreDifferentIteratorIterator() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();

		assertTrue(CollectionTools.elementsAreDifferent(list1.iterator(), list2.iterator()));
		assertFalse(CollectionTools.elementsAreEqual(list1, list2));
	}

	public void testElementsAreEqualIterableIterable() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < list1.size(); i++) {
			list2.add(String.valueOf((i + 1) * 1000));
		}
		assertFalse(CollectionTools.elementsAreIdentical(list1, list2));
		assertFalse(CollectionTools.elementsAreDifferent(list1, list2));
		assertTrue(CollectionTools.elementsAreEqual(list1, list2));
	}

	public void testElementsAreEqualIteratorIterator() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < list1.size(); i++) {
			list2.add(String.valueOf((i + 1) * 1000));
		}
		assertFalse(CollectionTools.elementsAreIdentical(list1.iterator(), list2.iterator()));
		assertFalse(CollectionTools.elementsAreDifferent(list1.iterator(), list2.iterator()));
		assertTrue(CollectionTools.elementsAreEqual(list1.iterator(), list2.iterator()));
	}


	// ********** elements are identical **********

	public void testElementsAreIdenticalIterableIterable() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");
		Iterable<String> iterable1 = list1;

		List<String> list2 = new ArrayList<String>();
		for (String s : list1) {
			list2.add(s);
		}
		Iterable<String> iterable2 = list2;
		assertTrue(CollectionTools.elementsAreIdentical(iterable1, iterable2));
		assertTrue(CollectionTools.elementsAreEqual(iterable1, iterable2));
	}

	public void testElementsAreIdenticalIteratorIterator() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<String> list2 = new ArrayList<String>();
		for (String s : list1) {
			list2.add(s);
		}
		assertTrue(CollectionTools.elementsAreIdentical(list1.iterator(), list2.iterator()));
		assertTrue(CollectionTools.elementsAreEqual(list1.iterator(), list2.iterator()));
	}

	public void testElementsAreIdenticalIteratorIterator_Not() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<String> list2 = new ArrayList<String>();
		for (String s : list1) {
			list2.add(s);
		}
		list2.remove(0);
		assertFalse(CollectionTools.elementsAreIdentical(list1.iterator(), list2.iterator()));
		assertFalse(CollectionTools.elementsAreEqual(list1.iterator(), list2.iterator()));
	}

	public void testElementsAreIdenticalIteratorIterator_DifferentSizes() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<String> list2 = new ArrayList<String>();
		for (String s : list1) {
			list2.add(s);
		}
		list2.remove(3);
		assertFalse(CollectionTools.elementsAreIdentical(list1.iterator(), list2.iterator()));
		assertFalse(CollectionTools.elementsAreEqual(list1.iterator(), list2.iterator()));
	}


	// ********** get **********

	public void testGetIterableInt() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		String o = CollectionTools.get(iterable, 1);
		assertEquals("one", o);
		list.add(null);
		o = CollectionTools.get(iterable, 3);
		assertNull(o);
	}

	public void testGetIteratorInt1() {
		List<String> list = this.buildStringList1();
		String o = CollectionTools.get(list.iterator(), 1);
		assertEquals("one", o);
		list.add(null);
		o = CollectionTools.get(list.iterator(), list.size() - 1);
		assertNull(o);
	}

	public void testGetIteratorInt2() {
		List<String> list = this.buildStringList1();
		boolean exCaught = false;
		try {
			CollectionTools.get(list.iterator(), list.size());
			fail();
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** hash code **********

	public void testHashCodeIterable1() {
		Iterable<String> iterable = null;
		assertEquals(0, CollectionTools.hashCode(iterable));
	}

	public void testHashCodeIterable2() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		int hashCode = CollectionTools.hashCode(iterable);
		assertEquals(list.hashCode(), hashCode);

		list.add(null);
		hashCode = CollectionTools.hashCode(iterable);
		assertEquals(list.hashCode(), hashCode);
	}


	// ********** index of **********

	public void testIndexOfIterableObject_String() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(1, CollectionTools.indexOf(iterable, "one"));
	}

	public void testIndexOfIteratorObject_String() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.indexOf(list.iterator(), "one"));
	}

	public void testIndexOfIteratorObject_String_Not() {
		List<String> list = this.buildStringList1();
		assertEquals(-1, CollectionTools.indexOf(list.iterator(), null));
		assertEquals(-1, CollectionTools.indexOf(list.iterator(), "shazam"));
	}

	public void testIndexOfIteratorObject_Null() {
		List<String> list = this.buildStringList1();
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.indexOf(list.iterator(), null));
	}

	public void testIndexOfIteratorObject_Object() {
		List<Object> list = new ArrayList<Object>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");

		String one = "1";
		assertEquals(1, CollectionTools.indexOf(list.iterator(), one));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.indexOf(list.iterator(), null));
	}


	// ********** insertion index of **********

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


	// ********** is empty **********
	
	public void testIsEmptyIterable() {
		assertFalse(CollectionTools.isEmpty(buildObjectList1()));
		assertTrue(CollectionTools.isEmpty(EmptyIterable.instance()));
	}
	
	public void testIsEmptyIterator() {
		assertFalse(CollectionTools.isEmpty(buildObjectList1().iterator()));
		assertTrue(CollectionTools.isEmpty(EmptyIterator.instance()));
	}
	
	
	// ********** iterable/iterator **********

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


	// ********** last **********

	public void testLastIterable1() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		assertEquals("two", CollectionTools.last(iterable));
		list.add(null);
		assertEquals(null, CollectionTools.last(iterable));
	}

	public void testLastIterable2() {
		Iterable<String> iterable = new ArrayList<String>();
		boolean exCaught = false;
		try {
			CollectionTools.last(iterable);
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testLastIterator1() {
		List<String> list = this.buildStringList1();
		assertEquals("two", CollectionTools.last(list.iterator()));
		list.add(null);
		assertEquals(null, CollectionTools.last(list.iterator()));
	}

	public void testLastIterator2() {
		List<String> list = new ArrayList<String>();
		boolean exCaught = false;
		try {
			CollectionTools.last(list.iterator());
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** last index of **********

	public void testLastIndexOfIterableObject() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		assertEquals(1, CollectionTools.lastIndexOf(iterable, "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(iterable, null));
	}

	public void testLastIndexOfIterableIntObject() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		assertEquals(1, CollectionTools.lastIndexOf(iterable, 23, "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(iterable, 42, null));
	}

	public void testLastIndexOfIteratorObject() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.lastIndexOf(list.iterator(), "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(list.iterator(), null));
	}

	public void testLastIndexOfIteratorObject_Empty() {
		assertEquals(-1, CollectionTools.lastIndexOf(EmptyIterator.instance(), "foo"));
	}

	public void testLastIndexOfIteratorIntObject() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.lastIndexOf(list.iterator(), 3, "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(list.iterator(), 4, null));
	}

	public void testLastIndexOfIteratorIntObject_Empty() {
		assertEquals(-1, CollectionTools.lastIndexOf(EmptyIterator.instance(), 42, "foo"));
	}


	// ********** list **********

	public void testListIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(this.buildStringList1(), CollectionTools.list(iterable));
	}

	public void testListIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(this.buildStringList1(), CollectionTools.list(iterable, 3));
	}

	public void testListIterator_String() {
		List<String> list = CollectionTools.list(this.buildStringList1().iterator());
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIterator_StringObject() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<Object> list2 = CollectionTools.<Object>list(list1.iterator());
		assertEquals(list1, list2);
	}

	public void testListIterator_Empty() {
		assertEquals(0, CollectionTools.list(EmptyIterator.instance()).size());
	}

	public void testListIteratorInt() {
		List<String> list = CollectionTools.list(this.buildStringList1().iterator(), 3);
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIteratorInt_Empty() {
		assertEquals(0, CollectionTools.list(EmptyIterator.instance(), 5).size());
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


	// ********** move **********

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

		result = CollectionTools.move(list, 2, 2);
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

		result = CollectionTools.move(list, 2, 2);
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

		result = CollectionTools.move(list, 1, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = CollectionTools.move(list, 1, 0, 0);
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

		result = CollectionTools.move(list, 1, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = CollectionTools.move(list, 1, 0, 0);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));
	}


	// ********** remove all **********

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

	public void testRemoveAllCollectionIterableInt() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, iterable, 4));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.removeAll(c, iterable, 55));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionIterator_Empty() {
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

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, EmptyIterator.instance()));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionIteratorInt_Empty() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, this.buildStringList1().iterator(), 5));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, this.buildStringList2().iterator(), 5));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, EmptyIterator.instance(), 0));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionIterator_Duplicates() {
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

	public void testRemoveAllCollectionIterator_ObjectString() {
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

	public void testRemoveAllCollectionObjectArray_Empty() {
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

		c = this.buildStringList1();
		assertFalse(CollectionTools.removeAll(c, new Object[0]));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);
	}

	public void testRemoveAllCollectionObjectArray_Duplicates() {
		Collection<String> c = new ArrayList<String>();
		c.add("a");
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("d");
		String[] a = new String[] { "a", "d" };
		assertTrue(CollectionTools.removeAll(c, (Object[]) a));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		assertFalse(CollectionTools.removeAll(c,(Object[])  a));
	}

	public void testRemoveAllCollectionObjectArray_MoreDuplicates() {
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
		assertTrue(CollectionTools.removeAll(c, (Object[]) a));
		assertEquals(3, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		assertFalse(CollectionTools.removeAll(c, (Object[]) a));
	}


	// ********** remove all occurrences **********

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


	// ********** remove elements at index **********

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


	// ********** remove duplicate elements **********

	public void testRemoveDuplicateElementsList1() {
		List<String> list = this.buildStringVector1();
		list.add("zero");
		list.add("zero");
		list.add("two");
		list.add("zero");
		assertTrue(CollectionTools.removeDuplicateElements(list));
		int i = 0;
		assertEquals("zero", list.get(i++));
		assertEquals("one", list.get(i++));
		assertEquals("two", list.get(i++));
		assertEquals(i, list.size());
	}

	public void testRemoveDuplicateElementsList2() {
		List<String> list = this.buildStringVector1();
		assertFalse(CollectionTools.removeDuplicateElements(list));
		int i = 0;
		assertEquals("zero", list.get(i++));
		assertEquals("one", list.get(i++));
		assertEquals("two", list.get(i++));
		assertEquals(i, list.size());
	}

	public void testRemoveDuplicateElementsList_Empty() {
		List<String> list = new ArrayList<String>();
		assertFalse(CollectionTools.removeDuplicateElements(list));
		assertEquals(0, list.size());
	}

	public void testRemoveDuplicateElementsList_SingleElement() {
		List<String> list = new ArrayList<String>();
		list.add("zero");
		assertFalse(CollectionTools.removeDuplicateElements(list));
		assertEquals(1, list.size());
	}


	// ********** retain all **********

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

	public void testRetainAllCollectionIterableInt() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = this.buildStringList1();
		assertFalse(CollectionTools.retainAll(c, iterable));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		iterable = this.buildStringList2();
		assertTrue(CollectionTools.retainAll(c, iterable, 7));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));
	}

	public void testRetainAllCollectionIterator() {
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

	public void testRetainAllCollectionIterator_ObjectString() {
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
	}

	public void testRetainAllCollectionIterator_EmptyIterator() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.retainAll(c, EmptyIterator.instance()));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionIterator_EmptyCollection() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, this.buildStringList1().iterator()));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionIterator_EmptyCollectionEmptyIterator() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, EmptyIterator.instance()));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionIteratorInt() {
		Collection<String> c = this.buildStringList1();
		assertFalse(CollectionTools.retainAll(c, this.buildStringList1().iterator(), 8));
		assertEquals(this.buildStringList1().size(), c.size());
		assertEquals(this.buildStringList1(), c);

		assertTrue(CollectionTools.retainAll(c, this.buildStringList2().iterator(), 9));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));
	}

	public void testRetainAllCollectionIteratorInt_EmptyIterator() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.retainAll(c, EmptyIterator.instance(), 0));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionIteratorInt_EmptyCollection() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, this.buildStringList1().iterator(), 3));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionIteratorInt_EmptyCollectionEmptyIterator() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, EmptyIterator.instance(), 0));
		assertEquals(0, c.size());
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

	public void testRetainAllCollectionObjectArray_EmptyObjectArray() {
		Collection<String> c = this.buildStringList1();
		assertTrue(CollectionTools.retainAll(c, new Object[0]));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionObjectArray_EmptyCollection() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, (Object[]) new String[] { "foo" }));
		assertEquals(0, c.size());
	}

	public void testRetainAllCollectionObjectArray_EmptyCollectionEmptyObjectArray() {
		Collection<String> c = new ArrayList<String>();
		assertFalse(CollectionTools.retainAll(c, (Object[]) new String[0]));
		assertEquals(0, c.size());
	}


	// ********** reverse list **********

	public void testReverseListIterable() {
		Iterable<String> iterable = this.buildStringList1();
		List<String> actual = CollectionTools.reverseList(iterable);
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		List<String> actual = CollectionTools.reverseList(iterable, 10);
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIterator_String() {
		List<String> actual = CollectionTools.reverseList(this.buildStringList1().iterator());
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIterator_Object() {
		List<Object> actual = CollectionTools.<Object>reverseList(this.buildStringList1().iterator());
		List<Object> expected = this.buildObjectList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}

	public void testReverseListIteratorInt() {
		List<String> actual = CollectionTools.reverseList(this.buildStringList1().iterator(), 33);
		List<String> expected = this.buildStringList1();
		Collections.reverse(expected);
		assertEquals(expected, actual);
	}


	// ********** rotate **********

	public void testRotateList() {
		List<String> actual = CollectionTools.rotate(this.buildStringList1());
		List<String> expected = this.buildStringList1();
		Collections.rotate(expected, 1);
		assertEquals(expected, actual);
	}


	// ********** set **********

	public void testSetIterable() {
		Iterable<String> iterable = this.buildStringSet1();
		assertEquals(this.buildStringSet1(), CollectionTools.set(iterable));
	}

	public void testSetIterableInt() {
		Iterable<String> iterable = this.buildStringSet1();
		assertEquals(this.buildStringSet1(), CollectionTools.set(iterable, 22));
	}

	public void testSetIterator_String() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().iterator()));
	}

	public void testSetIterator_Object() {
		List<String> list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		Set<String> set = new HashSet<String>();
		set.addAll(list);

		assertEquals(set, CollectionTools.<Object>set(list.iterator()));
	}

	public void testSetIteratorInt() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().iterator(), 3));
	}

	public void testSetObjectArray() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().toArray()));
	}


	// ********** singleton iterator **********

	public void testSingletonIterator_String() {
		Iterator<String> stream = CollectionTools.singletonIterator("foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSingletonIterator_Object() {
		Iterator<Object> stream = CollectionTools.<Object>singletonIterator("foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSingletonIterator_Cast() {
		Iterator<Object> stream = CollectionTools.singletonIterator((Object) "foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
	}

	public void testSingletonListIterator_String() {
		ListIterator<String> stream = CollectionTools.singletonListIterator("foo");
		assertTrue(stream.hasNext());
		assertEquals("foo", stream.next());
		assertFalse(stream.hasNext());
		assertTrue(stream.hasPrevious());
		assertEquals("foo", stream.previous());
	}


	// ********** size **********

	public void testSizeIterable() {
		Iterable<Object> iterable = this.buildObjectList1();
		assertEquals(3, CollectionTools.size(iterable));
	}

	public void testSizeIterator() {
		assertEquals(3, CollectionTools.size(this.buildObjectList1().iterator()));
	}
	
	
	// ********** sort **********

	public void testSortIterable() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Iterable<String> iterable1 = list;
		Iterable<String> iterable2 = CollectionTools.<String>sort(iterable1);
		assertTrue(CollectionTools.elementsAreEqual(ss, iterable2));
	}

	public void testSortIterableInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Iterable<String> iterable1 = list;
		Iterable<String> iterable2 = CollectionTools.<String>sort(iterable1, 77);
		assertTrue(CollectionTools.elementsAreEqual(ss, iterable2));
	}

	public void testSortIterableComparator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Iterable<String> iterable1 = list;
		Iterable<String> iterable2 = CollectionTools.<String>sort(iterable1, new ReverseComparator<String>());
		assertTrue(CollectionTools.elementsAreEqual(ss, iterable2));
	}

	public void testSortIterableComparatorInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Iterable<String> iterable1 = list;
		Iterable<String> iterable2 = CollectionTools.<String>sort(iterable1, new ReverseComparator<String>(), 77);
		assertTrue(CollectionTools.elementsAreEqual(ss, iterable2));
	}

	public void testSortIterator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Iterator<String> iterator1 = list.iterator();
		Iterator<String> iterator2 = CollectionTools.<String>sort(iterator1);
		assertTrue(CollectionTools.elementsAreEqual(ss.iterator(), iterator2));
	}

	public void testSortIteratorInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Iterator<String> iterator1 = list.iterator();
		Iterator<String> iterator2 = CollectionTools.<String>sort(iterator1, 77);
		assertTrue(CollectionTools.elementsAreEqual(ss.iterator(), iterator2));
	}

	public void testSortIteratorComparator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Iterator<String> iterator1 = list.iterator();
		Iterator<String> iterator2 = CollectionTools.<String>sort(iterator1, new ReverseComparator<String>());
		assertTrue(CollectionTools.elementsAreEqual(ss.iterator(), iterator2));
	}

	public void testSortIteratorComparatorInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Iterator<String> iterator1 = list.iterator();
		Iterator<String> iterator2 = CollectionTools.<String>sort(iterator1, new ReverseComparator<String>(), 77);
		assertTrue(CollectionTools.elementsAreEqual(ss.iterator(), iterator2));
	}


	// ********** sorted set **********

	public void testSortedSetIterable() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.addAll(list);

		Iterable<String> iterable = list;
		SortedSet<String> ss2 = CollectionTools.<String>sortedSet(iterable);
		assertEquals(ss1, ss2);
	}

	public void testSortedSetIterableInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.addAll(list);

		Iterable<String> iterable = list;
		SortedSet<String> ss2 = CollectionTools.<String>sortedSet(iterable, 5);
		assertEquals(ss1, ss2);
	}

	public void testSortedSetIterableComparator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss1 = new TreeSet<String>(new ReverseComparator<String>());
		ss1.addAll(list);

		Iterable<String> iterable = list;
		SortedSet<String> ss2 = CollectionTools.<String>sortedSet(iterable, new ReverseComparator<String>());
		assertEquals(ss1, ss2);
	}

	public void testSortedSetIterableComparatorInt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss1 = new TreeSet<String>(new ReverseComparator<String>());
		ss1.addAll(list);

		Iterable<String> iterable = list;
		SortedSet<String> ss2 = CollectionTools.<String>sortedSet(iterable, new ReverseComparator<String>(), 5);
		assertEquals(ss1, ss2);
	}

	public void testSortedSetIterator() {
		assertEquals(this.buildSortedStringSet1(), CollectionTools.sortedSet(this.buildSortedStringSet1().iterator()));
	}

	public void testSortedSetIterator_TreeSet() {
		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.add("0");
		ss1.add("2");
		ss1.add("3");
		ss1.add("1");

		SortedSet<String> set2 = CollectionTools.<String>sortedSet(ss1.iterator());
		assertEquals(ss1, set2);
	}

	public void testSortedSetIteratorInt() {
		assertEquals(this.buildSortedStringSet1(), CollectionTools.sortedSet(this.buildSortedStringSet1().iterator(), 8));
	}

	public void testSortedSetObjectArray() {
		assertEquals(this.buildSortedStringSet1(), CollectionTools.sortedSet(this.buildStringSet1().toArray(new String[0])));
	}

	public void testSortedSetObjectArrayComparator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss1 = new TreeSet<String>(new ReverseComparator<String>());
		ss1.addAll(list);

		String[] array = list.toArray(new String[list.size()]);
		SortedSet<String> ss2 = CollectionTools.<String>sortedSet(array, new ReverseComparator<String>());
		assertEquals(ss1, ss2);
	}


	// ********** Old School Vector **********

	public void testVectorIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Vector<String> v = CollectionTools.vector(iterable);
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		Vector<String> v = CollectionTools.vector(iterable, 8);
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIterator_String() {
		Vector<String> v = CollectionTools.vector(this.buildStringList1().iterator());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIterator_Object() {
		Vector<Object> v = CollectionTools.<Object>vector(this.buildStringList1().iterator());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorIteratorInt() {
		Vector<String> v = CollectionTools.vector(this.buildStringList1().iterator(), 7);
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}

	public void testVectorObjectArray() {
		Vector<String> v = CollectionTools.vector(this.buildStringArray1());
		assertEquals(3, v.size());
		assertTrue(v.containsAll(this.buildStringList1()));
	}


	// ********** single-use iterable **********

	public void testIterableIterator() {
		Iterator<Object> emptyIterator = EmptyIterator.instance();
		Iterable<Object> emptyIterable = CollectionTools.iterable(emptyIterator);
		
		assertFalse(emptyIterable.iterator().hasNext());
		
		boolean exceptionThrown = false;
		try {
			emptyIterator = emptyIterable.iterator();
			fail("invalid iterator: " + emptyIterator);
		} catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		assertTrue("IllegalStateException not thrown.", exceptionThrown);
	}

	public void testIterableIterator_NPE() {
		Iterator<Object> nullIterator = null;
		boolean exceptionThrown = false;
		try {
			Iterable<Object> emptyIterable = CollectionTools.iterable(nullIterator);
			fail("invalid iterable: " + emptyIterable);
		} catch (NullPointerException ise) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	public void testIterableIterator_ToString() {
		Iterator<Object> emptyIterator = EmptyIterator.instance();
		Iterable<Object> emptyIterable = CollectionTools.iterable(emptyIterator);
		assertNotNull(emptyIterable.toString());
	}


	// ********** java.util.Collections enhancements **********

	public void testCopyListList() {
		List<String> src = this.buildStringList1();
		List<String> dest = new ArrayList<String>();
		for (String s : src) {
			dest.add(s.toUpperCase());
		}
		List<String> result = CollectionTools.copy(dest, src);
		assertSame(dest, result);
		assertTrue(CollectionTools.elementsAreIdentical(src, dest));
	}

	public void testFillListObject() {
		List<String> list = this.buildStringList1();
		List<String> result = CollectionTools.fill(list, "foo");
		assertSame(list, result);
		for (String string : result) {
			assertEquals("foo", string);
		}
	}

	public void testShuffleList() {
		List<String> list = this.buildStringList1();
		List<String> result = CollectionTools.shuffle(list);
		assertSame(list, result);
	}

	public void testShuffleListRandom() {
		List<String> list = this.buildStringList1();
		List<String> result = CollectionTools.shuffle(list, new Random());
		assertSame(list, result);
	}

	public void testSortList() {
		List<String> list = this.buildStringList1();
		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);
		List<String> result = CollectionTools.sort(list);
		assertSame(list, result);
		assertTrue(CollectionTools.elementsAreEqual(ss, result));
	}

	public void testSwapListIntInt() {
		List<String> list = this.buildStringList1();
		List<String> result = CollectionTools.swap(list, 0, 1);
		assertSame(list, result);
		List<String> original = this.buildStringList1();
		assertEquals(original.get(0), result.get(1));
		assertEquals(original.get(1), result.get(0));
		assertEquals(original.get(2), result.get(2));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ReflectionTools.newInstance(CollectionTools.class);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}


	// ********** test harness **********

	private Object[] buildObjectArray1() {
		return new Object[] { "zero", "one", "two" };
	}

	private String[] buildStringArray1() {
		return new String[] { "zero", "one", "two" };
	}

	private Object[] buildObjectArray2() {
		return new Object[] { "three", "four", "five" };
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
