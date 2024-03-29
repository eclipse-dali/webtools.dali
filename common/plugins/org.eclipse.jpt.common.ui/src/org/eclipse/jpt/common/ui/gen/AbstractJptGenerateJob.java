/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.core.gen.LaunchConfigListener;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

/**
 *  AbstractJptGenerateJob
 */
public abstract class AbstractJptGenerateJob extends WorkspaceJob {
	private final IJavaProject javaProject;

	/* CU private */ final SynchronizedBoolean generationCompleted;
	/* CU private */ boolean generationSuccessful;

	// ********** constructor **********

	public AbstractJptGenerateJob(String name, IJavaProject javaProject) {
		super(name);
		
		this.javaProject = javaProject;
		this.generationCompleted = new SynchronizedBoolean(false);
		this.generationSuccessful = false;
		
		IResourceRuleFactory ruleFactory = this.javaProject.getProject().getWorkspace().getRuleFactory();
		this.setRule(ruleFactory.modifyRule(this.javaProject.getProject()));
	}

	// ********** abstract methods **********
	
	protected abstract JptGenerator buildGenerator();

	protected abstract void postGenerate();
	
	protected abstract String getJobName();
	
	// ********** overwrite WorkspaceJob **********

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		SubMonitor sm = SubMonitor.convert(monitor, this.getJobName(), 1);
		try {
			this.generate(sm.newChild(1));
		} 
		catch (OperationCanceledException e) {
			return Status.CANCEL_STATUS;
		}
		catch (RuntimeException re) {
			this.logException(re);
		}
		return Status.OK_STATUS;
	}

	// ********** convenience methods **********

	@SuppressWarnings("unused")
	protected void jptPluginLogException(Exception exception) {
		// do nothing by default
	}

	protected void refreshProject() {
		try {
			this.getJavaProject().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		catch (CoreException e) {
			this.logException(e);
		}
	}

	protected void logException(Exception exception) {
		String msg = exception.getMessage();
		String message = (msg == null) ? exception.toString() : msg;
		this.displayError(message);
		this.jptPluginLogException(exception);
	}

	protected IJavaProject getJavaProject() {
		return this.javaProject;
	}
	
	protected void openEditor(final IFile file) {
		if(file != null) {
			DisplayTools.asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = WorkbenchTools.getActivePage();
						if (page != null) {
							IDE.openEditor(page, file, true);
						}
					}
					catch (PartInitException e) {
						AbstractJptGenerateJob.this.logException(e);
					}
				}
			});
		}
	}
	
	// ********** internal methods **********

	private void generate(IProgressMonitor monitor) {
		JptGenerator generator = this.buildGenerator();

		LaunchConfigListener launchListener = this.buildLaunchListener();
		generator.addLaunchConfigListener(launchListener);
		generator.generate(monitor);
		try {
			this.generationCompleted.waitUntilTrue();
		}
		catch (InterruptedException e) {	
			this.logException(e);
		}
		finally {
			generator.removeLaunchConfigListener(launchListener);
			this.generationCompleted.setFalse();
		}
		
		if (this.generationSuccessful) {
			this.postGenerate();
		} else {
			this.displayError(JptCommonUiMessages.ABSTRACT_JPT_GENERATE_JOB__GENERATION_FAILED);
		}
	}

	private LaunchConfigListener buildLaunchListener() {
		return new LaunchConfigListener() {
			
			public void launchCompleted(boolean genSuccessful) {
				AbstractJptGenerateJob.this.generationSuccessful = genSuccessful;
				AbstractJptGenerateJob.this.generationCompleted.setTrue();
			}
		};
	}

	private void displayError(final String message) {
		DisplayTools.syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(
					AbstractJptGenerateJob.this.getShell(),
					JptCommonUiMessages.ABSTRACT_JPT_GENERATE_JOB__ERROR,
					message
				);
			}
		});
	}

	/* CU private */ Shell getShell() {
		Display display = Display.getCurrent();
		Shell shell = (display == null) ? null : display.getActiveShell();
		if (shell == null && display != null) {
			Shell[] shells = display.getShells();
			if (shells.length > 0)
				shell = shells[0];
		}
		return shell;
	}
}
