/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.io.Serializable;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;

/**
 * An <code>EmptyIterable</code> is just that.
 * Maybe just a touch better-performing than {@link java.util.Collections#EMPTY_SET}
 * since we don't create a new {@link Iterator} every time {@link #iterator()} is called.
 * (Not sure why they do that....)
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see EmptyIterator
 * @see EmptyListIterable
 */
public final class EmptyIterable<E>
	implements Iterable<E>, Serializable
{
	// singleton
	@SuppressWarnings("rawtypes")
	private static final Iterable INSTANCE = new EmptyIterable();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyIterable() {
		super();
	}

	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	@Override
	public String toString() {
		return "[]"; //$NON-NLS-1$
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
