/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.jpt.common.utility.collection.Queue;

/**
 * Adapt a {@link SortedSet} to create a priority implementation of the
 * {@link Queue} interface. Elements will dequeue in the order determined by
 * wrapped sorted set (i.e. {@link #dequeue} will return the element returned
 * by {@link SortedSet#first}.
 * @param <E> the type of elements maintained by the queue
 */
public class PriorityQueue<E>
	implements Queue<E>, Serializable
{
	private final SortedSet<E> elements;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a priority queue that uses the specified comparator
	 * to determine the order elements will be dequeued
	 * (i.e. {@link #dequeue} will return the current element with
	 * the lowest position, as specified by the comparator).
	 */
	public PriorityQueue(Comparator<? super E> comparator) {
		this(new TreeSet<E>(comparator));
	}

	public PriorityQueue(SortedSet<E> elements) {
		super();
		if (elements == null) {
			throw new NullPointerException();
		}
		this.elements = elements;
	}

	public void enqueue(E element) {
		this.elements.add(element);
	}

	public E dequeue() {
		E result = this.elements.first();
		this.elements.remove(result);
		return result;
	}

	public E peek() {
		return this.elements.first();
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}
}
