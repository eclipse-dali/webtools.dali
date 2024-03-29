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

import org.eclipse.jpt.common.utility.internal.iterator.RepeatingElementIterator;
import org.eclipse.jpt.common.utility.internal.iterator.RepeatingElementListIterator;

/**
 * A <em>read-only</em> repeating element list.
 * @param <E> the type of elements maintained by the list
 */
public class RepeatingElementList<E>
	extends AbstractRepeatingElementList<E>
{
	private final E element;

	/**
	 * Construct a <em>read-only</em> list with the specified number of
	 * elements in it.
	 */
	public RepeatingElementList(E element, int size) {
		super(size);
		this.element = element;
	}

	@Override
	protected Iterator<E> iterator(int iteratorSize) {
		return new RepeatingElementIterator<E>(this.element, iteratorSize);
	}

	@Override
	protected ListIterator<E> listIterator_(int iteratorSize) {
		return new RepeatingElementListIterator<E>(this.element, iteratorSize);
	}

	@Override
	protected List<E> subList(int subListSize) {
		return new RepeatingElementList<E>(this.element, subListSize);
	}

	@Override
	protected E getElement() {
		return this.element;
	}

	private static final long serialVersionUID = 1L;
}
