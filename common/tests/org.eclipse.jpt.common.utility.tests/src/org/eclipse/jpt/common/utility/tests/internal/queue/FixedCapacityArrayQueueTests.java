/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.queue.FixedCapacityArrayQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;

@SuppressWarnings("nls")
public class FixedCapacityArrayQueueTests
	extends QueueTests
{
	public FixedCapacityArrayQueueTests(String name) {
		super(name);
	}

	@Override
	FixedCapacityArrayQueue<String> buildQueue() {
		return QueueTools.fixedCapacityArrayQueue(10);
	}

	public void testCollectionConstructor() {
		ArrayList<String> c = new ArrayList<String>();
		c.add("first");
		c.add("second");
		c.add("third");
		c.add("fourth");
		c.add("fifth");
		c.add("sixth");
		c.add("seventh");
		c.add("eighth");
		c.add("ninth");
		c.add("tenth");
		Queue<String> queue = QueueTools.fixedCapacityArrayQueue(c);

		assertFalse(queue.isEmpty());
		assertEquals("first", queue.peek());
		assertEquals("first", queue.dequeue());
		assertEquals("second", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.peek());
		assertEquals("third", queue.dequeue());
		assertEquals("fourth", queue.dequeue());
		assertEquals("fifth", queue.dequeue());
		assertEquals("sixth", queue.dequeue());
		assertEquals("seventh", queue.dequeue());
		assertEquals("eighth", queue.dequeue());
		assertEquals("ninth", queue.dequeue());
		assertEquals("tenth", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testIsFull() {
		FixedCapacityArrayQueue<String> queue = this.buildQueue();
		assertFalse(queue.isFull());
		queue.enqueue("first");
		assertFalse(queue.isFull());
		queue.enqueue("second");
		assertFalse(queue.isFull());
		queue.enqueue("third");
		queue.enqueue("fourth");
		queue.enqueue("fifth");
		queue.enqueue("sixth");
		queue.enqueue("seventh");
		queue.enqueue("eighth");
		queue.enqueue("ninth");
		queue.enqueue("tenth");
		assertTrue(queue.isFull());

		queue.dequeue();
		assertFalse(queue.isEmpty());
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		assertFalse(queue.isFull());
	}

	public void testWrappedElements() {
		Queue<String> queue = this.buildQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue("first");
		assertFalse(queue.isEmpty());
		queue.enqueue("second");
		assertFalse(queue.isEmpty());
		queue.enqueue("third");
		queue.enqueue("fourth");
		queue.enqueue("fifth");
		queue.enqueue("sixth");

		// make room for 11 and 12
		assertEquals("first", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeue());

		queue.enqueue("seventh");
		queue.enqueue("eighth");
		queue.enqueue("ninth");
		queue.enqueue("tenth");
		queue.enqueue("eleventh");
		queue.enqueue("twelfth");

		assertEquals("fourth", queue.dequeue());
		assertEquals("fifth", queue.dequeue());
		assertEquals("sixth", queue.dequeue());
		assertEquals("seventh", queue.dequeue());
		assertEquals("eighth", queue.dequeue());
		assertEquals("ninth", queue.dequeue());
		assertEquals("tenth", queue.dequeue());
		assertEquals("eleventh", queue.dequeue());
		assertEquals("twelfth", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceeded() {
		Queue<String> queue = this.buildQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue("first");
		assertFalse(queue.isEmpty());
		queue.enqueue("second");
		assertFalse(queue.isEmpty());
		queue.enqueue("third");
		queue.enqueue("fourth");
		queue.enqueue("fifth");
		queue.enqueue("sixth");
		queue.enqueue("seventh");
		queue.enqueue("eighth");
		queue.enqueue("ninth");
		queue.enqueue("tenth");

		boolean exCaught = false;
		try {
			queue.enqueue("eleventh");
			fail("bogus");
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		assertEquals("first", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeue());
		assertEquals("fourth", queue.dequeue());
		assertEquals("fifth", queue.dequeue());
		assertEquals("sixth", queue.dequeue());
		assertEquals("seventh", queue.dequeue());
		assertEquals("eighth", queue.dequeue());
		assertEquals("ninth", queue.dequeue());
		assertEquals("tenth", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceededWithWrappedElements() {
		Queue<String> queue = this.buildQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue("first");
		assertFalse(queue.isEmpty());
		queue.enqueue("second");
		assertFalse(queue.isEmpty());
		queue.enqueue("third");
		queue.enqueue("fourth");
		queue.enqueue("fifth");
		queue.enqueue("sixth");

		assertEquals("first", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeue());

		queue.enqueue("seventh");
		queue.enqueue("eighth");
		queue.enqueue("ninth");
		queue.enqueue("tenth");
		queue.enqueue("eleventh");
		queue.enqueue("twelfth");
		queue.enqueue("thirteenth");

		boolean exCaught = false;
		try {
			queue.enqueue("fourteenth");
			fail("bogus");
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);


		assertEquals("fourth", queue.dequeue());
		assertEquals("fifth", queue.dequeue());
		assertEquals("sixth", queue.dequeue());
		assertEquals("seventh", queue.dequeue());
		assertEquals("eighth", queue.dequeue());
		assertEquals("ninth", queue.dequeue());
		assertEquals("tenth", queue.dequeue());
		assertEquals("eleventh", queue.dequeue());
		assertEquals("twelfth", queue.dequeue());
		assertEquals("thirteenth", queue.dequeue());
		assertTrue(queue.isEmpty());
	}
}
