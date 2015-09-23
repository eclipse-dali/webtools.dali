/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;

@SuppressWarnings("nls")
public class ReverseDequeTests
	extends DequeTests
{
	private Deque<String> original;

	public ReverseDequeTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.original = DequeTools.arrayDeque();
	}

	@Override
	public Deque<String> buildDeque() {
		return DequeTools.reverse(this.original);
	}

	public void testIsEmpty_combo() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		this.original.enqueueTail("first");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("second");
		assertFalse(queue.isEmpty());
		this.original.enqueueHead("zero");
		assertFalse(queue.isEmpty());
		queue.dequeueHead();
		assertFalse(queue.isEmpty());
		queue.dequeueHead();
		assertFalse(queue.isEmpty());
		queue.dequeueTail();
		assertTrue(queue.isEmpty());
	}

	public void testEnqueueTailAndDequeueTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.dequeueTail());
	}

	public void testEnqueueHeadAndDequeueHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueHead(first);
		this.original.enqueueHead(second);
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.dequeueHead());
	}

	public void testEnqueueAndDequeue_combo() {
		Deque<String> queue = this.buildDeque();
		String negative = "negative";
		String zero = "zero";
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
		this.original.enqueueHead(zero);
		this.original.enqueueHead(negative);
		assertEquals(negative, queue.dequeueTail());
		assertEquals(second, queue.dequeueHead());
		assertEquals(zero, queue.dequeueTail());
		assertEquals(first, queue.dequeueHead());

		assertTrue(queue.isEmpty());
	}

	public void testEnqueueTailAndPeekTail() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.peekTail());
		assertEquals(first, queue.dequeueTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.peekTail());
		assertEquals(second, queue.dequeueTail());
	}

	public void testEnqueueHeadAndPeekHead() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueHead(first);
		this.original.enqueueHead(second);
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.peekHead());
		assertEquals(first, queue.dequeueHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.dequeueHead());
	}

	public void testEnqueueAndPeek_combo() {
		Deque<String> queue = this.buildDeque();
		String negative = "negative";
		String zero = "zero";
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
		this.original.enqueueHead(zero);
		this.original.enqueueHead(negative);
		assertEquals(negative, queue.peekTail());
		assertEquals(negative, queue.peekTail());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.peekHead());

		assertEquals(negative, queue.dequeueTail());
		assertEquals(zero, queue.peekTail());
		assertEquals(zero, queue.peekTail());
		assertEquals(second, queue.peekHead());
		assertEquals(second, queue.peekHead());

		assertEquals(second, queue.dequeueHead());
		assertEquals(zero, queue.peekTail());
		assertEquals(first, queue.peekHead());

		assertEquals(first, queue.dequeueHead());
		assertEquals(zero, queue.peekTail());
		assertEquals(zero, queue.peekHead());

		assertEquals(zero, queue.dequeueHead());

		assertTrue(queue.isEmpty());
	}

	public void testEmptyDequeExceptionPeekTail_combo() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
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

	public void testEmptyDequeExceptionPeekHead_combo() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueHead(first);
		this.original.enqueueHead(second);
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

	public void testEmptyDequeExceptionDequeueTail_combo() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueTail(first);
		this.original.enqueueTail(second);
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

	public void testEmptyDequeExceptionDequeueHead_combo() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";

		this.original.enqueueHead(first);
		this.original.enqueueHead(second);
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

	@Override
	public void testClone() {
		// unsupported
	}

	@Override
	public void testToString() throws Exception {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.toString().startsWith("ReverseDeque"));
		assertTrue(queue.toString().endsWith("([])"));
		queue.enqueueTail("first");
		assertTrue(queue.toString().startsWith("ReverseDeque"));
		assertTrue(queue.toString().endsWith("([first])"));
		queue.enqueueTail("second");
		assertTrue(queue.toString().startsWith("ReverseDeque"));
		assertTrue(queue.toString().endsWith("([second, first])"));
		queue.enqueueTail("third");
		assertTrue(queue.toString().startsWith("ReverseDeque"));
		assertTrue(queue.toString().endsWith("([third, second, first])"));
		queue.enqueueHead("foo");
		assertTrue(queue.toString().startsWith("ReverseDeque"));
		assertTrue(queue.toString().endsWith("([third, second, first, foo])"));
	}

	public void testCtor_nullDeque() {
		boolean exCaught = false;
		try {
			Deque<String> deque = DequeTools.reverse(null);
			fail("bogus deque: " + deque);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
