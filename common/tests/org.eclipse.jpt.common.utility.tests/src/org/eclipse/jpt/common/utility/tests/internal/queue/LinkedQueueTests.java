/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;

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
}
