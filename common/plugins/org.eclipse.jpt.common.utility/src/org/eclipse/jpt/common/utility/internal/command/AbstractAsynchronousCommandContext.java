/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.util.concurrent.ThreadFactory;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;
import org.eclipse.jpt.common.utility.internal.collection.SynchronizedQueue;

/**
 * This command context will dispatch commands to be executed in a separate
 * thread, allowing calls to
 * {@link org.eclipse.jpt.common.utility.command.CommandContext#execute(Command)}
 * to return immediately.
 * <p>
 * <strong>NB:</strong> If a client-supplied command throws a runtime exception
 * while it is executing, the command context will use its
 * {@link ExceptionHandler exception handler} to handle the exception.
 */
public abstract class AbstractAsynchronousCommandContext<E extends StatefulCommandContext>
	implements StatefulCommandContext
{
	/**
	 * The wrapped command context.
	 */
	protected final E commandContext;

	/**
	 * This command queue is shared with the command execution/consumer thread.
	 * Adding a command to it will trigger the command to be executed by the
	 * command execution thread or, if another command is currently executing,
	 * to execute the new command once the currently executing command has
	 * finished executing.
	 */
	private final SynchronizedQueue<Command> commands = new SynchronizedQueue<Command>();

	/**
	 * Most of the thread-related behavior is delegated to this coordinator.
	 */
	private final ConsumerThreadCoordinator consumerThreadCoordinator;


	// ********** construction **********

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	protected AbstractAsynchronousCommandContext(Config<E> config) {
		this(config.getCommandContext(), config.getThreadFactory(), config.getThreadName(), config.getExceptionHandler());
	}

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	protected AbstractAsynchronousCommandContext(
			E commandContext,
			ThreadFactory threadFactory,
			String threadName,
			ExceptionHandler exceptionHandler
	) {
		super();
		if (commandContext == null) {
			throw new NullPointerException();
		}
		this.commandContext = commandContext;
		this.consumerThreadCoordinator = this.buildConsumerThreadCoordinator(threadFactory, threadName, exceptionHandler);
	}

	private ConsumerThreadCoordinator buildConsumerThreadCoordinator(ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		return new ConsumerThreadCoordinator(this.buildConsumer(), threadFactory, threadName, exceptionHandler);
	}

	private ConsumerThreadCoordinator.Consumer buildConsumer() {
		return new Consumer();
	}


	// ********** StatefulCommandContext implementation **********

	/**
	 * Build and start the command execution/consumer thread.
	 * <p>
	 * Note: We don't clear the command queue here; so if a command has been
	 * added to the queue <em>before</em> getting here, the first command will
	 * be executed promptly (albeit, asynchronously).
	 * The command queue will be non-empty if:<ul>
	 * <li>{@link #execute(Command)} was called after the command context was
	 *     constructed but before {@link #start()} was called; or
	 * <li>{@link #execute(Command)} was called after {@link #stop()} was called
	 *     but before {@link #start()} was called (to restart the command context); or
	 * <li>{@link #stop()} was called when there were still outstanding commands
	 *     remaining in the command queue
	 * </ul>
	 * 
	 * @exception IllegalStateException if the context has already been started
	 */
	public synchronized void start() {
		this.commandContext.start();
		this.consumerThreadCoordinator.start();
	}

	/**
	 * Put the specified command on the command queue, to be consumed by the
	 * command execution thread. If the context is stopped, the command will
	 * be queued and executed once the context is, if ever, started.
	 */
	public synchronized void execute(Command command) {
		this.commands.enqueue(command);
	}

	/**
	 * Interrupt the command execution thread so that it stops executing at the
	 * end of the current command. Suspend the current thread until
	 * the command execution thread is finished executing. If any uncaught
	 * exceptions were thrown while the execution thread was executing,
	 * wrap them in a composite exception and throw the composite exception.
	 * Any remaining commands will be executed once the exector is, if ever,
	 * restarted.
	 * 
	 * @exception IllegalStateException if the context has not been started
	 */
	public synchronized void stop() throws InterruptedException {
		this.consumerThreadCoordinator.stop();
		this.commandContext.stop();
	}


	// ********** misc **********

	/* CU private */ void waitForCommand() throws InterruptedException {
		this.commands.waitUntilNotEmpty();
	}

	/* CU private */ void executeNextCommand() {
		this.commandContext.execute(this.commands.dequeue());
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commands);
	}


	// ********** consumer **********

	/**
	 * This implementation of
	 * {@link org.eclipse.jpt.common.utility.internal.ConsumerThreadCoordinator.Consumer}
	 * will execute the commands enqueued by the asynchronous command context.
	 * It will wait until the shared command queue is non-empty to begin executing the
	 * commands in the queue. Once a comand is executed, the thread will quiesce until
	 * another command is placed in the command queue. If a new command is
	 * enqueued during the execution of another command (either recursively by
	 * the command itself or by another thread),
	 * the new command will be executed immediately after the currently
	 * executing command is finished.
	 * Stop the thread by calling {@link Thread#interrupt()}.
	 */
	class Consumer
		implements ConsumerThreadCoordinator.Consumer
	{
		/**
		 * Wait until a command has been placed in the queue.
		 */
		public void waitForProducer() throws InterruptedException {
			AbstractAsynchronousCommandContext.this.waitForCommand();
		}

		/**
		 * Execute the first command in the queue.
		 */
		public void consume() {
			AbstractAsynchronousCommandContext.this.executeNextCommand();
		}
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AbstractAsynchronousCommandContext}.
	 */
	public interface Config<E extends StatefulCommandContext> {
		E getCommandContext();
		ThreadFactory getThreadFactory();
		String getThreadName();
		ExceptionHandler getExceptionHandler();
	}

	/**
	 * Config useful for instantiating an {@link AbstractAsynchronousCommandContext}.
	 */
	protected abstract static class SimpleConfig<E extends StatefulCommandContext>
		implements Config<E>
	{
		private volatile E commandContext;
		private volatile ThreadFactory threadFactory;
		private volatile String threadName;
		private volatile ExceptionHandler exceptionHandler;

		protected SimpleConfig() {
			super();
			this.commandContext = this.buildDefaultCommandContext();
			this.threadFactory = this.buildDefaultThreadFactory();
			this.threadName = this.buildDefaultThreadName();
			this.exceptionHandler = this.buildDefaultExceptionHandler();
		}

		protected SimpleConfig(E commandContext, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super();
			this.commandContext = commandContext;
			this.threadFactory = threadFactory;
			this.threadName = threadName;
			this.exceptionHandler = exceptionHandler;
		}

		public void setCommandContext(E commandContext) {
			this.commandContext = commandContext;
		}
		public E getCommandContext() {
			return this.commandContext;
		}
		protected abstract E buildDefaultCommandContext();

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
