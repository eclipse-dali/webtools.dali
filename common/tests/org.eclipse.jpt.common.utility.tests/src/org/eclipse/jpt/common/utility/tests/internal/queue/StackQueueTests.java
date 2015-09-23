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

import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

// subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public class StackQueueTests
	extends TestCase
{
	public StackQueueTests(String name) {
		super(name);
	}

	private Queue<String> buildQueue() {
		return QueueTools.stackQueue();
	}

	public void testConstructor_NPE() {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.adapt((Stack<String>) null);
			fail("bogus queue: " + queue);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
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
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.dequeue());
	}

	public void testEnqueueAndPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(second, queue.peek());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
	}

	public void testEmptyQueueExceptionPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());

		boolean exCaught = false;
		try {
			String string = queue.peek();
			fail("bogus element: " + string);
		} catch (EmptyStackException ex) {
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
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());

		boolean exCaught = false;
		try {
			String string = queue.dequeue();
			fail("bogus element: " + string);
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
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
		assertEquals("[second, first]", queue.toString());
		queue.enqueue("third");
		assertEquals("[third, second, first]", queue.toString());
	}
}
