/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.PlatformRegistry;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Override any of the #execute() methods.
 */
public abstract class ProjectAction implements IObjectActionDelegate {

	private ISelection currentSelection;
    

	public ProjectAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
        this.currentSelection = selection;
	}

	protected IStructuredSelection getCurrentSelection() {
		if (this.currentSelection instanceof IStructuredSelection) {
			return (IStructuredSelection) this.currentSelection;
		}
		return null;
	}
	
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (this.currentSelection instanceof IStructuredSelection) {
			for (Iterator stream = ((IStructuredSelection) this.currentSelection).iterator(); stream.hasNext(); ) {
				this.execute(stream.next());
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

	protected IJpaPlatformUi jpaPlatformUi(IJpaProject project) {
		String coreJpaPlatformId = project.jpaPlatform().getId();
        return PlatformRegistry.instance().jpaPlatform(coreJpaPlatformId); 
	}
	
	protected void execute(IProject project) {
		IJpaProject jpaProject = JptCorePlugin.jpaProject(project);
		if (jpaProject == null) {
			return;
		}
		this.execute(jpaProject);
	}

	protected void execute(IJpaProject project) {
		throw new UnsupportedOperationException();
	}

}
