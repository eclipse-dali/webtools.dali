/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.predicate.int_.IntPredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedInt;
import org.eclipse.jpt.common.utility.reference.IntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

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

	public void testCtorIntObject() throws Exception {
		String mutex = "mutex";
		SynchronizedInt syncInt = new SynchronizedInt(22, mutex);
		assertEquals(22, syncInt.getValue());
		assertEquals(mutex, syncInt.getMutex());
	}

	public void testCtorInt() throws Exception {
		SynchronizedInt syncInt = new SynchronizedInt(22);
		assertEquals(22, syncInt.getValue());
		assertEquals(syncInt, syncInt.getMutex());
	}

	public void testCtorObject() throws Exception {
		String mutex = "mutex";
		SynchronizedInt syncInt = new SynchronizedInt(mutex);
		assertEquals(0, syncInt.getValue());
		assertEquals(mutex, syncInt.getMutex());
	}

	public void testGetValue() throws Exception {
		assertEquals(0, this.si.getValue());
	}

	public void testEqualsInt() throws Exception {
		assertTrue(this.si.equals(0));
		assertFalse(this.si.equals(7));

		this.si.setValue(this.value);
		assertFalse(this.si.equals(0));
		assertTrue(this.si.equals(7));
	}

	public void testNotEqualInt() throws Exception {
		assertTrue(this.si.notEqual(7));
		assertFalse(this.si.notEqual(0));

		this.si.setValue(this.value);
		assertTrue(this.si.notEqual(0));
		assertFalse(this.si.notEqual(7));
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

	public void testIsMemberOfIntPredicate() throws Exception {
		assertTrue(this.si.isMemberOf(IntPredicateTools.isEven()));
		assertFalse(this.si.isMemberOf(IntPredicateTools.isGreaterThan(2)));
		this.si.setValue(this.value);
		assertFalse(this.si.isMemberOf(IntPredicateTools.isEven()));
		assertTrue(this.si.isMemberOf(IntPredicateTools.isGreaterThan(2)));
	}

	public void testIsNotMemberOfIntPredicate() throws Exception {
		assertFalse(this.si.isNotMemberOf(IntPredicateTools.isEven()));
		assertTrue(this.si.isNotMemberOf(IntPredicateTools.isGreaterThan(2)));
		this.si.setValue(this.value);
		assertTrue(this.si.isNotMemberOf(IntPredicateTools.isEven()));
		assertFalse(this.si.isNotMemberOf(IntPredicateTools.isGreaterThan(2)));
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

	public void testSetZero() throws Exception {
		this.si.setZero();
		assertEquals(0, this.si.getValue());
		assertFalse(this.si.isNotZero());
		assertTrue(this.si.isZero());
	}

	public void testIncrement() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(1, this.si.increment());
		assertEquals(1, this.si.getValue());
	}

	public void testIncrementExact() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(1, this.si.incrementExact());
		assertEquals(1, this.si.getValue());
	}

	public void testDecrement() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(-1, this.si.decrement());
		assertEquals(-1, this.si.getValue());
	}

	public void testDecrementExact() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(-1, this.si.decrementExact());
		assertEquals(-1, this.si.getValue());
	}

	public void testHalve() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.halve());
		this.si.setValue(22);
		assertEquals(11, this.si.halve());
		assertEquals(11, this.si.getValue());
		assertEquals(5, this.si.halve());
		assertEquals(5, this.si.getValue());
	}

	public void testTwice() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.twice());
		this.si.setValue(11);
		assertEquals(22, this.si.twice());
		assertEquals(22, this.si.getValue());
		assertEquals(44, this.si.twice());
		assertEquals(44, this.si.getValue());
	}

	public void testTwiceExact() throws Exception {
		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.twiceExact());
		this.si.setValue(11);
		assertEquals(22, this.si.twiceExact());
		assertEquals(22, this.si.getValue());
		assertEquals(44, this.si.twiceExact());
		assertEquals(44, this.si.getValue());
	}

	public void testAbs() throws Exception {
		assertEquals(0, this.si.abs());
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(this.value, this.si.abs());
		assertEquals(this.value, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(this.value, this.si.abs());
		assertEquals(this.value, this.si.getValue());
	}

	public void testNegate() throws Exception {
		assertEquals(0, this.si.negate());
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(-this.value, this.si.negate());
		assertEquals(-this.value, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(this.value, this.si.negate());
		assertEquals(this.value, this.si.getValue());
	}

	public void testNegateExact() throws Exception {
		assertEquals(0, this.si.negateExact());
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(-this.value, this.si.negateExact());
		assertEquals(-this.value, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(this.value, this.si.negateExact());
		assertEquals(this.value, this.si.getValue());
	}

	public void testAddInt() throws Exception {
		assertEquals(0, this.si.add(0));
		assertEquals(0, this.si.getValue());
		assertEquals(3, this.si.add(3));
		assertEquals(3, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(10, this.si.add(3));
		assertEquals(10, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-4, this.si.add(3));
		assertEquals(-4, this.si.getValue());
	}

	public void testAddExactInt() throws Exception {
		assertEquals(0, this.si.addExact(0));
		assertEquals(0, this.si.getValue());
		assertEquals(3, this.si.addExact(3));
		assertEquals(3, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(10, this.si.addExact(3));
		assertEquals(10, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-4, this.si.addExact(3));
		assertEquals(-4, this.si.getValue());
	}

	public void testSubtractInt() throws Exception {
		assertEquals(0, this.si.subtract(0));
		assertEquals(0, this.si.getValue());
		assertEquals(-3, this.si.subtract(3));
		assertEquals(-3, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(4, this.si.subtract(3));
		assertEquals(4, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-10, this.si.subtract(3));
		assertEquals(-10, this.si.getValue());
	}

	public void testSubtractExactInt() throws Exception {
		assertEquals(0, this.si.subtractExact(0));
		assertEquals(0, this.si.getValue());
		assertEquals(-3, this.si.subtractExact(3));
		assertEquals(-3, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(4, this.si.subtractExact(3));
		assertEquals(4, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-10, this.si.subtractExact(3));
		assertEquals(-10, this.si.getValue());
	}

	public void testMultiplyInt() throws Exception {
		assertEquals(0, this.si.multiply(0));
		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.multiply(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(21, this.si.multiply(3));
		assertEquals(21, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-21, this.si.multiply(3));
		assertEquals(-21, this.si.getValue());
	}

	public void testMultiplyExactInt() throws Exception {
		assertEquals(0, this.si.multiplyExact(0));
		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.multiplyExact(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(21, this.si.multiplyExact(3));
		assertEquals(21, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-21, this.si.multiplyExact(3));
		assertEquals(-21, this.si.getValue());
	}

	public void testDivideInt() throws Exception {
//		assertEquals(0, this.si.divide(0)); // no divide by zero
//		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.divide(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(2, this.si.divide(3));
		assertEquals(2, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-2, this.si.divide(3));
		assertEquals(-2, this.si.getValue());
	}

	public void testFloorDivideInt() throws Exception {
//		assertEquals(0, this.si.floorDivide(0)); // no divide by zero
//		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.floorDivide(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(2, this.si.floorDivide(3));
		assertEquals(2, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-3, this.si.floorDivide(3));
		assertEquals(-3, this.si.getValue());
	}

	public void testRemainderInt() throws Exception {
//		assertEquals(0, this.si.remainder(0)); // no divide by zero
//		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.remainder(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(1, this.si.remainder(3));
		assertEquals(1, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-1, this.si.remainder(3));
		assertEquals(-1, this.si.getValue());
	}

	public void testFloorRemainderInt() throws Exception {
//		assertEquals(0, this.si.floorRemainder(0)); // no divide by zero
//		assertEquals(0, this.si.getValue());
		assertEquals(0, this.si.floorRemainder(3));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(1, this.si.floorRemainder(3));
		assertEquals(1, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(2, this.si.floorRemainder(3));
		assertEquals(2, this.si.getValue());
	}

	public void testMinInt() throws Exception {
		assertEquals(0, this.si.min(0));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(3, this.si.min(3));
		assertEquals(3, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(-7, this.si.min(3));
		assertEquals(-7, this.si.getValue());
	}

	public void testMaxInt() throws Exception {
		assertEquals(0, this.si.max(0));
		assertEquals(0, this.si.getValue());
		this.si.setValue(this.value);
		assertEquals(7, this.si.max(3));
		assertEquals(7, this.si.getValue());
		this.si.setValue(-this.value);
		assertEquals(3, this.si.max(3));
		assertEquals(3, this.si.getValue());
	}

	public void testCommitIntInt() throws Exception {
		assertFalse(this.si.commit(0, -3));
		assertEquals(0, this.si.getValue());

		assertFalse(this.si.commit(0, 3));
		assertEquals(0, this.si.getValue());

		assertTrue(this.si.commit(0, 0));
		assertEquals(0, this.si.getValue());

		this.si.setValue(this.value);
		assertTrue(this.si.commit(3, 7));
		assertEquals(3, this.si.getValue());

		this.si.setValue(-this.value);
		assertTrue(this.si.commit(3, -7));
		assertEquals(3, this.si.getValue());
	}

	public void testSwapRef() throws Exception {
		ModifiableIntReference temp = this.si;
		assertEquals(0, this.si.swap(temp));

		ModifiableIntReference ref = new SimpleIntReference(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(0, ref.getValue());

		this.si.setValue(42);
		ref.setValue(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(42, ref.getValue());
	}

	public void testSwapSyncInt() throws Exception {
		assertEquals(0, this.si.swap(this.si));

		ModifiableIntReference ref = new SynchronizedInt(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(0, ref.getValue());

		this.si.setValue(42);
		ref.setValue(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(42, ref.getValue());

		ref.setValue(33);
		assertEquals(42, ref.swap(this.si));
		assertEquals(33, this.si.getValue());
		assertEquals(42, ref.getValue());
	}

	public void testSwapSyncInt2() throws Exception {
		assertEquals(0, this.si.swap(this.si));

		SynchronizedInt ref = new SynchronizedInt(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(0, ref.getValue());

		this.si.setValue(42);
		ref.setValue(42);
		assertEquals(42, this.si.swap(ref));
		assertEquals(42, this.si.getValue());
		assertEquals(42, ref.getValue());
	}

	public void testGetMutexThis() throws Exception {
		assertSame(this.si, this.si.getMutex());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0
	 */
	public void testWaitUntilZero() throws Exception {
		this.verifyWaitUntilZero(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to 0 by t2
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	public void testWaitUntilZero2() throws Exception {
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to 0
	 */
	public void testWaitUntilZeroTimeout2() throws Exception {
		this.verifyWaitUntilZero(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to 0 by t2
		assertEquals(0, this.si.getValue());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be >= " + TWO_TICKS + "): " + time, time >= TWO_TICKS);
	}

	private void verifyWaitUntilZero(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetZeroCommand(), this.buildWaitUntilZeroCommand(t2Timeout));
	}

	private Command buildWaitUntilZeroCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws InterruptedException {
				SynchronizedIntTests.this.startTime = System.currentTimeMillis();
				SynchronizedIntTests.this.timeoutOccurred = this.timeoutOccurred(sInt);
				SynchronizedIntTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedInt sInt) throws InterruptedException {
				if (timeout < 0) {
					sInt.waitUntilZero();
					return false;
				}
				return ! sInt.waitUntilZero(timeout);
			}
		};
	}

	public void testWaitUntilNotPredicate() throws Exception {
		this.si.waitUntilNot(IntPredicateTools.isEqual(7));
		assertEquals(0, this.si.getValue());
	}

	public void testWaitUntilNotPredicateTimeout() throws Exception {
		this.si.waitUntilNot(IntPredicateTools.isEqual(7), TWO_TICKS);
		assertEquals(0, this.si.getValue());
	}

	public void testWaitUntilNotEqualInt() throws Exception {
		this.si.waitUntilNotEqual(7);
		assertEquals(0, this.si.getValue());
	}

	public void testWaitUntilNotEqualIntTimeout() throws Exception {
		this.si.waitUntilNotEqual(7, TWO_TICKS);
		assertEquals(0, this.si.getValue());
	}

	public void testWaitUntilNotZero() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilNotZero();
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilNotZeroTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilNotZero(TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilGreaterThanInt() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilGreaterThan(2);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilGreaterThanIntTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilGreaterThan(2, TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilGreaterThanOrEqualInt() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilGreaterThanOrEqual(2);
		assertEquals(3, this.si.getValue());
		this.si.waitUntilGreaterThanOrEqual(3);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilGreaterThanOrEqualIntTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilGreaterThanOrEqual(2, TWO_TICKS);
		assertEquals(3, this.si.getValue());
		this.si.waitUntilGreaterThanOrEqual(3, TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilLessThanInt() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilLessThan(4);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilLessThanIntTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilLessThan(4, TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilLessThanOrEqualInt() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilLessThanOrEqual(4);
		assertEquals(3, this.si.getValue());
		this.si.waitUntilLessThanOrEqual(3);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilLessThanOrEqualIntTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilLessThanOrEqual(4, TWO_TICKS);
		assertEquals(3, this.si.getValue());
		this.si.waitUntilLessThanOrEqual(3, TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilPositive() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilPositive();
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilPositiveTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilPositive(TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilNotPositive() throws Exception {
		this.si.setValue(-3);
		this.si.waitUntilNotPositive();
		assertEquals(-3, this.si.getValue());
	}

	public void testWaitUntilNotPositiveTimeout() throws Exception {
		this.si.setValue(-3);
		this.si.waitUntilNotPositive(TWO_TICKS);
		assertEquals(-3, this.si.getValue());
	}

	public void testWaitUntilNegative() throws Exception {
		this.si.setValue(-3);
		this.si.waitUntilNegative();
		assertEquals(-3, this.si.getValue());
	}

	public void testWaitUntilNegativeTimeout() throws Exception {
		this.si.setValue(-3);
		this.si.waitUntilNegative(TWO_TICKS);
		assertEquals(-3, this.si.getValue());
	}

	public void testWaitUntilNotNegative() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilNotNegative();
		assertEquals(3, this.si.getValue());
	}

	public void testWaitUntilNotNegativeTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitUntilNotNegative(TWO_TICKS);
		assertEquals(3, this.si.getValue());
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0;
	 * then t2 will set the value to 7
	 */
	public void testWaitToSetValue() throws Exception {
		this.verifyWaitToSetValue(-1);  // explicit indefinite wait
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to an object by t2
		assertTrue(this.si.isNotZero());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be > " + TICK + "): " + time, time > TICK);
	}

	/**
	 * t2 will wait indefinitely until t1 sets the value to 0;
	 * then t2 will set the value to 7
	 */
	public void testWaitToSetValue2() throws Exception {
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

	/**
	 * t2 will NOT time out waiting for t1 to set the value to 0
	 */
	public void testWaitToSetValueTimeout2() throws Exception {
		this.verifyWaitToSetValue(THREE_TICKS);
		// no timeout occurs...
		assertFalse(this.timeoutOccurred);
		// ...and the value should be set to zero by t2
		assertFalse(this.si.isZero());
		// make a reasonable guess about how long t2 took
		long time = this.calculateElapsedTime();
		assertTrue("t2 finished a bit early (expected value should be >= " + TWO_TICKS + "): " + time, time >= TWO_TICKS);
	}

	private void verifyWaitToSetValue(long t2Timeout) throws Exception {
		this.executeThreads(this.buildSetZeroCommand(), this.buildWaitToSetValueCommand(t2Timeout));
	}

	private Command buildWaitToSetValueCommand(final long timeout) {
		return new Command() {
			public void execute(SynchronizedInt sInt) throws InterruptedException {
				SynchronizedIntTests.this.startTime = System.currentTimeMillis();
				SynchronizedIntTests.this.timeoutOccurred = this.timeoutOccurred(sInt);
				SynchronizedIntTests.this.endTime = System.currentTimeMillis();
			}
			private boolean timeoutOccurred(SynchronizedInt sInt) throws InterruptedException {
				if (timeout < 0) {
					sInt.waitToSetValue(SynchronizedIntTests.this.value);
					return false;
				}
				return ! sInt.waitToSetValue(SynchronizedIntTests.this.value, timeout);
			}
		};
	}

	public void testWaitToSetZero() throws Exception {
		this.si.setValue(3);
		this.si.waitToSetZero();
		assertEquals(0, this.si.getValue());
	}

	public void testWaitToSetZeroTimeout() throws Exception {
		this.si.setValue(3);
		this.si.waitToSetZero(TWO_TICKS);
		assertEquals(0, this.si.getValue());
	}

	public void testWaitToCommit() throws Exception {
		this.si.setValue(3);
		this.si.waitToCommit(7, 3);
		assertEquals(7, this.si.getValue());
	}

	public void testWaitToCommitTimeout() throws Exception {
		this.si.setValue(3);
		assertTrue(this.si.waitToCommit(7, 3, TWO_TICKS));
		assertEquals(7, this.si.getValue());
	}

	public void testWaitToCommitTimeout2() throws Exception {
		this.si.setValue(3);
		assertFalse(this.si.waitToCommit(7, 333, TWO_TICKS));
		assertEquals(3, this.si.getValue());
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

	private Command buildInitializeValueCommand() {
		return new Command() {
			public void execute(final SynchronizedInt sInt) throws InterruptedException {
				sInt.execute(
					new org.eclipse.jpt.common.utility.command.Command() {
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

	public void testCompareTo() throws Exception {
		IntReference ref = this.si;
		assertEquals(0, this.si.compareTo(ref));

		ModifiableIntReference other = new SimpleIntReference(42);
		assertTrue(this.si.compareTo(other) < 0);
		assertTrue(other.compareTo(this.si) > 0);

		this.si.setValue(44);
		assertTrue(this.si.compareTo(other) > 0);
		assertTrue(other.compareTo(this.si) < 0);
	}


	// ********** Command interface **********

	private interface Command {
		void execute(SynchronizedInt sInt) throws InterruptedException;
	}


	// ********** standard methods **********

	public void testEquals() {
		SynchronizedInt si2 = new SynchronizedInt();
		assertTrue(this.si.equals(this.si));
		assertFalse(this.si.equals(si2));
	}

	public void testHashCode() {
		assertEquals(this.si.hashCode(), this.si.hashCode());
	}

	public void testClone() {
		SynchronizedInt clone = this.si.clone();
		assertEquals(0, clone.getValue());
		assertNotSame(clone, this.si);

		this.si.setValue(42);
		clone = this.si.clone();
		assertEquals(42, clone.getValue());
		assertNotSame(clone, this.si);
	}

	public void testSerialization() throws Exception {
		this.si.setValue(44);
		SynchronizedInt clone = TestTools.serialize(this.si);
		assertNotSame(this.si, clone);
		assertEquals(44, clone.getValue());
	}

	public void testToString() {
		assertEquals("[0]", this.si.toString());
		this.si.setValue(42);
		assertEquals("[42]", this.si.toString());
	}
}
