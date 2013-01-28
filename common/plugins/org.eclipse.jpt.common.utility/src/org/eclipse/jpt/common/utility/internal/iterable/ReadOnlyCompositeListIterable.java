/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>ReadOnlyCompositeListIterable</code> wraps a {@link ListIterable}
 * of {@link ListIterable}s and makes them appear to be a single
 * read-only {@link ListIterable}. A read-only composite list
 * iterable is more flexible than a normal composite list iterable when it
 * comes to the element types of the nested list iterables.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterator.ReadOnlyCompositeListIterator
 * @see CompositeListIterable
 */
public class ReadOnlyCompositeListIterable<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends ListIterable<? extends E>> iterables;


	/**
	 * Construct a list iterable with the specified list of list iterables.
	 */
	public ReadOnlyCompositeListIterable(ListIterable<? extends ListIterable<? extends E>> iterables) {
		super();
		if (iterables == null) {
			throw new NullPointerException();
		}
		this.iterables = iterables;
	}

	/**
	 * combined list iterators
	 */
	public ListIterator<E> iterator() {
		return IteratorTools.readOnlyCompositeListIterator(this.iterators());
	}

	/**
	 * list iterator of list iterators
	 */
	protected ListIterator<? extends ListIterator<? extends E>> iterators() {
		Transformer<ListIterable<? extends E>, ListIterator<? extends E>> transformer = IterableTools.readOnlyListIteratorTransformer();
		return IteratorTools.transform(this.iterables(), transformer);
	}

	/**
	 * list iterator of list iterables
	 */
	protected ListIterator<? extends ListIterable<? extends E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return ListTools.list(this).toString();
	}
}
