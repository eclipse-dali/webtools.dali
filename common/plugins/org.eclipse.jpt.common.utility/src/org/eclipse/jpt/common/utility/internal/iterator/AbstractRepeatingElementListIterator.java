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

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An <code>AbstractRepeatingElementListIterator</code> provides a {@link ListIterator}
 * that returns a single element a specific number of times.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see org.eclipse.jpt.common.utility.internal.collection.AbstractRepeatingElementList
 */
public abstract class AbstractRepeatingElementListIterator<E>
	extends AbstractRepeatingElementIterator<E>
	implements ListIterator<E>
{
	protected AbstractRepeatingElementListIterator(int size) {
		super(size);
	}

	public int nextIndex() {
		return this.cursor;
	}
	
	public int previousIndex() {
		return this.cursor - 1;
	}
	
	public boolean hasPrevious() {
		return this.cursor != 0;
	}
	
	public E previous() {
		if (this.hasPrevious()) {
			this.cursor--;
			return this.getElement();
		}
		throw new NoSuchElementException();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}
	
	public void set(E e) {
		throw new UnsupportedOperationException();
	}
}
