/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.j2ee.project.facet.JavaProjectMigrationOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;

public class MigrateJavaProjectAction implements IObjectActionDelegate
{
	private ISelection currentSelection;
	
	
	public MigrateJavaProjectAction() {
		super();
	}
	
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.currentSelection = selection;
	}
	
	public void run(IAction action) {
		// This action is currently enabled only for a singly selected, java,
		// non-faceted IProject
		IProject project = (IProject) ((IStructuredSelection) currentSelection).getFirstElement();
		execute(project);
	}
	
	private void execute(IProject project) {
		// add facets nature, java facet, and utility facet to project
		JavaProjectMigrationOperation operation = 
			J2EEProjectUtilities.createFlexJavaProjectForProjectOperation(project, false);
		operation.execute(null, null);
		
		IFacetedProject facetedProject;
		try {
			// get the faceted project
			facetedProject = ProjectFacetsManager.create(project);
		}
		catch (CoreException ce) {
			JptUiPlugin.log(ce);
			return;
		}
		
		// launch the UI with JPA facet preselected
		final ModifyFacetedProjectWizard wizard = new ModifyFacetedProjectWizard(facetedProject);
		IFacetedProjectWorkingCopy facetedProjectWorkingCopy = wizard.getFacetedProjectWorkingCopy();
		IProjectFacetVersion jpa1_0 = ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID).getDefaultVersion();
		facetedProjectWorkingCopy.addProjectFacet(jpa1_0);
		
		final WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		dialog.open();
	}
}
