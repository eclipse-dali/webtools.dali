/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * @see AbstractThreadLocalCommandContext
 */
public class ThreadLocalCommandContext
	extends AbstractThreadLocalCommandContext<CommandContext>
{
	/**
	 * The default command context simply executes commands directly.
	 */
	public ThreadLocalCommandContext() {
		this(DefaultCommandContext.instance());
	}

	public ThreadLocalCommandContext(CommandContext defaultCommandContext) {
		super(defaultCommandContext);
	}
}
