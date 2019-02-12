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

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandContext;

/**
 * Straightforward implementation of {@link StatefulExtendedCommandContext}
 * that executes commands immediately by default. This context can
 * also be used to adapt simple
 * {@link org.eclipse.jpt.common.utility.command.CommandContext command contexts}
 * to the
 * {@link StatefulExtendedCommandContext} interface, providing support for
 * lifecycle state. Any calls to {@link #waitToExecute(Command)} suspend the
 * calling thread until the command context is {@link #start() started}.
 */
public class SimpleStatefulExtendedCommandContext
	extends AbstractStatefulCommandContext<ExtendedCommandContext>
	implements StatefulExtendedCommandContext
{
	public SimpleStatefulExtendedCommandContext() {
		this(DefaultExtendedCommandContext.instance());
	}

	public SimpleStatefulExtendedCommandContext(ExtendedCommandContext commandContext) {
		super(commandContext);
	}

	/**
	 * Suspend the current thread until the command context is
	 * {@link #start() started}.
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		this.active.waitUntilTrue();
		this.commandContext.waitToExecute(command);
	}

	/**
	 * Suspend the current thread until the command context is
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
			return (timeout > 0) && this.commandContext.waitToExecute(command, timeout);
		}
		return false;
	}
}
