/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;

/**
 * Wrap a {@link Runnable} so it can be used as a {@link Command}.
 */
public class RunnableCommand
	implements Command
{
	protected final Runnable runnable;

	public RunnableCommand(Runnable runnable) {
		super();
		if (runnable == null) {
			throw new NullPointerException();
		}
		this.runnable = runnable;
	}

	public void execute() {
		this.runnable.run();
	}

	@Override
	public String toString() {
		return "Command[" + this.runnable +']'; //$NON-NLS-1$
	}
}
