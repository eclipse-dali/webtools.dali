/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.enumeration;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.comparator.ReverseComparator;
import org.eclipse.jpt.common.utility.internal.enumeration.EnumerationTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

@SuppressWarnings("nls")
public class EnumerationToolsTests
	extends TestCase
{
	public EnumerationToolsTests(String name) {
		super(name);
	}


	// ********** contains **********

	public void testContainsEnumerationObject_String() {
		Vector<String> v = this.buildStringVector1();
		assertTrue(EnumerationTools.contains(v.elements(), "one"));
		assertFalse(EnumerationTools.contains(v.elements(), null));
		v.add(null);
		assertTrue(EnumerationTools.contains(v.elements(), null));
	}

	public void testContainsEnumerationObject_Object() {
		Vector<Object> c = new Vector<Object>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		String one = "one";
		assertTrue(EnumerationTools.contains(c.elements(), one));
		assertFalse(EnumerationTools.contains(c.elements(), null));
		c.add(null);
		assertTrue(EnumerationTools.contains(c.elements(), null));
	}


	// ********** contains all **********

	public void testContainsAllEnumerationCollection() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1));
		Vector<String> v2 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), v2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v2));
	}

	public void testContainsAllEnumerationIntCollection() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), v1));
		Vector<String> v2 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), v2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), v1.size(), v2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), v2));
	}

	public void testContainsAllEnumerationIterable() {
		Vector<String> v1 = this.buildStringVector1();
		Iterable<String> stream1 = v1;
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream1));
		Vector<String> v2 = this.buildStringVector1();
		Iterable<String> stream2 = v2;
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), stream2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream2));
	}

	public void testContainsAllEnumerationIntIterable() {
		Vector<String> v1 = this.buildStringVector1();
		Iterable<String> stream1 = v1;
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream1));
		Vector<String> v2 = this.buildStringVector1();
		Iterable<String> stream2 = v2;
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));
	}

	public void testContainsAllEnumerationEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Enumeration<String> stream1 = v1.elements();
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream1));
		Vector<String> v2 = this.buildStringVector1();
		Enumeration<String> stream2 = v2.elements();
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), stream2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), stream2));
	}

	public void testContainsAllEnumerationIntEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Enumeration<String> stream1 = v1.elements();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream1));
		Vector<String> v2 = this.buildStringVector1();
		Enumeration<String> stream2 = v2.elements();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));

		v2.add(null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), stream2));
	}

	public void testContainsAllEnumerationArray() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1));
		String[] v2 = this.buildStringVector1().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertTrue(EnumerationTools.containsAll(v1.elements(), (Object[]) v2));

		v2 = ArrayTools.add(v2, null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), (Object[]) v2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), (Object[]) v2));
	}

	public void testContainsAllEnumerationIntArray() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), v1));
		String[] v2 = this.buildStringVector1().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), (Object[]) v2));

		v2 = ArrayTools.add(v2, null);
		assertFalse(EnumerationTools.containsAll(v1.elements(), v1.size(), (Object[]) v2));
		v1.add(null);
		assertTrue(EnumerationTools.containsAll(v1.elements(), v1.size(), (Object[]) v2));
	}


	// ********** elements are different/equal/identical **********

	public void testElementsAreDifferentEnumerationEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Vector<String> v2 = this.buildStringVector1();
		assertFalse(EnumerationTools.elementsAreDifferent(v1.elements(), v2.elements()));

		v2.add(null);
		assertTrue(EnumerationTools.elementsAreDifferent(v1.elements(), v2.elements()));
		v1.add(null);
		assertFalse(EnumerationTools.elementsAreDifferent(v1.elements(), v2.elements()));
	}

	public void testElementsAreEqualEnumerationEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Vector<String> v2 = this.buildStringVector1();
		assertTrue(EnumerationTools.elementsAreEqual(v1.elements(), v2.elements()));

		v2.add(null);
		assertFalse(EnumerationTools.elementsAreEqual(v1.elements(), v2.elements()));
		v1.add(null);
		assertTrue(EnumerationTools.elementsAreEqual(v1.elements(), v2.elements()));

		v2.setElementAt("foo", 1);
		assertFalse(EnumerationTools.elementsAreEqual(v1.elements(), v2.elements()));
		v1.setElementAt("foo", 1);
		assertTrue(EnumerationTools.elementsAreEqual(v1.elements(), v2.elements()));
	}

	public void testElementsAreIdenticalEnumerationEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Vector<String> v2 = new Vector<String>(v1);
		assertTrue(EnumerationTools.elementsAreIdentical(v1.elements(), v2.elements()));

		v2.add(null);
		assertFalse(EnumerationTools.elementsAreIdentical(v1.elements(), v2.elements()));
		v1.add(null);
		assertTrue(EnumerationTools.elementsAreIdentical(v1.elements(), v2.elements()));

		String foo = "foo";
		v2.setElementAt(foo, 1);
		assertFalse(EnumerationTools.elementsAreIdentical(v1.elements(), v2.elements()));
		v1.setElementAt(foo, 1);
		assertTrue(EnumerationTools.elementsAreIdentical(v1.elements(), v2.elements()));
	}

	public void testElementsAreNotIdenticalEnumerationEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		Vector<String> v2 = new Vector<String>(v1);
		assertFalse(EnumerationTools.elementsAreNotIdentical(v1.elements(), v2.elements()));

		v2.add(null);
		assertTrue(EnumerationTools.elementsAreNotIdentical(v1.elements(), v2.elements()));
		v1.add(null);
		assertFalse(EnumerationTools.elementsAreNotIdentical(v1.elements(), v2.elements()));

		String foo = "foo";
		v2.setElementAt(foo, 1);
		assertTrue(EnumerationTools.elementsAreNotIdentical(v1.elements(), v2.elements()));
		v1.setElementAt(foo, 1);
		assertFalse(EnumerationTools.elementsAreNotIdentical(v1.elements(), v2.elements()));
	}


	// ********** empty **********

	public void testEmptyEnumeration() {
		assertFalse(EnumerationTools.emptyEnumeration().hasMoreElements());
	}


	// ********** enumeration **********

	public void testEnumerationIterator() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(EnumerationTools.elementsAreIdentical(v1.elements(), EnumerationTools.enumeration(v1.iterator())));
	}


	// ********** get **********

	public void testGetEnumerationInt() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals("zero", EnumerationTools.get(v1.elements(), 0));
		assertEquals("one", EnumerationTools.get(v1.elements(), 1));
		assertEquals("two", EnumerationTools.get(v1.elements(), 2));
	}

	public void testGetEnumerationInt_Exception() {
		Vector<String> v1 = this.buildStringVector1();
		boolean exCaught = false;
		try {
			Object at = EnumerationTools.get(v1.elements(), 55);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** hash code **********

	public void testHashCodeEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals(v1.hashCode(), EnumerationTools.hashCode(v1.elements()));
	}


	// ********** index of **********

	public void testIndexOfEnumerationObject() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals(1, EnumerationTools.indexOf(v1.elements(), "one"));
	}

	public void testIndexOfEnumerationObjectInt() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals(1, EnumerationTools.indexOf(v1.elements(), "one", -1));
		assertEquals(1, EnumerationTools.indexOf(v1.elements(), "one", 1));
		assertEquals(2, EnumerationTools.indexOf(v1.elements(), "two", 1));
		assertEquals(-1, EnumerationTools.indexOf(v1.elements(), "one", 2));
		assertEquals(-1, EnumerationTools.indexOf(v1.elements(), "one", 22));
	}

	public void testIndexOfEnumerationObject_Null() {
		Vector<String> v1 = this.buildStringVector1();
		v1.add(null);
		assertEquals(v1.size() - 1, EnumerationTools.indexOf(v1.elements(), null));
	}

	public void testIndexOfEnumerationObjectInt_Null() {
		Vector<String> v1 = this.buildStringVector1();
		v1.add(null);
		assertEquals(v1.size() - 1, EnumerationTools.indexOf(v1.elements(), null, -1));
		assertEquals(v1.size() - 1, EnumerationTools.indexOf(v1.elements(), null, 2));
		assertEquals(v1.size() - 1, EnumerationTools.indexOf(v1.elements(), null, v1.size() - 1));
		assertEquals(-1, EnumerationTools.indexOf(v1.elements(), null, 22));
	}


	// ********** last index of **********

	public void testLastIndexOfEnumerationObject() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals(1, EnumerationTools.lastIndexOf(v1.elements(), "one"));
		v1.add(null);
		assertEquals(v1.size() - 1, EnumerationTools.lastIndexOf(v1.elements(), null));
	}

	public void testLastIndexOfEnumerationObjectInt() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals(-1, EnumerationTools.lastIndexOf(v1.elements(), "one", -1));
		assertEquals(1, EnumerationTools.lastIndexOf(v1.elements(), "one", 1));
		assertEquals(-1, EnumerationTools.lastIndexOf(v1.elements(), "two", 1));
		assertEquals(1, EnumerationTools.lastIndexOf(v1.elements(), "one", 2));
		assertEquals(1, EnumerationTools.lastIndexOf(v1.elements(), "one", 22));
	}

	public void testLastIndexOfEnumerationObject_Empty() {
		assertEquals(-1, EnumerationTools.lastIndexOf(EnumerationTools.emptyEnumeration(), "foo"));
	}

	public void testLastIndexOfEnumerationObjectInt_Null() {
		Vector<String> v1 = this.buildStringVector1();
		v1.add(null);
		assertEquals(-1, EnumerationTools.lastIndexOf(v1.elements(), null, -1));
		assertEquals(-1, EnumerationTools.lastIndexOf(v1.elements(), null, 2));
		assertEquals(v1.size() - 1, EnumerationTools.lastIndexOf(v1.elements(), null, v1.size() - 1));
		assertEquals(v1.size() - 1, EnumerationTools.lastIndexOf(v1.elements(), null, 22));
	}


	// ********** last **********

	public void testLastEnumeration1() {
		Vector<String> v1 = this.buildStringVector1();
		assertEquals("two", EnumerationTools.last(v1.elements()));
		v1.add(null);
		assertEquals(null, EnumerationTools.last(v1.elements()));
	}

	public void testLastEnumeration2() {
		Vector<String> v1 = new Vector<String>();
		boolean exCaught = false;
		try {
			EnumerationTools.last(v1.elements());
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** size **********

	public void testSizeEnumeration() {
		assertEquals(3, EnumerationTools.size(this.buildStringVector1().elements()));
	}

	public void testIsEmptyEnumeration() {
		assertTrue(EnumerationTools.isEmpty(EnumerationTools.emptyEnumeration()));
		assertFalse(EnumerationTools.isEmpty(this.buildStringVector1().elements()));
	}


	// ********** sort **********

	public void testSortEnumeration() {
		Vector<String> list = new Vector<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Enumeration<String> enumeration1 = list.elements();
		Enumeration<String> enumeration2 = EnumerationTools.<String>sort(enumeration1);
		assertTrue(EnumerationTools.elementsAreEqual(EnumerationTools.enumeration(ss), enumeration2));
	}

	public void testSortEnumerationInt() {
		Vector<String> list = new Vector<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>();
		ss.addAll(list);

		Enumeration<String> enumeration1 = list.elements();
		Enumeration<String> enumeration2 = EnumerationTools.<String>sort(enumeration1, 77);
		assertTrue(EnumerationTools.elementsAreEqual(EnumerationTools.enumeration(ss), enumeration2));
	}

	public void testSortEnumerationComparator() {
		Vector<String> list = new Vector<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Enumeration<String> enumeration1 = list.elements();
		Enumeration<String> enumeration2 = EnumerationTools.<String>sort(enumeration1, new ReverseComparator<String>());
		assertTrue(EnumerationTools.elementsAreEqual(EnumerationTools.enumeration(ss), enumeration2));
	}

	public void testSortEnumerationComparatorInt() {
		Vector<String> list = new Vector<String>();
		list.add("0");
		list.add("2");
		list.add("3");
		list.add("1");

		SortedSet<String> ss = new TreeSet<String>(new ReverseComparator<String>());
		ss.addAll(list);

		Enumeration<String> enumeration1 = list.elements();
		Enumeration<String> enumeration2 = EnumerationTools.<String>sort(enumeration1, new ReverseComparator<String>(), 77);
		assertTrue(EnumerationTools.elementsAreEqual(EnumerationTools.enumeration(ss), enumeration2));
	}


	// ********** iterator **********

	public void testIteratorEnumeration() {
		Vector<String> v1 = this.buildStringVector1();
		assertTrue(IteratorTools.elementsAreIdentical(EnumerationTools.iterator(v1.elements()), v1.iterator()));
	}


	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(EnumerationTools.class);
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

	private Vector<String> buildStringVector1() {
		Vector<String> v = new Vector<String>();
		this.addToCollection1(v);
		return v;
	}

	private void addToCollection1(Collection<? super String> c) {
		c.add("zero");
		c.add("one");
		c.add("two");
	}
}
