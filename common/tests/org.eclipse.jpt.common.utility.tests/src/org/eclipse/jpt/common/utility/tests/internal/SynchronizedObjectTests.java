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

import org.eclipse.jpt.common.utility.internal.SynchronizedObject;

@SuppressWarnings("nls")
public class SynchronizedObjectTests
	extends MultiThreadedTestCase
{
	private volatile SynchronizedObject<Object> so;
	volatile boolean timeoutOccurred;
	volatile Object value = new Object();
	volatile long startTime;
	volatile long endTime;
	volatile Object soValue;


	public SynchronizedObjectTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.so = new SynchronizedObject<Object>();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.soValue = null;
	}

	public void testAccessors() throws Exception {
		this.so.setValue(null);
		assertNull(this.so.getValue());
		assertFalse(this.so.isNotNull());
		assertTrue(this.so.isNull());

		this.so.setValue(this.value);
		assertEquals(this.value, this.so.getValue());
		assertTrue(this.so.isNotNull());
		assertFalse(this.so.isNull());

		this.so.setNull();
		assertNull(this.so.getValue());
		assertFalse(this.so.isNotNull());
		assertTrue(this.so.isNull());

		assertSame(this.so, this.so.getMutex());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null
	 */
	public void testWaitUntilNull() throws Exception {
		this.verifyWaitUntilNull(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to null by t2
		assertNull(this.so.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to null
	 */
	public void testWaitUntilNullTimeout() throws Exception {
		this.verifyWaitUntilNull(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to null by t1
		assertNull(this.so.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < " + THREE_TICKS + "): " + time, time < THREE_TICKS);
	}

	private void verifyWaitUntilNull(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetNullCommand(), this.buildWaitUntilNullCommand(t2Timeout));
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null;
	 * then t2 will set the value to an object
	 */
	public void testWaitToSetValue() throws Exception {
		this.verifyWaitToSetValue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to an object by t2
		assertTrue(this.so.isNotNull());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to null
	 */
	public void testWaitToSetValueTimeout() throws Exception {
		this.verifyWaitToSetValue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to null by t1
		assertTrue(this.so.isNull());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < " + THREE_TICKS + "): " + time, time < THREE_TICKS);
	}

	private void verifyWaitToSetValue(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetNullCommand(), this.buildWaitToSetValueCommand(t2Timeout));
	}

	/**
	 * t2 will wait until t1 is finished "initializing" the value;
	 * then t2 will get the newly-initialized value ("foo")
	 */
	public void testExecute() throws Exception {
		this.so.setValue(null);
		Runnable r1 = this.buildRunnable(this.buildInitializeValueCommand(), this.so, 0);
		// give t1 a head start of 100 ms
		Runnable r2 = this.buildRunnable(this.buildGetValueCommand(), this.so, TICK);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		assertEquals("foo", this.so.getValue());
		assertEquals("foo", this.soValue);
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TWO_TICKS + "): " + time, time > TWO_TICKS);
	}

	private void executeThreads(Command t1Command, Command t2Command) throws Exception {
		this.so.setValue(this.value);
		Runnable r1 = this.buildRunnable(t1Command, this.so, TWO_TICKS);
		Runnable r2 = this.buildRunnable(t2Command, this.so, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildSetNullCommand() {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) {
				sObject.setNull();
			}
		};
	}

	private Command buildWaitUntilNullCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) throws InterruptedException {
				SynchronizedObjectTests.this.startTime = System.currentTimeMillis();
				SynchronizedObjectTests.this.timeoutOccurred = ! sObject.waitUntilNull(timeout);
				SynchronizedObjectTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildWaitToSetValueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) throws InterruptedException {
				SynchronizedObjectTests.this.startTime = System.currentTimeMillis();
				SynchronizedObjectTests.this.timeoutOccurred = ! sObject.waitToSetValue(SynchronizedObjectTests.this.value, timeout);
				SynchronizedObjectTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildInitializeValueCommand() {
		return new Command() {
			public void execute(final SynchronizedObject<Object> sObject) throws InterruptedException {
				sObject.execute(
					new org.eclipse.jpt.common.utility.Command() {
						public void execute() {
							// pretend to perform some long initialization process
							try {
								Thread.sleep(5 * TICK);
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
							sObject.setValue("foo");
						}
					}
				);
			}
		};
	}

	private Command buildGetValueCommand() {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) throws InterruptedException {
				SynchronizedObjectTests.this.startTime = System.currentTimeMillis();
				SynchronizedObjectTests.this.soValue = sObject.getValue();
				SynchronizedObjectTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedObject<Object> sObject, final long sleep) {
		return new TestRunnable() {
			@Override
			protected void run_() throws InterruptedException {
				if (sleep != 0) {
					Thread.sleep(sleep);
				}
				command.execute(sObject);
			}
		};
	}

	private long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedObject<Object> so) throws InterruptedException;
	}

}
