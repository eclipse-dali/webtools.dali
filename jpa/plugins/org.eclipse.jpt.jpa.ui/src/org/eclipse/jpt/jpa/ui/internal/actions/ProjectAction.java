/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.InternalJpaPlatformUiManager;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Override any of the #execute() methods.
 */
public abstract class ProjectAction
	implements IObjectActionDelegate
{
	private ISelection currentSelection;
    

	public ProjectAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}

	public void selectionChanged(IAction action, ISelection selection) {
        this.currentSelection = selection;
	}

	protected IStructuredSelection getCurrentSelection() {
		if (this.currentSelection instanceof IStructuredSelection) {
			return (IStructuredSelection) this.currentSelection;
		}
		return null;
	}
	
	public void run(IAction action) {
		if (this.currentSelection instanceof IStructuredSelection) {
			for (Object each : ((IStructuredSelection) this.currentSelection).toArray()) {
				this.execute(each);
			}
		}
	}

	protected void execute(Object selection) {
		IProject project = this.projectFromSelection(selection);
		if (project != null) {
			this.execute(project);
		}
	}

	protected IProject projectFromSelection(Object selection) {
		if (selection instanceof IProject) {
			return (IProject) selection;
		}
		if (selection instanceof IJavaProject) {
			return ((IJavaProject) selection).getProject();
		}
		return null;
	}

	protected JpaPlatformUi getJpaPlatformUi(JpaProject project) {
        return (JpaPlatformUi) project.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	protected void execute(IProject project) {
		JpaProject jpaProject = this.getJpaProject(project);
		if (jpaProject != null) {
			this.execute(jpaProject);
		}
	}

	protected JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	protected abstract void execute(JpaProject project);
}
