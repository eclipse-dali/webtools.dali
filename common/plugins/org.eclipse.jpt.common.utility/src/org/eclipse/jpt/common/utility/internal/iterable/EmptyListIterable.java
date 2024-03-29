/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.io.Serializable;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyListIterator;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * An <code>EmptyListIterable</code> is just that.
 * Maybe just a touch better-performing than {@link java.util.Collections#EMPTY_LIST}
 * since we don't create a new {@link java.util.Iterator} every time
 * {@link #iterator()} is called. (Not sure why they do that....)
 * 
 * @param <E> the type of elements returned by the list iterable's list iterator
 * 
 * @see EmptyListIterator
 * @see EmptyIterable
 */
public final class EmptyListIterable<E>
	implements ListIterable<E>, Serializable
{
	// singleton
	@SuppressWarnings("rawtypes")
	private static final ListIterable INSTANCE = new EmptyListIterable();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ListIterable<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyListIterable() {
		super();
	}

	public ListIterator<E> iterator() {
		return EmptyListIterator.instance();
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
