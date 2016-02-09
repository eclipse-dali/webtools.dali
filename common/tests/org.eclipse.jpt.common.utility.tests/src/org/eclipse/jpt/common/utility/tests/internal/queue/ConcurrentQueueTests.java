/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;

@SuppressWarnings("nls")
public class ConcurrentQueueTests
	extends QueueTests
{
	public ConcurrentQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return QueueTools.concurrentQueue();
	}

	@Override
	Queue<Integer> buildConcurrentQueue() {
		return QueueTools.concurrentQueue();
	}

	public void testEnqueue_npe() {
		Queue<String> queue = this.buildQueue();
		boolean exCaught = false;
		try {
			queue.enqueue(null);
			fail();
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNodeToString() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.enqueue(first);
		queue.enqueue(second);
		queue.enqueue(third);

		Object headRef = ObjectTools.get(queue, "headRef");
		Object head = ObjectTools.get(headRef, "value");
		assertTrue(head.toString().startsWith("ConcurrentQueue.Node"));
		assertTrue(head.toString().endsWith("(null)")); // head points at sentinel node

		Object tailRef = ObjectTools.get(queue, "tailRef");
		Object tail = ObjectTools.get(tailRef, "value");
		assertTrue(tail.toString().startsWith("ConcurrentQueue.Node"));
		assertTrue(tail.toString().endsWith("(third)"));
	}
	
	@Override
	public void testSerialization() throws Exception {
		// unsupported
	}

	@Override
	public void testEmptyQueueExceptionPeek() {
		// unsupported
	}

	@Override
	public void testEmptyQueueExceptionDequeue() {
		// unsupported
	}

	@Override
	public void testClone() {
		// unsupported
	}
}
