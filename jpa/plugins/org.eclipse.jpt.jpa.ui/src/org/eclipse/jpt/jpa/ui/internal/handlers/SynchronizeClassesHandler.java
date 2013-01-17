/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandExecutor;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *  SynchronizeClassesHandler
 */
public class SynchronizeClassesHandler extends AbstractHandler
{
	private IFile persistenceXmlFile;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// There is always only one element in the actual selection.
		IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelectionChecked(event);
		this.persistenceXmlFile = this.buildPersistenceXmlFile(selection.getFirstElement());

		try {
			IRunnableWithProgress runnable = new SyncRunnable(this.persistenceXmlFile);
			this.buildProgressMonitorDialog().run(true, true, runnable);  // true => fork; true => cancellable
		} catch (InvocationTargetException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			JptJpaUiPlugin.instance().logError(ex);
		}
		return null;
	}

	private IFile buildPersistenceXmlFile(Object selection) {
		if (selection instanceof IFile) {
			return (IFile) selection;
		}
		if (selection instanceof PersistenceXml) {
			return (IFile) ((PersistenceXml) selection).getResource();
		}
		return null;
	}

	private ProgressMonitorDialog buildProgressMonitorDialog() {
		return new ProgressMonitorDialog(null);
	}


	// ********** sync runnable **********

	/**
	 * This is dispatched to the progress monitor dialog.
	 */
	/* CU private */ static class SyncRunnable
		implements IRunnableWithProgress
	{
		private IFile persistenceXmlFile;

		SyncRunnable(IFile persistenceXmlFile) {
			super();
			this.persistenceXmlFile = persistenceXmlFile;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				this.run_(monitor);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}

		private void run_(IProgressMonitor monitor) throws CoreException {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			// lock the entire project, since we might modify the metamodel classes
			ISchedulingRule rule = workspace.getRuleFactory().modifyRule(this.persistenceXmlFile.getProject());
			workspace.run(
				new SyncWorkspaceRunnable(this.persistenceXmlFile),
				rule,
				IWorkspace.AVOID_UPDATE,
				monitor
			);
		}
	}


	// ********** sync workspace runnable **********

	/**
	 * This is dispatched to the Eclipse workspace.
	 */
	/* CU private */ static class SyncWorkspaceRunnable
		implements IWorkspaceRunnable
	{
		private IFile persistenceXmlFile;

		SyncWorkspaceRunnable(IFile persistenceXmlFile) {
			super();
			this.persistenceXmlFile = persistenceXmlFile;
		}

		public void run(IProgressMonitor monitor) throws CoreException {
			if (monitor.isCanceled()) {
				return;
			}
			SubMonitor sm = SubMonitor.convert(monitor, JptUiMessages.SynchronizingClasses_TaskName, 20);

			JpaProject jpaProject = this.getJpaProject();
			if (jpaProject == null) {
				return;
			}

			JptXmlResource resource = jpaProject.getPersistenceXmlResource();
			if (resource == null) {
				// the resource can be null if the persistence.xml file has an invalid content type
				return;
			}

			if (sm.isCanceled()) {
				return;
			}
			sm.worked(1);
			
			PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
			if (persistenceXml == null) {
				return;  // unlikely...
			}

			Persistence persistence = persistenceXml.getRoot();
			if (persistence == null) {
				return;  // unlikely...
			}

			PersistenceUnit persistenceUnit = (persistence.getPersistenceUnitsSize() == 0) ?
					persistence.addPersistenceUnit() :
					persistence.getPersistenceUnit(0);
			if (sm.isCanceled()) {
				return;
			}
			sm.worked(1);

			Command syncCommand = new SyncCommand(persistenceUnit, sm.newChild(17));
			JpaProjectManager mgr = this.getJpaProjectManager();
			try {
				if (mgr != null) {
					mgr.execute(syncCommand, SynchronousUiCommandExecutor.instance());
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();  // skip save?
				throw new RuntimeException(ex);
			}

			resource.save();
			sm.worked(1);
		}

		private JpaProjectManager getJpaProjectManager() {
			return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
		}
	
		private IProject getProject() {
			return this.persistenceXmlFile.getProject();
		}

		private JpaProject getJpaProject() {
			return (JpaProject) this.getProject().getAdapter(JpaProject.class);
		}

		private IWorkspace getWorkspace() {
			return this.persistenceXmlFile.getWorkspace();
		}
	}


	// ********** sync command **********

	/**
	 * This is dispatched to the JPA project manager.
	 */
	/* CU private */ static class SyncCommand
		implements Command
	{
		private final PersistenceUnit persistenceUnit;
		private final IProgressMonitor monitor;

		SyncCommand(PersistenceUnit persistenceUnit, IProgressMonitor monitor) {
			super();
			this.persistenceUnit = persistenceUnit;
			this.monitor = monitor;
		}

		public void execute() {
			this.persistenceUnit.synchronizeClasses(this.monitor);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.persistenceUnit);
		}
	}
}