/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.synchronizers;

import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.internal.CompositeException;
import org.eclipse.jpt.common.utility.internal.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.internal.synchronizers.SynchronousSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SynchronousSynchronizerTests
	extends MultiThreadedTestCase
{
	PrimaryModel1 primaryModel1;
	SecondaryModel1 secondaryModel1;
	Command command1;
	Synchronizer synchronizer1;

	PrimaryModel2 primaryModel2;
	SecondaryModel2 secondaryModel2;
	Command command2;
	Synchronizer synchronizer2;

	public SynchronousSynchronizerTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.primaryModel1 = new PrimaryModel1();
		this.secondaryModel1 = new SecondaryModel1(this.primaryModel1);
		this.command1 = new SynchronizeSecondaryModelCommand1(this.secondaryModel1);
		this.synchronizer1 = new SynchronousSynchronizer(this.command1);
		this.primaryModel1.setSynchronizer(this.synchronizer1);

		this.primaryModel2 = new PrimaryModel2();
		this.secondaryModel2 = new SecondaryModel2(this.primaryModel2);
		this.command2 = new SynchronizeSecondaryModelCommand2(this.primaryModel2, this.secondaryModel2);
		this.synchronizer2 = new SynchronousSynchronizer(this.command2);
		this.primaryModel2.setSynchronizer(this.synchronizer2);
	}

	public void testInitialization() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testToString() {
		assertNotNull(this.synchronizer1.toString());
	}

	public void testChange() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setCount(7);
		assertEquals(14, this.secondaryModel1.getDoubleCount());
	}

	public void testStart() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(Synchronizer.Null.instance());
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.setSynchronizer(this.synchronizer1);
		assertEquals(14, this.secondaryModel1.getDoubleCount());
	}

	public void testStop() {
		assertEquals(4, this.secondaryModel1.getDoubleCount());
		this.primaryModel1.dispose();
		this.primaryModel1.setCount(7);
		assertEquals(4, this.secondaryModel1.getDoubleCount());
	}

	public void testDoubleStart() {
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
		assertEquals(14, this.secondaryModel1.getDoubleCount());
	}

	public void testDoubleStop() {
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

	public void testRecursiveChange() {
		assertEquals(4, this.secondaryModel2.getDoubleCount());
		this.primaryModel2.setCount(7);
		assertEquals(10, this.primaryModel2.getCountPlus3());
		assertEquals(14, this.secondaryModel2.getDoubleCount());
		assertEquals(20, this.secondaryModel2.getDoubleCountPlus3());
	}

	/**
	 * Call #stop() from another thread.
	 * Verify that no further synchronizations occur.
	 * Verify the call to #stop() does not return until the pending
	 * synchronization is complete (must set {@link #DEBUG} to true and
	 * look at the console).
	 * 
	 * ticks:
	 * 0 - start "sync" thread (which will sleep for 1 tick);
	 *      start "stop" thread (which will sleep for 2 ticks)
	 * 1 - "sync" thread wakes up and calls PrimaryModel1.setCount(int)
	 *      which triggers call to Synchronizer.synchronize() to begin
	 *      synchronization of SecondaryModel (which will run for 2 ticks;
	 *      i.e. it will finish on tick 3)
	 * 2 - "stop" thread wakes up and calls Synchronizer.stop() and should
	 *      wait until synchronization is complete
	 * 3 - synchronization completes first execution and should stop;
	 *      "stop" thread should run to completion once the synchronization has stopped
	 */
	public void testCallStopFromAnotherThread() throws Exception {
		log("=====" + this.getName() + "=====");
		PrimaryModel2 primaryModel3 = new PrimaryModel2();
		// a synchronize will occur here:
		SecondaryModel3 secondaryModel3 = new SecondaryModel3(primaryModel3);
		Command command3 = new SynchronizeSecondaryModelCommand2(primaryModel3, secondaryModel3);
		Synchronizer synchronizer3 = new SynchronousSynchronizer(command3);
		// another synchronize will occur here:
		primaryModel3.setSynchronizer(synchronizer3);
		secondaryModel3.setTicks(2);

		assertEquals(2, primaryModel3.getCount());
		assertEquals(5, primaryModel3.getCountPlus3());
		assertEquals(4, secondaryModel3.getDoubleCount());
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());

		Thread syncThread = this.buildTriggerSynchronizeThread(primaryModel3, 1);
		Thread stopThread = this.buildStopThread(synchronizer3, 2);

		log("ALL threads start");
		stopThread.start();
		syncThread.start();

		stopThread.join();
		syncThread.join();

		// 'doubleCount' is synchronized; but the synchronization is stopped
		// while that is happening (by the 'stopThread'), so 'doubleCountPlus3'
		// does not get synchronized
		assertEquals(7, primaryModel3.getCount());
		assertEquals(10, primaryModel3.getCountPlus3());
		assertEquals(14, secondaryModel3.getDoubleCount());

		// this does not get updated because it would've been updated by the
		// recursive call to #synchronize(), but by that time #stop() had been called
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());
	}

	private Thread buildTriggerSynchronizeThread(PrimaryModel2 primaryModel, long ticks) {
		return this.buildThread(this.buildTriggerSynchronizeRunnable(primaryModel, ticks), "trigger sync");
	}

	private Runnable buildTriggerSynchronizeRunnable(final PrimaryModel2 primaryModel, final long ticks) {
		return new Runnable() {
			public void run() {
				TestTools.sleep(ticks * TICK);
				primaryModel.setCount(7);
			}
		};
	}

	private Thread buildStopThread(Synchronizer synchronizer, long ticks) {
		return this.buildThread(this.buildStopRunnable(synchronizer, ticks), "stop");
	}

	private Runnable buildStopRunnable(final Synchronizer synchronizer, final long ticks) {
		return new Runnable() {
			public void run() {
				TestTools.sleep(ticks * TICK);
				log("STOP thread Synchronizer.stop()");
				synchronizer.stop();
				log("STOP thread stop");
			}
		};
	}

	/**
	 * Code cloned from {@link #testStopCalledFromAnotherThread()}.
	 * 
	 * Interrupt the "stop" thread while it is waiting for the "synch" thread to finish.
	 * Verify that no further synchronizations occur.
	 * Verify the call to #stop() returns *before*
	 * synchronization is complete (must set {@link #DEBUG} to true and
	 * look at the console).
	 * 
	 * ticks:
	 * 0 - start "sync" thread (which will sleep for 1 tick);
	 *      start "stop" thread (which will sleep for 2 ticks);
	 *      start "interrupt" thread (which will sleep for 3 ticks)
	 * 1 - "sync" thread wakes up and calls PrimaryModel1.setCount(int)
	 *      which triggers call to Synchronizer.synchronize() to begin
	 *      synchronization of SecondaryModel (which will run for 3 ticks;
	 *      i.e. it will finish on tick 4)
	 * 2 - "stop" thread wakes up and calls Synchronizer.stop() and should
	 *      wait until it is interrupted
	 * 3 - "interrupt" thread wakes up and interrupts "stop" thread;
	 *       both will run to completion at that point
	 * 4 - synchronization completes first execution and should stop since the
	 *      "stop" thread, before it was interrupted, told the "synch" thread to stop
	 */
	public void testInterruptStopThread() throws Exception {
		log("=====" + this.getName() + "=====");
		PrimaryModel2 primaryModel3 = new PrimaryModel2();
		// a synchronize will occur here:
		SecondaryModel3 secondaryModel3 = new SecondaryModel3(primaryModel3);
		Command command3 = new SynchronizeSecondaryModelCommand2(primaryModel3, secondaryModel3);
		Synchronizer synchronizer3 = new SynchronousSynchronizer(command3);
		// another synchronize will occur here:
		primaryModel3.setSynchronizer(synchronizer3);
		secondaryModel3.setTicks(3);

		assertEquals(2, primaryModel3.getCount());
		assertEquals(5, primaryModel3.getCountPlus3());
		assertEquals(4, secondaryModel3.getDoubleCount());
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());

		Thread syncThread = this.buildTriggerSynchronizeThread(primaryModel3, 1);
		Thread stopThread = this.buildStopThread(synchronizer3, 2);
		Thread interruptThread = this.buildInterruptThread(stopThread, 3);

		log("ALL threads start");
		stopThread.start();
		syncThread.start();
		interruptThread.start();

		stopThread.join();
		syncThread.join();
		interruptThread.join();

		// 'doubleCount' is synchronized; but the synchronization is stopped
		// while that is happening (by the 'stopThread'), so 'doubleCountPlus3'
		// does not get synchronized
		assertEquals(7, primaryModel3.getCount());
		assertEquals(10, primaryModel3.getCountPlus3());
		assertEquals(14, secondaryModel3.getDoubleCount());

		// this does not get updated because it would've been updated by the
		// recursive call to #synchronize(), but by that time #stop() had been called
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());
	}

	/**
	 * Code cloned from {@link #testStopCalledFromAnotherThread()}.
	 * 
	 * Interrupt the "sync" thread while it is synchronizing, cutting short the synchronization.
	 * Verify that no further synchronizations occur.
	 * Verify the call to #stop() does not return until the pending
	 * synchronization is stops after it is interrupted (must set {@link #DEBUG} to true and
	 * look at the console).
	 * 
	 * ticks:
	 * 0 - start "sync" thread (which will sleep for 1 tick)
	 *      start "stop" thread (which will sleep for 2 ticks)
	 *      start "interrupt" thread (which will sleep for 3 ticks)
	 * 1 - "sync" thread wakes up and calls PrimaryModel1.setCount(int)
	 *      which triggers call to Synchronizer.synchronize() to begin
	 *      synchronization of SecondaryModel (which will run for 4 ticks;
	 *      i.e. it will finish on tick 5)
	 * 2 - "stop" thread wakes up and calls Synchronizer.stop() and should
	 *      wait until the synchronization is interrupted
	 * 3 - "interrupt" thread wakes up and interrupts "sync" thread;
	 *      the "interrupt" thread runs to completion, while the "sync" thread keeps
	 *      going for another tick before it stops in "mid-synchronize"
	 * 4 - the "sync" thread acknowledges the interrupt and stops in
	 *      "mid-synchronize"; it will stop since the "stop" thread told it to at tick 2;
	 *      the "sync" and "stop" threads run to completion
	 */
	public void testInterruptSyncThread() throws Exception {
		log("=====" + this.getName() + "=====");
		PrimaryModel2 primaryModel3 = new PrimaryModel2();
		// a synchronize will occur here:
		SecondaryModel3 secondaryModel3 = new SecondaryModel3(primaryModel3);
		Command command3 = new SynchronizeSecondaryModelCommand2(primaryModel3, secondaryModel3);
		Synchronizer synchronizer3 = new SynchronousSynchronizer(command3);
		// another synchronize will occur here:
		primaryModel3.setSynchronizer(synchronizer3);
		secondaryModel3.setTicks(4);

		assertEquals(2, primaryModel3.getCount());
		assertEquals(5, primaryModel3.getCountPlus3());
		assertEquals(4, secondaryModel3.getDoubleCount());
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());

		Thread syncThread = this.buildTriggerSynchronizeThread(primaryModel3, 1);
		Thread stopThread = this.buildStopThread(synchronizer3, 2);
		Thread interruptThread = this.buildInterruptThread(syncThread, 3);

		log("ALL threads start");
		stopThread.start();
		syncThread.start();
		interruptThread.start();

		stopThread.join();
		syncThread.join();
		interruptThread.join();

		assertEquals(7, primaryModel3.getCount());
		assertEquals(10, primaryModel3.getCountPlus3());

		// none of the secondary model is synchronized because the synchronize()
		// method was interrupted before any synchronization had occurred
		assertEquals(4, secondaryModel3.getDoubleCount());
		assertEquals(10, secondaryModel3.getDoubleCountPlus3());
	}

	private Thread buildInterruptThread(Thread thread, long ticks) {
		return this.buildThread(this.buildInterruptRunnable(thread, ticks), "interrupt");
	}

	private Runnable buildInterruptRunnable(final Thread thread, final long ticks) {
		return new Runnable() {
			public void run() {
				TestTools.sleep(ticks * TICK);
				log("INTERRUPT thread Thread.interrupt()");
				thread.interrupt();
			}
		};
	}

	/**
	 * Call #stop() during a long-running "synchronize"; then call #start()
	 * while the #stop() is waiting for the "synchronize" to complete.
	 * 
	 * ticks:
	 * 0 - start "sync" thread (which will sleep for 1 tick)
	 *      start "stop" thread (which will sleep for 2 ticks)
	 *      start "start" thread (which will sleep for 3 ticks)
	 * 1 - "sync" thread wakes up and calls Synchronizer.synchronize() to begin
	 *      synchronization (which will run for 3 ticks; i.e. it will finish on tick 4)
	 * 2 - "stop" thread wakes up and calls Synchronizer.stop() and should
	 *      wait until the synchronization is finished (i.e. tick 4)
	 * 3 - "start" thread wakes up and calls Synchronizer.start()
	 *      which will throw an exception
	 * 4 - the "sync" thread finishes execution;
	 *      it will stop since the "stop" thread told it to at tick 2;
	 *      the "sync" and "stop" threads run to completion
	 */
	public void testCallStartDuringStop() throws Exception {
		log("=====" + this.getName() + "=====");
		DelayCommand command = this.buildDelayCommand(3);
		Synchronizer synchronizer = new SynchronousSynchronizer(command);
		synchronizer.start();

		Thread syncThread = this.buildSynchronizeThread(synchronizer, 1);
		Thread stopThread = this.buildStopThread(synchronizer, 2);
		SynchronizedBoolean exCaught = new SynchronizedBoolean(false);
		Thread startThread = this.buildStartThread(synchronizer, 3, exCaught);

		log("ALL threads start");
		syncThread.start();
		stopThread.start();
		startThread.start();

		syncThread.join();
		stopThread.join();
		startThread.join();

		assertTrue(exCaught.getValue());
		assertEquals(2, command.count);
	}

	private DelayCommand buildDelayCommand(int ticks) {
		return new DelayCommand(ticks);
	}

	class DelayCommand implements Command {
		final long ticks;
		boolean first = true;
		int count = 0;

		DelayCommand(int ticks) {
			super();
			this.ticks = ticks;
		}

		public void execute() {
			this.count++;
			// do nothing for the first call (from #start())
			if (this.first) {
				log("EXEC first");
				this.first = false;
				return;
			}
			log("EXEC start " + this.count);
			TestTools.sleep(this.ticks * TICK);
			log("EXEC stop " + this.count);
		}

	}

	private Thread buildStartThread(Synchronizer synchronizer, long ticks, SynchronizedBoolean exCaught) {
		return this.buildThread(this.buildStartRunnable(synchronizer, ticks, exCaught), "start");
	}

	private Runnable buildStartRunnable(final Synchronizer synchronizer, final long ticks, final SynchronizedBoolean exCaught) {
		return new Runnable() {
			public void run() {
				TestTools.sleep(ticks * TICK);
				log("START thread Synchronizer.start()");
				try {
					synchronizer.start();
				} catch (IllegalStateException ex) {
					exCaught.setTrue();
					log("START thread exception");
				}
				log("START thread stop");
			}
		};
	}

	private Thread buildSynchronizeThread(Synchronizer synchronizer, long ticks) {
		return this.buildThread(this.buildSynchronizeRunnable(synchronizer, ticks), "synchronize");
	}

	private Runnable buildSynchronizeRunnable(final Synchronizer synchronizer, final long ticks) {
		return new Runnable() {
			public void run() {
				TestTools.sleep(ticks * TICK);
				log("SYNC thread Synchronizer.synchronize()");
				synchronizer.synchronize();
				log("SYNC thread stop");
			}
		};
	}

	public void testException() throws Exception {
		BogusCommand command = new BogusCommand();
		Synchronizer synchronizer = new SynchronousSynchronizer(command);
		synchronizer.start();

		try {
			synchronizer.synchronize();
		} catch (NullPointerException ex) {
			// ignore
		}

		boolean exCaught = false;
		try {
			// we used to hang here, before we began handling exceptions
			synchronizer.stop();
			fail();
		} catch (CompositeException ex) {
			assertEquals(1, ex.getExceptions().length);
			exCaught = true;
		}
		assertTrue(exCaught);
		// start + synchronize
		assertEquals(2, command.count);
	}

	public class BogusCommand implements Command {
		int count = 0;
		public void execute() {
			this.count++;
			if (this.count > 1) {
				throw new NullPointerException();
			}
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

	/**
	 * Allow the time required to synchronize to be specified.
	 * A (non-delayed) synchronize will execute before we have a chance to
	 * change the tick count. (It is called in the SecondaryModel1 constructor.)
	 */
	public static class SecondaryModel3 extends SecondaryModel2 {
		private long delay = 0;
		public SecondaryModel3(PrimaryModel2 primaryModel) {
			super(primaryModel);
		}

		public void setTicks(long ticks) {
			this.delay = ticks * TICK;
		}

		@Override
		public void synchronize() {
			// don't log anything until 'ticks' is set
			if (this.delay == 0) {
				super.synchronize();
			} else {
				log("SYNC thread start - sync duration will be: " + (this.delay / TICK) + " ticks");
				try {
					Thread.sleep(this.delay);
				} catch (InterruptedException ex) {
					log("SYNC thread interrupted");
					TestTools.sleep(TICK);
					return;  // stop synchronize (i.e. don't call super.synchronize())
				}
				super.synchronize();
				log("SYNC thread stop");
			}
		}

	}

	public void testDEBUG() {
		assertFalse(DEBUG);
	}

	public static final boolean DEBUG = false;
	public static void log(String message) {
		if (DEBUG) {
			log_(message);
		}
	}

	private static void log_(String message) {
		System.out.print(timestamp());
		System.out.print(' ');
		System.out.print(message);
		System.out.println();
	}

	public static String timestamp() {
		return String.valueOf((System.currentTimeMillis() % 10000) / 1000f);
	}

}
