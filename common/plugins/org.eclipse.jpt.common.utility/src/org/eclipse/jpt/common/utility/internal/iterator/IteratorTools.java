/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.ArrayList;
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
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.command.DisabledParameterizedCommand;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Adapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
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
	 * If the command throws an exception for an element, the exception will be
	 * handled by the specified exception handler and processing of the
	 * remaining elements will continue.
	 */
	public static <E> void execute(Iterator<? extends E> iterator, ParameterizedCommand<E> command, ExceptionHandler exceptionHandler) {
		while (iterator.hasNext()) {
			try {
				command.execute(iterator.next());
			} catch (Throwable ex) {
				exceptionHandler.handleException(ex);
			}
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
	 * Execute the specified command for each element in the specified iterator.
	 * If the command throws an exception (other than an
	 * {@link InterruptedException}) for an element, the exception will be
	 * handled by the specified exception handler and processing of the
	 * remaining elements will continue.
	 */
	public static <E> void execute(Iterator<? extends E> iterator, InterruptibleParameterizedCommand<E> command, ExceptionHandler exceptionHandler) throws InterruptedException {
		while (iterator.hasNext()) {
			try {
				command.execute(iterator.next());
			} catch (InterruptedException ex) {
				throw ex;
			} catch (Throwable ex) {
				exceptionHandler.handleException(ex);
			}
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
		return iterator.hasNext() ? indexOf_(iterator, value, 0) : -1;
	}

	/**
	 * Return the index of the first occurrence of the
	 * specified element in the specified iterator, starting at the specified index;
	 * return -1 if there is no such element.
	 */
	public static int indexOf(Iterator<?> iterator, Object value, int startIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		} else {
			for (int i = 0; iterator.hasNext() && (i < startIndex); i++) {
				iterator.next();
			}
		}
		return iterator.hasNext() ? indexOf_(iterator, value, startIndex) : -1;
	}

	/**
	 * assume iterator has more elements and is positioned at the start index
	 * and start index >= 0
	 */
	private static int indexOf_(Iterator<?> iterator, Object value, int startIndex) {
		if (value == null) {
			for (int i = startIndex; iterator.hasNext(); i++) {
				if (iterator.next() == null) {
					return i;
				}
			}
		} else {
			for (int i = startIndex; iterator.hasNext(); i++) {
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
	 * Return the index of the last occurrence of the
	 * specified element in the specified iterator, starting at the specified index;
	 * return -1 if there is no such element.
	 */
	public static int lastIndexOf(Iterator<?> iterator, Object value, int startIndex) {
		if (startIndex < 0) {
			return -1;
		}
		return iterator.hasNext() ? lastIndexOf_(iterator, value, startIndex) : -1;
	}

	/**
	 * assume iterator has more elements and start index >= 0
	 */
	private static int lastIndexOf_(Iterator<?> iterator, Object value, int startIndex) {
		int last = -1;
		if (value == null) {
			for (int i = 0; iterator.hasNext(); i++) {
				if (i > startIndex) {
					return last;
				}
				if (iterator.next() == null) {
					last = i;
				}
			}
		} else {
			for (int i = 0; iterator.hasNext(); i++) {
				if (i > startIndex) {
					return last;
				}
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
	 * Return whether the specified iterator is empty.
	 * (Shortcut the iterator rather than calculating the entire size.)
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return ! iterator.hasNext();
	}

	/**
	 * Return whether the specified iterator is <em>not</em> empty.
	 * (Shortcut the iterator rather than calculating the entire size.)
	 */
	public static boolean isNotEmpty(Iterator<?> iterator) {
		return iterator.hasNext();
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
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<? extends E>> SimultaneousIterator<E> align(I... iterators) {
		return align(IterableTools.iterable(iterators), iterators.length);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<? extends E>> SimultaneousIterator<E> align(Iterable<I> iterables) {
		return new SimultaneousIterator<E>(iterables);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousIterator
	 */
	public static <E, I extends Iterator<? extends E>> SimultaneousIterator<E> align(Iterable<I> iterables, int iterablesSize) {
		return new SimultaneousIterator<E>(iterables, iterablesSize);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> alignList(I... iterators) {
		return alignList(IterableTools.listIterable(iterators), iterators.length);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterables.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> alignList(Iterable<I> iterables) {
		return new SimultaneousListIterator<E>(iterables);
	}

	/**
	 * Return an iterator that provides simultaneous processing of the elements
	 * in the specified iterators.
	 * @see SimultaneousListIterator
	 */
	public static <E, I extends ListIterator<E>> SimultaneousListIterator<E> alignList(Iterable<I> iterators, int iteratorsSize) {
		return new SimultaneousListIterator<E>(iterators, iteratorsSize);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see LateralIteratorWrapper
	 */
	public static <E1, E2> LateralIteratorWrapper<E1, E2> cast(Iterator<E1> iterator) {
		return new LateralIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return a list iterator that converts the specified iterator's element type.
	 * @see LateralListIteratorWrapper
	 */
	public static <E1, E2> LateralListIteratorWrapper<E1, E2> cast(ListIterator<E1> iterator) {
		return new LateralListIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SubIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubIteratorWrapper<E1, E2> downCast(Iterator<E1> iterator) {
		return new SubIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SubListIteratorWrapper
	 */
	public static <E1, E2 extends E1> SubListIteratorWrapper<E1, E2> downCast(ListIterator<E1> iterator) {
		return new SubListIteratorWrapper<E1, E2>(iterator);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SuperIteratorWrapper
	 */
	public static <E> Iterator<E> upCast(Iterator<? extends E> iterator) {
		return new SuperIteratorWrapper<E>(iterator);
	}

	/**
	 * Return an iterator that converts the specified iterator's element type.
	 * @see SuperListIteratorWrapper
	 */
	public static <E> ListIterator<E> upCast(ListIterator<? extends E> iterator) {
		return new SuperListIteratorWrapper<E>(iterator);
	}

	/**
	 * Return a chain iterator that starts with the specified element and uses
	 * the specified {@link Transformer transformer}.
	 * @see ChainIterator
	 */
	public static <E> ChainIterator<E> chainIterator(E first, Transformer<? super E, ? extends E> transformer) {
		return new ChainIterator<E>(first, transformer);
	}

	/**
	 * Return an iterator that clones the specified collection before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> clone(Collection<? extends E> collection) {
		return clone(collection, DisabledParameterizedCommand.instance());
	}

	/**
	 * Return an iterator that clones the specified collection before returning
	 * elements and uses the specified {@link ParameterizedCommand remove command}.
	 * @see CloneIterator
	 */
	public static <E> CloneIterator<E> clone(Collection<? extends E> collection, ParameterizedCommand<? super E> removeCommand) {
		return new CloneIterator<E>(collection, removeCommand);
	}

	/**
	 * Return an iterator that clones the specified list before returning
	 * elements.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> clone(List<? extends E> list) {
		return clone(list, Adapter.ReadOnly.<E>instance());
	}

	/**
	 * Return an iterator that clones the specified list before returning
	 * elements and uses the specified {@link CloneListIterator.Adapter adapter}.
	 * @see CloneIterator
	 */
	public static <E> CloneListIterator<E> clone(List<? extends E> list, CloneListIterator.Adapter<E> adapter) {
		return new CloneListIterator<E>(list, adapter);
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see CompositeIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterator<E> add(Iterator<? extends E> iterator, E object) {
		return concatenate(iterator, singletonIterator(object));
	}

	/**
	 * Return an iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see CompositeIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeIterator<E> insert(E object, Iterator<? extends E> iterator) {
		return concatenate(singletonIterator(object), iterator);
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> concatenate(Iterator<? extends E>... iterators) {
		return concatenate(iterator(iterators));
	}

	/**
	 * Return an iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeIterator
	 */
	public static <E> CompositeIterator<E> concatenate(Iterator<? extends Iterator<? extends E>> iterators) {
		return new CompositeIterator<E>(iterators);
	}

	/**
	 * Return an iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeIterator
	 */
	public static <P, E> CompositeIterator<E> children(Iterator<? extends P> parents, Transformer<? super P, ? extends Iterator<? extends E>> childrenTransformer) {
		return concatenate(transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see CompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterator<E> add(ListIterator<E> iterator, E object) {
		return concatenate(iterator, singletonListIterator(object));
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see CompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> CompositeListIterator<E> insert(E object, ListIterator<E> iterator) {
		return concatenate(singletonListIterator(object), iterator);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> concatenate(ListIterator<E>... iterators) {
		return concatenate(listIterator(iterators));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see CompositeListIterator
	 */
	public static <E> CompositeListIterator<E> concatenate(ListIterator<? extends ListIterator<E>> iterators) {
		return new CompositeListIterator<E>(iterators);
	}

	/**
	 * Return a list iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see CompositeListIterator
	 */
	public static <P, E> CompositeListIterator<E> children(ListIterator<? extends P> parents, Transformer<? super P, ? extends ListIterator<E>> childrenTransformer) {
		return concatenate(transform(parents, childrenTransformer));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterator followed by the specified object.
	 * @see ReadOnlyCompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterator<E> addReadOnly(ListIterator<? extends E> iterator, E object) {
		return concatenateReadOnly(iterator, singletonListIterator(object));
	}

	/**
	 * Return a list iterator that returns the specified object followed by the
	 * elements in the specified iterator.
	 * @see ReadOnlyCompositeListIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ReadOnlyCompositeListIterator<E> insertReadOnly(E object, ListIterator<? extends E> iterator) {
		return concatenateReadOnly(singletonListIterator(object), iterator);
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> concatenateReadOnly(ListIterator<? extends E>... iterators) {
		return concatenateReadOnly(listIterator(iterators));
	}

	/**
	 * Return a list iterator that returns the
	 * elements in the specified iterators.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <E> ReadOnlyCompositeListIterator<E> concatenateReadOnly(ListIterator<? extends ListIterator<? extends E>> iterators) {
		return new ReadOnlyCompositeListIterator<E>(iterators);
	}

	/**
	 * Return a list iterator on the children of the specified parents.
	 * Use the specified transformer to transform each parent into its children.
	 * @see ReadOnlyCompositeListIterator
	 */
	public static <P, E> ReadOnlyCompositeListIterator<E> readOnlyChildren(ListIterator<? extends P> parents, Transformer<? super P, ? extends ListIterator<? extends E>> childrenTransformer) {
		return concatenateReadOnly(transform(parents, childrenTransformer));
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
	 * Return an iterator that will use the specified predicate to filter the
	 * elements in the specified iterator.
	 * @see FilteringIterator
	 */
	public static <E> FilteringIterator<E> filter(Iterator<? extends E> iterator, Predicate<? super E> predicate) {
		return new FilteringIterator<E>(iterator, predicate);
	}

	/**
	 * Return an iterator that will return only the non-<code>null</code>
	 * elements in the specified iterator.
	 * @see FilteringIterator
	 */
	public static <E> FilteringIterator<E> removeNulls(Iterator<? extends E> iterator) {
		return filter(iterator, PredicateTools.isNotNull());
	}

	/**
	 * Return an iterator that will return the specified root element followed
	 * by its children etc. as determined by the specified transformer.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(E root, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		return graphIterator(singletonIterator(root), transformer);
	}

	/**
	 * Return an iterator that will return the specified root elements followed
	 * by their children etc. as determined by the specified transformer.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(E[] roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		return graphIterator(iterator(roots), transformer);
	}

	/**
	 * Return an iterator that will return the specified root elements followed
	 * by their children etc. as determined by the specified transformer.
	 * @see GraphIterator
	 */
	public static <E> GraphIterator<E> graphIterator(Iterator<? extends E> roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		return new GraphIterator<E>(roots, transformer);
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
	public static <E> QueueIterator<E> iterator(Queue<? extends E> queue) {
		return new QueueIterator<E>(queue);
	}

	/**
	 * Return an iterator on the specified stack.
	 * @see Stack
	 */
	public static <E> StackIterator<E> iterator(Stack<? extends E> stack) {
		return new StackIterator<E>(stack);
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
	public static <E> PeekableIterator<E> peekable(Iterator<? extends E> iterator) {
		return new PeekableIterator<E>(iterator);
	}

	/**
	 * Convert the specified iterator to read-only.
	 * @see ReadOnlyIterator
	 */
	public static <E> ReadOnlyIterator<E> readOnly(Iterator<? extends E> iterator) {
		return new ReadOnlyIterator<E>(iterator);
	}

	/**
	 * Convert the specified iterator to read-only.
	 * @see ReadOnlyListIterator
	 */
	public static <E> ReadOnlyListIterator<E> readOnly(ListIterator<? extends E> iterator) {
		return new ReadOnlyListIterator<E>(iterator);
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
	 * Return an iterator that returns the objects in the specified iterator
	 * in reverse order.
	 */
	public static <E> ReverseIterator<E> reverse(Iterator<? extends E> iterator) {
		return new ReverseIterator<E>(iterator);
	}

	/**
	 * Return an iterator that returns the objects in the specified iterable
	 * in reverse order.
	 * The specified iterator size is a performance hint.
	 */
	public static <E> ReverseIterator<E> reverse(Iterator<? extends E> iterator, int iteratorSize) {
		return new ReverseIterator<E>(iterator, iteratorSize);
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
	 * Return an iterator that synchronizes the specified iterator on itself.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronize(Iterator<? extends E> iterator) {
		return new SynchronizedIterator<E>(iterator);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator with the
	 * specified mutex.
	 * @see SynchronizedIterator
	 */
	public static <E> SynchronizedIterator<E> synchronize(Iterator<? extends E> iterator, Object mutex) {
		return new SynchronizedIterator<E>(iterator, mutex);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator on itself.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronize(ListIterator<E> iterator) {
		return new SynchronizedListIterator<E>(iterator);
	}

	/**
	 * Return an iterator that synchronizes the specified iterator with the
	 * specified mutex.
	 * @see SynchronizedListIterator
	 */
	public static <E> SynchronizedListIterator<E> synchronize(ListIterator<E> iterator, Object mutex) {
		return new SynchronizedListIterator<E>(iterator, mutex);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterator.
	 * @see TransformationIterator
	 */
	public static <E1, E2> TransformationIterator<E1, E2> transform(Iterator<? extends E1> iterator, Transformer<? super E1, ? extends E2> transformer) {
		return new TransformationIterator<E1, E2>(iterator, transformer);
	}

	/**
	 * Return an iterator that will use the specified transformer to transform the
	 * elements in the specified iterator.
	 * @see TransformationListIterator
	 */
	public static <E1, E2> TransformationListIterator<E1, E2> transform(ListIterator<? extends E1> iterator, Transformer<? super E1, ? extends E2> transformer) {
		return new TransformationListIterator<E1, E2>(iterator, transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified root and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(E root, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		return treeIterator(singletonIterator(root), transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(E[] roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
		return treeIterator(iterator(roots), transformer);
	}

	/**
	 * Construct an iterator that returns the nodes of a tree
	 * with the specified roots and transformer.
	 * @see TreeIterator
	 */
	public static <E> TreeIterator<E> treeIterator(Iterator<? extends E> roots, Transformer<? super E, ? extends Iterator<? extends E>> transformer) {
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
