/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.Queue;
import org.eclipse.jpt.utility.internal.SimpleQueue;

@SuppressWarnings("nls")
public class SimpleQueueTests extends TestCase {

	public SimpleQueueTests(String name) {
		super(name);
	}

	Queue<String> buildQueue() {
		return new SimpleQueue<String>();
	}

	public void testIsEmpty() {
		Queue<String> queue = this.buildQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue("first");
		assertFalse(queue.isEmpty());
		queue.enqueue("second");
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}

	public void testEnqueueAndDequeue() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
	}

	public void testEnqueueAndPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
	}

	public void testEmptyQueueExceptionPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.peek();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyQueueExceptionDequeue() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		SimpleQueue<String> queue = (SimpleQueue<String>) this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		this.verifyClone(queue, queue.clone());
	}

	public void testSerialization() throws Exception {
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	private void verifyClone(Queue<String> original, Queue<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

}
