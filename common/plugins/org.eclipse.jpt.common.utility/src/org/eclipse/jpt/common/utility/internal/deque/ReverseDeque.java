/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.deque;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This deque will reverse the order of the configured deque.
 * @param <E> the type of elements maintained by the deque
 * @see DequeTools
 */
public class ReverseDeque<E>
	implements Deque<E>, Serializable
{
	private Deque<E> deque;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a deque that reverses the specified deque.
	 */
	public ReverseDeque(Deque<E> deque) {
		super();
		if (deque == null) {
			throw new NullPointerException();
		}
		this.deque = deque;
	}


	// ********** Deque implementation **********

	public void enqueueTail(E element) {
		this.deque.enqueueHead(element);
	}

	public void enqueueHead(E element) {
		this.deque.enqueueTail(element);
	}

	public E dequeueHead() {
		return this.deque.dequeueTail();
	}

	public E dequeueTail() {
		return this.deque.dequeueHead();
	}

	public E peekHead() {
		return this.deque.peekTail();
	}

	public E peekTail() {
		return this.deque.peekHead();
	}

	public boolean isEmpty() {
		return this.deque.isEmpty();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.deque);
	}
}
