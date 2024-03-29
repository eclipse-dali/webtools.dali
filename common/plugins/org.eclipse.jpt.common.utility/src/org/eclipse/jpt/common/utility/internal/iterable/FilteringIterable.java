/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.FilteringIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * A <code>FilteringIterable</code> wraps another {@link Iterable}
 * and uses a {@link Predicate} to determine which elements in the
 * nested iterable are to be returned by the iterable's iterator.
 * 
 * @param <E> the type of elements to be filtered
 * 
 * @see FilteringIterator
 */
public class FilteringIterable<E>
	implements Iterable<E>
{
	private final Iterable<? extends E> iterable;
	private final Predicate<? super E> predicate;


	/**
	 * Construct an iterable with the specified nested
	 * iterable and filter.
	 */
	public FilteringIterable(Iterable<? extends E> iterable, Predicate<? super E> predicate) {
		super();
		if ((iterable == null) || (predicate == null)) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
		this.predicate = predicate;
	}

	public Iterator<E> iterator() {
		return IteratorTools.filter(this.iterable.iterator(), this.predicate);
	}

	@Override
	public String toString() {
		return ListTools.arrayList(this).toString();
	}
}
