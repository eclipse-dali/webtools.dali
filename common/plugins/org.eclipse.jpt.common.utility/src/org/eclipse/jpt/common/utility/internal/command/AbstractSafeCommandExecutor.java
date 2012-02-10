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
import org.eclipse.jpt.common.utility.command.CommandExecutor;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * This command executor wraps another command executor and uses an exception
 * handler to handle any exceptions thrown by an executing command.
 */
public abstract class AbstractSafeCommandExecutor<E extends CommandExecutor>
	implements CommandExecutor
{
	protected final E commandExecutor;
	protected final ExceptionHandler exceptionHandler;


	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	protected AbstractSafeCommandExecutor(E commandExecutor) {
		this(commandExecutor, ExceptionHandler.Null.instance());
	}

	protected AbstractSafeCommandExecutor(E commandExecutor, ExceptionHandler exceptionHandler) {
		super();
		if ((commandExecutor == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.commandExecutor = commandExecutor;
		this.exceptionHandler = exceptionHandler;
	}

	public void execute(Command command) {
		try {
			this.commandExecutor.execute(command);
		} catch (RuntimeException ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.commandExecutor);
	}
}
