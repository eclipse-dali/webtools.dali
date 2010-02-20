/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.RandomAccess;
import java.util.TreeSet;
import java.util.Vector;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.utility.internal.iterators.SuperIteratorWrapper;

/**
 * {@link Collection}-related utility methods.
 */
public final class CollectionTools {

	// ********** add all **********

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.addAll(Iterable iterable)</code>
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable) {
		return addAll(collection, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.addAll(Iterable iterable)</code>
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable, int size) {
		return addAll(collection, iterable.iterator(), size);
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.addAll(Iterator iterator)</code>
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator) {
		return iterator.hasNext() ? addAll_(collection, iterator) : false;
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, Iterator<? extends E> iterator) {
		boolean modified = false;
		while (iterator.hasNext()) {
			modified |= collection.add(iterator.next());
		}
		return modified;
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.addAll(Iterator iterator)</code>
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator, int size) {
		return iterator.hasNext() ? collection.addAll(list(iterator, size)) : false;
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.addAll(Object[] array)</code>
	 */
	public static <E> boolean addAll(Collection<? super E> collection, E... array) {
		return (array.length == 0) ? false : addAll_(collection, array);
	}

	/**
	 * assume the array is not empty
	 */
	private static <E> boolean addAll_(Collection<? super E> collection, E... array) {
		boolean modified = false;
		for (E element : array) {
			modified |= collection.add(element);
		}
		return modified;
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * <p>
	 * <code>List.addAll(Iterable iterable)</code>
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<E> iterable) {
		return addAll(list, index, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * <p>
	 * <code>List.addAll(Iterable iterable)</code>
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<E> iterable, int size) {
		return addAll(list, index, iterable.iterator(), size);
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * <p>
	 * <code>List.addAll(Iterator iterator)</code>
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator) {
		return iterator.hasNext() ? list.addAll(index, list(iterator)) : false;
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * <p>
	 * <code>List.addAll(Iterator iterator)</code>
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator, int size) {
		return iterator.hasNext() ? list.addAll(index, list(iterator, size)) : false;
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * <p>
	 * <code>List.addAll(Object[] array)</code>
	 */
	public static <E> boolean addAll(List<? super E> list, int index, E... array) {
		return (array.length == 0) ? false : list.addAll(index, Arrays.asList(array));
	}


	// ********** bag **********

	/**
	 * Return a bag corresponding to the specified enumeration.
	 * <p>
	 * <code>HashBag(Enumeration enumeration)</code>
	 */
	public static <E> HashBag<E> bag(Enumeration<? extends E> enumeration) {
		return bag(enumeration, new HashBag<E>());
	}

	/**
	 * Return a bag corresponding to the specified enumeration.
	 * The specified enumeration size is a performance hint.
	 * <p>
	 * <code>HashBag(Enumeration enumeration)</code>
	 */
	public static <E> HashBag<E> bag(Enumeration<? extends E> enumeration, int enumerationSize) {
		return bag(enumeration, new HashBag<E>(enumerationSize));
	}

	private static <E> HashBag<E> bag(Enumeration<? extends E> enumeration, HashBag<E> bag) {
		while (enumeration.hasMoreElements()) {
			bag.add(enumeration.nextElement());
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * <p>
	 * <code>HashBag(Iterable iterable)</code>
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable) {
		return bag(iterable.iterator());
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>HashBag(Iterable iterable)</code>
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable, int iterableSize) {
		return bag(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * <p>
	 * <code>HashBag(Iterator iterator)</code>
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator) {
		return bag(iterator, new HashBag<E>());
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>HashBag(Iterator iterator)</code>
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator, int iteratorSize) {
		return bag(iterator, new HashBag<E>(iteratorSize));
	}

	private static <E> HashBag<E> bag(Iterator<? extends E> iterator, HashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified array.
	 * <p>
	 * <code>HashBag(Object[] array)</code>
	 */
	public static <E> HashBag<E> bag(E... array) {
		int len = array.length;
		HashBag<E> bag = new HashBag<E>(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}


	// ********** collection **********

	/**
	 * Return a collection corresponding to the specified enumeration.
	 */
	public static <E> HashBag<E> collection(Enumeration<? extends E> enumeration) {
		return bag(enumeration);
	}

	/**
	 * Return a collection corresponding to the specified enumeration.
	 * The specified enumeration size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Enumeration<? extends E> enumeration, int enumerationSize) {
		return bag(enumeration, enumerationSize);
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable) {
		return collection(iterable.iterator());
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable, int iterableSize) {
		return collection(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator) {
		return bag(iterator);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator, int iteratorSize) {
		return bag(iterator, iteratorSize);
	}

	/**
	 * Return a collection corresponding to the specified array.
	 */
	public static <E> HashBag<E> collection(E... array) {
		return bag(array);
	}


	// ********** contains **********

	/**
	 * Return whether the specified enumeration contains the
	 * specified element.
	 * <p>
	 * <code>Enumeration.contains(Object o)</code>
	 */
	public static boolean contains(Enumeration<?> enumeration, Object value) {
		if (value == null) {
			while (enumeration.hasMoreElements()) {
				if (enumeration.nextElement() == null) {
					return true;
				}
			}
		} else {
			while (enumeration.hasMoreElements()) {
				if (value.equals(enumeration.nextElement())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return whether the specified iterable contains the
	 * specified element.
	 * <p>
	 * <code>Iterable.contains(Object o)</code>
	 */
	public static boolean contains(Iterable<?> iterable, Object value) {
		return contains(iterable.iterator(), value);
	}

	/**
	 * Return whether the specified iterator contains the
	 * specified element.
	 * <p>
	 * <code>Iterator.contains(Object o)</code>
	 */
	public static boolean contains(Iterator<?> iterator, Object value) {
		if (value == null) {
			while (iterator.hasNext()) {
				if (iterator.next() == null) {
					return true;
				}
			}
		} else {
			while (iterator.hasNext()) {
				if (value.equals(iterator.next())) {
					return true;
				}
			}
		}
		return false;
	}


	// ********** contains all **********

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterable.
	 * <p>
	 * <code>Collection.containsAll(Iterable iterable)</code>
	 */
	public static boolean containsAll(Collection<?> collection, Iterable<?> iterable) {
		return containsAll(collection, iterable.iterator());
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterator.
	 * <p>
	 * <code>Collection.containsAll(Iterator iterator)</code>
	 */
	public static boolean containsAll(Collection<?> collection, Iterator<?> iterator) {
		while (iterator.hasNext()) {
			if ( ! collection.contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified array.
	 * <p>
	 * <code>Collection.containsAll(Object[] array)</code>
	 */
	public static boolean containsAll(Collection<?> collection, Object... array) {
		for (int i = array.length; i-- > 0; ) {
			if ( ! collection.contains(array[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified collection.
	 * <p>
	 * <code>Iterable.containsAll(Collection collection)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, Collection<?> collection) {
		return containsAll(iterable.iterator(), collection);
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified collection.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.containsAll(Collection collection)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Collection<?> collection) {
		return containsAll(iterable.iterator(), iterableSize, collection);
	}

	/**
	 * Return whether the specified iterable 1 contains all of the
	 * elements in the specified iterable 2.
	 * <p>
	 * <code>Iterable.containsAll(Iterable iterable)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable1, Iterable<?> iterable2) {
		return containsAll(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return whether the specified iterable 1 contains all of the
	 * elements in the specified iterable 2.
	 * The specified iterable 1 size is a performance hint.
	 * <p>
	 * <code>Iterable.containsAll(Iterable iterable)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable1, int iterable1Size, Iterable<?> iterable2) {
		return containsAll(iterable1.iterator(), iterable1Size, iterable2.iterator());
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified iterator.
	 * <p>
	 * <code>Iterable.containsAll(Iterator iterator)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, Iterator<?> iterator) {
		return containsAll(iterable.iterator(), iterator);
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified iterator.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.containsAll(Iterator iterator)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Iterator<?> iterator) {
		return containsAll(iterable.iterator(), iterableSize, iterator);
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified array.
	 * <p>
	 * <code>Iterable.containsAll(Object[] array)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, Object... array) {
		return containsAll(iterable.iterator(), array);
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified array.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.containsAll(Object[] array)</code>
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Object... array) {
		return containsAll(iterable.iterator(), iterableSize, array);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified collection.
	 * <p>
	 * <code>Iterator.containsAll(Collection collection)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, Collection<?> collection) {
		return set(iterator).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified collection.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.containsAll(Collection collection)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Collection<?> collection) {
		return set(iterator, iteratorSize).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified iterable.
	 * <p>
	 * <code>Iterator.containsAll(Iterable iterable)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, Iterable<?> iterable) {
		return containsAll(set(iterator), iterable);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified iterable.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.containsAll(Iterable iterable)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Iterable<?> iterable) {
		return containsAll(set(iterator, iteratorSize), iterable);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the
	 * elements in the specified iterator 2.
	 * <p>
	 * <code>Iterator.containsAll(Iterator iterator)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator1, Iterator<?> iterator2) {
		return containsAll(set(iterator1), iterator2);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the
	 * elements in the specified iterator 2.
	 * The specified iterator 1 size is a performance hint.
	 * <p>
	 * <code>Iterator.containsAll(Iterator iterator)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator1, int iterator1Size, Iterator<?> iterator2) {
		return containsAll(set(iterator1, iterator1Size), iterator2);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified array.
	 * <p>
	 * <code>Iterator.containsAll(Object[] array)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, Object... array) {
		return containsAll(set(iterator), array);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified array.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.containsAll(Object[] array)</code>
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Object... array) {
		return containsAll(set(iterator, iteratorSize), array);
	}


	// ********** diff **********

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different, beginning at the end.
	 * If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the
	 * last element in the longer list.
	 * Use the elements' {@link Object#equals()} method to compare the
	 * elements.
	 * <p>
	 * <code>Collections.diffEnd(List list1, List list2)</code>
	 */
	public static int diffEnd(List<?> list1, List<?> list2) {
		return ArrayTools.diffEnd(list1.toArray(), list2.toArray());
	}

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return [size, -1].
	 * Use the elements' {@link Object#equals()} method to compare the
	 * elements.
	 * <p>
	 * <code>Collections.diffRange(List list1, List list2)</code>
	 * @see #diffStart(List, List)
	 * @see #diffEnd(List, List)
	 */
	public static Range diffRange(List<?> list1, List<?> list2) {
		return ArrayTools.diffRange(list1.toArray(), list2.toArray());
	}

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different. If the lists are identical, return
	 * the size of the two lists (i.e. one past the last index).
	 * If the lists are different sizes and all the elements in
	 * the shorter list match their corresponding elements in
	 * the longer list, return the size of the shorter list
	 * (i.e. one past the last index of the shorter list).
	 * Use the elements' {@link Object#equals()} method to compare the
	 * elements.
	 * <p>
	 * <code>Collections.diffStart(List list1, List list2)</code>
	 */
	public static int diffStart(List<?> list1, List<?> list2) {
		return ArrayTools.diffStart(list1.toArray(), list2.toArray());
	}


	// ********** identity diff **********

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different, beginning at the end.
	 * If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the
	 * last element in the longer list.
	 * Use object identity to compare the elements.
	 * <p>
	 * <code>Collections.identityDiffEnd(List list1, List list2)</code>
	 */
	public static int identityDiffEnd(List<?> list1, List<?> list2) {
		return ArrayTools.identityDiffEnd(list1.toArray(), list2.toArray());
	}

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return [size, -1].
	 * Use object identity to compare the elements.
	 * <p>
	 * <code>Collections.identityDiffStart(List list1, List list2)</code>
	 * @see #identityDiffStart(List, List)
	 * @see #identityDiffEnd(List, List)
	 */
	public static Range identityDiffRange(List<?> list1, List<?> list2) {
		return ArrayTools.identityDiffRange(list1.toArray(), list2.toArray());
	}

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different. If the lists are identical, return
	 * the size of the two lists (i.e. one past the last index).
	 * If the lists are different sizes and all the elements in
	 * the shorter list match their corresponding elements in
	 * the longer list, return the size of the shorter list
	 * (i.e. one past the last index of the shorter list).
	 * Use object identity to compare the elements.
	 * <p>
	 * <code>Collections.identityDiffStart(List list1, List list2)</code>
	 */
	public static int identityDiffStart(List<?> list1, List<?> list2) {
		return ArrayTools.identityDiffStart(list1.toArray(), list2.toArray());
	}


	// ********** elements are equal **********

	/**
	 * Return whether the specified iterables do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Iterable<?> iterable1, Iterable<?> iterable2) {
		return elementsAreDifferent(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return whether the specified iterators do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Iterator<?> iterator1, Iterator<?> iterator2) {
		return ! elementsAreEqual(iterator1, iterator2);
	}

	/**
	 * Return whether the specified iterables return equal elements.
	 * <p>
	 * <code>Iterable.elementsAreEqual(Iterable iterable)</code>
	 */
	public static boolean elementsAreEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
		return elementsAreEqual(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return whether the specified iterators return equal elements.
	 * <p>
	 * <code>Iterator.elementsAreEqual(Iterator iterator)</code>
	 */
	public static boolean elementsAreEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (Tools.valuesAreDifferent(iterator1.next(), iterator2.next())) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}


	// ********** elements are identical **********

	/**
	 * Return whether the specified iterables return the same elements.
	 * <p>
	 * <code>Iterable.identical(Iterable iterable)</code>
	 */
	public static boolean elementsAreIdentical(Iterable<?> iterable1, Iterable<?> iterable2) {
		return elementsAreIdentical(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return whether the specified iterators return the same elements.
	 * <p>
	 * <code>Iterator.identical(Iterator iterator)</code>
	 */
	public static boolean elementsAreIdentical(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (iterator1.next() != iterator2.next()) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}


	// ********** get **********

	/**
	 * Return the element corresponding to the specified index
	 * in the specified iterable.
	 * <p>
	 * <code>Iterable.get(int index)</code>
	 */
	public static <E> E get(Iterable<? extends E> iterable, int index) {
		return get(iterable.iterator(), index);
	}

	/**
	 * Return the element corresponding to the specified index
	 * in the specified iterator.
	 * <p>
	 * <code>Iterator.get(int index)</code>
	 */
	public static <E> E get(Iterator<? extends E> iterator, int index) {
		int i = 0;
		while (iterator.hasNext()) {
			E next = iterator.next();
			if (i++ == index) {
				return next;
			}
		}
		throw new IndexOutOfBoundsException(String.valueOf(index) + ':' + String.valueOf(i));
	}


	// ********** hash code **********

	public static int hashCode(Iterable<?> iterable) {
		if (iterable == null) {
			return 0;
		}
		int hash = 1;
		for (Object element : iterable) {
			hash = 31 * hash + (element == null ? 0 : element.hashCode());
		}
		return hash;
	}


	// ********** index of **********

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterable;
	 * return -1 if there is no such index.
	 * <p>
	 * <code>Iterable.indexOf(Object o)</code>
	 */
	public static int indexOf(Iterable<?> iterable, Object value) {
		return indexOf(iterable.iterator(), value);
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterator;
	 * return -1 if there is no such index.
	 * <p>
	 * <code>Iterator.indexOf(Object o)</code>
	 */
	public static int indexOf(Iterator<?> iterator, Object value) {
		if (value == null) {
			for (int i = 0; iterator.hasNext(); i++) {
				if (iterator.next() == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; iterator.hasNext(); i++) {
				if (value.equals(iterator.next())) {
					return i;
				}
			}
		}
		return -1;
	}


	// ********** insertion index of **********

	/**
	 * Return the maximum index of where the specified comparable object
	 * should be inserted into the specified sorted list and still keep
	 * the list sorted.
	 */
	public static <E extends Comparable<? super E>> int insertionIndexOf(List<E> sortedList, Comparable<E> value) {
		int len = sortedList.size();
		for (int i = 0; i < len; i++) {
			if (value.compareTo(sortedList.get(i)) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return the maximum index of where the specified object
	 * should be inserted into the specified sorted list and still keep
	 * the list sorted.
	 */
	public static <E> int insertionIndexOf(List<E> sortedList, E value, Comparator<? super E> comparator) {
		int len = sortedList.size();
		for (int i = 0; i < len; i++) {
			if (comparator.compare(value, sortedList.get(i)) < 0) {
				return i;
			}
		}
		return len;
	}


	// ********** iterable/iterator **********

	/**
	 * Return an iterable on the elements in the specified array.
	 * <p>
	 * <code>Arrays.iterable(Object[] array)</code>
	 */
	public static <E> Iterable<E> iterable(E... array) {
		return new ArrayIterable<E>(array);
	}

	/**
	 * Return an iterator on the elements in the specified array.
	 * <p>
	 * <code>Arrays.iterator(Object[] array)</code>
	 */
	public static <E> Iterator<E> iterator(E... array) {
		return new ArrayIterator<E>(array);
	}


	// ********** last **********

	/**
	 * Return the specified iterable's last element.
	 * <p>
	 * <code>Iterable.last()</code>
	 * 
     * @exception NoSuchElementException iterable is empty.
	 */
	public static <E> E last(Iterable<E> iterable) {
		return last(iterable.iterator());
	}

	/**
	 * Return the specified iterator's last element.
	 * <p>
	 * <code>Iterator.last()</code>
	 * 
     * @exception NoSuchElementException iterator is empty.
	 */
	public static <E> E last(Iterator<E> iterator) {
		E last;
		do {
			last = iterator.next();
		} while (iterator.hasNext());
		return last;
	}


	// ********** last index of **********

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterable;
	 * return -1 if there is no such index.
	 * <p>
	 * <code>Iterable.lastIndexOf(Object o)
	 */
	public static int lastIndexOf(Iterable<?> iterable, Object value) {
		return lastIndexOf(iterable.iterator(), value);
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterable;
	 * return -1 if there is no such index.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.lastIndexOf(Object o)
	 */
	public static int lastIndexOf(Iterable<?> iterable, int iterableSize, Object value) {
		return lastIndexOf(iterable.iterator(), iterableSize, value);
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterator;
	 * return -1 if there is no such index.
	 * <p>
	 * <code>Iterator.lastIndexOf(Object o)
	 */
	public static int lastIndexOf(Iterator<?> iterator, Object value) {
		return iterator.hasNext() ? list(iterator).lastIndexOf(value) : -1;
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterator;
	 * return -1 if there is no such index.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.lastIndexOf(Object o)
	 */
	public static int lastIndexOf(Iterator<?> iterator, int iteratorSize, Object value) {
		return iterator.hasNext() ? list(iterator, iteratorSize).lastIndexOf(value) : -1;
	}


	// ********** list **********

	/**
	 * Return a list corresponding to the specified iterable.
	 * <p>
	 * <code>Iterable.toList()</code>
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable) {
		return list(iterable.iterator());
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.toList()</code>
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable, int iterableSize) {
		return list(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * <p>
	 * <code>Iterator.toList()</code>
	 */
	public static <E> ArrayList<E> list(Iterator<? extends E> iterator) {
		return list(iterator, new ArrayList<E>());
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.toList()</code>
	 */
	public static <E> ArrayList<E> list(Iterator<? extends E> iterator, int iteratorSize) {
		return list(iterator, new ArrayList<E>(iteratorSize));
	}

	private static <E> ArrayList<E> list(Iterator<? extends E> iterator, ArrayList<E> list) {
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * Return a list corresponding to the specified array.
	 * Unlike {@link Arrays#asList(Object[])}, the list
	 * is modifiable and is not backed by the array.
	 */
	public static <E> ArrayList<E> list(E... array) {
		return new ArrayList<E>(Arrays.asList(array));
	}

	/**
	 * Return a list iterator for the specified array.
	 * <p>
	 * <code>Arrays.listIterator(Object[] array)</code>
	 */
	public static <E> ListIterator<E> listIterator(E... array) {
		return listIterator(array, 0);
	}

	/**
	 * Return a list iterator for the specified array
	 * starting at the specified position in the array.
	 * <p>
	 * <code>Arrays.listIterator(Object[] array, int index)</code>
	 */
	public static <E> ListIterator<E> listIterator(E[] array, int start) {
		return listIterator(array, start, array.length - start);
	}

	/**
	 * Return a list iterator for the specified array
	 * starting at the specified position in the array.
	 * <p>
	 * <code>Arrays.listIterator(Object[] array, int index, int length)</code>
	 */
	public static <E> ListIterator<E> listIterator(E[] array, int start, int length) {
		return new ArrayListIterator<E>(array, start, length);
	}


	// ********** move **********

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered list.
	 * <p>
	 * <code>List.move(int targetIndex, int sourceIndex)</code>
	 */
	public static <E> List<E> move(List<E> list, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? list : move_(list, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static <E> List<E> move_(List<E> list, int targetIndex, int sourceIndex) {
		if (list instanceof RandomAccess) {
			// move elements, leaving the list in place
			E temp = list.get(sourceIndex);
			if (targetIndex < sourceIndex) {
				for (int i = sourceIndex; i-- > targetIndex; ) {
					list.set(i + 1, list.get(i));
				}
			} else {
				for (int i = sourceIndex; i < targetIndex; i++) {
					list.set(i, list.get(i + 1));
				}
			}
			list.set(targetIndex, temp);
		} else {
			// remove the element and re-add it at the target index
			list.add(targetIndex, list.remove(sourceIndex));
		}
		return list;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered list.
	 * <p>
	 * <code>List.move(int targetIndex, int sourceIndex, int length)</code>
	 */
	public static <E> List<E> move(List<E> list, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return list;
		}
		if (length == 1) {
			return move_(list, targetIndex, sourceIndex);
		}
		if (list instanceof RandomAccess) {
			// move elements, leaving the list in place
			ArrayList<E> temp = new ArrayList<E>(list.subList(sourceIndex, sourceIndex + length));
			if (targetIndex < sourceIndex) {
				for (int i = sourceIndex; i-- > targetIndex; ) {
					list.set(i + length, list.get(i));
				}
			} else {
				for (int i = sourceIndex; i < targetIndex; i++) {
					list.set(i, list.get(i + length));
				}
			}
			for (int i = 0; i < length; i++) {
				list.set(targetIndex + i, temp.get(i));
			}
		} else {
			// remove the elements and re-add them at the target index
			list.addAll(targetIndex, removeElementsAtIndex(list, sourceIndex, length));
		}
		return list;
	}


	// ********** remove all **********

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.removeAll(Iterable iterable)</code>
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable) {
		return removeAll(collection, iterable.iterator());
	}

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Collection.removeAll(Iterable iterable)</code>
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return removeAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.removeAll(Iterator iterator)</code>
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator) {
		return iterator.hasNext() ? collection.removeAll(set(iterator)) : false;
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Collection.removeAll(Iterator iterator)</code>
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		return iterator.hasNext() ? collection.removeAll(set(iterator, iteratorSize)) : false;
	}

	/**
	 * Remove all the elements in the specified array
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.removeAll(Object[] array)</code>
	 */
	public static boolean removeAll(Collection<?> collection, Object... array) {
		return (array.length == 0) ? false : collection.removeAll(set(array));
	}


	// ********** remove all occurrences **********

	/**
	 * Remove all occurrences of the specified element
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.removeAllOccurrences(Object value)</code>
	 */
	public static boolean removeAllOccurrences(Collection<?> collection, Object value) {
		boolean modified = false;
		Iterator<?> stream = collection.iterator();
		if (value == null) {
			while (stream.hasNext()) {
				if (stream.next() == null) {
					stream.remove();
					modified = true;
				}
			}
		} else {
			while (stream.hasNext()) {
				if (value.equals(stream.next())) {
					stream.remove();
					modified = true;
				}
			}
		}
		return modified;
	}


	// ********** remove elements at index **********

	/**
	 * Remove the elements at the specified index.
	 * Return the removed elements.
	 * <p>
	 * <code>List.remove(int index, int length)</code>
	 */
	public static <E> ArrayList<E> removeElementsAtIndex(List<E> list, int index, int length) {
		List<E> subList = list.subList(index, index + length);
		ArrayList<E> result = new ArrayList<E>(subList);
		subList.clear();
		return result;
	}


	// ********** remove duplicate elements **********

	/**
	 * Remove any duplicate elements from the specified list,
	 * while maintaining the order.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean removeDuplicateElements(List<E> list) {
		int size = list.size();
		if ((size == 0) || (size == 1)) {
			return false;
		}
		return removeDuplicateElements(list, size);
	}

	/**
	 * assume list is non-empty
	 */
	static <E> boolean removeDuplicateElements(List<E> list, int size) {
		LinkedHashSet<E> temp = new LinkedHashSet<E>(size);		// take advantage of hashed look-up
		boolean modified = false;
		for (E item : list) {
			if ( ! temp.add(item)) {
				modified = true;  // duplicate item
			}
		}
		if (modified) {
			int i = 0;
			for (E e : temp) {
				list.set(i, e);
				i++;
			}
			int tempSize = temp.size();
			for (i = list.size(); i-- > tempSize; ) {
				list.remove(i);  // pull off the end
			}
		}
		return modified;
	}


	// ********** retain all **********

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.retainAll(Iterable iterable)</code>
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable) {
		return retainAll(collection, iterable.iterator());
	}

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Collection.retainAll(Iterable iterable)</code>
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return retainAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Retain only the elements in the specified iterator
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.retainAll(Iterator iterator)</code>
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified iterator
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Collection.retainAll(Iterator iterator)</code>
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator, iteratorSize));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified array
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * <p>
	 * <code>Collection.retainAll(Object[] array)</code>
	 */
	public static boolean retainAll(Collection<?> collection, Object... array) {
		if (array.length > 0) {
			return collection.retainAll(set(array));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}


	// ********** reverse list **********

	/**
	 * Return a list with entries in reverse order from those
	 * returned by the specified iterable.
	 * <p>
	 * <code>Iterable.reverseList()</code>
	 */
	public static <E> List<E> reverseList(Iterable<? extends E> iterable) {
		return reverseList(iterable.iterator());
	}

	/**
	 * Return a list with entries in reverse order from those
	 * returned by the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.reverseList()</code>
	 */
	public static <E> List<E> reverseList(Iterable<? extends E> iterable, int iterableSize) {
		return reverseList(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a list with entries in reverse order from those
	 * returned by the specified iterator.
	 * <p>
	 * <code>Iterator.reverseList()</code>
	 */
	public static <E> List<E> reverseList(Iterator<? extends E> iterator) {
		return reverse(list(iterator));
	}

	/**
	 * Return a list with entries in reverse order from those
	 * returned by the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.reverseList()</code>
	 */
	public static <E> List<E> reverseList(Iterator<? extends E> iterator, int size) {
		return reverse(list(iterator, size));
	}


	// ********** rotate **********

	/**
	 * Return the list after it has been "rotated" by one position.
	 * <p>
	 * <code>List.rotate()</code>
	 */
	public static <E> List<E> rotate(List<E> list) {
		return rotate(list, 1);
	}


	// ********** set **********

	/**
	 * Return a set corresponding to the specified iterable.
	 * <p>
	 * <code>HashSet(Iterable iterable)</code>
	 */
	public static <E> HashSet<E> set(Iterable<? extends E> iterable) {
		return set(iterable.iterator());
	}

	/**
	 * Return a set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>HashSet(Iterable iterable)</code>
	 */
	public static <E> HashSet<E> set(Iterable<? extends E> iterable, int iterableSize) {
		return set(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 * <p>
	 * <code>HashSet(Iterator iterator)</code>
	 */
	public static <E> HashSet<E> set(Iterator<? extends E> iterator) {
		return set(iterator, new HashSet<E>());
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>HashSet(Iterator iterator)</code>
	 */
	public static <E> HashSet<E> set(Iterator<? extends E> iterator, int iteratorSize) {
		return set(iterator, new HashSet<E>(iteratorSize));
	}

	private static <E> HashSet<E> set(Iterator<? extends E> iterator, HashSet<E> set) {
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Return a set corresponding to the specified array.
	 * <p>
	 * <code>HashSet(Object[] array)</code>
	 */
	public static <E> HashSet<E> set(E... array) {
		HashSet<E> set = new HashSet<E>(array.length);
		for (int i = array.length; i-- > 0;) {
			set.add(array[i]);
		}
		return set;
	}


	// ********** singleton iterator **********

	/**
	 * Return an iterator that returns only the single,
	 * specified object.
	 * <p>
	 * <code>Object.toIterator()</code>
	 */
	public static <E> Iterator<E> singletonIterator(E value) {
		return new SingleElementIterator<E>(value);
	}

	/**
	 * Return a list iterator that returns only the single,
	 * specified object.
	 * <p>
	 * <code>Object.toListIterator()</code>
	 */
	public static <E> ListIterator<E> singletonListIterator(E value) {
		return new SingleElementListIterator<E>(value);
	}


	// ********** size **********

	/**
	 * Return the number of elements returned by the specified iterable.
	 * <p>
	 * <code>Iterable.size()</code>
	 */
	public static int size(Iterable<?> iterable) {
		return size(iterable.iterator());
	}

	/**
	 * Return the number of elements returned by the specified iterator.
	 * <p>
	 * <code>Iterator.size()</code>
	 */
	public static int size(Iterator<?> iterator) {
		int size = 0;
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		return size;
	}
	
	/**
	 * Return whether the specified iterable is empty
	 * (Shortcuts the iterator rather than calculating the entire size)
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return isEmpty(iterable.iterator());
	}
	
	/**
	 * Return whether the specified iterator is empty
	 * (Shortcuts the iterator rather than calculating the entire size)
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return ! iterator.hasNext();
	}
	
	
	// ********** sort **********

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * <p>
	 * <code>Iterable.sort()</code>
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<E> iterable) {
		return sort(iterable, null);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.sort()</code>
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<E> iterable, int iterableSize) {
		return sort(iterable, null, iterableSize);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * <p>
	 * <code>Iterable.sort(Comparator comparator)</code>
	 */
	public static <E> Iterable<E> sort(Iterable<E> iterable, Comparator<? super E> comparator) {
		return sort(list(iterable), comparator);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>Iterable.sort(Comparator comparator)</code>
	 */
	public static <E> Iterable<E> sort(Iterable<E> iterable, Comparator<? super E> comparator, int iterableSize) {
		return sort(list(iterable, iterableSize), comparator);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * <p>
	 * <code>Iterator.sort()</code>
	 */
	public static <E extends Comparable<? super E>> ListIterator<E> sort(Iterator<? extends E> iterator) {
		return sort(iterator, null);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.sort()</code>
	 */
	public static <E extends Comparable<? super E>> ListIterator<E> sort(Iterator<? extends E> iterator, int iteratorSize) {
		return sort(iterator, null, iteratorSize);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * <p>
	 * <code>Iterator.sort(Comparator comparator)</code>
	 */
	public static <E> ListIterator<E> sort(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return sort(list(iterator), comparator).listIterator();
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>Iterator.sort(Comparator comparator)</code>
	 */
	public static <E> ListIterator<E> sort(Iterator<? extends E> iterator, Comparator<? super E> comparator, int iteratorSize) {
		return sort(list(iterator, iteratorSize), comparator).listIterator();
	}


	// ********** sorted set **********

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 * <p>
	 * <code>TreeSet(Iterable iterable)</code>
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterable<? extends E> iterable) {
		return sortedSet(iterable.iterator());
	}

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>TreeSet(Iterable iterable)</code>
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterable<? extends E> iterable, int iterableSize) {
		return sortedSet(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable
	 * and comparator.
	 * <p>
	 * <code>TreeSet(Iterable iterable, Comparator c)</code>
	 */
	public static <E> TreeSet<E> sortedSet(Iterable<? extends E> iterable, Comparator<? super E> comparator) {
		return sortedSet(iterable.iterator(), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable
	 * and comparator.
	 * The specified iterable size is a performance hint.
	 * <p>
	 * <code>TreeSet(Iterable iterable, Comparator c)</code>
	 */
	public static <E> TreeSet<E> sortedSet(Iterable<? extends E> iterable, Comparator<? super E> comparator, int iterableSize) {
		return sortedSet(iterable.iterator(), comparator, iterableSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 * <p>
	 * <code>TreeSet(Iterator iterator)</code>
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterator<? extends E> iterator) {
		return sortedSet(iterator, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>TreeSet(Iterator iterator)</code>
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(Iterator<? extends E> iterator, int iteratorSize) {
		return sortedSet(iterator, null, iteratorSize);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator
	 * and comparator.
	 * <p>
	 * <code>TreeSet(Iterator iterator, Comparator c)</code>
	 */
	public static <E> TreeSet<E> sortedSet(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return sortedSet(list(iterator), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator
	 * and comparator.
	 * The specified iterator size is a performance hint.
	 * <p>
	 * <code>TreeSet(Iterator iterator, Comparator c)</code>
	 */
	public static <E> TreeSet<E> sortedSet(Iterator<? extends E> iterator, Comparator<? super E> comparator, int iteratorSize) {
		return sortedSet(list(iterator, iteratorSize), comparator);
	}

	private static <E> TreeSet<E> sortedSet(List<E> list, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(list);
		return sortedSet;
	}

	/**
	 * Return a sorted set corresponding to the specified array.
	 * <p>
	 * <code>TreeSet(Object[] array)</code>
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> sortedSet(E... array) {
		return sortedSet(array, null);
	}

	/**
	 * Return a sorted set corresponding to the specified array
	 * and comparator.
	 * <p>
	 * <code>TreeSet(Object[] array, Comparator c)</code>
	 */
	public static <E> TreeSet<E> sortedSet(E[] array, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(Arrays.asList(array));
		return sortedSet;
	}


	// ********** Old School Vector **********

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 * <p>
	 * <code>Vector(Iterable iterable)</code>
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable) {
		return vector(iterable.iterator());
	}

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 * <p>
	 * <code>Vector(Iterable iterable, int size)</code>
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable, int size) {
		return vector(iterable.iterator(), size);
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
	 * <p>
	 * <code>Vector(Iterator iterator)</code>
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator) {
		return vector(iterator, new Vector<E>());
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
	 * <p>
	 * <code>Vector(Iterator iterator, int size)</code>
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator, int size) {
		return vector(iterator, new Vector<E>(size));
	}

	private static <E> Vector<E> vector(Iterator<? extends E> iterator, Vector<E> v) {
		while (iterator.hasNext()) {
			v.addElement(iterator.next());
		}
		return v;
	}

	/**
	 * Return a vector corresponding to the specified array.
	 * This is useful for legacy code that requires a {@link Vector}.
	 * <p>
	 * <code>Vector(Object... array)</code>
	 */
	public static <E> Vector<E> vector(E... array) {
		Vector<E> v = new Vector<E>(array.length);
		for (E item : array) {
			v.addElement(item);
		}
		return v;
	}


	// ********** single-use Iterable **********

	/**
	 * Return a one-use {@link Iterable} for the specified {@link Iterator}.
	 * Throw an {@link IllegalStateException} if {@link Iterable#iterator()}
	 * is called more than once.
	 * As such, this utility should only be used in one-use situations, such as
	 * a foreach loop.
	 */
	public static <E> Iterable<E> iterable(Iterator<? extends E> iterator) {
		return new SingleUseIterable<E>(iterator);
	}

	/**
	 * This is a one-time use iterable that can return a single iterator.
	 * Once the iterator is returned the iterable is no longer valid.
	 * As such, this utility should only be used in one-time use situations,
	 * such as a 'for-each' loop.
	 */
	public static class SingleUseIterable<E> implements Iterable<E> {
		private Iterator<E> iterator;

		public SingleUseIterable(Iterator<? extends E> iterator) {
			super();
			if (iterator == null) {
				throw new NullPointerException();
			}
			this.iterator = new SuperIteratorWrapper<E>(iterator);
		}

		public Iterator<E> iterator() {
			if (this.iterator == null) {
				throw new IllegalStateException("This method has already been called."); //$NON-NLS-1$
			}
			Iterator<E> result = this.iterator;
			this.iterator = null;
			return result;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.iterator);
		}

	}


	// ********** java.util.Collections enhancements **********

	/**
	 * Return the destination list after the source list has been copied into it.
	 * @see Collections#copy(List, List)
	 */
	public static <E> List<E> copy(List<E> dest, List<? extends E> src) {
		Collections.copy(dest, src);
		return dest;
	}

	/**
	 * Return the list after it has been "filled".
	 * @see Collections#fill(List, Object)
	 */
	public static <E> List<E> fill(List<E> list, E value) {
		Collections.fill(list, value);
		return list;
	}

	/**
	 * Return the list after it has been "reversed".
	 * @see Collections#reverse(List)
	 */
	public static <E> List<E> reverse(List<E> list) {
		Collections.reverse(list);
		return list;
	}

	/**
	 * Return the list after it has been "rotated".
	 * @see Collections#rotate(List, int)
	 */
	public static <E> List<E> rotate(List<E> list, int distance) {
		Collections.rotate(list, distance);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 * @see Collections#shuffle(List)
	 */
	public static <E> List<E> shuffle(List<E> list) {
		Collections.shuffle(list);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 * @see Collections#shuffle(List, Random)
	 */
	public static <E> List<E> shuffle(List<E> list, Random random) {
		Collections.shuffle(list, random);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 * NB: The list is sorted in place as a side-effect.
	 * @see Collections#sort(List)
	 */
	public static <E extends Comparable<? super E>> List<E> sort(List<E> list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 * NB: The list is sorted in place as a side-effect.
	 * @see Collections#sort(List, Comparator)
	 */
	public static <E> List<E> sort(List<E> list, Comparator<? super E> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * Return the list after the specified elements have been "swapped".
	 * @see Collections#swap(List, int, int)
	 */
	public static <E> List<E> swap(List<E> list, int i, int j) {
		Collections.swap(list, i, j);
		return list;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CollectionTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
