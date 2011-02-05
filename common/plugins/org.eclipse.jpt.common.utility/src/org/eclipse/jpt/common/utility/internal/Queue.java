/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Interface defining the classic queue behavior,
 * without the backdoors allowed by {@link java.util.Queue}.
 * 
 * @param <E> the type of elements contained by the queue
 */
public interface Queue<E> {

	/**
	 * "Enqueue" the specified item to the tail of the queue.
	 */
	void enqueue(E o);

	/**
	 * "Dequeue" an item from the head of the queue.
	 */
	E dequeue();

	/**
	 * Return the item on the head of the queue
	 * without removing it from the queue.
	 */
	E peek();

	/**
	 * Return whether the queue is empty.
	 */
	boolean isEmpty();


	final class Empty<E> implements Queue<E>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final Queue INSTANCE = new Empty();
		@SuppressWarnings("unchecked")
		public static <T> Queue<T> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		public void enqueue(E o) {
			throw new UnsupportedOperationException();
		}
		public E dequeue() {
			throw new NoSuchElementException();
		}
		public E peek() {
			throw new NoSuchElementException();
		}
		public boolean isEmpty() {
			return true;
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
