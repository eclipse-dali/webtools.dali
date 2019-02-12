/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility;

import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.command.CommandRunnable;
import org.eclipse.jpt.common.utility.internal.command.SynchronizingCommand;

/**
 * Gather the tiny bit of common behavior.
 */
abstract class AbstractUiCommandContext
	implements ExtendedCommandContext
{
	AbstractUiCommandContext() {
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
		DisplayTools.asyncExec(this.buildRunnable(syncCommand));

		// ...and wait for it to execute
		try {
			return syncCommand.waitForExecution(timeout);
		} finally {
			syncCommand.release();
		}
	}
}
