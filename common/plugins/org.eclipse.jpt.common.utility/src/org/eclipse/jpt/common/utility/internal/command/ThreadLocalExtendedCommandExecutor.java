/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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

/**
 * @see AbstractThreadLocalCommandExecutor
 */
public class ThreadLocalExtendedCommandExecutor
	extends AbstractThreadLocalCommandExecutor<ExtendedCommandContext>
	implements ExtendedCommandContext
{
	/**
	 * The default command executor simply executes commands directly.
	 */
	public ThreadLocalExtendedCommandExecutor() {
		this(DefaultExtendedCommandContext.instance());
	}

	public ThreadLocalExtendedCommandExecutor(ExtendedCommandContext defaultCommandExecutor) {
		super(defaultCommandExecutor);
	}

	public void waitToExecute(Command command) throws InterruptedException {
		this.getThreadLocalCommandExecutor().waitToExecute(command);
	}

	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		return this.getThreadLocalCommandExecutor().waitToExecute(command, timeout);
	}
}
