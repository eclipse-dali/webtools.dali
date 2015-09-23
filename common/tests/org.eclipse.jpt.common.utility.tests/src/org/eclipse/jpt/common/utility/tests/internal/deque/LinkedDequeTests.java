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

import java.util.Arrays;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.LinkedDeque;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class LinkedDequeTests
	extends DequeTests
{
	public LinkedDequeTests(String name) {
		super(name);
	}

	@Override
	Deque<String> buildDeque() {
		return DequeTools.linkedDeque();
	}

	public void testConstructorInt_IAE() {
		boolean exCaught = false;
		try {
			Deque<String> queue = DequeTools.linkedDeque(-3);
			fail("bogus deque: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";
		String third = "third";

		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueueTail(first);
		queue.enqueueTail(second);
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueueTail(third);
		assertEquals(3, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeueHead();
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeueHead();
		queue.dequeueHead();
		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
	}

	public void testSize_reverse() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";
		String third = "third";

		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueueHead(first);
		queue.enqueueHead(second);
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueueHead(third);
		assertEquals(3, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeueTail();
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeueTail();
		queue.dequeueTail();
		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
	}

	public void testBuildElements() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueueTail(first);
		queue.enqueueTail(second);
		queue.enqueueTail(third);

		Object[] elements = new Object[] { first, second, third };
		assertTrue(Arrays.equals(elements, ((Object[]) ObjectTools.execute(queue, "buildElements"))));
	}

	public void testBuildElements_reverse() {
		Deque<String> queue = this.buildDeque();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueueHead(first);
		queue.enqueueHead(second);
		queue.enqueueHead(third);

		Object[] elements = new Object[] { third, second, first };
		assertTrue(Arrays.equals(elements, ((Object[]) ObjectTools.execute(queue, "buildElements"))));
	}

	public void testNodeCache_max() {
		Deque<String> queue = new LinkedDeque<String>(2);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(queue, "nodeFactory");

		this.verifyNodeCache(0, factory);
		queue.enqueueTail(first);
		this.verifyNodeCache(0, factory);
		queue.enqueueTail(second);
		queue.enqueueHead(third);
		queue.enqueueHead(fourth);
		queue.enqueueTail(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		queue.dequeueHead();
		this.verifyNodeCache(1, factory);
		queue.dequeueHead();
		this.verifyNodeCache(2, factory);
		queue.dequeueTail();
		this.verifyNodeCache(2, factory);
		queue.dequeueHead();
		this.verifyNodeCache(2, factory);
		queue.dequeueTail();
		this.verifyNodeCache(2, factory);
		queue.enqueueTail(first);
		this.verifyNodeCache(1, factory);
		queue.enqueueTail(second);
		this.verifyNodeCache(0, factory);
		queue.enqueueTail(third);
		this.verifyNodeCache(0, factory);
	}


	public void testNodeCache_unlimited() {
		Deque<String> queue = DequeTools.linkedDeque(-1);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(queue, "nodeFactory");

		this.verifyNodeCache(0, factory);
		queue.enqueueTail(first);
		this.verifyNodeCache(0, factory);
		queue.enqueueTail(second);
		queue.enqueueHead(third);
		queue.enqueueHead(fourth);
		queue.enqueueTail(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		queue.dequeueHead();
		this.verifyNodeCache(1, factory);
		queue.dequeueHead();
		this.verifyNodeCache(2, factory);
		queue.dequeueTail();
		this.verifyNodeCache(3, factory);
		queue.dequeueHead();
		this.verifyNodeCache(4, factory);
		queue.dequeueTail();
		this.verifyNodeCache(5, factory);
		queue.enqueueTail(first);
		this.verifyNodeCache(4, factory);
		queue.enqueueTail(second);
		this.verifyNodeCache(3, factory);
		queue.enqueueTail(third);
		this.verifyNodeCache(2, factory);
		queue.enqueueTail(fourth);
		this.verifyNodeCache(1, factory);
		queue.enqueueTail(fifth);
		this.verifyNodeCache(0, factory);
	}

	public void verifyNodeCache(int size, Object factory) {
		assertEquals(size, ((Integer) ObjectTools.get(factory, "cacheSize")).intValue());
		int nodeCount = 0;
		for (Object node = ObjectTools.get(factory, "cacheHead"); node != null; node = ObjectTools.get(node, "next")) {
			nodeCount++;
		}
		assertEquals(size, nodeCount);
	}

	public void testNodeToString() {
		Deque<String> queue = DequeTools.linkedDeque();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueueTail(first);
		queue.enqueueTail(second);
		queue.enqueueHead(third);

		Object head = ObjectTools.get(queue, "head");
		assertTrue(head.toString().startsWith("LinkedDeque.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testSimpleNodeFactoryToString() {
		Deque<String> queue = DequeTools.linkedDeque();
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertEquals("LinkedDeque.SimpleNodeFactory", factory.toString());
	}

	public void testCachingNodeFactoryToString() {
		Deque<String> queue = DequeTools.linkedDeque(20);
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedDeque.CachingNodeFactory"));
		assertTrue(factory.toString().endsWith("(0)"));
	}

	public void testClone_caching() throws Exception {
		LinkedDeque<String> original = DequeTools.linkedDeque(20);
		original.enqueueTail("first");

		LinkedDeque<String> clone = original.clone();
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueueTail("second");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());

		Object factory = ObjectTools.get(original, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedDeque.CachingNodeFactory"));
	}

	public void testSerialization_caching() throws Exception {
		Deque<String> original = DequeTools.linkedDeque(20);
		original.enqueueTail("first");

		Deque<String> clone = TestTools.serialize(original);
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueueTail("second");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());

		Object factory = ObjectTools.get(original, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedDeque.CachingNodeFactory"));
	}
}
