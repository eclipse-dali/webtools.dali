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
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;

/**
 * @see AbstractSafeCommandExecutor
 */
public class SafeExtendedCommandExecutor
	extends AbstractSafeCommandExecutor<ExtendedCommandContext>
	implements ExtendedCommandContext
{
	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeExtendedCommandExecutor() {
		this(ExtendedCommandContext.Default.instance());
	}

	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	public SafeExtendedCommandExecutor(ExtendedCommandContext commandExecutor) {
		super(commandExecutor);
	}

	public SafeExtendedCommandExecutor(ExceptionHandler exceptionHandler) {
		this(ExtendedCommandContext.Default.instance(), exceptionHandler);
	}

	public SafeExtendedCommandExecutor(ExtendedCommandContext commandExecutor, ExceptionHandler exceptionHandler) {
		super(commandExecutor, exceptionHandler);
	}

	public void waitToExecute(Command command) throws InterruptedException {
		try {
			this.commandExecutor.waitToExecute(command);
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		try {
			return this.commandExecutor.waitToExecute(command, timeout);
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
			return true;  // hmmm... seems like we get here only if the command executed
		}
	}
}
