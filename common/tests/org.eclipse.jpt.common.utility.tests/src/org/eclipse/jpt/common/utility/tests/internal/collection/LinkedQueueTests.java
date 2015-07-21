/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.LinkedQueue;

@SuppressWarnings("nls")
public class LinkedQueueTests
	extends QueueTests
{
	public LinkedQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return new LinkedQueue<String>();
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

	public void testNodeCache() {
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

	public void verifyNodeCache(int size, Object factory) {
		assertEquals(size, ((Integer) ObjectTools.get(factory, "cacheSize")).intValue());
		int nodeCount = 0;
		for (Object node = ObjectTools.get(factory, "cacheHead"); node != null; node = ObjectTools.get(node, "next")) {
			nodeCount++;
		}
		assertEquals(size, nodeCount);
	}
}
