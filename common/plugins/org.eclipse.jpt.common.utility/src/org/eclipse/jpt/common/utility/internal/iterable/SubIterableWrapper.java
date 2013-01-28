/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import org.eclipse.jpt.common.utility.internal.iterator.SubIteratorWrapper;

/**
 * Wrap an iterable of elements of type <code>E1</code>, converting it into an
 * iterable of elements of type <code>E2</code>. <em>Assume</em> the wrapped
 * iterable contains only elements of type <code>E2</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * <p>
 * This is like a {@link LateralIterableWrapper} but with more restrictive type
 * parameters.
 * 
 * @param <E1> input: the type of elements contained by the wrapped iterable
 * @param <E2> output: the type of elements returned by the iterable's iterators
 * 
 * @see SubIteratorWrapper
 */
public class SubIterableWrapper<E1, E2 extends E1>
	extends LateralIterableWrapper<E1, E2>
{
	public SubIterableWrapper(Iterable<E1> iterable) {
		super(iterable);
	}
}
