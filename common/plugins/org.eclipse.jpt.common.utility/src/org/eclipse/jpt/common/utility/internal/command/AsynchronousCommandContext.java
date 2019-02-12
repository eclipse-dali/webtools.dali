/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import java.util.concurrent.ThreadFactory;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.SimpleThreadFactory;

/**
 * @see AbstractAsynchronousCommandContext
 */
public class AsynchronousCommandContext
	extends AbstractAsynchronousCommandContext<StatefulCommandContext>
{
	/**
	 * Construct an asynchronous command context.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Allow the command execution thread(s) to be assigned JDK-generated names.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandContext(ExceptionHandler exceptionHandler) {
		this(null, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Use simple JDK thread(s) for the command execution thread(s).
	 * Assign the command execution thread(s) the specified name.
	 * Any exceptions thrown by the consumer will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandContext(String threadName, ExceptionHandler exceptionHandler) {
		this(new SimpleStatefulCommandContext(DefaultCommandContext.instance()), SimpleThreadFactory.instance(), threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by a command will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandContext(StatefulCommandContext commandContext, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
		super(commandContext, threadFactory, threadName, exceptionHandler);
	}

	/**
	 * Construct an asynchronous command context.
	 * Delegate command execution to the specified command context.
	 * Use the specified thread factory to construct the command execution
	 * thread(s) and assign them the specified name.
	 * Any exceptions thrown by a command will be handled by the
	 * specified exception handler.
	 */
	public AsynchronousCommandContext(Config config) {
		super(config);
	}


	// ********** config **********

	/**
	 * Config useful for instantiating an {@link AsynchronousCommandContext}.
	 */
	public interface Config
		extends AbstractAsynchronousCommandContext.Config<StatefulCommandContext>
	{
		// generic
	}

	/**
	 * Config useful for instantiating an {@link AsynchronousCommandContext}.
	 */
	public static class SimpleConfig
		extends AbstractAsynchronousCommandContext.SimpleConfig<StatefulCommandContext>
		implements Config
	{
		public SimpleConfig() {
			super();
		}
		public SimpleConfig(StatefulCommandContext commandContext, ThreadFactory threadFactory, String threadName, ExceptionHandler exceptionHandler) {
			super(commandContext, threadFactory, threadName, exceptionHandler);
		}
		@Override
		protected StatefulCommandContext buildDefaultCommandContext() {
			return new SimpleStatefulCommandContext();
		}
	}
}
