/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.RepeatingCommand;
import org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.AsynchronousRepeatingCommandWrapper;
import org.eclipse.jpt.common.utility.internal.command.NullRepeatingCommand;
import org.eclipse.jpt.common.utility.internal.exception.CollectingExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class AsynchronousRepeatingCommandWrapperTests
	extends MultiThreadedTestCase
{
	PrimaryModel1 primaryModel1;
	SecondaryModel1 secondaryModel1;
	Command command1;
	RepeatingCommand repeatingCommand1;

	PrimaryModel2 primaryModel2;
	SecondaryModel2 secondaryModel2;
	Command command2;
	RepeatingCommand repeatingCommand2;

	public AsynchronousRepeatingCommandWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.primaryModel1 = new PrimaryModel1();
		this.secondaryModel1 = new SecondaryModel1(this.primaryModel1);
		this.command1 = new SynchronizeSecondaryModelCommand1(this.secondaryModel1);
		this.repeatingCommand1 = new AsynchronousRepeatingCommandWrapper(this.command1, this.buildThreadFactory(), null, RuntimeExceptionHandler.instance());
		this.primaryModel1.setSynchronizer(this.repeatingCommand1);

		this.primaryModel2 = new PrimaryModel2();
		this.secondaryModel2 = new SecondaryModel2(this.primaryModel2);
		this.command2 = new SynchronizeSecondaryModelCommand2(this.primaryModel2, this.secondaryModel2);
		this.repeatingCommand2 = new AsynchronousRepeatingCommandWrapper(this.command2, this.buildThreadFactory(), null, RuntimeExceptionHandler.instance());
		this.primaryModel2.setSynchronizer(this.repeatingCommand2);
	}

	@Override
	protected void tearDown() throws Exception {
		this.repeatingCommand1.stop();
		this.repeatingCommand2.stop();
		super.tearDown();
	}

	public void testInitialization() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testToString() {
		assertNotNull(this.repeatingCommand1.toString());
	}

	public void testChange() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setCount(7);

		this.sleep(TICK);
		this.repeatingCommand1.stop();
		this.sleep(TICK);

		assertEquals(14, this.secondaryModel1.getDoubleCount());

		// re-start so tear-down works
		this.repeatingCommand1.start();
	}

	public void testStart() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(NullRepeatingCommand.instance());
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(this.repeatingCommand1);
		// the async synchronizer does not synchronize at start-up
		assertEquals(4, this.secondaryModel1.getDoubleCount());

		this.primaryModel1.setCount(8);

		this.sleep(TICK);
		this.repeatingCommand1.stop();
		this.sleep(TICK);

		assertEquals(16, this.secondaryModel1.getDoubleCount());

		// re-start so tear-down works
		this.repeatingCommand1.start();
	}

	public void testStop() throws Exception {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.dispose();
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());

		// re-start so tear-down works
		this.repeatingCommand1.start();
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

		this.sleep(TICK);
		this.repeatingCommand1.stop();
		this.sleep(TICK);

		assertEquals(14, this.secondaryModel1.getDoubleCount());

		// re-start so tear-down works
		this.repeatingCommand1.start();
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

		// re-start so tear-down works
		this.repeatingCommand1.start();
	}

	public void testRecursiveChange() throws Exception {
		assertEquals(4, this.secondaryModel2.getDoubleCount());
		this.primaryModel2.setCount(7);

		this.sleep(TICK);
		assertEquals(10, this.primaryModel2.getCountPlus3());
		assertEquals(14, this.secondaryModel2.getDoubleCount());

		this.sleep(TICK);
		assertEquals(20, this.secondaryModel2.getDoubleCountPlus3());
	}

	public void testNullCommand() {
		boolean exCaught = false;
		try {
			RepeatingCommand s = new AsynchronousRepeatingCommandWrapper(null, this.buildThreadFactory(), null, RuntimeExceptionHandler.instance());
			fail("bogus: " + s);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testThreadName() throws Exception {
		RepeatingCommand s = new AsynchronousRepeatingCommandWrapper(this.command1, this.buildThreadFactory(), "sync", RuntimeExceptionHandler.instance());
		s.start();
		ConsumerThreadCoordinator ctc = (ConsumerThreadCoordinator) ObjectTools.get(s, "consumerThreadCoordinator");
		Thread t = (Thread) ObjectTools.get(ctc, "thread");
		assertEquals("sync", t.getName());
		s.stop();
	}

	public void testExecuteCalledBeforeStart() throws Exception {
		SimpleCommand command = new SimpleCommand();
		RepeatingCommand synchronizer = new AsynchronousRepeatingCommandWrapper(command, this.buildThreadFactory(), null, RuntimeExceptionHandler.instance());

		synchronizer.execute();
		synchronizer.start();
		this.sleep(TICK);
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
		CollectingExceptionHandler exHandler = new CollectingExceptionHandler();
		RepeatingCommand synchronizer = new AsynchronousRepeatingCommandWrapper(command, this.buildThreadFactory(), null, exHandler);
		synchronizer.start();

		synchronizer.execute();
		this.sleep(TICK);

		synchronizer.execute();
		this.sleep(TICK);

		synchronizer.stop();
		assertEquals(2, IterableTools.size(exHandler.getExceptions()));
		assertEquals(2, command.count);
	}

	public class BogusCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
			throw new NullPointerException();
		}
	}

	/**
	 * Make sure the <code>DEBUG</code> constant is <code>false</code>
	 * before checking in the code.
	 */
	public void testDEBUG() {
		TestTools.assertFalseDEBUG(AsynchronousRepeatingCommandWrapper.class);
	}


	// ********** synchronize commands **********

	public static class SynchronizeSecondaryModelCommand1
		implements Command
	{
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
	public static class SynchronizeSecondaryModelCommand2
		extends SynchronizeSecondaryModelCommand1
	{
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
		protected RepeatingCommand synchronizer;
		protected int count = 2;

		public PrimaryModel1() {
			super();
			this.setSynchronizer_(NullRepeatingCommand.instance());
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
			this.synchronizer.execute();
		}

		public void setSynchronizer(RepeatingCommand synchronizer) throws InterruptedException {
			if (synchronizer == null) {
				throw new NullPointerException();
			}
			this.synchronizer.stop();
			this.setSynchronizer_(synchronizer);
		}
			
		protected void setSynchronizer_(RepeatingCommand synchronizer) {
			this.synchronizer = synchronizer;
			this.synchronizer.start();
		}

		public void startSynchronizer() {
			this.synchronizer.start();  // this should cause an exception
		}
		public void dispose() throws InterruptedException {
			this.synchronizer.stop();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.getClass().getSimpleName());
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
	public static class PrimaryModel2
		extends PrimaryModel1
	{
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
			sb.append(this.getClass().getSimpleName());
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

	public static class SecondaryModel2
		extends SecondaryModel1
	{
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
}
