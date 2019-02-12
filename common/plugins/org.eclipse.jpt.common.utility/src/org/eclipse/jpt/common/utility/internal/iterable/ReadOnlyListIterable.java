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

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.ReadOnlyListIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

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
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.listIterable = iterable;
	}

	public ListIterator<E> iterator() {
		return IteratorTools.readOnly(this.listIterable.iterator());
	}

	@Override
	public String toString() {
		return this.listIterable.toString();
	}
}
