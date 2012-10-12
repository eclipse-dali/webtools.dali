/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterator.LateralIteratorWrapper;

/**
 * Wrap an iterable of elements of type <code>E1</code>, converting it into an
 * iterable of elements of type <code>E2</code>. <em>Assume</em> the wrapped
 * iterable contains only elements of type <code>E2</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * 
 * @param <E1> input: the type of elements contained by the wrapped iterable
 * @param <E2> output: the type of elements returned by the iterable's iterators
 * 
 * @see LateralIteratorWrapper
 * @see SubIterableWrapper
 */
public class LateralIterableWrapper<E1, E2>
	implements Iterable<E2>
{
	private final Iterable<E1> iterable;


	public LateralIterableWrapper(Iterable<E1> iterable) {
		super();
		if (iterable == null) {
			throw new NullPointerException();
		}
		this.iterable = iterable;
	}

	public Iterator<E2> iterator() {
		return new LateralIteratorWrapper<E1, E2>(this.iterable.iterator());
	}

	@Override
	public String toString() {
		return this.iterable.toString();
	}
}
