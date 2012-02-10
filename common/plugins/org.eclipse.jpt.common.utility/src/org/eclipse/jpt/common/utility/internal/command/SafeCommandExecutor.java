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
import org.eclipse.jpt.common.utility.command.CommandExecutor;

/**
 * @see AbstractSafeCommandExecutor
 */
public class SafeCommandExecutor
	extends AbstractSafeCommandExecutor<CommandExecutor>
{
	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandExecutor() {
		this(CommandExecutor.Default.instance());
	}

	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeCommandExecutor(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	public SafeCommandExecutor(ExceptionHandler exceptionHandler) {
		this(CommandExecutor.Default.instance(), exceptionHandler);
	}

	public SafeCommandExecutor(CommandExecutor commandExecutor, ExceptionHandler exceptionHandler) {
		super(commandExecutor, exceptionHandler);
	}
}
