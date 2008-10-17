/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.EmptyIterable;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyEnumeration;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

@SuppressWarnings("nls")
public class CollectionToolsTests extends TestCase {

	public CollectionToolsTests(String name) {
		super(name);
	}

	public void testAddObjectArrayObject_Object() {
		Object[] a = CollectionTools.add(this.buildObjectArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayObject_String() {
		String[] a = CollectionTools.add(this.buildStringArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayObject_EmptyArray() {
		String[] a = new String[0];
		a = CollectionTools.add(a, "twenty");
		assertEquals(1, a.length);
		assertTrue(CollectionTools.contains(a, "twenty"));
		assertEquals("twenty", a[0]);
	}

	public void testAddObjectArrayIntObject_Object() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_String() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_End() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 4, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "c", "d", "X" }, a));
	}

	public void testAddObjectArrayIntObject_Zero() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.add(a, 0, "X");
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "X", "a", "b", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_Exception() {
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

	public void testAddCharArrayChar_Empty() {
		char[] a = new char[0];
		a = CollectionTools.add(a, 'd');
		assertEquals(1, a.length);
		assertTrue(CollectionTools.contains(a, 'd'));
		assertTrue(Arrays.equals(new char[] { 'd' }, a));
	}

	public void testAddCharArrayIntChar() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.add(a, 2, 'X');
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'X', 'c', 'd' }, a));
	}

	public void testAddCharArrayIntChar_Zero() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.add(a, 0, 'X');
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'X', 'a', 'b', 'c', 'd' }, a));
	}

	public void testAddCharArrayIntChar_End() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.add(a, 4, 'X');
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'X' }, a));
	}

	public void testAddIntArrayInt() {
		int[] a = CollectionTools.add(this.buildIntArray(), 30);
		assertEquals(4, a.length);
		assertTrue(CollectionTools.contains(a, 30));
	}

	public void testAddIntArrayInt_Empty() {
		int[] a = new int[0];
		a = CollectionTools.add(a, 30);
		assertEquals(1, a.length);
		assertTrue(CollectionTools.contains(a, 30));
		assertTrue(Arrays.equals(new int[] { 30 }, a));
	}

	public void testAddIntArrayIntInt() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.add(a, 2, 99);
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 99, 3, 4 }, a));
	}

	public void testAddIntArrayIntInt_Zero() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.add(a, 0, 99);
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 99, 1, 2, 3, 4 }, a));
	}

	public void testAddIntArrayIntInt_End() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.add(a, 4, 99);
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 99 }, a));
	}

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
		assertTrue(CollectionTools.addAll(list1, iterable2.iterator(), list2.size()));
		assertEquals(6, list1.size());
		assertTrue(list1.containsAll(this.buildStringList2()));
	}

	public void testAddAllCollectionIterableInt_Unmodified() {
		Set<String> set1 = this.buildStringSet1();
		List<String> list1 = this.buildStringList1(); // same elements as set1
		Iterable<String> iterable3 = list1;
		assertFalse(CollectionTools.addAll(set1, iterable3.iterator(), list1.size()));
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

	public void testAddAllObjectArrayCollection_String() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_Object() {
		Object[] a = this.buildObjectArray1();
		Collection<String> c = this.buildStringList2();
		Object[] newArray = CollectionTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_EmptyArray() {
		String[] a = new String[0];
		Collection<String> c = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, c);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_EmptyCollection() {
		String[] a = this.buildStringArray1();
		Collection<String> c = new ArrayList<String>();
		String[] newArray = CollectionTools.addAll(a, c);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntCollection_String() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 1, c);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIntCollection_EmptyArray() {
		String[] a = new String[0];
		Collection<String> c = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 0, c);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIntCollection_EmptyCollection() {
		String[] a = this.buildStringArray1();
		Collection<String> c = new ArrayList<String>();
		String[] newArray = CollectionTools.addAll(a, 1, c);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterable_String() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 1, iterable);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterable_EmptyArray() {
		String[] a = new String[0];
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 0, iterable);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterable_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = new ArrayList<String>();
		String[] newArray = CollectionTools.addAll(a, 1, iterable);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterableInt_String() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 1, iterable, 3);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterableInt_EmptyArray() {
		String[] a = new String[0];
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = CollectionTools.addAll(a, 0, iterable, 3);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterableInt_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = new ArrayList<String>();
		String[] newArray = CollectionTools.addAll(a, 1, iterable, 0);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterator_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = CollectionTools.addAll(a, 1, iterator);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIterator_EmptyArray() {
		String[] a = new String[0];
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = CollectionTools.addAll(a, 0, iterator);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIterator_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = CollectionTools.addAll(a, 1, iterator);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIteratorInt_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = CollectionTools.addAll(a, 1, iterator, 3);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIteratorInt_EmptyArray() {
		String[] a = new String[0];
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = CollectionTools.addAll(a, 0, iterator, 3);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIteratorInt_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = CollectionTools.addAll(a, 1, iterator, 0);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList1();
		String[] newArray = CollectionTools.addAll(a, iterable);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterableInt() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList1();
		String[] newArray = CollectionTools.addAll(a, iterable, 33);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList1().iterator();
		String[] newArray = CollectionTools.addAll(a, iterator);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator_Object() {
		String[] a = this.buildStringArray1();
		Iterator<Object> iterator = this.buildObjectList1().iterator();
		Object[] newArray = CollectionTools.addAll(a, iterator);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildObjectList1()));
	}

	public void testAddAllObjectArrayIterator_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = CollectionTools.addAll(a, iterator);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIteratorInt() {
		String[] a = this.buildStringArray1();
		Iterator<Object> iterator = this.buildObjectList1().iterator();
		Object[] newArray = CollectionTools.addAll(a, iterator, 3);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildObjectList1()));
	}

	public void testAddAllObjectArrayIteratorInt_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = CollectionTools.addAll(a, iterator, 0);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayObjectArray_Object() {
		Object[] a1 = this.buildObjectArray1();
		Object[] a2 = this.buildObjectArray2();
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_String() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = this.buildStringArray2();
		String[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_ObjectString() {
		Object[] a1 = this.buildObjectArray1();
		String[] a2 = this.buildStringArray2();
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_EmptyArray1() {
		Object[] a1 = new Object[0];
		Object[] a2 = this.buildObjectArray2();
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_EmptyArray2() {
		Object[] a1 = this.buildObjectArray1();
		Object[] a2 = new Object[0];
		Object[] newArray = CollectionTools.addAll(a1, a2);

		assertEquals(3, newArray.length);
		assertTrue(CollectionTools.containsAll(newArray, a1));
	}

	public void testAddAllObjectArrayIntObjectArray_Object() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new Object[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_String() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_ObjectString() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 2, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_End() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 4, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d", "X", "X", "X" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_Zero() {
		Object[] a = new Object[0];
		a = CollectionTools.addAll(a, 0, new String[] { "X", "X", "X" });
		assertEquals(3, a.length);
		assertTrue(CollectionTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_EmptyArray2() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = CollectionTools.addAll(a, 4, new String[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_EmptyArray1() {
		Object[] a = new String[0];
		a = CollectionTools.addAll(a, 0, new Object[] { "a", "b", "c", "d" });
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d" }, a));
	}

	public void testAddAllCharArrayCharArray() {
		char[] a = CollectionTools.addAll(this.buildCharArray(), new char[] { 'd', 'e' });
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 'd'));
		assertTrue(CollectionTools.contains(a, 'e'));
	}

	public void testAddAllCharArrayCharArray_EmptyArray2() {
		char[] a = CollectionTools.addAll(this.buildCharArray(), new char[0]);
		assertEquals(3, a.length);
	}

	public void testAddAllCharArrayCharArrayEmptyArray1() {
		char[] a = CollectionTools.addAll(new char[0], new char[] { 'd', 'e' });
		assertEquals(2, a.length);
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

	public void testAddAllCharArrayIntCharArray_End() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.addAll(a, 4, new char[] { 'X', 'X', 'X' });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'X', 'X', 'X' }, a));
	}

	public void testAddAllCharArrayIntCharArray_EmptyArray1() {
		char[] a = new char[0];
		a = CollectionTools.addAll(a, 0, new char[] { 'X', 'X', 'X' });
		assertEquals(3, a.length);
		assertTrue(CollectionTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'X', 'X', 'X' }, a));
	}

	public void testAddAllCharArrayIntCharArray_EmptyArray2() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = CollectionTools.addAll(a, 2, new char[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd' }, a));
	}

	public void testAddAllIntArrayIntArray() {
		int[] a = CollectionTools.addAll(this.buildIntArray(), new int[] { 30, 40 });
		assertEquals(5, a.length);
		assertTrue(CollectionTools.contains(a, 30));
		assertTrue(CollectionTools.contains(a, 40));
	}

	public void testAddAllIntArrayIntArray_EmptyArray2() {
		int[] a = CollectionTools.addAll(this.buildIntArray(), new int[0]);
		assertEquals(3, a.length);
	}

	public void testAddAllIntArrayIntArray_EmptyArray1() {
		int[] a = CollectionTools.addAll(new int[0], new int[] { 30, 40 });
		assertEquals(2, a.length);
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

	public void testAddAllIntArrayIntIntArray_End() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.addAll(a, 4, new int[] { 99, 99, 99 });
		assertEquals(7, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 99, 99, 99 }, a));
	}

	public void testAddAllIntArrayIntIntArray_EmptyArray2() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = CollectionTools.addAll(a, 2, new int[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4 }, a));
	}

	public void testAddAllIntArrayIntIntArray_EmptyArray1() {
		int[] a = new int[0];
		a = CollectionTools.addAll(a, 0, new int[] { 99, 99, 99 });
		assertEquals(3, a.length);
		assertTrue(CollectionTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 99, 99, 99 }, a));
	}

	public void testArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = CollectionTools.array(iterable);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = CollectionTools.array(iterable, 3);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray_String() {
		Iterable<String> iterable = this.buildStringList1();
		String[] a = CollectionTools.array(iterable, new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray_Object() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = CollectionTools.array(iterable, new Object[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableIntObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		String[] a = CollectionTools.array(iterable, 3, new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterator() {
		Object[] a = CollectionTools.array(this.buildStringList1().iterator());
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterator_Empty() {
		Object[] a = CollectionTools.array(EmptyIterator.instance());
		assertEquals(0, a.length);
	}

	public void testArrayIteratorInt() {
		Object[] a = CollectionTools.array(this.buildStringList1().iterator(), 3);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorInt_Empty() {
		Object[] a = CollectionTools.array(EmptyIterator.instance(), 3);
		assertEquals(0, a.length);
	}

	public void testArrayIteratorObjectArray_String() {
		String[] a = CollectionTools.array(this.buildStringList1().iterator(), new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorObjectArray_Empty() {
		String[] a = CollectionTools.array(EmptyIterator.<String>instance(), new String[0]);
		assertEquals(0, a.length);
	}

	public void testArrayIteratorObjectArray_Object() {
		Object[] a = CollectionTools.array(this.buildStringList1().iterator(), new Object[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorIntObjectArray() {
		String[] a = CollectionTools.array(this.buildStringList1().iterator(), 3, new String[0]);
		assertEquals(3, a.length);
		assertTrue(CollectionTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorIntObjectArray_Empty() {
		String[] a = CollectionTools.array(EmptyIterator.<String>instance(), 3, new String[0]);
		assertEquals(0, a.length);
	}

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
		assertTrue(CollectionTools.containsAll(b, this.buildStringArray1()));
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

	public void testClearObjectArray() {
		String[] a = this.buildStringArray1();
		assertEquals(3, a.length);
		a = CollectionTools.clear(a);
		assertEquals(0, a.length);
	}

	public void testClearObjectArray_Empty() {
		String[] a = new String[0];
		assertEquals(0, a.length);
		a = CollectionTools.clear(a);
		assertEquals(0, a.length);
	}

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
		assertTrue(CollectionTools.containsAll(c, this.buildStringArray1()));
	}

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

	public void testContainsAllObjectArrayCollection() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList1()));
		assertFalse(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList2()));
	}

	public void testContainsAllObjectArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), iterable));
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.containsAll(this.buildObjectArray1(), iterable));
	}

	public void testContainsAllObjectArrayIterator() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList1().iterator()));
		assertFalse(CollectionTools.containsAll(this.buildObjectArray1(), this.buildStringList2().iterator()));
	}

	public void testContainsAllObjectArrayIterator_Empty() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), EmptyIterator.instance()));
	}

	public void testContainsAllObjectArrayObjectArray() {
		assertTrue(CollectionTools.containsAll(this.buildObjectArray1(), this.buildObjectArray1()));
		assertFalse(CollectionTools.containsAll(this.buildObjectArray1(), this.buildObjectArray2()));
	}

	public void testContainsAllCharArrayCharArray() {
		assertTrue(CollectionTools.containsAll(this.buildCharArray(), this.buildCharArray()));
		assertFalse(CollectionTools.containsAll(this.buildCharArray(), new char[] { 'x', 'y' }));
	}

	public void testContainsAllIntArrayIntArray() {
		assertTrue(CollectionTools.containsAll(this.buildIntArray(), this.buildIntArray()));
		assertFalse(CollectionTools.containsAll(this.buildIntArray(), new int[] { 444, 888 }));
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

	public void testEqualsIteratorIterator() {
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

	public void testIdenticalObjectArrayObjectArray_BothNull() {
		Object[] a1 = null;
		Object[] a2 = null;
		assertTrue(CollectionTools.identical(a1, a2));
	}

	public void testIdenticalObjectArrayObjectArray_OneNull() {
		Object[] a1 = null;
		Object[] a2 = new Object[0];
		assertFalse(CollectionTools.identical(a1, a2));
	}

	public void testIdenticalObjectArrayObjectArray_DifferentLengths() {
		Object[] a1 = new String[] {"foo", "bar"};
		Object[] a2 = new String[] {"foo", "bar", "baz"};
		assertFalse(CollectionTools.identical(a1, a2));
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

	public void testIdenticalListIteratorListIterator_Not() {
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
		assertFalse(CollectionTools.identical(list1.listIterator(), list2.listIterator()));
		assertFalse(CollectionTools.equals(list1.listIterator(), list2.listIterator()));
	}

	public void testIdenticalListIteratorListIterator_DifferentSizes() {
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
		assertFalse(CollectionTools.identical(list1.listIterator(), list2.listIterator()));
		assertFalse(CollectionTools.equals(list1.listIterator(), list2.listIterator()));
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

	public void testIndexOfListIteratorObject_String() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.indexOf(list.listIterator(), "one"));
	}

	public void testIndexOfListIteratorObject_Null() {
		List<String> list = this.buildStringList1();
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.indexOf(list.listIterator(), null));
	}

	public void testIndexOfListIteratorObject_Object() {
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
	}

	public void testIndexOfObjectArrayObject_Null() {
		Object[] a = this.buildObjectArray1();
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

	public void testLastIndexOfIteratorObject() {
		List<String> list = this.buildStringList1();
		assertEquals(1, CollectionTools.lastIndexOf(list.listIterator(), "one"));
		list.add(null);
		assertEquals(list.size() - 1, CollectionTools.lastIndexOf(list.listIterator(), null));
	}

	public void testLastIndexOfIteratorObject_Empty() {
		assertEquals(-1, CollectionTools.lastIndexOf(EmptyIterator.instance(), "foo"));
	}

	public void testLastIndexOfObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertEquals(1, CollectionTools.lastIndexOf(a, "one"));
	}

	public void testLastIndexOfObjectArrayObject_Null() {
		Object[] a = this.buildObjectArray1();
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

		result = CollectionTools.move(array, 4, 4);
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

		result = CollectionTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));

		result = CollectionTools.move(array, 1, 0, 0);
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

		result = CollectionTools.move(array, 2, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));
	}

	public void testMoveIntArrayIntIntInt() {
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

		result = CollectionTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));

		result = CollectionTools.move(array, 1, 0, 0);
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

		result = CollectionTools.move(array, 2, 2);
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

		result = CollectionTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));

		result = CollectionTools.move(array, 1, 0, 0);
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

	public void testRemoveAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[] { "E", "B" };
		String[] a3 = CollectionTools.removeAll(a1, a2);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayObjectArray_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[0];
		String[] a3 = CollectionTools.removeAll(a1, a2);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayObjectArray_NoMatches() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[] { "X", "Y", "Z" };
		String[] a3 = CollectionTools.removeAll(a1, a2);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayIterable() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = CollectionTools.removeAll(a1, iterable);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIterableInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = CollectionTools.removeAll(a1, iterable, 7);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = CollectionTools.removeAll(a1, iterable.iterator());
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIteratorInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = CollectionTools.removeAll(a1, iterable.iterator(), 7);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = CollectionTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayCollection_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = new ArrayList<String>();
		String[] a3 = CollectionTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayCollection_NoMatches() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "X", "Y", "Z" });
		String[] a3 = CollectionTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'A', 'A', 'C', 'C', 'D', 'D', 'F', 'F' }, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllCharArrayCharArray_Empty() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[0];
		assertTrue(Arrays.equals(a1, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllCharArrayCharArray_NoMatches() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'X', 'Z' };
		assertTrue(Arrays.equals(a1, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 1, 1, 3, 3, 4, 4, 6, 6 }, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray_Empty() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[0];
		assertTrue(Arrays.equals(a1, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray_NoMatches() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 52, 67 };
		assertTrue(Arrays.equals(a1, CollectionTools.removeAll(a1, a2)));
	}

	public void testRemoveObjectArrayObject_Object() {
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

	public void testRemoveObjectArrayObject_String() {
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

	public void testRemoveAllCollectionIterableInt() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(CollectionTools.removeAll(c, iterable, 3));
		assertEquals(0, c.size());
		assertFalse(c.contains("one"));
		assertFalse(c.contains("two"));
		assertFalse(c.contains("three"));

		c = this.buildStringList1();
		iterable = this.buildStringList2();
		assertFalse(CollectionTools.removeAll(c, iterable, 3));
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

	public void testRemoveAllCollectionIteratotInt() {
		Collection<String> c = new ArrayList<String>();
		c.add("a");
		c.add("a");
		c.add("b");
		c.add("c");
		c.add("d");
		c.add("d");
		String[] a = new String[] { "a", "d" };
		Iterator<String> iterator = new ArrayIterator<String>(a);
		assertTrue(CollectionTools.removeAll(c, iterator, 2));
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
		assertTrue(CollectionTools.removeAll(c, a));
		assertEquals(2, c.size());
		assertFalse(c.contains("a"));
		assertTrue(c.contains("b"));
		assertTrue(c.contains("c"));
		assertFalse(c.contains("d"));

		assertFalse(CollectionTools.removeAll(c, a));
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

	public void testRemoveElementsAtIndexObjectArrayIntInt_Empty() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}

	public void testRemoveElementsAtIndexCharArrayIntInt() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.removeElementsAtIndex(a, 0, 5);
		assertTrue(Arrays.equals(new char[] { 'D' }, a));
	}

	public void testRemoveElementsAtIndexCharArrayIntInt_Empty() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}

	public void testRemoveElementsAtIndexIntArrayIntInt() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = CollectionTools.removeElementsAtIndex(a, 3, 3);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7 }, a));
	}

	public void testRemoveElementsAtIndexIntArrayIntInt_Empty() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = CollectionTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}

	public void testReplaceAllObjectArrayObjectObject_Object() {
		Object[] a = new Object[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new Object[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllObjectArrayObjectObject_String() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = CollectionTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new String[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllObjectArrayObjectObject_Null() {
		String[] a = new String[] { null, "B", null, "C", null, "D" };
		a = CollectionTools.replaceAll(a, null, "Z");
		assertTrue(Arrays.equals(new String[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllCharArrayCharChar() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = CollectionTools.replaceAll(a, 'A', 'Z');
		assertTrue(Arrays.equals(new char[] { 'Z', 'B', 'Z', 'C', 'Z', 'D' }, a));
	}

	public void testReplaceAllIntArrayIntInt() {
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
		assertFalse(CollectionTools.retainAll(c, new String[] { "foo" }));
		assertEquals(0, c.size());
	}

	public void testRetainAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Object[] a2 = new Object[] { "E", "B", new Integer(7) };
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, a2)));
	}

	public void testRetainAllObjectArrayObjectArray_EmptyObjectArray1() {
		String[] a1 = new String[0];
		String[] a2 = new String[] { "E", "B", "" };
		String[] a3 = CollectionTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayObjectArray_EmptyObjectArray2() {
		String[] a1 = new String[] { "E", "B", "" };
		String[] a2 = new String[0];
		String[] a3 = CollectionTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayObjectArray_BothEmpty() {
		String[] a1 = new String[0];
		String[] a2 = new String[0];
		String[] a3 = CollectionTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterable() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, iterable)));
	}

	public void testRetainAllObjectArrayIterable_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = CollectionTools.retainAll(a1, iterable);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterableInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, iterable, 3)));
	}

	public void testRetainAllObjectArrayIterableInt_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = CollectionTools.retainAll(a1, iterable, 3);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, iterator)));
	}

	public void testRetainAllObjectArrayIterator_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		String[] a3 = CollectionTools.retainAll(a1, iterator);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIteratorInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, iterator, 3)));
	}

	public void testRetainAllObjectArrayIteratorInt_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		String[] a3 = CollectionTools.retainAll(a1, iterator, 3);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, CollectionTools.retainAll(a1, collection)));
	}

	public void testRetainAllObjectArrayCollection_EmptyObjectArray() {
		String[] a1 = new String[0];
		Collection<String> collection = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = CollectionTools.retainAll(a1, collection);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection_EmptyCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = new ArrayList<String>();
		String[] a3 = CollectionTools.retainAll(a1, collection);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection_All() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" });
		assertTrue(Arrays.equals(a1, CollectionTools.retainAll(a1, collection)));
	}

	public void testRetainAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'B', 'B', 'E', 'E' }, CollectionTools.retainAll(a1, a2)));
	}

	public void testRetainAllCharArrayCharArray_EmptyCharArray1() {
		char[] a1 = new char[0];
		char[] a2 = new char[] { 'E', 'B' };
		assertSame(a1, CollectionTools.retainAll(a1, a2));
	}

	public void testRetainAllCharArrayCharArray_EmptyCharArray2() {
		char[] a1 = new char[] { 'E', 'B' };
		char[] a2 = new char[0];
		assertEquals(0, CollectionTools.retainAll(a1, a2).length);
	}

	public void testRetainAllCharArrayCharArray_RetainAll() {
		char[] a1 = new char[] { 'E', 'B', 'E', 'B', 'E', 'B', 'E', 'B', 'E' };
		char[] a2 = new char[] { 'E', 'B' };
		assertSame(a1, CollectionTools.retainAll(a1, a2));
	}

	public void testRetainAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 2, 2, 5, 5 }, CollectionTools.retainAll(a1, a2)));
	}

	public void testRetainAllIntArrayIntArray_EmptyIntArray1() {
		int[] a1 = new int[0];
		int[] a2 = new int[] { 5, 2 };
		assertSame(a1, CollectionTools.retainAll(a1, a2));
	}

	public void testRetainAllIntArrayIntArray_EmptyIntArray2() {
		int[] a1 = new int[] { 5, 2 };
		int[] a2 = new int[0];
		assertEquals(0, CollectionTools.retainAll(a1, a2).length);
	}

	public void testRetainAllIntArrayIntArray_RetainAll() {
		int[] a1 = new int[] { 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5 };
		int[] a2 = new int[] { 5, 2 };
		assertSame(a1, CollectionTools.retainAll(a1, a2));
	}

	public void testReverseObjectArray_Object() {
		Object[] a = this.buildObjectArray1();
		a = CollectionTools.reverse(a);
		assertEquals("two", a[0]);
		assertEquals("one", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testReverseObjectArray_String() {
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

	public void testRotateObjectArray() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.rotate(a);
		assertEquals("two", a[0]);
		assertEquals("zero", a[1]);
		assertEquals("one", a[2]);
	}

	public void testRotateObjectArray_Zero() {
		String[] a1 = new String[0];
		String[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateObjectArray_One() {
		String[] a1 = new String[] { "foo  " };
		String[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt() {
		String[] a = this.buildStringArray1();
		a = CollectionTools.rotate(a, 2);
		assertEquals("one", a[0]);
		assertEquals("two", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testRotateObjectArrayInt_ZeroDistance() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = CollectionTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt_Zero() {
		String[] a1 = new String[0];
		String[] a2 = CollectionTools.rotate(a1, 7);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt_One() {
		String[] a1 = new String[] { "foo  " };
		String[] a2 = CollectionTools.rotate(a1, 8);
		assertSame(a1, a2);
	}

	public void testRotateCharArray() {
		char[] a = this.buildCharArray();
		a = CollectionTools.rotate(a);
		assertEquals('c', a[0]);
		assertEquals('a', a[1]);
		assertEquals('b', a[2]);
	}

	public void testRotateCharArray_Zero() {
		char[] a1 = new char[0];
		char[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateCharArray_One() {
		char[] a1 = new char[] { 'a' };
		char[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt() {
		char[] a = this.buildCharArray();
		a = CollectionTools.rotate(a, 2);
		assertEquals('b', a[0]);
		assertEquals('c', a[1]);
		assertEquals('a', a[2]);
	}

	public void testRotateCharArrayInt_ZeroDistance() {
		char[] a1 = new char[] { 'a', 'b', 'c' };
		char[] a2 = CollectionTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt_Zero() {
		char[] a1 = new char[0];
		char[] a2 = CollectionTools.rotate(a1, 2001);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt_One() {
		char[] a1 = new char[] { 'a' };
		char[] a2 = CollectionTools.rotate(a1, 22);
		assertSame(a1, a2);
	}

	public void testRotateIntArray() {
		int[] a = this.buildIntArray();
		a = CollectionTools.rotate(a);
		assertEquals(20, a[0]);
		assertEquals(0, a[1]);
		assertEquals(10, a[2]);
	}

	public void testRotateIntArray_Zero() {
		int[] a1 = new int[0];
		int[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateIntArray_One() {
		int[] a1 = new int[] { 77 };
		int[] a2 = CollectionTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt() {
		int[] a = this.buildIntArray();
		a = CollectionTools.rotate(a, 2);
		assertEquals(10, a[0]);
		assertEquals(20, a[1]);
		assertEquals(0, a[2]);
	}

	public void testRotateIntArrayInt_ZeroDistance() {
		int[] a1 = new int[] { 3, 2, 1 };
		int[] a2 = CollectionTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt_Zero() {
		int[] a1 = new int[0];
		int[] a2 = CollectionTools.rotate(a1, 3);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt_One() {
		int[] a1 = new int[] { 77 };
		int[] a2 = CollectionTools.rotate(a1, 44);
		assertSame(a1, a2);
	}

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
		Set<String> set1 = new HashSet<String>();
		set1.add("0");
		set1.add("1");
		set1.add("2");
		set1.add("3");

		Set<Object> set2 = CollectionTools.<Object>set(set1.iterator());
		assertEquals(set1, set2);
	}

	public void testSetIteratorInt() {
		assertEquals(this.buildStringSet1(), CollectionTools.set(this.buildStringSet1().iterator(), 3));
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

	public void testSortedSetIterableInt() {
		SortedSet<String> ss1 = new TreeSet<String>();
		ss1.add("0");
		ss1.add("2");
		ss1.add("3");
		ss1.add("1");

		Iterable<String> iterable = ss1;
		SortedSet<String> set2 = CollectionTools.<String>sortedSet(iterable, 5);
		assertEquals(ss1, set2);
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

	public void testRemoveDuplicateElementsObjectArray() {
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

	public void testRemoveDuplicateElementsObjectArray_Empty() {
		String[] array = CollectionTools.removeDuplicateElements(new String[0]);
		assertEquals(0, array.length);
	}

	public void testRemoveDuplicateElementsObjectArray_SingleElement() {
		String[] array = CollectionTools.removeDuplicateElements(new String[] { "foo" });
		assertEquals(1, array.length);
	}

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
