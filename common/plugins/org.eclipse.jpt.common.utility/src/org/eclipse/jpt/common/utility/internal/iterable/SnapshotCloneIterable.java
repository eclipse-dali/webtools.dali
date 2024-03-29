/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.iterator.CloneIterator;

/**
 * A <code>SnapshotCloneIterable</code> returns an iterator on a "snapshot" of a
 * collection, allowing for concurrent access to the original collection. A
 * copy of the collection is created when the iterable is constructed.
 * As a result, the contents of the collection will be the same with
 * every call to {@link #iterator()}.
 * <p>
 * The original collection passed to the <code>SnapshotCloneIterable</code>'s
 * constructor should be thread-safe (e.g. {@link java.util.Vector});
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, the iterator returned by a <code>SnapshotCloneIterable</code> does not
 * support the {@link Iterator#remove()} operation; this is because it does not
 * have access to the original collection. But if the <code>SnapshotCloneIterable</code>
 * is supplied with an {@link Closure remove closure} it will delegate the
 * {@link Iterator#remove()} operation to the command.
 * <p>
 * This iterable is useful for multiple passes over a collection that should not
 * be changed (e.g. by another thread) between passes.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see CloneIterator
 * @see LiveCloneIterable
 * @see SnapshotCloneListIterable
 */
public class SnapshotCloneIterable<E>
	extends CloneIterable<E>
{
	private final Object[] array;


	/**
	 * Construct a "snapshot" iterable for the specified collection.
	 * The specified command will be used by any generated iterators to
	 * remove objects from the original collection.
	 */
	public SnapshotCloneIterable(Collection<? extends E> collection, Closure<? super E> removeClosure) {
		super(removeClosure);
		this.array = collection.toArray();
	}

	public Iterator<E> iterator() {
		return new LocalCloneIterator<E>(this.array, this.removeClosure);
	}

	@Override
	public String toString() {
		return Arrays.toString(this.array);
	}


	// ********** clone iterator **********

	/**
	 * provide access to "internal" constructor
	 */
	protected static class LocalCloneIterator<E> extends CloneIterator<E> {
		protected LocalCloneIterator(Object[] array, Closure<? super E> removeClosure) {
			super(array, removeClosure);
		}
	}
}
