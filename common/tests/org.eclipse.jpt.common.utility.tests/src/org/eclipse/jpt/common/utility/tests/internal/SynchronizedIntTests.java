/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import org.eclipse.jpt.common.utility.internal.SynchronizedInt;

@SuppressWarnings("nls")
public class SynchronizedIntTests
	extends MultiThreadedTestCase
{
	private volatile SynchronizedInt si;
	volatile boolean timeoutOccurred;
	volatile int value = 7;
	volatile long startTime;
	volatile long endTime;
	volatile int sIntValue;


	public SynchronizedIntTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.si = new SynchronizedInt();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.sIntValue = 0;
	}

	public void testGetValue() throws Exception {
		assertEquals(0, this.si.getValue());
	}

	public void testEqualsInt() throws Exception {
		assertTrue(this.si.equals(0));
		this.si.setValue(this.value);
		assertTrue(this.si.equals(7));
	}

	public void testNotEqualInt() throws Exception {
		assertTrue(this.si.notEqual(7));
		this.si.setValue(this.value);
		assertTrue(this.si.notEqual(0));
	}

	public void testIsZero() throws Exception {
		assertTrue(this.si.isZero());
		this.si.setValue(this.value);
		assertFalse(this.si.isZero());
	}

	public void testIsNotZero() throws Exception {
		assertFalse(this.si.isNotZero());
		this.si.setValue(this.value);
		assertTrue(this.si.isNotZero());
	}

	public void testIsGreaterThan() throws Exception {
		assertTrue(this.si.isGreaterThan(-1));
		assertFalse(this.si.isGreaterThan(0));
		assertFalse(this.si.isGreaterThan(1));
		this.si.setValue(this.value);
		assertTrue(this.si.isGreaterThan(-1));
		assertFalse(this.si.isGreaterThan(7));
		assertFalse(this.si.isGreaterThan(8));
	}

	public void testIsGreaterThanOrEqual() throws Exception {
		assertTrue(this.si.isGreaterThanOrEqual(-1));
		assertTrue(this.si.isGreaterThanOrEqual(0));
		assertFalse(this.si.isGreaterThanOrEqual(1));
		this.si.setValue(this.value);
		assertTrue(this.si.isGreaterThanOrEqual(-1));
		assertTrue(this.si.isGreaterThanOrEqual(7));
		assertFalse(this.si.isGreaterThanOrEqual(8));
	}

	public void testIsLessThan() throws Exception {
		assertFalse(this.si.isLessThan(-1));
		assertFalse(this.si.isLessThan(0));
		assertTrue(this.si.isLessThan(1));
		this.si.setValue(this.value);
		assertFalse(this.si.isLessThan(-1));
		assertFalse(this.si.isLessThan(7));
		assertTrue(this.si.isLessThan(8));
	}

	public void testIsLessThanOrEqual() throws Exception {
		assertFalse(this.si.isLessThanOrEqual(-1));
		assertTrue(this.si.isLessThanOrEqual(0));
		assertTrue(this.si.isLessThanOrEqual(1));
		this.si.setValue(this.value);
		assertFalse(this.si.isLessThanOrEqual(-1));
		assertTrue(this.si.isLessThanOrEqual(7));
		assertTrue(this.si.isLessThanOrEqual(8));
	}

	public void testIsPositive() throws Exception {
		assertFalse(this.si.isPositive());
		this.si.setValue(this.value);
		assertTrue(this.si.isPositive());
		this.si.setValue(-3);
		assertFalse(this.si.isPositive());
	}

	public void testIsNotPositive() throws Exception {
		assertTrue(this.si.isNotPositive());
		this.si.setValue(this.value);
		assertFalse(this.si.isNotPositive());
		this.si.setValue(-3);
		assertTrue(this.si.isNotPositive());
	}

	public void testIsNegative() throws Exception {
		assertFalse(this.si.isNegative());
		this.si.setValue(this.value);
		assertFalse(this.si.isNegative());
		this.si.setValue(-3);
		assertTrue(this.si.isNegative());
	}

	public void testIsNotNegative() throws Exception {
		assertTrue(this.si.isNotNegative());
		this.si.setValue(this.value);
		assertTrue(this.si.isNotNegative());
		this.si.setValue(-3);
		assertFalse(this.si.isNotNegative());
	}

	public void testSetValue() throws Exception {
		this.si.setValue(0);
		assertEquals(0, this.si.getValue());
		assertFalse(this.si.isNotZero());
		assertTrue(this.si.isZero());

		this.si.setValue(this.value);
		assertEquals(this.value, this.si.getValue());
		assertTrue(this.si.isNotZero());
		assertFalse(this.si.isZero());
	}

	public void testAbs() throws Exception {
		assertEquals(0, this.si.abs());
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(this.value, this.si.abs());
		assertEquals(this.value, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(this.value, this.si.abs());
	}

	public void testNeg() throws Exception {
		assertEquals(0, this.si.neg());
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(-this.value, this.si.neg());
		this.si.setValue(-this.value);
		assertEquals(this.value, this.si.neg());
	}

	public void testSetZero() throws Exception {
		this.si.setZero();
		assertEquals(0, this.si.getValue());
		assertFalse(this.si.isNotZero());
		assertTrue(this.si.isZero());
	}

	public void testGetMutexThis() throws Exception {
		assertSame(this.si, this.si.getMutex());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0
	 */
	public void testWaitUntilZero() throws Exception {
		this.verifyWaitUntilZero(0);  // 0 = indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to 0 by t2
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to 0
	 */
	public void testWaitUntilZeroTimeout() throws Exception {
		this.verifyWaitUntilZero(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to 0 by t1
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < " + THREE_TICKS + "): " + time, time < THREE_TICKS);
	}

	private void verifyWaitUntilZero(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetZeroCommand(), this.buildWaitUntilZeroCommand(t2Timeout));
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0;
	 * then t2 will set the value to 7
	 */
	public void testWaitToSetValue() throws Exception {
		this.verifyWaitToSetValue(0);  // 0 = indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to an object by t2
		assertTrue(this.si.isNotZero());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to 0
	 */
	public void testWaitToSetValueTimeout() throws Exception {
		this.verifyWaitToSetValue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to zero by t1
		assertTrue(this.si.isZero());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < " + THREE_TICKS + "): " + time, time < THREE_TICKS);
	}

	private void verifyWaitToSetValue(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetZeroCommand(), this.buildWaitToSetValueCommand(t2Timeout));
	}

	/**
	 * t2 will wait until t1 is finished "initializing" the value;
	 * then t2 will get the newly-initialized value (42)
	 */
	public void testExecute() throws Exception {
		this.si.setValue(0);
		Runnable r1 = this.buildRunnable(this.buildInitializeValueCommand(), this.si, 0);
		// give t1 a head start
		Runnable r2 = this.buildRunnable(this.buildGetValueCommand(), this.si, TICK);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		assertEquals(42, this.si.getValue());
		assertEquals(42, this.sIntValue);
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TWO_TICKS + "): " + time, time > TWO_TICKS);
	}

	private void executeThreads(Command t1Command, Command t2Command) throws Exception {
		this.si.setValue(this.value);
		Runnable r1 = this.buildRunnable(t1Command, this.si, TWO_TICKS);
		Runnable r2 = this.buildRunnable(t2Command, this.si, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildSetZeroCommand() {
		return new Command() {
			public void execute(SynchronizedInt sInt) {
				sInt.setZero();
			}
		};
	}

	private Command buildWaitUntilZeroCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws InterruptedException {
				SynchronizedIntTests.this.startTime = System.currentTimeMillis();
				SynchronizedIntTests.this.timeoutOccurred = ! sInt.waitUntilZero(timeout);
				SynchronizedIntTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildWaitToSetValueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws InterruptedException {
				SynchronizedIntTests.this.startTime = System.currentTimeMillis();
				SynchronizedIntTests.this.timeoutOccurred = ! sInt.waitToSetValue(SynchronizedIntTests.this.value, timeout);
				SynchronizedIntTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildInitializeValueCommand() {
		return new Command() {
			public void execute(final SynchronizedInt sInt) throws InterruptedException {
				sInt.execute(
					new org.eclipse.jpt.common.utility.Command() {
						public void execute() {
							// pretend to perform some long initialization process
							try {
								Thread.sleep(5 * TICK);
							} catch (InterruptedException ex) {
								throw new RuntimeException(ex);
							}
							sInt.setValue(42);
						}
					}
				);
			}
		};
	}

	private Command buildGetValueCommand() {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws InterruptedException {
				SynchronizedIntTests.this.startTime = System.currentTimeMillis();
				SynchronizedIntTests.this.sIntValue = sInt.getValue();
				SynchronizedIntTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedInt sInt, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws InterruptedException {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(sInt);
			}
		};
	}

	private long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedInt sInt) throws InterruptedException;
	}

}
