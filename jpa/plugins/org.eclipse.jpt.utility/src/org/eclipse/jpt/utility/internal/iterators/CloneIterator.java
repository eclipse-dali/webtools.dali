/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>CloneIterator</code> iterates over a copy of a collection,
 * allowing for concurrent access to the original collection.
 * <p>
 * The original collection passed to the <code>CloneIterator</code>'s
 * constructor should be synchronized (e.g. java.util.Vector);
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, a <code>CloneIterator</code> does not support the
 * <code>#remove()</code> operation; this is because it does not have
 * access to the original collection. But if the <code>CloneIterator</code>
 * is supplied with an <code>Mutator</code> it will delegate the
 * <code>#remove()</code> operation to the <code>Mutator</code>.
 * Alternatively, a subclass can override the <code>#remove(Object)</code>
 * method.
 */
public class CloneIterator<E>
	implements Iterator<E>
{
	private final Iterator<Object> iterator;
	private E current;
	private final Mutator<E> mutator;
	private boolean removeAllowed;


	// ********** constructors **********

	/**
	 * Construct an iterator on a copy of the specified collection.
	 * The <code>#remove()</code> method will not be supported,
	 * unless a subclass overrides the <code>#remove(Object)</code>.
	 */
	public CloneIterator(Collection<? extends E> collection) {
		this(collection, Mutator.ReadOnly.<E>instance());
	}

	/**
	 * Construct an iterator on a copy of the specified array.
	 * The <code>#remove()</code> method will not be supported,
	 * unless a subclass overrides the <code>#remove(Object)</code>.
	 */
	public CloneIterator(E[] array) {
		this(array, Mutator.ReadOnly.<E>instance());
	}

	/**
	 * Construct an iterator on a copy of the specified collection.
	 * Use the specified mutator to remove objects from the
	 * original collection.
	 */
	public CloneIterator(Collection<? extends E> collection, Mutator<E> mutator) {
		this(mutator, collection.toArray());
	}

	/**
	 * Construct an iterator on a copy of the specified array.
	 * Use the specified mutator to remove objects from the
	 * original array.
	 */
	public CloneIterator(E[] array, Mutator<E> mutator) {
		this(mutator, array.clone());
	}

	/**
	 * Internal constructor used by subclasses.
	 * Swap order of arguments to prevent collision with other constructor.
	 * The passed in array will *not* be cloned.
	 */
	protected CloneIterator(Mutator<E> mutator, Object... array) {
		super();
		this.iterator = new ArrayIterator<Object>(array);
		this.current = null;
		this.mutator = mutator;
		this.removeAllowed = false;
	}


	// ********** Iterator implementation **********

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public E next() {
		this.current = this.nestedNext();
		this.removeAllowed = true;
		return this.current;
	}

	public void remove() {
		if ( ! this.removeAllowed) {
			throw new IllegalStateException();
		}
		this.remove(this.current);
		this.removeAllowed = false;
	}


	// ********** internal methods **********

	/**
	 * The collection passed in during construction held Es,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (Object[]).
	 */
	@SuppressWarnings("unchecked")
	protected E nestedNext() {
		return (E) this.iterator.next();
	}

	/**
	 * Remove the specified element from the original collection.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building an <code>Mutator</code>.
	 */
	protected void remove(E e) {
		this.mutator.remove(e);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}


	//********** member interface **********

	/**
	 * Used by <code>CloneIterator</code> to remove
	 * elements from the original collection; since the iterator
	 * does not have direct access to the original collection.
	 */
	public interface Mutator<T> {

		/**
		 * Remove the specified object from the original collection.
		 */
		void remove(T current);


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
			// remove is not supported
			public void remove(Object current) {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return "CloneIterator.Mutator.ReadOnly"; //$NON-NLS-1$
			}
		}

	}

}
