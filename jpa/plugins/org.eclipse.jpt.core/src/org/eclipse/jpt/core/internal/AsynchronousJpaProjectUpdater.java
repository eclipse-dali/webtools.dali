/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.utility.JobCommand;
import org.eclipse.jpt.core.internal.utility.JobSynchronizer;
import org.eclipse.jpt.utility.Synchronizer;
import org.eclipse.osgi.util.NLS;

/**
 * Adapt the "synchronizer" interface to the JPA project "updater" interface.
 * <p>
 * This updater will "update" the JPA project in an Eclipse job that executes
 * in a separate thread allowing calls to {@link JpaProject.Updater#update()}
 * to return immediately.
 */
public class AsynchronousJpaProjectUpdater
	extends AbstractSynchronizerJpaProjectUpdater
{
	public AsynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super(jpaProject);
	}

	@Override
	protected Synchronizer buildSynchronizer(JpaProject jpaProject) {
		return new JobSynchronizer(
				this.buildJobName(jpaProject),
				this.buildJobCommand(jpaProject),
				this.buildJobSchedulingRule(jpaProject)
			);
	}

	protected String buildJobName(JpaProject jpaProject) {
		return NLS.bind(JptCoreMessages.UPDATE_JOB_NAME, jpaProject.getName());
	}

	protected JobCommand buildJobCommand(JpaProject jpaProject) {
		return new LocalJobCommand(jpaProject);
	}

	protected ISchedulingRule buildJobSchedulingRule(JpaProject jpaProject) {
		return jpaProject.getProject();
	}


	// ********** Command **********

	/**
	 * Call the "internal" JPA project update method.
	 */
	protected static class LocalJobCommand
		implements JobCommand
	{
		protected final JpaProject jpaProject;

		protected LocalJobCommand(JpaProject jpaProject) {
			super();
			this.jpaProject = jpaProject;
		}

		public IStatus execute(IProgressMonitor monitor) {
			return this.jpaProject.update(monitor);
		}
	}

}
