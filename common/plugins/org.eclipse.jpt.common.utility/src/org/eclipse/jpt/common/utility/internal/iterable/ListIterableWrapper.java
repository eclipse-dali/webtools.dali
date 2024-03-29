/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * List iterable wrapper that can have its wrapped list iterable changed,
 * allowing a client to change a previously-supplied iterable's
 * behavior mid-stream.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * @see #setIterable(ListIterable)
 */
public class ListIterableWrapper<E>
	implements ListIterable<E>
{
	protected volatile ListIterable<E> iterable;

	public ListIterableWrapper(ListIterable<E> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public ListIterator<E> iterator() {
		return this.iterable.iterator();
	}

	public void setIterable(ListIterable<E> iterable) {
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.iterable);
	}
}
