/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.command.InterruptibleParameterizedCommand;
import org.eclipse.jpt.common.utility.command.ParameterizedCommand;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Iterator} utility methods.
 * @see org.eclipse.jpt.common.utility.internal.ArrayTools
 * @see CollectionTools
 * @see IterableTools
 * @see ListTools
 */
public final class IteratorTools {
	/**
	 * Return a bag corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator) {
		return CollectionTools.bag(iterator);
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> bag(Iterator<? extends E> iterator, int iteratorSize) {
		return CollectionTools.bag(iterator, iteratorSize);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator) {
		return CollectionTools.collection(iterator);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> HashBag<E> collection(Iterator<? extends E> iterator, int iteratorSize) {
		return CollectionTools.collection(iterator, iteratorSize);
	}

	/**
	 * Return whether the specified iterator contains the
	 * specified element.
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

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified collection.
	 */
	public static boolean containsAll(Iterator<?> iterator, Collection<?> collection) {
		return CollectionTools.set(iterator).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified collection.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Collection<?> collection) {
		return CollectionTools.set(iterator, iteratorSize).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified iterable.
	 */
	public static boolean containsAll(Iterator<?> iterator, Iterable<?> iterable) {
		return CollectionTools.containsAll(CollectionTools.set(iterator), iterable);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified iterable.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Iterable<?> iterable) {
		return CollectionTools.containsAll(CollectionTools.set(iterator, iteratorSize), iterable);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the
	 * elements in the specified iterator 2.
	 */
	public static boolean containsAll(Iterator<?> iterator1, Iterator<?> iterator2) {
		return CollectionTools.containsAll(CollectionTools.set(iterator1), iterator2);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the
	 * elements in the specified iterator 2.
	 * The specified iterator 1 size is a performance hint.
	 */
	public static boolean containsAll(Iterator<?> iterator1, int iterator1Size, Iterator<?> iterator2) {
		return CollectionTools.containsAll(CollectionTools.set(iterator1, iterator1Size), iterator2);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified array.
	 */
	public static boolean containsAll(Iterator<?> iterator, Object... array) {
		return CollectionTools.containsAll(CollectionTools.set(iterator), array);
	}

	/**
	 * Return whether the specified iterator contains all of the
	 * elements in the specified array.
	 * The specified iterator size is a performance hint.
	 */
	public static boolean containsAll(Iterator<?> iterator, int iteratorSize, Object... array) {
		return CollectionTools.containsAll(CollectionTools.set(iterator, iteratorSize), array);
	}

	/**
	 * Return whether the specified iterators do not return the same elements
	 * in the same order.
	 */
	public static boolean elementsAreDifferent(Iterator<?> iterator1, Iterator<?> iterator2) {
		return ! elementsAreEqual(iterator1, iterator2);
	}

	/**
	 * Return whether the specified iterators return equal elements
	 * in the same order.
	 */
	public static boolean elementsAreEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (ObjectTools.notEquals(iterator1.next(), iterator2.next())) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}

	/**
	 * Return whether the specified iterators return the same elements.
	 */
	public static boolean elementsAreIdentical(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (iterator1.next() != iterator2.next()) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}

	/**
	 * Return whether the specified iterators do <em>not</em> return the same
	 * elements.
	 */
	public static boolean elementsAreNotIdentical(Iterator<?> iterator1, Iterator<?> iterator2) {
		return ! elementsAreIdentical(iterator1, iterator2);
	}

	/**
	 * Execute the specified command for each element in the specified iterator.
	 */
	public static <E> void execute(Iterator<? extends E> iterator, ParameterizedCommand<E> command) {
		while (iterator.hasNext()) {
			command.execute(iterator.next());
		}
	}

	/**
	 * Execute the specified command for each element in the specified iterator.
	 */
	public static <E> void execute(Iterator<? extends E> iterator, InterruptibleParameterizedCommand<E> command) throws InterruptedException {
		while (iterator.hasNext()) {
			command.execute(iterator.next());
		}
	}

	/**
	 * Return the element corresponding to the specified index
	 * in the specified iterator.
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

	/**
	 * Return a hash code corresponding to the elements in the specified iterator.
	 */
	public static int hashCode(Iterator<?> iterator) {
		int hash = 1;
		while (iterator.hasNext()) {
			Object next = iterator.next();
			hash = 31 * hash + ((next == null) ? 0 : next.hashCode());
		}
		return hash;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterator;
	 * return -1 if there is no such element.
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

	/**
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterator;
	 * return -1 if there is no such element.
	 */
	public static int lastIndexOf(Iterator<?> iterator, Object value) {
		int last = -1;
		if (value == null) {
			for (int i = 0; iterator.hasNext(); i++) {
				if (iterator.next() == null) {
					last = i;
				}
			}
		} else {
			for (int i = 0; iterator.hasNext(); i++) {
				if (value.equals(iterator.next())) {
					last = i;
				}
			}
		}
		return last;
	}

	/**
	 * Return the specified iterator's last element.
	 * @exception java.util.NoSuchElementException iterator is empty.
	 */
	public static <E> E last(Iterator<E> iterator) {
		E last;
		do {
			last = iterator.next();
		} while (iterator.hasNext());
		return last;
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 */
	public static <E> ArrayList<E> list(Iterator<? extends E> iterator) {
		return ListTools.list(iterator);
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ArrayList<E> list(Iterator<? extends E> iterator, int iteratorSize) {
		return ListTools.list(iterator, iteratorSize);
	}

	/**
	 * Return the number of elements returned by the specified iterator.
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
	 * Return whether the specified iterator is empty
	 * (Shortcuts the iterator rather than calculating the entire size)
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return ! iterator.hasNext();
	}

	/**
	 * Return the iterator after it has been "sorted".
	 */
	public static <E extends Comparable<? super E>> ListIterator<E> sort(Iterator<? extends E> iterator) {
		return sort(iterator, null);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * The specified iterator size is a performance hint.
	 */
	public static <E extends Comparable<? super E>> ListIterator<E> sort(Iterator<? extends E> iterator, int iteratorSize) {
		return sort(iterator, null, iteratorSize);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 */
	public static <E> ListIterator<E> sort(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return ListTools.sort(ListTools.list(iterator), comparator).listIterator();
	}

	/**
	 * Return the iterator after it has been "sorted".
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ListIterator<E> sort(Iterator<? extends E> iterator, Comparator<? super E> comparator, int iteratorSize) {
		return ListTools.sort(ListTools.list(iterator, iteratorSize), comparator).listIterator();
	}

	/**
	 * Convert the specified iterator into an array.
	 * @see Collection#toArray()
	 */
	public static Object[] toArray(Iterator<?> iterator) {
		return list(iterator).toArray();
	}

	/**
	 * Convert the specified iterator into an array.
	 * The specified iterator size is a performance hint.
	 * @see Collection#toArray()
	 */
	public static Object[] toArray(Iterator<?> iterator, int iteratorSize) {
		return list(iterator, iteratorSize).toArray();
	}

	/**
	 * Convert the specified iterator into an array.
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] toArray(Iterator<? extends E> iterator, E[] array) {
		return list(iterator).toArray(array);
	}

	/**
	 * Convert the specified iterator into an array.
	 * The specified iterator size is a performance hint.
	 * @see Collection#toArray(Object[])
	 */
	public static <E> E[] toArray(Iterator<? extends E> iterator, int iteratorSize, E[] array) {
		return list(iterator, iteratorSize).toArray(array);
	}


	// ********** factory methods **********

	/**
	 * Return a chain iterator that starts with the specified element and uses
	 * the specified {@link ChainIterator.Linker linker}.
	 * @see ChainIterator
	 */
	public static <E> ChainIterator<E> chainIterator(E startLink, ChainIterator.Linker<E> linker) {
		return new ChainIterator<E>(startLink, linker);
	}

	/**
	 * Return an iterator that clones the specified collection before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> cloneIterator(Collection<? extends E> collection) {
		return new CloneIterator<E>(collection);
	}

	/**
	 * Return an iterator that clones the specified collection before returning
	 * elements and uses the specified {@link CloneIterator.Remover remover}.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> cloneIterator(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
		return new CloneIterator<E>(collection, remover);
	}

	/**
	 * Return an iterator that clones the specified array before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> cloneIterator(E[] array) {
		return new CloneIterator<E>(array);
	}

	/**
	 * Return an iterator that clones the specified array before returning
	 * elements and uses the specified {@link CloneIterator.Remover remover}.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> cloneIterator(E[] array, CloneIterator.Remover<E> remover) {
		return new CloneIterator<E>(array, remover);
	}

	/**
	 * Return an iterator that clones the specified list before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> cloneListIterator(List<? extends E> list) {
		return new CloneListIterator<E>(list);
	}

	/**
	 * Return an iterator that clones the specified list before returning
	 * elements and uses the specified {@link CloneListIterator.Mutator mutator}.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> cloneListIterator(List<? extends E> list, CloneListIterator.Mutator<E> mutator) {
		return new CloneListIterator<E>(list, mutator);
	}

	/**
	 * Return an iterator that clones the specified array before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> cloneListIterator(E[] array) {
		return new CloneListIterator<E>(array);
	}

	/**
	 * Return an iterator that clones the specified array before returning
	 * elements and uses the specified {@link CloneListIterator.Mutator mutator}.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> cloneListIterator(E[] array, CloneListIterator.Mutator<E> mutator) {
		return new CloneListIterator<E>(array, mutator);
	}

	/**
	 * Return an iterator that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(E object, Iterable<? extends E> iterable) {
		return compositeIterator(object, iterable.iterator());
	}

	/**
	 * Return an iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see CompositeIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterator<E> compositeIterator(E object, Iterator<? extends E> iterator) {
		return compositeIterator(new Iterator[] { singletonIterator(object), iterator });
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(Iterable<? extends E> iterable, E object) {
		return compositeIterator(iterable.iterator(), object);
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(Iterable<? extends E>... iterables) {
		return compositeIterator(Arrays.asList(iterables));
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterables.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(Iterable<? extends Iterable<? extends E>> iterables) {
		Transformer<Iterable<? extends E>, Iterator<? extends E>> transformer = iterableIteratorTransformer();
		return compositeIterator(transform(iterables.iterator(), transformer));
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see CompositeIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterator<E> compositeIterator(Iterator<? extends E> iterator, E object) {
		return compositeIterator(new Iterator[] { iterator, singletonIterator(object) });
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(Iterator<? extends E>... iterators) {
		return compositeIterator(iterator(iterators));
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> compositeIterator(Iterator<? extends Iterator<? extends E>> iterators) {
		return new CompositeIterator<E>(iterators);
	}

	/**
	 * Return an iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeIterator
	 */
	public static <P, E> CompositeIterator<E> compositeIterator(Iterator<? extends P> parents, Transformer<P, Iterator<? extends E>> childrenTransformer) {
		return compositeIterator(transform(parents, childrenTransformer));
	}

	/**
	 * Return an iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeIterator
	 */
	public static <P, E> CompositeIterator<E> compositeIterator(Iterable<? extends P> parents, Transformer<P, Iterable<? extends E>> childrenTransformer) {
		return compositeIterator(IterableTools.transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified list.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(E object, List<E> list) {
		return compositeListIterator(object, list.listIterator());
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(E object, ListIterable<E> iterable) {
		return compositeListIterator(object, iterable.iterator());
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see CompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterator<E> compositeListIterator(E object, ListIterator<E> iterator) {
		return compositeListIterator(new ListIterator[] { singletonListIterator(object), iterator });
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified lists.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(List<? extends List<E>> lists) {
		Transformer<List<E>, ListIterator<E>> transformer = listListIteratorTransformer();
		return compositeListIterator(transform(lists.listIterator(), transformer));
	}

	/**
	 * Return a list iterator that returns the elements in the specified list
	 * followed by the specified object.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(List<E> list, E object) {
		return compositeListIterator(list.listIterator(), object);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified lists.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(List<E>... lists) {
		return compositeListIterator(Arrays.asList(lists));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterable<E> iterable, E object) {
		return compositeListIterator(iterable.iterator(), object);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterable<E>... iterables) {
		return compositeListIterator(IterableTools.listIterable(iterables));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterables.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterable<? extends ListIterable<E>> iterables) {
		Transformer<ListIterable<E>, ListIterator<E>> transformer = listIterableListIteratorTransformer();
		return compositeListIterator(transform(iterables.iterator(), transformer));
	}

	/**
	 * Return a list iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeListIterator
	 */
	public static <P, E> CompositeListIterator<E> compositeListIterator(ListIterable<? extends P> parents, Transformer<P, ListIterable<E>> childrenTransformer) {
		return compositeListIterator(IterableTools.transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see CompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterator<E> iterator, E object) {
		return compositeListIterator(new ListIterator[] { iterator, singletonListIterator(object) });
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterator<E>... iterators) {
		return compositeListIterator(listIterator(iterators));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> compositeListIterator(ListIterator<? extends ListIterator<E>> iterators) {
		return new CompositeListIterator<E>(iterators);
	}

	/**
	 * Return a list iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeListIterator
	 */
	public static <P, E> CompositeListIterator<E> compositeListIterator(ListIterator<? extends P> parents, Transformer<P, ListIterator<E>> childrenTransformer) {
		return compositeListIterator(transform(parents, childrenTransformer));
	}

	/**
	 * Return an empty iterator.
	 */
	public static <E> Iterator<E> emptyIterator() {
		return EmptyIterator.instance();
	}

	/**
	 * Return an empty list iterator.
	 */
	public static <E> ListIterator<E> emptyListIterator() {
		return EmptyListIterator.instance();
	}

	/**
	 * Return an iterator that will use the specified filter to filter the
	 * elements in the specified iterable.
	 * @see FilteringIterator
	 */
	public static <E> FilteringIterator<E> filteringIterator(Iterable<? extends E> iterable, Filter<E> filter) {
		return new FilteringIterator<E>(iterable, filter);
	}

	/**
	 * Return an iterator that will use the specified filter to filter the
	 * elements in the specified iterator.
	 * @see FilteringIterator
	 */
	public static <E> FilteringIterator<E> filter(Iterator<? extends E> iterator, Filter<E> filter) {
		return new FilteringIterator<E>(iterator, filter);
	}

	/**
	 * Return an iterator that will return the specified root element followed
	 * by its children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(E root, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterator<E>(root, misterRogers);
	}

	/**
	 * Return an iterator that will return the specified root elements followed
	 * by their children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(E[] roots, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterator<E>(roots, misterRogers);
	}

	/**
	 * Return an iterator that will return the specified root elements followed
	 * by their children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(Iterable<E> roots, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterator<E>(roots, misterRogers);
	}

	/**
	 * Return an iterator that will return the specified root elements followed
	 * by their children etc. as determined by the specified Mr. Rogers.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(Iterator<E> roots, GraphIterator.MisterRogers<E> misterRogers) {
		return new GraphIterator<E>(roots, misterRogers);
	}

	/**
	 * Return a transformer that transforms an {@link Iterable} into an {@link Iterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<Iterable<? extends E>, Iterator<? extends E>> iterableIteratorTransformer() {
		return ITERABLE_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms an {@link Iterable} into an {@link Iterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer ITERABLE_ITERATOR_TRANSFORMER = new IterableIteratorTransformer();
	/* CU private */ static class IterableIteratorTransformer<E>
		implements Transformer<Iterable<E>, Iterator<E>>
	{
		public Iterator<E> transform(Iterable<E> iterable) {
			return iterable.iterator();
		}
	}

	/**
	 * Return an iterator the corresponds to the specified enumeration.
	 */
	public static <E> EnumerationIterator<E> iterator(Enumeration<E> enumeration) {
		return new EnumerationIterator<E>(enumeration);
	}

	/**
	 * Return an iterator on the elements in the specified array.
	 */
	public static <E> ArrayIterator<E> iterator(E... array) {
		return iterator(array, 0);
	}

	/**
	 * Return an iterator on the elements in the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> ArrayIterator<E> iterator(E[] array, int start) {
		return iterator(array, start, array.length);
	}

	/**
	 * Return an iterator on the elements in the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> ArrayIterator<E> iterator(E[] array, int start, int end) {
		return new ArrayIterator<E>(array, start, end);
	}

	/**
	 * Return an iterator on the specified queue.
	 * @see Queue
	 */
	public static <E> QueueIterator<E> iterator(Queue<E> queue) {
		return new QueueIterator<E>(queue);
	}

	/**
	 * Return an iterator on the specified stack.
	 * @see Stack
	 */
	public static <E> StackIterator<E> iterator(Stack<E> stack) {
		return new StackIterator<E>(stack);
	}

	/**
	 * Return an iterator that converts the specified iterable's element type.
	 * @see LateralIteratorWrapper
	 */
	public static <E1, E2> LateralIteratorWrapper<E1, E2> lateralIterator(Iterable<E1> iterable) {
		return new LateralIteratorWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see LateralIteratorWrapper
	 */
	public static <E1, E2> LateralIteratorWrapper<E1, E2> lateralIterator(Iterator<E1> iterator) {
		return new LateralIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return a list iterator that converts the specified list's element type.
	 * @see LateralListIteratorWrapper
	 */
	public static <E1, E2> LateralListIteratorWrapper<E1, E2> lateralListIterator(List<E1> list) {
		return new LateralListIteratorWrapper<E1, E2>(list);
	}

	/**
	 * Return a list iterator that converts the specified iterable's element type.
	 * @see LateralListIteratorWrapper
	 */
	public static <E1, E2> LateralListIteratorWrapper<E1, E2> lateralListIterator(ListIterable<E1> iterable) {
		return new LateralListIteratorWrapper<E1, E2>(iterable);
	}

	/**
	 * Return a list iterator that converts the specified iterator's element type.
	 * @see LateralListIteratorWrapper
	 */
	public static <E1, E2> LateralListIteratorWrapper<E1, E2> lateralListIterator(ListIterator<E1> iterator) {
		return new LateralListIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return a transformer that transforms a {@link ListIterable} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<ListIterable<E>, ListIterator<E>> listIterableListIteratorTransformer() {
		return LIST_ITERABLE_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link ListIterable} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer LIST_ITERABLE_LIST_ITERATOR_TRANSFORMER = new ListIterableListIteratorTransformer();
	/* CU private */ static class ListIterableListIteratorTransformer<E>
		implements Transformer<ListIterable<E>, ListIterator<E>>
	{
		public ListIterator<E> transform(ListIterable<E> list) {
			return list.iterator();
		}
	}

	/**
	 * Return a list iterator for the specified array.
	 */
	public static <E> ArrayListIterator<E> listIterator(E... array) {
		return listIterator(array, 0);
	}

	/**
	 * Return a list iterator for the specified array
	 * starting at the specified position in the array.
	 */
	public static <E> ArrayListIterator<E> listIterator(E[] array, int start) {
		return listIterator(array, start, array.length);
	}

	/**
	 * Return a list iterator for the specified array
	 * starting at the specified start index, inclusive, and continuing to
	 * the specified end index, exclusive.
	 */
	public static <E> ArrayListIterator<E> listIterator(E[] array, int start, int end) {
		return new ArrayListIterator<E>(array, start, end);
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<E>, ListIterator<E>> listListIteratorTransformer() {
		return LIST_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer LIST_LIST_ITERATOR_TRANSFORMER = new ListListIteratorTransformer();
	/* CU private */ static class ListListIteratorTransformer<E>
		implements Transformer<List<E>, ListIterator<E>>
	{
		public ListIterator<E> transform(List<E> list) {
			return list.listIterator();
		}
	}

	/**
	 * Return an iterator the returns <code>null</code> the specified number of times.
	 * @see NullElementIterator
	 */
	public static <E> NullElementIterator<E> nullElementIterator(int size) {
		return new NullElementIterator<E>(size);
	}

	/**
	 * Return a list iterator the returns <code>null</code> the specified number of times.
	 * @see NullElementListIterator
	 */
	public static <E> NullElementListIterator<E> nullElementListIterator(int size) {
		return new NullElementListIterator<E>(size);
	}

	/**
	 * Return a "peekable" iterator.
	 */
	public static <E> PeekableIterator<E> peekableIterator(Iterable<E> iterable) {
		return new PeekableIterator<E>(iterable);
	}

	/**
	 * Return a "peekable" iterator.
	 */
	public static <E> PeekableIterator<E> peekableIterator(Iterator<E> iterator) {
		return new PeekableIterator<E>(iterator);
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified list.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(E object, List<? extends E> list) {
		return readOnlyCompositeListIterator(object, list.listIterator());
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterable.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(E object, ListIterable<? extends E> iterable) {
		return readOnlyCompositeListIterator(object, iterable.iterator());
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see ReadOnlyCompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(E object, ListIterator<? extends E> iterator) {
		return readOnlyCompositeListIterator(new ListIterator[] { singletonListIterator(object), iterator });
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified lists.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(List<? extends List<? extends E>> lists) {
		Transformer<List<? extends E>, ListIterator<? extends E>> transformer = readOnlyListListIteratorTransformer();
		return readOnlyCompositeListIterator(transform(lists.listIterator(), transformer));
	}

	/**
	 * Return a list iterator that returns the elements in the specified list
	 * followed by the specified object.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(List<? extends E> list, E object) {
		return readOnlyCompositeListIterator(list.listIterator(), object);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified lists.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(List<? extends E>... lists) {
		return readOnlyCompositeListIterator(Arrays.asList(lists));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterable followed by the specified object.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterable<? extends E> iterable, E object) {
		return readOnlyCompositeListIterator(iterable.iterator(), object);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterable<? extends E>... iterables) {
		return readOnlyCompositeListIterator(IterableTools.listIterable(iterables));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterables.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterable<? extends ListIterable<? extends E>> iterables) {
		Transformer<ListIterable<? extends E>, ListIterator<? extends E>> transformer = readOnlyListIterableListIteratorTransformer();
		return readOnlyCompositeListIterator(transform(iterables.iterator(), transformer));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see ReadOnlyCompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterator<? extends E> iterator, E object) {
		return readOnlyCompositeListIterator(new ListIterator[] { iterator, singletonListIterator(object) });
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterator<? extends E>... iterators) {
		return readOnlyCompositeListIterator(listIterator(iterators));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterator<? extends ListIterator<? extends E>> iterators) {
		return new ReadOnlyCompositeListIterator<E>(iterators);
	}

	/**
	 * Return a list iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <P, E> ReadOnlyCompositeListIterator<E> readOnlyCompositeListIterator(ListIterator<? extends P> parents, Transformer<P, ListIterator<? extends E>> childrenTransformer) {
		return readOnlyCompositeListIterator(transform(parents, childrenTransformer));
	}

	/**
	 * Return a transformer that transforms a {@link ListIterable} into a
	 * read-only {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<ListIterable<? extends E>, ListIterator<? extends E>> readOnlyListIterableListIteratorTransformer() {
		return READ_ONLY_LIST_ITERABLE_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link ListIterable} into a
	 * read-only {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_ITERABLE_LIST_ITERATOR_TRANSFORMER = new ReadOnlyListIterableListIteratorTransformer();
	/* CU private */ static class ReadOnlyListIterableListIteratorTransformer<E>
		implements Transformer<ListIterable<? extends E>, ListIterator<? extends E>>
	{
		public ListIterator<? extends E> transform(ListIterable<? extends E> iterable) {
			return readOnlyListIterator(iterable);
		}
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyIterator
	 */
	public static <E> ReadOnlyIterator<E> readOnlyIterator(Iterable<? extends E> iterable) {
		return new ReadOnlyIterator<E>(iterable);
	}

	/**
	 * Convert the specified iterator to read-only.
	 * @see ReadOnlyIterator
	 */
	public static <E> ReadOnlyIterator<E> readOnlyIterator(Iterator<? extends E> iterator) {
		return new ReadOnlyIterator<E>(iterator);
	}

	/**
	 * Convert the specified list to read-only.
	 * @see ReadOnlyListIterator
	 */
	public static <E> ReadOnlyListIterator<E> readOnlyListIterator(List<? extends E> list) {
		return new ReadOnlyListIterator<E>(list);
	}

	/**
	 * Convert the specified iterable to read-only.
	 * @see ReadOnlyListIterator
	 */
	public static <E> ReadOnlyListIterator<E> readOnlyListIterator(ListIterable<? extends E> iterable) {
		return new ReadOnlyListIterator<E>(iterable);
	}

	/**
	 * Convert the specified iterator to read-only.
	 * @see ReadOnlyListIterator
	 */
	public static <E> ReadOnlyListIterator<E> readOnlyListIterator(ListIterator<? extends E> iterator) {
		return new ReadOnlyListIterator<E>(iterator);
	}

	/**
	 * Return a transformer that transforms a {@link List} into a
	 * read-only {@link ListIterator}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, ListIterator<? extends E>> readOnlyListListIteratorTransformer() {
		return READ_ONLY_LIST_LIST_ITERATOR_TRANSFORMER;
	}

	/**
	 * A transformer that transforms a {@link List} into a
	 * read-only {@link ListIterator}.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer READ_ONLY_LIST_LIST_ITERATOR_TRANSFORMER = new ReadOnlyListListIteratorTransformer();
	/* CU private */ static class ReadOnlyListListIteratorTransformer<E>
		implements Transformer<List<? extends E>, ListIterator<? extends E>>
	{
		public ListIterator<? extends E> transform(List<? extends E> list) {
			return readOnlyListIterator(list);
		}
	}

	/**
	 * Return an iterator the returns the specified object the specified number
	 * of times.
	 * @see RepeatingElementIterator
	 */
	public static <E> RepeatingElementIterator<E> repeatingElementIterator(E element, int size) {
		return new RepeatingElementIterator<E>(element, size);
	}

	/**
	 * Return a list iterator the returns the specified object the specified number
	 * of times.
	 * @see RepeatingElementIterator
	 */
	public static <E> RepeatingElementListIterator<E> repeatingElementListIterator(E element, int size) {
		return new RepeatingElementListIterator<E>(element, size);
	}

	/**
	 * Return an iterator the returns the first object in each row of the
	 * specified result set.
	 * @see ResultSetIterator
	 */
	public static <E> ResultSetIterator<E> resultSetIterator(ResultSet resultSet) {
		return new ResultSetIterator<E>(resultSet);
	}

	/**
	 * Return an iterator the returns the objects produced by the specified adapter.
	 * @see ResultSetIterator
	 */
	public static <E> ResultSetIterator<E> resultSetIterator(ResultSet resultSet, ResultSetIterator.Adapter<E> adapter) {
		return new ResultSetIterator<E>(resultSet, adapter);
	}

	/**
	 * Return an iterator that returns the objects in the specified iterable
	 * in reverse order.
	 */
	public static <E> ReverseIterator<E> reverseIterator(Iterable<E> iterable) {
		return new ReverseIterator<E>(iterable);
	}

	/**
	 * Return an iterator that returns the objects in the specified iterable
	 * in reverse order.
	 */
	public static <E> ReverseIterator<E> reverseIterator(Iterable<E> iterable, int iterableSize) {
		return new ReverseIterator<E>(iterable, iterableSize);
	}

	/**
	 * Return an iterator that returns the objects in the specified iterator
	 * in reverse order.
	 */
	public static <E> ReverseIterator<E> reverseIterator(Iterator<E> iterator) {
		return new ReverseIterator<E>(iterator);
	}

	/**
	 * Return an iterator that returns the objects in the specified iterable
	 * in reverse order.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ReverseIterator<E> reverseIterator(Iterator<E> iterator, int iteratorSize) {
		return new ReverseIterator<E>(iterator, iteratorSize);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<E>> SimultaneousIterator<E> simultaneousIterator(I... iterators) {
		return new SimultaneousIterator<E>(iterators);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<E>> SimultaneousIterator<E> simultaneousIterator(Iterable<I> iterables) {
		return new SimultaneousIterator<E>(iterables);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<E>> SimultaneousIterator<E> simultaneousIterator(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousIterator<E>(iterables, iterablesSize);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> simultaneousListIterator(I... iterators) {
		return new SimultaneousListIterator<E>(iterators);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> simultaneousListIterator(Iterable<I> iterables) {
		return new SimultaneousListIterator<E>(iterables);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> simultaneousListIterator(Iterable<I> iterators, int iteratorsSize) {
		return new SimultaneousListIterator<E>(iterators, iteratorsSize);
	}

	/**
	 * Return an iterator that returns only the single,
	 * specified object.
	 * @see SingleElementIterator
	 */
	public static <E> SingleElementIterator<E> singletonIterator(E value) {
		return new SingleElementIterator<E>(value);
	}

	/**
	 * Return a list iterator that returns only the single,
	 * specified object.
	 * @see SingleElementListIterator
	 */
	public static <E> SingleElementListIterator<E> singletonListIterator(E value) {
		return new SingleElementListIterator<E>(value);
	}

	/**
	 * Return an iterator that converts the specified iterable's element type.
	 * @see SubIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubIteratorWrapper<E1, E2> subIterator(Iterable<E1> iterable) {
		return new SubIteratorWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SubIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubIteratorWrapper<E1, E2> subIterator(Iterator<E1> iterator) {
		return new SubIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return an iterator that converts the specified list's element type.
	 * @see SubListIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubListIteratorWrapper<E1, E2> subListIterator(List<E1> list) {
		return new SubListIteratorWrapper<E1, E2>(list);
	}

	/**
	 * Return an iterator that converts the specified iterable's element type.
	 * @see SubListIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubListIteratorWrapper<E1, E2> subListIterator(ListIterable<E1> iterable) {
		return new SubListIteratorWrapper<E1, E2>(iterable);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SubListIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubListIteratorWrapper<E1, E2> subListIterator(ListIterator<E1> iterator) {
		return new SubListIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return an iterator that converts the specified iterable's element type.
	 * @see SuperIteratorWrapper
	 */
	public static <E> Iterator<E> SuperIteratorWrapper(Iterable<? extends E> iterable) {
		return new SuperIteratorWrapper<E>(iterable);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SuperIteratorWrapper
	 */
	public static <E> Iterator<E> SuperIteratorWrapper(Iterator<? extends E> iterator) {
		return new SuperIteratorWrapper<E>(iterator);
	}

	/**
	 * Return an iterator that converts the specified list's element type.
	 * @see SuperListIteratorWrapper
	 */
	public static <E> ListIterator<E> SuperListIteratorWrapper(List<? extends E> list) {
		return new SuperListIteratorWrapper<E>(list);
	}

	/**
	 * Return an iterator that converts the specified iterable's element type.
	 * @see SuperListIteratorWrapper
	 */
	public static <E> ListIterator<E> SuperListIteratorWrapper(ListIterable<? extends E> iterable) {
		return new SuperListIteratorWrapper<E>(iterable);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SuperListIteratorWrapper
	 */
	public static <E> ListIterator<E> SuperListIteratorWrapper(ListIterator<? extends E> iterator) {
		return new SuperListIteratorWrapper<E>(iterator);
	}

	/**
	 * Return an iterator that synchronizes the specified iterable's iterator on itself.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronizedIterator(Iterable<? extends E> iterable) {
		return new SynchronizedIterator<E>(iterable);
	}

	/**
	 * Return an iterator that synchronizes the specified iterable's iterator with the
	 * specified mutex.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronizedIterator(Iterable<? extends E> iterable, Object mutex) {
		return new SynchronizedIterator<E>(iterable, mutex);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator on itself.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronizedIterator(Iterator<? extends E> iterator) {
		return new SynchronizedIterator<E>(iterator);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator with the
	 * specified mutex.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronizedIterator(Iterator<? extends E> iterator, Object mutex) {
		return new SynchronizedIterator<E>(iterator, mutex);
	}

	/**
	 * Return an iterator that synchronizes the specified list's iterator on itself.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronizedListIterator(List<E> list) {
		return new SynchronizedListIterator<E>(list);
	}

	/**
	 * Return an iterator that synchronizes the specified iterable's iterator on itself.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronizedListIterator(ListIterable<E> iterable) {
		return new SynchronizedListIterator<E>(iterable);
	}

	/**
	 * Return an iterator that synchronizes the specified iterable's iterator with the
	 * specified mutex.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronizedListIterator(ListIterable<E> iterable, Object mutex) {
		return new SynchronizedListIterator<E>(iterable, mutex);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator on itself.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronizedListIterator(ListIterator<E> iterator) {
		return new SynchronizedListIterator<E>(iterator);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator with the
	 * specified mutex.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronizedListIterator(ListIterator<E> iterator, Object mutex) {
		return new SynchronizedListIterator<E>(iterator, mutex);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationIterator
	 */
	public static <E1, E2> TransformationIterator<E1, E2> transformationIterator(Iterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		return new TransformationIterator<E1, E2>(iterable, transformer);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterator.
	 * @see TransformationIterator
	 */
	public static <E1, E2> TransformationIterator<E1, E2> transform(Iterator<? extends E1> iterator, Transformer<E1, ? extends E2> transformer) {
		return new TransformationIterator<E1, E2>(iterator, transformer);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified list.
	 * @see TransformationListIterator
	 */
	public static <E1, T1 extends E1, E2> TransformationListIterator<E1, E2> transformationListIterator(List<? extends E1> list, Transformer<E1, ? extends E2> transformer) {
		return new TransformationListIterator<E1, E2>(list, transformer);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterable.
	 * @see TransformationListIterator
	 */
	public static <E1, E2> TransformationListIterator<E1, E2> transformationListIterator(ListIterable<? extends E1> iterable, Transformer<E1, ? extends E2> transformer) {
		return new TransformationListIterator<E1, E2>(iterable, transformer);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterator.
	 * @see TransformationListIterator
	 */
	public static <E1, E2> TransformationListIterator<E1, E2> transform(ListIterator<? extends E1> iterator, Transformer<E1, ? extends E2> transformer) {
		return new TransformationListIterator<E1, E2>(iterator, transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified root and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(E root, Transformer<E, Iterator<? extends E>> transformer) {
		return treeIterator(new SingleElementIterator<E>(root), transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(E[] roots, Transformer<E, Iterator<? extends E>> transformer) {
		return treeIterator(new ArrayIterator<E>(roots), transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(Iterable<? extends E> roots, Transformer<E, Iterator<? extends E>> transformer) {
		return treeIterator(roots.iterator(), transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(Iterator<? extends E> roots, Transformer<E, Iterator<? extends E>> transformer) {
		return new TreeIterator<E>(roots, transformer);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private IteratorTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
