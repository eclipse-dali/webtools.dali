/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.command.SingleUseQueueingExtendedCommandContext;

/**
 * This command context wraps and extends a {@link SingleUseQueueingExtendedCommandContext},
 * adding support for executing {@link JobCommand}s.
 */
public class SingleUseQueueingExtendedJobCommandContext
	extends AbstractSingleUseQueueingJobCommandContext<SingleUseQueueingExtendedCommandContext, StatefulExtendedCommandContext>
	implements CombinedExtendedCommandContext, StatefulExtendedCommandContext
{
	public SingleUseQueueingExtendedJobCommandContext() {
		this(new SingleUseQueueingExtendedCommandContext());
	}

	public SingleUseQueueingExtendedJobCommandContext(ExtendedCommandContext commandContext) {
		this(new SingleUseQueueingExtendedCommandContext(commandContext));
	}

	public SingleUseQueueingExtendedJobCommandContext(StatefulExtendedCommandContext commandContext) {
		this(new SingleUseQueueingExtendedCommandContext(commandContext));
	}

	public SingleUseQueueingExtendedJobCommandContext(SingleUseQueueingExtendedCommandContext commandContext) {
		super(commandContext);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command)
	 */
	public void waitToExecute(Command command) throws InterruptedException {
		this.commandContext.waitToExecute(command);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		return this.commandContext.waitToExecute(command, timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command) throws InterruptedException {
		this.commandContext.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException {
		return this.commandContext.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command, String jobName) throws InterruptedException {
		// ignore 'jobName'
		this.commandContext.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException {
		// ignore 'jobName'
		return this.commandContext.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command)
	 */
	public void waitToExecute(JobCommand command, String jobName, ISchedulingRule rule) throws InterruptedException {
		// ignore 'jobName' and 'rule'
		this.commandContext.waitToExecute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see SingleUseQueueingExtendedCommandContext#waitToExecute(Command, long)
	 */
	public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule rule, long timeout) throws InterruptedException {
		// ignore 'jobName' and 'rule'
		return this.commandContext.waitToExecute(new JobCommandCommandAdapter(command), timeout);
	}
}
