/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.RepeatingCommand;
import org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;
import org.eclipse.jpt.common.utility.internal.StackTrace;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * This repeating command will perform a client-supplied command in a separate
 * thread, allowing calls to {@link org.eclipse.jpt.common.utility.command.Command#execute()}
 * to return immediately.
 */
public class AsynchronousRepeatingCommandWrapper
	implements RepeatingCommand
{
	/**
	 * This flag is shared with the execution/consumer thread. Setting it to
	 * <code>true</code> will trigger the execution to begin or, if the
	 * execution is currently under way, to execute again, once the current
	 * execution is complete.
	 */
	final SynchronizedBoolean executeFlag = new SynchronizedBoolean(false);

	/**
	 * Most of the thread-related behavior is delegated to this coordinator.
	 */
	private final ConsumerThreadCoordinator consumerThreadCoordinator;

	/**
	 * List of stack traces for each (repeating) invocation of the command,
	 * starting with the initial invocation. The list is cleared with each
	 * initial invocation of the command.
	 */
	private final ArrayList<StackTrace> stackTraces = DEBUG ? new ArrayList<StackTrace>() : null;
	// see AsynchronousRepeatingCommandWrapperTests.testDEBUG()
	private static final boolean DEBUG = false;


	// ********** construction **********

	/**
	 * Construct an asynchronous repeating command that executes the specified
	 * command.
	 * Use simple JDK thread(s) for the execution thread(s).
	 * Allow the execution thread(s) to be assigned JDK-generated names.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousRepeatingCommandWrapper(Command command, ExceptionHandler exceptionHandler) {
		this(command, null, null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous repeating command that executes the specified
	 * command. Assign the execution thread(s) the specified name.
	 * Use simple JDK thread(s) for the execution thread(s).
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousRepeatingCommandWrapper(Command command, String threadName, ExceptionHandler exceptionHandler) {
		this(command, null, threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous repeating command that executes the specified
	 * command.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Assign the synchronization thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousRepeatingCommandWrapper(Config config) {
		this(config.getCommand(), config.getThreadFactory(), config.getThreadName(), config.getExceptionHandler());
	}

	/**
	 * Construct an asynchronous repeating command that executes the specified
	 * command.
	 * Use the specified thread factory to construct the synchronization thread(s).
	 * Assign the synchronization thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousRepeatingCommandWrapper(Command command, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super();
		this.consumerThreadCoordinator = new ConsumerThreadCoordinator(this.buildConsumer(command), threadFactory, threadName, exceptionHandler);
	}

	ConsumerThreadCoordinator.Consumer buildConsumer(Command command) {
		return new Consumer(command);
	}


	// ********** RepeatingCommand implementation **********

	/**
	 * Build and start the execution thread, but postpone the first
	 * execution until requested, i.e. via a call to {@link #execute()}.
	 * <p>
	 * Note: We don't clear the "execute" flag here; so if the flag has
	 * been set to <code>true</code> <em>before</em> getting here, the first
	 * execution will start promptly (albeit, asynchronously).
	 * The "execute" flag will be set if:<ul>
	 * <li>{@link #execute()} was called after the repeating command was
	 *     constructed but before {@link #start()} was called; or
	 * <li>{@link #execute()} was called after {@link #stop()} was called
	 *     but before {@link #start()} was called (to restart the repeating
	 *     command); or
	 * <li>{@link #stop()} was called when there was an outstanding request
	 *     for an execution (i.e. the "execute" flag was <code>true</code> at
	 *     the time {@link #stop()} was called)
	 * </ul>
	 * 
	 * @exception IllegalStateException if the command has already been started
	 */
	public void start() {
		this.consumerThreadCoordinator.start();
	}

	/**
	 * Set the "execute" flag so the execution thread will either<ul>
	 * <li>if the thread is quiesced, start an execution immediately, or
	 * <li>if the thread is currently executing, execute again,
	 *     once the current execution is complete
	 * </ul>
	 */
	public void execute() {
		synchronized (this.executeFlag) {
			if (DEBUG) {
				if (this.executeFlag.isFalse()) {
					this.stackTraces.clear();
				}
				this.stackTraces.add(new StackTrace());
			}
			this.executeFlag.setTrue();
		}
	}

	/**
	 * Interrupt the execution thread so that it stops executing at the
	 * end of the current execution. Suspend the current thread until
	 * the execution thread is finished executing. If any uncaught
	 * exceptions were thrown while the execution thread was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 * 
	 * @exception IllegalStateException if the coordinator has not been started
	 */
	public void stop() throws InterruptedException {
		this.consumerThreadCoordinator.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.consumerThreadCoordinator);
	}


	// ********** consumer **********

	/**
	 * This implementation of
	 * {@link org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator.Consumer}
	 * will execute the client-supplied command.
	 * It will wait until the shared "execute" flag is set to execute the
	 * command. Once the comand is executed, the thread will quiesce until
	 * the flag is set again. If the flag was set during the execution of the
	 * command (either recursively by the command itself or by another thread),
	 * the command will be re-executed immediately. Stop the thread by calling
	 * {@link Thread#interrupt()}.
	 */
	class Consumer
		implements ConsumerThreadCoordinator.Consumer
	{
		/**
		 * The client-supplied command that executes on the
		 * execution/consumer thread.
		 */
		private final Command command;

		Consumer(Command command) {
			super();
			if (command == null) {
				throw new NullPointerException();
			}
			this.command = command;
		}

		/**
		 * Wait until the "execute" flag is set,
		 * then clear it and allow the command to execute.
		 */
		public void waitForProducer() throws InterruptedException {
			AsynchronousRepeatingCommandWrapper.this.executeFlag.waitToSetFalse();
		}

		/**
		 * Execute the client-supplied command.
		 */
		public void consume() {
			this.command.execute();
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.command);
		}
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AbstractAsynchronousCommandExecutor}.
	 */
	public interface Config {
		Command getCommand();
		ThreadFactory getThreadFactory();
		String getThreadName();
		ExceptionHandler getExceptionHandler();
	}

	/**
	 * Config useful for instantiating an {@link AbstractAsynchronousCommandExecutor}.
	 */
	protected abstract static class SimpleConfig
		implements Config
	{
		private volatile Command command;
		private volatile ThreadFactory threadFactory;
		private volatile String threadName;
		private volatile ExceptionHandler exceptionHandler;

		protected SimpleConfig() {
			super();
			this.command = this.buildDefaultCommand();
			this.threadFactory = this.buildDefaultThreadFactory();
			this.threadName = this.buildDefaultThreadName();
			this.exceptionHandler = this.buildDefaultExceptionHandler();
		}

		protected SimpleConfig(Command command, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super();
			this.command = command;
			this.threadFactory = threadFactory;
			this.threadName = threadName;
			this.exceptionHandler = exceptionHandler;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
		public Command getCommand() {
			return this.command;
		}
		protected abstract Command buildDefaultCommand();

		public void setThreadFactory(ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
		}
		public ThreadFactory getThreadFactory() {
			return this.threadFactory;
		}
		protected ThreadFactory buildDefaultThreadFactory() {
			return SimpleThreadFactory.instance();
		}

		public void setThreadName(String threadName) {
			this.threadName = threadName;
		}
		public String getThreadName() {
			return this.threadName;
		}
		protected String buildDefaultThreadName() {
			return null;
		}

		public void setExceptionHandler(ExceptionHandler exceptionHandler) {
			this.exceptionHandler = exceptionHandler;
		}
		public ExceptionHandler getExceptionHandler() {
			return this.exceptionHandler;
		}
		protected ExceptionHandler buildDefaultExceptionHandler() {
			return RuntimeExceptionHandler.instance();
		}
	}
}
