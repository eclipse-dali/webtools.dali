/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Iterable wrapper that can have its wrapped iterable changed,
 * allowing a client to change a previously-supplied iterable's
 * behavior mid-stream.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * @see #setIterable(Iterable)
 */
public class IterableWrapper<E>
	implements Iterable<E>
{
	protected volatile Iterable<E> iterable;

	public IterableWrapper(Iterable<E> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public Iterator<E> iterator() {
		return this.iterable.iterator();
	}

	public void setIterable(Iterable<E> iterable) {
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
