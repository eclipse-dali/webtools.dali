/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.Range;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
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
		return iterator.hasNext() ? list.addAll(index, list(iterator)) : false;
	}

	/**
	 * Add all the elements returned by the specified iterator
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator, int iteratorSize) {
		return iterator.hasNext() ? list.addAll(index, list(iterator, iteratorSize)) : false;
	}

	/**
	 * Add all the elements in the specified array
	 * to the specified list at the specified index.
	 * Return whether the list changed as a result.
	 */
	public static <E> boolean addAll(List<? super E> list, int index, E... array) {
		return (array.length == 0) ? false : list.addAll(index, Arrays.asList(array));
	}


	// ********** diff **********

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return <code>[size, -1]</code>.
	 * Use the elements' {@link Object#equals(Object)} method to compare the
	 * elements.
	 * @see #indexOfDifference(List, List)
	 * @see #lastIndexOfDifference(List, List)
	 */
	public static Range differenceRange(List<?> list1, List<?> list2) {
		int end = lastIndexOfDifference(list1, list2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(list1.size(), end);
		}
		// the lists are different, calculate the start of the range
		return new Range(indexOfDifference(list1, list2), end);
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
	 * Return a new list with the filtered
	 * elements of the specified list.
	 */
	public static <E> ArrayList<E> filter(Collection<? extends E> list, Filter<E> filter) {
		ArrayList<E> result = new ArrayList<E>(list.size());
		for (E e : list) {
			if (filter.accept(e)) {
				result.add(e);
			}
		}
		return result;
	}


	// ********** identity diff **********

	/**
	 * Return the range of elements in the specified
	 * arrays that are different.
	 * If the arrays are identical, return <code>[size, -1]</code>.
	 * Use object identity to compare the elements.
	 * @see #indexOfIdentityDifference(List, List)
	 * @see #lastIndexOfIdentityDifference(List, List)
	 */
	public static Range identityDifferenceRange(List<?> list1, List<?> list2) {
		int end = lastIndexOfIdentityDifference(list1, list2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(list1.size(), end);
		}
		// the lists are different, calculate the start of the range
		return new Range(indexOfIdentityDifference(list1, list2), end);
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


	// ********** move **********

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered list.
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


	// ********** remove **********

	/**
	 * Remove the elements at the specified index.
	 * Return the removed elements.
	 */
	public static <E> ArrayList<E> removeElementsAtIndex(List<E> list, int index, int length) {
		List<E> subList = list.subList(index, index + length);
		ArrayList<E> removed = new ArrayList<E>(subList);
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

		LinkedHashSet<E> temp = new LinkedHashSet<E>(listSize);		// take advantage of hashed look-up
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
	public static <E1, E2> ArrayList<E2> transform(List<E1> list, Transformer<E1, ? extends E2> transformer) {
		ArrayList<E2> result = new ArrayList<E2>(list.size());
		for (E1 e : list) {
			result.add(transformer.transform(e));
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
	 * Return a list corresponding to the specified iterable.
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable) {
		return list(iterable.iterator());
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable, int iterableSize) {
		return list(iterable.iterator(), iterableSize);
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 */
	public static <E> ArrayList<E> list(Iterator<? extends E> iterator) {
		return list(iterator, new ArrayList<E>());
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
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
		ArrayList<E> list = new ArrayList<E>(array.length);
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
		extends TransformerAdapter<List<E>, ListIterator<E>>
	{
		@Override
		public ListIterator<E> transform(List<E> list) {
			return list.listIterator();
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterator<? extends E>> readOnlyListIteratorTransformer() {
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
		extends TransformerAdapter<List<? extends E>, ListIterator<? extends E>>
	{
		@Override
		public ListIterator<? extends E> transform(List<? extends E> list) {
			return IteratorTools.readOnlyListIterator(list.listIterator());
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
		extends TransformerAdapter<List<E>, ListIterable<E>>
	{
		@Override
		public ListIterable<E> transform(List<E> list) {
			return IterableTools.listIterable(list);
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * <em>read-only</em> {@link ListIterable}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterable<? extends E>> readOnlyListIterableTransformer() {
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
		extends TransformerAdapter<List<? extends E>, ListIterable<? extends E>>
	{
		@Override
		public ListIterable<? extends E> transform(List<? extends E> list) {
			return IterableTools.listIterable(list);
		}
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
