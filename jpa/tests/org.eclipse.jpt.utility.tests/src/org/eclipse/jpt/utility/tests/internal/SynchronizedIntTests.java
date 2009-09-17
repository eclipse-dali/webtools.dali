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

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.SynchronizedInt;

@SuppressWarnings("nls")
public class SynchronizedIntTests extends TestCase {
	private volatile SynchronizedInt si;
	private volatile boolean exCaught;
	private volatile boolean timeoutOccurred;
	volatile int value = 7;
	private volatile long startTime;
	private volatile long endTime;
	private volatile int sIntValue;


	public SynchronizedIntTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.si = new SynchronizedInt();
		this.exCaught = false;
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.sIntValue = 0;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAccessors() throws Exception {
		this.si.setValue(0);
		assertEquals(0, this.si.getValue());
		assertFalse(this.si.isNotZero());
		assertTrue(this.si.isZero());

		this.si.setValue(this.value);
		assertEquals(this.value, this.si.getValue());
		assertTrue(this.si.isNotZero());
		assertFalse(this.si.isZero());

		this.si.setZero();
		assertEquals(0, this.si.getValue());
		assertFalse(this.si.isNotZero());
		assertTrue(this.si.isZero());

		assertSame(this.si, this.si.getMutex());
	}

	public void testEquals() throws Exception {
		this.si.setValue(0);
		SynchronizedInt so2 = new SynchronizedInt(0);
		assertEquals(this.si, so2);

		this.si.setValue(this.value);
		assertFalse(this.si.equals(so2));

		so2.setValue(this.value);
		assertEquals(this.si, so2);
	}

	public void testHashCode() {
		this.si.setValue(this.value);
		assertEquals(this.value, this.si.hashCode());

		this.si.setValue(0);
		assertEquals(0, this.si.hashCode());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0
	 */
	public void testWaitUntilZero() throws Exception {
		this.verifyWaitUntilZero(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to 0 by t2
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.elapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > 150): " + time, time > 150);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to 0
	 */
	public void testWaitUntilZeroTimeout() throws Exception {
		this.verifyWaitUntilZero(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to 0 by t1
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.elapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < 150): " + time, time < 150);
	}

	private void verifyWaitUntilZero(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetZeroCommand(), this.buildWaitUntilZeroCommand(t2Timeout));
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0;
	 * then t2 will set the value to 7
	 */
	public void testWaitToSetValue() throws Exception {
		this.verifyWaitToSetValue(0);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to an object by t2
		assertTrue(this.si.isNotZero());
		// make a reasonable guess about how long t2 took
		long time = this.elapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > 150): " + time, time > 150);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to 0
	 */
	public void testWaitToSetValueTimeout() throws Exception {
		this.verifyWaitToSetValue(20);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to null by t1
		assertTrue(this.si.isZero());
		// make a reasonable guess about how long t2 took
		long time = this.elapsedTime();
		assertTrue("t2 finished a bit late (expected value should be < 150): " + time, time < 150);
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
		// give t1 a head start of 100 ms
		Runnable r2 = this.buildRunnable(this.buildGetValueCommand(), this.si, 100);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
		assertEquals(42, this.si.getValue());
		assertEquals(42, this.sIntValue);
		// make a reasonable guess about how long t2 took
		long time = this.elapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > 100): " + time, time > 300);
	}

	private void executeThreads(Command t1Command, Command t2Command) throws Exception {
		this.si.setValue(this.value);
		Runnable r1 = this.buildRunnable(t1Command, this.si, 200);
		Runnable r2 = this.buildRunnable(t2Command, this.si, 0);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		while (t1.isAlive() || t2.isAlive()) {
			Thread.sleep(50);
		}
		assertFalse(this.exCaught);
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
			public void execute(SynchronizedInt sInt) throws Exception {
				SynchronizedIntTests.this.setStartTime(System.currentTimeMillis());
				SynchronizedIntTests.this.setTimeoutOccurred( ! sInt.waitUntilZero(timeout));
				SynchronizedIntTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Command buildWaitToSetValueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws Exception {
				SynchronizedIntTests.this.setStartTime(System.currentTimeMillis());
				SynchronizedIntTests.this.setTimeoutOccurred( ! sInt.waitToSetValue(SynchronizedIntTests.this.value, timeout));
				SynchronizedIntTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Command buildInitializeValueCommand() {
		return new Command() {
			public void execute(final SynchronizedInt sInt) throws Exception {
				sInt.execute(
					new org.eclipse.jpt.utility.Command() {
						public void execute() {
							// pretend to perform some long initialization process
							try {
								Thread.sleep(500);
							} catch (Exception ex) {
								SynchronizedIntTests.this.setExCaught(true);
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
			public void execute(SynchronizedInt sInt) throws Exception {
				SynchronizedIntTests.this.setStartTime(System.currentTimeMillis());
				SynchronizedIntTests.this.setSOValue(sInt.getValue());
				SynchronizedIntTests.this.setEndTime(System.currentTimeMillis());
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedInt sInt, final long sleep) {
		return new Runnable() {
			public void run() {
				try {
					if (sleep != 0) {
						Thread.sleep(sleep);
					}
					command.execute(sInt);
				} catch (Exception ex) {
					SynchronizedIntTests.this.setExCaught(true);
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

	private long elapsedTime() {
		return this.endTime - this.startTime;
	}

	void setSOValue(int sIntValue) {
		this.sIntValue = sIntValue;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedInt sInt) throws Exception;
	}

}
