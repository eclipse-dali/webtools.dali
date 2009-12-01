/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

/**
 * A <code>LiveCloneIterable</code> returns an iterator on a current copy of a
 * collection, allowing for concurrent access to the original collection. A
 * copy of the collection is created every time {@link #iterator()} is
 * called. As a result, the contents of the collection can be different with
 * each call to {@link #iterator()} (i.e. it is "live").
 * <p>
 * The original collection passed to the <code>LiveCloneIterable</code>'s
 * constructor should be thread-safe (e.g. {@link java.util.Vector});
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, the iterator returned by a <code>LiveCloneIterable</code> does not
 * support the {@link Iterator#remove()} operation; this is because it does not
 * have access to the original collection. But if the <code>LiveCloneIterable</code>
 * is supplied with an {@link CloneIterator.Remover} it will delegate the
 * {@link Iterator#remove()} operation to the <code>Remover</code>.
 * Alternatively, a subclass can override the iterable's {@link #remove(Object)}
 * method.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see CloneIterator
 * @see SnapshotCloneIterable
 */
public class LiveCloneIterable<E>
	extends CloneIterable<E>
{
	private final Collection<? extends E> collection;


	// ********** constructors **********

	/**
	 * Construct a "live" iterable for the specified collection.
	 * The {@link Iterator#remove()} operation will not be supported
	 * by the iterator returned by {@link #iterator()}
	 * unless a subclass overrides the iterable's {@link #remove(Object)}
	 * method.
	 */
	public LiveCloneIterable(Collection<? extends E> collection) {
		super();
		this.collection = collection;
	}

	/**
	 * Construct a "live" iterable for the specified collection.
	 * The specified remover will be used by any generated iterators to
	 * remove objects from the original collection.
	 */
	public LiveCloneIterable(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
		super(remover);
		this.collection = collection;
	}


	// ********** Iterable implementation **********

	public Iterator<E> iterator() {
		return new CloneIterator<E>(this.collection, this.remover);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.collection);
	}

}
