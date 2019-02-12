/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.collection;

/**
 * A collection that allows duplicate elements.
 * <p>
 * The <code>Bag</code> interface places additional stipulations,
 * beyond those inherited from the {@link java.util.Collection} interface,
 * on the contracts of the {@link #equals(Object)} and {@link #hashCode()} methods.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * @see org.eclipse.jpt.common.utility.internal.collection.HashBag
 */
public interface Bag<E>
	extends java.util.Collection<E>
{
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
	 * contract of the {@link Object#hashCode()} method.
	 */
	int hashCode();

	/**
	 * Return the number of times the specified object occurs in the bag.
	 */
	int count(Object o);

	/**
	 * Add the specified object the specified number of times to the bag.
	 * Return whether the bag changed.
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
	 * Calling {@link java.util.Iterator#remove() remove} on the
	 * returned iterator will remove from the bag <em>all</em> copies of
	 * the iterator's current item.
	 */
	java.util.Iterator<E> uniqueIterator();

	/**
	 * Return the number of unique items in the bag.
	 */
	int uniqueCount();

	/**
	 * Return an iterator that returns an entry for each item in the bag
	 * once and only once, irrespective of how many times
	 * the item was added to the bag. The entry will indicate the item's
	 * count.
	 * Calling {@link java.util.Iterator#remove() remove} on the
	 * returned iterator will remove from the bag <em>all</em> copies of
	 * the item associated with the iterator's current entry.
	 */
	java.util.Iterator<Entry<E>> entries();


	/**
	 * A bag entry (element-count pair).
	 * The {@link Bag#entries()} method returns an iterator whose
	 * elements are of this class. The <em>only</em> way to obtain a reference
	 * to a bag entry is from the iterator returned by this method. These
	 * <code>Bag.Entry</code> objects are valid <em>only</em> for the duration
	 * of the iteration; more formally, the behavior of a bag entry is
	 * undefined if the backing bag has been modified after the entry was
	 * returned by the iterator, except through the {@link #setCount(int)}
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
		 * NB: Use {@link java.util.Iterator#remove()} to set the
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
}
