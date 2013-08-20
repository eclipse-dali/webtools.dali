/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.iterator.CloneIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

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
 * is supplied with an {@link Closure remove closure} it will delegate the
 * {@link Iterator#remove()} operation to the command.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see CloneIterator
 * @see SnapshotCloneIterable
 * @see LiveCloneListIterable
 */
public class LiveCloneIterable<E>
	extends CloneIterable<E>
{
	private final Collection<? extends E> collection;


	/**
	 * Construct a "live" iterable for the specified collection.
	 * The specified command will be used by any generated iterators to
	 * remove objects from the original collection.
	 */
	public LiveCloneIterable(Collection<? extends E> collection, Closure<? super E> removeClosure) {
		super(removeClosure);
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
	}


	// ********** Iterable implementation **********

	public Iterator<E> iterator() {
		return IteratorTools.clone(this.collection, this.removeClosure);
	}

	@Override
	public String toString() {
		return this.collection.toString();
	}
}
