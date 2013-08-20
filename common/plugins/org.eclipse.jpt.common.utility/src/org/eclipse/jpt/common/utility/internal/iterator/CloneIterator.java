/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>CloneIterator</code> iterates over a copy of a collection,
 * allowing for concurrent access to the original collection.
 * <p>
 * The original collection passed to the <code>CloneIterator</code>'s
 * constructor should be synchronized (e.g. {@link java.util.Vector});
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, a <code>CloneIterator</code> does not support the
 * {@link #remove()} operation; this is because it does not have
 * access to the original collection. But if the <code>CloneIterator</code>
 * is supplied with an {@link Closure} it will delegate the
 * {@link #remove()} operation to the {@link Closure}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#cloneLive(Collection)
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#cloneLive(Collection, Closure)
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#cloneSnapshot(Collection)
 * @see org.eclipse.jpt.common.utility.internal.iterable.IterableTools#cloneSnapshot(Collection, Closure)
 */
public class CloneIterator<E>
	implements Iterator<E>
{
	private final Iterator<Object> iterator;
	private E current;
	private final Closure<? super E> removeClosure;
	private boolean removeAllowed;


	/**
	 * Construct an iterator on a copy of the specified collection.
	 * Use the specified command to remove objects from the
	 * original collection.
	 */
	public CloneIterator(Collection<? extends E> collection, Closure<? super E> removeClosure) {
		this(collection.toArray(), removeClosure);
	}

	/**
	 * Internal constructor used by subclasses.
	 */
	protected CloneIterator(Object[] array, Closure<? super E> removeClosure) {
		super();
		if (removeClosure == null) {
			throw new NullPointerException();
		}
		this.iterator = IteratorTools.iterator(array);
		this.current = null;
		this.removeClosure = removeClosure;
		this.removeAllowed = false;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/**
	 * The collection passed in during construction held elements of type <code>E</code>,
	 * so this cast is not a problem. We need this cast because
	 * all the elements of the original collection were copied into
	 * an object array (<code>Object[]</code>).
	 */
	public E next() {
		@SuppressWarnings("unchecked")
		E next = (E) this.iterator.next();
		this.current = next;
		this.removeAllowed = true;
		return this.current;
	}

	public void remove() {
		if ( ! this.removeAllowed) {
			throw new IllegalStateException();
		}
		this.removeClosure.execute(this.current);
		this.removeAllowed = false;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
