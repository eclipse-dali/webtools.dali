/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;

/**
 * This job will not run until any currently outstanding Dali <em>updates</em> etc.
 * are complete. As a result, the runnable dispatched (by this job) to the
 * UI thread will not run until the previously scheduled UI runnables are
 * complete also (e.g. events triggered by the aforementioned <em>updates</em>
 * etc.).
 * <p>
 * Typically, client code will set the JPA selection when some sort of user
 * action is complete (e.g. when a menu action is complete). The action will
 * have modified the model (e.g. setting an attribute's mapping) and, if the
 * action was performed on a single element, will want to select that modified
 * element once the action is complete (via the appropriate JPA selection
 * manager). Unless the action is performing its action sychronously
 * (via a call to
 * {@link org.eclipse.jpt.jpa.core.JpaProjectManager#execute(org.eclipse.jpt.common.utility.command.Command)
 * JpaProjectManager.execute(...)}),
 * the modification(s) will have triggered a background <em>update</em> that
 * executes in a job that locks the corresponding project. This <em>update</em> will
 * modify other parts of the model, resulting in events that will modify the UI.
 * These UI modifications must be dispatched to the UI thread (via something like
 * {@link org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper
 * SWTPropertyChangeListenerWrapper}).
 * <p>
 * As a result, the setting of the JPA selection (which, itself, also modifies
 * the UI via events) is performed via a job that, like the <em>update</em> job,
 * locks on the corresponding project. As a result this job will not execute
 * until any outstanding <em>update</em> jobs are complete. Once this job is
 * executing, it dispatches the actual setting of the JPA selection to the UI
 * thread; meaning, again, it will not execute until any outstanding UI-targeted
 * events triggered by the <em>updates</em> have executed.
 */
class SetJpaSelectionJob
	extends Job
{
	private final Runnable setJpaSelectionRunnable;


	SetJpaSelectionJob(Manager manager, JpaStructureNode selection) {
		super(JptUiMessages.SetJpaSelection_jobName);
		this.setJpaSelectionRunnable = new SetJpaSelectionRunnable(manager, selection);
		// if the selection is null we don't need a scheduling rule -
		// the JPA selection can be set to null at any time
		if (selection != null) {
			this.setRule(selection.getJpaProject().getProject());
		}
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SWTUtil.asyncExec(this.setJpaSelectionRunnable);
		return Status.OK_STATUS;
	}

	/**
	 * UI runnable.
	 * @see SetJpaSelectionJob#run(IProgressMonitor)
	 */
	private static class SetJpaSelectionRunnable
		extends RunnableAdapter
	{
		private final Manager jpaSelectionManager;
		private final JpaStructureNode selection;

		SetJpaSelectionRunnable(Manager manager, JpaStructureNode selection) {
			super();
			this.jpaSelectionManager = manager;
			this.selection = selection;
		}

		@Override
		public void run() {
			this.jpaSelectionManager.setSelection_(this.selection);
		}
	}

	/**
	 * Internal interface used to set the JPA selection while executing on
	 * the UI thread.
	 * @see SetJpaSelectionRunnable#run()
	 */
	interface Manager {
		/**
		 * @see SetJpaSelectionRunnable#run()
		 */
		void setSelection_(JpaStructureNode selection);
	}
}
