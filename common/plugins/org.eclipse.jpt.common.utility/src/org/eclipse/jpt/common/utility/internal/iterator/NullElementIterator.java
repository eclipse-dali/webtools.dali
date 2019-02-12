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
 * A <code>NullElementIterator</code> provides an {@link java.util.Iterator}
 * that returns a <code>null</code> a specific number of times.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.collection.NullElementList
 */
public class NullElementIterator<E>
	extends AbstractRepeatingElementIterator<E>
{
	/**
	 * Construct an iterator for the specified number of <code>null</code>s.
	 */
	public NullElementIterator(int size) {
		super(size);
	}

	@Override
	protected E getElement() {
		return null;
	}
}
