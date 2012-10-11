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
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Command} to the {@link JobCommand} interface.
 * Necessarily, the progress monitor is ignored and a status of
 * OK is returned.
 */
public class CommandJobCommandAdapter
	implements JobCommand
{
	private final Command command;


	public CommandJobCommandAdapter(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public IStatus execute(IProgressMonitor monitor) {
		this.command.execute();
		return Status.OK_STATUS;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
