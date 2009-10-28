/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.Queue;
import org.eclipse.jpt.utility.internal.SimpleQueue;
import org.eclipse.jpt.utility.internal.SynchronizedQueue;

@SuppressWarnings("nls")
public class SynchronizedQueueTests extends SimpleQueueTests {
	private volatile SynchronizedQueue<String> sq;
	private volatile boolean exCaught;
	private volatile boolean timeoutOccurred;
	private volatile long startTime;
	private volatile long endTime;
	private volatile Object dequeuedObject;

	static final String ITEM_1 = new String();
	static final String ITEM_2 = new String();

	public SynchronizedQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return new SynchronizedQueue<String>();
	}

	@Override
	public void testClone() {
		// synchronized queue is not cloneable
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.sq = new SynchronizedQueue<String>();
		this.exCaught = false;
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.dequeuedObject = null;
	}

	/**
	 * test first with an unsynchronized queue,
	 * then with a synchronized queue
	 */
	public void testConcurrentAccess() throws Exception {
		this.verifyConcurrentAccess(new SlowSimpleQueue<String>(), "first");
		this.verifyConcurrentAccess(new SlowSynchronizedQueue<String>(), "second");
	}

	private void verifyConcurrentAccess(SlowQueue<String> slowQueue, String expected) throws Exception {
		slowQueue.enqueue("first");
		slowQueue.enqueue("second");

		Thread thread = new Thread(this.buildRunnable(slowQueue));
		thread.start();
		Thread.sleep(200);

		assertEquals(expected, slowQueue.dequeue());
		thread.join();
		assertTrue(slowQueue.isEmpty());
	}

	private Runnable buildRunnable(final SlowQueue<String> slowQueue) {
		return new Runnable() {
			public void run() {
				slowQueue.slowDequeue();
			}
		};
	}


	private interface SlowQueue<E> extends Queue<E> {
		Object slowDequeue();
	}

	private class SlowSimpleQueue<E> extends SimpleQueue<E> implements SlowQueue<E> {
		SlowSimpleQueue() {
			super();
		}
		public Object slowDequeue() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.dequeue();
		}

	}

	private class SlowSynchronizedQueue<E> extends SynchronizedQueue<E> implements SlowQueue<E> {
		SlowSynchronizedQueue() {
			super();
		}
		public synchronized Object slowDequeue() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.dequeue();
		}

	}


	// ********** waits **********

	public void testWaitToDequeue() throws Exception {
		this.verifyWaitToDequeue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been dequeued by t2...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and the queue should be empty
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() > 150);
	}

	public void testWaitToDequeueTimeout() throws Exception {
		this.verifyWaitToDequeue(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue was never dequeued...
		assertNull(this.dequeuedObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() < 150);
	}

	private void verifyWaitToDequeue(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildEnqueueCommand(), this.sq, 200);
		Runnable r2 = this.buildRunnable(this.buildWaitToDequeueCommand(timeout), this.sq, 0);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
	}

	public void testWaitToEnqueue() throws Exception {
		this.verifyWaitToEnqueue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the queue gets dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...and an item is enqueued on to the queue by t2
		assertFalse(this.sq.isEmpty());
		assertSame(ITEM_2, this.sq.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() > 150);
	}

	public void testWaitToEnqueueTimeout() throws Exception {
		this.verifyWaitToEnqueue(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the queue is eventually dequeued by t1...
		assertSame(ITEM_1, this.dequeuedObject);
		// ...but nothing is enqueued on to the queue by t2
		assertTrue(this.sq.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() < 150);
	}

	private void verifyWaitToEnqueue(long timeout) throws Exception {
		this.sq.enqueue(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildDequeueCommand(), this.sq, 200);
		Runnable r2 = this.buildRunnable(this.buildWaitToEnqueueCommand(timeout), this.sq, 0);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
	}

	private Command buildEnqueueCommand() {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) {
				synchronizedQueue.enqueue(ITEM_1);
			}
		};
	}

	private Command buildWaitToDequeueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws Exception {
				SynchronizedQueueTests.this.setStartTime(System.currentTimeMillis());
				try {
					SynchronizedQueueTests.this.setDequeuedObject(synchronizedQueue.waitToDequeue(timeout));
				} catch (NoSuchElementException ex) {
					SynchronizedQueueTests.this.setTimeoutOccurred(true);
				}
				SynchronizedQueueTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Command buildDequeueCommand() {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) {
				SynchronizedQueueTests.this.setDequeuedObject(synchronizedQueue.dequeue());
			}
		};
	}

	private Command buildWaitToEnqueueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedQueue<String> synchronizedQueue) throws Exception {
				SynchronizedQueueTests.this.setStartTime(System.currentTimeMillis());
				SynchronizedQueueTests.this.setTimeoutOccurred( ! synchronizedQueue.waitToEnqueue(ITEM_2, timeout));
				SynchronizedQueueTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedQueue<String> synchronizedQueue, final long sleep) {
		return new Runnable() {
			public void run() {
				try {
					if (sleep != 0) {
						Thread.sleep(sleep);
					}
					command.execute(synchronizedQueue);
				} catch (Exception ex) {
					SynchronizedQueueTests.this.setExCaught(true);
				}
			}
		};
	}

	void setExCaught(boolean exCaught) {
		this.exCaught = exCaught;
	}

	void setTimeoutOccurred(boolean timeoutOccurred) {
		this.timeoutOccurred = timeoutOccurred;
	}

	void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	void setDequeuedObject(Object dequeuedObject) {
		this.dequeuedObject = dequeuedObject;
	}

	long elapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedQueue<String> synchronizedQueue) throws Exception;
	}

}
