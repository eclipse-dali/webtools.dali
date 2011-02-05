/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;

/**
 * A <code>LiveCloneListIterable</code> returns a list iterator on a current
 * copy of a list, allowing for concurrent access to the original list. A
 * copy of the list is created every time {@link #iterator()} is
 * called. As a result, the contents of the list can be different with
 * each call to {@link #iterator()} (i.e. it is "live").
 * <p>
 * The original list passed to the <code>LiveCloneListIterable</code>'s
 * constructor should be thread-safe (e.g. {@link java.util.Vector});
 * otherwise you run the risk of a corrupted list.
 * <p>
 * By default, the list iterator returned by a <code>LiveCloneListIterable</code>
 * does not support the modify operations; this is because it does not
 * have access to the original list. But if the <code>LiveCloneListIterable</code>
 * is supplied with an {@link CloneListIterator.Mutator} it will delegate the
 * modify operations to the <code>Mutator</code>.
 * Alternatively, a subclass can override the list iterable's mutation
 * methods.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see CloneListIterator
 * @see SnapshotCloneListIterable
 * @see LiveCloneIterable
 */
public class LiveCloneListIterable<E>
	extends CloneListIterable<E>
{
	private final List<? extends E> list;


	// ********** constructors **********

	/**
	 * Construct a "live" list iterable for the specified list.
	 * The {@link ListIterator} mutation operations will not be supported
	 * by the list iterator returned by {@link #iterator()}
	 * unless a subclass overrides the iterable's mutation
	 * methods.
	 */
	public LiveCloneListIterable(List<? extends E> list) {
		super();
		this.list = list;
	}

	/**
	 * Construct a "live" list iterable for the specified list.
	 * The specified mutator will be used by any generated list iterators to
	 * modify the original list.
	 */
	public LiveCloneListIterable(List<? extends E> list, CloneListIterator.Mutator<E> mutator) {
		super(mutator);
		this.list = list;
	}


	// ********** ListIterable implementation **********

	public ListIterator<E> iterator() {
		return new CloneListIterator<E>(this.list, this.mutator);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.list);
	}

}
