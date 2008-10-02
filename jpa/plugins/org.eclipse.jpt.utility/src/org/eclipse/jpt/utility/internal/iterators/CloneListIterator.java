/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>CloneListIterator</code> iterates over a copy of a list,
 * allowing for concurrent access to the original list.
 * <p>
 * The original list passed to the <code>CloneListIterator</code>'s
 * constructor should be synchronized; otherwise you run the risk of
 * a corrupted list.
 * <p>
 * By default, a <code>CloneListIterator</code> does not support the
 * modification operations; this is because it does not have
 * access to the original list. But if the <code>CloneListIterator</code>
 * is supplied with a <code>Mutator</code> it will delegate the
 * modification operations to the <code>Mutator</code>.
 * Alternatively, a subclass can override the modification methods.
 */
public class CloneListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<Object> nestedListIterator;
	private int cursor;
	private String state;
	private final Mutator<E> mutator;

	private static final String UNKNOWN = "unknown";
	private static final String PREVIOUS = "previous";
	private static final String NEXT = "next";


	// ********** constructors **********

	/**
	 * Construct a list iterator on a copy of the specified list.
	 * The modification methods will not be supported,
	 * unless a subclass overrides them.
	 */
	public CloneListIterator(List<? extends E> list) {
		this(list, Mutator.ReadOnly.<E>instance());
	}

	/**
	 * Construct a list iterator on a copy of the specified list.
	 * Use the specified list mutator to modify the original list.
	 */
	public CloneListIterator(List<? extends E> list, Mutator<E> mutator) {
		super();
		// build a copy of the list and keep it in synch with original (if the mutator allows changes)
		// that way the nested list iterator will maintain some of our state
		this.nestedListIterator = CollectionTools.list(list.toArray()).listIterator();
		this.mutator = mutator;
		this.cursor = 0;
		this.state = UNKNOWN;
	}


	// ********** ListIterator implementation **********

	public boolean hasNext() {
		return this.nestedListIterator.hasNext();
	}

	public E next() {
		// allow the nested iterator to throw an exception before we modify the index
		E next = this.nestedNext();
		this.cursor++;
		this.state = NEXT;
		return next;
	}

	public void remove() {
		// allow the nested iterator to throw an exception before we modify the original list
		this.nestedListIterator.remove();
		if (this.state == PREVIOUS) {
			this.remove(this.cursor);
		} else {
			this.cursor--;
			this.remove(this.cursor);
		}
	}

	public int nextIndex() {
		return this.nestedListIterator.nextIndex();
	}

	public int previousIndex() {
		return this.nestedListIterator.previousIndex();
	}

	public boolean hasPrevious() {
		return this.nestedListIterator.hasPrevious();
	}

	public E previous() {
		// allow the nested iterator to throw an exception before we modify the index
		E previous = this.nestedPrevious();
		this.cursor--;
		this.state = PREVIOUS;
		return previous;
	}

	public void add(E o) {
		// allow the nested iterator to throw an exception before we modify the original list
		this.nestedListIterator.add(o);
		this.add(this.cursor, o);
		this.cursor++;
	}

	public void set(E o) {
		// allow the nested iterator to throw an exception before we modify the original list
		this.nestedListIterator.set(o);
		if (this.state == PREVIOUS) {
			this.set(this.cursor, o);
		} else {
			this.set(this.cursor - 1, o);
		}
	}


	// ********** internal methods **********

	/**
	 * The list passed in during construction held Es,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (Object[]).
	 */
	@SuppressWarnings("unchecked")
	protected E nestedNext() {
		return (E) this.nestedListIterator.next();
	}

	/**
	 * The list passed in during construction held Es,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (Object[]).
	 */
	@SuppressWarnings("unchecked")
	protected E nestedPrevious() {
		return (E) this.nestedListIterator.previous();
	}

	/**
	 * Add the specified element to the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Mutator</code>.
	 */
	protected void add(int index, E o) {
		this.mutator.add(index, o);
	}

	/**
	 * Remove the specified element from the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Mutator</code>.
	 */
	protected void remove(int index) {
		this.mutator.remove(index);
	}

	/**
	 * Set the specified element in the original list.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Mutator</code>.
	 */
	protected void set(int index, E o) {
		this.mutator.set(index, o);
	}


	// ********** overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}


	//********** member interface **********

	/**
	 * Used by <code>CloneListIterator</code> to remove
	 * elements from the original list; since the list iterator
	 * does not have direct access to the original list.
	 */
	public interface Mutator<T> {

		/**
		 * Add the specified object to the original list.
		 */
		void add(int index, T o);

		/**
		 * Remove the specified object from the original list.
		 */
		void remove(int index);

		/**
		 * Set the specified object in the original list.
		 */
		void set(int index, T o);


		final class ReadOnly<S> implements Mutator<S> {
			@SuppressWarnings("unchecked")
			public static final Mutator INSTANCE = new ReadOnly();
			@SuppressWarnings("unchecked")
			public static <R> Mutator<R> instance() {
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
				return "CloneListIterator.Mutator.ReadOnly";
			}
		}

	}

}
