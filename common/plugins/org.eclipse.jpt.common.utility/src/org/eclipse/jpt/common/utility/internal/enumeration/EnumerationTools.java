/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.enumeration;

import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.EnumerationIterator;

/**
 * {@link Enumeration} utility methods.
 * @see org.eclipse.jpt.common.utility.internal.ArrayTools
 * @see CollectionTools
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools
 * @see org.eclipse.jpt.common.utility.internal.iterator.IteratorTools
 * @see ListTools
 */
public final class EnumerationTools {
	/**
	 * Return whether the specified enumeration contains the
	 * specified element.
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
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified collection.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, Collection<?> collection) {
		return CollectionTools.set(iterator(enumeration)).containsAll(collection);
	}

	/**
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified collection.
	 * The specified enumeration size is a performance hint.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, int enumerationSize, Collection<?> collection) {
		return CollectionTools.set(iterator(enumeration), enumerationSize).containsAll(collection);
	}

	/**
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified iterable.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, Iterable<?> iterable) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration)), iterable);
	}

	/**
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified iterable.
	 * The specified enumeration size is a performance hint.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, int enumerationSize, Iterable<?> iterable) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration), enumerationSize), iterable);
	}

	/**
	 * Return whether the specified enumeration 1 contains all of the
	 * elements in the specified enumeration 2.
	 */
	public static boolean containsAll(Enumeration<?> enumeration1, Enumeration<?> enumeration2) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration1)), iterator(enumeration2));
	}

	/**
	 * Return whether the specified enumeration 1 contains all of the
	 * elements in the specified enumeration 2.
	 * The specified iterator 1 size is a performance hint.
	 */
	public static boolean containsAll(Enumeration<?> enumeration1, int enumeration1Size, Enumeration<?> enumeration2) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration1), enumeration1Size), iterator(enumeration2));
	}

	/**
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified array.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, Object... array) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration)), array);
	}

	/**
	 * Return whether the specified enumeration contains all of the
	 * elements in the specified array.
	 * The specified enumeration size is a performance hint.
	 */
	public static boolean containsAll(Enumeration<?> enumeration, int enumerationSize, Object... array) {
		return CollectionTools.containsAll(CollectionTools.set(iterator(enumeration), enumerationSize), array);
	}

	/**
	 * Return whether the specified enumerations do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Enumeration<?> enumeration1, Enumeration<?> enumeration2) {
		return ! elementsAreEqual(enumeration1, enumeration2);
	}

	/**
	 * Return whether the specified enumerations return equal elements
	 * in the same order.
	 */
	public static boolean elementsAreEqual(Enumeration<?> enumeration1, Enumeration<?> enumeration2) {
		while (enumeration1.hasMoreElements() && enumeration2.hasMoreElements()) {
			if (ObjectTools.notEquals(enumeration1.nextElement(), enumeration2.nextElement())) {
				return false;
			}
		}
		return ! (enumeration1.hasMoreElements() || enumeration2.hasMoreElements());
	}

	/**
	 * Return whether the specified enumerations return the same elements.
	 */
	public static boolean elementsAreIdentical(Enumeration<?> enumeration1, Enumeration<?> enumeration2) {
		while (enumeration1.hasMoreElements() && enumeration2.hasMoreElements()) {
			if (enumeration1.nextElement() != enumeration2.nextElement()) {
				return false;
			}
		}
		return ! (enumeration1.hasMoreElements() || enumeration2.hasMoreElements());
	}

	/**
	 * Return whether the specified enumerations do <em>not</em> return the same
	 * elements.
	 */
	public static boolean elementsAreNotIdentical(Enumeration<?> enumeration1, Enumeration<?> enumeration2) {
		return ! elementsAreIdentical(enumeration1, enumeration2);
	}

	/**
	 * Return an empty enumeration.
	 */
	public static <E> Enumeration<E> emptyEnumeration() {
		return EmptyEnumeration.instance();
	}

	/**
	 * Adapt the specified iterator to the {@link Enumeration} interface.
	 */
	public static <E> Enumeration<E> enumeration(Iterator<E> iterator) {
		return new IteratorEnumeration<E>(iterator);
	}

	/**
	 * Return the element corresponding to the specified index
	 * in the specified enumeration.
	 */
	public static <E> E get(Enumeration<? extends E> enumeration, int index) {
		int i = 0;
		while (enumeration.hasMoreElements()) {
			E next = enumeration.nextElement();
			if (i++ == index) {
				return next;
			}
		}
		throw new IndexOutOfBoundsException(String.valueOf(index) + ':' + String.valueOf(i));
	}

	/**
	 * Return a hash code corresponding to the elements in the specified iterator.
	 */
	public static int hashCode(Enumeration<?> enumeration) {
		int hash = 1;
		while (enumeration.hasMoreElements()) {
			Object next = enumeration.nextElement();
			hash = 31 * hash + ((next == null) ? 0 : next.hashCode());
		}
		return hash;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified enumeration;
	 * return -1 if there is no such element.
	 */
	public static int indexOf(Enumeration<?> enumeration, Object value) {
		if (value == null) {
			for (int i = 0; enumeration.hasMoreElements(); i++) {
				if (enumeration.nextElement() == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; enumeration.hasMoreElements(); i++) {
				if (value.equals(enumeration.nextElement())) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified enumeration;
	 * return -1 if there is no such element.
	 */
	public static int lastIndexOf(Enumeration<?> enumeration, Object value) {
		int last = -1;
		if (value == null) {
			for (int i = 0; enumeration.hasMoreElements(); i++) {
				if (enumeration.nextElement() == null) {
					last = i;
				}
			}
		} else {
			for (int i = 0; enumeration.hasMoreElements(); i++) {
				if (value.equals(enumeration.nextElement())) {
					last = i;
				}
			}
		}
		return last;
	}

	/**
	 * Return the specified enumeration's last element.
	 * @exception java.util.NoSuchElementException enumeration is empty.
	 */
	public static <E> E last(Enumeration<E> enumeration) {
		E last;
		do {
			last = enumeration.nextElement();
		} while (enumeration.hasMoreElements());
		return last;
	}

	/**
	 * Return the number of elements returned by the specified enumeration.
	 */
	public static int size(Enumeration<?> enumeration) {
		int size = 0;
		while (enumeration.hasMoreElements()) {
			enumeration.nextElement();
			size++;
		}
		return size;
	}

	/**
	 * Return whether the specified enumeration is empty
	 * (Shortcuts the enumeration rather than calculating the entire size)
	 */
	public static boolean isEmpty(Enumeration<?> enumeration) {
		return ! enumeration.hasMoreElements();
	}

	/**
	 * Return the enumeration after it has been "sorted".
	 */
	public static <E extends Comparable<? super E>> Enumeration<E> sort(Enumeration<? extends E> enumeration) {
		return sort(enumeration, null);
	}

	/**
	 * Return the enumeration after it has been "sorted".
	 * The specified enumeration size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> Enumeration<E> sort(Enumeration<? extends E> enumeration, int enumerationSize) {
		return sort(enumeration, null, enumerationSize);
	}

	/**
	 * Return the enumeration after it has been "sorted".
	 */
	public static <E> Enumeration<E> sort(Enumeration<? extends E> enumeration, Comparator<? super E> comparator) {
		return ListTools.sort(CollectionTools.vector(iterator(enumeration)), comparator).elements();
	}

	/**
	 * Return the enumeration after it has been "sorted".
	 * The specified enumeration size is a performance hint.
	 */
	public static <E> Enumeration<E> sort(Enumeration<? extends E> enumeration, Comparator<? super E> comparator, int enumerationSize) {
		return ListTools.sort(CollectionTools.vector(iterator(enumeration), enumerationSize), comparator).elements();
	}

	/**
	 * Return an iterator corresponding to the specified enumeration.
	 */
	public static <E> Iterator<E> iterator(Enumeration<? extends E> enumeration) {
		return new EnumerationIterator<E>(enumeration);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private EnumerationTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
