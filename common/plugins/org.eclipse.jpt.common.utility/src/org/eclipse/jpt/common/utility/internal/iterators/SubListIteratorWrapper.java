/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterators;

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Wrap a list iterator on elements of type <code>E1</code>, converting it into
 * a list iterator on elements of type <code>E2</code>. <em>Assume</em> the
 * wrapped list iterator returns only elements of type <code>E2</code>.
 * The result is a {@link ClassCastException} if this assumption is false.
 * <p>
 * This is a {@link LateralListIteratorWrapper} with more restrictive type
 * parameters.
 * 
 * @param <E1> input: the type of elements returned by the wrapped list iterator
 * @param <E2> output: the type of elements returned by the list iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper
 */
public class SubListIteratorWrapper<E1, E2 extends E1>
	extends LateralListIteratorWrapper<E1, E2>
{
	public SubListIteratorWrapper(List<E1> list) {
		super(list);
	}

	public SubListIteratorWrapper(ListIterable<E1> listIterable) {
		super(listIterable);
	}

	public SubListIteratorWrapper(ListIterator<E1> iterator) {
		super(iterator);
	}

	@Override
	public void set(E2 e) {
		// no explicit cast necessary
		this.listIterator.set(e);
	}

	@Override
	public void add(E2 e) {
		// no explicit cast necessary
		this.listIterator.add(e);
	}
}
