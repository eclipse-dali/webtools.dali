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
 * @see AbstractThreadLocalCommandContext
 */
public class ThreadLocalExtendedCommandContext
	extends AbstractThreadLocalCommandContext<ExtendedCommandContext>
	implements ExtendedCommandContext
{
	/**
	 * The default command executor simply executes commands directly.
	 */
	public ThreadLocalExtendedCommandContext() {
		this(DefaultExtendedCommandContext.instance());
	}

	public ThreadLocalExtendedCommandContext(ExtendedCommandContext defaultCommandContext) {
		super(defaultCommandContext);
	}

	public void waitToExecute(Command command) throws InterruptedException {
		this.getThreadLocalCommandContext().waitToExecute(command);
	}

	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		return this.getThreadLocalCommandContext().waitToExecute(command, timeout);
	}
}
