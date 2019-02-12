/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Wrap an iterator and synchronize all its methods so it can be safely shared
 * among multiple threads.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class SynchronizedIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;

	/** Object to synchronize on. */
	private final Object mutex;


	public SynchronizedIterator(Iterator<? extends E> iterator) {
		super();
		if (iterator == null) {
			throw new NullPointerException();
		}
		this.iterator = iterator;
		this.mutex = this;
	}

	public SynchronizedIterator(Iterator<? extends E> iterator, Object mutex) {
		super();
		if ((iterator == null) || (mutex == null)) {
			throw new NullPointerException();
		}
		this.iterator = iterator;
		this.mutex = mutex;
	}

	public boolean hasNext() {
		synchronized (this.mutex) {
			return this.iterator.hasNext();
		}
	}

	public E next() {
		synchronized (this.mutex) {
			return this.iterator.next();
		}
	}

	public void remove() {
		synchronized (this.mutex) {
			this.iterator.remove();
		}
	}

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return ObjectTools.toString(this, this.iterator);
		}
	}
}
