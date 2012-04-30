/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility;

import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.internal.command.CommandRunnable;
import org.eclipse.jpt.common.utility.internal.command.SynchronizingCommand;

/**
 * Gather the tiny bit of common behavior.
 */
abstract class AbstractUiCommandExecutor
	implements ExtendedCommandExecutor
{
	AbstractUiCommandExecutor() {
		super();
	}

	Runnable buildRunnable(Command command) {
		return new CommandRunnable(command);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * A common way to put a time constraint on a command dispatched
	 * to the UI thread.
	 */
	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitToExecute(command);
			return true;
		}

		// configure the sync command to execute the client command
		SynchronizingCommand syncCommand = new SynchronizingCommand(command);

		// dispatch the sync command to the UI thread...
		SWTUtil.asyncExec(this.buildRunnable(syncCommand));

		// ...and wait for it to execute
		try {
			return syncCommand.waitForExecution(timeout);
		} finally {
			syncCommand.release();
		}
	}
}