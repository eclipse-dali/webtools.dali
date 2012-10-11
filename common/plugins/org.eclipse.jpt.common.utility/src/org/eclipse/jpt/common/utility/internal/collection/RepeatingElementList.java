/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
