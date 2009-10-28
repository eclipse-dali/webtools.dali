/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.EmptyStackException;
import org.eclipse.jpt.utility.internal.SimpleStack;
import org.eclipse.jpt.utility.internal.Stack;
import org.eclipse.jpt.utility.internal.SynchronizedStack;

@SuppressWarnings("nls")
public class SynchronizedStackTests extends SimpleStackTests {
	private volatile SynchronizedStack<String> ss;
	private volatile boolean exCaught;
	private volatile boolean timeoutOccurred;
	private volatile long startTime;
	private volatile long endTime;
	private volatile Object poppedObject;

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
		this.exCaught = false;
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

		Thread thread = new Thread(this.buildRunnable(slowStack));
		thread.start();
		Thread.sleep(200);

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
				Thread.sleep(500);
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
				Thread.sleep(500);
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
		assertTrue(this.elapsedTime() > 150);
	}

	public void testWaitToPopTimeout() throws Exception {
		this.verifyWaitToPop(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack was never popped...
		assertNull(this.poppedObject);
		// ...and it still holds the item
		assertSame(ITEM_1, this.ss.peek());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() < 150);
	}

	private void verifyWaitToPop(long timeout) throws Exception {
		Runnable r1 = this.buildRunnable(this.buildPushCommand(), this.ss, 200);
		Runnable r2 = this.buildRunnable(this.buildWaitToPopCommand(timeout), this.ss, 0);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
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
		assertTrue(this.elapsedTime() > 150);
	}

	public void testWaitToPushTimeout() throws Exception {
		this.verifyWaitToPush(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the stack is eventually popped by t1...
		assertSame(ITEM_1, this.poppedObject);
		// ...but nothing is pushed on to the stack by t2
		assertTrue(this.ss.isEmpty());
		// make a reasonable guess about how long t2 took
		assertTrue(this.elapsedTime() < 150);
	}

	private void verifyWaitToPush(long timeout) throws Exception {
		this.ss.push(ITEM_1);
		Runnable r1 = this.buildRunnable(this.buildPopCommand(), this.ss, 200);
		Runnable r2 = this.buildRunnable(this.buildWaitToPushCommand(timeout), this.ss, 0);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
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
			public void execute(SynchronizedStack<String> synchronizedStack) throws Exception {
				SynchronizedStackTests.this.setStartTime(System.currentTimeMillis());
				try {
					SynchronizedStackTests.this.setPoppedObject(synchronizedStack.waitToPop(timeout));
				} catch (EmptyStackException ex) {
					SynchronizedStackTests.this.setTimeoutOccurred(true);
				}
				SynchronizedStackTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Command buildPopCommand() {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) {
				SynchronizedStackTests.this.setPoppedObject(synchronizedStack.pop());
			}
		};
	}

	private Command buildWaitToPushCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedStack<String> synchronizedStack) throws Exception {
				SynchronizedStackTests.this.setStartTime(System.currentTimeMillis());
				SynchronizedStackTests.this.setTimeoutOccurred( ! synchronizedStack.waitToPush(ITEM_2, timeout));
				SynchronizedStackTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedStack<String> synchronizedStack, final long sleep) {
		return new Runnable() {
			public void run() {
				try {
					if (sleep != 0) {
						Thread.sleep(sleep);
					}
					command.execute(synchronizedStack);
				} catch (Exception ex) {
					SynchronizedStackTests.this.setExCaught(true);
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

	void setPoppedObject(Object poppedObject) {
		this.poppedObject = poppedObject;
	}

	long elapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedStack<String> synchronizedStack) throws Exception;
	}

}
