/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.queue.LinkedQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class LinkedQueueTests
	extends QueueTests
{
	public LinkedQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return QueueTools.linkedQueue();
	}

	public void testConstructorInt_IAE() {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.linkedQueue(-3);
			fail("bogus queue: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";
		String third = "third";

		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.enqueue(third);
		assertEquals(3, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeue();
		assertEquals(2, ((Integer) ObjectTools.execute(queue, "size")).intValue());
		queue.dequeue();
		queue.dequeue();
		assertEquals(0, ((Integer) ObjectTools.execute(queue, "size")).intValue());
	}

	public void testBuildElements() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueue(first);
		queue.enqueue(second);
		queue.enqueue(third);

		Object[] elements = new Object[] { first, second, third };
		assertTrue(Arrays.equals(elements, ((Object[]) ObjectTools.execute(queue, "buildElements"))));
	}

	public void testNodeCache_max() {
		Queue<String> queue = new LinkedQueue<String>(2);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(queue, "nodeFactory");

		this.verifyNodeCache(0, factory);
		queue.enqueue(first);
		this.verifyNodeCache(0, factory);
		queue.enqueue(second);
		queue.enqueue(third);
		queue.enqueue(fourth);
		queue.enqueue(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		queue.dequeue();
		this.verifyNodeCache(1, factory);
		queue.dequeue();
		this.verifyNodeCache(2, factory);
		queue.dequeue();
		this.verifyNodeCache(2, factory);
		queue.dequeue();
		this.verifyNodeCache(2, factory);
		queue.dequeue();
		this.verifyNodeCache(2, factory);
		queue.enqueue(first);
		this.verifyNodeCache(1, factory);
		queue.enqueue(second);
		this.verifyNodeCache(0, factory);
		queue.enqueue(third);
		this.verifyNodeCache(0, factory);
	}

	public void testNodeCache_unlimited() {
		Queue<String> queue = new LinkedQueue<String>(-1);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(queue, "nodeFactory");

		this.verifyNodeCache(0, factory);
		queue.enqueue(first);
		this.verifyNodeCache(0, factory);
		queue.enqueue(second);
		queue.enqueue(third);
		queue.enqueue(fourth);
		queue.enqueue(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		queue.dequeue();
		this.verifyNodeCache(1, factory);
		queue.dequeue();
		this.verifyNodeCache(2, factory);
		queue.dequeue();
		this.verifyNodeCache(3, factory);
		queue.dequeue();
		this.verifyNodeCache(4, factory);
		queue.dequeue();
		this.verifyNodeCache(5, factory);
		queue.enqueue(first);
		this.verifyNodeCache(4, factory);
		queue.enqueue(second);
		this.verifyNodeCache(3, factory);
		queue.enqueue(third);
		this.verifyNodeCache(2, factory);
		queue.enqueue(fourth);
		this.verifyNodeCache(1, factory);
		queue.enqueue(fifth);
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
		Queue<String> queue = QueueTools.linkedQueue();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueue(first);
		queue.enqueue(second);
		queue.enqueue(third);

		Object head = ObjectTools.get(queue, "head");
		assertTrue(head.toString().startsWith("LinkedQueue.Node"));
		assertTrue(head.toString().endsWith("(first)"));
	}

	public void testSimpleNodeFactoryToString() {
		Queue<String> queue = QueueTools.linkedQueue();
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertEquals("LinkedQueue.SimpleNodeFactory", factory.toString());
	}

	public void testCachingNodeFactoryToString() {
		Queue<String> queue = QueueTools.linkedQueue(20);
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedQueue.CachingNodeFactory"));
		assertTrue(factory.toString().endsWith("(0)"));
	}

	public void testClone_caching() throws Exception {
		LinkedQueue<String> original = QueueTools.linkedQueue(20);
		original.enqueue("first");

		LinkedQueue<String> clone = original.clone();
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("second");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());

		Object factory = ObjectTools.get(original, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedQueue.CachingNodeFactory"));
	}

	public void testSerialization_caching() throws Exception {
		Queue<String> original = QueueTools.linkedQueue(20);
		original.enqueue("first");

		Queue<String> clone = TestTools.serialize(original);
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("second");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());

		Object factory = ObjectTools.get(original, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedQueue.CachingNodeFactory"));
	}
}
