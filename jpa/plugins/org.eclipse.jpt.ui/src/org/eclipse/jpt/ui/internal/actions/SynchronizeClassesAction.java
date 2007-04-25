package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.synch.SynchronizeClassesJob;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class SynchronizeClassesAction 
	implements IObjectActionDelegate 
{
	private IFile file;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// no-op for now
	}

	public void run(IAction action) {
		SynchronizeClassesJob job = new SynchronizeClassesJob(file);
		job.schedule();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// only one element in actual selection
		file = (IFile) ((StructuredSelection) selection).getFirstElement();
	}
}
