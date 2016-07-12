/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.eclipse.jpt.common.utility.internal.Range;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link List} utility methods.
 */
public final class ListTools {

	// ********** add all **********

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<? extends E> iterable) {
		return addAll(list, index, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<? extends E> iterable, int iterableSize) {
		return addAll(list, index, iterable.iterator(), iterableSize);
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator) {
		return iterator.hasNext() && addAll_(list, index, iterator);
	}

	/**
	 * assume the iterator is not empty
	 */
	private static <E> boolean addAll_(List<? super E> list, int index, Iterator<? extends E> iterator) {
		return (index == list.size()) ? CollectionTools.addAll_(list, iterator) : list.addAll(index, arrayList(iterator));
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() && list.addAll(index, arrayList(iterator, iteratorSize));
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 */
	@SafeVarargs
	public static <E> boolean addAll(List<? super E> list, int index, E... array) {
		return (array.length != 0) && list.addAll(index, Arrays.asList(array));
	}


	// ********** diff **********

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * Return <code>null</code> if the arrays are identical.
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 * @see #indexOfDifference(List, List)
	 * @see #lastIndexOfDifference(List, List)
	 */
	public static Range differenceRange(List<?> list1, List<?> list2) {
		int end = lastIndexOfDifference(list1, list2);
		return (end == -1) ? null : new Range(indexOfDifference(list1, list2), end);
	}

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different. If the lists are identical, return
	 * the size of the two lists (i.e. one past the last index).
	 * If the lists are different sizes and all the elements in
	 * the shorter list match their corresponding elements in
	 * the longer list, return the size of the shorter list
	 * (i.e. one past the last index of the shorter list).
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 */
	public static int indexOfDifference(List<?> list1, List<?> list2) {
		int end = Math.min(list1.size(), list2.size());
		for (int i = 0; i < end; i++) {
			Object o = list1.get(i);
			if (o == null) {
				if (list2.get(i) != null) {
					return i;
				}
			} else {
				if ( ! o.equals(list2.get(i))) {
					return i;
				}
			}
		}
		return end;
	}

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different, beginning at the end.
	 * If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the
	 * last element in the longer list.
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 */
	public static int lastIndexOfDifference(List<?> list1, List<?> list2) {
		int len1 = list1.size();
		int len2 = list2.size();
		if (len1 != len2) {
			return Math.max(len1, len2) - 1;
		}
		for (int i = len1 - 1; i > -1; i--) {
			Object o = list1.get(i);
			if (o == null) {
				if (list2.get(i) != null) {
					return i;
				}
			} else {
				if ( ! o.equals(list2.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}


	// ********** filter **********

	/**
	 * Return a new list with the elements of the specified collection
	 * that are instances of the specified class.
	 */
	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E> filter(Collection<?> collection, Class<E> clazz) {
		return (ArrayList<E>) filter(collection, PredicateTools.instanceOf(clazz));
	}

	/**
	 * Return a new list with the filtered
	 * elements of the specified collection.
	 */
	public static <E> ArrayList<E> filter(Collection<? extends E> collection, Predicate<? super E> predicate) {
		ArrayList<E> result = new ArrayList<>(collection.size());
		for (E each : collection) {
			if (predicate.evaluate(each)) {
				result.add(each);
			}
		}
		return result;
	}


	// ********** identity diff **********

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * Return <code>null</code> if the arrays are identical.
	 * Use object identity to compare the elements.
	 * @see #indexOfIdentityDifference(List, List)
	 * @see #lastIndexOfIdentityDifference(List, List)
	 */
	public static Range identityDifferenceRange(List<?> list1, List<?> list2) {
		int end = lastIndexOfIdentityDifference(list1, list2);
		return (end == -1) ? null : new Range(indexOfIdentityDifference(list1, list2), end);
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
	 */
	public static int indexOfIdentityDifference(List<?> list1, List<?> list2) {
		int end = Math.min(list1.size(), list2.size());
		for (int i = 0; i < end; i++) {
			if (list1.get(i) != list2.get(i)) {
				return i;
			}
		}
		return end;
	}

	/**
	 * Return the index of the first elements in the specified
	 * lists that are different, beginning at the end.
	 * If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the
	 * last element in the longer list.
	 * Use object identity to compare the elements.
	 */
	public static int lastIndexOfIdentityDifference(List<?> list1, List<?> list2) {
		int len1 = list1.size();
		int len2 = list2.size();
		if (len1 != len2) {
			return Math.max(len1, len2) - 1;
		}
		for (int i = len1 - 1; i > -1; i--) {
			if (list1.get(i) != list2.get(i)) {
				return i;
			}
		}
		return -1;
	}


	// ********** index of **********

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified list, starting at the specified index;
	 * or return -1 if there is no such element.
	 */
	public static <E> int indexOf(List<E> list, Object value, int startIndex) {
		int size = list.size();
		return (size == 0) ? -1 : (startIndex >= size) ? -1 : indexOf(list, value, (startIndex < 0) ? 0 : startIndex, size);
	}

	/**
	 * assume 0 <= start index < list size
	 */
	private static <E> int indexOf(List<E> list, Object value, int startIndex, int listSize) {
		if (value == null) {
			for (int i = startIndex; i < listSize; i++) {
				if (list.get(i) == null) {
					return i;
				}
			}
		} else {
			for (int i = startIndex; i < listSize; i++) {
				if (value.equals(list.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified list, starting at the specified index;
	 * or return -1 if there is no such element.
	 */
	public static <E> int identityIndexOf(List<E> list, Object value, int startIndex) {
		int size = list.size();
		return (size == 0) ? -1 : (startIndex >= size) ? -1 : identityIndexOf(list, value, (startIndex < 0) ? 0 : startIndex, size);
	}

	/**
	 * assume 0 <= start index < list size
	 */
	private static <E> int identityIndexOf(List<E> list, Object value, int startIndex, int listSize) {
		for (int i = startIndex; i < listSize; i++) {
			if (list.get(i) == value) {
				return i;
			}
		}
		return -1;
	}


	// ********** insertion index **********

	/**
	 * Return an index of where the specified comparable object
	 * can be inserted into the specified sorted list and still keep
	 * the list sorted. If the specified sorted list is an instance of
	 * {@link RandomAccess} return the <em>maximum</em> insertion index;
	 * otherwise return the <em>minimum</em> insertion index.
	 */
	public static <E extends Comparable<? super E>> int insertionIndexOf(List<E> sortedList, Comparable<E> value) {
		if (sortedList instanceof RandomAccess) {
			for (int i = sortedList.size(); i-- > 0; ) {
				if (value.compareTo(sortedList.get(i)) >= 0) {
					return i + 1;
				}
			}
			return 0;
		}
		int i = 0;
		for (E element : sortedList) {
			if (value.compareTo(element) <= 0) {
				return i;
			}
			i++;
		}
		return i;
	}

	/**
	 * Return an index of where the specified comparable object
	 * can be inserted into the specified sorted list and still keep
	 * the list sorted. If the specified sorted list is an instance of
	 * {@link RandomAccess} return the <em>maximum</em> insertion index;
	 * otherwise return the <em>minimum</em> insertion index.
	 */
	public static <E> int insertionIndexOf(List<E> sortedList, E value, Comparator<? super E> comparator) {
		if (sortedList instanceof RandomAccess) {
			for (int i = sortedList.size(); i-- > 0; ) {
				if (comparator.compare(value, sortedList.get(i)) >= 0) {
					return i + 1;
				}
			}
			return 0;
		}
		int i = 0;
		for (E element : sortedList) {
			if (comparator.compare(value, element) <= 0) {
				return i;
			}
			i++;
		}
		return i;
	}


	// ********** last index of **********

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified array, starting at the specified index;
	 * or return -1 if there is no such element.
	 */
	public static <E> int lastIndexOf(List<E> list, Object value, int startIndex) {
		if (startIndex < 0) {
			return -1;
		}
		int size = list.size();
		return (size == 0) ? -1 : lastIndexOf_(list, value, (startIndex >= size) ? size - 1 : startIndex);
	}

	/**
	 * assume 0 <= start index < list size
	 */
	private static <E> int lastIndexOf_(List<E> list, Object value, int startIndex) {
		if (value == null) {
			for (int i = startIndex; i >= 0; i--) {
				if (list.get(i) == null) {
					return i;
				}
			}
		} else {
			for (int i = startIndex; i >= 0; i--) {
				if (value.equals(list.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified array, starting at the specified index;
	 * or return -1 if there is no such element.
	 */
	public static <E> int lastIdentityIndexOf(List<E> list, Object value, int startIndex) {
		if (startIndex < 0) {
			return -1;
		}
		int size = list.size();
		return (size == 0) ? -1 : lastIdentityIndexOf_(list, value, (startIndex >= size) ? size - 1 : startIndex);
	}

	/**
	 * assume 0 <= start index < list size
	 */
	private static <E> int lastIdentityIndexOf_(List<E> list, Object value, int startIndex) {
		for (int i = startIndex; i >= 0; i--) {
			if (list.get(i) == value) {
				return i;
			}
		}
		return -1;
	}


	// ********** move **********

	/**
	 * Move the specified element from its current position to the specified
	 * index. Return the altered list.
	 */
	public static <E, L extends List<E>> L move(L list, int index, E element) {
		return move(list, index, list.indexOf(element));
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered list.
	 */
	public static <E, L extends List<E>> L move(L list, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? list : move_(list, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static <E, L extends List<E>> L move_(L list, int targetIndex, int sourceIndex) {
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
	 */
	public static <E, L extends List<E>> L move(L list, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return list;
		}
		if (length == 1) {
			return move_(list, targetIndex, sourceIndex);
		}
		if (list instanceof RandomAccess) {
			// move elements, leaving the list in place
			ArrayList<E> temp = new ArrayList<>(list.subList(sourceIndex, sourceIndex + length));
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


	// ********** remove **********

	/**
	 * Remove the elements at the specified index.
	 * Return the removed elements.
	 */
	public static <E> ArrayList<E> removeElementsAtIndex(List<? extends E> list, int index, int length) {
		List<? extends E> subList = list.subList(index, index + length);
		ArrayList<E> removed = new ArrayList<>(subList);
		subList.clear();
		return removed;
	}

	/**
	 * Remove any duplicate elements from the specified list,
	 * while maintaining the order.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean removeDuplicateElements(List<E> list) {
		int listSize = list.size();
		if ((listSize == 0) || (listSize == 1)) {
			return false;
		}

		LinkedHashSet<E> temp = new LinkedHashSet<>(listSize);		// take advantage of hashed look-up
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


	// ********** rotate **********

	/**
	 * Return the list after it has been "rotated" by one position.
	 */
	public static <E> List<E> rotate(List<E> list) {
		return rotate(list, 1);
	}


	// ********** transform **********

	/**
	 * Return a new list with transformations of the
	 * elements in the specified list.
	 */
	public static <I, O> ArrayList<O> transform(Collection<I> list, Transformer<? super I, ? extends O> transformer) {
		ArrayList<O> result = new ArrayList<>(list.size());
		for (I each : list) {
			result.add(transformer.transform(each));
		}
		return result;
	}


	// ********** java.util.Collections enhancements **********

	/**
	 * Return the destination list after the source list has been copied into it.
	 * @see Collections#copy(List, List)
	 */
	public static <E, L extends List<E>> L copy(L dest, List<? extends E> src) {
		Collections.copy(dest, src);
		return dest;
	}

	/**
	 * Return the list after it has been "filled".
	 * @see Collections#fill(List, Object)
	 */
	public static <E, L extends List<E>> L fill(L list, E value) {
		Collections.fill(list, value);
		return list;
	}

	/**
	 * Return the list after it has been "reversed".
	 * @see Collections#reverse(List)
	 */
	public static <E, L extends List<E>> L reverse(L list) {
		Collections.reverse(list);
		return list;
	}

	/**
	 * Return the list after it has been "rotated".
	 * @see Collections#rotate(List, int)
	 */
	public static <E, L extends List<E>> L rotate(L list, int distance) {
		Collections.rotate(list, distance);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 * @see Collections#shuffle(List)
	 */
	public static <E, L extends List<E>> L shuffle(L list) {
		Collections.shuffle(list);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 * @see Collections#shuffle(List, Random)
	 */
	public static <E, L extends List<E>> L shuffle(L list, Random random) {
		Collections.shuffle(list, random);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 * NB: The list is sorted in place as a side-effect.
	 * @see Collections#sort(List)
	 */
	public static <E extends Comparable<? super E>, L extends List<E>> L sort(L list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 * NB: The list is sorted in place as a side-effect.
	 * @see Collections#sort(List, Comparator)
	 */
	public static <E, L extends List<E>> L sort(L list, Comparator<? super E> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * Return the list after the specified elements have been "swapped".
	 * @see Collections#swap(List, int, int)
	 */
	public static <E, L extends List<E>> L swap(L list, int i, int j) {
		Collections.swap(list, i, j);
		return list;
	}


	// ********** factory methods **********

	/**
	 * Return an array list corresponding to the specified iterable.
	 */
	public static <E> ArrayList<E> arrayList(Iterable<? extends E> iterable) {
		return arrayList(iterable.iterator());
	}

	/**
	 * Return an array list corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayList<E> arrayList(Iterable<? extends E> iterable, int iterableSize) {
		return arrayList(iterable.iterator(), iterableSize);
	}

	/**
	 * Return an array list corresponding to the specified iterator.
	 */
	public static <E> ArrayList<E> arrayList(Iterator<? extends E> iterator) {
		return arrayList(iterator, new ArrayList<E>());
	}

	/**
	 * Return an array list corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayList<E> arrayList(Iterator<? extends E> iterator, int iteratorSize) {
		return arrayList(iterator, new ArrayList<E>(iteratorSize));
	}

	private static <E> ArrayList<E> arrayList(Iterator<? extends E> iterator, ArrayList<E> list) {
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * Return an array list corresponding to the specified array.
	 * Unlike {@link Arrays#asList(Object[])}, the list
	 * is modifiable and is not backed by the array.
	 */
	@SafeVarargs
	public static <E> ArrayList<E> arrayList(E... array) {
		ArrayList<E> list = new ArrayList<>(array.length);
		for (E e : array) {
			list.add(e);
		}
		return list;
	}


	// ********** transformers **********

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<E>, ListIterator<E>> listIteratorTransformer() {
		return LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer LIST_ITERATOR_TRANSFORMER = new ListIteratorTransformer();

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterator}.
	 */
	public static class ListIteratorTransformer<E>
		implements Transformer<List<E>, ListIterator<E>>
	{
		public ListIterator<E> transform(List<E> list) {
			return list.listIterator();
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterator<E>> readOnlyListIteratorTransformer() {
		return READ_ONLY_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_ITERATOR_TRANSFORMER = new ReadOnlyListIteratorTransformer();

	/**
	 * A transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	public static class ReadOnlyListIteratorTransformer<E>
		implements Transformer<List<? extends E>, ListIterator<E>>
	{
		public ListIterator<E> transform(List<? extends E> list) {
			return IteratorTools.<E>readOnly(list.listIterator());
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * {@link ListIterable}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<E>, ListIterable<E>> listIterableTransformer() {
		return LIST_ITERABLE_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterable}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer LIST_ITERABLE_TRANSFORMER = new ListIterableTransformer();

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterable}.
	 */
	public static class ListIterableTransformer<E>
		implements Transformer<List<E>, ListIterable<E>>
	{
		public ListIterable<E> transform(List<E> list) {
			return IterableTools.listIterable(list);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterable}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterable<E>> readOnlyListIterableTransformer() {
		return READ_ONLY_LIST_ITERABLE_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterable}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_ITERABLE_TRANSFORMER = new ReadOnlyListIterableTransformer();

	/**
	 * A transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterable}.
	 */
	public static class ReadOnlyListIterableTransformer<E>
		implements Transformer<List<? extends E>, ListIterable<E>>
	{
		public ListIterable<E> transform(List<? extends E> list) {
			return IterableTools.<E>readOnly(IterableTools.listIterable(list));
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}


	// ********** read/write lock wrapper **********

	/**
	 * Return a wrapper of the specified list that uses an nonfair
	 * reentrant read/write lock to control access to the list.
	 * The wrapper provides a simplified
	 * "list" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <E> the type of elements maintained by the list
	 */
	public static <E> ReadWriteLockListWrapper<E> readWriteLockWrapper(List<E> list) {
		return readWriteLockWrapper(list, false);
	}

	/**
	 * Return a wrapper of the specified list that uses a
	 * reentrant read/write lock with the specified fairness policy
	 * to control access to the list.
	 * The wrapper provides a simplified
	 * "list" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <E> the type of elements maintained by the list
	 */
	public static <E> ReadWriteLockListWrapper<E> readWriteLockWrapper(List<E> list, boolean fair) {
		return readWriteLockWrapper(list, new ReentrantReadWriteLock(fair));
	}

	/**
	 * Return a wrapper of the specified list that uses the specified
	 * read/write lock to control access to the list.
	 * The wrapper provides a simplified
	 * "list" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <E> the type of elements maintained by the list
	 */
	public static <E> ReadWriteLockListWrapper<E> readWriteLockWrapper(List<E> list, ReadWriteLock lock) {
		return new ReadWriteLockListWrapper<>(list, lock);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ListTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
