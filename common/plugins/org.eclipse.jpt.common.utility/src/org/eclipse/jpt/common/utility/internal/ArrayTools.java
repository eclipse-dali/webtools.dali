/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * Array-related utility methods.
 */
public final class ArrayTools {
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	public static final int[] EMPTY_INT_ARRAY = new int[0];

	// ********** instantiation **********

	/**
	 * Return a new array with the same length
	 * and the same component type as the specified array.
	 * <p>
	 * <code>Arrays.newArray(Object[] array)</code>
	 */
	public static <E> E[] newArray(E[] array) {
		return newArray(array, array.length);
	}

	/**
	 * Return a new array with the specified length
	 * and the same component type as the specified array.
	 * <p>
	 * <code>Arrays.newArray(Object[] array, int length)</code>
	 */
	public static <E> E[] newArray(E[] array, int length) {
		return newArray(componentType(array), length);
	}

	/**
	 * Return the specified array's component type, with appropriate support
	 * for generics.
	 */
	public static <E> Class<? extends E> componentType(E[] array) {
		Class<?> rawComponentType = array.getClass().getComponentType();
		@SuppressWarnings("unchecked")
		Class<? extends E> componentType = (Class<? extends E>) rawComponentType;
		return componentType;
	}

	/**
	 * Return a new array with the specified component type and length,
	 * with appropriate support for generics. The component type cannot be a
	 * primitive type.
	 */
	public static <E> E[] newArray(Class<? extends E> componentType, int length) {
		if (componentType.isPrimitive()) {
			throw new IllegalArgumentException("Array class cannot be primitive: " + componentType); //$NON-NLS-1$
		}
		return newArray_(componentType, length);
	}

	/**
	 * assume the component type is not a primitive class
	 */
	@SuppressWarnings("unchecked")
	private static <E> E[] newArray_(Class<? extends E> componentType, int length) {
		return (E[]) ((componentType == OBJECT_CLASS) ?
				new Object[length] :
				Array.newInstance(componentType, length));
	}
	private static final Class<Object> OBJECT_CLASS = Object.class;


	// ********** conversion **********

	/**
	 * Return an array corresponding to the specified iterable.
	 * <p>
	 * <code>Iterable.toArray()</code>
	 * @see Collection#toArray()
	 */
	public static Object[] array(Iterable<?> iterable) {
		return array(iterable.iterator());
	}

	/**
	 * Return an array corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.toArray()</code>
	 * @see Collection#toArray()
	 */
	public static Object[] array(Iterable<?> iterable, int iterableSize) {
		return array(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array corresponding to the specified iterable;
	 * the runtime type of the returned array is that of the specified array.
	 * If the iterable fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of the iterable.
	 * <p>
	 * <code>Iterable.toArray(Object[])</code>
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] array(Iterable<? extends E> iterable, E[] array) {
		return array(iterable.iterator(), array);
	}

	/**
	 * Return an array corresponding to the specified iterable;
	 * the runtime type of the returned array is that of the specified array.
	 * If the iterable fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of the iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.toArray(Object[])</code>
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] array(Iterable<? extends E> iterable, int iterableSize, E[] array) {
		return array(iterable.iterator(), iterableSize, array);
	}

	/**
	 * Return an array corresponding to the specified iterator.
	 * <p>
	 * <code>Iterator.toArray()</code>
	 * @see Collection#toArray()
	 */
	public static Object[] array(Iterator<?> iterator) {
		return iterator.hasNext() ?
				CollectionTools.list(iterator).toArray() :
				EMPTY_OBJECT_ARRAY;
	}

	/**
	 * Return an array corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.toArray()</code>
	 * @see Collection#toArray()
	 */
	public static Object[] array(Iterator<?> iterator, int iteratorSize) {
		return iterator.hasNext() ?
				CollectionTools.list(iterator, iteratorSize).toArray() :
				EMPTY_OBJECT_ARRAY;
	}

	/**
	 * Return an array corresponding to the specified iterator;
	 * the runtime type of the returned array is that of the specified array.
	 * If the iterator fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of the iterator.
	 * <p>
	 * <code>Iterator.toArray(Object[])</code>
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] array(Iterator<? extends E> iterator, E[] array) {
		return iterator.hasNext() ?
				CollectionTools.list(iterator).toArray(array) :
				emptyArray(array);
	}

	/**
	 * Return an array corresponding to the specified iterator;
	 * the runtime type of the returned array is that of the specified array.
	 * If the iterator fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of the iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.toArray(Object[])</code>
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] array(Iterator<? extends E> iterator, int iteratorSize, E[] array) {
		return iterator.hasNext() ?
				CollectionTools.list(iterator, iteratorSize).toArray(array) :
				emptyArray(array);
	}

	/**
	 * If the specified array is empty, return it;
	 * otherwise, set its first element to null.
	 * @see Collection#toArray(Object[])
	 */
	private static <E> E[] emptyArray(E[] array) {
		return (array.length == 0) ? array : clearFirst(array);
	}

	/**
	 * Set the specified array's first element to null and and return the array.
	 * Assume the array length > 0.
	 */
	private static <E> E[] clearFirst(E[] array) {
		array[0] = null;
		return array;
	}


	// ********** add **********

	/**
	 * Return a new array containing the elements in the
	 * specified array followed by the specified object to be added.
	 * <p>
	 * <code>Arrays.add(Object[] array, Object o)</code>
	 */
	public static <E> E[] add(E[] array, E value) {
		int len = array.length;
		E[] result = newArray(array, len + 1);
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array containing the elements in the
	 * specified array with the specified object added at the specified index.
	 * <p>
	 * <code>Arrays.add(Object[] array, int index, Object o)</code>
	 */
	public static <E> E[] add(E[] array, int index, E value) {
		int len = array.length;
		E[] result = newArray(array, len + 1);
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (index < len) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}

	/**
	 * Return a new array containing the elements in the
	 * specified array followed by the specified value to be added.
	 * <p>
	 * <code>Arrays.add(char[] array, char value)</code>
	 */
	public static char[] add(char[] array, char value) {
		int len = array.length;
		char[] result = new char[len + 1];
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array containing the elements in the
	 * specified array with the specified value added at the specified index.
	 * <p>
	 * <code>Arrays.add(char[] array, int index, char value)</code>
	 */
	public static char[] add(char[] array, int index, char value) {
		int len = array.length;
		char[] result = new char[len + 1];
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (index < len) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}

	/**
	 * Return a new array containing the elements in the
	 * specified array followed by the specified value to be added.
	 * <p>
	 * <code>Arrays.add(int[] array, int value)</code>
	 */
	public static int[] add(int[] array, int value) {
		int len = array.length;
		int[] result = new int[len + 1];
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array containing the elements in the
	 * specified array with the specified value added at the specified index.
	 * <p>
	 * <code>Arrays.add(int[] array, int index, int value)</code>
	 */
	public static int[] add(int[] array, int index, int value) {
		int len = array.length;
		int[] result = new int[len + 1];
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (index < len) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}


	// ********** add all **********

	/**
	 * Return an array containing the elements in the
	 * specified array followed by the elements
	 * in the specified collection.
	 *<p>
	 * <code>Arrays.addAll(Object[] array, Collection collection)</code>
	 */
	public static <E> E[] addAll(E[] array, Collection<? extends E> collection) {
		return addAll(array, collection, collection.size());
	}

	/**
	 * check collection size
	 */
	private static <E> E[] addAll(E[] array, Collection<? extends E> collection, int collectionSize) {
		return (collectionSize == 0) ? array : addAll_(array, collection, collectionSize);
	}

	/**
	 * assume the collection is non-empty
	 */
	private static <E> E[] addAll_(E[] array, Collection<? extends E> collection) {
		return addAll_(array, collection, collection.size());
	}

	/**
	 * assume collection size > zero
	 */
	private static <E> E[] addAll_(E[] array, Collection<? extends E> collection, int collectionSize) {
		return addAll(array, collection, array.length, collectionSize);
	}

	/**
	 * assume collection size > zero; check array length
	 */
	private static <E> E[] addAll(E[] array, Collection<? extends E> collection, int arrayLength, int collectionSize) {
		return (arrayLength == 0) ?
				collection.toArray(newArray(array, collectionSize)) :
				addAll_(array, collection, arrayLength, collectionSize);
	}

	/**
	 * assume array length and collection size > zero
	 */
	private static <E> E[] addAll_(E[] array, Collection<? extends E> collection, int arrayLength, int collectionSize) {
		E[] result = newArray(array, arrayLength + collectionSize);
		System.arraycopy(array, 0, result, 0, arrayLength);
		int i = arrayLength;
		for (E element : collection) {
			result[i++] = element;
		}
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array followed by the elements
	 * in the specified iterable.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] addAll(E[] array, Iterable<? extends E> iterable) {
		return addAll(array, iterable.iterator());
	}

	/**
	 * Return an array containing the elements in the
	 * specified array followed by the elements
	 * in the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] addAll(E[] array, Iterable<? extends E> iterable, int iterableSize) {
		return addAll(array, iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array containing the elements in the
	 * specified array followed by the elements
	 * in the specified iterator.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] addAll(E[] array, Iterator<? extends E> iterator) {
		return iterator.hasNext() ? addAll_(array, CollectionTools.list(iterator)) : array;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array followed by the elements
	 * in the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] addAll(E[] array, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() ? addAll_(array, CollectionTools.list(iterator, iteratorSize)) : array;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array 1 followed by the elements
	 * in the specified array 2.
	 * <p>
	 * <code>Arrays.addAll(Object[] array1, Object[] array2)</code>
	 */
	public static <E> E[] addAll(E[] array1, E... array2) {
		return addAll(array1, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static <E> E[] addAll(E[] array1, E[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static <E> E[] addAll_(E[] array1, E[] array2, int array2Length) {
		return addAll(array1, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static <E> E[] addAll(E[] array1, E[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ? array2 : addAll_(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0
	 */
	private static <E> E[] addAll_(E[] array1, E[] array2, int array1Length, int array2Length) {
		E[] result = newArray(array1, array1Length + array2Length);
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * first specified array with the objects in the second
	 * specified array added at the specified index.
	 * <p>
	 * <code>Arrays.addAll(Object[] array1, int index, Object[] array2)</code>
	 */
	public static <E> E[] addAll(E[] array1, int index, E... array2) {
		return addAll(array1, index, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static <E> E[] addAll(E[] array1, int index, E[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, index, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static <E> E[] addAll_(E[] array1, int index, E[] array2, int array2Length) {
		return addAll(array1, index, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static <E> E[] addAll(E[] array1, int index, E[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ?
					array2 :
					(index == array1Length) ?  // 'array2' added to end of 'array1'
						addAll_(array1, array2, array1Length, array2Length) :
						addAll_(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0 and index != array 1 length
	 */
	private static <E> E[] addAll_(E[] array1, int index, E[] array2, int array1Length, int array2Length) {
		E[] result = newArray(array1, array1Length + array2Length);
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array with the elements
	 * in the specified collection inserted at the specified index.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, int index, Collection c)</code>
	 */
	public static <E> E[] addAll(E[] array, int index, Collection<? extends E> collection) {
		return addAll(array, index, collection, collection.size());
	}

	/**
	 * check collection size
	 */
	private static <E> E[] addAll(E[] array, int index, Collection<? extends E> collection, int collectionSize) {
		return (collectionSize == 0) ? array : addAll_(array, index, collection, collectionSize);
	}

	/**
	 * assume collection size > 0
	 */
	private static <E> E[] addAll_(E[] array, int index, Collection<? extends E> collection, int collectionSize) {
		return addAll(array, index, collection, array.length, collectionSize);
	}

	/**
	 * assume collection size > 0; check array length
	 */
	private static <E> E[] addAll(E[] array, int index, Collection<? extends E> collection, int arrayLength, int collectionSize) {
		if (arrayLength == 0) {
			if (index == 0) {
				return collection.toArray(newArray(array, collectionSize));
			}
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return (index == arrayLength) ?  // 'collection' added to end of 'array'
				addAll_(array, collection, arrayLength, collectionSize) :
				addAll_(array, index, collection, arrayLength, collectionSize);
	}

	/**
	 * assume array length and collection size > 0 and index != array length
	 */
	private static <E> E[] addAll_(E[] array, int index, Collection<? extends E> collection, int arrayLength, int collectionSize) {
		E[] result = newArray(array, arrayLength + collectionSize);
		System.arraycopy(array, 0, result, 0, index);
		int i = index;
		for (E item : collection) {
			result[i++] = item;
		}
		System.arraycopy(array, index, result, index + collectionSize, arrayLength - index);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array with the elements
	 * in the specified iterable inserted at the specified index.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, int index, Iterable iterable)</code>
	 */
	public static <E> E[] addAll(E[] array, int index, Iterable<? extends E> iterable) {
		return addAll(array, index, iterable.iterator());
	}

	/**
	 * Return an array containing the elements in the
	 * specified array with the elements
	 * in the specified iterable inserted at the specified index.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, int index, Iterable iterable)</code>
	 */
	public static <E> E[] addAll(E[] array, int index, Iterable<? extends E> iterable, int iterableSize) {
		return addAll(array, index, iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array containing the elements in the
	 * specified array with the elements
	 * in the specified iterator inserted at the specified index.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, int index, Iterator iterator)</code>
	 */
	public static <E> E[] addAll(E[] array, int index, Iterator<? extends E> iterator) {
		return iterator.hasNext() ? addAll_(array, index, CollectionTools.list(iterator)) : array;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array with the elements
	 * in the specified iterator inserted at the specified index.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Arrays.addAll(Object[] array, int index, Iterator iterator)</code>
	 */
	public static <E> E[] addAll(E[] array, int index, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() ? addAll_(array, index, CollectionTools.list(iterator, iteratorSize)) : array;
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] addAll_(E[] array, int index, Collection<? extends E> collection) {
		return addAll_(array, index, collection, collection.size());
	}

	/**
	 * Return an array containing the elements in the
	 * specified array 1 followed by the elements
	 * in the specified array 2.
	 * <p>
	 * <code>Arrays.addAll(char[] array1, char[] array2)</code>
	 */
	public static char[] addAll(char[] array1, char... array2) {
		return addAll(array1, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static char[] addAll(char[] array1, char[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static char[] addAll_(char[] array1, char[] array2, int array2Length) {
		return addAll(array1, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static char[] addAll(char[] array1, char[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ? array2 : addAll_(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0
	 */
	private static char[] addAll_(char[] array1, char[] array2, int array1Length, int array2Length) {
		char[] result = new char[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * first specified array with the objects in the second
	 * specified array added at the specified index.
	 * <p>
	 * <code>Arrays.add(char[] array1, int index, char[] array2)</code>
	 */
	public static char[] addAll(char[] array1, int index, char... array2) {
		return addAll(array1, index, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static char[] addAll(char[] array1, int index, char[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, index, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static char[] addAll_(char[] array1, int index, char[] array2, int array2Length) {
		return addAll(array1, index, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static char[] addAll(char[] array1, int index, char[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ?
					array2 :
					(index == array1Length) ?  // 'array2' added to end of 'array1'
						addAll_(array1, array2, array1Length, array2Length) :
						addAll_(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0 and index != array 1 length
	 */
	private static char[] addAll_(char[] array1, int index, char[] array2, int array1Length, int array2Length) {
		char[] result = new char[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * specified array 1 followed by the elements
	 * in the specified array 2.
	 * <p>
	 * <code>Arrays.addAll(int[] array1, int[] array2)</code>
	 */
	public static int[] addAll(int[] array1, int... array2) {
		return addAll(array1, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static int[] addAll(int[] array1, int[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static int[] addAll_(int[] array1, int[] array2, int array2Length) {
		return addAll(array1, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static int[] addAll(int[] array1, int[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ? array2 : addAll_(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0
	 */
	private static int[] addAll_(int[] array1, int[] array2, int array1Length, int array2Length) {
		int[] result = new int[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return an array containing the elements in the
	 * first specified array with the objects in the second
	 * specified array added at the specified index.
	 * <p>
	 * <code>Arrays.add(int[] array1, int index, int[] array2)</code>
	 */
	public static int[] addAll(int[] array1, int index, int... array2) {
		return addAll(array1, index, array2, array2.length);
	}

	/**
	 * check array 2 length
	 */
	private static int[] addAll(int[] array1, int index, int[] array2, int array2Length) {
		return (array2Length == 0) ? array1 : addAll_(array1, index, array2, array2Length);
	}

	/**
	 * assume array 2 length > 0
	 */
	private static int[] addAll_(int[] array1, int index, int[] array2, int array2Length) {
		return addAll(array1, index, array2, array1.length, array2Length);
	}

	/**
	 * assume array 2 length > 0; check array 1 length
	 */
	private static int[] addAll(int[] array1, int index, int[] array2, int array1Length, int array2Length) {
		return (array1Length == 0) ?
					array2 :
					(index == array1Length) ?  // 'array2' added to end of 'array1'
						addAll_(array1, array2, array1Length, array2Length) :
						addAll_(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0 and index != array 1 length
	 */
	private static int[] addAll_(int[] array1, int index, int[] array2, int array1Length, int array2Length) {
		int[] result = new int[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}


	// ********** clear **********

	/**
	 * Return an empty array with the same component type as the specified array.
	 * <p>
	 * <code>Arrays.clear(Object[] array)</code>
	 */
	public static <E> E[] clear(E[] array) {
		return (array.length == 0) ? array : newArray(array, 0);
	}


	// ********** concatenate **********

	/**
	 * Return an array containing all the elements in all the
	 * specified arrays, concatenated in the specified order.
	 * This is useful for building constant arrays out of other constant arrays.
	 * <p>
	 * <code>Arrays.concatenate(Object[]... arrays)</code>
	 */
	public static <E> E[] concatenate(E[]... arrays) {
		int len = 0;
		for (E[] array : arrays) {
			len += array.length;
		}
		E[] result = newArray(arrays[0], len);
		if (len == 0) {
			return result;
		}
		int current = 0;
		for (E[] array : arrays) {
			int arrayLength = array.length;
			if (arrayLength > 0) {
				System.arraycopy(array, 0, result, current, arrayLength);
				current += arrayLength;
			}
		}
		return result;
	}

	/**
	 * Return an array containing all the elements in  all the
	 * specified arrays, concatenated in the specified order.
	 * This is useful for building constant arrays out other constant arrays.
	 * <p>
	 * <code>Arrays.concatenate(char[]... arrays)</code>
	 */
	public static char[] concatenate(char[]... arrays) {
		int len = 0;
		for (char[] array : arrays) {
			len += array.length;
		}
		if (len == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		char[] result = new char[len];
		int current = 0;
		for (char[] array : arrays) {
			int arrayLength = array.length;
			if (arrayLength != 0) {
				System.arraycopy(array, 0, result, current, arrayLength);
				current += arrayLength;
			}
		}
		return result;
	}

	/**
	 * Return an array containing all the elements in  all the
	 * specified arrays, concatenated in the specified order.
	 * This is useful for building constant arrays out other constant arrays.
	 * <p>
	 * <code>Arrays.concatenate(int[]... arrays)</code>
	 */
	public static int[] concatenate(int[]... arrays) {
		int len = 0;
		for (int[] array : arrays) {
			len += array.length;
		}
		if (len == 0) {
			return EMPTY_INT_ARRAY;
		}
		int[] result = new int[len];
		int current = 0;
		for (int[] array : arrays) {
			int arrayLength = array.length;
			if (arrayLength != 0) {
				System.arraycopy(array, 0, result, current, arrayLength);
				current += arrayLength;
			}
		}
		return result;
	}


	// ********** contains **********

	/**
	 * Return whether the specified array contains the
	 * specified element.
	 * <p>
	 * <code>Arrays.contains(Object[] array, Object o)</code>
	 */
	public static boolean contains(Object[] array, Object value) {
		return contains(array, value, array.length);
	}

	/**
	 * check array length
	 */
	private static boolean contains(Object[] array, Object value, int arrayLength) {
		return (arrayLength == 0) ? false : contains_(array, value, arrayLength);
	}

	/**
	 * assume array length > 0
	 */
	public static boolean contains_(Object[] array, Object value, int arrayLength) {
		if (value == null) {
			for (int i = arrayLength; i-- > 0; ) {
				if (array[i] == null) {
					return true;
				}
			}
		} else {
			for (int i = arrayLength; i-- > 0; ) {
				if (value.equals(array[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return whether the specified array contains the
	 * specified element.
	 * <p>
	 * <code>Arrays.contains(char[] array, char value)</code>
	 */
	public static boolean contains(char[] array, char value) {
		return contains(array, value, array.length);
	}

	/**
	 * check array length
	 */
	private static boolean contains(char[] array, char value, int arrayLength) {
		return (arrayLength == 0) ? false : contains_(array, value, arrayLength);
	}

	/**
	 * assume array length > 0
	 */
	private static boolean contains_(char[] array, char value, int arrayLength) {
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified array contains the
	 * specified element.
	 * <p>
	 * <code>Arrays.contains(int[] array, int value)</code>
	 */
	public static boolean contains(int[] array, int value) {
		return contains(array, value, array.length);
	}

	/**
	 * check array length
	 */
	private static boolean contains(int[] array, int value, int arrayLength) {
		return (arrayLength == 0) ? false : contains_(array, value, arrayLength);
	}

	/**
	 * assume array length > 0
	 */
	private static boolean contains_(int[] array, int value, int arrayLength) {
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}


	// ********** contains all **********

	/**
	 * Return whether the specified array contains all of the
	 * elements in the specified collection.
	 * <p>
	 * <code>Arrays.containsAll(Object[] array, Collection collection)</code>
	 */
	public static boolean containsAll(Object[] array, Collection<?> collection) {
		return containsAll(array, collection.iterator());
	}

	/**
	 * Return whether the specified array contains all of the
	 * elements in the specified iterable.
	 * <p>
	 * <code>Arrays.containsAll(Object[] array, Iterable iterable)</code>
	 */
	public static boolean containsAll(Object[] array, Iterable<?> iterable) {
		return containsAll(array, iterable.iterator());
	}

	/**
	 * Return whether the specified array contains all of the
	 * elements in the specified iterator.
	 * <p>
	 * <code>Arrays.containsAll(Object[] array, Iterator iterator)</code>
	 */
	public static boolean containsAll(Object[] array, Iterator<?> iterator) {
		// use hashed lookup
		HashSet<Object> set = CollectionTools.set(array);
		while (iterator.hasNext()) {
			if ( ! set.contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the
	 * elements in the specified array 2.
	 * <p>
	 * <code>Arrays.containsAll(Object[] array1, Object[] array2)</code>
	 */
	public static boolean containsAll(Object[] array1, Object... array2) {
		// use hashed lookup
		HashSet<Object> set = CollectionTools.set(array1);
		for (int i = array2.length; i-- > 0; ) {
			if ( ! set.contains(array2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the
	 * elements in the specified array 2.
	 * <p>
	 * <code>Arrays.containsAll(char[] array1, char[] array2)</code>
	 */
	public static boolean containsAll(char[] array1, char... array2) {
		for (int i = array2.length; i-- > 0; ) {
			if ( ! contains(array1, array2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the
	 * elements in the specified array 2.
	 * <p>
	 * <code>Arrays.containsAll(int[] array1, int[] array2)</code>
	 */
	public static boolean containsAll(int[] array1, int... array2) {
		for (int i = array2.length; i-- > 0; ) {
			if ( ! contains(array1, array2[i])) {
				return false;
			}
		}
		return true;
	}


	// ********** diff **********

	/**
	 * Return the index of the first elements in the specified
	 * arrays that are different, beginning at the end.
	 * If the arrays are identical, return -1.
	 * If the arrays are different sizes, return the index of the
	 * last element in the longer array.
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 */
	public static int diffEnd(Object[] array1, Object[] array2) {
		int len1 = array1.length;
		int len2 = array2.length;
		if (len1 != len2) {
			return Math.max(len1, len2) - 1;
		}
		for (int i = len1 - 1; i > -1; i--) {
			Object o = array1[i];
			if (o == null) {
				if (array2[i] != null) {
					return i;
				}
			} else {
				if ( ! o.equals(array2[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return [size, -1].
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 * @see #diffStart(Object[], Object[])
	 * @see #diffEnd(Object[], Object[])
	 */
	public static Range diffRange(Object[] array1, Object[] array2) {
		int end = diffEnd(array1, array2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(array1.length, end);
		}
		// the lists are different, calculate the start of the range
		return new Range(diffStart(array1, array2), end);
	}

	/**
	 * Return the index of the first elements in the specified
	 * arrays that are different. If the arrays are identical, return
	 * the size of the two arrays (i.e. one past the last index).
	 * If the arrays are different sizes and all the elements in
	 * the shorter array match their corresponding elements in
	 * the longer array, return the size of the shorter array
	 * (i.e. one past the last index of the shorter array).
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 */
	public static int diffStart(Object[] array1, Object[] array2) {
		int end = Math.min(array1.length, array2.length);
		for (int i = 0; i < end; i++) {
			Object o = array1[i];
			if (o == null) {
				if (array2[i] != null) {
					return i;
				}
			} else {
				if ( ! o.equals(array2[i])) {
					return i;
				}
			}
		}
		return end;
	}


	// ********** identity diff **********

	/**
	 * Return the index of the first elements in the specified
	 * arrays that are different, beginning at the end.
	 * If the arrays are identical, return -1.
	 * If the arrays are different sizes, return the index of the
	 * last element in the longer array.
	 * Use object identity to compare the elements.
	 */
	public static int identityDiffEnd(Object[] array1, Object[] array2) {
		int len1 = array1.length;
		int len2 = array2.length;
		if (len1 != len2) {
			return Math.max(len1, len2) - 1;
		}
		for (int i = len1 - 1; i > -1; i--) {
			if (array1[i] != array2[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return [size, -1].
	 * Use object identity to compare the elements.
	 * @see #identityDiffStart(Object[], Object[])
	 * @see #identityDiffEnd(Object[], Object[])
	 */
	public static Range identityDiffRange(Object[] array1, Object[] array2) {
		int end = identityDiffEnd(array1, array2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(array1.length, end);
		}
		// the lists are different, calculate the start of the range
		return new Range(identityDiffStart(array1, array2), end);
	}

	/**
	 * Return the index of the first elements in the specified
	 * arrays that are different. If the arrays are identical, return
	 * the size of the two arrays (i.e. one past the last index).
	 * If the arrays are different sizes and all the elements in
	 * the shorter array match their corresponding elements in
	 * the longer array, return the size of the shorter array
	 * (i.e. one past the last index of the shorter array).
	 * Use object identity to compare the elements.
	 */
	public static int identityDiffStart(Object[] array1, Object[] array2) {
		int end = Math.min(array1.length, array2.length);
		for (int i = 0; i < end; i++) {
			if (array1[i] != array2[i]) {
				return i;
			}
		}
		return end;
	}


	// ********** elements are identical **********

	/**
	 * Return whether the specified arrays contain the same elements.
	 * <p>
	 * <code>Arrays.identical(Object[] array1, Object[] array2)</code>
	 */
	public static boolean elementsAreIdentical(Object[] array1, Object[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		int length = array1.length;
		if (array2.length != length) {
			return false;
		}
		for (int i = length; i-- > 0; ) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}


	// ********** index of **********

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.indexOf(Object[] array, Object o)</code>
	 */
	public static int indexOf(Object[] array, Object value) {
		int len = array.length;
		if (value == null) {
			for (int i = 0; i < len; i++) {
				if (array[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < len; i++) {
				if (value.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.identityIndexOf(Object[] array, Object o)</code>
	 */
	public static int identityIndexOf(Object[] array, Object value) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.indexOf(char[] array, char value)</code>
	 */
	public static int indexOf(char[] array, char value) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.indexOf(int[] array, int value)</code>
	 */
	public static int indexOf(int[] array, int value) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}


	// ********** insertion index of **********

	/**
	 * Return the maximum index of where the specified comparable object
	 * should be inserted into the specified sorted array and still keep
	 * the array sorted.
	 */
	public static <E extends Comparable<? super E>> int insertionIndexOf(E[] sortedArray, Comparable<E> value) {
		int len = sortedArray.length;
		for (int i = 0; i < len; i++) {
			if (value.compareTo(sortedArray[i]) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return the maximum index of where the specified comparable object
	 * should be inserted into the specified sorted array and still keep
	 * the array sorted.
	 */
	public static <E> int insertionIndexOf(E[] sortedArray, E value, Comparator<? super E> comparator) {
		int len = sortedArray.length;
		for (int i = 0; i < len; i++) {
			if (comparator.compare(value, sortedArray[i]) < 0) {
				return i;
			}
		}
		return len;
	}


	// ********** last index of **********

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified array;
	 * return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.lastIndexOf(Object[] array, Object o)</code>
	 */
	public static int lastIndexOf(Object[] array, Object value) {
		int len = array.length;
		if (value == null) {
			for (int i = len; i-- > 0; ) {
				if (array[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = len; i-- > 0; ) {
				if (value.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.lastIndexOf(char[] array, char value)</code>
	 */
	public static int lastIndexOf(char[] array, char value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified array,
	 * or return -1 if there is no such index.
	 * <p>
	 * <code>Arrays.lastIndexOf(int[] array, int value)</code>
	 */
	public static int lastIndexOf(int[] array, int value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}


	// ********** min/max **********

	/**
	 * Return the character from the specified array with the minimum value.
	 * <p>
	 * <code>Arrays.min(char[] array)</code>
	 */
	public static char min(char... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int last = len - 1;
		char min = array[last];
		for (int i = last; i-- > 0; ) {
			char c = array[i];
			if (c < min) {
				min = c;
			}
		}
		return min;
	}

	/**
	 * Return the integer from the specified array with the minimum value.
	 * <p>
	 * <code>Arrays.min(int[] array)</code>
	 */
	public static int min(int... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int last = len - 1;
		int min = array[last];
		for (int i = last; i-- > 0; ) {
			int x = array[i];
			if (x < min) {
				min = x;
			}
		}
		return min;
	}

	/**
	 * Return the character from the specified array with the maximum value.
	 * <p>
	 * <code>Arrays.max(char[] array)</code>
	 */
	public static char max(char... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int last = len - 1;
		char max = array[last];
		for (int i = last; i-- > 0; ) {
			char c = array[i];
			if (c > max) {
				max = c;
			}
		}
		return max;
	}

	/**
	 * Return the integer from the specified array with the maximum value.
	 * <p>
	 * <code>Arrays.max(int[] array)</code>
	 */
	public static int max(int... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int last = len - 1;
		int max = array[last];
		for (int i = last; i-- > 0; ) {
			int x = array[i];
			if (x > max) {
				max = x;
			}
		}
		return max;
	}


	// ********** move **********

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(Object[] array, int targetIndex, int sourceIndex)</code>
	 */
	public static <E> E[] move(E[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume target index != source index
	 */
	private static <E> E[] move_(E[] array, int targetIndex, int sourceIndex) {
		E temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(Object[] array, int targetIndex, int sourceIndex, int length)</code>
	 */
	public static <E> E[] move(E[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		E[] temp = newArray(array, length);
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(int[] array, int targetIndex, int sourceIndex)</code>
	 */
	public static int[] move(int[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static int[] move_(int[] array, int targetIndex, int sourceIndex) {
		int temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(int[] array, int targetIndex, int sourceIndex, int length)</code>
	 */
	public static int[] move(int[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		int[] temp = new int[length];
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(char[] array, int targetIndex, int sourceIndex)</code>
	 */
	public static char[] move(char[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static char[] move_(char[] array, int targetIndex, int sourceIndex) {
		char temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array.
	 * <p>
	 * <code>Arrays.move(char[] array, int targetIndex, int sourceIndex, int length)</code>
	 */
	public static char[] move(char[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		char[] temp = new char[length];
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}


	// ********** remove **********

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.remove(Object[] array, Object value)</code>
	 */
	public static <E> E[] remove(E[] array, Object value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.remove(char[] array, char value)</code>
	 */
	public static char[] remove(char[] array, char value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.remove(int[] array, int value)</code>
	 */
	public static int[] remove(int[] array, int value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the first element removed.
	 * <p>
	 * <code>Arrays.removeFirst(Object[] array)</code>
	 */
	public static <E> E[] removeFirst(E[] array) {
		return removeElementAtIndex(array, 0);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the first element removed.
	 * <p>
	 * <code>Arrays.removeFirst(char[] array)</code>
	 */
	public static char[] removeFirst(char[] array) {
		return removeElementAtIndex(array, 0);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the first element removed.
	 * <p>
	 * <code>Arrays.removeFirst(int[] array)</code>
	 */
	public static int[] removeFirst(int[] array) {
		return removeElementAtIndex(array, 0);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the last element removed.
	 * <p>
	 * <code>Arrays.removeLast(Object[] array)</code>
	 */
	public static <E> E[] removeLast(E[] array) {
		return removeElementAtIndex(array, array.length - 1);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the last element removed.
	 * <p>
	 * <code>Arrays.removeLast(char[] array)</code>
	 */
	public static char[] removeLast(char[] array) {
		return removeElementAtIndex(array, array.length - 1);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the last element removed.
	 * <p>
	 * <code>Arrays.removeLast(int[] array)</code>
	 */
	public static int[] removeLast(int[] array) {
		return removeElementAtIndex(array, array.length - 1);
	}


	// ********** remove all **********

	/**
	 * Remove from the specified array all the elements in
	 * the specified iterable and return the result.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] removeAll(E[] array, Iterable<?> iterable) {
		return removeAll(array, iterable.iterator());
	}

	/**
	 * Remove from the specified array all the elements in
	 * the specified iterable and return the result.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] removeAll(E[] array, Iterable<?> iterable, int iterableSize) {
		return removeAll(array, iterable.iterator(), iterableSize);
	}

	/**
	 * Remove from the specified array all the elements in
	 * the specified iterator and return the result.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] removeAll(E[] array, Iterator<?> iterator) {
		// convert to a set to take advantage of hashed look-up
		return iterator.hasNext() ? removeAll_(array, CollectionTools.set(iterator)) : array;
	}

	/**
	 * Remove from the specified array all the elements in
	 * the specified iterator and return the result.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] removeAll(E[] array, Iterator<?> iterator, int iteratorSize) {
		// convert to a set to take advantage of hashed look-up
		return iterator.hasNext() ? removeAll_(array, CollectionTools.set(iterator, iteratorSize)) : array;
	}

	/**
	 * Remove from the specified array all the elements in
	 * the specified collection and return the result.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array, Collection collection)</code>
	 */
	public static <E> E[] removeAll(E[] array, Collection<?> collection) {
		return collection.isEmpty() ? array : removeAll_(array, collection);
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] removeAll_(E[] array, Collection<?> collection) {
		return removeAll(array, collection, array.length);
	}

	/**
	 * assume collection is non-empty; check array length
	 */
	private static <E> E[] removeAll(E[] array, Collection<?> collection, int arrayLength) {
		return (arrayLength == 0) ? array : removeAll_(array, collection, arrayLength);
	}

	/**
	 * assume collection is non-empty and array length > 0
	 */
	private static <E> E[] removeAll_(E[] array, Collection<?> collection, int arrayLength) {
		// build the indices of the elements that are to remain
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = 0; i < arrayLength; i++) {
			if ( ! collection.contains(array[i])) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays.removeAll(Object[] array1, Object[] array2)</code>
	 */
	public static <E> E[] removeAll(E[] array1, Object... array2) {
		// convert to a set to take advantage of hashed look-up
		return (array2.length == 0) ? array1 : removeAll_(array1, CollectionTools.set(array2));
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays#removeAll(char[] array1, char[] array2)</code>
	 */
	public static char[] removeAll(char[] array1, char... array2) {
		if (array2.length == 0) {
			return array1;
		}
		int array1Length = array1.length;
		if (array1Length == 0) {
			return array1;
		}
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if ( ! contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // nothing was removed
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays#removeAll(int[] array1, int[] array2)</code>
	 */
	public static int[] removeAll(int[] array1, int... array2) {
		if (array2.length == 0) {
			return array1;
		}
		int array1Length = array1.length;
		if (array1Length == 0) {
			return array1;
		}
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if ( ! contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // nothing was removed
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}


	// ********** remove all occurrences **********

	/**
	 * Remove from the specified array all occurrences of
	 * the specified element and return the result.
	 * <p>
	 * <code>Arrays.removeAllOccurrences(Object[] array, Object value)</code>
	 */
	public static <E> E[] removeAllOccurrences(E[] array, Object value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		if (value == null) {
			for (int i = arrayLength; i-- > 0; ) {
				if (array[i] != null) {
					indices[j++] = i;
				}
			}
		} else {
			for (int i = array.length; i-- > 0; ) {
				if ( ! value.equals(array[i])) {
					indices[j++] = i;
				}
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the specified array all occurrences of
	 * the specified element and return the result.
	 * <p>
	 * <code>Arrays.removeAllOccurrences(char[] array, char value)</code>
	 */
	public static char[] removeAllOccurrences(char[] array, char value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] != value) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the specified array all occurrences of
	 * the specified element and return the result.
	 * <p>
	 * <code>Arrays.removeAllOccurrences(int[] array, int value)</code>
	 */
	public static int[] removeAllOccurrences(int[] array, int value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] != value) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}


	// ********** remove duplicate elements **********

	/**
	 * Remove any duplicate elements from the specified array,
	 * while maintaining the order.
	 */
	public static <E> E[] removeDuplicateElements(E... array) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		ArrayList<E> temp = CollectionTools.list(array);
		return CollectionTools.removeDuplicateElements(temp, len) ?
					temp.toArray(newArray(array, temp.size())) :
					array;
	}


	// ********** remove element at index **********

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.removeElementAtIndex(Object[] array, int index)</code>
	 */
	public static <E> E[] removeElementAtIndex(E[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.removeElementAtIndex(char[] array, int index)</code>
	 */
	public static char[] removeElementAtIndex(char[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified element removed.
	 * <p>
	 * <code>Arrays.removeElementAtIndex(int[] array, int index)</code>
	 */
	public static int[] removeElementAtIndex(int[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}


	// ********** remove elements at index **********

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified elements removed.
	 * <p>
	 * <code>Arrays.removeElementsAtIndex(Object[] array, int index, int length)</code>
	 */
	public static <E> E[] removeElementsAtIndex(E[] array, int index, int length) {
		if (length == 0) {
			return array;
		}
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		E[] result = newArray(array, newLength);
		if ((newLength == 0) && (index == 0)) {
			return result;  // performance tweak
		}
		if (index != 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		int length2 = newLength - index;
		if (length2 != 0) {
			System.arraycopy(array, index + length, result, index, length2);
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified elements removed.
	 * <p>
	 * <code>Arrays.removeElementsAtIndex(char[] array, int index, int length)
	 */
	public static char[] removeElementsAtIndex(char[] array, int index, int length) {
		if (length == 0) {
			return array;
		}
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		if ((newLength == 0) && (index == 0)) {
			return EMPTY_CHAR_ARRAY;  // performance tweak
		}
		char[] result = new char[newLength];
		if (index != 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		int length2 = newLength - index;
		if (length2 != 0) {
			System.arraycopy(array, index + length, result, index, length2);
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the
	 * specified array with the specified elements removed.
	 * <p>
	 * <code>Arrays.removeElementsAtIndex(int[] array, int index, int length)
	 */
	public static int[] removeElementsAtIndex(int[] array, int index, int length) {
		if (length == 0) {
			return array;
		}
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		if ((newLength == 0) && (index == 0)) {
			return EMPTY_INT_ARRAY;  // performance tweak
		}
		int[] result = new int[newLength];
		if (index != 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		int length2 = newLength - index;
		if (length2 != 0) {
			System.arraycopy(array, index + length, result, index, length2);
		}
		return result;
	}


	// ********** replace all **********

	/**
	 * Replace all occurrences of the specified old value with
	 * the specified new value. Return the altered array.
	 * <p>
	 * <code>Arrays.replaceAll(Object[] array, Object oldValue, Object newValue)</code>
	 */
	public static <E> E[] replaceAll(E[] array, Object oldValue, E newValue) {
		if (oldValue == null) {
			for (int i = array.length; i-- > 0; ) {
				if (array[i] == null) {
					array[i] = newValue;
				}
			}
		} else {
			for (int i = array.length; i-- > 0; ) {
				if (oldValue.equals(array[i])) {
					array[i] = newValue;
				}
			}
		}
		return array;
	}

	/**
	 * Replace all occurrences of the specified old value with
	 * the specified new value. Return the altered array.
	 *<p>
	 * <code> Arrays.replaceAll(int[] array, int oldValue, int newValue)</code>
	 */
	public static int[] replaceAll(int[] array, int oldValue, int newValue) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
		return array;
	}

	/**
	 * Replace all occurrences of the specified old value with
	 * the specified new value. Return the altered array.
	 * <p>
	 * <code>Arrays.replaceAll(char[] array, char oldValue, char newValue)</code>
	 */
	public static char[] replaceAll(char[] array, char oldValue, char newValue) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
		return array;
	}


	// ********** retain all **********

	/**
	 * Retain in the specified array all the elements in
	 * the specified iterable and return the result.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] retainAll(E[] array, Iterable<?> iterable) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterable.iterator());
	}

	/**
	 * Retain in the specified array all the elements in
	 * the specified iterable and return the result.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array, Iterable iterable)</code>
	 */
	public static <E> E[] retainAll(E[] array, Iterable<?> iterable, int iterableSize) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterable.iterator(), iterableSize);
	}

	/**
	 * Retain in the specified array all the elements in
	 * the specified iterator and return the result.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] retainAll(E[] array, Iterator<?> iterator) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterator);
	}

	/**
	 * Retain in the specified array all the elements in
	 * the specified iterator and return the result.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array, Iterator iterator)</code>
	 */
	public static <E> E[] retainAll(E[] array, Iterator<?> iterator, int iteratorSize) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterator, iteratorSize);
	}

	/**
	 * assume array length > 0
	 */
	private static <E> E[] retainAll(E[] array, int arrayLength, Iterator<?> iterator) {
		return iterator.hasNext() ?
				retainAll_(array, CollectionTools.set(iterator), arrayLength) :
				newArray(array, 0);
	}

	/**
	 * assume array length > 0
	 */
	private static <E> E[] retainAll(E[] array, int arrayLength, Iterator<?> iterator, int iteratorSize) {
		return iterator.hasNext() ?
				retainAll_(array, CollectionTools.set(iterator, iteratorSize), arrayLength) :
				newArray(array, 0);
	}

	/**
	 * Retain in the specified array all the elements in
	 * the specified collection and return the result.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array, Collection collection)</code>
	 */
	public static <E> E[] retainAll(E[] array, Collection<?> collection) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, collection, arrayLength);
	}

	/**
	 * assume array length > 0
	 */
	private static <E> E[] retainAll(E[] array, Collection<?> collection, int arrayLength) {
		return collection.isEmpty() ?
				newArray(array, 0) :
				retainAll_(array, collection, arrayLength);
	}

	/**
	 * assume collection is non-empty and array length > 0
	 */
	private static <E> E[] retainAll_(E[] array, Collection<?> collection, int arrayLength) {
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = 0; i < arrayLength; i++) {
			if (collection.contains(array[i])) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // everything was retained
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays.retainAll(Object[] array1, Object[] array2)</code>
	 */
	public static <E> E[] retainAll(E[] array1, Object[] array2) {
		int array1Length = array1.length;
		return (array1Length == 0) ?
				array1 :
				(array2.length == 0) ?
					newArray(array1, 0) :
					retainAll(array1, CollectionTools.set(array2), array1Length);
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays.retainAll(char[] array1, char[] array2)</code>
	 */
	public static char[] retainAll(char[] array1, char... array2) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array1 : retainAll(array1, array2, array1Length);
	}

	/**
	 * assume array 1 length > 0
	 */
	private static char[] retainAll(char[] array1, char[] array2, int array1Length) {
		int array2Length = array2.length;
		return (array2Length == 0) ? EMPTY_CHAR_ARRAY : retainAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0
	 */
	private static char[] retainAll(char[] array1, char[] array2, int array1Length, int array2Length) {
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if (contains_(array2, array1[i], array2Length)) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // everything was retained
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in
	 * the second specified array and return the result.
	 * <p>
	 * <code>Arrays.retainAll(int[] array1, int[] array2)</code>
	 */
	public static int[] retainAll(int[] array1, int... array2) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array1 : retainAll(array1, array2, array1Length);
	}

	/**
	 * assume array 1 length > 0
	 */
	private static int[] retainAll(int[] array1, int[] array2, int array1Length) {
		int array2Length = array2.length;
		return (array2Length == 0) ? EMPTY_INT_ARRAY : retainAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume both array lengths > 0
	 */
	private static int[] retainAll(int[] array1, int[] array2, int array1Length, int array2Length) {
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if (contains_(array2, array1[i], array2Length)) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // everything was retained
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}


	// ********** reverse **********

	/**
	 * Return the array, reversed.
	 * <p>
	 * <code>Arrays.reverse(Object... array)</code>
	 */
	public static <E> E[] reverse(E... array) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}

	/**
	 * Return the array, reversed.
	 * <p>
	 * <code>Arrays.reverse(char... array)</code>
	 */
	public static char[] reverse(char... array) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}

	/**
	 * Return the array, reversed.
	 * <p>
	 * <code>Arrays.reverse(int... array)</code>
	 */
	public static int[] reverse(int... array) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}


	// ********** rotate **********

	/**
	 * Return the rotated array after rotating it one position.
	 * <p>
	 * <code>Arrays.rotate(Object[] array)</code>
	 */
	public static <E> E[] rotate(E... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * <p>
	 * <code>Arrays.rotate(Object[] array, int distance)</code>
	 */
	public static <E> E[] rotate(E[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			E displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				E temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}

	/**
	 * Return the rotated array after rotating it one position.
	 * <p>
	 * <code>Arrays.rotate(char[] array)</code>
	 */
	public static char[] rotate(char... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * <p>
	 * <code>Arrays.rotate(char[] array, int distance)</code>
	 */
	public static char[] rotate(char[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			char displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				char temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}

	/**
	 * Return the rotated array after rotating it one position.
	 * <p>
	 * <code>Arrays.rotate(int[] array)</code>
	 */
	public static int[] rotate(int... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * <p>
	 * <code>Arrays.rotate(int[] array, int distance)</code>
	 */
	public static int[] rotate(int[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			int displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				int temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}


	// ********** shuffle **********

	private static final Random RANDOM = new Random();

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(Object... array)</code>
	 */
	public static <E> E[] shuffle(E... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(Object[] array, Random r)</code>
	 */
	public static <E> E[] shuffle(E[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			swap(array, i, random.nextInt(len));
		}
		return array;
	}

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(char... array)</code>
	 */
	public static char[] shuffle(char... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(char[] array, Random r)</code>
	 */
	public static char[] shuffle(char[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			swap(array, i, random.nextInt(len));
		}
		return array;
	}

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(int... array)</code>
	 */
	public static int[] shuffle(int... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it.
	 * <p>
	 * <code>Arrays.shuffle(int[] array, Random r)</code>
	 */
	public static int[] shuffle(int[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			swap(array, i, random.nextInt(len));
		}
		return array;
	}


	// ********** sub-array **********

	/**
	 * Return a sub-array of the specified array with elements copied from
	 * the specified range. The "from" index is inclusive; the "to" index is exclusive.
	 * <p>
	 * <code>Arrays.subArray(E[] array, int fromIndex, int toIndex)</code>
	 */
	public static <E> E[] subArray(E[] array, int fromIndex, int toIndex) {
		int len = toIndex - fromIndex;
		E[] result = newArray(array, len);
		if (len > 0) {
			System.arraycopy(array, fromIndex, result, 0, len);
		}
		return result;
	}

	/**
	 * Return a sub-array of the specified array with elements copied from
	 * the specified range. The "from" index is inclusive; the "to" index is exclusive.
	 * <p>
	 * <code>Arrays.subArray(int[] array, int fromIndex, int toIndex)</code>
	 */
	public static int[] subArray(int[] array, int fromIndex, int toIndex) {
		int len = toIndex - fromIndex;
		if (len == 0) {
			return EMPTY_INT_ARRAY;
		}
		int[] result = new int[len];
		System.arraycopy(array, fromIndex, result, 0, len);
		return result;
	}

	/**
	 * Return a sub-array of the specified array with elements copied from
	 * the specified range. The "from" index is inclusive; the "to" index is exclusive.
	 * <p>
	 * <code>
	 * Arrays.subArray(char[] array, int fromIndex, int toIndex)</code>
	 * </code>
	 */
	public static char[] subArray(char[] array, int fromIndex, int toIndex) {
		int len = toIndex - fromIndex;
		if (len == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		char[] result = new char[len];
		System.arraycopy(array, fromIndex, result, 0, len);
		return result;
	}


	// ********** swap **********

	/**
	 * Return the array after the specified elements have been "swapped".
	 * <p>
	 * <code>Arrays.swap(Object[] array, int i, int j)</code>
	 */
	public static <E> E[] swap(E[] array, int i, int j) {
		return (i == j) ? array : swap_(array, i, j);
	}

	/**
	 * assume the indices are different
	 */
	private static <E> E[] swap_(E[] array, int i, int j) {
		E temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	/**
	 * Return the array after the specified elements have been "swapped".
	 * <p>
	 * <code>Arrays.swap(char[] array, int i, int j)</code>
	 */
	public static char[] swap(char[] array, int i, int j) {
		return (i == j) ? array : swap_(array, i, j);
	}

	/**
	 * assume the indices are different
	 */
	private static char[] swap_(char[] array, int i, int j) {
		char temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	/**
	 * Return the array after the specified elements have been "swapped".
	 * <p>
	 * <code>Arrays.swap(int[] array, int i, int j)</code>
	 */
	public static int[] swap(int[] array, int i, int j) {
		return (i == j) ? array : swap_(array, i, j);
	}

	/**
	 * assume the indices are different
	 */
	private static int[] swap_(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}


	// ********** Arrays enhancements **********

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(boolean[], boolean)
	 */
	public static boolean[] fill(boolean[] array, boolean value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(boolean[], int, int, boolean)
	 */
	public static boolean[] fill(boolean[] array, int fromIndex, int toIndex, boolean value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(byte[], byte)
	 */
	public static byte[] fill(byte[] array, byte value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(byte[], int, int, byte)
	 */
	public static byte[] fill(byte[] array, int fromIndex, int toIndex, byte value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(char[], char)
	 */
	public static char[] fill(char[] array, char value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(char[], int, int, char)
	 */
	public static char[] fill(char[] array, int fromIndex, int toIndex, char value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(double[], double)
	 */
	public static double[] fill(double[] array, double value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(double[], int, int, double)
	 */
	public static double[] fill(double[] array, int fromIndex, int toIndex, double value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(float[], float)
	 */
	public static float[] fill(float[] array, float value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(float[], int, int, float)
	 */
	public static float[] fill(float[] array, int fromIndex, int toIndex, float value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(int[], int)
	 */
	public static int[] fill(int[] array, int value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(int[], int, int, int)
	 */
	public static int[] fill(int[] array, int fromIndex, int toIndex, int value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(Object[], Object)
	 */
	public static <E> E[] fill(E[] array, E value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(Object[], int, int, Object)
	 */
	public static <E> E[] fill(E[] array, int fromIndex, int toIndex, E value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(long[], long)
	 */
	public static long[] fill(long[] array, long value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(long[], int, int, long)
	 */
	public static long[] fill(long[] array, int fromIndex, int toIndex, long value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(short[], short)
	 */
	public static short[] fill(short[] array, short value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 * @see Arrays#fill(short[], int, int, short)
	 */
	public static short[] fill(short[] array, int fromIndex, int toIndex, short value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(byte[])
	 */
	public static byte[] sort(byte... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(byte[], int, int)
	 */
	public static byte[] sort(byte[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(char[])
	 */
	public static char[] sort(char... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(char[], int, int)
	 */
	public static char[] sort(char[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(double[])
	 */
	public static double[] sort(double... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(double[], int, int)
	 */
	public static double[] sort(double[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(float[])
	 */
	public static float[] sort(float... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(float[], int, int)
	 */
	public static float[] sort(float[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(int[])
	 */
	public static int[] sort(int... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(int[], int, int)
	 */
	public static int[] sort(int[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(Object[])
	 */
	public static <E> E[] sort(E... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(Object[], Comparator)
	 */
	public static <E> E[] sort(E[] array, Comparator<? super E> comparator) {
		Arrays.sort(array, comparator);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(Object[], int, int)
	 */
	public static <E> E[] sort(E[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(Object[], int, int, Comparator)
	 */
	public static <E> E[] sort(E[] array, int fromIndex, int toIndex, Comparator<? super E> comparator) {
		Arrays.sort(array, fromIndex, toIndex, comparator);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(long[])
	 */
	public static long[] sort(long... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(long[], int, int)
	 */
	public static long[] sort(long[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(short[])
	 */
	public static short[] sort(short... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 * @see Arrays#sort(short[], int, int)
	 */
	public static short[] sort(short[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ArrayTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
