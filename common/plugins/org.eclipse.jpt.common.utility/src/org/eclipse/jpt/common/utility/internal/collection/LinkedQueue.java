/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.jpt.common.utility.collection.Queue;

/**
 * Linked list implementation of the {@link Queue} interface.
 * @param <E> the type of elements maintained by the queue
 * @see LinkedList
 */
public class LinkedQueue<E>
	implements Queue<E>, Cloneable, Serializable
{
	private LinkedList<E> elements;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct an empty queue.
	 */
	public LinkedQueue() {
		super();
		this.elements = new LinkedList<E>();
	}

	/**
	 * Construct a queue containing the elements of the specified
	 * collection. The queue will dequeue its elements in the same
	 * order they are returned by the collection's iterator (i.e. the
	 * first element returned by the collection's iterator will be the
	 * first element returned by {@link #dequeue()}).
	 */
	public LinkedQueue(Collection<? extends E> c) {
		super();
		this.elements = new LinkedList<E>(c);
	}


	// ********** Queue implementation **********

	public void enqueue(E element) {
		this.elements.addLast(element);
	}

	public E dequeue() {
		return this.elements.removeFirst();
	}

	public E peek() {
		return this.elements.getFirst();
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}


	// ********** standard methods **********

	@Override
	public LinkedQueue<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			LinkedQueue<E> clone = (LinkedQueue<E>) super.clone();
			@SuppressWarnings("unchecked")
			LinkedList<E> list = (LinkedList<E>) this.elements.clone();
			clone.elements = list;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}
}
