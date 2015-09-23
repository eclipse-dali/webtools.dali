/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.queue.LinkedQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.queue.SynchronizedQueue;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.deque.DequeToolsTests;

@SuppressWarnings("nls")
public class SynchronizedQueueTests
	extends QueueTests
{
	private volatile SynchronizedQueue<String> sq;
	volatile boolean timeoutOccurred;
	volatile long startTime;
	volatile long endTime;
	volatile Object dequeuedObject;

	boolean commandExecuted;

	static final String ITEM_1 = new String();
	static final String ITEM_2 = new String();

	public SynchronizedQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return QueueTools.synchronizedQueue();
	}

	@Override
	public void testClone() {
		// synchronized queue is not cloneable
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.sq = QueueTools.synchronizedQueue();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.dequeuedObject = null;
	}

	// ********** constructor **********

	public void testConstructorQueue() throws Exception {
		Queue<String> innerQueue = QueueTools.arrayQueue();
		SynchronizedQueue<String> stack = QueueTools.synchronizedQueue(innerQueue);
		assertNotNull(stack);
		assertSame(stack, stack.getMutex());
	}

	public void testConstructorQueue_NPE() throws Exception {
		boolean exCaught = false;
		try {
			Queue<String> stack = QueueTools.synchronizedQueue(null);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorQueueObject() throws Exception {
		String mutex = "mutex";
		Queue<String> innerQueue = QueueTools.arrayQueue();
		SynchronizedQueue<String> stack = QueueTools.synchronizedQueue(innerQueue, mutex);
		assertNotNull(stack);
		assertSame(mutex, stack.getMutex());
	}

	public void testConstructorQueueObject_NPE1() throws Exception {
		String mutex = "mutex";
		boolean exCaught = false;
		try {
			Queue<String> stack = QueueTools.synchronizedQueue(null, mutex);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorQueueObject_NPE2() throws Exception {
		Queue<String> innerQueue = QueueTools.arrayQueue();
		boolean exCaught = false;
		try {
			Queue<String> stack = QueueTools.synchronizedQueue(innerQueue, null);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	// ********** concurrent access **********

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentDequeue() throws Exception {
		this.verifyConcurrentDequeue(new SlowLinkedQueue<String>(), "first");
		this.verifyConcurrentDequeue(new SlowSynchronizedQueue<String>(), "second");
	}

	private void verifyConcurrentDequeue(SlowQueue<String> slowQueue, String expected) throws Exception {
		slowQueue.enqueue("first");
		slowQueue.enqueue("second");

		Thread thread = this.buildThread(this.buildRunnableDequeue(slowQueue));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(expected, slowQueue.dequeue());
		thread.join();
		assertTrue(slowQueue.isEmpty());
	}

	private Runnable buildRunnableDequeue(final SlowQueue<String> slowQueue) {
		return new Runnable() {
			public void run() {
				slowQueue.slowDequeue();
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentEnqueue() throws Exception {
		this.verifyConcurrentEnqueue(new SlowLinkedQueue<String>(), "second", "first");
		this.verifyConcurrentEnqueue(new SlowSynchronizedQueue<String>(), "first", "second");
	}

	private void verifyConcurrentEnqueue(SlowQueue<String> slowQueue, String first, String second) throws Exception {
		Thread thread = this.buildThread(this.buildRunnableEnqueue(slowQueue, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		slowQueue.enqueue("second");
		thread.join();
		assertEquals(first, slowQueue.dequeue());
		assertEquals(second, slowQueue.dequeue());
		assertTrue(slowQueue.isEmpty());
	}

	private Runnable buildRunnableEnqueue(final SlowQueue<String> slowQueue, final String element) {
		return new Runnable() {
			public void run() {
				slowQueue.slowEnqueue(element);
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentIsEmpty() throws Exception {
		this.verifyConcurrentIsEmpty(new SlowLinkedQueue<String>(), true);
		this.verifyConcurrentIsEmpty(new SlowSynchronizedQueue<String>(), false);
	}

	private void verifyConcurrentIsEmpty(SlowQueue<String> slowQueue, boolean empty) throws Exception {
		Thread thread = this.buildThread(this.buildRunnableEnqueue(slowQueue, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(empty, slowQueue.isEmpty());
		thread.join();
		assertEquals("first", slowQueue.dequeue());
		assertTrue(slowQueue.isEmpty());
	}


	private interface SlowQueue<E> extends Queue<E> {
		Object slowDequeue();
		void slowEnqueue(E element);
	}

	private class SlowLinkedQueue<E> extends LinkedQueue<E> implements SlowQueue<E> {
		private static final long serialVersionUID = 1L;
		SlowLinkedQueue() {
			super();
		}
		public Object slowDequeue() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.dequeue();
		}
		public void slowEnqueue(E element) {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			this.enqueue(element);
		}
	}

	private class SlowSynchronizedQueue<E> extends SynchronizedQueue<E> implements SlowQueue<E> {
		private static final long serialVersionUID = 1L;
		SlowSynchronizedQueue() {
			super(QueueTools.<E>linkedQueue());
		}
		public Object slowDequeue() {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				return this.dequeue();
			}
		}
		public void slowEnqueue(E element) {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				this.enqueue(element);
			}
		}
	}


	// ********** waits **********

	public void testWaitUntilEmpty() throws Exception {
		this.verifyWaitUntilEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmpty2() throws Exception {
		this.verifyWaitUntilEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmptyTimeout() throws Exception {
		this.verifyWaitUntilEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was dequeued...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilEmpty(long timeout) throws Exception {
		this.sq.enqueue(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueCommand(), this.sq, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilEmptyCommand(timeout), this.sq, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				SynchronizedQueueTests.this.startTime = System.currentTimeMillis();
				SynchronizedQueueTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedQueue);
				SynchronizedQueueTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				if (timeout < 0) {
					synchronizedQueue.waitUntilEmpty();
					return false;
				}
				return ! synchronizedQueue.waitUntilEmpty(timeout);
			}
		};
	}

	public void testWaitUntilNotEmpty() throws Exception {
		this.verifyWaitUntilNotEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been enqueued by t1...
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_1, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmpty2() throws Exception {
		this.verifyWaitUntilNotEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been enqueued by t1...
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_1, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmptyTimeout() throws Exception {
		this.verifyWaitUntilNotEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and an item should have been enqueued by t1...
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_1, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilNotEmpty(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueCommand(), this.sq, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilNotEmptyCommand(timeout), this.sq, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilNotEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				SynchronizedQueueTests.this.startTime = System.currentTimeMillis();
				SynchronizedQueueTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedQueue);
				SynchronizedQueueTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				if (timeout < 0) {
					synchronizedQueue.waitUntilNotEmpty();
					return false;
				}
				return ! synchronizedQueue.waitUntilNotEmpty(timeout);
			}
		};
	}

	public void testWaitToDequeue() throws Exception {
		this.verifyWaitToDequeue(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeued by t2...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeue2() throws Exception {
		this.verifyWaitToDequeue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeued by t2...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeueTimeout() throws Exception {
		this.verifyWaitToDequeue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was never dequeued...
		assertNull(this.dequeuedObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToDequeue(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueCommand(), this.sq, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToDequeueCommand(timeout), this.sq, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToDequeueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				SynchronizedQueueTests.this.startTime = System.currentTimeMillis();
				this.waitToDequeue(synchronizedQueue);
				SynchronizedQueueTests.this.endTime = System.currentTimeMillis();
			}
			private void waitToDequeue(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				if (timeout < 0) {
					SynchronizedQueueTests.this.dequeuedObject = synchronizedQueue.waitToDequeue();
					return;
				}
				try {
					SynchronizedQueueTests.this.dequeuedObject = synchronizedQueue.waitToDequeue(timeout);
				} catch (NoSuchElementException ex) {
					SynchronizedQueueTests.this.timeoutOccurred = true;
				}
			}
		};
	}

	public void testWaitToEnqueue() throws Exception {
		this.verifyWaitToEnqueue(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and an item is enqueued on to the queue by t2
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_2, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueue2() throws Exception {
		this.verifyWaitToEnqueue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and an item is enqueued on to the queue by t2
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_2, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueueTimeout() throws Exception {
		this.verifyWaitToEnqueue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue is eventually dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...but nothing is enqueued on to the queue by t2
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToEnqueue(long timeout) throws Exception {
		this.sq.enqueue(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueCommand(), this.sq, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToEnqueueCommand(timeout), this.sq, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToEnqueueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				SynchronizedQueueTests.this.startTime = System.currentTimeMillis();
				SynchronizedQueueTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedQueue);
				SynchronizedQueueTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException {
				if (timeout < 0) {
					synchronizedQueue.waitToEnqueue(ITEM_2);
					return false;
				}
				return ! synchronizedQueue.waitToEnqueue(ITEM_2, timeout);
			}
		};
	}

	private Command buildEnqueueCommand() {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) {
				synchronizedQueue.enqueue(ITEM_1);
			}
		};
	}

	private Command buildDequeueCommand() {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) {
				SynchronizedQueueTests.this.dequeuedObject = synchronizedQueue.dequeue();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedQueue<String> synchronizedQueue, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws Throwable {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(synchronizedQueue);
			}
		};
	}

	long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedQueue<String> synchronizedQueue) throws InterruptedException;
	}


	// ********** execute **********

	public void testExecute() throws Exception {
		org.eclipse.jpt.common.utility.command.Command command = new org.eclipse.jpt.common.utility.command.Command() {
			public void execute() {
				SynchronizedQueueTests.this.commandExecuted = true;
			}
		};
		this.commandExecuted = false;
		this.sq.execute(command);
		assertTrue(this.commandExecuted);
	}


	// ********** additional protocol **********

	public void testEnqueueAllIterable() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		this.sq.enqueueAll(list);
		assertEquals("one", this.sq.dequeue());
		assertEquals("two", this.sq.dequeue());
		assertEquals("three", this.sq.dequeue());
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllIterable_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		this.sq.enqueueAll(list);
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllObjectArray() throws Exception {
		this.sq.enqueueAll(new String[] { "one", "two", "three" });
		assertEquals("one", this.sq.dequeue());
		assertEquals("two", this.sq.dequeue());
		assertEquals("three", this.sq.dequeue());
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllObjectArray_empty() throws Exception {
		this.sq.enqueueAll(new String[0]);
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllStack() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		this.sq.enqueueAll(stack);
		assertEquals("three", this.sq.dequeue());
		assertEquals("two", this.sq.dequeue());
		assertEquals("one", this.sq.dequeue());
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		this.sq.enqueueAll(stack);
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllQueue() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("one");
		queue.enqueue("two");
		queue.enqueue("three");
		this.sq.enqueueAll(queue);
		assertEquals("one", this.sq.dequeue());
		assertEquals("two", this.sq.dequeue());
		assertEquals("three", this.sq.dequeue());
		assertTrue(this.sq.isEmpty());
	}

	public void testEnqueueAllQueue_empty() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		this.sq.enqueueAll(queue);
		assertTrue(this.sq.isEmpty());
	}

	public void testDrain() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		ArrayList<String> list = this.sq.drain();
		assertTrue(this.sq.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrain_empty() throws Exception {
		ArrayList<String> list = this.sq.drain();
		assertTrue(this.sq.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainToCollection() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(this.sq.drainTo(list));
		assertTrue(this.sq.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainToCollection_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(this.sq.drainTo(list));
		assertTrue(this.sq.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainToListInt() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.sq.drainTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("one", list.get(2));
		assertEquals("two", list.get(3));
		assertEquals("three", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testDrainToListInt_end() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.sq.drainTo(list, 3));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("one", list.get(3));
		assertEquals("two", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainToListInt_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertFalse(this.sq.drainTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
	}

	public void testDrainToStack() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(this.sq.drainTo(stack));
		assertTrue(this.sq.isEmpty());
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testDrainToStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(this.sq.drainTo(stack));
		assertTrue(this.sq.isEmpty());
		assertTrue(stack.isEmpty());
	}

	public void testDrainToQueue() throws Exception {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertTrue(this.sq.drainTo(queue));
		assertTrue(this.sq.isEmpty());
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testDrainToQueue_empty() throws Exception {
		Queue<String> queue = QueueTools.arrayQueue();
		assertFalse(this.sq.drainTo(queue));
		assertTrue(this.sq.isEmpty());
		assertTrue(queue.isEmpty());
	}

	public void testDrainToMapTransformer() {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.sq.drainTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainToMapTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.sq.drainTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainToMapTransformerTransformer() {
		this.sq.enqueue("one");
		this.sq.enqueue("two");
		this.sq.enqueue("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.sq.drainTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainToMapTransformerTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.sq.drainTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertTrue(map.isEmpty());
	}
}
