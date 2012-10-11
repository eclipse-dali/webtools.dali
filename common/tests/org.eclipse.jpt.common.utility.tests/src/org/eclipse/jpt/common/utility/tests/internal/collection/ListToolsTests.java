/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.Range;
import org.eclipse.jpt.common.utility.internal.ReverseComparator;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;

@SuppressWarnings("nls")
public class ListToolsTests
	extends TestCase
{
	public ListToolsTests(String name) {
		super(name);
	}
	public void testAddAllListIntObjectArray() {
		List<String> list = this.buildStringList1();
		ListTools.addAll(list, 2, new String[] { "X", "X", "X" });
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntObjectArray_Zero() {
		List<String> list = new ArrayList<String>();
		ListTools.addAll(list, 0, new String[] { "X", "X", "X" });
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntObjectArray_EmptyArray() {
		List<String> list = this.buildStringList1();
		ListTools.addAll(list, 2, new String[0]);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		ListTools.addAll(list, 2, iterable);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterable_Zero() {
		List<String> list = new ArrayList<String>();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		ListTools.addAll(list, 0, iterable);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterable_EmptyIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = EmptyIterable.instance();
		ListTools.addAll(list, 2, iterable);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		ListTools.addAll(list, 2, iterable, 3);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt_Zero() {
		List<String> list = new ArrayList<String>();
		Iterable<String> iterable = Arrays.asList(new String[] { "X", "X", "X" });
		ListTools.addAll(list, 0, iterable, 3);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterableInt_EmptyIterable() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = EmptyIterable.instance();
		ListTools.addAll(list, 2, iterable, 0);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		ListTools.addAll(list, 2, iterator);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIterator_Zero() {
		List<String> list = new ArrayList<String>();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		ListTools.addAll(list, 0, iterator);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIterator_EmptyIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = EmptyIterator.instance();
		ListTools.addAll(list, 2, iterator);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		ListTools.addAll(list, 2, iterator, 3);
		assertEquals(6, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "X", "X", "X", "two" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt_Zero() {
		List<String> list = new ArrayList<String>();
		Iterator<String> iterator = Arrays.asList(new String[] { "X", "X", "X" }).iterator();
		ListTools.addAll(list, 0, iterator, 3);
		assertEquals(3, list.size());
		assertTrue(list.contains("X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, list.toArray()));
	}

	public void testAddAllListIntIteratorInt_EmptyIterator() {
		List<String> list = this.buildStringList1();
		Iterator<String> iterator = EmptyIterator.instance();
		ListTools.addAll(list, 2, iterator, 0);
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new Object[] { "zero", "one", "two" }, list.toArray()));
	}



	// ********** diff **********

	public void testIndexOfDifference() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(3, ListTools.indexOfDifference(list1, list2));
	}

	public void testLastIndexOfDifference() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(-1, ListTools.lastIndexOfDifference(list1, list2));
	}

	public void testDifferenceRange() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("a"));
		list2.add(new String("b"));
		list2.add(new String("c"));
		assertEquals(new Range(3, -1), ListTools.differenceRange(list1, list2));
	}


	// ********** identity diff **********

	public void testIndexOfIdentityDifference() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(3, ListTools.indexOfIdentityDifference(list1, list2));
	}

	public void testLastIndexOfIdentityDifference() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(-1, ListTools.lastIndexOfIdentityDifference(list1, list2));
	}

	public void testIdentityDifferenceRange() {
		List<String> list1 = new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
		assertEquals(new Range(3, -1), ListTools.identityDifferenceRange(list1, list2));
	}


	// ********** insertion index of **********

	public void testInsertionIndexOfListComparableRandomAccess() {
		List<String> list = Arrays.asList(new String[] { "A", "C", "D" });
		assertEquals(1, ListTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "C", "D" });
		assertEquals(2, ListTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" });
		assertEquals(4, ListTools.insertionIndexOf(list, "B"));

		list = Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" });
		assertEquals(6, ListTools.insertionIndexOf(list, "E"));

		list = Arrays.asList(new String[] { "B", "B", "B", "C", "D" });
		assertEquals(0, ListTools.insertionIndexOf(list, "A"));

		list = Arrays.asList(new String[] { "A", "A", "B", "B", "C", "D" });
		assertEquals(2, ListTools.insertionIndexOf(list, "A"));
	}

	public void testInsertionIndexOfListComparableNonRandomAccess() {
		List<String> list = new LinkedList<String>(Arrays.asList(new String[] { "A", "C", "D" }));
		assertEquals(1, ListTools.insertionIndexOf(list, "B"));

		list = new LinkedList<String>(Arrays.asList(new String[] { "A", "B", "C", "D" }));
		assertEquals(1, ListTools.insertionIndexOf(list, "B"));

		list = new LinkedList<String>(Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" }));
		assertEquals(1, ListTools.insertionIndexOf(list, "B"));

		list = new LinkedList<String>(Arrays.asList(new String[] { "A", "B", "B", "B", "C", "D" }));
		assertEquals(6, ListTools.insertionIndexOf(list, "E"));

		list = new LinkedList<String>(Arrays.asList(new String[] { "B", "B", "B", "C", "D" }));
		assertEquals(0, ListTools.insertionIndexOf(list, "A"));

		list = new LinkedList<String>(Arrays.asList(new String[] { "A", "A", "B", "B", "C", "D" }));
		assertEquals(0, ListTools.insertionIndexOf(list, "A"));
	}

	public void testInsertionIndexOfListObjectComparatorRandomAccess() {
		Comparator<String> c = new ReverseComparator<String>();
		List<String> list = Arrays.asList(new String[] { "D", "C", "A" });
		assertEquals(2, ListTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "A" });
		assertEquals(3, ListTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" });
		assertEquals(5, ListTools.insertionIndexOf(list, "B", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" });
		assertEquals(0, ListTools.insertionIndexOf(list, "E", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "B" });
		assertEquals(5, ListTools.insertionIndexOf(list, "A", c));

		list = Arrays.asList(new String[] { "D", "C", "B", "B", "A", "A" });
		assertEquals(6, ListTools.insertionIndexOf(list, "A", c));
	}

	public void testInsertionIndexOfListObjectComparatorNonRandomAccess() {
		Comparator<String> c = new ReverseComparator<String>();
		List<String> list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "A" }));
		assertEquals(2, ListTools.insertionIndexOf(list, "B", c));

		list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "B", "A" }));
		assertEquals(2, ListTools.insertionIndexOf(list, "B", c));

		list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" }));
		assertEquals(2, ListTools.insertionIndexOf(list, "B", c));

		list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "B", "B", "B", "A" }));
		assertEquals(0, ListTools.insertionIndexOf(list, "E", c));

		list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "B", "B", "B" }));
		assertEquals(5, ListTools.insertionIndexOf(list, "A", c));

		list = new LinkedList<String>(Arrays.asList(new String[] { "D", "C", "B", "B", "A", "A" }));
		assertEquals(4, ListTools.insertionIndexOf(list, "A", c));
	}


	// ********** list **********

	public void testListIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(this.buildStringList1(), ListTools.list(iterable));
	}

	public void testListIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(this.buildStringList1(), ListTools.list(iterable, 3));
	}

	public void testListIterator_String() {
		List<String> list = ListTools.list(this.buildStringList1().iterator());
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIterator_StringObject() {
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		list1.add("2");
		list1.add("3");

		List<Object> list2 = ListTools.<Object>list(list1.iterator());
		assertEquals(list1, list2);
	}

	public void testListIterator_Empty() {
		assertEquals(0, ListTools.list(EmptyIterator.instance()).size());
	}

	public void testListIteratorInt() {
		List<String> list = ListTools.list(this.buildStringList1().iterator(), 3);
		assertEquals(this.buildStringList1(), list);
	}

	public void testListIteratorInt_Empty() {
		assertEquals(0, ListTools.list(EmptyIterator.instance(), 5).size());
	}

	public void testListObjectArray() {
		List<String> list = ListTools.list(this.buildStringArray1());
		assertEquals(this.buildStringList1(), list);
	}


	// ********** move **********

	public void testMoveListIntIntRandomAccess() {
		List<String> list = new ArrayList<String>();
		CollectionTools.addAll(list, new String[] { "0", "1", "2", "3", "4", "5" });

		List<String> result = ListTools.move(list, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = ListTools.move(list, 0, 5);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));
	}

	public void testMoveListIntIntSequentialAccess() {
		List<String> list = new LinkedList<String>();
		CollectionTools.addAll(list, new String[] { "0", "1", "2", "3", "4", "5" });

		List<String> result = ListTools.move(list, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = ListTools.move(list, 0, 5);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));
	}

	public void testMoveListIntIntIntRandomAccess() {
		List<String> list = new ArrayList<String>(Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5" }));

		List<String> result = ListTools.move(list, 4, 2, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = ListTools.move(list, 0, 5, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 0, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 0, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 0, 0);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));
	}

	public void testMoveListIntIntIntSequentialAccess() {
		List<String> list = new LinkedList<String>(Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5" }));

		List<String> result = ListTools.move(list, 4, 2, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result.toArray()));

		result = ListTools.move(list, 0, 5, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4, 1);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result.toArray()));

		result = ListTools.move(list, 2, 4, 2);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 0, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 0, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 1, 4);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));

		result = ListTools.move(list, 1, 0, 0);
		assertSame(list, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result.toArray()));
	}


	// ********** remove elements at index **********

	public void testRemoveElementsAtIndexListIntInt() {
		List<String> list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "A", "C", "A", "D" }));
		List<String> removed = ListTools.removeElementsAtIndex(list, 3, 2);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "D" }, list.toArray()));
		assertTrue(Arrays.equals(new String[] { "C", "A" }, removed.toArray()));

		list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" }));
		removed = ListTools.removeElementsAtIndex(list, 3, 3);
		assertTrue(Arrays.equals(new String[] { "A", "B", "C" }, list.toArray()));
		assertTrue(Arrays.equals(new String[] { "D", "E", "F" }, removed.toArray()));

		list = new ArrayList<String>(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" }));
		removed = ListTools.removeElementsAtIndex(list, 0, 3);
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
		assertTrue(ListTools.removeDuplicateElements(list));
		int i = 0;
		assertEquals("zero", list.get(i++));
		assertEquals("one", list.get(i++));
		assertEquals("two", list.get(i++));
		assertEquals(i, list.size());
	}

	public void testRemoveDuplicateElementsList2() {
		List<String> list = this.buildStringVector1();
		assertFalse(ListTools.removeDuplicateElements(list));
		int i = 0;
		assertEquals("zero", list.get(i++));
		assertEquals("one", list.get(i++));
		assertEquals("two", list.get(i++));
		assertEquals(i, list.size());
	}

	public void testRemoveDuplicateElementsList_Empty() {
		List<String> list = new ArrayList<String>();
		assertFalse(ListTools.removeDuplicateElements(list));
		assertEquals(0, list.size());
	}

	public void testRemoveDuplicateElementsList_SingleElement() {
		List<String> list = new ArrayList<String>();
		list.add("zero");
		assertFalse(ListTools.removeDuplicateElements(list));
		assertEquals(1, list.size());
	}


	// ********** rotate **********

	public void testRotateList() {
		List<String> actual = ListTools.rotate(this.buildStringList1());
		List<String> expected = this.buildStringList1();
		Collections.rotate(expected, 1);
		assertEquals(expected, actual);
	}


	// ********** java.util.Collections enhancements **********

	public void testCopyListList() {
		List<String> src = this.buildStringList1();
		List<String> dest = new ArrayList<String>();
		for (String s : src) {
			dest.add(s.toUpperCase());
		}
		List<String> result = ListTools.copy(dest, src);
		assertSame(dest, result);
	}

	public void testFillListObject() {
		List<String> list = this.buildStringList1();
		List<String> result = ListTools.fill(list, "foo");
		assertSame(list, result);
		for (String string : result) {
			assertEquals("foo", string);
		}
	}

	public void testShuffleList() {
		List<String> list = this.buildStringList1();
		List<String> result = ListTools.shuffle(list);
		assertSame(list, result);
	}

	public void testShuffleListRandom() {
		List<String> list = this.buildStringList1();
		List<String> result = ListTools.shuffle(list, new Random());
		assertSame(list, result);
	}

	public void testSortList() {
		List<String> list = this.buildStringList1();
		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);
		List<String> result = ListTools.sort(list);
		assertSame(list, result);
	}

	public void testSwapListIntInt() {
		List<String> list = this.buildStringList1();
		List<String> result = ListTools.swap(list, 0, 1);
		assertSame(list, result);
		List<String> original = this.buildStringList1();
		assertEquals(original.get(0), result.get(1));
		assertEquals(original.get(1), result.get(0));
		assertEquals(original.get(2), result.get(2));
	}


	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ListTools.class);
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

	private String[] buildStringArray1() {
		return new String[] { "zero", "one", "two" };
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

	private void addToCollection1(Collection<? super String> c) {
		c.add("zero");
		c.add("one");
		c.add("two");
	}
}
