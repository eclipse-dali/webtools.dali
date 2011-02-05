/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterables;

import java.util.ListIterator;

/**
 * A <code>ListIterable</code> simply extends {@link Iterable}
 * to return a {@link ListIterator} of type <code>E</code>.
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 */
public interface ListIterable<E>
	extends Iterable<E>
{
	/**
	 * Return a list iterator over a set of elements of type E.
	 */
	ListIterator<E> iterator();
}
