/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.Synchronizer;

/**
 * This synchronizer will perform synchronizations in a separate thread,
 * allowing calls to {@link Synchronizer#synchronize()} to return immediately.
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
	protected final SynchronizedBoolean synchronizeFlag = new SynchronizedBoolean(false);

	/**
	 * The runnable passed to the synchronization thread each time it is built.
	 */
	protected final Runnable runnable;

	/**
	 * Optional, client-supplied name for the synchronization thread.
	 * If null, allow the JDK to assign a name.
	 */
	protected final String threadName;

	/**
	 * The synchronization is performed on this thread. A new thread is built
	 * for every start/stop cycle (since a thread cannot be started more than
	 * once).
	 */
	protected Thread thread;


	// ********** construction **********

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Use the JDK-generated thread name, and use
	 * a "null" exception handler.
	 */
	public AsynchronousSynchronizer(Command command) {
		this(command, null, ExceptionHandler.Null.instance());
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Use the specified thread name, and use a
	 * "null" exception handler.
	 */
	public AsynchronousSynchronizer(Command command, String threadName) {
		this(command, threadName, ExceptionHandler.Null.instance());
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Use the specified exception handler, and use
	 * the JDK-generated thread name.
	 */
	public AsynchronousSynchronizer(Command command, ExceptionHandler exceptionHandler) {
		this(command, null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous synchronizer that uses the specified command to
	 * perform the synchronization. Use the specified thread name and exception
	 * handler.
	 */
	public AsynchronousSynchronizer(Command command, String threadName, ExceptionHandler exceptionHandler) {
		super();
		if (command == null) {
			throw new NullPointerException("command"); //$NON-NLS-1$
		}
		if (exceptionHandler == null) {
			throw new NullPointerException("exceptionHandler"); //$NON-NLS-1$
		}
		this.runnable = this.buildRunnable(command, exceptionHandler);
		this.threadName = threadName;
	}

	protected Runnable buildRunnable(Command command, ExceptionHandler exceptionHandler) {
		return new RunnableSychronization(command, this.synchronizeFlag, exceptionHandler);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Build and start the synchronization thread, but postpone the first
	 * synchronization until requested.
	 */
	public synchronized void start() {
		if (this.thread != null) {
			throw new IllegalStateException("The Synchronizer was not stopped."); //$NON-NLS-1$
		}
		this.thread = this.buildThread();
		this.thread.start();
	}

	protected Thread buildThread() {
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
			// not sure why *this* thread would be interrupted;
			// ignore it for now...
			// 'thread' is still "interrupted", so the #run() loop will still stop - we
			// just won't wait around for it...
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
	protected static class RunnableSychronization implements Runnable {
		/** The client-supplied command that executes on this thread. */
		protected final Command command;

		/** When this flag is set to true, we execute the client-supplied command. */
		protected final SynchronizedBoolean synchronizeFlag;

		/** Pass any exceptions encountered during synchronization to the client-supplied handler. */
		protected final ExceptionHandler exceptionHandler;


		protected RunnableSychronization(
				Command command,
				SynchronizedBoolean synchronizeFlag,
				ExceptionHandler exceptionHandler
		) {
			super();
			this.command = command;
			this.synchronizeFlag = synchronizeFlag;
			this.exceptionHandler = exceptionHandler;
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
		 * call to {@link Thread#isInterrupted()} will stop the loop. If this thread is
		 * interrupted during the call to {@link SynchronizedBoolean#waitToSetFalse()},
		 * we will catch the {@link InterruptedException} and stop the loop.
		 */
		public void run() {
			while ( ! Thread.currentThread().isInterrupted()) {
				try {
					this.synchronizeFlag.waitToSetFalse();
				} catch (InterruptedException ex) {
					// we were interrupted while waiting, must be quittin' time
					return;
				}
				this.execute();
			}
		}

		/**
		 * Execute the client-supplied command, handling any exceptions. If an
		 * exception occurs (and the client-supplied exception handler does not
		 * throw yet another exception), we terminate the synchronization until the
		 * "synchronize" flag is set again. Some exceptions occur because
		 * of concurrent changes to the model that occur <em>after</em> synchronization
		 * starts but before it completes an entire pass over the model. If that
		 * is the case, things should be OK; because the exception will be
		 * caught and the "synchronize" flag will have been set again <em>during</em> the
		 * initial synchronization pass. So when we return from catching the exception
		 * we will simply re-start the synchronization, hopefully with the model in
		 * a consistent state that will prevent another exception from
		 * occurring. Of course, if we have any exceptions that are <em>not</em>
		 * the result of the model being in an inconsistent state, the exception
		 * handler will probably fill its log; and those exceptions are bugs that need
		 * to be fixed. (!) Hopefully the user will notice the enormous log and
		 * file a bug....  ~bjv
		 */
		protected void execute() {
			try {
				this.command.execute();
			} catch (Throwable t) {
				this.handleException(t);
			}
		}

		/**
		 * Delegate to the client-supplied exception handler.
		 */
		protected void handleException(Throwable t) {
			this.exceptionHandler.handleException(t);
		}

	}

}
