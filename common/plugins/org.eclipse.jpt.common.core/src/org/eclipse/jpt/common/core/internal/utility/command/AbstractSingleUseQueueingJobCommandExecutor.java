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
import org.eclipse.jpt.common.core.utility.command.CombinedCommandExecutor;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.StatefulCommandExecutor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.command.AbstractSingleUseQueueingCommandExecutor;

/**
 * This job command executor wraps and extends an
 * {@link AbstractSingleUseQueueingCommandExecutor},
 * adding support for executing {@link JobCommand}s.
 * <p>
 * <strong>NB:</strong> This executor <em>ignores</em> any
 * {@link ISchedulingRule scheduling rules}.
 */
public abstract class AbstractSingleUseQueueingJobCommandExecutor<E1 extends AbstractSingleUseQueueingCommandExecutor<E2>, E2 extends StatefulCommandExecutor>
	implements CombinedCommandExecutor, StatefulCommandExecutor
{
	/**
	 * Since the {@link JobCommand}s are simply converted into {@link Command}s,
	 * we can delegate to an {@link AbstractSingleUseQueueingCommandExecutor}.
	 */
	protected final E1 commandExecutor;


	protected AbstractSingleUseQueueingJobCommandExecutor(E1 commandExecutor) {
		super();
		this.commandExecutor = commandExecutor;
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#start()
	 */
	public void start() {
		this.commandExecutor.start();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#execute(Command)
	 */
	public void execute(Command command) {
		this.commandExecutor.execute(command);
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#execute(Command)
	 */
	public void execute(JobCommand command) {
		this.commandExecutor.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#execute(Command)
	 */
	public void execute(JobCommand command, String jobName) {
		// ignore 'jobName'
		this.commandExecutor.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#execute(Command)
	 */
	public void execute(JobCommand command, String jobName, ISchedulingRule rule) {
		// ignore 'jobName' and 'rule'
		this.commandExecutor.execute(new JobCommandCommandAdapter(command));
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#suspend()
	 */
	public void suspend() {
		this.commandExecutor.suspend();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#resume()
	 */
	public void resume() {
		this.commandExecutor.resume();
	}

	/**
	 * @see AbstractSingleUseQueueingCommandExecutor#stop()
	 */
	public void stop() throws InterruptedException {
		this.commandExecutor.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.commandExecutor);
	}
}
