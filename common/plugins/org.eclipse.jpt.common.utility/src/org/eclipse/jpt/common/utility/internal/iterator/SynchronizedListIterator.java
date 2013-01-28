/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

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


	public SynchronizedListIterator(ListIterator<E> listIterator) {
		super();
		if (listIterator == null) {
			throw new NullPointerException();
		}
		this.listIterator = listIterator;
		this.mutex = this;
	}

	public SynchronizedListIterator(ListIterator<E> listIterator, Object mutex) {
		super();
		if ((listIterator == null) || (mutex == null)) {
			throw new NullPointerException();
		}
		this.listIterator = listIterator;
		this.mutex = mutex;
	}

	public boolean hasNext() {
		synchronized (this.mutex) {
			return this.listIterator.hasNext();
		}
	}

	public E next() {
		synchronized (this.mutex) {
			return this.listIterator.next();
		}
	}

	public int nextIndex() {
		synchronized (this.mutex) {
			return this.listIterator.nextIndex();
		}
	}

	public boolean hasPrevious() {
		synchronized (this.mutex) {
			return this.listIterator.hasPrevious();
		}
	}

	public E previous() {
		synchronized (this.mutex) {
			return this.listIterator.previous();
		}
	}

	public int previousIndex() {
		synchronized (this.mutex) {
			return this.listIterator.previousIndex();
		}
	}

	public void remove() {
		synchronized (this.mutex) {
			this.listIterator.remove();
		}
	}

	public void add(E e) {
		synchronized (this.mutex) {
			this.listIterator.add(e);
		}
	}

	public void set(E e) {
		synchronized (this.mutex) {
			this.listIterator.set(e);
		}
	}

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return ObjectTools.toString(this, this.listIterator);
		}
	}
}
