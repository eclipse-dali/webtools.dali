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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.queue.ConcurrentQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
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

	public void testConcurrentAccess() throws InterruptedException {
		ConcurrentQueue<Integer> queue = QueueTools.concurrentQueue();

		int threadCount = 10;
		int elementsPerThread = 100000;
		SynchronizedBoolean startFlag = new SynchronizedBoolean(false);

		EnqueueRunnable[] enqueueRunnables = new EnqueueRunnable[threadCount];
		Thread[] enqueueThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			EnqueueRunnable enqueueRunnable = new EnqueueRunnable(queue, (i * elementsPerThread), elementsPerThread, startFlag);
			enqueueRunnables[i] = enqueueRunnable;
			Thread enqueueThread = new Thread(enqueueRunnable);
			enqueueThreads[i] = enqueueThread;
			enqueueThread.start();
		}

		DequeueRunnable[] dequeueRunnables = new DequeueRunnable[threadCount];
		Thread[] dequeueThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			DequeueRunnable dequeueRunnable = new DequeueRunnable(queue, elementsPerThread, startFlag);
			dequeueRunnables[i] = dequeueRunnable;
			Thread dequeueThread = new Thread(dequeueRunnable);
			dequeueThreads[i] = dequeueThread;
			dequeueThread.start();
		}

		startFlag.setTrue();
		for (int i = 0; i < threadCount; i++) {
			enqueueThreads[i].join();
			assertTrue(enqueueRunnables[i].exceptions.isEmpty());
		}
		for (int i = 0; i < threadCount; i++) {
			dequeueThreads[i].join();
			assertTrue(dequeueRunnables[i].exceptions.isEmpty());
		}

		// if we get here, we have, at the least, dequeued as many elements as we enqueued...
		// ...now verify that all the dequeued elements are unique
		// (i.e. none were lost or duplicated)
		int totalCount = threadCount * elementsPerThread;
		int uberMax = totalCount + 1;
		int uberMaxThreadIndex = threadCount + 1;
		int[] indexes = ArrayTools.fill(new int[threadCount], 0);
		for (int i = 0; i < totalCount; i++) {
			int min = uberMax;
			int minThreadIndex = uberMaxThreadIndex;
			for (int j = 0; j < threadCount; j++) {
				int currentIndex = indexes[j];
				if (currentIndex < elementsPerThread) {
					int current = dequeueRunnables[j].elements[currentIndex].intValue();
					if (current < min) {
						min = current;
						minThreadIndex = j;
					}
				}
			}
			assertEquals(i, min);
			indexes[minThreadIndex]++;
		}
	}

	public static class EnqueueRunnable
		implements Runnable
	{
		private final Queue<Integer> queue;
		private final int start;
		private final int stop;
		private final SynchronizedBoolean startFlag;
		final List<InterruptedException> exceptions = new ArrayList<>();

		public EnqueueRunnable(Queue<Integer> queue, int start, int count, SynchronizedBoolean startFlag) {
			super();
			this.queue = queue;
			this.start = start;
			this.stop = start + count;
			this.startFlag = startFlag;
		}

		public void run() {
			try {
				this.run_();
			} catch (InterruptedException ex) {
				this.exceptions.add(ex);
			}
		}

		private void run_() throws InterruptedException {
			this.startFlag.waitUntilTrue();
			for (int i = this.start; i < this.stop; i++) {
				this.queue.enqueue(Integer.valueOf(i));
				if ((i % 20) == 0) {
					Thread.sleep(0);
				}
			}
		}
	}

	public static class DequeueRunnable
		implements Runnable
	{
		private final Queue<Integer> queue;
		private final int count;
		final Integer[] elements;
		int elementsCount;
		private final SynchronizedBoolean startFlag;
		final List<InterruptedException> exceptions = new ArrayList<>();
	
		public DequeueRunnable(Queue<Integer> queue, int count, SynchronizedBoolean startFlag) {
			super();
			this.queue = queue;
			this.count = count;
			this.elements = new Integer[count];
			this.elementsCount = 0;
			this.startFlag = startFlag;
		}
	
		public void run() {
			try {
				this.run_();
			} catch (InterruptedException ex) {
				this.exceptions.add(ex);
			}
		}
	
		private void run_() throws InterruptedException {
			this.startFlag.waitUntilTrue();
			int i = 0;
			while (true) {
				Integer element = this.queue.peek(); // fiddle with peek also
				element = this.queue.dequeue();
				if (element != null) {
					this.elements[this.elementsCount++] = element;
					if (this.elementsCount == this.count) {
						break;
					}
				}
				if ((i % 20) == 0) {
					Thread.sleep(0);
				}
				if (i == Integer.MAX_VALUE) {
					i = 0;
				} else {
					i++;
				}
			}
			Arrays.sort(this.elements);
		}
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
