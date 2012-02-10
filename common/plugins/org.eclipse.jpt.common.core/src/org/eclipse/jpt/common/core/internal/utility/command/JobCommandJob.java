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
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;

/**
 * A job that executes a {@link JobCommand job command} or
 * {@link Command command}.
 */
public class JobCommandJob
	extends Job
{
	private final JobCommand command;


	public JobCommandJob(String name, Command command) {
		this(name, new CommandJobCommandAdapter(command));
	}

	public JobCommandJob(String name, JobCommand command) {
		super(name);
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return this.command.execute(monitor);
	}
}
