/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.NullExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This command context wraps another command context and uses an exception
 * handler to handle any exceptions thrown by an executing command.
 */
public abstract class AbstractSafeCommandContext<E extends CommandContext>
	implements CommandContext
{
	protected final E commandContext;
	protected final ExceptionHandler exceptionHandler;


	/**
	 * <strong>NB:</strong> The default exception handler simply
	 * <em>ignores</em> any and all exceptions.
	 */
	protected AbstractSafeCommandContext(E commandContext) {
		this(commandContext, NullExceptionHandler.instance());
	}

	protected AbstractSafeCommandContext(E commandContext, ExceptionHandler exceptionHandler) {
		super();
		if ((commandContext == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.commandContext = commandContext;
		this.exceptionHandler = exceptionHandler;
	}

	public void execute(Command command) {
		try {
			this.commandContext.execute(command);
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commandContext);
	}
}
