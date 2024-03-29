/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;

/**
 * Interruptible command wrapper that will handle any exceptions thrown by the wrapped
 * command with an {@link ExceptionHandler exception handler}.
 */
public class SafeInterruptibleCommandWrapper
	implements InterruptibleCommand
{
	private final InterruptibleCommand command;
	private final ExceptionHandler exceptionHandler;


	public SafeInterruptibleCommandWrapper(InterruptibleCommand command, ExceptionHandler exceptionHandler) {
		super();
		if ((command == null) || (exceptionHandler == null)) {
			throw new NullPointerException();
		}
		this.command = command;
		this.exceptionHandler = exceptionHandler;
	}

	public void execute() throws InterruptedException {
		try {
			this.command.execute();
		} catch (InterruptedException ex) {
			throw ex;
		} catch (Throwable ex) {
			this.exceptionHandler.handleException(ex);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
