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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.internal.Range;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

@SuppressWarnings("nls")
public class ArrayToolsTests extends TestCase {

	public ArrayToolsTests(String name) {
		super(name);
	}


	// ********** instantiation **********

	public void testNewArrayObjectArray() {
		String[] array1 = new String[2];
		String[] array2 = ArrayTools.newArray(array1);
		array2[0] = "foo";
		array2[1] = "bar";
		assertEquals(String.class, array2.getClass().getComponentType());
		assertEquals(2, array2.length);
	}

	public void testNewArrayObjectArrayInt() {
		String[] array1 = new String[2];
		String[] array2 = ArrayTools.newArray(array1, 5);
		array2[0] = "foo";
		array2[4] = "bar";
		assertEquals(String.class, array2.getClass().getComponentType());
		assertEquals(5, array2.length);
	}

	public void testNewArrayObjectArrayInt_Exception() {
		String[] array1 = new String[2];
		Object[] array2 = ArrayTools.newArray(array1, 5);
		boolean exCaught = false;
		try {
			array2[1] = Integer.valueOf(7);
			fail("bogus array: " + Arrays.toString(array2));
		} catch (ArrayStoreException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testComponentType() {
		String[] array = new String[2];
		Class<? extends String> javaClass = ArrayTools.componentType(array);
		assertEquals(String.class, javaClass);
	}

	public void testNewArrayClassInt() {
		String[] array = ArrayTools.newArray(String.class, 5);
		array[0] = "foo";
		array[4] = "bar";
		assertEquals(String.class, array.getClass().getComponentType());
		assertEquals(5, array.length);
	}

	public void testNewArrayClassInt_Exception() {
		Object[] array = ArrayTools.newArray(String.class, 5);
		boolean exCaught = false;
		try {
			array[1] = Integer.valueOf(7);
			fail("bogus array: " + Arrays.toString(array));
		} catch (ArrayStoreException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNewArrayClassInt_Primitive() {
		boolean exCaught = false;
		try {
			Object[] array = ArrayTools.newArray(int.class, 5);
			fail("bogus array: " + Arrays.toString(array));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** conversion **********

	public void testArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = ArrayTools.array(iterable);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableInt() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = ArrayTools.array(iterable, 3);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray_String() {
		Iterable<String> iterable = this.buildStringList1();
		String[] a = ArrayTools.array(iterable, new String[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableObjectArray_Object() {
		Iterable<String> iterable = this.buildStringList1();
		Object[] a = ArrayTools.array(iterable, new Object[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterableIntObjectArray() {
		Iterable<String> iterable = this.buildStringList1();
		String[] a = ArrayTools.array(iterable, 3, new String[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterator() {
		Object[] a = ArrayTools.array(this.buildStringList1().iterator());
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIterator_Empty() {
		Object[] a = ArrayTools.array(EmptyIterator.instance());
		assertEquals(0, a.length);
	}

	public void testArrayIteratorInt() {
		Object[] a = ArrayTools.array(this.buildStringList1().iterator(), 3);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorInt_Empty() {
		Object[] a = ArrayTools.array(EmptyIterator.instance(), 3);
		assertEquals(0, a.length);
	}

	public void testArrayIteratorObjectArray_String() {
		String[] a = ArrayTools.array(this.buildStringList1().iterator(), new String[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorObjectArray_Empty() {
		String[] a = ArrayTools.array(EmptyIterator.<String>instance(), new String[0]);
		assertEquals(0, a.length);
	}

	public void testArrayIteratorObjectArray_Empty_ClearArray() {
		String[] a = ArrayTools.array(EmptyIterator.<String>instance(), new String[5]);
		assertEquals(5, a.length);
		assertNull(a[0]);
	}

	public void testArrayIteratorObjectArray_Object() {
		Object[] a = ArrayTools.array(this.buildStringList1().iterator(), new Object[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorIntObjectArray() {
		String[] a = ArrayTools.array(this.buildStringList1().iterator(), 3, new String[0]);
		assertEquals(3, a.length);
		assertTrue(ArrayTools.containsAll(a, this.buildStringList1().iterator()));
	}

	public void testArrayIteratorIntObjectArray_Empty() {
		String[] a = ArrayTools.array(EmptyIterator.<String>instance(), 3, new String[0]);
		assertEquals(0, a.length);
	}


	// ********** add **********

	public void testAddObjectArrayObject_Object() {
		Object[] a = ArrayTools.add(this.buildObjectArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(ArrayTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayObject_String() {
		String[] a = ArrayTools.add(this.buildStringArray1(), "twenty");
		assertEquals(4, a.length);
		assertTrue(ArrayTools.contains(a, "twenty"));
		assertEquals("twenty", a[a.length-1]);
	}

	public void testAddObjectArrayObject_EmptyArray() {
		String[] a = new String[0];
		a = ArrayTools.add(a, "twenty");
		assertEquals(1, a.length);
		assertTrue(ArrayTools.contains(a, "twenty"));
		assertEquals("twenty", a[0]);
	}

	public void testAddObjectArrayIntObject_Object() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = ArrayTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_String() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = ArrayTools.add(a, 2, "X");
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_End() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = ArrayTools.add(a, 4, "X");
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "c", "d", "X" }, a));
	}

	public void testAddObjectArrayIntObject_Zero() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = ArrayTools.add(a, 0, "X");
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "X", "a", "b", "c", "d" }, a));
	}

	public void testAddObjectArrayIntObject_Exception() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		boolean exCaught = false;
		try {
			a = ArrayTools.add(a, 33, "X");
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddCharArrayChar() {
		char[] a = ArrayTools.add(this.buildCharArray(), 'd');
		assertEquals(4, a.length);
		assertTrue(ArrayTools.contains(a, 'd'));
	}

	public void testAddCharArrayChar_Empty() {
		char[] a = new char[0];
		a = ArrayTools.add(a, 'd');
		assertEquals(1, a.length);
		assertTrue(ArrayTools.contains(a, 'd'));
		assertTrue(Arrays.equals(new char[] { 'd' }, a));
	}

	public void testAddCharArrayIntChar() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.add(a, 2, 'X');
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'X', 'c', 'd' }, a));
	}

	public void testAddCharArrayIntChar_Zero() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.add(a, 0, 'X');
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'X', 'a', 'b', 'c', 'd' }, a));
	}

	public void testAddCharArrayIntChar_End() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.add(a, 4, 'X');
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'X' }, a));
	}

	public void testAddIntArrayInt() {
		int[] a = ArrayTools.add(this.buildIntArray(), 30);
		assertEquals(4, a.length);
		assertTrue(ArrayTools.contains(a, 30));
	}

	public void testAddIntArrayInt_Empty() {
		int[] a = new int[0];
		a = ArrayTools.add(a, 30);
		assertEquals(1, a.length);
		assertTrue(ArrayTools.contains(a, 30));
		assertTrue(Arrays.equals(new int[] { 30 }, a));
	}

	public void testAddIntArrayIntInt() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.add(a, 2, 99);
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 99, 3, 4 }, a));
	}

	public void testAddIntArrayIntInt_Zero() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.add(a, 0, 99);
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 99, 1, 2, 3, 4 }, a));
	}

	public void testAddIntArrayIntInt_End() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.add(a, 4, 99);
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 99 }, a));
	}


	// ********** add all **********

	public void testAddAllObjectArrayCollection_String() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_Object() {
		Object[] a = this.buildObjectArray1();
		Collection<String> c = this.buildStringList2();
		Object[] newArray = ArrayTools.addAll(a, c);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_EmptyArray() {
		String[] a = new String[0];
		Collection<String> c = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, c);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayCollection_EmptyCollection() {
		String[] a = this.buildStringArray1();
		Collection<String> c = new ArrayList<String>();
		String[] newArray = ArrayTools.addAll(a, c);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntCollection_String() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 1, c);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIntCollection_String_End() {
		String[] a = this.buildStringArray1();
		Collection<String> c = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 3, c);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIntCollection_EmptyArray() {
		String[] a = new String[0];
		Collection<String> c = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 0, c);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, c));
	}

	public void testAddAllObjectArrayIntCollection_EmptyArray_Exception() {
		String[] a = new String[0];
		Collection<String> c = this.buildStringList2();
		boolean exCaught = false;
		try {
			String[] newArray = ArrayTools.addAll(a, 3, c);
			fail("bogus array: " + Arrays.toString(newArray));
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddAllObjectArrayIntCollection_EmptyCollection() {
		String[] a = this.buildStringArray1();
		Collection<String> c = new ArrayList<String>();
		String[] newArray = ArrayTools.addAll(a, 1, c);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterable_String() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 1, iterable);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterable_EmptyArray() {
		String[] a = new String[0];
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 0, iterable);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterable_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = new ArrayList<String>();
		String[] newArray = ArrayTools.addAll(a, 1, iterable);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterableInt_String() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 1, iterable, 3);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterableInt_EmptyArray() {
		String[] a = new String[0];
		Iterable<String> iterable = this.buildStringList2();
		String[] newArray = ArrayTools.addAll(a, 0, iterable, 3);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, iterable));
	}

	public void testAddAllObjectArrayIntIterableInt_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = new ArrayList<String>();
		String[] newArray = ArrayTools.addAll(a, 1, iterable, 0);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIterator_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = ArrayTools.addAll(a, 1, iterator);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIterator_EmptyArray() {
		String[] a = new String[0];
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = ArrayTools.addAll(a, 0, iterator);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIterator_EmptyIterable() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = ArrayTools.addAll(a, 1, iterator);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIntIteratorInt_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = ArrayTools.addAll(a, 1, iterator, 3);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIteratorInt_EmptyArray() {
		String[] a = new String[0];
		Iterator<String> iterator = this.buildStringList2().iterator();
		String[] newArray = ArrayTools.addAll(a, 0, iterator, 3);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList2()));
	}

	public void testAddAllObjectArrayIntIteratorInt_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = ArrayTools.addAll(a, 1, iterator, 0);

		assertEquals(3, newArray.length);
	}

	public void testAddAllObjectArrayIterable() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList1();
		String[] newArray = ArrayTools.addAll(a, iterable);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterableInt() {
		String[] a = this.buildStringArray1();
		Iterable<String> iterable = this.buildStringList1();
		String[] newArray = ArrayTools.addAll(a, iterable, 33);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator_String() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = this.buildStringList1().iterator();
		String[] newArray = ArrayTools.addAll(a, iterator);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIterator_Object() {
		String[] a = this.buildStringArray1();
		Iterator<Object> iterator = this.buildObjectList1().iterator();
		Object[] newArray = ArrayTools.addAll(a, iterator);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildObjectList1()));
	}

	public void testAddAllObjectArrayIterator_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = ArrayTools.addAll(a, iterator);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayIteratorInt() {
		String[] a = this.buildStringArray1();
		Iterator<Object> iterator = this.buildObjectList1().iterator();
		Object[] newArray = ArrayTools.addAll(a, iterator, 3);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildObjectList1()));
	}

	public void testAddAllObjectArrayIteratorInt_EmptyIterator() {
		String[] a = this.buildStringArray1();
		Iterator<String> iterator = EmptyIterator.instance();
		String[] newArray = ArrayTools.addAll(a, iterator, 0);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, this.buildStringList1()));
	}

	public void testAddAllObjectArrayObjectArray_Object() {
		Object[] a1 = this.buildObjectArray1();
		Object[] a2 = this.buildObjectArray2();
		Object[] newArray = ArrayTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, a1));
		assertTrue(ArrayTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_String() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = this.buildStringArray2();
		String[] newArray = ArrayTools.addAll(a1, a2);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, (Object[]) a1));
		assertTrue(ArrayTools.containsAll(newArray, (Object[]) a2));
	}

	public void testAddAllObjectArrayObjectArray_ObjectString() {
		Object[] a1 = this.buildObjectArray1();
		String[] a2 = this.buildStringArray2();
		Object[] newArray = ArrayTools.addAll(a1, (Object[]) a2);

		assertEquals(6, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, a1));
		assertTrue(ArrayTools.containsAll(newArray, (Object[]) a2));
	}

	public void testAddAllObjectArrayObjectArray_EmptyArray1() {
		Object[] a1 = new Object[0];
		Object[] a2 = this.buildObjectArray2();
		Object[] newArray = ArrayTools.addAll(a1, a2);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, a2));
	}

	public void testAddAllObjectArrayObjectArray_EmptyArray2() {
		Object[] a1 = this.buildObjectArray1();
		Object[] a2 = new Object[0];
		Object[] newArray = ArrayTools.addAll(a1, a2);

		assertEquals(3, newArray.length);
		assertTrue(ArrayTools.containsAll(newArray, a1));
	}

	public void testAddAllObjectArrayIntObjectArray_Object() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = ArrayTools.addAll(a, 2, new Object[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_String() {
		String[] a = new String[] { "a", "b", "c", "d" };
		a = ArrayTools.addAll(a, 2, new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new String[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_ObjectString() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = ArrayTools.addAll(a, 2, (Object[]) new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "X", "X", "X", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_End() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = ArrayTools.addAll(a, 4, (Object[]) new String[] { "X", "X", "X" });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d", "X", "X", "X" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_Zero() {
		Object[] a = new Object[0];
		a = ArrayTools.addAll(a, 0, (Object[]) new String[] { "X", "X", "X" });
		assertEquals(3, a.length);
		assertTrue(ArrayTools.contains(a, "X"));
		assertTrue(Arrays.equals(new Object[] { "X", "X", "X" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_EmptyArray2() {
		Object[] a = new Object[] { "a", "b", "c", "d" };
		a = ArrayTools.addAll(a, 4, (Object[]) new String[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d" }, a));
	}

	public void testAddAllObjectArrayIntObjectArray_EmptyArray1() {
		Object[] a = new String[0];
		a = ArrayTools.addAll(a, 0, new Object[] { "a", "b", "c", "d" });
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new Object[] { "a", "b", "c", "d" }, a));
	}

	public void testAddAllCharArrayCharArray() {
		char[] a = ArrayTools.addAll(this.buildCharArray(), new char[] { 'd', 'e' });
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 'd'));
		assertTrue(ArrayTools.contains(a, 'e'));
	}

	public void testAddAllCharArrayCharArray_EmptyArray2() {
		char[] a = ArrayTools.addAll(this.buildCharArray(), new char[0]);
		assertEquals(3, a.length);
	}

	public void testAddAllCharArrayCharArrayEmptyArray1() {
		char[] a = ArrayTools.addAll(new char[0], new char[] { 'd', 'e' });
		assertEquals(2, a.length);
		assertTrue(ArrayTools.contains(a, 'd'));
		assertTrue(ArrayTools.contains(a, 'e'));
	}

	public void testAddAllCharArrayIntCharArray() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.addAll(a, 2, new char[] { 'X', 'X', 'X' });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'X', 'X', 'X', 'c', 'd' }, a));
	}

	public void testAddAllCharArrayIntCharArray_End() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.addAll(a, 4, new char[] { 'X', 'X', 'X' });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'X', 'X', 'X' }, a));
	}

	public void testAddAllCharArrayIntCharArray_EmptyArray1() {
		char[] a = new char[0];
		a = ArrayTools.addAll(a, 0, new char[] { 'X', 'X', 'X' });
		assertEquals(3, a.length);
		assertTrue(ArrayTools.contains(a, 'X'));
		assertTrue(Arrays.equals(new char[] { 'X', 'X', 'X' }, a));
	}

	public void testAddAllCharArrayIntCharArray_EmptyArray2() {
		char[] a = new char[] { 'a', 'b', 'c', 'd' };
		a = ArrayTools.addAll(a, 2, new char[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd' }, a));
	}

	public void testAddAllIntArrayIntArray() {
		int[] a = ArrayTools.addAll(this.buildIntArray(), new int[] { 30, 40 });
		assertEquals(5, a.length);
		assertTrue(ArrayTools.contains(a, 30));
		assertTrue(ArrayTools.contains(a, 40));
	}

	public void testAddAllIntArrayIntArray_EmptyArray2() {
		int[] a = ArrayTools.addAll(this.buildIntArray(), new int[0]);
		assertEquals(3, a.length);
	}

	public void testAddAllIntArrayIntArray_EmptyArray1() {
		int[] a = ArrayTools.addAll(new int[0], new int[] { 30, 40 });
		assertEquals(2, a.length);
		assertTrue(ArrayTools.contains(a, 30));
		assertTrue(ArrayTools.contains(a, 40));
	}

	public void testAddAllIntArrayIntIntArray() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.addAll(a, 2, new int[] { 99, 99, 99 });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 99, 99, 99, 3, 4 }, a));
	}

	public void testAddAllIntArrayIntIntArray_End() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.addAll(a, 4, new int[] { 99, 99, 99 });
		assertEquals(7, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4, 99, 99, 99 }, a));
	}

	public void testAddAllIntArrayIntIntArray_EmptyArray2() {
		int[] a = new int[] { 1, 2, 3, 4 };
		a = ArrayTools.addAll(a, 2, new int[0]);
		assertEquals(4, a.length);
		assertTrue(Arrays.equals(new int[] { 1, 2, 3, 4 }, a));
	}

	public void testAddAllIntArrayIntIntArray_EmptyArray1() {
		int[] a = new int[0];
		a = ArrayTools.addAll(a, 0, new int[] { 99, 99, 99 });
		assertEquals(3, a.length);
		assertTrue(ArrayTools.contains(a, 99));
		assertTrue(Arrays.equals(new int[] { 99, 99, 99 }, a));
	}


	// ********** clear **********

	public void testClearObjectArray() {
		String[] a = this.buildStringArray1();
		assertEquals(3, a.length);
		a = ArrayTools.clear(a);
		assertEquals(0, a.length);
	}

	public void testClearObjectArray_Empty() {
		String[] a = new String[0];
		assertEquals(0, a.length);
		a = ArrayTools.clear(a);
		assertEquals(0, a.length);
	}


	// ********** concatenate **********

	public void testConcatenateObjectArrayArray() {
		String[] aArray = new String[] { "a", "b", "c", "d" };
		String[] eArray = new String[] { "e", "f", "g", "h" };
		String[] iArray = new String[] { "i", "j", "k", "l" };

		String[] expected = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" };
		String[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testConcatenateObjectArrayArray_Empty() {
		String[] aArray = new String[] {  };
		String[] eArray = new String[0];
		String[] iArray = new String[0];

		String[] expected = new String[0];
		String[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertEquals(0, actual.length);
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testConcatenateCharArrayArray() {
		char[] aArray = new char[] { 'a', 'b', 'c', 'd' };
		char[] eArray = new char[] { 'e', 'f', 'g', 'h' };
		char[] iArray = new char[] { 'i', 'j', 'k', 'l' };

		char[] expected = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l' };
		char[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testConcatenateCharArrayArray_Empty() {
		char[] aArray = new char[] {  };
		char[] eArray = new char[0];
		char[] iArray = new char[0];

		char[] expected = new char[0];
		char[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertEquals(0, actual.length);
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testConcatenateIntArrayArray() {
		int[] aArray = new int[] { 'a', 'b', 'c', 'd' };
		int[] eArray = new int[] { 'e', 'f', 'g', 'h' };
		int[] iArray = new int[] { 'i', 'j', 'k', 'l' };

		int[] expected = new int[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l' };
		int[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testConcatenateIntArrayArray_Empty() {
		int[] aArray = new int[] {  };
		int[] eArray = new int[0];
		int[] iArray = new int[0];

		int[] expected = new int[0];
		int[] actual = ArrayTools.concatenate(aArray, eArray, iArray);
		assertEquals(0, actual.length);
		assertTrue(Arrays.equals(expected, actual));
	}


	// ********** contains **********

	public void testContainsObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertTrue(ArrayTools.contains(a, "one"));
		assertFalse(ArrayTools.contains(a, null));
		Object[] a2 = ArrayTools.add(a, null);
		assertTrue(ArrayTools.contains(a2, null));
	}

	public void testContainsObjectArrayObject_EmptyArray() {
		Object[] a = new Object[0];
		assertFalse(ArrayTools.contains(a, "one"));
		assertFalse(ArrayTools.contains(a, null));
	}

	public void testContainsCharArrayChar() {
		char[] a = this.buildCharArray();
		assertTrue(ArrayTools.contains(a, 'a'));
		assertFalse(ArrayTools.contains(a, 'z'));
		char[] a2 = ArrayTools.add(a, 'z');
		assertTrue(ArrayTools.contains(a2, 'z'));
	}

	public void testContainsCharArrayObject_EmptyArray() {
		char[] a = new char[0];
		assertFalse(ArrayTools.contains(a, 'a'));
	}

	public void testContainsIntArrayInt() {
		int[] a = this.buildIntArray();
		assertTrue(ArrayTools.contains(a, 10));
		assertFalse(ArrayTools.contains(a, 55));
		int[] a2 = ArrayTools.add(a, 55);
		assertTrue(ArrayTools.contains(a2, 55));
	}

	public void testContainsIntArrayObject_EmptyArray() {
		int[] a = new int[0];
		assertFalse(ArrayTools.contains(a, 'a'));
	}


	// ********** contains all **********

	public void testContainsAllObjectArrayCollection() {
		assertTrue(ArrayTools.containsAll(this.buildObjectArray1(), this.buildStringList1()));
		assertFalse(ArrayTools.containsAll(this.buildObjectArray1(), this.buildStringList2()));
	}

	public void testContainsAllObjectArrayIterable() {
		Iterable<String> iterable = this.buildStringList1();
		assertTrue(ArrayTools.containsAll(this.buildObjectArray1(), iterable));
		iterable = this.buildStringList2();
		assertFalse(ArrayTools.containsAll(this.buildObjectArray1(), iterable));
	}

	public void testContainsAllObjectArrayIterator() {
		assertTrue(ArrayTools.containsAll(this.buildObjectArray1(), this.buildStringList1().iterator()));
		assertFalse(ArrayTools.containsAll(this.buildObjectArray1(), this.buildStringList2().iterator()));
	}

	public void testContainsAllObjectArrayIterator_Empty() {
		assertTrue(ArrayTools.containsAll(this.buildObjectArray1(), EmptyIterator.instance()));
	}

	public void testContainsAllObjectArrayObjectArray() {
		assertTrue(ArrayTools.containsAll(this.buildObjectArray1(), this.buildObjectArray1()));
		assertFalse(ArrayTools.containsAll(this.buildObjectArray1(), this.buildObjectArray2()));
	}

	public void testContainsAllCharArrayCharArray() {
		assertTrue(ArrayTools.containsAll(this.buildCharArray(), this.buildCharArray()));
		assertFalse(ArrayTools.containsAll(this.buildCharArray(), new char[] { 'x', 'y' }));
	}

	public void testContainsAllIntArrayIntArray() {
		assertTrue(ArrayTools.containsAll(this.buildIntArray(), this.buildIntArray()));
		assertFalse(ArrayTools.containsAll(this.buildIntArray(), new int[] { 444, 888 }));
	}


	// ********** diff **********

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
		assertEquals(-1, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(-1, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.diffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(4, ArrayTools.diffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(-1, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(2, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, ArrayTools.diffEnd(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(-1, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, ArrayTools.diffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(-1, ArrayTools.diffEnd(array1, array2));
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
		assertEquals(new Range(5, -1), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(new Range(1, -1), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.diffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(new Range(0, 4), ArrayTools.diffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(new Range(0, -1), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(new Range(0, 2), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(new Range(0, 0), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(new Range(3, 3), ArrayTools.diffRange(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(new Range(5, -1), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(2, 2), ArrayTools.diffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(new Range(5, -1), ArrayTools.diffRange(array1, array2));
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
		assertEquals(5, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(1, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(4, ArrayTools.diffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(0, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(0, ArrayTools.diffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(0, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, e_, c_, d_ };
		assertEquals(2, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, ArrayTools.diffStart(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c__));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(5, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, ArrayTools.diffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(5, ArrayTools.diffStart(array1, array2));
	}


	// ********** identity diff **********

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
		assertEquals(-1, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(-1, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(4, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(-1, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(2, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, ArrayTools.identityDiffEnd(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(2, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, ArrayTools.identityDiffEnd(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(-1, ArrayTools.identityDiffEnd(array1, array2));
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
		assertEquals(new Range(5, -1), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(new Range(1, -1), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(0, 4), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(new Range(0, 4), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(new Range(0, -1), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { b_, c_, a_, d_, e_ };
		assertEquals(new Range(0, 2), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(new Range(0, 0), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(new Range(3, 3), ArrayTools.identityDiffRange(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(new Range(2, 2), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(new Range(2, 2), ArrayTools.identityDiffRange(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(new Range(5, -1), ArrayTools.identityDiffRange(array1, array2));
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
		assertEquals(5, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a };
		array2 = new String[] { a_ };
		assertEquals(1, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(4, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(4, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(0, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[0];
		assertEquals(0, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[0];
		array2 = new String[0];
		assertEquals(0, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, e_, c_, d_ };
		assertEquals(2, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, c, e };
		array2 = new String[] { a_, b_, c_, d_ };
		assertEquals(3, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { b, c, d, e };
		array2 = new String[] { a_, c_, d_, e_ };
		assertEquals(0, ArrayTools.identityDiffStart(array1, array2));

		String c__ = new String(c);
		assertTrue((c != c__) && c.equals(c_));
		array1 = new String[] { a, b, c, d, e };
		array2 = new String[] { a_, b_, c__, d_, e_ };
		assertEquals(2, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, c_, d_, e_ };
		assertEquals(2, ArrayTools.identityDiffStart(array1, array2));

		array1 = new String[] { a, b, null, d, e };
		array2 = new String[] { a_, b_, null, d_, e_ };
		assertEquals(5, ArrayTools.identityDiffStart(array1, array2));
	}


	// ********** elements are identical **********

	public void testElementsAreIdenticalObjectArrayObjectArray() {
		Object[] a1 = new Object[4];
		for (int i = 0; i < a1.length; i++) {
			a1[i] = String.valueOf(i * 1000);
		}

		Object[] a2 = new Object[a1.length];
		for (int i = 0; i < a2.length; i++) {
			a2[i] = a1[i];
		}

		assertTrue(ArrayTools.elementsAreIdentical(a1, a2));
		a2[2] = "2000";
		assertFalse(ArrayTools.elementsAreIdentical(a1, a2));
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testElementsAreIdenticalObjectArrayObjectArray_BothNull() {
		Object[] a1 = null;
		Object[] a2 = null;
		assertTrue(ArrayTools.elementsAreIdentical(a1, a2));
	}

	public void testElementsAreIdenticalObjectArrayObjectArray_OneNull() {
		Object[] a1 = null;
		Object[] a2 = new Object[0];
		assertFalse(ArrayTools.elementsAreIdentical(a1, a2));
	}

	public void testElementsAreIdenticalObjectArrayObjectArray_DifferentLengths() {
		Object[] a1 = new String[] {"foo", "bar"};
		Object[] a2 = new String[] {"foo", "bar", "baz"};
		assertFalse(ArrayTools.elementsAreIdentical(a1, a2));
	}


	// ********** index of **********

	public void testIndexOfObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertEquals(1, ArrayTools.indexOf(a, "one"));
	}

	public void testIndexOfObjectArrayObject_NotFound() {
		Object[] a = this.buildObjectArray1();
		assertEquals(-1, ArrayTools.indexOf(a, "twenty"));
	}

	public void testIndexOfObjectArrayObject_Null() {
		Object[] a = this.buildObjectArray1();
		a = ArrayTools.add(a, null);
		assertEquals(a.length - 1, ArrayTools.indexOf(a, null));
	}

	public void testIndexOfObjectArrayObject_Null_NotFound() {
		Object[] a = this.buildObjectArray1();
		assertEquals(-1, ArrayTools.indexOf(a, null));
	}

	public void testIdentityIndexOfObjectArrayObject() {
		String foo = "foo";
		String bar = "bar";
		String baz = "baz";
		Object[] a = new Object[3];
		a[0] = foo;
		a[1] = bar;
		a[2] = baz;
		assertEquals(1, ArrayTools.identityIndexOf(a, bar));
	}

	public void testIdentityIndexOfObjectArrayObject_NotFound() {
		String foo = "foo";
		String bar = "bar";
		String baz = "baz";
		Object[] a = new Object[3];
		a[0] = foo;
		a[1] = bar;
		a[2] = baz;
		assertEquals(-1, ArrayTools.identityIndexOf(a, new String("bar")));
	}

	public void testIndexOfCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(1, ArrayTools.indexOf(a, 'b'));
		a = ArrayTools.add(a, 'd');
		assertEquals(a.length - 1, ArrayTools.indexOf(a, 'd'));
	}

	public void testIndexOfCharArrayChar_NotFound() {
		char[] a = this.buildCharArray();
		assertEquals(-1, ArrayTools.indexOf(a, 'z'));
	}

	public void testIndexOfIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(1, ArrayTools.indexOf(a, 10));
		a = ArrayTools.add(a, 30);
		assertEquals(a.length - 1, ArrayTools.indexOf(a, 30));
	}

	public void testIndexOfIntArrayInt_NotFound() {
		int[] a = this.buildIntArray();
		assertEquals(-1, ArrayTools.indexOf(a, 1000));
	}


	// ********** insertion index of **********

	public void testInsertionIndexOfObjectArrayComparable() {
		String[] a = new String[] { "A", "C", "D" };
		assertEquals(1, ArrayTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "C", "D" };
		assertEquals(2, ArrayTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "B", "B", "C", "D" };
		assertEquals(4, ArrayTools.insertionIndexOf(a, "B"));

		a = new String[] { "A", "B", "B", "B", "C", "D" };
		assertEquals(6, ArrayTools.insertionIndexOf(a, "E"));

		a = new String[] { "B", "B", "B", "C", "D" };
		assertEquals(0, ArrayTools.insertionIndexOf(a, "A"));

		a = new String[] { "A", "A", "B", "B", "C", "D" };
		assertEquals(2, ArrayTools.insertionIndexOf(a, "A"));
	}

	public void testInsertionIndexOfObjectArrayObjectComparator() {
		Comparator<String> c = new ReverseComparator<String>();
		String[] a = new String[] { "D", "C", "A" };
		assertEquals(2, ArrayTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "A" };
		assertEquals(3, ArrayTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "B", "B", "A" };
		assertEquals(5, ArrayTools.insertionIndexOf(a, "B", c));

		a = new String[] { "D", "C", "B", "B", "B", "A" };
		assertEquals(0, ArrayTools.insertionIndexOf(a, "E", c));

		a = new String[] { "D", "C", "B", "B", "B" };
		assertEquals(5, ArrayTools.insertionIndexOf(a, "A", c));

		a = new String[] { "D", "C", "B", "B", "A", "A" };
		assertEquals(6, ArrayTools.insertionIndexOf(a, "A", c));
	}


	// ********** last index of **********

	public void testLastIndexOfObjectArrayObject() {
		Object[] a = this.buildObjectArray1();
		assertEquals(1, ArrayTools.lastIndexOf(a, "one"));
	}

	public void testLastIndexOfObjectArrayObject_NotFound() {
		Object[] a = this.buildObjectArray1();
		assertEquals(-1, ArrayTools.lastIndexOf(a, "twenty"));
	}

	public void testLastIndexOfObjectArrayObject_Null() {
		Object[] a = this.buildObjectArray1();
		a = ArrayTools.add(a, null);
		assertEquals(a.length - 1, ArrayTools.lastIndexOf(a, null));
	}

	public void testLastIndexOfObjectArrayObject_Null_NotFound() {
		Object[] a = this.buildObjectArray1();
		assertEquals(-1, ArrayTools.lastIndexOf(a, null));
	}

	public void testLastIndexOfCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(1, ArrayTools.lastIndexOf(a, 'b'));
		a = ArrayTools.add(a, 'd');
		assertEquals(a.length - 1, ArrayTools.lastIndexOf(a, 'd'));
	}

	public void testLastIndexOfCharArrayChar_NotFound() {
		char[] a = this.buildCharArray();
		assertEquals(-1, ArrayTools.lastIndexOf(a, 'z'));
	}

	public void testLastIndexOfIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(1, ArrayTools.lastIndexOf(a, 10));
		a = ArrayTools.add(a, 30);
		assertEquals(a.length - 1, ArrayTools.lastIndexOf(a, 30));
	}

	public void testLastIndexOfIntArrayInt_NotFound() {
		int[] a = this.buildIntArray();
		assertEquals(-1, ArrayTools.lastIndexOf(a, 1000));
	}


	// ********** min/max **********

	public void testMinCharArray() {
		assertEquals('a', ArrayTools.min(this.buildCharArray()));
	}

	public void testMinCharArray_Exception() {
		char[] array = new char[0];
		boolean exCaught = false;
		try {
			char c = ArrayTools.min(array);
			fail("bogus char: " + c);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMinIntArray() {
		assertEquals(0, ArrayTools.min(this.buildIntArray()));
	}

	public void testMinIntArray_Exception() {
		int[] array = new int[0];
		boolean exCaught = false;
		try {
			int i = ArrayTools.min(array);
			fail("bogus int: " + i);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMaxCharArray1() {
		assertEquals('c', ArrayTools.max(this.buildCharArray()));
	}

	public void testMaxCharArray2() {
		char[] array = new char[] { 'x', 'a', 'b', 'c' };
		assertEquals('x', ArrayTools.max(array));
	}

	public void testMaxCharArray_Exception() {
		char[] array = new char[0];
		boolean exCaught = false;
		try {
			char c = ArrayTools.max(array);
			fail("bogus char: " + c);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMaxIntArray1() {
		assertEquals(20, ArrayTools.max(this.buildIntArray()));
	}

	public void testMaxIntArray2() {
		int[] array = new int[] { 77, 3, 1, -3 };
		assertEquals(77, ArrayTools.max(array));
	}

	public void testMaxIntArray_Exception() {
		int[] array = new int[0];
		boolean exCaught = false;
		try {
			int i = ArrayTools.max(array);
			fail("bogus int: " + i);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** move **********

	public void testMoveObjectArrayIntInt() {
		String[] array = new String[] { "0", "1", "2", "3", "4", "5" };

		String[] result = ArrayTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result));

		result = ArrayTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result));

		result = ArrayTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result));

		result = ArrayTools.move(array, 4, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result));
	}

	public void testMoveObjectArrayIntIntInt() {
		String[] array = new String[] { "0", "1", "2", "3", "4", "5" };

		String[] result = ArrayTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "1", "3", "4", "2", "5" }, result));

		result = ArrayTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "1", "3", "4", "2" }, result));

		result = ArrayTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "4", "1", "3", "2" }, result));

		result = ArrayTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));

		result = ArrayTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "0", "3", "2", "4", "5", "1" }, result));

		result = ArrayTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));

		result = ArrayTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));

		result = ArrayTools.move(array, 1, 0, 0);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new String[] { "5", "0", "3", "2", "4", "1" }, result));
	}

	public void testMoveIntArrayIntInt() {
		int[] array = new int[] { 0, 1, 2, 3, 4, 5 };

		int[] result = ArrayTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 1, 3, 4, 2, 5 }, result));

		result = ArrayTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 1, 3, 4, 2 }, result));

		result = ArrayTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));

		result = ArrayTools.move(array, 2, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));
	}

	public void testMoveIntArrayIntIntInt() {
		int[] array = new int[] { 0, 1, 2, 3, 4, 5 };

		int[] result = ArrayTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 1, 3, 4, 2, 5 }, result));

		result = ArrayTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 1, 3, 4, 2 }, result));

		result = ArrayTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 4, 1, 3, 2 }, result));

		result = ArrayTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));

		result = ArrayTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 0, 3, 2, 4, 5, 1 }, result));

		result = ArrayTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));

		result = ArrayTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));

		result = ArrayTools.move(array, 1, 0, 0);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new int[] { 5, 0, 3, 2, 4, 1 }, result));
	}

	public void testMoveCharArrayIntInt() {
		char[] array = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };

		char[] result = ArrayTools.move(array, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'd', 'e', 'c', 'f' }, result));

		result = ArrayTools.move(array, 0, 5);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'f', 'a', 'b', 'd', 'e', 'c' }, result));

		result = ArrayTools.move(array, 2, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'f', 'a', 'e', 'b', 'd', 'c' }, result));

		result = ArrayTools.move(array, 2, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'f', 'a', 'e', 'b', 'd', 'c' }, result));
	}

	public void testMoveCharArrayIntIntInt() {
		char[] array = new char[] { 'a', 'b', 'b', 'c', 'd', 'e' };

		char[] result = ArrayTools.move(array, 4, 2, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'b', 'c', 'd', 'b', 'e' }, result));

		result = ArrayTools.move(array, 0, 5, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'b', 'c', 'd', 'b' }, result));

		result = ArrayTools.move(array, 2, 4, 1);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'd', 'b', 'c', 'b' }, result));

		result = ArrayTools.move(array, 2, 4, 2);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));

		result = ArrayTools.move(array, 0, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'a', 'c', 'b', 'd', 'e', 'b' }, result));

		result = ArrayTools.move(array, 1, 0, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));

		result = ArrayTools.move(array, 1, 1, 4);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));

		result = ArrayTools.move(array, 1, 0, 0);
		assertSame(array, result);  // the array is modified in place and returned
		assertTrue(Arrays.equals(new char[] { 'e', 'a', 'c', 'b', 'd', 'b' }, result));
	}


	// ********** remove **********

	public void testRemoveObjectArrayObject_Object() {
		Object[] a = this.buildObjectArray1();
		a = ArrayTools.add(a, "three");
		a = ArrayTools.add(a, "four");
		a = ArrayTools.add(a, "five");

		assertEquals(6, a.length);
		assertTrue(ArrayTools.contains(a, "three"));
		a = ArrayTools.remove(a, "three");
		assertEquals(5, a.length);
		assertFalse(ArrayTools.contains(a, "three"));
		assertTrue(ArrayTools.contains(a, "four"));
		assertTrue(ArrayTools.contains(a, "five"));
	}

	public void testRemoveObjectArrayObject_String() {
		String[] a = this.buildStringArray1();
		a = ArrayTools.add(a, "three");
		a = ArrayTools.add(a, "four");
		a = ArrayTools.add(a, "five");

		assertEquals(6, a.length);
		assertTrue(ArrayTools.contains(a, "three"));
		a = ArrayTools.remove(a, "three");
		assertEquals(5, a.length);
		assertFalse(ArrayTools.contains(a, "three"));
		assertTrue(ArrayTools.contains(a, "four"));
		assertTrue(ArrayTools.contains(a, "five"));
	}

	public void testRemoveCharArrayChar() {
		char[] a = this.buildCharArray();
		a = ArrayTools.add(a, 'd');
		a = ArrayTools.add(a, 'e');
		a = ArrayTools.add(a, 'f');

		assertEquals(6, a.length);
		assertTrue(ArrayTools.contains(a, 'd'));
		a = ArrayTools.remove(a, 'd');
		assertEquals(5, a.length);
		assertFalse(ArrayTools.contains(a, 'd'));
		assertTrue(ArrayTools.contains(a, 'e'));
		assertTrue(ArrayTools.contains(a, 'f'));
	}

	public void testRemoveIntArrayInt() {
		int[] a = this.buildIntArray();
		a = ArrayTools.add(a, 30);
		a = ArrayTools.add(a, 40);
		a = ArrayTools.add(a, 50);

		assertEquals(6, a.length);
		assertTrue(ArrayTools.contains(a, 30));
		a = ArrayTools.remove(a, 30);
		assertEquals(5, a.length);
		assertFalse(ArrayTools.contains(a, 30));
		assertTrue(ArrayTools.contains(a, 40));
		assertTrue(ArrayTools.contains(a, 50));
	}


	// ********** remove all **********

	public void testRemoveAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[] { "E", "B" };
		String[] a3 = ArrayTools.removeAll(a1, (Object[]) a2);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayObjectArray_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[0];
		String[] a3 = ArrayTools.removeAll(a1, (Object[]) a2);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayObjectArray_NoMatches() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a2 = new String[] { "X", "Y", "Z" };
		String[] a3 = ArrayTools.removeAll(a1, (Object[]) a2);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayIterable() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, iterable);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIterableInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, iterable, 7);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, iterable.iterator());
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIterator_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a3 = ArrayTools.removeAll(a1, EmptyIterator.instance());
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayIteratorInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<?> iterable = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, iterable.iterator(), 7);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayIteratorInt_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		String[] a3 = ArrayTools.removeAll(a1, EmptyIterator.instance(), 7);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(new String[] { "A", "A", "C", "C", "D", "D", "F", "F" }, a3));
	}

	public void testRemoveAllObjectArrayCollection_Empty() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = new ArrayList<String>();
		String[] a3 = ArrayTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllObjectArrayCollection_EmptyArray() {
		String[] a1 = new String[0];
		Collection<String> collection = Arrays.asList(new String[] { "E", "B" });
		String[] a3 = ArrayTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(a1, a3));
		assertEquals(0, a3.length);
	}

	public void testRemoveAllObjectArrayCollection_NoMatches() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "X", "Y", "Z" });
		String[] a3 = ArrayTools.removeAll(a1, collection);
		assertTrue(Arrays.equals(a1, a3));
	}

	public void testRemoveAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'A', 'A', 'C', 'C', 'D', 'D', 'F', 'F' }, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllCharArrayCharArray_Empty1() {
		char[] a1 = new char[0];
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllCharArrayCharArray_Empty2() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[0];
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllCharArrayCharArray_NoMatches() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'X', 'Z' };
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 1, 1, 3, 3, 4, 4, 6, 6 }, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray_Empty1() {
		int[] a1 = new int[0];
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray_Empty2() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[0];
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}

	public void testRemoveAllIntArrayIntArray_NoMatches() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 52, 67 };
		assertTrue(Arrays.equals(a1, ArrayTools.removeAll(a1, a2)));
	}


	// ********** remove all occurrences **********

	public void testRemoveAllOccurrencesObjectArrayObject() {
		String[] a = this.buildStringArray1();
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, "three");
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, "two");
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, "two");
		assertEquals(2, a.length);

		a = ArrayTools.add(a, "five");
		a = ArrayTools.add(a, "five");
		a = ArrayTools.add(a, "five");
		assertEquals(5, a.length);
		a = ArrayTools.removeAllOccurrences(a, "five");
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, "five");
		assertEquals(2, a.length);

		a = ArrayTools.add(a, null);
		a = ArrayTools.add(a, null);
		a = ArrayTools.add(a, null);
		assertEquals(5, a.length);
		a = ArrayTools.removeAllOccurrences(a, null);
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, null);
		assertEquals(2, a.length);
	}

	public void testRemoveAllOccurrencesObjectArrayObject_Empty() {
		String[] a = new String[0];
		a = ArrayTools.removeAllOccurrences(a, "three");
		assertEquals(0, a.length);
	}

	public void testRemoveAllOccurrencesCharArrayChar() {
		char[] a = this.buildCharArray();
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, 'd');
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, 'b');
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, 'b');
		assertEquals(2, a.length);

		a = ArrayTools.add(a, 'g');
		a = ArrayTools.add(a, 'g');
		a = ArrayTools.add(a, 'g');
		assertEquals(5, a.length);
		a = ArrayTools.removeAllOccurrences(a, 'g');
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, 'g');
		assertEquals(2, a.length);
	}

	public void testRemoveAllOccurrencesCharArrayChar_Empty() {
		char[] a = new char[0];
		a = ArrayTools.removeAllOccurrences(a, 'a');
		assertEquals(0, a.length);
	}

	public void testRemoveAllOccurrencesIntArrayInt() {
		int[] a = this.buildIntArray();
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, 55);
		assertEquals(3, a.length);
		a = ArrayTools.removeAllOccurrences(a, 10);
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, 10);
		assertEquals(2, a.length);

		a = ArrayTools.add(a, 77);
		a = ArrayTools.add(a, 77);
		a = ArrayTools.add(a, 77);
		assertEquals(5, a.length);
		a = ArrayTools.removeAllOccurrences(a, 77);
		assertEquals(2, a.length);
		a = ArrayTools.removeAllOccurrences(a, 77);
		assertEquals(2, a.length);
	}

	public void testRemoveAllOccurrencesIntArrayInt_Empty() {
		int[] a = new int[0];
		a = ArrayTools.removeAllOccurrences(a, 22);
		assertEquals(0, a.length);
	}


	// ********** remove duplicate elements **********

	public void testRemoveDuplicateElementsObjectArray() {
		List<String> list = this.buildStringVector1();
		list.add("zero");
		list.add("zero");
		list.add("two");
		list.add("zero");
		String[] array = ArrayTools.removeDuplicateElements(list.toArray(new String[list.size()]));
		int i = 0;
		assertEquals("zero", array[i++]);
		assertEquals("one", array[i++]);
		assertEquals("two", array[i++]);
		assertEquals(i, array.length);
	}

	public void testRemoveDuplicateElementsObjectArray_Empty() {
		String[] array = ArrayTools.removeDuplicateElements(new String[0]);
		assertEquals(0, array.length);
	}

	public void testRemoveDuplicateElementsObjectArray_SingleElement() {
		String[] array = ArrayTools.removeDuplicateElements(new String[] { "foo" });
		assertEquals(1, array.length);
	}

	public void testRemoveDuplicateElementsObjectArray_NoDuplicates() {
		String[] a1 = new String[] { "foo", "bar", "baz" };
		String[] a2 = ArrayTools.removeDuplicateElements(a1);
		assertEquals(3, a2.length);
		assertTrue(Arrays.equals(a1, a2));
	}


	// ********** remove element at index **********

	public void testRemoveElementAtIndexObjectArrayInt() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.removeElementAtIndex(a, 3);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "A", "D" }, a));
	}

	public void testRemoveElementAtIndexCharArrayInt() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.removeElementAtIndex(a, 3);
		assertTrue(Arrays.equals(new char[] { 'A', 'B', 'A', 'A', 'D' }, a));
	}

	public void testRemoveElementAtIndexIntArrayInt() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = ArrayTools.removeElementsAtIndex(a, 3, 3);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7 }, a));
	}

	public void testRemoveFirstObjectArray() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.removeFirst(a);
		assertTrue(Arrays.equals(new String[] { "B", "A", "C", "A", "D" }, a));
	}

	public void testRemoveFirstCharArray() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.removeFirst(a);
		assertTrue(Arrays.equals(new char[] { 'B', 'A', 'C', 'A', 'D' }, a));
	}

	public void testRemoveFirstIntArray() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = ArrayTools.removeFirst(a);
		assertTrue(Arrays.equals(new int[] { 6, 7, 33, 2, 11 }, a));
	}

	public void testRemoveLastObjectArray() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.removeLast(a);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "C", "A" }, a));
	}

	public void testRemoveLastCharArray() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.removeLast(a);
		assertTrue(Arrays.equals(new char[] { 'A', 'B', 'A', 'C', 'A' }, a));
	}

	public void testRemoveLastIntArray() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = ArrayTools.removeLast(a);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7, 33, 2 }, a));
	}


	// ********** remove elements at index **********

	public void testRemoveElementsAtIndexObjectArrayIntInt() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.removeElementsAtIndex(a, 3, 2);
		assertTrue(Arrays.equals(new String[] { "A", "B", "A", "D" }, a));
	}

	public void testRemoveElementsAtIndexObjectArrayIntInt_ZeroLength() {
		String[] a1 = new String[] { "A", "B", "A", "C", "A", "D" };
		String[] a2 = ArrayTools.removeElementsAtIndex(a1, 3, 0);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testRemoveElementsAtIndexObjectArrayIntInt_Empty() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}

	public void testRemoveElementsAtIndexCharArrayIntInt() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.removeElementsAtIndex(a, 0, 5);
		assertTrue(Arrays.equals(new char[] { 'D' }, a));
	}

	public void testRemoveElementsAtIndexCharArrayIntInt_ZeroLength() {
		char[] a1 = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		char[] a2 = ArrayTools.removeElementsAtIndex(a1, 3, 0);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testRemoveElementsAtIndexCharArrayIntInt_Empty() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}

	public void testRemoveElementsAtIndexIntArrayIntInt() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = ArrayTools.removeElementsAtIndex(a, 3, 3);
		assertTrue(Arrays.equals(new int[] { 8, 6, 7 }, a));
	}

	public void testRemoveElementsAtIndexIntArrayIntInt_ZeroLength() {
		int[] a1 = new int[] { 8, 6, 7, 33, 2, 11 };
		int[] a2 = ArrayTools.removeElementsAtIndex(a1, 3, 0);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testRemoveElementsAtIndexIntArrayIntInt_Empty() {
		int[] a = new int[] { 8, 6, 7, 33, 2, 11 };
		a = ArrayTools.removeElementsAtIndex(a, 0, 6);
		assertEquals(0, a.length);
	}


	// ********** replace all **********

	public void testReplaceAllObjectArrayObjectObject_Object() {
		Object[] a = new Object[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new Object[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllObjectArrayObjectObject_String() {
		String[] a = new String[] { "A", "B", "A", "C", "A", "D" };
		a = ArrayTools.replaceAll(a, "A", "Z");
		assertTrue(Arrays.equals(new String[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllObjectArrayObjectObject_Null() {
		String[] a = new String[] { null, "B", null, "C", null, "D" };
		a = ArrayTools.replaceAll(a, null, "Z");
		assertTrue(Arrays.equals(new String[] { "Z", "B", "Z", "C", "Z", "D" }, a));
	}

	public void testReplaceAllCharArrayCharChar() {
		char[] a = new char[] { 'A', 'B', 'A', 'C', 'A', 'D' };
		a = ArrayTools.replaceAll(a, 'A', 'Z');
		assertTrue(Arrays.equals(new char[] { 'Z', 'B', 'Z', 'C', 'Z', 'D' }, a));
	}

	public void testReplaceAllIntArrayIntInt() {
		int[] a = new int[] { 0, 1, 0, 7, 0, 99 };
		a = ArrayTools.replaceAll(a, 0, 13);
		assertTrue(Arrays.equals(new int[] { 13, 1, 13, 7, 13, 99 }, a));
	}


	// ********** retain all **********

	public void testRetainAllObjectArrayObjectArray() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Object[] a2 = new Object[] { "E", "B", new Integer(7) };
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, a2)));
	}

	public void testRetainAllObjectArrayObjectArray_EmptyObjectArray1() {
		String[] a1 = new String[0];
		String[] a2 = new String[] { "E", "B", "" };
		String[] a3 = ArrayTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayObjectArray_EmptyObjectArray2() {
		String[] a1 = new String[] { "E", "B", "" };
		String[] a2 = new String[0];
		String[] a3 = ArrayTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayObjectArray_BothEmpty() {
		String[] a1 = new String[0];
		String[] a2 = new String[0];
		String[] a3 = ArrayTools.retainAll(a1, a2);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterable() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, iterable)));
	}

	public void testRetainAllObjectArrayIterable_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = ArrayTools.retainAll(a1, iterable);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterableInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, iterable, 3)));
	}

	public void testRetainAllObjectArrayIterableInt_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterable<String> iterable = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = ArrayTools.retainAll(a1, iterable, 3);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, iterator)));
	}

	public void testRetainAllObjectArrayIterator_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		String[] a3 = ArrayTools.retainAll(a1, iterator);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayIterator_EmptyIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		assertTrue(Arrays.equals(new String[0], ArrayTools.retainAll(a1, EmptyIterator.instance())));
	}

	public void testRetainAllObjectArrayIteratorInt() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, iterator, 3)));
	}

	public void testRetainAllObjectArrayIteratorInt_EmptyIterator() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		assertTrue(Arrays.equals(new String[0], ArrayTools.retainAll(a1, EmptyIterator.instance(), 3)));
	}

	public void testRetainAllObjectArrayIteratorInt_EmptyObjectArray() {
		String[] a1 = new String[0];
		Iterator<String> iterator = Arrays.asList(new String[] { "E", "B", "" }).iterator();
		String[] a3 = ArrayTools.retainAll(a1, iterator, 3);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "E", "B", "" });
		assertTrue(Arrays.equals(new String[] { "B", "B", "E", "E" }, ArrayTools.retainAll(a1, collection)));
	}

	public void testRetainAllObjectArrayCollection_EmptyObjectArray() {
		String[] a1 = new String[0];
		Collection<String> collection = Arrays.asList(new String[] { "E", "B", "" });
		String[] a3 = ArrayTools.retainAll(a1, collection);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection_EmptyCollection() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = new ArrayList<String>();
		String[] a3 = ArrayTools.retainAll(a1, collection);
		assertEquals(0, a3.length);
	}

	public void testRetainAllObjectArrayCollection_All() {
		String[] a1 = new String[] { "A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F" };
		Collection<String> collection = Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F" });
		assertTrue(Arrays.equals(a1, ArrayTools.retainAll(a1, collection)));
	}

	public void testRetainAllCharArrayCharArray() {
		char[] a1 = new char[] { 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F' };
		char[] a2 = new char[] { 'E', 'B' };
		assertTrue(Arrays.equals(new char[] { 'B', 'B', 'E', 'E' }, ArrayTools.retainAll(a1, a2)));
	}

	public void testRetainAllCharArrayCharArray_EmptyCharArray1() {
		char[] a1 = new char[0];
		char[] a2 = new char[] { 'E', 'B' };
		assertSame(a1, ArrayTools.retainAll(a1, a2));
	}

	public void testRetainAllCharArrayCharArray_EmptyCharArray2() {
		char[] a1 = new char[] { 'E', 'B' };
		char[] a2 = new char[0];
		assertEquals(0, ArrayTools.retainAll(a1, a2).length);
	}

	public void testRetainAllCharArrayCharArray_RetainAll() {
		char[] a1 = new char[] { 'E', 'B', 'E', 'B', 'E', 'B', 'E', 'B', 'E' };
		char[] a2 = new char[] { 'E', 'B' };
		assertSame(a1, ArrayTools.retainAll(a1, a2));
	}

	public void testRetainAllIntArrayIntArray() {
		int[] a1 = new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
		int[] a2 = new int[] { 5, 2 };
		assertTrue(Arrays.equals(new int[] { 2, 2, 5, 5 }, ArrayTools.retainAll(a1, a2)));
	}

	public void testRetainAllIntArrayIntArray_EmptyIntArray1() {
		int[] a1 = new int[0];
		int[] a2 = new int[] { 5, 2 };
		assertSame(a1, ArrayTools.retainAll(a1, a2));
	}

	public void testRetainAllIntArrayIntArray_EmptyIntArray2() {
		int[] a1 = new int[] { 5, 2 };
		int[] a2 = new int[0];
		assertEquals(0, ArrayTools.retainAll(a1, a2).length);
	}

	public void testRetainAllIntArrayIntArray_RetainAll() {
		int[] a1 = new int[] { 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5 };
		int[] a2 = new int[] { 5, 2 };
		assertSame(a1, ArrayTools.retainAll(a1, a2));
	}


	// ********** reverse **********

	public void testReverseObjectArray_Object() {
		Object[] a = this.buildObjectArray1();
		a = ArrayTools.reverse(a);
		assertEquals("two", a[0]);
		assertEquals("one", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testReverseObjectArray_String() {
		String[] a = this.buildStringArray1();
		a = ArrayTools.reverse(a);
		assertEquals("two", a[0]);
		assertEquals("one", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testReverseObjectArray_Singleton() {
		String[] a1 = new String[] { "foo" };
		String[] a2 = ArrayTools.reverse(a1);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testReverseCharArray() {
		char[] a = this.buildCharArray();
		a = ArrayTools.reverse(a);
		assertEquals('c', a[0]);
		assertEquals('b', a[1]);
		assertEquals('a', a[2]);
	}

	public void testReverseCharArray_Singleton() {
		char[] a1 = new char[] { 'f' };
		char[] a2 = ArrayTools.reverse(a1);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testReverseIntArray() {
		int[] a = this.buildIntArray();
		a = ArrayTools.reverse(a);
		assertEquals(20, a[0]);
		assertEquals(10, a[1]);
		assertEquals(0, a[2]);
	}

	public void testReverseIntArray_Singleton() {
		int[] a1 = new int[] { 7 };
		int[] a2 = ArrayTools.reverse(a1);
		assertTrue(Arrays.equals(a1, a2));
	}


	// ********** rotate **********

	public void testRotateObjectArray() {
		String[] a = this.buildStringArray1();
		a = ArrayTools.rotate(a);
		assertEquals("two", a[0]);
		assertEquals("zero", a[1]);
		assertEquals("one", a[2]);
	}

	public void testRotateObjectArray_Zero() {
		String[] a1 = new String[0];
		String[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateObjectArray_One() {
		String[] a1 = new String[] { "foo  " };
		String[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt() {
		String[] a = this.buildStringArray1();
		a = ArrayTools.rotate(a, 2);
		assertEquals("one", a[0]);
		assertEquals("two", a[1]);
		assertEquals("zero", a[2]);
	}

	public void testRotateObjectArrayInt_ZeroDistance() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = ArrayTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt_NegativeDistance() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = ArrayTools.rotate(a1, -1);
		assertEquals("one", a2[0]);
		assertEquals("two", a2[1]);
		assertEquals("zero", a2[2]);
	}

	public void testRotateObjectArrayInt_Zero() {
		String[] a1 = new String[0];
		String[] a2 = ArrayTools.rotate(a1, 7);
		assertSame(a1, a2);
	}

	public void testRotateObjectArrayInt_One() {
		String[] a1 = new String[] { "foo  " };
		String[] a2 = ArrayTools.rotate(a1, 8);
		assertSame(a1, a2);
	}

	public void testRotateCharArray() {
		char[] a = this.buildCharArray();
		a = ArrayTools.rotate(a);
		assertEquals('c', a[0]);
		assertEquals('a', a[1]);
		assertEquals('b', a[2]);
	}

	public void testRotateCharArray_Zero() {
		char[] a1 = new char[0];
		char[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateCharArray_One() {
		char[] a1 = new char[] { 'a' };
		char[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt() {
		char[] a = this.buildCharArray();
		a = ArrayTools.rotate(a, 2);
		assertEquals('b', a[0]);
		assertEquals('c', a[1]);
		assertEquals('a', a[2]);
	}

	public void testRotateCharArrayInt_ZeroDistance() {
		char[] a1 = new char[] { 'a', 'b', 'c' };
		char[] a2 = ArrayTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt_NegativeDistance() {
		char[] a = this.buildCharArray();
		a = ArrayTools.rotate(a, -1);
		assertEquals('b', a[0]);
		assertEquals('c', a[1]);
		assertEquals('a', a[2]);
	}

	public void testRotateCharArrayInt_Zero() {
		char[] a1 = new char[0];
		char[] a2 = ArrayTools.rotate(a1, 2001);
		assertSame(a1, a2);
	}

	public void testRotateCharArrayInt_One() {
		char[] a1 = new char[] { 'a' };
		char[] a2 = ArrayTools.rotate(a1, 22);
		assertSame(a1, a2);
	}

	public void testRotateIntArray() {
		int[] a = this.buildIntArray();
		a = ArrayTools.rotate(a);
		assertEquals(20, a[0]);
		assertEquals(0, a[1]);
		assertEquals(10, a[2]);
	}

	public void testRotateIntArray_Zero() {
		int[] a1 = new int[0];
		int[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateIntArray_One() {
		int[] a1 = new int[] { 77 };
		int[] a2 = ArrayTools.rotate(a1);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt() {
		int[] a = this.buildIntArray();
		a = ArrayTools.rotate(a, 2);
		assertEquals(10, a[0]);
		assertEquals(20, a[1]);
		assertEquals(0, a[2]);
	}

	public void testRotateIntArrayInt_ZeroDistance() {
		int[] a1 = new int[] { 3, 2, 1 };
		int[] a2 = ArrayTools.rotate(a1, 0);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt_NegativeDistance() {
		int[] a = this.buildIntArray();
		a = ArrayTools.rotate(a, -1);
		assertEquals(10, a[0]);
		assertEquals(20, a[1]);
		assertEquals(0, a[2]);
	}

	public void testRotateIntArrayInt_Zero() {
		int[] a1 = new int[0];
		int[] a2 = ArrayTools.rotate(a1, 3);
		assertSame(a1, a2);
	}

	public void testRotateIntArrayInt_One() {
		int[] a1 = new int[] { 77 };
		int[] a2 = ArrayTools.rotate(a1, 44);
		assertSame(a1, a2);
	}


	// ********** shuffle **********

	public void testShuffleObjectArray() {
		String[] array1 = this.buildStringArray1();
		String[] array2 = ArrayTools.shuffle(this.buildStringArray1());
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, (Object[]) array2));
	}

	public void testShuffleObjectArray_Singleton() {
		String[] array1 = new String[] { "foo" };
		String[] array2 = ArrayTools.shuffle(new String[] { "foo" });
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, (Object[]) array2));
	}

	public void testShuffleCharArray() {
		char[] array1 = this.buildCharArray();
		char[] array2 = ArrayTools.shuffle(this.buildCharArray());
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, array2));
	}

	public void testShuffleCharArray_Singleton() {
		char[] array1 = new char[] { 'f' };
		char[] array2 = ArrayTools.shuffle(new char[] { 'f' });
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, array2));
	}

	public void testShuffleIntArray() {
		int[] array1 = this.buildIntArray();
		int[] array2 = ArrayTools.shuffle(this.buildIntArray());
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, array2));
	}

	public void testShuffleIntArray_Singleton() {
		int[] array1 = new int[] { 7 };
		int[] array2 = ArrayTools.shuffle(new int[] { 7 });
		assertEquals(array1.length, array2.length);
		assertTrue(ArrayTools.containsAll(array1, array2));
	}


	// ********** sub-array **********

	public void testSubArrayObjectArrayIntInt() {
		String[] array = new String[] {"foo", "bar", "baz", "joo", "jar", "jaz"};
		String[] result = new String[] {"foo", "bar", "baz", "joo"};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 0, 4)));

		result = new String[] {"jar"};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 4, 5)));

		result = new String[0];
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 5, 5)));

		result = new String[] {"joo", "jar", "jaz"};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 3, 6)));
	}

	public void testSubArrayIntArrayIntInt() {
		int[] array = new int[] {77, 99, 333, 4, 9090, 42};
		int[] result = new int[] {77, 99, 333, 4};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 0, 4)));

		result = new int[] {9090};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 4, 5)));

		result = new int[0];
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 5, 5)));

		result = new int[] {4, 9090, 42};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 3, 6)));
	}

	public void testSubArrayCharArrayIntInt() {
		char[] array = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
		char[] result = new char[] {'a', 'b', 'c', 'd'};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 0, 4)));

		result = new char[] {'e'};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 4, 5)));

		result = new char[0];
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 5, 5)));

		result = new char[] {'d', 'e', 'f'};
		assertTrue(Arrays.equals(result, ArrayTools.subArray(array, 3, 6)));
	}


	// ********** swap **********

	public void testSwapObjectArray() {
		String[] a = this.buildStringArray1();
		a = ArrayTools.swap(a, 1, 2);
		assertEquals("zero", a[0]);
		assertEquals("two", a[1]);
		assertEquals("one", a[2]);
	}

	public void testSwapObjectArray_SameIndices() {
		String[] a1 = this.buildStringArray1();
		String[] a2 = this.buildStringArray1();
		a1 = ArrayTools.swap(a1, 1, 1);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testSwapCharArray() {
		char[] a = this.buildCharArray();
		a = ArrayTools.swap(a, 1, 2);
		assertEquals('a', a[0]);
		assertEquals('c', a[1]);
		assertEquals('b', a[2]);
	}

	public void testSwapCharArray_SameIndices() {
		char[] a1 = this.buildCharArray();
		char[] a2 = this.buildCharArray();
		a1 = ArrayTools.swap(a1, 1, 1);
		assertTrue(Arrays.equals(a1, a2));
	}

	public void testSwapIntArray() {
		int[] a = this.buildIntArray();
		a = ArrayTools.swap(a, 1, 2);
		assertEquals(0, a[0]);
		assertEquals(20, a[1]);
		assertEquals(10, a[2]);
	}

	public void testSwapIntArray_SameIndices() {
		int[] a1 = this.buildIntArray();
		int[] a2 = this.buildIntArray();
		a1 = ArrayTools.swap(a1, 1, 1);
		assertTrue(Arrays.equals(a1, a2));
	}


	// ********** Arrays enhancements **********

	public void testFillBooleanArrayBoolean() {
		boolean[] a1 = new boolean[9];
		boolean[] a2 = ArrayTools.fill(a1, true);
		for (boolean x : a1) {
			assertTrue(x);
		}
		assertSame(a1, a2);
	}

	public void testFillBooleanArrayIntIntBoolean() {
		boolean[] a1 = new boolean[9];
		boolean[] a2 = ArrayTools.fill(a1, false);
		int from = 3;
		int to = 6;
		boolean[] a3 = ArrayTools.fill(a2, from, to, true);
		for (int i = 0; i < a1.length; i++) {
			boolean x = a1[i];
			if (i < from || i >= to) {
				assertFalse(x);
			} else {
				assertTrue(x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillByteArrayByte() {
		byte[] a1 = new byte[9];
		byte[] a2 = ArrayTools.fill(a1, (byte) 77);
		for (byte x : a1) {
			assertEquals(77, x);
		}
		assertSame(a1, a2);
	}

	public void testFillByteArrayIntIntByte() {
		byte[] a1 = new byte[9];
		byte[] a2 = ArrayTools.fill(a1, (byte) 3);
		int from = 3;
		int to = 6;
		byte[] a3 = ArrayTools.fill(a2, from, to, (byte) 77);
		for (int i = 0; i < a1.length; i++) {
			byte x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertEquals(77, x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillCharArrayChar() {
		char[] a1 = new char[9];
		char[] a2 = ArrayTools.fill(a1, 'c');
		for (char x : a1) {
			assertEquals('c', x);
		}
		assertSame(a1, a2);
	}

	public void testFillCharArrayIntIntChar() {
		char[] a1 = new char[9];
		char[] a2 = ArrayTools.fill(a1, 'a');
		int from = 3;
		int to = 6;
		char[] a3 = ArrayTools.fill(a2, from, to, 'c');
		for (int i = 0; i < a1.length; i++) {
			char x = a1[i];
			if (i < from || i >= to) {
				assertEquals('a', x);
			} else {
				assertEquals('c', x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillDoubleArrayDouble() {
		double[] a1 = new double[9];
		double[] a2 = ArrayTools.fill(a1, 77.77);
		for (double x : a1) {
			assertEquals(77.77, x, 0.0);
		}
		assertSame(a1, a2);
	}

	public void testFillDoubleArrayIntIntDouble() {
		double[] a1 = new double[9];
		double[] a2 = ArrayTools.fill(a1, 3.3);
		int from = 3;
		int to = 6;
		double[] a3 = ArrayTools.fill(a2, from, to, 77.77);
		for (int i = 0; i < a1.length; i++) {
			double x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3.3, x, 0.0);
			} else {
				assertEquals(77.77, x, 0.0);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillFloatArrayFloat() {
		float[] a1 = new float[9];
		float[] a2 = ArrayTools.fill(a1, 77.77f);
		for (float x : a1) {
			assertEquals(77.77f, x, 0.0);
		}
		assertSame(a1, a2);
	}

	public void testFillFloatArrayIntIntFloat() {
		float[] a1 = new float[9];
		float[] a2 = ArrayTools.fill(a1, 3.3f);
		int from = 3;
		int to = 6;
		float[] a3 = ArrayTools.fill(a2, from, to, 77.77f);
		for (int i = 0; i < a1.length; i++) {
			float x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3.3f, x, 0.0);
			} else {
				assertEquals(77.77f, x, 0.0);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillIntArrayInt() {
		int[] a1 = new int[9];
		int[] a2 = ArrayTools.fill(a1, 77);
		for (int x : a1) {
			assertEquals(77, x);
		}
		assertSame(a1, a2);
	}

	public void testFillIntArrayIntIntInt() {
		int[] a1 = new int[9];
		int[] a2 = ArrayTools.fill(a1, 3);
		int from = 3;
		int to = 6;
		int[] a3 = ArrayTools.fill(a2, from, to, 77);
		for (int i = 0; i < a1.length; i++) {
			int x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertEquals(77, x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillObjectArrayObject() {
		String[] a1 = new String[9];
		String[] a2 = ArrayTools.fill(a1, "77");
		for (String x : a1) {
			assertEquals("77", x);
		}
		assertSame(a1, a2);
	}

	public void testFillObjectArrayIntIntObject() {
		String[] a1 = new String[9];
		String[] a2 = ArrayTools.fill(a1, "3");
		int from = 3;
		int to = 6;
		String[] a3 = ArrayTools.fill(a2, from, to, "77");
		for (int i = 0; i < a1.length; i++) {
			String x = a1[i];
			if (i < from || i >= to) {
				assertEquals("3", x);
			} else {
				assertEquals("77", x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillLongArrayLong() {
		long[] a1 = new long[9];
		long[] a2 = ArrayTools.fill(a1, 77);
		for (long x : a1) {
			assertEquals(77, x);
		}
		assertSame(a1, a2);
	}

	public void testFillLongArrayIntIntLong() {
		long[] a1 = new long[9];
		long[] a2 = ArrayTools.fill(a1, 3);
		int from = 3;
		int to = 6;
		long[] a3 = ArrayTools.fill(a2, from, to, 77);
		for (int i = 0; i < a1.length; i++) {
			long x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertEquals(77, x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testFillShortArrayShort() {
		short[] a1 = new short[9];
		short[] a2 = ArrayTools.fill(a1, (short) 77);
		for (short x : a1) {
			assertEquals(77, x);
		}
		assertSame(a1, a2);
	}

	public void testFillShortArrayIntIntShort() {
		short[] a1 = new short[9];
		short[] a2 = ArrayTools.fill(a1, (short) 3);
		int from = 3;
		int to = 6;
		short[] a3 = ArrayTools.fill(a2, from, to, (short) 77);
		for (int i = 0; i < a1.length; i++) {
			short x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertEquals(77, x);
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortByteArray() {
		byte[] a1 = new byte[3];
		a1[0] = (byte) 33;
		a1[1] = (byte) 11;
		a1[2] = (byte) 22;
		byte[] a2 = ArrayTools.sort(a1);
		byte last = (byte) 0;
		for (byte x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortByteArrayIntInt() {
		byte[] a1 = new byte[9];
		byte[] a2 = ArrayTools.fill(a1, (byte) 3);
		a2[3] = (byte) 33;
		a2[4] = (byte) 11;
		a2[5] = (byte) 22;
		int from = 3;
		int to = 6;
		byte[] a3 = ArrayTools.sort(a2, from, to);
		byte last = (byte) 0;
		for (int i = 0; i < a1.length; i++) {
			byte x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortCharArray() {
		char[] a1 = new char[3];
		a1[0] = 'z';
		a1[1] = 'b';
		a1[2] = 'm';
		char[] a2 = ArrayTools.sort(a1);
		char last = 'a';
		for (char x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortCharArrayIntInt() {
		char[] a1 = new char[9];
		char[] a2 = ArrayTools.fill(a1, 'c');
		a2[3] = 'z';
		a2[4] = 'b';
		a2[5] = 'm';
		int from = 3;
		int to = 6;
		char[] a3 = ArrayTools.sort(a2, from, to);
		char last = 'a';
		for (int i = 0; i < a1.length; i++) {
			char x = a1[i];
			if (i < from || i >= to) {
				assertEquals('c', x);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortDoubleArray() {
		double[] a1 = new double[3];
		a1[0] = 33.33;
		a1[1] = 11.11;
		a1[2] = 22.22;
		double[] a2 = ArrayTools.sort(a1);
		double last = 0;
		for (double x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortDoubleArrayIntInt() {
		double[] a1 = new double[9];
		double[] a2 = ArrayTools.fill(a1, 3.3);
		a2[3] = 33.33;
		a2[4] = 11.11;
		a2[5] = 22.22;
		int from = 3;
		int to = 6;
		double[] a3 = ArrayTools.sort(a2, from, to);
		double last = 0;
		for (int i = 0; i < a1.length; i++) {
			double x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3.3, x, 0.0);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortFloatArray() {
		float[] a1 = new float[3];
		a1[0] = 33.33f;
		a1[1] = 11.11f;
		a1[2] = 22.22f;
		float[] a2 = ArrayTools.sort(a1);
		float last = 0;
		for (float x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortFloatArrayIntInt() {
		float[] a1 = new float[9];
		float[] a2 = ArrayTools.fill(a1, 3.3f);
		a2[3] = 33.33f;
		a2[4] = 11.11f;
		a2[5] = 22.22f;
		int from = 3;
		int to = 6;
		float[] a3 = ArrayTools.sort(a2, from, to);
		float last = 0;
		for (int i = 0; i < a1.length; i++) {
			float x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3.3f, x, 0.0);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortIntArray() {
		int[] a1 = new int[3];
		a1[0] = 33;
		a1[1] = 11;
		a1[2] = 22;
		int[] a2 = ArrayTools.sort(a1);
		int last = 0;
		for (int x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortIntArrayIntInt() {
		int[] a1 = new int[9];
		int[] a2 = ArrayTools.fill(a1, 3);
		a2[3] = 33;
		a2[4] = 11;
		a2[5] = 22;
		int from = 3;
		int to = 6;
		int[] a3 = ArrayTools.sort(a2, from, to);
		int last = 0;
		for (int i = 0; i < a1.length; i++) {
			int x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortObjectArray() {
		String[] a1 = new String[3];
		a1[0] = "y";
		a1[1] = "b";
		a1[2] = "m";
		String[] a2 = ArrayTools.sort(a1);
		String last = "a";
		for (String x : a1) {
			assertTrue(last.compareTo(x) < 0);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortObjectArrayComparator() {
		String[] a1 = new String[3];
		a1[0] = "y";
		a1[1] = "b";
		a1[2] = "m";
		String[] a2 = ArrayTools.sort(a1, new ReverseComparator<String>());
		String last = "z";
		for (String x : a1) {
			assertTrue(last.compareTo(x) > 0);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortObjectArrayIntInt() {
		String[] a1 = new String[9];
		String[] a2 = ArrayTools.fill(a1, "c");
		a2[3] = "y";
		a2[4] = "b";
		a2[5] = "m";
		int from = 3;
		int to = 6;
		String[] a3 = ArrayTools.sort(a2, from, to);
		String last = "a";
		for (int i = 0; i < a1.length; i++) {
			String x = a1[i];
			if (i < from || i >= to) {
				assertEquals("c", x);
			} else {
				assertTrue(last.compareTo(x) < 0);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortObjectArrayIntIntComparator() {
		String[] a1 = new String[9];
		String[] a2 = ArrayTools.fill(a1, "c");
		a2[3] = "y";
		a2[4] = "b";
		a2[5] = "m";
		int from = 3;
		int to = 6;
		String[] a3 = ArrayTools.sort(a2, from, to, new ReverseComparator<String>());
		String last = "z";
		for (int i = 0; i < a1.length; i++) {
			String x = a1[i];
			if (i < from || i >= to) {
				assertEquals("c", x);
			} else {
				assertTrue(last.compareTo(x) > 0);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortLongArray() {
		long[] a1 = new long[3];
		a1[0] = 33;
		a1[1] = 11;
		a1[2] = 22;
		long[] a2 = ArrayTools.sort(a1);
		long last = 0;
		for (long x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortLongArrayIntInt() {
		long[] a1 = new long[9];
		long[] a2 = ArrayTools.fill(a1, 3);
		a2[3] = 33;
		a2[4] = 11;
		a2[5] = 22;
		int from = 3;
		int to = 6;
		long[] a3 = ArrayTools.sort(a2, from, to);
		long last = 0;
		for (int i = 0; i < a1.length; i++) {
			long x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}

	public void testSortShortArray() {
		short[] a1 = new short[3];
		a1[0] = (short) 33;
		a1[1] = (short) 11;
		a1[2] = (short) 22;
		short[] a2 = ArrayTools.sort(a1);
		short last = (short) 0;
		for (short x : a1) {
			assertTrue(last < x);
			last = x;
		}
		assertSame(a1, a2);
	}

	public void testSortShortArrayIntInt() {
		short[] a1 = new short[9];
		short[] a2 = ArrayTools.fill(a1, (short) 3);
		a2[3] = (short) 33;
		a2[4] = (short) 11;
		a2[5] = (short) 22;
		int from = 3;
		int to = 6;
		short[] a3 = ArrayTools.sort(a2, from, to);
		short last = (short) 0;
		for (int i = 0; i < a1.length; i++) {
			short x = a1[i];
			if (i < from || i >= to) {
				assertEquals(3, x);
			} else {
				assertTrue(last < x);
				last = x;
			}
		}
		assertSame(a1, a2);
		assertSame(a1, a3);
	}


	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ReflectionTools.newInstance(ArrayTools.class);
			fail("bogus: " + at);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}


	// ********** utility **********

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

	private Vector<String> buildStringVector1() {
		Vector<String> v = new Vector<String>();
		this.addToCollection1(v);
		return v;
	}

}
