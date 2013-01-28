/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;

/**
 * Wrap an iterator on elements of type <code>E1</code>, converting it into an
 * iterator on elements of type <code>E2</code>. <em>Assume</em> the wrapped
 * iterator returns only elements of type <code>E2</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * <p>
 * This is a {@link LateralIteratorWrapper} with more restrictive type
 * parameters.
 * 
 * @param <E1> input: the type of elements returned by the wrapped iterator
 * @param <E2> output: the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper
 */
public class SubIteratorWrapper<E1, E2 extends E1>
	extends LateralIteratorWrapper<E1, E2>
{
	public SubIteratorWrapper(Iterator<E1> iterator) {
		super(iterator);
	}
}
