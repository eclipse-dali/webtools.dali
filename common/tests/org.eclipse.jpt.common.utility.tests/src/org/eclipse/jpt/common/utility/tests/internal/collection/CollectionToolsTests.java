/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ReverseComparator;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.ArrayToolsTests;

@SuppressWarnings("nls")
public class CollectionToolsTests
	extends TestCase
{
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


	// ********** bag **********

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

	// ********** filter **********

	public void testFilterCollectionFilter() {
		Collection<String> c = CollectionTools.collection(new String[] { "zero", "one", "two", "three", "four" });
		Collection<String> actual = CollectionTools.filter(c, new ArrayToolsTests.StringLengthFilter(3));
		Collection<String> expected = CollectionTools.collection(new String[] { "one", "two" });
		assertEquals(expected, actual);
	}

	public void testFilterCollectionFilterTransparent() {
		Collection<String> c = CollectionTools.collection(new String[] { "zero", "one", "two", "three", "four" });
		Collection<String> actual = CollectionTools.filter(c, Predicate.True.<String>instance());
		Collection<String> expected = CollectionTools.collection(new String[] { "zero", "one", "two", "three", "four" });
		assertEquals(expected, actual);
		assertNotSame(expected, actual);
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
		Iterator<String> iterator = IteratorTools.iterator(a);
		assertTrue(CollectionTools.removeAll(c, iterator));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		iterator = IteratorTools.iterator(a);
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
		Iterator<String> iterator = IteratorTools.iterator(a);
		assertTrue(CollectionTools.removeAll(c, iterator));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		iterator = IteratorTools.iterator(a);
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


	// ********** transform **********

	public void testTransformCollectionTransformer() {
		List<String> list = Arrays.asList(new String[] { "zero", "one", "two" });
		Collection<String> actual = CollectionTools.transform(list, ArrayToolsTests.UPPER_CASE_TRANSFORMER);
		assertEquals(3, actual.size());
		assertTrue(actual.contains("ZERO"));
		assertTrue(actual.contains("ONE"));
		assertTrue(actual.contains("TWO"));
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


	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(CollectionTools.class);
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
