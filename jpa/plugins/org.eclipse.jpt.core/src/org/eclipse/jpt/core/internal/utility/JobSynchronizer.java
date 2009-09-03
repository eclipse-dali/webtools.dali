/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.utility.Synchronizer;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This synchronizer will perform synchronizations in an Eclipse job on a
 * separate thread, allowing calls to {@link Synchronizer#synchronize()}
 * to return immediately.
 */
public class JobSynchronizer implements Synchronizer {
	protected final SynchronizeJob job;


	// ********** construction **********

	/**
	 * Construct a job synchronizer that uses the specified command to
	 * perform the synchronization.
	 */
	public JobSynchronizer(String jobName, JobCommand command) {
		this(jobName, command, null);
	}

	/**
	 * Construct a job synchronizer that uses the specified command to
	 * perform the synchronization.
	 */
	public JobSynchronizer(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		super();
		this.job = this.buildJob(jobName, command, schedulingRule);
	}

	protected SynchronizeJob buildJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		return new SynchronizeJob(jobName, command, schedulingRule);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Allow the job to be scheduled, but postpone the first synchronization
	 * until requested.
	 */
	public void start() {
		this.job.start();
	}

	/**
	 * Simply re-schedule the job, allowing the current execution
	 * to run to completion (i.e. do not cancel it).
	 * This should reduce the number of times the job is re-executed
	 * (recursively).
	 */
	public void synchronize() {
		this.job.schedule();
	}

	/**
	 * Wait for the current job execution to complete.
	 */
	public void stop() {
		this.job.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.job);
	}


	// ********** synchronization job **********

	/**
	 * This is the job that gets scheduled by the job synchronizer.
	 * When the job is run it executes the client-supplied job command.
	 */
	protected static class SynchronizeJob extends Job {
		/**
		 * The client-supplied job command that executes every time the job
		 * runs.
		 */
		protected final JobCommand command;

		/**
		 * When this flag is set to false, the job does not stop immediately;
		 * but it will not be scheduled to run again.
		 */
		protected boolean shouldSchedule;

		protected SynchronizeJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
			super(jobName);
			this.command = command;
			this.shouldSchedule = false;
			this.setRule(schedulingRule);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return this.command.execute(monitor);
		}

		@Override
		public boolean shouldSchedule() {
			return this.shouldSchedule;
		}

		protected void start() {
			if (this.shouldSchedule) {
				throw new IllegalStateException("The Synchronizer was not stopped."); //$NON-NLS-1$
			}
			this.shouldSchedule = true;
		}

		/**
		 * Prevent the job from running again and wait for the current
		 * execution, if there is any, to end before returning.
		 */
		public void stop() {
			// this will prevent the job from being scheduled to run again
			this.shouldSchedule = false;
			// this will cancel the job if it has already been scheduled, but is currently WAITING
			this.cancel();
			try {
				// if the job is currently RUNNING, wait until it is done before returning
				this.join();
			} catch (InterruptedException ex) {
				// the job thread was interrupted while waiting - ignore
			}
		}

	}

}
