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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.Synchronizer;
import org.eclipse.jpt.utility.internal.SynchronousSynchronizer;

/**
 * Adapt the "synchronizer" interface to the JPA project "updater" interface.
 * <p>
 * This updater will "update" the JPA project immediately and not return until
 * the "update" and all resulting "updates" are complete. This implementation
 * should be used sparingly and for as short a time as possible, as it increases
 * the probability of deadlocks. A deadlock can occur when a JPA project is
 * updated from multiple threads and various resources are locked in varying
 * orders.
 */
public class SynchronousJpaProjectUpdater
	extends AbstractSynchronizerJpaProjectUpdater
{
	public SynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super(jpaProject);
	}

	@Override
	protected Synchronizer buildSynchronizer(JpaProject jpaProject) {
		return new SynchronousSynchronizer(this.buildCommand(jpaProject));
	}

	protected Command buildCommand(JpaProject jpaProject) {
		return new LocalCommand(jpaProject);
	}


	// ********** Command **********

	/**
	 * Call the "internal" JPA project update method.
	 */
	protected static class LocalCommand
		implements Command
	{
		protected final JpaProject jpaProject;

		protected LocalCommand(JpaProject jpaProject) {
			super();
			this.jpaProject = jpaProject;
		}

		public void execute() {
			this.jpaProject.update(new NullProgressMonitor());
		}
	}

}
