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

import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.ReadOnlyIterator;

/**
 * A <code>ReadOnlyIterable</code> wraps another {@link Iterable}
 * and returns a read-only {@link Iterator}.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see ReadOnlyIterator
 * @see ReadOnlyListIterable
 */
public class ReadOnlyIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> iterable;


	/**
	 * Construct an iterable the returns a read-only iterator on the elements
	 * in the specified iterable.
	 */
	public ReadOnlyIterable(Iterable<? extends E> iterable) {
		super();
		this.iterable = iterable;
	}

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.iterable);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterable);
	}

}
