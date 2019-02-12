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

import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;

/**
 * @see AbstractSafeCommandContext
 */
public class SafeCommandContext
	extends AbstractSafeCommandContext<CommandContext>
{
	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandContext() {
		this(DefaultCommandContext.instance());
	}

	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandContext(CommandContext commandContext) {
		super(commandContext);
	}

	public SafeCommandContext(ExceptionHandler exceptionHandler) {
		this(DefaultCommandContext.instance(), exceptionHandler);
	}

	public SafeCommandContext(CommandContext commandContext, ExceptionHandler exceptionHandler) {
		super(commandContext, exceptionHandler);
	}
}
