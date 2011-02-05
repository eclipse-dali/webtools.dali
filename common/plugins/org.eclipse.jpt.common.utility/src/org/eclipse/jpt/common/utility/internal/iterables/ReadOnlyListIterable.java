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

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyListIterator;

/**
 * A <code>ReadOnlyListIterable</code> wraps another {@link ListIterable}
 * and returns a read-only {@link ListIterator}.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see ReadOnlyListIterator
 * @see ReadOnlyIterable
 */
public class ReadOnlyListIterable<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends E> listIterable;


	/**
	 * Construct a list iterable the returns a read-only list iterator on the elements
	 * in the specified list iterable.
	 */
	public ReadOnlyListIterable(ListIterable<? extends E> iterable) {
		super();
		this.listIterable = iterable;
	}

	public ListIterator<E> iterator() {
		return new ReadOnlyListIterator<E>(this.listIterable);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listIterable);
	}

}
