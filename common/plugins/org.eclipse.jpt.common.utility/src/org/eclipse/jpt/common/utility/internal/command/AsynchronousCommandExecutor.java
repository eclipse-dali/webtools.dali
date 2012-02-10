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
import org.eclipse.jpt.common.utility.command.CommandExecutor;
import org.eclipse.jpt.common.utility.command.StatefulCommandExecutor;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;

/**
 * @see AbstractAsynchronousCommandExecutor
 */
public class AsynchronousCommandExecutor
	extends AbstractAsynchronousCommandExecutor<StatefulCommandExecutor>
{
	/**
	 * Construct an asynchronous command executor.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Allow the command execution thread(s) to be assigned JDK-generated names.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandExecutor(ExceptionHandler exceptionHandler) {
		this(null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Assign the command execution thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandExecutor(String threadName, ExceptionHandler exceptionHandler) {
		this(new SimpleStatefulCommandExecutor(CommandExecutor.Default.instance()), SimpleThreadFactory.instance(), threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Delegate command execution to the specified command executor.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by a command will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandExecutor(StatefulCommandExecutor commandExecutor, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super(commandExecutor, threadFactory, threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command executor.
	 * Delegate command execution to the specified command executor.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by a command will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandExecutor(Config config) {
		super(config);
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AsynchronousCommandExecutor}.
	 */
	public interface Config
		extends AbstractAsynchronousCommandExecutor.Config<StatefulCommandExecutor>
	{
		// generic
	}

	/**
	 * Config useful for instantiating an {@link AsynchronousCommandExecutor}.
	 */
	public static class SimpleConfig
		extends AbstractAsynchronousCommandExecutor.SimpleConfig<StatefulCommandExecutor>
		implements Config
	{
		public SimpleConfig() {
			super();
		}
		public SimpleConfig(StatefulCommandExecutor commandExecutor, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super(commandExecutor, threadFactory, threadName, exceptionHandler);
		}
		@Override
		protected StatefulCommandExecutor buildDefaultCommandExecutor() {
			return new SimpleStatefulCommandExecutor();
		}
	}
}
