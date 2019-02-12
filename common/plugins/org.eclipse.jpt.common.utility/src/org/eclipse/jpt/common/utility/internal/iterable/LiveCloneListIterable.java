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

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

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
 * is supplied with an {@link org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator.Adapter adapter}
 * it will delegate the {@link ListIterator} mutation operations to the adapter.
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


	/**
	 * Construct a "live" list iterable for the specified list.
	 * The specified mutator will be used by any generated list iterators to
	 * modify the original list.
	 */
	public LiveCloneListIterable(List<? extends E> list, CloneListIterator.Adapter<E> adapter) {
		super(adapter);
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
	}

	public ListIterator<E> iterator() {
		return IteratorTools.clone(this.list, this.adapter);
	}

	@Override
	public String toString() {
		return this.list.toString();
	}
}
