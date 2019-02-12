/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

/**
 * A <code>RepeatingElementIterator</code> provides an {@link java.util.Iterator}
 * that returns a specific object a specific number of times.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.collection.RepeatingElementList
 */
public class RepeatingElementIterator<E>
	extends AbstractRepeatingElementIterator<E>
{
	private final E element;


	public RepeatingElementIterator(E element, int size) {
		super(size);
		this.element = element;
	}

	@Override
	protected E getElement() {
		return this.element;
	}
}
