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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Adapt a {@link JobCommand} to the {@link Command} interface.
 * Necessarily passing in a <em>null</em> progress monitor
 * and ignoring the returned status.
 */
public class JobCommandCommandAdapter
	implements Command
{
	private final JobCommand jobCommand;


	public JobCommandCommandAdapter(JobCommand jobCommand) {
		super();
		if (jobCommand == null) {
			throw new NullPointerException();
		}
		this.jobCommand = jobCommand;
	}

	public void execute() {
		this.jobCommand.execute(new NullProgressMonitor());
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.jobCommand);
	}
}
