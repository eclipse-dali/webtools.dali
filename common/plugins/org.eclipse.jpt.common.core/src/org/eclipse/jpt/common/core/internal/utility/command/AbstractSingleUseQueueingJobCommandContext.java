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
import org.eclipse.jpt.common.core.utility.command.CombinedCommandContext;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandContext;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.AbstractSingleUseQueueingCommandContext;

/**
 * This job command context wraps and extends an
 * {@link AbstractSingleUseQueueingCommandContext},
 * adding support for executing {@link JobCommand}s.
 * <p>
 * <strong>NB:</strong> This context <em>ignores</em> any
 * {@link ISchedulingRule scheduling rules}.
 */
public abstract class AbstractSingleUseQueueingJobCommandContext<E1 extends AbstractSingleUseQueueingCommandContext<E2>, E2 extends StatefulCommandContext>
	implements CombinedCommandContext, StatefulCommandContext
{
	/**
	 * Since the {@link JobCommand}s are simply converted into {@link Command}s,
	 * we can delegate to an {@link AbstractSingleUseQueueingCommandContext}.
	 */
	protected final E1 commandContext;


	protected AbstractSingleUseQueueingJobCommandContext(E1 commandContext) {
		super();
		this.commandContext = commandContext;
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#start()
	 */
	public void start() {
		this.commandContext.start();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#execute(Command)
	 */
	public void execute(Command command) {
		this.commandContext.execute(command);
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#execute(Command)
	 */
	public void execute(JobCommand command) {
		this.commandContext.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#execute(Command)
	 */
	public void execute(JobCommand command, String jobName) {
		// ignore 'jobName'
		this.commandContext.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#execute(Command)
	 */
	public void execute(JobCommand command, String jobName, ISchedulingRule rule) {
		// ignore 'jobName' and 'rule'
		this.commandContext.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#suspend()
	 */
	public void suspend() {
		this.commandContext.suspend();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#resume()
	 */
	public void resume() {
		this.commandContext.resume();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandContext#stop()
	 */
	public void stop() throws InterruptedException {
		this.commandContext.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commandContext);
	}
}
