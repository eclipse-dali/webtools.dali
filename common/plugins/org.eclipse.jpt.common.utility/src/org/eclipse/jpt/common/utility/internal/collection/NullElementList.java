/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.NullElementIterator;
import org.eclipse.jpt.common.utility.internal.iterator.NullElementListIterator;

/**
 * A <em>read-only</em> "null element" list.
 * @param <E> the type of elements maintained by the list
 */
public class NullElementList<E>
	extends AbstractRepeatingElementList<E>
{
	/**
	 * Construct a <em>read-only</em> list with the specified number of
	 * <code>null</code>s in it.
	 */
	public NullElementList(int size) {
		super(size);
	}

	@Override
	protected Iterator<E> iterator(int iteratorSize) {
		return new NullElementIterator<E>(iteratorSize);
	}

	@Override
	protected ListIterator<E> listIterator_(int iteratorSize) {
		return new NullElementListIterator<E>(iteratorSize);
	}

	@Override
	protected List<E> subList(int subListSize) {
		return new NullElementList<E>(subListSize);
	}

	@Override
	protected E getElement() {
		return null;
	}

	private static final long serialVersionUID = 1L;
}
