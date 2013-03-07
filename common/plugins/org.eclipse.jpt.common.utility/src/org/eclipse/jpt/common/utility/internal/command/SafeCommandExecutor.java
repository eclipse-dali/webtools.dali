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

import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * @see AbstractSafeCommandExecutor
 */
public class SafeCommandExecutor
	extends AbstractSafeCommandExecutor<CommandContext>
{
	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandExecutor() {
		this(CommandContext.Default.instance());
	}

	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandExecutor(CommandContext commandExecutor) {
		super(commandExecutor);
	}

	public SafeCommandExecutor(ExceptionHandler exceptionHandler) {
		this(CommandContext.Default.instance(), exceptionHandler);
	}

	public SafeCommandExecutor(CommandContext commandExecutor, ExceptionHandler exceptionHandler) {
		super(commandExecutor, exceptionHandler);
	}
}
