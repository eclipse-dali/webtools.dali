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
 * A <code>FixedCloneIterable</code> returns an iterator on a static copy of a
 * collection, allowing for concurrent access to the original collection. A
 * copy of the collection is created at construction time.
 * As a result, the contents of the collection will be the same with
 * every call to <code>#iterable()</code>.
 * <p>
 * The original collection passed to the <code>FixedCloneIterable</code>'s
 * constructor should be synchronized (e.g. java.util.Vector);
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, the iterator returned by a <code>FixedCloneIterable</code> does not
 * support the <code>#remove()</code> operation; this is because it does not
 * have access to the original collection. But if the <code>FixedCloneIterable</code>
 * is supplied with an <code>CloneIterator.Remover</code> it will delegate the
 * <code>#remove()</code> operation to the <code>Remover</code>.
 * Alternatively, a subclass can override the <code>#remove(Object)</code>
 * method.
 * <p>
 * This iterable is useful for multiple passes over a collection that should not
 * be changed between passes (e.g. by another thread).
 * 
 * @see CloneIterator
 * @see LiveCloneIterable
 */
public class StaticCloneIterable<E>
	extends CloneIterable<E>
{
	private final Object[] array;


	// ********** constructors **********

	/**
	 * Construct a static iterable for the specified collection.
	 * The <code>#remove()</code> method will not be supported
	 * by the <code>Iterator</code> returned by <code>#iterable()</code>
	 * unless a subclass overrides the <code>#remove(Object)</code>.
	 */
	public StaticCloneIterable(Collection<? extends E> collection) {
		super();
		this.array = collection.toArray();
	}

	/**
	 * Construct a static iterable for the specified collection.
	 * Use the specified remover to remove objects from the
	 * original collection.
	 */
	public StaticCloneIterable(Collection<? extends E> collection, CloneIterator.Remover<E> remover) {
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
