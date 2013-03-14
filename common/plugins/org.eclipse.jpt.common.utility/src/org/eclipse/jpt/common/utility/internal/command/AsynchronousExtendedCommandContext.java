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
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;

/**
 * {@link Command}s executed via calls to {@link #waitToExecute(Command)} will
 * execute on the <em>current</em> thread <em>after</em> all the commands
 * already dispatched to the other thread have executed.
 * 
 * @see AbstractAsynchronousCommandContext
 */
public class AsynchronousExtendedCommandContext
	extends AbstractAsynchronousCommandContext<StatefulExtendedCommandContext>
	implements StatefulExtendedCommandContext
{
	/**
	 * Construct an asynchronous command context.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Allow the command execution thread(s) to be assigned JDK-generated names.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandContext(ExceptionHandler exceptionHandler) {
		this(null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Assign the command execution thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandContext(String threadName, ExceptionHandler exceptionHandler) {
		this(new SimpleStatefulExtendedCommandExecutor(DefaultExtendedCommandContext.instance()), SimpleThreadFactory.instance(), threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandContext(StatefulExtendedCommandContext commandContext, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super(commandContext, threadFactory, threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandContext(Config config) {
		super(config);
	}


	/**
	 * Wait until all the commands currently on the queue have been executed,
	 * then execute the specified command. Suspend the command-executing thread
	 * while we execute the specified command.
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		SynchronizingCommand syncCommand = new SynchronizingCommand();

		this.execute(syncCommand);  // dispatch the sync command to the other thread

		try {
			syncCommand.waitForExecution();
			this.commandContext.waitToExecute(command);
		} finally {
			syncCommand.release();
		}
	}

	/**
	 * Wait until all the commands currently on the queue have been executed,
	 * then execute the specified command. Suspend the command-executing thread
	 * while we execute the specified command.
	 */
	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitToExecute(command);
			return true;
		}

		long stop = System.currentTimeMillis() + timeout;
		SynchronizingCommand syncCommand = new SynchronizingCommand();

		this.execute(syncCommand);  // dispatch the sync command to the other thread

		try {
			if (syncCommand.waitForExecution(timeout)) {
				// adjust the time
				timeout = stop - System.currentTimeMillis();
				return (timeout > 0) && this.commandContext.waitToExecute(command, timeout);
			}
			return false;
		} finally {
			syncCommand.release();
		}
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AsynchronousExtendedCommandContext}.
	 */
	public interface Config
		extends AbstractAsynchronousCommandContext.Config<StatefulExtendedCommandContext>
	{
		// generic
	}

	/**
	 * Config useful for instantiating an {@link AsynchronousExtendedCommandContext}.
	 */
	public static class SimpleConfig
		extends AbstractAsynchronousCommandContext.SimpleConfig<StatefulExtendedCommandContext>
		implements Config
	{
		public SimpleConfig() {
			super();
		}
		public SimpleConfig(StatefulExtendedCommandContext commandContext, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super(commandContext, threadFactory, threadName, exceptionHandler);
		}
		@Override
		protected StatefulExtendedCommandContext buildDefaultCommandContext() {
			return new SimpleStatefulExtendedCommandExecutor();
		}
	}
}
