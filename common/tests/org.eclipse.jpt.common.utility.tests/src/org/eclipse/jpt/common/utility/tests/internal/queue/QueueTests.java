/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

// subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class QueueTests
	extends MultiThreadedTestCase
{
	public QueueTests(String name) {
		super(name);
	}

	abstract Queue<String> buildQueue();

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
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
	}

	public void testEnqueueAndPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
	}

	public void testEmptyQueueExceptionPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.peek();
			fail();
		} catch (NoSuchElementException ex) {
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
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		@SuppressWarnings("unchecked")
		Queue<String> clone = (Queue<String>) ObjectTools.execute(queue, "clone");
		this.verifyClone(queue, clone);
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
		assertEquals("[first, second]", queue.toString());
		queue.enqueue("third");
		assertEquals("[first, second, third]", queue.toString());
	}

	// ********** concurrency **********

	Queue<Integer> buildConcurrentQueue() {
		return null;
	}

	public void testConcurrentAccess() throws InterruptedException {
		Queue<Integer> queue = this.buildConcurrentQueue();
		if (queue == null) {
			return;
		}

		int threadCount = 10;
		int elementsPerThread = 100000;
		SynchronizedBoolean startFlag = new SynchronizedBoolean(false);

		EnqueueRunnable[] enqueueRunnables = new EnqueueRunnable[threadCount];
		Thread[] enqueueThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			EnqueueRunnable enqueueRunnable = new EnqueueRunnable(queue, (i * elementsPerThread), elementsPerThread, startFlag);
			enqueueRunnables[i] = enqueueRunnable;
			Thread enqueueThread = new Thread(enqueueRunnable, "Enqueue-" + i);
			enqueueThreads[i] = enqueueThread;
			enqueueThread.start();
		}

		DequeueRunnable[] dequeueRunnables = new DequeueRunnable[threadCount];
		Thread[] dequeueThreads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			DequeueRunnable dequeueRunnable = new DequeueRunnable(queue, elementsPerThread, startFlag);
			dequeueRunnables[i] = dequeueRunnable;
			Thread dequeueThread = new Thread(dequeueRunnable, "Dequeue-" + i);
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
		final List<InterruptedException> exceptions = new Vector<>();

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
		private int elementsCount;
		private final SynchronizedBoolean startFlag;
		final List<InterruptedException> exceptions = new Vector<>();
	
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
				Integer element = null;
				try {
					element = this.queue.peek(); // fiddle with peek also
					element = this.queue.dequeue();
				} catch (NoSuchElementException ex) {
					element = null;
				}
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
}
