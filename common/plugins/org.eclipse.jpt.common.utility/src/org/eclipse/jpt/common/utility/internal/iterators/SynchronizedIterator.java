/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterators;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.StringTools;

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


	public SynchronizedIterator(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	public SynchronizedIterator(Iterable<? extends E> iterable, Object mutex) {
		this(iterable.iterator(), mutex);
	}

	public SynchronizedIterator(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
		this.mutex = this;
	}

	public SynchronizedIterator(Iterator<? extends E> iterator, Object mutex) {
		super();
		this.iterator = iterator;
		this.mutex = mutex;
	}

	public synchronized boolean hasNext() {
		synchronized (this.mutex) {
			return this.iterator.hasNext();
		}
	}

	public synchronized E next() {
		synchronized (this.mutex) {
			return this.iterator.next();
		}
	}

	public synchronized void remove() {
		synchronized (this.mutex) {
			this.iterator.remove();
		}
	}

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return StringTools.buildToStringFor(this, this.iterator);
		}
	}

}
