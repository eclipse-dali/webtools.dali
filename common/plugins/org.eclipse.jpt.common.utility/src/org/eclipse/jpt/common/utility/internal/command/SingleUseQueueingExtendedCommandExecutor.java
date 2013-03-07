/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
 * until the command executor is {@link #start() started} and any previously-
 * dispatched commands have executed.
 * <p>
 * <strong>NB:</strong> Calls to {@link #waitToExecute(Command)} will suspend
 * the current thread <em>indefinitely</em> if the command executor is
 * {@link #stop() stopped}.
 * 
 * @see AbstractSingleUseQueueingCommandExecutor
 */
public class SingleUseQueueingExtendedCommandExecutor
	extends AbstractSingleUseQueueingCommandExecutor<StatefulExtendedCommandContext>
	implements StatefulExtendedCommandContext
{
	public SingleUseQueueingExtendedCommandExecutor() {
		this(ExtendedCommandContext.Default.instance());
	}

	public SingleUseQueueingExtendedCommandExecutor(ExtendedCommandContext commandExecutor) {
		this(new SimpleStatefulExtendedCommandExecutor(commandExecutor));
	}

	public SingleUseQueueingExtendedCommandExecutor(StatefulExtendedCommandContext commandExecutor) {
		super(commandExecutor);
	}

	public void waitToExecute(Command command) throws InterruptedException {
		SynchronizingCommand syncCommand = new SynchronizingCommand();

		this.execute(syncCommand);  // put the sync command on the queue and wait until it has executed

		try {
			syncCommand.waitForExecution();
			this.commandExecutor.waitToExecute(command);
		} finally {
			syncCommand.release();
		}
	}

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
}
