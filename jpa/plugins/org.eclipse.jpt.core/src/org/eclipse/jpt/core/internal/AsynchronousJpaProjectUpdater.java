/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.util.NLS;

/**
 * This updater will "update" the project in a job that executes in a separate
 * thread and allows calls to #update() to return immediately.
 */
public class AsynchronousJpaProjectUpdater implements JpaProject.Updater {
	protected final UpdateJob job;

	public AsynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.job = this.buildJob(jpaProject);
	}

	protected UpdateJob buildJob(JpaProject jpaProject) {
		return new UpdateJob(jpaProject);
	}

	/**
	 * Allow the job to be scheduled, but delay the first "update" until requested.
	 */
	public void start() {
		this.job.start();
	}

	/**
	 * Let the job run to completion (i.e. do not cancel it).
	 * This should reduce the number of times the job is re-scheduled.
	 */
	public void update() {
		this.job.schedule();
	}

	public void stop() {
		this.job.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.job);
	}


	/**
	 * This is the job that gets scheduled by the asynchronous updater.
	 * When the job is run it tells the JPA project to "update".
	 * Only a single instance of this job per project can run at a time.
	 */
	protected static class UpdateJob extends Job {
		protected final JpaProject jpaProject;

		/**
		 * When this flag is set to false, the job does not stop immediately;
		 * but it cannot be scheduled to run again.
		 */
		protected boolean shouldSchedule;

		protected UpdateJob(JpaProject jpaProject) {
			super(buildName(jpaProject));
			this.jpaProject = jpaProject;
			this.shouldSchedule = false;
			this.setRule(jpaProject.getProject());
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return this.jpaProject.update(monitor);
		}

		@Override
		public boolean shouldSchedule() {
			return this.shouldSchedule;
		}

		protected void start() {
			if (this.shouldSchedule) {
				throw new IllegalStateException("The Updater was not stopped."); //$NON-NLS-1$
			}
			this.shouldSchedule = true;
		}

		/**
		 * Prevent the job from running again and wait for the current
		 * execution, if there is any, to end before returning.
		 */
		protected void stop() {
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

		protected static String buildName(JpaProject jpaProject) {
			return NLS.bind(JptCoreMessages.UPDATE_JOB_NAME, jpaProject.getName());
		}

	}

}
