/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterator;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A <code>QueueIterator</code> provides an {@link Iterator}
 * for a {@link Queue} of objects of type <code>E</code>. The queue's elements
 * are {@link Queue#dequeue() dequeued}" as the iterator returns them with
 * calls to {@link #next()}.
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see Queue
 * @see org.eclipse.jpt.common.utility.internal.iterable.QueueIterable
 */
public class QueueIterator<E>
	implements Iterator<E>
{
	private final Queue<E> queue;


	/**
	 * Construct an iterator for the specified queue.
	 */
	public QueueIterator(Queue<E> queue) {
		super();
		this.queue = queue;
	}

	public boolean hasNext() {
		return ! this.queue.isEmpty();
	}

	public E next() {
		return this.queue.dequeue();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.queue);
	}
}
