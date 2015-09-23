/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;

/**
 * A <code>CloneListIterator</code> iterates over a copy of a list,
 * allowing for concurrent access to the original list.
 * <p>
 * The original list passed to the <code>CloneListIterator</code>'s
 * constructor should be synchronized; otherwise you run the risk of
 * a corrupted list (e.g. {@link java.util.Vector}.
 * <p>
 * By default, a <code>CloneListIterator</code> does not support the
 * modification operations; this is because it does not have
 * access to the original list. But if the <code>CloneListIterator</code>
 * is supplied with a {@link Adapter} it will delegate the
 * modification operations to the {@link Adapter}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable
 * @see org.eclipse.jpt.common.utility.internal.iterable.SnapshotCloneListIterable
 */
public class CloneListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<Object> listIterator;
	private int cursor;
	private State state;
	private final Adapter<E> adapter;

	private enum State {
		UNKNOWN,
		PREVIOUS,
		NEXT
	}


	/**
	 * Construct a list iterator on a copy of the specified list.
	 * Use the specified adapter to modify the original list.
	 */
	public CloneListIterator(List<? extends E> list, Adapter<E> adapter) {
		this(list.toArray(), adapter);
	}

	/**
	 * Internal constructor used by subclasses.
	 */
	protected CloneListIterator(Object[] array, Adapter<E> adapter) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		// build a copy of the list and keep it in sync with original (if the mutator allows changes)
		// that way the nested list iterator will maintain some of our state
		this.listIterator = ListTools.arrayList(array).listIterator();
		this.adapter = adapter;
		this.cursor = 0;
		this.state = State.UNKNOWN;
	}

	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	/**
	 * The list passed in during construction held elements of type <code>E</code>,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (<code>Object[]</code>).
	 */
	public E next() {
		// allow the nested iterator to throw an exception before we modify the index
		@SuppressWarnings("unchecked")
		E next = (E) this.listIterator.next();
		this.cursor++;
		this.state = State.NEXT;
		return next;
	}

	public void remove() {
		// allow the nested iterator to throw an exception before we modify the original list
		this.listIterator.remove();
		if (this.state != State.PREVIOUS) {
			this.cursor--;
		}
		this.adapter.remove(this.cursor);
	}

	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	/**
	 * The list passed in during construction held elements of type <code>E</code>,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (<code>Object[]</code>).
	 */
	public E previous() {
		// allow the nested iterator to throw an exception before we modify the index
		@SuppressWarnings("unchecked")
		E previous = (E) this.listIterator.previous();
		this.cursor--;
		this.state = State.PREVIOUS;
		return previous;
	}

	public void add(E o) {
		// allow the nested iterator to throw an exception before we modify the original list
		this.listIterator.add(o);
		this.adapter.add(this.cursor, o);
		this.cursor++;
	}

	public void set(E o) {
		// allow the nested iterator to throw an exception before we modify the original list
		this.listIterator.set(o);
		if (this.state == State.PREVIOUS) {
			this.adapter.set(this.cursor, o);
		} else {
			this.adapter.set(this.cursor - 1, o);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	//********** adapter interface **********

	/**
	 * Used by {@link CloneListIterator} to add elements to,
	 * remove elements from, and set elements in
	 * the original list; since the list iterator
	 * does not have direct access to the original list.
	 */
	public interface Adapter<E> {

		/**
		 * Add the specified object to the original list.
		 */
		void add(int index, E o);

		/**
		 * Remove the specified object from the original list.
		 */
		void remove(int index);

		/**
		 * Set the specified object in the original list.
		 */
		void set(int index, E o);


		final class ReadOnly<S>
			implements Adapter<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final Adapter INSTANCE = new ReadOnly();
			@SuppressWarnings("unchecked")
			public static <R> Adapter<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private ReadOnly() {
				super();
			}
			// add is not supported
			public void add(int index, Object o) {
				throw new UnsupportedOperationException();
			}
			// remove is not supported
			public void remove(int index) {
				throw new UnsupportedOperationException();
			}
			// set is not supported
			public void set(int index, Object o) {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return ObjectTools.singletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}
