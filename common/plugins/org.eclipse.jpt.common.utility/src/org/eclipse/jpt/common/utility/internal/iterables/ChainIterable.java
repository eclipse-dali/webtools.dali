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
import org.eclipse.jpt.common.utility.internal.iterators.ChainIterator;

/**
 * A <code>ChainIterable</code> provides a pluggable {@link Iterable}
 * that loops over a chain of arbitrarily linked objects. The chain
 * should be null-terminated (i.e. a call to the {@link #nextLink(Object)}
 * method should return <code>null</code> when it is passed the last
 * link of the chain).
 * To use, supply a starting link and supply a {@link ChainIterator.Linker} or
 * subclass <code>ChainIterable</code> and override the
 * {@link #nextLink(Object)} method.
 * The starting link will be the first object returned by the iterable's iterator.
 * If the starting link is <code>null</code>, the iterable will be empty.
 * Note this iterable does not support <code>null</code> elements.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see ChainIterator
 */
public class ChainIterable<E>
	implements Iterable<E>
{
	private final E startLink;
	private final ChainIterator.Linker<E> linker;


	/**
	 * Construct an iterable with the specified starting link
	 * and a default linker that calls back to the iterable.
	 * Use this constructor if you want to override the
	 * {@link #nextLink(Object)} method instead of building
	 * a {@link ChainIterator.Linker}.
	 */
	public ChainIterable(E startLink) {
		super();
		this.startLink = startLink;
		this.linker = this.buildDefaultLinker();
	}

	/**
	 * Construct an iterator with the specified starting link
	 * and linker.
	 */
	public ChainIterable(E startLink, ChainIterator.Linker<E> linker) {
		super();
		this.startLink = startLink;
		this.linker = linker;
	}

	protected ChainIterator.Linker<E> buildDefaultLinker() {
		return new DefaultLinker();
	}

	public Iterator<E> iterator() {
		return new ChainIterator<E>(this.startLink, this.linker);
	}

	/**
	 * Return the next link in the chain; null if there are no more links.
	 * <p>
	 * This method can be overridden by a subclass as an alternative to
	 * building a {@link ChainIterator.Linker}.
	 */
	protected E nextLink(@SuppressWarnings("unused") E currentLink) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.startLink);
	}


	//********** default linker **********

	protected class DefaultLinker implements ChainIterator.Linker<E> {
		public E nextLink(E currentLink) {
			return ChainIterable.this.nextLink(currentLink);
		}
	}

}
