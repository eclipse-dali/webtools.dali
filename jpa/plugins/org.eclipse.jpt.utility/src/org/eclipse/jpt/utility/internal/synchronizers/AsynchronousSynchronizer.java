/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.synchronizers;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;

/**
 * This synchronizer will perform synchronizations in a separate thread,
 * allowing calls to {@link Synchronizer#synchronize()} to return immediately.
 * <p>
 * <strong>NB:</strong> The client-supplied command should handle any exceptions
 * appropriately (e.g. log the exception and return gracefully so the thread
 * can continue the synchronization process).
 */
public class AsynchronousSynchronizer
	implements Synchronizer
{
	/**
	 * This flag is shared with the synchronization thread. Setting it to true
	 * will trigger the synchronization to begin or, if the synchronization is
	 * currently executing, to execute again, once the current execution is
	 * complete.
	 */
	final SynchronizedBoolean synchronizeFlag = new SynchronizedBoolean(false);

	/**
	 * The runnable passed to the synchronization thread each time it is built.
	 */
	private final Runnable runnable;

	/**
	 * Optional, client-supplied name for the synchronization thread.
	 * If null, allow the JDK to assign a name.
	 */
	private final String threadName;

	/**
	 * The synchronization is performed on this thread. A new thread is built
	 * for every start/stop cycle (since a thread cannot be started more than
	 * once).
	 */
	private Thread thread;


	// ********** construction **********

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Allow the generated thread(s) to be assigned
	 * JDK-generated names.
	 */
	public AsynchronousSynchronizer(Command command) {
		this(command, null);
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Assign the generated thread(s) the specified
	 * name.
	 */
	public AsynchronousSynchronizer(Command command, String threadName) {
		super();
		this.runnable = this.buildRunnable(command);
		this.threadName = threadName;
	}

	Runnable buildRunnable(Command command) {
		return new RunnableSynchronization(command);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Build and start the synchronization thread, but postpone the first
	 * synchronization until requested, via {@link #synchronize()}.
	 */
	public synchronized void start() {
		if (this.thread != null) {
			throw new IllegalStateException("The Synchronizer was not stopped."); //$NON-NLS-1$
		}
		this.thread = this.buildThread();
		this.thread.start();
	}

	private Thread buildThread() {
		Thread t = new Thread(this.runnable);
		if (this.threadName != null) {
			t.setName(this.threadName);
		}
		return t;
	}

	/**
	 * Set the "synchronize" flag so the synchronization thread will execute
	 * a synchronization.
	 */
	public void synchronize() {
		this.synchronizeFlag.setTrue();
	}

	public synchronized void stop() {
		if (this.thread == null) {
			throw new IllegalStateException("The Synchronizer was not started."); //$NON-NLS-1$
		}
		this.synchronizeFlag.setFalse();
		this.thread.interrupt();
		try {
			this.thread.join();
		} catch (InterruptedException ex) {
			// the thread that called #stop() was interrupted while waiting to
			// join the synchronization thread - ignore;
			// 'thread' is still "interrupted", so its #run() loop will still stop
			// after its current execution - we just won't wait around for it...
		}
		this.thread = null;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.thread);
	}


	// ********** synchronization thread runnable **********

	/**
	 * This implementation of {@link Runnable} will execute a client-supplied command.
	 * It will wait until a shared "synchronize" flag is set to execute the
	 * command. Once the the comand is executed, the thread will quiesce until
	 * the flag is set again. If the flag was set during the execution of the
	 * command (either recursively by the command itself or by another thread),
	 * the command will be re-executed immediately. Stop the thread by calling
	 * {@link Thread#interrupt()}.
	 */
	class RunnableSynchronization implements Runnable {
		/** The client-supplied command that executes on this thread. */
		private final Command command;


		RunnableSynchronization(Command command) {
			super();
			if (command == null) {
				throw new NullPointerException();
			}
			this.command = command;
		}

		/**
		 * Loop while this thread has not been interrupted by another thread.
		 * In each loop: Wait until the "synchronize" flag is set,
		 * then clear it and execute the command. If the
		 * "synchronize" flag was set <em>during</em> the synchronization,
		 * there will be no "wait" before beginning the next synchronization
		 * (thus the call to {@link Thread#isInterrupted()} before each cycle).
		 * <p>
		 * If this thread is interrupted <em>during</em> the synchronization, the
		 * call to {@link Thread#interrupted()} will stop the loop. If this thread is
		 * interrupted during the call to {@link SynchronizedBoolean#waitToSetFalse()},
		 * we will catch the {@link InterruptedException} and stop the loop.
		 */
		public void run() {
			while ( ! Thread.interrupted()) {
				try {
					AsynchronousSynchronizer.this.synchronizeFlag.waitToSetFalse();
				} catch (InterruptedException ex) {
					// we were interrupted while waiting, must be Quittin' Time
					return;
				}
				this.execute();
			}
		}

		/**
		 * Execute the client-supplied command.
		 */
		void execute() {
			this.command.execute();
		}

	}

}
