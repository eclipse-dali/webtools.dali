/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleObjectReference;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedObject;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

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
		this.so = new SynchronizedObject<>();
		this.timeoutOccurred = false;
		this.startTime = 0;
		this.endTime = 0;
		this.soValue = null;
	}

	public void testCtorObjectObject() throws Exception {
		String v = "foo";
		String mutex = "lock";
		this.so = new SynchronizedObject<>(v, mutex);
		assertEquals(v, this.so.getValue());
		assertEquals(mutex, this.so.getMutex());
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

	public void testValueEqualsObject() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertTrue(this.so.valueEquals(null));
		assertFalse(this.so.valueEquals(v));

		this.so.setValue(v);
		assertTrue(this.so.valueEquals(v));
		assertFalse(this.so.valueEquals(null));
	}

	public void testValueNotEqualObject() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertFalse(this.so.valueNotEqual(null));
		assertTrue(this.so.valueNotEqual(v));

		this.so.setValue(v);
		assertFalse(this.so.valueNotEqual(v));
		assertTrue(this.so.valueNotEqual(null));
	}

	public void testIsObject() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertTrue(this.so.is(null));
		assertFalse(this.so.is(v));

		this.so.setValue(v);
		assertTrue(this.so.is(v));
		assertFalse(this.so.is(null));
		assertFalse(this.so.is(new String(v)));
	}

	public void testIsNotObject() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertFalse(this.so.isNot(null));
		assertTrue(this.so.isNot(v));

		this.so.setValue(v);
		assertFalse(this.so.isNot(v));
		assertTrue(this.so.isNot(null));
		assertTrue(this.so.isNot(new String(v)));
	}

	public void testIsMemberOfPredicate() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertTrue(this.so.isMemberOf(PredicateTools.isNull()));
		assertFalse(this.so.isMemberOf(PredicateTools.isNotNull()));

		this.so.setValue(v);
		assertFalse(this.so.isMemberOf(PredicateTools.isNull()));
		assertTrue(this.so.isMemberOf(PredicateTools.isNotNull()));
	}

	public void testIsNotMemberOfPredicate() throws Exception {
		String v = "foo";
		this.so.setValue(null);
		assertFalse(this.so.isNotMemberOf(PredicateTools.isNull()));
		assertTrue(this.so.isNotMemberOf(PredicateTools.isNotNull()));

		this.so.setValue(v);
		assertTrue(this.so.isNotMemberOf(PredicateTools.isNull()));
		assertFalse(this.so.isNotMemberOf(PredicateTools.isNotNull()));
	}

	public void testCommit() throws Exception {
		String v1 = "foo";
		this.so.setValue(null);
		assertTrue(this.so.commit(v1, null));
		assertEquals(v1, this.so.getValue());
		assertFalse(this.so.commit(v1, null));
		assertEquals(v1, this.so.getValue());

		String v2 = "bar";
		assertTrue(this.so.commit(v2, v1));
		assertEquals(v2, this.so.getValue());
		assertFalse(this.so.commit(v2, v1));
		assertEquals(v2, this.so.getValue());
	}

	public void testSwapRef() throws Exception {
		this.so.setValue(null);
		ModifiableObjectReference<Object> temp = this.so;
		assertEquals(null, this.so.swap(temp));

		ModifiableObjectReference<Object> ref = new SimpleObjectReference<>("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals(null, ref.getValue());

		this.so.setValue("foo");
		ref.setValue("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals("foo", ref.getValue());
	}

	public void testSwapSyncObject() throws Exception {
		this.so.setValue(null);
		assertEquals(null, this.so.swap(this.so));

		ModifiableObjectReference<Object> ref = new SynchronizedObject<>("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals(null, ref.getValue());

		this.so.setValue("foo");
		ref.setValue("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals("foo", ref.getValue());

		ref.setValue("bar");
		assertEquals("foo", ref.swap(this.so));
		assertEquals("bar", this.so.getValue());
		assertEquals("foo", ref.getValue());
	}

	public void testSwapSyncInt2() throws Exception {
		this.so.setValue(null);
		assertEquals(null, this.so.swap(this.so));

		SynchronizedObject<Object> ref = new SynchronizedObject<>("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals(null, ref.getValue());

		this.so.setValue("foo");
		ref.setValue("foo");
		assertEquals("foo", this.so.swap(ref));
		assertEquals("foo", this.so.getValue());
		assertEquals("foo", ref.getValue());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null
	 */
	public void testWaitUntilNull() throws Exception {
		this.verifyWaitUntilNull(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to null by t2
		assertNull(this.so.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null
	 */
	public void testWaitUntilNul2l() throws Exception {
		this.verifyWaitUntilNull(0);  // 0 = indefinite wait
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to null
	 */
	public void testWaitUntilNullTimeout2() throws Exception {
		this.verifyWaitUntilNull(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to null by t2
		assertNull(this.so.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be >= " + TWO_TICKS + "): " + time, time >= TWO_TICKS);
	}

	private void verifyWaitUntilNull(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetNullCommand(), this.buildWaitUntilNullCommand(t2Timeout));
	}

	private Command buildWaitUntilNullCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) throws InterruptedException {
				SynchronizedObjectTests.this.startTime = System.currentTimeMillis();
				SynchronizedObjectTests.this.timeoutOccurred = this.timeoutOccurred(sObject);
				SynchronizedObjectTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedObject<Object> sObject) throws InterruptedException {
				if (timeout < 0) {
					sObject.waitUntilNull();
					return false;
				}
				return ! sObject.waitUntilNull(timeout);
			}
		};
	}

	public void testWaitUntilNotPredicate() throws Exception {
		this.so.setValue(this.value);
		this.so.waitUntilNot(PredicateTools.isNull());
		assertEquals(this.value, this.so.getValue());
	}

	public void testWaitUntilValueEqualsObject() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		String v2 = new String(v1);
		this.so.waitUntilValueEquals(v2);
		assertEquals(v2, this.so.getValue());
	}

	public void testWaitUntilValueNotEqualObject() throws Exception {
		String v = "foo";
		this.so.setValue(v);
		this.so.waitUntilValueNotEqual(null);
		assertEquals(v, this.so.getValue());
	}

	public void testWaitUntilValueIsObject() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		this.so.waitUntilValueIs(v1);
		assertEquals(v1, this.so.getValue());
	}

	public void testWaitUntilValueIsNotObject() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		String v2 = new String(v1);
		this.so.waitUntilValueIsNot(v2);
		assertSame(v1, this.so.getValue());
		assertNotSame(v2, this.so.getValue());
	}

	public void testWaitUntilNotNull() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		this.so.waitUntilNotNull();
		assertEquals(v1, this.so.getValue());
	}

	public void testWaitToSetValueObject() throws Exception {
		this.so.setValue(null);
		assertNull(this.so.waitToSetValue(this.value));
		assertEquals(this.value, this.so.getValue());
	}

	public void testWaitToSetNull() throws Exception {
		this.so.setValue(this.value);
		assertEquals(this.value, this.so.waitToSetNull());
		assertNull(this.so.getValue());
	}

	public void testWaitToCommit() throws Exception {
		this.so.setValue(this.value);
		assertEquals(this.value, this.so.waitToCommit("foo", this.value));
		assertEquals("foo", this.so.getValue());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null;
	 * then t2 will set the value to an object
	 */
	public void testWaitToSetValue() throws Exception {
		this.verifyWaitToSetValue(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to an object by t2
		assertTrue(this.so.isNotNull());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to null;
	 * then t2 will set the value to an object
	 */
	public void testWaitToSetValue2() throws Exception {
		this.verifyWaitToSetValue(0);  // 0 = indefinite wait
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to null
	 */
	public void testWaitToSetValueTimeout2() throws Exception {
		this.verifyWaitToSetValue(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to a value by t2
		assertFalse(this.so.isNull());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be >= " + TWO_TICKS + "): " + time, time >= TWO_TICKS);
	}

	private void verifyWaitToSetValue(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetNullCommand(), this.buildWaitToSetValueCommand(t2Timeout));
	}

	private Command buildWaitToSetValueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedObject<Object> sObject) throws InterruptedException {
				SynchronizedObjectTests.this.startTime = System.currentTimeMillis();
				SynchronizedObjectTests.this.timeoutOccurred = this.timeoutOccurred(sObject);
				SynchronizedObjectTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedObject<Object> sObject) throws InterruptedException {
				if (timeout < 0) {
					sObject.waitToSetValue(SynchronizedObjectTests.this.value);
					return false;
				}
				return ! sObject.waitToSetValue(SynchronizedObjectTests.this.value, timeout);
			}
		};
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

	private Command buildInitializeValueCommand() {
		return new Command() {
			public void execute(final SynchronizedObject<Object> sObject) throws InterruptedException {
				sObject.execute(
					new org.eclipse.jpt.common.utility.command.Command() {
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

	public void testWaitUntilNotPredicateTimeout() throws Exception {
		this.so.setValue(this.value);
		this.so.waitUntilNot(PredicateTools.isNull(), TWO_TICKS);
		assertEquals(this.value, this.so.getValue());
	}

	public void testWaitUntilValueEqualsObjectTimeout() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		String v2 = new String(v1);
		this.so.waitUntilValueEquals(v2, TWO_TICKS);
		assertEquals(v2, this.so.getValue());
	}

	public void testWaitUntilValueNotEqualObjectTimeout() throws Exception {
		String v = "foo";
		this.so.setValue(v);
		this.so.waitUntilValueNotEqual(null, TWO_TICKS);
		assertEquals(v, this.so.getValue());
	}

	public void testWaitUntilValueIsObjectTimeout() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		this.so.waitUntilValueIs(v1, TWO_TICKS);
		assertEquals(v1, this.so.getValue());
	}

	public void testWaitUntilValueIsNotObjectTimeout() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		String v2 = new String(v1);
		this.so.waitUntilValueIsNot(v2, TWO_TICKS);
		assertSame(v1, this.so.getValue());
		assertNotSame(v2, this.so.getValue());
	}

	public void testWaitUntilNotNullTimeout() throws Exception {
		String v1 = "foo";
		this.so.setValue(v1);
		this.so.waitUntilNotNull(TWO_TICKS);
		assertEquals(v1, this.so.getValue());
	}

	public void testWaitToSetValueObjectTimeout() throws Exception {
		this.so.setValue(null);
		assertTrue(this.so.waitToSetValue(this.value, TWO_TICKS));
		assertEquals(this.value, this.so.getValue());
	}

	public void testWaitToSetNullTimeout() throws Exception {
		this.so.setValue(this.value);
		assertTrue(this.so.waitToSetNull(TWO_TICKS));
		assertNull(this.so.getValue());
	}

	public void testWaitToCommitTimeout() throws Exception {
		this.so.setValue(this.value);
		assertTrue(this.so.waitToCommit("foo", this.value, TWO_TICKS));
		assertEquals("foo", this.so.getValue());
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedObject<Object> so) throws InterruptedException;
	}


	// ********** standard methods **********

	public void testEquals() {
		SynchronizedObject<Object> so2 = new SynchronizedObject<>();
		assertTrue(this.so.equals(this.so));
		assertFalse(this.so.equals(so2));
	}

	public void testHashCode() {
		assertEquals(this.so.hashCode(), this.so.hashCode());
	}

	public void testClone() {
		this.so.setValue(null);
		SynchronizedObject<Object> clone = this.so.clone();
		assertNull(clone.getValue());
		assertNotSame(clone, this.so);

		this.so.setValue("foo");
		clone = this.so.clone();
		assertEquals("foo", clone.getValue());
		assertNotSame(clone, this.so);
	}

	public void testSerialization() throws Exception {
		this.so.setValue("foo");
		SynchronizedObject<Object> clone = TestTools.serialize(this.so);
		assertNotSame(this.so, clone);
		assertEquals("foo", clone.getValue());
	}

	public void testToString() {
		assertEquals("[null]", this.so.toString());
		this.so.setValue("foo");
		assertEquals("[foo]", this.so.toString());
	}
}
