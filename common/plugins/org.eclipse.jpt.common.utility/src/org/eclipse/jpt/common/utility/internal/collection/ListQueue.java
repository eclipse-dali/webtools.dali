/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.collection.Queue;

/**
 * Adapt a {@link List} to the {@link Queue} interface.
 * Elements are dequeued from the front of the list (i.e. index 0).
 * @param <E> the type of elements maintained by the queue
 * @see QueueTools
 */
public class ListQueue<E>
	implements Queue<E>, Serializable
{
	private List<E> list;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a queue, adapting the specified list.
	 * The queue will dequeue its elements in the same
	 * order they are returned by the list's iterator (i.e. the
	 * first element returned by the list's iterator will be the
	 * first element returned by {@link #dequeue()}).
	 */
	public ListQueue(List<E> list) {
		super();
		this.list = list;
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		this.list.add(element);
	}

	public E dequeue() {
		try {
			return this.list.remove(0);
		} catch (IndexOutOfBoundsException ex) {
			throw new NoSuchElementException();
		}
	}

	public E peek() {
		try {
			return this.list.get(0);
		} catch (IndexOutOfBoundsException ex) {
			throw new NoSuchElementException();
		}
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return this.list.toString();
	}
}
