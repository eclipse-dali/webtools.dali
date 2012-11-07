/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.utility.command.InterruptibleJobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Command wrapper that can have its wrapped command changed,
 * allowing a client to change a previously-supplied command's
 * behavior mid-stream.
 * 
 * @see #setCommand(InterruptibleJobCommand)
 */
public class InterruptibleJobCommandWrapper
	implements InterruptibleJobCommand
{
	protected volatile InterruptibleJobCommand command;

	public InterruptibleJobCommandWrapper(InterruptibleJobCommand command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public IStatus execute(IProgressMonitor monitor) throws InterruptedException {
		return this.command.execute(monitor);
	}

	public void setCommand(InterruptibleJobCommand command) {
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
