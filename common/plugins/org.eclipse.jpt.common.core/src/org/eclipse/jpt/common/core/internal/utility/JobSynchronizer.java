/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;

/**
 * This synchronizer will perform synchronizations in an Eclipse job on a
 * separate thread, allowing calls to {@link Synchronizer#synchronize()}
 * to return immediately.
 * <p>
 * If necessary, the client-supplied job command should handle any
 * exceptions appropriately. Although, the default exception-handling provided
 * by the Eclipse Job Framework is probably adequate in most cases:<ul>
 * <li>An {@link org.eclipse.core.runtime.OperationCanceledException OperationCanceledException}
 *     results in a {@link org.eclipse.core.runtime.Status#CANCEL_STATUS CANCEL_STATUS}.
 * <li>Any non-{@link ThreadDeath} {@link Throwable}
 *     results in a {@link org.eclipse.core.runtime.IStatus#ERROR ERROR}
 *     {@link org.eclipse.core.runtime.IStatus IStatus}
 * </ul>
 * @see org.eclipse.core.internal.jobs.Worker#run()
 */
public class JobSynchronizer
	implements Synchronizer
{
	/**
	 * The synchronization is performed by this job. The same job is used
	 * for every start/stop cycle (since a job can be re-started).
	 */
	private final SynchronizationJob job;


	// ********** construction **********

	/**
	 * Construct a job synchronizer that uses the specified job command to
	 * perform the synchronization. Assign the generated Eclipse job the
	 * specified name.
	 */
	public JobSynchronizer(String jobName, JobCommand command) {
		this(jobName, command, null);
	}

	/**
	 * Construct a job synchronizer that uses the specified job command to
	 * perform the synchronization. Assign the generated Eclipse job the
	 * specified name and scheduling rule.
	 */
	public JobSynchronizer(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		super();
		this.job = this.buildJob(jobName, command, schedulingRule);
	}

	SynchronizationJob buildJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
		return new SynchronizationJob(jobName, command, schedulingRule);
	}


	// ********** Synchronizer implementation **********

	/**
	 * Allow the job to be scheduled, but postpone the first synchronization
	 * until requested, via {@link #synchronize()}.
	 */
	public void start() {
		this.job.start();
	}

	/**
	 * "Schedule" the job.
	 */
	public void synchronize() {
		this.job.synchronize();
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
	static class SynchronizationJob extends Job {
		/**
		 * The client-supplied job command that executes every time the job
		 * runs.
		 */
		private final JobCommand command;

		/**
		 * When this flag is set to false, the job does not stop immediately;
		 * but it will not be scheduled to run again.
		 */
		// use 'volatile' because synchronization isn't really required
		volatile boolean shouldSchedule;


		SynchronizationJob(String jobName, JobCommand command, ISchedulingRule schedulingRule) {
			super(jobName);
			if (command == null) {
				throw new NullPointerException();
			}
			this.command = command;
			this.shouldSchedule = false;
			this.setRule(schedulingRule);
		}

		/**
		 * Just set the "should schedule" flag so the job <em>can</em> be
		 * scheduled; but don't actually schedule it.
		 */
		void start() {
			if (this.shouldSchedule) {
				throw new IllegalStateException("The Synchronizer was not stopped."); //$NON-NLS-1$
			}
			this.shouldSchedule = true;
		}

		/**
		 * Simply re-schedule the job, allowing the current execution
		 * to run to completion (i.e. do not try to cancel it prematurely).
		 * This should minimize the number of times the job is re-executed
		 * (recursively and otherwise).
		 */
		void synchronize() {
			this.schedule();
		}

		/**
		 * Any uncaught exceptions thrown by the command will be reasonably
		 * handled by the Job Framework.
		 * @see org.eclipse.core.internal.jobs.Worker#run()
		 */
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return this.command.execute(monitor);
		}

		/**
		 * Prevent the job from running again and wait for the current
		 * execution, if there is any, to end before returning.
		 */
		void stop() {
			if ( ! this.shouldSchedule) {
				throw new IllegalStateException("The Synchronizer was not started."); //$NON-NLS-1$
			}
			// this will prevent the job from being scheduled to run again
			this.shouldSchedule = false;
			// this will cancel the job if it has already been scheduled, but is currently WAITING
			this.cancel();
			try {
				// if the job is currently RUNNING, wait until it is done before returning
				this.join();
			} catch (InterruptedException ex) {
				// the thread that called #stop() was interrupted while waiting to
				// join the synchronization job - ignore;
				// 'shouldSchedule' is still set to 'false', so the job loop will still stop - we
				// just won't wait around for it...
			}
		}

		/**
		 * This is part of the normal {@link Job} behavior. By default, it is
		 * not used (i.e. it always returns <code>true</code>).
		 * We implement it here.
		 */
		@Override
		public boolean shouldSchedule() {
			return this.shouldSchedule;
		}

	}

}
