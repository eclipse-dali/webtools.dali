/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.commands;

import static org.eclipse.wst.common.project.facet.core.util.internal.FileUtil.*;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.internal.FacetedProjectNature;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;

public class ConvertJavaProjectToJpaCommandHandler
		extends AbstractHandler {
	
	public Object execute(ExecutionEvent event)
			throws ExecutionException {
		
		IProject project = null;
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		
		if (currentSelection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) currentSelection).getFirstElement();
			project = (IProject) Platform.getAdapterManager().getAdapter(element, IProject.class);
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
			final ConvertJavaProjectToJpaRunnable runnable 
					= new ConvertJavaProjectToJpaRunnable(project);
			
			try {
				new ProgressMonitorDialog(shell).run(true, true, runnable);
				
				ModifyFacetedProjectWizard wizard 
						= new ModifyFacetedProjectWizard(runnable.getFacetedProjectWorkingCopy());
				WizardDialog dialog = new WizardDialog(shell, wizard);
				
				if (dialog.open() == Dialog.CANCEL) {
					throw new InterruptedException();
				}
			}
			catch (InvocationTargetException e) {
				JptJpaUiPlugin.log(e);
			}
			catch(InterruptedException e) {
				removeFacetNature(project);
			}
		}
		
		
		public static void removeFacetNature(IProject project) {
			try {
				IProjectDescription description = project.getDescription();
				String[] prevNatures = description.getNatureIds();
				String[] newNatures = ArrayTools.remove(prevNatures, FacetedProjectNature.NATURE_ID);
				description.setNatureIds( newNatures );
				
				validateEdit( project.getFile( FILE_DOT_PROJECT ) );
		            
				project.setDescription(description, null);
			}
			catch (CoreException ce) {
				JptJpaUiPlugin.log(ce);
			}
		}
		
		
		public ConvertJavaProjectToJpaRunnable(IProject project) {
			this.project = project;
		}
		
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			
			monitor.beginTask(JptUiMessages.convertToJpa_convertingProject, 1000);
			
			try {
				IProgressMonitor createProgressMonitor = new SubProgressMonitor(monitor, 100);
				IFacetedProject fproj = ProjectFacetsManager.create(this.project, true, createProgressMonitor);
				
				if (monitor.isCanceled()) {
					throw new InterruptedException();
				}
				
				monitor.setTaskName(JptUiMessages.convertToJpa_detectingTechnologies);
				
				IProgressMonitor detectProgressMonitor = new SubProgressMonitor(monitor, 800);
				this.fprojwc = fproj.createWorkingCopy();
				this.fprojwc.detect(detectProgressMonitor);
				
				if (! this.fprojwc.hasProjectFacet(JpaFacet.FACET)) {
					this.fprojwc.addProjectFacet(JpaFacet.FACET.getDefaultVersion());
				}
			}
			catch(CoreException e) {
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
