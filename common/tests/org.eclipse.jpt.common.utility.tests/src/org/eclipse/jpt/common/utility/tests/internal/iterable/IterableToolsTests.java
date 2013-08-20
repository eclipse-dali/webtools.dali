/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.comparator.ReverseComparator;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.tests.internal.ArrayToolsTests;

@SuppressWarnings("nls")
public class IterableToolsTests
	extends TestCase
{
	public IterableToolsTests(String name) {
		super(name);
	}

	public void testContainsIterableObject() {
		Collection<String> c = this.buildStringList1();
		Iterable<String> iterable = c;
		assertTrue(IterableTools.contains(iterable, "one"));
		assertFalse(IterableTools.contains(iterable, null));
		c.add(null);
		assertTrue(IterableTools.contains(iterable, null));
	}

	public void testContainsAllIterableCollection() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, this.buildStringList1()));
	}

	public void testContainsAllIterableIntCollection() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, 3, this.buildStringList1()));
	}

	public void testContainsAllIterableIterable() {
		Iterable<String> iterable1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable1, iterable2));
	}

	public void testContainsAllIterableIntIterable() {
		Iterable<String> iterable1 = this.buildStringList1();
		Iterable<String> iterable2 = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable1, 3, iterable2));
	}

	public void testContainsAllIterableIterator() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, this.buildStringList1().iterator()));
	}

	public void testContainsAllIterableIntIterator() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, 3, this.buildStringList1().iterator()));
	}

	public void testContainsAllIterableObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, this.buildObjectArray1()));
		iterable = this.buildStringList2();
		assertFalse(IterableTools.containsAll(iterable, this.buildObjectArray1()));
	}

	public void testContainsAllIterableIntObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(IterableTools.containsAll(iterable, 3, this.buildObjectArray1()));
		iterable = this.buildStringList2();
		assertFalse(IterableTools.containsAll(iterable, 3, this.buildObjectArray1()));
	}

	public void testElementsAreDifferentIterableIterable() {
		List<String> list1 = new ArrayList<String>();
		list1.add("1000");
		list1.add("2000");
		list1.add("3000");
		list1.add("4000");

		List<String> list2 = new ArrayList<String>();

		assertTrue(IterableTools.elementsAreDifferent(list1, list2));
		assertFalse(IterableTools.elementsAreEqual(list1, list2));
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
		assertFalse(IterableTools.elementsAreIdentical(list1, list2));
		assertFalse(IterableTools.elementsAreDifferent(list1, list2));
		assertTrue(IterableTools.elementsAreEqual(list1, list2));
	}

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
		assertTrue(IterableTools.elementsAreIdentical(iterable1, iterable2));
		assertTrue(IterableTools.elementsAreEqual(iterable1, iterable2));
	}

	public void testElementsAreNotIdenticalIterableIterable() {
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
		assertFalse(IterableTools.elementsAreNotIdentical(iterable1, iterable2));
		assertTrue(IterableTools.elementsAreEqual(iterable1, iterable2));
	}

	public void testExecuteParmCommand() {
		List<String> list = this.buildStringList1();
		ArrayToolsTests.ConcatenateClosure closure = new ArrayToolsTests.ConcatenateClosure();
		IterableTools.execute(list, closure);
		assertEquals("zeroonetwo", closure.string);
	}

	public void testExecuteInterruptibleParmCommand() throws Exception {
		List<String> list = this.buildStringList1();
		ArrayToolsTests.InterruptibleConcatenateClosure closure = new ArrayToolsTests.InterruptibleConcatenateClosure();
		IterableTools.execute(list, closure);
		assertEquals("zeroonetwo", closure.string);
	}

	public void testGetIterableInt() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		String o = IterableTools.get(iterable, 1);
		assertEquals("one", o);
		list.add(null);
		o = IterableTools.get(iterable, 3);
		assertNull(o);
	}

	public void testHashCodeIterable1() {
		Iterable<String> iterable = null;
		assertEquals(0, IterableTools.hashCode(iterable));
	}

	public void testHashCodeIterable2() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		int hashCode = IterableTools.hashCode(iterable);
		assertEquals(list.hashCode(), hashCode);

		list.add(null);
		hashCode = IterableTools.hashCode(iterable);
		assertEquals(list.hashCode(), hashCode);
	}

	public void testIndexOfIterableObject_String() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(1, IterableTools.indexOf(iterable, "one"));
	}

	public void testIndexOfIterableObjectInt() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(1, IterableTools.indexOf(iterable, "one", -11));
		assertEquals(1, IterableTools.indexOf(iterable, "one", 1));
		assertEquals(-1, IterableTools.indexOf(iterable, "one", 2));
		assertEquals(-1, IterableTools.indexOf(iterable, "one", 22));
	}

	public void testIsEmptyIterable() {
		assertFalse(IterableTools.isEmpty(buildObjectList1()));
		assertTrue(IterableTools.isEmpty(EmptyIterable.instance()));
	}
	
	public void testIterableObjectArray() {
		String[] strings = this.buildStringArray1();
		int i = 0;
		for (String string : IterableTools.iterable(strings)) {
			assertEquals(strings[i++], string);
		}
	}

	public void testLastIterable1() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		assertEquals("two", IterableTools.last(iterable));
		list.add(null);
		assertEquals(null, IterableTools.last(iterable));
	}

	public void testLastIterable2() {
		Iterable<String> iterable = new ArrayList<String>();
		boolean exCaught = false;
		try {
			IterableTools.last(iterable);
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testLastIndexOfIterableObject() {
		List<String> list = this.buildStringList1();
		Iterable<String> iterable = list;
		assertEquals(1, IterableTools.lastIndexOf(iterable, "one"));
		list.add(null);
		assertEquals(list.size() - 1, IterableTools.lastIndexOf(iterable, null));
	}

	public void testLastIndexOfIterableObjectInt() {
		Iterable<String> iterable = this.buildStringList1();
		assertEquals(-1, IterableTools.lastIndexOf(iterable, "one", -11));
		assertEquals(1, IterableTools.lastIndexOf(iterable, "one", 1));
		assertEquals(1, IterableTools.lastIndexOf(iterable, "one", 2));
		assertEquals(1, IterableTools.lastIndexOf(iterable, "one", 22));
	}

	public void testSizeIterable() {
		Iterable<Object> iterable = this.buildObjectList1();
		assertEquals(3, IterableTools.size(iterable));
	}

	public void testSortIterable() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Iterable<String> iterable1 = list;
		Iterable<String> iterable2 = IterableTools.<String>sort(iterable1);
		assertTrue(IterableTools.elementsAreEqual(ss, iterable2));
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
		Iterable<String> iterable2 = IterableTools.<String>sort(iterable1, 77);
		assertTrue(IterableTools.elementsAreEqual(ss, iterable2));
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
		Iterable<String> iterable2 = IterableTools.<String>sort(iterable1, new ReverseComparator<String>());
		assertTrue(IterableTools.elementsAreEqual(ss, iterable2));
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
		Iterable<String> iterable2 = IterableTools.<String>sort(iterable1, new ReverseComparator<String>(), 77);
		assertTrue(IterableTools.elementsAreEqual(ss, iterable2));
	}

	public void testTransformIterableTransformer() {
		List<String> list = Arrays.asList(new String[] { "zero", "one", "two" });
		Iterable<String> actual = IterableTools.transform(list, ArrayToolsTests.UPPER_CASE_TRANSFORMER);
		List<Object> expected = Arrays.asList(new Object[] { "ZERO", "ONE", "TWO" });
		assertTrue(IterableTools.elementsAreEqual(expected, actual));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(IterableTools.class);
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

	private Object[] buildObjectArray1() {
		return new Object[] { "zero", "one", "two" };
	}

	private String[] buildStringArray1() {
		return new String[] { "zero", "one", "two" };
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

	private List<String> buildStringList2() {
		List<String> l = new ArrayList<String>();
		this.addToCollection2(l);
		return l;
	}

	private void addToCollection1(Collection<? super String> c) {
		c.add("zero");
		c.add("one");
		c.add("two");
	}

	private void addToCollection2(Collection<? super String> c) {
		c.add("three");
		c.add("four");
		c.add("five");
	}
}
