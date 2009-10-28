/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterables;

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.Queue;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.QueueIterator;

/**
 * A <code>QueueIterable</code> provides an {@link Iterable}
 * for a {@link Queue} of objects of type <code>E</code>. The queue's elements
 * are {@link Queue#dequeue() dequeue}d" as the iterable's iterator returns
 * them with calls to {@link Iterator#next()}.
 * 
 * @param <E> the type of elements returned by the iterable's iterator
 * 
 * @see Queue
 * @see QueueIterator
 */
public class QueueIterable<E>
	implements Iterable<E>
{
	private final Queue<E> queue;

	/**
	 * Construct an iterable for the specified queue.
	 */
	public QueueIterable(Queue<E> queue) {
		super();
		this.queue = queue;
	}

	public Iterator<E> iterator() {
		return new QueueIterator<E>(this.queue);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.queue);
	}

}
