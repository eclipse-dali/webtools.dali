/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

// subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class QueueTests
	extends MultiThreadedTestCase
{
	public QueueTests(String name) {
		super(name);
	}

	abstract Queue<String> buildQueue();

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
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		@SuppressWarnings("unchecked")
		Queue<String> clone = (Queue<String>) ObjectTools.execute(queue, "clone");
		this.verifyClone(queue, clone);
	}

	public void testSerialization() throws Exception {
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	protected void verifyClone(Queue<String> original, Queue<String> clone) {
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

	public void testToString() throws Exception {
		Queue<String> queue = this.buildQueue();
		assertEquals("[]", queue.toString());
		queue.enqueue("first");
		assertEquals("[first]", queue.toString());
		queue.enqueue("second");
		assertEquals("[first, second]", queue.toString());
		queue.enqueue("third");
		assertEquals("[first, second, third]", queue.toString());
	}
}
