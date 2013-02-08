/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.internal.FacetedProjectNature;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;

/**
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class ConvertJavaProjectToJpaCommandHandler
	extends AbstractHandler
{
	
	public Object execute(ExecutionEvent event)
			throws ExecutionException {
		
		IProject project = null;
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		
		if (currentSelection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) currentSelection).getFirstElement();
			project = PlatformTools.getAdapter(element, IProject.class);
		} 
		
		if (project == null) {
			return null;
		}
		
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		
		if (activeWorkbenchWindow != null) {
			Shell shell = activeWorkbenchWindow.getShell();
			
			ConvertJavaProjectToJpaRunnable.runInProgressDialog(shell, project);
		}
		
		return null;
	}
	
	
	private static class ConvertJavaProjectToJpaRunnable
			implements IRunnableWithProgress {
		
		private final IProject project;
		private IFacetedProjectWorkingCopy fprojwc;

		public static void runInProgressDialog(Shell shell, IProject project) {
			final boolean startedWithFacetNature = hasFacetNature(project);
			final ConvertJavaProjectToJpaRunnable runnable 
					= new ConvertJavaProjectToJpaRunnable(project);

			try {
				try {
					new ProgressMonitorDialog(shell).run(true, true, runnable);
				}
				catch (InvocationTargetException ex) {
					JptJpaUiPlugin.instance().logError(ex);
					return;
				}
				
				ModifyFacetedProjectWizard wizard 
						= new ModifyFacetedProjectWizard(runnable.getFacetedProjectWorkingCopy());
				WizardDialog dialog = new WizardDialog(shell, wizard);
				
				if (dialog.open() == Window.CANCEL) {
					//throw an InterruptedException since this is also what the above
					//ProgressMonitorDialog will do if the user cancels that.
					throw new InterruptedException();
				}
			}
			catch(InterruptedException ex) {
				//No need to log since InterruptedException is thrown when the user cancels:
				//1. the ProgressMonitorDialog for converting to a faceted project
				//2. the ModifyFacetedProjectWizard
				if (!startedWithFacetNature && hasFacetNature(project)) {
					//only remove the facet nature if the project did not have it to begin with
					//and it has already been added before cancellation
					removeFacetNature(project);
				}
				Thread.currentThread().interrupt();
			}
		}

		protected static boolean hasFacetNature(IProject project) {
			try {
				return project.getDescription().hasNature(FacetedProjectNature.NATURE_ID);
			}
			catch (CoreException ce) {
				JptJpaUiPlugin.instance().logError(ce);
			}
			return false;
		}

		protected static void removeFacetNature(IProject project) {
			try {
				IProjectDescription description = project.getDescription();
				String[] prevNatures = description.getNatureIds();
				String[] newNatures = ArrayTools.remove(prevNatures, FacetedProjectNature.NATURE_ID);
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}
			catch (CoreException ce) {
				JptJpaUiPlugin.instance().logError(ce);
			}
		}
		
		
		public ConvertJavaProjectToJpaRunnable(IProject project) {
			this.project = project;
		}
		
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			
			monitor.beginTask(JptJpaUiMessages.convertToJpa_convertingProject, 1000);
			try {
				IProgressMonitor createProgressMonitor = new SubProgressMonitor(monitor, 100);
				IFacetedProject fproj = ProjectFacetsManager.create(this.project, true, createProgressMonitor);
				
				if (monitor.isCanceled()) {
					throw new InterruptedException();
				}
				
				monitor.setTaskName(JptJpaUiMessages.convertToJpa_detectingTechnologies);				
				IProgressMonitor detectProgressMonitor = new SubProgressMonitor(monitor, 400);
				this.fprojwc = fproj.createWorkingCopy();
				this.fprojwc.detect(detectProgressMonitor);

				monitor.setTaskName(JptJpaUiMessages.convertToJpa_installingJpaFacet);
				this.fprojwc.addProjectFacet(JpaProject.FACET.getDefaultVersion());
			}
			catch (CoreException e) {
				throw new InvocationTargetException(e);
			}
			finally {
				monitor.done();
			}
		}
		
		protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
			return this.fprojwc;
		}
	}
}
