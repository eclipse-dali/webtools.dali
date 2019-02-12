/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Command wrapper that can have its wrapped command changed,
 * allowing a client to change a previously-supplied command's
 * behavior mid-stream.
 * 
 * @see #setCommand(InterruptibleCommand)
 */
public class InterruptibleCommandWrapper
	implements InterruptibleCommand
{
	protected volatile InterruptibleCommand command;

	public InterruptibleCommandWrapper(InterruptibleCommand command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public void execute() throws InterruptedException {
		this.command.execute();
	}

	public void setCommand(InterruptibleCommand command) {
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
