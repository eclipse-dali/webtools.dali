/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.util.Vector;
import java.util.concurrent.ThreadFactory;

/**
 * A <code>ConsumerThreadCoordinator</code> controls the creation,
 * starting, and stopping of a general purpose "consumer" thread. Construct
 * the coordinator with a {@link Consumer} that both waits for the producer
 * to "produce" something to "consume" and, once the wait is over,
 * "consumes" whatever is available.
 * <p>
 * <strong>NB:</strong> The client-supplied consumer should handle any
 * exception appropriately (e.g. log the exception and return gracefully) so
 * the thread can continue executing.
 */
public class ConsumerThreadCoordinator {
	/**
	 * The runnable passed to the consumer thread each time it is built.
	 */
	private final Runnable runnable;

	/**
	 * Client-supplied thread factory. Defaults to a straightforward
	 * implementation.
	 */
	private final ThreadFactory threadFactory;

	/**
	 * Optional, client-supplied name for the consumer thread.
	 * If null, the JDK assigns a name.
	 */
	private final String threadName;

	/**
	 * The consumer is executed on this thread. A new thread is built
	 * for every start/stop cycle (since a thread cannot be started more than
	 * once).
	 */
	private volatile Thread thread;

	/**
	 * A list of the uncaught exceptions thrown by the consumer
	 * during the current start/stop cycle.
	 */
	final Vector<Throwable> exceptions = new Vector<Throwable>();


	// ********** construction **********

	/**
	 * Construct a consumer thread coordinator for the specified consumer.
	 * Use simple JDK thread(s) for the consumer thread(s).
	 * Allow the consumer thread(s) to be assigned JDK-generated names.
	 */
	public ConsumerThreadCoordinator(Consumer consumer) {
		this(consumer, SimpleThreadFactory.instance());
	}

	/**
	 * Construct a consumer thread coordinator for the specified consumer.
	 * Use the specified thread factory to construct the consumer thread(s).
	 * Allow the consumer thread(s) to be assigned JDK-generated names.
	 */
	public ConsumerThreadCoordinator(Consumer consumer, ThreadFactory threadFactory) {
		this(consumer, threadFactory, null);
	}

	/**
	 * Construct a consumer thread coordinator for the specified consumer.
	 * Assign the consumer thread(s) the specified name.
	 * Use simple JDK thread(s) for the consumer thread(s).
	 */
	public ConsumerThreadCoordinator(Consumer consumer, String threadName) {
		this(consumer, SimpleThreadFactory.instance(), threadName);
	}

	/**
	 * Construct a consumer thread coordinator for the specified consumer.
	 * Use the specified thread factory to construct the consumer thread(s).
	 * Assign the consumer thread(s) the specified name.
	 */
	public ConsumerThreadCoordinator(Consumer consumer, ThreadFactory threadFactory, String threadName) {
		super();
		this.runnable = this.buildRunnable(consumer);
		this.threadFactory = threadFactory;
		this.threadName = threadName;
	}

	private Runnable buildRunnable(Consumer consumer) {
		return new RunnableConsumer(consumer);
	}


	// ********** Lifecycle support **********

	/**
	 * Build and start the consumer thread.
	 */
	public synchronized void start() {
		if (this.thread != null) {
			throw new IllegalStateException("Not stopped."); //$NON-NLS-1$
		}
		this.thread = this.buildThread();
		this.thread.start();
	}

	private Thread buildThread() {
		Thread t = this.threadFactory.newThread(this.runnable);
		if (this.threadName != null) {
			t.setName(this.threadName);
		}
		return t;
	}

	/**
	 * Interrupt the consumer thread so that it stops executing at the
	 * end of its current iteration. Suspend the current thread until
	 * the consumer thread is finished executing. If any uncaught
	 * exceptions were thrown while the consumer thread was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 */
	public synchronized void stop() {
		if (this.thread == null) {
			throw new IllegalStateException("Not started."); //$NON-NLS-1$
		}
		this.thread.interrupt();
		try {
			this.thread.join();
		} catch (InterruptedException ex) {
			// the thread that called #stop() was interrupted while waiting to
			// join the consumer thread - ignore;
			// 'thread' is still "interrupted", so its #run() loop will still stop
			// after its current execution - we just won't wait around for it...
		}
		this.thread = null;

		if (this.exceptions.size() > 0) {
			Throwable[] temp = this.exceptions.toArray(new Throwable[this.exceptions.size()]);
			this.exceptions.clear();
			throw new CompositeException(temp);
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.thread);
	}


	// ********** consumer thread runnable **********

	/**
	 * This implementation of {@link Runnable} is a long-running consumer that
	 * will repeatedly execute the consumer {@link Consumer#execute()} method.
	 * With each iteration, the consumer thread will wait
	 * until the other consumer method, {@link Consumer#waitForProducer()}, allows the
	 * consumer thread to proceed (i.e. there is something for the consumer to
	 * consume). Once {@link Consumer#execute()} is finished, the thread will quiesce
	 * until {@link Consumer#waitForProducer()} returns again.
	 * Stop the thread by calling {@link Thread#interrupt()}.
	 */
	private class RunnableConsumer
		implements Runnable
	{
		/**
		 * Client-supplied consumer that controls waiting for something to consume
		 * and the consuming itself.
		 */
		private final Consumer consumer;

		RunnableConsumer(Consumer consumer) {
			super();
			this.consumer = consumer;
		}

		/**
		 * Loop while this thread has not been interrupted by another thread.
		 * In each loop: Pause execution until {@link Consumer#waitForProducer()}
		 * allows us to proceed.
		 * <p>
		 * If this thread is interrupted <em>during</em> {@link Consumer#execute()},
		 * the call to {@link Thread#interrupted()} will stop the loop. If this thread is
		 * interrupted during the call to {@link Consumer#waitForProducer()},
		 * we will catch the {@link InterruptedException} and stop the loop also.
		 */
		public void run() {
			while ( ! Thread.interrupted()) {
				try {
					this.consumer.waitForProducer();
				} catch (InterruptedException ex) {
					// we were interrupted while waiting, must be Quittin' Time
					return;
				}
				this.execute();
			}
		}

		/**
		 * Execute the consumer {@link Consumer#execute()} method.
		 * Do not allow any unhandled exceptions to kill the thread.
		 * Store them up for later pain.
		 * @see ConsumerThreadCoordinator#stop()
		 */
		private void execute() {
			try {
				this.execute_();
			} catch (Throwable ex) {
				ConsumerThreadCoordinator.this.exceptions.add(ex);
			}
		}

		/**
		 * Subclass-implemented behavior: consume stuff.
		 */
		private void execute_() {
			this.consumer.execute();
		}

	}


	// ********** consumer interface **********

	/**
	 * Interface implemented by clients that controls:<ul>
	 * <li>when the consumer thread suspends, waiting for something to consume
	 * <li>the consuming of whatever is being produced
	 * </ul>
	 */
	public interface Consumer {
		/**
		 * Wait for something to consume.
		 * Throw an {@link InterruptedException} if the thread is interrupted.
		 */
		void waitForProducer() throws InterruptedException;

		/**
		 * Consume whatever is currently available.
		 */
		void execute();
	}

}
