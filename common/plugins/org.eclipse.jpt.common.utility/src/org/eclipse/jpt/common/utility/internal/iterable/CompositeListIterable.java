/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterator.CompositeListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>CompositeListIterable</code> wraps a {@link ListIterable}
 * of {@link ListIterable}s and makes them appear to be a single
 * {@link ListIterable}.
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see CompositeListIterator
 * @see CompositeIterable
 * @see ReadOnlyCompositeListIterable
 */
public class CompositeListIterable<E>
	implements ListIterable<E>
{
	private final ListIterable<? extends ListIterable<E>> iterables;


	/**
	 * Construct a list iterable with the specified list of list iterables.
	 */
	public CompositeListIterable(ListIterable<? extends ListIterable<E>> iterables) {
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
		return new CompositeListIterator<E>(this.iterators());
	}

	/**
	 * list iterator of list iterators
	 */
	protected ListIterator<? extends ListIterator<E>> iterators() {
		Transformer<ListIterable<E>, ListIterator<E>> transformer = IterableTools.listIteratorTransformer();
		return IteratorTools.transform(this.iterables(), transformer);
	}

	/**
	 * list iterator of list iterables
	 */
	protected ListIterator<? extends ListIterable<E>> iterables() {
		return this.iterables.iterator();
	}

	@Override
	public String toString() {
		return ListTools.arrayList(this).toString();
	}
}
