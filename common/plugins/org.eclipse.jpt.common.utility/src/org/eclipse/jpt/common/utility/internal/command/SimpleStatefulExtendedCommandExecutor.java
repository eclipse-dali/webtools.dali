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

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.CommandExecutor;
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandExecutor;

/**
 * Straightforward implementation of {@link StatefulExtendedCommandExecutor}
 * that executes commands immediately by default. This executor can
 * also be used to adapt simple {@link CommandExecutor}s to the
 * {@link StatefulExtendedCommandExecutor} interface, providing support for
 * lifecycle state. Any calls to {@link #waitToExecute(Command)} suspend the
 * calling thread until the command executor is {@link #start() started}.
 */
public class SimpleStatefulExtendedCommandExecutor
	extends AbstractStatefulCommandExecutor<ExtendedCommandExecutor>
	implements StatefulExtendedCommandExecutor
{
	public SimpleStatefulExtendedCommandExecutor() {
		this(ExtendedCommandExecutor.Default.instance());
	}

	public SimpleStatefulExtendedCommandExecutor(ExtendedCommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	/**
	 * Suspend the current thread until the command executor is
	 * {@link #start() started}.
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		this.active.waitUntilTrue();
		this.commandExecutor.waitToExecute(command);
	}

	/**
	 * Suspend the current thread until the command executor is
	 * {@link #start() started}.
	 */
	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		if (timeout == 0L) {
			this.waitToExecute(command);
			return true;
		}

		long stop = System.currentTimeMillis() + timeout;
		if (this.active.waitUntilTrue(timeout)) {
			// adjust the time
			timeout = stop - System.currentTimeMillis();
			return (timeout > 0) && this.commandExecutor.waitToExecute(command, timeout);
		}
		return false;
	}
}
