/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.ChainIterator;
import org.eclipse.jpt.common.utility.internal.iterator.CloneIterator;
import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.GraphIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.PeekableIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Iterable} utility methods.
 * @see ArrayTools
 * @see CollectionTools
 * @see IteratorTools
 * @see ListTools
 */
public final class IterableTools {
	/**
	 * Return a bag corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable) {
		return CollectionTools.bag(iterable);
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Iterable<? extends E> iterable, int iterableSize) {
		return CollectionTools.bag(iterable, iterableSize);
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable) {
		return CollectionTools.collection(iterable);
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterable<? extends E> iterable, int iterableSize) {
		return CollectionTools.collection(iterable, iterableSize);
	}

	/**
	 * Return whether the specified iterable contains the
	 * specified element.
	 */
	public static boolean contains(Iterable<?> iterable, Object value) {
		return IteratorTools.contains(iterable.iterator(), value);
	}

	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified collection.
	 */
	public static boolean containsAll(Iterable<?> iterable, Collection<?> collection) {
		return IteratorTools.containsAll(iterable.iterator(), collection);
	}


	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified collection.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Collection<?> collection) {
		return IteratorTools.containsAll(iterable.iterator(), iterableSize, collection);
	}


	/**
	 * Return whether the specified iterable 1 contains all of the
	 * elements in the specified iterable 2.
	 */
	public static boolean containsAll(Iterable<?> iterable1, Iterable<?> iterable2) {
		return IteratorTools.containsAll(iterable1.iterator(), iterable2);
	}


	/**
	 * Return whether the specified iterable 1 contains all of the
	 * elements in the specified iterable 2.
	 * The specified iterable 1 size is a performance hint.
	 */
	public static boolean containsAll(Iterable<?> iterable1, int iterable1Size, Iterable<?> iterable2) {
		return IteratorTools.containsAll(iterable1.iterator(), iterable1Size, iterable2);
	}


	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified iterator.
	 */
	public static boolean containsAll(Iterable<?> iterable, Iterator<?> iterator) {
		return IteratorTools.containsAll(iterable.iterator(), iterator);
	}


	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified iterator.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Iterator<?> iterator) {
		return IteratorTools.containsAll(iterable.iterator(), iterableSize, iterator);
	}


	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified array.
	 */
	public static boolean containsAll(Iterable<?> iterable, Object... array) {
		return IteratorTools.containsAll(iterable.iterator(), array);
	}


	/**
	 * Return whether the specified iterable contains all of the
	 * elements in the specified array.
	 * The specified iterable size is a performance hint.
	 */
	public static boolean containsAll(Iterable<?> iterable, int iterableSize, Object... array) {
		return IteratorTools.containsAll(iterable.iterator(), iterableSize, array);
	}

	/**
	 * Return whether the specified iterables do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Iterable<?> iterable1, Iterable<?> iterable2) {
		return IteratorTools.elementsAreDifferent(iterable1.iterator(), iterable2.iterator());
	}


	/**
	 * Return whether the specified iterables return equal elements
	 * in the same order.
	 */
	public static boolean elementsAreEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
		return IteratorTools.elementsAreEqual(iterable1.iterator(), iterable2.iterator());
	}


	/**
	 * Return whether the specified iterables return the same elements.
	 */
	public static boolean elementsAreIdentical(Iterable<?> iterable1, Iterable<?> iterable2) {
		return IteratorTools.elementsAreIdentical(iterable1.iterator(), iterable2.iterator());
	}


	/**
	 * Return whether the specified iterables do <em>not</em> return the same
	 * elements.
	 */
	public static boolean elementsAreNotIdentical(Iterable<?> iterable1, Iterable<?> iterable2) {
		return IteratorTools.elementsAreNotIdentical(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return the element corresponding to the specified index
	 * in the specified iterable.
	 */
	public static <E> E get(Iterable<? extends E> iterable, int index) {
		return IteratorTools.get(iterable.iterator(), index);
	}

	/**
	 * Return a hash code corresponding to the elements in the specified iterable.
	 */
	public static int hashCode(Iterable<?> iterable) {
		return (iterable == null) ? 0 : IteratorTools.hashCode(iterable.iterator());
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterable;
	 * return -1 if there is no such element.
	 */
	public static int indexOf(Iterable<?> iterable, Object value) {
		return IteratorTools.indexOf(iterable.iterator(), value);
	}

	/**
	 * Return whether the specified iterable is empty
	 * (Shortcuts the iterator rather than calculating the entire size)
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return IteratorTools.isEmpty(iterable.iterator());
	}

	/**
	 * Return the specified iterable's last element.
	 * @exception java.util.NoSuchElementException iterable is empty.
	 */
	public static <E> E last(Iterable<E> iterable) {
		return IteratorTools.last(iterable.iterator());
	}

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterable;
	 * return -1 if there is no such element.
	 */
	public static int lastIndexOf(Iterable<?> iterable, Object value) {
		return IteratorTools.lastIndexOf(iterable.iterator(), value);
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable) {
		return ListTools.list(iterable);
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> ArrayList<E> list(Iterable<? extends E> iterable, int iterableSize) {
		return ListTools.list(iterable, iterableSize);
	}

	/**
	 * Return the number of elements returned by the specified iterable.
	 */
	public static int size(Iterable<?> iterable) {
		return IteratorTools.size(iterable.iterator());
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<E> iterable) {
		return sort(iterable, null);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<E> iterable, int iterableSize) {
		return sort(iterable, null, iterableSize);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 */
	public static <E> Iterable<E> sort(Iterable<E> iterable, Comparator<? super E> comparator) {
		return ListTools.sort(ListTools.list(iterable), comparator);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> Iterable<E> sort(Iterable<E> iterable, Comparator<? super E> comparator, int iterableSize) {
		return ListTools.sort(ListTools.list(iterable, iterableSize), comparator);
	}

	/**
	 * Convert the specified iterable into an array.
	 * @see Collection#toArray()
	 */
	public static Object[] toArray(Iterable<?> iterable) {
		return list(iterable).toArray();
	}

	/**
	 * Convert the specified iterable into an array.
	 * The specified iterable size is a performance hint.
	 * @see Collection#toArray()
	 */
	public static Object[] toArray(Iterable<?> iterable, int iterableSize) {
		return list(iterable, iterableSize).toArray();
	}

	/**
	 * Convert the specified iterable into an array.
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] toArray(Iterable<? extends E> iterable, E[] array) {
		return list(iterable).toArray(array);
	}

	/**
	 * Convert the specified iterable into an array.
	 * The specified iterable size is a performance hint.
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] toArray(Iterable<? extends E> iterable, int iterableSize, E[] array) {
		return list(iterable, iterableSize).toArray(array);
	}


	// ********** factory methods **********

	/**
	 * Return a chain iterable that starts with the specified element and uses
	 * the specified {@link org.eclipse.jpt.common.utility.internal.iterator.ChainIterator.Linker linker}.
	 * @see ChainIterable
	 */
	public static <E> Iterable<E> chainIterable(E startLink, ChainIterator.Linker<E> linker) {
		return new ChainIterable<E>(startLink, linker);
	}

	/**
	 * Return an iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> compositeIterable(E object, Iterable<? extends E> iterable) {
		return compositeIterable(new Iterable[] { singletonIterable(object), iterable });
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> compositeIterable(Iterable<? extends E> iterable, E object) {
		return compositeIterable(new Iterable[] { iterable, singletonIterable(object) });
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterable
	 */
	public static <E> Iterable<E> compositeIterable(Iterable<? extends E>... iterables) {
		return compositeIterable(Arrays.asList(iterables));
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterable
	 */
	public static <E> Iterable<E> compositeIterable(Iterable<? extends Iterable<? extends E>> iterables) {
		return new CompositeIterable<E>(iterables);
	}

	/**
	 * Return an iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeIterable
	 */
	public static <P, E> Iterable<E> compositeIterable(Iterable<? extends P> parents, Transformer<P, Iterable<? extends E>> childrenTransformer) {
		return compositeIterable(transformationIterable(parents, childrenTransformer));
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified list.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(E object, List<E> list) {
		return compositeListIterable(object, listIterable(list));
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ListIterable<E> compositeListIterable(E object, ListIterable<E> iterable) {
		return compositeListIterable(new ListIterable[] { singletonListIterable(object), iterable });
	}

	/**
	 * Return a list iterable that returns the elements in the specified list
	 * followed by the specified object.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(List<E> list, E object) {
		return compositeListIterable(listIterable(list), object);
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified lists.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(List<E>... lists) {
		return compositeListIterable(Arrays.asList(lists));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified lists.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(List<? extends List<E>> lists) {
		Transformer<List<E>, ListIterable<E>> transformer = listListIterableTransformer();
		return compositeListIterable(transformationListIterable(listIterable(lists), transformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(ListIterable<? extends ListIterable<E>> iterables) {
		return new CompositeListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeListIterable
	 */
	public static <P, E> ListIterable<E> compositeListIterable(ListIterable<? extends P> parents, Transformer<P, ListIterable<E>> childrenTransformer) {
		return compositeListIterable(transformationListIterable(parents, childrenTransformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ListIterable<E> compositeListIterable(ListIterable<E> iterable, E object) {
		return compositeListIterable(new ListIterable[] { iterable, singletonListIterable(object) });
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterable
	 */
	public static <E> ListIterable<E> compositeListIterable(ListIterable<E>... iterables) {
		return compositeListIterable(listIterable(iterables));
	}

	/**
	 * Return an empty iterable.
	 */
	public static <E> Iterable<E> emptyIterable() {
		return EmptyIterable.instance();
	}

	/**
	 * Return an empty list iterable.
	 */
	public static <E> ListIterable<E> emptyListIterable() {
		return EmptyListIterable.instance();
	}

	/**
	 * Return an iterable that will use the specified filter to filter the
	 * elements in the specified iterable.
	 * @see FilteringIterable
	 */
	public static <E> Iterable<E> filteringIterable(Iterable<E> iterable, Filter<E> filter) {
		return new FilteringIterable<E>(iterable, filter);
	}

	/**
	 * Return an iterable that will return the specified root element followed
	 * by its children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterable
	 */
	public static <E> Iterable<E> graphIterable(E root, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterable<E>(root, misterRogers);
	}

	/**
	 * Return an iterable that will return the specified root elements followed
	 * by their children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterable
	 */
	public static <E> Iterable<E> graphIterable(E[] roots, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterable<E>(roots, misterRogers);
	}

	/**
	 * Return an iterable that will return the specified root elements followed
	 * by their children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterable
	 */
	public static <E> Iterable<E> graphIterable(Iterable<E> roots, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterable<E>(roots, misterRogers);
	}

	/**
	 * Return an iterable on the elements in the specified array.
	 */
	public static <E> Iterable<E> iterable(E... array) {
		return iterable(array, 0);
	}

	/**
	 * Return an iterable on the elements in the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> Iterable<E> iterable(E[] array, int start) {
		return iterable(array, start, array.length);
	}

	/**
	 * Return an iterable on the elements in the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> Iterable<E> iterable(E[] array, int start, int end) {
		return new ArrayIterable<E>(array, start, end);
	}

	/**
	 * Return an iterable on the specified queue.
	 * @see Queue
	 */
	public static <E> Iterable<E> iterable(Queue<E> queue) {
		return new QueueIterable<E>(queue);
	}

	/**
	 * Return an iterable on the specified stack.
	 * @see Stack
	 */
	public static <E> Iterable<E> iterable(Stack<E> stack) {
		return new StackIterable<E>(stack);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see LateralIterableWrapper
	 */
	public static <E1, E2> Iterable<E2> lateralIterable(Iterable<E1> iterable) {
		return new LateralIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return a list iterable that converts the specified list iterable's element type.
	 * @see LateralIterableWrapper
	 */
	public static <E1, E2> ListIterable<E2> lateralIterable(ListIterable<E1> iterable) {
		return new LateralListIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return a list iterable for the specified array.
	 */
	public static <E> ListIterable<E> listIterable(E... array) {
		return listIterable(array, 0);
	}

	/**
	 * Return a list iterable for the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> ListIterable<E> listIterable(E[] array, int start) {
		return listIterable(array, start, array.length);
	}

	/**
	 * Return a list iterable for the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> ListIterable<E> listIterable(E[] array, int start, int end) {
		return new ArrayListIterable<E>(array, start, end);
	}

	/**
	 * Return a list iterable for the specified list.
	 */
	public static <E> ListIterable<E> listIterable(List<E> list) {
		return new ListListIterable<E>(list);
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * {@link ListIterable}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<E>, ListIterable<E>> listListIterableTransformer() {
		return LIST_LIST_ITERABLE_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterable}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer LIST_LIST_ITERABLE_TRANSFORMER = new ListListIterableTransformer();
	/* CU private */ static class ListListIterableTransformer<E>
		implements Transformer<List<E>, ListIterable<E>>
	{
		public ListIterable<E> transform(List<E> list) {
			return listIterable(list);
		}
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * read-only {@link ListIterable}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterable<? extends E>> readOnlyListListIterableTransformer() {
		return READ_ONLY_LIST_LIST_ITERABLE_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * read-only {@link ListIterable}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_LIST_ITERABLE_TRANSFORMER = new ReadOnlyListListIterableTransformer();
	/* CU private */ static class ReadOnlyListListIterableTransformer<E>
		implements Transformer<List<? extends E>, ListIterable<? extends E>>
	{
		public ListIterable<? extends E> transform(List<? extends E> list) {
			return listIterable(list);
		}
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements.
	 * @see LiveCloneIterable
	 */
	public static <E> Iterable<E> liveCloneIterable(Collection<? extends E> collection) {
		return new LiveCloneIterable<E>(collection);
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneIterator.Remover remover}.
	 * @see LiveCloneIterable
	 */
	public static <E> Iterable<E> liveCloneIterable(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
		return new LiveCloneIterable<E>(collection, remover);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements.
	 * @see LiveCloneListIterable
	 */
	public static <E> ListIterable<E> liveCloneListIterable(List<? extends E> list) {
		return new LiveCloneListIterable<E>(list);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Mutator mutator}.
	 * @see LiveCloneListIterable
	 */
	public static <E> ListIterable<E> liveCloneListIterable(List<? extends E> list, CloneListIterator.Mutator<E> mutator) {
		return new LiveCloneListIterable<E>(list, mutator);
	}

	/**
	 * Construct an iterable that wraps the specified iterable and returns a
	 * {@link PeekableIterator}.
	 * @see PeekableIterable
	 */
	public static <E> PeekableIterable<E> peekableIterable(Iterable<? extends E> iterable) {
		return new PeekableIterable<E>(iterable);
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified list.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E, T extends E> ListIterable<E> readOnlyCompositeListIterable(E object, List<T> list) {
		return readOnlyCompositeListIterable(object, listIterable(list));
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see ReadOnlyCompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ListIterable<E> readOnlyCompositeListIterable(E object, ListIterable<? extends E> iterable) {
		return readOnlyCompositeListIterable(new ListIterable[] { singletonListIterable(object), iterable });
	}

	/**
	 * Return a list iterable that returns the elements in the specified list
	 * followed by the specified object.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ListIterable<E> readOnlyCompositeListIterable(List<E> list, E object) {
		return readOnlyCompositeListIterable(listIterable(list), object);
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified lists.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ListIterable<E> readOnlyCompositeListIterable(List<E>... lists) {
		return readOnlyCompositeListIterable(Arrays.asList(lists));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified lists.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ListIterable<E> readOnlyCompositeListIterable(List<? extends List<? extends E>> lists) {
		Transformer<List<? extends E>, ListIterable<? extends E>> transformer = readOnlyListListIterableTransformer();
		return readOnlyCompositeListIterable(transformationListIterable(listIterable(lists), transformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ListIterable<E> readOnlyCompositeListIterable(ListIterable<? extends ListIterable<? extends E>> iterables) {
		return new ReadOnlyCompositeListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <P, E> ListIterable<E> readOnlyCompositeListIterable(ListIterable<? extends P> parents, Transformer<P, ListIterable<? extends E>> childrenTransformer) {
		return readOnlyCompositeListIterable(transformationListIterable(parents, childrenTransformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see ReadOnlyCompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ListIterable<E> readOnlyCompositeListIterable(ListIterable<? extends E> iterable, E object) {
		return readOnlyCompositeListIterable(new ListIterable[] { iterable, singletonListIterable(object) });
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ListIterable<E> readOnlyCompositeListIterable(ListIterable<? extends E>... iterables) {
		return readOnlyCompositeListIterable(listIterable(iterables));
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyIterable
	 */
	public static <E> Iterable<E> readOnlyIterable(Iterable<? extends E> iterable) {
		return new ReadOnlyIterable<E>(iterable);
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyListIterable
	 */
	public static <E> ListIterable<E> readOnlyListIterable(ListIterable<? extends E> iterable) {
		return new ReadOnlyListIterable<E>(iterable);
	}

	/**
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<E>> Iterable<List<E>> simultaneousIterable(I... iterables) {
		return new SimultaneousIterable<E>(iterables);
	}

	/**
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<E>> Iterable<List<E>> simultaneousIterable(Iterable<I> iterables) {
		return new SimultaneousIterable<E>(iterables);
	}

	/**
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<E>> Iterable<List<E>> simultaneousIterable(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousIterable<E>(iterables, iterablesSize);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> ListIterable<List<E>> simultaneousListIterable(I... iterables) {
		return new SimultaneousListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> ListIterable<List<E>> simultaneousListIterable(Iterable<I> iterables) {
		return new SimultaneousListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> ListIterable<List<E>> simultaneousListIterable(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousListIterable<E>(iterables, iterablesSize);
	}

	/**
	 * Return an iterable that returns only the single,
	 * specified object.
	 * @see SingleElementIterable
	 */
	public static <E> Iterable<E> singletonIterable(E value) {
		return new SingleElementIterable<E>(value);
	}

	/**
	 * Return a list iterable that returns only the single,
	 * specified object.
	 * @see SingleElementListIterable
	 */
	public static <E> ListIterable<E> singletonListIterable(E value) {
		return new SingleElementListIterable<E>(value);
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements.
	 * @see SnapshotCloneIterable
	 */
	public static <E> Iterable<E> snapshotCloneIterable(Collection<? extends E> collection) {
		return new SnapshotCloneIterable<E>(collection);
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneIterator.Remover remover}.
	 * @see LiveCloneIterable
	 */
	public static <E> Iterable<E> snapshotCloneIterable(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
		return new SnapshotCloneIterable<E>(collection, remover);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements.
	 * @see SnapshotCloneListIterable
	 */
	public static <E> ListIterable<E> snapshotCloneIterable(List<? extends E> list) {
		return new SnapshotCloneListIterable<E>(list);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Mutator mutator}.
	 * @see SnapshotCloneListIterable
	 */
	public static <E> ListIterable<E> snapshotCloneIterable(List<? extends E> list, CloneListIterator.Mutator<E> mutator) {
		return new SnapshotCloneListIterable<E>(list, mutator);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SubIterableWrapper
	 */
	public static <E1, E2 extends E1> Iterable<E2> subIterable(Iterable<E1> iterable) {
		return new SubIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterable that converts the specified list's element type.
	 * @see SubListIterableWrapper
	 */
	public static <E1, E2 extends E1> ListIterable<E2> subListIterable(List<E1> list) {
		return new SubListIterableWrapper<E1, E2>(list);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SubListIterableWrapper
	 */
	public static <E1, E2 extends E1> ListIterable<E2> subListIterable(ListIterable<E1> iterable) {
		return new SubListIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SuperIterableWrapper
	 */
	public static <E> Iterable<E> superIterable(Iterable<? extends E> iterable) {
		return new SuperIterableWrapper<E>(iterable);
	}

	/**
	 * Return an iterable that converts the specified list's element type.
	 * @see SuperListIterableWrapper
	 */
	public static <E> ListIterable<E> superListIterable(List<? extends E> list) {
		return new SuperListIterableWrapper<E>(list);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SuperListIterableWrapper
	 */
	public static <E> ListIterable<E> superListIterable(ListIterable<? extends E> iterable) {
		return new SuperListIterableWrapper<E>(iterable);
	}

	/**
	 * Return an iterable that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationIterable
	 */
	public static <E1, E2> Iterable<E2> transformationIterable(Iterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		return new TransformationIterable<E1, E2>(iterable, transformer);
	}

	/**
	 * Return an iterable that will use the specified transformer to transform the
	 * elements in the specified list.
	 * @see TransformationListIterable
	 */
	public static <E1, E2, T extends E1> ListIterable<E2> transformationListIterable(List<T> list, Transformer<E1, ? extends E2> transformer) {
		return new TransformationListIterable<E1, E2>(list, transformer);
	}

	/**
	 * Return an iterable that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationListIterable
	 */
	public static <E1, E2> ListIterable<E2> transformationListIterable(ListIterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		return new TransformationListIterable<E1, E2>(iterable, transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified root and transformer.
	 * @see TreeIterable
	 */
	public static <E> Iterable<E> treeIterable(E root, Transformer<E, Iterator<? extends E>> transformer) {
		return treeIterable(new SingleElementIterable<E>(root), transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterable
	 */
	public static <E> Iterable<E> treeIterable(E[] roots, Transformer<E, Iterator<? extends E>> transformer) {
		return treeIterable(new ArrayIterable<E>(roots), transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterable
	 */
	public static <E> Iterable<E> treeIterable(Iterable<? extends E> roots, Transformer<E, Iterator<? extends E>> transformer) {
		return new TreeIterable<E>(roots, transformer);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private IterableTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
