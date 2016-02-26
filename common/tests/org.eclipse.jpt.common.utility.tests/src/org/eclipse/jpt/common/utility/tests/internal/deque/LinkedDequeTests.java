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
}
