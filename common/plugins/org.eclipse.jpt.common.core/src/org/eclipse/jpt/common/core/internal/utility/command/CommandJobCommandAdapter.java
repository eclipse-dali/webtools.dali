/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
 * The adapter can also be configured to return a particular
 * {@link IStatus status}, by default {@link Status#OK}.
 * Necessarily, the progress monitor is checked only once, just
 * before the {@link Command} is {@link Command#execute() executed}.
 */
public class CommandJobCommandAdapter
	implements JobCommand
{
	private final Command command;
	private final IStatus status;


	public CommandJobCommandAdapter(Command command) {
		this(command, Status.OK_STATUS);
	}

	public CommandJobCommandAdapter(Command command, IStatus status) {
		super();
		if ((command == null) || (status == null)) {
			throw new NullPointerException();
		}
		this.command = command;
		this.status = status;
	}

	public IStatus execute(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		this.command.execute();
		return this.status;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
