/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.command.Command;

/**
 * Command wrapper that will handle any exceptions thrown by the wrapped
 * command with an {@link ExceptionHandler exception handler}.
 */
public class SafeCommandWrapper
	implements Command
{
	private final Command command;
	private final ExceptionHandler exceptionHandler;


	public SafeCommandWrapper(Command command, ExceptionHandler exceptionHandler) {
		super();
		if ((command == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.command = command;
		this.exceptionHandler = exceptionHandler;
	}

	public void execute() {
		try {
			this.command.execute();
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
