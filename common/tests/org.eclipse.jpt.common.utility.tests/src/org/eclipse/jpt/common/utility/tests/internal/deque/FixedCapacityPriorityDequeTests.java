/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.FixedCapacityPriorityDeque;

@SuppressWarnings("nls")
public class FixedCapacityPriorityDequeTests
	extends AbstractPriorityDequeTests
{
	public FixedCapacityPriorityDequeTests(String name) {
		super(name);
	}

	@Override
	<E extends Comparable<E>> FixedCapacityPriorityDeque<E> buildDeque() {
		return DequeTools.<E>fixedCapacityPriorityDeque(10);
	}

	@Override
	<E extends Comparable<E>> FixedCapacityPriorityDeque<E> buildDeque(int capacity) {
		return DequeTools.fixedCapacityPriorityDeque(capacity);
	}

	@Override
	<E extends Comparable<E>> FixedCapacityPriorityDeque<E> buildDeque(Comparator<E> comparator, int capacity) {
		return DequeTools.fixedCapacityPriorityDeque(comparator, capacity);
	}

	public void testIsFull() throws Exception {
		FixedCapacityPriorityDeque<String> deque = this.buildDeque();
		assertFalse(deque.isFull());
		deque.enqueue("first");
		assertFalse(deque.isFull());
		deque.enqueue("second");
		assertFalse(deque.isFull());
		deque.enqueue("third");
		deque.enqueue("fourth");
		deque.enqueue("fifth");
		deque.enqueue("sixth");
		deque.enqueue("seventh");
		deque.enqueue("eighth");
		deque.enqueue("ninth");
		deque.enqueue("tenth");
		assertTrue(deque.isFull());

		deque.dequeueHead();
		assertFalse(deque.isEmpty());
		deque.dequeueHead();
		deque.dequeueHead();
		deque.dequeueHead();
		deque.dequeueHead();
		deque.dequeueHead();
		deque.dequeueHead();
		deque.dequeueHead();
		assertFalse(deque.isFull());
	}

	public void testCapacityExceeded() {
		FixedCapacityPriorityDeque<String> deque = this.buildDeque();
		assertTrue(deque.isEmpty());
		deque.enqueue("first");
		assertFalse(deque.isEmpty());
		deque.enqueue("second");
		assertFalse(deque.isEmpty());
		deque.enqueue("third");
		deque.enqueue("fourth");
		deque.enqueue("fifth");
		deque.enqueue("sixth");
		deque.enqueue("seventh");
		deque.enqueue("eighth");
		deque.enqueue("ninth");
		deque.enqueue("tenth");

		boolean exCaught = false;
		try {
			deque.enqueue("eleventh");
			fail("bogus deque: " + deque);
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		assertEquals("eighth", deque.dequeueHead());
		assertEquals("fifth", deque.dequeueHead());
		assertEquals("first", deque.dequeueHead());
		assertEquals("fourth", deque.dequeueHead());
		assertFalse(deque.isEmpty());
		assertEquals("ninth", deque.dequeueHead());
		assertEquals("second", deque.dequeueHead());
		assertEquals("seventh", deque.dequeueHead());
		assertEquals("sixth", deque.dequeueHead());
		assertFalse(deque.isEmpty());
		assertEquals("tenth", deque.dequeueHead());
		assertEquals("third", deque.dequeueHead());
		assertTrue(deque.isEmpty());
	}

}
