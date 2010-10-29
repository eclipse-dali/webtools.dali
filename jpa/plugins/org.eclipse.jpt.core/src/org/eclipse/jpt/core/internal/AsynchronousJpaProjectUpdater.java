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
import org.eclipse.jpt.core.internal.utility.CallbackJobSynchronizer;
import org.eclipse.jpt.core.internal.utility.JobCommand;
import org.eclipse.jpt.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.osgi.util.NLS;

/**
 * Adapt the "callback synchronizer" interface to the JPA project "updater"
 * interface.
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
	protected CallbackSynchronizer buildSynchronizer() {
		return new CallbackJobSynchronizer(
				this.buildJobName(),
				this.buildJobCommand(),
				this.buildJobSchedulingRule()
			);
	}

	protected String buildJobName() {
		return NLS.bind(JptCoreMessages.UPDATE_JOB_NAME, this.jpaProject.getName());
	}

	protected JobCommand buildJobCommand() {
		return new JobCommand() {
			public IStatus execute(IProgressMonitor monitor) {
				return AsynchronousJpaProjectUpdater.this.jpaProject.update(monitor);
			}
		};
	}

	protected ISchedulingRule buildJobSchedulingRule() {
		return this.jpaProject.getProject();
	}

}
