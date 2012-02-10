/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;

/**
 * {@link Command}s executed via calls to {@link #waitToExecute(Command)} will
 * execute on the <em>current</em> thread <em>after</em> all the commands
 * already dispatched to the other thread have executed.
 * 
 * @see AbstractAsynchronousCommandExecutor
 */
public class AsynchronousExtendedCommandExecutor
	extends AbstractAsynchronousCommandExecutor<StatefulExtendedCommandExecutor>
	implements StatefulExtendedCommandExecutor
{
	/**
	 * Construct an asynchronous command executor.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Allow the command execution thread(s) to be assigned JDK-generated names.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandExecutor(ExceptionHandler exceptionHandler) {
		this(null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Assign the command execution thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandExecutor(String threadName, ExceptionHandler exceptionHandler) {
		this(new SimpleStatefulExtendedCommandExecutor(ExtendedCommandExecutor.Default.instance()), SimpleThreadFactory.instance(), threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Delegate command execution to the specified command executor.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandExecutor(StatefulExtendedCommandExecutor commandExecutor, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super(commandExecutor, threadFactory, threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Delegate command execution to the specified command executor.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousExtendedCommandExecutor(Config config) {
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
			this.commandExecutor.waitToExecute(command);
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
				return (timeout > 0) && this.commandExecutor.waitToExecute(command, timeout);
			}
			return false;
		} finally {
			syncCommand.release();
		}
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AsynchronousExtendedCommandExecutor}.
	 */
	public interface Config
		extends AbstractAsynchronousCommandExecutor.Config<StatefulExtendedCommandExecutor>
	{
		// generic
	}

	/**
	 * Config useful for instantiating an {@link AsynchronousExtendedCommandExecutor}.
	 */
	public static class SimpleConfig
		extends AbstractAsynchronousCommandExecutor.SimpleConfig<StatefulExtendedCommandExecutor>
		implements Config
	{
		public SimpleConfig() {
			super();
		}
		public SimpleConfig(StatefulExtendedCommandExecutor commandExecutor, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super(commandExecutor, threadFactory, threadName, exceptionHandler);
		}
		@Override
		protected StatefulExtendedCommandExecutor buildDefaultCommandExecutor() {
			return new SimpleStatefulExtendedCommandExecutor();
		}
	}
}
