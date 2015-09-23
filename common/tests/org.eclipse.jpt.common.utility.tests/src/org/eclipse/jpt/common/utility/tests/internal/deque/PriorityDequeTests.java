/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.deque.AbstractPriorityDeque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.PriorityDeque;

@SuppressWarnings("nls")
public class PriorityDequeTests
	extends AbstractPriorityDequeTests
{
	public PriorityDequeTests(String name) {
		super(name);
	}

	@Override
	<E extends Comparable<E>> PriorityDeque<E> buildDeque() {
		return DequeTools.<E>priorityDeque();
	}

	@Override
	<E extends Comparable<E>> PriorityDeque<E> buildDeque(int capacity) {
		return DequeTools.priorityDeque(capacity);
	}

	@Override
	<E extends Comparable<E>> PriorityDeque<E> buildDeque(Comparator<E> comparator, int capacity) {
		return DequeTools.priorityDeque(comparator, capacity);
	}

	public void testArrayCapacityExceeded() {
		AbstractPriorityDeque<Integer> deque = this.buildDeque();
		assertTrue(deque.isEmpty());
		deque.enqueue(Integer.valueOf(1));
		assertFalse(deque.isEmpty());
		deque.enqueue(Integer.valueOf(2));
		assertFalse(deque.isEmpty());
		deque.enqueue(Integer.valueOf(3));
		deque.enqueue(Integer.valueOf(10));
		deque.enqueue(Integer.valueOf(11));
		deque.enqueue(Integer.valueOf(12));
		deque.enqueue(Integer.valueOf(4));
		deque.enqueue(Integer.valueOf(4));
		deque.enqueue(Integer.valueOf(7));
		deque.enqueue(Integer.valueOf(8));
		deque.enqueue(Integer.valueOf(9));
		deque.enqueue(Integer.valueOf(5));
		deque.enqueue(Integer.valueOf(6));
		deque.enqueue(Integer.valueOf(9));

		assertEquals(Integer.valueOf(1), deque.dequeueHead());
		assertFalse(deque.isEmpty());
		assertEquals(Integer.valueOf(2), deque.dequeueHead());
		assertFalse(deque.isEmpty());
		assertEquals(Integer.valueOf(3), deque.dequeueHead());
		assertEquals(Integer.valueOf(4), deque.dequeueHead());
		assertEquals(Integer.valueOf(12), deque.dequeueTail());
		assertEquals(Integer.valueOf(4), deque.dequeueHead());
		assertEquals(Integer.valueOf(5), deque.dequeueHead());
		assertEquals(Integer.valueOf(6), deque.dequeueHead());
		assertEquals(Integer.valueOf(7), deque.dequeueHead());
		assertEquals(Integer.valueOf(11), deque.dequeueTail());
		assertEquals(Integer.valueOf(8), deque.dequeueHead());
		assertEquals(Integer.valueOf(10), deque.dequeueTail());
		assertEquals(Integer.valueOf(9), deque.dequeueHead());
		assertEquals(Integer.valueOf(9), deque.dequeueHead());
		assertTrue(deque.isEmpty());
	}

	public void testEnsureCapacity() throws Exception {
		PriorityDeque<String> deque = this.buildDeque();
		deque.enqueue("b");
		deque.enqueue("c");
		deque.enqueue("a");
		assertEquals(11, ((Object[]) ObjectTools.get(deque, "elements")).length);
		deque.ensureCapacity(420);
		assertEquals(421, ((Object[]) ObjectTools.get(deque, "elements")).length);
		assertEquals("a", deque.dequeueHead());
		assertEquals("c", deque.dequeueTail());
		assertEquals("b", deque.dequeueHead());
		assertTrue(deque.isEmpty());
	}

	public void testTrimToSize() throws Exception {
		PriorityDeque<String> deque = this.buildDeque();
		deque.enqueue("b");
		deque.enqueue("c");
		deque.enqueue("a");
		assertEquals(11, ((Object[]) ObjectTools.get(deque, "elements")).length);
		deque.trimToSize();
		assertEquals(4, ((Object[]) ObjectTools.get(deque, "elements")).length);
		assertEquals("a", deque.dequeueHead());
		assertEquals("c", deque.dequeueTail());
		assertEquals("b", deque.dequeueHead());
		assertTrue(deque.isEmpty());
	}

	public void testTrimToSize_nop() throws Exception {
		PriorityDeque<String> deque = this.buildDeque(3);
		deque.enqueue("b");
		deque.enqueue("c");
		deque.enqueue("a");
		assertEquals(4, ((Object[]) ObjectTools.get(deque, "elements")).length);
		deque.trimToSize();
		assertEquals(4, ((Object[]) ObjectTools.get(deque, "elements")).length);
		assertEquals("a", deque.dequeueHead());
		assertEquals("c", deque.dequeueTail());
		assertEquals("b", deque.dequeueHead());
		assertTrue(deque.isEmpty());
	}
}
