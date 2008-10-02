/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * A collection that allows duplicate elements.
 * <p>
 * The <code>Bag</code> interface places additional stipulations,
 * beyond those inherited from the <code>java.util.Collection</code> interface,
 * on the contracts of the <code>equals</code> and <code>hashCode</code> methods.
 * 
 * @see HashBag
 */

public interface Bag<E> extends java.util.Collection<E> {

	/**
	 * Compares the specified object with this bag for equality. Returns
	 * <code>true</code> if the specified object is also a bag, the two bags
	 * have the same size, and every member of the specified bag is
	 * contained in this bag with the same number of occurrences (or equivalently,
	 * every member of this bag is contained in the specified bag with the same
	 * number of occurrences). This definition ensures that the
	 * equals method works properly across different implementations of the
	 * bag interface.
	 */
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this bag. The hash code of a bag is
	 * defined to be the sum of the hash codes of the elements in the bag,
	 * where the hashcode of a <code>null</code> element is defined to be zero.
	 * This ensures that <code>b1.equals(b2)</code> implies that
	 * <code>b1.hashCode() == b2.hashCode()</code> for any two bags
	 * <code>b1</code> and <code>b2</code>, as required by the general
	 * contract of the <code>Object.hashCode</code> method.
	 */
	int hashCode();

	/**
	 * Return the number of times the specified object occurs in the bag.
	 */
	int count(Object o);

	/**
	 * Add the specified object the specified number of times to the bag.
	 */
	boolean add(E o, int count);

	/**
	 * Remove the specified number of occurrences of the specified object
	 * from the bag. Return whether the bag changed.
	 */
	boolean remove(Object o, int count);

	/**
	 * Return an iterator that returns each item in the bag
	 * once and only once, irrespective of how many times
	 * the item was added to the bag.
	 */
	java.util.Iterator<E> uniqueIterator();

	/**
	 * Return an iterator that returns an entry for each item in the bag
	 * once and only once, irrespective of how many times
	 * the item was added to the bag. The entry will indicate the item's
	 * count.
	 */
	java.util.Iterator<Entry<E>> entries();


	/**
	 * A bag entry (element-count pair).
	 * The <code>Bag.entries</code> method returns an iterator whose
	 * elements are of this class. The <i>only</i> way to obtain a reference
	 * to a bag entry is from the iterator returned by this method. These
	 * <code>Bag.Entry</code> objects are valid <i>only</i> for the duration
	 * of the iteration; more formally, the behavior of a bag entry is
	 * undefined if the backing bag has been modified after the entry was
	 * returned by the iterator, except through the <code>setCount</code>
	 * operation on the bag entry.
	 */
	interface Entry<E> {

		/**
		 * Return the entry's element.
		 */
		E getElement();

		/**
		 * Return entry's count; i.e. the number of times the entry's element
		 * occurs in the bag.
		 * @see Bag#count(Object)
		 */
		int getCount();

		/**
		 * Set the entry's count; i.e. the number of times the entry's element
		 * occurs in the bag. The new count must be a positive number.
		 * Return the previous count of the entry's element.
		 * NB: Use the iterator's <code>remove</code> method to set the
		 * count to zero.
		 */
		int setCount(int count);

		/**
		 * Return whether the entry is equal to the specified object;
		 * i.e. the specified object is a <code>Bag.Entry</code> and its
		 * element and count are the same as the entry's.
		 */
		boolean equals(Object obj);

		/**
		 * Return the entry's hash code.
		 */
		int hashCode();

	}


	final class Empty<E> extends AbstractCollection<E> implements Bag<E>, Serializable {
		@SuppressWarnings("unchecked")
		public static final Bag INSTANCE = new Empty();
		@SuppressWarnings("unchecked")
		public static <T> Bag<T> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		@Override
		public Iterator<E> iterator() {
			return EmptyIterator.instance();
		}
		@Override
		public int size() {
			return 0;
		}
		public Iterator<E> uniqueIterator() {
			return EmptyIterator.instance();
		}
		public int count(Object o) {
			return 0;
		}
		public Iterator<Bag.Entry<E>> entries() {
			return EmptyIterator.instance();
		}
		public boolean remove(Object o, int count) {
			return false;
		}
		public boolean add(E o, int count) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if ( ! (o instanceof Bag)) {
				return false;
			}
			return ((Bag<?>) o).size() == 0;
		}
		@Override
		public int hashCode() {
			return 0;
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			return INSTANCE;
		}
	}

}
