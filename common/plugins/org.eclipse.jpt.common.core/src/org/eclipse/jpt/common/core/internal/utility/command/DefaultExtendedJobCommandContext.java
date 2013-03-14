/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import java.io.Serializable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.core.utility.command.ExtendedJobCommandContext;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the command context interface
 * that simply executes the command without any sort of enhancement.
 */
public final class DefaultExtendedJobCommandContext
	implements ExtendedJobCommandContext, Serializable
{
	public static final ExtendedJobCommandContext INSTANCE = new DefaultExtendedJobCommandContext();

	public static ExtendedJobCommandContext instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DefaultExtendedJobCommandContext() {
		super();
	}

	public void execute(JobCommand command) {
		command.execute(new NullProgressMonitor());
	}

	public void execute(JobCommand command, String jobName) {
		this.execute(command);
	}

	public void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
		this.execute(command);
	}

	public void waitToExecute(JobCommand command) {
		this.execute(command);
	}

	public boolean waitToExecute(JobCommand command, long timeout) {
		this.execute(command);
		return true;
	}

	public void waitToExecute(JobCommand command, String jobName) {
		this.execute(command);
	}

	public boolean waitToExecute(JobCommand command, String jobName, long timeout) {
		this.execute(command);
		return true;
	}

	public void waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
		this.execute(command);
	}

	public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) {
		this.execute(command);
		return true;
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
