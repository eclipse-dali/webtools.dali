/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;

/**
 * Wrap a {@link Command} so it can be used as a {@link Runnable}.
 */
public class CommandRunnable
	implements Runnable
{
	protected final Command command;

	public CommandRunnable(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void run() {
		this.command.execute();
	}

	@Override
	public String toString() {
		return "Runnable[" + this.command +']'; //$NON-NLS-1$
	}
}
