/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.LinkedDeque;
import org.eclipse.jpt.common.utility.internal.deque.SynchronizedDeque;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;

@SuppressWarnings("nls")
public class SynchronizedDequeTests
	extends DequeTests
{
	private volatile SynchronizedDeque<String> syncDeque;
	volatile boolean timeoutOccurred;
	volatile long startTime;
	volatile long endTime;
	volatile Object dequeueHeadObject;
	volatile Object dequeueTailObject;

	boolean commandExecuted;

	static final String ITEM_1 = new String();
	static final String ITEM_2 = new String();

	public SynchronizedDequeTests(String name) {
		super(name);
	}

	@Override
	Deque<String> buildDeque() {
		return DequeTools.synchronizedDeque();
	}

	@Override
	public void testClone() {
		// unsupported
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.syncDeque = DequeTools.synchronizedDeque();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.dequeueHeadObject = null;
	}

	// ********** constructor **********

	public void testConstructorDeque() throws Exception {
		Deque<String> innerDeque = DequeTools.arrayDeque();
		SynchronizedDeque<String> stack = DequeTools.synchronizedDeque(innerDeque);
		assertNotNull(stack);
		assertSame(stack, stack.getMutex());
	}

	public void testConstructorDeque_NPE() throws Exception {
		boolean exCaught = false;
		try {
			Deque<String> stack = DequeTools.synchronizedDeque(null);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorDequeObject() throws Exception {
		String mutex = "mutex";
		Deque<String> innerDeque = DequeTools.arrayDeque();
		SynchronizedDeque<String> stack = DequeTools.synchronizedDeque(innerDeque, mutex);
		assertNotNull(stack);
		assertSame(mutex, stack.getMutex());
	}

	public void testConstructorDequeObject_NPE1() throws Exception {
		String mutex = "mutex";
		boolean exCaught = false;
		try {
			Deque<String> stack = DequeTools.synchronizedDeque(null, mutex);
			fail("bogus stack: " + stack);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorDequeObject_NPE2() throws Exception {
		Deque<String> innerDeque = DequeTools.arrayDeque();
		boolean exCaught = false;
		try {
			Deque<String> stack = DequeTools.synchronizedDeque(innerDeque, null);
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
	public void testConcurrentDequeueHead() throws Exception {
		this.verifyConcurrentDequeueHead(new SlowLinkedDeque<String>(), "first");
		this.verifyConcurrentDequeueHead(new SlowSynchronizedDeque<String>(), "second");
	}

	private void verifyConcurrentDequeueHead(SlowDeque<String> slowDeque, String expected) throws Exception {
		slowDeque.enqueueTail("first");
		slowDeque.enqueueTail("second");

		Thread thread = this.buildThread(this.buildRunnableDequeueHead(slowDeque));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(expected, slowDeque.dequeueHead());
		thread.join();
		assertTrue(slowDeque.isEmpty());
	}

	private Runnable buildRunnableDequeueHead(final SlowDeque<String> slowDeque) {
		return new Runnable() {
			public void run() {
				slowDeque.slowDequeueHead();
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentDequeueTail() throws Exception {
		this.verifyConcurrentDequeueTail(new SlowLinkedDeque<String>(), "first");
		this.verifyConcurrentDequeueTail(new SlowSynchronizedDeque<String>(), "second");
	}

	private void verifyConcurrentDequeueTail(SlowDeque<String> slowDeque, String expected) throws Exception {
		slowDeque.enqueueHead("first");
		slowDeque.enqueueHead("second");

		Thread thread = this.buildThread(this.buildRunnableDequeueTail(slowDeque));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(expected, slowDeque.dequeueTail());
		thread.join();
		assertTrue(slowDeque.isEmpty());
	}

	private Runnable buildRunnableDequeueTail(final SlowDeque<String> slowDeque) {
		return new Runnable() {
			public void run() {
				slowDeque.slowDequeueTail();
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentEnqueueTail() throws Exception {
		this.verifyConcurrentEnqueueTail(new SlowLinkedDeque<String>(), "second", "first");
		this.verifyConcurrentEnqueueTail(new SlowSynchronizedDeque<String>(), "first", "second");
	}

	private void verifyConcurrentEnqueueTail(SlowDeque<String> slowDeque, String first, String second) throws Exception {
		Thread thread = this.buildThread(this.buildRunnableEnqueueTail(slowDeque, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		slowDeque.enqueueTail("second");
		thread.join();
		assertEquals(first, slowDeque.dequeueHead());
		assertEquals(second, slowDeque.dequeueHead());
		assertTrue(slowDeque.isEmpty());
	}

	private Runnable buildRunnableEnqueueTail(final SlowDeque<String> slowDeque, final String element) {
		return new Runnable() {
			public void run() {
				slowDeque.slowEnqueueTail(element);
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentEnqueueHead() throws Exception {
		this.verifyConcurrentEnqueueHead(new SlowLinkedDeque<String>(), "second", "first");
		this.verifyConcurrentEnqueueHead(new SlowSynchronizedDeque<String>(), "first", "second");
	}

	private void verifyConcurrentEnqueueHead(SlowDeque<String> slowDeque, String first, String second) throws Exception {
		Thread thread = this.buildThread(this.buildRunnableEnqueueHead(slowDeque, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		slowDeque.enqueueHead("second");
		thread.join();
		assertEquals(first, slowDeque.dequeueTail());
		assertEquals(second, slowDeque.dequeueTail());
		assertTrue(slowDeque.isEmpty());
	}

	private Runnable buildRunnableEnqueueHead(final SlowDeque<String> slowDeque, final String element) {
		return new Runnable() {
			public void run() {
				slowDeque.slowEnqueueHead(element);
			}
		};
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentIsEmpty() throws Exception {
		this.verifyConcurrentIsEmpty(new SlowLinkedDeque<String>(), true);
		this.verifyConcurrentIsEmpty(new SlowSynchronizedDeque<String>(), false);
	}

	private void verifyConcurrentIsEmpty(SlowDeque<String> slowDeque, boolean empty) throws Exception {
		Thread thread = this.buildThread(this.buildRunnableEnqueueTail(slowDeque, "first"));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(empty, slowDeque.isEmpty());
		thread.join();
		assertEquals("first", slowDeque.dequeueHead());
		assertTrue(slowDeque.isEmpty());
	}


	private interface SlowDeque<E> extends Deque<E> {
		Object slowDequeueHead();
		Object slowDequeueTail();
		void slowEnqueueTail(E element);
		void slowEnqueueHead(E element);
	}

	private class SlowLinkedDeque<E> extends LinkedDeque<E> implements SlowDeque<E> {
		private static final long serialVersionUID = 1L;
		SlowLinkedDeque() {
			super();
		}
		public Object slowDequeueHead() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.dequeueHead();
		}
		public Object slowDequeueTail() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.dequeueTail();
		}
		public void slowEnqueueTail(E element) {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			this.enqueueTail(element);
		}
		public void slowEnqueueHead(E element) {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			this.enqueueHead(element);
		}
	}

	private class SlowSynchronizedDeque<E> extends SynchronizedDeque<E> implements SlowDeque<E> {
		private static final long serialVersionUID = 1L;
		SlowSynchronizedDeque() {
			super(DequeTools.<E>linkedDeque());
		}
		public Object slowDequeueHead() {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				return this.dequeueHead();
			}
		}
		public Object slowDequeueTail() {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				return this.dequeueTail();
			}
		}
		public void slowEnqueueTail(E element) {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				this.enqueueTail(element);
			}
		}
		public void slowEnqueueHead(E element) {
			synchronized (this.getMutex()) {
				try {
					Thread.sleep(5 * TICK);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				this.enqueueHead(element);
			}
		}
	}


	// ********** waits **********

	public void testWaitUntilEmpty() throws Exception {
		this.verifyWaitUntilEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmpty2() throws Exception {
		this.verifyWaitUntilEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilEmptyTimeout() throws Exception {
		this.verifyWaitUntilEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was dequeueHead...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilEmpty(long timeout) throws Exception {
		this.syncDeque.enqueueTail(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueHeadCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilEmptyCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				SynchronizedDequeTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					synchronizedDeque.waitUntilEmpty();
					return false;
				}
				return ! synchronizedDeque.waitUntilEmpty(timeout);
			}
		};
	}

	public void testWaitUntilNotEmpty() throws Exception {
		this.verifyWaitUntilNotEmpty(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been enqueueTail by t1...
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_1, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmpty2() throws Exception {
		this.verifyWaitUntilNotEmpty(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been enqueueTail by t1...
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_1, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilNotEmptyTimeout() throws Exception {
		this.verifyWaitUntilNotEmpty(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and an item should have been enqueueTail by t1...
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_1, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilNotEmpty(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueTailCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitUntilNotEmptyCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitUntilNotEmptyCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				SynchronizedDequeTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					synchronizedDeque.waitUntilNotEmpty();
					return false;
				}
				return ! synchronizedDeque.waitUntilNotEmpty(timeout);
			}
		};
	}

	public void testWaitToDequeueHead() throws Exception {
		this.verifyWaitToDequeueHead(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueHead by t2...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeueHead2() throws Exception {
		this.verifyWaitToDequeueHead(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueHead by t2...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeueHeadTimeout() throws Exception {
		this.verifyWaitToDequeueHead(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was never dequeueHead...
		assertNull(this.dequeueHeadObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToDequeueHead(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueTailCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToDequeueHeadCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToDequeueHeadCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				this.waitToDequeueHead(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private void waitToDequeueHead(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					SynchronizedDequeTests.this.dequeueHeadObject = synchronizedDeque.waitToDequeueHead();
					return;
				}
				try {
					SynchronizedDequeTests.this.dequeueHeadObject = synchronizedDeque.waitToDequeueHead(timeout);
				} catch (NoSuchElementException ex) {
					SynchronizedDequeTests.this.timeoutOccurred = true;
				}
			}
		};
	}

	public void testWaitToDequeueTail() throws Exception {
		this.verifyWaitToDequeueTail(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueTaild by t2...
		assertSame(ITEM_1, this.dequeueTailObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeueTail2() throws Exception {
		this.verifyWaitToDequeueTail(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeueTaild by t2...
		assertSame(ITEM_1, this.dequeueTailObject);
		// ...and the queue should be empty
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToDequeueTailTimeout() throws Exception {
		this.verifyWaitToDequeueTail(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was never dequeueTaild...
		assertNull(this.dequeueTailObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.syncDeque.peekTail());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToDequeueTail(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueHeadCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToDequeueTailCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToDequeueTailCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				this.waitToDequeueTail(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private void waitToDequeueTail(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					SynchronizedDequeTests.this.dequeueTailObject = synchronizedDeque.waitToDequeueTail();
					return;
				}
				try {
					SynchronizedDequeTests.this.dequeueTailObject = synchronizedDeque.waitToDequeueTail(timeout);
				} catch (NoSuchElementException ex) {
					SynchronizedDequeTests.this.timeoutOccurred = true;
				}
			}
		};
	}

	public void testWaitToEnqueueTail() throws Exception {
		this.verifyWaitToEnqueueTail(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and an item is enqueueTail on to the queue by t2
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_2, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueueTail2() throws Exception {
		this.verifyWaitToEnqueueTail(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and an item is enqueueTail on to the queue by t2
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_2, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueueTailTimeout() throws Exception {
		this.verifyWaitToEnqueueTail(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue is eventually dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...but nothing is enqueueTail on to the queue by t2
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToEnqueueTail(long timeout) throws Exception {
		this.syncDeque.enqueueTail(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueHeadCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToEnqueueTailCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToEnqueueTailCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				SynchronizedDequeTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					synchronizedDeque.waitToEnqueueTail(ITEM_2);
					return false;
				}
				return ! synchronizedDeque.waitToEnqueueTail(ITEM_2, timeout);
			}
		};
	}

	public void testWaitToEnqueueHead() throws Exception {
		this.verifyWaitToEnqueueHead(-1);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and an item is enqueueHead on to the queue by t2
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_2, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueueHead2() throws Exception {
		this.verifyWaitToEnqueueHead(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...and an item is enqueueHead on to the queue by t2
		assertFalse(this.syncDeque.isEmpty());
		assertSame(ITEM_2, this.syncDeque.peekHead());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToEnqueueHeadTimeout() throws Exception {
		this.verifyWaitToEnqueueHead(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue is eventually dequeueHead by t1...
		assertSame(ITEM_1, this.dequeueHeadObject);
		// ...but nothing is enqueueHead on to the queue by t2
		assertTrue(this.syncDeque.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToEnqueueHead(long timeout) throws Exception {
		this.syncDeque.enqueueHead(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueHeadCommand(), this.syncDeque, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToEnqueueHeadCommand(timeout), this.syncDeque, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildWaitToEnqueueHeadCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				SynchronizedDequeTests.this.startTime = System.currentTimeMillis();
				SynchronizedDequeTests.this.timeoutOccurred = this.timeoutOccurred(synchronizedDeque);
				SynchronizedDequeTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException {
				if (timeout < 0) {
					synchronizedDeque.waitToEnqueueHead(ITEM_2);
					return false;
				}
				return ! synchronizedDeque.waitToEnqueueHead(ITEM_2, timeout);
			}
		};
	}

	private Command buildEnqueueTailCommand() {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) {
				synchronizedDeque.enqueueTail(ITEM_1);
			}
		};
	}

	private Command buildEnqueueHeadCommand() {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) {
				synchronizedDeque.enqueueHead(ITEM_1);
			}
		};
	}

	private Command buildDequeueHeadCommand() {
		return new Command() {
			public void execute(SynchronizedDeque<String> synchronizedDeque) {
				SynchronizedDequeTests.this.dequeueHeadObject = synchronizedDeque.dequeueHead();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedDeque<String> synchronizedDeque, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws Throwable {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(synchronizedDeque);
			}
		};
	}

	long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedDeque<String> synchronizedDeque) throws InterruptedException;
	}


	// ********** execute **********

	public void testExecute() throws Exception {
		org.eclipse.jpt.common.utility.command.Command command = new org.eclipse.jpt.common.utility.command.Command() {
			public void execute() {
				SynchronizedDequeTests.this.commandExecuted = true;
			}
		};
		this.commandExecuted = false;
		this.syncDeque.execute(command);
		assertTrue(this.commandExecuted);
	}


	// ********** additional protocol **********

	public void testEnqueueTailAllIterable() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		this.syncDeque.enqueueTailAll(list);
		assertEquals("one", this.syncDeque.dequeueHead());
		assertEquals("two", this.syncDeque.dequeueHead());
		assertEquals("three", this.syncDeque.dequeueHead());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllIterable_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		this.syncDeque.enqueueTailAll(list);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllObjectArray() throws Exception {
		this.syncDeque.enqueueTailAll(new String[] { "one", "two", "three" });
		assertEquals("one", this.syncDeque.dequeueHead());
		assertEquals("two", this.syncDeque.dequeueHead());
		assertEquals("three", this.syncDeque.dequeueHead());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllObjectArray_empty() throws Exception {
		this.syncDeque.enqueueTailAll(new String[0]);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllStack() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		this.syncDeque.enqueueTailAll(stack);
		assertEquals("three", this.syncDeque.dequeueHead());
		assertEquals("two", this.syncDeque.dequeueHead());
		assertEquals("one", this.syncDeque.dequeueHead());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		this.syncDeque.enqueueTailAll(stack);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllDeque() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		queue.enqueueTail("one");
		queue.enqueueTail("two");
		queue.enqueueTail("three");
		this.syncDeque.enqueueTailAll(queue);
		assertEquals("one", this.syncDeque.dequeueHead());
		assertEquals("two", this.syncDeque.dequeueHead());
		assertEquals("three", this.syncDeque.dequeueHead());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueTailAllDeque_empty() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		this.syncDeque.enqueueTailAll(queue);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testDrainHead() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		ArrayList<String> list = this.syncDeque.drainHead();
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainHead_empty() throws Exception {
		ArrayList<String> list = this.syncDeque.drainHead();
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainHeadToCollection() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(this.syncDeque.drainHeadTo(list));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainHeadToCollection_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(this.syncDeque.drainHeadTo(list));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainHeadToListInt() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.syncDeque.drainHeadTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("one", list.get(2));
		assertEquals("two", list.get(3));
		assertEquals("three", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testDrainHeadToListInt_end() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.syncDeque.drainHeadTo(list, 3));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("one", list.get(3));
		assertEquals("two", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainHeadToListInt_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertFalse(this.syncDeque.drainHeadTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
	}

	public void testDrainHeadToStack() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(this.syncDeque.drainHeadTo(stack));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testDrainHeadToStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(this.syncDeque.drainHeadTo(stack));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(stack.isEmpty());
	}

	public void testDrainHeadToDeque() throws Exception {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("three");
		Deque<String> queue = DequeTools.arrayDeque();
		assertTrue(this.syncDeque.drainHeadTo(queue));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", queue.dequeueHead());
		assertEquals("two", queue.dequeueHead());
		assertEquals("three", queue.dequeueHead());
		assertTrue(queue.isEmpty());
	}

	public void testDrainHeadToDeque_empty() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		assertFalse(this.syncDeque.drainHeadTo(queue));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(queue.isEmpty());
	}

	public void testDrainHeadToMapTransformer() {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.syncDeque.drainHeadTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainHeadToMapTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.syncDeque.drainHeadTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainHeadToMapTransformerTransformer() {
		this.syncDeque.enqueueTail("one");
		this.syncDeque.enqueueTail("two");
		this.syncDeque.enqueueTail("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.syncDeque.drainHeadTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainHeadToMapTransformerTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.syncDeque.drainHeadTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertTrue(map.isEmpty());
	}

	public void testEnqueueHeadAllIterable() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		this.syncDeque.enqueueHeadAll(list);
		assertEquals("one", this.syncDeque.dequeueTail());
		assertEquals("two", this.syncDeque.dequeueTail());
		assertEquals("three", this.syncDeque.dequeueTail());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllIterable_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		this.syncDeque.enqueueHeadAll(list);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllObjectArray() throws Exception {
		this.syncDeque.enqueueHeadAll(new String[] { "one", "two", "three" });
		assertEquals("one", this.syncDeque.dequeueTail());
		assertEquals("two", this.syncDeque.dequeueTail());
		assertEquals("three", this.syncDeque.dequeueTail());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllObjectArray_empty() throws Exception {
		this.syncDeque.enqueueHeadAll(new String[0]);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllStack() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		this.syncDeque.enqueueHeadAll(stack);
		assertEquals("three", this.syncDeque.dequeueTail());
		assertEquals("two", this.syncDeque.dequeueTail());
		assertEquals("one", this.syncDeque.dequeueTail());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		this.syncDeque.enqueueHeadAll(stack);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllDeque() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		queue.enqueueHead("one");
		queue.enqueueHead("two");
		queue.enqueueHead("three");
		this.syncDeque.enqueueHeadAll(queue);
		assertEquals("one", this.syncDeque.dequeueTail());
		assertEquals("two", this.syncDeque.dequeueTail());
		assertEquals("three", this.syncDeque.dequeueTail());
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testEnqueueHeadAllDeque_empty() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		this.syncDeque.enqueueHeadAll(queue);
		assertTrue(this.syncDeque.isEmpty());
	}

	public void testDrainTail() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		ArrayList<String> list = this.syncDeque.drainTail();
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainTail_empty() throws Exception {
		ArrayList<String> list = this.syncDeque.drainTail();
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainTailToCollection() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(this.syncDeque.drainTailTo(list));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainTailToCollection_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(this.syncDeque.drainTailTo(list));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(list.isEmpty());
	}

	public void testDrainTailToListInt() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.syncDeque.drainTailTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("one", list.get(2));
		assertEquals("two", list.get(3));
		assertEquals("three", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testDrainTailToListInt_end() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertTrue(this.syncDeque.drainTailTo(list, 3));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("one", list.get(3));
		assertEquals("two", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainTailToListInt_empty() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		assertFalse(this.syncDeque.drainTailTo(list, 2));
		assertEquals("aaa", list.get(0));
		assertEquals("bbb", list.get(1));
		assertEquals("ccc", list.get(2));
	}

	public void testDrainTailToStack() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(this.syncDeque.drainTailTo(stack));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testDrainTailToStack_empty() throws Exception {
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(this.syncDeque.drainTailTo(stack));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(stack.isEmpty());
	}

	public void testDrainTailToDeque() throws Exception {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("three");
		Deque<String> queue = DequeTools.arrayDeque();
		assertTrue(this.syncDeque.drainTailTo(queue));
		assertTrue(this.syncDeque.isEmpty());
		assertEquals("one", queue.dequeueTail());
		assertEquals("two", queue.dequeueTail());
		assertEquals("three", queue.dequeueTail());
		assertTrue(queue.isEmpty());
	}

	public void testDrainTailToDeque_empty() throws Exception {
		Deque<String> queue = DequeTools.arrayDeque();
		assertFalse(this.syncDeque.drainTailTo(queue));
		assertTrue(this.syncDeque.isEmpty());
		assertTrue(queue.isEmpty());
	}

	public void testDrainTailToMapTransformer() {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.syncDeque.drainTailTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainTailToMapTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.syncDeque.drainTailTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainTailToMapTransformerTransformer() {
		this.syncDeque.enqueueHead("one");
		this.syncDeque.enqueueHead("two");
		this.syncDeque.enqueueHead("zero");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(this.syncDeque.drainTailTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainTailToMapTransformerTransformer_empty() {
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(this.syncDeque.drainTailTo(map, DequeToolsTests.FIRST_LETTER_TRANSFORMER, DequeToolsTests.EMPHASIZER));
		assertTrue(map.isEmpty());
	}
}
