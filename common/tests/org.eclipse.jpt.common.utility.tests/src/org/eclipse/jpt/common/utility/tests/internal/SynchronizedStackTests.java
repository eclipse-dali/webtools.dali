/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.SimpleStack;
import org.eclipse.jpt.common.utility.internal.Stack;
import org.eclipse.jpt.common.utility.internal.SynchronizedStack;

@SuppressWarnings("nls")
public class SynchronizedStackTests
	extends SimpleStackTests
{
	private volatile SynchronizedStack<String> ss;
	volatile boolean timeoutOccurred;
	volatile long startTime;
	volatile long endTime;
	volatile Object poppedObject;

	static final String ITEM_1 = new String();
	static final String ITEM_2 = new String();

	public SynchronizedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new SynchronizedStack<String>();
	}

	@Override
	public void testClone() {
		// synchronized stack is not cloneable
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.ss = new SynchronizedStack<String>();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.poppedObject = null;
	}

	/**
	 * test first with an unsynchronized stack,
	 * then with a synchronized stack
	 */
	public void testConcurrentAccess() throws Exception {
		this.verifyConcurrentAccess(new SlowSimpleStack<String>(), "second");
		this.verifyConcurrentAccess(new SlowSynchronizedStack<String>(), "first");
	}

	private void verifyConcurrentAccess(SlowStack<String> slowStack, String expected) throws Exception {
		slowStack.push("first");
		slowStack.push("second");

		Thread thread = this.buildThread(this.buildRunnable(slowStack));
		thread.start();
		Thread.sleep(TWO_TICKS);

		assertEquals(expected, slowStack.pop());
		thread.join();
		assertTrue(slowStack.isEmpty());
	}

	private Runnable buildRunnable(final SlowStack<String> slowStack) {
		return new Runnable() {
			public void run() {
				slowStack.slowPop();
			}
		};
	}


	private interface SlowStack<E> extends Stack<E> {
		Object slowPop();
	}

	private class SlowSimpleStack<E> extends SimpleStack<E> implements SlowStack<E> {
		SlowSimpleStack() {
			super();
		}
		public Object slowPop() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.pop();
		}

	}

	private class SlowSynchronizedStack<E> extends SynchronizedStack<E> implements SlowStack<E> {
		SlowSynchronizedStack() {
			super();
		}
		public synchronized Object slowPop() {
			try {
				Thread.sleep(5 * TICK);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return this.pop();
		}

	}


	// ********** waits **********

	public void testWaitToPop() throws Exception {
		this.verifyWaitToPop(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and an item should have been popped by t2...
		assertSame(ITEM_1, this.poppedObject);
		// ...and the stack should be empty
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPopTimeout() throws Exception {
		this.verifyWaitToPop(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack was never popped...
		assertNull(this.poppedObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToPop(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildPushCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToPopCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	public void testWaitToPush() throws Exception {
		this.verifyWaitToPush(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the stack gets popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...and an item is pushed on to the stack by t2
		assertFalse(this.ss.isEmpty());
		assertSame(ITEM_2, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitToPushTimeout() throws Exception {
		this.verifyWaitToPush(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack is eventually popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...but nothing is pushed on to the stack by t2
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToPush(long timeout) throws Exception {
		this.ss.push(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildPopCommand(), this.ss, TWO_TICKS);
		Runnable r2 = this.buildRunnable(this.buildWaitToPushCommand(timeout), this.ss, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildPushCommand() {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) {
				synchronizedStack.push(ITEM_1);
			}
		};
	}

	private Command buildWaitToPopCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				try {
					SynchronizedStackTests.this.poppedObject = synchronizedStack.waitToPop(timeout);
				} catch (EmptyStackException ex) {
					SynchronizedStackTests.this.timeoutOccurred = true;
				}
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildPopCommand() {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) {
				SynchronizedStackTests.this.poppedObject = synchronizedStack.pop();
			}
		};
	}

	private Command buildWaitToPushCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException {
				SynchronizedStackTests.this.startTime = System.currentTimeMillis();
				SynchronizedStackTests.this.timeoutOccurred = ! synchronizedStack.waitToPush(ITEM_2, timeout);
				SynchronizedStackTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedStack<String> synchronizedStack, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws Throwable {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(synchronizedStack);
			}
		};
	}

	long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedStack<String> synchronizedStack) throws InterruptedException;
	}

}
