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

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap an iterator and synchronize all its methods so it can be safely shared
 * among multiple threads.
 */
public class SynchronizedIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;


	public SynchronizedIterator(Iterable<? extends E> iterable) {
		this(iterable.iterator());
	}

	public SynchronizedIterator(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
	}

	public synchronized boolean hasNext() {
		return this.iterator.hasNext();
	}

	public synchronized E next() {
		return this.iterator.next();
	}

	public synchronized void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
