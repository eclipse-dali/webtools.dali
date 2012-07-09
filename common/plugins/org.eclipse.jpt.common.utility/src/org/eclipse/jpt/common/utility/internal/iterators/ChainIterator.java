/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterators;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * A <code>ChainIterator</code> provides a pluggable {@link Iterator}
 * that loops over a chain of arbitrarily linked objects. The chain
 * should be null-terminated (i.e. a call to the {@link #nextLink(Object)}
 * method should return <code>null</code> when it is passed the last
 * link of the chain).
 * To use, supply a starting link and supply a {@link Linker} or 
 * subclass <code>ChainIterator</code> and override the
 * {@link #nextLink(Object)} method.
 * The starting link will be the first object returned by the iterator.
 * If the starting link is <code>null</code>, the iterator will be empty.
 * Note this iterator does not support <code>null</code> elements.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.ChainIterable
 */
public class ChainIterator<E>
	implements Iterator<E>
{
	private E nextLink;
	private final Linker<E> linker;


	/**
	 * Construct an iterator with the specified starting link
	 * and a disabled linker.
	 * Use this constructor if you want to override the
	 * {@link #nextLink(Object)} method instead of building
	 * a {@link Linker}.
	 */
	public ChainIterator(E startLink) {
		this(startLink, Linker.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified starting link
	 * and linker.
	 */
	public ChainIterator(E startLink, Linker<E> linker) {
		super();
		this.nextLink = startLink;
		this.linker = linker;
	}

	public boolean hasNext() {
		return this.nextLink != null;
	}

	public E next() {
		if (this.nextLink == null) {
			throw new NoSuchElementException();
		}
		E result = this.nextLink;
		this.nextLink = this.nextLink(this.nextLink);
		return result;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the next link in the chain; null if there are no more links.
	 */
	protected E nextLink(E currentLink) {
		return this.linker.nextLink(currentLink);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nextLink);
	}


	//********** member interface **********

	/**
	 * Used by {@link ChainIterator} to link
	 * the elements in the chain.
	 */
	public interface Linker<T> {

		/**
		 * Return the next link in the chain; null if there are no more links.
		 */
		T nextLink(T currentLink);


		final class Null<S>
			implements Linker<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final Linker INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static <R> Linker<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			// simply return null, indicating the chain is ended
			public S nextLink(S currentLink) {
				return null;
			}
			@Override
			public String toString() {
				return StringTools.buildSingletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}


		final class Disabled<S>
			implements Linker<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final Linker INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R> Linker<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public S nextLink(S currentLink) {
				throw new UnsupportedOperationException();  // ChainIterator.nextLink(Object) was not implemented
			}
			@Override
			public String toString() {
				return StringTools.buildSingletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}