/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import org.eclipse.jpt.common.utility.internal.SynchronizedBoolean;

public class SynchronizedBooleanTests
	extends MultiThreadedTestCase
{
	private volatile SynchronizedBoolean sb;
	volatile boolean timeoutOccurred;
	volatile long startTime;
	volatile long endTime;


	public SynchronizedBooleanTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.sb = new SynchronizedBoolean();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
	}

	public void testGetValue() throws Exception {
		assertFalse(this.sb.getValue());
	}

	public void testIs() throws Exception {
		assertTrue(this.sb.is(false));
	}

	public void testIsNot() throws Exception {
		assertTrue(this.sb.isNot(true));
	}

	public void testIsTrue() throws Exception {
		assertFalse(this.sb.isTrue());
	}

	public void testIsFalse() throws Exception {
		assertTrue(this.sb.isFalse());
	}

	public void testSetValueFalse() throws Exception {
		this.sb.setValue(false);
		assertFalse(this.sb.getValue());
		assertFalse(this.sb.isTrue());
		assertTrue(this.sb.isFalse());
	}

	public void testSetValueTrue() throws Exception {
		this.sb.setValue(true);
		assertTrue(this.sb.getValue());
		assertTrue(this.sb.isTrue());
		assertFalse(this.sb.isFalse());
	}

	public void testFlip() throws Exception {
		assertTrue(this.sb.flip());
		assertFalse(this.sb.flip());
	}

	public void testFalseAndTrue() throws Exception {
		assertFalse(this.sb.and(true));
		assertFalse(this.sb.getValue());
	}

	public void testTrueAndTrue() throws Exception {
		this.sb.setValue(true);
		assertTrue(this.sb.and(true));
		assertTrue(this.sb.getValue());
	}

	public void testFalseAndFalse() throws Exception {
		assertFalse(this.sb.and(false));
		assertFalse(this.sb.getValue());
	}

	public void testTrueAndFalse() throws Exception {
		this.sb.setValue(true);
		assertFalse(this.sb.and(false));
		assertFalse(this.sb.getValue());
	}

	public void testFalseOrTrue() throws Exception {
		assertTrue(this.sb.or(true));
		assertTrue(this.sb.getValue());
	}

	public void testTrueOrTrue() throws Exception {
		this.sb.setValue(true);
		assertTrue(this.sb.or(true));
		assertTrue(this.sb.getValue());
	}

	public void testFalseOrFalse() throws Exception {
		assertFalse(this.sb.or(false));
		assertFalse(this.sb.getValue());
	}

	public void testTrueOrFalse() throws Exception {
		this.sb.setValue(true);
		assertTrue(this.sb.or(false));
		assertTrue(this.sb.getValue());
	}

	public void testFalseXorTrue() throws Exception {
		assertTrue(this.sb.xor(true));
		assertTrue(this.sb.getValue());
	}

	public void testTrueXorTrue() throws Exception {
		this.sb.setValue(true);
		assertFalse(this.sb.xor(true));
		assertFalse(this.sb.getValue());
	}

	public void testFalseXorFalse() throws Exception {
		assertFalse(this.sb.xor(false));
		assertFalse(this.sb.getValue());
	}

	public void testTrueXorFalse() throws Exception {
		this.sb.setValue(true);
		assertTrue(this.sb.xor(false));
		assertTrue(this.sb.getValue());
	}

	public void testSetNotTrue() throws Exception {
		this.sb.setNot(true);
		assertFalse(this.sb.getValue());
		assertFalse(this.sb.isTrue());
		assertTrue(this.sb.isFalse());
	}

	public void testSetNotFalse() throws Exception {
		this.sb.setNot(false);
		assertTrue(this.sb.getValue());
		assertTrue(this.sb.isTrue());
		assertFalse(this.sb.isFalse());
	}

	public void testSetFalse() throws Exception {
		this.sb.setFalse();
		assertFalse(this.sb.getValue());
		assertFalse(this.sb.isTrue());
		assertTrue(this.sb.isFalse());
	}

	public void testSetTrue() throws Exception {
		this.sb.setTrue();
		assertTrue(this.sb.getValue());
		assertTrue(this.sb.isTrue());
		assertFalse(this.sb.isFalse());
	}

	public void testCommitFalseSuccess() throws Exception {
		assertTrue(this.sb.commit(false, false));
		assertFalse(this.sb.getValue());
	}

	public void testCommitTrueSuccess() throws Exception {
		assertTrue(this.sb.commit(false, true));
		assertTrue(this.sb.getValue());
	}

	public void testCommitFalseFailure() throws Exception {
		assertFalse(this.sb.commit(true, false));
		assertFalse(this.sb.getValue());
	}

	public void testCommitTrueFailure() throws Exception {
		assertFalse(this.sb.commit(true, true));
		assertFalse(this.sb.getValue());
	}

	public void testSwapSame() throws Exception {
		assertFalse(this.sb.swap(this.sb));
		assertFalse(this.sb.getValue());
	}

	public void testSwapSameValue() throws Exception {
		SynchronizedBoolean sb2 = new SynchronizedBoolean();
		assertFalse(this.sb.swap(sb2));
		assertFalse(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testSwapDifferentValue() throws Exception {
		SynchronizedBoolean sb2 = new SynchronizedBoolean(true);
		assertTrue(this.sb.swap(sb2));
		assertTrue(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testGetMutexThis() throws Exception {
		assertSame(this.sb, this.sb.getMutex());
	}

	public void testGetMutexObject() throws Exception {
		Object mutex = new Object();
		SynchronizedBoolean syncBool = new SynchronizedBoolean(mutex);
		assertSame(mutex, syncBool.getMutex());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to true
	 */
	public void testWaitUntilTrue() throws Exception {
		this.verifyWaitUntilTrue(0);  // 0 = indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to true by t2
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to true
	 */
	public void testWaitUntilTrueTimeout() throws Exception {
		this.verifyWaitUntilTrue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to true by t1
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitUntilTrue(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetTrueCommand(), this.buildWaitUntilTrueCommand(t2Timeout));
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to false
	 */
	public void testWaitToSetFalse() throws Exception {
		this.verifyWaitToSetFalse(0);  // 0 = indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to false by t2
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	/**
	 * t2 will time out waiting for t1 to set the value to false
	 */
	public void testWaitToSetFalseTimeout() throws Exception {
		this.verifyWaitToSetFalse(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to true by t1
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	private void verifyWaitToSetFalse(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetTrueCommand(), this.buildWaitToSetFalseCommand(t2Timeout));
	}

	private void executeThreads(Command t1Command, Command t2Command) throws Exception {
		this.sb.setFalse();
		Runnable r1 = this.buildRunnable(t1Command, this.sb, TWO_TICKS);
		Runnable r2 = this.buildRunnable(t2Command, this.sb, 0);
		Thread t1 = this.buildThread(r1);
		Thread t2 = this.buildThread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	private Command buildSetTrueCommand() {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) {
				syncBool.setTrue();
			}
		};
	}

	private Command buildWaitUntilTrueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred = ! syncBool.waitUntilTrue(timeout);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Command buildWaitToSetFalseCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred =  ! syncBool.waitToSetFalse(timeout);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
		};
	}

	private Runnable buildRunnable(final Command command, final SynchronizedBoolean syncBool, final long delay) {
		return new TestRunnable() {
			@Override
			public void run_() throws InterruptedException {
				if (delay != 0) {
					Thread.sleep(delay);
				}
				command.execute(syncBool);
			}
		};
	}

	long calculateElapsedTime() {
		return this.endTime - this.startTime;
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedBoolean syncBool) throws InterruptedException;
	}

}
