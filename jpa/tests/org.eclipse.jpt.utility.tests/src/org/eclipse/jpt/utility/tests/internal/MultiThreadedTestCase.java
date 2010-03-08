/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadFactory;

import org.eclipse.jpt.utility.internal.CompositeException;

import junit.framework.TestCase;

/**
 * This test case helps simplify the testing of multi-threaded code.
 */
@SuppressWarnings("nls")
public abstract class MultiThreadedTestCase
	extends TestCase
{
	private final ArrayList<Thread> threads = new ArrayList<Thread>();
	/* private */ final Vector<Throwable> exceptions = new Vector<Throwable>();

	/**
	 * The default "tick" is one second.
	 * Specify the appropriate system property to override.
	 */
	public static final String TICK_SYSTEM_PROPERTY_NAME = "org.eclipse.jpt.utility.tests.tick";
	public static final long TICK = Long.getLong(TICK_SYSTEM_PROPERTY_NAME, 1000).longValue();
	public static final long TWO_TICKS = 2 * TICK;
	public static final long THREE_TICKS = 3 * TICK;

	/**
	 * Default constructor.
	 */
	public MultiThreadedTestCase() {
		super();
	}

	/**
	 * Named constructor.
	 */
	public MultiThreadedTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Stop all the threads constructed during the test case.
	 * If any exceptions were thrown by the threads, re-throw them here.
	 */
	@Override
	protected void tearDown() throws Exception {
		for (Thread thread : this.threads) {
			if (thread.isAlive()) {
				throw new IllegalStateException("thread is still alive: " + thread);
			}
		}
		if ( ! this.exceptions.isEmpty()) {
			throw new CompositeException(this.exceptions);
		}
		TestTools.clear(this);
		super.tearDown();
	}

	protected Thread buildThread(Runnable runnable) {
		return this.buildThread(runnable, null);
	}

	protected Thread buildThread(Runnable runnable, String name) {
		Thread thread = new Thread(new RunnableWrapper(runnable));
		if (name != null) {
			thread.setName(name);
		}
		this.threads.add(thread);
		return thread;
	}

	protected ThreadFactory buildThreadFactory() {
		return new TestThreadFactory();
	}

	/**
	 * Convenience method that handles {@link InterruptedException}.
	 */
	public void sleep(long millis) {
		TestTools.sleep(millis);
	}


	/**
	 * Wrap a runnable and log any exception it throws.
	 */
	public class TestThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			return MultiThreadedTestCase.this.buildThread(r);
		}
	}

	/**
	 * Simplify runnables that execute call that throws checked exceptions.
	 */
	public abstract class TestRunnable implements Runnable {
		public final void run() {
			try {
				this.run_();
			} catch (Throwable ex) {
				throw new RuntimeException(ex);
			}
		}
		protected abstract void run_() throws Throwable;
	}

	/**
	 * Wrap a runnable and log any exception it throws.
	 */
	private class RunnableWrapper implements Runnable {
		private final Runnable runnable;
		RunnableWrapper(Runnable runnable) {
			super();
			this.runnable = runnable;
		}
		public void run() {
			try {
				this.runnable.run();
			} catch (Throwable ex) {
				MultiThreadedTestCase.this.exceptions.add(ex);
			}
		}
	}
}
