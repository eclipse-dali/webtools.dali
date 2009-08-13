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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

/**
 * A <code>SnapshotCloneIterable</code> returns an iterator on a "snapshot" of a
 * collection, allowing for concurrent access to the original collection. A
 * copy of the collection is created when the iterable is constructed.
 * As a result, the contents of the collection will be the same with
 * every call to <code>#iterable()</code>.
 * <p>
 * The original collection passed to the <code>SnapshotCloneIterable</code>'s
 * constructor should be thread-safe (e.g. java.util.Vector);
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, the iterator returned by a <code>SnapshotCloneIterable</code> does not
 * support the <code>#remove()</code> operation; this is because it does not
 * have access to the original collection. But if the <code>SnapshotCloneIterable</code>
 * is supplied with an <code>CloneIterator.Remover</code> it will delegate the
 * iterator's <code>#remove()</code> operation to the <code>Remover</code>.
 * Alternatively, a subclass can override the iterable's <code>#remove(Object)</code>
 * method.
 * <p>
 * This iterable is useful for multiple passes over a collection that should not
 * be changed (e.g. by another thread) between passes.
 * 
 * @see CloneIterator
 * @see LiveCloneIterable
 */
public class SnapshotCloneIterable<E>
	extends CloneIterable<E>
{
	private final Object[] array;


	// ********** constructors **********

	/**
	 * Construct a "snapshot" iterable for the specified collection.
	 * The <code>#remove()</code> method will not be supported
	 * by the iterator returned by <code>#iterable()</code>
	 * unless a subclass overrides the iterable's <code>#remove(Object)</code>
	 * method.
	 */
	public SnapshotCloneIterable(Collection<? extends E> collection) {
		super();
		this.array = collection.toArray();
	}

	/**
	 * Construct a "snapshot" iterable for the specified collection.
	 * The specified remover will be used by any generated iterators to
	 * remove objects from the original collection.
	 */
	public SnapshotCloneIterable(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
		super(remover);
		this.array = collection.toArray();
	}


	// ********** Iterable implementation **********

	public Iterator<E> iterator() {
		return new LocalCloneIterator<E>(this.remover, this.array);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, Arrays.toString(this.array));
	}


	// ********** clone iterator **********

	/**
	 * provide access to "internal" constructor
	 */
	protected static class LocalCloneIterator<E> extends CloneIterator<E> {
		protected LocalCloneIterator(Remover<E> remover, Object[] array) {
			super(remover, array);
		}
	}

}
