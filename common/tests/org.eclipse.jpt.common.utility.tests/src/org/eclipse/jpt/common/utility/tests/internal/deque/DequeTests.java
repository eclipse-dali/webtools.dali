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

import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

// subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class DequeTests
	extends MultiThreadedTestCase
{
	public DequeTests(String name) {
		super(name);
	}

	abstract Deque<String> buildDeque();

	public void testIsEmpty() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueTail("first");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("second");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("zero");
		assertFalse(queue.isEmpty());
		queue.dequeueHead();
		assertFalse(queue.isEmpty());
		queue.dequeueHead();
		assertFalse(queue.isEmpty());
		queue.dequeueTail();
		assertTrue(queue.isEmpty());
	}

	public void testEnqueueTailAndDequeueHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.dequeueHead());
	}

	public void testEnqueueHeadAndDequeueTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueHead(first);
		queue.enqueueHead(second);
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.dequeueTail());
	}

	public void testEnqueueAndDequeue() {
		Deque<String> queue = this.buildDeque();
		String negative = "negative";
		String zero = "zero";
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		queue.enqueueHead(zero);
		queue.enqueueHead(negative);
		assertEquals(negative, queue.dequeueHead());
		assertEquals(second, queue.dequeueTail());
		assertEquals(zero, queue.dequeueHead());
		assertEquals(first, queue.dequeueTail());

		assertTrue(queue.isEmpty());
	}

	public void testEnqueueTailAndPeekHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.dequeueHead());
	}

	public void testEnqueueHeadAndPeekTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueHead(first);
		queue.enqueueHead(second);
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.dequeueTail());
	}

	public void testEnqueueAndPeek() {
		Deque<String> queue = this.buildDeque();
		String negative = "negative";
		String zero = "zero";
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		queue.enqueueHead(zero);
		queue.enqueueHead(negative);
		assertEquals(negative, queue.peekHead());
		assertEquals(negative, queue.peekHead());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.peekTail());

		assertEquals(negative, queue.dequeueHead());
		assertEquals(zero, queue.peekHead());
		assertEquals(zero, queue.peekHead());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.peekTail());

		assertEquals(second, queue.dequeueTail());
		assertEquals(zero, queue.peekHead());
		assertEquals(first, queue.peekTail());

		assertEquals(first, queue.dequeueTail());
		assertEquals(zero, queue.peekHead());
		assertEquals(zero, queue.peekTail());

		assertEquals(zero, queue.dequeueTail());

		assertTrue(queue.isEmpty());
	}

	public void testEmptyDequeExceptionPeekHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.dequeueHead());

		boolean exCaught = false;
		try {
			queue.peekHead();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyDequeExceptionPeekTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueHead(first);
		queue.enqueueHead(second);
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.dequeueTail());

		boolean exCaught = false;
		try {
			queue.peekTail();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyDequeExceptionDequeueHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueTail(first);
		queue.enqueueTail(second);
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.dequeueHead());

		boolean exCaught = false;
		try {
			queue.dequeueHead();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyDequeExceptionDequeueTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		queue.enqueueHead(first);
		queue.enqueueHead(second);
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.dequeueTail());

		boolean exCaught = false;
		try {
			queue.dequeueTail();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		Deque<String> queue = this.buildDeque();
		queue.enqueueTail("first");
		queue.enqueueTail("second");
		queue.enqueueTail("third");

		@SuppressWarnings("unchecked")
		Deque<String> clone = (Deque<String>) ObjectTools.execute(queue, "clone");
		this.verifyClone(queue, clone);
	}

	public void testSerialization() throws Exception {
		Deque<String> queue = this.buildDeque();
		queue.enqueueTail("first");
		queue.enqueueTail("second");
		queue.enqueueTail("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	protected void verifyClone(Deque<String> original, Deque<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peekTail(), clone.peekTail());
		assertEquals(original.dequeueTail(), clone.dequeueTail());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueueTail("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testToString() throws Exception {
		Deque<String> queue = this.buildDeque();
		assertEquals("[]", queue.toString());
		queue.enqueueTail("first");
		assertEquals("[first]", queue.toString());
		queue.enqueueTail("second");
		assertEquals("[first, second]", queue.toString());
		queue.enqueueTail("third");
		assertEquals("[first, second, third]", queue.toString());
		queue.enqueueHead("foo");
		assertEquals("[foo, first, second, third]", queue.toString());
	}
}
