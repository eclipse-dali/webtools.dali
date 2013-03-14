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

import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * @see AbstractThreadLocalCommandContext
 */
public class ThreadLocalCommandExecutor
	extends AbstractThreadLocalCommandContext<CommandContext>
{
	/**
	 * The default command executor simply executes commands directly.
	 */
	public ThreadLocalCommandExecutor() {
		this(DefaultCommandContext.instance());
	}

	public ThreadLocalCommandExecutor(CommandContext defaultCommandExecutor) {
		super(defaultCommandExecutor);
	}
}
