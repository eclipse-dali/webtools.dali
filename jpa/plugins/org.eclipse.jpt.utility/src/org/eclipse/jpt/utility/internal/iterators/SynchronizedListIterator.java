/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap a list iterator and synchronize all its methods so it can be safely shared
 * among multiple threads.
 * 
 * @param <E> the type of elements returned by the iterator
 */
public class SynchronizedListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<E> listIterator;

	/** Object to synchronize on. */
	private final Object mutex;


	public SynchronizedListIterator(List<E> list) {
		this(list.listIterator());
	}

	public SynchronizedListIterator(List<E> list, Object mutex) {
		this(list.listIterator(), mutex);
	}

	public SynchronizedListIterator(ListIterator<E> listIterator) {
		super();
		this.listIterator = listIterator;
		this.mutex = this;
	}

	public SynchronizedListIterator(ListIterator<E> listIterator, Object mutex) {
		super();
		this.listIterator = listIterator;
		this.mutex = mutex;
	}

	public synchronized boolean hasNext() {
		synchronized (this.mutex) {
			return this.listIterator.hasNext();
		}
	}

	public synchronized E next() {
		synchronized (this.mutex) {
			return this.listIterator.next();
		}
	}

	public synchronized int nextIndex() {
		synchronized (this.mutex) {
			return this.listIterator.nextIndex();
		}
	}

	public synchronized boolean hasPrevious() {
		synchronized (this.mutex) {
			return this.listIterator.hasPrevious();
		}
	}

	public synchronized E previous() {
		synchronized (this.mutex) {
			return this.listIterator.previous();
		}
	}

	public synchronized int previousIndex() {
		synchronized (this.mutex) {
			return this.listIterator.previousIndex();
		}
	}

	public synchronized void remove() {
		synchronized (this.mutex) {
			this.listIterator.remove();
		}
	}

	public synchronized void add(E e) {
		synchronized (this.mutex) {
			this.listIterator.add(e);
		}
	}

	public synchronized void set(E e) {
		synchronized (this.mutex) {
			this.listIterator.set(e);
		}
	}

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return StringTools.buildToStringFor(this, this.listIterator);
		}
	}

}
