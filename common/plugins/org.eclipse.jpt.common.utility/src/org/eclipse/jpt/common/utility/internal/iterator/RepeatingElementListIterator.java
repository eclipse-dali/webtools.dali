/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

/**
 * A <code>RepeatingElementListIterator</code> provides a {@link java.util.ListIterator}
 * that returns a specific object a specific number of times.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.collection.RepeatingElementList
 */
public class RepeatingElementListIterator<E>
	extends AbstractRepeatingElementListIterator<E>
{
	private final E element;


	public RepeatingElementListIterator(E element, int size) {
		super(size);
		this.element = element;
	}

	@Override
	protected E getElement() {
		return this.element;
	}
}
