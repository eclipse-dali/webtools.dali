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

import java.util.List;
import java.util.ListIterator;

/**
 * A <code>ListListIterable</code> adapts a {@link List}
 * to the {@link ListIterable} interface.
 * 
 * @param <E> the type of elements returned by the iterable's iterators
 */
public class ListListIterable<E>
	implements ListIterable<E>
{
	private final List<E> list;

	public ListListIterable(List<E> list) {
		super();
		this.list = list;
	}

	public ListIterator<E> iterator() {
		return this.list.listIterator();
	}

}
