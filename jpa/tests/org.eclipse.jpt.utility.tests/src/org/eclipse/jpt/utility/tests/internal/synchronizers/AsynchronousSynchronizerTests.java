/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.synchronizers;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CompositeException;
import org.eclipse.jpt.utility.internal.synchronizers.AsynchronousSynchronizer;
import org.eclipse.jpt.utility.internal.synchronizers.Synchronizer;

@SuppressWarnings("nls")
public class AsynchronousSynchronizerTests extends TestCase {
	PrimaryModel1 primaryModel1;
	SecondaryModel1 secondaryModel1;
	Command command1;
	Synchronizer synchronizer1;

	PrimaryModel2 primaryModel2;
	SecondaryModel2 secondaryModel2;
	Command command2;
	Synchronizer synchronizer2;

	public AsynchronousSynchronizerTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.primaryModel1 = new PrimaryModel1();
		this.secondaryModel1 = new SecondaryModel1(this.primaryModel1);
		this.command1 = new SynchronizeSecondaryModelCommand1(this.secondaryModel1);
		this.synchronizer1 = new AsynchronousSynchronizer(this.command1);
		this.primaryModel1.setSynchronizer(this.synchronizer1);

		this.primaryModel2 = new PrimaryModel2();
		this.secondaryModel2 = new SecondaryModel2(this.primaryModel2);
		this.command2 = new SynchronizeSecondaryModelCommand2(this.primaryModel2, this.secondaryModel2);
		this.synchronizer2 = new AsynchronousSynchronizer(this.command2);
		this.primaryModel2.setSynchronizer(this.synchronizer2);
	}

	@Override
	protected void tearDown() throws Exception {
		stop(this.synchronizer1);
		stop(this.synchronizer2);
		super.tearDown();
	}

	protected static void stop(Synchronizer synchronizer) {
		if (ClassTools.fieldValue(synchronizer, "thread") != null) {
			synchronizer.stop();
		}
	}

	public void testInitialization() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testToString() {
		assertNotNull(this.synchronizer1.toString());
	}

	public void testChange() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setCount(7);

		delay(1);
		this.synchronizer1.stop();
		delay(1);

		assertEquals(14, this.secondaryModel1.getDoubleCount());
	}

	public void testStart() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(Synchronizer.Null.instance());
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(this.synchronizer1);
		// the async synchronizer does not synchronize at start-up
		assertEquals(4, this.secondaryModel1.getDoubleCount());

		this.primaryModel1.setCount(8);

		delay(1);
		this.synchronizer1.stop();
		delay(1);

		assertEquals(16, this.secondaryModel1.getDoubleCount());
	}

	public void testStop() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.dispose();
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testDoubleStart() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		boolean exCaught = false;
		try {
			this.primaryModel1.startSynchronizer();
			fail();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		this.primaryModel1.setCount(7);

		delay(1);
		this.synchronizer1.stop();
		delay(1);

		assertEquals(14, this.secondaryModel1.getDoubleCount());
	}

	public void testDoubleStop() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.dispose();
		boolean exCaught = false;
		try {
			this.primaryModel1.dispose();
			fail();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testRecursiveChange() throws Exception {
		assertEquals(4, this.secondaryModel2.getDoubleCount());
		this.primaryModel2.setCount(7);

		delay(1);
		assertEquals(10, this.primaryModel2.getCountPlus3());
		assertEquals(14, this.secondaryModel2.getDoubleCount());

		delay(1);
		assertEquals(20, this.secondaryModel2.getDoubleCountPlus3());
	}

	public void testNullCommand() {
		boolean exCaught = false;
		try {
			Synchronizer s = new AsynchronousSynchronizer(null);
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testThreadName() {
		Synchronizer s = new AsynchronousSynchronizer(this.command1, "sync");
		s.start();
		Thread t = (Thread) ClassTools.fieldValue(s, "thread");
		assertEquals("sync", t.getName());
		s.stop();
	}

	public void testSynchronizeCalledBeforeStart() throws Exception {
		SimpleCommand command = new SimpleCommand();
		Synchronizer synchronizer = new AsynchronousSynchronizer(command);

		synchronizer.synchronize();
		synchronizer.start();
		delay(1);
		synchronizer.stop();
		assertEquals(1, command.count);
	}

	public class SimpleCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
		}
	}

	public void testException() throws Exception {
		BogusCommand command = new BogusCommand();
		Synchronizer synchronizer = new AsynchronousSynchronizer(command);
		synchronizer.start();

		synchronizer.synchronize();
		delay(1);

		synchronizer.synchronize();
		delay(1);

		boolean exCaught = false;
		try {
			synchronizer.stop();
			fail();
		} catch (CompositeException ex) {
			assertEquals(2, ex.getExceptions().length);
			exCaught = true;
		}
		assertTrue(exCaught);
		assertEquals(2, command.count);
	}

	public class BogusCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
			throw new NullPointerException();
		}
	}


	// ********** synchronize commands **********
	public static class SynchronizeSecondaryModelCommand1 implements Command {
		private final SecondaryModel1 secondaryModel;

		public SynchronizeSecondaryModelCommand1(SecondaryModel1 secondaryModel) {
			super();
			this.secondaryModel = secondaryModel;
		}

		public void execute() {
			this.secondaryModel.synchronize();
		}

	}

	/**
	 * the primary model (subclass) has to synchronize with itself (superclass)
	 */
	public static class SynchronizeSecondaryModelCommand2 extends SynchronizeSecondaryModelCommand1 {
		private final PrimaryModel2 primaryModel;

		public SynchronizeSecondaryModelCommand2(PrimaryModel2 primaryModel, SecondaryModel2 secondaryModel) {
			super(secondaryModel);
			this.primaryModel = primaryModel;
		}

		@Override
		public void execute() {
			super.execute();
			this.primaryModel.synchronize();
		}

	}

	// ********** primary models **********
	/**
	 * this object will call the synchronizer whenever its count changes,
	 * allowing interested parties to synchronize with the change
	 */
	public static class PrimaryModel1 {
		protected Synchronizer synchronizer;
		protected int count = 2;

		public PrimaryModel1() {
			super();
			this.setSynchronizer_(Synchronizer.Null.instance());
		}

		public int getCount() {
			return this.count;
		}
		public void setCount(int count) {
			if (count != this.count) {
				this.count = count;
				this.stateChanged();
			}
		}

		protected void stateChanged() {
			this.synchronizer.synchronize();
		}

		public void setSynchronizer(Synchronizer synchronizer) {
			if (synchronizer == null) {
				throw new NullPointerException();
			}
			this.synchronizer.stop();
			this.setSynchronizer_(synchronizer);
		}
			
		protected void setSynchronizer_(Synchronizer synchronizer) {
			this.synchronizer = synchronizer;
			this.synchronizer.start();
		}

		public void startSynchronizer() {
			this.synchronizer.start();  // this should cause an exception
		}
		public void dispose() {
			this.synchronizer.stop();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(ClassTools.toStringClassNameForObject(this));
			sb.append('(');
			this.toString(sb);
			sb.append(')');
			return sb.toString();
		}
		public void toString(StringBuilder sb) {
			sb.append("count=");
			sb.append(this.count);
		}

	}

	/**
	 * This model synchronizes with itself, triggering a recursive synchronization
	 * with the change. Whenever the [inherited] 'count' changes, 'countPlus3'
	 * is updated appropriately and another synchronize is initiated if appropriate.
	 */
	public static class PrimaryModel2 extends PrimaryModel1 {
		private int countPlus3 = 0;

		public PrimaryModel2() {
			super();
			this.countPlus3 = this.count + 3;
		}

		public int getCountPlus3() {
			return this.countPlus3;
		}
		protected void setCountPlus3(int countPlus3) {
			if (countPlus3 != this.countPlus3) {
				this.countPlus3 = countPlus3;
				this.stateChanged();
			}
		}

		// synchronize with itself, so to speak
		public void synchronize() {
			this.setCountPlus3(this.count + 3);
		}

		@Override
		public void toString(StringBuilder sb) {
			super.toString(sb);
			sb.append(", countPlus3=");
			sb.append(this.countPlus3);
		}

	}

	// ********** secondary models **********
	/**
	 * This dependent object updates its 'doubleCount' whenever the
	 * PrimaryModel1's 'count' changes, via the 'synchronizer'.
	 */
	public static class SecondaryModel1 {
		protected final PrimaryModel1 primaryModel;
		protected int doubleCount = 0;

		public SecondaryModel1(PrimaryModel1 primaryModel) {
			super();
			this.primaryModel = primaryModel;
			this.synchronize();
		}

		public void synchronize() {
			this.doubleCount = this.primaryModel.getCount() * 2;
		}

		public int getDoubleCount() {
			return this.doubleCount;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(ClassTools.toStringClassNameForObject(this));
			sb.append('(');
			this.toString(sb);
			sb.append(')');
			return sb.toString();
		}
		public void toString(StringBuilder sb) {
			sb.append("doubleCount=");
			sb.append(this.doubleCount);
		}

	}

	public static class SecondaryModel2 extends SecondaryModel1 {
		private int doubleCountPlus3 = 0;

		public SecondaryModel2(PrimaryModel2 extendedPrimaryModel) {
			super(extendedPrimaryModel);
		}

		protected PrimaryModel2 getExtendedPrimaryModel() {
			return (PrimaryModel2) this.primaryModel;
		}

		@Override
		public void synchronize() {
			super.synchronize();
			int temp = this.getExtendedPrimaryModel().getCountPlus3() * 2;
			if (this.doubleCountPlus3 != temp) {
				this.doubleCountPlus3 = temp;
			}
		}

		public int getDoubleCountPlus3() {
			return this.doubleCountPlus3;
		}

		@Override
		public void toString(StringBuilder sb) {
			super.toString(sb);
			sb.append(", doubleCountPlus3=");
			sb.append(this.doubleCountPlus3);
		}

	}

	public static void delay(float ticks) {
		try {
			delay_(ticks);
		} catch (InterruptedException ex) {
			// just stop everything and return
			return;
		}
	}

	public static void delay_(float ticks) throws InterruptedException {
		// a tenth of second per tick should work...
		long milliseconds = (long) (ticks * 100);
		long stop = System.currentTimeMillis() + milliseconds;
		long remaining = milliseconds;
		while (remaining > 0L) {
			Thread.sleep(remaining);
			remaining = stop - System.currentTimeMillis();
		}
	}

}
