/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.closure.DisabledClosure;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Iterable} utility methods.
 * @see org.eclipse.jpt.common.utility.internal.ArrayTools
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
	 * Return whether the specified iterable is <code>null</code>
	 * or, if it is not <code>null</code>, contains a <code>null</code>.
	 * Convenient for argument checking.
	 */
	public static boolean isOrContainsNull(Iterable<?> iterable) {
		return (iterable == null) || contains(iterable, null);
	}

	/**
	 * Return whether the specified array contains a <code>null</code>.
	 */
	public static boolean containsNull(Iterable<?> iterable) {
		return contains(iterable, null);
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
	 * Execute the specified closure for each element in the specified iterable.
	 */
	public static <E> void execute(Iterable<? extends E> iterable, Closure<E> closure) {
		IteratorTools.execute(iterable.iterator(), closure);
	}

	/**
	 * Execute the specified closure for each element in the specified iterable.
	 * If the closure throws an exception for an element, the exception will be
	 * handled by the specified exception handler and processing of the
	 * remaining elements will continue.
	 */
	public static <E> void execute(Iterable<? extends E> iterable, Closure<E> closure, ExceptionHandler exceptionHandler) {
		IteratorTools.execute(iterable.iterator(), closure, exceptionHandler);
	}

	/**
	 * Execute the specified closure for each element in the specified iterable.
	 */
	public static <E> void execute(Iterable<? extends E> iterable, InterruptibleClosure<E> closure) throws InterruptedException {
		IteratorTools.execute(iterable.iterator(), closure);
	}

	/**
	 * Execute the specified closure for each element in the specified iterable.
	 * If the closure throws an exception (other than an
	 * {@link InterruptedException}) for an element, the exception will be
	 * handled by the specified exception handler and processing of the
	 * remaining elements will continue.
	 */
	public static <E> void execute(Iterable<? extends E> iterable, InterruptibleClosure<E> closure, ExceptionHandler exceptionHandler) throws InterruptedException {
		IteratorTools.execute(iterable.iterator(), closure, exceptionHandler);
	}

	/**
	 * Return the first element corresponding in the specified iterable.
	 */
	public static <E> E first(Iterable<? extends E> iterable) {
		return iterable.iterator().next();
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
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterable, starting at the specified index;
	 * return -1 if there is no such element.
	 */
	public static int indexOf(Iterable<?> iterable, Object value, int startIndex) {
		return IteratorTools.indexOf(iterable.iterator(), value, startIndex);
	}

	/**
	 * Return whether the specified iterable is empty.
	 * (Shortcut the iterator rather than calculating the entire size.)
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return IteratorTools.isEmpty(iterable.iterator());
	}

	/**
	 * Return whether the specified iterable is <em>not</em> empty.
	 * (Shortcut the iterator rather than calculating the entire size.)
	 */
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return IteratorTools.isNotEmpty(iterable.iterator());
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
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterable, starting at the specified index;
	 * return -1 if there is no such element.
	 */
	public static int lastIndexOf(Iterable<?> iterable, Object value, int startIndex) {
		return IteratorTools.lastIndexOf(iterable.iterator(), value, startIndex);
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
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<? extends E> iterable) {
		return sort(iterable, null);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<? extends E> iterable, int iterableSize) {
		return sort(iterable, null, iterableSize);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 */
	public static <E> Iterable<E> sort(Iterable<? extends E> iterable, Comparator<? super E> comparator) {
		return ListTools.sort(ListTools.list(iterable), comparator);
	}

	/**
	 * Return an iterable containing the sorted elements of the specified iterable.
	 * The specified iterable size is a performance hint.
	 */
	public static <E> Iterable<E> sort(Iterable<? extends E> iterable, Comparator<? super E> comparator, int iterableSize) {
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
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<? extends E>> SimultaneousIterable<E> align(I... iterables) {
		return align(iterable(iterables), iterables.length);
	}

	/**
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<? extends E>> SimultaneousIterable<E> align(Iterable<I> iterables) {
		return new SimultaneousIterable<E>(iterables);
	}

	/**
	 * Return an iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterable
	 */
	public static <E, I extends Iterable<? extends E>> SimultaneousIterable<E> align(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousIterable<E>(iterables, iterablesSize);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> SimultaneousListIterable<E> alignList(I... iterables) {
		return alignList(iterable(iterables), iterables.length);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> SimultaneousListIterable<E> alignList(Iterable<I> iterables) {
		return new SimultaneousListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterable
	 */
	public static <E, I extends ListIterable<E>> SimultaneousListIterable<E> alignList(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousListIterable<E>(iterables, iterablesSize);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see LateralIterableWrapper
	 */
	public static <E1, E2> LateralIterableWrapper<E1, E2> cast(Iterable<E1> iterable) {
		return new LateralIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return a list iterable that converts the specified list iterable's element type.
	 * @see LateralIterableWrapper
	 */
	public static <E1, E2> LateralListIterableWrapper<E1, E2> cast(ListIterable<E1> iterable) {
		return new LateralListIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SubIterableWrapper
	 */
	public static <E1, E2 extends E1> SubIterableWrapper<E1, E2> downCast(Iterable<E1> iterable) {
		return new SubIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SubListIterableWrapper
	 */
	public static <E1, E2 extends E1> SubListIterableWrapper<E1, E2> downCast(ListIterable<E1> iterable) {
		return new SubListIterableWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SuperIterableWrapper
	 */
	public static <E> SuperIterableWrapper<E> upCast(Iterable<? extends E> iterable) {
		return new SuperIterableWrapper<E>(iterable);
	}

	/**
	 * Return an iterable that converts the specified iterable's element type.
	 * @see SuperListIterableWrapper
	 */
	public static <E> SuperListIterableWrapper<E> upCast(ListIterable<? extends E> iterable) {
		return new SuperListIterableWrapper<E>(iterable);
	}

	/**
	 * Return a chain iterable that starts with the specified element and uses
	 * the specified {@link Transformer transformer}.
	 * @see ChainIterable
	 */
	public static <E> ChainIterable<E> chainIterable(E first, Transformer<? super E, ? extends E> transformer) {
		return new ChainIterable<E>(first, transformer);
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements.
	 * @see LiveCloneIterable
	 */
	public static <E> LiveCloneIterable<E> cloneLive(Collection<? extends E> collection) {
		return cloneLive(collection, DisabledClosure.instance());
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements and uses the specified remove closure.
	 * @see LiveCloneIterable
	 */
	public static <E> LiveCloneIterable<E> cloneLive(Collection<? extends E> collection, Closure<? super E> removeClosure) {
		return new LiveCloneIterable<E>(collection, removeClosure);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements.
	 * @see LiveCloneListIterable
	 */
	public static <E> LiveCloneListIterable<E> cloneLive(List<? extends E> list) {
		return cloneLive(list, CloneListIterator.Adapter.ReadOnly.<E>instance());
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Adapter adapter}.
	 * @see LiveCloneListIterable
	 */
	public static <E> LiveCloneListIterable<E> cloneLive(List<? extends E> list, CloneListIterator.Adapter<E> adapter) {
		return new LiveCloneListIterable<E>(list, adapter);
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements.
	 * @see SnapshotCloneIterable
	 */
	public static <E> SnapshotCloneIterable<E> cloneSnapshot(Collection<? extends E> collection) {
		return cloneSnapshot(collection, DisabledClosure.instance());
	}

	/**
	 * Return an iterable that clones the specified collection before returning
	 * elements and uses the specified remove closure.
	 * @see LiveCloneIterable
	 */
	public static <E> SnapshotCloneIterable<E> cloneSnapshot(Collection<? extends E> collection, Closure<? super E> removeClosure) {
		return new SnapshotCloneIterable<E>(collection, removeClosure);
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements.
	 * @see SnapshotCloneListIterable
	 */
	public static <E> SnapshotCloneListIterable<E> cloneSnapshot(List<? extends E> list) {
		return cloneSnapshot(list, CloneListIterator.Adapter.ReadOnly.<E>instance());
	}

	/**
	 * Return a list iterable that clones the specified collection before returning
	 * elements and uses the specified {@link org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Adapter adapter}.
	 * @see SnapshotCloneListIterable
	 */
	public static <E> SnapshotCloneListIterable<E> cloneSnapshot(List<? extends E> list, CloneListIterator.Adapter<E> adapter) {
		return new SnapshotCloneListIterable<E>(list, adapter);
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterable<E> add(Iterable<? extends E> iterable, E object) {
		return concatenate(iterable, singletonIterable(object));
	}

	/**
	 * Return an iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterable<E> insert(E object, Iterable<? extends E> iterable) {
		return concatenate(singletonIterable(object), iterable);
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterable
	 */
	public static <E> CompositeIterable<E> concatenate(Iterable<? extends E>... iterables) {
		return concatenate(Arrays.asList(iterables));
	}

	/**
	 * Return an iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterable
	 */
	public static <E> CompositeIterable<E> concatenate(Iterable<? extends Iterable<? extends E>> iterables) {
		return new CompositeIterable<E>(iterables);
	}

	/**
	 * Return an iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeIterable
	 */
	public static <P, E> CompositeIterable<E> children(Iterable<? extends P> parents, Transformer<? super P, ? extends Iterable<? extends E>> childrenTransformer) {
		return concatenate(transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterable<E> add(ListIterable<E> iterable, E object) {
		return concatenate(iterable, singletonListIterable(object));
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterable<E> insert(E object, ListIterable<E> iterable) {
		return concatenate(singletonListIterable(object), iterable);
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterable
	 */
	public static <E> CompositeListIterable<E> concatenate(ListIterable<E>... iterables) {
		return concatenate(listIterable(iterables));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterable
	 */
	public static <E> CompositeListIterable<E> concatenate(ListIterable<? extends ListIterable<E>> iterables) {
		return new CompositeListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeListIterable
	 */
	public static <P, E> CompositeListIterable<E> children(ListIterable<? extends P> parents, Transformer<? super P, ? extends ListIterable<E>> childrenTransformer) {
		return concatenate(transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see ReadOnlyCompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterable<E> addReadOnly(ListIterable<? extends E> iterable, E object) {
		return concatenateReadOnly(iterable, singletonListIterable(object));
	}

	/**
	 * Return a list iterable that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see ReadOnlyCompositeListIterable
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterable<E> insertReadOnly(E object, ListIterable<? extends E> iterable) {
		return concatenateReadOnly(singletonListIterable(object), iterable);
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ReadOnlyCompositeListIterable<E> concatenateReadOnly(ListIterable<? extends E>... iterables) {
		return concatenateReadOnly(listIterable(iterables));
	}

	/**
	 * Return a list iterable that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <E> ReadOnlyCompositeListIterable<E> concatenateReadOnly(ListIterable<? extends ListIterable<? extends E>> iterables) {
		return new ReadOnlyCompositeListIterable<E>(iterables);
	}

	/**
	 * Return a list iterable on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see ReadOnlyCompositeListIterable
	 */
	public static <P, E> ReadOnlyCompositeListIterable<E> readOnlyChildren(ListIterable<? extends P> parents, Transformer<? super P, ? extends ListIterable<? extends E>> childrenTransformer) {
		return concatenateReadOnly(transform(parents, childrenTransformer));
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
	 * Return an iterable that will use the specified predicate to filter the
	 * elements in the specified iterable.
	 * @see FilteringIterable
	 */
	public static <E> FilteringIterable<E> filter(Iterable<? extends E> iterable, Predicate<? super E> predicate) {
		return new FilteringIterable<E>(iterable, predicate);
	}

	/**
	 * Return an iterable that will return only the non-<code>null</code>
	 * elements in the specified iterable.
	 * @see FilteringIterable
	 */
	public static <E> FilteringIterable<E> removeNulls(Iterable<? extends E> iterable) {
		return filter(iterable, PredicateTools.isNotNull());
	}

	/**
	 * Return an iterable that will return the specified root element followed
	 * by its children etc. as determined by the specified transformer.
	 * @see GraphIterable
	 */
	public static <E> GraphIterable<E> graphIterable(E root, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return graphIterable(singletonIterable(root), transformer);
	}

	/**
	 * Return an iterable that will return the specified root elements followed
	 * by their children etc. as determined by the specified transformer.
	 * @see GraphIterable
	 */
	public static <E> GraphIterable<E> graphIterable(E[] roots, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return graphIterable(Arrays.asList(roots), transformer);
	}

	/**
	 * Return an iterable that will return the specified root elements followed
	 * by their children etc. as determined by the specified transformer.
	 * @see GraphIterable
	 */
	public static <E> GraphIterable<E> graphIterable(Iterable<? extends E> roots, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return new GraphIterable<E>(roots, transformer);
	}

	/**
	 * Return an iterable on the elements in the specified array.
	 */
	public static <E> ArrayIterable<E> iterable(E... array) {
		return iterable(array, 0);
	}

	/**
	 * Return an iterable on the elements in the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> ArrayIterable<E> iterable(E[] array, int start) {
		return iterable(array, start, array.length);
	}

	/**
	 * Return an iterable on the elements in the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> ArrayIterable<E> iterable(E[] array, int start, int end) {
		return new ArrayIterable<E>(array, start, end);
	}

	/**
	 * Return an iterable on the specified queue.
	 * @see Queue
	 */
	public static <E> QueueIterable<E> iterable(Queue<? extends E> queue) {
		return new QueueIterable<E>(queue);
	}

	/**
	 * Return an iterable on the specified stack.
	 * @see Stack
	 */
	public static <E> StackIterable<E> iterable(Stack<? extends E> stack) {
		return new StackIterable<E>(stack);
	}

	/**
	 * Return a list iterable for the specified array.
	 */
	public static <E> ArrayListIterable<E> listIterable(E... array) {
		return listIterable(array, 0);
	}

	/**
	 * Return a list iterable for the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> ArrayListIterable<E> listIterable(E[] array, int start) {
		return listIterable(array, start, array.length);
	}

	/**
	 * Return a list iterable for the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> ArrayListIterable<E> listIterable(E[] array, int start, int end) {
		return new ArrayListIterable<E>(array, start, end);
	}

	/**
	 * Return a list iterable for the specified list.
	 */
	public static <E> ListListIterable<E> listIterable(List<E> list) {
		return new ListListIterable<E>(list);
	}

	/**
	 * Construct an iterable that wraps the specified iterable and returns a
	 * {@link org.eclipse.jpt.common.utility.internal.iterator.PeekableIterator}.
	 * @see PeekableIterable
	 */
	public static <E> PeekableIterable<E> peekable(Iterable<? extends E> iterable) {
		return new PeekableIterable<E>(iterable);
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyIterable
	 */
	public static <E> ReadOnlyIterable<E> readOnly(Iterable<? extends E> iterable) {
		return new ReadOnlyIterable<E>(iterable);
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyListIterable
	 */
	public static <E> ReadOnlyListIterable<E> readOnly(ListIterable<? extends E> iterable) {
		return new ReadOnlyListIterable<E>(iterable);
	}

	/**
	 * Return an iterable that returns only the single,
	 * specified object.
	 * @see SingleElementIterable
	 */
	public static <E> SingleElementIterable<E> singletonIterable(E value) {
		return new SingleElementIterable<E>(value);
	}

	/**
	 * Return a list iterable that returns only the single,
	 * specified object.
	 * @see SingleElementListIterable
	 */
	public static <E> SingleElementListIterable<E> singletonListIterable(E value) {
		return new SingleElementListIterable<E>(value);
	}

	/**
	 * Return an iterable that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationIterable
	 */
	public static <E1, E2> TransformationIterable<E1, E2> transform(Iterable<? extends E1> iterable, Transformer<? super E1, ? extends E2> transformer) {
		return new TransformationIterable<E1, E2>(iterable, transformer);
	}

	/**
	 * Return an iterable that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationListIterable
	 */
	public static <E1, E2, T extends E1> TransformationListIterable<E1, E2> transform(ListIterable<T> iterable, Transformer<? super E1, ? extends E2> transformer) {
		return new TransformationListIterable<E1, E2>(iterable, transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified root and transformer.
	 * @see TreeIterable
	 */
	public static <E> TreeIterable<E> treeIterable(E root, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return treeIterable(singletonIterable(root), transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterable
	 */
	public static <E> TreeIterable<E> treeIterable(E[] roots, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return treeIterable(iterable(roots), transformer);
	}

	/**
	 * Construct an iterable that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterable
	 */
	public static <E> TreeIterable<E> treeIterable(Iterable<? extends E> roots, Transformer<? super E, ? extends Iterable<? extends E>> transformer) {
		return new TreeIterable<E>(roots, transformer);
	}


	// ********** transformers **********

	/**
	 * Return a transformer that transforms an {@link Iterable} into its
	 * corresponding {@link Iterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<Iterable<? extends E>, Iterator<E>> iteratorTransformer() {
		return ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms an {@link Iterable} into its
	 * corresponding {@link Iterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer ITERATOR_TRANSFORMER = new IteratorTransformer();

	/**
	 * A transformer that transforms an {@link Iterable} into its
	 * corresponding {@link Iterator}.
	 */
	public static class IteratorTransformer<E>
		implements Transformer<Iterable<? extends E>, Iterator<E>>
	{
		@SuppressWarnings("unchecked")
		public Iterator<E> transform(Iterable<? extends E> iterable) {
			return (Iterator<E>) iterable.iterator();
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Return a transformer that transforms a {@link ListIterable} into its
	 * corresponding {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<ListIterable<E>, ListIterator<E>> listIteratorTransformer() {
		return ListIterable.TRANSFORMER;
	}

	/**
	 * Return a transformer that transforms a {@link ListIterable} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<ListIterable<? extends E>, ListIterator<? extends E>> readOnlyListIteratorTransformer() {
		return READ_ONLY_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link ListIterable} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_ITERATOR_TRANSFORMER = new ReadOnlyListIteratorTransformer();

	/**
	 * A transformer that transforms a {@link ListIterable} into a
	 * <em>read-only</em> {@link ListIterator}.
	 */
	public static class ReadOnlyListIteratorTransformer<E>
		implements Transformer<ListIterable<? extends E>, ListIterator<? extends E>>
	{
		public ListIterator<? extends E> transform(ListIterable<? extends E> iterable) {
			return IteratorTools.readOnly(iterable.iterator());
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
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
