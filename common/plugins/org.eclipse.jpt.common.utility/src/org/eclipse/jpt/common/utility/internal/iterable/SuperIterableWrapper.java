/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;

/**
 * Wrap an iterable of elements of any sub-type of <code>E</code>, converting it into an
 * iterable of elements of type <code>E</code>. This shouldn't be a problem since there
 * is no way to add invalid elements to the iterable.
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 */
public class SuperIterableWrapper<E>
	implements Iterable<E>
{
	private final Iterable<E> iterable;


	@SuppressWarnings("unchecked")
	public SuperIterableWrapper(Iterable<? extends E> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		// this should be a safe cast - the iterator will only ever
		// return E (or a sub-type) from #next()
		this.iterable = (Iterable<E>) iterable;
	}

	public Iterator<E> iterator() {
		return this.iterable.iterator();
	}

	@Override
	public String toString() {
		return this.iterable.toString();
	}
}
