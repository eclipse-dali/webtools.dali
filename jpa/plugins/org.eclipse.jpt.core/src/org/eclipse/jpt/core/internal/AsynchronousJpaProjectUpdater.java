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

/**
 * This updater will update the project in a job that executes in a separate
 * thread and allows calls to #update() to return immediately.
 */
public class AsynchronousJpaProjectUpdater implements JpaProject.Updater {
	protected final JpaProject jpaProject;
	protected final UpdateJob job;

	public AsynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.job = this.buildJob();
	}

	protected UpdateJob buildJob() {
		return new UpdateJob();
	}

	/**
	 * Let the job run to completion (i.e. do not cancel it).
	 * This should reduce the number of times the job is re-scheduled.
	 */
	public void update() {
		this.job.schedule();
	}

	/**
	 * prevent the job from running again and wait for the current
	 * execution, if there is any, to end before returning
	 */
	public void dispose() {
		this.job.setShouldSchedule(false);
		this.job.cancel();  // this will cancel the job if it is currently WAITING
		try {
			this.job.join();
		} catch (InterruptedException ex) {
			// the job thread was interrupted while waiting - ignore
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.jpaProject);
	}


	/**
	 * This is the job that gets scheduled by the asynchronous updater.
	 * When the job is run it tells the JPA project to "update".
	 * Only a single instance of this job per project can run at a time.
	 */
	protected class UpdateJob extends Job {
		protected boolean shouldSchedule;

		protected UpdateJob() {
			super("Update JPA project: " + AsynchronousJpaProjectUpdater.this.jpaProject.name());  // TODO i18n
			this.shouldSchedule = true;
			this.setRule(AsynchronousJpaProjectUpdater.this.jpaProject.project());
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return AsynchronousJpaProjectUpdater.this.jpaProject.update(monitor);
		}

		/**
		 * When setting to false, the job does not stop immediately;
		 * but it won't run again, even if it is scheduled.
		 */
		public void setShouldSchedule(boolean shouldSchedule) {
			this.shouldSchedule = shouldSchedule;
		}

		@Override
		public boolean shouldSchedule() {
			return this.shouldSchedule;
		}

	}

}
