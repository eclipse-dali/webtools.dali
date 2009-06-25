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
 */
public class SynchronizedListIterator<E>
	implements ListIterator<E>
{
	private final ListIterator<E> listIterator;


	public SynchronizedListIterator(List<E> list) {
		this(list.listIterator());
	}

	public SynchronizedListIterator(ListIterator<E> listIterator) {
		super();
		this.listIterator = listIterator;
	}

	public synchronized boolean hasNext() {
		return this.listIterator.hasNext();
	}

	public synchronized E next() {
		return this.listIterator.next();
	}

	public synchronized int nextIndex() {
		return this.listIterator.nextIndex();
	}

	public synchronized boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	public synchronized E previous() {
		return this.listIterator.previous();
	}

	public synchronized int previousIndex() {
		return this.listIterator.previousIndex();
	}

	public synchronized void remove() {
		this.listIterator.remove();
	}

	public synchronized void add(E e) {
		this.listIterator.add(e);
	}

	public synchronized void set(E e) {
		this.listIterator.set(e);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.listIterator);
	}

}
