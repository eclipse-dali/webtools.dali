/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandContext;

/**
 * Calls to {@link #waitToExecute(Command)} will suspend the current thread
 * until the command context is {@link #start() started} and any previously-
 * dispatched commands have executed.
 * 
 * @see AbstractQueueingCommandContext
 */
public class QueueingExtendedCommandContext
	extends AbstractQueueingCommandContext<StatefulExtendedCommandContext>
	implements StatefulExtendedCommandContext
{
	public QueueingExtendedCommandContext() {
		this(DefaultExtendedCommandContext.instance());
	}

	public QueueingExtendedCommandContext(ExtendedCommandContext commandContext) {
		this(new SimpleStatefulExtendedCommandContext(commandContext));
	}

	public QueueingExtendedCommandContext(StatefulExtendedCommandContext commandContext) {
		super(commandContext);
	}

	/**
	 * Suspend the current thread until the command context is
	 * {@link #start() started}.
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		SynchronizingCommand syncCommand = new SynchronizingCommand();

		this.execute(syncCommand);  // put the sync command on the queue and wait until it has executed

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
}
