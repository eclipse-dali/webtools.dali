/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Collection} utility methods.
 * @see org.eclipse.jpt.common.utility.internal.ArrayTools
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools
 * @see org.eclipse.jpt.common.utility.internal.iterator.IteratorTools
 * @see ListTools
 */
public final class CollectionTools {

	// ********** add all **********

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable) {
		return addAll(collection, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable, int iterableSize) {
		return addAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator) {
		return iterator.hasNext() && addAll_(collection, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	/* package */ static <E> boolean addAll_(Collection<? super E> collection, Iterator<? extends E> iterator) {
		boolean modified = false;
		do {
			modified |= collection.add(iterator.next());
		} while (iterator.hasNext());
		return modified;
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() && collection.addAll(ListTools.arrayList(iterator, iteratorSize));
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static <E> boolean addAll(Collection<? super E> collection, E... array) {
		return (array.length != 0) && addAll_(collection, array);
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


	// ********** contains all **********

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterable.
	 */
	public static boolean containsAll(Collection<?> collection, Iterable<?> iterable) {
		return containsAll(collection, iterable.iterator());
	}

	/**
	 * Return whether the specified collection contains all of the
	 * elements in the specified iterator, retrieving elements from the iterator
	 * until one is not found in the collection.
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
	 */
	public static boolean containsAll(Collection<?> collection, Object... array) {
		for (int i = array.length; i-- > 0; ) {
			if ( ! collection.contains(array[i])) {
				return false;
			}
		}
		return true;
	}


	// ********** filter **********

	/**
	 * Return a new collection with the filtered
	 * elements of the specified collection.
	 */
	public static <E> HashBag<E> filter(Collection<? extends E> collection, Predicate<? super E> filter) {
		HashBag<E> result = hashBag(collection.size());
		for (E e : collection) {
			if (filter.evaluate(e)) {
				result.add(e);
			}
		}
		return result;
	}


	// ********** partition **********

	/**
	 * Divide the specified collection into the specified number of partitions.
	 * If the collection does not split evenly into the specified number of
	 * partitions, all the partitions will be one of two sizes; the first
	 * partions will be of size <code>collection.size()/count+1</code>,
	 * while the last partions will be of size <code>collection.size()/count</code>.
	 * The partitions will maintain the order of elements returned by the
	 * collection's iterator (i.e. the first elements returned by the iterator
	 * will be in the first partition, first element first).
	 */
	public static <E> ArrayList<ArrayList<E>> partition(Collection<? extends E> collection, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("count must be greater than zero: " + count); //$NON-NLS-1$
		}
		int collectionSize = collection.size();
		if (collectionSize < count) {
			throw new IllegalArgumentException("collection size (" + collectionSize + ") must be greater than or equal to count: " + count); //$NON-NLS-1$ //$NON-NLS-2$
		}
		int partitionSize = collectionSize / count;
		int remainder = collectionSize % count;

		Iterator<? extends E> stream = collection.iterator();
		ArrayList<ArrayList<E>> result = new ArrayList<ArrayList<E>>(count);
		if (remainder != 0) {
			// spread the remainder elements across the first partitions
			partitionSize++;
			for (int i = remainder; i-- > 0; ) {
				ArrayList<E> partition = new ArrayList<E>(partitionSize);
				for (int j = partitionSize; j-- > 0;) {
					partition.add(stream.next());
				}
				result.add(partition);
			}
			partitionSize--;
			count = count - remainder;
		}
		for (int i = count; i-- > 0; ) {
			ArrayList<E> partition = new ArrayList<E>(partitionSize);
			for (int j = partitionSize; j-- > 0;) {
				partition.add(stream.next());
			}
			result.add(partition);
		}
		return result;
	}


	// ********** remove all **********

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable) {
		return removeAll(collection, iterable.iterator());
	}

	/**
	 * Remove all the elements returned by the specified iterable
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return removeAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator) {
		return iterator.hasNext() && collection.removeAll(hashSet(iterator));
	}

	/**
	 * Remove all the elements returned by the specified iterator
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		return iterator.hasNext() && collection.removeAll(hashSet(iterator, iteratorSize));
	}

	/**
	 * Remove all the elements in the specified array
	 * from the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean removeAll(Collection<?> collection, Object... array) {
		return (array.length != 0) && collection.removeAll(hashSet(array));
	}


	// ********** remove all occurrences **********

	/**
	 * Remove all occurrences of the specified element
	 * from the specified collection.
	 * Return whether the collection changed as a result.
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


	// ********** retain all **********

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable) {
		return retainAll(collection, iterable.iterator());
	}

	/**
	 * Retain only the elements in the specified iterable
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable, int iterableSize) {
		return retainAll(collection, iterable.iterator(), iterableSize);
	}

	/**
	 * Retain only the elements in the specified iterator
	 * in the specified collection.
	 * Return whether the collection changed as a result.
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator) {
		if (iterator.hasNext()) {
			return collection.retainAll(hashSet(iterator));
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
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator, int iteratorSize) {
		if (iterator.hasNext()) {
			return collection.retainAll(hashSet(iterator, iteratorSize));
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
	 */
	public static boolean retainAll(Collection<?> collection, Object... array) {
		if (array.length > 0) {
			return collection.retainAll(hashSet(array));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}


	// ********** to array fix **********

	/**
	 * Return an array containing all of the elements in the specified collection.
	 * This is a compile-time type-safe alternative to
	 * {@link Collection#toArray(Object[])}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(Collection<? extends E> collection, Class<E> clazz) {
		return collection.toArray((E[]) Array.newInstance(clazz, collection.size()));
	}


	// ********** transform **********

	/**
	 * Return a new collection with transformations of the
	 * elements in the specified collection.
	 */
	public static <E1, E2> HashBag<E2> transform(Collection<E1> collection, Transformer<? super E1, ? extends E2> transformer) {
		HashBag<E2> result = hashBag(collection.size());
		for (E1 e : collection) {
			result.add(transformer.transform(e));
		}
		return result;
	}


	// ********** hash bag factory methods **********

	/**
	 * Return a new hash bag.
	 */
	public static <E> HashBag<E> hashBag() {
		return new HashBag<E>();
	}

	/**
	 * Return a new hash bag with the specified initial capacity.
	 */
	public static <E> HashBag<E> hashBag(int initialCapacity) {
		return new HashBag<E>(initialCapacity);
	}

	/**
	 * Return a new hash bag with the specified initial capacity
	 * and load factor.
	 */
	public static <E> HashBag<E> hashBag(int initialCapacity, float loadFactor) {
		return new HashBag<E>(initialCapacity, loadFactor);
	}

	/**
	 * Return a new hash bag corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> hashBag(Iterable<? extends E> iterable) {
		return hashBag(iterable.iterator());
	}

	/**
	 * Return a new hash bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> hashBag(Iterable<? extends E> iterable, int iterableSize) {
		return hashBag(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a new hash bag corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> hashBag(Iterator<? extends E> iterator) {
		HashBag<E> bag = hashBag();
		return hashBag(iterator, bag);
	}

	/**
	 * Return a new hash bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> hashBag(Iterator<? extends E> iterator, int iteratorSize) {
		HashBag<E> bag = hashBag(iteratorSize);
		return hashBag(iterator, bag);
	}

	private static <E> HashBag<E> hashBag(Iterator<? extends E> iterator, HashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return a new hash bag corresponding to the specified array.
	 */
	public static <E> HashBag<E> hashBag(E... array) {
		int len = array.length;
		HashBag<E> bag = hashBag(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}


	// ********** synchronized bag factory methods **********

	/**
	 * Return a synchronized bag.
	 */
	public static <E> SynchronizedBag<E> synchronizedBag() {
		HashBag<E> bag = hashBag();
		return synchronizedBag(bag);
	}

	/**
	 * Return a bag that synchronizes with specified mutex.
	 */
	public static <E> SynchronizedBag<E> synchronizedBag(Object mutex) {
		HashBag<E> bag = hashBag();
		return synchronizedBag(bag, mutex);
	}

	/**
	 * Return a bag that synchronizes the specified bag.
	 */
	public static <E> SynchronizedBag<E> synchronizedBag(Bag<E> bag) {
		return new SynchronizedBag<E>(bag);
	}

	/**
	 * Return a bag that synchronizes the specified bag
	 * with specified mutex.
	 */
	public static <E> SynchronizedBag<E> synchronizedBag(Bag<E> bag, Object mutex) {
		return new SynchronizedBag<E>(bag, mutex);
	}

	/**
	 * Return a bag corresponding to the specified stack, draining the stack
	 * in the process.
	 * The specified stack size is a performance hint.
	 */
	public static <E> Bag<E> emptyBag() {
		return EmptyBag.instance();
	}


	// ********** identity hash bag factory methods **********

	/**
	 * Return a new identity hash bag.
	 */
	public static <E> IdentityHashBag<E> identityHashBag() {
		return new IdentityHashBag<E>();
	}

	/**
	 * Return a new identity hash bag with the specified initial capacity.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(int initialCapacity) {
		return new IdentityHashBag<E>(initialCapacity);
	}

	/**
	 * Return a new identity hash bag with the specified initial capacity
	 * and load factor.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(int initialCapacity, float loadFactor) {
		return new IdentityHashBag<E>(initialCapacity, loadFactor);
	}

	/**
	 * Return a new identity hash bag corresponding to the specified iterable.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(Iterable<? extends E> iterable) {
		return identityHashBag(iterable.iterator());
	}

	/**
	 * Return a new identity hash bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(Iterable<? extends E> iterable, int iterableSize) {
		return identityHashBag(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a new identity hash bag corresponding to the specified iterator.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(Iterator<? extends E> iterator) {
		IdentityHashBag<E> bag = identityHashBag();
		return identityHashBag(iterator, bag);
	}

	/**
	 * Return a new identity hash bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(Iterator<? extends E> iterator, int iteratorSize) {
		IdentityHashBag<E> bag = identityHashBag(iteratorSize);
		return identityHashBag(iterator, bag);
	}

	private static <E> IdentityHashBag<E> identityHashBag(Iterator<? extends E> iterator, IdentityHashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return a new identity hash bag corresponding to the specified array.
	 */
	public static <E> IdentityHashBag<E> identityHashBag(E... array) {
		int len = array.length;
		IdentityHashBag<E> bag = identityHashBag(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}


	// ********** hash set factory methods **********

	/**
	 * Return a new hash set corresponding to the specified iterable.
	 */
	public static <E> HashSet<E> hashSet(Iterable<? extends E> iterable) {
		return hashSet(iterable.iterator());
	}

	/**
	 * Return a new hash set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashSet<E> hashSet(Iterable<? extends E> iterable, int iterableSize) {
		return hashSet(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a new hash set corresponding to the specified iterator.
	 */
	public static <E> HashSet<E> hashSet(Iterator<? extends E> iterator) {
		return hashSet(iterator, new HashSet<E>());
	}

	/**
	 * Return a new hash set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashSet<E> hashSet(Iterator<? extends E> iterator, int iteratorSize) {
		return hashSet(iterator, new HashSet<E>(iteratorSize));
	}

	private static <E> HashSet<E> hashSet(Iterator<? extends E> iterator, HashSet<E> set) {
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Return a new hash set corresponding to the specified array.
	 */
	public static <E> HashSet<E> hashSet(E... array) {
		HashSet<E> set = new HashSet<E>(array.length);
		for (int i = array.length; i-- > 0;) {
			set.add(array[i]);
		}
		return set;
	}


	// ********** tree (sorted) set factory methods **********

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterable.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> treeSet(Iterable<? extends E> iterable) {
		return treeSet(iterable.iterator());
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> treeSet(Iterable<? extends E> iterable, int iterableSize) {
		return treeSet(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterable
	 * and comparator.
	 */
	public static <E> TreeSet<E> treeSet(Iterable<? extends E> iterable, Comparator<? super E> comparator) {
		return treeSet(iterable.iterator(), comparator);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterable
	 * and comparator.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> TreeSet<E> treeSet(Iterable<? extends E> iterable, Comparator<? super E> comparator, int iterableSize) {
		return treeSet(iterable.iterator(), comparator, iterableSize);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterator.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> treeSet(Iterator<? extends E> iterator) {
		return treeSet(iterator, null);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> treeSet(Iterator<? extends E> iterator, int iteratorSize) {
		return treeSet(iterator, null, iteratorSize);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterator
	 * and comparator.
	 */
	public static <E> TreeSet<E> treeSet(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return treeSet(ListTools.arrayList(iterator), comparator);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified iterator
	 * and comparator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> TreeSet<E> treeSet(Iterator<? extends E> iterator, Comparator<? super E> comparator, int iteratorSize) {
		return treeSet(ListTools.arrayList(iterator, iteratorSize), comparator);
	}

	private static <E> TreeSet<E> treeSet(ArrayList<E> list, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(list);
		return sortedSet;
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified array.
	 */
	public static <E extends Comparable<? super E>> TreeSet<E> treeSet(E... array) {
		return treeSet(array, null);
	}

	/**
	 * Return a new tree (sorted) set corresponding to the specified array
	 * and comparator.
	 */
	public static <E> TreeSet<E> treeSet(E[] array, Comparator<? super E> comparator) {
		TreeSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(Arrays.asList(array));
		return sortedSet;
	}


	// ********** Old School Vector factory methods **********

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable) {
		return vector(iterable.iterator());
	}

	/**
	 * Return a vector corresponding to the specified iterable.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable, int size) {
		return vector(iterable.iterator(), size);
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator) {
		return vector(iterator, new Vector<E>());
	}

	/**
	 * Return a vector corresponding to the specified iterator.
	 * This is useful for legacy code that requires a {@link Vector}.
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
	 */
	public static <E> Vector<E> vector(E... array) {
		Vector<E> v = new Vector<E>(array.length);
		for (E item : array) {
			v.addElement(item);
		}
		return v;
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
