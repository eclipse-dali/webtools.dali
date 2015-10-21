/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.command.InterruptibleCommandAdapter;
import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
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
		assertFalse(this.sb.setValue(true));
		assertTrue(this.sb.getValue());
	}

	public void testIs() throws Exception {
		assertTrue(this.sb.is(false));
		assertFalse(this.sb.is(true));
		assertFalse(this.sb.setValue(true));
		assertTrue(this.sb.is(true));
		assertFalse(this.sb.is(false));
	}

	public void testIsNot() throws Exception {
		assertTrue(this.sb.isNot(true));
		assertFalse(this.sb.setValue(true));
		assertTrue(this.sb.isNot(false));
	}

	public void testIsTrue() throws Exception {
		assertFalse(this.sb.isTrue());
		assertFalse(this.sb.setValue(true));
		assertTrue(this.sb.isTrue());
	}

	public void testIsFalse() throws Exception {
		assertTrue(this.sb.isFalse());
		assertFalse(this.sb.setValue(true));
		assertFalse(this.sb.isFalse());
	}

	public void testSetValueFalse() throws Exception {
		assertFalse(this.sb.setValue(false));
		assertFalse(this.sb.getValue());
		assertFalse(this.sb.isTrue());
		assertTrue(this.sb.isFalse());
		assertFalse(this.sb.setValue(false));
	}

	public void testSetValueTrue() throws Exception {
		assertFalse(this.sb.setValue(true));
		assertTrue(this.sb.getValue());
		assertTrue(this.sb.isTrue());
		assertFalse(this.sb.isFalse());
		assertTrue(this.sb.setValue(true));
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
		assertTrue(this.sb.commit(true, false));
		assertTrue(this.sb.getValue());
	}

	public void testCommitFalseFailure() throws Exception {
		assertFalse(this.sb.commit(false, true));
		assertFalse(this.sb.getValue());
	}

	public void testCommitTrueFailure() throws Exception {
		assertFalse(this.sb.commit(true, true));
		assertFalse(this.sb.getValue());
	}

	public void testSwapSame1() throws Exception {
		assertFalse(this.sb.swap((ModifiableBooleanReference) this.sb));
		assertFalse(this.sb.getValue());
	}

	public void testSwapSameValues1() throws Exception {
		ModifiableBooleanReference sb2 = new SimpleBooleanReference(false);
		assertFalse(this.sb.swap(sb2));
		assertFalse(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testSwapDifferentValues1() throws Exception {
		ModifiableBooleanReference sb2 = new SimpleBooleanReference(true);
		assertTrue(this.sb.swap(sb2));
		assertTrue(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testSwapSame2() throws Exception {
		assertFalse(this.sb.swap(this.sb));
		assertFalse(this.sb.getValue());
	}

	public void testSwapSameValues2() throws Exception {
		SynchronizedBoolean sb2 = new SynchronizedBoolean();
		assertFalse(this.sb.swap(sb2));
		assertFalse(this.sb.getValue());
		assertFalse(sb2.getValue());

		assertFalse(sb2.swap(this.sb));
		assertFalse(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testSwapDifferentValues2() throws Exception {
		SynchronizedBoolean sb2 = new SynchronizedBoolean(true);
		assertTrue(this.sb.swap(sb2));
		assertTrue(this.sb.getValue());
		assertFalse(sb2.getValue());

		assertTrue(sb2.swap(this.sb));
		assertFalse(this.sb.getValue());
		assertTrue(sb2.getValue());
	}

	public void testSwpSameValues3() throws Exception {
		ModifiableBooleanReference sb2 = new SynchronizedBoolean();
		assertFalse(this.sb.swap(sb2));
		assertFalse(this.sb.getValue());
		assertFalse(sb2.getValue());
	}

	public void testSwapDifferentValues3() throws Exception {
		ModifiableBooleanReference sb2 = new SynchronizedBoolean(true);
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
		this.verifyWaitUntilTrue(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to true by t2
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilTrue2() throws Exception {
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to true
	 */
	public void testWaitUntilTrueTimeout2() throws Exception {
		this.verifyWaitUntilTrue(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to true by t2
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() >= TWO_TICKS);
	}

	private void verifyWaitUntilTrue(long t2Timeout) throws Exception {
		this.sb.setFalse();
		this.executeThreads(this.buildSetTrueCommand(), this.buildWaitUntilTrueCommand(t2Timeout));
	}

	private Command buildWaitUntilTrueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred = this.timeoutOccurred(syncBool);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedBoolean syncBool) throws InterruptedException {
				if (timeout < 0) {
					syncBool.waitUntilTrue();
					return false;
				}
				return ! syncBool.waitUntilTrue(timeout);
			}
		};
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to false
	 */
	public void testWaitUntilFalse() throws Exception {
		this.verifyWaitUntilFalse(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to false by t2
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	public void testWaitUntilFalse2() throws Exception {
		this.verifyWaitUntilFalse(0);  // 0 = indefinite wait
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
	public void testWaitUntilFalseTimeout() throws Exception {
		this.verifyWaitUntilFalse(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to false by t1
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	/**
	 * t2 will NOT time out waiting for t1 to set the value to false
	 */
	public void testWaitUntilFalseTimeout2() throws Exception {
		this.verifyWaitUntilFalse(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to false by t2
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() >= TWO_TICKS);
	}

	private void verifyWaitUntilFalse(long t2Timeout) throws Exception {
		this.sb.setTrue();
		this.executeThreads(this.buildSetFalseCommand(), this.buildWaitUntilFalseCommand(t2Timeout));
	}

	private Command buildWaitUntilFalseCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred = this.timeoutOccurred(syncBool);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedBoolean syncBool) throws InterruptedException {
				if (timeout < 0) {
					syncBool.waitUntilFalse();
					return false;
				}
				return ! syncBool.waitUntilFalse(timeout);
			}
		};
	}

	public void testWaitUntilValueIsNotTrue() throws Exception {
		this.sb.waitUntilValueIsNot(true);
		assertFalse(this.sb.getValue());
	}

	public void testWaitUntilValueIsNotFalse() throws Exception {
		this.sb.setTrue();
		this.sb.waitUntilValueIsNot(false);
		assertTrue(this.sb.getValue());
	}

	public void testWaitUntilValueIsNotTrueTimeout() throws Exception {
		this.sb.waitUntilValueIsNot(true, 500);
		assertFalse(this.sb.getValue());
	}

	public void testWaitUntilValueIsNotFalseTimeout() throws Exception {
		this.sb.setTrue();
		this.sb.waitUntilValueIsNot(false, 500);
		assertTrue(this.sb.getValue());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to true
	 */
	public void testWaitToSetTrue() throws Exception {
		this.verifyWaitToSetTrue(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to true by t2
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to true
	 */
	public void testWaitToSetTrue2() throws Exception {
		this.verifyWaitToSetTrue(0);  // 0 = indefinite wait
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
	public void testWaitToSetTrueTimeout() throws Exception {
		this.verifyWaitToSetTrue(TICK);
		// timeout occurs...
		assertTrue(this.timeoutOccurred);
		// ...and the value will eventually be set to false by t1
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() < THREE_TICKS);
	}

	/**
	 * t2 will NOT time out waiting for t1 to set the value to true
	 */
	public void testWaitToSetTrueTimeout2() throws Exception {
		this.verifyWaitUntilTrue(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to true by t2
		assertTrue(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() >= TWO_TICKS);
	}

	private void verifyWaitToSetTrue(long t2Timeout) throws Exception {
		this.sb.setTrue();
		this.executeThreads(this.buildSetFalseCommand(), this.buildWaitToSetTrueCommand(t2Timeout));
	}

	private Command buildWaitToSetTrueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred = this.timeoutOccurred( syncBool);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedBoolean syncBool) throws InterruptedException {
				if (timeout < 0) {
					syncBool.waitToSetTrue();
					return false;
				}
				return ! syncBool.waitToSetTrue(timeout);
			}
		};
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to false
	 */
	public void testWaitToSetFalse() throws Exception {
		this.verifyWaitToSetFalse(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to false by t2
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() > TICK);
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to false
	 */
	public void testWaitToSetFalse2() throws Exception {
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to false
	 */
	public void testWaitToSetFalseTimeout2() throws Exception {
		this.verifyWaitUntilFalse(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to false by t2
		assertFalse(this.sb.getValue());
		// make a reasonable guess about how long t2 took
		assertTrue(this.calculateElapsedTime() >= TWO_TICKS);
	}

	private void verifyWaitToSetFalse(long t2Timeout) throws Exception {
		this.sb.setFalse();
		this.executeThreads(this.buildSetTrueCommand(), this.buildWaitToSetFalseCommand(t2Timeout));
	}

	private Command buildWaitToSetFalseCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) throws InterruptedException {
				SynchronizedBooleanTests.this.startTime = System.currentTimeMillis();
				SynchronizedBooleanTests.this.timeoutOccurred = this.timeoutOccurred( syncBool);
				SynchronizedBooleanTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedBoolean syncBool) throws InterruptedException {
				if (timeout < 0) {
					syncBool.waitToSetFalse();
					return false;
				}
				return ! syncBool.waitToSetFalse(timeout);
			}
		};
	}

	public void testWaitToSetValueNotTrue() throws Exception {
		this.sb.waitToSetValueNot(false);
		assertTrue(this.sb.getValue());
	}

	public void testWaitToSetValueNotFalse() throws Exception {
		this.sb.setTrue();
		this.sb.waitToSetValueNot(true);
		assertFalse(this.sb.getValue());
	}

	public void testWaitToSetValueNotTrueTimeout() throws Exception {
		this.sb.waitToSetValueNot(false, 500);
		assertTrue(this.sb.getValue());
	}

	public void testWaitToSetValueNotFalseTimeout() throws Exception {
		this.sb.setTrue();
		this.sb.waitToSetValueNot(true, 500);
		assertFalse(this.sb.getValue());
	}

	public void testExecute() throws Exception {
		final ModifiableBooleanReference done = new SimpleBooleanReference(false);
		this.sb.execute(new InterruptibleCommandAdapter() {
			@Override
			public void execute() throws InterruptedException {
				done.setTrue();
			}
		});
		assertTrue(done.getValue());
	}

	public void testSerialization() throws Exception {
		this.sb.setTrue();
		SynchronizedBoolean clone = TestTools.serialize(this.sb);
		assertNotSame(this.sb, clone);
		assertTrue(clone.getValue());
	}

	private void executeThreads(Command t1Command, Command t2Command) throws Exception {
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

	private Command buildSetFalseCommand() {
		return new Command() {
			public void execute(SynchronizedBoolean syncBool) {
				syncBool.setFalse();
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


	// ********** standard methods **********

	public void testEquals() {
		SynchronizedBoolean sb2 = new SynchronizedBoolean(true);
		assertTrue(this.sb.equals(this.sb));
		assertFalse(this.sb.equals(sb2));
	}

	public void testHashCode() {
		assertEquals(this.sb.hashCode(), this.sb.hashCode());
	}

	public void testClone() {
		SynchronizedBoolean clone = this.sb.clone();
		assertFalse(clone.getValue());
		assertNotSame(clone, this.sb);
	}

	public void testToString() {
		assertEquals("[false]", this.sb.toString());
		assertFalse(this.sb.setTrue());
		assertEquals("[true]", this.sb.toString());
	}
}
