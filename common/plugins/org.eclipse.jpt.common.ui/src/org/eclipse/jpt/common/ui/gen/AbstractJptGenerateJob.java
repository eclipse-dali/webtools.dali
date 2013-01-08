/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 *  AbstractJptGenerateJob
 */
public abstract class AbstractJptGenerateJob extends WorkspaceJob {
	private final IJavaProject javaProject;

	private final SynchronizedBoolean generationCompleted;
	private boolean generationSuccessful;

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
			SWTUtil.asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, file, true);
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
		
		this.postGenerate(this.generationSuccessful);
	}
	
	private void postGenerate(boolean generationSuccessful) {
		if( ! generationSuccessful) {
			this.displayError(JptCommonUiMessages.AbstractJptGenerateJob_generationFailed);
			return;
		}
		else {
			this.postGenerate();
		}
	}

	private LaunchConfigListener buildLaunchListener() {
		return new LaunchConfigListener() {
			
			public void launchCompleted(boolean generationSuccessful) {
				AbstractJptGenerateJob.this.generationSuccessful = generationSuccessful;
				AbstractJptGenerateJob.this.generationCompleted.setTrue();
			}
		};
	}

	private void displayError(final String message) {
		SWTUtil.syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(
					AbstractJptGenerateJob.this.getShell(),
					JptCommonUiMessages.AbstractJptGenerateJob_error,
					message
				);
			}
		});
	}

	private Shell getShell() {
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