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

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jpt.common.core.utility.command.CombinedExtendedCommandContext;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;

/**
 * A command context that schedules a {@link org.eclipse.core.runtime.jobs.Job
 * job} to [asynchronously] execute each {@link JobCommand job command} or
 * {@link Command command}.
 * Synchronous command executions are coordinated with the job via a
 * {@link JobChangeListener job listener}.
 */
public class SimpleJobCommandContext
	implements CombinedExtendedCommandContext
{
	private final String defaultJobName;
	private final ISchedulingRule defaultJobSchedulingRule;


	/**
	 * Construct a job command context with no default job name and no
	 * default scheduling rule.
	 */
	public SimpleJobCommandContext() {
		this(null);
	}

	/**
	 * Construct a job command context with the specified default job name and
	 * no default scheduling rule.
	 */
	public SimpleJobCommandContext(String defaultJobName) {
		this(defaultJobName, null);
	}

	/**
	 * Construct a job command context with the specified default job name and
	 * default scheduling rule.
	 */
	public SimpleJobCommandContext(String defaultJobName, ISchedulingRule defaultJobSchedulingRule) {
		super();
		this.defaultJobName = defaultJobName;
		this.defaultJobSchedulingRule = defaultJobSchedulingRule;
	}

	public void execute(Command command) {
		this.execute(new CommandJobCommandAdapter(command));
	}

	public void waitToExecute(Command command) throws InterruptedException {
		this.waitToExecute(new CommandJobCommandAdapter(command));
	}

	public boolean waitToExecute(Command command, long timeout) throws InterruptedException {
		return this.waitToExecute(new CommandJobCommandAdapter(command), timeout);
	}

	/**
	 * Assign the resulting {@link Job} the default name and scheduling rule.
	 */
	public void execute(JobCommand command) {
		this.execute(command, null);
	}

	/**
	 * Assign the resulting {@link Job} the default scheduling rule.
	 */
	public void execute(JobCommand command, String jobName) {
		this.execute(command, jobName, this.defaultJobSchedulingRule);
	}

	public void execute(JobCommand command, String jobName, ISchedulingRule rule) {
		this.execute_(command, this.buildJobName(jobName, command), rule);
	}

	/**
	 * Pre-condition: the job name must not be <code>null</code>.
	 */
	private void execute_(JobCommand command, String jobName, ISchedulingRule rule) {
		Job job = new JobCommandJob(jobName, command);
		job.setRule(rule);
		job.schedule();
	}

	/**
	 * Assign the resulting {@link Job} the default name and scheduling rule.
	 */
	public void waitToExecute(JobCommand command) throws InterruptedException {
		this.waitToExecute(command, null);
	}

	/**
	 * Assign the resulting {@link Job} the default name and scheduling rule.
	 */
	public boolean waitToExecute(JobCommand command, long timeout) throws InterruptedException {
		return this.waitToExecute(command, null, timeout);
	}

	/**
	 * Assign the resulting {@link Job} the specified name and the default
	 * scheduling rule.
	 */
	public void waitToExecute(JobCommand command, String jobName) throws InterruptedException {
		this.waitToExecute(command, jobName, this.defaultJobSchedulingRule);
	}

	/**
	 * Assign the resulting {@link Job} the specified name and the default
	 * scheduling rule.
	 */
	public boolean waitToExecute(JobCommand command, String jobName, long timeout) throws InterruptedException {
		return this.waitToExecute(command, jobName, this.defaultJobSchedulingRule, timeout);
	}

	/**
	 * Assign the resulting {@link Job} the specified name and scheduling rule.
	 */
	public void waitToExecute(JobCommand command, String jobName, ISchedulingRule rule) throws InterruptedException {
		this.waitToExecute_(command, this.buildJobName(jobName, command), rule, 0);
	}

	/**
	 * Assign the resulting {@link Job} the specified name and scheduling rule.
	 */
	public boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule rule, long timeout) throws InterruptedException {
		return this.waitToExecute_(command, this.buildJobName(jobName, command), rule, timeout);
	}

	/**
	 * Pre-condition: the job name must not be <code>null</code>.
	 * <p>
	 * Schedule a job that will notify us when it is finished.
	 * The current thread will suspend until the job is finished.
	 */
	private boolean waitToExecute_(JobCommand command, String jobName, ISchedulingRule rule, long timeout) throws InterruptedException {
		if ((timeout == 0L) && Job.getJobManager().isSuspended()) {
			// the job manager is suspended during workbench start-up;
			// so this method will lock up accordingly, which is not a Good Thing
			// if it is called from the Eclipse Main thread during start-up
			// (i.e. deadlock!)
			return false;
		}

		Job job = new JobCommandJob(jobName, command);
		JobChangeListener listener = new JobChangeListener();
		job.addJobChangeListener(listener);
		job.setRule(rule);
		job.schedule();
		try {
			return listener.waitUntilDone(timeout);
		} finally {
			job.removeJobChangeListener(listener);
		}
	}

	private String buildJobName(String jobName, Object command) {
		if (jobName != null) {
			return jobName;
		}
		if (this.defaultJobName != null) {
			return this.defaultJobName;
		}
		return ClassTools.toStringName(command.getClass());
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** job listener **********

	/**
	 * This job listener notifies any interested threads when the
	 * {@link Job job} is done.
	 */
	/* CU private */ class JobChangeListener
		extends JobChangeAdapter
	{
		private final SynchronizedBoolean done = new SynchronizedBoolean(false);

		@Override
		public void done(IJobChangeEvent event) {
			super.done(event);
			this.done.setTrue();
		}

		boolean waitUntilDone(long timeout) throws InterruptedException {
			return this.done.waitUntilTrue(timeout);
		}
	}
}
