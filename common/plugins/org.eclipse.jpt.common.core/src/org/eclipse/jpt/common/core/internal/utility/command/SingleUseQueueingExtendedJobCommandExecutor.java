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

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.core.utility.command.CombinedExtendedCommandContext;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.command.StatefulExtendedCommandContext;
import org.eclipse.jpt.common.utility.internal.command.SingleUseQueueingExtendedCommandExecutor;

/**
 * This command executor wraps and extends a {@link SingleUseQueueingExtendedCommandExecutor},
 * adding support for executing {@link JobCommand}s.
 */
public class SingleUseQueueingExtendedJobCommandExecutor
	extends AbstractSingleUseQueueingJobCommandExecutor<SingleUseQueueingExtendedCommandExecutor, StatefulExtendedCommandContext>
	implements CombinedExtendedCommandContext, StatefulExtendedCommandContext
{
	public SingleUseQueueingExtendedJobCommandExecutor() {
		this(new SingleUseQueueingExtendedCommandExecutor());
	}

	public SingleUseQueueingExtendedJobCommandExecutor(ExtendedCommandContext commandExecutor) {
		this(new SingleUseQueueingExtendedCommandExecutor(commandExecutor));
	}

	public SingleUseQueueingExtendedJobCommandExecutor(StatefulExtendedCommandContext commandExecutor) {
		this(new SingleUseQueueingExtendedCommandExecutor(commandExecutor));
	}

	public SingleUseQueueingExtendedJobCommandExecutor(SingleUseQueueingExtendedCommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command)
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		this.commandExecutor.waitToExecute(command);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		return this.commandExecutor.waitToExecute(command, timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command) throws InterruptedException {
		this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException {
		return this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command, String jobName) throws InterruptedException {
		// ignore 'jobName'
		this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException {
		// ignore 'jobName'
		return this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command, String jobName, ISchedulingRule rule) throws InterruptedException {
		// ignore 'jobName' and 'rule'
		this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandExecutor#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule rule, long timeout) throws InterruptedException {
		// ignore 'jobName' and 'rule'
		return this.commandExecutor.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}
}
