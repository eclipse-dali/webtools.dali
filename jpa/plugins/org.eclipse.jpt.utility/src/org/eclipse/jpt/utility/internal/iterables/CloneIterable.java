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
 * A <code>CloneIterable</code> returns an iterator on a current copy of a
 * collection, allowing for concurrent access to the original collection. A
 * copy of the collection is created every time <code>#iterable()</code> is
 * called. As a result, the contents of the collection can be different with
 * each call to <code>#iterable()</code>.
 * <p>
 * The original collection passed to the <code>CloneIterabler</code>'s
 * constructor should be synchronized (e.g. java.util.Vector);
 * otherwise you run the risk of a corrupted collection.
 * <p>
 * By default, the iterator returned by a <code>CloneIterable</code> does not
 * support the <code>#remove()</code> operation; this is because it does not
 * have access to the original collection. But if the <code>CloneIterable</code>
 * is supplied with an <code>CloneIterator.Mutator</code> it will delegate the
 * <code>#remove()</code> operation to the <code>Mutator</code>.
 * 
 * @see CloneIterator
 * @see FixedCloneIterable
 */
public class CloneIterable<E>
	implements Iterable<E>
{
	private final Collection<? extends E> collection;
	private final CloneIterator.Mutator<E> mutator;


	// ********** constructors **********

	/**
	 * Construct a live iterable for the specified collection.
	 * The <code>#remove()</code> method will not be supported
	 * by the <code>Iterator</code> returned by <code>#iterable()</code>.
	 */
	public CloneIterable(E[] array) {
		this(Arrays.asList(array));
	}

	/**
	 * Construct a live iterable for the specified collection.
	 * The <code>#remove()</code> method will not be supported
	 * by the <code>Iterator</code> returned by <code>#iterable()</code>.
	 */
	public CloneIterable(Collection<? extends E> collection) {
		this(collection, CloneIterator.Mutator.ReadOnly.<E>instance());
	}

	/**
	 * Construct a live iterable for the specified collection.
	 * Use the specified mutator to remove objects from the
	 * original collection.
	 */
	public CloneIterable(Collection<? extends E> collection, CloneIterator.Mutator<E> mutator) {
		super();
		this.collection = collection;
		this.mutator = mutator;
	}


	// ********** Iterable implementation **********

	public Iterator<E> iterator() {
		return new CloneIterator<E>(this.collection, this.mutator);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
